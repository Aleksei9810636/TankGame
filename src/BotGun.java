import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

    public class BotGun extends  GameObject {
        double x;
        double y;
        double AbcVAngle;
        double VAngle;
        double Angle = 90;
        double DirectionAngle;
        double TankVAngle;
        Tank tank1;
        Tank tank2;
        BufferedImage image = ImageIO.read(new File("imgs\\gun.png"));


        public BotGun(double AbcVAngle, Tank tank1, Tank tank2) throws IOException {
            this.AbcVAngle = AbcVAngle;
            this.tank1=tank1;
            this.tank2=tank2;
        }

        public void paint(Graphics g, double x, double y) {
            BufferedImage img = rotateImage(image, Angle);
            g.drawImage(img, (int) (x - img.getWidth() * 0.5), (int) (y - img.getHeight() * 0.5), null);
            this.x=x;
            this.y=y;
        }
        public void DirectionAngle(){
            DirectionAngle=90+Math.toDegrees(Math.atan2((tank1.y-y),(tank1.x-x)));
        }

        public void UpdatePlace() {
            DirectionAngle();
            VAngle += TankVAngle;
            Angle += VAngle;
            if (DirectionAngle > Angle) {
                if (DirectionAngle - Angle <= 180) {
                    VAngle = AbcVAngle;
                } else {
                    VAngle = -AbcVAngle;
                }
            }
            if (DirectionAngle < Angle) {
                if (Angle - DirectionAngle < 180) {
                    VAngle = -AbcVAngle;
                } else {
                    VAngle = AbcVAngle;
                }
            }
            if (Angle < 0) { // эти два ифа добавлены в связи с багом. если изначально мышь слево, то происходито баg
                Angle += 360;
            }
            if (Angle > 360) {
                Angle -= 360;
            }
        }
    }