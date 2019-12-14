package Controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import mainClasses.Consumer;
import mainClasses.DataBase;
import mainClasses.Requests.RequestAndReply;

public class CityStatisticsController {
//    static DataBase manager = new DataBase();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private PieChart pieChart;

    @FXML
    void initialize() throws IOException, ClassNotFoundException {
//        manager.connect();
        Socket socket = new Socket("localhost", 12345);
        String[] cities = {"Almaty", "Astana", "Shymkent", "Taraz", "Aktau", "Karagandy", "Pavlodar", "Oskemen", "Semey", "Atyrau", "Kostanay", "Oral"};
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        int[] city = new int[12];
        for (int i = 0; i < 12; i++){
            RequestAndReply request = new RequestAndReply("GET_CITY_COUNTER", cities[i]);
            oos.writeObject(request);
            RequestAndReply requestAndReply = (RequestAndReply) ois.readObject();
            city[i] = requestAndReply.getId2();
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Almaty", city[0]),
                new PieChart.Data("Astana", city[1]),
                new PieChart.Data("Shymkent", city[2]),
                new PieChart.Data("Taraz", city[3]),
                new PieChart.Data("Aktobe", city[4]),
                new PieChart.Data("Karagandy", city[5]),
                new PieChart.Data("Pavlodar", city[6]),
                new PieChart.Data("Oskemen", city[7]),
                new PieChart.Data("Semey", city[8]),
                new PieChart.Data("Atyrau", city[9]),
                new PieChart.Data("Kostanay", city[10]),
                new PieChart.Data("Oral", city[11])
        );
        pieChart.setData(pieChartData);

        backBtn.setOnAction(actionEvent -> {
            backBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmlFiles/statistics.fxml"));
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
