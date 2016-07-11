package cuLife.actionMessage;

/**
 * Class sends information about mouse click on the board
 * @author super_marcin
 *
 */
public class ActionMessageMousePress extends ActionMessage{
	int i;
	int j;
	public ActionMessageMousePress(int i, int j)
	{
		this.i = i;
		this.j = j;
	}
	/**
	 * This function returns a row
	 * @return
	 */
	public int geti()
	{
		return i;
	}
	/**
	 * This function returns a column
	 * @return
	 */
	public int getj()
	{
		return j;
	}

}
