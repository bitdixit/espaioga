package net.bitdixit.ywu;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class AsanaInfoActivity extends Activity
implements OnGestureListener
{
	private GestureDetector gestureScanner;	
	private String          asanaName;
    private Library 		library;
	
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        super.dispatchTouchEvent(ev);
        return gestureScanner.onTouchEvent(ev);
    }
    
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asana_info);
        
        Bundle bundle = getIntent().getExtras();
        asanaName = bundle.getString("asana");
        library = new Library(this);

        gestureScanner = new GestureDetector(this);
        
        setContent();
    }
	
	public void setContent()
	{
        Asana asana = library.asanas.get(asanaName);

        ImageView img1 = (ImageView) findViewById(R.id.infoAsanaImage1);

        img1.setImageBitmap(asana.getImages().get(0));
        
        ImageView img2 = (ImageView) findViewById(R.id.infoAsanaImage2);
        if (asana.getImages().size()>1)
        {	
            img2.setVisibility(View.VISIBLE);
            img2.setImageBitmap(asana.getImages().get(1));
        }
        else
            img2.setVisibility(View.INVISIBLE);

        ImageView img3 = (ImageView) findViewById(R.id.infoAsanaImage3);
        if (asana.getImages().size()>2)
        {	
            img3.setVisibility(View.VISIBLE);
            img3.setImageBitmap(asana.getImages().get(2));
        }
        else
            img3.setVisibility(View.INVISIBLE);

        TextView text = (TextView) findViewById(R.id.infoAsanaName);
        text.setText(asanaName);       
        
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.scrollTo(0, 0);
        
	}

	public void move(boolean forward)
	{
		int pos = 0;
		String[] asanas = library.asanaList();
		while (!asanas[pos].equals(asanaName)) pos++;
		asanaName = asanas[(asanas.length+pos+(forward?1:-1))%asanas.length];
		setContent();		
	}
	
    @Override
    public boolean onTouchEvent(MotionEvent me)
    {
 
        return gestureScanner.onTouchEvent(me);
    }	
	
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public float abs(float v) { if (v<0) return -v; return v; }
	
	long lastGestureProcessed=0;
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		
		if (abs(distanceX)>abs(distanceY)
			&& abs(distanceX)>10
			&& System.currentTimeMillis()-lastGestureProcessed>200)
		{
			int pos = 0;
			String[] asanas = library.asanaList();
			while (!asanas[pos].equals(asanaName)) pos++;
			asanaName = asanas[(asanas.length+pos+(distanceX>10?1:-1))%asanas.length];
			setContent();
			lastGestureProcessed=System.currentTimeMillis();
		}

		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
}
