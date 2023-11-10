package br.com.luque.java2uml;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Rules {
    private Set<String> packages;
    private Set<String> classes;
    private Set<String> ignorePackages;
    private Set<String> ignoreClasses;

    public Rules() {
        this.packages = new HashSet<>();
        this.classes = new HashSet<>();
        this.ignorePackages = new HashSet<>();
        this.ignoreClasses = new HashSet<>();
    }

    public void addPackages(String... packagesName) {
        Objects.requireNonNull(packagesName);
        this.packages.addAll(List.of(packagesName).stream().filter(p -> !p.isEmpty()).toList());
    }

    public void addClasses(String... classesName) {
        Objects.requireNonNull(classesName);
        this.classes.addAll(List.of(classesName).stream().filter(c -> !c.isEmpty()).toList());
    }

    public void ignorePackages(String... packagesName) {
        Objects.requireNonNull(packagesName);
        this.ignorePackages.addAll(List.of(packagesName).stream().filter(p -> !p.isEmpty()).toList());
    }

    public void ignoreClasses(String... classesName) {
        Objects.requireNonNull(classesName);
        this.ignoreClasses.addAll(List.of(classesName).stream().filter(c -> !c.isEmpty()).toList());
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
