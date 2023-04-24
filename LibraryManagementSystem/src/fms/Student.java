package fms;

public class Student {
    private String studentName, fatherName, course, phoneNumber, address, gender;
    private int studentId, age;

    public Student(int studentId, String studentName, String fatherName, String course, int age, String gender, String phoneNumber, String address) {
        this.studentName = studentName;
        this.fatherName = fatherName;
        this.course = course;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.studentId = Integer.parseInt(String.valueOf(studentId));
        this.age = Integer.parseInt(String.valueOf(age));
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void setCourse(String course) {
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

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(char gender) {
        this.gender = String.valueOf(gender);
    }

    public Student() {}

    public String getStudentName() {
        return studentName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getCourse() {
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

    public int getAge() {
        return age;
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
                getAge()+" "+
                getGender()+" "+
                getPhoneNumber()+" "+
                getAddress();
    }
}
