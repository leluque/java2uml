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
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer)
                throws IllegalClassFormatException {

                String[] packages = agentArgs.split(",");

                if (className == null || Stream.of(packages).noneMatch(className::startsWith)) {
                    return null;
                }

                String dotClassName = className.replace('/', '.');
                try {

                    ClassPool classPool = ClassPool.getDefault();
                    CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));

                    if (dotClassName.startsWith("pooaula1")) {
                        for (CtConstructor method : ctClass.getDeclaredConstructors()) {
                            method.insertBefore("{ System.out.println(\"Executed constructor: " + ctClass.getName() + "." + method.getName() + "\"); }");
                        }

                        for (CtMethod method : ctClass.getDeclaredMethods()) {
                            method.insertBefore("{ System.out.println(\"Executed method: " + ctClass.getName() + "." + method.getName() + "\"); }");
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
    }
}
