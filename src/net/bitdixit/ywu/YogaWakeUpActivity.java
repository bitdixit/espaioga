package net.bitdixit.ywu;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class YogaWakeUpActivity
	extends Activity
{
	Preferences preferences;
	
	private long mPracticeTime = 0L;
	private YogaWakeUpActivity thisActivity;
	
	public TimerTask timerTask = new TimerTask()
	{ public int onTimer(int count) { return clickTimer(count); } };	
	
	public String getRemaining()
	{
		if (mPracticeTime==0) return ""; 
        Date until = new Date(System.currentTimeMillis()+(mPracticeTime*1000));
		String s=(mPracticeTime/60)+"m left - till ";
		if (until.getHours()<10) s+="0";
		s+=""+until.getHours()+":";
		if (until.getMinutes()<10) s+="0";
		s+=""+until.getMinutes();
		return s;
	}
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        preferences = new Preferences(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main);
        
        thisActivity= this;        
           
        ImageView img = (ImageView) findViewById(R.id.imageBuda);
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        img.startAnimation(myFadeInAnimation);
              
           
        img.setOnLongClickListener(new OnLongClickListener() {			
			public boolean onLongClick(View v) {
				onLongClickImage(); return true; } });
        
        img.setOnClickListener(new OnClickListener()
        { public void onClick(View v) { onClickImage(); } }); 
       	
        setAirPlaneMode(true);
    }
	@Override
    protected void onPause()
	{
		super.onPause();
    	timerTask.stop();
	};
	
	@Override 
	protected void onResume()
	{
		super.onResume();
		setAirPlaneMode(true);
		stopTimer();
	};
    private void onClickImage()
    {
        if (mPracticeTime==0L)
        {
      	  startTimer();
      	  mPracticeTime = 15*60;
        }
        else
        {
      	  mPracticeTime += 5 * 60;
        }
        Toast.makeText(this, getRemaining(), Toast.LENGTH_SHORT).show();
    	
    }
    private void onLongClickImage()
    {
    	if (mPracticeTime>0)
    	{
    		Toast.makeText(thisActivity, "practice ends", Toast.LENGTH_SHORT).show();
    		stopTimer();
    	}
    	
    }
    private void startTimer()
    {
    	setImage(true);
        timerTask.start(preferences.delayToFirstBell()*1000);
    }
    
    private int clickTimer(int count)
    {
    	if (count==0) playBell(); 
    	
    	mPracticeTime--;
    	
    	if (mPracticeTime==preferences.alertBell1()*60 ||
    		mPracticeTime==preferences.alertBell2()*60 ||
    		mPracticeTime==preferences.alertBell3()*60)
    	{
    		playBell();    	
    	}

    	if (mPracticeTime==0)
    	{
    		new TimerTask() {				
				public int onTimer(int count)
				{
					finish();
					return 0;
				}
			}.start(4000);
            Toast.makeText(thisActivity, "you are wellcome", Toast.LENGTH_SHORT).show();
            ImageView img = (ImageView) findViewById(R.id.imageBuda);
            Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadeout);
            img.startAnimation(myFadeInAnimation);
    		return 0;
    	}
    	
    	return 1000;
    }
    private void stopTimer()
    {
    	setImage(false);
    	timerTask.stop();
        mPracticeTime=0L;
    }
    public void playBell()
    {
		MediaPlayer mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.bell_sound);
		mediaPlayer.start();    	
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    public void setImage(boolean active)
    {
    	ImageView iv = (ImageView)findViewById(R.id.imageBuda);
    	iv.setImageResource(active?R.drawable.budahi:R.drawable.budalo);       
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuConfigure:
            	startActivity(new Intent(this, PreferencesActivity.class));
                break;
            case R.id.menuInfo:
            	startActivity(new Intent(this, InformationActivity.class));
                break;
            case R.id.menuLibrary:
            	startActivity(new Intent(this, AsanaListActivity.class));
                break;
                                     
        }
        return true;
    }
    
    public void setAirPlaneMode(boolean isEnabled)
    {
    	if (preferences.getSetAirplaneMode())
    	{
	    	// toggle airplane mode
	    	Settings.System.putInt(
	    		 this.getApplicationContext().getContentResolver(),
	    	      Settings.System.AIRPLANE_MODE_ON, isEnabled ? 1 : 0);
	
	    	// Post an intent to reload
	    	Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
	    	intent.putExtra("state", !isEnabled);
	    	sendBroadcast(intent);    	
    	}
    }

}