package com.example.buttonmenu;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String name;
    private String Uname;
    private String phone;
    private String email;

    public User(String name, String Uname, String phone, String email) {
        this.name = name;
        this.Uname = Uname;
        this.phone = phone;
        this.email = email;
    }

    public User() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("Uname", Uname);
        result.put("phone", phone);
        result.put("email", email);
        return result;
    }

}

