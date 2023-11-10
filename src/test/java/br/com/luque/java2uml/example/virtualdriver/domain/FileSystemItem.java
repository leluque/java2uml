package br.com.luque.java2uml.example.virtualdriver.domain;

import java.util.Collection;

public interface FileSystemItem {
    String getName();
    void setName(String name);
    Folder getParent();
    Collection<BaseFileSystemItem> getAncestors();
    String getPath();
    long size();
}
