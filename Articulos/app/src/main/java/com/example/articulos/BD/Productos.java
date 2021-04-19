package com.example.articulos.BD;

public class Productos {

    private int id;
    private int stock;
    private String name;
    private String pic;
    private String DataDue;
    private String category;
    private boolean status;

    public Productos(){

    }

    public Productos(int id, int stock, String name, String pic, String DataDue, String category, boolean status){
        this.id = id;
        this.stock = stock;
        this.name = name;
        this.pic = pic;
        this.DataDue = DataDue;
        this.category = category;
        this.status = status;
    }

    public Productos(int stock, String name, String pic, String DataDue, String category, boolean status){
        this.stock = stock;
        this.name = name;
        this.pic = pic;
        this.DataDue = DataDue;
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

    public String getDataDue() {
        return DataDue;
    }

    public void setDataDue(String dataDue) {
        DataDue = dataDue;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
