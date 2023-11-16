package br.com.luque.java2uml.core.sequencediagram.instrumentation;

import br.com.luque.java2uml.plantuml.writer.sequencediagram.PlantUMLWriter;
import javassist.*;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MethodLoggerAgent {

    public static boolean shutdownAdded = false;

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer)
                throws IllegalClassFormatException {
                if (loader == null) {
                    return classfileBuffer;
                }

                String[] packages = agentArgs.split(",");

                if (className == null || Stream.of(packages).noneMatch(className::startsWith)) {
                    return classfileBuffer;
                }

                // Check for common lambda or synthetic class patterns
//                if (className.contains("$")) {
//                    System.out.println("ATTENTION 1: " + className);
//                    return classfileBuffer; // Skip transformation for synthetic classes
//                }
                if (className.contains("$$Lambda$") || className.contains("$$")) {
                    System.out.println("ATTENTION 2: " + className);
                    return classfileBuffer; // Skip transformation for synthetic classes
                }

                String dotClassName = className.replace('/', '.');
                try {

                    ClassPool classPool = ClassPool.getDefault();
                    CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));

                    if (Stream.of(packages).anyMatch(dotClassName::startsWith)) {
                        for (CtConstructor method : ctClass.getDeclaredConstructors()) {
                            String uuid = String.valueOf(Math.random()).replace(".", "");
                            String methodCall = "\"" + ctClass.getName() + "\",";
                            methodCall += "\"" + uuid + "\",";
                            methodCall += "true,";
                            methodCall += "\"" + method.getName() + "\",";
                            methodCall += "\"\",";
                            methodCall += "new String[] {";
                            methodCall += Stream.of(method.getParameterTypes()).map(c -> "\"" + c.getName() + "\"").collect(Collectors.joining(","));
                            if (methodCall.endsWith(",")) {
                                methodCall = methodCall.substring(0, methodCall.length() - 1);
                            }
                            methodCall += "}";
                            method.insertBefore("{br.com.luque.java2uml.core.sequencediagram.instrumentation.CallStack.INSTANCE.registerStart(" + methodCall + ");}");
                            method.insertAfter("{br.com.luque.java2uml.core.sequencediagram.instrumentation.CallStack.INSTANCE.registerEnd(" + methodCall + ");}");
                        }

                        for (CtMethod method : ctClass.getDeclaredMethods()) {
                            String uuid = String.valueOf(Math.random()).replace(".", "");
//                            if (method.getName().equals("main") && method.getReturnType().getName().equals("void") && method.getParameterTypes().length == 1 && method.getParameterTypes()[0].getName().equals("[Ljava.lang.String;") && Modifier.isStatic(method.getModifiers())) {
//                                continue;
//                            }
                            String methodCall = "\"" + ctClass.getName() + "\",";
                            if (Modifier.isStatic(method.getModifiers())) {
                                methodCall += "\"" + uuid + "\",";
                            } else {
                                methodCall += "this.toString(),";
                            }
                            methodCall += "false,";
                            methodCall += "\"" + method.getName() + "\",";
                            methodCall += method.getReturnType() == null ? "null," : "\"" + method.getReturnType().getName() + "\",";
                            methodCall += "new String[] {";
                            methodCall += Stream.of(method.getParameterTypes()).map(c -> "\"" + c.getName() + "\"").collect(Collectors.joining(","));
                            if (methodCall.endsWith(",")) {
                                methodCall = methodCall.substring(0, methodCall.length() - 1);
                            }
                            methodCall += "}";
                            method.insertBefore("{br.com.luque.java2uml.core.sequencediagram.instrumentation.CallStack.INSTANCE.registerStart(" + methodCall + ");}");
                            method.insertAfter("{br.com.luque.java2uml.core.sequencediagram.instrumentation.CallStack.INSTANCE.registerEnd(" + methodCall + ");}");
                        }
                    }

                    byte[] byteCode = ctClass.toBytecode();
                    ctClass.detach();
                    return byteCode;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        if (!shutdownAdded) {
            // Register a shutdown hook to run your method
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Running cleanup code after the main program");
                runCleanupMethod();
            }));
            shutdownAdded = true;
        }
    }

    private static void runCleanupMethod() {
        System.out.println(PlantUMLWriter.generateSequenceDiagram());
    }
}
