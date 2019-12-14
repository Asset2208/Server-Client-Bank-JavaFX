package mainClasses;

import java.io.Serializable;

public class Consumer extends Person implements Serializable {
    private String telNumber;
    private String iin;
    private String accNumber;
    private double balance = 0;
//    private Boolean isActive = false;
private int isActive;
    public Consumer(){}

    public Consumer(Long id, String login, String password, String firstName, String lastName, String dateOfBirth, String city, String telNumber, String iin, String accNumber, double balance, int isActive) {
        super(id, login, password, firstName, lastName, dateOfBirth, city);
        this.telNumber = telNumber;
        this.iin = iin;
        this.accNumber = accNumber;
        this.balance = balance;
        this.isActive = isActive;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) { this.telNumber = telNumber; }

    public String getIin() {
        return iin;
    }

    public void setIin(String iin) {
        this.iin = iin;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

//    public boolean isActive() { return isActive; }

//    public void setActive(boolean active) { isActive = active; }


    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    @Override
    public String staffOrConsumer() {
        return "consumer";
    }

    @Override
    public String toString() {
        return "ID: " + getId() + "\n" +
                "First name: " + getFirstName() + "\n" +
                "Last name: " + getLastName() + "\n" +
                "Date of birth: " + getDateOfBirth() + "\n" +
                "City: " + getCity() + "\n" +
                "Tellephone number: " + telNumber + "\n" +
                "IIN: " + iin + "\n" +
                "Account number: " + accNumber  + "\n" +
                "Balance: " + balance  + "\n" +
                "IsActive " + isActive;
    }
}

