package br.com.luque.java2uml.core.sequencediagram.instrumentation;

import br.com.luque.java2uml.core.sequencediagram.model.Message;
import br.com.luque.java2uml.core.sequencediagram.model.Method;
import br.com.luque.java2uml.core.sequencediagram.model.MethodExecution;
import br.com.luque.java2uml.core.sequencediagram.model.Participant;

import java.util.*;

@SuppressWarnings("unused")
public enum CallStack {
    INSTANCE;

    private final List<Participant> participants = new ArrayList<>();
    private final Set<Method> methods = new HashSet<>();
    // Not using stack to handle async calls
    private final List<MethodExecution> stack = new LinkedList<>();
    private final List<Message> messages = new LinkedList<>();

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

    public void registerStart(long threadId,
                              String executionId,
                              String className,
                              String objectId,
                              boolean constructor,
                              String methodName,
                              String methodReturnType,
                              String[] methodParameterTypes) {
        MethodExecution to = new MethodExecution(threadId, executionId, getParticipant(className, objectId), getMethod(className, constructor, methodName, methodReturnType, methodParameterTypes));

        if (stack.isEmpty()) {
            messages.add(new Message(to));
        } else {
            MethodExecution from = stack.get(stack.size() - 1);
            Message.Types type = Message.getDefaultTypeFor(to);
            if (threadId != from.getThreadId()) {
                type = Message.Types.ASYNCHRONOUS;
            }
            messages.add(new Message(from, to, type));
        }
        stack.add(to);
    }

    public void registerEnd(String executionId, String objectId) {
        stack.stream().filter(m -> m.getId().equals(executionId)).findFirst().ifPresent(m -> {
            if (m.getParticipant().getObjectId().isEmpty() && !objectId.isEmpty()) {
                m.getParticipant().setObjectId(objectId);
            }
            stack.remove(m);
        });
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
