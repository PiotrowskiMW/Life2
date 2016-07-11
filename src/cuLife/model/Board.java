package cuLife.model;

import java.util.Random;
import java.util.Scanner;
import java.io.*;

/**
 * class stores boards
 *
 * @author zdenek
 *
 */
public class Board {

    /**
     * Constant that holds the size of the board
     */
    private final int size;
    /**
     * Table for storing the state board
     */
    Cell[][] board;

    /**
     * Creates boards of a predetermined size
     *
     * @param size The size of the board
     */
    Board(final int size) {
        this.size = size;
        this.board = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Cell();
            }
        }
        randomBoard();
    }

    void iterate(int rule) {
        /**
         * a copy of the array of states on the board and creating new one
         */
        Cell[][] p = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                p[i][j] = new Cell();
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                p[i][j].state = this.board[i][j].state;
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int aliveNeigboursNumber = countAliveNeigbours(i, j, p);
                
                switch (rule) {
                    case 0: // Comway's game
                        if (p[i][j].state == false) {
                            board[i][j].state = aliveNeigboursNumber == 3;
                        } else board[i][j].state = aliveNeigboursNumber == 2 || aliveNeigboursNumber == 3;
                        break;
                    case 1: // Diamoeba
                        if (p[i][j].state == false) {
                            board[i][j].state = aliveNeigboursNumber == 3 || aliveNeigboursNumber == 5 || aliveNeigboursNumber == 6 || aliveNeigboursNumber == 7 || aliveNeigboursNumber == 8;
                        } else {
                            board[i][j].state = aliveNeigboursNumber == 5 || aliveNeigboursNumber == 6 || aliveNeigboursNumber == 7 || aliveNeigboursNumber == 8;
                        }
                        break;
                    default: // Walled city
                        if (p[i][j].state == false) {
                            board[i][j].state = aliveNeigboursNumber == 4 || aliveNeigboursNumber == 5 || aliveNeigboursNumber == 6 || aliveNeigboursNumber == 7 || aliveNeigboursNumber == 8;
                        } else board[i][j].state = aliveNeigboursNumber == 2 || aliveNeigboursNumber == 3 || aliveNeigboursNumber == 4 || aliveNeigboursNumber == 5;
                        break;
                }

            }
        }
    }

    private int countAliveNeigbours(int i, int j, Cell[][] p) {
        i += size;
        j += size;
        int aliveNeigboursNumber = 0;
        if (p[(i - 1) % size][(j - 1) % size].state) {
            aliveNeigboursNumber++;
        }
        if (p[(i - 1) % size][j % size].state) {
            aliveNeigboursNumber++;
        }
        if (p[(i - 1) % size][(j + 1) % size].state) {
            aliveNeigboursNumber++;
        }
        if (p[i % size][(j - 1) % size].state) {
            aliveNeigboursNumber++;
        }
        if (p[i % size][(j + 1) % size].state) {
            aliveNeigboursNumber++;
        }
        if (p[(i + 1) % size][(j - 1) % size].state) {
            aliveNeigboursNumber++;
        }
        if (p[(i + 1) % size][j % size].state) {
            aliveNeigboursNumber++;
        }
        if (p[(i + 1) % size][(j + 1) % size].state) {
            aliveNeigboursNumber++;
        }

        return aliveNeigboursNumber;
    }

    /**
     * Changes the state cells to the opposite
     *
     * @param i coordinate row cell
     * @param j coordinate column cell
     */
    public void toggleCell(int i, int j) {
        board[i][j].state = !board[i][j].state;
    }

    /**
     * Checks state cells
     *
     * @param i coordinate row cell
     * @param j coordinate column cell
     * @return
     */
    public boolean checkCell(int i, int j) {
        return board[i][j].state;
    }

    /**
     * The randomizing board
     */
    void randomBoard() {
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j].state = rand.nextBoolean();
            }
        }
    }

    /**
     * Deleting the board
     */
    void erase() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j].state = false;
            }
        }
    }

    void initializeWithLoadedValues(String path) {
        try (Scanner input = new Scanner(new File(path))) {
            String line;
            boolean state;
            char tmp;
            for (int i = 0; i < size; i++) {
                line = input.nextLine();
                for (int j = 0; j < size; j++) {
                    state = line.charAt(j) != '0';
                    this.board[j][i].state = state;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void saveState(String path) {
        try (PrintWriter output = new PrintWriter("output.txt")) {
            for (int i = 0; i < size; i++) {
                for(int j=0;j< size;j++){
                    output.print(this.board[j][i].state ? 1 : 0);
                }
                output.println();                
            }
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } 

    }

}
