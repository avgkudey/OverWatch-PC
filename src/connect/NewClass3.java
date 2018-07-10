/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;

/**
 *
 * @author KASUN
 */
public class NewClass3 {
    public static void main(String[] args) throws AWTException {
          Robot d=new Robot();
        Rectangle r=new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        d.createScreenCapture(r);
        System.err.println(r.height);
        System.err.println(r.width);
        
    }
}
