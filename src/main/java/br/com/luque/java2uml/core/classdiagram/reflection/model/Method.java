package br.com.luque.java2uml.core.classdiagram.reflection.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class Method extends BaseItem {
    private java.lang.reflect.Method method;
    private java.lang.reflect.Constructor<?> constructor;
    private Visibilities visibility;
    private Parameter[] parameters;
    private Clazz returnType;
    private boolean static_;
    private boolean abstract_;

    public Method(java.lang.reflect.Method method, ClazzPool clazzPool) {
        super(method.getName(), clazzPool);
        setMethod(method);
        extractMethodInfo();
    }

    public Method(java.lang.reflect.Constructor<?> constructor, ClazzPool clazzPool) {
        super(constructor.getDeclaringClass().getSimpleName(), clazzPool);
        setConstructor(constructor);
        extractConstructorInfo();
    }

    public void setMethod(java.lang.reflect.Method method) {
        this.method = Objects.requireNonNull(method);
    }

    public void setConstructor(java.lang.reflect.Constructor<?> constructor) {
        this.constructor = Objects.requireNonNull(constructor);
    }

    public Visibilities getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibilities visibility) {
        this.visibility = Objects.requireNonNull(visibility);
    }

    public Parameter[] getParameters() {
        return parameters;
    }

    public void setParameters(Parameter[] parameters) {
        this.parameters = parameters;
    }

    public Clazz getReturnType() {
        return returnType;
    }

    public void setReturnType(Clazz returnType) {
        this.returnType = Objects.requireNonNull(returnType);
    }

    public boolean isConstructor() {
        return returnType == null;
    }

    public boolean isStatic() {
        return static_;
    }

    public boolean isAbstract() {
        return abstract_;
    }

    public boolean hasDependency() {
        return getDependencies().length > 0;
    }

    public Clazz[] getDependencies() {
        List<Clazz> dependencies = new ArrayList<>();
        if (returnType != null && getClazzPool().getRules().includes(returnType.getJavaClass())) {
            dependencies.add(returnType);
        }
        Stream.of(parameters).filter(p -> getClazzPool().getRules().includes(p.getType().getJavaClass())).forEach(p -> dependencies.add(p.getType()));
        return dependencies.toArray(Clazz[]::new);
    }

    private void extractMethodInfo() {
        this.visibility = Visibilities.fromModifiers(method.getModifiers());
        this.parameters = Stream.of(method.getParameters()).map(p -> new Parameter(p.getName(), getClazzPool().getFor(p.getType()), getClazzPool())).toArray(Parameter[]::new);
        this.returnType = getClazzPool().getFor(method.getReturnType());
        this.static_ = java.lang.reflect.Modifier.isStatic(method.getModifiers());
        this.abstract_ = java.lang.reflect.Modifier.isAbstract(method.getModifiers());
    }

    private void extractConstructorInfo() {
        this.visibility = Visibilities.fromModifiers(constructor.getModifiers());
        this.parameters = Stream.of(constructor.getParameters()).map(p -> new Parameter(p.getName(), getClazzPool().getFor(p.getType()), getClazzPool())).toArray(Parameter[]::new);
    }
}
