package application;

import java.util.Date;

public class Order {
    private String orderNumber;
    private Date orderDate;
    private String employeeID;
    private String clientID;

    // Constructor
    public Order(String orderNumber, Date orderDate, String employeeID, String clientID) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.employeeID = employeeID;
        this.clientID = clientID;
    }

    // Setters
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    // Getters
    public String getOrderNumber() {
        return orderNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getClientID() {
        return clientID;
    }

    // toString method
    @Override
    public String toString() {
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", orderDate=" + orderDate +
                ", employeeID=" + employeeID +
                ", clientID=" + clientID +
                '}';
    }
}
