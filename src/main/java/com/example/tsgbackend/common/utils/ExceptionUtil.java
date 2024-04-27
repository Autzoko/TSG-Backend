package com.example.tsgbackend.common.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {

    public static String getStackTraceInfo(Exception e) {
        StringWriter stringWriter = null;
        PrintWriter printWriter = null;

        try {
            stringWriter = new StringWriter();
            printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            printWriter.flush();
            stringWriter.flush();

            return stringWriter.toString();
        } catch (Exception exception) {
            return "error happened";
        } finally {
            if(stringWriter != null) {
                try {
                    stringWriter.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
            if(printWriter != null) {
                printWriter.close();
            }
        }
    }
}
