import java.awt.*;

public class StringPaint {
    long StartTime;
    String s;
    int x;
    int y;
    int t;
    boolean death=true;
    public StringPaint(String s, double x, double y, int t){
        StartTime=System.currentTimeMillis();
        this.s=s;
        this.x=(int)x;
        this.y=(int)y;
        this.t=t;
    }
    public void paint(Graphics g) {
        if (System.currentTimeMillis()-StartTime<t) {
            g.drawString(s, x, y);
        } else {
            death=false;
        }
    }
}
