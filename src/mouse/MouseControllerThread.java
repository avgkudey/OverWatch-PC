package mouse;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MouseControllerThread extends Thread {

    static Socket s;
    static ServerSocket ss;
    static InputStreamReader isr;
    static BufferedReader br;
    static String message;

    @Override
    public void run() {

        try {
            System.err.println("Mouse Started");
            ss = new ServerSocket(connect.ConnectionDetails.serverMouseEventCollector);
            Robot robot = new Robot();
            while (true) {
                s = ss.accept();
                isr = new InputStreamReader(s.getInputStream());
                br = new BufferedReader(isr);
                message = br.readLine();
                System.err.println(message);
                try {
                    if (message.matches("touch up")) {
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                    }
                    if (message.matches("left-press")) {
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                    }
                    if (message.matches("left-release")) {
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                    }
                    if (message.matches("mid-press")) {
                        robot.mousePress(InputEvent.BUTTON2_MASK);
                        robot.mouseRelease(InputEvent.BUTTON2_MASK);
                    }
                    if (message.matches("right-press")) {
                        robot.mousePress(InputEvent.BUTTON3_MASK);
                    }
                    if (message.matches("right-release")) {
                        robot.mouseRelease(InputEvent.BUTTON3_MASK);
                    }

                    if (message.contains(",")) {//  mouse move

                        float movex = Float.parseFloat(message.split(",")[0]);//extract movement in x direction
                        float movey = Float.parseFloat(message.split(",")[1]);//extract movement in y direction
                        Point point = MouseInfo.getPointerInfo().getLocation(); //Get current mouse position
                        float nowx = point.x;
                        float nowy = point.y;
                        robot.mouseMove((int) (nowx + movex), (int) (nowy + movey));//Move mouse pointer to new location
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}
