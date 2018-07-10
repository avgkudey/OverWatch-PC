package livescreen;

import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.imageio.ImageIO;

public class SendLiveScreenThread extends Thread {

    public void run() {
        try {
            System.out.println("Screen Send Started");
            Socket socket = new Socket(connect.ConnectionDetails.androidIP, connect.ConnectionDetails.RemoteDesktopScreenSendPort);
            while (true) {
                BufferedImage screenshot = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                screenshot.getHeight();
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(resize(screenshot, 400, 300), "jpeg", os);
                InputStream fis = new ByteArrayInputStream(os.toByteArray());
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(buffer);
                oos.flush();
                oos.close();
//                socket.close();
//                fis.close();
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            run();
        }
    }

    public BufferedImage resize(BufferedImage img, int newW, int newH) throws AWTException {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_DEFAULT);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.SCALE_DEFAULT);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dimg;
    }
}
