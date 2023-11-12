package br.com.luque.java2uml.classsearch;

import br.com.luque.java2uml.Rules;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

public class ClasspathSearcher {
    private static final Logger logger = Logger.getLogger(ClasspathSearcher.class.getName());
    private final Rules rules;

    public ClasspathSearcher(Rules rules) {
        this.rules = Objects.requireNonNull(rules);
    }

    public Class<?>[] search() {
        return searchClasses();
    }

    private Class<?>[] searchClasses() {
        Set<Class<?>> classes = new HashSet<>();

        for (String qualifiedClassName : rules.getClasses()) {
            try {
                classes.add(Class.forName(qualifiedClassName));
            } catch (ClassNotFoundException e) {
                logger.warning("Class not found: " + qualifiedClassName);
            }
        }

        for (String packageName : rules.getPackages()) {
            try {
                Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(packageName.replace('.', '/'));
                while (resources.hasMoreElements()) {
                    URL resource = resources.nextElement();

                    classes.addAll(searchClassesRecursively(new File(resource.getPath()), packageName));
                }
            } catch (IOException e) {
                logger.warning("Package not found: " + packageName);
            }
        }

        return classes.toArray(Class<?>[]::new);
    }

    private Collection<Class<?>> searchClassesRecursively(File folder, String packageName) {
        Objects.requireNonNull(folder);
        if (!folder.exists()) {
            return Collections.emptyList();
        } else if (!folder.isDirectory()) {
            throw new IllegalArgumentException("The folder must be a directory!");
        }

        Set<Class<?>> classes = new HashSet<>();
        for (File child : Objects.requireNonNull(folder.listFiles())) {
            if (child.isDirectory()) {
                if (rules.getIgnorePackages().contains(packageName + "." + child.getName())) {
                    continue;
                }

                classes.addAll(searchClassesRecursively(child, packageName + "." + child.getName()));
            } else if (child.getName().endsWith(".class")) {
                String qualifiedClassName = packageName + "." + child.getName().substring(0, child.getName().length() - ".class".length());

                if (rules.getIgnoreClasses().contains(packageName + "." + qualifiedClassName)) {
                    continue;
                }

                try {
                    classes.add(Class.forName(qualifiedClassName));
                } catch (ClassNotFoundException e) {
                    logger.warning("Class not found: " + qualifiedClassName);
                }
            }
        }
        return classes;
    }
}
