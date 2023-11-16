package br.com.luque.java2uml.plantuml.writer.sequencediagram;

import br.com.luque.java2uml.core.sequencediagram.model.MethodExecution;
import br.com.luque.java2uml.core.sequencediagram.model.Participant;

public class PlantUMLHelper {

    public static String getParticipantClassName(MethodExecution methodExecution) {
        return null == methodExecution ? "EntryPoint" : getParticipantClassName(methodExecution.getParticipant());
    }

    public static String getParticipantObjectName(MethodExecution methodExecution) {
        return null == methodExecution ? "entryPoint" : getParticipantObjectName(methodExecution.getParticipant());
    }

    public static String getParticipantClassName(Participant participant) {
        return null == participant ? "EntryPoint" : participant.getClassName();
    }

    public static String getParticipantObjectName(Participant participant) {
        return null == participant ? "entryPoint" : participant.getObjectId();
    }
}
