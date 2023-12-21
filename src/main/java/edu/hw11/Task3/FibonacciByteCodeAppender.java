package edu.hw11.Task3;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import org.jetbrains.annotations.NotNull;

public class FibonacciByteCodeAppender implements ByteCodeAppender {
    private static final String CLASS_NAME = "Fibonacci";
    private static final String METHOD_NAME = "fib";
    private static final String DESCRIPTOR = "(I)J";

    @SuppressWarnings("MagicNumber")
    @Override
    public @NotNull Size apply(
        MethodVisitor methodVisitor,
        Implementation.@NotNull Context context,
        @NotNull MethodDescription methodDescription
    ) {
        Label moreThanTwoLabel = new Label();

        methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);

        methodVisitor.visitInsn(Opcodes.ICONST_2);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPGE, moreThanTwoLabel);

        methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
        methodVisitor.visitInsn(Opcodes.I2L);
        methodVisitor.visitInsn(Opcodes.LRETURN);

        methodVisitor.visitLabel(moreThanTwoLabel);
        methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);

        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);

        methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
        methodVisitor.visitInsn(Opcodes.ICONST_1);
        methodVisitor.visitInsn(Opcodes.ISUB);

        // Вызов fib(n - 1)
        methodVisitor.visitMethodInsn(
            Opcodes.INVOKEVIRTUAL,
            CLASS_NAME,
            METHOD_NAME,
            DESCRIPTOR,
            false
        );

        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);

        methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
        methodVisitor.visitInsn(Opcodes.ICONST_2);
        methodVisitor.visitInsn(Opcodes.ISUB);

        // Вызов fib(n - 2)
        methodVisitor.visitMethodInsn(
            Opcodes.INVOKEVIRTUAL,
            CLASS_NAME,
            METHOD_NAME,
            DESCRIPTOR,
            false
        );

        // Сложение результатов fib(n - 1) и fib(n - 2)
        methodVisitor.visitInsn(Opcodes.LADD);

        methodVisitor.visitInsn(Opcodes.LRETURN);

        return new ByteCodeAppender.Size(20, 0);
    }
}
