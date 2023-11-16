package br.com.luque.java2uml.core.sequencediagram.model;

import java.util.Objects;

@SuppressWarnings("unused")
public class Message {
    private MethodExecution from;
    private MethodExecution to;

    public Message(MethodExecution to) {
        setTo(to);
    }

    public Message(MethodExecution from, MethodExecution to) {
        setFrom(from);
        setTo(to);
    }

    public MethodExecution getFrom() {
        return from;
    }

    private void setFrom(MethodExecution from) {
        this.from = Objects.requireNonNull(from);
    }

    public MethodExecution getTo() {
        return to;
    }

    private void setTo(MethodExecution to) {
        this.to = Objects.requireNonNull(to);
    }

    @Override
    public String toString() {
        return from + " -> " + to;
    }
}
