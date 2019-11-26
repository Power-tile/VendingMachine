package cn.moonshotacademy;

import cn.moonshotacademy.Product;
import cn.moonshotacademy.interfaces.StorageTemplate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Optional;

public class Storage implements StorageTemplate {
    private HashMap<String, LinkedList<Product>> productMenu;
    private HashMap<String, Product> productInfo;

    public Storage() {
        productMenu = new HashMap<String, LinkedList<Product>>();
        productInfo = new HashMap<String, Product>();
    }

    public Integer getTypeCount() {
        return Integer.valueOf(productMenu.size());
    }
    public HashMap<String, Integer> getMenuDetail() {
        HashMap<String, Integer> ret = new HashMap<String, Integer>();
        for (String i : productMenu.keySet()) {
            ret.put(i, Integer.valueOf(productMenu.get(i).size()));        
        }
        return ret;
    }

    public void addItem(Product p) {
        Optional<String> templateInMenu = productMenu.keySet().stream().filter(curr -> (p.getName().equals(curr))).findFirst();
        if (templateInMenu.isEmpty()) {
            LinkedList<Product> tmpList = new LinkedList<Product>();
            tmpList.add(p);
            productMenu.put(p.getName(), tmpList);
            productInfo.put(p.getName(), p.clone());
        } else {
            productMenu.get(templateInMenu.get()).add(p);
        }
    }

    public ArrayList<Product> removeItem(String template, Integer count) {
        if (productMenu.get(template).isEmpty()) {
            return null;
        } else {
            ArrayList<Product> ret = new ArrayList<Product>();
            for (int i = 1; i <= count.intValue(); i++) {
                ret.add(productMenu.get(template).removeFirst());
            }
            return ret;
        }
    }

    public boolean queryTemplate(String template) {
        return productMenu.containsKey(template);
    }
    public Integer querySize(String template) {
        return Integer.valueOf(productMenu.get(template).size());
    }
    public Integer queryCost(String template, Integer cnt) {
        return Integer.valueOf(productInfo.get(template).getCost(0));
    }
}
