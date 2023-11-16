package br.com.luque.java2uml.core.sequencediagram.instrumentation.data;

public class C {
    public static void c1() {
        System.out.println("c1");
    }

    public void c2() {
        c1();
        System.out.println("c2");
    }
}
