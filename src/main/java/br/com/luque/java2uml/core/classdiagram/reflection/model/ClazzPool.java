package br.com.luque.java2uml.core.classdiagram.reflection.model;

import br.com.luque.java2uml.Rules;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("unused")
public class ClazzPool {
    private final Rules rules;
    private final Map<Class<?>, Clazz> clazzMap;

    public ClazzPool(Rules rules) {
        this.rules = Objects.requireNonNull(rules);
        this.clazzMap = new HashMap<>();
    }

    public Clazz getFor(Class<?> originalClass) {
        Objects.requireNonNull(originalClass);
        if (clazzMap.containsKey(originalClass)) {
            return clazzMap.get(originalClass);
        }
        Clazz clazz;
        if (rules.includes(originalClass)) {
            clazz = new ScopedClazz(originalClass, this);
        } else {
            clazz = new UnscopedClazz(originalClass, this);
        }
        clazzMap.put(originalClass, clazz);
        clazz.extractClassInfo();
        return clazzMap.get(originalClass);
    }


    public Rules getRules() {
        return rules;
    }

    public Clazz[] getClazzes() {
        return clazzMap.values().toArray(new Clazz[0]);
    }

    public Clazz[] getScopedClazzes() {
        return clazzMap.values().stream().filter(c -> c instanceof ScopedClazz).toArray(Clazz[]::new);
    }
}
