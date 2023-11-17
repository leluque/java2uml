package br.com.luque.java2uml.core.sequencediagram.model;

import java.util.Arrays;
import java.util.Objects;

public class MethodExecution {
    private String id;
    private Participant participant;
    private Method method;
    private long threadId;

    public MethodExecution(Long threadId, String id, Participant participant, Method method) {
        setThreadId(threadId);
        setId(id);
        setParticipant(participant);
        setMethod(method);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Objects.requireNonNull(id);
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = Objects.requireNonNull(threadId);
    }

    public Participant getParticipant() {
        return participant;
    }

    private void setParticipant(Participant participant) {
        this.participant = Objects.requireNonNull(participant);
    }

    public Method getMethod() {
        return method;
    }

    private void setMethod(Method method) {
        this.method = Objects.requireNonNull(method);
    }

    public boolean correspondsTo(long threadId, String className, String objectId, boolean constructor, String methodName, String methodReturnType, String... methodParameterTypes) {
        return this.threadId == threadId
            && participant.getClassName().equals(className)
            && Objects.equals(participant.getObjectId(), objectId)
            && method.isConstructor()
            && method.getName().equals(methodName)
            && method.getReturnType().equals(methodReturnType)
            && Arrays.equals(method.getParameterTypes(), methodParameterTypes);
    }

    @Override
    public String toString() {
        return participant + "." + method;
    }
}
