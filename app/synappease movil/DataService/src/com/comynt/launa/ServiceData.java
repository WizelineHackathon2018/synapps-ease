package com.comynt.launa;

import com.System.Dispositivo.Info;
import com.System.Utils.Logg;
import com.System.Utils.Utils;
import com.comynt.launa.GPS;
import com.comynt.launa.Repositorio;

import BO.BOConfiguracionEnvio;
import BO.BOGps;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class ServiceData extends Service 
{
	private static ServiceData instance  = null;
	Repositorio repositorio = Repositorio.getInstance();	
	Handler handler;

	  public static boolean isRunning()
	  {
		  return instance != null;
	  }

	  @Override
	  public IBinder onBind(Intent intent)
	  {
	    return null;
	  }

	  @Override
	  public void onCreate() 
	  {
		  Logg.info("Servicio ServiceData creado");
		  
	    instance=this;
	    handler = new Handler();
	    
		repositorio.context = ServiceData.this;
		  
		if(repositorio.context != null)
		{
			repositorio.IniciaVariables(ServiceData.this);
		}	    
	    
        super.onCreate();	    
	  }
	  
	  public void runOnUiThread(Runnable runnable) 
	  {
	        handler.post(runnable);
	  }	  

	  @Override
	  public void onDestroy() 
	  {
		  Logg.info("Servicio ServiceData destruido");
		  
			 try 
			 {
				startMainActivityWithWorkaround();
			 }
			 catch (NameNotFoundException e) 
			 {
				e.printStackTrace();
			 }
			 catch (ActivityNotFoundException e) 
			 {
				e.printStackTrace();
			 }
		  
		  instance = null;
	  }
	  
		protected void startMainActivityWithWorkaround() throws NameNotFoundException, ActivityNotFoundException 
		{
			Logg.info("startMainActivityWithWorkaround: ServiceData");
			
		    final String packageName = ServiceData.this.getPackageName();
		    final Intent launchIntent = ServiceData.this.getPackageManager().getLaunchIntentForPackage(packageName);
		    if (launchIntent == null) 
		    {
		      Logg.info("Launch intent is null");
		    } 
		    else
		    {
		      final String mainActivity = launchIntent.getComponent().getClassName();
		      Logg.info(String.format("Open activity with package name %s / class name %s", packageName, mainActivity));
		      final Intent intent = new Intent(Intent.ACTION_MAIN);
		      intent.addCategory(Intent.CATEGORY_LAUNCHER);
		      intent.setComponent(new ComponentName(packageName, mainActivity));
		      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		      ServiceData.this.startActivity(intent);
		    }
		}

	  @Override
	  public void onStart(Intent intent, int startid) 
	  {
		  Logg.info("Servicio ServiceData iniciado!!");
		  
		  //repositorio.GuardarEvento("Dispositivo Prendido", ServiceData.this);
		  
		  lanzarNotificacion();
		  
		  //repositorio.programaAlarma(ServiceData.this);
	  }

	   void lanzarNotificacion()
	   {
//			BOGps boGps = new BOGps(this);

//			boGps.TipoProceso = "SQL";
//			boGps.AgregarProceso("SQL.EliminarTodos");
//			boGps.EjecutarProceso();

//			BOConfiguracionEnvio conf = new BOConfiguracionEnvio(this);
//			conf.TipoProceso = "SQL";
//			conf.AgregarProceso("SQL.EliminarTodos");
//			conf.EjecutarProceso();
			
		   runOnUiThread(new Runnable(){
	       		  public void run() 
	       		  {
	     			  try
	     			  {
	     				 repositorio = Repositorio.getInstance();     				  
	     				  
	     	                int contaArranca = 0;
	     	                while (contaArranca < 5)
	     	                {
	     	                    try {
	     	                        Thread.sleep(1000);
	     	                    }
	     	                    catch (Exception e){ }
	     	                    contaArranca++;
	     	                }

	     					if(repositorio.gps == null)
	     					{
	     						if(repositorio.context != null)
	     						{
	         	                    try 
	         	                    {
	             						repositorio.gps = new GPS(ServiceData.this);
	             						
	         							Logg.info("Servicio ServiceData GPS iniciado con exito contexto ok!!");
	         							
	         							repositorio.EnviarDatos();
	         							
	         							Logg.info("Enviando datos pendientes de venta!!");
	         	                    }
	         	                    catch (Exception e)
	         	                    { 
	         							Logg.info("Servicio ServiceData contexto nulo!!");
	         	                    }
	     						}
	     						else
	     						{
	     							Logg.info("Servicio ServiceData contexto nulo!!");
	     						}
	     					}
	     			  }
	     			  catch(Exception ex) 
	     			  { 
	     				  
	     			  }
	       		  }
	       		});

//			if(repositorio.gps == null)
//			{
//				repositorio.gps = new GPS(this);
//			}
	   }

}
