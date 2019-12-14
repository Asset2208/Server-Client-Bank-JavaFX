package mainClasses;

import java.io.Serializable;

public class Staff extends Person implements Serializable {
    private String telNumber;
    private String iin;
    private String position;
    private double salary;

    @Override
    public String staffOrConsumer() {
        return "staff";
    }

    public Staff(Long id, String login, String password,  String firstName, String lastName, String dateOfBirth, String city, String telNumber, String iin, String position, double salary) {
        super(id, login, password, firstName, lastName, dateOfBirth, city);
        this.telNumber = telNumber;
        this.iin = iin;
        this.position = position;
        this.salary = salary;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getIin() {
        return iin;
    }

    public void setIin(String iin) {
        this.iin = iin;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "telNumber='" + telNumber + '\'' +
                ", iin='" + iin + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}

