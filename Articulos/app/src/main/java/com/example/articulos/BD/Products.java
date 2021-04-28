package com.example.articulos.BD;

public class Products {

    private int id;
    private int stock;
    private String name;
    private String pic;
    private String DateDue;
    private String category;
    private boolean status;

    public Products(){

    }

    public Products(int id, int stock, String name, String pic, String DateDue, String category, boolean status){
        this.id = id;
        this.stock = stock;
        this.name = name;
        this.pic = pic;
        this.DateDue = DateDue;
        this.category = category;
        this.status = status;
    }

    public Products(int stock, String name, String pic, String DateDue, String category, boolean status){
        this.stock = stock;
        this.name = name;
        this.pic = pic;
        this.DateDue = DateDue;
        this.category = category;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDateDue() {
        return DateDue;
    }

    public void setDateDue(String dateDue) {
        DateDue = dateDue;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
