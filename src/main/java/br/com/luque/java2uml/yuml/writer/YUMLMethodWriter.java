package br.com.luque.java2uml.yuml.writer;

import br.com.luque.java2uml.reflection.model.Method;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class YUMLMethodWriter implements br.com.luque.java2uml.writer.MethodWriter {
	public String getString(Method method) {
		Objects.requireNonNull(method);

		String result = YUMLHelper
				.getVisibilityText(method.getVisibility());
		result += method.getName();
		result += "(";
		result += Stream.of(method.getParameters()).map(p -> String.format("%s:%s", p.getName(), p.getType().getName())).collect(Collectors.joining(","));
		result += "):";
		result += method.getReturnType().getName();
		return result;
	}
}
