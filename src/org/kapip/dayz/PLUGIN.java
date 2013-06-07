package org.kapip.dayz;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class PLUGIN {
	public static JavaPlugin A;
	public static World MAIN_WORLD = Bukkit.getWorlds().get(0);
	
	public static boolean DEBUGMODE = false;
	
	public static HashMap<String, Integer> PVP_LOG = new HashMap<String, Integer>();
	public static ArrayList<String> PVP_LOGGED = new ArrayList<String>();
}
