package com.kravchenko.timekeeping23.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringHelper {

    public static String removeBreak(String str){
        return str.replaceAll("\n", "");
    }
}
