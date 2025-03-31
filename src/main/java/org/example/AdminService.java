package org.example;

import java.sql.*;

public class AdminService {
    private final static String connectionString = "jdbc:sqlserver://localhost:1433;databaseName=Library;user=sa;password=5034081;encrypt=false;trustServerCertificate=true";




    public static String signIn(String email, String password) {
        String sql = "EXEC AdminSignIn ?, ?";
        try (Connection conn = DriverManager.getConnection(connectionString);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
//bug : rs.next useless cuz i always return smth in my procedure

            if (rs.next() && rs.getString("Message").equals("Sign-in Successful")) {
                SessionManager.CurrentuserId = rs.getInt("UserID");
                return "Welcome " + rs.getString("Name") + "!";
            } else {
                SessionManager.CurrentuserId = -1;
                return "Invalid Email or Password";
            }
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

//notice that without it the pas won`t be hashed
    public static void addNewAdmin(String firstName, String lastName, String email, String phone, String address, String password) {
        try (Connection conn = DriverManager.getConnection(connectionString);
             PreparedStatement stmt = conn.prepareStatement("EXEC AddNewAdmins ?, ?, ?, ?, ?, ?")) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, address);
            stmt.setString(6, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getString("message"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void searchBookByName(String bookName) {
        try (Connection conn = DriverManager.getConnection(connectionString);
             PreparedStatement stmt = conn.prepareStatement("EXEC SearchBookByName ?")) {
            stmt.setString(1, bookName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("ISBN: " + rs.getString("ISBN") +
                        " | Name: " + rs.getString("BookName") +
                        " | Author: " + rs.getString("AuthorName") +
                        " | Category: " + rs.getString("CategoryName") +
                        " | Publisher: " + rs.getString("PublisherName") +
                        " | Publish Date: " + rs.getString("PublishDate") +
                        " | Price: $" + rs.getDouble("Price") +
                        " | Borrow Price: $" + rs.getDouble("BorrowPrice"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeMember(int userID) {
        try (Connection conn = DriverManager.getConnection(connectionString);
             PreparedStatement stmt = conn.prepareStatement("EXEC RemoveMember ?")) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getString("Message"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void listOverdueBooks() {
        try (Connection conn = DriverManager.getConnection(connectionString);
             PreparedStatement stmt = conn.prepareStatement("EXEC ListOverdueBooks")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Overdue Book: " + rs.getString("BookID") + " Due Date: " + rs.getDate("DueDate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void getAvailableBooks() {
        String sql = "SELECT * FROM AvailableBooks";

        try (Connection conn = DriverManager.getConnection(connectionString);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String isbn = rs.getString("ISBN");
                String bookName = rs.getString("BookName");

                System.out.println("ISBN: " + isbn + ", Book Name: " + bookName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void EditBookInfo(String BookName, int toEdit, String Input) {
        try (Connection conn = DriverManager.getConnection(connectionString);
             PreparedStatement stmt = conn.prepareStatement("EXEC EditBookInfo ?, ?, ?")) {
            stmt.setString(1, BookName);
            stmt.setInt(2, toEdit);
            stmt.setString(3, Input);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println(rs.getString("Message"));
            } else {
                System.out.println("No response from database.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void SearchCategory(String category) {
        try (Connection conn = DriverManager.getConnection(connectionString);
             PreparedStatement stmt = conn.prepareStatement("EXEC SearchBookByCategory ?")) {
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();

            boolean hasResults = false;
            ResultSetMetaData metaData = rs.getMetaData();

            while (rs.next()) {
                // Check if the result contains a "message" column
                if (metaData.getColumnCount() == 1 && metaData.getColumnLabel(1).equalsIgnoreCase("message")) {
                    System.out.println(rs.getString("message"));
                    return;
                }

                hasResults = true;
                System.out.println("Book: " + rs.getString("BookName") +
                        " | Author: " + rs.getString("AuthorName") +
                        " | Date: " + rs.getString("PublishDate"));
            }

            if (!hasResults) {
                System.out.println("No books found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static String searchBookByAuthor(String authorName) {
        String sql = "EXEC SearchBookByAuthorName ?";
        try (Connection conn = DriverManager.getConnection(connectionString);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, authorName);
            ResultSet rs = pstmt.executeQuery();
            StringBuilder result = new StringBuilder();
            while (rs.next()) {
                result.append("Book: ").append(rs.getString("BookName"))
                        .append(" | Category: ").append(rs.getString("CategoryName"))
                        .append(" | Publish Date: ").append(rs.getString("PublishDate"))
                        .append("\n");
            }
            return result.length() > 0 ? result.toString() : "No books found for this author.";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public static String addBook(String isbn, String bookName, String authorName, String publisherName, String category, double borrowPrice, double price, String publishDate, int numOfCopies) {
        String sql = "EXEC AddBook ?, ?, ?, ?, ?, ?, ?, ?, ?";
        try (Connection conn = DriverManager.getConnection(connectionString);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, isbn);
            pstmt.setString(2, bookName);
            pstmt.setString(3, authorName);
            pstmt.setString(4, publisherName);
            pstmt.setString(5, category);
            pstmt.setDouble(6, borrowPrice);
            pstmt.setDouble(7, price);
            pstmt.setString(8, publishDate);
            pstmt.setInt(9, numOfCopies);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? rs.getString("message") : "Error adding book.";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }




    public static void searchForMember(String searchKeyword) {
        String sql = "EXEC SearchForMember ?";
        try (Connection conn = DriverManager.getConnection(connectionString);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, searchKeyword);
            ResultSet rs = pstmt.executeQuery();
            boolean hasResults = false;
            ResultSetMetaData metaData = rs.getMetaData();

            while (rs.next()) {
                if (metaData.getColumnCount() == 1 && metaData.getColumnLabel(1).equalsIgnoreCase("message")) {
                    System.out.println(rs.getString("message"));
                    return;
                }
                hasResults = true;
                System.out.println("User ID: " + rs.getInt("UserID") +
                        " | Name: " + rs.getString("FirstName") + " " + rs.getString("LastName") +
                        " | Email: " + rs.getString("Email") +
                        " | Phone: " + rs.getString("Phone"));
            }
            if (!hasResults) {
                System.out.println("No Memebers found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static String removeBook(String bookName) {
        String sql = "EXEC RemoveBook ?";
        try (Connection conn = DriverManager.getConnection(connectionString);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bookName);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? rs.getString("Message") : "Book Not Found.";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }
    public static void selectFromAllBooks() {
        String query = "SELECT * FROM AllBooks"; // Querying the view

        try (Connection conn = DriverManager.getConnection(connectionString);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Process the results
            while (rs.next()) {
                String bookID = rs.getString("ISBN");
                String title = rs.getString("BookName");
                String category = rs.getString("CategoryName");
                String publisher = rs.getString("PublisherName");
                String author = rs.getString("AuthorName");

                System.out.println("BookID: " + bookID + ", Title: " + title +
                        ", Category: " + category + ", Publisher: " + publisher +
                        ", Author: " + author);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void selectFromMembers() {
        String query = "SELECT * FROM Members"; // Querying the view

        try (Connection conn = DriverManager.getConnection(connectionString);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Process the results
            while (rs.next()) {
                int userID = rs.getInt("UserID");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String email = rs.getString("Email");
                String phone = rs.getString("Phone");
                String address = rs.getString("Address");
                Date registrationDate = rs.getDate("RegistrationDate");

                System.out.println("UserID: " + userID + ", Name: " + firstName + " " + lastName +
                        ", Email: " + email + ", Phone: " + phone +
                        ", Address: " + address + ", Registered On: " + registrationDate);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
// bug admin sign up ?

