package cn.moonshotacademy.interfaces;

import cn.moonshotacademy.interfaces.StorageTemplate;
import cn.moonshotacademy.interfaces.UserTemplate;

import java.util.ArrayList;

public interface ControllerTemplate {
    public Integer getStorageListCount();
    public ArrayList<StorageTemplate> getStorageList();
    public ArrayList<String> getTemplateList();
    public ArrayList<UserTemplate> getUserList();
}