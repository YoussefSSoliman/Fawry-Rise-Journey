import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class ECommerceSystem {
    private ArrayList<Product> inventory;
    private ShippingService shippingService;
    private double egpPerKg;

    public ECommerceSystem(double egpPerKg) {
        this.inventory = new ArrayList<>();
        this.egpPerKg = egpPerKg;
        this.shippingService = new ShippingService(egpPerKg);
    }

    public void addProduct(Product product){
        inventory.add(product);
    }

    public String checkout(Customer customer) throws Exception{

        if (customer.getCart().isEmpty()){
            throw new Exception("Can't checkout an empty cart.");
        }

        StringBuilder invoice = new StringBuilder("*** PURCHASE NOTICE ***\n");

        ArrayList<CheckoutProduct> currentCustomerCart = customer.getCart();

        Map<String,Object> shipping_notice = shippingService.getShipment(currentCustomerCart);

        String mini_invoice = (String) shipping_notice.get("invoice");
        double weight_fees = (double) shipping_notice.get("weight_fees");
        double total_price = 0;

        invoice.append(mini_invoice);
        invoice.append("** Checkout Receipt **\n");

        for (CheckoutProduct product : currentCustomerCart) {
            if (!this.inventory.contains(product.getParent())){
                throw new Exception("Can't checkout a product that isn't in the inventory");
            }

            if (product.getParent().getQuantity() < product.getQuantity()){
                throw new Exception("Not enough of product:" + product.getParent().getName() + 
                " in the inventory, requested: "+product.getQuantity() +", available: " 
                + product.getParent().getQuantity());
            }

            if (product.getParent() instanceof ExpirableProduct){
                 ExpirableProduct parent = (ExpirableProduct) product.getParent();
                 if (parent.getExpiryDate().isBefore(LocalDate.now())){
                    throw new Exception("Product " + parent.getName() + " is expired");
                 }
            }

            invoice.append(product.getQuantity() + "x " + product.getParent().getName() + "   " + (product.getParent().getPrice()*product.getQuantity()) + "EGP\n");
            total_price += product.getParent().getPrice()*product.getQuantity();
        }

        if (total_price + weight_fees > customer.getBalance()){
            throw new Exception("Insuffiecient customer balance");
        }

        invoice.append("-----------------\n");
        invoice.append("Subtotal: "+ total_price+"\n");
        invoice.append("Shipping: "+ weight_fees+"\n");
        invoice.append("Total: "+ (total_price+weight_fees)+"\n");
        invoice.append("-----------------\n");
        invoice.append("Balance before: " +customer.getBalance() + "EGP\n");
        
        customer.setBalance(customer.getBalance() - (total_price+weight_fees));

        invoice.append("Balance after: " +customer.getBalance() + "EGP\n");

        for (CheckoutProduct product : currentCustomerCart) {
            product.getParent().decrementQuantity(product.getQuantity());
        }
        return invoice.toString();
    }

    public ArrayList<Product> getInventory() {
        return inventory;
    }

    public double getEgpPerKg() {
        return egpPerKg;
    }

    public void setInventory(ArrayList<Product> inventory) {
        this.inventory = inventory;
    }

    public void setEgpPerKg(double egpPerKg) {
        this.egpPerKg = egpPerKg;
        this.shippingService.setEgpPerKg(egpPerKg);
    }

}
