package br.com.luque.java2uml.core.sequencediagram.model;

import java.time.Instant;
import java.util.Objects;

@SuppressWarnings("unused")
public class Message implements Comparable<Message> {
    private Instant instant;
    private MethodExecution from;
    private MethodExecution to;

    public enum Types {
        SYNCHRONOUS, ASYNCHRONOUS, CREATION
    }

    private Types type;

    public Message(MethodExecution to) {
        setInstant(Instant.now());
        setTo(to);
        setType(getDefaultTypeFor(to));
    }

    public Message(MethodExecution to, Types type) {
        setInstant(Instant.now());
        setTo(to);
        setType(type);
    }

    public Message(MethodExecution from, MethodExecution to) {
        setInstant(Instant.now());
        setFrom(from);
        setTo(to);
        setType(getDefaultTypeFor(to));
    }

    public Message(MethodExecution from, MethodExecution to, Types type) {
        setInstant(Instant.now());
        setFrom(from);
        setTo(to);
        setType(type);
    }

    public Instant getInstant() {
        return instant;
    }

    private void setInstant(Instant instant) {
        this.instant = Objects.requireNonNull(instant);
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

    public Types getType() {
        return type;
    }

    public static Types getDefaultTypeFor(MethodExecution methodExecution) {
        return methodExecution.getMethod().isConstructor() ? Types.CREATION : Types.SYNCHRONOUS;
    }

    public void setType(Types type) {
        Objects.requireNonNull(type);
        if ((to.getMethod().isConstructor() && Types.CREATION != type) ||
            (!to.getMethod().isConstructor() && Types.CREATION == type)) {
            throw new IllegalArgumentException("The type CREATION must be used for constructors");
        }
        this.type = type;
    }

    @Override
    public int compareTo(Message other) {
        return instant.compareTo((other.instant));
    }

    @Override
    public String toString() {
        return from + " -> " + to;
    }
}
