import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) throws IOException{
        BufferedImage SteelBox= ImageIO.read(new File("imgs\\SteelBox.jpg"));
        BufferedImage WoodBox= ImageIO.read(new File("imgs\\WoodBox.jpg"));
        long StartTime;
        StartTime=System.currentTimeMillis();
        System.out.println(StartTime);


        GameObject gameObject=new GameObject();  // вообщем то бесполезно
        Tank tank1=new Tank(1800,300, 1,  0.05, 900, 50, 5);
        Tank tank2=new Tank(1800, 500, 1, 0.05, 900, 1050, 2);
        Gun gun=new Gun(0.2);
        KeyBoardGun keyBoardGun=new KeyBoardGun(0.2);
        ArrayList<Wall> walls=new ArrayList<>();
        Wall wall1=new Wall(1000, 100, 100, 200,WoodBox, 300);
        Wall wall6=new Wall(1000, 600, 100, 200, WoodBox, 300);

        walls.add(wall1);
        walls.add(wall6);
        // Создаем окно
        JFrame frame = new JFrame();
        TankPanel tankPanel=new TankPanel(tank1, tank2, walls, gun, keyBoardGun, StartTime);
        frame.add(tankPanel);        // добавляем в окно панель
        frame.setSize(1900,1000);
        frame.setVisible(true);
        Wall wall2=new Wall(0,0, frame.getWidth(), 50, SteelBox, 100000);
        Wall wall3=new Wall(frame.getWidth()-50,0,50, frame.getHeight(), SteelBox, 100000);
        Wall wall4=new Wall(0,frame.getHeight()-90,frame.getWidth()-50, 50, SteelBox, 100000);
        Wall wall5=new Wall(-60,0,50, 1000, SteelBox, 100000);
        walls.add(wall2);
        walls.add(wall3);
        walls.add(wall4);
        walls.add(wall5);


        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();   // менеджер по трудоустройству слушателей клавиатуры
        manager.addKeyEventDispatcher(tankPanel);    // подключаем нашу панель к прослушиванию клавиатуры

        while (true) {
            frame.repaint();

        }


    }
}
