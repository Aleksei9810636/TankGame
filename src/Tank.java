
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Tank extends GameObject {
    double x;
    double y;
    double VAngle;
    double vy;
    double VMax;
    double a;
    double angle = -90;
    double HitPoints;
    double HitPointsMax;
    double laja;
    double RechargeTime;
    long LastShotTime = 0;
    BufferedImage image = ImageIO.read(new File("imgs\\Tank1.jpg"));
    boolean typeOfEventW;
    boolean typeOfEventA;
    boolean typeOfEventS;
    boolean typeOfEventD;
    boolean typeOfEventCheat = false;

    public Tank(double x, double y, double VMax, double a, double HitPoints, double laja, double RechargeTime) throws IOException {
        this.x = x;
        this.y = y;
        this.VAngle = 0;
        this.vy = 0;
        this.VMax = VMax;
        this.a = a;
        this.HitPoints = HitPoints;
        this.laja = laja;
        this.RechargeTime = RechargeTime;
        HitPointsMax = HitPoints;

    }

    public void paint(Graphics g) {
        BufferedImage img = rotateImage(image, angle);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, (int) (x - img.getWidth() * 0.5), (int) (y - img.getHeight() * 0.5), null);


        if (typeOfEventCheat) {
            g.setColor(new Color(166, 11, 227));
            g.fillRect(1100, 20, 30, 5);
            LastShotTime=1;
        }


        g.setColor(new Color(75, 68, 68));
        g.drawRect((int) (laja * 0.5), 920, (int) (HitPointsMax * 0.5), 10);
        g.setColor(new Color(239, 12, 12));
        g.fillRect((int) (laja * 0.5), 920, (int) (HitPoints * 0.5), 10);
        g.drawString((HitPoints + "/" + HitPointsMax), (int) (laja*0.5 + 200), 910);
        if (HitPoints <= 0) {
            g.setColor(new Color(0, 224, 205));
            g.fillRect(0, 0, 2000, 2000);

        }
        if (LastShotTime == 0) {
            g.drawString("Нажмите кнопку стрельбы, чтобы выстрелить", (int) (x - 50), (int) (y - 50));
        } else {
            if ((((System.currentTimeMillis() - LastShotTime) * 0.001) % 1000) < RechargeTime) {
                Integer.toString(((int) ((System.currentTimeMillis() - LastShotTime) * 0.001) % 1000));
                g.drawString((Integer.toString((int) (1 + RechargeTime - ((System.currentTimeMillis() - LastShotTime) * 0.001) % 1000))), (int) (x - 50), (int) (y - 50));
            } else {
                g.drawString("0", (int) (x - 50), (int) (y - 50));
            }

        }
    }


    // если что х и у это координаты центра танка

    public void UpdatePlace() {
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
        if(typeOfEventCheat){
            VMax=2.5;
        }
    }



    public int[] getTankX() {
        int w = image.getWidth();
        int h = image.getWidth();
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
        int h = image.getWidth();
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
        int h = image.getWidth();
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

