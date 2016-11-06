package edu.galileo.android.androidchat.entities;

import java.util.Map;

/**
 * Created by ludwin on 5/11/16.
 */
public class User {

    public final static boolean ONLINE = true;
    public final static boolean OFFLINE = false;

    private String email;
    private boolean online;
    Map<String, Boolean> contacts;

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public Map<String, Boolean> getContacts() {
        return contacts;
    }

    public void setContacts(Map<String, Boolean> contacts) {
        this.contacts = contacts;
    }
}
