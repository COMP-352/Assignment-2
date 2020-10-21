package com.company;

import java.util.*;
import java.io.*;

public class Main {

    public static void BoardCreation(int[][] board) {
//  ** Note this is just to fill the board with a random length between 5-20 and random numbers n->n-1. Still have to figure out
//  ** how we can traverse the array
        Random random = new Random();
        int min = 0;
        int max = (board.length) - 1;

        //  Two for loops to fill the board
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                board[row][col] = (int) (Math.random() * max + min);
                if (board[row][col] == 0) {
                    min++;
                }
            }
        }
        BoardPrintAfterCreation(board);
    }


    public static void BoardPrintAfterCreation(int[][] board) {
        //  Two for-loops to display the board's output
        System.out.println((char) 27 + "[0m" + "Your gaming board has been created and is as follow:");
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                if (board[row][col] == 0)
                    System.out.print((char) 27 + "[1m" + "\033[3m" + board[row][col] + "\033[0m" + "\t ");
                else
                    System.out.print((char) 27 + "[0m" + board[row][col] + "\t ");
            }
            System.out.println();
            System.out.println();
        }
    }

    public static void BoardPrint(int[][] board, int start_row, int start_col) {
        //  Two for-loops to display the board's output after implementing the starting point

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                if (board[row][col] == 0)
                    System.out.print((char) 27 + "[1m" + "\033[3m" + board[row][col] + "\033[0m" + "\t ");
                else if(row == start_row && col == start_col)
                    System.out.print((char) 27 + "[1m" + "\033[3m" + board[row][col] + "\033[0m" + "\t ");
                else
                    System.out.print((char) 27 + "[0m" + board[row][col] + "\t ");
            }
            System.out.println();
            System.out.println();
        }
    }


    public static String DefineStart(int[][] board, int start_row, int start_col) {
        String start_string;
        System.out.println("It is now time for you to select your starting point." +
                "\nYou can begin only at one of the four corners of the board." +
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


    public static boolean IllegalStart(int[][]board,String start_string, int start_row, int start_col) {    //check if the 0 and the start are at the same position
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

    public static boolean Validation(String start, int[][] board, int start_row, int start_col) {
        if (IllegalStart(board, start, start_row, start_col))
            return false;
        else return StartStringValidation(start);
    }

    public static int DefineStartingRow(int[][] board, String start) {
        int start_row = 0;

        if (start.equals("bottom-left"))
            start_row = board.length - 1;

        if (start.equals("bottom-right"))
            start_row = board.length - 1;

        return start_row;
    }


    public static int DefineStartingCol(int[][] board, String start) {

        int start_col = 0;

        if (start.equals("top-right"))
            start_col = (board.length - 1);

        if (start.equals("bottom-right"))
            start_col = (board.length - 1);

        return start_col;
    }


    public static boolean MagicBoard(int d) {

        return true;
    }


    public static void main(String[] args) throws IOException {
        int d;
        int start_row = 0;
        int start_col = 0;
        String start_string;
        System.out.println("Welcome in our MagicBoard game !\n" +
                "Please enter the size (between 5 and 20) of the squared board you wanna play with: ");
        Scanner input = new Scanner(System.in);
        d = input.nextInt();
        while (d < 5 || d > 20) {
            System.out.println("The size of your board is incorrect. " +
                    "Please enter again an integer between 5 and 20 to access the game !");
            d = input.nextInt();
        }

        final int row = d;
        final int col = d;
        int[][] board = new int[row][col];

        BoardCreation(board);
        start_string = DefineStart(board, start_row, start_col);
        start_col = DefineStartingCol(board, start_string);
        start_row = DefineStartingRow(board, start_string);
        System.out.println((char) 27 + "[0m" +
                "\nYour gaming board is now ready for you to play.\nPlease note that the end point (0) and the "+
                "point where you are located are written in bold and italic so that you can easy identify them.\n");
        BoardPrint(board, start_row, start_col);
    }
}
