package pers.etherealss.demo.infrastructure.utils;

import java.io.Closeable;

/**
 * @author wtk
 * @description
 * @date 2021-09-16
 */
public class IOUtil {

    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
