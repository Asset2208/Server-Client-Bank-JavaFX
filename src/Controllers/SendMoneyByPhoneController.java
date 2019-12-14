package Controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
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

import javax.swing.*;

public class SendMoneyByPhoneController {
//    static Date date = new Date();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private TableView<Consumer> tableView;

    @FXML
    private TableColumn<Consumer, String> nameColumn;

    @FXML
    private TableColumn<Consumer, String> surnameColumn;

    @FXML
    private TextField moneyAmountField;

    @FXML
    private Button sendMoneyBtn;

    @FXML
    private TextField phoneNumField;

    @FXML
    private Button findConsumerBtn;

    public ObservableList<Consumer> getConsumer(String phoneNum) {
//        ObservableList<Consumer> cons = FXCollections.observableArrayList();
//        ArrayList<Consumer> list = manager.getConsumerByPhoneNumber(phoneNum);
//        for (Consumer c : list){
//            cons.add(c);
//
//            System.out.println(c);
//        }
        ObservableList<Consumer> cons = FXCollections.observableArrayList();
        try {
            Socket socket = new Socket("localhost", 12345);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            RequestAndReply requestAndReply = new RequestAndReply("GET_CONSUMERS_BY_PHONE", phoneNum);
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
    void initialize() throws IOException, ClassNotFoundException {
        Controller controller = new Controller();
        String login = controller.getLogName();
//        ArrayList<Consumer> sender = manager.getConsumerByLogin(login);
        Socket socket = new Socket("localhost", 12345);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        RequestAndReply requestAndReply = new RequestAndReply("GET_CONSUMER_BY_LOGIN", login);
        oos.writeObject(requestAndReply);
        RequestAndReply requestAndReply2 = (RequestAndReply) ois.readObject();
        ArrayList<Consumer> sender = requestAndReply2.getConsumers();

        nameColumn.setCellValueFactory(new PropertyValueFactory<Consumer, String>("firstName"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Consumer, String>("lastName"));

        findConsumerBtn.setOnAction(actionEvent -> {
            String phoneNum = phoneNumField.getText();
            try {
                RequestAndReply requestAndReply3 = new RequestAndReply("IS_EXIST_CONSUMER_BY_PHONE", phoneNum);
                oos.writeObject(requestAndReply3);
                RequestAndReply requestAndReply4 = (RequestAndReply) ois.readObject();
                boolean isExist = requestAndReply4.isExist();
                if(isExist){
                    tableView.setItems(getConsumer(phoneNum));
                }
                else {
                    JOptionPane.showMessageDialog(null, "There is no such a man with this number", "Incorrect phone!", JOptionPane.ERROR_MESSAGE);
                    phoneNumField.setText("");
                    tableView.setItems(null);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        sendMoneyBtn.setOnAction(actionEvent -> {
            String phoneNum = phoneNumField.getText();
            try {
                RequestAndReply request = new RequestAndReply("IS_EXIST_CONSUMER_BY_PHONE", phoneNum);
                oos.writeObject(request);
                RequestAndReply request1 = (RequestAndReply) ois.readObject();
                boolean isExist = request1.isExist();
                if(isExist){
                    RequestAndReply request2 = new RequestAndReply("GET_CONSUMERS_BY_PHONE", phoneNum);
                    oos.writeObject(request2);
                    RequestAndReply request3 = (RequestAndReply)ois.readObject();
                    ArrayList<Consumer> receiver = request3.getConsumers();
                    double money = Double.parseDouble(moneyAmountField.getText());
                    if(sender.get(0).getBalance() > money){
                        try {
                            RequestAndReply requestAndReply1 = new RequestAndReply("WITHDRAW_MONEY", login, sender.get(0).getBalance() - money);
                            oos.writeObject(requestAndReply1);
                            RequestAndReply requestAndReply3 = (RequestAndReply) ois.readObject();
                            System.out.println(requestAndReply3.getCode());
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
//                        manager.addMoney(receiver.get(0).getLogin(), receiver.get(0).getBalance() + money);
                        try {
                            System.out.println("Adding money!!!");
                            RequestAndReply requestAndReply1 = new RequestAndReply("ADD_MONEY", receiver.get(0).getLogin(), receiver.get(0).getBalance() + money);
                            oos.writeObject(requestAndReply1);
                            RequestAndReply requestAndReply3 = (RequestAndReply) ois.readObject();
                            System.out.println(requestAndReply3.getCode());
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        Date date = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        String currDateTime = formatter.format(date);
                        Transactions transactions = new Transactions(null, sender.get(0).getId(), receiver.get(0).getId(), money, currDateTime, 0, 0);
                        try {
                            RequestAndReply requestAndReply4 = new RequestAndReply("SEND_MONEY_OPERATION", transactions);
                            oos.writeObject(requestAndReply4);
                            RequestAndReply requestAndReply5 = (RequestAndReply) ois.readObject();
                            System.out.println(requestAndReply5.getCode());
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
//                        manager.withdrawMoney(login, sender.get(0).getBalance() - money);
//                        manager.addMoney(receiver.get(0).getLogin(), receiver.get(0).getBalance() + money);
//                        Date date = new Date();
//                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//                        String currDateTime = formatter.format(date);
//                        manager.sendMoneyOperation(new Transactions(null, sender.get(0).getId(), receiver.get(0).getId(), money, currDateTime, 0, 0));
                        JOptionPane.showMessageDialog(null, "Successful operation!", "Successful!", JOptionPane.INFORMATION_MESSAGE);
                        sendMoneyBtn.getScene().getWindow().hide();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/fxmlFiles/sendMoneyMenu.fxml"));
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
                        JOptionPane.showMessageDialog(null, "You don't have such an amount of money to send!", "Not enough funds!", JOptionPane.ERROR_MESSAGE);
                        moneyAmountField.setText("");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "There is no such a man with this number", "Incorrect phone!", JOptionPane.ERROR_MESSAGE);
                    phoneNumField.setText("");
                    tableView.setItems(null);
                    moneyAmountField.setText("");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        });




        backBtn.setOnAction(actionEvent -> {
            backBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmlFiles/sendMoneyMenu.fxml"));
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
