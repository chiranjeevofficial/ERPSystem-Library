package lms;

public class IssuedBook {
    private int issuedId, studentId, bookId;
    private String issuedDate, dueDate, returnDate;
    private double fineAmount;
    public int getIssuedId() {
        return issuedId;
    }

    public void setIssuedId(int issuedId) {
        this.issuedId = issuedId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    @Override
    public String toString() {
        return getIssuedId()+" "+
                getStudentId()+" "+
                getBookId()+" "+
                getIssuedDate()+" "+
                getDueDate()+" "+
                getReturnDate()+" "+
                getFineAmount();
    }
}
