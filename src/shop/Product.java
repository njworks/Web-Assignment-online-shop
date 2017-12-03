package shop;

public class Product {
    public String PID;
    public String artist;
    public String title;
    public String description;
    public int price;
    public String thumbnail;
    public String fullimage;

    public Product(
            String PID, String artist, String title,
            String description, int price, String thumbnail, String fullimage) {
        this.PID = PID;
        this.artist = artist;
        this.title = title;
        this.description = description;
        this.price = price;
        this.thumbnail = thumbnail;
        this.fullimage = fullimage;
    }

    public String toString() {
        int pound = price / 100;
        int pence = price % 100;
        return title + "\t " + pound+"."+pence;
    }

    public String convertprice(Product p){
        int cPrice = p.price;
        int pound = cPrice/100;
        int pence = cPrice%100;
        String out ="£ "+ pound+"."+pence;
        return out;
    }


}
