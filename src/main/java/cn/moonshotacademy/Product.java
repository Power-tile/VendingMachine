package cn.moonshotacademy;

import cn.moonshotacademy.ProductTemplate;
import java.sql.Date;

public class Product {
    private ProductTemplate template;
    private Date creationTime; 

    public Product(ProductTemplate _template, Date _creationTime) {
        template = _template;
        creationTime = _creationTime;
    }

    public ProductTemplate getTemplate() {
        return template;
    }
    public Date getCreationTime() {
        return creationTime;
    }
}