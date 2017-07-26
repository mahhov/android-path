package manuk.path.game.userinterface;

public class UserInterfaceHandler {
	public PlayUserInterface playUserInterface;
	public CharacterUserInterface characterUserInterface;
	
	public UserInterfaceHandler() {
		playUserInterface = new PlayUserInterface();
		characterUserInterface = new CharacterUserInterface();
	}
}
