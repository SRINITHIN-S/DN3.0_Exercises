public class EmployeeManagementSystem {

    // Define the Employee class
    static class Employee {
        private String employeeId;
        private String name;
        private String position;
        private double salary;

        // Constructor
        public Employee(String employeeId, String name, String position, double salary) {
            this.employeeId = employeeId;
            this.name = name;
            this.position = position;
            this.salary = salary;
        }

        // Getters
        public String getEmployeeId() {
            return employeeId;
        }

        public String getName() {
            return name;
        }

        public String getPosition() {
            return position;
        }

        public double getSalary() {
            return salary;
        }

        @Override
        public String toString() {
            return "Employee ID: " + employeeId + ", Name: " + name + ", Position: " + position + ", Salary: $" + salary;
        }
    }
    
    // Define the Employee Management System
    static class EmployeeManager {
        private Employee[] employees;
        private int size;

        // Constructor
        public EmployeeManager(int capacity) {
            employees = new Employee[capacity];
            size = 0;
        }

        // Add an employee
        public void addEmployee(Employee employee) {
            if (size < employees.length) {
                employees[size++] = employee;
            } else {
                System.out.println("Cannot add employee. Array is full.");
            }
        }

        // Search for an employee by ID
        public Employee searchEmployeeById(String employeeId) {
            for (int i = 0; i < size; i++) {
                if (employees[i].getEmployeeId().equals(employeeId)) {
                    return employees[i];
                }
            }
            return null; // Not found
        }

        // Traverse and print all employees
        public void traverseEmployees() {
            if (size == 0) {
                System.out.println("No employees to display.");
                return;
            }
            for (int i = 0; i < size; i++) {
                System.out.println(employees[i]);
            }
        }

        // Delete an employee by ID
        public void deleteEmployeeById(String employeeId) {
            int index = -1;
            for (int i = 0; i < size; i++) {
                if (employees[i].getEmployeeId().equals(employeeId)) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                System.out.println("Employee not found.");
                return;
            }
            // Shift elements to fill the gap
            for (int i = index; i < size - 1; i++) {
                employees[i] = employees[i + 1];
            }
            employees[--size] = null; // Clear last element
            System.out.println("Employee deleted.");
        }
    }

    public static void main(String[] args) {
        // Create EmployeeManager with capacity for 5 employees
        EmployeeManager manager = new EmployeeManager(5);

        // Add employees
        manager.addEmployee(new Employee("E001", "John Doe", "Software Engineer", 90000));
        manager.addEmployee(new Employee("E002", "Jane Smith", "Project Manager", 95000));
        manager.addEmployee(new Employee("E003", "Alice Johnson", "UX Designer", 85000));

        // Display all employees
        System.out.println("All Employees:");
        manager.traverseEmployees();

        // Search for an employee
        Employee searchedEmployee = manager.searchEmployeeById("E002");
        System.out.println("\nSearched Employee:");
        System.out.println(searchedEmployee != null ? searchedEmployee : "Employee not found.");

        // Delete an employee
        manager.deleteEmployeeById("E003");

        // Display all employees after deletion
        System.out.println("\nEmployees after Deletion:");
        manager.traverseEmployees();
    }
}
