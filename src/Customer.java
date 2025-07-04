import java.util.ArrayList;

public class Customer {
    private double balance;
    private ArrayList<CheckoutProduct> cart;

    public Customer(double balance) {
        this.balance = balance;
        this.cart = new ArrayList<>();
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return this.balance;
    }

    public void addToCart(CheckoutProduct product){
        this.cart.add(product);
    }
    public ArrayList<CheckoutProduct> getCart() {
        return new ArrayList<>(this.cart);
    }

    public void clearCart(){
        this.cart.clear();
    }
    
    
}
