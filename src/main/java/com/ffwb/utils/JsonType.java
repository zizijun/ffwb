package com.ffwb.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by jinchuyang on 2017/6/23.
 */
public class JsonType {

    public static String simpleMapToJsonStr(Map map) {

        if (map == null || map.isEmpty()) {

            return "null";

        }

        String jsonStr = "{";

        Set keySet = map.keySet();

        for (Object key : keySet) {

            jsonStr += "\"" + key + "\":\"" + map.get(key) + "\",";

        }

        jsonStr = jsonStr.substring(1, jsonStr.length() - 2);

        jsonStr += "}";

        return jsonStr;

    }

    //{"pass":"4355","name":"12342","wang":"fsf"}

    public static Map getData(String str) {

        String sb = str.substring(1, str.length() - 1);

        String[] name = sb.split("\\\",\\\"");

        String[] nn = null;

        Map<String,String> map = new HashMap<>();

        for (String aName : name) {

            nn = aName.split("\\\":\\\"");

            map.put(nn[0], nn[1]);

        }

        return map;

    }


}