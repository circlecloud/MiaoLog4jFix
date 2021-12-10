package pw.yumc.MiaoLog4j2Fix.reflect;

import java.lang.reflect.InvocationTargetException;

/**
 * A unchecked wrapper for any of Java's checked reflection exceptions:
 * <p>
 * These exceptions are
 * <ul>
 * <li>{@link ClassNotFoundException}</li>
 * <li>{@link IllegalAccessException}</li>
 * <li>{@link IllegalArgumentException}</li>
 * <li>{@link InstantiationException}</li>
 * <li>{@link InvocationTargetException}</li>
 * <li>{@link NoSuchMethodException}</li>
 * <li>{@link NoSuchFieldException}</li>
 * <li>{@link SecurityException}</li>
 * </ul>
 *
 * @author Lukas Eder
 */
public class ReflectException extends RuntimeException {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -6213149635297151442L;

    public ReflectException() {
        super();
    }

    public ReflectException(final String message) {
        super(message);
    }

    public ReflectException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ReflectException(final Throwable cause) {
        super(cause);
    }
}
