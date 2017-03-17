package mazePathFinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * path generator class (helper class)
 * 
 * @author Bolun Gao & Boqian Yao
 * 
 * @param <Type>
 */
public class PathGenerator {

	private Node node[][];
	private Node start;
	private Node goal;
	private int numRow, numCol;

	/**
	 * constructor
	 *
     * @author Bolun Gao
     *
	 * @param filename
	 */
	public PathGenerator(String filename) {
		try {
			FileReader(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		findPath();
	}

	/**
	 * read the input file
     *
     * @author Bolun Gao
     *
	 * @param filename
	 * @throws FileNotFoundException
	 */
	private void FileReader(String filename) throws FileNotFoundException {
		if (filename == null)
			throw new FileNotFoundException();

		File file = new File(filename);
		Scanner scan = new Scanner(file);
		numRow = scan.nextInt();
		numCol = scan.nextInt();
		scan.nextLine();

		// set row and column
		node = new Node[numRow][numCol];
		int row = 0;

		// loop lines
		while (scan.hasNextLine()) {

			String current = scan.nextLine();
			char[] charArr = current.toCharArray();

			// loop characters and set node value
			for (int col = 0; col < charArr.length; col++) {
				if (charArr[col] == 'X') {
					node[row][col] = null;
				} else if (charArr[col] == 'S') {
					node[row][col] = new Node(1, row, col);
					start = node[row][col];
				} else if (charArr[col] == 'G') {
					node[row][col] = new Node(2, row, col);
					goal = node[row][col];
				} else {
					node[row][col] = new Node(0, row, col);
				}
			}
			row++;
		}

		scan.close();
	}

	/**
	 * fine the shortest path
     *
     * @author Bolun Gao
     *
	 */
	private void findPath() {

		// create a queue
		LinkedList<Node> queue = new LinkedList<Node>();
		start.visited = true;
		queue.addLast(start);
		Node curr = null;

		boolean foundPath = false;

		// loop the queue
		while (!queue.isEmpty()) {
			curr = queue.poll();

			// check goal(value 2 represents goal).
			if (curr.data == 2) {
				foundPath = true;
				break;
			}

			// check the nearby
			if (curr.col > 0 && NodeChecker(node[curr.row][curr.col - 1])) {
				queue.addLast(node[curr.row][curr.col - 1]);
				node[curr.row][curr.col - 1].visited = true;
				node[curr.row][curr.col - 1].cameFrom = curr;
			}
			if (curr.col < numCol - 1
					&& NodeChecker(node[curr.row][curr.col + 1])) {
				queue.addLast(node[curr.row][curr.col + 1]);
				node[curr.row][curr.col + 1].visited = true;
				node[curr.row][curr.col + 1].cameFrom = curr;
			}
			if (curr.row > 0 && NodeChecker(node[curr.row - 1][curr.col])) {
				queue.addLast(node[curr.row - 1][curr.col]);
				node[curr.row - 1][curr.col].visited = true;
				node[curr.row - 1][curr.col].cameFrom = curr;
			}
			if (curr.row < numRow - 1
					&& NodeChecker(node[curr.row + 1][curr.col])) {
				queue.addLast(node[curr.row + 1][curr.col]);
				node[curr.row + 1][curr.col].visited = true;
				node[curr.row + 1][curr.col].cameFrom = curr;

			}

		}
		// loop through the path and set value
		while (curr.cameFrom != null && foundPath) {
			curr = curr.cameFrom;
			if (curr.data != 1 && curr.data != 2) {
				node[curr.row][curr.col].data = 3;
			}
		}
	}

	/**
	 * print the the solved maze
     *
     * @author Bolun Gao
     *
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public void filePrinter(String filename) throws FileNotFoundException {
		if (filename == null)
			throw new FileNotFoundException();
		try {
			// PrintWriter(FileWriter) will write output to a file
			PrintWriter output = new PrintWriter(new FileWriter(filename));

			// Set up the dot graph and properties
			output.println(numRow + " " + numCol);
			String line = "";
			for (int i = 0; i < numRow; i++) {
				for (int j = 0; j < numCol; j++) {
					if (node[i][j] == null) {
						line += "X";
					} else if (node[i][j].data == 0) {
						line += " ";
					} else if (node[i][j].data == 1) {
						line += "S";
					} else if (node[i][j].data == 2) {
						line += "G";
					} else if (node[i][j].data == 3) {
						line += ".";
					}
				}
				// Create a new line for next row.
				output.println(line);
				line = "";
			}
			output.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * helper class check Node value and whether visited or not
     *
     * @author Bolun Gao
	 * 
	 * @param node
	 * @return
	 */
	private boolean NodeChecker(Node node) {
		if (node != null && node.visited == false) {
			return true;
		}
		return false;
	}

	/**
	 * Node class
	 * 
	 * @author Bolun Gao & Boqian Yao
	 *
	 */
	private class Node {

		// the integer 0 represents the space,
		// 1 represents start point,
		// 2 represents goal.
		// 3 represents path.
		private int data;
		private boolean visited;
		private Node cameFrom;
		private int row;
		private int col;

		private Node(int _data, int _row, int _col) {
			data = _data;
			visited = false;
			cameFrom = null;
			row = _row;
			col = _col;
		}
	}
}
