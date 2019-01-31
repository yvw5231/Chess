package model;

import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ui.DBconnect;
import ui.Graphic;

/**
 * A class that sets up non-starting positions
 * 
 * @author Yuan Tang & Yusheng Wang
 * 
 */
public class Position {
	public int _offset;
	private final int _index;
	protected Stack<Piece> _stack;
	protected Board _board;
	private static int[] _counter;
	JFrame frame;

	/**
	 * A constructor that setsup all of the non-starting Positions
	 * 
	 * @param offset
	 *            the height of the position
	 * @param index
	 *            the coordinate of the position inside an array
	 * @param b
	 *            a reference to a board
	 */
	public Position(int offset, int index, Board b) {
		_offset = offset;
		_stack = new Stack<Piece>();
		_index = index;
		_board = b;
		for (int i = 0; i < 6; i++) {
			_counter = new int[6];
			_counter[i] = 0;
		}
		frame = new JFrame();
	}

	/**
	 * A method that returns the height of a position
	 * 
	 * @return height
	 */
	public int height() {
		if (_offset == -1) {
			return -1 * (_stack.size() + _offset);
		} else {
			return _stack.size() + _offset;
		}
	}

	/**
	 * A method that properly places a piece onto a stack
	 * 
	 * @param p
	 *            the piece to be placed
	 */
	public void putOn(Piece p) {
		_stack.push(p);
		_board.notifyOfChange();
	}

	/**
	 * A method that returns the top piece of a stack
	 * 
	 * @return top piece of a stack
	 */
	public Piece peekTop() {
		return _stack.peek();
	}

	/**
	 * A method that allows for the next player to make a move
	 */
	public void increase() {
		_board._moveCount++;
		_board._moveCount = _board._moveCount % (_board.getPlayers());
	}

	/**
	 * A method that keeps track of the pieces that have been popped off the
	 * board
	 * 
	 * @param p
	 *            A piece that has been popped off.
	 */
	public void colorCounter(Piece p) {
		if ((p.toString() == "R")) {
			_counter[0]++;
			System.out.println("Red counter " + _counter[0]);
			increase();
		}
		if ((p.toString() == "B")) {
			_counter[1]++;
			increase();

		}
		if ((p.toString() == "G")) {
			_counter[2]++;
			increase();
		}
		if ((p.toString() == "W")) {
			_counter[3]++;
			increase();
		}
		if ((p.toString() == "K")) {
			_counter[4]++;
			increase();
		}
		if ((p.toString() == "Y")) {
			_counter[5]++;
			increase();
		}
	}

	/**
	 * A method that is used when moving pieces outside of the left, middle, and
	 * right starting track.
	 */
	public void moveTop() {
		int distanceToMove = height();
		Piece p = _stack.pop();
		if (p.toString().equals(_board._currentColor[_board._moveCount])) {
			if ((_index + distanceToMove) >= 13) {
				p.getTrack()[13].putOn(p);
				colorCounter(p);
			}
			 else {
			p.getTrack()[_index + distanceToMove].putOn(p);
			increase();
			}

		} else {
			_stack.push(p);
		}

		if (_board.getPlayers() < 4) {
			for (int i = 0; i < 6; i++) {
				if (_counter[i] == 10) {
					JOptionPane.showMessageDialog(frame, p.toString()
							+ " Is The Winner");
					Graphic.runGraphics(null);
					DBconnect.runProcess(null);
				}
			}
		} else {
			for (int i = 0; i < 6; i++) {
				if (_counter[i] == 6) {
					JOptionPane.showMessageDialog(frame, p.toString()
							+ " Is The Winner");
					Graphic.runGraphics(null);
					DBconnect.runProcess(null);
				}
			}
		}
	}

	/**
	 * A method that moves a piece backwards.
	 */
	public void moveBackward() {
		Piece p = _stack.pop();
		if (p.toString().equals(_board._currentColor[_board._moveCount])) {
		p.getTrack()[(_index - _stack.size())].putOn(p);
		increase();
		}
		else{
			_stack.push(p);
		}
	}

	/**
	 * A method that changes the initial offset
	 * 
	 * @param offset
	 *            the new height to be set
	 */
	public void specialSpot(int offset) {
		_offset = offset;
	}

	@Override
	/**
	 * A method that returns the name of all the pieces in a stack
	 */
	public String toString() {
		return _stack.toString();
	}
}
