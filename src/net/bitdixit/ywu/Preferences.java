package net.bitdixit.ywu;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences
{	
	SharedPreferences preferences;
	public Preferences(Activity activity)
	{
		preferences=PreferenceManager.getDefaultSharedPreferences(activity.getBaseContext());
	}
	public int delayToFirstBell()
	{
		return Integer.parseInt(preferences.getString("delayToFirstBell", "10"));
	}
	public int alertBell1()
	{
		return Integer.parseInt(preferences.getString("alertBell1", ""+60));
	}
	public int alertBell2()
	{
		return Integer.parseInt(preferences.getString("alertBell2", ""+(5*60)));
	}
	public int alertBell3()
	{
		return Integer.parseInt(preferences.getString("alertBell3", ""+(10*60)));
	}
}
