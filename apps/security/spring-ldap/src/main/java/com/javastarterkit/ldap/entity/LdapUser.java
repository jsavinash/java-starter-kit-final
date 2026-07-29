// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.ldap.entity;

public class LdapUser {

    private String uid;
    private String cn;
    private String sn;
    private String email;
    private String department;

    public LdapUser() {}

    public LdapUser(String uid, String cn, String sn, String email, String department) {
        this.uid = uid;
        this.cn = cn;
        this.sn = sn;
        this.email = email;
        this.department = department;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
