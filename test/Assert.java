/**
 * Minimal assertion helper for the test classes. Each check throws
 * AssertionError with a readable message on failure; the test runner
 * catches it and reports the test as failed.
 */
public final class Assert {

    private Assert() {
        // Utility class: do not instantiate.
    }

    public static void equals(Object expected, Object actual, String what) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError(what + ": expected <" + expected + "> but was <" + actual + ">");
        }
    }

    public static void isTrue(boolean condition, String what) {
        if (!condition) {
            throw new AssertionError(what + ": expected true");
        }
    }

    public static void isFalse(boolean condition, String what) {
        if (condition) {
            throw new AssertionError(what + ": expected false");
        }
    }

    public static void isNull(Object value, String what) {
        if (value != null) {
            throw new AssertionError(what + ": expected null but was <" + value + ">");
        }
    }

    public static void notNull(Object value, String what) {
        if (value == null) {
            throw new AssertionError(what + ": expected a non-null value");
        }
    }

    /** Runs the code and requires it to throw the given exception type. */
    public static void expectThrows(Class<? extends Throwable> type, Runnable code, String what) {
        try {
            code.run();
        } catch (Throwable thrown) {
            if (type.isInstance(thrown)) {
                return;
            }
            throw new AssertionError(what + ": expected " + type.getSimpleName()
                    + " but got " + thrown.getClass().getSimpleName());
        }
        throw new AssertionError(what + ": expected " + type.getSimpleName()
                + " but nothing was thrown");
    }
}
