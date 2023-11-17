package br.com.luque.java2uml.core.sequencediagram.instrumentation;

import br.com.luque.java2uml.plantuml.writer.sequencediagram.PlantUMLWriter;
import javassist.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MethodTransformerAgent {
    private static final Logger logger = Logger.getLogger(MethodTransformerAgent.class.getName());
    public static boolean shutdownAdded = false;

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer) {
                if (null == loader) {
                    return classfileBuffer;
                }

                String[] targetPackages = agentArgs.split(",");
                if (targetPackages.length == 0) {
                    return classfileBuffer;
                }

                if (className == null
                    || Stream.of(targetPackages).noneMatch(className::startsWith)
                    || className.contains("$$Lambda$")
                    || className.contains("$$")) {
                    return classfileBuffer;
                }

                String fullyQualifiedName = className.replace('/', '.');
                ClassPool classPool = ClassPool.getDefault();
                try {
                    CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
                    if (Stream.of(targetPackages).anyMatch(fullyQualifiedName::startsWith)) {
                        for (CtConstructor method : ctClass.getDeclaredConstructors()) {
                            MethodCall methodCall = getMethodCall(method);
                            method.insertBefore("{br.com.luque.java2uml.core.sequencediagram.instrumentation.CallStack.INSTANCE.registerStart(%s);}".formatted(methodCall.arguments));
                            method.insertAfter("{br.com.luque.java2uml.core.sequencediagram.instrumentation.CallStack.INSTANCE.registerEnd(\"%s\",String.valueOf(System.identityHashCode(this)));}".formatted(methodCall.executionId));
                        }

                        for (CtMethod method : ctClass.getDeclaredMethods()) {
                            MethodCall methodCall = getMethodCall(method);
                            method.insertBefore("{br.com.luque.java2uml.core.sequencediagram.instrumentation.CallStack.INSTANCE.registerStart(%s);}".formatted(methodCall.arguments));
                            method.insertAfter("{br.com.luque.java2uml.core.sequencediagram.instrumentation.CallStack.INSTANCE.registerEnd(\"%s\",\"%s\");}".formatted(methodCall.executionId, Modifier.isStatic(method.getModifiers()) ? "" : "String.valueOf(System.identityHashCode(this))"));
                        }
                    }

                    byte[] byteCode = ctClass.toBytecode();
                    ctClass.detach();
                    return byteCode;
                } catch (CannotCompileException | NotFoundException | IOException e) {
                    logger.warning(String.format("The class %s could not be transformed due to: %s", className, e.getMessage()));
                }
                return classfileBuffer;
            }
        });
        if (!shutdownAdded) {
            // Register a shutdown hook to run your method
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                runCleanupMethod();
            }));
            shutdownAdded = true;
        }
    }

    private static void runCleanupMethod() {
        System.out.println("##########");
        System.out.println("PlantUML sequence diagram:");
        System.out.println(PlantUMLWriter.generateSequenceDiagram());
    }

    private static class MethodCall {
        String arguments;
        UUID executionId;

        public MethodCall(UUID executionId, String arguments) {
            this.executionId = executionId;
            this.arguments = arguments;
        }
    }

    private static MethodCall getMethodCall(CtBehavior method) throws NotFoundException {
        UUID executionId = UUID.randomUUID();
        String arguments = "Thread.currentThread().getId(),"; // Thread ID.
        arguments += "\"%s\",".formatted(executionId.toString()); // Execution ID.
        arguments += "\"%s\",".formatted(method.getDeclaringClass().getName()); // Class name.
        if (method instanceof CtConstructor) {
            arguments += "\"\","; // Object id. Impossible to obtain before the constructor is called.
            arguments += "true,"; // Is constructor.
        } else {
            if (Modifier.isStatic(method.getModifiers())) {
                arguments += "\"\","; // No object.
            } else {
                arguments += "String.valueOf(System.identityHashCode(this)),"; // Object id.
            }
            arguments += "false,"; // Not is constructor.
        }
        arguments += "\"%s\",".formatted(method.getName()); // Method name.
        if (method instanceof CtConstructor) {
            arguments += "\"\","; // Return type.
        } else {
            CtClass returnType = ((CtMethod) method).getReturnType();
            arguments += returnType == null ? "null," : "\"%s\",".formatted(returnType.getName()); // Return type.
        }
        arguments += "new String[] {";
        arguments += Stream.of(method.getParameterTypes()).map(c -> "\"%s\"".formatted(c.getName())).collect(Collectors.joining(","));
        arguments += "}";
        return new MethodCall(executionId, arguments);
    }
}
