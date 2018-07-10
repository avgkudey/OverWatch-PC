package detailsCollector;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import overwatchpc.ConnectionController;

public class Collector extends Thread {

    ServerSocket ss;
    Socket s;
    BufferedReader br;
    InputStreamReader isr;
    String message;

    @Override
    public void run() {
        try {
            Robot robot = new Robot();
            System.err.println("Det Collector Started");
            ss = new ServerSocket(connect.ConnectionDetails.serverDetailCollector);
            while (true) {
                s = ss.accept();
                connect.ConnectionDetails.androidIP = (((InetSocketAddress) s.getRemoteSocketAddress())
                        .getAddress()).toString().replace("/", "");
                isr = new InputStreamReader(s.getInputStream());
                br = new BufferedReader(isr);
                message = br.readLine();
                System.out.println(message);

                if (message.contains("authorize")) {
                    ConnectionController.permissionStates.setState(1);
                }

                if (message.matches("SHUTDOWN")) {
                    Runtime runtime = Runtime.getRuntime();
                    try {
                        Process proc = runtime.exec("Shutdown -s -t 0");
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                }

                if (message.matches("REBOOT")) {
                    Runtime runtime = Runtime.getRuntime();
                    try {
                        Process proc = runtime.exec("Shutdown -r -t 0");
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                }
                if (message.matches("HIBERNATE")) {
                    Runtime runtime = Runtime.getRuntime();
                    try {
                        Process proc = runtime.exec("%windir%\\System32\\rundll32.exe powrprof.dll,SetSuspendState 0,1,0");
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                }

                if (message.matches("LOCK")) {
                    Runtime runtime = Runtime.getRuntime();
                    try {
                        Process proc = runtime.exec("rundll32.exe user32.dll,LockWorkStation");
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                }

                if (message.matches("SIGNOUT")) {
                    Runtime runtime = Runtime.getRuntime();
                    try {
                        Process proc = runtime.exec("Shutdown -l -f");
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                }

                if (message.matches("medianext")) {
                    GlobalScreen.postNativeEvent(new NativeKeyEvent(2401, 0,
                            176, 57369, org.jnativehook.keyboard.NativeKeyEvent.CHAR_UNDEFINED));
                }
                if (message.matches("mediaplay")) {
                    GlobalScreen.postNativeEvent(new NativeKeyEvent(2401, 0,
                            176, 57378, org.jnativehook.keyboard.NativeKeyEvent.CHAR_UNDEFINED));
                }
                if (message.matches("mediaback")) {
                    GlobalScreen.postNativeEvent(new NativeKeyEvent(2401, 0,
                            176, 57360, org.jnativehook.keyboard.NativeKeyEvent.CHAR_UNDEFINED));
                }

                if (message.matches("mediastop")) {
                    GlobalScreen.postNativeEvent(new NativeKeyEvent(2401, 0,
                            176, 57380, org.jnativehook.keyboard.NativeKeyEvent.CHAR_UNDEFINED));
                }

                if (message.matches("mediamute")) {
                    GlobalScreen.postNativeEvent(new NativeKeyEvent(2401, 0,
                            176, 57376, org.jnativehook.keyboard.NativeKeyEvent.CHAR_UNDEFINED));
                }
                ///////////////////////////////////////////////////////////////

                if (message.matches("playervluppress")) {
                    robot.keyPress(KeyEvent.VK_UP);
                }
                if (message.matches("playervldownpress")) {
                    robot.keyPress(KeyEvent.VK_DOWN);
                }
                if (message.matches("playervlupres")) {
                    robot.keyRelease(KeyEvent.VK_UP);
                }
                if (message.matches("playervldownres")) {
                    robot.keyRelease(KeyEvent.VK_DOWN);
                }

                //////////////////////////////
                if (message.matches("skbackpress")) {
                    robot.keyPress(KeyEvent.VK_LEFT);
                }
                if (message.matches("sknextpress")) {
                    robot.keyPress(KeyEvent.VK_RIGHT);
                }

                if (message.matches("skbackres")) {
                    robot.keyRelease(KeyEvent.VK_LEFT);
                }
                if (message.matches("sknextres")) {
                    robot.keyRelease(KeyEvent.VK_RIGHT);
                }

                /////////////////////////////
                if (message.matches("playerplay")) {
                    robot.keyPress(KeyEvent.VK_SPACE);
                    robot.keyRelease(KeyEvent.VK_SPACE);
                }
                if (message.matches("playerback")) {
                    robot.keyPress(KeyEvent.VK_PAGE_UP);
                    robot.keyRelease(KeyEvent.VK_PAGE_UP);
                }
                if (message.matches("playernext")) {
                    robot.keyPress(KeyEvent.VK_PAGE_DOWN);
                    robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
                }

                //////////////////////////////////////////////////joystick 1
                if (message.matches("apress")) {
                    robot.keyPress(KeyEvent.VK_A);
                    
                }
                if (message.matches("ares")) {
                    robot.keyRelease(KeyEvent.VK_A);
                }
                if (message.matches("dpress")) {
                    robot.keyPress(KeyEvent.VK_D);
                }
                if (message.matches("dres")) {
                    robot.keyRelease(KeyEvent.VK_D);
                }
                if (message.matches("wpress")) {
                    robot.keyPress(KeyEvent.VK_W);
                }
                if (message.matches("wres")) {
                    robot.keyRelease(KeyEvent.VK_W);
                }
                if (message.matches("spress")) {
                    robot.keyPress(KeyEvent.VK_S);
                }
                if (message.matches("sres")) {
                    robot.keyRelease(KeyEvent.VK_S);
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
