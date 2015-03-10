package game;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class snakeapp extends JApplet {
    public void init() {
        
        setSize(500,500);
        
        JPanel bgpanel = new JPanel();
        bgpanel.setDoubleBuffered(true);
        bgpanel.setBackground(Color.white);
        //we use card layout to show different screens such as menu , game, result ect
        CardLayout cardlayout = new CardLayout();
        bgpanel.setLayout(cardlayout);
        add(bgpanel);
        JPanel menu = new JPanel();
        menu.setBackground(Color.white);
        //function to create buttons and other things inside the menu pane
        prepareMenus(menu);
        
        bgpanel.add(menu,"Menus");
        
        JPanel game = new JPanel();
        bgpanel.add(game,"GameScreen");
        game.setBackground(Color.white);
        
        JPanel result = new JPanel();
        bgpanel.add(result,"ResultScreen");
        result.setBackground(Color.white);
        
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
        
        
        JButton btn_exit = new JButton();
        menu.add(btn_start, BorderLayout.CENTER);
        btn_exit.setFont(f);
        btn_exit.setForeground(Color.red);
        btn_exit.setBackground(Color.white);
        btn_exit.setText("Exit");
        btn_exit.addActionListener(new endgamelistener());
        menu.add(btn_exit, BorderLayout.SOUTH);
    
    }
        class startgamelistener implements ActionListener{
          public void actionPerformed(ActionEvent e) {
              System.out.println("Game Started");
          }
        }
        class endgamelistener implements ActionListener{
          public void actionPerformed(ActionEvent e) {
              System.out.println("Game Ended");
              System.exit(0);
          }
        }
}


/*
we will use this space to write code that might be used in future 

        //layout we will use will have 6 columns and as many rows as we want
        //GridLayout grid = new GridLayout(0,6);

*/