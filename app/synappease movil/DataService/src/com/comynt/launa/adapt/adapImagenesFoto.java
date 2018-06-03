package com.comynt.launa.adapt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

import com.System.Utils.Logg;
import com.comynt.launa.R;

import BO.BO;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.GridView;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.widget.Gallery;

public class adapImagenesFoto extends BaseAdapter
{
    int mGalleryItemBackground;
    private Context mContext;
    public List<String> elementos = new ArrayList<String>();
    public boolean hayfoto = false;

    public adapImagenesFoto(Context c) //, int Tipo) 
    {
    	mContext = c;
    	elementos.add("camara_0");
    	hayfoto = false;
    }

    public int getCount()
    {
    	return elementos.size();
    }

    public Object getItem(int position) {
    	return position;
    }

    public long getItemId(int position) {
    	return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) 
    {
    	ImageView imageView = new ImageView(mContext);
    	
    	if(elementos.get(position).equals("camara_0"))
    	{
    		imageView.setImageResource(R.drawable.camara_0);
    	}
    	else
    	{
        	InputStream in = null;
        	try
        	{
            	in = new FileInputStream(elementos.get(position));
        		
            	Bitmap bm2 = BitmapFactory.decodeStream(in);
            	imageView.setImageBitmap(bm2);
            	
//            	if(bm2!=null)
//            	{
//            		bm2.recycle();
//            	    bm2=null;
//            	}
        	}
    		catch (Exception ex)
    		{
    			Logg.error(ex.getMessage());
    			elementos.set(position, "camara_0");
    			imageView.setImageResource(R.drawable.camara_0);
    		}
        	finally
        	{
    			if(in != null)
    			{
    				try {
    					in.close();
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			}
        	}
    	}

    	return imageView;
    }
    
    public void AgregarFoto(String URL)
    {
    	if(!hayfoto)
    	{
    		elementos.clear();
    		elementos.add(URL);
    	}
    	else
    	{
    		elementos.add(URL);
    	}
    	
    	this.notifyDataSetChanged();
    }
}

