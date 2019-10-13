package launch;

import controller.BoardController;
import controller.MainController;
import view.MainView;

public class LaunchApp {
	public static void main(String[] args) {
		MainView mainView= new MainView();
		BoardController boardController = new BoardController(mainView);
		MainController mainController = new MainController(mainView);
		
		mainView.setMainController(mainController);
		mainView.setBoardController(boardController);
	}
}
