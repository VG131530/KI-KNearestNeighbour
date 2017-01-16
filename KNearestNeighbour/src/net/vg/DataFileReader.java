package net.vg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class DataFileReader {

	/**
	 * Reads the file given by the path
	 * 
	 * @param pathToData
	 * @return
	 */
	public static double[][] readFile(String pathToData) {
		double[][] dataResults = null;
		int NUMBER_OF_LINES;
		int NUMBER_OF_ATTRIBUTES;
		try {
			NUMBER_OF_LINES = getLineNumbers(new FileReader(pathToData)) + 1;
			NUMBER_OF_ATTRIBUTES = getAttributeNumber(new BufferedReader(new FileReader(pathToData)));

			dataResults = new double[NUMBER_OF_LINES][NUMBER_OF_ATTRIBUTES];
			BufferedReader bufferedReader = new BufferedReader(new FileReader(pathToData));

			String line = "";
			int i = 0;
			while ((line = bufferedReader.readLine()) != null) {
				String[] splittedString = line.split(",");
				for (int j = 0; j < NUMBER_OF_ATTRIBUTES; j++) {
					dataResults[i][j] = Double.parseDouble(splittedString[j]);
				}
				i++;
			}
			bufferedReader.close();
		} catch (

		IOException e) {
			e.printStackTrace();
			return null;
		}
		return dataResults;
	}

	private static int getAttributeNumber(BufferedReader bufferedReader) throws IOException {
		int length = bufferedReader.readLine().split(",").length;
		bufferedReader.close();
		return length;
	}

	/**
	 * Returns the number of lines in the file provided by the FileReader
	 * 
	 * @param fileReader
	 *            holds the File to count the lines of
	 * @return number of lines
	 * @throws IOException
	 */
	private static int getLineNumbers(FileReader fileReader) throws IOException {
		LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
		lineNumberReader.skip(Long.MAX_VALUE);
		int lineNumber = lineNumberReader.getLineNumber();
		lineNumberReader.close();
		return lineNumber;
	}
}
