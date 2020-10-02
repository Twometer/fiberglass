package de.twometer.fiberglass.hosting.impl;

import de.twometer.fiberglass.http.MimeType;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class StaticFileProvider {

    private String folder;

    private ClassLoader loader;

    private final Map<String, StaticFile> fileMap = new HashMap<>();

    public void initialize(String folder, ClassLoader loader) {
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

            var lastSlashIndex = resourcePath.lastIndexOf("/");
            var fileName = lastSlashIndex != -1 ? resourcePath.substring(lastSlashIndex + 1) : resourcePath;

            return new StaticFile(resourcePath, fileName, MimeType.getMimeType(resourcePath), stream.toByteArray());
        }
    }


    public StaticFile getFile(String path) throws FileNotFoundException {
        if (!fileMap.containsKey(path))
            throw new FileNotFoundException(path);
        return fileMap.get(path);
    }

    public Map<String, StaticFile> getFileMap() {
        return fileMap;
    }
}
