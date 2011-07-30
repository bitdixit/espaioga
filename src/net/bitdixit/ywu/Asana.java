package net.bitdixit.ywu;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Asana
{
	private Library library;
	private String name;
	
	public String getName() {
		return "aa";
	}

	public Asana(Library library, String name)
	{
		this.library = library;
		this.name = name;
	}
	public List<Bitmap> getImages()
	{
		ArrayList<Bitmap> bitmaps=new ArrayList<Bitmap>();
		try{
			
		for (String file: library.getAssertManager().list(Library.ASANA_DIR+"/"+name))
		{
			if (file.contains("img_")) 
			{
		        InputStream istr = library.getAssertManager().open(Library.ASANA_DIR+"/"+name+"/"+file);
		        Bitmap bitmap = BitmapFactory.decodeStream(istr);	
		        bitmaps.add(bitmap);
			}
		}
		} catch (Exception e)
		{
			
		}
		return bitmaps;
    }
}
