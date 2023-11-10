package br.com.luque.java2uml.example.virtualdriver.domain;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class BaseFileSystemItem implements FileSystemItem {
	private String name;
	private Folder parent;
	
	public BaseFileSystemItem(String name) {
		this(name, null);
	}
	
	public BaseFileSystemItem(String name, Folder parent) {
		setName(name);
		setParent(parent);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		Objects.requireNonNull(name);
		name = name.trim();
		if (name.isEmpty()) {
			throw new IllegalArgumentException("Cannot be empty!");
		}
		this.name = name;
	}

	@Override
	public Folder getParent() {
		return this.parent;
	}

	private void setParent(Folder parent) {
		this.parent = parent;
	}
	
	@Override
	public Collection<BaseFileSystemItem> getAncestors() {
		LinkedList<BaseFileSystemItem> ancestors = new LinkedList<>();
		ancestors.add(this);
		BaseFileSystemItem current = this;
		while (current.getParent() != null) {
			ancestors.addFirst(current.getParent());
			current = current.getParent();
		}
		return ancestors;
	}

	@Override
	public String getPath() {
		return getAncestors().stream().map(BaseFileSystemItem::getName).collect(Collectors.joining("/"));
	}
}
