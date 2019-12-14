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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainClasses.Consumer;
import mainClasses.DataBase;
import mainClasses.Requests.RequestAndReply;

public class MyAccountInfoController {

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
    private TextField balanceField;

    @FXML
    private TextField accNumberField;

    @FXML
    void initialize() throws IOException, ClassNotFoundException {
        Controller controller = new Controller();
        String login = controller.getLogName();
        Socket socket = new Socket("localhost", 12345);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        RequestAndReply requestAndReply = new RequestAndReply("GET_CONSUMER_BY_LOGIN", login);
        oos.writeObject(requestAndReply);
        RequestAndReply requestAndReply2 = (RequestAndReply) ois.readObject();
        ArrayList<Consumer> cons = requestAndReply2.getConsumers();
//        ArrayList<Consumer> cons = manager.getConsumerByLogin(login);
        loginField.setText(cons.get(0).getLogin());
        nameField.setText(cons.get(0).getFirstName());
        surnameField.setText(cons.get(0).getLastName());
        balanceField.setText(Double.toString(cons.get(0).getBalance()));
        dateOfBirth.setText(cons.get(0).getDateOfBirth());
        cityField.setText(cons.get(0).getCity());
        telNumberField.setText(cons.get(0).getTelNumber());
        iinField.setText(cons.get(0).getIin());
        accNumberField.setText(cons.get(0).getAccNumber());

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
