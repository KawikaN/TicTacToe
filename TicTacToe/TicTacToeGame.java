
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.*;

interface TicTacToeInterface {
    public void createButtons();

    public void compTurn();

    public boolean canMove();

    public void disable();

    public void verticalWin();

    public void horizontalWin();

    public void diagnoalWin();

    public void outputFile() throws IOException;

    public int win();

}

public class TicTacToeGame extends JFrame implements TicTacToeInterface {

    public static void main(String[] args) {
        new TicTacToeGame();

    }

    private JButton[][] ticTacToe = new JButton[3][3]; // 2D array of buttons
    private MyListener myListener = new MyListener(); // create listener for key presses

    private int playerNum = 0;
    private int comNum = 0;
    private boolean won = false;

    public TicTacToeGame() { // constructor
        this.setVisible(true); // Makes window visible
        this.setSize(500, 500); // creates window for game
        this.setTitle("Tic-Tac-Toe"); // Sets title
        createButtons(); // initiates buttons
        this.setLayout(new GridLayout(3, 3)); // layout for the boxes in our window
    }

    public void createButtons() {
        for (int i = 0; i < ticTacToe.length; i++) {
            for (int j = 0; j < ticTacToe.length; j++) { // Iterates through array
                ticTacToe[i][j] = new JButton(); // Creates a button
                ticTacToe[i][j].addActionListener(myListener); // Allow button to be interacted with
                this.add(ticTacToe[i][j]); // Adds a button to grid
                ticTacToe[i][j].setVisible(true); // Makes buttons visable
            }
        }
    }

    private class MyListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < ticTacToe.length; i++) {
                for (int j = 0; j < ticTacToe.length; j++) { // Iterates through array
                    if (e.getSource() == ticTacToe[i][j]) {
                        ticTacToe[i][j].setText("X"); // Places an x in box that is clicked
                        ticTacToe[i][j].setEnabled(false); // Disables button after turn
                        verticalWin(); // Checks to see if there is a win verticle, horizontal, diaginol
                        if (canMove() && won == false) {
                            compTurn(); // Computers move
                            verticalWin();
                            // Checks for win
                        }
                    }
                }
            }

        }

    }

    public void compTurn() {
        int random1 = (int) (Math.random() * ticTacToe.length);
        int random2 = (int) (Math.random() * ticTacToe.length);
        // Creates random variables for our computer to make choices
        do {
            random1 = (int) (Math.random() * ticTacToe.length);
            random2 = (int) (Math.random() * ticTacToe.length);

        } while (!ticTacToe[random1][random2].isEnabled());
        ticTacToe[random1][random2].setText("O"); // Place a O for spots
        ticTacToe[random1][random2].setEnabled(false); // Disables button
    }

    public boolean canMove() {
        for (int i = 0; i < ticTacToe.length; i++) {
            for (int j = 0; j < ticTacToe.length; j++) {
                if (ticTacToe[i][j].getText().equals("")) {
                    return true; // Checks if its an available move
                }
            }
        }
        return false;
    }

    public void disable() {
        for (int i = 0; i < ticTacToe.length; i++) {
            for (int j = 0; j < ticTacToe.length; j++) {
                ticTacToe[i][j].setEnabled(false);
                // loops through and disables
            }
        }
    }

    public void verticalWin() {
        for (int i = 0; i < ticTacToe.length; i++) {
            for (int j = 0; j < ticTacToe.length; j++) {
                if (ticTacToe[j][i].getText() == "X") { // Checks boxes for Xʻs
                    playerNum++;
                } else if (ticTacToe[j][i].getText() == "O") { // Checks boxes for Oʻs
                    comNum++;
                }
                win(); // call win function to display win

            }
            playerNum = 0;
            comNum = 0;
            // reseting default variables
        }
        horizontalWin();
    }

    public void horizontalWin() {
        for (int i = 0; i < ticTacToe.length; i++) {
            for (int j = 0; j < ticTacToe.length; j++) {
                if (ticTacToe[i][j].getText() == "X") {
                    playerNum++;
                } else if (ticTacToe[i][j].getText() == "O") {
                    comNum++;
                }
                win();

            }
            playerNum = 0;
            comNum = 0;
        }
        diagnoalWin(); // Continue to call other win possibilities
    }

    public void diagnoalWin() {
        for (int i = 0; i < ticTacToe.length; i++) {
            if (ticTacToe[i][i].getText() == "X") {
                playerNum++;
            } else if (ticTacToe[i][i].getText() == "O") {
                comNum++;
            }
        }
        if (win() == 1) { // Calls win method and checks if it reutnrs 1(means that none of the if
                          // functions ran)
            playerNum = 0;
            comNum = 0;
            // if true default variables
        }

        for (int i = 0; i < ticTacToe.length; i++) {
            if (ticTacToe[i][(ticTacToe.length - 1) - i].getText() == "X") {
                playerNum++;
            } else if (ticTacToe[i][(ticTacToe.length - 1) - i].getText() == "O") {
                comNum++;
            }
        }
        if (win() == 1) {
            playerNum = 0;
            comNum = 0;
        }
    }

    public void outputFile() throws IOException {
        FileWriter fw = new FileWriter(new File("Tfile.txt")); // Creating output file
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter printWritter = new PrintWriter(bw);
        String won = "";
        if (playerNum == 3) { // Checking if user won
            won = "TicTacToe Game, won: the user";
        } else if (comNum == 3) { // Checking if computer won
            won = "TicTacToe Game, won: the computer";
        }
        printWritter.println(won); // Writing to file

        for (int i = 0; i < ticTacToe.length; i++) {
            for (int j = 0; j < ticTacToe.length; j++) {
                if (ticTacToe[i][j].getText() == "X") {
                    printWritter.print("x");
                } else if (ticTacToe[i][j].getText() == "O") {
                    printWritter.print("o");
                } else {
                    printWritter.print("y");
                }
                // Iterates through and writes to file anywhere X, O or blank spaces are
                if (j != ticTacToe.length - 1) {
                    printWritter.print(" - ");
                }
                // Divider between each character

            }
            printWritter.println();
            // Prints another line
        }
        printWritter.close();
    }

    public int win() {
        if (playerNum == ticTacToe.length) {
            JOptionPane.showMessageDialog(new JFrame(), "You Win! Your results are in Tfile.txt", "PlayerWin",
                    JOptionPane.PLAIN_MESSAGE);
            // Displays player winner message
            won = true;
            disable();
            try {
                outputFile();
            } catch (IOException IOE) { // try and catches writing to file
                System.out.println("File Error");
            }
            return 0;
        } else if (comNum == ticTacToe.length) {
            JOptionPane.showMessageDialog(new JFrame(), "You Loose! Your results are in Tfile.txt", "ComputerWin",
                    JOptionPane.PLAIN_MESSAGE);
            won = true;
            disable();
            try {
                outputFile();
            } catch (IOException IOE) {
                System.out.println("File Error");
            }
            return 0;
        }
        return 1; // if no if statements were true return default of 1
    }
}
