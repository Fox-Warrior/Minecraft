import net.minecraft.server.MinecraftServer;

public class InstantDel extends Plugin {

	private MinecraftServer world = etc.getMCServer();

	private InstantDel.Listener listener;

	public InstantDel() {
		this.listener = new InstantDel.Listener();
	}

	public void initialize() {
		etc.getLoader().addListener(PluginLoader.Hook.BLOCK_CREATED,
				this.listener, this, PluginListener.Priority.MEDIUM);
	}

	public void enable() {
	}

	public void disable() {
	}

	public class Listener extends PluginListener {
		public Listener() {
		}

		public boolean onBlockCreate(Player player, Block blockPlaced,
				Block blockClicked, int itemInHand) {
			if (itemInHand == 278) {
				world.e.d(blockClicked.getX(), blockClicked.getY(),
						blockClicked.getZ(), 0);
				Item item = new Item();
				item.setItemId(blockClicked.getType());
				player.giveItem(item);
				return true;
			}
			return false;
		}
	}
}
