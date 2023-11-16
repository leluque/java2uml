package br.com.luque.java2uml.plantuml.writer.sequencediagram;

import br.com.luque.java2uml.core.sequencediagram.model.Participant;

import java.util.Objects;

public class PlantUMLParticipantWriter {
    public String getString(Participant participant) {
        Objects.requireNonNull(participant);
        return " participant " + participant.getClassName() + " as " + participant.getObjectId();
    }
}
