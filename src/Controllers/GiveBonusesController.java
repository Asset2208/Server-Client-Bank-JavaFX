package Controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import mainClasses.Transactions;

public class GiveBonusesController {

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
    private TableColumn<Consumer, Double> balanceColumn;

    @FXML
    private TextField indexUserField;

    @FXML
    private Button giveBonusBtn;

    @FXML
    private TextField bonusAmountField;

//    ObservableList<Consumer> cons = FXCollections.observableArrayList();

    public ObservableList<Consumer> getConsumer() {
        ObservableList<Consumer> cons = FXCollections.observableArrayList();
        try {
            Socket socket = new Socket("localhost", 12345);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            RequestAndReply requestAndReply = new RequestAndReply("VIEW_ACTIVATED_CONSUMERS_REPLY");
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
//    public ObservableList<Consumer> getConsumer() {
//        ObservableList<Consumer> cons = FXCollections.observableArrayList();
//        ArrayList<Consumer> list = manager.getActivatedConsumer();
//        for (Consumer c : list){
//            cons.add(c);
//
//            System.out.println(c);
//        }
//        return cons;
//    }
    @FXML
    void initialize() {
//        manager.connect();
        idColumn.setCellValueFactory(new PropertyValueFactory<Consumer, Long>("id"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<Consumer, String>("login"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Consumer, String>("firstName"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Consumer, String>("lastName"));
        isActiveColumn.setCellValueFactory(new PropertyValueFactory<>("isActive"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        tableView.setItems(getConsumer());
        giveBonusBtn.setOnAction(actionEvent -> {
            try {
                Socket socket = new Socket("localhost", 12345);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                int id = Integer.parseInt(indexUserField.getText());
                double balance = Double.parseDouble(bonusAmountField.getText());
                RequestAndReply requestAndReply = new RequestAndReply("GIVE_BONUSES", id, balance);
                oos.writeObject(requestAndReply);
                RequestAndReply requestAndReply2 = (RequestAndReply)ois.readObject();
                System.out.println(requestAndReply2);
//                manager.giveBonuses(id, balance);
                int adminId = 0;
                Long idd = Integer.toUnsignedLong(id);
                Long adIdd = Integer.toUnsignedLong(adminId);
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String currDateTime = formatter.format(date);
                Transactions transactions = new Transactions(null, adIdd, idd, balance, currDateTime, 0, 0);
                try {
                    RequestAndReply requestAndReply4 = new RequestAndReply("SEND_MONEY_OPERATION", transactions);
                    oos.writeObject(requestAndReply4);
                    RequestAndReply requestAndReply5 = (RequestAndReply) ois.readObject();
                    System.out.println(requestAndReply5.getCode());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
//                manager.sendMoneyOperation(new Transactions(null, adIdd, idd, balance, currDateTime, 0, 0));
                tableView.setItems(getConsumer());
                indexUserField.setText("");
                bonusAmountField.setText("");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
//            int id = Integer.parseInt(indexUserField.getText());
//            double balance = Double.parseDouble(bonusAmountField.getText());
//            manager.giveBonuses(id, balance);
//            int adminId = 0;
//            Long idd = Integer.toUnsignedLong(id);
//            Long adIdd = Integer.toUnsignedLong(adminId);
//            Date date = new Date();
//            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//            String currDateTime = formatter.format(date);
//            manager.sendMoneyOperation(new Transactions(null, adIdd, idd, balance, currDateTime, 0, 0));
//            tableView.setItems(getConsumer());
//            indexUserField.setText("");
//            bonusAmountField.setText("");
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
