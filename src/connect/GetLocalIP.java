package connect;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class GetLocalIP extends Thread {

    public void run() {

        String ip;
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip = socket.getLocalAddress().getHostAddress();
            connect.ConnectionDetails.serverIP = ip;
            Robot r = new Robot();
            BufferedImage screenshot = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ConnectionDetails.screenWidth = screenshot.getWidth();
            ConnectionDetails.screenheight = screenshot.getHeight();
            System.err.println(connect.ConnectionDetails.serverIP);
            String qrDet = ip.toString() + "," + connect.ConnectionDetails.serverDetailCollector + ","
                    + connect.ConnectionDetails.serverMouseEventCollector + ","
                    + connect.ConnectionDetails.serverJoystickPort + ","
                    + connect.ConnectionDetails.serverRDMouseEventReceive + ","
                    + connect.ConnectionDetails.screenheight + ","
                    + connect.ConnectionDetails.screenWidth;
            ByteArrayOutputStream out = QRCode.from(qrDet).to(ImageType.JPG).stream();
            File f = new File("C:\\Users\\Public\\qr.jpg");
            if (f.exists()) {
                System.out.println("File exists");
            } else {
                f.createNewFile(); // if the file does not exist, create it
                System.out.println("Created non-existing file");
            }
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(out.toByteArray());
            fos.flush();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
