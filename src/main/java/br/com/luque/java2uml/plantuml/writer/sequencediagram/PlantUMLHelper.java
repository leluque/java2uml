package br.com.luque.java2uml.plantuml.writer.sequencediagram;

import br.com.luque.java2uml.core.sequencediagram.model.Message;
import br.com.luque.java2uml.core.sequencediagram.model.MethodExecution;
import br.com.luque.java2uml.core.sequencediagram.model.Participant;

public class PlantUMLHelper {
    public static final String ENTRY_POINT_CLASS_NAME = "Actor";
    public static final String ENTRY_POINT_OBJECT_NAME = "actor";

    public static String getParticipantClassName(MethodExecution methodExecution) {
        return null == methodExecution ? ENTRY_POINT_CLASS_NAME : getParticipantClassName(methodExecution.getParticipant());
    }

    public static String getParticipantObjectName(MethodExecution methodExecution) {
        return null == methodExecution ? ENTRY_POINT_OBJECT_NAME : getParticipantObjectName(methodExecution.getParticipant());
    }

    public static String getParticipantClassName(Participant participant) {
        return null == participant ? ENTRY_POINT_CLASS_NAME : participant.getClassName();
    }

    public static String getParticipantObjectName(Participant participant) {
        return null == participant ? ENTRY_POINT_OBJECT_NAME : (participant.getObjectId().isEmpty() ? participant.getClassName() : participant.getObjectId());
    }

    public static String getTextForMessageType(Message.Types type) {
        return switch (type) {
            case CREATION, SYNCHRONOUS -> " -> ";
            case ASYNCHRONOUS -> " ->> ";
        };
    }
}
