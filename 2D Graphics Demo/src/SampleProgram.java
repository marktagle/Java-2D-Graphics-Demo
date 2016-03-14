import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class SampleGameProgram {
    public int x = 0, y = 0, w = 50, h = 50;
    public Color clr = Color.RED;
    public int spX = 0, spY = 0;

    Color[] colors = {Color.RED, Color.BLUE, Color.YELLOW, Color.PINK, Color.ORANGE};
    int currcolor = 0;
    public SampleGameProgram(int nx, int ny, int nw, int nh, Color nClr)
    {
        x = nx;
        y = ny;
        w = nw;
        h = nh;
        clr = nClr;
    }

    public void changeColor(){
        currcolor++;
        if(currcolor > colors.length) currcolor = 0;
        clr = colors[currcolor];

    }

    public void moveBall(){
        x += spX;
        y += spY;
    }

}

class JavaSampleProgram extends JPanel implements KeyListener{

    int elapsedframes = 0;
    SampleGameProgram b = new SampleGameProgram(0, 0, 50, 50, Color.RED);

    public JavaSampleProgram(){
        addKeyListener(this);
        setFocusable(true);
        Thread td = new Thread(new Runnable(){
            public void run(){
                while(true){
                    try {
                        update();
                        render();
                        Thread.sleep(16);
                    } catch(Exception ex){

                    }
                }
            }
        });
        td.start();
    }

    boolean colorChanged = false;
    void update(){
        elapsedframes++;
        //65, 87, 83, 68
        //right
        if(keys[39] || keys[68]){
            b.spX = 10;
        }

        //left
        if(keys[37]  || keys[65]){
            b.spX = -10;
        }

        //up
        if(keys[38] || keys[87])
            b.spY = -10;

        if(keys[40] || keys[83])
            b.spY = 10;

        if(keys[37] == false && keys[39] == false
                && keys[68] == false && keys[65] == false){
            b.spX = 0;
        }
        if(keys[38] == false && keys[40] == false
                && keys[87] == false && keys[83] == false){
            b.spY = 0;
        }
        if(keys[32]) {
            if(colorChanged == false) {

                b.changeColor();
                colorChanged = true;
            }
        }
        if(keys[32] == false)
            colorChanged = false;

        b.moveBall();
    }

    void render(){

        repaint();
    }

    boolean[] keys = new boolean[256];
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        System.out.println(e.getKeyCode());
    }
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public void paintComponent(Graphics g){
        g.fillRect(0, 0, getWidth(), getHeight());

        //draw objects here.
        //Draw ball
        g.setColor(b.clr);
        g.fillOval(b.x, b.y, b.w, b.h);

        //Show elapsed frame counter
        g.setColor(Color.WHITE);
        g.drawString(elapsedframes + "", 0, 20);
    }


    public static void main(String[] args){
        JFrame wnd = new JFrame("2D Game Java");
        wnd.setSize(600, 480);
        wnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wnd.setContentPane(new JavaSampleProgram());
        wnd.setVisible(true);
        wnd.setLocationRelativeTo(null);
    }

}