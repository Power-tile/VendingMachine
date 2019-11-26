package cn.moonshotacademy.interfaces;

public interface UserTemplate {
    public Integer getBalance();
    public void addBalance(Integer value);
    public String getName();
    public void setName(String _name);
    public Integer getStorageIndex();
    public void setStorageIndex(Integer storageIndex);
    public boolean checkPassword(String password);
    public void setPassword(String password);
}