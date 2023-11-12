package br.com.luque.java2uml.yuml.writer;

import br.com.luque.java2uml.reflection.model.Clazz;
import br.com.luque.java2uml.reflection.model.RelationshipField;
import br.com.luque.java2uml.reflection.model.ScopedClazz;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class YUMLRelationshipWriter implements br.com.luque.java2uml.writer.RelationshipWriter {

	private Set<RelationshipField> alreadyProcessedRelationships = new HashSet<>();

	public String getString(RelationshipField field) {
		return this.getString(field, null);
	}

	public String getString(RelationshipField field, RelationshipField otherSide) {
		Objects.requireNonNull(field);
		if(alreadyProcessedRelationships.contains(field)) {
			return "";
		}

		String result = "[";
		result += YUMLClassWriter.getFormattedClassName(field.getClazz());
		result += "]";
		if(null != otherSide) {
			alreadyProcessedRelationships.add(otherSide);

			result += switch (otherSide.getCardinality()) {
				case ONE -> "1";
				case N -> "*";
			};
		}
		if(field.isAggregation()) {
			result += "<>";
		} else if(field.isComposition()) {
			result += "++";
		}
		result += switch(field.getCardinality()) {
			case ONE -> "->1";
			case N -> "->*";
		};

		result += "[";
		result += YUMLClassWriter.getFormattedClassName(field.getOtherSide());
		result += "]";
		return result;
	}

	public String getRealizationString(ScopedClazz scopedClazz) {
		Objects.requireNonNull(scopedClazz);
		if(!scopedClazz.hasInterfaces()) {
			return "";
		}
		String result = "";
		for(Clazz interface_ : scopedClazz.getInterfaces()) {
			result += "[" + YUMLClassWriter.getFormattedClassName(interface_) + "]^-.-[" + YUMLClassWriter.getFormattedClassName(scopedClazz) + "]";
			result += "\n";
		}
		return result;
	}

	public String getInheritanceString(ScopedClazz scopedClazz) {
		Objects.requireNonNull(scopedClazz);
		if(!scopedClazz.hasSuperclass()) {
			return "";
		}
		String result = "";
		result += "[" + YUMLClassWriter.getFormattedClassName(scopedClazz.getSuperclass()) + "]^[" + YUMLClassWriter.getFormattedClassName(scopedClazz) + "]";
		result += "\n";
		return result;
	}
}
