package br.com.luque.java2uml;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class Rules {
    private final Set<String> packages;
    private final Set<String> classes;
    private final Set<String> ignorePackages;
    private final Set<String> ignoreClasses;

    public Rules() {
        this.packages = new HashSet<>();
        this.classes = new HashSet<>();
        this.ignorePackages = new HashSet<>();
        this.ignoreClasses = new HashSet<>();
    }

    public Rules addPackages(String... packagesName) {
        Objects.requireNonNull(packagesName);
        this.packages.addAll(Stream.of(packagesName).filter(p -> !p.isEmpty()).toList());
        return this;
    }

    public Rules addClasses(String... classesName) {
        Objects.requireNonNull(classesName);
        this.classes.addAll(Stream.of(classesName).filter(c -> !c.isEmpty()).toList());
        return this;
    }

    public Rules ignorePackages(String... packagesName) {
        Objects.requireNonNull(packagesName);
        this.ignorePackages.addAll(Stream.of(packagesName).filter(p -> !p.isEmpty()).toList());
        return this;
    }

    public Rules ignoreClasses(String... classesName) {
        Objects.requireNonNull(classesName);
        this.ignoreClasses.addAll(Stream.of(classesName).filter(c -> !c.isEmpty()).toList());
        return this;
    }

    public Set<String> getPackages() {
        return packages;
    }

    public Set<String> getClasses() {
        return classes;
    }

    public Set<String> getIgnorePackages() {
        return ignorePackages;
    }

    public Set<String> getIgnoreClasses() {
        return ignoreClasses;
    }

    public boolean includes(Class<?> originalClass) {
        return
            (
                packages.stream().anyMatch(p -> originalClass.getPackageName().startsWith(p)) ||
                    classes.stream().anyMatch(c -> originalClass.getName().equals(c))
            ) &&
                (
                    ignorePackages.stream().noneMatch(p -> originalClass.getPackageName().startsWith(p)) &&
                        ignoreClasses.stream().noneMatch(c -> originalClass.getName().equals(c))
                );
    }
}
