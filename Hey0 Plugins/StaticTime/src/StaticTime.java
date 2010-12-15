import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Logger;


public class StaticTime extends Plugin {
	private StaticTime.Listener listener;
	boolean Enabled = false;
	int Delay;
	int Clock;
	Player Enabler;
	Timer timer;          // Timer déclenchant des tics
	PropertiesFile Settings = new PropertiesFile("server.properties");
	static final Logger log = Logger.getLogger("Minecraft");
	static final float version = 1.1F;

	
	public StaticTime() {
		this.listener = new StaticTime.Listener();
	}
	
	
    ActionListener action = new ActionListener ()
    {
      // Méthode appelée à chaque tic du timer
      public void actionPerformed (ActionEvent event)
      {
        	etc.getServer().setTime(Clock);
      }
    };
	
	
	public void RegisterCommands(){
		etc.getInstance().addCommand("/statictime <Delay> <Time>|/st <Delay> <Time> -", "Fix the server time");
	}
    
	public void initialize() {
		etc.getLoader().addListener(PluginLoader.Hook.COMMAND,
				this.listener, this, PluginListener.Priority.MEDIUM);
		Enabled = Settings.getBoolean("AutoDay", false);
		Delay = Settings.getInt("StaticTimeDelay", 5000);
		Clock = Settings.getInt("StaticTimeClock", 6000);
		timer = new Timer(Delay, action);
		if(Enabled == true){
			timer.start();
		}
		RegisterCommands();
		StaticTime.log.info("[StaticTime plugin version " + version + "] : successfuly loaded.");
	}
	
	public void disable() {
		Enabled = false;
		timer.stop();
		timer = null;
	}


	public void enable() {
		Enabled = Settings.getBoolean("StaticTime", false);
		Delay = Settings.getInt("StaticTimeDelay", 5000);
		Clock = Settings.getInt("StaticTimeClock", 6000);
		timer = new Timer(Delay, action);
		if(Enabled == true){
			timer.start();
		}
	}



	
	public class Listener extends PluginListener {

		public Listener() {
		}

		public boolean onCommand(Player arg0, String[] arg1) {
			if(arg1[0].equalsIgnoreCase("/statictime") || arg1[0].equalsIgnoreCase("/st")){
				Enabler = arg0;
				if(Enabled == false)
				{
					for(Player player : etc.getServer().getPlayerList()){
						player.sendMessage(Colors.Gold + "[StaticTime] " + Colors.Red + "StaticTime enabled (Clock: " + Clock + " interval: " + Delay + ")");
					}
					
					Enabled = true;
					if(arg1.length > 1){
						Delay = Integer.parseInt(arg1[1]);
						timer.setDelay(Delay);
						Settings.setInt("StaticTimeDelay", Integer.parseInt(arg1[1]));
					}
					if(arg1.length > 2){
						Clock = Integer.parseInt(arg1[2]);
						Settings.setInt("StaticTimeClock", Integer.parseInt(arg1[2]));
					}
					timer.start();
					Settings.setBoolean("StaticTime", true);
				} else {
					if(arg1.length > 1){
						
						Delay = Integer.parseInt(arg1[1]);
						timer.setDelay(Delay);
						Settings.setInt("StaticTimeDelay", Integer.parseInt(arg1[1]));
						
						if(arg1.length > 2){
							Clock = Integer.parseInt(arg1[2]);
							Settings.setInt("StaticTimeClock", Integer.parseInt(arg1[2]));
						}
						
						for(Player player : etc.getServer().getPlayerList()){
							player.sendMessage(Colors.Gold + "[StaticTime] " + Colors.Red + "StaticTime settings changed (Clock: " + Clock);
							player.sendMessage(Colors.Red + "interval: " + Delay + ")");
						}
						
					} else {
					for(Player player : etc.getServer().getPlayerList()){
						player.sendMessage(Colors.Gold + "[StaticTime] " + Colors.Red + "StaticTime disabled!");	
					}
						Enabled = false;
						timer.stop();
						Settings.setBoolean("StaticTime", false);
					}
				}
				return true;
			}
			return false;
		}
		
		
		
	}

}
