package model;

import java.awt.Color;

/**
 * A class that deals with players
 * 
 * @author Yuan Tang & Yusheng Wang
 * 
 */
@SuppressWarnings("unused")
public class Player {

	private final Color _color;
	private final int _score;

	/**
	 * A constructor that assigns a color to a player.
	 * 
	 * @param c
	 *            a real color
	 */
	public Player(Color c) {
		_color = c;
		_score = 0;
	}

}
