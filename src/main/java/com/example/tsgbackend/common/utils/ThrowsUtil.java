package com.example.tsgbackend.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ThrowsUtil {

    /**
     * @description get stack info of exception
     * @param throwable throwable
     * @return java.lang.String
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        try (PrintWriter printWriter = new PrintWriter(stringWriter)) {
            throwable.printStackTrace(printWriter);
            return stringWriter.toString();
        }
    }
}
