package com.System.UI;

import com.System.Utils.Logg;

import BO.BO;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class kImageButton extends ImageButton implements OnClickListener
{
	kIImageButton myListener = null;
	
	public String Name = "";
	public String Val = "";
	public BO BussinesObject = null;
	
	public kImageButton(Context context)
	{
		super(context);
		
		if(!context.getClass().getName().equals("com.android.layoutlib.bridge.android.BridgeContext"))
		{
			try
			{
				this.myListener = (kIImageButton)context;
				setOnClickListener(this);
			}
			catch(Exception ex)
			{
				Logg.error("Error al convertir kButton, falta implementar interface kIButton?? en " + context.getClass().getName());
				//throw ex;
			}
		}
	}
	
	public kImageButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		
		if(!context.getClass().getName().equals("com.android.layoutlib.bridge.android.BridgeContext"))
		{
			try
			{
				this.myListener = (kIImageButton)context;
				setOnClickListener(this);
			}
			catch(Exception ex)
			{
				Logg.error("Error al convertir kButton, falta implementar interface kIButton?? en " + context.getClass().getName());
				//throw ex;
			}
		}
	}
	
	public kImageButton(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);

		if(!context.getClass().getName().equals("com.android.layoutlib.bridge.android.BridgeContext"))
		{
			try
			{
				this.myListener = (kIImageButton)context;
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
			this.myListener.commandAction(this, v);	
		}
		else
		{
			Logg.error("Error al convertir kIButton_OnClick, falta implementar interface kIButton?? en " + v.getContext().getClass().getName());
		}
	}

}
