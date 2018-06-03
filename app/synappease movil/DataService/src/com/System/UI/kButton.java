package com.System.UI;

import com.System.Utils.Logg;

import BO.BO;
import android.content.Context;
import android.widget.Button;
import android.widget.RemoteViews.RemoteView;
import android.widget.Toast;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.*;

@RemoteView
public class kButton extends Button implements OnClickListener
{
	kIButton myListener = null;
	
	public String Name = "";
	public String Val = "";
	public BO BussinesObject = null;
	
	public kButton(Context context)
	{
		super(context);
		
		if(!context.getClass().getName().equals("com.android.layoutlib.bridge.android.BridgeContext"))
		{
			try
			{
				this.myListener = (kIButton)context;
				setOnClickListener(this);
			}
			catch(Exception ex)
			{
				Logg.error("Error al convertir kButton, falta implementar interface kIButton?? en " + context.getClass().getName());
				//throw ex;
			}
		}
	}
	
	public kButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		
		if(!context.getClass().getName().equals("com.android.layoutlib.bridge.android.BridgeContext"))
		{
			try
			{
				this.myListener = (kIButton)context;
				setOnClickListener(this);
			}
			catch(Exception ex)
			{
				Logg.error("Error al convertir kButton, falta implementar interface kIButton?? en " + context.getClass().getName());
				//throw ex;
			}
		}
	}
	
	public kButton(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);

		if(!context.getClass().getName().equals("com.android.layoutlib.bridge.android.BridgeContext"))
		{
			try
			{
				this.myListener = (kIButton)context;
				setOnClickListener(this);
			}
			catch(Exception ex)
			{
				Logg.error("Error al convertir kButton, falta implementar interface kIButton?? en " + context.getClass().getName());
				//throw ex;
			}
		}
	}
	
	@Override
	public void onClick(View v)
	{
		this.playSoundEffect(0);
		
		if(this.myListener != null)
		{
//        	Toast.makeText(v.getContext(), 
//			"Click " + getResources().getResourceEntryName(this.getId()), Toast.LENGTH_SHORT).show();
			
			this.myListener.commandAction(this, v);	
		}
		else
		{
			Logg.error("Error al convertir kIButton_OnClick, falta implementar interface kIButton?? en " + v.getContext().getClass().getName());
		}
	}

}
