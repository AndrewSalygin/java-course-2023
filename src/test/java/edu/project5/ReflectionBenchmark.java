package edu.project5;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@State(Scope.Thread)
public class ReflectionBenchmark {
    private static final String METHOD_NAME = "name";
    private static final int WARMUP_TIME = 5;
    private static final int MEASUREMENT_TIME = 5;

    private Student student;
    private Method method;
    private MethodHandle methodHandle;
    private Function<Student, String> lambdaMetaFactory;

    record Student(String name, String surname) {
    }

    @Setup
    public void setup() {
        initializeStudent();
        initializeMethod();
        initializeMethodHandle();
        initializeLambdaMetaFactory();
    }

    private void initializeStudent() {
        student = new Student("Andrew", "Salygin");
    }

    private void initializeMethod() {
        try {
            method = Student.class.getMethod(METHOD_NAME);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Error initializing method", e);
        }
    }

    private void initializeMethodHandle() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(String.class);
        try {
            methodHandle = lookup.findVirtual(Student.class, METHOD_NAME, methodType);
        } catch (IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException("Error initializing method handle", e);
        }
    }

    private void initializeLambdaMetaFactory() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType functionType = MethodType.methodType(Function.class);
        MethodType methodType = MethodType.methodType(Object.class, Object.class);

        try {
            lambdaMetaFactory = (Function<Student, String>) LambdaMetafactory.metafactory(
                lookup,
                "apply",
                functionType,
                methodType,
                methodHandle,
                MethodType.methodType(String.class, Student.class)
            ).getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException("Error initializing lambda meta factory", e);
        }
    }

    @Benchmark
    public void directAccess(Blackhole blackhole) {
        String name = student.name();
        blackhole.consume(name);
    }

    @Benchmark
    public void reflection(Blackhole blackhole) throws InvocationTargetException, IllegalAccessException {
        String name = (String) method.invoke(student);
        blackhole.consume(name);
    }

    @Benchmark
    public void methodHandle(Blackhole blackhole) throws Throwable {
        String name = (String) methodHandle.invoke(student);
        blackhole.consume(name);
    }

    @Benchmark
    public void lambdaMetaFactory(Blackhole blackhole) {
        String name = lambdaMetaFactory.apply(student);
        blackhole.consume(name);
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
            .include(ReflectionBenchmark.class.getSimpleName())
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.NANOSECONDS)
            .forks(1)
            .warmupForks(1)
            .warmupIterations(1)
            .warmupTime(TimeValue.seconds(WARMUP_TIME))
            .measurementIterations(1)
            .measurementTime(TimeValue.seconds(MEASUREMENT_TIME))
            .build();

        new Runner(options).run();
    }
}
