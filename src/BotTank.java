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
    Tank tank1;
    Tank tank2;
    BotGun botGun;

    public BotTank(double x, double y, double VMax, double a, double hitPointsMax,
                   double rechargeTime, BufferedImage image, Tank tank1, Tank tank2) throws IOException {
        this.x = x;
        this.y = y;
        this.VMax = VMax;
        this.a = a;
        HitPoints = hitPointsMax;
        HitPointsMax = hitPointsMax;
        RechargeTime = rechargeTime;
        this.image = image;
        this.tank1=tank1;
        this.tank2=tank2;
        botGun=new BotGun(0.2,tank1,tank2);

    }

    public void paint(Graphics g) {
        BufferedImage img = rotateImage(image, angle);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, (int) (x - img.getWidth() * 0.5), (int) (y - img.getHeight() * 0.5), null);
        botGun.paint(g,x,y);
        botGun.UpdatePlace();
        g.drawString(Integer.toString((int)HitPoints),(int) x,(int) y-40);
    }
    public void UPPlace(){
        typeOfEventW=true;
    }

    public void UpdatePlace() {
        UPPlace();
        double angleInRadians = Math.toRadians(angle);
        x += vy * Math.sin(angleInRadians);
        y -= vy * Math.cos(angleInRadians);
        angle += VAngle * 1;
        if (typeOfEventW != typeOfEventS) {
            if ((Math.abs(vy)) < VMax) {
                if (typeOfEventW) {
                    vy = vy + a;
                } else {
                    vy = vy - a;
                }
            }
        } else {
            if (Math.abs(vy) <= a) {
                vy = 0;
            } else {
                vy = vy - Math.signum(vy) * a;
            }
        }


        if (typeOfEventA != typeOfEventD) {
            if ((Math.abs(VAngle)) < VMax * 0.5) {             // ВНИМАНИЕ какой то ... догадался сделать угловую и
                if (typeOfEventA) {                      //линейную максимальную скорость одинаковыми
                    VAngle -= a;
                } else {
                    VAngle += a;
                }
            }
        } else {
            if (Math.abs(VAngle) <= a) {
                VAngle = 0;
            } else {
                VAngle -= Math.signum(VAngle) * a;
            }
        }
    }
    public int[] getTankX() {
        int w = image.getWidth();
        int h = image.getHeight();
        double s = Math.sin(Math.toRadians(angle));
        double c = Math.cos(Math.toRadians(angle));
        int[] TankX = new int[]{
                (int) (x + w * 0.5 * c + h * 0.5 * s),
                (int) (x - w * 0.5 * c + h * 0.5 * s),
                (int) (x - w * 0.5 * c - h * 0.5 * s),
                (int) (x + w * 0.5 * c - h * 0.5 * s)};
        return TankX;
    }

    public int[] getTankY() {
        int w = image.getWidth();
        int h = image.getHeight();
        double s = Math.sin(Math.toRadians(angle));
        double c = Math.cos(Math.toRadians(angle));
        int[] TankY = new int[]{
                (int) (y + w * 0.5 * s - h * 0.5 * c),
                (int) (y - w * 0.5 * s - h * 0.5 * c),
                (int) (y - w * 0.5 * s + h * 0.5 * c),
                (int) (y + w * 0.5 * s + h * 0.5 * c),
        };        // тут может быть лажа
        return TankY;
    }


}
