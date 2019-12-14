package Controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mainClasses.Consumer;
import mainClasses.Requests.RequestAndReply;

import javax.swing.*;

public class ShowConsumersController {
    private static int id = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        ShowConsumersController.id = id;
    }

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

    @FXML
    private TextField indexUserField;

    @FXML
    private Button showMoreInfBtn;

    ObservableList<Consumer> cons = FXCollections.observableArrayList();

//    public ObservableList<Consumer> getConsumer() {
//        ObservableList<Consumer> cons = FXCollections.observableArrayList();
////        ArrayList<Consumer> list = manager.getAllConsumers();
//        for (Consumer c : list){
//            cons.add(c);
//
//            System.out.println(c);
//        }
//        return cons;
//    }
    @FXML
    void initialize() {

        try {
            Socket socket = new Socket("localhost", 12345);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            RequestAndReply requestAndReply = new RequestAndReply("VIEW_CONSUMERS_REPLY");
            oos.writeObject(requestAndReply);
            RequestAndReply requestAndReply2 = (RequestAndReply)ois.readObject();
            ArrayList<Consumer> list = requestAndReply2.getConsumers();
            for (Consumer consumer : list){
                cons.add(consumer);
            }

//            oos.close();
//            ois.close();
        }
        catch (IOException | ClassNotFoundException ex){
            ex.printStackTrace();
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<Consumer, Long>("id"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<Consumer, String>("login"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Consumer, String>("firstName"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Consumer, String>("lastName"));
        isActiveColumn.setCellValueFactory(new PropertyValueFactory<>("isActive"));
        tableView.setItems(cons);

        showMoreInfBtn.setOnAction(actionEvent -> {
            try {
                id = Integer.parseInt(indexUserField.getText());
                Socket socket = new Socket("localhost", 12345);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                RequestAndReply requestAndReply = new RequestAndReply("CHECK_CONSUMERS_REPLY", id);
                oos.writeObject(requestAndReply);
                RequestAndReply requestAndReply2 = (RequestAndReply)ois.readObject();
                boolean isExist = requestAndReply2.isExist();

                if(isExist){
                    setId(id);
                    System.out.println("ID IN SHOW CONSUMERS IS: " + getId());
                    showMoreInfBtn.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/fxmlFiles/showIndividualConsumer.fxml"));
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
                    JOptionPane.showMessageDialog(null, "There is no such a consumer with this ID", "ID error!", JOptionPane.ERROR_MESSAGE);
                    indexUserField.setText("");
                }
                oos.close();
                ois.close();
            }
            catch (IOException | ClassNotFoundException ex){
                ex.printStackTrace();
            }

//            id = Integer.parseInt(indexUserField.getText());
//            if(manager.isExistConsumerByID(id)){
//                setId(id);
//                System.out.println("ID IN SHOW CONSUMERS IS: " + getId());
//                showMoreInfBtn.getScene().getWindow().hide();
//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(getClass().getResource("/fxmlFiles/showIndividualConsumer.fxml"));
//                try {
//                    loader.load();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Parent root = loader.getRoot();
//                Stage stage = new Stage();
//                stage.setScene(new Scene(root));
//                stage.show();
//            }
//            else {
//                JOptionPane.showMessageDialog(null, "There is no such a consumer with this ID", "ID error!", JOptionPane.ERROR_MESSAGE);
//                indexUserField.setText("");
//            }

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
