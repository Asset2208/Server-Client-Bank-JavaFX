package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mainClasses.AnnityCredit;

public class TableCreditEqual {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private TableView<AnnityCredit> table;

    @FXML
    private TableColumn<AnnityCredit, Integer> monthColumn;

    @FXML
    private TableColumn<AnnityCredit, Double> monthlyPaymentColumn;

    @FXML
    private TableColumn<AnnityCredit, Double> interestPaymentColumn;

    @FXML
    private TableColumn<AnnityCredit, Double> mainDebtColumn;

    @FXML
    private TableColumn<AnnityCredit, Double> debtEndColumn;

    @FXML
    private Label firstText;

    @FXML
    private Label secondText;

    @FXML
    private Label thirdText;
    public ObservableList<AnnityCredit> getAnnuity(ArrayList<AnnityCredit> list){
        ObservableList<AnnityCredit> lap = FXCollections.observableArrayList();

        lap.addAll(list);
        return lap;
    }
    @FXML
    void initialize() {
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        monthlyPaymentColumn.setCellValueFactory(new PropertyValueFactory<>("monthlyPayment"));
        interestPaymentColumn.setCellValueFactory(new PropertyValueFactory<>("interest"));
        mainDebtColumn.setCellValueFactory(new PropertyValueFactory<>("mainDebt"));
        debtEndColumn.setCellValueFactory(new PropertyValueFactory<>("endDebt"));
        ArrayList<AnnityCredit> annityCredits = new ArrayList<>();

        EqualCreditController equalCreditController = new EqualCreditController();
        double sum = equalCreditController.getSum();
        int months = equalCreditController.getMonths();
        double telokredita = sum / months;
        double endMonthDebt = sum - telokredita;
        double procents = (sum * 0.22) / 12;
        double monthlyPayment = procents + telokredita;
        double totalSum = monthlyPayment;
        double totalDolgPercent = procents;
        double totalTeloCredita = telokredita;
        for (int i = 0; i < months; i++){
            int x = i + 1;
            if (i == 0){
                System.out.println(i + " " + monthlyPayment + " " + procents + " " + telokredita + " " + endMonthDebt);
                annityCredits.add(new AnnityCredit(i, Math.round(monthlyPayment), Math.round(procents), Math.round(telokredita), Math.round(endMonthDebt)));

                procents = (endMonthDebt * 0.22) / 12;
                monthlyPayment = telokredita + procents;
                endMonthDebt -= telokredita;
                totalSum += monthlyPayment;

                totalDolgPercent += procents;
            }
            else {
                System.out.println(i + " " + monthlyPayment + " " + procents + " " + telokredita + " " + endMonthDebt);
                annityCredits.add(new AnnityCredit(i, Math.round(monthlyPayment), Math.round(procents), Math.round(telokredita), Math.round(endMonthDebt)));
                procents = (endMonthDebt * 0.22) / 12;
                endMonthDebt -= telokredita;

                monthlyPayment = telokredita + procents;
                if (x < months){
                    totalSum += monthlyPayment;
                }

                System.out.println(totalSum);
                totalDolgPercent += procents;
                totalTeloCredita += telokredita;
            }
        }

        table.setItems(getAnnuity(annityCredits));
        firstText.setText(Double.toString(totalSum));
        secondText.setText(Double.toString(totalDolgPercent));
        thirdText.setText(Double.toString(totalTeloCredita));

        backBtn.setOnAction(actionEvent -> {
            backBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmlFiles/equal.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
    }
}
