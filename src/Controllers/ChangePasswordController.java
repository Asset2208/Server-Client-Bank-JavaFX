package Controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import mainClasses.Consumer;
import mainClasses.DataBase;
import mainClasses.Requests.RequestAndReply;

import javax.swing.*;

public class ChangePasswordController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private Button changeBtn;

    @FXML
    private PasswordField currPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField verifyNewPasswordField;

    @FXML
    void initialize() throws IOException, ClassNotFoundException {
//        manager.connect();
        Controller controller = new Controller();
        String login = controller.getLogName();
//        ArrayList<Consumer> cons = manager.getConsumerByLogin(login);
        Socket socket = new Socket("localhost", 12345);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        RequestAndReply requestAndReply = new RequestAndReply("GET_CONSUMER_BY_LOGIN", login);
        oos.writeObject(requestAndReply);
        RequestAndReply requestAndReply2 = (RequestAndReply) ois.readObject();
        ArrayList<Consumer> cons = requestAndReply2.getConsumers();

        changeBtn.setOnAction(actionEvent -> {
            String oldPassword = currPasswordField.getText();
            String newPassword = newPasswordField.getText();
            String verifyNewPassword = verifyNewPasswordField.getText();
            if(cons.get(0).getPassword().equals(oldPassword)){
                if(newPassword.equals(verifyNewPassword)){
//                    manager.changePassword(cons.get(0).getLogin(), newPassword);
                    RequestAndReply requestAndReply1 = new RequestAndReply("CHANGE_PASSWORD", cons.get(0).getLogin(), newPassword);
                    try {
                        oos.writeObject(requestAndReply1);
                        RequestAndReply requestAndReply3 = (RequestAndReply) ois.readObject();
                        System.out.println(requestAndReply3.getCode());
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, "Password is changed", "Successful!", JOptionPane.INFORMATION_MESSAGE);
                    changeBtn.getScene().getWindow().hide();
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
                    JOptionPane.showMessageDialog(null, "Passwords are different", "Password error!", JOptionPane.ERROR_MESSAGE);
                    currPasswordField.setText("");
                    newPasswordField.setText("");
                    verifyNewPasswordField.setText("");
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Incorrect current password", "Password error!", JOptionPane.ERROR_MESSAGE);
                currPasswordField.setText("");
                newPasswordField.setText("");
                verifyNewPasswordField.setText("");
            }
        });
        backBtn.setOnAction(actionEvent -> {
            backBtn.getScene().getWindow().hide();
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
        });
    }
}
