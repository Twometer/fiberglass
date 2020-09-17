package de.twometer.fiberglass.http;

public class MimeType {

    public static final String CSS = "text/css";
    public static final String TXT = "text/plain";
    public static final String HTML = "text/html";
    public static final String JS = "text/javascript";
    public static final String JSON = "application/json";
    public static final String PNG = "image/png";
    public static final String JPEG = "image/jpeg";
    public static final String WEBP = "image/webp";
    public static final String SVG = "image/svg+xml";
    public static final String ICO = "image/x-icon";
    public static final String BMP = "image/bmp";
    public static final String GIF = "image/gif";
    public static final String WAVE = "audio/wave";
    public static final String OGG = "audio/ogg";
    public static final String WEBM = "video/webm";
    public static final String OCTET_STREAM = "application/octet-stream";

    public static String getMimeType(String filename) {
        var ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        switch (ext) {
            case "css":
                return CSS;
            case "txt":
                return TXT;
            case "html":
                return HTML;
            case "js":
                return JS;
            case "json":
                return JSON;
            case "png":
                return PNG;
            case "jfif":
            case "pjpeg":
            case "pjp":
            case "jpeg":
            case "jpg":
                return JPEG;
            case "webp":
                return WEBP;
            case "svg":
                return SVG;
            case "cur":
            case "ico":
                return ICO;
            case "bmp":
                return BMP;
            case "gif":
                return GIF;
            case "wav":
                return WAVE;
            case "ogg":
                return OGG;
            case "webm":
                return WEBM;
            default:
                return OCTET_STREAM;
        }
    }

}
