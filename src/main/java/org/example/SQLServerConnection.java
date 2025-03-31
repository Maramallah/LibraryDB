package org.example;
import java.sql.*;
import java.util.Scanner;

public class SQLServerConnection {
    public static void main(String[] args) {
        // SQL Server connection details
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Library;encrypt=false;trustServerCertificate=true";
         String connectionString = "jdbc:sqlserver://localhost:1433;databaseName=Library;user=sa;password=5034081;encrypt=false;trustServerCertificate=true";

        //AdminService adminService = new AdminService();
        Scanner scanner = new Scanner(System.in);

//        System.out.println("==== Library Admin Service Test ====");
//        while (true) {
//            System.out.println("\nChoose an operation:");
//            System.out.println("1. Admin Sign In");
//            System.out.println("2. Add New Admin");
//            System.out.println("3. Search Book By Name");
//            System.out.println("4. Remove Member");
//            System.out.println("5. List Overdue Books");
//            System.out.println("6. Get Available Books");
//            System.out.println("7. Edit Book Info");
//            System.out.println("8. Search Category");
//            System.out.println("9. Search Book By Author");
//            System.out.println("10. Add Book");
//            System.out.println("11. Search For Member");
//            System.out.println("12. Remove Book");
//            System.out.println("13. View All Books");
//            System.out.println("14. View Members");
//            System.out.println("0. Exit");
//            System.out.print("Enter your choice: ");
//
//            int choice = scanner.nextInt();
//            scanner.nextLine(); // Consume newline
//
//            switch (choice) {
//                case 1: // Admin Sign In
//                    System.out.print("Enter Email: ");
//                    String email = scanner.nextLine();
//                    System.out.print("Enter Password: ");
//                    String password = scanner.nextLine();
//                    System.out.println(AdminService.signIn(email, password));
//                    break;
//
//                case 2: // Add New Admin
//                    System.out.print("Enter First Name: ");
//                    String firstName = scanner.nextLine();
//                    System.out.print("Enter Last Name: ");
//                    String lastName = scanner.nextLine();
//                    System.out.print("Enter Email: ");
//                    String adminEmail = scanner.nextLine();
//                    System.out.print("Enter Phone: ");
//                    String phone = scanner.nextLine();
//                    System.out.print("Enter Address: ");
//                    String address = scanner.nextLine();
//                    System.out.print("Enter Password: ");
//                    String adminPassword = scanner.nextLine();
//                    AdminService.addNewAdmin(firstName, lastName, adminEmail, phone, address, adminPassword);
//                    break;
//
//                case 3: // Search Book By Name
//                    System.out.print("Enter Book Name: ");
//                    String bookName = scanner.nextLine();
//                    AdminService.searchBookByName(bookName);
//                    break;
//
//                case 4: // Remove Member
//                    System.out.print("Enter User ID to Remove: ");
//                    int userID = scanner.nextInt();
//                    AdminService.removeMember(userID);
//                    break;
//
//                case 5: // List Overdue Books
//                    AdminService.listOverdueBooks();
//                    break;
//
//                case 6: // Get Available Books
//                    AdminService adminService = new AdminService();
//                    adminService.getAvailableBooks();
//                    break;
//
//                case 7: // Edit Book Info
//                    System.out.print("Enter Book Name: ");
//                    String bookToEdit = scanner.nextLine();
//                    System.out.print("Enter Field to Edit (1: Name, 2: Author, 3: Price): ");
//                    int fieldToEdit = scanner.nextInt();
//                    scanner.nextLine();
//                    System.out.print("Enter New Value: ");
//                    String newValue = scanner.nextLine();
//                    new AdminService().EditBookInfo(bookToEdit, fieldToEdit, newValue);
//                    break;
//
//                case 8: // Search Category
//                    System.out.print("Enter Category: ");
//                    String category = scanner.nextLine();
//                    new AdminService().SearchCategory(category);
//                    break;
//
//                case 9: // Search Book By Author
//                    System.out.print("Enter Author Name: ");
//                    String authorName = scanner.nextLine();
//                    System.out.println(AdminService.searchBookByAuthor(authorName));
//                    break;
//
//                case 10: // Add Book
//                    System.out.print("Enter ISBN: ");
//                    String isbn = scanner.nextLine();
//                    System.out.print("Enter Book Name: ");
//                    String newBookName = scanner.nextLine();
//                    System.out.print("Enter Author Name: ");
//                    String newAuthorName = scanner.nextLine();
//                    System.out.print("Enter Publisher Name: ");
//                    String publisherName = scanner.nextLine();
//                    System.out.print("Enter Category: ");
//                    String bookCategory = scanner.nextLine();
//                    System.out.print("Enter Borrow Price: ");
//                    double borrowPrice = scanner.nextDouble();
//                    System.out.print("Enter Price: ");
//                    double price = scanner.nextDouble();
//                    scanner.nextLine(); // Consume newline
//                    System.out.print("Enter Publish Date (YYYY-MM-DD): ");
//                    String publishDate = scanner.nextLine();
//                    System.out.print("Enter Number of Copies: ");
//                    int numOfCopies = scanner.nextInt();
//                    System.out.println(AdminService.addBook(isbn, newBookName, newAuthorName, publisherName, bookCategory, borrowPrice, price, publishDate, numOfCopies));
//                    break;
//
//                case 11: // Search For Member
//                    System.out.print("Enter Search Keyword: ");
//                    String searchKeyword = scanner.nextLine();
//                    AdminService.searchForMember(searchKeyword);
//                    break;
//
//                case 12: // Remove Book
//                    System.out.print("Enter Book Name to Remove: ");
//                    String bookToRemove = scanner.nextLine();
//                    System.out.println(AdminService.removeBook(bookToRemove));
//                    break;
//
//                case 13: // View All Books
//                    AdminService.selectFromAllBooks();
//                    break;
//
//                case 14: // View Members
//                    AdminService.selectFromMembers();
//                    break;
//
//                case 0: // Exit
//                    System.out.println("Exiting...");
//                    scanner.close();
//                    return;
//
//                default:
//                    System.out.println("Invalid choice. Please try again.");
//            }
//        }
        while (true) {
            System.out.println("\n--- Library System Testing ---");
            System.out.println("1. Sign Up");
            System.out.println("2. Sign In");
            System.out.println("3. Calculate Fine");
            System.out.println("4. Borrow Book");
            System.out.println("5. Buy Book");
            System.out.println("6. Return Book");
            System.out.println("7. Pay Fine");
            System.out.println("8. Borrow Record History");
            System.out.println("9. Current Borrow Records");
            System.out.println("10. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("First Name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Last Name: ");
                    String lastName = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Phone: ");
                    String phone = scanner.nextLine();
                    System.out.print("Address: ");
                    String address = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    System.out.println(MemberService.signUp(firstName, lastName, email, phone, address, password));
                    break;

                case 2:
                    System.out.print("Email: ");
                    String signInEmail = scanner.nextLine();
                    System.out.print("Password: ");
                    String signInPassword = scanner.nextLine();
                    System.out.println(MemberService.signIn(signInEmail, signInPassword));
                    break;

                case 3:
                    System.out.println("Total Fine: " + MemberService.calculateFine());
                    break;

                case 4:
                    System.out.print("Book Name: ");
                    String borrowBookName = scanner.nextLine();
                    System.out.println(MemberService.borrowBook(borrowBookName));
                    break;

                case 5:
                    System.out.print("Book Name: ");
                    String buyBookName = scanner.nextLine();
                    System.out.println(MemberService.buyBook(buyBookName));
                    break;

                case 6:
                    System.out.print("Book Name: ");
                    String returnBookName = scanner.nextLine();
                    System.out.println(MemberService.returnBook(returnBookName));
                    break;

                case 7:
                    System.out.println(MemberService.payFine());
                    break;

                case 8: // Borrow Record History
                    MemberService.borrowRecordHistory();
                    break;

                case 9: // Current Borrow Records
                    MemberService.currentBorrowRecords();
                    break;

                case 10:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }
}
