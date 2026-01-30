package mods.betterfoliage.loader;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class BetterFoliageTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if(!transformedName.equals("net.minecraft.client.renderer.BlockModelRenderer$AmbientOcclusionFace")) return basicClass;
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(basicClass);
        classReader.accept(classNode, 0);

        // where: net.minecraft.client.renderer.BlockModelRenderer$AmbientOcclusionFace
        // what: make constructor public
        // why: use vanilla AO calculation at will without duplicating code
        for (MethodNode methodNode : classNode.methods)
            if(methodNode.name.equals("<init>"))
                methodNode.access = Opcodes.ACC_PUBLIC;

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(writer);
        return writer.toByteArray();
    }
}
