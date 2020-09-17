package de.twometer.fiberglass.response;

import com.google.gson.Gson;
import de.twometer.fiberglass.http.MimeType;

public class JsonResponse extends HttpTextResponse {

    public JsonResponse(Object obj) {
        var gson = new Gson();
        setTextBody(gson.toJson(obj));
        addHeader("Content-Type", MimeType.JSON);
    }

}
