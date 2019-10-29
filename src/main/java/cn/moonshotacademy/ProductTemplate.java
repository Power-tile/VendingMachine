package cn.moonshotacademy;

public class ProductTemplate {
    private String name;
    private Integer cost;

    public ProductTemplate(String _name, Integer _cost) {
        name = _name;
        cost = _cost;
    }

    public String getName() {
        return name;
    }
    public void setName(String _name) {
        name = _name;
    }

    public Integer getCost() {
        return cost;
    }
    public void setCost(Integer _cost) {
        cost = _cost;
    }

    public boolean equivalent(Object t) {
        if (t.getClass().equals(ProductTemplate.class)) {
            return ((ProductTemplate)t).getName().equals(this.name) && ((ProductTemplate)t).getCost().equals(this.cost);
        } else {
            return false;
        }
    }
}