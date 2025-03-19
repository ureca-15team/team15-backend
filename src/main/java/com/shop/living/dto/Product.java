package com.shop.living.dto;

public class Product {
    private int prodcode;
    private String prodname;
    private String company; 
    private String pimg;
    private String description;
    private int price;

    // 기본 생성자
    public Product() {}

    // 매개변수 생성자
    public Product(int prodcode, String prodname, String pimg, int price) {
        this.prodcode = prodcode;
        this.prodname = prodname;
        this.pimg = pimg;
        this.price = price;
    }

    // Getter & Setter
    public int getProdcode() { return prodcode; }
    public void setProdcode(int prodcode) { this.prodcode = prodcode; }

    public String getProdname() { return prodname; }
    public void setProdname(String prodname) { this.prodname = prodname; }
    
    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getPimg() { return pimg; }
    public void setPimg(String pimg) { this.pimg = pimg; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    @Override
    public String toString() {
        return "Product [prodcode=" + prodcode + ", prodname=" + prodname + ", company=" + company +
               ", pimg=" + pimg + ", description=" + description + ", price=" + price + "]";
    }
}