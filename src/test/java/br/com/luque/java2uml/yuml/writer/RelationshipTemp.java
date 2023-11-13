package br.com.luque.java2uml.yuml.writer;

import br.com.luque.java2uml.reflection.model.data.ClassWithOnlyCommonJavaTypes;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class RelationshipTemp {
    private ClassWithOnlyCommonJavaTypes field2;
    private ClassWithOnlyCommonJavaTypes[] field3;
    private ClassWithOnlyCommonJavaTypes[][] field4;
    private List<ClassWithOnlyCommonJavaTypes> field5;
    private List<ClassWithOnlyCommonJavaTypes>[][] field6;

    public static void main(String[] args) {
        try {
            Field field = RelationshipTemp.class.getDeclaredField("field6");

            System.out.println(field.getType().getPackageName());

            // Get the generic type of the field
            Type genericFieldType = field.getGenericType();

            if (genericFieldType instanceof GenericArrayType) {
                // Get the type of the array's components (which is also an array)
                Type arrayComponentType = ((GenericArrayType) genericFieldType).getGenericComponentType();

                if (arrayComponentType instanceof GenericArrayType) {
                    // Get the type of the inner array's components (should be a ParameterizedType)
                    Type innerArrayComponentType = ((GenericArrayType) arrayComponentType).getGenericComponentType();

                    if (innerArrayComponentType instanceof ParameterizedType) {
                        ParameterizedType parameterizedType = (ParameterizedType) innerArrayComponentType;
                        Type[] typeArguments = parameterizedType.getActualTypeArguments();

                        for (Type typeArgument : typeArguments) {
                            if (typeArgument instanceof Class<?>) {
                                Class<?> argumentClass = (Class<?>) typeArgument;
                                System.out.println("Generic type: " + argumentClass.getName());
                            }
                        }
                    }
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void handleField6() throws NoSuchFieldException {
        Field field6 = RelationshipTemp.class.getDeclaredField("field6");


        // Check if the field is an array
        Class<?> fieldType = field6.getType();
        Class<?> componentType = field6.getType().getComponentType();
        while (componentType.isArray()) {
            componentType = componentType.getComponentType();
        }
        System.out.println(ParameterizedType.class.isAssignableFrom(componentType));
        if (ParameterizedType.class.isAssignableFrom(componentType)) {
            ParameterizedType genericType = (ParameterizedType) field6.getGenericType();
            Type[] typeArguments = genericType.getActualTypeArguments();

            for (Type typeArgument : typeArguments) {
                Class<?> argumentClass = (Class<?>) typeArgument;
                System.out.println("Generic type: " + argumentClass.getName());
            }
        }
    }

    public static void handleField3() throws NoSuchFieldException {
        Field field3 = RelationshipTemp.class.getDeclaredField("field4");
        System.out.println(field3.getType().isArray());
        System.out.println(field3.getType().getName());
        System.out.println(field3.getGenericType());

        Class<?> componentType = field3.getType().getComponentType();
        while (componentType.isArray()) {
            componentType = componentType.getComponentType();
        }
        System.out.println(componentType.getName());
    }


}
