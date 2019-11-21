package cn.moonshotacademy.interfaces;

import java.util.Date;

public interface ProductTemplate {
    public String getName();
    public void setName(String name);
    public Integer getCost(Integer cnt);
    public void setCost(Integer cost);
    public Date getCreationTime();
}