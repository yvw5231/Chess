package model;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * A class that creates everything board related
 * 
 * @author Yuan Tang & Yusheng Wang
 * 
 */
public class Board {

	private ActionListener _listener;

	int players;
	private final Position[] _left;
	public final Position[] _right;
	private final Position[] _middle;
	private StartPosition[] _redStart;
	private StartPosition[] _blueStart;
	private StartPosition[] _greenStart;
	private StartPosition[] _whiteStart;
	private StartPosition[] _blackStart;
	private StartPosition[] _yellowStart;
	private final StartPosition[][] _players = { _redStart, _blueStart,
			_greenStart, _whiteStart, _blackStart, _yellowStart };

	public int _minus;
	public int _plusTwo;
	public int _plusFive;

	public String[] _allColors = { "R", "B", "G", "W", "K", "Y" };
	public String[] _currentColor;
	public int _moveCount = 0;

	public Position _p;

	/**
	 * A method that sets up the ActionListener.
	 * 
	 * @param al
	 *            An ActionListener.
	 */
	public void setActionListener(ActionListener al) {
		_listener = al;
	}

	/**
	 * A method that notifies the user of changes in the ActionListener.
	 */
	public void notifyOfChange() {
		if (_listener != null) {
			_listener.actionPerformed(null);
		}
	}

	/**
	 * Creates and sets up all of the critical parts of the GUI and game.
	 * 
	 * @param The
	 *            number of players.
	 */
	public Board(int x) {
		players = x;
		_left = new Position[14];
		_right = new Position[14];
		_middle = new Position[14];
		for (int i = 0; i < 3; i++) {
			_left[i] = (new Position(0, i, this));
			_right[i] = (new Position(0, i, this));
			_middle[i] = (new Position(0, i, this));
		}
		for (int i = 3; i < 14; i++) {
			_left[i] = (new Position(0, i, this));
			_right[i] = _middle[i] = _left[i];

		}
		for (int i = 0; i < players; i++) {
			_players[i] = new StartPosition[4];
		}

		for (int i = 0; i < players; i++) {
			if (players < 4) {
				for (int j = 0; j < 4; j++) {
					_players[i][j] = (new StartPosition(0, j, this));
					if (i == 0)
						populate(_players[i][j], "R", j + 1);
					if (i == 1)
						populate(_players[i][j], "B", j + 1);
					if (i == 2)
						populate(_players[i][j], "G", j + 1);
				}
			}

			else {
				for (int j = 0; j < 3; j++) {
					_players[i][j] = (new StartPosition(0, j, this));
					if (i == 0)
						populate(_players[i][j], "R", j + 1);
					if (i == 1)
						populate(_players[i][j], "B", j + 1);
					if (i == 2)
						populate(_players[i][j], "G", j + 1);
					if (i == 3)
						populate(_players[i][j], "W", j + 1);
					if (i == 4)
						populate(_players[i][j], "K", j + 1);
					if (i == 5)
						populate(_players[i][j], "Y", j + 1);
				}
			}
		}
		randomize();
		populate(_right[_minus], "Backward", 1);
	}

	/**
	 * A method that populates the game positions
	 * 
	 * @param pos
	 *            The position of the game board
	 * @param s
	 *            The first letter of the color of the game pieces
	 * @param howMany
	 *            How many pieces to populate.
	 */
	private void populate(Position pos, String s, int howMany) {
		for (int i = 0; i < howMany; i++) {
			pos.putOn(new Piece(s));
		}
	}

	/**
	 * A method that adds the values of the game positions to the GUI.
	 * 
	 * @return The game board.
	 */
	public ArrayList<String> getBoard() {
		ArrayList<String> temp = new ArrayList<String>();
		for (int j = 0; j < players; j++) {
			for (int i = 0; i < 4; i++) {
				if (_players[j][i] != null)
					temp.add(_players[j][i].toString());
			}
		}
		for (int i = 0; i < 3; i++) {
			temp.add(_left[i].toString());
			temp.add(_right[i].toString());
			temp.add(_middle[i].toString());
		}
		for (int i = 3; i < 13; i++) {
			temp.add(_right[i].toString());
		}
		return temp;
	}

	/**
	 * A method that fetches the coordinates of a player.
	 * 
	 * @param j
	 *            The number of players.
	 * @param i
	 *            The number of pieces
	 * @return Returns the coordinates of the players and pieces in a
	 *         multidimensional array.
	 */
	public StartPosition getPlayerStart(int j, int i) {
		return _players[j][i];
	}

	/**
	 * A method that gets the left track.
	 * 
	 * @param i
	 *            The position index of the left track.
	 * @return The value in the left track with respect to the parameter.
	 */
	public Position getLeft(int i) {
		return _left[i];
	}

	/**
	 * A method that gets the right track.
	 * 
	 * @param i
	 *            The position index of the right track.
	 * @return The value in the right track with respect to the parameter.
	 */
	public Position getRight(int i) {
		return _right[i];
	}

	/**
	 * A method that gets the middle track.
	 * 
	 * @param i
	 *            The position index of the middle track.
	 * @return The value in the middle track with respect to the parameter.
	 */
	public Position getMiddle(int i) {
		return _middle[i];
	}

	/**
	 * A method that gets the right track.
	 * 
	 * @param i
	 *            The position index of the right track.
	 * @return The value in the right track with respect to the parameter.
	 */

	public Position getCommon(int i) {
		return _right[i];
	}

	/**
	 * A method that gets the left track array.
	 * 
	 * @return the top array.
	 */
	public Position[] leftTrack() {
		return _left;
	}

	/**
	 * A method that gets the right track array.
	 * 
	 * @return the bottom array.
	 */
	public Position[] rightTrack() {
		return _right;
	}

	/**
	 * A method that gets the middle track array.
	 * 
	 * @return the middle array.
	 */
	public Position[] middleTrack() {
		return _middle;
	}

	/**
	 * A method that gets the number of players.
	 * 
	 * @return number of players
	 */
	public int getPlayers() {
		return players;
	}

	/**
	 * A method that returns the pieces in a given array.
	 * 
	 * @param index
	 *            index of players.
	 * @return pieces in an array based upon index.
	 */
	public StartPosition[] getPlayerStart(int index) {
		return _players[index];
	}

	/**
	 * A method that creates three random positions to be assigned to the board.
	 */
	public void randomize() {
		Random r = new Random();
		_plusTwo = r.nextInt(9) + 3;
		_plusFive = r.nextInt(9) + 3;
		_minus = r.nextInt(9) + 3;

		while (_plusTwo == _plusFive) {
			_plusFive = r.nextInt(9) + 3;
		}

		while (_plusTwo == _minus || _plusFive == _minus) {
			_minus = r.nextInt(9) + 3;
		}

		_right[_plusTwo].specialSpot(2);
		 System.out.println("forward two " + (_plusTwo + 1));
		 _right[_plusFive].specialSpot(5);
		 System.out.println("forward five " + (_plusFive + 1));
		_right[_minus].specialSpot(-1);
		System.out.println("backward " + (_minus + 1));
	}

	/**
	 * A method that creates an array of the teams based on the number of
	 * players.
	 */
	public void createArray() {
		System.out.print("The current colors are ");
		_currentColor = new String[getPlayers()];
		for (int i = 0; i < getPlayers(); i++) {
			_currentColor[i] = _allColors[i];
			System.out.print("[" + _currentColor[i] + "]" + " ");
		}
	}

}
