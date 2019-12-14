package Controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainClasses.Consumer;
import mainClasses.DataBase;
import Controllers.ShowConsumersController;
import mainClasses.Requests.RequestAndReply;

public class ShowIndividualConsumerController {

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
    private TextField telNumberField;

    @FXML
    private TextField iinField;

    @FXML
    private TextField dateOfBirth;

    @FXML
    private TextField cityField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField balanceField;

    @FXML
    private TextField accNumberField;

    @FXML
    private TextField isActiveField;

    @FXML
    private TextField idField;

    @FXML
    void initialize() {
        ShowConsumersController show = new ShowConsumersController();
        int id = show.getId();
        try {
            Socket socket = new Socket("localhost", 12345);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            RequestAndReply requestAndReply = new RequestAndReply("GET_CONSUMER_BY_ID", id);
            oos.writeObject(requestAndReply);
            RequestAndReply requestAndReply2 = (RequestAndReply)ois.readObject();
            ArrayList<Consumer> consumers = requestAndReply2.getConsumers();
            idField.setText(Long.toString(consumers.get(0).getId()));
            loginField.setText(consumers.get(0).getLogin());
            passwordField.setText(consumers.get(0).getPassword());
            nameField.setText(consumers.get(0).getFirstName());
            surnameField.setText(consumers.get(0).getLastName());
            balanceField.setText(Double.toString(consumers.get(0).getBalance()));
            dateOfBirth.setText(consumers.get(0).getDateOfBirth());
            cityField.setText(consumers.get(0).getCity());
            telNumberField.setText(consumers.get(0).getTelNumber());
            iinField.setText(consumers.get(0).getIin());
            accNumberField.setText(consumers.get(0).getAccNumber());
            isActiveField.setText(Integer.toString(consumers.get(0).getIsActive()));

            oos.close();
            ois.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
//        manager.connect();
//        ShowConsumersController show = new ShowConsumersController();
//        int id = show.getId();
//        ArrayList<Consumer> cons = manager.getConsumerByID(id);
//        idField.setText(Long.toString(cons.get(0).getId()));
//        loginField.setText(cons.get(0).getLogin());
//        passwordField.setText(cons.get(0).getPassword());
//        nameField.setText(cons.get(0).getFirstName());
//        surnameField.setText(cons.get(0).getLastName());
//        balanceField.setText(Double.toString(cons.get(0).getBalance()));
//        dateOfBirth.setText(cons.get(0).getDateOfBirth());
//        cityField.setText(cons.get(0).getCity());
//        telNumberField.setText(cons.get(0).getTelNumber());
//        iinField.setText(cons.get(0).getIin());
//        accNumberField.setText(cons.get(0).getAccNumber());
//        isActiveField.setText(Integer.toString(cons.get(0).getIsActive()));

        backBtn.setOnAction(actionEvent -> {
            backBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmlFiles/showConsumers.fxml"));
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
