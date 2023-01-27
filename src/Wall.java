import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;

public class Wall {
    int x;                        // вроде не центрированы
    int y;
    int width;
    int height;
    int UnitX;
    int UnitY;
    int SizeBoxX=50;
    int SizeBoxY=10;
    double BoxHP;
    Rectangle2D rectangle = new Rectangle2D.Double();
    BufferedImage image;


    public Wall(int x, int y, int width, int height, BufferedImage image, int BoxHP) throws IOException {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        rectangle.setRect(x, y, width, height);
//        UpdateHeightAndWidth();
        this.image = image;
        this.BoxHP=BoxHP;
    }

//    public void UpdateHeightAndWidth(){
//        UnitX=width/ SizeBoxX;
//        UnitY=height/ SizeBoxY;
//        width =UnitX * SizeBoxX;
//        height=UnitY * SizeBoxY;
//    }

    public void paint(Graphics g) {
//        for(int a=0; a<UnitY; a++){                    // a это по игрику  b это по иксу
//            for(int b=0; b<UnitX; b++){
//                g.drawImage(image, x+SizeBox*b, y+SizeBox*a,x+SizeBox*(b+1), y+SizeBox*(a+1),0,0, image.getWidth(), image.getHeight(), null);
//            }
//        }
        g.drawImage(image, x, y, null);
    }

}
