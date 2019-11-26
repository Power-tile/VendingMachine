package cn.moonshotacademy.interfaces;

import cn.moonshotacademy.Product;

import java.util.HashMap;
import java.util.ArrayList;

public interface StorageTemplate {
    public Integer getTypeCount();
    public HashMap<String, Integer> getMenuDetail();
    
    public void addItem(Product p);
    public ArrayList<Product> removeItem(String template, Integer count);

    public boolean queryTemplate(String template);
    public Integer querySize(String template);
    public Integer queryCost(String template, Integer cnt);
}