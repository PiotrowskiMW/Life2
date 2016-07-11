package cuLife.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import cuLife.Logger;
import cuLife.actionMessage.ActionMessage;
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
import cuLife.view.View;


/**
 * Controller class 
 * 
 * @author super_marcin
 *
 */
public class Controller {

	BlockingQueue<ActionMessage> messageQueue;
	Simulation simulationThread;
	private final Map<Class<? extends ActionMessage>, Strategy> strategyMap;
	Model modelLink;
	View viewLink;

	/**
	 * class constructor 
	 * @param queue reference to event queue
	 * @param model reference to model
	 * @param view reference to view
	 */
	public Controller(BlockingQueue<ActionMessage> queue, Model model,
			View view) {
		messageQueue = queue;
		modelLink = model;
		viewLink = view;
		this.strategyMap = new HashMap<Class<? extends ActionMessage>, Strategy>();
		addStategies();
		run();
	}

	/**
	 * Function which handle execution of the program
	 */
	void run() {
		while (true) {
			try {

				ActionMessage msg = messageQueue.take();
				strategyMap.get(msg.getClass()).executeMessage(msg);
			} catch (InterruptedException e) {
				Logger.log("Error with taking event from queue!");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Adding strategies to map
	 */
	private void addStategies() {
		strategyMap.put(ActionMessageMousePress.class, new MouseStrategy());
		strategyMap.put(ActionMessageStep.class, new StepStrategy());
		strategyMap.put(ActionMessageStartSimulation.class,
				new StartSimulationStrategy());
		strategyMap.put(ActionMessageRandom.class, new RandStrategy());
		strategyMap.put(ActionMessageStopSimulation.class, new StopStrategy());
		strategyMap.put(ActionMessageExit.class, new ExitStrategy());
		strategyMap.put(ActionMessageErase.class, new EraseStrategy());
		strategyMap.put(ActionMessageLoadBoard.class, new LoadStrategy());
		strategyMap.put(ActionMessageSaveBoard.class, new SaveStrategy());
		strategyMap.put(ActionMessageChangeRules.class, new ChangeRulesStrategy());
	}

	/**
	 * function which erase board 
	 * @param msg
	 */
	private void doErase(ActionMessageErase msg) {
		modelLink.erase();
		viewLink.repaintBoard();
	}
	
	private void doMouse(ActionMessageMousePress msg) {
		int i, j;
		i = msg.geti();
		j = msg.getj();
		modelLink.toggle(i, j);
		viewLink.repaintBoard();
	}

	private void doStop(ActionMessageStopSimulation msg) {
		if (simulationThread != null) {
			simulationThread.changeIsGoing();
			simulationThread = null;
		}
	}

	private void doExit(ActionMessageExit msg) {
		System.exit(0);
	}

	private void doStep(ActionMessageStep msg) {
		modelLink.iterate();
		viewLink.repaintBoard();
	}
	private void doLoad(ActionMessageLoadBoard msg) {
		modelLink.loadFile(msg.getPath());
		viewLink.repaintBoard();
	}
	private void doSave(ActionMessageSaveBoard msg) {
		modelLink.saveFile(msg.getPath());
		viewLink.repaintBoard();
	}
	private void doChangeRules(ActionMessageChangeRules msg) {
		modelLink.changeRules();
		viewLink.repaintBoard();
	}

	private void startSymulation(ActionMessageStartSimulation msg) {
		if (simulationThread == null) {
			simulationThread = new Simulation(msg.getTimeOut(), msg.getSteps());
			new Thread(simulationThread).start();
		}
	}

	/**
	 * Class that implements a new thread to handle the simulation
	 * @author super_marcin
	 *
	 */
	private class Simulation implements Runnable {
		boolean isGoing = true;
		int timeOut;
		int steps;

		Simulation(int timeOut, int steps) {
			this.timeOut = timeOut;
			this.steps = steps;
		}

		@Override
		public void run() {
			while (steps != 0 && isGoing) {
				modelLink.iterate();
				viewLink.repaintBoard();
				try {
					Thread.sleep(timeOut);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
				steps--;
			}
			simulationThread =null;
		}

		void changeIsGoing() {
			isGoing = false;
		}
	}

	private void doRand(ActionMessageRandom msg) {
		modelLink.getRandomBoard();
		viewLink.repaintBoard();
	}

	private abstract class Strategy {
		abstract void executeMessage(ActionMessage msg);
	}

	private class StartSimulationStrategy extends Strategy {
		@Override
		void executeMessage(final ActionMessage msg) {
			startSymulation((ActionMessageStartSimulation) msg);
		}
	}

	private class StepStrategy extends Strategy {
		@Override
		void executeMessage(final ActionMessage msg) {
			doStep((ActionMessageStep) msg);
		}
	}

	private class MouseStrategy extends Strategy {
		@Override
		void executeMessage(final ActionMessage msg) {
			doMouse((ActionMessageMousePress) msg);
		}
	}

	private class RandStrategy extends Strategy {
		@Override
		void executeMessage(final ActionMessage msg) {
			doRand((ActionMessageRandom) msg);
		}
	}

	private class StopStrategy extends Strategy {
		@Override
		void executeMessage(final ActionMessage msg) {
			doStop((ActionMessageStopSimulation) msg);
		}
	}

	private class ExitStrategy extends Strategy {
		@Override
		void executeMessage(final ActionMessage msg) {
			doExit((ActionMessageExit) msg);
		}
	}

	private class EraseStrategy extends Strategy {
		@Override
		void executeMessage(final ActionMessage msg) {
			doErase((ActionMessageErase) msg);
		}
	}
	private class LoadStrategy extends Strategy {
		@Override
		void executeMessage(final ActionMessage msg) {
			doLoad((ActionMessageLoadBoard) msg);
		}
	}
	private class SaveStrategy extends Strategy {
		@Override
		void executeMessage(final ActionMessage msg) {
			doSave((ActionMessageSaveBoard) msg);
		}
	}
	private class ChangeRulesStrategy extends Strategy {
		@Override
		void executeMessage(final ActionMessage msg) {
			doChangeRules((ActionMessageChangeRules) msg);
		}
	}
}
