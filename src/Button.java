import java.awt.*;
import java.awt.image.BufferedImage;

public class Button {
    double x;
    double y;
    double Width;
    double Height;
    double MouseX;
    double MouseY;
    String Name;
    String TypeMouse = "No";
    BufferedImage NormImage;
    BufferedImage ClickImage;

    public Button(double x, double y, double Width, double Height, String Name, BufferedImage normImage, BufferedImage clickImage) {
        this.x = x;
        this.y = y;
        this.Width=Width;
        this.Height=Height;
        this.Name=Name;
        NormImage = normImage;
        ClickImage = clickImage;
    }
    public  void  paint(Graphics g ){
        UPSituation();

        if(TypeMouse.equals("no")){
            g.drawImage(NormImage, (int) x, (int) y,(int) Width,(int) Height, null );
//            System.out.println(x+" "+ y);
        }
        if(TypeMouse.equals("yes")){
            g.drawImage(ClickImage, (int) x, (int) y,(int) Width,(int) Height, null );
        }
    }
    public void UPSituation(){
        if(MouseX>x && MouseX<x+Width && MouseY>y && MouseY<y+Height ){
            TypeMouse="yes";
        }else{
            TypeMouse="no";
        }
    }
}
