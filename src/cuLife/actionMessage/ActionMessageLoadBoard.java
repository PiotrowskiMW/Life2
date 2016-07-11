package cuLife.actionMessage;

public class ActionMessageLoadBoard extends ActionMessage{
	String path;
	public ActionMessageLoadBoard(String path)
	{
		this.path = path;
	}
	/**
	 * This function returns path to a file
	 * @return
	 */
	public String getPath()
	{
		return path;
	}
}
