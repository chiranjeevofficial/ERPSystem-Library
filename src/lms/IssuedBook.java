package lms;

public class IssuedBook {
    private int issuedId, studentId, accessionId;
    private String issuedDate;
    private static IssuedBook ref;
    private IssuedBook() {

    }
    public static IssuedBook getInstance() {
        if(ref == null) {
            ref = new IssuedBook();
            return ref;
        }
        return null;
    }

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

    public int getAccessionId() {
        return accessionId;
    }

    public void setAccessionId(int accessionId) {
        this.accessionId = accessionId;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    @Override
    public String toString() {
        return getIssuedId()+" "+
                getStudentId()+" "+
                getAccessionId()+" "+
                getIssuedDate();
    }
}
