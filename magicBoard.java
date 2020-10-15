package A2;

import java.util.Random;

public class magicBoard {

//  ** Note this is just to fill the board with a random length between 5-20 and random numbers n->n-1. Still have to figure out
//  ** how we can traverse the array
    public static void createBoard()
    {
        Random random = new Random();
        int min = 5;
        int max = 20;

        //  To randomize array row length:
        final int row = random.nextInt(max + 1 - min) + min ;
        //  Collum length dependent on row length
        final int col = row - 1 ;

        int [][] board = new int [row][col];

        //  Two for loops to fill the board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = (int)(Math.random() * board.length - 1);
            }
        }

        //  Two for loops to display the board output
        for(int i = 0;i < board.length - 1; i++)
        {
            for(int j = 0; j < board[i].length; j++)
                System.out.print(board[i][j]+"\t ");
                System.out.println();
                System.out.println();
        }

    }

    public static void main(String[] args) {
     createBoard();
    }
}
