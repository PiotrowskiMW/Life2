
package cuLife;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

import javax.swing.JOptionPane;

import cuLife.actionMessage.ActionMessage;
import cuLife.controller.Controller;
import cuLife.model.Model;
import cuLife.view.View;



/**
 * Class which start the program
 * @author super_marcin
 *
 */
public class Execute {

	/**
	 * main function
	 * @param args
	 */
	public static void main(String[] args)
	{
		BlockingQueue<ActionMessage> actionQueue = new LinkedTransferQueue<ActionMessage>();
		/**
		 * Asking user about board size
		 */
		String strsize = (String) JOptionPane.showInputDialog(null,"Input board size:","Board Size.",JOptionPane.PLAIN_MESSAGE);
		String strCellSize = (String) JOptionPane.showInputDialog(null,"Input size of cell in pixels:","Start new game!",JOptionPane.PLAIN_MESSAGE);
		int size;
		int cellSize;
		try{
			size = Integer.parseInt(strsize);
			cellSize = Integer.parseInt(strCellSize);
		}catch(Exception e)
		{
			size = 100;
			cellSize = 5;
			JOptionPane.showMessageDialog(null, "Invalid input data, Default board will be displayed!", "Error!", 1);
		}
		Model model = new Model(size);
		View view = new View(size,cellSize,actionQueue,model);
		Controller ctrl = new Controller(actionQueue,model,view);
	}
}
