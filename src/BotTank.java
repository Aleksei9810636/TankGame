import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BotTank extends GameObject {
    double x;
    double y;
    double VAngle;
    double vy;
    double VMax;
    double a;
    double angle = 90;
    double HitPoints;
    double HitPointsMax;
    double RechargeTime;
    long LastShotTime = 0;
    BufferedImage image;
    boolean typeOfEventW;
    boolean typeOfEventA;
    boolean typeOfEventS;
    boolean typeOfEventD;

    public BotTank(double x, double y, double VMax, double a, double hitPointsMax, double rechargeTime, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.VMax = VMax;
        this.a = a;
        HitPoints = hitPointsMax;
        HitPointsMax = hitPointsMax;
        RechargeTime = rechargeTime;
        this.image = image;
    }
    public void paint(Graphics g) {
        BufferedImage img = rotateImage(image, angle);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, (int) (x - img.getWidth() * 0.5), (int) (y - img.getHeight() * 0.5), null);
    }
    public void UpdatePlace() {
        x+=0.1;
    }


    }
