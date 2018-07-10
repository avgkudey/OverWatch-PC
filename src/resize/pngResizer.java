/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resize;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class pngResizer {

    public static BufferedImage convertRGBAToIndexed(BufferedImage src) {
        BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
        Graphics g = dest.getGraphics();
        g.setColor(new Color(231, 20, 189));
        g.fillRect(0, 0, dest.getWidth(), dest.getHeight());
        dest = makeTransparent(dest, 0, 0);
        dest.createGraphics().drawImage(src, 0, 0, null);
        return dest;
    }

    public static BufferedImage makeTransparent(BufferedImage image, int x, int y) {
        ColorModel cm = image.getColorModel();
        if (!(cm instanceof IndexColorModel)) {
            return image; // sorry...
        }
        IndexColorModel icm = (IndexColorModel) cm;
        WritableRaster raster = image.getRaster();
        int pixel = raster.getSample(x, y, 0);
        int size = icm.getMapSize();
        byte[] reds = new byte[size];
        byte[] greens = new byte[size];
        byte[] blues = new byte[size];
        icm.getReds(reds);
        icm.getGreens(greens);
        icm.getBlues(blues);
        IndexColorModel icm2 = new IndexColorModel(8, size, reds, greens, blues, pixel);
        return new BufferedImage(icm2, raster, image.isAlphaPremultiplied(), null);
    }

    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {
        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = 0;
        int new_height = 0;

        if (original_width > original_height) {
            new_width = bound_width;
            new_height = (new_width * original_height) / original_width;
        } else {
            new_height = bound_height;
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }

    public static void resizeImage(File original_image, File resized_image, int IMG_SIZE) throws IOException {

        BufferedImage originalImage = ImageIO.read(original_image);

        String extension = "png";

        int type = extension.equals("gif") || (originalImage.getType() == 0) ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

        Dimension new_dim = getScaledDimension(new Dimension(10, 10), new Dimension(10, 10));

        BufferedImage resizedImage = new BufferedImage((int) new_dim.getWidth(), (int) new_dim.getHeight(), type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, (int) new_dim.getWidth(), (int) new_dim.getHeight(), null);
        g.dispose();

        if (!extension.equals("gif")) {
            ImageIO.write(resizedImage, extension, resized_image);
        } else {
            // Gif Transparence workarround
            ImageIO.write(convertRGBAToIndexed(resizedImage), "gif", resized_image);
        }

    }

    public static void main(String[] args) {
        File input = null;
        File output = null;
        try {

            int xReductionFactor = 10;//  224
            try {
                input = new File("‪D:\\Screenshot (4).png");
                output = new File("‪D:\\Screenshot (4).png");
            } catch (Exception e) {
                System.err.println(e+"sa");
            }
            for (File f : input.listFiles()) {
                if (!f.getName().endsWith("png")) {
                    continue;
                }
                resizeImage(f, new File(output + "\\" + f.getName()), xReductionFactor);
            }
            System.out.println(xReductionFactor + "px finished.");
        } catch (Exception e) {
            System.err.println(e.getMessage());

        }
    }
}
