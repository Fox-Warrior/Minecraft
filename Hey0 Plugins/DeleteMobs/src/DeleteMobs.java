public class DeleteMobs extends Plugin {


	private DeleteMobs.Listener listener;

	public DeleteMobs() {
		this.listener = new DeleteMobs.Listener();
	}

	public void initialize() {
		etc.getLoader().addListener(PluginLoader.Hook.COMMAND,
				this.listener, this, PluginListener.Priority.MEDIUM);
	}

	public void enable() {
	}

	public void disable() {
	}

	public class Listener extends PluginListener {
		public boolean onCommand(Player player, String[] split) {
			if(split[0].equalsIgnoreCase("/mobsdel")){
				int counter = 0;
				for(Mob v : etc.getServer().getMobList()){
					v.setHealth(0);
					counter++;
				}
				player.sendMessage(Colors.Gold + "[DeleteMobs] " + Colors.Green+ + counter + " Mob(s) deleted.");
				return true;
			}
			return false;
		}

		public Listener() {
		}


	}
}
