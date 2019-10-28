package cn.moonshotacademy;

import cn.moonshotacademy.Storage;
import cn.moonshotacademy.UI;
import cn.moonshotacademy.User;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.Optional;

public class Controller {
    private static Controller _instance;

    private static ArrayList<ProductTemplate> templateList = new ArrayList<ProductTemplate>();
    private static ArrayList<Storage> storageList = new ArrayList<Storage>();
    private static ArrayList<User> userList = new ArrayList<User>();
    private static ArrayList<UI> uiList = new ArrayList<UI>();

    public static Controller getInstance() {
        if (_instance == null) {
            _instance = new Controller();
        }
        return _instance;
    }

    private Controller() {
        Storage first = new Storage();
        storageList.add(first);
        addItemToStorage(1, new Product(new ProductTemplate("H2SO4", 10), Date.valueOf("2016-3-5")));
        addItemToStorage(1, new Product(new ProductTemplate("H2SO4", 10), Date.valueOf("2018-11-30")));
        addItemToStorage(1, new Product(new ProductTemplate("HClO", 30), Date.valueOf("2019-8-5")));
        addItemToStorage(1, new Product(new ProductTemplate("HClO", 30), Date.valueOf("2019-8-5")));
        addItemToStorage(1, new Product(new ProductTemplate("HClO", 30), Date.valueOf("2019-8-5")));
        addItemToStorage(1, new Product(new ProductTemplate("HClO", 30), Date.valueOf("2019-8-5")));
        addItemToStorage(1, new Product(new ProductTemplate("HClO", 30), Date.valueOf("2019-8-5")));
        addItemToStorage(1, new Product(new ProductTemplate("HClO", 30), Date.valueOf("2019-8-5")));
        addItemToStorage(1, new Product(new ProductTemplate("HClO", 30), Date.valueOf("2019-8-5")));
        addItemToStorage(1, new Product(new ProductTemplate("HCN", 1000), Date.valueOf("2019-8-5")));
        addItemToStorage(1, new Product(new ProductTemplate("HCN", 1000), Date.valueOf("2019-8-5")));
        addItemToStorage(1, new Product(new ProductTemplate("HCN", 1000), Date.valueOf("2019-8-5")));
        addItemToStorage(1, new Product(new ProductTemplate("HCN", 1000), Date.valueOf("2019-8-5")));
        addItemToStorage(1, new Product(new ProductTemplate("HCN", 1000), Date.valueOf("2019-8-5")));
        addItemToStorage(1, new Product(new ProductTemplate("HCN", 1000), Date.valueOf("2019-8-5")));
        addItemToStorage(1, new Product(new ProductTemplate("HCN", 1000), Date.valueOf("2019-8-5")));
        addItemToStorage(1, new Product(new ProductTemplate("HCN", 1000), Date.valueOf("2019-8-5")));
        addItemToStorage(1, new Product(new ProductTemplate("HCN", 1000), Date.valueOf("2019-8-5")));
        addItemToStorage(1, new Product(new ProductTemplate("HCN", 1000), Date.valueOf("2019-8-5")));
        for (int i = 1; i <= 100000; i++) {
            addItemToStorage(1, new Product(new ProductTemplate("HOH", 1), Date.valueOf("2019-8-5")));
        }
        
        addUser("Power_tile", 10000000, "Hello, World!");
        addUser("admin", 1, "admin");

        addUI(Integer.valueOf(1));
    }

    public Integer getStorageListCount() {
        return Integer.valueOf(storageList.size());
    }
    public ArrayList<Storage> getStorageList() {
        return storageList;
    }
    public ArrayList<ProductTemplate> getTemplateList() {
        return templateList;
    }
    public ArrayList<User> getUserList() {
        return userList;
    }
    public ArrayList<UI> getUIList() {
        return uiList;
    }

    public HashMap<ProductTemplate, Integer> getStorageMenu(Integer index) {
        return storageList.get(index.intValue() - 1).getMenuDetail();
    }
    public Integer getTemplateCount(Integer storageIndex, ProductTemplate template) {
        return storageList.get(storageIndex.intValue() - 1).querySize(template);
    }

    public void addUser(User user) {
        userList.add(user);
    }
    public void addUser(String name, Integer balance, String password) {
        userList.add(new User(name, balance, password));
    }

    public User checkUserName(String name) {
        Optional<User> tmp = userList.stream().filter(curr -> curr.getName().equals(name)).findFirst();
        if (tmp.isEmpty()) {
            return null;
        } else {
            return tmp.get();
        }
    }
    public boolean checkUserPassword(User user, String password) {
        return user.checkPassword(password);
    }
    public boolean checkTemplate(Integer storageIndex, ProductTemplate template) {
        return storageList.get(storageIndex - 1).queryTemplate(template);
    }
    public boolean checkSize(Integer storageIndex, ProductTemplate template, Integer size) {
        return storageList.get(storageIndex - 1).querySize(template).intValue() >= size.intValue();
    }

    public void addUI(Integer storageIndex) {
        uiList.add(new UI(storageIndex));
    }
    public UI getUI(Integer index) {
        return uiList.get(index.intValue() - 1);
    }

    public boolean addItemToStorage(Integer storageIndex, Product p) {
        if (storageIndex.intValue() > storageList.size()) {
            return false;
        } else {
            if (!templateList.contains(p.getTemplate())) {
                templateList.add(p.getTemplate());
            }
            storageList.get(storageIndex.intValue() - 1).addItem(p);
            return true;
       }
    }
    public ArrayList<Product> deleteItemFromStorage(Integer storageIndex, ProductTemplate t, Integer count) {
        return storageList.get(storageIndex - 1).removeItem(t, count);
    }

    public ArrayList<Product> request(User user, Integer storageIndex, ProductTemplate t, Integer count) {
        user.addBalance(-t.getCost() * count.intValue());
        return deleteItemFromStorage(storageIndex, t, count);
    }
}