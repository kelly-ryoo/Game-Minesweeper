import java.util.ArrayList;
import java.util.Collection;

public class P5_Ryoo_Kelly_MineSweeperModel implements P5_Ryoo_Kelly_MSModelInterface {

	private int[][] grid; // 0 is not a bomb, 1 is bomb
	private boolean[][] flagGrid;
	private boolean[][] revealed;

	ArrayList<Integer> vChange;
	ArrayList<Integer> hChange;

	public P5_Ryoo_Kelly_MineSweeperModel(int rowsNums, int colsNums, int minesNums) {
		
		grid = new int[rowsNums][colsNums];
		flagGrid = new boolean[rowsNums][colsNums];
		revealed = new boolean[rowsNums][colsNums];
		
		this.setRandomMines(minesNums);

		hChange = new ArrayList<Integer>();
		hChange.add(-1);
		hChange.add(-1);
		hChange.add(-1);
		hChange.add(0);
		hChange.add(0);
		hChange.add(1);
		hChange.add(1);
		hChange.add(1);

		vChange = new ArrayList<Integer>();
		vChange.add(-1);
		vChange.add(0);
		vChange.add(1);
		vChange.add(-1);
		vChange.add(1);
		vChange.add(-1);
		vChange.add(0);
		vChange.add(1);

		for (int i = 0; i < grid.length; i++) {
			for (int k = 0; k < grid[0].length; k++) {
				grid[i][k] = 0;
			}
		}

		for (int i = 0; i < grid.length; i++) {
			for (int k = 0; k < grid[0].length; k++) {
				flagGrid[i][k] = false;
			}
		}

		for (int i = 0; i < grid.length; i++) {
			for (int k = 0; k < grid[0].length; k++) {
				revealed[i][k] = false;
			}
		}

	}

	public int totalBombCount() {
		int count = 0;

		for (int rows = 0; rows < getRowNum(); rows++) {
			for (int cols = 0; cols < getColNum(); cols++) {

				if (getGridValue(rows, cols) == 1) { // if its alive
					count++;
				}

			}
		}

		return count;
	}
	


	public boolean isMine(int rows, int cols) {
		if (rows < getRowNum() && rows >= 0 && cols < getColNum() && cols >= 0) {
			if (grid[rows][cols] == 1) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void setRandomMines(int numMines) {

		for (int i = 0; i < numMines; i++) {
			int rowNums = (int)(Math.random() * getRowNum());
			int colNums = (int)(Math.random() * getColNum());
			
			if (!isMine(rowNums, colNums)) {
				setBomb(rowNums, colNums);
			} else {
				i--;
			}
		}

	}

	@Override
	public int getGridValue(int rows, int cols) {
		return grid[rows][cols];
	}

	public int getRowNum() {
		return grid.length;
	}

	public int getColNum() {
		return grid[0].length;
	}

	public void setBomb(int rows, int cols) {
		grid[rows][cols] = 1;
	}

	public void setGrid(int[][] grids) {
		grid = grids;
	}

	public int getNumMineNeighbors(int rows, int cols) {

		int count = 0;

		int rowNum = rows;
		int colNum = cols;

		for (int i = 0; i < 8; i++) {

			rowNum = rows + hChange.get(i);
			colNum = cols + vChange.get(i);
			// System.out.println(rowNum);
			// System.out.println(colNum);

			if (rowNum < getRowNum() && rowNum >= 0 && colNum < getColNum() && colNum >= 0) {

				if (isMine(rowNum, colNum)) {
					count++;
					rowNum = rows;
					colNum = cols;
				}
			}

		}

		return count;

	}

	public boolean isInBounds(int rows, int cols) {
		if (!(rows < grid[0].length)) {
			return false;
		} else if (!(rows < grid[0].length)) {
			return false;
		}

		return true;
	}

	@Override
	public void setFlag(int rows, int cols) {
		flagGrid[rows][cols] = true;
	}

	/*
	public void placeMines(int numMines) {
		for (int i = 0; i < numMines; i++) {
			int randRow = (int) ((Math.random()) * grid.length);
			int randCol = (int) ((Math.random()) * grid[0].length);

			grid[randRow][randCol] = 1;
		}
	}
	*/
	
	public void clearMines() {
		for(int i = 0;i < getRowNum(); i++) {
			for(int k = 0; k < getColNum(); k++) {
				grid[i][k] = 0;
			}
		}
	}

	@Override
	public void reveal(int rows, int cols) {

		if(!isRevealed(rows, cols) && grid[rows][cols] == 0 && !isMine(rows, cols)) {
			revealed[rows][cols] = true;
			//System.out.println("revealed: row " + rows + " and col " + cols);
		}
		if(getNumMineNeighbors(rows, cols) == 0){
			int row;
			int col;
			for (int i = 0; i < 8; i++) {

				row = rows + hChange.get(i);
				col = cols + vChange.get(i);

				if (row < getRowNum() && row >= 0 && col < getColNum() && col >= 0) {
					if (grid[row][col] == 0 && !isRevealed(row, col) && !isMine(rows, cols)) {
						reveal(row, col);
						//System.out.println("revealing: row " + row + " and col " + col);
					}
				}
			}
		}

	}

	@Override
	public boolean isRevealed(int rows, int cols) {
		if (revealed[rows][cols]) {
			return true;
		}
		return false;
	}

	@Override
	public void removeFlag(int rows, int cols) {
		flagGrid[rows][cols] = false;
	}
	
	
	

	public boolean isGameWon() {

		for (int i = 0; i < getRowNum(); i++) {
			for (int k = 0; k < getColNum(); k++) {
				boolean isBomb = false;
				if (grid[i][k] == 1 && !isFlagged(i, k)) {
					return false;
				}
			}
		}

		return true;
	}

	public int numFlags() {
		int count = 0;
		for (int i = 0; i < getRowNum(); i++) {
			for (int k = 0; k < getColNum(); k++) {
				if (flagGrid[i][k]) {
					count++;
				}
			}
		}

		return count;
	}

	public void reset() {

		flagGrid = new boolean[grid[0].length][grid.length];
		for (int i = 0; i < grid[0].length; i++) {
			for (int k = 0; k < grid.length; k++) {
				flagGrid[i][k] = false;
			}
		}

		revealed = new boolean[grid[0].length][grid.length];
		for (int i = 0; i < grid[0].length; i++) {
			for (int k = 0; k < grid.length; k++) {
				revealed[i][k] = false;
			}
		}
	}
	
	public void revealOne(int rows, int cols) {
		revealed[rows][cols] = true;
	}

	public boolean isFlagged(int rows, int cols) {
		if (flagGrid[rows][cols]) {
			return true;
		}
		return false;
	}
	
	public void levelM(int row, int col, int mine) {
		grid = new int[row][col];
		flagGrid = new boolean[row][col];
		revealed = new boolean[row][col];
		for (int i = 0; i < grid.length; i++) {
			for (int k = 0; k < grid[0].length; k++) {
				grid[i][k] = 0;
			}
		}

		for (int i = 0; i < grid.length; i++) {
			for (int k = 0; k < grid[0].length; k++) {
				flagGrid[i][k] = false;
			}
		}

		for (int i = 0; i < grid.length; i++) {
			for (int k = 0; k < grid[0].length; k++) {
				revealed[i][k] = false;
			}
		}
		setRandomMines(mine);
	}

	@Override
	public void setSize(int rows, int cols) {
		this.grid = new int[rows][cols];
		reset();
		
	}


}
