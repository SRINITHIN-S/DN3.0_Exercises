public class TaskManagementSystem {

    // Define the Task class
    static class Task {
        private String taskId;
        private String taskName;
        private String status;

        // Constructor
        public Task(String taskId, String taskName, String status) {
            this.taskId = taskId;
            this.taskName = taskName;
            this.status = status;
        }

        // Getters
        public String getTaskId() {
            return taskId;
        }

        public String getTaskName() {
            return taskName;
        }

        public String getStatus() {
            return status;
        }

        @Override
        public String toString() {
            return "Task ID: " + taskId + ", Task Name: " + taskName + ", Status: " + status;
        }
    }

    // Define the Node class for the singly linked list
    static class Node {
        Task task;
        Node next;

        // Constructor
        public Node(Task task) {
            this.task = task;
            this.next = null;
        }
    }

    // Define the SinglyLinkedList class
    static class SinglyLinkedList {
        private Node head;

        // Constructor
        public SinglyLinkedList() {
            this.head = null;
        }

        // Add a task
        public void addTask(Task task) {
            Node newNode = new Node(task);
            if (head == null) {
                head = newNode;
            } else {
                Node current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
            }
        }

        // Search for a task by ID
        public Task searchTaskById(String taskId) {
            Node current = head;
            while (current != null) {
                if (current.task.getTaskId().equals(taskId)) {
                    return current.task;
                }
                current = current.next;
            }
            return null; // Not found
        }

        // Traverse and print all tasks
        public void traverseTasks() {
            Node current = head;
            if (current == null) {
                System.out.println("No tasks to display.");
                return;
            }
            while (current != null) {
                System.out.println(current.task);
                current = current.next;
            }
        }

        // Delete a task by ID
        public void deleteTaskById(String taskId) {
            if (head == null) {
                System.out.println("No tasks to delete.");
                return;
            }
            if (head.task.getTaskId().equals(taskId)) {
                head = head.next;
                System.out.println("Task deleted.");
                return;
            }
            Node current = head;
            while (current.next != null && !current.next.task.getTaskId().equals(taskId)) {
                current = current.next;
            }
            if (current.next == null) {
                System.out.println("Task not found.");
            } else {
                current.next = current.next.next;
                System.out.println("Task deleted.");
            }
        }
    }

    public static void main(String[] args) {
        // Create SinglyLinkedList
        SinglyLinkedList taskList = new SinglyLinkedList();

        // Add tasks
        taskList.addTask(new Task("T001", "Design Homepage", "In Progress"));
        taskList.addTask(new Task("T002", "Develop API", "Completed"));
        taskList.addTask(new Task("T003", "Write Documentation", "Pending"));

        // Display all tasks
        System.out.println("All Tasks:");
        taskList.traverseTasks();

        // Search for a task
        Task searchedTask = taskList.searchTaskById("T002");
        System.out.println("\nSearched Task:");
        System.out.println(searchedTask != null ? searchedTask : "Task not found.");

        // Delete a task
        taskList.deleteTaskById("T003");

        // Display all tasks after deletion
        System.out.println("\nTasks after Deletion:");
        taskList.traverseTasks();
    }
}
