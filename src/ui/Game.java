
package ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.Board;
import model.Position;
import model.StartPosition;

/**
 * A class that creates everything for the game
 * 
 * @author Yuan Tang & Yusheng Wang
 * 
 */
public class Game implements Runnable, ActionListener {

	private ArrayList<JButton> _buttons;
	private final Board _board;
	/**
	 * A constructor that takes in the run arguments
	 * 
	 * @param players
	 *            the number of players
	 * @param isFinch
	 *            whether or not a finch is used
	 */
	public Game(int players) {
		_board = new Board(players);
		_board.setActionListener(this);
	}

	@Override
	/**
	 * A method that creates the entire game
	 */
	public void run() {
		int rows = _board.getPlayers();

		JFrame window = new JFrame("Stage 2");
		Container c = window.getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.X_AXIS));

		JPanel start = new JPanel();
		start.setLayout(new BoxLayout(start, BoxLayout.Y_AXIS));

		JPanel[] startPlayers = new JPanel[6];
		for (int i = 0; i < rows; i++) {
			startPlayers[i] = new JPanel();
			startPlayers[i].setLayout(new BoxLayout(startPlayers[i],
					BoxLayout.X_AXIS));
		}

		JPanel left = new JPanel();
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

		JPanel upperLeft = new JPanel();
		upperLeft.setLayout(new BoxLayout(upperLeft, BoxLayout.X_AXIS));

		JPanel middleLeft = new JPanel();
		middleLeft.setLayout(new BoxLayout(middleLeft, BoxLayout.X_AXIS));

		JPanel lowerLeft = new JPanel();
		lowerLeft.setLayout(new BoxLayout(lowerLeft, BoxLayout.X_AXIS));

		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.X_AXIS));

		c.add(start);
		c.add(left);
		c.add(right);
		for (int i = 0; i < rows; i++) {
			start.add(startPlayers[i]);
		}

		left.add(upperLeft);
		left.add(middleLeft);
		left.add(lowerLeft);

		_buttons = new ArrayList<JButton>();
		for (int j = 0; j < _board.getPlayers(); j++) {
			if (_board.getPlayers() < 4) {
				for (int i = 0; i < 4; i++) {
					createStartButton(startPlayers[j],
							_board.getPlayerStart(j, i));
				}
			} else {
				for (int i = 0; i < 3; i++) {
					createStartButton(startPlayers[j],
							_board.getPlayerStart(j, i));
				}
			}
		}
		for (int i = 0; i < 3; i++) {
			createTrackButton(upperLeft, _board.getLeft(i));
			createTrackButton(lowerLeft, _board.getRight(i));
			createTrackButton(middleLeft, _board.getMiddle(i));
		}
		for (int i = 3; i < 13; i++) {
			createTrackButton(right, _board.getCommon(i));
		}
		// createTrackButton(right, _board.getMinus(_board._minus));

		_board.createArray();
		update(_board.getBoard());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);
	}


	/**
	 * A method that adds the ActionListeners to a start position
	 * 
	 * @param panel
	 *            JPanel of start position
	 * @param pos
	 *            start position
	 */
	public void createStartButton(JPanel panel, StartPosition pos) {
		createButton(panel, new StartHandler(pos));
	}

	/**
	 * A method that adds the ActionListeners to non-start positions
	 * 
	 * @param panel
	 *            JPanel of non-start position
	 * @param pos
	 *            non-start position
	 */
	public void createTrackButton(JPanel panel, Position pos) {
		createButton(panel, new TrackHandler(pos));
	}

	/**
	 * A method that adds ActionListeners to a button
	 * 
	 * @param panel
	 *            JPanel of button
	 * @param listener
	 *            The proper listener (Start vs. Track)
	 */
	private void createButton(JPanel panel, ActionListener listener) {
		JButton b;
		b = new JButton();
		panel.add(b);
		b.addActionListener(listener);
		_buttons.add(b);
	}

	@Override
	/**
	 * A method that updates the game board
	 * @param e the value of what's clicked
	 */
	public void actionPerformed(ActionEvent e) {
		update(_board.getBoard());
	}

	/**
	 * A method that sets the text to each button
	 * 
	 * @param board
	 *            a reference to a board
	 */
	private void update(ArrayList<String> board) {
		for (int i = 0; i < _buttons.size(); i++) {
			_buttons.get(i).setText(board.get(i));
		}
	}

}
