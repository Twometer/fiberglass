package de.twometer.fiberglass.hosting.impl;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class StaticFileProvider {

    private final String folder;

    private final ClassLoader loader;

    private final Map<String, StaticFile> fileMap = new HashMap<>();

    public StaticFileProvider(String folder, ClassLoader loader) {
        this.folder = folder;
        this.loader = loader;
    }

    public void scan() throws IOException {
        scan(folder);
    }

    private void scan(String basePath) throws IOException {
        try (InputStream in = loader.getResourceAsStream(basePath); BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String resource;
            while ((resource = br.readLine()) != null) {
                var resourcePath = basePath + "/" + resource;
                if (resource.contains(".")) {
                    fileMap.put(resourcePath.substring((folder).length()), loadStaticFile(resourcePath));
                } else {
                    scan(resourcePath);
                }
            }
        }
    }

    private StaticFile loadStaticFile(String resourcePath) throws IOException {
        try (InputStream in = loader.getResourceAsStream(resourcePath)) {
            var stream = new ByteArrayOutputStream();
            var buffer = new byte[4096];

            int read;
            while ((read = in.read(buffer)) > 0)
                stream.write(buffer, 0, read);

            return new StaticFile(getMimeType(resourcePath), stream.toByteArray());
        }
    }

    private String getMimeType(String filename) {
        var ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        switch (ext) {
            case "css":
                return "text/css";
            case "txt":
                return "text/plain";
            case "html":
                return "text/html";
            case "js":
                return "text/javascript";
            case "json":
                return "application/json";
            case "png":
                return "image/png";
            case "jfif":
            case "pjpeg":
            case "pjp":
            case "jpeg":
            case "jpg":
                return "image/jpeg";
            case "webp":
                return "image/webp";
            case "svg":
                return "image/svg+xml";
            case "cur":
            case "ico":
                return "image/x-icon";
            case "bmp":
                return "image/bmp";
            case "gif":
                return "image/gif";
            case "wav":
                return "audio/wave";
            case "ogg":
                return "audio/ogg";
            case "webm":
                return "video/webm";
            default:
                return "application/octet-stream";
        }
    }

    public StaticFile getFile(String path) throws FileNotFoundException {
        if (!fileMap.containsKey(path))
            throw new FileNotFoundException(path);
        return fileMap.get(path);
    }

}
