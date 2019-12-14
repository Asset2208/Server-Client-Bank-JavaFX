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
import mainClasses.Transactions;

import javax.swing.*;

public class AddMoneyController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private TextField moneySumField;

    @FXML
    private Button addMoneyBtn;

    @FXML
    void initialize() throws IOException, ClassNotFoundException {
//        manager.connect();
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
        addMoneyBtn.setOnAction(actionEvent -> {
            if (!(moneySumField.getText().matches("[0-9]+"))){
                JOptionPane.showMessageDialog(null, "You are not allowed to enter letters", "Money error!", JOptionPane.ERROR_MESSAGE);
                addMoneyBtn.getScene().getWindow().hide();
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
            else if(Double.parseDouble(moneySumField.getText()) > 0){
//                try {
//                    Socket sockett = new Socket("localhost", 12345);
//                    ObjectOutputStream ooS = new ObjectOutputStream(socket.getOutputStream());
//                    ObjectInputStream oiS = new ObjectInputStream(socket.getInputStream());

                double money = cons.get(0).getBalance() + Double.parseDouble(moneySumField.getText());
                double balance = Double.parseDouble(moneySumField.getText());
                try {
                    System.out.println("Adding money!!!");
                    RequestAndReply requestAndReply1 = new RequestAndReply("ADD_MONEY", login, money);
                    oos.writeObject(requestAndReply1);
                    RequestAndReply requestAndReply3 = (RequestAndReply) ois.readObject();
                    System.out.println(requestAndReply3.getCode());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

//                manager.addMoney(login, money);

                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String currDateTime = formatter.format(date);
                Transactions transactions = new Transactions(null, cons.get(0).getId(), cons.get(0).getId(), balance, currDateTime, 0, 1);
                try {
                    RequestAndReply requestAndReply4 = new RequestAndReply("SEND_MONEY_OPERATION", transactions);
                    oos.writeObject(requestAndReply4);
                    RequestAndReply requestAndReply5 = (RequestAndReply) ois.readObject();
                    System.out.println(requestAndReply5.getCode());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
//                manager.sendMoneyOperation(new Transactions(null, cons.get(0).getId(), cons.get(0).getId(), balance, currDateTime, 0, 1));

                JOptionPane.showMessageDialog(null, "Money added!", "Successful!", JOptionPane.INFORMATION_MESSAGE);

                addMoneyBtn.getScene().getWindow().hide();
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
//                catch (IOException e) {
//                    e.printStackTrace();
//                }
//                double money = cons.get(0).getBalance() + Double.parseDouble(moneySumField.getText());
//                double balance = Double.parseDouble(moneySumField.getText());
//                manager.addMoney(login, money);
//                Date date = new Date();
//                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//                String currDateTime = formatter.format(date);
//                manager.sendMoneyOperation(new Transactions(null, cons.get(0).getId(), cons.get(0).getId(), balance, currDateTime, 0, 1));
//                JOptionPane.showMessageDialog(null, "Money added!", "Successful!", JOptionPane.INFORMATION_MESSAGE);
//                addMoneyBtn.getScene().getWindow().hide();
//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(getClass().getResource("/fxmlFiles/consumerPage.fxml"));
//                try {
//                    loader.load();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Parent root = loader.getRoot();
//                Stage stage = new Stage();
//                stage.setScene(new Scene(root));
//                stage.show();
            else {
                JOptionPane.showMessageDialog(null, "Incorrect value of money", "Money error!", JOptionPane.ERROR_MESSAGE);
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
