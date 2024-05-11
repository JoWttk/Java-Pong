package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements Runnable, KeyListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public static boolean CanClose = false;

    private static final int ROCKET_WIDTH = 15;
    private static final int ROCKET_HEIGHT = 70;
    private static final int ROCKET_SPEED = 5;

    private static final int BALL_SIZE = 15;

    private static int BALL_START_X = (Game.WIDTH / 2) - (Game.BALL_SIZE / 2);
    private static int BALL_START_Y = (Game.HEIGHT / 2) - (Game.BALL_SIZE / 2);
    private static int BALL_SPEED_X = 4;
    private static int BALL_SPEED_Y = 4;

    private static final int SCORE_Y = 50;

    private static final double UPS = 64.0D;
    private static final double FPS = 90.0D;

    private static final boolean[] KEYS = new boolean[65535];

    public static int color_num=0;

    private static Color BACKGROUND_COLOR = Color.BLACK;
    private static Color ROCKET_COLOR = Color.WHITE; //new Color(200, 200, 200);
    private static Color BALL_COLOR = Color.WHITE; // new Color(200, 200, 200);

    private static final Font FONT = new Font("Arial", Font.PLAIN, 36);

    private final Rectangle leftRocket;
    private final Rectangle rightRocket;
    private final Rectangle ball;

    public static int MaxScore = 5;

    private int leftScore;
    private int rightScore;

    private boolean gameStarted;

    public int Points(int points){
        return MaxScore=points;
    }

    public Game() {
        super(true);

        this.setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        this.setFocusable(true);
        this.addKeyListener(this);

        this.leftRocket = new Rectangle(0, (Game.HEIGHT / 2) - (Game.ROCKET_HEIGHT / 2), Game.ROCKET_WIDTH, Game.ROCKET_HEIGHT);
        this.rightRocket = new Rectangle(Game.WIDTH - Game.ROCKET_WIDTH, (Game.HEIGHT / 2) - (Game.ROCKET_HEIGHT / 2), Game.ROCKET_WIDTH, Game.ROCKET_HEIGHT);
        this.ball = new Rectangle(Game.BALL_START_X, Game.BALL_START_Y, Game.BALL_SIZE, Game.BALL_SIZE);
    }

    @Override
    public void run() {
        long initialTime = System.nanoTime();
        final double timeU = 1000000000 / Game.UPS;
        final double timeF = 1000000000 / Game.FPS;
        double deltaU = 0, deltaF = 0;
        int frames = 0, ticks = 0;
        long timer = System.currentTimeMillis();

        while(true) {
            long currentTime = System.nanoTime();
            deltaU += (currentTime - initialTime) / timeU;
            deltaF += (currentTime - initialTime) / timeF;
            initialTime = currentTime;

            if(deltaU >= 1) {
                this.update();
                ticks++;
                deltaU--;
            }

            if(deltaF >= 1) {
                this.repaint(0, 0, Game.WIDTH, Game.HEIGHT);
                frames++;
                deltaF--;
            }

            if(System.currentTimeMillis() - timer > 1000) {
                System.out.println("UPS: " + ticks + ", FPS: " + frames);
                frames = 0;
                ticks = 0;
                timer += 1000;
            }

            try {
                Thread.sleep(2L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sleep_window(JFrame window){
        try{
            Thread.sleep(1500);
            window.setVisible(false);
            window.dispose();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) { 
        Game.KEYS[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Game.KEYS[e.getKeyCode()] = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Game.BACKGROUND_COLOR);
        g2.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

        g2.setColor(Game.ROCKET_COLOR);
        g2.fillRect(this.leftRocket.x, this.leftRocket.y, Game.ROCKET_WIDTH, Game.ROCKET_HEIGHT);
        g2.fillRect(this.rightRocket.x, this.rightRocket.y, Game.ROCKET_WIDTH, Game.ROCKET_HEIGHT);

        g2.setColor(Game.BALL_COLOR);
        g2.fillOval(this.ball.x, this.ball.y, Game.BALL_SIZE, Game.BALL_SIZE);

        g2.setFont(Game.FONT);

        //String s = this.leftScore + "/"+MaxScore + " : " + this.rightScore+"/"+MaxScore;

        String s = null;
        String m = null;
        String g_ = null;

        if (this.gameStarted){
            s = this.leftScore + " : " + this.rightScore;
        }else{
            s = "Press Space to start";
        }

        if(Game.KEYS[KeyEvent.VK_1]) {
            PointsValue.Points=1;
        }else if(Game.KEYS[KeyEvent.VK_2]){
            PointsValue.Points=2;
        }else if(Game.KEYS[KeyEvent.VK_3]){
            PointsValue.Points=3;
        }else if(Game.KEYS[KeyEvent.VK_4]){
            PointsValue.Points=4;
        }else if(Game.KEYS[KeyEvent.VK_5]){
            PointsValue.Points=5;
        }else if(Game.KEYS[KeyEvent.VK_6]){
            PointsValue.Points=6;
        }else if(Game.KEYS[KeyEvent.VK_7]){
            PointsValue.Points=7;
        }else if(Game.KEYS[KeyEvent.VK_8]){
            PointsValue.Points=8;
        }else if(Game.KEYS[KeyEvent.VK_9]){
            PointsValue.Points=9;
        }

        if(!this.gameStarted){
            m = "     Game Points: "+PointsValue.Points;
            g_= "      Max Points: 9";

            g2.drawString(m, (0) - (0), 100);
            g2.drawString(g_, (0) - (0), 140);
        }

        FontMetrics fontMetrics = g2.getFontMetrics(Game.FONT);
        int w = fontMetrics.stringWidth(s);

        g2.drawString(s, (Game.WIDTH / 2) - (w / 2), Game.SCORE_Y);
        g2.dispose();
    }

    public void Winner(String s){
        JFrame WinnerWindow = new JFrame("Who wins?");
        JLabel win_label = new JLabel(s+" wins!",SwingConstants.CENTER);       

        win_label.setFont(new Font("DialogInput",Font.PLAIN,26));
        win_label.setForeground(Color.GREEN);
        
        WinnerWindow.setSize(324,94);
        WinnerWindow.add(win_label);
        WinnerWindow.setResizable(false);
        WinnerWindow.setLocationRelativeTo(null);
        WinnerWindow.getContentPane().setBackground(Color.BLACK);
        WinnerWindow.setVisible(true);

        sleep_window(WinnerWindow);
    }

    private void update() {
        if (this.leftScore>=MaxScore){
            this.gameStarted = false;
            CanClose = false;
            
            this.leftScore=0;
            this.rightScore=0;

            this.leftRocket.x=0;
            this.leftRocket.y=(Game.HEIGHT / 2) - (Game.ROCKET_HEIGHT / 2);

            this.rightRocket.x=Game.WIDTH - Game.ROCKET_WIDTH;
            this.rightRocket.y=(Game.HEIGHT / 2) - (Game.ROCKET_HEIGHT / 2);

            this.ball.x=(Game.WIDTH / 2) - (Game.BALL_SIZE / 2);
            this.ball.y=(Game.HEIGHT / 2) - (Game.BALL_SIZE / 2);

            Winner("Left");
        }
        else if (this.rightScore>=MaxScore){
            this.gameStarted = false;
            CanClose = false;

            this.leftScore=0;
            this.rightScore=0;

            this.leftRocket.x=0;
            this.leftRocket.y=(Game.HEIGHT / 2) - (Game.ROCKET_HEIGHT / 2);

            this.rightRocket.x=Game.WIDTH - Game.ROCKET_WIDTH;
            this.rightRocket.y=(Game.HEIGHT / 2) - (Game.ROCKET_HEIGHT / 2);

            this.ball.x=(Game.WIDTH / 2) - (Game.BALL_SIZE / 2);
            this.ball.y=(Game.HEIGHT / 2) - (Game.BALL_SIZE / 2);

            Winner("Right");
        }

        if(Game.KEYS[KeyEvent.VK_SPACE]) {
            this.gameStarted = true;
            Points(PointsValue.Points);
        }

        if (Game.KEYS[KeyEvent.VK_O]){
            PointsValue.Points = 3;
            System.out.println(PointsValue.Points);
        }

        if(Game.KEYS[KeyEvent.VK_ESCAPE] || Game.KEYS[KeyEvent.VK_C] && this.gameStarted){
            this.gameStarted = false;
            this.leftScore=0;
            this.rightScore=0;

            this.leftRocket.x=0;
            this.leftRocket.y=(Game.HEIGHT / 2) - (Game.ROCKET_HEIGHT / 2);

            this.rightRocket.x=Game.WIDTH - Game.ROCKET_WIDTH;
            this.rightRocket.y=(Game.HEIGHT / 2) - (Game.ROCKET_HEIGHT / 2);

            this.ball.x=(Game.WIDTH / 2) - (Game.BALL_SIZE / 2);
            this.ball.y=(Game.HEIGHT / 2) - (Game.BALL_SIZE / 2);
        }

        if(Game.KEYS[KeyEvent.VK_W] && this.gameStarted) {
            if(this.leftRocket.y > 0) {
                this.leftRocket.y -= Game.ROCKET_SPEED;
            }
        }
        if(Game.KEYS[KeyEvent.VK_S] && this.gameStarted) {
            if(this.leftRocket.y + Game.ROCKET_HEIGHT < Game.HEIGHT) {
                this.leftRocket.y += Game.ROCKET_SPEED;
            }
        }

        if(Game.KEYS[KeyEvent.VK_UP] && this.gameStarted) {
            if(this.rightRocket.y > 0) {
                this.rightRocket.y -= Game.ROCKET_SPEED;
            }
        }
        if(Game.KEYS[KeyEvent.VK_DOWN] && this.gameStarted) {
            if(this.rightRocket.y + Game.ROCKET_HEIGHT < Game.HEIGHT) {
                this.rightRocket.y += Game.ROCKET_SPEED;
            }
        }

        if(this.gameStarted) {
            CanClose = true;
            this.ball.x += Game.BALL_SPEED_X;
            this.ball.y += Game.BALL_SPEED_Y;

            if(this.ball.y + Game.BALL_SIZE > Game.HEIGHT) {
                Game.BALL_SPEED_Y *= -1;
            }
            if(this.ball.y + (Game.BALL_SIZE / 2) < 0) {
                Game.BALL_SPEED_Y *= -1;
            }

            if(this.ball.x + (Game.BALL_SIZE / 2) > Game.WIDTH) {
                this.leftScore++;
                Game.BALL_SPEED_X *= -1;

                this.ball.x = Game.BALL_START_X;
                this.ball.y = Game.BALL_START_Y;
            }
            if(this.ball.x < 0) {
                this.rightScore++;
                Game.BALL_SPEED_X *= -1;

                this.ball.x = Game.BALL_START_X;
                this.ball.y = Game.BALL_START_Y/2;
            } 

            if(this.ball.x < this.leftRocket.x + Game.ROCKET_WIDTH && this.ball.y > this.leftRocket.y && this.ball.y <= this.leftRocket.y + Game.ROCKET_HEIGHT) {
                Game.BALL_SPEED_X *= -1;
            }

            if(this.ball.x + Game.BALL_SIZE > this.rightRocket.x && this.ball.y > this.rightRocket.y && this.ball.y <= this.rightRocket.y + Game.ROCKET_HEIGHT) {
                Game.BALL_SPEED_X *= -1;
            }
        }
    }

    private void start() {
        new Thread(this).start();
    }

    public static void main(String[] args) {
        Game pong = new Game();
        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(pong);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        pong.start();
    }
}