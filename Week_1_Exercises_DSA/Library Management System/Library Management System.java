import java.util.Arrays;

public class LibraryManagementSystem {

    // Define the Book class
    static class Book {
        private String bookId;
        private String title;
        private String author;

        // Constructor
        public Book(String bookId, String title, String author) {
            this.bookId = bookId;
            this.title = title;
            this.author = author;
        }

        // Getters
        public String getBookId() {
            return bookId;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        @Override
        public String toString() {
            return "Book ID: " + bookId + ", Title: " + title + ", Author: " + author;
        }
    }

    // Linear Search for books by title
    public static Book linearSearchByTitle(Book[] books, String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null; // Not found
    }

    // Binary Search for books by title (requires sorted array)
    public static Book binarySearchByTitle(Book[] books, String title) {
        int low = 0;
        int high = books.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            int comparison = books[mid].getTitle().compareToIgnoreCase(title);

            if (comparison == 0) {
                return books[mid];
            } else if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null; // Not found
    }

    public static void main(String[] args) {
        // Sample books
        Book[] books = {
            new Book("B001", "The Great Gatsby", "F. Scott Fitzgerald"),
            new Book("B002", "To Kill a Mockingbird", "Harper Lee"),
            new Book("B003", "1984", "George Orwell")
        };

        // Linear Search
        Book resultLinear = linearSearchByTitle(books, "1984");
        System.out.println("Linear Search Result:");
        System.out.println(resultLinear != null ? resultLinear : "Book not found.");

        // Binary Search requires sorted array
        Arrays.sort(books, (a, b) -> a.getTitle().compareToIgnoreCase(b.getTitle()));

        // Binary Search
        Book resultBinary = binarySearchByTitle(books, "To Kill a Mockingbird");
        System.out.println("\nBinary Search Result:");
        System.out.println(resultBinary != null ? resultBinary : "Book not found.");
    }
}
