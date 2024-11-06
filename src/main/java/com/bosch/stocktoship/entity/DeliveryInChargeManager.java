import java.util.HashMap;
import java.util.Map;

public class DeliveryInChargeManager {
    /**
     * AUTHOR: CHARUL SAINI (SIC2COB)
     * This class manages the mapping of departments to their delivery in-charges.
     */
    
    // Map to store department names and their corresponding delivery in-charge
    private Map<String, String> deliveryInChargeMap;

    /**
     * Initializes the delivery in-charge map with sample data.
     * 
     * @return A map of departments to delivery in-charges.
     */
    public Map<String, String> DeliveryMap() {
        // Initialize the map with sample department data
        deliveryInChargeMap = new HashMap<>();
        deliveryInChargeMap.put("Electronics", "John Doe");
        deliveryInChargeMap.put("Furniture", "Jane Smith");
        deliveryInChargeMap.put("Clothing", "Emily Johnson");
        
        return deliveryInChargeMap;
    }

//    Retrieves the delivery in-charge for a given department.
    public String getDeliveryInCharge(String department) {
        // Return the delivery in-charge for the given department
        return deliveryInChargeMap.get(department);
    }
}
