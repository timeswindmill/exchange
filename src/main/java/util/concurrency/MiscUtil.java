/*
 * Copyright 2012 Real Logic Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package util.concurrency;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;

/**
 * Miscellaneous useful functions.
 */
public class MiscUtil {
    /**
     * Size of a bytes in bytes
     */
    public static final int SIZE_OF_BYTE = 1;
    /**
     * Size of a boolean in bytes
     */
    public static final int SIZE_OF_BOOLEAN = 1;

    /**
     * Size of a char in bytes
     */
    public static final int SIZE_OF_CHAR = 2;
    /**
     * Size of a short in bytes
     */
    public static final int SIZE_OF_SHORT = 2;

    /**
     * Size of an int in bytes
     */
    public static final int SIZE_OF_INT = 4;
    /**
     * Size of a a float in bytes
     */
    public static final int SIZE_OF_FLOAT = 4;

    /**
     * Size of a long in bytes
     */
    public static final int SIZE_OF_LONG = 8;
    /**
     * Size of a double in bytes
     */
    public static final int SIZE_OF_DOUBLE = 8;

    private static final Unsafe unsafe;

    static {
        try {
            final PrivilegedExceptionAction<Unsafe> action = new PrivilegedExceptionAction<Unsafe>() {
                public Unsafe run() throws Exception {
                    final Field f = Unsafe.class.getDeclaredField("theUnsafe");
                    f.setAccessible(true);
                    return (Unsafe) f.get(null);
                }
            };

            unsafe = AccessController.doPrivileged(action);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the instance of {@link sun.misc.Unsafe}.
     *
     * @return the instance of Unsafe
     */
    public static Unsafe getUnsafe() {
        return unsafe;
    }

    /**
     * Fast method of finding the next power of 2 from a given value, including the value.
     * <p/>
     * If the value is <= 0 then the return will be 1.
     *
     * @param value from which to search for next power of 2
     * @return The next power of 2 or the value itself if it is a power of 2
     */
    public static int findNextPositivePowerOfTwo(final int value) {
        return 1 << (32 - Integer.numberOfLeadingZeros(value - 1));
    }

    /**
     * How many times should a value be shifted left for a given scale of pointer.
     *
     * @param scale of the pointer in bytes.
     * @return the number of positions the a pointer value should be left shifted
     */
    public static int calculateShiftForScale(final int scale) {
        if (4 == scale) {
            return 2;
        } else if (8 == scale) {
            return 3;
        } else {
            throw new IllegalStateException("Unknown pointer size");
        }
    }
}