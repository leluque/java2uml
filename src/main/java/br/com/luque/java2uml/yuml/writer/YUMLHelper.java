package br.com.luque.java2uml.yuml.writer;

import br.com.luque.java2uml.reflection.Visibilities;

public class YUMLHelper {
	public static String getVisibilityText(Visibilities visibility) {
		return switch(visibility) {
			case PUBLIC -> "+";
			case PROTECTED -> "#";
			case PRIVATE -> "-";
			default -> "~";
		};
	}
}
