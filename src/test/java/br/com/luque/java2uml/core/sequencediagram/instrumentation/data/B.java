package br.com.luque.java2uml.core.sequencediagram.instrumentation.data;

public class B {
    public B() {
        C c = new C();
        c.c2();
    }

    public void b1() {
        b2();
    }

    public void b2() {
        System.out.println("b2");
    }
}
