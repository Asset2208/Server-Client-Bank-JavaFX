package mainClasses;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class DataBase {
    private Connection connection;

//    public void connect() {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_db?useUnicode=true&serverTimezone=UTC", "root", "");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


    public DataBase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_db?useUnicode=true&serverTimezone=UTC", "root", "");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkUsername(String username) throws SQLException {
        boolean checkUser = false;
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM consumers WHERE login =?");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            checkUser = true;
        }
        ps.close();
        rs.close();
        return checkUser;
    }

    public boolean checUserNameStaff(String username) throws SQLException{
        boolean checkStaff = false;
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM staff WHERE login =?");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            checkStaff = true;
        }
        ps.close();
        rs.close();
        return checkStaff;
    }

    public String checkLoginPassword(String username, String password) throws SQLException{
        String checkLogPass = "notFound";
        PreparedStatement ps = connection.prepareStatement("SELECT isactive FROM consumers WHERE login =? AND password =?");
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            int isActive = rs.getInt(1);
            if(isActive == 0){
//                JOptionPane.showMessageDialog(null, "Your account hasn't been activated yet!");
                checkLogPass = "yesNotActivated";
            }
            else {
//                JOptionPane.showMessageDialog(null, "welcome, " + username + "!");
                checkLogPass = "yesActivated";
            }
        }
        ps.close();
        rs.close();
        return checkLogPass;
    }

    public ArrayList<Consumer> getAllConsumers(){
        ArrayList<Consumer> list = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from consumers");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("id");
                String login = rs.getString("login");
                String password = rs.getString("password");
                String firstName = rs.getString("name");
                String lastName = rs.getString("surname");
                String dateOfBirth = rs.getString("date");
                String city = rs.getString("city");
                String telnumber = rs.getString("telnumber");
                String iin = rs.getString("iin");
                String accnumber = rs.getString("accnumber");
                double balance = rs.getDouble("balance");
                int isActive = rs.getInt("isactive");
//                int x = rs.getInt("isactive");
//                System.out.println(x);
                list.add(new Consumer(id, login, password, firstName, lastName, dateOfBirth, city, telnumber, iin, accnumber, balance, isActive));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<Consumer> getNotActivatedConsumer(){
        ArrayList<Consumer> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM consumers WHERE isactive =?");
            ps.setBoolean(1, false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                String login = rs.getString("login");
                String password = rs.getString("password");
                String firstName = rs.getString("name");
                String lastName = rs.getString("surname");
                String dateOfBirth = rs.getString("date");
                String city = rs.getString("city");
                String telnumber = rs.getString("telnumber");
                String iin = rs.getString("iin");
                String accnumber = rs.getString("accnumber");
                double balance = rs.getDouble("balance");
                int isActive = rs.getInt("isactive");
//                int x = rs.getInt("isactive");
//                System.out.println(x);
                list.add(new Consumer(id, login, password, firstName, lastName, dateOfBirth, city, telnumber, iin, accnumber, balance, isActive));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public int getCityCounter(String nameCity){
        int count = 0;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM consumers WHERE city = ?");
            ps.setString(1, nameCity);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                count += 1;
            }
            rs.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return count;
    }

    public ArrayList<Consumer> getActivatedConsumer(){
        ArrayList<Consumer> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM consumers WHERE isactive =?");
            ps.setBoolean(1, true);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                String login = rs.getString("login");
                String password = rs.getString("password");
                String firstName = rs.getString("name");
                String lastName = rs.getString("surname");
                String dateOfBirth = rs.getString("date");
                String city = rs.getString("city");
                String telnumber = rs.getString("telnumber");
                String iin = rs.getString("iin");
                String accnumber = rs.getString("accnumber");
                double balance = rs.getDouble("balance");
                int isActive = rs.getInt("isactive");
//                int x = rs.getInt("isactive");
//                System.out.println(x);
                list.add(new Consumer(id, login, password, firstName, lastName, dateOfBirth, city, telnumber, iin, accnumber, balance, isActive));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Consumer> getConsumerByID(int index){
        ArrayList<Consumer> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM consumers WHERE id =?");
            ps.setLong(1, index);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                String login = rs.getString("login");
                String password = rs.getString("password");
                String firstName = rs.getString("name");
                String lastName = rs.getString("surname");
                String dateOfBirth = rs.getString("date");
                String city = rs.getString("city");
                String telnumber = rs.getString("telnumber");
                String iin = rs.getString("iin");
                String accnumber = rs.getString("accnumber");
                double balance = rs.getDouble("balance");
                int isActive = rs.getInt("isactive");
//                int x = rs.getInt("isactive");
//                System.out.println(x);
                list.add(new Consumer(id, login, password, firstName, lastName, dateOfBirth, city, telnumber, iin, accnumber, balance, isActive));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public boolean isExistConsumerByID(int id){
        boolean isExistConsumer = false;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM consumers WHERE id =?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                isExistConsumer = true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return isExistConsumer;
    }

    public boolean isExistStaffByID(int id){
        boolean isExistStaff = false;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM staff WHERE id =?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                isExistStaff = true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return isExistStaff;
    }

    public boolean isExistConsumerByIIN(String iin){
        boolean isExistConsumer = false;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM consumers WHERE iin =?");
            ps.setString(1, iin);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                isExistConsumer = true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return isExistConsumer;
    }

    public boolean isExistConsumerByPhoneNumber(String phoneNumber){
        boolean isExistConsumer = false;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM consumers WHERE telnumber =?");
            ps.setString(1, phoneNumber);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                isExistConsumer = true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return isExistConsumer;
    }

    public boolean isExistStaffByPhoneNumber(String phoneNumber){
        boolean isExistStaff = false;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM staff WHERE telnumber =?");
            ps.setString(1, phoneNumber);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                isExistStaff = true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return isExistStaff;
    }

    public boolean isExistConsumerByAccNumber(String accNumber){
        boolean isExistConsumer = false;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM consumers WHERE accnumber =?");
            ps.setString(1, accNumber);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                isExistConsumer = true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return isExistConsumer;
    }

    public ArrayList<Consumer> getConsumerByLogin(String loginn){
        ArrayList<Consumer> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM consumers WHERE login =?");
            ps.setString(1, loginn);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                String login = rs.getString("login");
                String password = rs.getString("password");
                String firstName = rs.getString("name");
                String lastName = rs.getString("surname");
                String dateOfBirth = rs.getString("date");
                String city = rs.getString("city");
                String telnumber = rs.getString("telnumber");
                String iin = rs.getString("iin");
                String accnumber = rs.getString("accnumber");
                double balance = rs.getDouble("balance");
                int isActive = rs.getInt("isactive");
//                int x = rs.getInt("isactive");
//                System.out.println(x);
                list.add(new Consumer(id, login, password, firstName, lastName, dateOfBirth, city, telnumber, iin, accnumber, balance, isActive));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Consumer> getConsumerByPhoneNumber(String phoneNum){
        ArrayList<Consumer> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM consumers WHERE telnumber =?");
            ps.setString(1, phoneNum);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                String login = rs.getString("login");
                String password = rs.getString("password");
                String firstName = rs.getString("name");
                String lastName = rs.getString("surname");
                String dateOfBirth = rs.getString("date");
                String city = rs.getString("city");
                String telnumber = rs.getString("telnumber");
                String iin = rs.getString("iin");
                String accnumber = rs.getString("accnumber");
                double balance = rs.getDouble("balance");
                int isActive = rs.getInt("isactive");
//                int x = rs.getInt("isactive");
//                System.out.println(x);
                list.add(new Consumer(id, login, password, firstName, lastName, dateOfBirth, city, telnumber, iin, accnumber, balance, isActive));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Consumer> getConsumerByIIN(String iinNumber){
        ArrayList<Consumer> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM consumers WHERE iin =?");
            ps.setString(1, iinNumber);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                String login = rs.getString("login");
                String password = rs.getString("password");
                String firstName = rs.getString("name");
                String lastName = rs.getString("surname");
                String dateOfBirth = rs.getString("date");
                String city = rs.getString("city");
                String telnumber = rs.getString("telnumber");
                String iin = rs.getString("iin");
                String accnumber = rs.getString("accnumber");
                double balance = rs.getDouble("balance");
                int isActive = rs.getInt("isactive");
//                int x = rs.getInt("isactive");
//                System.out.println(x);
                list.add(new Consumer(id, login, password, firstName, lastName, dateOfBirth, city, telnumber, iin, accnumber, balance, isActive));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Consumer> getConsumerByAccountNumber(String accountNumber){
        ArrayList<Consumer> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM consumers WHERE accnumber =?");
            ps.setString(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                String login = rs.getString("login");
                String password = rs.getString("password");
                String firstName = rs.getString("name");
                String lastName = rs.getString("surname");
                String dateOfBirth = rs.getString("date");
                String city = rs.getString("city");
                String telnumber = rs.getString("telnumber");
                String iin = rs.getString("iin");
                String accnumber = rs.getString("accnumber");
                double balance = rs.getDouble("balance");
                int isActive = rs.getInt("isactive");
//                int x = rs.getInt("isactive");
//                System.out.println(x);
                list.add(new Consumer(id, login, password, firstName, lastName, dateOfBirth, city, telnumber, iin, accnumber, balance, isActive));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public void changePassword(String loginn, String pass){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE consumers SET password =? WHERE login = ?");
            ps.setString(1, pass);
            ps.setString(2, loginn);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void accNumberCreator(String loginn){
        ArrayList<Consumer> list = new ArrayList<>();
        try {
            PreparedStatement pss = connection.prepareStatement("SELECT * FROM consumers WHERE login = ?");
            pss.setString(1, loginn);
            ResultSet rs = pss.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                String login = rs.getString("login");
                String password = rs.getString("password");
                String firstName = rs.getString("name");
                String lastName = rs.getString("surname");
                String dateOfBirth = rs.getString("date");
                String city = rs.getString("city");
                String telnumber = rs.getString("telnumber");
                String iin = rs.getString("iin");
                String accnumber = rs.getString("accnumber");
                double balance = rs.getDouble("balance");
                int isActive = rs.getInt("isactive");
                list.add(new Consumer(id, login, password, firstName, lastName, dateOfBirth, city, telnumber, iin, accnumber, balance, isActive));
            }
            rs.close();
            PreparedStatement ps = connection.prepareStatement("UPDATE consumers SET accnumber =? WHERE login = ?");
            int lastNumbers = list.get(0).getId().intValue();
            System.out.println("Numbers are: " + lastNumbers);
            String last = Integer.toString(1000 + lastNumbers);
            String accNUmber = "516949310000" + last;
            ps.setString(1, accNUmber);
            ps.setString(2, loginn);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void giveBonuses(int idd, double money){
        ArrayList<Consumer> list = new ArrayList<>();
        try {
            PreparedStatement pss = connection.prepareStatement("SELECT * FROM consumers WHERE id = ?");
            pss.setLong(1, idd);
            ResultSet rs = pss.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                String login = rs.getString("login");
                String password = rs.getString("password");
                String firstName = rs.getString("name");
                String lastName = rs.getString("surname");
                String dateOfBirth = rs.getString("date");
                String city = rs.getString("city");
                String telnumber = rs.getString("telnumber");
                String iin = rs.getString("iin");
                String accnumber = rs.getString("accnumber");
                double balance = rs.getDouble("balance");
                int isActive = rs.getInt("isactive");
                list.add(new Consumer(id, login, password, firstName, lastName, dateOfBirth, city, telnumber, iin, accnumber, balance, isActive));
            }
            rs.close();
            PreparedStatement ps = connection.prepareStatement("UPDATE consumers SET balance =? WHERE id = ?");
            double value = money + list.get(0).getBalance();
            ps.setDouble(1, value);
            ps.setLong(2, idd);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void withdrawMoney(String loginn, double money){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE consumers SET balance =? WHERE login = ?");
            ps.setDouble(1, money);
            ps.setString(2, loginn);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void addMoney(String loginn, double money){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE consumers SET balance =? WHERE login = ?");
            ps.setDouble(1, money);
            ps.setString(2, loginn);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void sendMoneyOperation(Transactions transactions){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO transactions (id, id_sender, id_receiver, money, date, isWithdrawal, isAddition) VALUES (NULL, ?, ?, ?, ?, ?, ?)");
            ps.setLong(1, transactions.getId_sender());
            ps.setLong(2, transactions.getId_receiver());
            ps.setDouble(3, transactions.getBalance());
            ps.setString(4, transactions.getDate());
            ps.setInt(5, transactions.getIsWithdrawal());
            ps.setInt(6, transactions.getIsAddition());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<Transactions> getWithdrawalTransactions(int idd){
        ArrayList<Transactions> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM transactions WHERE id_sender =? AND isWithdrawal =?");
            ps.setLong(1, idd);
            ps.setInt(2, 1);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                Long idSender = rs.getLong("id_sender");
                Long idReceiver = rs.getLong("id_receiver");
                double balance = rs.getDouble("money");
                String date = rs.getString("date");
                int isWithdrawal = rs.getInt("isWithdrawal");
                int isAddition = rs.getInt("isAddition");
                list.add(new Transactions(id, idSender, idReceiver, balance, date, isWithdrawal, isAddition));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Transactions> getAdditionTransactions(int idd){
        ArrayList<Transactions> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM transactions WHERE id_sender =? AND isAddition =?");
            ps.setLong(1, idd);
            ps.setInt(2, 1);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                Long idSender = rs.getLong("id_sender");
                Long idReceiver = rs.getLong("id_receiver");
                double balance = rs.getDouble("money");
                String date = rs.getString("date");
                int isWithdrawal = rs.getInt("isWithdrawal");
                int isAddition = rs.getInt("isAddition");
                list.add(new Transactions(id, idSender, idReceiver, balance, date, isWithdrawal, isAddition));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Transactions> getAllTransactions(){
        ArrayList<Transactions> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM transactions");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                Long idSender = rs.getLong("id_sender");
                Long idReceiver = rs.getLong("id_receiver");
                double balance = rs.getDouble("money");
                String date = rs.getString("date");
                int isWithdrawal = rs.getInt("isWithdrawal");
                int isAddition = rs.getInt("isAddition");
                list.add(new Transactions(id, idSender, idReceiver, balance, date, isWithdrawal, isAddition));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public void activateUser(int id){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE consumers SET isactive =? WHERE id = ?");
            ps.setBoolean(1, true);
            ps.setLong(2, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void addConsumer(Consumer consumer) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO consumers (id, login, password, name, surname, date, city, telnumber, iin, accnumber, balance, isactive) VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, consumer.getLogin());
            ps.setString(2, consumer.getPassword());
            ps.setString(3, consumer.getFirstName());
            ps.setString(4, consumer.getLastName());
            ps.setString(5, consumer.getDateOfBirth());
            ps.setString(6, consumer.getCity());
            ps.setString(7, consumer.getTelNumber());
            ps.setString(8, consumer.getIin());
            ps.setString(9, consumer.getAccNumber());
            ps.setDouble(10, consumer.getBalance());
            ps.setBoolean(11, false);

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addStaff(Staff staff){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO staff (id, login, password, name, surname, date, city, telnumber, iin, position, salary) VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, staff.getLogin());
            ps.setString(2, staff.getPassword());
            ps.setString(3, staff.getFirstName());
            ps.setString(4, staff.getLastName());
            ps.setString(5, staff.getDateOfBirth());
            ps.setString(6, staff.getCity());
            ps.setString(7, staff.getTelNumber());
            ps.setString(8, staff.getIin());
            ps.setString(9, staff.getPosition());
            ps.setDouble(10, staff.getSalary());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}