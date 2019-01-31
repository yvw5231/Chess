package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Position;

/**
 * A class that handles all of the clicking for non-starting positions
 * 
 * @author Yuan Tang & Yusheng Wang
 * 
 */
public class TrackHandler implements ActionListener {

	private final Position _pos;

	/**
	 * A constructor that initializes the position
	 * 
	 * @param pos
	 *            the position
	 */
	public TrackHandler(Position pos) {
		_pos = pos;
	}

	@Override
	/**
	 * A method that moves a piece from a non-empty stack
	 */
	public void actionPerformed(ActionEvent e) {
		if (_pos.height() >= 1 && _pos.peekTop().toString() != "Backward") {
			_pos.moveTop();
		} else if (_pos._offset < 0 && _pos.peekTop().toString() != "Backward") {
			_pos.moveBackward();
		}
	}

}
