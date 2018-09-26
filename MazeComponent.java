
// Name: Sarthak Seth
// USC NetID: sethsart@usc.edu
// CS 455 PA3
// Spring 2018

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Scanner;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

import javax.swing.JComponent;

/**
 * MazeComponent class
 * 
 * A component that displays the maze and path through it if one has been found.
 */
public class MazeComponent extends JComponent {

	private static final int START_X = 10; // top left of corner of maze in frame
	private static final int START_Y = 10;
	private static final int BOX_WIDTH = 20; // width and height of one maze "location"
	private static final int BOX_HEIGHT = 20;
	private static final int INSET = 2;
	// how much smaller on each side to make entry/exit inner box
	private int[][] mazeData1;
	private MazeCoord startLoc1 = new MazeCoord(0, 0);
	private MazeCoord endLoc1 = new MazeCoord(0, 0);
	private int rowval;
	private int colval;
	private Maze abc;

	private Color color;
	private Color startPoint;
	private Color endPoint;
	private Color path;

	/**
	 * Constructs the component.
	 * 
	 * @param maze
	 *            the maze to display
	 */
	public MazeComponent(Maze maze) {
		abc = maze;
		startLoc1 = maze.getEntryLoc();
		endLoc1 = maze.getExitLoc();
		rowval = maze.numRows();
		color = null;
		startPoint = Color.YELLOW;
		endPoint = Color.GREEN;
		path = Color.BLUE;
		colval = maze.numCols();
		this.mazeData1 = new int[rowval][colval];

		for (int i = 0; i < mazeData1.length; i++) {

			for (int j = 0; j < mazeData1[0].length; j++) {
				MazeCoord currPoint = new MazeCoord(i, j);
				if (maze.hasWallAt(currPoint)) {
					mazeData1[i][j] = 1;
				} else {
					mazeData1[i][j] = 0;

				}

			}

		}

	}

	/**
	 * Draws the current state of maze including the path through it if one has been
	 * found.
	 * 
	 * @param g
	 *            the graphics context
	 */
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		for (int row = 0; row < mazeData1.length; row++) {
			for (int col = 0; col < mazeData1[0].length; col++) {
				switch (mazeData1[row][col]) {
				case 0:
					color = Color.WHITE;
					break;
				case 1:
					color = Color.BLACK;
					break;

				}

				g2.setColor(color);
				g2.fillRect(START_X + BOX_WIDTH * col, START_Y + BOX_HEIGHT * row, BOX_WIDTH, BOX_HEIGHT);

				g2.drawRect(START_X + BOX_WIDTH * col, START_Y + BOX_HEIGHT * row, BOX_WIDTH, BOX_HEIGHT);

				if (row == startLoc1.getRow() && col == startLoc1.getCol()) {
					g2.setColor(startPoint);
					g2.fillRect(START_X + BOX_WIDTH * col + INSET, START_Y + BOX_HEIGHT * row + INSET,
							BOX_WIDTH - INSET - INSET, BOX_HEIGHT - INSET - INSET);
					g2.drawRect(START_X + BOX_WIDTH * col + INSET, START_Y + BOX_HEIGHT * row + INSET,
							BOX_WIDTH - INSET - INSET, BOX_HEIGHT - INSET - INSET);
				}
				if (row == endLoc1.getRow() && col == endLoc1.getCol()) {
					g2.setColor(endPoint);
					g2.fillRect(START_X + BOX_WIDTH * col + INSET, START_Y + BOX_HEIGHT * row + INSET,
							BOX_WIDTH - INSET - INSET, BOX_HEIGHT - INSET - INSET);
					g2.drawRect(START_X + BOX_WIDTH * col + INSET, START_Y + BOX_HEIGHT * row + INSET,
							BOX_WIDTH - INSET - INSET, BOX_HEIGHT - INSET - INSET);

				}

			}

		}

		if (!abc.getPath().isEmpty()) {
			for (int a = 0; a < abc.getPath().size(); a++) {

				int increment = a + 1;
				if (increment < abc.getPath().size()) {
					int linRow = START_X + BOX_WIDTH * abc.getPath().get(a).getRow();
					int linCol = (START_Y + BOX_HEIGHT * abc.getPath().get(a).getCol());
					int linRow1 = START_X + BOX_WIDTH * abc.getPath().get(increment).getRow();
					int linCol1 = (START_Y + BOX_HEIGHT * abc.getPath().get(increment).getCol());

					Line2D.Double pathDirection = new Line2D.Double(((2 * linCol) + BOX_WIDTH) / 2,
							((2 * linRow) + BOX_WIDTH) / 2, ((2 * linCol1) + BOX_WIDTH) / 2,
							((2 * linRow1) + BOX_WIDTH) / 2);

					g2.setColor(path);
					g2.draw(pathDirection);
				}
			}
		}

	}
}
