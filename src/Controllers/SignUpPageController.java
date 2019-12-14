package Controllers;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.sql.*;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import mainClasses.Consumer;
import mainClasses.DataBase;
import mainClasses.Requests.RequestAndReply;
import mainClasses.SendSMS;

import javax.swing.*;

public class SignUpPageController {
    private ObservableList<String> cityChoices = FXCollections.observableArrayList("Almaty", "Astana","Taraz", "Shymkent", "Aktau", "Karagandy", "Pavlodar", "Oskemen", "Semey", "Atyrau", "Kostanay", "Oral");
    String bdate = "";
    String city = "";
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
    private PasswordField passwordField;

    @FXML
    private PasswordField rePasswordField;

    @FXML
    private Button registrateBtn;


    @FXML
    void initialize() throws SQLException, IOException {



        cityChoiceBox.setItems(cityChoices);
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
                RequestAndReply request = new RequestAndReply("IS_EXIST_CONSUMER_BY_LOGIN", loginField.getText());
                oos.writeObject(request);
                RequestAndReply request1 = (RequestAndReply) ois.readObject();
                boolean isExist = request1.isExist();
                if(isExist){
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
                if(isExist){
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
            if (c==0) {
                try {
                    RequestAndReply requestAndReply = new RequestAndReply("ADD_CONSUMER_REQUEST", new Consumer(null, login, password, firstName, lastName, bdate, city, telNumber, iin, "123123", 0, 0));
                    oos.writeObject(requestAndReply);
//                    RequestAndReply requestAndReply2 = (RequestAndReply) ois.readObject();
                    RequestAndReply requestAndReply1 = new RequestAndReply("ACC_NUMBER_CREATOR", login);
                    oos.writeObject(requestAndReply1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                manager.addConsumer(new Consumer(null, login, password, firstName, lastName, bdate, city, telNumber, iin, "123123", 0, 0));
//                manager.accNumberCreator(login);
                JOptionPane.showMessageDialog(null, "Sent to moderator, please wait!");
                registrateBtn.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxmlFiles/mainPage.fxml"));
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
            loader.setLocation(getClass().getResource("/fxmlFiles/mainPage.fxml"));
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
