import java.time.LocalDate;

public abstract class Product {

    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName(){
        return this.name;
    }
    
    public double getPrice(){
        return this.price;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public void decrementQuantity(int decrementValue){
        this.quantity -= decrementValue;
    }

    public void setQuantity(int quantity) throws Exception {
        if (quantity < 0) throw new Exception("Quantity cannot be negative");

        this.quantity = quantity;
    }
}

interface ShippableProductInterface {
    public abstract double getWeight();
}


class ExpirableProduct extends Product {

    private LocalDate expiryDate;

    public ExpirableProduct(String name, double price, int quantity, LocalDate expiryDate) {
        super(name, price, quantity);
        this.expiryDate = expiryDate;
    }

    public LocalDate getExpiryDate(){
        return this.expiryDate;
    }

}

class ShippableProduct extends Product implements ShippableProductInterface {

    private double weight;

    public ShippableProduct(String name, double price, int quantity, double weight) {
        super(name, price, quantity);
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

} 

class ShippableExpirableProduct extends ExpirableProduct implements ShippableProductInterface {

    private double weight;
    

    public ShippableExpirableProduct(String name, double price, int quantity, LocalDate expiryDate, double weight) {
        super(name, price, quantity, expiryDate);
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }
    
}

class NormalProduct extends Product {

    public NormalProduct(String name, double price, int quantity) {
        super(name, price, quantity);
    }

    
}

class CheckoutProduct{
    private Product parent;
    private int quantity;

    public CheckoutProduct(int quantity, Product parent) {
        this.parent = parent;
        this.quantity = quantity;
    }

    public Product getParent() {
        return this.parent;
    }

    public int getQuantity() {
        return this.quantity;
    }



}