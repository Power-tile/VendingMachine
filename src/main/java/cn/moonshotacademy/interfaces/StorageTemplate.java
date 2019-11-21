package cn.moonshotacademy.interfaces;

import cn.moonshotacademy.interfaces.ProductTemplate;

import java.util.HashMap;
import java.util.ArrayList;

public interface StorageTemplate {
    public Integer getTypeCount();
    public HashMap<String, Integer> getMenuDetail();
    
    public void addItem(ProductTemplate p);
    public ArrayList<ProductTemplate> removeItem(String template, Integer count);

    public boolean queryTemplate(String template);
    public Integer querySize(String template);
    public Integer queryCost(String template, Integer cnt);
}