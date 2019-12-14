package mainClasses.Requests;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Server {
    private ServerSocket serverSocket = null;
    private Connection connection = null;

    public Server() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_db?useUnicode=true&serverTimezone=UTC", "root", "");
        }
        catch (SQLException | ClassNotFoundException e ) {
            e.printStackTrace();
        }
        try {
            serverSocket = new ServerSocket(12345);
            System.out.println("Server is run!");
        }
        catch (IOException e ) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {
            while (true) {
                System.out.println("Waiting for a client!");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress().getHostAddress());
                ClientHandler clientHandler = new ClientHandler(socket, connection);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.listen();
    }
}
