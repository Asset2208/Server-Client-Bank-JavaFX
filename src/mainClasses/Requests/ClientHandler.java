package mainClasses.Requests;

import mainClasses.DataBase;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

public class ClientHandler extends Thread {
    private Socket socket = null;
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;
    private Connection connection = null;

    public ClientHandler(Socket socket, Connection connection) {
        this.socket = socket;
        this.connection = connection;

        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        DataBase db = new DataBase();

        try {
            while (true){
                RequestAndReply requestUser = (RequestAndReply) ois.readObject();
                System.out.println(requestUser);

                // здесь будет код
                if(requestUser.getCode().equals("ADD_CONSUMER_REQUEST")){
                    db.addConsumer(requestUser.getConsumer());
                    oos.writeObject(new RequestAndReply("SUCCESS_ADDING"));
                }
                else if(requestUser.getCode().equals("VIEW_CONSUMERS_REPLY")){
                    RequestAndReply requestAndReply2 = new RequestAndReply("VIEW_CONSUMERS_REPLY");
                    requestAndReply2.setConsumers(db.getAllConsumers());
                    oos.writeObject(requestAndReply2);
                }
                else if(requestUser.getCode().equals("CHECK_CONSUMERS_REPLY")){
                    if(db.isExistConsumerByID(requestUser.getId2())){
                        RequestAndReply requestAndReply2 = new RequestAndReply("CHECK_CONSUMERS_REPLY");
                        requestAndReply2.setExist(true);
                        oos.writeObject(requestAndReply2);
                    }
                    else {
                        oos.writeObject(new RequestAndReply("NO SUCH CONSUMER"));
                        requestUser.setExist(false);
                    }
                }
                else if(requestUser.getCode().equals("GET_CONSUMER_BY_ID")){
                    RequestAndReply requestAndReply2 = new RequestAndReply("GET_CONSUMER");
                    requestAndReply2.setConsumers(db.getConsumerByID(requestUser.getId2()));
                    oos.writeObject(requestAndReply2);
                }
                else if(requestUser.getCode().equals("VIEW_NOT_ACTIVATED_CONSUMERS_REPLY")){
                    RequestAndReply requestAndReply2 = new RequestAndReply("GET_NOT_ACTIVATED_CONSUMER");
                    requestAndReply2.setConsumers(db.getNotActivatedConsumer());
                    oos.writeObject(requestAndReply2);
                }
                else if (requestUser.getCode().equals("ACTIVATE_CONSUMERS")){
                    db.activateUser(requestUser.getId2());
                    oos.writeObject(new RequestAndReply("SUCCESS_ACTIVATION"));
                }
                else if (requestUser.getCode().equals("GET_CONSUMER_BY_LOGIN")){
                    RequestAndReply requestAndReply2 = new RequestAndReply("SUCCESS_GET_CONSUMER_BY_LOGIN");
                    requestAndReply2.setConsumers(db.getConsumerByLogin(requestUser.getText()));
                    oos.writeObject(requestAndReply2);
                }
                else if (requestUser.getCode().equals("ADD_MONEY")){
//                    RequestAndReply requestAndReply2 = new RequestAndReply("ADD_MONEY");
                    db.addMoney(requestUser.getText(), requestUser.getAmount());
                    oos.writeObject(new RequestAndReply("SUCCESS_ADD_MONEY"));
                }
                else if (requestUser.getCode().equals("SEND_MONEY_OPERATION")){
                    db.sendMoneyOperation(requestUser.getTransaction());
                    oos.writeObject(new RequestAndReply("SUCCESS_SEND_TRANSACTION"));
                }
                else if (requestUser.getCode().equals("CHANGE_PASSWORD")){
                    db.changePassword(requestUser.getText(), requestUser.getText_2());
                    oos.writeObject(new RequestAndReply("SUCCESS_CHANGE_PASSWORD"));
                }
                else if(requestUser.getCode().equals("VIEW_ACTIVATED_CONSUMERS_REPLY")){
                    RequestAndReply requestAndReply2 = new RequestAndReply("GET_NOT_ACTIVATED_CONSUMER");
                    requestAndReply2.setConsumers(db.getActivatedConsumer());
                    oos.writeObject(requestAndReply2);
                }
                else if (requestUser.getCode().equals("GIVE_BONUSES")){
                    db.giveBonuses(requestUser.getId2(), requestUser.getAmount());
                    oos.writeObject(new RequestAndReply("SUCCESS_GIVE_BONUSES"));
                }
                else if (requestUser.getCode().equals("WITHDRAW_MONEY")){
                    db.withdrawMoney(requestUser.getText(), requestUser.getAmount());
                    oos.writeObject(new RequestAndReply("SUCCESS_WITHDRAW_MONEY"));
                }
                else if (requestUser.getCode().equals("GET_ALL_TRANSACTIONS")){
                    RequestAndReply requestAndReply2 = new RequestAndReply("GET_ALL_TRANSACTIONS");
                    requestAndReply2.setTransactions(db.getAllTransactions());
                    oos.writeObject(requestAndReply2);
                }
                else if (requestUser.getCode().equals("CHECK_LOGIN_PASSWORD")){
                    RequestAndReply requestAndReply2 = new RequestAndReply("CHECK_LOGIN_PASSWORD_ANSWER");
                    requestAndReply2.setText(db.checkLoginPassword(requestUser.getText(), requestUser.getText_2()));
                    oos.writeObject(requestAndReply2);
                }
                else if (requestUser.getCode().equals("ACC_NUMBER_CREATOR")){
                    db.accNumberCreator(requestUser.getText());
                    oos.writeObject(new RequestAndReply("SUCCESS_ACCOUNT_NUMBER_CREATION"));
                }
                else if (requestUser.getCode().equals("GET_CONSUMERS_BY_ACC_NUMBER")){
                    RequestAndReply requestAndReply2 = new RequestAndReply("SUCCESS_GET_CONSUMER_BY_ACC_NUMBER");
                    requestAndReply2.setConsumers(db.getConsumerByAccountNumber(requestUser.getText()));
                    oos.writeObject(requestAndReply2);
                }
                else if (requestUser.getCode().equals("GET_CONSUMERS_BY_IIN")){
                    RequestAndReply requestAndReply2 = new RequestAndReply("SUCCESS_GET_CONSUMER_BY_IIN");
                    requestAndReply2.setConsumers(db.getConsumerByIIN(requestUser.getText()));
                    oos.writeObject(requestAndReply2);
                }
                else if (requestUser.getCode().equals("GET_CONSUMERS_BY_PHONE")){
                    RequestAndReply requestAndReply2 = new RequestAndReply("SUCCESS_GET_CONSUMER_BY_PHONE");
                    requestAndReply2.setConsumers(db.getConsumerByPhoneNumber(requestUser.getText()));
                    oos.writeObject(requestAndReply2);
                }
                else if (requestUser.getCode().equals("IS_EXIST_CONSUMER_BY_ACCNUM")){
                    RequestAndReply requestAndReply2 = new RequestAndReply("IS_EXIST_CONSUMER_BY_ACCNUM_ANSWER");
                    requestAndReply2.setExist(db.isExistConsumerByAccNumber(requestUser.getText()));
                    oos.writeObject(requestAndReply2);
                }
                else if (requestUser.getCode().equals("IS_EXIST_CONSUMER_BY_LOGIN")){
                    RequestAndReply requestAndReply2 = new RequestAndReply("IS_EXIST_CONSUMER_BY_LOGIN_ANSWER");
                    requestAndReply2.setExist(db.checkUsername(requestUser.getText()));
                    oos.writeObject(requestAndReply2);
                }
                else if (requestUser.getCode().equals("IS_EXIST_CONSUMER_BY_PHONE")){
                    RequestAndReply requestAndReply2 = new RequestAndReply("IS_EXIST_CONSUMER_BY_PHONE_ANSWER");
                    requestAndReply2.setExist(db.isExistConsumerByPhoneNumber(requestUser.getText()));
                    oos.writeObject(requestAndReply2);
                }
                else if (requestUser.getCode().equals("IS_EXIST_CONSUMER_BY_IIN")){
                    RequestAndReply requestAndReply2 = new RequestAndReply("IS_EXIST_CONSUMER_BY_IIN_ANSWER");
                    requestAndReply2.setExist(db.isExistConsumerByIIN(requestUser.getText()));
                    oos.writeObject(requestAndReply2);
                }
                else if (requestUser.getCode().equals("ADD_STAFF_REQUEST")){
                    db.addStaff(requestUser.getStaff());
                    oos.writeObject(new RequestAndReply("SUCCESS_ADDING"));
                }
                else if (requestUser.getCode().equals("IS_EXIST_STAFF_BY_LOGIN")){
                    RequestAndReply requestAndReply2 = new RequestAndReply("IS_EXIST_STAFF_BY_LOGIN_ANSWER");
                    requestAndReply2.setExist(db.checUserNameStaff(requestUser.getText()));
                    oos.writeObject(requestAndReply2);
                }
                else if (requestUser.getCode().equals("IS_EXIST_STAFF_BY_PHONE")){
                    RequestAndReply requestAndReply2 = new RequestAndReply("IS_EXIST_STAFF_BY_PHONE_ANSWER");
                    requestAndReply2.setExist(db.isExistStaffByPhoneNumber(requestUser.getText()));
                    oos.writeObject(requestAndReply2);
                }
                else if (requestUser.getCode().equals("GET_CITY_COUNTER")){
                    RequestAndReply requestAndReply2 = new RequestAndReply("GET_CITY_COUNTER");
                    requestAndReply2.setId2(db.getCityCounter(requestUser.getText()));
                    oos.writeObject(requestAndReply2);
                }
//                else if (requestUser.getCode().equals("GET_STAFF")){
//                    RequestAndReply requestAndReply2 = new RequestAndReply("GET_STAFF");
//                    requestAndReply2.setStaffs(db.getCityCounter(requestUser.getText()));
//                    oos.writeObject(requestAndReply2);
//                }
            }
        }
        catch (ClassNotFoundException | IOException | SQLException e){
            e.printStackTrace();
        }
    }
}
