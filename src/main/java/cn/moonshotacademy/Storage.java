package cn.moonshotacademy;

import cn.moonshotacademy.Product;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Optional;

public class Storage {
    private HashMap<ProductTemplate, LinkedList<Product>> productMenu;

    public Storage() {
        productMenu = new HashMap<ProductTemplate, LinkedList<Product>>();
    }

    public void addItem(Product p) {
        Optional<ProductTemplate> templateInMenu = productMenu.keySet().stream().filter(curr -> curr.equals(p.getTemplate())).findFirst();
        if (templateInMenu.isEmpty()) {
            LinkedList<Product> tmpList = new LinkedList<Product>();
            tmpList.add(p);
            productMenu.put(p.getTemplate(), tmpList);
        } else {
            productMenu.get(templateInMenu.get()).add(p);
        }
    }

    public int getTypeCount() {
        return productMenu.size();
    }
    public HashMap<ProductTemplate, Integer> getMenuDetail() {
        HashMap<ProductTemplate, Integer> ret = new HashMap<ProductTemplate, Integer>();
        for (ProductTemplate i : productMenu.keySet()) {
            ret.put(i, Integer.valueOf(productMenu.get(i).size()));        
        }
        return ret;
    }

    public boolean queryTemplate(ProductTemplate template) {
        return productMenu.containsKey(template);
    }
    public Integer querySize(ProductTemplate template) {
        return Integer.valueOf(productMenu.get(template).size());
    }

    public ArrayList<Product> removeItem(ProductTemplate template, Integer count) {
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
}
