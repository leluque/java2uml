package br.com.luque.java2uml.yuml.writer;

import br.com.luque.java2uml.reflection.model.Method;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class YUMLMethodWriter implements br.com.luque.java2uml.writer.MethodWriter {
	public String getString(Method method) {
		Objects.requireNonNull(method);

		return YUMLHelper
				.getVisibilityText(method.getVisibility()) + method.getName() + "(" + Stream.of(method.getParameters()).map(p -> String.format("%s:%s", p.getName(), p.getType().getName())).collect(Collectors.joining(",")) + "):" + method.getReturnType().getName();
	}
}
