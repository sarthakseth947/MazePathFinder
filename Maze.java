
// Name: Sarthak Seth
// USC NetID: sethsart@usc.edu
// CS 455 PA3
// Spring 2018

import java.util.LinkedList;

/**
 * Maze class
 * 
 * Stores information about a maze and can find a path through the maze (if
 * there is one).
 * 
 * Assumptions about structure of the maze, as given in mazeData, startLoc, and
 * endLoc (parameters to constructor), and the path: -- no outer walls given in
 * mazeData -- search assumes there is a virtual border around the maze (i.e.,
 * the maze path can't go outside of the maze boundaries) -- start location for
 * a path is maze coordinate startLoc -- exit location is maze coordinate
 * exitLoc -- mazeData input is a 2D array of booleans, where true means there
 * is a wall at that location, and false means there isn't (see public FREE /
 * WALL constants below) -- in mazeData the first index indicates the row. e.g.,
 * mazeData[row][col] -- only travel in 4 compass directions (no diagonal paths)
 * -- can't travel through walls
 * 
 */

public class Maze {

	public static final boolean FREE = false;
	public static final boolean WALL = true;
	private boolean[][] mazeData;
	private MazeCoord startLoc;
	private MazeCoord endLoc;
	private LinkedList<MazeCoord> path;
	private int[][] mazeDataHelper;
	private int xDirection;
	private int yDirection;

	private int rowDisplacement = 0;
	private int colDisplacement = 0;

	/**
	 * Constructs a maze.
	 * 
	 * @param mazeData
	 *            the maze to search. See general Maze comments above for what goes
	 *            in this array.
	 * @param startLoc
	 *            the location in maze to start the search (not necessarily on an
	 *            edge)
	 * @param exitLoc
	 *            the "exit" location of the maze (not necessarily on an edge) PRE:
	 *            0 <= startLoc.getRow() < mazeData.length and 0 <=
	 *            startLoc.getCol() < mazeData[0].length and 0 <= endLoc.getRow() <
	 *            mazeData.length and 0 <= endLoc.getCol() < mazeData[0].length
	 * 
	 */
	public Maze(boolean[][] mazeData, MazeCoord startLoc, MazeCoord exitLoc) {
		this.mazeData = mazeData;
		this.startLoc = startLoc;
		this.endLoc = exitLoc;
		path = new LinkedList<>();
		mazeDataHelper = new int[mazeData.length][mazeData[0].length];

		this.xDirection = startLoc.getRow();
		this.yDirection = startLoc.getCol();
		for (int i = 0; i < mazeData.length; i++) {
			for (int j = 0; j < mazeData[0].length; j++) {
				if (mazeData[i][j] == true) {
					mazeDataHelper[i][j] = 0;
				} else {
					mazeDataHelper[i][j] = 1;
				}

			}

		}
	}

	/**
	 * Returns the number of rows in the maze
	 * 
	 * @return number of rows
	 */
	public int numRows() {

		return mazeData.length; 
	}

	/**
	 * Returns the number of columns in the maze
	 * 
	 * @return number of columns
	 */
	public int numCols() {
		return mazeData[0].length; 
	}

	/**
	 * Returns true iff there is a wall at this location
	 * 
	 * @param loc
	 *            the location in maze coordinates
	 * @return whether there is a wall here PRE: 0 <= loc.getRow() < numRows() and 0
	 *         <= loc.getCol() < numCols()
	 */
	public boolean hasWallAt(MazeCoord loc) {
		if (!((loc.getCol() < numCols() && loc.getCol() >= 0) && (loc.getRow() < numRows() && loc.getRow() >= 0))) {
			return false;
		}

		if (mazeData[loc.getRow()][loc.getCol()] == false) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Returns the entry location of this maze.
	 */
	public MazeCoord getEntryLoc() {
		return startLoc;
	}

	/**
	 * Returns the exit location of this maze.
	 */
	public MazeCoord getExitLoc() {
		return endLoc;
	}

	/**
	 * Returns the path through the maze. First element is start location, and last
	 * element is exit location. If there was not path, or if this is called before
	 * a call to search, returns empty list.
	 * 
	 * @return the maze path
	 */
	public LinkedList<MazeCoord> getPath() {

		return path;

	}

	/**
	 * Find a path from start location to the exit location (see Maze constructor
	 * parameters, startLoc and exitLoc) if there is one. Client can access the path
	 * found via getPath method.
	 * 
	 * @return whether a path was found.
	 */
	public boolean search() {

		return mazeDataHelper(mazeDataHelper, xDirection, yDirection, path, endLoc);

	}

	/**
	 * Get called by the search method and works as a helper method to add the
	 * traversed path from start location to end location(If available)
	 * 
	 * @param mazeDataHelper
	 *            the maze to search
	 * 
	 * @param xDirection
	 *            Row value of the start location
	 * 
	 * @param YDirection
	 *            Column value of the start location
	 *
	 * @param path
	 *            All the coordinates of the traversed path
	 *
	 * @param endloc
	 *            location of the end location maze coordinates
	 * @return whether a path was found.
	 */
	private boolean mazeDataHelper(int[][] mazeDataHelper, int xDirection, int yDirection, LinkedList<MazeCoord> path,
			MazeCoord endloc) {

		if (mazeDataHelper[endloc.getRow()][endloc.getCol()] == 2) {

			return true;
		}

		if (!(xDirection < mazeDataHelper.length && xDirection >= 0)
				&& (yDirection < mazeDataHelper[0].length && yDirection >= 0)) {

			return false;
		}
		if ((xDirection < mazeDataHelper.length && xDirection >= 0)
				&& (yDirection < mazeDataHelper[0].length && yDirection >= 0)
				&& mazeDataHelper[xDirection][yDirection] == 1) {
			return false;
		}
		if ((xDirection < mazeDataHelper.length && xDirection >= 0)
				&& (yDirection < mazeDataHelper[0].length && yDirection >= 0)
				&& mazeDataHelper[xDirection][yDirection] == 0) {
			mazeDataHelper[xDirection][yDirection] = 2;

			rowDisplacement = -1;
			colDisplacement = 0;
			if (mazeDataHelper(mazeDataHelper, xDirection + rowDisplacement, yDirection + colDisplacement, path,
					endloc)) {
				path.addFirst(new MazeCoord(xDirection, yDirection));
				return true;

			}
			rowDisplacement = 1;
			colDisplacement = 0;
			if (mazeDataHelper(mazeDataHelper, xDirection + rowDisplacement, yDirection + colDisplacement, path,
					endloc)) {
				path.addFirst(new MazeCoord(xDirection, yDirection));
				return true;
			}
			rowDisplacement = 0;
			colDisplacement = -1;
			if (mazeDataHelper(mazeDataHelper, xDirection + rowDisplacement, yDirection + colDisplacement, path,
					endloc)) {
				path.addFirst(new MazeCoord(xDirection, yDirection));
				return true;
			}
			rowDisplacement = 0;
			colDisplacement = 1;
			if (mazeDataHelper(mazeDataHelper, xDirection + rowDisplacement, yDirection + colDisplacement, path,
					endloc)) {
				path.addFirst(new MazeCoord(xDirection, yDirection));
				return true;
			}
		}
		return false;
	}
}
