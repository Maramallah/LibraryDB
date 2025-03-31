package org.example;

import java.sql.*;

public class MemberService {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=Library;encrypt=false;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "5034081";
    private static int CurrentuserId=0;

    public static String signUp(String firstName, String lastName, String email, String phone, String address, String password) {
        String sql = "EXEC SignUp ?, ?, ?, ?, ?, ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, phone);
            pstmt.setString(5, address);
            pstmt.setString(6, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String message = rs.getString("Message");
                int userId = rs.getInt("UserID");

                // Store UserID if the user was successfully created
                CurrentuserId = (userId > 0) ? userId : -1;

                return message;
            }
            return "Error: No response from database.";
        } catch (SQLException e) {
            return "SQL Error: " + e.getMessage();
        }
    }




    public static String signIn(String email, String password) {
        String sql = "EXEC MemberSignIn ?, ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("UserID"); // Retrieve UserID
                CurrentuserId = (userId == -1) ? -1 : userId; // Store or reset UserID
                return rs.getString("Message"); // Return only the message
            } else {
               CurrentuserId=-1;// Reset if sign-in fails
                return "Invalid Email or Password";
            }
        } catch (SQLException e) {
            return e.getMessage();
        }
    }


    public static int calculateFine() {
        String sql = "EXEC CalculateFine ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, CurrentuserId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? rs.getInt("TotalFine") : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void borrowRecordHistory() {
        String sql = "EXEC BorrowRecordeHistory ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, CurrentuserId);
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            boolean hasResults = false;

            while (rs.next()) {
                hasResults = true;
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(metaData.getColumnLabel(i) + ": " + rs.getString(i) + " | ");
                }
                System.out.println(); // Move to the next line after each row
            }

            if (!hasResults) {
                System.out.println("No borrowing history found.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void currentBorrowRecords() {
        String sql = "EXEC CurrentBorrowRecords ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, CurrentuserId);
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            boolean hasResults = false;

            while (rs.next()) {
                hasResults = true;
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(metaData.getColumnLabel(i) + ": " + rs.getString(i) + " | ");
                }
                System.out.println();
            }

            if (!hasResults) {
                System.out.println("No current borrow records found.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public static String borrowBook(String bookName) {
        String sql = "EXEC BorrowBook ?, ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, CurrentuserId);
            pstmt.setString(2, bookName);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? rs.getString("Message") : "Book Not Available";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public static String buyBook(String bookName) {
        String sql = "EXEC BuyBook ?, ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bookName);
            pstmt.setInt(2, CurrentuserId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? rs.getString("Message") : "Book Not Available";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public static String returnBook( String bookName) {
        String sql = "EXEC ReturnBook ?, ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, CurrentuserId);
            pstmt.setString(2, bookName);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? rs.getString("Message") : "No records found";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public static String payFine() {
        String sql = "EXEC PayFine ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, CurrentuserId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? rs.getString("Message") : "No Fine to Pay";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }
}

//bug sign uo needs to be modifyed to give the user id