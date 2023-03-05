import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FixConstant {
    //Start here
    public static int labelMarginX = 20;
    public static int labelWidth = 140;
    public static int labelHeight = 30;

    public static int textFieldMarginX = 170;
    public static int textFieldWidth = 230;
    public static int textFieldHeight = 30;

    public static int buttonMarginX = 70;
    public static int buttonWidth = 90;
    public static int buttonHeight = 30;

    public static Font titleFont = new Font("Times New Roman",Font.BOLD,25);
    public static Font headFont = new Font("Times New Roman",Font.BOLD,20);
    public static Font textFont = new Font("Times New Roman",Font.BOLD,15);

    public static Font myFont = new Font("Century Gothic",Font.BOLD,15);

    public static int loginLabelMarginX = 140;
    public static int loginTextFieldMarginX = 250;

    public static String bookTitle, bookAuthor, bookPublisher, bookEdition, bookId;
    public static int bookQuantity, bookSerialNumber;

    public static String getBookID(String title, String author, String publisher, String edition) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmm"));
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYMMdd"));
        return "SPDC" + date + time + title.charAt(0) + author.charAt(0) +  publisher.charAt(0)+edition.charAt(0);
    }

    public static void getBook(){
        System.out.println("AddBook Name: "+ FixConstant.bookTitle +
                "\nAddBook Author: "+FixConstant.bookAuthor+
                "\nAddBook Publisher: "+FixConstant.bookPublisher+
                "\nAddBook Edition: "+FixConstant.bookEdition+
                "\nAddBook Quantity: "+FixConstant.bookQuantity+
                "\nAddBook Serial Number: "+FixConstant.bookSerialNumber);
    }
}
