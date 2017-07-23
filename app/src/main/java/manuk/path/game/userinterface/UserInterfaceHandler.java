package manuk.path.game.userinterface;

public class UserInterfaceHandler {
	public GameUserInterface gameUserInterface;
	public PauseUserInterface pauseUserInterface;
	
	public UserInterfaceHandler() {
		gameUserInterface = new GameUserInterface();
		pauseUserInterface = new PauseUserInterface();
	}
	
}
