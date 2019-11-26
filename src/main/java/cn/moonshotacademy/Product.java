package cn.moonshotacademy;

import java.util.Date;

public class Product {
    protected String name;
    protected Integer cost;
    protected Date creationTime;

    public Product(String name, Integer cost, Date creationTime) {
        this.name = name;
        this.cost = cost;
        this.creationTime = creationTime;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getCost(Integer cnt) {
        return this.cost;
    }
    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Date getCreationTime() {
        return this.creationTime;
    }

    public Product clone() {
        return new Product(this.name, this.cost, this.creationTime);
    }
}