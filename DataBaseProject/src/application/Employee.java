package application;

import java.util.Date;

public class Employee {
    private String ID;
    private String name;
    private Date DOB;
    private Date empDate;
    private String emailAddress;
    private String depId;
    private String phoneNum;
    private String address;
    private double salary;
    private String bankInfo;

    // Constructor
    public Employee(String ID, String name, Date DOB, Date empDate, String emailAddress, String depId, String phoneNum,
                    String address, double salary, String bankInfo) {
        this.ID = ID;
        this.name = name;
        this.DOB = DOB;
        this.empDate = empDate;
        this.emailAddress = emailAddress;
        this.depId = depId;
        this.phoneNum = phoneNum;
        this.address = address;
        this.salary = salary;
        this.bankInfo = bankInfo;
    }

    // Setters
    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public void setEmpDate(Date empDate) {
        this.empDate = empDate;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setBankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
    }

    // Getters
    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public Date getDOB() {
        return DOB;
    }

    public Date getEmpDate() {
        return empDate;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getDepId() {
        return depId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public double getSalary() {
        return salary;
    }

    public String getBankInfo() {
        return bankInfo;
    }

    // toString method
    @Override
    public String toString() {
        return "Employee{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", DOB=" + DOB +
                ", empDate=" + empDate +
                ", emailAddress='" + emailAddress + '\'' +
                ", depId=" + depId +
                ", phoneNum='" + phoneNum + '\'' +
                ", address='" + address + '\'' +
                ", salary=" + salary +
                ", bankInfo='" + bankInfo + '\'' +
                '}';
    }
}
