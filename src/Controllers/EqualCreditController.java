package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;

public class EqualCreditController {
    private static int sum;
    private static int months;

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        EqualCreditController.months = months;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        EqualCreditController.sum = sum;
    }
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private TextField sumOfCreditTextArea;

    @FXML
    private Button twelveBtn;

    @FXML
    private Button twentyFourBtn;

    @FXML
    private Button thirtySixBtn;

    @FXML
    void initialize() {
        twelveBtn.setOnAction(actionEvent -> {
            int c = 0;
            if (!(sumOfCreditTextArea.getText().matches("[0-9]+"))){
                JOptionPane.showMessageDialog(null, "You entered letters, fix it!", "Enter error!", JOptionPane.ERROR_MESSAGE);
                c++;
            }
            else if (sumOfCreditTextArea.getText().equals("")){
                JOptionPane.showMessageDialog(null, "You entered nothing", "Enter error!", JOptionPane.ERROR_MESSAGE);
                c++;
            }
            if (c == 0){
                int money = Integer.parseInt(sumOfCreditTextArea.getText());
                setSum(money);
                setMonths(12);
                twelveBtn.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxmlFiles/tableCreditEqual.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }
        });

        twentyFourBtn.setOnAction(actionEvent -> {
            int c = 0;
            if (!(sumOfCreditTextArea.getText().matches("[0-9]+"))){
                JOptionPane.showMessageDialog(null, "You entered letters, fix it!", "Enter error!", JOptionPane.ERROR_MESSAGE);
                c++;
            }
            else if (sumOfCreditTextArea.getText().equals("")){
                JOptionPane.showMessageDialog(null, "You entered nothing", "Enter error!", JOptionPane.ERROR_MESSAGE);
                c++;
            }
            if (c == 0){
                int money = Integer.parseInt(sumOfCreditTextArea.getText());
                setSum(money);
                setMonths(24);
                twelveBtn.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxmlFiles/tableCreditEqual.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }
        });

        thirtySixBtn.setOnAction(actionEvent -> {
            int c = 0;
            if (!(sumOfCreditTextArea.getText().matches("[0-9]+"))){
                JOptionPane.showMessageDialog(null, "You entered letters, fix it!", "Enter error!", JOptionPane.ERROR_MESSAGE);
                c++;
            }
            else if (sumOfCreditTextArea.getText().equals("")){
                JOptionPane.showMessageDialog(null, "You entered nothing", "Enter error!", JOptionPane.ERROR_MESSAGE);
                c++;
            }
            if (c == 0){
                int money = Integer.parseInt(sumOfCreditTextArea.getText());
                setSum(money);
                setMonths(36);
                twelveBtn.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxmlFiles/tableCreditEqual.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }
        });

        backBtn.setOnAction(actionEvent -> {
            backBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmlFiles/creditPage.fxml"));
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
