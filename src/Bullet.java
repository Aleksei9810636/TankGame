import java.awt.*;
import java.util.Random;

public class Bullet {
    double x;
    double y;
    double Angle;
    double v=8;
    double DamageNormal=10;
    double Damage;
    int IndicationTank;
    int R=61;
    int G=239;
    int B=21;


    public Bullet(double x, double y, double GunAngle, int IndicationTank) {
        this.x = x;
        this.y = y;
        this.Angle=GunAngle;
        this.IndicationTank=IndicationTank; // какой танк выстрелил
        Random random=new Random();
        Damage=DamageNormal+random.nextInt(30)-15;
    }

    public void paint(Graphics g) {
        g.setColor(new Color(R, G, B, 255));
        g.fillOval((int) x, (int) y, 10, 10);
    }
    public boolean update() {
        x+=v*Math.sin(Math.toRadians(Angle));
        y-=v*Math.cos(Math.toRadians(Angle));
        if(x<-10||x>3000||y<-10||y>2000){
            return false;
        }
        return true;

    }

}
