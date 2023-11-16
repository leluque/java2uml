package br.com.luque.java2uml.core.sequencediagram.model;

import java.util.Arrays;
import java.util.Objects;

public class MethodExecution {
    private Participant participant;
    private Method method;

    public MethodExecution(Participant participant, Method method) {
        setParticipant(participant);
        setMethod(method);
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

    public boolean correspondsTo(String className, String objectId, boolean constructor, String methodName, String methodReturnType, String... methodParameterTypes) {
        return participant.getClassName().equals(className)
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
