package cn.moonshotacademy;

import cn.moonshotacademy.interfaces.UserTemplate;

public class User implements UserTemplate {
    private String name;
    private Integer balance;
    private String password;
    private Integer storageIndex;

    public User() {}
    public User(String name, String password, Integer balance, Integer storageIndex) {
        this.name = name;
        this.password = password;
        this.balance = Integer.valueOf(balance);
        this.storageIndex = storageIndex;
    }
    
    public Integer getBalance() {
        return this.balance;
    }
    public void addBalance(Integer value) {
        this.balance = Integer.valueOf(this.balance.intValue() + value.intValue());
    }
    public void setBalance(Integer value) {
        this.balance = value;
    }

    public String getName() {
        return name;
    }
    public void setName(String _name) {
        name = _name;
    }

    public Integer getStorageIndex() {
        return this.storageIndex;
    }
    public void setStorageIndex(Integer storageIndex) {
        this.storageIndex = storageIndex;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
    public void setPassword(String password) {
        this.password = password;
    }
}