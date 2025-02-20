package application;

public class Client {
    private String clientID;
    private String clientName;
    private String phoneNumber;
    private String emailAddress;
    private String bankInfo;

    public Client(String clientID, String clientName, String phoneNumber, String emailAddress, String bankInfo) {
        this.clientID = clientID;
        this.clientName = clientName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.bankInfo = bankInfo;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientID='" + clientID + '\'' +
                ", clientName='" + clientName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", bankInfo='" + bankInfo + '\'' +
                '}';
    }
}
