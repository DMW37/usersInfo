package com.dmw.entity;

public class UserLogin {
    private Integer id;
    private String account;
    private String password;

    public UserLogin(Integer id, String account, String password) {
        this.id = id;
        this.account = account;
        this.password = password;
    }

    public UserLogin() {
    }

    @Override
    public String toString() {
        return "UserLogin{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
