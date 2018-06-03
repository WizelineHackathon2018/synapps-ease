package com.comynt.launa.async;

import com.comynt.launa.GPS;
import com.comynt.launa.Repositorio;

import BO.*;
import android.content.Context;
import com.System.Dispositivo.Info;
import com.System.Utils.Logg;

public class AsyncEnviarPosiciones extends Thread
{
    private BOGps puntoGps;
    Context context;
    
    public AsyncEnviarPosiciones(Context context)
    {
    	this.context = context;
    }
    
    public void run()
    {
        if( GPS.lsGps != null )
        {
            GPS.enviandoPuntos = true;
            try
            {
                GPS.enviandoPuntos = true;
                for(int x = 0; x < GPS.lsGps.size(); x++)
                {
                    puntoGps = (BOGps)GPS.lsGps.get(x);
                    
                    if(!puntoGps.Velocidad.equals(""))
                    {
                        //Enviamos al servidor la posicion gps
                        puntoGps.TipoProceso = "WS";
                        puntoGps.AgregarProceso("ActualizarPosicionGPS4");
                        puntoGps.AgregarParametro("wsNamesPace", "http://tempuri.org/");
                        puntoGps.AgregarParametro("wsUrl", Repositorio.wsURL);
                        puntoGps.AgregarParametro("WebMethod", "ActualizarPosicionGPS4");
                        
                        puntoGps.AgregarParametro("Coordenadas", puntoGps.Coordenadas);
                        puntoGps.AgregarParametro("FechaHora", puntoGps.FechaHora);
                        
                        puntoGps.AgregarParametro("IME", Info.getPIN(context));
                        
                        puntoGps.AgregarParametro("Bateria", puntoGps.Bateria);
                        puntoGps.AgregarParametro("Velocidad", puntoGps.Velocidad );
                        puntoGps.AgregarParametro("TipoAviso", puntoGps.TipoAviso );
                        puntoGps.AgregarParametro("Wifi", Info.GetIPAdress(context));
                        
                        if( Info.ServicioDatosActivos(context))
                        {
                            puntoGps.EjecutarProceso();
                            
                            //si esta bien elimina lo q mandaste
                            if(puntoGps.Error.equals(""))
                            {
                                puntoGps.ClearParametros();
                                puntoGps.ClearProceso();
                                puntoGps.TipoProceso = "SQL";
                                puntoGps.AgregarProceso("SQL.Eliminar");
                                puntoGps.EjecutarProceso();
                            }
                        }
                    }
                    else
                    {
                    	Logg.info("Punto OpencellID elminado");
                    	
                        puntoGps.ClearParametros();
                        puntoGps.ClearProceso();
                        puntoGps.TipoProceso = "SQL";
                        puntoGps.AgregarProceso("SQL.Eliminar");
                        puntoGps.EjecutarProceso();
                    }
                    
                    try {
                        Thread.sleep(1000);
                    }
                    catch (Exception e){ }
                }
                GPS.enviandoPuntos = false;
           }
           catch (Exception e)
           {
               GPS.enviandoPuntos = false;
           }
        }
        else
            GPS.enviandoPuntos = false;
        
        
        //envio de la info general
        
    }

}

