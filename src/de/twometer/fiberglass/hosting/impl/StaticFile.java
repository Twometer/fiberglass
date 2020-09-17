package de.twometer.fiberglass.hosting.impl;

public class StaticFile {

    private final String path;

    private final String name;

    private final String mimeType;

    private final byte[] contents;

    public StaticFile(String path, String name, String mimeType, byte[] contents) {
        this.path = path;
        this.name = name;
        this.mimeType = mimeType;
        this.contents = contents;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public byte[] getContents() {
        return contents;
    }

}
