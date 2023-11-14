package br.com.luque.java2uml.core.instrumentation;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.stream.Stream;

public class MethodLoggerAgent {

    public static boolean shutdownAdded = false;

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer)
                throws IllegalClassFormatException {

                String[] packages = agentArgs.split(",");

                if (className == null || Stream.of(packages).noneMatch(className::startsWith)) {
                    return classfileBuffer;
                }

                // Check for common lambda or synthetic class patterns
                if (className.contains("$$Lambda$") || className.contains("$$")) {
                    return classfileBuffer; // Skip transformation for synthetic classes
                }

                String dotClassName = className.replace('/', '.');
                try {

                    ClassPool classPool = ClassPool.getDefault();
                    CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));

                    if (dotClassName.startsWith("pooaula1")) {
                        for (CtConstructor method : ctClass.getDeclaredConstructors()) {
                            method.insertBefore("{ br.com.luque.java2uml.core.instrumentation.CallPool.INSTANCE.addCall(\"" + ctClass.getName() + "." + method.getName() + "\"); }");
                        }

                        for (CtMethod method : ctClass.getDeclaredMethods()) {
                            method.insertBefore("{ br.com.luque.java2uml.core.instrumentation.CallPool.INSTANCE.addCall(\"" + ctClass.getName() + "." + method.getName() + "\"); }");
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
        Stream.of(CallPool.INSTANCE.getCalls()).forEach(System.out::println);
    }
}
