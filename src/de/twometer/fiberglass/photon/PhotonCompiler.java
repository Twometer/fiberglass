package de.twometer.fiberglass.photon;

import de.twometer.fiberglass.hosting.impl.StaticFile;
import de.twometer.fiberglass.util.StringUtil;
import org.joor.Reflect;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class PhotonCompiler {

    public CompiledPhotonPage compile(StaticFile file) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(file.getContents())));
        var writer = new JavaWriter();

        var className = createUniqueClassName(file);

        writer.openClass(className, "de.twometer.fiberglass.photon.CompiledPhotonPage");

        writer.openFunction("void", "render", "Object model_in, StringBuilder output");

        var nestingDepth = 0;

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("@photon")) {
                // metadata
                var parts = StringUtil.split(line, ' ', true);
                if (parts.length != 3)
                    continue;

                if (parts[1].equalsIgnoreCase("model")) {
                    writer.writeStatement("var model = (" + parts[2] + ") model_in");
                }

                if (parts[1].equalsIgnoreCase("version")) {
                    writer.writeLine("// page version " + parts[2]);
                }

            } else {
                // code
                if (line.trim().startsWith("@") && line.trim().endsWith("{")) {
                    nestingDepth++;
                    writer.writeLine(line.substring(1));
                    continue;
                }

                if (line.trim().equals("}") && nestingDepth > 0) {
                    writer.writeLine(line);
                    nestingDepth--;
                    continue;
                }

                if (line.isBlank())
                    continue;

                writer.writeStatement(processLine(line));
            }
        }

        writer.closeBlock();
        writer.closeBlock();

        var generatedCode = writer.toString();
        return Reflect.compile(className, generatedCode).create().get();
    }

    private String processLine(String s) {
        var outBuilder = new StringBuilder();
        var prevChar = '\0';
        var inElement = false;
        outBuilder.append("output.append(\"");
        for (var i = 0; i < s.length(); i++) {
            var chr = s.charAt(i);
            var nextChr = (i + 1) < s.length() ? s.charAt(i + 1) : '\0';

            if (chr == '\\' && nextChr == '@') {
                prevChar = chr;
                continue;
            }

            if (chr == '@' && prevChar != '\\' && nextChr == '{') {
                inElement = true;
                outBuilder.append("\").append(");
                continue;
            }
            if (inElement && chr == '{')
                continue;

            if (inElement) {
                if (chr != '}')
                    outBuilder.append(chr);
            } else {
                if (chr == '\\')
                    outBuilder.append("\\\\");
                else if (chr == '\"')
                    outBuilder.append("\\\"");
                else
                    outBuilder.append(chr);
            }

            if (inElement && chr == '}') {
                outBuilder.append(").append(\"");
                inElement = false;
            }


            prevChar = chr;
        }

        if (inElement)
            throw new IllegalStateException("Expected closing bracket.");

        outBuilder.append("\").append(\"\\n\")");
        return outBuilder.toString();
    }

    private String createUniqueClassName(StaticFile file) {
        var builder = new StringBuilder();
        builder.append("PhGen_");
        for (char c : file.getName().toCharArray()) {
            if (Character.isLetterOrDigit(c))
                builder.append(c);
        }
        return builder.toString();
    }

}
