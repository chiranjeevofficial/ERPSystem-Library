package lms;

public class Student {
    private String studentName, fatherName, phoneNumber, address, gender, dateOfBirth;
    private int studentId, course;
    private static Student ref;

    private Student() {

    }

    public static Student getInstance() {
        if (ref == null) {
            ref = new Student();
            return ref;
        }
        return null;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setGender(char gender) {
        this.gender = String.valueOf(gender);
    }

    public String getStudentName() {
        return studentName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public int getCourse() {
        return course;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getGender() {
        return gender;
    }
    @Override
    public String toString() {
        return  getStudentId()+" "+
                getStudentName()+" "+
                getFatherName()+" "+
                getCourse()+" "+
                getGender()+" "+
                getPhoneNumber()+" "+
                getAddress();
    }
}
