package br.com.luque.java2uml.example.virtualdriver.domain;

import java.util.Objects;

@SuppressWarnings("unused")
public class File extends BaseFileSystemItem {
    private String contentType;
    private long size;

    public File(String name, String contentType, long size, Folder parent) {
        super(name, Objects.requireNonNull(parent));
        setContentType(contentType);
        setSize(size);
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        Objects.requireNonNull(contentType);
        contentType = contentType.trim();
        if (contentType.isEmpty()) {
            throw new IllegalArgumentException("Cannot be empty!");
        }
        this.contentType = contentType;
    }

    public void setSize(long size) {
        if (size < 0) {
            throw new IllegalArgumentException("Must be positive.");
        }
        this.size = size;
    }

    @Override
    public long size() {
        return this.size;
    }
}
