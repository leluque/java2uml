package br.com.luque.java2uml.reflection.model;

import br.com.luque.java2uml.ClazzPool;
import br.com.luque.java2uml.reflection.annotation.Aggregation;
import br.com.luque.java2uml.reflection.annotation.Composition;
import br.com.luque.java2uml.reflection.annotation.MappedBy;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
        if(isOneRelationship(field, clazzPool)) {
            return true;
        }
        if (isArrayRelationship(field, clazzPool)) {
            return true;
        }
        return isCollectionRelationship(field, clazzPool);
    }

    private static boolean isOneRelationship(java.lang.reflect.Field field, ClazzPool clazzPool) {
        return clazzPool.getRules().includes(field.getType());
    }

    private static boolean isArrayRelationship(java.lang.reflect.Field field, ClazzPool clazzPool) {
        return field.getType().isArray()
                && clazzPool.getRules().includes(field.getType().getComponentType());
    }

    private static boolean isCollectionRelationship(java.lang.reflect.Field field, ClazzPool clazzPool) {
        if(!field.getType().getPackageName().startsWith("java.util")) {
            return false;
        }
        ParameterizedType generics = (ParameterizedType) field.getGenericType();
        Type[] typeArguments = generics.getActualTypeArguments();
        for (Type typeArgument : typeArguments) {
            if (typeArgument instanceof Class<?> originalClass
                    && clazzPool.getRules().includes(originalClass)) {
                return true;
            }
        }
        return false;
    }

    private void extractRelationshipInfo() {
        if(isOneRelationship(getField(), getClazzPool())) {
            cardinality = Cardinalities.ONE;
            otherSide = getClazzPool().getFor(getField().getType());
        } else if (isArrayRelationship(getField(), getClazzPool())) {
            cardinality = Cardinalities.N;
            otherSide = getClazzPool().getFor(getField().getType().getComponentType());
        } else if (isCollectionRelationship(getField(), getClazzPool())) {
            cardinality = Cardinalities.N;
            ParameterizedType generics = (ParameterizedType) getField().getGenericType();
            Type[] typeArguments = generics.getActualTypeArguments();
            for (Type typeArgument : typeArguments) {
                if (typeArgument instanceof Class<?> originalClass
                        && getClazzPool().getRules().includes(originalClass)) {
                    otherSide = getClazzPool().getFor(originalClass);
                    break;
                }
            }
        }

        MappedBy mappedByAnnotation = getField().getAnnotation(MappedBy.class);
        if(null != mappedByAnnotation) {
            mappedBy = mappedByAnnotation.value();
        }

        setVariation(Variations.ASSOCIATION);
        Aggregation aggregationAnnotation = getField().getAnnotation(Aggregation.class);
        if(null != aggregationAnnotation) {
            setVariation(Variations.AGGREGATION);
        }
        Composition compositionAnnotation = getField().getAnnotation(Composition.class);
        if(null != compositionAnnotation) {
            setVariation(Variations.COMPOSITION);
        }
    }
}
