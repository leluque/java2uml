package br.com.luque.java2uml.plantuml.writer.sequencediagram;

import br.com.luque.java2uml.core.sequencediagram.model.Message;

import java.util.Objects;

public class PlantUMLConstructionMessageWriter {
    public String getString(Message message) {
        Objects.requireNonNull(message);
        return PlantUMLHelper.getParticipantObjectName(message.getFrom()) + " -> " + PlantUMLHelper.getParticipantObjectName(message.getTo()) + " ** : <<create>>";
    }
}
