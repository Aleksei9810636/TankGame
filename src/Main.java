import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

//        Thread threadMusic = new Thread(() -> {
//            while(true) {
//                new MakeSound().playSound("music\\SoundNO.wav");
//                System.out.println("audio file finished!");
//                //threadMusic.start();  вставь это туда, где должна заиграть музыка
//            }
//        });
//        threadMusic.start();



        BufferedImage SteelBox= ImageIO.read(new File("imgs\\TimeBox1.png"));
        BufferedImage WoodBox= ImageIO.read(new File("imgs\\WoodBox.jpg"));
        Scanner scanner = new Scanner(new File("src\\Time.txt"));
        PrintWriter out = new PrintWriter("src\\Time.txt");
        long StartTime;
        StartTime=System.currentTimeMillis();

        Dimension sSize = Toolkit.getDefaultToolkit ().getScreenSize ();
        int vert = sSize.height-70;
        int hor  = sSize.width-30;

        GameObject gameObject=new GameObject();  // вообщем то бесполезно
        Tank tank1=new Tank(750,400, 5,  0.2, 900, 50, 0.5);
        Tank tank2=new Tank(1050, 600, 5, 0.2, 900, 2500, 0.5);
        Gun gun=new Gun(1.5);
        KeyBoardGun keyBoardGun=new KeyBoardGun(1.7);
        ArrayList<Wall> walls=new ArrayList<>();
        Wall wall1=new Wall(400, 200, 100, 101,WoodBox, 300);
//        Wall wall6=new Wall(750, 120, 100, 101, WoodBox, 300);
//        Wall wall7=new Wall(1000, 200, 100,100 , WoodBox, 300);
//
//        Wall wall2=new Wall(0,0, hor, 100, SteelBox, 100000);        // края
//        Wall wall3=new Wall(hor-100,0,100,vert,SteelBox, 100000 );         // края
//        Wall wall4=new Wall(0,vert-100, hor, 100, SteelBox,10000);             // края
//        Wall wall5=new Wall(-300, -200, 100, vert+200, SteelBox, 100000 );          // края


        walls.add(wall1);
//        walls.add(wall6);
//        walls.add(wall2);
//        walls.add(wall3);
//        walls.add(wall4);
//        walls.add(wall5);
//        walls.add(wall7);
        // Создаем окно
        JFrame frame = new JFrame();
        TankPanel tankPanel=new TankPanel(tank1, tank2, walls, gun, keyBoardGun, StartTime);
        frame.add(tankPanel);        // добавляем в окно панель
        frame.setSize(hor,vert);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // при закрытии окошка нужно завершить выполнение программы



        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();   // менеджер по трудоустройству слушателей клавиатуры
        manager.addKeyEventDispatcher(tankPanel);    // подключаем нашу панель к прослушиванию клавиатуры

        while (true) {
            frame.repaint();
            Thread.sleep(20);
        }
    }
}