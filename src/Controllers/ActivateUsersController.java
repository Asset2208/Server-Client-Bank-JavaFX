package Controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mainClasses.Consumer;
import mainClasses.DataBase;
import mainClasses.Requests.RequestAndReply;

public class ActivateUsersController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private TableView<Consumer> tableView;

    @FXML
    private TableColumn<Consumer, Long> idColumn;

    @FXML
    private TableColumn<Consumer, String> loginColumn;

    @FXML
    private TableColumn<Consumer, String> nameColumn;

    @FXML
    private TableColumn<Consumer, String> surnameColumn;

    @FXML
    private TableColumn<Consumer, Integer> isActiveColumn;
//    private TableColumn<Consumer, Boolean> isActiveColumn;
    @FXML
    private TextField indexUserField;

    @FXML
    private Button activateBtn;



    public ObservableList<Consumer> getConsumer() {
        ObservableList<Consumer> cons = FXCollections.observableArrayList();
        try {
            Socket socket = new Socket("localhost", 12345);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            RequestAndReply requestAndReply = new RequestAndReply("VIEW_NOT_ACTIVATED_CONSUMERS_REPLY");
            oos.writeObject(requestAndReply);
            RequestAndReply requestAndReply2 = (RequestAndReply)ois.readObject();
            ArrayList<Consumer> list = requestAndReply2.getConsumers();
            for (Consumer consumer : list){
                cons.add(consumer);
            }

            oos.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cons;
    }
    @FXML
    void initialize() {
//        manager.connect();
//        try {
//            Socket socket = new Socket("localhost", 12345);
//            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//            RequestAndReply requestAndReply = new RequestAndReply("VIEW_NOT_ACTIVATED_CONSUMERS_REPLY");
//            oos.writeObject(requestAndReply);
//            RequestAndReply requestAndReply2 = (RequestAndReply)ois.readObject();
//            ArrayList<Consumer> list = requestAndReply2.getConsumers();
//            for (Consumer consumer : list){
//                cons.add(consumer);
//            }
//
//            oos.close();
//            ois.close();
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        idColumn.setCellValueFactory(new PropertyValueFactory<Consumer, Long>("id"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<Consumer, String>("login"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Consumer, String>("firstName"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Consumer, String>("lastName"));
        isActiveColumn.setCellValueFactory(new PropertyValueFactory<>("isActive"));
        tableView.setItems(getConsumer());

        activateBtn.setOnAction(actionEvent -> {
//            int id = Integer.parseInt(indexUserField.getText());
//            manager.activateUser(id);
//            tableView.setItems(getConsumer());
//            indexUserField.setText("");
            try {
                int id = Integer.parseInt(indexUserField.getText());
                Socket socket = new Socket("localhost", 12345);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                RequestAndReply requestAndReply = new RequestAndReply("ACTIVATE_CONSUMERS", id);
                oos.writeObject(requestAndReply);
                RequestAndReply requestAndReply2 = (RequestAndReply) ois.readObject();
                System.out.println(requestAndReply2.getCode());
                tableView.setItems(getConsumer());
                oos.close();
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
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
