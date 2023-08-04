package com.kravchenko.timekeeping23.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class IdHelper {

    public static int createId(HttpServletRequest req){
        int id;
        var attributeId = (String) req.getAttribute("id");
        if (attributeId != null) {
            id = Integer.parseInt(attributeId);
        } else {
            id = Integer.parseInt(req.getParameter("id"));
        }
        return id;
    }
}
