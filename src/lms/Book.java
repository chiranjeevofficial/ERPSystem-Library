package lms;

public class Book {
    private String title, author, publisher, edition, date;
    private int accessionId, course, quantity;
    private double price;
    private boolean availability;
    private static Book ref;

    private Book() {

    }

    public static Book getInstance() {
        if(ref == null) {
            ref = new Book();
            return ref;
        }
        return null;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public int getAccessionId() {
        return accessionId;
    }

    public void setAccessionId(int accessionId) {
        this.accessionId = accessionId;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return  getAccessionId()+" "+
                getTitle()+" "+
                getAuthor()+" "+
                getPublisher()+" "+
                getEdition()+" "+
                getCourse()+" "+
                getDate()+" "+
                getPrice()+" "+
                getAvailability();
    }
}
