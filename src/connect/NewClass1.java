/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import static jdk.nashorn.internal.objects.NativeError.printStackTrace;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 *
 * @author KASUN
 */
public class NewClass1 {

    public NewClass1() {
        
        String ip;
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip = socket.getLocalAddress().getHostAddress();
            System.err.println(ip);
//            qrgenarator.QRGen.qrgen(ip);
            
            ByteArrayOutputStream out = QRCode.from(ip).to(ImageType.JPG).stream();
            File f = new File("‪d:\\qr.jpg");
            if (f.exists()) {
                System.out.println("File exists");
            } else {
                f.createNewFile(); // if the file does not exist, create it
                System.out.println("Created non-existing file");
            }
            if (f.canWrite()) {
                System.out.println("File can be written to");
            }

            FileOutputStream fos = new FileOutputStream(f);
            fos.write(out.toByteArray());
            fos.flush();
        } catch (Exception e) {
            System.err.println(e);
//            printStackTrace(e);
        }
    }
    
}
