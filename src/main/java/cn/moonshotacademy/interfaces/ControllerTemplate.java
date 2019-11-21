package cn.moonshotacademy.interfaces;

import cn.moonshotacademy.interfaces.StorageTemplate;
import cn.moonshotacademy.interfaces.ProductTemplate;
import cn.moonshotacademy.interfaces.UserTemplate;
import cn.moonshotacademy.interfaces.UITemplate;

import java.util.ArrayList;

public interface ControllerTemplate {
    public Integer getStorageListCount();
    public ArrayList<StorageTemplate> getStorageList();
    public ArrayList<String> getTemplateList();
    public ArrayList<UserTemplate> getUserList();

}