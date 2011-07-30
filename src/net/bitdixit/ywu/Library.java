package net.bitdixit.ywu;

import java.util.Arrays;
import java.util.HashMap;

import android.app.Activity;
import android.content.res.AssetManager;

public class Library
{
	public static String ASANA_DIR = "library/asana";
	AssetManager assetManager;
	HashMap<String,Asana> asanas;
	
	public Library(Activity activity)
	{
		assetManager = activity.getResources().getAssets();
		asanas = new HashMap<String,Asana>();
		try { load(); } catch (Exception forget) {}
	}
	private void load() throws Exception
	{
		String[] asanaFiles = assetManager.list(ASANA_DIR);
		for (String asanaName : asanaFiles)
			asanas.put(asanaName,new Asana(this,asanaName));
	}
	public AssetManager getAssertManager()
	{
		return assetManager;
	}
	public HashMap<String,Asana> asanas()
	{
		return asanas;
	}
	public String[] asanaList()
	{
		  String[] asanasName
		  	= asanas.keySet().toArray(new String[asanas().size()]);
		  Arrays.sort(asanasName);
		  return asanasName;
	}
}
