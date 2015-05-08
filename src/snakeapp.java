import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

public class snakeapp {
    
    public static void main(String s[]) throws URISyntaxException {
        JFrame frame = new JFrame("Snake Game v1.0");
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //sets window to the center of the screen
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        
        snakeapp fa = new snakeapp();
        fa.init(frame);
        
        // code for displaying icon 
                BufferedImage img = null;
                try {

                    URL icon_location = fa.getClass().getResource("/icon_snake.png");
                    URI loc = new URI(icon_location.toString());
                    File f = new File(loc);
                    img = ImageIO.read(f);

                } 
                catch (IOException e) {
                    System.out.println("Icon Image could not be loaded");
                }
        frame.setIconImage(img);
        frame.setVisible(true);
    
    }
    
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
    board b_read;
    snake s_read;
    String data[][];
    static int dataloaded=0;
    public void init(JFrame frame) {
        
        
        bgpanel = new JPanel();
        bgpanel.setDoubleBuffered(true);
        bgpanel.setBackground(Color.white);
        bgpanel.setSize(500, 500);
        
        //we use card layout to show different screens such as menu , game, result ect
        cardlayout = new CardLayout();
        bgpanel.setLayout(cardlayout);
        
        frame.add(bgpanel);
        
        //now we create various menus to add to be added
        menu = new JPanel();
        menu.setBackground(Color.white);
        
        
        bgpanel.add(menu,"Menus");
        
        game = new JPanel();
        bgpanel.add(game,"GameScreen");
        game.setBackground(Color.white);
        
        result = new JPanel();
        result.setOpaque(true);
        bgpanel.add(result,"ResultScreen");
        result.setBackground(Color.white);
        
        //function to create buttons and other things inside the menu pane
        prepareMenus(menu);
        //starts with menu
        cardlayout.show(bgpanel,"Menus");
        
        
    }
    void showresultscreen(JPanel result,int score,String name){
        GridBagLayout gbl = new GridBagLayout();
        result.setLayout(gbl);
        result.setBackground(Color.white);
        result.setOpaque(true);
        GridBagConstraints c = new GridBagConstraints();
        JPanel screen=new JPanel(); 
        JLabel lbl = new JLabel("Scores");
        
        result.add(lbl,c);
        c.gridx=0;
        c.gridy=3;
        storeresult(name,score);
        
        if(dataloaded==1)
        {    
        
        String[] columnNames = {"Name","Score"};
        JTable table = new JTable(data, columnNames);
        cardlayout.show(bgpanel,"ResultScreen");
        c.gridy++;
        
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        screen.add(scrollPane);
        result.repaint();        
        result.add(screen,c);
        result.repaint();
        
        }
    }
    
    
    void prepareMenus(JPanel menu){
        
        //set layout 
            BorderLayout bl =new BorderLayout();
            
            GridBagLayout gbl = new GridBagLayout();
            menu.setLayout(gbl);
            //menu.setBackground(Color.red);
        //create a new gridbaglaout contraint that species where the components will be placed think of it as a table 
            GridBagConstraints c = new GridBagConstraints();
            
        //prepare title
            c.anchor=GridBagConstraints.CENTER;
            JLabel gamename = gamenamelbl();
            menu.add(gamename,c);
            c.gridx=0;
            c.gridy=3;
        //start button
            //places the start button in next row of the grid
            c.gridy++;
            c.weightx=0.5;
            JButton btn_start = startbtn();
            menu.add(btn_start,c);
        
        //load previous saved game button 
            c.gridy++;
            JButton btn_load = loadbtn();    
            menu.add(btn_load,c);
            
        //exit button
            
            c.weighty = 1.0;   //request any extra vertical space
            c.anchor = GridBagConstraints.PAGE_END; //bottom of space
            
            JButton btn_exit = exitbtn();
            btn_exit.setBorderPainted(false);
            menu.add(btn_exit,c);
    
    }
    JButton loadbtn(){
            JButton btn_load = new JButton();
            Font f = new Font("Comic Sans MS", Font.PLAIN, 26);
            btn_load.setFont(f);
            btn_load.setForeground(Color.red);
            btn_load.setBackground(Color.white);
            btn_load.setText("Load Game");
            btn_load.addActionListener(new loadgamelistener());
        
        return btn_load;
    }
    JButton startbtn(){
            JButton btn_start = new JButton();
            Font f = new Font("Comic Sans MS", Font.PLAIN, 28);
            btn_start.setFont(f);
            btn_start.setForeground(Color.red);
            btn_start.setBackground(Color.white);
            btn_start.setText("Start Game");
            btn_start.addActionListener(new startgamelistener());
        
        return btn_start;
    }
    JButton exitbtn(){
            JButton btn_exit = new JButton();
            Font f = new Font("Comic Sans MS", Font.PLAIN, 28);
            btn_exit.setFont(f);
            btn_exit.setForeground(Color.red);
            btn_exit.setBackground(Color.white);
            btn_exit.setText("Exit");
            btn_exit.addActionListener(new endgamelistener());
        
        return btn_exit;
    }
    JButton savebtn(){
            JButton btn_save = new JButton();
            Font f = new Font("Comic Sans MS", Font.PLAIN, 28);
            btn_save.setFont(f);
            btn_save.setForeground(Color.red);
            btn_save.setBackground(Color.white);
            btn_save.setText("Save Game");
            btn_save.addActionListener(new savegamelistener());
        
        return btn_save;
    }
    JLabel gamenamelbl(){
            JLabel gamename = new JLabel();
            gamename.setBackground(Color.white);
            gamename.setForeground(Color.blue);
            Font flbl = new Font("Comic Sans Ms",Font.BOLD,28);
            gamename.setFont(flbl);

            // :) was able to solve it using gridbaglayout
           

            gamename.setText("SNAKE By Ashish");
        
        return gamename;
    } 
    
    void prepareGame(JPanel screen,board b_input,snake s_input){
        //set layout     
            BorderLayout bl =new BorderLayout();
            screen.setLayout(bl);
            
        //prepare title
            JLabel gamename = gamenamelbl();
            screen.add(gamename, BorderLayout.PAGE_START);
        
        //exit button    
            JButton btn_exit = exitbtn();
            screen.add(btn_exit, BorderLayout.SOUTH);
        
        

        //here we start
        b = b_input;
        //create snake 
        s = s_input;
        score=b.score;    
        drawpanel = new MyCustomPanel();
        drawpanel.setBackground(Color.white);
        drawpanel.setFocusable(true);
        keyboardinputlistener k = new keyboardinputlistener();
        screen.addKeyListener(k);
        drawpanel.addKeyListener(k);
        //drawpanel.repaint();
        screen.add(drawpanel,BorderLayout.CENTER);
        
        //save button
            JButton btn_save = savebtn();
            drawpanel.add(btn_save, BorderLayout.PAGE_END);
            btn_save.setVisible(true);
        gamerunning=1;
        new snakethread(150);
        
    }
    
    class MyCustomPanel extends JPanel {
        public MyCustomPanel() {
            
        }

        
        public void paintComponent(Graphics g) {
            Dimension d = getSize();
            g.setColor(Color.white);
            g.fillRect(0,0,d.width,d.height);
            
            int initial_x = 30,initial_y=55;
            
            Font stringFont = new Font( "SansSerif", Font.PLAIN, 28 );
            g.setFont(stringFont);
            
            
            stringFont = new Font( "SansSerif", Font.PLAIN, 18 );
            g.setFont(stringFont);
            g.setColor(Color.BLACK);
            //score += counter/20;
            if (gamerunning==0){score = 0;}
            else{
            score = s.foods;
            }
            //g.drawString("Key pressed ="+c,initial_x+5,initial_y+5);
            int score_pos_x=initial_x+5;
            int score_pos_y=initial_y+5;
            g.drawString("Score : "+score+"",score_pos_x,score_pos_y);
            counter++;
            
            //draws border
            g.drawRect(initial_x-7+20-13, initial_y-13+50-5,21*20+10,13*20+10);
            g.drawRect(initial_x-9+20-13, initial_y-16+50-5,21*20+15,13*20+15);
            int y = initial_y+53;
            
            
            for(int i=0;i<13;i++){
                int x= initial_x+22;
                for(int j=0;j<21;j++){
                    
                    
                    
                    //g.drawImage(null, x, y, rootPane);
                    if(b.bs[i][j].equals("-")){
                        stringFont = new Font( "SansSerif", Font.PLAIN, 10 );
                        g.setFont(stringFont);
                        g.setColor(Color.black);
                        g.drawString(""+b.bs[i][j]+"",x-10,y-4);
                    }
                    if(b.bs[i][j].equals("X"))
                    {
                        stringFont = new Font( "SansSerif", Font.BOLD, 16 );
                        g.setFont(stringFont);
                        g.setColor(Color.BLUE);
                        
                        g.fillRoundRect(x-15, y-15, 17, 17, 0, 0);
                        g.setColor(Color.white);
                        g.drawRoundRect(x-14, y-14, 15, 15, 0, 0);
                        g.setColor(Color.black);
                        g.drawRoundRect(x-15, y-15, 17, 17, 0, 0);
                        
                        
                        //g.drawString(""+b.bs[i][j]+"",x,y);
                    }
                    if(b.bs[i][j].equals("$"))
                    {
                        stringFont = new Font( "SansSerif", Font.BOLD, 13 );
                        g.setFont(stringFont);
                        g.setColor(Color.magenta);
                        g.drawString(""+b.bs[i][j]+"",x-10,y-2);
                    }
                    
                    x+=20;
                }
                y+=20;
            }
            
            
            if(gameover==1){
                Graphics2D g2d = (Graphics2D) g;
                g.setColor(Color.white);
                g.fillRect(score_pos_x, score_pos_y-20,80,20);
                
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                        7 * 0.1f));
                g2d.setColor(Color.white);
                g2d.fillRect(initial_x-7+10, initial_y-13+50,21*20,13*20);
                stringFont = new Font( "SansSerif", Font.BOLD, 48 );
                g.setFont(stringFont);
                g2d.setColor(Color.white);
                g2d.fillRect(93,initial_y+13*8,21*15,13*6);
                g.setColor(Color.red);
                
                g.drawString("Game OVER",110,initial_y+13*7+70);
                g.setColor(Color.blue);
                g.drawString("Score : "+score,110,initial_y+13*7+70+50);
                
                String name = JOptionPane.showInputDialog("Enter your name");
                showresultscreen(result,score,name);
                
            }
            
        }
    }
    
    void storeresult(String name,int score){
        JOptionPane.showMessageDialog(null,"your NAme is "+name);
        
        try {
        
        String urlstr = "http://ashishpadalkar.atwebpages.com/snakescore.py";
        String urlcomplete = urlstr+"?name="+name+"&score="+score;
        System.out.println(""+urlcomplete);
        URL myURL = new URL(urlcomplete);
        URLConnection myURLConnection = myURL.openConnection();
        myURLConnection.connect();
        InputStream is =myURLConnection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        data=new String[1000][2];
        // read each line and write to System.out
        int k=0,j=0;
        while ((line = br.readLine()) != null) {
            
            if(k%2==0){data[j][0]=line;
                System.out.print("data["+j+"][0]="+line);
            }
            else{data[j][1]=line;
            j++;
                System.out.println("data["+j+"][1]="+line);
            }
            k++;
        }
        dataloaded=1;
          
        } catch (MalformedURLException ex) {
        Logger.getLogger(snakeapp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
        Logger.getLogger(snakeapp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

   class keyboardinputlistener implements KeyListener{
        @Override
        public void keyPressed(KeyEvent e) {
            if(gameover==0){
                
                c=e.getKeyChar();
                int keytyped=e.getKeyCode();
                //System.out.println("the key typed is :"+c);
                //System.out.println("keycode:"+e.getKeyCode());
                int mov=0;
                if((keytyped ==KeyEvent.VK_S || keytyped ==KeyEvent.VK_DOWN)&&s.prevh!='U'){s.prevh='D';mov=1;}
                if((keytyped ==KeyEvent.VK_W || keytyped ==KeyEvent.VK_UP)&&s.prevh!='D'){s.prevh='U';mov=1;}
                if((keytyped ==KeyEvent.VK_A || keytyped ==KeyEvent.VK_LEFT)&&s.prevh!='R'){s.prevh='L';mov=1;}
                if((keytyped ==KeyEvent.VK_D || keytyped ==KeyEvent.VK_RIGHT)&&s.prevh!='L'){s.prevh='R';mov=1;}
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
                board b_new = new board(2);
                snake s_new = new snake(new point(0,0),3);
                    
                prepareGame(game,b_new,s_new);  
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
        class savegamelistener implements ActionListener{
          public void actionPerformed(ActionEvent e) {
              try {
                        serialize(b,s);
                        System.out.println("Game Saved");
              } catch (IOException ex) {
                  Logger.getLogger(snakeapp.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
        }
        class loadgamelistener implements ActionListener{
          public void actionPerformed(ActionEvent e) {
              System.out.println("Loading Game");
              try {
                  deserialize();
              } catch (IOException ex) {
                  Logger.getLogger(snakeapp.class.getName()).log(Level.SEVERE, null, ex);
              }
              b_read.printboard();
              //s=s_read;
              prepareGame(game,b_read,s_read);  
              cardlayout.show(bgpanel,"GameScreen");
          }
        }
        
    public void serialize(board b,snake s) throws FileNotFoundException, IOException{
        System.out.println("Serializing board and snake");
        FileOutputStream fos = new FileOutputStream("save_game.out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
            //copy static variable 
            b.score=score;
            //create a copy of current board which is static and thus 
            //we create a non static copy of it which is to be serialized
            arraycopier(board.bs,b.bs_copy);
        oos.writeObject(b);
        oos.writeObject(s);
        System.out.println("save game with head_y = "+s.head.x+" and head_x = "+s.head.y);
    }
    
    public void deserialize() throws FileNotFoundException, IOException{
        System.out.println("Deserializing board and snake");
        FileInputStream fileIn = new FileInputStream("save_game.out");
        ObjectInputStream ois = new ObjectInputStream(fileIn);
        
        b_read = null;
        s_read = null;
        
        //read the objects 
        try {
            b_read = (board) ois.readObject();
            s_read = (snake) ois.readObject();
            arraycopier(b_read.bs_copy,board.bs);
            System.out.println("Retrieved game with head_y = "+s_read.head.x+" and head_x = "+s_read.head.y);
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(snakeapp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ois.close();
        fileIn.close();
         
    }
    
    // here E is the generic class 
    < E > void arraycopier(E oldarray[][],E newarray[][]){
        System.out.println("oldarray.length= "+oldarray.length);
        for(int i=0;i<oldarray.length;i++){
            System.out.println("oldarray[i].length= "+oldarray[i].length);
            for(int j=0;j<oldarray[i].length;j++){
             newarray[i][j]=oldarray[i][j];
         }   
        }
    }
    
    class snakethread implements Runnable{
        Thread t;
        int speed;
        snakethread(int s){
        t=new Thread(this);
        t.start();
        System.out.println("thread 1 is alive : "+t.isAlive());
        this.speed=s;
        }
        public void run(){
            System.out.println("thread 1 is alive : "+t.isAlive());
            while(s.headcollided==0 && gamerunning == 1){
                try {
                    Thread.sleep(speed);

                    if(playerReady==1){
                        
                        s.move(s.prevh);
                        int m = s.check();
                        if(m==1){gameover=1;System.out.println("collision");}
                        drawpanel.repaint();
                        //System.out.println("snake running towards :"+snake.prevh);
                    }
                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(snakethread.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
    }
        
}

class board implements Serializable{
    
//we need this field in order to serialise without problems 
static final long serialVersionUID=1111;
    
static String bs[][]=new String[13][21];
String bs_copy[][]=new String[13][21];
int score;
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
                if(k<=seed){
                    bs[i][j]="-";
                }
                if(k>seed){
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
        for(int i=0;i<bs.length;i++){
            for(int j =0;j<bs[i].length;j++){
                System.out.print(""+bs[i][j]+" ");
            }
            System.out.println("");
        }
    }
}

class point implements Serializable{
    //we need this field in order to serialise without problems 
    static final long serialVersionUID=1111;
    
    int y,x;
    point linkforward;
    
    public point(int y,int x){
        this.y=y;
        this.x=x;
        this.linkforward=null;
    }
}

class snake implements Serializable{
    
    //we need this field in order to serialise without problems 
    static final long serialVersionUID=1111;
    int foods=0;
    char prevh;
    Thread t;
    point head;
    point tail;
    int headcollided;
    
        public snake(point t,int initiallength){
            //s = new snake(new point(0,0));
        foods=0;  
        tail=t;
        head=t;
        headcollided=0;

        for(int j=0;j<initiallength;j++){

                        int i=0;
                        //x to represent the snake
                        board.bs[i][j]="X";
                        if( i==0 && j==0 ){ }
                        else{
                        movehead(new point(i,j));
                        }
                        //coardinates of the head

            }
        //so that by default snake moves to the right because of snake's position on board
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
            //when we encounter food
            if(board.bs[heady+dy][headx+dx].equals("$")){
                    movehead(tmpmov);
                    board.bs[head.y][head.x]="X";
                    foods++;
            }
            else
            {
                movehead(tmpmov);
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