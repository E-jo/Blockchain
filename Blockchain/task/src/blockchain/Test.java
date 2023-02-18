package blockchain;

import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int rows = sc.nextInt();
        int cols = sc.nextInt();

        int topInterval = rows * 2 - 1;
        for (int j = 0; j < rows; j++) {
            for (int i = j + 1; i <= cols; i++) {
                if (i == j + 1 || i == j + 1 + topInterval) {
                    System.out.print(i);
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

    }


}
