package mainClasses.Requests;
import mainClasses.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Reply implements Serializable{
    private String code;
    private ArrayList<Consumer> consumers;
    private ArrayList<Staff> staff;
    private ArrayList<Transactions> transactions;
    public Reply() {}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<Consumer> getConsumers() {
        return consumers;
    }

    public void setConsumers(ArrayList<Consumer> consumers) {
        this.consumers = consumers;
    }

    public ArrayList<Staff> getStaff() {
        return staff;
    }

    public void setStaff(ArrayList<Staff> staff) {
        this.staff = staff;
    }

    public ArrayList<Transactions> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transactions> transactions) {
        this.transactions = transactions;
    }
}
