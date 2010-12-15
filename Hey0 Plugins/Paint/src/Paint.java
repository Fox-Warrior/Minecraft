import java.io.*;
import java.util.ArrayList;
import java.util.logging.Logger;
import com.foxwarrior.util.*;
import net.minecraft.server.MinecraftServer;

public class Paint extends Plugin {

	private MinecraftServer world = etc.getMCServer();
	static Boolean PaintMode = true;
	static Boolean PaintOn = false;
	static ArrayList<String> PaintPlayerList = new ArrayList<String>();
	static ArrayList<Integer> PaintTypes = new ArrayList<Integer>();
	static ArrayList<Integer> PaintTools = new ArrayList<Integer>();
	static ArrayList<Integer> PaintMods = new ArrayList<Integer>();
	static ArrayList<Integer> PaintDistances = new ArrayList<Integer>();
	static final Logger log = Logger.getLogger("Minecraft");
	static int MinPaintDistance = 2;
	static int MaxPaintDistance = 300;
	static int DefaultPaintDistance = 300;
	static int DefaultPaintBlockID = 1;
	static int DefaultPaintToolID = 296;
	static String DefaultPaintDir = "paint" + "//";
	static boolean SaveSettings = true;
	static boolean DebugMode = false;
	static int DebugLevel = 1;
	static final double version = 1.6;
	static boolean IsAReload = false;
	static Player PlayerReloadedPlugin;
	
	private Paint.Listener listener;

	

	public Paint() {
		this.listener = new Paint.Listener();
	}
	
	public void RegisterCommands(){
		etc.getInstance().addCommand("/paintbrush|/pb -", "Gives to you the paint brush");
		etc.getInstance().addCommand("/painttype|/pt -", "Change the block used to paint");
		etc.getInstance().addCommand("/painttool|/pto -", "Change the tool used to paint");
		etc.getInstance().addCommand("/painttoggle|/ptg -", "Toggle the mod");
		etc.getInstance().addCommand("/paintmode|/pm -", "Toggle the paint mode [Paint|Create]");
		etc.getInstance().addCommand("/paintcopy|/pc -", "Use the aimed block to paint");
		etc.getInstance().addCommand("/paintcurrenttool|/pcto -", "Use the tool in your hand to paint");
		etc.getInstance().addCommand("/paintdistance|/pdist -", "Change the max paint distance");
		etc.getInstance().addCommand("/paintversion|/pv -", "Gets the version of the plugin");
		etc.getInstance().addCommand("/paintreload|/prel -", "Reload the plugin's config.");
	}
	
	public void CreateDefaultFiles(){
		if(DebugMode){
			Paint.log.info("[Paint plugin] : Creating default tables and directories...");
		}
		new File(DefaultPaintDir + "//").mkdir();
		ArrayManager.saveArrayList_Integer(DefaultPaintDir + "Paint_PaintTypes.dat", new ArrayList<Integer>());
		ArrayManager.saveArrayList_Integer(DefaultPaintDir + "Paint_PaintTools.dat", new ArrayList<Integer>());
		ArrayManager.saveArrayList_Integer(DefaultPaintDir + "Paint_PaintMods.dat", new ArrayList<Integer>());
		ArrayManager.saveArrayList_Integer(DefaultPaintDir + "Paint_PaintDistances.dat", new ArrayList<Integer>());
		ArrayManager.saveArrayList_String(DefaultPaintDir + "Paint_PaintPlayerList.dat", new ArrayList<String>());
		if(DebugMode){
			Paint.log.info("[Paint plugin] : Default tables and directories created.");
		}
	}
	
	public boolean CheckFiles(){
		boolean Result = true;
		String[] FileNames = {
				"Paint_PaintTypes.dat",
				"Paint_PaintTools.dat",
				"Paint_PaintMods.dat",
				"Paint_PaintDistances.dat",
				"Paint_PaintPlayerList.dat"
				};		
		for(String v : FileNames){
			boolean exists = new File(DefaultPaintDir + v).exists();
			if(!exists){
				Result = false;
				break;
			} else {
				continue;
			}
		}
		return Result;
	}
	
	public void SaveSettings()
	{
		
		boolean Checked = CheckFiles();
		if(!Checked){
			CreateDefaultFiles();
		}
		
		if(DebugMode && DebugLevel > 2){
			Paint.log.info("[Paint plugin] : Saving tables...");
		}
		
		ArrayManager.saveArrayList_Integer(DefaultPaintDir + "Paint_PaintTypes.dat", PaintTypes);
		ArrayManager.saveArrayList_Integer(DefaultPaintDir + "Paint_PaintTools.dat", PaintTools);
		ArrayManager.saveArrayList_Integer(DefaultPaintDir + "Paint_PaintMods.dat", PaintMods);
		ArrayManager.saveArrayList_Integer(DefaultPaintDir + "Paint_PaintDistances.dat", PaintDistances);
		ArrayManager.saveArrayList_String(DefaultPaintDir + "Paint_PaintPlayerList.dat", PaintPlayerList);
		
		if(DebugMode && DebugLevel > 2){
			Paint.log.info("[Paint plugin] : Tables saved.");
		}
		
	}

	
	public void initialize() {
		etc.getLoader().addListener(PluginLoader.Hook.COMMAND,
				this.listener, this, PluginListener.Priority.MEDIUM);
		etc.getLoader().addListener(PluginLoader.Hook.ARM_SWING,
				this.listener, this, PluginListener.Priority.MEDIUM);
		etc.getLoader().addListener(PluginLoader.Hook.LOGIN,
				this.listener, this, PluginListener.Priority.MEDIUM);
		etc.getLoader().addListener(PluginLoader.Hook.BLOCK_CREATED,
				this.listener, this, PluginListener.Priority.MEDIUM);
		etc.getLoader().addListener(PluginLoader.Hook.DISCONNECT,
				this.listener, this, PluginListener.Priority.MEDIUM);
	}


	public void LoadSettings() {
		RegisterCommands();
		SettingsManager properties = new SettingsManager("PaintPlugin.properties", "Paint Plugin Config File");		
		try {
			
			if(DebugMode){
				Paint.log.info("[Paint plugin] : Loading config...");
			}
			
			SaveSettings = properties.getBoolean("SaveSettings", true);
			DebugMode = properties.getBoolean("DebugMode", false);
			DebugLevel = properties.getInt("DebugLevel", 1);
			MinPaintDistance = properties.getInt("MinPaintDistance", 2);
			MaxPaintDistance = properties.getInt("MaxPaintDistance", 300);
			DefaultPaintDistance = properties.getInt("DefaultPaintDistance", 300);
			DefaultPaintDir = properties.getString("DefaultPaintDirectory", "paint") + "//";
			DefaultPaintBlockID = properties.getInt("DefaultPaintBlockID", 1);
			DefaultPaintToolID = properties.getInt("DefaultPaintTool", 296);
			boolean Checked = CheckFiles();
			
			if(!Checked && SaveSettings){
				CreateDefaultFiles();
			}
			
			if(DebugMode){
				Paint.log.info("[Paint plugin] : Config loaded.");
			}
			
			if(SaveSettings){
				

				
				PaintPlayerList = ArrayManager.loadArrayList_String(DefaultPaintDir + "Paint_PaintPlayerList.dat");
				if(DebugMode){
					Paint.log.info("[Paint plugin] : Loading players...");
				}
				PaintTypes = ArrayManager.loadArrayList_Integer(DefaultPaintDir + "Paint_PaintTypes.dat");
				if(DebugMode){
					Paint.log.info("[Paint plugin] : Loading blocs...");
				}
				PaintTools = ArrayManager.loadArrayList_Integer(DefaultPaintDir + "Paint_PaintTools.dat");
				if(DebugMode){
					Paint.log.info("[Paint plugin] : Loading tools...");
				}
				PaintMods = ArrayManager.loadArrayList_Integer(DefaultPaintDir + "Paint_PaintMods.dat");
				if(DebugMode){
					Paint.log.info("[Paint plugin] : Loading mods...");
				}
				PaintDistances = ArrayManager.loadArrayList_Integer(DefaultPaintDir + "Paint_PaintDistances.dat");
				if(DebugMode){
					Paint.log.info("[Paint plugin] : loading distances...");
				}
				
				// Clamp and refresh users max distances form PaintPlugin.properties
				for (int v : PaintDistances){
					PaintDistances.set(PaintDistances.indexOf(v), MathFunctions.Clamp(MinPaintDistance, MaxPaintDistance, v));
				}		
				
				if(IsAReload){
					etc.getInstance();
					for(Player player : etc.getServer().getPlayerList()){
						boolean inList = false;
						for (String p : PaintPlayerList){
							if (p.equalsIgnoreCase(player.getName())){
								inList = true;
							}
						}
						if (!inList){
							if(DebugMode){
								Paint.log.info("[Paint plugin] : Creating tables for user " + player.getName() + "...");
							}
							PaintPlayerList.add(player.getName());
							int index = getPlayerIndex(player.getName());
							PaintTypes.add(index, null);
							PaintTools.add(index, null);
							PaintMods.add(index, null);
							PaintDistances.add(index, null);
							setBlocType(player.getName(), DefaultPaintBlockID);
							setPaintToolType(player.getName(), DefaultPaintToolID);
							setPaintMode(player.getName(), 1);
							setPaintDistance(player.getName(), DefaultPaintDistance);
							if(SaveSettings){
								SaveSettings();
							}
							if(DebugMode){
								Paint.log.info("[Paint plugin] : Tables created.");
							}
						}
					}
			}
				
				if(DebugMode){
					Paint.log.info("[Paint plugin] : Tables loaded.");
				}
				
			}
			
			if(!IsAReload){
				Paint.log.info("[Paint plugin version " + version + "] : successfuly loaded.");
			} else {
				Paint.log.info("[Paint plugin version " + version + "] : successfuly reloaded.");	
				PlayerReloadedPlugin.sendMessage(Colors.Gold + "[Paint] " + Colors.Green + "Plugin reloaded.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void enable() {
		LoadSettings();
	}

	public void disable() {
		SaveSettings();
	}
	
	private boolean isValidPaintID(int blocID){
		if (blocID >= 0 && blocID <=85){
			if ( (blocID > 20 && blocID < 35) || blocID==36 ){
				return false;
			}
			else{
				return true;
			}
		}
		else
			return false;
	}
	
	private static int getPlayerIndex(String playerName){
		return PaintPlayerList.indexOf(playerName);
	}
	
	public static void setBlocType(String playerName, int BlocID){		
			int index = getPlayerIndex(playerName);
			PaintTypes.set(index, BlocID);
	}
	
	public static void setPaintToolType(String playerName, int BlocID){		
		int index = getPlayerIndex(playerName);
		PaintTools.set(index, BlocID);
	}
	
	public static void setPaintMode(String playerName, int Mode){		
		int index = getPlayerIndex(playerName);
		PaintMods.set(index, Mode);
	}
	
	public static void setPaintDistance(String playerName, int Distance){		
		int index = getPlayerIndex(playerName);
		PaintDistances.set(index, Distance);
	}
	
	public class Listener extends PluginListener {


		private long lastToolClick;

		public Listener() {
		}

		public void onLogin(Player player) {
			boolean inList = false;
			for (String p : PaintPlayerList){
				if (p.equalsIgnoreCase(player.getName())){
					inList = true;
				}
			}
			if (!inList){
				if(DebugMode){
					Paint.log.info("[Paint plugin] : Creating tables for user " + player.getName() + "...");
				}
				PaintPlayerList.add(player.getName());
				int index = getPlayerIndex(player.getName());
				PaintTypes.add(index, null);
				PaintTools.add(index, null);
				PaintMods.add(index, null);
				PaintDistances.add(index, null);
				setBlocType(player.getName(), DefaultPaintBlockID);
				setPaintToolType(player.getName(), DefaultPaintToolID);
				setPaintMode(player.getName(), 1);
				setPaintDistance(player.getName(), DefaultPaintDistance);
				if(SaveSettings){
					SaveSettings();
				}
				if(DebugMode){
					Paint.log.info("[Paint plugin] : Tables created.");
				}
			}
		}
		
		public void onDisconnect(Player player) {
			if(SaveSettings){
				SaveSettings();
			}
		}
		
		public boolean onCommand(Player player, String[] split) {
			int index = getPlayerIndex(player.getName());
			if(player.canUseCommand("/paintmode")){
				if (split[0].equalsIgnoreCase("/pt") || split[0].equalsIgnoreCase("/painttype")){
					if( split.length > 1){
						int Blok = Integer.parseInt(split[1]);
					if(isValidPaintID(Blok)){
						setBlocType(player.getName(), Integer.parseInt(split[1]));
						player.sendMessage(Colors.Gold + "[Paint] " + Colors.Green + "Bloc type changed to " + Blok + ".");
						if(SaveSettings){
							SaveSettings();
						}
						if(DebugMode && DebugLevel > 1){
							Paint.log.info("[Paint plugin] : " + player.getName() + " has changed his bloc type to (" + Blok + ").");
						}
					} else {
						player.sendMessage(Colors.Gold + "[Paint] " + Colors.Red + "Invalid bloc ID");
					}
				} else {
					player.sendMessage(Colors.Gold + "[Paint] " + Colors.Green + "Current paint bloc type (" + PaintTypes.get(index) + ").");
				}
					return true;
				}
				
				if (split[0].equalsIgnoreCase("/pto") || split[0].equalsIgnoreCase("/painttool")){
					if( split.length > 1){
						int Blok = Integer.parseInt(split[1]);
					if(isValidPaintID(Blok)){
						setPaintToolType(player.getName(), Integer.parseInt(split[1]));
						player.sendMessage(Colors.Gold + "[Paint] " + Colors.Green + "Tool changed to (Bloc ID:" + Blok + ").");
						if(SaveSettings){
							SaveSettings();
						}
						if(DebugMode && DebugLevel > 1){
							Paint.log.info("[Paint plugin] : " + player.getName() + " has changed his tool to (Bloc ID:" + Blok + ").");
						}
					} else {
						player.sendMessage(Colors.Gold + "[Paint] " + Colors.Red + "Invalid bloc ID");
					}
				} else {
					player.sendMessage(Colors.Gold + "[Paint] " + Colors.Green + "Current paint tool (" + PaintTools.get(index) + ").");
				}
					return true;
				}
				
				else if (split[0].equalsIgnoreCase("/p") || split[0].equalsIgnoreCase("/paint")){
					PaintOn = !PaintOn;
					player.sendMessage(Colors.Gold + "[Paint] " + Colors.Green + ((!PaintOn) ? "Enabled." : "Disabled."));
					if(DebugMode && DebugLevel > 1){
						Paint.log.info("[Paint plugin] : " + player.getName() + " has " + ((!PaintOn) ? "Enabled" : "Disabled") + " the plugin.");
					}
					return true;
				}
				
				else if (split[0].equalsIgnoreCase("/pm") || split[0].equalsIgnoreCase("/paintmode")){
					PaintMode = !PaintMode;
					player.sendMessage(Colors.Gold + "[Paint] " + Colors.Green + "Mode " + ((PaintMode) ? "paint." : "create."));
					setPaintMode(player.getName(), (PaintMode) ? 1 : 0);
					if(SaveSettings){
						SaveSettings();
					}
					if(DebugMode && DebugLevel > 1){
						Paint.log.info("[Paint plugin] : " + player.getName() + " has changed his mod to (" + ((PaintMode) ? "paint." : "create.") + ").");
					}
					return true;
				}
				
				else if (split[0].equalsIgnoreCase("/pc") || split[0].equalsIgnoreCase("/paintcopy")){
					HitBlox blox = new HitBlox(player, 300, 0.3);
					if (blox.getTargetBlock() != null){
						setBlocType(player.getName(), blox.getCurBlock().getType());
						player.sendMessage(Colors.Gold + "[Paint] " + Colors.Green + "Bloc type changed to (Bloc ID:" + blox.getTargetBlock().getType() + ").");
						if(SaveSettings){
							SaveSettings();
						}
						if(DebugMode && DebugLevel > 1){
							Paint.log.info("[Paint plugin] : " + player.getName() + " has copied a block (Bloc ID:" + blox.getTargetBlock().getType() + ").");
						}
					}
					return true;
				}
				
				else if (split[0].equalsIgnoreCase("/pcto") || split[0].equalsIgnoreCase("/paintcurrenttool")){
					if (player.getItemInHand() != 0){
						setPaintToolType(player.getName(), player.getItemInHand());
						player.sendMessage(Colors.Gold + "[Paint] " + Colors.Green + "Tool changed to (Bloc ID:" + player.getItemInHand() + ").");
						if(SaveSettings){
							SaveSettings();
						}
						if(DebugMode && DebugLevel > 1){
							Paint.log.info("[Paint plugin] : " + player.getName() + " has changed his tool to (Bloc ID:" + player.getItemInHand() + ").");
						}
					}
					return true;
				}
				
				else if (split[0].equalsIgnoreCase("/pb") || split[0].equalsIgnoreCase("/paintbrush")){
					Item IT = new Item();
					IT.setItemId(PaintTools.get(index));
					player.giveItem(IT);
					player.sendMessage(Colors.Gold + "[Paint] " + "PaintBrush given.");
					return true;
				}
				
				else if (split[0].equalsIgnoreCase("/pv") || split[0].equalsIgnoreCase("/paintversion")){
					player.sendMessage(Colors.Gold + "[Paint] " + Colors.Green + "Version " + version + ".");
					return true;
				}
				
				else if (split[0].equalsIgnoreCase("/paintreload") || split[0].equalsIgnoreCase("/prel")){
					PlayerReloadedPlugin = player;
					player.sendMessage(Colors.Gold + "[Paint] " + Colors.Green + "Reloading plugin...");
					IsAReload = true;
					LoadSettings();
					return true;
				}
				
				else if (split[0].equalsIgnoreCase("/pdist") || split[0].equalsIgnoreCase("/paintdistance")){
					if (split.length > 1){
						int InputValue = MathFunctions.Clamp(MinPaintDistance, MaxPaintDistance, Integer.parseInt(split[1]));
						setPaintDistance(player.getName(), InputValue);
						player.sendMessage(Colors.Gold + "[Paint] " + Colors.Green + "Max paint distance changed to (" + InputValue + ").");
						if(SaveSettings){
							SaveSettings();
						}
						if(DebugMode && DebugLevel > 1){
							Paint.log.info("[Paint plugin] : " + player.getName() + " has changed his max paint distance to (" + InputValue + ").");
						}
					} else {
						player.sendMessage(Colors.Gold + "[Paint] " + Colors.Green + "Current paint distance (" + PaintDistances.get(index) + ").");
					}
					return true;
				}
			}
			return false;				
		}
		
		public void onArmSwing(Player player){
			int index = getPlayerIndex(player.getName());
			if(player.getItemInHand() == PaintTools.get(index) && !PaintOn && player.canUseCommand("/paintmode")){
				HitBlox blox = new HitBlox(player, PaintDistances.get(index), 0.3);
				if (blox.getTargetBlock() != null)
				{
					if(PaintMods.get(index) == 1){
						world.e.d(blox.getCurBlock().getX(), blox.getCurBlock().getY(), blox.getCurBlock().getZ(), PaintTypes.get(index));
						if(DebugMode && DebugLevel > 2){
							Paint.log.info("[Paint plugin] : " + player.getName() + " has created a block (X:" + blox.getCurBlock().getX() + ", Y:"+blox.getCurBlock().getY()+", Z:"+blox.getCurBlock().getZ()+", Bloc ID:" + blox.getCurBlock().getType() + ", Mode:Paint).");
						}
					} else {
						if(PaintTypes.get(index) == 0){
							world.e.d(blox.getCurBlock().getX(), blox.getCurBlock().getY(), blox.getCurBlock().getZ(), 0);
							if(DebugMode && DebugLevel > 2){
								Paint.log.info("[Paint plugin] : " + player.getName() + " has created a block (X:" + blox.getCurBlock().getX() + ", Y:"+blox.getCurBlock().getY()+", Z:"+blox.getCurBlock().getZ()+", Bloc ID:" + blox.getCurBlock().getType() + ", Mode:Paint).");
							}
						} else {
							world.e.d(blox.getLastBlock().getX(), blox.getLastBlock().getY(), blox.getLastBlock().getZ(), PaintTypes.get(index));
							if(DebugMode && DebugLevel > 2){
								Paint.log.info("[Paint plugin] : " + player.getName() + " has created a block (X:" + blox.getLastBlock().getX() + ", Y:"+blox.getLastBlock().getY()+", Z:"+blox.getLastBlock().getZ()+", Bloc ID:" + blox.getLastBlock().getType() + ", Mode:Create).");
							}
						}
					}
				}
			}
		}
		
	  public boolean hasToolBeenDoubleClicked()
	  {
	    return System.currentTimeMillis() - this.lastToolClick < 500L;
	  }

	  public void triggerToolClick()
	  {
	    this.lastToolClick = System.currentTimeMillis();
	  }
		
		public boolean onBlockCreate(Player player, Block blockPlaced, Block blockClicked, int itemInHand){
			int index = getPlayerIndex(player.getName());
			if(itemInHand == PaintTools.get(index) && !PaintOn && player.canUseCommand("/paintmode")){
				if (hasToolBeenDoubleClicked()) {
					setBlocType(player.getName(), blockClicked.getType());
					player.sendMessage(Colors.Gold + "[Paint] " + Colors.Green + "Bloc type changed to (Bloc ID:" + blockClicked.getType() + ").");
					if(SaveSettings){
						SaveSettings();
					}
					if(DebugMode && DebugLevel > 1){
						Paint.log.info("[Paint plugin] : " + player.getName() + " has copied a block (Bloc ID:" + blockClicked.getType() + ").");
					}
					return true;
				} else {	
					triggerToolClick();
					boolean B = (PaintMods.get(index) != 0);
					PaintMode = !B;
					player.sendMessage(Colors.Gold + "[Paint] " + Colors.Green + "Mode " + ((PaintMode) ? "paint." : "create."));
					setPaintMode(player.getName(), (PaintMode) ? 1 : 0);
					if(DebugMode && DebugLevel > 1){
						Paint.log.info("[Paint plugin] : " + player.getName() + " has changed his mod to (" + ((PaintMode) ? "paint." : "create.") + ").");
					}
					return true;
				}
			}
			return false;		
		}
	}
}