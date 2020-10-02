package de.twometer.fiberglass.hosting.impl;

import de.twometer.fiberglass.api.Controller;
import de.twometer.fiberglass.api.annotation.Http;
import de.twometer.fiberglass.api.annotation.Index;
import de.twometer.fiberglass.api.annotation.Param;
import de.twometer.fiberglass.http.StatusCode;
import de.twometer.fiberglass.request.HttpRequest;
import de.twometer.fiberglass.response.ErrorResponse;
import de.twometer.fiberglass.response.IResponse;
import de.twometer.fiberglass.response.ResponseFactory;
import de.twometer.fiberglass.routing.ParsedRequestRoute;
import de.twometer.fiberglass.util.TypeConverter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;

public class ControllerInvoker {

    private final Class<?> controllerClass;
    private final Controller controller;

    public ControllerInvoker(Class<?> controllerClass, Controller controller) {
        this.controllerClass = controllerClass;
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

    public IResponse invoke(ControllerRequestContext context) {
        var methods = controllerClass.getMethods();
        for (var method : methods) {
            var isIndex = method.isAnnotationPresent(Index.class);
            Http httpAnnotation = method.getAnnotation(Http.class);
            if (httpAnnotation == null || httpAnnotation.value() != context.getRequest().getMethod())
                continue;

            if (methodMatches(context, isIndex, method)) {
                var parameters = prepareParameters(context, method);
                return invokeControllerMethod(method, parameters);
            }
        }

        return new ErrorResponse(StatusCode.NotFound);
    }

    private IResponse invokeControllerMethod(Method method, Object[] parameters) {
        try {
            return (IResponse) method.invoke(controller, parameters);
        } catch (InvocationTargetException e) {
            return ResponseFactory.newInternalServerError(e.getTargetException());
        } catch (IllegalAccessException e) {
            return ResponseFactory.newInternalServerError(e);
        }
    }

    private Object[] prepareParameters(ControllerRequestContext context, Method method) {
        var parameters = method.getParameters();
        var parameterValues = new Object[method.getParameterCount()];
        for (int i = 0; i < parameters.length; i++) {
            var parameter = parameters[i];

            if (parameter.getType() == HttpRequest.class) {
                parameterValues[i] = context.getRequest();
                continue;
            }

            var parameterValue = findParameterValue(context, parameter);
            parameterValues[i] = TypeConverter.convertType(parameter.getType(), parameterValue);
        }
        return parameterValues;
    }

    private String findParameterValue(ControllerRequestContext context, Parameter parameter) {
        return context.getRequestRoute().getPathParameters().entrySet()
                .stream()
                .filter(c -> parameterMatches(parameter, c.getKey()))
                .map(Map.Entry::getValue)
                .findAny()
                .orElse(
                        context.getRequest().getQuery().entrySet()
                                .stream()
                                .filter(c -> parameterMatches(parameter, c.getKey()))
                                .map(Map.Entry::getValue)
                                .findAny()
                                .orElse(null)
                );
    }

    private boolean parameterMatches(Parameter parameter, String name) {
        Param paramAnnotation = parameter.getAnnotation(Param.class);
        return paramAnnotation != null && paramAnnotation.value().equalsIgnoreCase(name);
    }

    private boolean methodMatches(ControllerRequestContext context, boolean isIndex, Method method) {
        var returnTypeOk = method.getReturnType() == IResponse.class;
        var nameOk = methodNameMatches(isIndex, context.getRequestRoute().getAction(), method.getName());
        var signatureOk = methodSignatureMatches(context.getRequestRoute(), method.getParameters());
        return returnTypeOk && nameOk && signatureOk;
    }

    private boolean methodSignatureMatches(ParsedRequestRoute route, Parameter[] signature) {
        for (var param : route.getPathParameters().keySet()) {
            if (Arrays.stream(signature).noneMatch(p -> parameterMatches(p, param))) {
                return false;
            }
        }
        return true;
    }

    private boolean methodNameMatches(boolean isIndex, String action, String methodName) {
        return (isIndex && action == null) || (!isIndex && methodName.equalsIgnoreCase(action));
    }
}
