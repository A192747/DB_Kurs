package com.example.Kurs.util;

import java.util.HashMap;
import java.util.Map;

public class HQLCreator {
    private static String getHQLrequest(StringBuilder str, HashMap<String, String> map) throws Exception {
        int counter = 0;
        StringBuilder hql = new StringBuilder();
        for(Map.Entry<String, String> pair : map.entrySet()) {
            if(!pair.getValue().isEmpty()
                    && !(pair.getValue().lastIndexOf('\'') == pair.getValue().length()-1
                    && (pair.getValue().substring(1, pair.getValue().length() - 1).isEmpty()))) {
                if(counter > 0)
                    hql.append(" AND ");
                hql.append(pair.getKey()).append(pair.getValue());
                counter++;
            }
        }
        int hqlSize = str.length();
        str.append(hql);
        if(hqlSize == str.length())
            throw new Exception("Заполните поля для изменения!");
        return String.valueOf(str);
    }
    public static String getHQLJournal(String str0, String str1, String str2, String str3) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("time_out = ", "'" + str0 + "'");
        map.put("time_in = ", "'" + str1 + "'");
        map.put("j.auto.id = ", str2);
        map.put("j.route.id = ", str3);

        StringBuilder hql = new StringBuilder("select j FROM Journal j WHERE ");
        return getHQLrequest(hql, map);
    }

    public static String getHQLAuto(String str0, String str1, String str2, String str3) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("num = ", "'" + str0 + "'");
        map.put("color = ", "'" + str1 + "'");
        map.put("mark = ", "'" + str2 + "'");
        map.put("a.person.id = ", str3);

        StringBuilder hql = new StringBuilder("select a FROM Auto a WHERE ");
        return getHQLrequest(hql, map);
    }

    public static String getHQLAutoPersonnel(String str0, String str1, String str2) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("last_name = ",  "'" + str0 + "'");
        map.put("first_name = ", "'" + str1 + "'");
        map.put("parther_name = ", "'" + str2 + "'");

        StringBuilder hql = new StringBuilder("select p FROM AutoPersonnel p WHERE ");
        return getHQLrequest(hql, map);
    }

    public static String getHQLRoutes(String str0) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("name = ",  "'" + str0 + "'");

        StringBuilder hql = new StringBuilder("select p FROM Routes p WHERE ");
        return getHQLrequest(hql, map);
    }
}
