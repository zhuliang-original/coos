package top.coos;

import top.coos.exception.CoreRuntimeException;

public abstract class Coos {

    static public StackTraceElement getStackTrace() {
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        return stacks[1];
    }

    static public StackTraceElement getParentStackTrace() {
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        if (stacks.length < 3) {
            throw new CoreRuntimeException("not found parent stack trace");
        }
        return stacks[2];
    }

    static public StackTraceElement getFristStackTrace() {
        StackTraceElement[] elements = new Throwable().getStackTrace();
        return elements[elements.length - 1];
    }

}
