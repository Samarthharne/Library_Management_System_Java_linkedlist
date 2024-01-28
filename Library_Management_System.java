import java.util.Objects;
import java.util.Scanner;

class User {
    String username;
    String password;

    User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

class Node<T> {
    T data;
    Node<T> next;

    Node(T data) {
        this.data = data;
        this.next = null;
    }
}

class LinkedList<T> {
    Node<T> head;

    void add(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = head;
        head = newNode;
    }

    boolean contains(T data) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
}

class Authentication {
    private static LinkedList<User> usersList = new LinkedList<>();

    static boolean authenticate(String username, String password) {
        Node<User> current = usersList.head;
        while (current != null) {
            if (current.data.username.equals(username) && current.data.password.equals(password)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    static void addUser(String username, String password) {
        User newUser = new User(username, password);
        usersList.add(newUser);
        System.out.println("User added successfully: " + username);
    }

    static void setPassword(String username, String newPassword) {
        User user = new User(username, "");
        Node<User> current = usersList.head;
        while (current != null) {
            if (current.data.equals(user)) {
                current.data.password = newPassword;
                System.out.println("Password set successfully for user: " + username);
                return;
            }
            current = current.next;
        }
        System.out.println("User not found: " + username);
    }
}

class Book {
    String title;
    boolean isAvailable;
    String issuedTo;

    Book(String title) {
        this.title = title;
        this.isAvailable = true;
        this.issuedTo = null;
    }
}

class Library {
    private static LinkedList<Book> booksList = new LinkedList<>();

    void issueBook(String title, String username) {
        Node<Book> current = booksList.head;

        while (current != null) {
            if (current.data.title.equals(title) && current.data.isAvailable) {
                current.data.isAvailable = false;
                current.data.issuedTo = username;
                System.out.println("Book issued successfully: " + title);
                return;
            }
            current = current.next;
        }

        System.out.println("Book not available or not found: " + title);
    }

    void addBook(Book book) {
        booksList.add(book);
        System.out.println("Book added successfully: " + book.title);
    }

    void removeBook(String title) {
        Node<Book> current = booksList.head;
        Node<Book> prev = null;

        while (current != null) {
            if (current.data.title.equals(title)) {
                if (prev == null) {
                    booksList.head = current.next;
                } else {
                    prev.next = current.next;
                }
                System.out.println("Book removed successfully: " + title);
                return;
            }
            prev = current;
            current = current.next;
        }
        System.out.println("Book not found: " + title);
    }

    void displayAvailableBooks() {
        System.out.println("Available Books:");
        Node<Book> current = booksList.head;
        while (current != null) {
            if (current.data.isAvailable) {
                System.out.println(current.data.title + " - Available");
            } else {
                System.out.println(current.data.title + " - Issued to: " + current.data.issuedTo);
            }
            current = current.next;
        }
    }
}

public class Library_Management_System {
    private static Scanner scanner = new Scanner(System.in);
    private static Library library = new Library();

    public static void main(String[] args) {
        while (true) {
            System.out.println("Choose an Option:");
            System.out.println("1. Student Login");
            System.out.println("2. Librarian Login");
            System.out.println("3. Add New Student");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    studentMenu();
                    break;
                case 2:
                    librarianMenu();
                    break;
                case 3:
                    addStudent();
                    break;
                case 4:
                    System.out.println("Exiting Library Management System. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }

    private static void studentMenu() {
        System.out.println("Enter your username:");
        scanner.nextLine(); // Consume the newline character
        String username = scanner.nextLine();

        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        if (Authentication.authenticate(username, password)) {
            while (true) {
                System.out.println("\nStudent Menu:");
                System.out.println("1. View Books");
                System.out.println("2. Issue Book");
                System.out.println("3. Exit");
                int studentChoice = scanner.nextInt();

                switch (studentChoice) {
                    case 1:
                        library.displayAvailableBooks();
                        break;
                    case 2:
                        issueBook(username);
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            }
        } else {
            System.out.println("Authentication failed. Incorrect username or password.");
        }
    }

    private static void librarianMenu() {
        System.out.println("Enter your username:");
        scanner.nextLine();
        String username = scanner.nextLine();

        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        if (Objects.equals(username, "admin") && Objects.equals(password, "admin123")) {
            while (true) {
                System.out.println("\nLibrarian Menu:");
                System.out.println("1. Add Book");
                System.out.println("2. Remove Book");
                System.out.println("3. Display Available Books");
                System.out.println("4. Exit");
                int librarianChoice = scanner.nextInt();

                switch (librarianChoice) {
                    case 1:
                        System.out.println("Enter the title of the book to add:");
                        scanner.nextLine(); // Consume the newline character
                        String addTitle = scanner.nextLine();
                        library.addBook(new Book(addTitle));
                        break;
                    case 2:
                        System.out.println("Enter the title of the book to remove:");
                        scanner.nextLine(); // Consume the newline character
                        String removeTitle = scanner.nextLine();
                        library.removeBook(removeTitle);
                        break;
                    case 3:
                        library.displayAvailableBooks();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            }
        } else {
            System.out.println("Authentication failed. Incorrect username or password.");
        }
    }

    private static void issueBook(String username) {
        System.out.println("Enter the title of the book to issue:");
        scanner.nextLine(); // Consume the newline character
        String issueTitle = scanner.nextLine();
        library.issueBook(issueTitle, username);
    }
    private static void addStudent() {
        System.out.println("Enter the username for the new student:");
        scanner.nextLine(); // Consume the newline character
        String username = scanner.nextLine();

        System.out.println("Enter the password for the new student:");
        String password = scanner.nextLine();

        Authentication.addUser(username, password);
    }
}