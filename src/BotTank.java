import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BotTank extends GameObject {
    double x;
    double y;
    double VAngle;
    double AbcVAngle=0.2;
    double vy;
    double VMax;
    double a;
    double angle = 90;
    double DirectionAngle;
    double HitPoints;
    double HitPointsMax;
    double RechargeTime;
    long LastShotTime = 0;
    BufferedImage image;
    boolean typeOfEventW;
    boolean typeOfEventA;
    boolean typeOfEventS;
    boolean typeOfEventD;
    int SignmTank=1;
    Tank tank1;

    BotGun botGun;

    public BotTank(double x, double y, double VMax, double a, double hitPointsMax,
                   double rechargeTime, BufferedImage image, Tank tank1) throws IOException {
        this.x = x;
        this.y = y;
        this.VMax = VMax;
        this.a = a;
        HitPoints = hitPointsMax;
        HitPointsMax = hitPointsMax;
        RechargeTime = rechargeTime;
        this.image = image;
        this.tank1=tank1;
        botGun=new BotGun(0.2,tank1, this);

    }

    public void paint(Graphics g) {
        BufferedImage img = rotateImage(image, angle);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, (int) (x - img.getWidth() * 0.5), (int) (y - img.getHeight() * 0.5), null);
        botGun.paint(g,x,y);
        if(VAngle>=0){
            SignmTank=1;
        }else {
            SignmTank = -1;
        }
        botGun.UpdatePlace(SignmTank);
        g.setColor(new Color(239, 12, 12));
        g.drawString(Integer.toString((int)HitPoints),(int) x,(int) y-40);
    }
    public void UPPlace(){

//        typeOfEventW=true;
        DirectionAngle= botGun.DirectionAngle;

    }
    public void UpdatePlace() {
        UPPlace();
        double angleInRadians = Math.toRadians(angle);
        x += vy * Math.sin(angleInRadians);
        y -= vy * Math.cos(angleInRadians);
//        angle += VAngle * 1;
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
        boolean flag=false;
        angle += VAngle;
        if (DirectionAngle > angle) {
            if (DirectionAngle - angle <= 180) {
                VAngle = AbcVAngle;
                if((DirectionAngle - angle )<= 45){
                    flag=true;
                }
            } else {
                VAngle = -AbcVAngle;
                if((360-DirectionAngle+angle)<=45){
                    flag=true;
                }
            }
        }
        if (DirectionAngle < angle) {
            if (angle - DirectionAngle <= 180) {
                VAngle = -AbcVAngle;
                if((angle-DirectionAngle)<=45){
                    flag=true;
                }
            } else {
                VAngle = AbcVAngle;
                if((360-angle+DirectionAngle)<=45){
                    flag=true;
                }
            }
        }
        if(flag){
            typeOfEventW=true;
        }else{
            typeOfEventW=false;
        }
        if (angle < 0) { // эти два ифа добавлены в связи с багом. если изначально мышь слево, то происходито баg
            angle += 360;
        }
        if (angle > 360) {
            angle -= 360;
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

    public int[] getTankX(int a, int b) {
        int w = image.getWidth();
        int h = image.getHeight();
        double s = Math.sin(Math.toRadians(angle));
        double c = Math.cos(Math.toRadians(angle));
        int[] TankX = new int[2];
        int i = 0;
        if (a == 1) {
            TankX[i] = (int) (x + w * 0.5 * c + h * 0.5 * s);
            i += 1;
        }
        if (a == 2) {
            TankX[i] = (int) (x - w * 0.5 * c + h * 0.5 * s);
            i += 1;
        }
        if (a == 3) {
            TankX[i] = (int) (x - w * 0.5 * c - h * 0.5 * s);
            i += 1;
        }
        if (a == 4) {
            TankX[i] = (int) (x + w * 0.5 * c - h * 0.5 * s);
            i += 1;
        }

        if (b == 1) {
            TankX[i] = (int) (x + w * 0.5 * c + h * 0.5 * s);
            i += 1;
        }
        if (b == 2) {
            TankX[i] = (int) (x - w * 0.5 * c + h * 0.5 * s);
            i += 1;
        }
        if (b == 3) {
            TankX[i] = (int) (x - w * 0.5 * c - h * 0.5 * s);
            i += 1;
        }
        if (b == 4) {
            TankX[i] = (int) (x + w * 0.5 * c - h * 0.5 * s);
            i += 1;
        }
        return TankX;
    }

    public int[] getTankY(int a, int b) {
        int w = image.getWidth();
        int h = image.getWidth();
        double s = Math.sin(Math.toRadians(angle));
        double c = Math.cos(Math.toRadians(angle));
        int[] TankX = new int[2];
        int i = 0;


        int[] TankY = new int[]{
                (int) (y + w * 0.5 * s - h * 0.5 * c),
                (int) (y - w * 0.5 * s - h * 0.5 * c),
                (int) (y - w * 0.5 * s + h * 0.5 * c),
                (int) (y + w * 0.5 * s + h * 0.5 * c),
        };



        if (a == 1) {
            TankY[i] = (int) (y + w * 0.5 * s - h * 0.5 * c);
            i += 1;
        }
        if (a == 2) {
            TankX[i] = (int)(y - w * 0.5 * s - h * 0.5 * c);
            i += 1;
        }
        if (a == 3) {
            TankX[i] = (int) (y - w * 0.5 * s + h * 0.5 * c);
            i += 1;
        }
        if (a == 4) {
            TankX[i] = (int) (x + w * 0.5 * c - h * 0.5 * s);
            i += 1;
        }

        if (b == 1) {
            TankX[i] = (int) (x + w * 0.5 * c + h * 0.5 * s);
            i += 1;
        }
        if (b == 2) {
            TankX[i] = (int) (x - w * 0.5 * c + h * 0.5 * s);
            i += 1;
        }
        if (b == 3) {
            TankX[i] = (int) (x - w * 0.5 * c - h * 0.5 * s);
            i += 1;
        }
        if (b == 4) {
            TankX[i] = (int) (x + w * 0.5 * c - h * 0.5 * s);
            i += 1;
        }
        return TankX;
    }



}
