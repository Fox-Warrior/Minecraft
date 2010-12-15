import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MiniBlockPlugin extends Plugin {
	private MiniBlockPlugin.Listener listener;
	boolean Enabled = false;
	int Delay;
	Player Enabler;
	Timer timer;          // Timer déclenchant des tics
	PropertiesFile Settings = new PropertiesFile("server.properties");

	
	public MiniBlockPlugin() {
		this.listener = new MiniBlockPlugin.Listener();
	}
	
	
    ActionListener action = new ActionListener ()
    {
      // Méthode appelée à chaque tic du timer
      public void actionPerformed (ActionEvent event)
      {
        	etc.DeleteMiniBlocks();
      }
    };
	
	
	public void initialize() {
		etc.getLoader().addListener(PluginLoader.Hook.COMMAND,
				this.listener, this, PluginListener.Priority.MEDIUM);
		Enabled = Settings.getBoolean("AutoRemoveMiniBlocks", false);
		Delay = Settings.getInt("AutoRemoveMiniBlocksDelay", 5000);
		timer = new Timer(Delay, action);
		if(Enabled == true){
			timer.start();
		}
	}
	
	public void disable() {
		
	}


	public void enable() {
		
	}



	
	public class Listener extends PluginListener {

		public Listener() {
		}

		public boolean onCommand(Player arg0, String[] arg1) {
			if(arg1[0].equalsIgnoreCase("/mbdelauto")){
				Enabler = arg0;
				if(Enabled == false)
				{
					arg0.sendMessage("Automatic MiniBlock deleter enabled!");
					Enabled = true;
					if(arg1.length > 1){
						timer.setDelay(Delay);
						Settings.setInt("AutoRemoveMiniBlocksDelay", Integer.parseInt(arg1[1]));
					}
					timer.start();
					Settings.setBoolean("AutoRemoveMiniBlocks", true);
				} else {
					arg0.sendMessage("Automatic MiniBlock deleter disabled!");
					Enabled = false;
					timer.stop();
					Settings.setBoolean("AutoRemoveMiniBlocks", false);
				}
			}
			return false;
		}
		
		
		
	}

}
