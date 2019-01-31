package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.StartPosition;

/**
 * A class that handles all of the clicking on the starting positions. Pops up a
 * box for users to select which track they desire
 * 
 * @author Yuan Tang & Yusheng Wang
 * 
 */
public class StartHandler implements ActionListener {

	private final StartPosition _pos;

	/**
	 * A constructor that initializes the start position
	 * 
	 * @param pos
	 *            the start position
	 */
	public StartHandler(StartPosition pos) {
		_pos = pos;
	}

	/**
	 * A method that pops up a box and then performs moving based upon what the
	 * user clicked
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (_pos.height() >= 1 && _pos.height() < 4) {
			JFrame frame = new JFrame();
			Icon icon = null;
			Object[] possibilities = { "Top", "Middle", "Bottom" };
			String s = (String) JOptionPane.showInputDialog(frame,
					"Pick track:\n" + "Top, Middle, or Bottom", "Track",
					JOptionPane.PLAIN_MESSAGE, icon, possibilities, "");
			if (s == "Top")
				_pos.moveTopLeft();
			else if (s == "Middle")
				_pos.moveTopMiddle();
			else if (s == "Bottom")
				_pos.moveTopRight();
		} else if (_pos.height() == 4) {
			_pos.moveTopMiddle();
		}

	}
}
