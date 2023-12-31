package br.com.luque.java2uml.example.virtualdriver.domain;

import br.com.luque.java2uml.core.classdiagram.reflection.annotation.Aggregation;
import br.com.luque.java2uml.core.classdiagram.reflection.annotation.Composition;
import br.com.luque.java2uml.core.classdiagram.reflection.annotation.MappedBy;

import java.util.Set;

public class Foo {

    @Aggregation
    private Set<Bar> bars1;
    @Composition
    @MappedBy("foo")
    private Set<Bar> bars2;

}
