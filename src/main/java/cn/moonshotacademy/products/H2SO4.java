package cn.moonshotacademy.products;

import cn.moonshotacademy.Product;
import java.util.Date;

public class H2SO4 extends Product {
    public H2SO4(Date creationTime) {
        super("H2S04", 10, creationTime);
    }

    @Override
    public Integer getCost(Integer cnt) {
        return this.cost;
    }
}