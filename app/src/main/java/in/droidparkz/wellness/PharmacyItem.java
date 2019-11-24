package in.droidparkz.wellness;

public class PharmacyItem {

    private String name;
    private String quantity;
    private String price;

    public PharmacyItem(String name, String quantity, String price) {

        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

}