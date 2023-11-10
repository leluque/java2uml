package br.com.luque.java2uml.reflection;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ClazzPool {
    private Rules rules;
    private Map<Class<?>, Clazz> clazzMap;

    public ClazzPool(Rules rules) {
        this.rules = Objects.requireNonNull(rules);
        this.clazzMap = new HashMap<>();
    }

    public Clazz getFor(Class<?> originalClass) {
        Objects.requireNonNull(originalClass);
        if (clazzMap.containsKey(originalClass)) {
            return clazzMap.get(originalClass);
        }
        if (originalClass.isInterface()) {
            if (rules.includes(originalClass)) {
                clazzMap.put(originalClass, new ScopedClazz(originalClass, this));
            } else {
                clazzMap.put(originalClass, new UnscopedClazz(originalClass, this));
            }
        }
        else {
            if(rules.includes(originalClass)) {
                clazzMap.put(originalClass, ScopedClazz.newInterface(originalClass, this));
            } else {
                clazzMap.put(originalClass, UnscopedClazz.newInterface(originalClass, this));
            }
        }
        return clazzMap.get(originalClass);
    }

    public Rules getRules() {
        return rules;
    }
}
