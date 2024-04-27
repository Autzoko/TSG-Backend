package com.example.tsgbackend.common.constant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaptchaConstants {

    public static String codeType = "arithmetic";

    public static Long expiration = 2L;

    public static int length = 2;

    public static int width = 111;

    public static int height = 28;

    public static String fontName;

    public static int fontSize = 25;

}
