package prototype;

import java.util.Random;
import java.util.Arrays;
import java.util.Scanner;

public class TicTacToe {
	
    //array for tic tac toe grid
    static String[] grid = {" ", " ", " ", " ", " ", " ", " ", " ", " "};
    
    public static void main(String[] args) {
        
    		Scanner first = new Scanner(System.in);  // Reading from System.in
        System.out.println("Who goes first? 0 for player, 1 for computer");
        int g = first.nextInt();

        System.out.println("You are X");
        System.out.println("Numbers 1-9 correspond to a");
        System.out.println("place in the grid as follows:");
        
        if (g == 0) {//player goes first
                //for loop runs both turns 4 times
                for (int O = 0; O < 4; O++) {

                    System.out.println("");
                    System.out.println("123");
                    System.out.println("456");
                    System.out.println("789");
                    System.out.println("");
                    
                    grid[getUserInput()] = "X";
                    drawGrid();
                    
                    playerCheck();
                    drawGrid();
                }
                
                //final move for the player
                grid[getUserInput()] = "X";
                
                finalCheck();
                drawGrid();
                
            } else if (g != 0) {//computer goes first
                //for loop runs both turns 4 times
                for (int O = 0; O < 4; O++) {
                    
                    System.out.println("");
                    System.out.println("123");
                    System.out.println("456");
                    System.out.println("789");
                    System.out.println("");
                    
                    playerCheck();
                    drawGrid();
                
                    //gets user input
                    grid[getUserInput()] = "X";
                    drawGrid();
                }
                
                //final move for the computer
            playerCheck();
            drawGrid();
            System.out.println("The game ended in a draw.");
        }
        

    }
    
    public static void drawGrid() {
        System.out.println("");
        System.out.println(grid[0] + "|" + grid[1] + "|" + grid[2]);
        System.out.println("-----");
        System.out.println(grid[3] + "|" + grid[4] + "|" + grid[5]);
        System.out.println("-----");
        System.out.println(grid[6] + "|" + grid[7] + "|" + grid[8]);
        System.out.println("");
    }
    
    public static void playerCheck() {
        /* checks to see if anyone has won, or if the player is about to win
         * the order it goes is:
         * player won?
         * computer to win?
         * computer won?
         * player to win? */
        boolean win_check = true;//this var is to have and easy way to stop the check
            int win = 0;
            int target = 0;
            
            //PLAYER WON CHECK:
            
            //top left to bottom right diagonal test for player win
        for (int i = 0; i < 9 && win_check == true; i+=4) {
                if (grid[i] == "X") {
                    win++;
//                    System.out.println("X found at " + i);
                }
        }
        if (win == 3) {
                drawGrid();
                System.out.println("You won diagonally!");
                System.exit(0);
        } else {
                win = 0;
        }
        
        //top right to bottom left diagonal test for player win
        for (int i = 2; i < 7 && win_check == true; i+=2) {
                if (grid[i] == "X") {
                    win++;
                }
        }
        if (win == 3) {
                drawGrid();
                System.out.println("You won diagonally!");
                System.exit(0);
        } else {
                win = 0;
        }
        
        //vertical test for player win
        for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 9; j+=3) {
                    if (grid[i+j] == "X") {
                        win++;
                    }
                }
                
                if (win == 3) {
                    drawGrid();
                    System.out.println("You won vertically!");
                    System.exit(0);
                } else {
                    win = 0;
                }
            }
        
        //horizontal test for player win
        for (int i = 0; i < 9 && win_check == true; i+=3) {
                for (int j = 0; j < 3; j++) {
                    if (grid[i+j] == "X") {
                        win++;
                    }
                }
                
                if (win == 3) {
                    drawGrid();
                    System.out.println("You won horizontally!");
                    System.exit(0);
                } else {
                    win = 0;
                }
            }
            
            //ALL COMPUTER CHECKS:
        
        //vertical test for computer win
        for (int i = 0; i < 3 && win_check == true; i++) {
                for (int j = 0; j < 9; j+=3) {
                    if (grid[i+j] == "O") {
                        win++;
                    } else {
                        target = i+j;
                    }
                }
                
                if (win == 2 && grid[target] == " ") {
//                    System.out.println("The CP chose spot: to win vert");
//                    System.out.println(target+1);
                    grid[target] = "O";
                    win_check = false;
                    win = 0;
                    drawGrid();
                    System.out.println("The computer won vertically!");
                    System.exit(0);
                } else {
                    win = 0;
                }
            }
        
        //horizontal test for computer win
        for (int i = 0; i < 9 && win_check == true; i+=3) {
                for (int j = 0; j < 3; j++) {
                    if (grid[i+j] == "O") {
                        win++;
                    } else {
                        target = i+j;
                    }
                }
                
                if (win == 2 && grid[target] == " ") {
//                    System.out.println("The CP chose spot: to win horiz");
//                    System.out.println(target+1);
                    grid[target] = "O";
                    win_check = false;
                    win = 0;
                    drawGrid();
                    System.out.println("The computer won horizontally!");
                    System.exit(0);
                } else {
                    win = 0;
                }
            }
        
        //top left to bottom right diagonal test for computer win
        for (int i = 0; i < 9 && win_check == true; i+=4) {
                if (grid[i] == "O") {
//                    System.out.println("O found at " + i);
                    win++;
                } else {
                    target = i;
                }
        }
        if (win == 2 && grid[target] == " ") {
//            System.out.println("The CP chose spot: to win diag");
//            System.out.println(target+1);
                grid[target] = "O";
                win = 0;
                win_check = false;
                System.out.println("The computer won diagonally!");
                drawGrid();
                System.exit(0);
        } else {
                win = 0;
        }
        
        //top right to bottom left diagonal test for computer win
        for (int i = 2; i < 7 && win_check == true; i+=2) {
                if (grid[i] == "O") {
                    win++;
                } else {
                    target = i;
                }
        }
        if (win == 2 && grid[target] == " ") {
//            System.out.println("The CP chose spot: to block diag");
//            System.out.println(target+1);
                grid[target] = "O";
                win = 0;
                win_check = false;
                System.out.println("The computer won diagonally!");
                drawGrid();
                System.exit(0);
        } else {
                win = 0;
        }
        
        //PLAYER TO WIN CHECK:
        
        //top left to bottom right diagonal test for player win
        for (int i = 0; i < 9 && win_check == true; i+=4) {
                if (grid[i] == "X") {
//                    System.out.println("X found at " + i);
                    win++;
                } else {
                    target = i;
                }
        }
        if (win == 2 && grid[target] == " ") {
//            System.out.println("The CP chose spot: to block diag");
//            System.out.println(target+1);
                grid[target] = "O";
                win = 0;
                win_check = false;
        } else {
                win = 0;
        }
        
        //top right to bottom left diagonal test for player win
        for (int i = 2; i < 7 && win_check == true; i+=2) {
                if (grid[i] == "X") {
                    win++;
                } else {
                    target = i;
                }
        }
        if (win == 2 && grid[target] == " ") {
//            System.out.println("The CP chose spot: to win diag");
//            System.out.println(target+1);
                grid[target] = "O";
                win = 0;
                win_check = false;
        } else {
                win = 0;
        }
        
        //vertical test for player win
        for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 9; j+=3) {
                    if (grid[i+j] == "X") {
                        win++;
                    } else {
                        target = i+j;
                    }
                }
                
                if (win == 2 && grid[target] == " ") {
//                    System.out.println("The CP chose spot: to block vert");
//                    System.out.println(target+1);
                    grid[target] = "O";
                    win_check = false;
                    win = 0;
                } else {
                    win = 0;
                }
            }
        
        //horizontal test for player win
        for (int i = 0; i < 9 && win_check == true; i+=3) {
                for (int j = 0; j < 3; j++) {
                    if (grid[i+j] == "X") {
                        win++;
                    } else {
                        target = i+j;
                    }
                }
                
                if (win == 2 && grid[target] == " ") {
//                    System.out.println("The CP chose spot: to block horiz");
//                    System.out.println(target+1);
                    grid[target] = "O";
                    win_check = false;
                    win = 0;
                } else {
                    win = 0;
                }
            }
        
        if (win_check == true) {
            //computer selects random space to place O
            Random rand = new Random();
            int  n = rand.nextInt(9);
//            System.out.println("The CP chose spot: randomly");
//            System.out.println(n+1);
            while (grid[n] != " ") {//while loop checks that space is free
                n = rand.nextInt(9);
            }
            grid[n] = "O";
            win_check = false;
        }
    }
    
    public static void finalCheck() {
        /* checks to see if the player won on their last turn */
        boolean win_check = true;//this var is to have and easy way to stop
        int win = 0;
        int target = 0;
        
        //vertical test for player win
        for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 9; j+=3) {
                    if (grid[i+j] == "X") {
                        win++;
                    } else {
                        target = i+j;
                    }
                }
                
                if (win == 3) {
                    System.out.println("You won vertically!");
                    System.exit(0);
                } else {
                    win = 0;
                }
            }
        
        //top left to bottom right diagonal test for player win
        for (int i = 0; i < 9 && win_check == true; i+=4) {
                if (grid[i] == "X") {
                    win++;
//                    System.out.println("X found at " + i);
                }
        }
        if (win == 3) {
                drawGrid();
                System.out.println("You won diagonally!");
                System.exit(0);
        } else {
                win = 0;
        }
        
        //top right to bottom left diagonal test for player win
        for (int i = 2; i < 7 && win_check == true; i+=2) {
                if (grid[i] == "X") {
                    win++;
                }
        }
        if (win == 3) {
                drawGrid();
                System.out.println("You won diagonally!");
                System.exit(0);
        } else {
                win = 0;
        }
        
        //horizontal test for player win
        for (int i = 0; i < 9 && win_check == true; i+=3) {
                for (int j = 0; j < 3; j++) {
                    if (grid[i+j] == "X") {
                        win++;
                    } else {
                        target = i+j;
                    }
                }
                
                if (win == 3) {
                    System.out.println("You won horizontally!");
                    System.exit(0);
                } else {
                    win = 0;
                    System.out.println("");
                    System.out.println("The game ended in a draw.");
                    win_check = false;
                }
            }
    }
    
    public static int getUserInput() {
        System.out.println("Select your position (1-9)");
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        String input = reader.nextLine();
        int selection = 0;
        try {
               selection = Integer.parseInt(input);
               if (selection <= 9 && selection >= 1 && grid[selection-1] == " ") {        
               } else {
                           selection = 0;
               }
        } catch (NumberFormatException e) {
        }
        if (selection == 0) {
            System.out.println("That wasn't a valid space");
            return getUserInput();
        } else {
            return selection -1;
        }
    }
    
}

