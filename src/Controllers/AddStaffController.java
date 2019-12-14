package Controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainClasses.Consumer;
import mainClasses.Requests.RequestAndReply;
import mainClasses.Staff;
import mainClasses.DataBase;

import javax.swing.*;

public class AddStaffController {
    private ObservableList<String> cityChoices = FXCollections.observableArrayList("Almaty", "Astana","Taraz", "Shymkent", "Aktau", "Karagandy", "Pavlodar", "Oskemen", "Semey", "Atyrau", "Kostanay", "Oral");
    private ObservableList<String> positionChoises = FXCollections.observableArrayList("Trader", "Chief accountant", "Cashier", "Managing director", "Call-center operator", "Guide", "Accountant");
    String bdate = "";
    String city = "";
    String position = "";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private TextField loginField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private ChoiceBox<String> cityChoiceBox;

    @FXML
    private TextField telNumberField;

    @FXML
    private TextField iinField;

    @FXML
    private Button registrateBtn;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField rePasswordField;

    @FXML
    private ChoiceBox<String> positionChoiseBox;

    @FXML
    private TextField salaryField;

    @FXML
    void initialize() throws IOException {
        cityChoiceBox.setItems(cityChoices);
        positionChoiseBox.setItems(positionChoises);
        Socket socket = new Socket("localhost", 12345);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        registrateBtn.setOnAction(actionEvent -> {
            int c = 0;
            String login = loginField.getText();
            String password = passwordField.getText();
            String repassword = rePasswordField.getText();
            String firstName = nameField.getText();
            String lastName = surnameField.getText();
            if(dateOfBirth.getValue() != null) {
                LocalDate bdatee = dateOfBirth.getValue();
                bdate = bdatee.toString();
            }
            else {
                bdate = "";
            }
            if(cityChoiceBox.getValue() != null) {
                city = cityChoiceBox.getValue();
            }
            else {
                city = "";
            }
            String telNumber = telNumberField.getText();
            String iin = iinField.getText();
            if(positionChoiseBox.getValue() != null) {
                position = positionChoiseBox.getValue();
            }
            else {
                position = "";
            }
            double salary = Double.parseDouble(salaryField.getText());
            if(loginField.getText().equals("")){
                JOptionPane.showMessageDialog(null, "You didn't entered login!");
                c++;
            }
            if(nameField.getText().equals("")){
                JOptionPane.showMessageDialog(null, "You didn't entered name!");
                c++;
            }
            else if(!(nameField.getText().matches("[a-zA-Z]+"))){
                JOptionPane.showMessageDialog(null, "You entered digits in your name, fix it!", "Enter error!", JOptionPane.ERROR_MESSAGE);
                c++;
            }
            if(surnameField.getText().equals("")){
                JOptionPane.showMessageDialog(null, "You didn't entered surname!");
                c++;
            }
            else if(!(surnameField.getText().matches("[a-zA-Z]+"))){
                JOptionPane.showMessageDialog(null, "You entered digits in your surname, fix it!", "Enter error!", JOptionPane.ERROR_MESSAGE);
                c++;
            }
            if (passwordField.getText().equals("")){
                JOptionPane.showMessageDialog(null, "You didn't entered password!");
                c++;
            }
            if (rePasswordField.getText().equals("")){
                JOptionPane.showMessageDialog(null, "You didn't verify your password");
                c++;
            }
            if(!(passwordField.getText().equals(rePasswordField.getText()))){
                JOptionPane.showMessageDialog(null, "Passwords are not equal!");
                c++;
            }

            try {
                RequestAndReply request = new RequestAndReply("IS_EXIST_CONSUMER_BY_LOGIN", login);
                oos.writeObject(request);
                RequestAndReply request1 = (RequestAndReply) ois.readObject();
                boolean isExist = request1.isExist();

                RequestAndReply request2 = new RequestAndReply("IS_EXIST_STAFF_BY_LOGIN", login);
                oos.writeObject(request2);
                RequestAndReply request3 = (RequestAndReply) ois.readObject();
                boolean isExistStaff = request3.isExist();
                if(isExist){
                    JOptionPane.showMessageDialog(null, "This Username Already Exist!");
                    c++;
                }
                else if (isExistStaff){
                    JOptionPane.showMessageDialog(null, "This Username Already Exist!");
                    c++;
                }
                else if (loginField.getText().equals("admin")){
                    JOptionPane.showMessageDialog(null, "This login already exist!");
                    c++;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (dateOfBirth.getValue() == null){
                JOptionPane.showMessageDialog(null, "You didn't entered date");
                c++;
            }
            if(city.equals("")){
                JOptionPane.showMessageDialog(null, "You didn't entered city");
                c++;
            }
            if (telNumberField.getText().equals("")){
                JOptionPane.showMessageDialog(null, "You didn't entered telephone number");
                c++;
            }
            else if(!(telNumberField.getText().matches("[0-9]+"))){
                JOptionPane.showMessageDialog(null, "You entered letters in your phone number, fix it!", "Enter error!", JOptionPane.ERROR_MESSAGE);
                c++;
            }
            try {
                RequestAndReply request = new RequestAndReply("IS_EXIST_CONSUMER_BY_PHONE", telNumberField.getText());
                oos.writeObject(request);
                RequestAndReply request1 = (RequestAndReply) ois.readObject();
                boolean isExist = request1.isExist();

                RequestAndReply request2 = new RequestAndReply("IS_EXIST_STAFF_BY_PHONE", login);
                oos.writeObject(request2);
                RequestAndReply request3 = (RequestAndReply) ois.readObject();
                boolean isExistStaff = request3.isExist();
                if(isExist){
                    JOptionPane.showMessageDialog(null, "There is already exist such as phone number!", "Enter error!", JOptionPane.ERROR_MESSAGE);
                    c++;
                }
                else if (isExistStaff){
                    JOptionPane.showMessageDialog(null, "There is already exist such as phone number!", "Enter error!", JOptionPane.ERROR_MESSAGE);
                    c++;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            if(iinField.getText().equals("")){
                JOptionPane.showMessageDialog(null, "You didn't entered IIN");
                c++;
            }
            else if(!(iinField.getText().matches("[0-9]+"))){
                JOptionPane.showMessageDialog(null, "You entered letters in your iin number, fix it!", "Enter error!", JOptionPane.ERROR_MESSAGE);
                c++;
            }
            try {
                RequestAndReply request = new RequestAndReply("IS_EXIST_CONSUMER_BY_IIN", iinField.getText());
                oos.writeObject(request);
                RequestAndReply request1 = (RequestAndReply) ois.readObject();
                boolean isExist = request1.isExist();
                if(isExist){
                    JOptionPane.showMessageDialog(null, "There is already exist such as iin number!", "Enter error!", JOptionPane.ERROR_MESSAGE);
                    c++;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (c == 0){
                try {
                    RequestAndReply requestAndReply = new RequestAndReply("ADD_STAFF_REQUEST", new Staff(null, login, password, firstName, lastName, bdate, city, telNumber, iin, position, salary));
                    oos.writeObject(requestAndReply);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                    manager.addStaff(new Staff(null, login, password, firstName, lastName, bdate, city, telNumber, iin, position, salary));
                JOptionPane.showMessageDialog(null, "Added!");
                registrateBtn.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxmlFiles/adminPage.fxml"));
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
            loader.setLocation(getClass().getResource("/fxmlFiles/adminPage.fxml"));
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
