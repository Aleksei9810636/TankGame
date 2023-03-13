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
    int SizeBoxX=75;
    int SizeBoxY=75;
    double BoxHP;
    Rectangle2D rectangle = new Rectangle2D.Double();
    BufferedImage image;


    public Wall(int x, int y, int width, int height, BufferedImage image, int BoxHP) throws IOException {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        rectangle.setRect(x, y, width, height);
        this.image = image;
        this.BoxHP=BoxHP;
    }

    public void paint(Graphics g) {
        g.drawImage(image, x, y, x+SizeBoxX, y+SizeBoxY,0,0, image.getWidth(), image.getHeight(),null);
    }

}
