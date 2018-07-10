package connect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class NewClass {

    public static void main(String[] args) {
        ServerSocket ss;
        Socket s;
        BufferedReader br;
        InputStreamReader isr;
        String message;
        try {
            ss = new ServerSocket(connect.ConnectionDetails.serverDetailCollector);
            while (true) {
                s = ss.accept();
                connect.ConnectionDetails.androidIP = (((InetSocketAddress) s.getRemoteSocketAddress())
                        .getAddress()).toString().replace("/", "");
                isr = new InputStreamReader(s.getInputStream());
                br = new BufferedReader(isr);
                message = br.readLine();
                System.out.println(message);
            }
        } catch (Exception e) {
            System.err.println(e);
        }

//        String ip;
//        try (final DatagramSocket socket = new DatagramSocket()) {
//            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
//            ip = socket.getLocalAddress().getHostAddress();
//            System.err.println(ip);
//            String kk = ip.toString();
//            ByteArrayOutputStream out = QRCode.from(kk).to(ImageType.JPG).stream();
//            File f = new File("C:\\Users\\Public\\qr.jpg");
//            if (f.exists()) {
//                System.out.println("File exists");
//            } else {
//                f.createNewFile(); // if the file does not exist, create it
//                System.out.println("Created non-existing file");
//            }
//            if (f.canWrite()) {
//                System.out.println("File can be written to");
//            }
//
//            FileOutputStream fos = new FileOutputStream(f);
//            fos.write(out.toByteArray());
//            fos.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
