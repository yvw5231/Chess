package model;

/**
 * A class that sets up the starting positions
 * 
 * @author Yuan Tang & Yusheng Wang
 * 
 */
public class StartPosition extends Position {
	/**
	 * A constructor that sets up the starting positions
	 * 
	 * @param offset
	 *            the height of a given position
	 * @param index
	 *            the coordinate of a position in an array
	 * @param b
	 *            a reference to the board
	 */
	public StartPosition(int offset, int index, Board b) {
		super(offset, index, b);

	}

	@Override
	/**
	 * A method that throws an UnsupportedOperationException
	 */
	public void moveTop() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void increase() {
		_board._moveCount++;
		_board._moveCount = _board._moveCount % (_board.getPlayers());
	}

	/**
	 * A method that moves to the top track
	 */

	public void moveTopLeft() {
		int distanceToMove = height() - 1;
		Piece p = _stack.pop();
		if (p.toString().equals(_board._currentColor[_board._moveCount])) {
			p.setTrack(_board.leftTrack());
			p.getTrack()[distanceToMove].putOn(p);
			increase();
		} else {
			_stack.push(p);
		}
	}

	/**
	 * A method that moves to the bottom track
	 */
	public void moveTopRight() {
		int distanceToMove = height() - 1;
		Piece p = _stack.pop();
		if (p.toString().equals(_board._currentColor[_board._moveCount])) {

			p.setTrack(_board.rightTrack());
			p.getTrack()[distanceToMove].putOn(p);
			increase();
		} else {
			_stack.push(p);
		}
	}

	/**
	 * A method that moves to the middle track
	 */
	public void moveTopMiddle() {
		int distanceToMove = height() - 1;
		Piece p = _stack.pop();
		if (p.toString().equals(_board._currentColor[_board._moveCount])) {
			p.setTrack(_board.middleTrack());
			p.getTrack()[distanceToMove].putOn(p);
			increase();
		} else {
			_stack.push(p);
		}
	}

	/**
	 * A method that returns names of all the pieces in a given stack
	 */
	@Override
	public String toString() {
		return _stack.toString();
	}
}
