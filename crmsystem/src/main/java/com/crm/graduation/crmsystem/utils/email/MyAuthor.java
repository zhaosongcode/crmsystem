package com.crm.graduation.crmsystem.utils.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.StringTokenizer;

public class MyAuthor extends Authenticator{

    public PasswordAuthentication getPasswordAuthentication(String param) {
        String username, password;

        StringTokenizer st = new StringTokenizer(param, ",");
        username = st.nextToken();
        password = st.nextToken();

        return new PasswordAuthentication(username, password);
    }

}
