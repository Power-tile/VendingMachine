package cn.moonshotacademy;

public class User {
    private String name;
    private Integer balance;
    private String password;

    public User(String _name, Integer _balance, String _password) { // TODO: REPEATED USERNAME?
        name = _name;
        balance = _balance;
        password = _password;
    }
    public User(String _name, String _password, Integer _storageIndex) {
        name = _name;
        balance = Integer.valueOf(1000);
        password = _password;
    }
    
    public Integer getBalance() {
        return balance;
    }
    public void addBalance(Integer _value) {
        balance = Integer.valueOf(balance.intValue() + _value.intValue());
    }

    public String getName() {
        return name;
    }
    public void setName(String _name) {
        name = _name;
    }

    public boolean checkPassword(String _password) {
        return password.equals(_password);
    }
    public void changePassword(String _password) {
        password = _password;
    }
}