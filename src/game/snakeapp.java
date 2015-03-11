package game;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class snakeapp extends JApplet {
    CardLayout cardlayout;
    JPanel bgpanel,menu,game,result,drawpanel;
    static int gameover =0;
    static char c;
    static int gamerunning =0;
    static int counter =0;
    static int score ;
    static int steps =0;
    static int playerReady=0;
    board b ;
    snake s ;
    
    public void init() {
        
        setSize(500,500);
        
        bgpanel = new JPanel();
        bgpanel.setDoubleBuffered(true);
        bgpanel.setBackground(Color.white);
        
        //we use card layout to show different screens such as menu , game, result ect
        cardlayout = new CardLayout();
        bgpanel.setLayout(cardlayout);
        add(bgpanel);
        
        //now we create various menus to add to be added
        menu = new JPanel();
        menu.setBackground(Color.white);
            //function to create buttons and other things inside the menu pane
        prepareMenus(menu);
        bgpanel.add(menu,"Menus");
        
        game = new JPanel();
        bgpanel.add(game,"GameScreen");
        game.setBackground(Color.white);
        
        result = new JPanel();
        bgpanel.add(result,"ResultScreen");
        result.setBackground(Color.white);
        
        //starts with menu
        cardlayout.show(bgpanel,"Menus");
        
    }
   
   
   
   
   
    
    void prepareMenus(JPanel menu){
        
        BorderLayout bl =new BorderLayout();
        menu.setLayout(bl);
        
        JLabel gamename = new JLabel();
        gamename.setBackground(Color.white);
        gamename.setForeground(Color.blue);
        Font flbl = new Font("Comic Sans Ms",Font.BOLD,28);
        gamename.setFont(flbl);
        gamename.setAlignmentX(CENTER_ALIGNMENT);
        gamename.setText("            SNAKE By Ashish");
        menu.add(gamename, BorderLayout.PAGE_START);
        
        
        JButton btn_start = new JButton();
        Font f = new Font("Comic Sans MS", Font.PLAIN, 28);
        btn_start.setFont(f);
        btn_start.setForeground(Color.red);
        btn_start.setBackground(Color.white);
        btn_start.setText("Start Game");
        btn_start.addActionListener(new startgamelistener());
        menu.add(btn_start, BorderLayout.CENTER);
        
        JButton btn_exit = new JButton();
        btn_exit.setFont(f);
        btn_exit.setForeground(Color.red);
        btn_exit.setBackground(Color.white);
        btn_exit.setText("Exit");
        btn_exit.addActionListener(new endgamelistener());
        menu.add(btn_exit, BorderLayout.SOUTH);
    
    }
    void prepareGame(JPanel screen){
        
        BorderLayout bl =new BorderLayout();
        screen.setLayout(bl);
        
        JLabel gamename = new JLabel();
        gamename.setBackground(Color.white);
        gamename.setForeground(Color.blue);
        Font flbl = new Font("Comic Sans Ms",Font.BOLD,28);
        gamename.setFont(flbl);
        gamename.setAlignmentX(CENTER_ALIGNMENT);
        gamename.setText("            SNAKE By Ashish");
        screen.add(gamename, BorderLayout.PAGE_START);
        Font f = new Font("Comic Sans MS", Font.PLAIN, 28);
        JButton btn_exit = new JButton();
        btn_exit.setFont(f);
        btn_exit.setForeground(Color.red);
        btn_exit.setBackground(Color.white);
        btn_exit.setText("Exit");
        btn_exit.addActionListener(new endgamelistener());
        screen.add(btn_exit, BorderLayout.SOUTH);
        
        
        drawpanel = new MyCustomPanel();
        drawpanel.setBackground(Color.white);
        drawpanel.setFocusable(true);
        keyboardinputlistener k = new keyboardinputlistener();
        addKeyListener(k);
        drawpanel.addKeyListener(k);
        screen.add(drawpanel,BorderLayout.CENTER);
        
        //here we start with difficulty 2
        b = new board(2);
        //create snake at 0,0 with length 3
        s = new snake(new point(0,0),3);
        gamerunning=0;
        new snakethread();
        //repaint();
    }
    
    class MyCustomPanel extends JPanel {
        public MyCustomPanel() {
            
        }

        
        public void paintComponent(Graphics g) {
            getContentPane().setBackground(Color.white);
            Dimension d = getSize();
            g.setColor(Color.white);
            g.fillRect(0,0,d.width,d.height);
            
            int initial_x = 25,initial_y=35;
            
            Font stringFont = new Font( "SansSerif", Font.PLAIN, 28 );
            g.setFont(stringFont);
            
            if(gameover==1){
            g.setColor(Color.red);
            g.drawString("Game OVER",180,initial_y+13*20+80);
            }
            stringFont = new Font( "SansSerif", Font.PLAIN, 18 );
            g.setFont(stringFont);
            g.setColor(Color.BLACK);
            score= counter/10;
            g.drawString("Key pressed = "+c,initial_x+5,initial_y+5);
            g.drawString("Score "+score,initial_x+5,initial_y+25);
            counter++;
            //draws border
            g.drawRect(initial_x-7+20, initial_y-13+50,21*20,13*20);
            g.drawRect(initial_x-9+20, initial_y-16+50,21*20+5,13*20+5);
            int y = initial_y+53;
            
            
            for(int i=0;i<13;i++){
                int x= initial_x+22;
                for(int j=0;j<21;j++){
                    //g.drawImage(null, x, y, rootPane);
                    if(b.bs[i][j].equals("-")){
                        stringFont = new Font( "SansSerif", Font.PLAIN, 10 );
                        g.setFont(stringFont);
                        g.setColor(Color.black);
                        g.drawString(""+b.bs[i][j]+"",x,y);
                    }
                    if(b.bs[i][j].equals("X"))
                    {
                        stringFont = new Font( "SansSerif", Font.BOLD, 16 );
                        g.setFont(stringFont);
                        g.setColor(Color.BLUE);
                        g.drawString(""+b.bs[i][j]+"",x,y);
                    }
                    if(b.bs[i][j].equals("$"))
                    {
                        stringFont = new Font( "SansSerif", Font.BOLD, 13 );
                        g.setFont(stringFont);
                        g.setColor(Color.magenta);
                        g.drawString(""+b.bs[i][j]+"",x,y);
                    }
                    
                    x+=20;
                }
                y+=20;
            }
            
            
        }
    }
    

   class keyboardinputlistener implements KeyListener{
        @Override
        public void keyPressed(KeyEvent e) {
            if(gameover==0){
                
                c=e.getKeyChar();
                int keytyped=e.getKeyCode();
                System.out.println("the key typed is :"+c);
                System.out.println("keycode:"+e.getKeyCode());
                int mov=0;
                if((keytyped ==KeyEvent.VK_S || keytyped ==KeyEvent.VK_DOWN)&&snake.prevh!='U'){snake.prevh='D';mov=1;}
                if((keytyped ==KeyEvent.VK_W || keytyped ==KeyEvent.VK_UP)&&snake.prevh!='D'){snake.prevh='U';mov=1;}
                if((keytyped ==KeyEvent.VK_A || keytyped ==KeyEvent.VK_LEFT)&&snake.prevh!='R'){snake.prevh='L';mov=1;}
                if((keytyped ==KeyEvent.VK_D || keytyped ==KeyEvent.VK_RIGHT)&&snake.prevh!='L'){snake.prevh='R';mov=1;}
                int m = s.check();
                if(m==1){gameover=1;System.out.println("collision");}
                drawpanel.repaint();
            }
        }
        @Override
        public void keyTyped(KeyEvent e) {
        }
        @Override
        public void keyReleased(KeyEvent e) {
            //to start the game as soon as a key has been released
            playerReady=1;
        }
   
   }    
    
        class startgamelistener implements ActionListener{
          public void actionPerformed(ActionEvent e) {
              System.out.println("Game Started");
                //starts with Game screen
                prepareGame(game);  
                cardlayout.show(bgpanel,"GameScreen");
                //repaint();
                
          }
        }
        class endgamelistener implements ActionListener{
          public void actionPerformed(ActionEvent e) {
              System.out.println("Game Ended");
              System.exit(0);
          }
        }
        
    class snakethread implements Runnable{
        Thread t;
        snakethread(){
        t=new Thread(this);
        t.start();
        System.out.println("thread 1 is alive : "+t.isAlive());
        }
        public void run(){
            System.out.println("thread 1 is alive : "+t.isAlive());
            while(s.headcollided==0 && gamerunning == 0){
                try {
                    Thread.sleep(300);

                    if(playerReady==1){
                        
                        s.move(snake.prevh);
                        int m = s.check();
                        if(m==1){gameover=1;System.out.println("collision");}
                        drawpanel.repaint();
                        System.out.println("snake running towards :"+snake.prevh);
                    }
                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(snakethread.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
}
        
}
class board{
static String bs[][]=new String[13][21];
static char b[][]=new char[13][21];
snake s;
int initiallength;
    board(int difficulty){
        
        // difficulty level should be less for creating less no of food
        // 1 is highest and 5 is least difficult
        int min =1;int max=150;
        if(difficulty>5){difficulty=5;}
        int seed = max - (difficulty*10);
        int food=0;
        Random rn = new Random();
        
        for(int i=0;i<13;i++){
            for(int j=0;j<21;j++){
                int k = rn.nextInt(max - min + 1) + min;
                if(k<=seed){b[i][j]='-';
                bs[i][j]="-";
                }
                if(k>seed){
                    b[i][j]='$';
                    bs[i][j]="$";
                    food++;
                }
            }
        }
        initiallength=4;
        //create a snake from 0,0
        System.out.println("no of food: "+food);
        printboard();
        
    }
    void printboard(){
        System.out.println("New board ");
        for(int i=0;i<b.length;i++){
            for(int j =0;j<b[i].length;j++){
                System.out.print(""+b[i][j]+" ");
            }
            System.out.println("");
        }
    }
}
class point{
    int y,x;
    point linkforward;
    
    public point(int y,int x){
        this.y=y;
        this.x=x;
        this.linkforward=null;
    }
}
class snake{
    static char prevh;
    Thread t;
    point head;
    point tail;
    int headcollided;
    public snake(point t,int initiallength){
        //s = new snake(new point(0,0));
      
    tail=t;
    head=t;
    headcollided=0;
        
    for(int j=0;j<initiallength;j++){
                    
                    int i=0;
                    //x to represent the snake
                    board.b[i][j]='X';
                    board.bs[i][j]="X";
                    if( i==0 && j==0 ){ }
                    else{
                    movehead(new point(i,j));
                    }
                    //coardinates of the head
                    
        }
    prevh='R';
    }
    
    
    int check(){
        
    int collision_x=head.x;
    int collision_y=head.y;
        if(headcollided==1){
        System.out.print(""+collision_x+" "+collision_y+" "); 
        return 1;
    }
    else return 0;
    }
    public void movetail(){
        //move tail to new second last point
        this.tail = tail.linkforward;
    }
    //insert at front of list
    public void movehead(point p){
        head.linkforward=p;
        this.head=p;
    }
    public void move(char c){
        int dx=0,dy=0;
        if(c=='U'){dx=0;dy=-1;}
        if(c=='D'){dx=0;dy=1;}
        if(c=='L'){dx=-1;dy=0;}
        if(c=='R'){dx=1;dy=0;}
        int headx=head.x;
        int heady=head.y;
        point tmpmov=new point(heady+dy,headx+dx);
        //check if head crosses boundary
        if((headx+dx<0)||(heady+dy<0)||(heady+dy>12)||(headx+dx>20))
        {
            headcollided=1;
        }
        //when head is within boundary
        else{
            
            if(board.b[heady+dy][headx+dx]=='$'){
                    movehead(tmpmov);
                    board.b[head.y][head.x]='X';
                    board.bs[head.y][head.x]="X";
            }
            else
            {
                movehead(tmpmov);
                board.b[head.y][head.x]='X';
                board.b[tail.y][tail.x]='-';
                board.bs[head.y][head.x]="X";
                board.bs[tail.y][tail.x]="-";
                movetail();
                
            }

        }

        
        //check if snake collided with itself
        point cur = tail;
        while(cur.linkforward!=null){
            if(cur.x==head.x && cur.y==head.y){
                headcollided=1;
                break;
            }
            cur=cur.linkforward;
        }
        
        
        
    
    }
}



/*
we will use this space to write code that might be used in future 

        //layout we will use will have 6 columns and as many rows as we want
        //GridLayout grid = new GridLayout(0,6);

*/