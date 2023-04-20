import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Gun extends  GameObject {
    double AbcVAngle;
    double VAngle;
    double Angle=-90;
    double MouseAngle;
    double TankVAngle;
    int SignmTank;
    BufferedImage image = ImageIO.read(new File("imgs\\TankGun.png"));

    public Gun(double AbcVAngle) throws IOException {
        this.AbcVAngle = AbcVAngle;
    }

    public void paint(Graphics g, double x, double y) {       // x и y это координаты центра
        BufferedImage img = rotateImage(image, Angle);
        g.drawImage(img, (int) (x - img.getWidth() * 0.5), (int) (y - img.getHeight() * 0.5), null);
//        g.drawImage(img,(int)(x-30),(int)(y-30),(int)(x+30),(int)(y+30), 0, 0, img.getWidth(), img.getHeight(), null);

    }

    public void UpdatePlace() {
        VAngle+=TankVAngle;
        Angle+=VAngle;
        if (MouseAngle > Angle) {
            if (MouseAngle - Angle <= 180) {
                VAngle = AbcVAngle;
            } else {
                VAngle = -AbcVAngle;
            }
        }
        if (MouseAngle < Angle) {
            if (Angle - MouseAngle < 180) {
                VAngle = -AbcVAngle;
            } else {
                VAngle = AbcVAngle;
            }
        }
        if (Angle < 0) {
            Angle += 360;
        }
        if (Angle > 360) {
            Angle -= 360;
        }
    }


//    public void UpdatePlace(){
//        if(MouseAngle>Angle){
//            if(MouseAngle-Angle<=180){
//                Angle+= AbcVAngle;
//            } else {
//                Angle-= AbcVAngle;
//            }
//        }
//        if(MouseAngle<Angle){
//            if(Angle-MouseAngle<180){
//                Angle-= AbcVAngle;
//            } else {
//                Angle+= AbcVAngle;
//            }
//        }
//        Angle+=TankVAngle;
//        if(Angle<0){ // эти два ифа добавлены в связи с багом. если изначально мышь слево, то происходито баg
//            Angle+=360;
//        }
//        if(Angle>360){
//            Angle-=360;
//        }
//        System.out.println(AbcVAngle);
//
//    }
    }
