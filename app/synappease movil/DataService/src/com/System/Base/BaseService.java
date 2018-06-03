package com.System.Base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Arrays;

import com.System.Utils.Logg;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;

public class BaseService extends Service 
{
	@Override
	public IBinder onBind(Intent intent) 
	{
		return null;
	}

	@Override
	public void onCreate() 
	{
		super.onCreate();

		final Thread.UncaughtExceptionHandler oldHandler = Thread.getDefaultUncaughtExceptionHandler();

		Thread.setDefaultUncaughtExceptionHandler(
				new Thread.UncaughtExceptionHandler() 
				{
					@Override
					public void uncaughtException(Thread paramThread, Throwable paramThrowable)
					{
						String filename = "errorSLog.txt";
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
	}
}
