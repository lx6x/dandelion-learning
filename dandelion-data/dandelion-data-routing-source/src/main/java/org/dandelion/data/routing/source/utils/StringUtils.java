package org.dandelion.data.routing.source.utils;

/**
 * TODO String utils
 *
 * @author L
 * @version 1.0
 * @date 2022/06/08 21:22
 */
public class StringUtils {


    /**
     * Check if an object is empty
     *
     * @param object Object
     * @return true：null / false：not null
     * @author L
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * Check if an object is not null
     *
     * @param object Object
     * @return true：not null / false：null
     * @author L
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }
}
