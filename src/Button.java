import java.awt.*;
import java.awt.image.BufferedImage;

public class Button {
    double x;
    double y;
    double MouseX;
    double MouseY;
    String Name;
    String TypeMouse = "No";
    BufferedImage NormImage;
    BufferedImage ClickImage;

    public Button(double x, double y, String Name, BufferedImage normImage, BufferedImage clickImage) {
        this.x = x;
        this.y = y;
        this.Name=Name;
        NormImage = normImage;
        ClickImage = clickImage;
    }
    public  void  paint(Graphics g ){
        if(TypeMouse.equals("No")){
            g.drawImage(NormImage, (int) x, (int) y, 80, 80, null );
//            System.out.println(x+" "+ y);
        }
    }
}
