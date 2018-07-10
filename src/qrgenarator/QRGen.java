package qrgenarator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class QRGen {

    public static void qrgen(String text) {
        try {
            String txt = text;
            ByteArrayOutputStream out = QRCode.from(txt).to(ImageType.JPG).stream();
            File f = new File("‪C:\\Users\\Public\\qr.jpg");
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
        }
    }
}
