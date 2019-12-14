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
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import mainClasses.Consumer;
import mainClasses.DataBase;
import mainClasses.Requests.RequestAndReply;
import mainClasses.Transactions;

public class TransactionsAdminController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private TextArea transactionsArea;

    @FXML
    void initialize() throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", 12345);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        RequestAndReply requestAndReply = new RequestAndReply("GET_ALL_TRANSACTIONS");
        oos.writeObject(requestAndReply);
        RequestAndReply requestAndReply2 = (RequestAndReply) ois.readObject();
        ArrayList<Transactions> transactions = requestAndReply2.getTransactions();
//        ArrayList<Transactions> transactions = manager.getAllTransactions();
        for (Transactions tr : transactions){
            if(tr.getId_sender().equals(tr.getId_receiver())){
//                ArrayList<Consumer> cons = manager.getConsumerByID(tr.getId_sender().intValue());
                RequestAndReply requestAndReply1 = new RequestAndReply("GET_CONSUMER_BY_ID", tr.getId_sender().intValue());
                oos.writeObject(requestAndReply1);
                RequestAndReply requestAndReply3 = (RequestAndReply) ois.readObject();
                ArrayList<Consumer> cons = requestAndReply3.getConsumers();
                if(tr.getIsWithdrawal() == 1){
                    transactionsArea.appendText(tr.getDate() + " " + cons.get(0).getFirstName() + " " + cons.get(0).getLastName() + " withdrawed " + tr.getBalance() + " tenges.\n");
                }
                else if(tr.getIsAddition() == 1){
                    transactionsArea.appendText(tr.getDate() + " " + cons.get(0).getFirstName() + " " + cons.get(0).getLastName() + " added " + tr.getBalance() + " tenges.\n");
                }
            }
            else if(tr.getId_sender() == 0){
//                ArrayList<Consumer> cons = manager.getConsumerByID(tr.getId_receiver().intValue());
                RequestAndReply requestAndReply1 = new RequestAndReply("GET_CONSUMER_BY_ID", tr.getId_receiver().intValue());
                oos.writeObject(requestAndReply1);
                RequestAndReply requestAndReply3 = (RequestAndReply) ois.readObject();
                ArrayList<Consumer> cons = requestAndReply3.getConsumers();
                transactionsArea.appendText(tr.getDate() + " " + cons.get(0).getFirstName() + " " + cons.get(0).getLastName() + " was given a bonus " + tr.getBalance() + " tenges.\n");
            }
            else {
//                ArrayList<Consumer> sender = manager.getConsumerByID(tr.getId_sender().intValue());
                RequestAndReply requestAndReply1 = new RequestAndReply("GET_CONSUMER_BY_ID", tr.getId_sender().intValue());
                oos.writeObject(requestAndReply1);
                RequestAndReply requestAndReply3 = (RequestAndReply) ois.readObject();
                ArrayList<Consumer> sender = requestAndReply3.getConsumers();

                RequestAndReply requestAndReply4 = new RequestAndReply("GET_CONSUMER_BY_ID", tr.getId_receiver().intValue());
                oos.writeObject(requestAndReply4);
                RequestAndReply requestAndReply5 = (RequestAndReply) ois.readObject();
                ArrayList<Consumer> receiver = requestAndReply5.getConsumers();
//                ArrayList<Consumer> receiver = manager.getConsumerByID(tr.getId_receiver().intValue());
                transactionsArea.appendText(tr.getDate() + " " + sender.get(0).getFirstName() + " " + sender.get(0).getLastName() + " sent to " + receiver.get(0).getFirstName() + " " + receiver.get(0).getLastName() + " " + tr.getBalance() + " tenges.\n");
            }
        }

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
