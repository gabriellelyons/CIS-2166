import java.util.Arrays;

public class Sudoku {
	
	public static boolean solve(int board[][], int row, int col) {		
		
		if(col >= 9) {
			row++;
			col = 0;
			
		} if(row >= 9){
			return true;	
		}
		
		if(board[row][col] != 0) {
			return solve(board,row,col + 1);
		}
		
		for(int choice = 1; choice <= 9; choice++) {
			if(valid(board, row, col, choice)) {
				//mark board at position with choice 
				board[row][col] = choice;
			
				if(solve(board, row, col + 1)) {
					return true;
				}
			}
		}

		//clear any choices entered at position on board;
		board[row][col] = 0;
		
		return false; //backtrack
	}
	
	public static boolean valid(int board[][], int row, int col, int choice) {
		//check rows
		for(int c = 0; c < board.length; c++) {
			if(c != col && board[row][c] == choice) {
					return false;
			}
		}
		
		//check columns
		for(int r = 0; r < board.length; r++) {
			if(r != row && board[r][col] == choice) {
					return false;
			}
		}
		
		//gets coordinates of upper left cell to base check on
		int i = (row / 3) * 3;
		int j = (col /3) * 3;
		//check 3x3 boxes
		for(int r = i; r < i + 3; r++) {
			for(int c = j; c < j + 3; c++) {
				if(board[r][c] == choice) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public static void puzzleSetUp(int board[][]) {
		board[0][0] = 5;	board[0][1] = 3;	board[0][4] = 7;
		board[1][0] = 6;	board[1][3] = 1; 	board[1][4] = 9; 	board[1][5] = 5;
		board[2][1] = 9;	board[2][2] = 8;	board[2][7] = 6;
		board[3][0] = 8;	board[3][4] = 6;	board[3][8] = 3;
		board[4][0] = 4;	board[4][3] = 8;	board[4][5] = 3;	board[4][8] = 1;
		board[5][0] = 7;	board[5][4] = 2;	board[5][8] = 6;
		board[6][1] = 6; 	board[6][6] = 2;	board[6][7] = 8;
		board[7][3] = 4;	board[7][4] = 1;	board[7][5] = 9;	board[7][8] = 5;
		board[8][4] = 8;	board[8][7] = 7;	board[8][8] = 9;			
	}
	
	public static void main(String[] args) {
		int board[][] = new int[9][9];		// [row][column]
		puzzleSetUp(board);
		
		solve(board, 0, 0);
		for(int []row: board) {
			System.out.println(Arrays.toString(row));
		}
	}
}
