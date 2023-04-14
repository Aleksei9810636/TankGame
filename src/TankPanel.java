import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class TankPanel extends JPanel implements KeyEventDispatcher, MouseListener, MouseMotionListener {
    String stage="start";                 //"game";   //"menu";
    String User="gamer";
    BufferedImage imageStartMenu = ImageIO.read(new File("imgs\\ThisWell.jpg"));
    BufferedImage Start1on1button1 = ImageIO.read(new File("imgs\\1on1version1.png"));
    BufferedImage Start1on1button2 = ImageIO.read(new File("imgs\\1on1version2.png"));
    BufferedImage ReplyButton1 = ImageIO.read(new File("imgs\\NeonReply1.png"));
    BufferedImage ReplyButton2 = ImageIO.read(new File("imgs\\NeonReply2.png"));
    BufferedImage imageButton1ToBattle = ImageIO.read(new File("imgs\\ToBattle.png"));
    BufferedImage PausBotton1 = ImageIO.read(new File("imgs\\PauseButton1.png"));
    BufferedImage PausBotton2 = ImageIO.read(new File("imgs\\PauseButton21.png"));
    BufferedImage imagestart = ImageIO.read(new File("imgs\\Start.jpg"));
    BufferedImage imagemenu = ImageIO.read(new File("imgs\\Menu.jpg"));
    BufferedImage imageFon = ImageIO.read(new File("imgs\\Fon.jpg"));
    BufferedImage TestWall = ImageIO.read(new File("imgs\\pol_ot_Aleshe.jpg"));
//BufferedImage imageFon = ImageIO.read(new File("imgs\\pol_ot_Aleshe.jpg"));
    Tank Tank1;
    Tank Tank2;


    Tank UzeTank1;
    Tank UzeTank2;
    Gun gun1;

    KeyBoardGun keyBoardGun;
    double MouseX;
    double MouseY;
    double PanelWidth;
    double PanelHeight;
    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<Wall> walls = new ArrayList<>();
    ArrayList<Wall> Startwalls;
    ArrayList<BotTank> BotTanks = new ArrayList<>();
    ArrayList<StringPaint> stringPaint= new ArrayList<>();
    ArrayList<Button> buttons=new ArrayList<>();
    int sumBotTank = 0;
    long StartTime;
    long time;
    long StringStartTime;
    long gametime=0;
    Button buttonInBattle=null;
    Button buttonReply=null;
    Button buttonStart1on1=null;
    FileWriter writer=new FileWriter("file\\file.txt", true);
    FileReader reader = new FileReader("file\\file.txt");
    Scanner scanner=new Scanner(reader);
    int[] i1=new int[]{1, 1, 100, 100};
    int[] i2=new int[]{1, 100, 100, 1};

    Polygon ploika= new Polygon(i1, i2, 4);



    Thread threadMusic = new Thread(() -> {
//        while(true) {
//            new MakeSound().playSound("music\\silent-wood.wav");
//            System.out.println("audio file finished!");
//            //threadMusic();  вставь это туда, где должна заиграть музыка
//        }
    });
    void threadYest_probitie(){
        new Thread(() -> {
            new MakeSound().playSound("music\\Yest_probitie.wav");
           // threadYest_probitie.start();  вставь это туда, где должна заиграть музыка
            System.out.println("audio file finished!");
        }).start();
    }
    void tread_BOOM(){
        new Thread(() -> {
            new MakeSound().playSound("music\\BOOM.wav");
            //thread_BOOM;  вставь это туда, где должна заиграть музыка
//            System.out.println("audio file finished!");
        }).start();
    }


    public TankPanel(Tank tank1, Tank tank2, ArrayList walls, Gun gun, KeyBoardGun keyBoardGun, long StartTime) throws IOException {       //Это вероятно не надо
        this.UzeTank1 = tank1;
        Tank Tank1Del=new Tank(tank1.x, tank1.y, tank1.VMaxUze, tank1.a, tank1.HitPoints, tank1.laja, tank1.RechargeTime);
        this.Tank1=Tank1Del;
        this.gun1 = gun;
        this.UzeTank2 = tank2;
        Tank Tank2Del=new Tank(tank2.x, tank2.y, tank2.VMaxUze, tank2.a, tank2.HitPoints, tank2.laja, tank2.RechargeTime);
        this.Tank2=Tank2Del;
        this.keyBoardGun = keyBoardGun;
        this.Startwalls = walls;
        WallsDifferentiation();
        addMouseListener(this);
        addMouseMotionListener(this);
        this.StartTime = StartTime;
        System.nanoTime();
        StringStartTime=0;


        threadMusic.start();
//        thread.interrupt();          //  это остановит музыку
    }




        public void WallsDifferentiation () throws IOException {
        for (int i = 0; i < Startwalls.size(); i++) {
            Wall startwall = Startwalls.get(i);
            int UnitX = startwall.width / startwall.SizeBoxX;
            int UnitY = startwall.height / startwall.SizeBoxY;
            startwall.width = UnitX * startwall.SizeBoxX;
            startwall.height = UnitY * startwall.SizeBoxY;
            for (int a = 0; a < UnitX; a++) {
                for (int b = 0; b < UnitY; b++) {
                    Wall wall = new Wall(startwall.x + a * startwall.SizeBoxX, startwall.y + b * startwall.SizeBoxY,
                            startwall.SizeBoxX, startwall.SizeBoxY, startwall.image, (int) startwall.BoxHP);
                    walls.add(wall);
                }
            }
        }
    }


        //  Далее управление клавиатурой и мышкой


        @Override
        public void mouseClicked (MouseEvent e){              // на отпускание




            // запись всей строки

//            try {
//                writer.write("A\n");
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//            try {
//                writer.flush();
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }

//            int i = 1;

//            while (scanner.hasNextLine()) {
//                System.out.println(i + " : " + scanner.nextLine());
//            }
            if(User.equals("creator")){
                System.out.println("mouseClicked: "+ "("+e.getX()+";" + e.getY()+")");
                Wall wall = null;
                int variationx=e.getX();
                int variationy=e.getY();
                int biteJ=0;
                for(int j=0; j< walls.size(); j++){
                    Wall w=walls.get(j);
                    Wall bite=walls.get(biteJ);

//                    if( ((w.x-w.SizeBoxX/2)-e.getX())*((w.x-w.SizeBoxX/2)-e.getX())+
//                            ((w.y-w.SizeBoxY/2)-e.getY())*((w.y-w.SizeBoxY/2)-e.getY())<4*w.SizeBoxX*2*w.SizeBoxX){
//                        System.out.println("первое усл");
//                    }
//                    if(((w.x-w.SizeBoxX/2)-e.getX())*((w.x-w.SizeBoxX/2)-e.getX())+
//                            ((w.y-w.SizeBoxY/2)-e.getY())*((w.y-w.SizeBoxY/2)-e.getY())<=
//                            ((bite.x-bite.SizeBoxX/2)-e.getX())*((bite.x-bite.SizeBoxX/2)-e.getX())+
//                                    ((bite.y-bite.SizeBoxY/2)-e.getY())*((bite.y-bite.SizeBoxY/2)-e.getY())){
//                        System.out.println("второе усл");
//                    }





                    if(     ((w.x-w.SizeBoxX/2)-e.getX())*((w.x-w.SizeBoxX/2)-e.getX())+
                            ((w.y-w.SizeBoxY/2)-e.getY())*((w.y-w.SizeBoxY/2)-e.getY())<4*w.SizeBoxX*2*w.SizeBoxX
                        &&
                            ((w.x-w.SizeBoxX/2)-e.getX())*((w.x-w.SizeBoxX/2)-e.getX())+
                            ((w.y-w.SizeBoxY/2)-e.getY())*((w.y-w.SizeBoxY/2)-e.getY())<=
                            ((bite.x-bite.SizeBoxX/2)-e.getX())*((bite.x-bite.SizeBoxX/2)-e.getX())+
                            ((bite.y-bite.SizeBoxY/2)-e.getY())*((bite.y-bite.SizeBoxY/2)-e.getY())
                    ){
                        biteJ=j;
                    }
                }
                Wall w =walls.get(biteJ);

                if(e.getX()>w.x+w.SizeBoxX && e.getY()>w.y && e.getY()<w.y+w.SizeBoxY){
                    variationx=w.x+w.SizeBoxX;
                    variationy=w.y;
                    System.out.println("right build: ("+w.x+";"+w.y+")");
                }
                if (e.getX() < w.x &&  e.getY()>w.y && e.getY()<w.y+w.SizeBoxY) {
                    variationx = w.x - w.SizeBoxX;
                    variationy = w.y;
                    System.out.println("left build: ("+w.x+";"+w.y+")");
                }
                if(e.getY()>w.y+w.SizeBoxY && e.getX()>w.x && e.getX()<w.x+w.SizeBoxX){
                    variationx=w.x;
                    variationy=w.y+w.SizeBoxY;
                    System.out.println("down build: ("+w.x+";"+w.y+")");
                }if(e.getY()<w.y && e.getX()>w.x && e.getX()<w.x+w.SizeBoxX){
                    variationx=w.x;
                    variationy=w.y-w.SizeBoxY;
                    System.out.println("up build: ("+w.x+";"+w.y+")");
                }

                if(e.getButton()==3){
                    int[] wallX = new  int[]{
                            variationx+w.SizeBoxX,
                            variationx,
                            variationx,
                            variationx+w.SizeBoxX
                    };
                    int[] wallY = new  int[]{
                            variationy,
                            variationy,
                            variationy+w.SizeBoxY,
                            variationy+w.SizeBoxY
                    };

                    ploika=new Polygon(wallX, wallY, 4);
                }


                if(e.getButton()==1) {
                    try {

                        wall = new Wall(variationx, variationy, 75, 75, TestWall, 300);
                        String str="Wall wall = new Wall("+variationx+", "+variationy+", "+"75, 75, TestWall, 300)";
                        try {
                            writer.write(str+"\n");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        try {
                             writer.flush();
                       } catch (IOException ex) {
                          throw new RuntimeException(ex);
                       }

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    walls.add(wall);
                }
            }

        }


        @Override
        public void mousePressed (MouseEvent e){               //на нажатие
//        System.out.println("mousePressed");
            boolean FlagTypeMouse=true;
            for(int i = 0 ; i<buttons.size(); i++){
                if(buttons.get(i).TypeMouse.equals("yes")){
                    FlagTypeMouse=false;
                }
            }

        if ((System.currentTimeMillis() - UzeTank1.LastShotTime) * 0.001 > UzeTank1.RechargeTime && stage.equals("game") && FlagTypeMouse) {
            UzeTank1.LastShotTime = System.currentTimeMillis();
            Bullet bullet = new Bullet(UzeTank1.x, UzeTank1.y, gun1.Angle, 1);
            bullets.add(bullet);
            tread_BOOM();
        }
            for(int i=0; i<buttons.size(); i++){
                int k=-1; // для того чтобы кнопка не нажималась много раз за один раз
                if(buttons.get(i).Name.equals("pause") && buttons.get(i).TypeMouse.equals("yes")){
                    if(!stage.equals("menu") && k<0){
                        stage="menu";
                        k+=1;
                    }
                    if(stage.equals("menu") && k<0){
                        stage="game";
                        k++;
                    }
                }
                if(buttons.get(i).Name.equals("InBattle") && buttons.get(i).TypeMouse.equals("yes")){
                    stage="game";
                }
                if(buttons.get(i).Name.equals("reply") && buttons.get(i).TypeMouse.equals("yes")){
                    try {
                        GameReply();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if(buttons.get(i).Name.equals("Start1on1") && buttons.get(i).TypeMouse.equals("yes")){
                    System.out.println("Start1on1");
                }
            }


    }

        @Override
        public void mouseReleased (MouseEvent e){              // хз что но похоже на отпускание
    }

        @Override
        public void mouseEntered (MouseEvent e){                //видимо когда наводим на панель
        //       System.out.println("mouseEntered");
    }

        @Override
        public void mouseExited (MouseEvent e){                 //видимо конда уводим с панели
//        System.out.println("mouseExited");
    }

        @Override
        public void mouseDragged (MouseEvent e){           //движется и зажата
    }

        @Override
        public void mouseMoved (MouseEvent e){             // движется и не зажата
        MouseX = e.getX();
        MouseY = e.getY();
            for(int i=0; i<buttons.size(); i++){
                buttons.get(i).MouseX=MouseX;
                buttons.get(i).MouseY=MouseY;
            }

    }

        @Override
        public boolean dispatchKeyEvent (KeyEvent e){

        if (e.getKeyCode() == 192) {                      // фигово реализоваанное подключение фигово реализованного чита
            UzeTank1.typeOfEventCheat = true;
        }
        if (e.getKeyCode() == 27) {
            UzeTank1.typeOfEventCheat = false;
        }


        if (e.getID() == KeyEvent.KEY_PRESSED) {
//            System.out.println(e.getKeyCode());
            if (e.getKeyCode() == 192) {                      // фигово реализоваанное подключение фигово реализованного чита
                UzeTank1.typeOfEventCheat = true;
            }
            if (e.getKeyCode() == 104) {
                if ((System.currentTimeMillis() - UzeTank2.LastShotTime) * 0.001 > UzeTank2.RechargeTime && stage.equals("game")) {
                    UzeTank2.LastShotTime = System.currentTimeMillis();
                    Bullet bullet = new Bullet(UzeTank2.x, UzeTank2.y, keyBoardGun.Angle, 2);
                    bullets.add(bullet);
                    tread_BOOM();
                }
            }
            if (e.getKeyCode() == 100) {
                keyBoardGun.typeOfEvent4 = true;
            }
            if (e.getKeyCode() == 102) {
                keyBoardGun.typeOfEvent6 = true;
            }
        }
        if (e.getID() == KeyEvent.KEY_RELEASED) {
            if (e.getKeyCode() == 100) {
                keyBoardGun.typeOfEvent4 = false;
            }
            if (e.getKeyCode() == 102) {
                keyBoardGun.typeOfEvent6 = false;
            }
        }


        if (e.getID() == KeyEvent.KEY_PRESSED){            //  creator              работает только при нажатии
            if (e.getKeyChar() == ']') {
                User="creator";
            }
        }
        if (e.getID() == KeyEvent.KEY_RELEASED) {
            if (e.getKeyChar() == ']') {
                User="gamer";
            }
        }                                                   // end creator


        if (e.getID() == KeyEvent.KEY_PRESSED) {
            if (e.getKeyChar() == 'w') {
                UzeTank1.typeOfEventW = true;
            }
            if (e.getKeyChar() == 'a') {
                UzeTank1.typeOfEventA = true;
            }
            if (e.getKeyChar() == 's') {
                UzeTank1.typeOfEventS = true;
            }
            if (e.getKeyChar() == 'd') {
                UzeTank1.typeOfEventD = true;
            }


            if (e.getKeyCode() == 38) {                //////ijkl                            46   лево права дуло                 "стрелочка вправо" выстрел
                UzeTank2.typeOfEventW = true;
            }
            if (e.getKeyCode() == 37) {
                UzeTank2.typeOfEventA = true;
            }
            if (e.getKeyCode() == 40) {
                UzeTank2.typeOfEventS = true;
            }
            if (e.getKeyCode() == 39) {
                UzeTank2.typeOfEventD = true;
            }
        }
        if (e.getID() == KeyEvent.KEY_RELEASED) {
            if (e.getKeyCode() == 27) {                          // чит
                UzeTank1.typeOfEventCheat = false;
            }

            if (e.getKeyChar() == 'w') {
                UzeTank1.typeOfEventW = false;
            }
            if (e.getKeyChar() == 'a') {
                UzeTank1.typeOfEventA = false;
            }
            if (e.getKeyChar() == 's') {
                UzeTank1.typeOfEventS = false;
            }
            if (e.getKeyChar() == 'd') {
                UzeTank1.typeOfEventD = false;
            }
            if (e.getKeyCode() == 38) {                //////ijkl                            46   лево права дуло                 "стрелочка вправо" выстрел
                UzeTank2.typeOfEventW = false;
            }
            if (e.getKeyCode() == 37) {
                UzeTank2.typeOfEventA = false;
            }
            if (e.getKeyCode() == 40) {
                UzeTank2.typeOfEventS = false;
            }
            if (e.getKeyCode() == 39) {
                UzeTank2.typeOfEventD = false;
            }
        }
        return false;
    }

        public void UPTime (Graphics g){
        time = System.currentTimeMillis() - StartTime;
        g.drawString(Integer.toString((int) (time / 1000)), 100, 100);

    }
    public void ButtonControl(){
//        Button buttonInBattle=null;// = new Button(PanelWidth * 0.5-800, 10,400, 150, "InBattle", imageButton1ToBattle, imageButton1ToBattle);
        if(stage.equals("StartMenu") && buttons.size()<1){
            Button buttonInBattle0=new Button(PanelWidth * 0.5-200, 10, 400, 200, "InBattle", imageButton1ToBattle, imageButton1ToBattle);
            buttonInBattle = buttonInBattle0;
            buttons.add(buttonInBattle);
        }if(!stage.equals("StartMenu")){
            if(buttonInBattle!=null) {
                buttons.remove(buttonInBattle);
            }
        }

        if(stage.equals("StartMenu") && buttons.size()<2){        // старт 1 на 1
            buttonStart1on1 = new Button(PanelWidth * 0.5-150, PanelHeight*0.5-50,300,100,"Start1on1", Start1on1button1 , Start1on1button2);
            buttons.add(buttonStart1on1);
        }if(!stage.equals("StartMenu")){
            buttons.remove(buttonStart1on1);
        }

        if(buttons.size()<1 && !stage.equals("StartMenu")) {
            Button button = new Button(PanelWidth * 0.5, 1,75,75,"pause", PausBotton1, PausBotton2);
            buttons.add(button);
        }

        if(stage.equals("menu") && buttons.size()<2){
            buttonReply = new Button(PanelWidth * 0.5-200, 10,75,75,"reply", ReplyButton1, ReplyButton2);
            buttons.add(buttonReply);
        }
        if(!stage.equals("menu")){
            if(buttonReply!=null) {
                buttons.remove(buttonReply);
            }
        }



    }

    public void UPgametime (Graphics g){

        gametime =5 ;
        g.drawString(Integer.toString((int) (time / 1000)), 800, 100);
    }
        public void BotControl (Tank tank1, Tank tank2, ArrayList < Wall > walls) throws IOException {
        if (time > 3000 && sumBotTank < 0) {
            BufferedImage image = ImageIO.read(new File("imgs\\RedTank.jpg"));
            BotTank botTank1 = new BotTank(550, 111 * sumBotTank + 400, 1, 0.05, 900, 3, image, tank1, tank2);
            BotTanks.add(sumBotTank, botTank1);
//            BotGun botGun1= new BotGun(0.2, UzeTank1, UzeTank2);
//            BotGuns.add(sumBotTank, botGun1);
            sumBotTank++;
        }
        for (int i = 0; i < BotTanks.size(); i++) {
            BotTank botTank = BotTanks.get(i);
            if (botTank.botGun.typeOfEventFire && (((System.currentTimeMillis() - botTank.LastShotTime) * 0.001) % 1000) > botTank.RechargeTime) {
                botTank.LastShotTime = System.currentTimeMillis();
                Bullet bullet = new Bullet(botTank.x, botTank.y, botTank.botGun.Angle, 11);
                bullets.add(bullet);
            }
        }

    }

    public  void GameReply () throws IOException {
        Tank Tank1Del=new Tank(Tank1.x, Tank1.y, Tank1.VMaxUze, Tank1.a, Tank1.HitPoints, Tank1.laja, Tank1.RechargeTime);
        UzeTank1=Tank1;
        Tank1=Tank1Del;
        Tank Tank2Del=new Tank(Tank2.x, Tank2.y, Tank2.VMaxUze, Tank2.a, Tank2.HitPoints, Tank2.laja, Tank2.RechargeTime);
        UzeTank2=Tank2;
        Tank2=Tank2Del;

        walls=new ArrayList<>();

        while (scanner.hasNextLine()) {             // бежим по строкам пока они есть
            String s=scanner.nextLine();
            if(s.startsWith("Wall wall = new Wall")){

                BufferedImage nameImage= TestWall;

                ArrayList<Integer> v = new ArrayList<>();

                int NumberCin=0;
                for(int i=0; i<s.length();i++){  // бежим по конкретной строке
                    char c = s.charAt(i);
                    String digit="";
                    while(Character.isDigit(c)) {
                        digit += c;
                        i++;
                        c=s.charAt(i);
                    }
                    if(!digit.equals("")) {
                        v.add(parseInt(digit));
                    }
                }
                if(v.size()==5) {
                    Wall wall = new Wall(v.get(0), v.get(1), v.get(2), v.get(3), nameImage, v.get(4));
                    walls.add(wall);
                }else{
                    System.out.println("Что то не так с базой данных стенок!!!");
                }
            }
        }

    }



        public void updateCollisions (Graphics g){
        int[] Tank1X = UzeTank1.getTankX();
        int[] Tank1Y = UzeTank1.getTankY();

        int[] Tank2X = UzeTank2.getTankX();
        int[] Tank2Y = UzeTank2.getTankY();
        g.setColor(new Color(252, 252, 252));            // это центр
        g.fillOval((int) (UzeTank1.x - 1), (int) (UzeTank1.y - 1), 2, 2);


        Polygon P_tank1 = new Polygon(Tank1X, Tank1Y, 4);
        Polygon P_tank2 = new Polygon(Tank2X, Tank2Y, 4);

        for (int i = 0; i < walls.size(); i++) {
            Wall wall = walls.get(i);


            if (P_tank1.intersects(wall.x, wall.y, wall.width, wall.height)) {        //если пересекаются..

                if (P_tank1.intersects(wall.x, wall.y + wall.height, wall.width, 1)) {
                    this.UzeTank1.y += Math.abs(this.UzeTank1.vy) ;
                }
                if (P_tank1.intersects(wall.x, wall.y, 1, wall.height)) {
                    this.UzeTank1.x -= Math.abs(this.UzeTank1.vy) ;
                }
                if (P_tank1.intersects(wall.x, wall.y, wall.width, 1)) {
                    this.UzeTank1.y -= Math.abs(this.UzeTank1.vy) ;
                }
                if (P_tank1.intersects(wall.x + wall.width, wall.y, 1, wall.height)) {
                    this.UzeTank1.x += Math.abs(this.UzeTank1.vy) ;
                }
                g.setColor(new Color(55, 250, 31, 255));
                g.fillRect(10, 10, 1200, 20);
            }
            if (P_tank2.intersects(wall.x, wall.y, wall.width, wall.height)) {        //если пересекаются..

                if (P_tank2.intersects(wall.x, wall.y + wall.height, wall.width, 1)) {
                    this.UzeTank2.y += Math.abs(this.UzeTank2.vy) ;
                }
                if (P_tank2.intersects(wall.x, wall.y, 1, wall.height)) {
                    this.UzeTank2.x -= Math.abs(this.UzeTank2.vy) ;
                }
                if (P_tank2.intersects(wall.x, wall.y, wall.width, 1)) {
                    this.UzeTank2.y -= Math.abs(this.UzeTank2.vy) ;
                }
                if (P_tank2.intersects(wall.x + wall.width, wall.y, 1, wall.height)) {
                    this.UzeTank2.x += Math.abs(this.UzeTank2.vy) ;
                }
                g.setColor(new Color(55, 250, 31, 255));
                g.fillRect(10, 10, 1200, 20);
            }

            for (int j = 0; j < BotTanks.size(); j++) {                                                               // пересечение ботиков со стенкой
                Polygon P_botTank = new Polygon(BotTanks.get(j).getTankX(), BotTanks.get(j).getTankY(), 4);

                if (P_botTank.intersects(wall.x, wall.y, wall.width, wall.height)) {        //если пересекаются..

                    if (P_botTank.intersects(wall.x, wall.y + wall.height, wall.width, 1)) {
                        this.BotTanks.get(j).y += Math.abs(this.UzeTank1.vy) ;
                    }
                    if (P_botTank.intersects(wall.x, wall.y, 1, wall.height)) {
                        this.BotTanks.get(j).x -= Math.abs(this.UzeTank1.vy) ;
                    }
                    if (P_botTank.intersects(wall.x, wall.y, wall.width, 1)) {
                        this.BotTanks.get(j).y -= Math.abs(this.UzeTank1.vy) ;
                    }
                    if (P_botTank.intersects(wall.x + wall.width, wall.y, 1, wall.height)) {
                        this.BotTanks.get(j).x += Math.abs(this.UzeTank1.vy) ;
                    }
                }
            }
        }

    }



        public void GunAngle () {                                             ////// not UzeTank2
        double Angle1 = MouseX - UzeTank1.x;
        double Angle2 = UzeTank1.y - MouseY;
        double angle = 90 - Math.toDegrees(Math.atan2(Angle2, Angle1));
        if (angle < 0) {
            gun1.MouseAngle = 360 + angle;
        } else {
            gun1.MouseAngle = angle;
        }
        gun1.TankVAngle = UzeTank1.VAngle;
//        for(long i = 0; i<100000; i++){
//            for(long j = 0; j<10000; j++){
//
//            }
//        }

    }
        public void BulletList (Graphics g){                             ////////////////////// это со стенками
        for (Bullet bullet : bullets) { //bullets.get(i) = bullet
            bullet.paint(g);
            bullet.update();
        }
        for (int i = 0; i < walls.size(); i++) {
            Wall wall = walls.get(i);
            int[] WallX = new int[]{
                    wall.x,
                    wall.x + wall.width,
                    wall.x + wall.width,
                    wall.x};
            int[] WallY = new int[]{
                    wall.y,
                    wall.y,
                    wall.y + wall.height,
                    wall.y + wall.height};
            Polygon P_wall = new Polygon(WallX, WallY, 4);
            for (int k = 0; k < bullets.size(); k++) {
                if ((P_wall.intersects(bullets.get(k).x, bullets.get(k).y, 10, 10))) {
                    wall.BoxHP -= bullets.get(k).Damage;
                    if (wall.BoxHP <= 0) {
                        walls.remove(i);
                        --i;
                    }
                    bullets.remove(k);
                    --k;
                }
            }
        }
    }
//    public double SetAngle(){
//
//    }
    public boolean lineIntersects(double ax1,double ay1,double ax2,double ay2,double bx1,double by1,double bx2,double by2){
        return Math.signum((ax2 - ax1) * (by2 - ay1) - (ay2 - ay1) * (bx2 - ax1)) != Math.signum((ax2 - ax1) * (by1 - ay1) - (ay2 - ay1) * (bx1 - ax1))
                && Math.signum((bx2 - bx1) * (ay1 - by1) - (by2 - by1) * (ax1 - bx1)) != Math.signum((bx2 - bx1) * (ay2 - by1) - (by2 - by1) * (ax2 - bx1));
    }//                          ax*by-ay*bx

        public void HitCheck (Graphics g) {
        int[] Tank1X = UzeTank1.getTankX();
        int[] Tank1Y = UzeTank1.getTankY();
        int[] Tank2X = UzeTank2.getTankX();
        int[] Tank2Y = UzeTank2.getTankY();
        Polygon P_tank2 = new Polygon(Tank2X, Tank2Y, 4);
        Polygon P_tank1 = new Polygon(Tank1X, Tank1Y, 4);
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (P_tank1.intersects(bullet.x, bullet.y, 10, 10) && bullet.IndicationTank != 1) {
                if(!UzeTank1.typeOfEventCheat) {
                    this.UzeTank1.HitPoints -= bullets.get(i).Damage;                                                                               // отстойненько т.к. размер пули не читается
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








//            if (P_tank1.intersects(bullet.x, bullet.y, 10, 10) && bullet.IndicationTank != 1) {
//                threadYest_probitie();
//                for (int v = 1; v < 5; v++) {
//                    int nextv;
//                    if (v == 4) {
//                        nextv = 1;
//                    } else {
//                        nextv = v + 1;
//                    }
//                    int[] TankX = UzeTank1.getTankX(v, nextv);
//                    int[] TankY = UzeTank1.getTankY(v, nextv);
//                    Polygon P_t1 = new Polygon(TankX, TankY, 2);
//                    if (P_t1.intersects(bullet.x, bullet.y, 10, 10) && bullet.IndicationTank != 1) {
//
//                        StringPaint stringPaint1 = new StringPaint(Integer.toString(v) + nextv, 100, 500, 2500);
//                        stringPaint.add(stringPaint1);
//                        if (!UzeTank1.typeOfEventCheat) {
//                            this.UzeTank1.HitPoints -= bullets.get(i).Damage;                                                                               // отстойненько т.к. размер пули не читается
//                            bullets.remove(i);
//                        } else {
//                            bullets.remove(i);
//                        }
//                        if (i == 0) {     //этот иф призван сюда исправить баги с размером массива и удаление последнего элемента
//                            break;
//                        } else {
//                            i -= 1;
//                        }
//                    }
//                }
////                if (!UzeTank1.typeOfEventCheat ) {
////                    this.UzeTank1.HitPoints -= bullets.get(i).Damage;                                                                               // отстойненько т.к. размер пули не читается
////                    bullets.remove(i);
////                } else {
////                    bullets.remove(i);
////
////                }
////                if (i == 0) {     //этот иф призван сюда исправить баги с размером массива и удаление последнего элемента
////                    break;
////                } else {
////                    i -= 1;
////                }
//            }

            if (bullets.size() != 0 && P_tank2.intersects(bullets.get(i).x, bullets.get(i).y, 10, 10) && bullets.get(i).IndicationTank != 2) {                   // отстойненько т.к. размер пули не читается
                this.UzeTank2.HitPoints -= bullets.get(i).Damage;                                                                               // отстойненько т.к. размер пули не читается
                bullets.remove(i);
                if (i == 0) {     //этот иф призван сюда исправить баги с размером массива и удаление последнего элемента
                    break;
                } else {
                    i -= 1;
                }
            }

            for (int s = 0; s < BotTanks.size(); s++) {
                Polygon P_botTank = new Polygon(BotTanks.get(s).getTankX(), BotTanks.get(s).getTankY(), 4);
                if (bullets.size() != 0 && P_botTank.intersects(bullets.get(i).x, bullets.get(i).y, 10, 10) && (bullets.get(i).IndicationTank != 11)) {                   // отстойненько т.к. размер пули не читается
                    threadYest_probitie();
                    BotTanks.get(s).HitPoints -= bullets.get(i).Damage;
                    bullets.remove(i);
                    if (BotTanks.get(s).HitPoints <= 0) {
                        BotTanks.remove(s);
                        s -= 1;
                        sumBotTank -= 1;
                    }
                    if (i == 0) {     //этот иф призван сюда исправить баги с размером массива и удаление последнего элемента
                        break;
                    } else {
                        i -= 1;
                    }
                }
            }
        }}
        @Override
        protected void paintComponent (Graphics g) {
            super.paintComponent(g);
//            g.drawImage(imageFon, 0, 0,this.getWidth(), this.getHeight(), 0, 0,imagemenu.getWidth(), imagemenu.getHeight(), null );
            g.drawImage(imageFon, 0, 0,this.getWidth(), this.getHeight(), null );
            PanelWidth=this.getWidth();
            PanelHeight=this.getHeight();
            UPTime(g);
            if(time<1500){
                if(time>100){
                    stage="StartMenu";
                }
            }










            if (stage.equals("game")) {

                // это вставила джава
                try {
                    BotControl(UzeTank1, UzeTank2, walls);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                UPgametime(g);
                updateCollisions(g);
                GunAngle();
                UzeTank1.UpdatePlace();
                UzeTank2.UpdatePlace();          ///

                gun1.UpdatePlace();
                keyBoardGun.UpdatePlace();
                BulletList(g);
                HitCheck(g);


                UzeTank1.paint(g);
                UzeTank2.paint(g);                    ///

                gun1.paint(g, UzeTank1.x, UzeTank1.y);
                keyBoardGun.paint(g, UzeTank2.x, UzeTank2.y);
                for (int i = 0; i < stringPaint.size(); i++) {
                    stringPaint.get(i).paint(g);
                    if (!stringPaint.get(i).death) {
                        stringPaint.remove(i);
                    }
                }
                if(UzeTank1.StagePanel.equals("menu") || UzeTank2.StagePanel.equals("menu")){
                    stage="menu";
                }


                for (int i = 0; i < walls.size(); i++) {
                    walls.get(i).paint(g);
                }
//                for(int i=0; i<buttons.size(); i++){
//                    if(buttons.get(i).Name.equals("pause")){
//                        buttons.get(i).paint(g);
//                    }
//                }
                for (int i = 0; i < BotTanks.size(); i++) {
                    BotTanks.get(i).UpdatePlace();
                    BotTanks.get(i).paint(g);
//            BotGuns.get(i).UpdatePlace();
//            BotGuns.get(i).paint(g,BotTanks.get(i).x,BotTanks.get(i).y );
//            System.out.println(BotTanks.size());
//            System.out.println(BotGuns.size());

                }
                g.drawPolygon(UzeTank1.getTankX(), UzeTank1.getTankY(), 4);
                g.setColor(new Color(6, 214, 241, 255));
                g.drawLine((int) (UzeTank1.getTankX(1)), (int) UzeTank1.getTankY(1), (int) UzeTank1.getTankX(2), (int) UzeTank1.getTankY(2));

                //g.drawLine((int)(UzeTank1.getTankX(2)), (int)UzeTank1.getTankY(2), (int)UzeTank1.getTankX(3), (int)UzeTank1.getTankY(3));
            } else
            {
                if(stage.equals("start")){
                    g.drawImage(imagestart, 0, 0,this.getWidth(), this.getHeight(), 0, 0, imagestart.getWidth(), imagestart.getHeight(), null );
                }
                if(stage.equals("menu")){
                    g.drawImage(imagemenu, 0, 0,this.getWidth(), this.getHeight(), 0, 0,imagemenu.getWidth(), imagemenu.getHeight(), null );
                }
                if(stage.equals("StartMenu")){
                    g.drawImage(imageStartMenu, 0, 0,this.getWidth(), this.getHeight(),null );
                }
            }
            ButtonControl();
            for(int i=0; i<buttons.size(); i++){
                buttons.get(i).paint(g);
            }

            g.drawPolygon(ploika);
        }


    }

