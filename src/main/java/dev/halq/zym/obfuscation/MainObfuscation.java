package dev.halq.zym.obfuscation;

import dev.halq.zym.Ui.components.MainComponent;
import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

/**
 * pasted from https://github.com/CorruptedBytes/ClassRemapper/blob/a3b6ee3a2d0ecd59df0a9da2beaf62e2b54177b3/src/me/alex/remapper/CustomRemapper.java#L161
 */

public class MainObfuscation {

    public static final List<ClassNode> classes = new ArrayList<>();
    public static final Map <String, byte[]> files = new HashMap<>();

    protected static void saveJar(File out) {
        try (JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(out))) {
            for (ClassNode classNode: classes) {
                jarOutputStream.putNextEntry(new JarEntry(classNode.name + ".class"));
                jarOutputStream.write(toByteArray(classNode));
                jarOutputStream.closeEntry();
            }

            for (Map.Entry <String, byte[]> entry: files.entrySet()) {
                jarOutputStream.putNextEntry(new JarEntry(entry.getKey()));
                jarOutputStream.write(entry.getValue());
                jarOutputStream.closeEntry();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void loadJar(File file) {
        try (JarFile jarFile = new JarFile(file)) {
            final Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                final JarEntry jarEntry = entries.nextElement();
                try (InputStream inputStream = jarFile.getInputStream(jarEntry)) {
                    final byte[] bytes = IOUtils.toByteArray(inputStream);
                    if (!jarEntry.getName().endsWith(".class")) {
                        files.put(jarEntry.getName(), bytes);
                        continue;
                    }

                    try {
                        if (checkClassVerify(bytes)) {
                            final ClassNode classNode = new ClassNode();
                            new ClassReader(bytes).accept(classNode, ClassReader.EXPAND_FRAMES);
                            classes.add(classNode);
                        }
                    } catch (Exception e) {
                        System.err.println("There was an error loading " + jarEntry.getName());
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    protected static boolean checkClassVerify(byte[] bytes) {
        return String.format("%X%X%X%X", bytes[0], bytes[1], bytes[2], bytes[3]).equals("CAFEBABE");
    }

    protected static byte[] toByteArray(ClassNode classNode) {
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        try {
            classNode.accept(writer);
            return writer.toByteArray();
        } catch (Throwable t) {
            writer = new ClassWriter(0);
            classNode.accept(writer);
            return writer.toByteArray();
        }
    }
}
