import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * AUTHOR: CHARUL SAINI (SIC2COB)
 */
public class OutboundRequisitionForm {

    // Fields to store the details of the outbound requisition form
    private String productName; // Name of the product
    private String productCode; // Unique product code
    private String deliveryDepartment; // Department responsible for delivery
    private String deliveryInCharge; // Person in charge of the delivery
    private String departmentInCharge; // (Currently unused, can be removed or used later)

    // Reference to the DeliveryInChargeManager to handle delivery in-charge lookup
    public DeliveryInChargeManager deliveryInChargeManager = new DeliveryInChargeManager();

    // Getter and setter methods for fields
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }
    public String getDeliveryDepartment() { return deliveryDepartment; }
    public void setDeliveryDepartment(String deliveryDepartment) { this.deliveryDepartment = deliveryDepartment; }
    public String getDepartmentInCharge() { return departmentInCharge; }
    public void setDepartmentInCharge(String departmentInCharge) { this.departmentInCharge = departmentInCharge; }

    // Default constructor
    public OutboundRequisitionForm() { super(); }

    /**
     * This method confirms the details of the outbound requisition form.
     * Checks for missing required fields and returns the formatted details.
     */
    public String confirmForm() {
        if (productName.equals("") || productCode.equals("") || deliveryDepartment.equals("")) {
            throw new IllegalArgumentException("Missing required fields");
        }
        return "Product Name: " + productName + "\n" + "Product Code: " + productCode + "\n" +
               "Delivery Department: " + deliveryDepartment + "\n" +
               "Delivery In Charge: " + deliveryInCharge + "\n" + "Add To Product Cart!!";
    }

    /**
     * Displays available locations for the product based on the product code.
     * If product code matches the inbound requisition, it shows available locations.
     */
    public void displayAvailableLocations() {
        String temporaryCode = InboundRequisition.product.getCode(); // Fetch product code from Inbound Requisition
        if (productCode.equals(temporaryCode)) {
            if (InboundRequisition.getProductLocations().isEmpty()) {
                System.out.println("No Available Location");
                throw new InternalError("No Available Location");
            }
            System.out.println("Available Locations for product Code " + productCode + ":");
            for (Location location : InboundRequisition.getProductLocations()) {
                System.out.println("Rack " + location.getRack() + ", Shelf " + location.getShelf());
            }
            Robot robot = new Robot(productCode); // Display robot handling details
            robot.display();
        } else {
            System.out.println("No Product Found");
            throw new IllegalArgumentException("No Product Found");
        }
    }

    // Override toString method for custom string representation of the form
    @Override
    public String toString() {
        return "Product Name: " + productName + ", Product Code: " + productCode +
               ", Delivery Department: " + deliveryDepartment + ", Department In-charge: " + departmentInCharge;
    }

    /**
     * This method handles user input and simulates creating an outbound requisition form.
     */
    public void displayDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Outbound Requisition Form");

        // Prompt user for product details
        System.out.println("Product Name ");
        productName = scanner.nextLine();
        System.out.println("Product Code ");
        productCode = scanner.nextLine();
        System.out.println("Delivery Department(Electronics, Clothing and Furniture) ");
        deliveryDepartment = scanner.nextLine();

        // Display available locations for the product
        displayAvailableLocations();

        // Get the delivery in-charge based on the department entered by the user
        Map<String, String> map = deliveryInChargeManager.DeliveryMap();
        if (map.containsKey(deliveryDepartment)) {
            deliveryInCharge = map.get(deliveryDepartment);
        } else {
            deliveryInCharge = "Wrong Department";
        }

        // Confirm and display the final form details
        String confirmFormString = confirmForm();
        System.out.println(confirmFormString);
    }
}
