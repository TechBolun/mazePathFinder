package mazePathFinder;

import java.io.FileNotFoundException;

/**
 * the class that print the solved maze file
 * 
 * @author Bolun Gao & Boqian Yao
 *
 */
public class PathFinder {

	public static void solveMaze(String inputFile, String outputFile) {
		PathGenerator path = new PathGenerator(inputFile);
		try {
			path.filePrinter(outputFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
