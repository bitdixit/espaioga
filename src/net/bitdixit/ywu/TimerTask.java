package net.bitdixit.ywu;

import android.os.Handler;
import android.os.SystemClock;

public abstract class TimerTask implements Runnable
{
	   private Handler mHandler = new Handler();
	   public void start(int next)
	   {
	        mHandler.removeCallbacks(this);
	        mHandler.postDelayed(this, next);		   
	   }
	   public void run()
	   {
		   int next=onTimer();
		   if (next>=0)
			   mHandler.postAtTime(this, SystemClock.uptimeMillis()+next);
	   }
	   public void stop()
	   {
	        mHandler.removeCallbacks(this);			   
	   }
	   public abstract int onTimer();
}
