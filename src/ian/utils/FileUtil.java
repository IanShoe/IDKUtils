/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ian.utils;

import java.io.File;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author shoemaki
 */
public class FileUtil {

    /**
     * Hash method to encode file state into a unique code.
     *
     * @param file file to encode.
     * @return encoded string
     */
    public static String sha1(File file) {
        try {
            return new String(DigestUtils.sha(FileUtils.readFileToByteArray(file)));
        } catch (Throwable t) {
            throw new IllegalArgumentException("File given could not be processed.", t);
        }
    }

    public static String sha1(String value) {
        try {
            return new String(DigestUtils.sha(value));
        } catch (Throwable t) {
            throw new IllegalArgumentException("File given could not be processed.", t);
        }
    }
}
