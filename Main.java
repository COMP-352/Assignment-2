package com.company;

import java.util.*;
import java.util.List;

class Node{
int data;
Node next;
}

final class Position {
    Node head;
    private int row;
    private int col;
    private int value;
 //  private int parent_counter;
    private final LinkedList<Position> nextMove = new LinkedList<>();
    private final Position parent;

    public void insertNode(int data) {
        Node node = new Node();
        node.data = data;
        node.next = null;

        if (head == null)
            head = node;
        else {
            Node n = head;
            while (n.next != null)
                n = n.next;
            n.next = node;
        }
    }

    public void delete(int index){
        if (index==0)
            head = head.next;
        else{
            Node n = head;
            Node n1; //= null;
            for (int i=0;i<index;i++)
                n=n.next;
            n1=n.next;
            n.next=n1.next;
        }
    }

    public Position(Position parent) {
        this.parent = parent;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void setValue(int move){
        this.value = move;
    }

    public int getValue(){
        return value;
    }

    public List<Position> getNextMoves() {
        return nextMove;
    }

    public Position getParent() {
        return parent;
    }

    public Position nextPosition(Position previousPosition, int row, int col) {
      //  if (previousPosition.getNextMoves().size()==0)
     //       this.parent_counter++;
        Position nextPosition = new Position(previousPosition);
        nextPosition.setPosition(row, col);
        previousPosition.getNextMoves().add(nextPosition);

        return nextPosition;
    }
}

public class Main {

    // fill the board with the given length and random numbers 1->n-1 with only one 0 at a random position
    public static void BoardCreation(int[][] board) {
        Random random = new Random();
        int min = 1;
        int max = (board.length) - 1;

        //  Two for loops to fill the board
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                board[row][col] = (int) (Math.random() * max + min);
            }
        }
        max++;
        min--;
        board[random.nextInt(max) + min][random.nextInt(max) + min] = 0; //creates a single 0 randomly in the already created matrix
        BoardPrintAfterCreation(board);
    }

    // prints the board once it has been created (method called in the board-creation method)
    public static void BoardPrintAfterCreation(int[][] board) {
        //  Two for-loops to display the board's output
        System.out.println((char) 27 + "[0m" + "Your gaming board has been created and is as follow:");
        for (int[] ints : board) {
            for (int col = 0; col < board.length; col++) {
                if (ints[col] == 0)
                    System.out.print((char) 27 + "[1m" + "\033[3m" + ints[col] + "\033[0m" + "\t ");
                else
                    System.out.print((char) 27 + "[0m" + ints[col] + "\t ");
            }
            System.out.println();
            System.out.println();
        }
    }

    // prints the board with the position where the user actually is
    public static void BoardPrint(int[][] board, int start_row, int start_col) {
        //  Two for-loops to display the board's output after implementing the starting point

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                if (board[row][col] == 0)
                    System.out.print((char) 27 + "[1m" + "\033[3m" + board[row][col] + "\033[0m" + "\t ");
                else if (row == start_row && col == start_col)
                    System.out.print((char) 27 + "[1m" + "\033[3m" + board[row][col] + "\033[0m" + "\t ");
                else
                    System.out.print((char) 27 + "[0m" + board[row][col] + "\t ");
            }
            System.out.println();
            System.out.println();
        }
    }

    // defines where the user wants to start and if it is a legal position or not
    public static String DefineStart(int[][] board, int start_row, int start_col) {
        String start_string;
        System.out.println("It is now time for you to select your starting point." +
                "\nYou can begin only at one of the four corners of the board and at a different position than the 0." +
                "\nPlease write either \"top-left\", \"top-right\", \"bottom-left\" or \"bottom-right\".");
        Scanner input = new Scanner(System.in);
        start_string = input.nextLine();

        while (!Validation(start_string, board, start_row, start_col)) {
            if (!StartStringValidation(start_string))
                System.out.println("I am sorry, you did not enter correctly the wanted position, you have to try again and take " +
                        "care of typos.\nPlease write either \"top-left\", \"top-right\", \"bottom-left\" or \"bottom-right\".");
            if (IllegalStart(board, start_string, start_row, start_col))
                System.out.println("Dear user, you are not allowed to start at the same position than the 0. Please enter an other position !");

            start_string = input.nextLine();
        }
        return start_string;
    }

    // check if the 0 and the start are at the same position
    public static boolean IllegalStart(int[][] board, String start_string, int start_row, int start_col) {
        if (StartStringValidation(start_string)) {
            start_row = DefineStartingRow(board, start_string);
            start_col = DefineStartingCol(board, start_string);
        }

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                if (board[row][col] == 0)
                    if (row == start_row && col == start_col)
                        return true;
            }
        }
        return false;
    }

    // check if the user's input corresponds to the four starting positions available
    public static boolean StartStringValidation(String start) {

        int counter = 0;
        if (!start.equals("top-left"))
            counter++;
        if (!start.equals("top-right"))
            counter++;
        if (!start.equals("bottom-right"))
            counter++;
        if (!start.equals("bottom-left"))
            counter++;

        return counter != 4; //returns false if the input string does not correspond to the four wanted strings
    }

    /* returns true or false for validating both conditions (not start at the 0 position and an incorrect input string)
    I did this method because no while statement worked with this two different conditions */
    public static boolean Validation(String start, int[][] board, int start_row, int start_col) {
        if (IllegalStart(board, start, start_row, start_col))
            return false;
        else return StartStringValidation(start);
    }

    /* returns the row index of the starting position
    which will be memorize in the main, because I ignored how to call by reference in Java */
    public static int DefineStartingRow(int[][] board, String start) {
        int start_row = 0;

        if (start.equals("bottom-left"))
            start_row = board.length - 1;

        if (start.equals("bottom-right"))
            start_row = board.length - 1;

        return start_row;
    }

    /* returns the column index of the starting position
       which will be memorize in the main, because I ignored how to call by reference in Java */
    public static int DefineStartingCol(int[][] board, String start) {

        int start_col = 0;

        if (start.equals("top-right"))
            start_col = (board.length - 1);

        if (start.equals("bottom-right"))
            start_col = (board.length - 1);

        return start_col;
    }

    // the wanted method of the assignment which will return true if the game is solvable or false if not
    public static boolean MagicBoard(Position position, int[][] board, int[][] second_board, int move, int counter) {


        if (second_board[position.getRow()][position.getCol()] == 1)
            return false;

        second_board[position.getRow()][position.getCol()] = 1;
        if (board[position.getRow()][position.getCol()] == board.length - 1) {
            if (position.getCol() != 0)
                if (position.getCol() != board.length - 1)
                    return false;
            if (position.getRow() != 0)
                if (position.getRow() != board.length - 1)
                    return false;
        }

        if (counter == 0) {// RIGHT > column addition
            if (position.getCol() + move < board.length) {
                Position next_position = position.nextPosition(position, position.getRow(), (position.getCol() + move));
                MagicBoard(next_position, board, second_board, board[next_position.getRow()][next_position.getCol()], 0);
            } else counter++;
        }
        if (counter == 1) { // LEFT > column subtraction
            if (position.getCol() - move >= 0) {
                Position next_position = position.nextPosition(position, position.getRow(), (position.getCol() - move));
                MagicBoard(next_position, board, second_board, board[next_position.getRow()][next_position.getCol()], 0);
            } else counter++;
        }
        if (counter == 2) { // UP > row subtraction
            if (position.getRow() - move >= 0) {
                Position next_position = position.nextPosition(position, (position.getRow() - move), position.getCol());
                MagicBoard(next_position, board, second_board, board[next_position.getRow()][next_position.getCol()], 0);
            } else counter++;
        }
        if (counter == 2) { // DOWN > row addition
            if (position.getRow() + move < board.length) {
                Position next_position = position.nextPosition(position, (position.getRow() + move), position.getCol());
                MagicBoard(next_position, board, second_board, board[next_position.getRow()][next_position.getCol()], 0);
            } else if (position.getParent() != null) {
                position.getParent().getNextMoves().remove(0);
                MagicBoard(position.getParent(), board, second_board, board[position.getParent().getRow()][position.getParent().getCol()], 0);
            }
        }
        return true; //(board[nextPosition.getRow()][nextPosition.getCol()] == board[end_row][end_col]);
    }

    public static void LetsPlay(int[][] board, int move, int start_row, int start_col, int end_row, int end_col) {
        Scanner input = new Scanner(System.in);
        String move_string = input.nextLine();

        if (move_string.equals("-1")) {
            System.out.println((char) 27 + "[0m" +
                    "Some boards are impossible to solve, don't worry if you did no achieve this one, try an other one later !");
            return;
        }


        while (!LegalMoveString(move_string)) {
            System.out.println((char) 27 + "[0m" +
                    "The word entered is not correct, please enter either \"left\", \"right\", \"up\" or \"down\" to move through the magic board.");
            move_string = input.nextLine();
        }

        switch (move_string) {
            case "left":
                if (start_col - move >= 0) {
                    start_col = start_col - move;
                    move = board[start_row][start_col];
                } else
                    System.out.println((char) 27 + "[0m" + "This move is impossible, I am sorry. Try an other move");
                break;

            case "right":
                if (start_col + move < board.length) {
                    start_col = start_col + move;
                    move = board[start_row][start_col];
                } else
                    System.out.println((char) 27 + "[0m" + "This move is impossible, I am sorry. Try an other move");
                break;

            case "up":
                if (start_row - move >= 0) {
                    start_row = start_row - move;
                    move = board[start_row][start_col];
                } else
                    System.out.println((char) 27 + "[0m" + "This move is impossible, I am sorry. Try an other move");
                break;

            case "down":
                if (start_row + move < board.length) {
                    start_row = start_row + move;
                    move = board[start_row][start_col];
                } else
                    System.out.println((char) 27 + "[0m" + "This move is impossible, I am sorry. Try an other move");
                break;
        }

        BoardPrint(board, start_row, start_col);

        if (start_row == end_row)
            if (start_col == end_col) {
                System.out.println((char) 27 + "[0m" + "Hurrah !! You won ! Congratulations :)");
                return;
            }

        System.out.println((char) 27 + "[0m" + "Please enter your next move. If you want to surrender, enter -1.");
        LetsPlay(board, move, start_row, start_col, end_row, end_col);
    }

    public static boolean LegalMoveString(String move_string) {
        int counter = 0;
        if (!move_string.equals("left"))
            counter++;
        if (!move_string.equals("right"))
            counter++;
        if (!move_string.equals("up"))
            counter++;
        if (!move_string.equals("down"))
            counter++;
        return counter != 4;
    }


    public static void main(String[] args) {
        int d;
        int start_row = 0;
        int start_col = 0;
        // int end_row;
        //   int end_col;

        String start_string;

        System.out.println("Welcome in our MagicBoard game !\n" +
                "Please enter the size (between 5 and 20) of the squared board you wanna play with: ");
        Scanner input = new Scanner(System.in); // asks the user to choose the size of his board between 5 to 20
        d = input.nextInt();

        // verifies the size of the board and the game will not continue until the size is correct
        while (d < 5 || d > 20) {
            System.out.println("The size of your board is incorrect. " +
                    "Please enter again an integer between 5 and 20 to access the game !");
            d = input.nextInt();
        }

        final int row = d;
        final int col = d;
        int[][] board = new int[row][col]; // creation of the board !!

        BoardCreation(board); // Filling of the board
        int end_row = 0;
        int end_col = 0;
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board.length; j++)
                if (board[i][j] == 0) {
                    end_row = i;
                    end_col = j;
                }

        start_string = DefineStart(board, start_row, start_col); // Defining the starting point

        // memorizes in the main function the index of the starting position
        start_col = DefineStartingCol(board, start_string);
        start_row = DefineStartingRow(board, start_string);

        Position StartPosition = new Position(null);
        StartPosition.setPosition(start_row, start_col);
        BoardPrint(board, start_row, start_col);
        int move = board[StartPosition.getRow()][StartPosition.getCol()];

        int[][] second_board = new int[row][col];
        for (int i = 0 ; i<board.length;i++)
            for (int j = 0; j<board.length;j++)
                second_board[i][j] = 0;

        int counter = 0;

        if (MagicBoard(StartPosition, board, second_board, move, counter)) { // check if the game is doable
            System.out.println((char) 27 + "[0m" +
                    "\nYour gaming board is solvable and now ready for you to play !\nPlease note that the end point (0) and the " +
                    "point where you are located are written in bold and italic so that you can easy identify them.\n");
            BoardPrint(board, start_row, start_col);
        } else { // if the game is not doable
            System.out.println("\nWe are sorry to announce you that the board initiated is not solvable.");
        }
        System.out.println((char) 27 + "[0m" +
                "It is now time for you to play. Please enter either \"left\", \"right\", \"up\" or \"down\" to move through the magic board.");

        LetsPlay(board, move, start_row, start_col, end_row, end_col);
    }
}
