package br.com.luque.java2uml.core.instrumentation;

import java.util.ArrayList;
import java.util.List;

public enum CallPool {

    INSTANCE;

    public List<String> calls = new ArrayList<>();

    public void addCall(String call) {
        calls.add(call);
    }

    public String[] getCalls() {
        return calls.toArray(new String[0]);
    }

}
