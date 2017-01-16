package net.vg;

import java.io.File;

public class Main {

	public static void main(String[] args) {

		int NEIGHBOUR_COUNT = 3;
		
		double[][] testData = DataFileReader.readFile("DataFolder" + File.separator +"app1.test");
		double[][] trainData = DataFileReader.readFile("DataFolder" + File.separator +"app1.data");
		double[][] testDataWONormation = DataFileReader.readFile("DataFolder" + File.separator +"app1.test");

		
		new KNearesNeighbourHeuristic(NEIGHBOUR_COUNT, testData, trainData, testDataWONormation).doMagic();
	}
}
