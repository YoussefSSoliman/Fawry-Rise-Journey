import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShippingService {
    
    private double egpPerKg;

    public ShippingService(double egpPerKg){
        this.egpPerKg = egpPerKg;
    }

    public Map<String,Object> getShipment(ArrayList<CheckoutProduct> cart){

        Map<String, Object> miniInvoice = new HashMap<>();

        double totalWeight = 0.0;
        StringBuffer invoice = new StringBuffer("** SHIPMENT NOTICE **\n");

        
        for (CheckoutProduct product : cart) {
            if (product.getParent() instanceof ShippableProductInterface){
                double weight = ((ShippableProductInterface) product.getParent()).getWeight();
                int quantity = product.getQuantity();
                String name = product.getParent().getName();
                totalWeight += weight*quantity;
                invoice.append(quantity + "x " + name + "   " + (weight*quantity) + "g\n");
            }
        }

        invoice.append("Total Weight: " + totalWeight + "g\n");
        invoice.append("-----------------\n");

        miniInvoice.put("invoice", invoice.toString());
        miniInvoice.put("weight_fees", totalWeight*this.egpPerKg/1000);

        
        return miniInvoice;
    }

    public void setEgpPerKg(double egpPerKg){
        this.egpPerKg = egpPerKg;
    }

}
