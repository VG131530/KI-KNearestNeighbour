package net.vg;

public class KNearesNeighbourHeuristic {
	/**
	 * Anzahl der zu vergleichenden Nachbaren
	 */
	private final int NEIGHBOUR_COUNT;
	private final int RESULT_INDEX;
	
	private final int CLASS_INDEX = 0;
	private final int DISTANCE_INDEX = 1;
	private final int COLUMN_INDEX = 2;

	private int COUNT_RIGHT = 0;
	private int COUNT_FALSE = 0;
	
	/**
	 * [K NeighboursCount] | [0 = Klasse | 1 = Distanz | 2 = Zeilenindex in der Testdatei]
	 * 
	 */
	private double currentNearestNeighbours[][];

	private double[][] testData;
	private double[][] testDataWONormation;
	private double[][] trainData;

	public KNearesNeighbourHeuristic(int NEIGHBOUR_COUNT, double[][] testData, double[][] trainData, double[][] testDataWONormation) {
		this.NEIGHBOUR_COUNT = NEIGHBOUR_COUNT;
		this.RESULT_INDEX = testData[0].length - 1;
		
		currentNearestNeighbours = new double[NEIGHBOUR_COUNT][3];
		
		this.testData = testData;
		this.testDataWONormation = testDataWONormation;
		this.trainData = trainData;
		
		normalize(this.testData);
		normalize(this.trainData);
	}

	public void doMagic() {
		for (int i = 0; i < testData.length; i++) {
			
			for (int j = 0; j < trainData.length; j++) {
				for (int k = 0; k < NEIGHBOUR_COUNT; k++) {
					double distance = difference(testData[i], trainData[j]);
					if (currentNearestNeighbours[k][DISTANCE_INDEX] == 0 ||
							distance < currentNearestNeighbours[k][DISTANCE_INDEX]) {
						// Wenn die Unterschied zwischen TestDaten und Trainingsdaten geringer ist als der zuletzt Gemessene....
						currentNearestNeighbours[k][CLASS_INDEX] = trainData[j][RESULT_INDEX];
						currentNearestNeighbours[k][DISTANCE_INDEX] = difference(testData[i], trainData[j]);
						currentNearestNeighbours[k][COLUMN_INDEX] = i;
						break;
					}
				}
			}
			// Berechnung der Klassifikation
			int clas = evaluateResults();
			System.out.print("Der Vektor: (");
			printVector(i);
			System.out.print(") wurde ");
			printResult(clas);
			System.out.print(" der Klasse " + clas + " zugeordnet.\n");
			
			//Reset 
			this.currentNearestNeighbours = new double[NEIGHBOUR_COUNT][3];
		}
		System.out.println("Es wurden "+ COUNT_FALSE +" Vektoren falsch klassifiziert. Das entspricht " +
				((100.0/(COUNT_RIGHT+COUNT_FALSE))*COUNT_FALSE)+"%.");
	}

	private void printResult(int clas) {
		if(testData[(int) currentNearestNeighbours[0][COLUMN_INDEX]][RESULT_INDEX] == clas){
			System.out.print("richtig");
			COUNT_RIGHT++;
		}
		else{
			System.out.print("[FALSCH_______]");
			COUNT_FALSE++;
		}
	}

	private void printVector(int testDataIndex) {
		for(int n = 0; n < testDataWONormation[testDataIndex].length - 1; n++){
			if(n == testDataWONormation[testDataIndex].length - 2) // Um den letzten Wert ohne Komma zu schreiben....
				System.out.print(testDataWONormation[testDataIndex][n]);
			else
				System.out.print(testDataWONormation[testDataIndex][n]+", ");
		}
	}

	private int evaluateResults() {
		double clastmp = 0.0;
		for(int k = 0; k < NEIGHBOUR_COUNT; k++){
			clastmp += currentNearestNeighbours[k][CLASS_INDEX];
		}
		clastmp /= NEIGHBOUR_COUNT;
		return clastmp >= 0.5 ? 1 : 0;
	}

	private void normalize(double[][] data) {
		for (double[] array : data) {
			double sum = 0;
			
			for (int i = 0; i < array.length - 1; i++) {
				sum += Math.pow(Math.abs(array[i]), 2);
			}
			sum = Math.sqrt(sum);
			sum = 1000.0 / sum;
			
			// SQRT(Attr[1]^2 + Attr[2]^2 + Attr[3]^2 + Attr[4]^2... + Attr[n]^2) / 1000
			
			for (int i = 0; i < array.length - 1; i++) {
				array[i] = sum * array[i];
			}
		}
	}

	private double difference(double[] testData, double[] trainData) {
		double difference = 0;
		for (int i = 0; i < testData.length; i++) {
			difference = difference + Math.pow(testData[i] - trainData[i], 2);
		}
		return Math.sqrt(difference);
	}
}
