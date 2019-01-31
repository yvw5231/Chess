package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


/**
 * A class that runs the game
 * 
 * @author Yusheng Wang & Yuan Tang
 * 
 */
public class Driver extends JFrame {
	private static final long serialVersionUID = 1L;
	JPanel panel;
	
	private JButton player2 = new JButton("2 players");
	private JButton player3 = new JButton("3 players");
	private JButton player4 = new JButton("4 players");
	private JButton player5 = new JButton("5 players");
	private JButton player6 = new JButton("6 players");
	private JButton rank = new JButton("View High Score Repository");

	public Driver(){
	   
		setTitle("choose players");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
        setResizable(true);
        setVisible(true);
		JPanel playerPanel = new JPanel(new GridLayout(5,3));
	    playerPanel.add(player2);
	    playerPanel.add(player3);
	    playerPanel.add(player4);
	    playerPanel.add(player5);
	    playerPanel.add(player6);
	    playerPanel.add(rank);
	    
	    JPanel p = new JPanel(new BorderLayout());
        p.add(playerPanel,BorderLayout.CENTER);
        
        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(p,BorderLayout.CENTER);
        
	    player2.addActionListener(new ActionListener(){
	    	@Override
            public void actionPerformed(ActionEvent e){
            	SwingUtilities.invokeLater(new Game(2));
            	setVisible(false);
            	}
	    });
        player3.addActionListener(new ActionListener(){
        	@Override
            public void actionPerformed(ActionEvent e){
            	SwingUtilities.invokeLater(new Game(3));
            	setVisible(false);
            }
	    });
        player4.addActionListener(new ActionListener(){
        	@Override
            public void actionPerformed(ActionEvent e){
            	SwingUtilities.invokeLater(new Game(4));
            	setVisible(false);
            }
	    });
        player5.addActionListener(new ActionListener(){
        	@Override
            public void actionPerformed(ActionEvent e){
            	SwingUtilities.invokeLater(new Game(5));
            	setVisible(false);
            }
	    });
        player6.addActionListener(new ActionListener(){
        	@Override
            public void actionPerformed(ActionEvent e){
            	SwingUtilities.invokeLater(new Game(6));
            	setVisible(false);
            }
	    });
        rank.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DBconnect.viewHighScore(null);
			}
		});
        
        setSize(450,200);
	}
	public static void main(String[] args) {
		new Driver();
	}
	
}
