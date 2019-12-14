package Controllers;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
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

public class TableCreditController {

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

        AnnuityController annuityController = new AnnuityController();
        double sum = annuityController.getSum();
        int months = annuityController.getMonths();
        System.out.println("MONTHSS "+ months);
        double monthPercent = 22.0/100.0/12;
        double dolg = sum * monthPercent;

//        double monthlyPayment = sum * (monthPercent + (monthPercent)/(Math.pow((1+monthPercent), months) - 1));
        double monthlyPayment = (sum * monthPercent) / (1 - Math.pow((1 + monthPercent), -(months)));
        System.out.println(monthlyPayment);
        double totalSum = 0;
        double totalDolgPercent = 0;
        double totalTeloCredita = 0;
        double endMonthDebt = sum;
        double teloCredita = monthlyPayment - dolg;
        for (int i = 0; i < months; i++){
            if (i == 0){
                int x = i + 1;
                totalSum += monthlyPayment;
                endMonthDebt -= teloCredita;
                totalDolgPercent += dolg;
                totalTeloCredita += teloCredita;
                annityCredits.add(new AnnityCredit(x, Math.round(monthlyPayment), Math.round(dolg), Math.round(teloCredita), Math.round(endMonthDebt)));
                dolg = endMonthDebt * monthPercent;
            }
            else {
                int x = i + 1;
                totalSum += monthlyPayment;
                totalDolgPercent += dolg;
                teloCredita = monthlyPayment - dolg;
                totalTeloCredita += teloCredita;
                endMonthDebt -= teloCredita;
                annityCredits.add(new AnnityCredit(x, Math.round(monthlyPayment), Math.round(dolg), Math.round(teloCredita), Math.round(endMonthDebt)));
                dolg = endMonthDebt * monthPercent;
            }
        }

        table.setItems(getAnnuity(annityCredits));
        firstText.setText(Double.toString(totalSum));
        secondText.setText(Double.toString(totalDolgPercent));
        thirdText.setText(Double.toString(totalTeloCredita));

        backBtn.setOnAction(actionEvent -> {
            backBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmlFiles/annuity.fxml"));
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
