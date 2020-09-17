package de.twometer.fiberglass.hosting.impl;

public class StaticFile {

    private final String mimeType;

    private final byte[] contents;

    public StaticFile(String mimeType, byte[] contents) {
        this.mimeType = mimeType;
        this.contents = contents;
    }

    public String getMimeType() {
        return mimeType;
    }

    public byte[] getContents() {
        return contents;
    }

}
