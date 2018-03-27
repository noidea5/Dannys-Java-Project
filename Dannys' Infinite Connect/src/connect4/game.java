package connect4;

import java.util.Arrays;
import java.util.Scanner;

public class game {
	//make constructor for a game
	static int[][] grid = new int[9][9];
	static int inARowLength = 3;
	//this int can be changed by the user, depending on how many they want to be needed in a row to win the game
	
	static boolean setUpByUser = false;
	//boolean keeps track of whether or not the game has been initialized yet
	
	static char[] letters;
	//the letters correspond to the indexes of the rows of the grid
	static boolean gravityMode = true;
	//dictates whether or not you can place the pieces anywhere, or if they fall to the bottom of the column

	public static void main(String[] args) {
		initializeSettings();
	}
	
	public static void initializeSettings() {
		setUpByUser = false;
		letters = new char[9];
		//resets letters array to all 9 posibile letters, in a row, which will eventually be cut down and flipped in order to position them accordingly in the grid that is displayed to the user.
		letters[0] = 'a'; letters[1] = 'b'; letters[2] = 'c'; letters[3] = 'd'; letters[4] = 'e'; letters[5] = 'f'; letters[6] = 'g'; letters[7] = 'h'; letters[8] = 'i';
		System.out.println("Welcome to Infinite Connect. This is a two player game. Or you could be lonely and play against yourself.");
		int collumnCount = getUserInput(9,1,"how many columns should there be?") + 1;
		int rowCount = getUserInput(9,1,"how many rows should there be?") + 1;
		if (getUserInput(2,1,"do you want there to be gravity? 1 for yes, 2 for no.") + 1 == 1) {
			gravityMode = true; 
		} else {
			gravityMode = false;
		}
		grid = new int[collumnCount][rowCount];
	//resets the global grid array to the size that the user wants
		inARowLength = getUserInput(returnBigger(collumnCount,rowCount),1,"how many should be needed in a row to win?") + 1;	
	//the following code adjusts the array of strings to add a letter for each row when the board is displayed
		char[] temporaryArray = new char[rowCount];
		for (int i = 0; i < rowCount; i ++) {
			temporaryArray[i] = letters[i];
		}
		letters = reverse(temporaryArray);
		setUpByUser = true;
		//the getUserInput method win only print the board to the user if this method is set to true
		
		//starts the game
		playerPlays(1);
	}
	
	//reverse an array code inspired by Bill the Lizard on Stack Overflow--https://stackoverflow.com/questions/2137755/how-do-i-reverse-an-int-array-in-java
		public static char[] reverse(char[] data) {
		    int left = 0;
		    int right = data.length - 1;
		    while( left < right ) {
		        // swap the values at the left and right indices
		        char letter = data[left];
		        data[left] = data[right];
		        data[right] = letter;
		        // move the left and right index pointers in toward the center
		        left++;
		        right--;
		    }
		    return data;
		}
	
	public static int returnBigger(int x, int y) {
		if (x>y) {
			return x;
		} else {
			return y;
		}
		//returns which number is bigger
	}
	
	
	public static int[] chooseBetweenModes(int player) {
		if (gravityMode) {
			return fillBoard(getUserInput(grid.length,player,"which column?"),player);
		} else {
			return getUserInputWithoutGravity(player);
		}
		//getUserInputWithout Gravity and the above composed functions (with fillBoard and getUserInput) are the equivalents, but for different game modes
	}
	
	public static void playerPlays(int player) {
		//prompts the current player to place their piece, based on the game mode, and then checks for either a win or draw
		if (winCheck(chooseBetweenModes(player),player)) {
			System.out.println("Player " + convertToGamePiece(player) + " wins!");
			//restarts the game if the user wants
			if (getUserInput(2, 1, "would you like to play again? 1 for yes, 2 for no.") == 0) initializeSettings();
			
		} else {
			//checks for draw
			if (boardIsFull()) {
				System.out.println("It's a tie.");
				//restarts the game if the user wants
				if (getUserInput(2, 1, "would you like to play again? 1 for yes, 2 for no.") == 0) initializeSettings();
				
			} else {
				//advances to the next turn
				playerPlays(((player + 2)%2)+1);
			}
		}
	}
	
	public static boolean boardIsFull() {
		boolean toReturn = true;
		for (int i = 0; i < grid.length*grid[0].length; i ++) {
			if (grid[i%grid.length][i/grid.length] == 0) {
				toReturn = false;
				i = grid.length*grid[0].length;
				//sets i to the max to prevent loop from running more
			}
		}
		return toReturn;	
	}
	
	public static boolean winCheck(int[] coordinatePlaced, int playerWhoPlayed) {
		boolean didWin = false;
		//vertical
		String colString = "";
		for (int i = 0; i < grid[0].length; i++) {
			colString += Integer.toString(grid[coordinatePlaced[0]][i]);
		}
		//horizontal
		String rowString = "";
		for (int i = 0; i < grid.length; i++) {
			rowString += Integer.toString(grid[i][coordinatePlaced[1]]);
		}
		didWin = testForWinWithinString(colString, playerWhoPlayed);
		if (didWin != true) didWin = testForWinWithinString(rowString, playerWhoPlayed);
		//diagonals 1 for positive slope, -1 for negative slope
		if (didWin != true) didWin = diagonalTest(1,coordinatePlaced,playerWhoPlayed);
		if (didWin != true) didWin = diagonalTest(-1,coordinatePlaced,playerWhoPlayed);
		return didWin;
	}
	
	//this method can be used for both diagonals, by adjusting the slope
	public static boolean diagonalTest(int slope, int[] coordinatePlaced, int playerWhoPlayed) {
		String diagString = "";
		for (int i = -coordinatePlaced[0]; i < grid.length - coordinatePlaced[0]; i ++) {
			if (coordinatePlaced[1]+slope*i >= 0 && coordinatePlaced[1]+slope*i <= grid[0].length-1) {
				diagString += Integer.toString(grid[coordinatePlaced[0]+i][coordinatePlaced[1]+slope*i]);
			}
		}
		return testForWinWithinString(diagString, playerWhoPlayed);
	}
	
	//the following method takes a string of characters, either 1s, 2s, or 0s, and checks if there are 4 consecutive pieces from a given player
	public static boolean testForWinWithinString(String sequence, int player) {
		boolean toReturn = false;
		String[] splitSequenceBy0 = sequence.split("0");
		for (int u = 0; u < splitSequenceBy0.length; u ++) {
			String[] splitSequence = splitSequenceBy0[u].split(Integer.toString(((player + 2)%2)+1));
			for (int r = 0; r < splitSequence.length; r ++) {
				if (splitSequence[r].length() > inARowLength-1) {
					toReturn = true;
				}
			}
		}
		return toReturn;
	}
	
	//this method is used to display something other than 0s, 1s, and 2s to the user
	public static String convertToGamePiece(int gridValue) {
		if (gridValue == 1) {
			return "X";
		} else if (gridValue == 2) {
			return "O";
		} else {
			return "|";
		}
	}
	
	public static void printGrid() {
		System.out.print("  ");
		for (int row = grid[0].length; row >= 0; row--) {
			for (int column = -1; column < grid.length; column++) {
				if (column == -1 && row != grid[0].length) {
					System.out.print(letters[row] + " ");		
				} else if (row != grid[0].length) {
					System.out.print(convertToGamePiece(grid[column][row]) + " ");
				} else if (column != -1) {
					System.out.print(Integer.toString(column+1) + " ");	
				}
			}
			if (row != grid[0].length) {
				System.out.println("");
			} else {
				//the following code adds the "interface" that distinguishes the slots that a piece can be placed in (for each column, a v is added as an arrow)
				System.out.println("");
				System.out.print("  v");
				for (int i = 0; i < grid.length-1; i ++) {
					System.out.print("-v");
				}
				System.out.println("");	
			}
		}
	}
	
	public static int[] fillBoard(int columnPlaced, int player) {
		int i = 0;
		boolean finishedPlacing = false;
		while (i < grid[columnPlaced].length && finishedPlacing == false) {
			if (grid[columnPlaced][i] != 1 && grid[columnPlaced][i] != 2) {
				finishedPlacing = true;
				i --;
				//subtracts 1 to cancel out the subsequent addition of i, thereby leaving i as the correct value within the column where the checker is placed.
			}
			i ++;
		}
		if (i == grid[columnPlaced].length) {
			System.out.println("Sorry, that column is all filled up.");
			return fillBoard(getUserInput(grid.length,player,"which column?"),player);
		} else {
			//grid part set
			grid[columnPlaced][i] = player;
			int[] arrayToReturn = {columnPlaced,i};
			//the place that the piece has been placed is returned in order to be subsequently used in the check for win method
			return arrayToReturn;
		}
	}
	//code to safely have user input a natural number
	public static int getUserInput(int optionsCount, int player, String message) {
		if (setUpByUser) printGrid();
		System.out.println(">>" + "Player " + convertToGamePiece(player) + ", " + message + " Input an integer from 1 to " + Integer.toString(optionsCount));
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		String input = reader.nextLine();
		int selection = 0;
		try {
		       selection = Integer.parseInt(input);
		       if (selection <= optionsCount && selection >= 1) {		
		       } else {
		    	   		selection = 0;
		       }
		} catch (NumberFormatException e) {
			//catches errors, and if caught, the method will run itself again since the selection int has not been updated.
		}
		if (selection == 0) {
			System.out.println("That wasn't a valid input");
			return getUserInput(optionsCount,player,message);
		} else {
			return selection -1;
		}
	}
	
//
	public static int[] placeWithoutGravity(String coordinate, int player) {
		int row = -1;
		int column = Integer.parseInt(Character.toString(coordinate.charAt(0)))-1; 
		for (int i = 0; i < letters.length; i ++) {
			if (letters[i] == coordinate.charAt(1)) 
				row = i;
		}
		//sets that part of the grid as the current plauer
		grid[column][row] = player;
		int[] toReturn = {column,row};
		//the place that the piece has been placed is returned in order to be subsequently used in the check for win method
		return toReturn;
	}
	
//this method returns the row number that the computer can process, from the character the user has inputed
	public static int convertLetterToInt(char row) {
		int yCoor = -1;
		for (int i = 0; i < letters.length; i ++) {
			if (letters[i] == row)
				yCoor = i;
		}
		return yCoor;
	}
	
	public static int[] getUserInputWithoutGravity(int player) {
		int[] coordinate = new int[2];
		if (setUpByUser) printGrid();
		System.out.println(">>" + "Player " + convertToGamePiece(player) + ", type in the coordinate of where you want to play, in the form number, then letter. Like this: 1a");
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		String input = reader.nextLine();
		boolean works = false;
		if (input.length() == 2) {
			try {
				int xCoor = Integer.parseInt(Character.toString(input.charAt(0))) -1;
				int yCoor = convertLetterToInt(input.charAt(1));
				
				if (xCoor < grid.length && xCoor >= 0 && yCoor != -1) {
					coordinate[0] = xCoor;
					coordinate[1] = yCoor;
					works = true;
				}
			} catch (NumberFormatException e) {
				//if an error occurs (like the user's input cannot be converted the the correct format, nothing is broken since this empty section runs)
			}
		}
		if (works && grid[coordinate[0]][coordinate[1]] == 0) {
			grid[coordinate[0]][coordinate[1]] = player;
			return coordinate;
		} else {
			System.out.println("That wasn't a valid coordinate.");
			return getUserInputWithoutGravity(player);
		}
	}

}