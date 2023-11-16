package br.com.luque.java2uml.core.sequencediagram.instrumentation;

import br.com.luque.java2uml.core.sequencediagram.model.Message;
import br.com.luque.java2uml.core.sequencediagram.model.Method;
import br.com.luque.java2uml.core.sequencediagram.model.MethodExecution;
import br.com.luque.java2uml.core.sequencediagram.model.Participant;

import java.util.*;

public enum CallStack {
    INSTANCE;

    private Set<Participant> participants = new HashSet<>();
    private Set<Method> methods = new HashSet<>();
    // Not using stack to handle async calls
    private List<MethodExecution> stack = new LinkedList<>();
    private List<Message> messages = new LinkedList<>();

    public Participant getParticipant(String className, String objectId) {
        Participant existing = participants.stream()
            .filter(p -> p.getClassName().equals(className) && Objects.equals(p.getObjectId(), objectId))
            .findFirst()
            .orElse(null);
        if (existing == null) {
            existing = new Participant(className, objectId);
            participants.add(existing);
        }
        return existing;
    }

    public Method getMethod(String className,
                            boolean constructor,
                            String methodName,
                            String methodReturnType,
                            String... methodParameterTypes) {
        Method existing = methods.stream()
            .filter(m -> m.getClassName().equals(className)
                && m.isConstructor() == constructor
                && m.getName().equals(methodName)
                && m.getReturnType().equals(methodReturnType)
                && Arrays.equals(m.getParameterTypes(), methodParameterTypes))
            .findFirst()
            .orElse(null);
        if (existing == null) {
            existing = new Method(className,
                constructor,
                methodName,
                methodReturnType,
                methodParameterTypes);
            methods.add(existing);
        }
        return existing;
    }

    public void registerStart(String className,
                              String objectId,
                              boolean constructor,
                              String methodName,
                              String methodReturnType,
                              String[] methodParameterTypes) {
        MethodExecution methodExecution = new MethodExecution(getParticipant(className, objectId), getMethod(className, constructor, methodName, methodReturnType, methodParameterTypes));

        if (stack.isEmpty()) {
            messages.add(new Message(methodExecution));
        } else {
            messages.add(new Message(stack.get(stack.size() - 1), methodExecution));
        }
        stack.add(methodExecution);
    }

    public void registerEnd(String className,
                            String objectId,
                            boolean constructor,
                            String methodName,
                            String methodReturnType,
                            String[] methodParameterTypes) {
        ListIterator<MethodExecution> iterator = stack.listIterator(stack.size());

        // Iterate from last to first
        while (iterator.hasPrevious()) {
            MethodExecution methodExecution = iterator.previous();
            if (methodExecution.correspondsTo(className, objectId, constructor, methodName, methodReturnType, methodParameterTypes)) {
                iterator.remove();
                break;
            }
        }
    }

    public Set<Participant> getParticipants() {
        return participants;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
