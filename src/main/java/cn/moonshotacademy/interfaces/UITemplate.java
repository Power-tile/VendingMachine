package cn.moonshotacademy.interfaces;

import cn.moonshotacademy.interfaces.UserTemplate;

public interface UITemplate {
    public Integer getStatus();
    public UserTemplate getCurrentUser();
    
    public void interact();    
}