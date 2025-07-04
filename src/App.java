import java.time.LocalDate;

public class App {
    public static void main(String[] args) throws Exception {
        ECommerceSystem system = new ECommerceSystem(100);
        Product cheese = new ShippableExpirableProduct("Cheese", 10, 100, LocalDate.of(2025,7,10), 125);
        Product milk = new ShippableExpirableProduct("Milk", 20, 100, LocalDate.of(2025, 8, 2), 500);
        Product tissues = new NormalProduct("Tissues", 2, 100);
        Product tv = new ShippableProduct("TV", 1000, 100, 2500);

        system.addProduct(tv);
        system.addProduct(cheese);
        system.addProduct(milk);
        system.addProduct(tissues);

        Customer customer = new Customer(10000);
        customer.addToCart(new CheckoutProduct(1, tv));
        customer.addToCart(new CheckoutProduct(1, cheese));
        customer.addToCart(new CheckoutProduct(3, tissues));

        System.out.println(system.checkout(customer));


        System.out.println("-------Testing--------");

        // Testing
        try{
            cheese.setQuantity(-1);
        }
        catch (Exception e){
            System.out.println("Exception " + e);
        }

        try{
            customer.addToCart(new CheckoutProduct(1000, cheese));
            System.out.println(system.checkout(customer));
        }
        catch (Exception e){
            System.out.println("Exception " + e);
        }
        
        customer.clearCart();
        try{
            customer.addToCart(new CheckoutProduct(100, tv));
            System.out.println(system.checkout(customer));
        }
        catch (Exception e){
            System.out.println("Exception " + e);
        }

        customer.clearCart();
        try{
            Product yoghurt = new ExpirableProduct("Yoghurt", 20, 100, LocalDate.of(2025, 7, 1));
            customer.addToCart(new CheckoutProduct(1, yoghurt));
            System.out.println(system.checkout(customer));
        }
        catch (Exception e){
            System.out.println("Exception " + e);
        }

        customer.clearCart();
        try{
            Product yoghurt = new ExpirableProduct("Yoghurt", 20, 100, LocalDate.of(2025, 7, 1));
            system.addProduct(yoghurt);
            customer.addToCart(new CheckoutProduct(1, yoghurt));
            System.out.println(system.checkout(customer));
        }
        catch (Exception e){
            System.out.println("Exception " + e);
        }

    }
}
