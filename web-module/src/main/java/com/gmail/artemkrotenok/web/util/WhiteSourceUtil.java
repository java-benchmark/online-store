package com.gmail.artemkrotenok.web.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

public final class WhiteSourceUtil {

    private WhiteSourceUtil() { }

    public static boolean isSubDirectory(File base, File child) {
        try {
            base = base.getCanonicalFile();
            child = child.getCanonicalFile();
        } catch (IOException e) {
            return false;
        }

        File parentFile = child;
        while (parentFile != null) {
            if (base.equals(parentFile)) {
                return true;
            }
            parentFile = parentFile.getParentFile();
        }
        return false;
    }

    public static boolean isSubDirectory(File child) {
        File base = getBaseDirectory();

        return isSubDirectory(base, child);
    }

    public static boolean isSubDirectory(String basePath, String childPath) {
        File base = new File(basePath);
        File child = new File(childPath);

        return isSubDirectory(base, child);
    }

    public static boolean isSubDirectory(String childPath) {
        File base = getBaseDirectory();
        File child = new File(childPath);

        return isSubDirectory(base, child);
    }

    private static File getBaseDirectory(){
        //String base = System.getProperty("user.dir");
        String base = FileSystems.getDefault().getPath("").toAbsolutePath().toString();

        return new File(base);
    }
}
