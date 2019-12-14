package mainClasses;

import java.io.Serializable;

public class Transactions implements Serializable {
    private Long idTransaction;
    private Long id_sender;
    private Long id_receiver;
    private double balance;
    private String date;
    private int isWithdrawal;
    private int isAddition;

    public Transactions(Long idTransaction, Long id_sender, Long id_receiver, double balance, String date, int isWithdrawal, int isAddition) {
        this.idTransaction = idTransaction;
        this.id_sender = id_sender;
        this.id_receiver = id_receiver;
        this.balance = balance;
        this.date = date;
        this.isWithdrawal = isWithdrawal;
        this.isAddition = isAddition;
    }

    public Long getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(Long idTransaction) {
        this.idTransaction = idTransaction;
    }

    public Long getId_sender() {
        return id_sender;
    }

    public void setId_sender(Long id_sender) {
        this.id_sender = id_sender;
    }

    public Long getId_receiver() {
        return id_receiver;
    }

    public void setId_receiver(Long id_receiver) {
        this.id_receiver = id_receiver;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIsWithdrawal() {
        return isWithdrawal;
    }

    public void setIsWithdrawal(int isWithdrawal) {
        this.isWithdrawal = isWithdrawal;
    }

    public int getIsAddition() {
        return isAddition;
    }

    public void setIsAddition(int isAddition) {
        this.isAddition = isAddition;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "idTransaction=" + idTransaction +
                ", id_sender=" + id_sender +
                ", id_receiver=" + id_receiver +
                ", balance=" + balance +
                ", date='" + date + '\'' +
                '}';
    }
}
