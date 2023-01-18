import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TankPanel extends JPanel implements KeyEventDispatcher, MouseListener, MouseMotionListener {
    Tank tank1;
    Tank tank2;
    Gun gun1;
    KeyBoardGun keyBoardGun;
    double MouseX;
    double MouseY;
    ArrayList<Bullet> bullets= new ArrayList<>();
    ArrayList<Wall> walls;
    ArrayList<BotTank> BotTanks= new ArrayList<>();
    ArrayList<BotGun> BotGuns= new ArrayList<BotGun>();
    int sumBotTank=0;
    long StartTime;
    long time;

    public TankPanel(Tank tank1, Tank tank2, ArrayList walls, Gun gun, KeyBoardGun keyBoardGun, long StartTime ) throws IOException {       //Это вероятно не надо
        this.tank1 = tank1;
        this.gun1 =gun;
        this.tank2 = tank2;
        this.keyBoardGun=keyBoardGun;
        this.walls=walls;
        addMouseListener(this);
        addMouseMotionListener(this);
        this.StartTime=StartTime;
    }


    //  Далее управление клавиатурой и мышкой




    @Override
    public void mouseClicked(MouseEvent e) {              // на отпускание
    }

    @Override
    public void mousePressed(MouseEvent e) {               //на нажатие
//        System.out.println("mousePressed");
        if((System.currentTimeMillis()-tank1.LastShotTime)*0.001>tank1.RechargeTime) {
            tank1.LastShotTime=System.currentTimeMillis();
            Bullet bullet = new Bullet(tank1.x, tank1.y, gun1.Angle, 1);
            bullets.add(bullet);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {              // хз что но похоже на отпускание
//       System.out.println("mouseReleased");
    }

    @Override
    public void mouseEntered(MouseEvent e) {                //видимо когда наводим на панель
        //       System.out.println("mouseEntered");
    }

    @Override
    public void mouseExited(MouseEvent e) {                 //видимо конда уводим с панели
//        System.out.println("mouseExited");
    }

    @Override
    public void mouseDragged(MouseEvent e) {           //движется и зажата
    }

    @Override
    public void mouseMoved(MouseEvent e) {             // движется и не зажата
        MouseX=e.getX();
        MouseY=e.getY();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {

        if(e.getKeyCode() == 192){                      // фигово реализоваанное подключение фигово реализованного чита
                tank1.typeOfEventCheat = true;
        }
        if(e.getKeyCode() == 27){
            tank1.typeOfEventCheat = false;
        }


        if (e.getID() == KeyEvent.KEY_PRESSED) {
//            System.out.println(e.getKeyCode());
            if (e.getKeyCode() == 39) {
                if ((System.currentTimeMillis() - tank2.LastShotTime) * 0.001 > tank2.RechargeTime) {
                    tank2.LastShotTime = System.currentTimeMillis();
                    Bullet bullet = new Bullet(tank2.x, tank2.y, keyBoardGun.Angle, 2);
                    bullets.add(bullet);
                }
            }
            if (e.getKeyCode() == 100) {
                keyBoardGun.typeOfEvent4 = true;
            }
            if (e.getKeyCode() == 102) {
                keyBoardGun.typeOfEvent6 = true;
            }
        }
        if(e.getID() == KeyEvent.KEY_RELEASED){
            if (e.getKeyCode() == 100) {
                keyBoardGun.typeOfEvent4 = false;
            }
            if (e.getKeyCode() == 102) {
                keyBoardGun.typeOfEvent6 = false;
            }
        }


        if (e.getID() == KeyEvent.KEY_PRESSED) {
            if (e.getKeyChar() == 'w') {
                tank1.typeOfEventW = true;
            }
            if(e.getKeyChar() == 'a') {
                tank1.typeOfEventA = true;
            }
            if(e.getKeyChar() == 's') {
                tank1.typeOfEventS = true;
            }
            if(e.getKeyChar() == 'd') {
                tank1.typeOfEventD = true;
            }





            if (e.getKeyCode() == 38) {                //////ijkl                            46   лево права дуло                 "стрелочка вправо" выстрел
                tank2.typeOfEventW = true;
            }
            if (e.getKeyCode() == 37) {
                tank2.typeOfEventA = true;
            }
            if (e.getKeyCode() == 40) {
                tank2.typeOfEventS = true;
            }
            if (e.getKeyCode() == 39) {
                tank2.typeOfEventD = true;
            }
        }
        if(e.getID() == KeyEvent.KEY_RELEASED){
            if (e.getKeyChar() == 'w'){
                tank1.typeOfEventW = false;
            }
            if (e.getKeyChar() == 'a'){
                tank1.typeOfEventA = false;
            }
            if (e.getKeyChar() == 's'){
                tank1.typeOfEventS = false;
            }
            if (e.getKeyChar() == 'd'){
                tank1.typeOfEventD = false;
            }
            if (e.getKeyCode() == 38) {                //////ijkl                            46   лево права дуло                 "стрелочка вправо" выстрел
                tank2.typeOfEventW = false;
            }
            if (e.getKeyCode() == 37) {
                tank2.typeOfEventA = false;
            }
            if (e.getKeyCode() == 40) {
                tank2.typeOfEventS = false;
            }
            if (e.getKeyCode() == 39) {
                tank2.typeOfEventD = false;
            }
        }
        return false;
    }

    public  void UPTime(Graphics g){
        time=System.currentTimeMillis()-StartTime;
        g.drawString(Integer.toString((int)(time/1000)), 800,100);

    }
    public void BotControl( Tank tank1,Tank tank2,ArrayList<Wall> walls) throws IOException {
        if(time>1000&& sumBotTank<2) {
            BufferedImage image = ImageIO.read(new File("imgs\\Tank1.jpg"));
            BotTank botTank1 = new BotTank(111, 111*sumBotTank+500, 1, 0.05, 900, 1, image, tank1, tank2);
            BotTanks.add(sumBotTank, botTank1);
            BotGun botGun= new BotGun(0.2, tank1, tank2);
            BotGuns.add(sumBotTank, botGun);
            sumBotTank++;
        }
    }



    public void updateCollisions(Graphics g) {
        int[] Tank1X = tank1.getTankX();
        int[] Tank1Y = tank1.getTankY();

        int[] Tank2X = tank2.getTankX();
        int[] Tank2Y = tank2.getTankY();
        g.setColor(new Color(252, 252, 252));            // это центр
        g.fillOval((int) (tank1.x - 1), (int) (tank1.y - 1), 2, 2);


        Polygon P_tank1 = new Polygon(Tank1X, Tank1Y, 4);
        Polygon P_tank2 = new Polygon(Tank2X, Tank2Y, 4);

        for (int i = 0; i < walls.size(); i++) {
            Wall wall = walls.get(i);


            if (P_tank1.intersects(wall.x, wall.y, wall.width, wall.height)) {        //если пересекаются..

                if (P_tank1.intersects(wall.x, wall.y + wall.height, wall.width, 1)) {
                    this.tank1.y += Math.abs(this.tank1.vy) + 1;
                }
                if (P_tank1.intersects(wall.x, wall.y, 1, wall.height)) {
                    this.tank1.x -= Math.abs(this.tank1.vy) + 1;
                }
                if (P_tank1.intersects(wall.x, wall.y, wall.width, 1)) {
                    this.tank1.y -= Math.abs(this.tank1.vy) + 1;
                }
                if (P_tank1.intersects(wall.x + wall.width, wall.y, 1, wall.height)) {
                    this.tank1.x += Math.abs(this.tank1.vy) + 1;
                }
                g.setColor(new Color(55, 250, 31, 255));
                g.fillRect(10, 10, 1200, 20);
            }
            if (P_tank2.intersects(wall.x, wall.y, wall.width, wall.height)) {        //если пересекаются..

                if (P_tank2.intersects(wall.x, wall.y + wall.height, wall.width, 1)) {
                    this.tank2.y += Math.abs(this.tank2.vy) + 1;
                }
                if (P_tank2.intersects(wall.x, wall.y, 1, wall.height)) {
                    this.tank2.x -= Math.abs(this.tank2.vy) + 1;
                }
                if (P_tank2.intersects(wall.x, wall.y, wall.width, 1)) {
                    this.tank2.y -= Math.abs(this.tank2.vy) + 1;
                }
                if (P_tank2.intersects(wall.x + wall.width, wall.y, 1, wall.height)) {
                    this.tank2.x += Math.abs(this.tank2.vy) + 1;
                }
                g.setColor(new Color(55, 250, 31, 255));
                g.fillRect(10, 10, 1200, 20);
            }

            for (int j = 0; j < BotTanks.size(); j++) {                                                               // пересечение ботиков со стенкой
                Polygon P_botTank = new Polygon(BotTanks.get(j).getTankX(), BotTanks.get(j).getTankY(), 4);

                if (P_botTank.intersects(wall.x, wall.y, wall.width, wall.height)) {        //если пересекаются..

                    if (P_botTank.intersects(wall.x, wall.y + wall.height, wall.width, 1)) {
                        this.BotTanks.get(j).y += Math.abs(this.tank1.vy) + 1;
                    }
                    if (P_botTank.intersects(wall.x, wall.y, 1, wall.height)) {
                        this.BotTanks.get(j).x -= Math.abs(this.tank1.vy) + 1;
                    }
                    if (P_botTank.intersects(wall.x, wall.y, wall.width, 1)) {
                        this.BotTanks.get(j).y -= Math.abs(this.tank1.vy) + 1;
                    }
                    if (P_botTank.intersects(wall.x + wall.width, wall.y, 1, wall.height)) {
                        this.BotTanks.get(j).x += Math.abs(this.tank1.vy) + 1;
                    }
                }
            }
            }

        }


    public void GunAngle(){                                             ////// not tank2
        double Angle1=MouseX- tank1.x;
        double Angle2= tank1.y-MouseY;
        double angle=90-Math.toDegrees(Math.atan2(Angle2, Angle1));
        if(angle<0){
            gun1.MouseAngle=360+angle;
        } else {
            gun1.MouseAngle = angle;
        }
        gun1.TankVAngle=tank1.VAngle;

    }
    public void BulletList(Graphics g){
        for(Bullet bullet : bullets){ //bullets.get(i) = bullet
            bullet.paint(g);
            bullet.update();
        }
        for (int i = 0; i<walls.size(); i++){
            Wall wall=walls.get(i);
            int[] WallX=new int[]{
                    (int)(wall.x),
                    (int)(wall.x+wall.width),
                    (int)(wall.x+wall.width),
                    (int)(wall.x) };
            int[] WallY=new int[]{
                    (int)(wall.y),
                    (int)(wall.y),
                    (int)(wall.y+wall.height),
                    (int)(wall.y+wall.height) };
            Polygon P_wall=new Polygon(WallX, WallY, 4);
            for(int k = 0; k<bullets.size(); k++){
                if((P_wall.intersects(bullets.get(k).x, bullets.get(k).y, 10, 10))){       ///////////////////////////////////////////////////////////////////////////////
                    bullets.remove(k);
                    --k;
                }
            }
        }

    }
    public void HitCheck() {
        int[] Tank1X = tank1.getTankX();
        int[] Tank1Y = tank1.getTankY();
        int[] Tank2X = tank2.getTankX();
        int[] Tank2Y = tank2.getTankY();
        Polygon P_tank2 = new Polygon(Tank2X, Tank2Y, 4);
        Polygon P_tank1 = new Polygon(Tank1X, Tank1Y, 4);
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet=bullets.get(i);
            if (P_tank1.intersects(bullet.x, bullet.y, 10, 10) && bullet.IndicationTank == 2) {
                if(!tank1.typeOfEventCheat) {
                    this.tank1.HitPoints -= bullets.get(i).Damage;                                                                               // отстойненько т.к. размер пули не читается
                    bullets.remove(i);
                }else {
                    bullets.remove(i);
                }
                if (i == 0) {     //этот иф призван сюда исправить баги с размером массива и удаление последнего элемента
                    break;
                } else {
                    i -= 1;
                }
            }
            if (P_tank2.intersects(bullets.get(i).x, bullets.get(i).y, 10, 10) && bullets.get(i).IndicationTank == 1) {                   // отстойненько т.к. размер пули не читается
                this.tank2.HitPoints -= bullets.get(i).Damage;
                bullets.remove(i);
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        UPTime(g);
        // это вставила джава
        try {
            BotControl(tank1, tank2, walls);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //
        updateCollisions(g);
        GunAngle();
        tank1.UpdatePlace();
        tank2.UpdatePlace();          ///

        gun1.UpdatePlace();
        keyBoardGun.UpdatePlace();
        BulletList(g);
        HitCheck();


        tank1.paint(g);
        tank2.paint(g);                    ///

        gun1.paint(g, tank1.x, tank1.y);
        keyBoardGun.paint(g, tank2.x, tank2.y);

        for(int i=0; i<walls.size();i++){
            walls.get(i).paint(g);
        }
        for(int i=0;i<BotTanks.size();i++){
            BotTanks.get(i).UpdatePlace();
            BotTanks.get(i).paint(g);
            BotGuns.get(i).UpdatePlace();
            BotGuns.get(i).paint(g,BotTanks.get(i).x,BotTanks.get(i).y );
        }

        g.drawPolygon(tank1.getTankX(), tank1.getTankY(), 4);


    }
}
