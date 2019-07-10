package com.sensing.core.utils.Exception;

import com.sensing.core.utils.Exception.CapServerException;

public class ExpUtil {

    public static String getExceptionDetail(Exception e) {
        StringBuffer stringBuffer = new StringBuffer(e.toString() + "\n");
        StackTraceElement[] messages = e.getStackTrace();
        int length = messages.length;
        for (int i = 0; i < length; i++) {
            stringBuffer.append("\t" + messages[i].toString() + "\n");
        }
        return stringBuffer.toString();
    }

    public static Exception dealException(Exception e) {
        if (e instanceof CapServerException) {
            return new CapServerException();
        }
        return e;
    }
}
