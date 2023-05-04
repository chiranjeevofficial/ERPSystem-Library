package lms;

public class Borrow {
    private int borrowId, studentId, accessionId;
    private Double fineAmount;
    private String borrowDate, returnDate;
    public static Borrow ref;
    private Borrow() {

    }
    public static Borrow getInstance() {
        if (ref == null) {
            ref = new Borrow();
            return ref;
        }
        return null;
    }

    public int getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getAccessionId() {
        return accessionId;
    }

    public void setAccessionId(int accessionId) {
        this.accessionId = accessionId;
    }

    public Double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(Double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return getBorrowId()+" "+
                getStudentId()+" "+
                getAccessionId()+" "+
                getBorrowDate()+" "+
                getReturnDate()+" "+
                getFineAmount();
    }
}
