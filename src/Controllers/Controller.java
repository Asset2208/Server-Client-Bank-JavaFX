package Controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainClasses.DataBase;
import mainClasses.Requests.RequestAndReply;
import mainClasses.SendSMS;

import javax.swing.*;

public class Controller {
//    static DataBase manager = new DataBase();
    private static String logName;

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        Controller.logName = logName;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button signUpBtn;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button enterBtn;

    @FXML
    void initialize() {

//        manager.connect();
        signUpBtn.setOnAction(actionEvent -> {
            signUpBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmlFiles/signUpPage.fxml"));

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
        enterBtn.setOnAction(actionEvent -> {
            String login = loginField.getText();
            String password = passwordField.getText();

            try {
                Socket socket = new Socket("localhost", 12345);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                RequestAndReply requestAndReply = new RequestAndReply("CHECK_LOGIN_PASSWORD", login, password);
                oos.writeObject(requestAndReply);
                RequestAndReply requestAndReply2 = (RequestAndReply) ois.readObject();
                String answer = requestAndReply2.getText();
//                SendSMS.SendSMSManager("Asset", "Kyrgauldy", "87071880414");
                System.out.println(answer);
                if(login.equals("admin") && password.equals("admin")){
                    System.out.println(requestAndReply2);
                    enterBtn.getScene().getWindow().hide();
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
                else if(answer.equals("yesNotActivated")){
                    JOptionPane.showMessageDialog(null, "Your account hasn't been activated yet!");
//                    JOptionPane.showMessageDialog(null, "Welcome, " + login + "!");
                    loginField.setText("");
                    passwordField.setText("");
                }
//                manager.checkLoginPassword(login, password).equals("yesActivated"
                else if(answer.equals("yesActivated")){
//                    JOptionPane.showMessageDialog(null, "welcome, " + login + "!");
                    setLogName(login);
                    enterBtn.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/fxmlFiles/consumerPage.fxml"));

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
                else {
                    JOptionPane.showMessageDialog(null, "Incorrect login or password! ");
                    loginField.setText("");
                    passwordField.setText("");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
