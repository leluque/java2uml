package br.com.luque.java2uml.yuml.writer;

import br.com.luque.java2uml.reflection.model.Clazz;
import br.com.luque.java2uml.reflection.model.RelationshipField;
import br.com.luque.java2uml.reflection.model.ScopedClazz;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class YUMLRelationshipWriter implements br.com.luque.java2uml.writer.RelationshipWriter {

	private final Set<RelationshipField> alreadyProcessedRelationships = new HashSet<>();

	public String getString(RelationshipField field) {
		return this.getString(field, null);
	}

	public String getString(RelationshipField field, RelationshipField otherSide) {
		Objects.requireNonNull(field);
		if(alreadyProcessedRelationships.contains(field)) {
			return "";
		}

		var result = new StringBuilder("[");
		result.append(YUMLClassWriter.getFormattedClassName(field.getClazz()));
		result.append("]");
		if(null != otherSide) {
			alreadyProcessedRelationships.add(otherSide);

			result.append(switch (otherSide.getCardinality()) {
				case ONE -> "1";
				case N -> "*";
			});
		}
		if(field.isAggregation()) {
			result.append("<>");
		} else if(field.isComposition()) {
			result.append("++");
		}
		result.append(switch(field.getCardinality()) {
			case ONE -> "->1";
			case N -> "->*";
		});

		result.append("[");
		result.append(YUMLClassWriter.getFormattedClassName(field.getOtherSide()));
		result.append("]");
		return result.toString();
	}

	public String getRealizationString(ScopedClazz scopedClazz) {
		Objects.requireNonNull(scopedClazz);
		if(!scopedClazz.hasInterfaces()) {
			return "";
		}
		var result = new StringBuilder();
		for(Clazz interface_ : scopedClazz.getInterfaces()) {
			result.append("[");
			result.append(YUMLClassWriter.getFormattedClassName(interface_));
			result.append("]^-.-[");
			result.append(YUMLClassWriter.getFormattedClassName(scopedClazz));
			result.append("]\n");
		}
		return result.toString();
	}

	public String getInheritanceString(ScopedClazz scopedClazz) {
		Objects.requireNonNull(scopedClazz);
		if(!scopedClazz.hasSuperclass()) {
			return "";
		}
		return "[" +
				YUMLClassWriter.getFormattedClassName(scopedClazz.getSuperclass()) +
				"]^[" +
				YUMLClassWriter.getFormattedClassName(scopedClazz) +
				"]\n";
	}
}
