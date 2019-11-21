package cn.moonshotacademy.products;

import cn.moonshotacademy.Product;
import java.util.Date;

public class HClO extends Product {
    public HClO(Date creationTime) {
        super("HClO", 30, creationTime);
    }

    @Override
    public Integer getCost(Integer cnt) {
        return this.cost;
    }
}