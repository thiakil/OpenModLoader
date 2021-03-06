/*
 * Copyright 2016 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.openmodloader.loader.transformer;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

public class AccessTransformer implements IClassTransformer {

    //TODO: Replace this with *actual* access transformers
    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (!name.startsWith("net.minecraft") && name.contains(".")) { //Only transform minecraft and non mapped things
            return bytes;
        }
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        boolean isClassPublic = (classNode.access & Opcodes.ACC_PUBLIC) != 0;
        if (!isClassPublic) {
            classNode.access &= ~Opcodes.ACC_PRIVATE;
            classNode.access &= ~Opcodes.ACC_PROTECTED;
            classNode.access |= Opcodes.ACC_PUBLIC;
        }

        for (MethodNode method : classNode.methods) {
            boolean isPublic = (method.access & Opcodes.ACC_PUBLIC) != 0;
            if (!isPublic) {
                method.access &= ~Opcodes.ACC_PRIVATE;
                method.access &= ~Opcodes.ACC_PROTECTED;
                method.access |= Opcodes.ACC_PUBLIC;
            }
        }
        for (FieldNode field : classNode.fields) {
            boolean isPublic = (field.access & Opcodes.ACC_PUBLIC) != 0;
            if (!isPublic) {
                field.access &= ~Opcodes.ACC_PRIVATE;
                field.access &= ~Opcodes.ACC_PROTECTED;
                field.access |= Opcodes.ACC_PUBLIC;
            }
        }

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(writer);
        return writer.toByteArray();
    }
}