package com.comynt.launa.async;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;

import com.comynt.launa.GPSPoint;
import com.comynt.launa.Repositorio;

public class AsyncObtenerGPS extends Thread 
{
	private Repositorio repositorio = Repositorio.getInstance();
	public IAsyncEvent evento;

	Context context;
	
	public boolean continuar = true; 

	public AsyncObtenerGPS(Context context)
	{
		this.context = context;
		continuar = true;
	}

	public void run()
	{
    	while (continuar)
    	{
    		//Validamos si tiene gps y si el tiempo es menor a un minuto
    		if(!GPSPoint.PosicionGPS.equals(""))
    		{
                Calendar c = Calendar.getInstance();

                int minuto = c.get(Calendar.MINUTE);
                int hora = c.get(Calendar.HOUR_OF_DAY);
                
                if(minuto > 0)
                {
                	minuto = minuto - 1;
                }
                else
                {
                	if(hora > 0)
                	{
                		hora = hora - 1;                		
                	}
                	else
                	{
                		hora = 23;
                		minuto = 59;
                	}
                }

                c.set(Calendar.HOUR_OF_DAY, hora);
                c.set(Calendar.MINUTE, minuto);

                Date fechahoraminutoantes = c.getTime();

                if(GPSPoint.Tiempo > fechahoraminutoantes.getTime())
                {
            		if (this.evento != null)
            			this.evento.Event("GPS", GPSPoint.PosicionGPS);
                }
    		}

        	try {
        		Thread.sleep(1000L);
            }
            catch (Exception localException){}

    	}
	}

}
