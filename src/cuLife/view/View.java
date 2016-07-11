package cuLife.view;

import java.util.concurrent.BlockingQueue;

import cuLife.Logger;
import cuLife.actionMessage.ActionMessage;
import cuLife.model.Model;






/**
 * Class that implements the view
 * @author Milan
 *
 */
public class View {

	Model modelLink;
	BlockingQueue<ActionMessage> messageQueue;
	ProgramFrame progFrame;
	public View(int sizeC,int cellSize, BlockingQueue<ActionMessage> queue,Model model)
	{
		modelLink = model;
		messageQueue = queue;
		progFrame = new ProgramFrame(sizeC,cellSize,this,modelLink);

	}
	/**
    * Function to insert a message about the incident on board to the queue
	 */
	void insertQueue(ActionMessage message)
	{
		try {
			messageQueue.put(message);
		} catch (InterruptedException e) {
			Logger.log("Error during adding item to queue");
			e.printStackTrace();
		}
	}
	/**
	 * Refresh of board
	 */
	public void repaintBoard()
	{
		progFrame.repaint();
	}
}
