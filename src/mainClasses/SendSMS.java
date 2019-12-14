package mainClasses;

import com.telesign.MessagingClient;
import com.telesign.RestClient;

public class SendSMS {

    public static void SendSMSManager(String name, String address, String contact) {

        String customerId = "F56A7FFF-E83C-4CBB-98AC-6E51C9259398";
        String apiKey = "k+ZAZJOMNeTPeymjM/yq7ybBaMPvzyvoM+VI7R8ma+5KB0dCUMxUh6Hc5SdkGgIXsXN+zviXmOznhuIrpolVRw==";
        String phoneNumber = "77071880414";
        String message = "Имя: " + name + "\n" + "Адресс: " + address + "\n" + "Контакт: " + address;
        String messageType = "ARN";

        try {
            MessagingClient messagingClient = new MessagingClient(customerId, apiKey);
            RestClient.TelesignResponse telesignResponse = messagingClient.message(phoneNumber, message, messageType, null);
            System.out.println("Your reference id is: "+ "35BADF1E5DAC0D689190648F0B898FF4");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}