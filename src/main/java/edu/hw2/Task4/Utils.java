package edu.hw2.Task4;

public final class Utils {
    private Utils() {}

    public static CallingInfo callingInfo() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String className = stackTrace[2].getClassName();
        String methodName = stackTrace[2].getMethodName();
        return new CallingInfo(className, methodName);
    }
}
