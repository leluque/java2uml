package br.com.luque.java2uml.plantuml.writer.sequencediagram;

import br.com.luque.java2uml.core.sequencediagram.instrumentation.CallStack;
import br.com.luque.java2uml.core.sequencediagram.model.Message;
import br.com.luque.java2uml.core.sequencediagram.model.Participant;

public class PlantUMLWriter {
    public static String generateSequenceDiagram() {
        PlantUMLParticipantWriter participantWriter = new PlantUMLParticipantWriter();
        PlantUMLConstructionMessageWriter constructionMessageWriter = new PlantUMLConstructionMessageWriter();
        PlantUMLMessageWriter messageWriter = new PlantUMLMessageWriter();
        StringBuilder result = new StringBuilder();
        result.append("@startuml\n");
        result.append(" actor Actor as %s%n".formatted(PlantUMLHelper.ENTRY_POINT_OBJECT_NAME));
        for (Participant participant : CallStack.INSTANCE.getParticipants()) {
            result.append(participantWriter.getString(participant)).append("\n");
        }
        result.append("\n");
        for (Message message : CallStack.INSTANCE.getMessages()) {
            if (message.getTo().getMethod().isConstructor()) {
                result.append(constructionMessageWriter.getString(message)).append("\n");
            } else {
                result.append(messageWriter.getString(message)).append("\n");
            }
        }

        result.append("@enduml");
        return result.toString().trim();
    }
}
