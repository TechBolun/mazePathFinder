package mazePathFinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 * The class for timer straight-line distance
 * 
 * @author Bolun Gao
 * 
 * @param <Type>
 */

public class PathGenerateSLD {

	private Node node[][];
	private Node start;
	private Node goal;
	private int numRow, numCol;

	public PathGenerateSLD(String filename) {
		try {
			FileReader(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		findPathSLD();
	}

	private void FileReader(String filename) throws FileNotFoundException {
		if (filename == null)
			throw new FileNotFoundException();

		File file = new File(filename);
		Scanner scan = new Scanner(file);
		numRow = scan.nextInt();
		numCol = scan.nextInt();
		scan.nextLine();

		node = new Node[numRow][numCol];
		int row = 0;
		Random r = new Random();
		while (scan.hasNextLine()) {

			String current = scan.nextLine();
			char[] charArr = current.toCharArray();

			for (int col = 0; col < charArr.length; col++) {
				if (charArr[col] == 'X') {
					node[row][col] = new Node(r.nextInt(1000 + col), row, col);
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
	 * Method for Straight-line distance timer
	 */
	private void findPathSLD() {
		LinkedList<Node> queue = new LinkedList<Node>();
		start.visited = true;
		queue.addFirst(start);
		Node curr = null;

		boolean foundPath = false;

		while (!queue.isEmpty()) {
			curr = queue.poll();

			if (curr.data == 2) {
				foundPath = true;
				break;
			}
			if (curr.row > 0 && !node[curr.row - 1][curr.col].visited) {
				queue.addLast(node[curr.row - 1][curr.col]);
				node[curr.row - 1][curr.col].visited = true;
				node[curr.row - 1][curr.col].cameFrom = curr;
			}
			if (curr.row < numRow - 1 && !node[curr.row + 1][curr.col].visited) {
				queue.addLast(node[curr.row + 1][curr.col]);
				node[curr.row + 1][curr.col].visited = true;
				node[curr.row + 1][curr.col].cameFrom = curr;

			}
			if (curr.col > 0 && !node[curr.row][curr.col - 1].visited) {
				queue.addLast(node[curr.row][curr.col - 1]);
				node[curr.row][curr.col - 1].visited = true;
				node[curr.row][curr.col - 1].cameFrom = curr;
			}
			if (curr.col < numCol - 1 && !node[curr.row][curr.col + 1].visited) {
				queue.addLast(node[curr.row][curr.col + 1]);
				node[curr.row][curr.col + 1].visited = true;
				node[curr.row][curr.col + 1].cameFrom = curr;
			}

		}

		while (curr.cameFrom != null && foundPath) {
			curr = curr.cameFrom;
			if (curr.data != 1 && curr.data != 2) {
				node[curr.row][curr.col].data = 3;
			}
		}
	}

	private class Node {
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
