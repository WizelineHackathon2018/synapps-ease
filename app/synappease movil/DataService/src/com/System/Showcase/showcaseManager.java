package com.System.Showcase;

import java.util.Vector;

import com.System.Base.kBase;
import com.System.Sonidos.Sounds;
import com.System.Utils.Logg;
import com.github.amlcurran.showcaseview.ApiUtils;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.comynt.launa.async.IAsyncEvent;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public class showcaseManager implements OnClickListener 
{
    private ShowcaseView showcaseView;
    private final ApiUtils apiUtils = new ApiUtils();
    private Activity activity;
    private Vector<showcaseItem> items =  new Vector();
    int counter = 0;
    int paso = 0;
    int counterpaso = 0;
	kBase evento;
	
    public showcaseManager(Activity activity)
    {
    	this.activity = activity;
    	
    	try
    	{
    		evento = (kBase) activity;
    	}
    	catch(Exception ex){}
    }
    
	public void AgregarElemento(int IdView)
	{
		items.add(new showcaseItem(0, IdView));
	}
	
	public void AgregarElemento(String Text)
	{
		items.add(new showcaseItem(1, Text));
		counterpaso++;
	}
	
	boolean isshowHelp = false;
	boolean isHelp = false;
	
	public void showHelp()
	{
		if((items.size() > 0) && (!isshowHelp))
		{
			try
			{
				evento.CerrarMenus();
			}
			catch(Exception ex){}
			
			showcaseItem item = (showcaseItem) items.get(0);
			
			if(item.Tipo == 0)
			{
				if(items.size() == 1)
				{
					if(showcaseView == null)
					{
				        showcaseView = new ShowcaseView.Builder(activity)
				        .setTarget(new ViewTarget(activity.findViewById(item.IdView)))
				        .setOnClickListener(this)
				        .build();
				        showcaseView.setButtonText("TERMINAR");
					}
					else
					{
						showcaseView.setShowcase(new ViewTarget(activity.findViewById(item.IdView)), true);
				        showcaseView.setButtonText("TERMINAR");
				        
				        showcaseView.show();
				    }
				}
				else
				{
					if(showcaseView == null)
					{
				        showcaseView = new ShowcaseView.Builder(activity)
				        .setTarget(new ViewTarget(activity.findViewById(item.IdView)))
				        .setOnClickListener(this)
				        .build();
				        showcaseView.setButtonText("CONTINUAR AYUDA");
					}
					else
					{
						showcaseView.setShowcase(new ViewTarget(activity.findViewById(item.IdView)), true);
				        showcaseView.setButtonText("CONTINUAR AYUDA");
				        
				        showcaseView.show();
					}
				}
			}
			else
			{
				if(items.size() == 1)
				{
					if(showcaseView == null)
					{
				        showcaseView = new ShowcaseView.Builder(activity)
				        .setTarget(Target.NONE)
				        .setContentTitle("PASO 1 DE " + counterpaso)
				        .setContentText(item.Text.toUpperCase())
				        .setOnClickListener(this)
				        .build();
				        
				        showcaseView.setButtonText("TERMINAR");
					}
					else
					{
		                showcaseView.setTarget(Target.NONE);
		                showcaseView.setContentTitle("PASO 1 DE " + counterpaso);
		                showcaseView.setContentText(item.Text.toUpperCase());
		                showcaseView.setButtonText("TERMINAR");
		                showcaseView.setOnClickListener(this);
		                
		                showcaseView.show();
					}
			        
//	    			try
//	    			{
//	    				evento.Habla(item.Text);
//	    			}
//	    			catch(Exception ex){}
				}
				else
				{
					if(showcaseView == null)
					{
				        showcaseView = new ShowcaseView.Builder(activity)
				        .setTarget(Target.NONE)
				        .setContentTitle("PASO 1 DE " + counterpaso)
				        .setContentText(item.Text.toUpperCase())
				        .setOnClickListener(this)
				        .build();
				        
				        showcaseView.setButtonText("CONTINUAR AYUDA");
					}
					else
					{
		                showcaseView.setTarget(Target.NONE);
		                showcaseView.setContentTitle("PASO 1 DE " + counterpaso);
		                showcaseView.setContentText(item.Text.toUpperCase());
		                showcaseView.setButtonText("CONTINUAR AYUDA");
		                showcaseView.setOnClickListener(this);
		                
		                showcaseView.show();
					}
			        
//	    			try
//	    			{
//	    				evento.Habla(item.Text);
//	    			}
//	    			catch(Exception ex){}
				}
				
				paso = 1;
			}
			
			isshowHelp = true;
			counter = 1;
			isHelp = true;
		}
	}
	
	@Override
	public void onClick(View v) 
	{
		if(isHelp)
		{
			Logg.info("Showcase counter: " + counter);
			
			if(counter >= items.size())
			{
				showcaseView.hide();
				counter = 0;
				paso = 0;
				isshowHelp = false;
				isHelp = false;
			}
			else
			{
				showcaseItem item = (showcaseItem) items.get(counter);

				if(item.Tipo == 0) //Es un view
				{
					showcaseView.setShowcase(new ViewTarget( activity.findViewById(item.IdView) ), true);
				}
				else //Es un no view
				{
					if(counter == items.size() - 1)
					{
		                showcaseView.setTarget(Target.NONE);
		                showcaseView.setContentTitle("PASO " + (paso + 1) + " DE " + counterpaso);
		                showcaseView.setContentText(item.Text.toUpperCase());
		                showcaseView.setButtonText("TERMINAR");
		                
		                showcaseView.show();
		                
//		    			try
//		    			{
//		    				evento.Habla(item.Text);
//		    			}
//		    			catch(Exception ex){}
					}
					else
					{
		                showcaseView.setTarget(Target.NONE);
		                showcaseView.setContentTitle("PASO " + (paso + 1) + " DE " + counterpaso);
		                showcaseView.setContentText(item.Text.toUpperCase());
		                showcaseView.setButtonText("CONTINUAR AYUDA");

		                showcaseView.show();
		                
//		    			try
//		    			{
//		    				evento.Habla(item.Text);
//		    			}
//		    			catch(Exception ex){}
					}
					
					paso++;
				}
			}
			
			counter++;
		}
		else
		{
			showcaseView.hide();
			counter = 0;
			paso = 0;
			isshowHelp = false;
			isHelp = false;
		}
	}
	
	public void showHelp(int IdView, String Texto)
	{
		if(showcaseView == null)
		{
	        showcaseView = new ShowcaseView.Builder(activity)
	        .setTarget(new ViewTarget(activity.findViewById(IdView)))
	        .setOnClickListener(this)
	        .setContentTitle("AVISO")
	        .setContentText(Texto.toUpperCase())
	        .build();
	        showcaseView.setButtonText("ACEPTAR");
		}
		else
		{
			showcaseView.setShowcase(new ViewTarget( activity.findViewById(IdView) ), true);
	        showcaseView.setContentTitle("AVISO");
	        showcaseView.setContentText(Texto.toUpperCase());
	        showcaseView.setButtonText("ACEPTAR");
	        
	        showcaseView.show();
		}
		
		isHelp = false;
	}

	public void showErrorHelp(int IdView, String Texto)
	{
		try
		{
			Sounds.PlayFailed(activity, false);
		}catch(Exception ex){}
		
		if(showcaseView == null)
		{
	        showcaseView = new ShowcaseView.Builder(activity)
	        .setTarget(new ViewTarget(activity.findViewById(IdView)))
	        .setOnClickListener(this)
	        .setContentTitle("AVISO")
	        .setContentText(Texto.toUpperCase())
	        .build();
	        showcaseView.setButtonText("ACEPTAR");
		}
		else
		{
			showcaseView.setShowcase(new ViewTarget( activity.findViewById(IdView) ), true);
	        showcaseView.setContentTitle("AVISO");
	        showcaseView.setContentText(Texto.toUpperCase());
	        showcaseView.setButtonText("ACEPTAR");
	        
	        showcaseView.show();
		}
		
		isHelp = false;
	}
	
}
