
public interface P5_Ryoo_Kelly_MSModelInterface {
	
	public boolean isMine(int row, int col);
	
	public void setRandomMines(int numMines);
	
	public int getRowNum();
	
	public int getColNum();
	
	public void setBomb(int row, int col);
	
	public int getGridValue(int row, int col);
	
	public void setGrid(int[][] grid);
	
	public int getNumMineNeighbors(int row, int col);
	
	public boolean isInBounds(int row, int col);
	
	public void setFlag(int row, int col);
	
	
	public void reveal(int row, int col); // RECURSUVE
	
	public boolean isRevealed(int row, int col);
	
	public void removeFlag(int row, int col);
	
	public boolean isGameWon();
	
	public int numFlags();
	
	public void setSize(int row, int col);
	
	
	public void reset();
	
	
}
