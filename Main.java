package codeeval_LCS;

/*
 * Copyright (C) 2015 hankoor
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 Longest Common Subsequence

 Challenge Description:

 You are given two sequences. Write a program to determine the longest 
 common subsequence between the two strings (each string can have a maximum 
 length of 50 characters). NOTE: This subsequence need not be contiguous. The 
 input file may contain empty lines, these need to be ignored.
 Input sample:

 The first argument will be a path to a filename that contains two strings per 
 line, semicolon delimited. You can assume that there is only one unique 
 subsequence per test case. E.g.

 XMJYAUZ;MZJAWXU

 Output sample:

 The longest common subsequence. Ensure that there are no trailing 
 empty spaces on each line you print. E.g.

 MJAU
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    private static void loadFile(String fileName) {
        File file = new File(fileName);
        String x, y;                //the two strings to be compared
        int lengthX, lengthY; //length for alg-matters
        int[][] matrix;             /*matrix for use with 
         https://en.wikipedia.org/wiki/Longest_common_subsequence_problem
         */

        if (!file.canRead() || !file.isFile()) {
            System.exit(0);
        }
        BufferedReader in = null;
        String row;
        try {
            in = new BufferedReader(new FileReader(fileName));
            while ((row = in.readLine()) != null) {
                /**
                 * initialize variables for this row
                 */
                x = row.split(";")[0];
                y = row.split(";")[1];
                lengthX = x.length();
                lengthY = y.length();
                matrix = new int[lengthX + 1][lengthY + 1];

                /**
                 * run through matrix to find ways as described in Wiki
                 * run started from outer-right position, as matrix
                 * was stored reversed.
                 * Unlike in:
                 * https://en.wikipedia.org/wiki/Longest_common_subsequence_problem#Worked_example
                 */
                for (int i = lengthX - 1; i >= 0; i--) {
                    for (int j = lengthY - 1; j >= 0; j--) {
                        if (x.charAt(i) == y.charAt(j)) {
                            matrix[i][j] = matrix[i + 1][j + 1] + 1;
                        } else {
                            matrix[i][j] = Math.max(matrix[i + 1][j], matrix[i][j + 1]);
                        }
                    }
                }
                //DEBUG
                    //System.out.println(Arrays.deepToString(matrix));
                //<----DEBUG
                
                int i = 0, j = 0;
                while (i < lengthX && j < lengthY) {
                    if (x.charAt(i) == y.charAt(j)) {
                        System.out.print(x.charAt(i));
                        i++;
                        j++;
                    } else if (matrix[i + 1][j] >= matrix[i][j + 1]) {
                        i++;
                    } else {
                        j++;
                    }
                }
                System.out.println();
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ioEx) {
                    ioEx.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] pathToFile) {
        if ((pathToFile != null) && (pathToFile.length == 1) && (!pathToFile[0].isEmpty())) {
            loadFile(pathToFile[0]);
        }
    }
}