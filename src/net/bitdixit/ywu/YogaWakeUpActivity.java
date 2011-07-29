package net.bitdixit.ywu;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class YogaWakeUpActivity
	extends Activity
{
	Preferences preferences;
	
	private long mPracticeTime = 0L;
	public boolean firstTime;
	private YogaWakeUpActivity thisActivity;
	
	public TimerTask timerTask = new TimerTask()
	{ public int onTimer() { return clickTimer(); } };	
	
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
        
        img.setOnLongClickListener(new OnLongClickListener() {			
			public boolean onLongClick(View v) {
				onLongClickImage(); return true; } });
        
        img.setOnClickListener(new OnClickListener()
        { public void onClick(View v) { onClickImage(); } });    
    }
    
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
        Toast.makeText(thisActivity, "Practice ends", Toast.LENGTH_SHORT).show();
		stopTimer();
    	
    }
    private void startTimer()
    {
    	ImageView iv = (ImageView)findViewById(R.id.imageBuda);
    	iv.setImageResource(R.drawable.budahi);       
        timerTask.start(preferences.delayToFirstBell()*1000);
        firstTime=true;
    }
    
    private int clickTimer()
    {
    	if (firstTime) { playBell(); firstTime = false; }
    	
    	mPracticeTime--;
    	
    	if (mPracticeTime==preferences.alertBell1() ||
    		mPracticeTime==preferences.alertBell2() ||
    		mPracticeTime==preferences.alertBell3())
    	{
    		playBell();    	
    	}

    	if (mPracticeTime==0)
    	{
    		stopTimer();
    		return 0;
    	}
    	
    	return 1000;
    }
    private void stopTimer()
    {
    	ImageView iv = (ImageView)findViewById(R.id.imageBuda);
    	iv.setImageResource(R.drawable.budalo);       
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
        
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuConfigure: startActivity(new Intent(this, PreferencesActivity.class));
                                     break;
            case R.id.menuInfo: startActivity(new Intent(this, InformationActivity.class));
            break;
                                     
        }
        return true;
    }
}