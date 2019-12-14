package mainClasses;

public class AnnityCredit {
    private int month;
    private double monthlyPayment;
    private double interest;
    private double mainDebt;
    private double endDebt;

    public AnnityCredit(int month, double monthlyPayment, double interest, double mainDebt, double endDebt) {
        this.month = month;
        this.monthlyPayment = monthlyPayment;
        this.interest = interest;
        this.mainDebt = mainDebt;
        this.endDebt = endDebt;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getMainDebt() {
        return mainDebt;
    }

    public void setMainDebt(double mainDebt) {
        this.mainDebt = mainDebt;
    }

    public double getEndDebt() {
        return endDebt;
    }

    public void setEndDebt(double endDebt) {
        this.endDebt = endDebt;
    }

    @Override
    public String toString() {
        return "AnnityCredit{" +
                "month=" + month +
                ", monthlyPayment=" + monthlyPayment +
                ", interest=" + interest +
                ", mainDebt=" + mainDebt +
                ", endDebt=" + endDebt +
                '}';
    }
}
