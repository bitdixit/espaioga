package net.bitdixit.ywu;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Window;
import android.view.WindowManager;

public class PreferencesActivity extends PreferenceActivity
{
	  @Override
	  public void onCreate(Bundle savedInstanceState)
	  {
	         super.onCreate(savedInstanceState);  
	         addPreferencesFromResource(R.xml.preferences);
	  }
}
