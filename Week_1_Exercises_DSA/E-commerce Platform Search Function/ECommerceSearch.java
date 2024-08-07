import java.util.Arrays;

public class ECommerceSearch {

    // Define the Product class
    static class Product {
        private String productId;
        private String productName;
        private String category;

        // Constructor
        public Product(String productId, String productName, String category) {
            this.productId = productId;
            this.productName = productName;
            this.category = category;
        }

        // Getters
        public String getProductId() {
            return productId;
        }

        public String getProductName() {
            return productName;
        }

        public String getCategory() {
            return category;
        }

        @Override
        public String toString() {
            return "Product ID: " + productId + ", Name: " + productName + ", Category: " + category;
        }
    }

    // Linear Search
    public static Product linearSearch(Product[] products, String targetProductId) {
        for (Product product : products) {
            if (product.getProductId().equals(targetProductId)) {
                return product;
            }
        }
        return null; // Not found
    }

    // Binary Search
    public static Product binarySearch(Product[] products, String targetProductId) {
        int left = 0;
        int right = products.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            Product midProduct = products[mid];

            if (midProduct.getProductId().equals(targetProductId)) {
                return midProduct;
            }
            if (targetProductId.compareTo(midProduct.getProductId()) < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return null; // Not found
    }

    // Method to sort products by productId for binary search
    public static void sortProducts(Product[] products) {
        Arrays.sort(products, (p1, p2) -> p1.getProductId().compareTo(p2.getProductId()));
    }

    public static void main(String[] args) {
        // Example products
        Product[] products = {
            new Product("P003", "Smartwatch", "Electronics"),
            new Product("P001", "Laptop", "Electronics"),
            new Product("P002", "Smartphone", "Electronics")
        };

        // Sorting products for binary search
        sortProducts(products);

        // Search examples
        String targetProductId = "P002";

        // Linear Search
        Product linearSearchResult = linearSearch(products, targetProductId);
        System.out.println("Linear Search Result: " + (linearSearchResult != null ? linearSearchResult : "Not found"));

        // Binary Search
        Product binarySearchResult = binarySearch(products, targetProductId);
        System.out.println("Binary Search Result: " + (binarySearchResult != null ? binarySearchResult : "Not found"));
    }
}
