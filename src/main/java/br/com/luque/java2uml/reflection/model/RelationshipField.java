package br.com.luque.java2uml.reflection.model;

import br.com.luque.java2uml.ClazzPool;
import br.com.luque.java2uml.reflection.annotation.Aggregation;
import br.com.luque.java2uml.reflection.annotation.Composition;
import br.com.luque.java2uml.reflection.annotation.MappedBy;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class RelationshipField extends Field {
    public enum Cardinalities {
        ONE, N
    }

    private Cardinalities cardinality;

    public enum Variations {
        ASSOCIATION, AGGREGATION, COMPOSITION
    }

    private Variations variation;

    private Clazz otherSide;
    private String mappedBy;

    public RelationshipField(Clazz clazz, java.lang.reflect.Field field, ClazzPool clazzPool) {
        super(clazz, field, clazzPool);
        extractRelationshipInfo();
    }

    public Cardinalities getCardinality() {
        return cardinality;
    }

    public Variations getVariation() {
        return variation;
    }

    public void setVariation(Variations variation) {
        this.variation = Objects.requireNonNull(variation);
    }

    public Clazz getOtherSide() {
        return otherSide;
    }

    public String getMappedBy() {
        return mappedBy;
    }

    public boolean isMappedBy() {
        return null != mappedBy;
    }

    public boolean isAssociation() {
        return Variations.ASSOCIATION.equals(getVariation());
    }

    public boolean isAggregation() {
        return Variations.AGGREGATION.equals(getVariation());
    }

    public boolean isComposition() {
        return Variations.COMPOSITION.equals(getVariation());
    }

    public static boolean isRelationship(java.lang.reflect.Field field, ClazzPool clazzPool) {
        if (isOneRelationship(field, clazzPool)) {
            return true;
        }
        if (isPureArrayRelationship(field, clazzPool)) {
            return true;
        }
        return isGenericRelationship(field, clazzPool);
    }

    private static boolean isOneRelationship(java.lang.reflect.Field field, ClazzPool clazzPool) {
        return clazzPool.getRules().includes(field.getType());
    }

    private static boolean isPureArrayRelationship(java.lang.reflect.Field field, ClazzPool clazzPool) {
        if (!field.getType().isArray()) {
            return false;
        }

        return clazzPool.getRules().includes(extractArrayType(field, clazzPool));
    }

    private static boolean isCollectionRelationship(java.lang.reflect.Field field, ClazzPool clazzPool) {
        return field.getType().getPackageName().startsWith("java.util");
    }

    private static boolean isGenericRelationship(java.lang.reflect.Field field, ClazzPool clazzPool) {
        if (!field.getType().getPackageName().startsWith("java.util")) {
            return false;
        }

        return extractScopedGenerics(field, clazzPool).length > 0;
    }

    private static Class<?> extractArrayType(java.lang.reflect.Field field, ClazzPool clazzPool) {
        if (!field.getType().isArray()) {
            return null;
        }

        // Handle the case of arrays of arrays ...
        Class<?> componentType = field.getType().getComponentType();
        while (componentType.isArray()) {
            componentType = componentType.getComponentType();
        }
        return componentType;
    }

    private static Class<?>[] extractScopedGenerics(java.lang.reflect.Field field, ClazzPool clazzPool) {
        // Handle the case of arrays of arrays of collections ...
        Type genericFieldType = field.getGenericType();
        while (genericFieldType instanceof GenericArrayType) {
            genericFieldType = ((GenericArrayType) genericFieldType).getGenericComponentType();
        }

        List<Class<?>> scopedGenerics = new ArrayList<>();
        if (genericFieldType instanceof ParameterizedType) {
            ParameterizedType generics = (ParameterizedType) genericFieldType;
            Type[] typeArguments = generics.getActualTypeArguments();
            for (Type typeArgument : typeArguments) {
                if (typeArgument instanceof Class<?> originalClass
                    && clazzPool.getRules().includes(originalClass)) {
                    scopedGenerics.add(originalClass);
                }
            }
        }
        return scopedGenerics.toArray(new Class<?>[0]);
    }

    private void extractRelationshipInfo() {
        if (isPureArrayRelationship(getField(), getClazzPool())) {
            cardinality = Cardinalities.N;
            otherSide = getClazzPool().getFor(extractArrayType(getField(), getClazzPool()));
        } else if (isGenericRelationship(getField(), getClazzPool())) {
            cardinality = isCollectionRelationship(getField(), getClazzPool()) ? Cardinalities.N : Cardinalities.ONE;

            // TODO: handle multiple relationships (more than one generic in the scope).
            Class<?>[] scopedGenerics = extractScopedGenerics(getField(), getClazzPool());
            if (scopedGenerics.length > 0) {
                otherSide = getClazzPool().getFor(scopedGenerics[0]);
            }
        } else if (isOneRelationship(getField(), getClazzPool())) { // This one must go last because it doesn't check if it is array nor collection to avoid double check.
            cardinality = Cardinalities.ONE;
            otherSide = getClazzPool().getFor(getField().getType());
        }

        MappedBy mappedByAnnotation = getField().getAnnotation(MappedBy.class);
        if (null != mappedByAnnotation) {
            mappedBy = mappedByAnnotation.value();
        }

        setVariation(Variations.ASSOCIATION);
        Aggregation aggregationAnnotation = getField().getAnnotation(Aggregation.class);
        if (null != aggregationAnnotation) {
            setVariation(Variations.AGGREGATION);
        }
        Composition compositionAnnotation = getField().getAnnotation(Composition.class);
        if (null != compositionAnnotation) {
            setVariation(Variations.COMPOSITION);
        }
    }
}
