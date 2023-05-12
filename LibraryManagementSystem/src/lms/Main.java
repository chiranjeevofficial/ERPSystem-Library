package lms;

public class Main {
    public static void main(String[] args) {
        try {
            new LoginPage();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}