package game;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class snakeapp extends JApplet {
    CardLayout cardlayout;
    JPanel bgpanel,menu,game,result;
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
        
        board b = new board(1);
    
    }
    
        class startgamelistener implements ActionListener{
          public void actionPerformed(ActionEvent e) {
              System.out.println("Game Started");
                //starts with Game screen
                cardlayout.show(bgpanel,"GameScreen");
                prepareGame(game);
                
          }
        }
        class endgamelistener implements ActionListener{
          public void actionPerformed(ActionEvent e) {
              System.out.println("Game Ended");
              System.exit(0);
          }
        }
}
class board{
static String bs[][]=new String[13][21];
static char b[][]=new char[13][21];
//snake s;
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