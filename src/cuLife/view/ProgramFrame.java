package cuLife.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cuLife.actionMessage.ActionMessageChangeRules;
import cuLife.actionMessage.ActionMessageErase;
import cuLife.actionMessage.ActionMessageExit;
import cuLife.actionMessage.ActionMessageLoadBoard;
import cuLife.actionMessage.ActionMessageMousePress;
import cuLife.actionMessage.ActionMessageRandom;
import cuLife.actionMessage.ActionMessageSaveBoard;
import cuLife.actionMessage.ActionMessageStartSimulation;
import cuLife.actionMessage.ActionMessageStep;
import cuLife.actionMessage.ActionMessageStopSimulation;
import cuLife.model.Model;


/**
 * 
 * @author Milan
 * Class that draw table of program
 */
public class ProgramFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6569750258452523289L;
	private final int boardSize;
	private final int cellSize;
	public ProgramFrame(int size, int sizeCell, final View view, Model model) {
		super("Game of life by Central Yourope!");
		this.cellSize = sizeCell;
		this.setLayout(new BorderLayout());
		DrawBoard left = new DrawBoard(size,cellSize, model);
		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.PAGE_AXIS));
		final int boardWidth = size * cellSize+2;
		this.boardSize = size;
		JButton buttonStart = new JButton("Start simulation");
		JButton buttonInfStart = new JButton("Start infinite simulation");
		JButton buttonStop = new JButton("Stop!");
		JButton buttonErase = new JButton("Erase board!");
		JButton buttonExit = new JButton("Exit!");
		JButton buttonStep = new JButton("Step");
		JButton buttonRand = new JButton("Random board");
		JButton buttonLoad = new JButton("Load file");
		JButton buttonSave = new JButton("Save file");
		JButton buttonChangeRules = new JButton("Change rules");
		JLabel stepsLabel = new JLabel("Input number of steps to execute:");
		JLabel timeOutLabel = new JLabel("Input delay betwen steps [ms]:");
		JLabel pathLabel = new JLabel("Input file path:");
		final JTextField stepsField = new JTextField("10000");
		final JTextField timeOutField = new JTextField("50");
		final JTextField filePathField = new JTextField("file.txt");
		buttonStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int timeout=0;
				int iterations=0;
				try{
					timeout = Integer.parseInt(timeOutField.getText());
					iterations = Integer.parseInt(stepsField.getText());
				}
				catch(Exception err)
				{
					JOptionPane.showMessageDialog(null, "Wrong input. Try again!", "Error!", 1);
				}
				ActionMessageStartSimulation startMsg = new ActionMessageStartSimulation(
						timeout, iterations);
				view.insertQueue(startMsg);

			}
		});
		buttonInfStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int timeout=0;
				int iterations=-1;
				try{
					timeout = Integer.parseInt(timeOutField.getText());
				}
				catch(Exception err)
				{
					JOptionPane.showMessageDialog(null, "Wrong input. Try again!", "Error!", 1);
					iterations = 0;
				}
				ActionMessageStartSimulation startMsg = new ActionMessageStartSimulation(
						timeout, iterations);
				view.insertQueue(startMsg);

			}
		});
		buttonStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ActionMessageStopSimulation stopMsg = new ActionMessageStopSimulation();
				view.insertQueue(stopMsg);

			}
		});
		buttonErase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ActionMessageErase eraseMsg = new ActionMessageErase();
				view.insertQueue(eraseMsg);

			}
		});
		buttonStep.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ActionMessageStep stepMsg = new ActionMessageStep();
				view.insertQueue(stepMsg);
			}
		});
		buttonRand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ActionMessageRandom randMsg = new ActionMessageRandom();
				view.insertQueue(randMsg);
			}
		});
		buttonExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ActionMessageExit randMsg = new ActionMessageExit();
				view.insertQueue(randMsg);
			}
		});
		buttonChangeRules.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ActionMessageChangeRules msg = new ActionMessageChangeRules();
				view.insertQueue(msg);
			}
		});
		buttonLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String path=null;
				try{
					path = filePathField.getText();
				}
				catch(Exception err)
				{
					JOptionPane.showMessageDialog(null, "Wrong input. Try again!", "Error!", 1);
				}
				ActionMessageLoadBoard loadMsg = new ActionMessageLoadBoard(
						path);
				view.insertQueue(loadMsg);

			}
		});
		buttonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String path=null;
				try{
					path = filePathField.getText();
				}
				catch(Exception err)
				{
					JOptionPane.showMessageDialog(null, "Wrong input. Try again!", "Error!", 1);
				}
				ActionMessageSaveBoard Msg = new ActionMessageSaveBoard(
						path);
				view.insertQueue(Msg);

			}
		});
		left.setLayout(null);
		right.setPreferredSize(new Dimension(300, 200));
		right.add(timeOutLabel);
		right.add(timeOutField);
		right.add(stepsLabel);
		right.add(stepsField);
		right.add(buttonStart);
		right.add(buttonInfStart);
		right.add(buttonStop);
		right.add(buttonErase);
		right.add(buttonStep);
		right.add(buttonRand);
		right.add(pathLabel);
		right.add(filePathField);
		right.add(buttonLoad);
		right.add(buttonSave);
		right.add(buttonChangeRules);
		right.add(buttonExit);
		timeOutField.setMaximumSize(new Dimension(300,30));
		stepsField.setMaximumSize(new Dimension(300,30));
		filePathField.setMaximumSize(new Dimension(300,30));
		left.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				int i;
				int j;
				i = arg0.getX() / cellSize;
				j = arg0.getY() / cellSize;
				if(i<boardSize && j<boardSize)
				{
					ActionMessageMousePress mouseMsg = new ActionMessageMousePress(i,j);
					view.insertQueue(mouseMsg);
				}
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		left.setPreferredSize(new Dimension(boardWidth, boardWidth));
		getContentPane().add(left, BorderLayout.CENTER);
		getContentPane().add(right, BorderLayout.EAST);
		pack();
		setVisible(true);
	}
}
