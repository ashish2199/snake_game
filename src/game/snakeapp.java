package game;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JApplet;
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
        menu.setBackground(Color.red);
        bgpanel.add(menu,"Menus");
        
        JPanel game = new JPanel();
        bgpanel.add(game,"GameScreen");
        
        JPanel result = new JPanel();
        bgpanel.add(result,"ResultScreen");
        
        cardlayout.show(bgpanel,"GameScreen");
        
    }
}


/*
we will use this space to write code that might be used in future 

        //layout we will use will have 6 columns and as many rows as we want
        //GridLayout grid = new GridLayout(0,6);

*/