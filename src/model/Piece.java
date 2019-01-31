package model;

/**
 * A class that holds the properties of a game piece
 * 
 * @author Yuan Tang & Yusheng Wang
 * 
 */
public class Piece {

	private final String _string;
	private Position[] _track;

	/**
	 * A method that returns the name of a piece.
	 * 
	 * @param s
	 *            the name.
	 */
	public Piece(String s) {
		_string = s;
	}

	/**
	 * A method that sets a track.
	 * 
	 * @param track
	 *            array of positions.
	 */
	public void setTrack(Position[] track) {
		_track = track;
	}

	@Override
	/**
	 * returns the name of the piece.
	 */
	public String toString() {
		return _string;
	}

	/**
	 * A method that gets an array of Positions
	 * 
	 * @return an array of Positions.
	 */
	public Position[] getTrack() {
		return _track;
	}

}
