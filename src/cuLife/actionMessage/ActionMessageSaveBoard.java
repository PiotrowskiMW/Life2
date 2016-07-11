package cuLife.actionMessage;

public class ActionMessageSaveBoard extends ActionMessage{
	String path;
	public ActionMessageSaveBoard(String path)
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
