package com.System.UI;

import java.util.Calendar;

import com.System.Utils.Logg;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class kListView extends ListView implements OnItemClickListener 
{
	kIListView myListener = null;
	
	public String Name = "";
	public String Val = "";
	
	private int positionanterior = -2;
	public int selectedfila = -1;
	
	private long tiempoanterior = 0;
	private long tiempoactual = 0;

	public kListView(Context context) 
	{
		super(context);

		if(!context.getClass().getName().equals("com.android.layoutlib.bridge.android.BridgeContext"))
		{
			try
			{
				this.myListener = (kIListView)context;
				this.setOnItemClickListener(this);
			}
			catch(Exception ex)
			{
				Logg.error("Error al convertir kIButton: " + context.getClass().getName());
				//throw ex;
			}
		}
		
		long fechaactual = 0;
		Calendar c = Calendar.getInstance();
		tiempoanterior = c.getTime().getTime();
	}
	
	public kListView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);

		if(!context.getClass().getName().equals("com.android.layoutlib.bridge.android.BridgeContext"))
		{
			try
			{
				this.myListener = (kIListView)context;
				this.setOnItemClickListener(this);
			}
			catch(Exception ex)
			{
				Logg.error("Error al convertir kIButton: " + context.getClass().getName());
				//throw ex;
			}
		}
		
		long fechaactual = 0;
		Calendar c = Calendar.getInstance();
		tiempoanterior = c.getTime().getTime();
	}

	public kListView(Context context, AttributeSet attrs, int defStyleAttr) 
	{
		super(context, attrs, defStyleAttr);

		if(!context.getClass().getName().equals("com.android.layoutlib.bridge.android.BridgeContext"))
		{
			try
			{
				this.myListener = (kIListView)context;
				this.setOnItemClickListener(this);
			}
			catch(Exception ex)
			{
				Logg.error("Error al convertir kIButton: " + context.getClass().getName());
				//throw ex;
			}
		}
		
		long fechaactual = 0;
		Calendar c = Calendar.getInstance();
		tiempoanterior = c.getTime().getTime();
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
		this.playSoundEffect(0);
		selectedfila = position;

		Calendar c = Calendar.getInstance();
		tiempoactual = c.getTime().getTime();
		
		if(this.myListener != null)
		{
			this.myListener.listItemAction(this, parent, view, position, id);
			
	    	if((tiempoactual - tiempoanterior < 1500) && (positionanterior == position))
	    	{
	    		//DOBLE CLICK SE GENERA EL EVENTO DOUBLE CLICK
	    		this.myListener.doubleclick_listItemAction(this, parent, view, position, id);
	    	}
	    	tiempoanterior = tiempoactual;
	    	positionanterior = position;
			
		}
		else
		{
			Logg.error("Error al convertir kIListView_onItemClick: " + view.getContext().getClass().getName());
		}
	}

}
