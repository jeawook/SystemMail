package com.systemmail.mail;

public class SMTPCommand {

    public static final String HELO = "HELO ";
    public static final String MAILFROM = "MAIL FROM";
    public static final String RCPTTO = "RCPT TO";
    public static final String DATA = "DATA";
    public static final String END = ".";
    public static final String QUIT = "QUIT";

    public static String create(String key, String value) {
        return String.format("%s: %s", key, value);
    }

    public static String createData(String data, String key) {
        return String.format("%s\n%s", data, key );
    }
}
