package cn.moonshotacademy;

import cn.moonshotacademy.interfaces.*;
import cn.moonshotacademy.products.*; // TODO: ADD BEAN SUPPORT

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Date;

public class Controller {
    private static Controller _instance;

    private static ArrayList<String> templateList = new ArrayList<String>();
    private static ArrayList<StorageTemplate> storageList = new ArrayList<StorageTemplate>();
    private static ArrayList<UserTemplate> userList = new ArrayList<UserTemplate>();
    private static ArrayList<UITemplate> uiList = new ArrayList<UITemplate>();

    public static Controller getInstance() {
        if (_instance == null) {
            _instance = new Controller();
        }
        return _instance;
    }

    private Controller() {
        // TODO: Delete this initialize after getting Spring
        Storage first = new Storage();
        storageList.add(first);
        for (int i = 0; i < 10000; i++) {
            storageList.get(0).addItem(new HOH(new Date()));
        }
        for (int i = 0; i < 100; i++) {
            storageList.get(0).addItem(new HClO(new Date()));
        }
        
        addUser("Power_tile", "Hello, World!", 1000000, 1);
        addUser("admin", "admin", 1, 1);

        addUI(Integer.valueOf(1));
    }

    public Integer getStorageTemplateListCount() {
        return Integer.valueOf(storageList.size());
    }
    public ArrayList<StorageTemplate> getStorageTemplateList() {
        return storageList;
    }
    public Integer getProductCost(Integer storageIndex, String t, Integer index) {
        return storageList.get(storageIndex.intValue() - 1).queryCost(t, index);
    }
    public ArrayList<String> getTemplateList() {
        return templateList;
    }
    public ArrayList<UserTemplate> getUserList() {
        return userList;
    }
    public ArrayList<UITemplate> getUIList() {
        return uiList;
    }
    public HashMap<String, Integer> getStorageMenu(Integer index) {
        return storageList.get(index.intValue() - 1).getMenuDetail();
    }
    public Integer getTemplateCount(Integer storageIndex, String template) {
        return storageList.get(storageIndex.intValue() - 1).querySize(template);
    }

    public void addUser(UserTemplate user) {
        userList.add(user);
    }
    public void addUser(String name, String password, Integer balance, Integer storageIndex) {
        // TODO: user init
        userList.add(new User(name, password, balance, storageIndex));
    }

    public UserTemplate checkUserName(String name) {
        Optional<UserTemplate> tmp = userList.stream().filter(curr -> curr.getName().equals(name)).findFirst();
        if (tmp.isEmpty()) {
            return null;
        } else {
            return tmp.get();
        }
    }
    public boolean checkUserPassword(UserTemplate user, String password) {
        return user.checkPassword(password);
    }
    public boolean checkTemplate(Integer storageIndex, String template) {
        return storageList.get(storageIndex - 1).queryTemplate(template);
    }
    public boolean checkSize(Integer storageIndex, String template, Integer size) {
        return storageList.get(storageIndex - 1).querySize(template).intValue() >= size.intValue();
    }

    public void addUI(Integer storageIndex) {
        // TODO: import bean for UI construction
        uiList.add(new UI(storageIndex));
    }
    public UITemplate getUI(Integer index) {
        return uiList.get(index.intValue() - 1);
    }

    public boolean addItemToStorageTemplate(Integer storageIndex, ProductTemplate p) {
        if (storageIndex.intValue() > storageList.size()) {
            return false; // TODO: throw an exception
        } else {
            if (!templateList.contains(p.getName())) {
                templateList.add(p.getName());
            }
            storageList.get(storageIndex.intValue() - 1).addItem(p);
            return true;
       }
    }
    public ArrayList<ProductTemplate> deleteItemFromStorageTemplate(Integer storageIndex, String template, Integer count) {
        return storageList.get(storageIndex - 1).removeItem(template, count);
    }

    public ArrayList<ProductTemplate> request(UserTemplate user, Integer storageIndex, String t, Integer count) {
        user.addBalance(-calculateCost(storageIndex, t, count));
        return deleteItemFromStorageTemplate(storageIndex, t, count);
    }

    public Integer calculateCost(Integer storageIndex, String t, Integer count) {
        int cost = 0;
        for (int i = 0; i < count; i++) {
            cost += getProductCost(storageIndex, t, count).intValue();
        }
        return Integer.valueOf(cost);
    }
}