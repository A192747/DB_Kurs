package com.example.Kurs.util;

import com.example.Kurs.models.Auto;
import com.example.Kurs.models.AutoPersonnel;
import com.example.Kurs.models.Journal;
import com.example.Kurs.models.Routes;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class UserAuthentication {

    private static String userLogin = null;
    private static String userPasswd = null;
    private static Map<String, String> users = new HashMap<>();

    private static Properties properties = null;

    public static boolean authenticateUser(String username, String password) {
        // Check if the user exists
        if (!users.containsKey(username)) {
            return false;
        }

        // Check if the password is correct
        String hashedPassword = users.get(username);
        return hashedPassword.equals(hashPassword(password));
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    public static void adduser(String login, String passwd) {
        users.put(login, passwd);
    }

    public static void setUserHibernateConfig(String login, String passwd) {
        properties.put(Environment.USER, login);
        properties.put(Environment.PASS, passwd);
    }
    public static Properties getHibernateConfig() {
        return properties;
    }

    public static boolean initHibernateProperties() {
        properties = new Properties();
        try (InputStream inputStream = new FileInputStream("src/main/resources/hibernate.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            return false;
        }
        return true;
    }


}
