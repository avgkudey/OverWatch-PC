package detailSender;

import connect.ConnectionDetails;
import java.io.PrintWriter;
import java.net.Socket;

public class DetSender {

    public static void sender(String text) {
        try {
            Socket s = new Socket(ConnectionDetails.androidIP, 6005);
            PrintWriter writer = new PrintWriter(s.getOutputStream());
            writer.write(text);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
