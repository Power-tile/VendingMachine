package cn.moonshotacademy.products;

import cn.moonshotacademy.Product;
import java.util.Date;

public class HOH extends Product {
    public HOH(Date creationTime) {
        super("HOH", 1, creationTime);
    }

    @Override
    public Integer getCost(Integer cnt) {
        return this.cost;
    }
}