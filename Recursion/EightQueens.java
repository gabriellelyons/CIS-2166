import java.util.Arrays;

public class EightQueens {

	public static boolean solve(int board[][], int col) {

		if(col >= board.length) {					
			return true;				
		}
		
		for(int row = 0; row < board.length; row++) { 		
			if(valid(board, row, col)) {
				
				//mark board at position with choice 
				board[row][col] = 1;
					
				if(solve(board, col + 1)) {
					return true;
				}
				
				//clear any choices entered at position on board;
				board[row][col] = 0;
			}
		}
		
		return false; //backtrack
	}
	
	public static boolean valid(int board[][], int row, int col) {

		//check rows
		for(int i = col; i > 0; i--) {
			if(board[row][i-1] == 1) {
					return false;
			}
		}

		//check diagonal lower left
		int j = col;
		for(int i = row; i < board.length-1; i++) {
			if(j==0) {
				break;
			}
			if(board[i+1][j-1] == 1) {
				return false;
			}
			j--;
		}
			
		//check diagonal upper left
		j = col;
		for(int i = row; i > 0; i--) {
			if(j==0) {
				break;
			}
			if(board[i-1][j-1] == 1) {
				return false;
			}
			j--;
		}
		
		return true;
	}
	
	
	public static void main(String[] args) {
		int board[][] = new int[8][8];		// [row][column]
	
		solve(board, 0);
		for(int []row: board) {
			System.out.println(Arrays.toString(row));
		}
	}
}
