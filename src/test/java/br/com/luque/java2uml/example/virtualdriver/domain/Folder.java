package br.com.luque.java2uml.example.virtualdriver.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Folder extends BaseFileSystemItem {
	private final Set<FileSystemItem> children;

	public Folder(String name) {
		this(name, null);
	}
	
	public Folder(String name, Folder parent) {
		super(name, parent);
		this.children = new HashSet<>();
	}

	public Collection<FileSystemItem> getChildren() {
		return new ArrayList<>(this.children);
	}

	public boolean isRoot() {
		return getParent() == null;
	}

	public boolean containsChild(FileSystemItem child) {
		return this.children.contains(child);
	}

	public File newFile(String name, String contentType, long size) {
		File newFile = new File(name, contentType, size, this);
		this.children.add(newFile);
		return newFile;
	}

	public Folder newFolder(String name) {
		Folder newFolder = new Folder(name, this);
		this.children.add(newFolder);
		return newFolder;
	}

	public long size() {
		return children.stream().mapToLong(FileSystemItem::size).sum();
	}
}