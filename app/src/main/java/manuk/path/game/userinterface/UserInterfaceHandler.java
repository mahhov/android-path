package manuk.path.game.userinterface;

public class UserInterfaceHandler {
	public PlayUserInterface playUserInterface;
	public PauseUserInterface pauseUserInterface;
	
	public UserInterfaceHandler() {
		playUserInterface = new PlayUserInterface();
		pauseUserInterface = new PauseUserInterface();
	}
}
