import java.util.HashMap;

class Product {
    private String productId;
    private String productName;
    private int quantity;
    private double price;

    // Constructor
    public Product(String productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product ID: " + productId + ", Name: " + productName + ", Quantity: " + quantity + ", Price: " + price;
    }
}

class Inventory {
    private HashMap<String, Product> products;

    // Constructor
    public Inventory() {
        products = new HashMap<>();
    }

    // Method to add a product
    public void addProduct(Product product) {
        if (products.containsKey(product.getProductId())) {
            System.out.println("Product with ID " + product.getProductId() + " already exists.");
        } else {
            products.put(product.getProductId(), product);
            System.out.println("Product added: " + product);
        }
    }

    // Method to update a product
    public void updateProduct(String productId, int quantity, double price) {
        if (products.containsKey(productId)) {
            Product product = products.get(productId);
            product.setQuantity(quantity);
            product.setPrice(price);
            System.out.println("Product updated: " + product);
        } else {
            System.out.println("Product with ID " + productId + " not found.");
        }
    }

    // Method to delete a product
    public void deleteProduct(String productId) {
        if (products.containsKey(productId)) {
            products.remove(productId);
            System.out.println("Product with ID " + productId + " removed.");
        } else {
            System.out.println("Product with ID " + productId + " not found.");
        }
    }

    // Method to display all products
    public void displayProducts() {
        if (products.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            for (Product product : products.values()) {
                System.out.println(product);
            }
        }
    }
}

public class InventoryManagementSystem {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        // Adding products
        inventory.addProduct(new Product("P001", "Laptop", 50, 75000));
        inventory.addProduct(new Product("P002", "Smartphone", 100, 50000));

        // Displaying products
        inventory.displayProducts();

        // Updating a product
        inventory.updateProduct("P001", 60, 74000);

        // Displaying products
        inventory.displayProducts();

        // Deleting a product
        inventory.deleteProduct("P002");

        // Displaying products
        inventory.displayProducts();
    }
}
