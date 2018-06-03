package com.System.Base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import com.System.Dispositivo.api.DeviceInfo;
import com.System.Showcase.showcaseManager;
import com.System.Utils.Logg;
import com.System.Utils.Utils;
import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;
import com.comynt.launa.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.comynt.launa.MyApplication;
import com.comynt.launa.Repositorio;
import com.comynt.launa.adapt.ViewPagerAdapter;
import com.comynt.launa.adapt.ViewPagerAdapterGraficos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

public class BaseActivity extends Activity implements kBase
{
	Repositorio repositorio = Repositorio.getInstance();
	
	public showcaseManager showman;
	
	public static TextToSpeech mTts;
	public OnInitListener tts_escucha;
	static boolean listohabla = false;
	
	@Override
	protected void onResume() {
	  super.onResume();
	  MyApplication.activityResumed();
	}

	@Override
	protected void onPause() {
	  super.onPause();
	  MyApplication.activityPaused();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		final Thread.UncaughtExceptionHandler oldHandler = Thread.getDefaultUncaughtExceptionHandler();

		Thread.setDefaultUncaughtExceptionHandler(
				new Thread.UncaughtExceptionHandler() {
					@Override
					public void uncaughtException(Thread paramThread, Throwable paramThrowable)
					{
						String filename = "errorALog.txt";
						String contenido = paramThrowable.getMessage() + "\n" + Arrays.toString(paramThrowable.getStackTrace());

						Logg.error("Error Aplicacion: " + paramThrowable.getMessage() + "\n" + Arrays.toString(paramThrowable.getStackTrace()));

						try
						{
							BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new 
									File(Environment.getExternalStorageDirectory() + File.separator + filename)));
							bufferedWriter.write(contenido);
							bufferedWriter.close();
						}
						catch (Exception e)
						{
							Logg.error("Error Handler: " + Arrays.toString(e.getStackTrace()));
						}	                	

						if (oldHandler != null)
							oldHandler.uncaughtException(paramThread, paramThrowable); //Delegates to Android's error handling
						else
							System.exit(2); //Prevents the service/app from freezing
					}
				});
		
		tts_escucha = new OnInitListener()
		{
		    public void onInit(int status)
		    {
		        if (status == TextToSpeech.SUCCESS)
		        {
		        	listohabla = true;
		        }
		        else 
		        {
		        	//mens("Sin iniciar TextToSpeech.");
		        }
		    }
		};
        mTts = new TextToSpeech(this, tts_escucha);

		showman = new showcaseManager(this);
		
		//Verificamos si hay error y si hay mandar mensaje
		checkError("errorSLog.txt");
		checkError("errorFALog.txt");
		checkError("errorALog.txt");
	}
	
	private void checkError(String filename)
	{
		try 
		{
			BufferedReader bufferedReader;
			
			File archivo = new File(Environment.getExternalStorageDirectory() + File.separator + filename);
			bufferedReader = new BufferedReader(new FileReader(archivo));

			String read;
			StringBuilder builder = new StringBuilder("");

			while((read = bufferedReader.readLine()) != null)
			{
				builder.append(read);
			}

			//Evento de error
			Toast.makeText(this, "Parece que e tenido un error, ya e informado sobre el y en breve se solucionara", Toast.LENGTH_LONG).show();

			bufferedReader.close();

			archivo.delete();	        

			//Guardamos el error en el BOError
			if(filename.equals("errorSLog.txt")) //Error en el servicio
			{
				DeviceInfo dispositivo = new DeviceInfo(this);

				try
				{
					repositorio.Error.Os = dispositivo.getOsVersion();
				}catch(Exception ex){}
				
				try
				{
					repositorio.Error.Sdk = dispositivo.getSdkVersion();
				}catch(Exception ex){}
				
				try
				{
					repositorio.Error.Dev = dispositivo.getDevice();
				}catch(Exception ex){}
				
				try
				{
					repositorio.Error.Imei = dispositivo.getDeviceId();
				}catch(Exception ex){}
				
				try
				{
					repositorio.Error.Imsi = dispositivo.getIMSINumber();
				}catch(Exception ex){}
				
				try
				{
					repositorio.Error.Mac = dispositivo.getMACAddress();
				}catch(Exception ex){}
				
				//repositorio.Error.Mail = dispositivo.getEmail();
				
				try
				{
					repositorio.Error.Simser = dispositivo.getSimSerialNumber();
				}catch(Exception ex){}

				repositorio.Error.Detalle = builder.toString(); 

				repositorio.Error.Tipo = "BaseService";
				repositorio.Error.FechaHora = Utils.getFechaHoraActual();

				repositorio.GuardarError();

				Logg.info("Se agrego el error: " + builder.toString());
			}
			else if(filename.equals("errorFALog.txt")) //Error en el fragmentactivty
			{
				DeviceInfo dispositivo = new DeviceInfo(this);

				try
				{
					repositorio.Error.Os = dispositivo.getOsVersion();
				}catch(Exception ex){}
				
				try
				{
					repositorio.Error.Sdk = dispositivo.getSdkVersion();
				}catch(Exception ex){}
				
				try
				{
					repositorio.Error.Dev = dispositivo.getDevice();
				}catch(Exception ex){}
				
				try
				{
					repositorio.Error.Imei = dispositivo.getDeviceId();
				}catch(Exception ex){}
				
				try
				{
					repositorio.Error.Imsi = dispositivo.getIMSINumber();
				}catch(Exception ex){}
				
				try
				{
					repositorio.Error.Mac = dispositivo.getMACAddress();
				}catch(Exception ex){}
				
				//repositorio.Error.Mail = dispositivo.getEmail();
				
				try
				{
					repositorio.Error.Simser = dispositivo.getSimSerialNumber();
				}catch(Exception ex){}

				repositorio.Error.Detalle = builder.toString(); 

				repositorio.Error.Tipo = "BaseFragmentActivity";
				repositorio.Error.FechaHora = Utils.getFechaHoraActual();

				repositorio.GuardarError();

				Logg.info("Se agrego el error: " + builder.toString());
			}
			else if(filename.equals("errorALog.txt")) //Error en la actividad
			{
				DeviceInfo dispositivo = new DeviceInfo(this);

				try
				{
					repositorio.Error.Os = dispositivo.getOsVersion();
				}catch(Exception ex){}
				
				try
				{
					repositorio.Error.Sdk = dispositivo.getSdkVersion();
				}catch(Exception ex){}
				
				try
				{
					repositorio.Error.Dev = dispositivo.getDevice();
				}catch(Exception ex){}
				
				try
				{
					repositorio.Error.Imei = dispositivo.getDeviceId();
				}catch(Exception ex){}
				
				try
				{
					repositorio.Error.Imsi = dispositivo.getIMSINumber();
				}catch(Exception ex){}
				
				try
				{
					repositorio.Error.Mac = dispositivo.getMACAddress();
				}catch(Exception ex){}
				
				//repositorio.Error.Mail = dispositivo.getEmail();
				
				try
				{
					repositorio.Error.Simser = dispositivo.getSimSerialNumber();
				}catch(Exception ex){}

				repositorio.Error.Detalle = builder.toString(); 

				repositorio.Error.Tipo = "BaseActivity";
				repositorio.Error.FechaHora = Utils.getFechaHoraActual();

				repositorio.GuardarError();

				Logg.info("Se agrego el error: " + builder.toString());
			}

		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
	
	public static void habla(String cad)
	{
		if((listohabla) && (!mTts.isSpeaking()))
		{
			Logg.info("Sirigonia: " + cad);
			
			mTts.speak(cad, TextToSpeech.QUEUE_FLUSH, null);
	    	while(mTts.isSpeaking());
		}
	}

	@Override
	public void CerrarMenus() 
	{
		//NO HAY MENUS PARA CERRAR NI SE CARGAN
	}

	@Override
	public void Habla(String texto) 
	{
		// TODO Auto-generated method stub
			habla(texto);
	}
	
//	public void showHelp(int IdView, String Texto)
//	{
//		showman.showHelp(IdView, Texto);
//	}
	
	public void showHelp(int IdView, String Texto)
	{
		showman.showErrorHelp(IdView, Texto);
	}
	
}
