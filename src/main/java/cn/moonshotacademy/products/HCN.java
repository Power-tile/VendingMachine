package cn.moonshotacademy.products;

import cn.moonshotacademy.Product;
import java.util.Date;

public class HCN extends Product {
    public HCN(Date creationTime) {
        super("HCN", 1000, creationTime);
    }

    @Override
    public Integer getCost(Integer cnt) {
        return this.cost;
    }
}