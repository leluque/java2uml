package br.com.luque.java2uml.example.virtualdriver.domain;

import br.com.luque.java2uml.core.classdiagram.reflection.annotation.MappedBy;

public class Bar {
    @MappedBy("bars1")
    private Foo foo;
}
