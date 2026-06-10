
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

import javax.imageio.ImageIO;

public class TomarCapturaTiempo {

    private static boolean run = true;

    public static void main(String[] args) {
        
        LocalTime horaDetener = LocalTime.of(11,05);
        
        try {
            while (run) {
                Thread.sleep(500);
                if(LocalTime.now().isAfter(horaDetener)) {
                    tomarCaptura();
                    break; 
                }
                
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        
    }
    
    private static void tomarCaptura() {

        try {
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle screenRectangle = new Rectangle(screenSize);
            Robot robot;
            robot = new Robot();
            BufferedImage image = robot.createScreenCapture(screenRectangle);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", new File(df.format(new Date())+".jpg"));//Escribe en ruta local
            baos.flush();
            baos.close();
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}