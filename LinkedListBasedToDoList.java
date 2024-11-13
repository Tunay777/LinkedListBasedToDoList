
import java.util.Scanner;
import java.io.*;

public class LinkedListBasedToDoList {
   private class Node {
      String task; 
      Node next; 
      Node(String task) {
         this.task = task;
         next = null;
      }
   }
   private Node head; 
   private int count;
   public LinkedListBasedToDoList() {
      head = null;
      count = 0;
   }
   public void addTask(String task) {
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
      count++;
      System.out.println("Task added: " + task);
   }
   public void removeTask(int position) {
      if (position < 1 || position > count) { 
         System.out.println("Invalid task number: " + position);
         return;
      }
      if (position == 1) { 
         head = head.next; 
      } else {
         Node current = head;
         for (int i = 1; i < position - 1; i++) { 
            current = current.next;
         }
         current.next = current.next.next; 
      }
      count--; 
      System.out.println("Task removed from position: " + position);
   }
   public void displayTasks() {
      if (count == 0) {
         System.out.println("To-do list is empty!");
         return;
      }
      Node current = head;
      int position = 1;
      while (current != null) {
         System.out.println(position + ". " + current.task);
         current = current.next;
         position++;
      }
   }
   public void saveTasks(String filename) {
      if (count == 0) {
         System.out.println("To-do list is empty! No tasks to save.");
         return;
      }
      try {
         File file = new File(filename);
         PrintWriter output = new PrintWriter(file);
         Node current = head;
         while (current != null) {
            output.println(current.task);
            current = current.next;
         }
         output.close();
         System.out.println("Tasks saved to " + filename);
      } catch (IOException e) {
         System.out.println("An error occurred while saving tasks.");
      }
   }
   public void loadTasks(String filename) {
      try {
         File file = new File(filename);
         if (!file.exists()) {
            System.out.println("No previous tasks found.");
            return;
         }
         Scanner input = new Scanner(file);
         while (input.hasNextLine()) {
            String task = input.nextLine();
            addTask(task); // Use the addTask method to add the loaded task
         }
         input.close();
         System.out.println("Tasks loaded from " + filename);
      } catch (FileNotFoundException e) {
         System.out.println("An error occurred while loading tasks.");
      }
   }
   public static void main(String[] args) {
      LinkedListBasedToDoList toDoList = new LinkedListBasedToDoList();
      toDoList.loadTasks("tasks.txt");

      Scanner scanner = new Scanner(System.in);
      int choice;
      do {
         System.out.println("\nTo-Do List Menu:");
         System.out.println("1. Add task");
         System.out.println("2. Display tasks");
         System.out.println("3. Remove task");
         System.out.println("4. Exit");
         System.out.print("Choose an option: ");
         choice = scanner.nextInt();
         scanner.nextLine(); // consume the newline

         System.out.println();
         switch (choice) {
            case 1:
               System.out.print("Enter the task description: ");
               String task = scanner.nextLine();
               toDoList.addTask(task);
               break;
            case 2:
               toDoList.displayTasks();
               break;
            case 3:
               System.out.print("Enter the task number to remove: ");
               int taskNumber = scanner.nextInt();
               toDoList.removeTask(taskNumber);
               break;
            case 4:
               toDoList.saveTasks("tasks.txt");
               System.out.println("Exiting...");
               break;
            default:
               System.out.println("Invalid option. Please try again.");
         }
      } while (choice != 4);
      scanner.close();
   }
}
