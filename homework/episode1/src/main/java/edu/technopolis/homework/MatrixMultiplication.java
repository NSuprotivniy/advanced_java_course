package edu.technopolis.homework;

import javax.management.DynamicMBean;

/**
 * Matrix multiplication home task.
 * <br/>
 * Matrix dimension and elements are passed as CLI arguments.
 */

/**
 * Created by nsuprotivniy on 20.10.16.
 */

public class MatrixMultiplication {
		
		public static int[][] matrixMult(int[][] A, int[][] B) throws MatrixException, NullPointerException {

        if(A == null || B == null) throw new NullPointerException("Matrix is null");
        if (A[0].length != B.length) throw new MatrixException("Incorrect matrix size");


        int a = A.length, b = B[0].length, m = A[0].length;

        int[][] resualt = new int[a][b];

        for (int i = 0; i < a; i++)
            for (int j = 0; j < b; j++)
                for (int k = 0; k < m; k++)
                    resualt[i][j] += A[i][k] * B[k][j];

        return resualt;
    }



    public static void main(String args[]) {

        if (args.length == 0) {
            System.out.println("Invalid input.");
            return;
        }
				
				// Read matrixes from args
        int n = Integer.parseInt(args[0]), m = Integer.parseInt(args[1]);
        int x = Integer.parseInt(args[2]), y = Integer.parseInt(args[3]);

        int[][] A = new int[n][m];
        int[][] B = new int[x][y];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                A[i][j] = Integer.parseInt(args[j * n + i + 4]);

        for (int i = 0; i < x; i++)
            for (int j = 0; j < y; j++)
                B[i][j] = Integer.parseInt(args[j * n + i + 4 + n * m]);

        try {
						// Calc result
            int[][] resualt = matrixMult(A, B);

						// Print result matrix
            for (int i = 0; i <  resualt.length; i++) {
                for (int j = 0; j < resualt[0].length; j++)
                    System.out.print(resualt[i][j] + " ");
                System.out.println();
            }

        }
        catch (Exception excp){
            System.out.println(excp.getMessage());
        }
    }
}

class MatrixException extends Exception {

    public MatrixException(String message) {
        super(message);
    }
}

    
