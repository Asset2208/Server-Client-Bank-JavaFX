package mainClasses.Requests;

import mainClasses.*;

import java.io.Serializable;
import java.util.ArrayList;

public class RequestAndReply implements Serializable{
    private String code;
    private Consumer consumer;
    private Staff staff;
    private Transactions transaction;

    private ArrayList<Consumer> consumers = null;
    private ArrayList<Staff> staffs = null;
    private  ArrayList<Transactions> transactions = null;
    private int id2;
    private Long id;
    private boolean isExist;
    private String text;
    private String text_2;
    private double amount;

    public RequestAndReply(String code, Long id) {
        this.code = code;
        this.id = id;
    }

    public RequestAndReply(String code, int id2) {
        this.code = code;
        this.id2 = id2;
    }

    public RequestAndReply(String code, String text, double amount) {
        this.code = code;
        this.text = text;
        this.amount = amount;
    }

    public RequestAndReply(String code, int id2, double amount) {
        this.code = code;
        this.id2 = id2;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public RequestAndReply(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId2() {
        return id2;
    }

    public void setId2(int id2) {
        this.id2 = id2;
    }

    public RequestAndReply(String code) {
        this.code = code;
    }

    public RequestAndReply(String code, Consumer consumer) {
        this.code = code;
        this.consumer = consumer;
    }

    public RequestAndReply(String code, Staff staff) {
        this.code = code;
        this.staff = staff;
    }

    public RequestAndReply(String code, String text, String text_2) {
        this.code = code;
        this.text = text;
        this.text_2 = text_2;
    }

    public RequestAndReply(String code, Transactions transaction) {
        this.code = code;
        this.transaction = transaction;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Transactions getTransaction() {
        return transaction;
    }

    public void setTransaction(Transactions transaction) {
        this.transaction = transaction;
    }

    public ArrayList<Consumer> getConsumers() {
        return consumers;
    }

    public void setConsumers(ArrayList<Consumer> consumers) {
        this.consumers = consumers;
    }

    public ArrayList<Staff> getStaffs() {
        return staffs;
    }

    public void setStaffs(ArrayList<Staff> staffs) {
        this.staffs = staffs;
    }

    public ArrayList<Transactions> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transactions> transactions) {
        this.transactions = transactions;
    }

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
    }

    public String getText_2() {
        return text_2;
    }

    public void setText_2(String text_2) {
        this.text_2 = text_2;
    }

    @Override
    public String toString() {
        return "(REQUEST AND REPLY HANDLER) CODE = " + "[" + code + "]";
    }
}
