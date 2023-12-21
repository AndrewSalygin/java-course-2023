package edu.hw11;

import edu.hw11.Task2.ArithmeticUtils;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Task2Test {
    static {
        ByteBuddyAgent.install();
    }
    @Test
    public void task2Test() throws InstantiationException, IllegalAccessException {
        DynamicType.Loaded<ArithmeticUtils> dynamicClass = new ByteBuddy()
            .redefine(ArithmeticUtils.class)
            .method(ElementMatchers.named("sum"))
            .intercept(MethodDelegation.to(MyInterceptor.class))
            .make()
            .load(ArithmeticUtils.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());

        ArithmeticUtils arithmeticUtils = dynamicClass.getLoaded().newInstance();
        Assertions.assertEquals(arithmeticUtils.sum(10, 2), 20);
    }

    public static class MyInterceptor {
        public static int sum(int a, int b) {
            return a * b;
        }
    }
}
