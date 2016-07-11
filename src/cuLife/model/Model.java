
package cuLife.model;

/**
 * Model class
 * @author Zdenek
 *
 */
public class Model
{
	private final Board mainBoard;
        private int ruleType;
	
	public Model(int size)
	{
		mainBoard = new Board(size);
                ruleType = 0;
		
	}
	/**
	 * This function returns the state of the cell i and j
	 * @param i row
	 * @param j column
	 * @return
	 */
	public boolean state(int i, int j)
	{
		return mainBoard.checkCell(i, j);
	}
	/**
	 * The changing state of cell i, j on the opposite
	 * @param i row
	 * @param j column
	 */
	public void toggle(int i, int j)
	{
		mainBoard.toggleCell(i, j);
	}
	/**
	 * A function that generates a random game board
	 */
	public void getRandomBoard()
	{
		mainBoard.randomBoard();
	}
	/**
	 * A function that do one iteration
	 */
	public void iterate()
	{
		mainBoard.iterate(this.ruleType%3);
	}
	/**
	 * A function that reset board
	 */
	public void erase()
	{
		mainBoard.erase();
	}
	public void loadFile(String path)
	{
		mainBoard.erase();
                mainBoard.initializeWithLoadedValues(path);
	}
	public void saveFile(String path)
	{
		//mainBoard.erase();
		///todo implement this function
            mainBoard.saveState(path);
	}
	public void changeRules()
	{
		mainBoard.randomBoard();
                this.ruleType++;
		///todo implement this function
	}
}
