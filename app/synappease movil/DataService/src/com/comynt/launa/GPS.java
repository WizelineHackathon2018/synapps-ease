package com.comynt.launa;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationProvider;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;

import java.util.*;
import java.io.*;

import BO.*;

import com.System.Dispositivo.Info;
import com.System.Utils.Utils;
import com.System.Utils.Logg;
import com.comynt.launa.async.*;

public final class GPS extends TimerTask
{
	static Repositorio repositorio = Repositorio.getInstance();
	
    public static int Contador = 0;
    public static int GpsEstado = 0;
    public static int ContadorReprograma = 0;
    public static int ContadorResetea = 0;
    
    public static double longitude, latitude, velocidad;
    
    static Thread readthread;
    
    public static long lastGPSRead; //ultimo envio
    
    public static long ultimoGPSValido; //ultimo valido del listener
    public static long ultimoTemporal; //ultimo temporal del listener
    public static long ultimoTempo2; //ultimo temporal del listener
	public static long OpenCellIDRead;
    
    public static long ultimoMinuto; //ultimo temporal del listener
    
    public long tiempoMaximoReseteoGps = 360000; // reseteo gps 6 min por defecto
    public long tiempoMaximoReseteoEquipo = 7200000; // reseteo dispositivo 2 horas
    
    public static long lastReset;
    
    private Timer timer;
    
    public static String log       = "";
    private static String coordenadas     = "";
    public static String tipoAviso = "Normal";
    
    private int SegundosEnvio      = 180000;//segundos a los cuales debe enviar default 3 min
    private int TickerTime         = 20000; //cada 20 segundos de ejecuta el proceso para verificar
    
    private boolean panico         = false;
    private static boolean gpsIniciado = false;
    private boolean estaReseteando = false;
    private boolean primerRepro    = false;
    public static boolean enviandoPuntos    = false;
	public static boolean enviandoPuntosCellId    = false;
    
    public static boolean fueraServicio    = false;
    public static boolean temporalmentefueraServicio = false;
    public static boolean enviarTodo = false;
    
    public static Context contexto;
    
    public static BOGps gps = new BOGps(contexto);
    public static List<BO> lsGps = null;
    
    private BOConfiguracionEnvio configEnvio = new BOConfiguracionEnvio(contexto);
    private List<BO> lsConfigEnvio = null;
    
    private BOFechaActualizacion response =  new BOFechaActualizacion(contexto);
    private List<BO> lsResponse = null;
    
    private BOReset FechaReset = new BOReset(contexto);
    private List<BO> lsFechaReset = null;
    
    private BOFechaActualizacion ultimafecha =  new BOFechaActualizacion(contexto);
    private List<BO> lsUltimaFecha = null;
    
    private int newHandle = 0;


    //public static String wsURL = "http://zinterprod.no-ip.org:8090/VM_Damsa/WSVentas.asmx";
    
    //public static String wsURL = "http://zinterprod.no-ip.org:8090/VM_LaUna/WSVentas.asmx";
    
    //public static String wsURL = "http://192.168.1.251:80/VentasMoviles/WSVentas.asmx";


    private static GPSListener gpslistener;
    public static LocationManager provider;
    private static Context context;
    
//    private static AsyncEnviarPosiciones oAsyncEnviarPosiciones;
//    private static Thread _asyncEnviarPosiciones;
//    
//	private static AsyncEnviarPosicionesCellId oAsyncEnviarPosicionesCellId;
//	private static Thread _asyncEnviarPosicionesCellId;
    
    private static int ContadorWifi = 0;
    
    static boolean isGPSEnabled = false;
    static boolean isNetworkEnabled = false;
    static boolean canGetLocation = false;
    
    private boolean gps_enabled = false;
    private LocationManager lm;    
	
    public GPS(Context context)
    {
    	try
    	{
    		this.context = context;
    		if(provider != null)
    		{
    			provider = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    		}
    	}
    	catch(Exception ex)
    	{
    		int x = 0;
    	}
    	
    	
        Logg.info("Nueva instancia de GPS()");
        lastGPSRead = System.currentTimeMillis();
        lastReset = lastGPSRead;
        ultimoGPSValido = lastGPSRead;
        ultimoTemporal = lastGPSRead;
        ultimoTempo2 = lastGPSRead;
        ultimoMinuto = lastGPSRead;
		OpenCellIDRead = lastGPSRead;
        
        //obtenemos las ultima reseteada
        FechaReset.TipoProceso = "SQL";
        FechaReset.AgregarProceso("SQL.Consultar");
        FechaReset.AgregarParametro("idSortField", String.valueOf(FechaReset.ID_Fecha));
        FechaReset.AgregarParametro("idFilterField", String.valueOf(FechaReset.ID_Fecha));
        FechaReset.AgregarParametro("filtro", "");
        this.lsFechaReset = FechaReset.ConsultarProceso();
        
        //obtenemos las ultima fecha de actualizacion
        ultimafecha.TipoProceso = "SQL";
        ultimafecha.AgregarProceso("SQL.Consultar");
        ultimafecha.AgregarParametro("idSortField", String.valueOf(ultimafecha.ID_FechaModificacion));
        ultimafecha.AgregarParametro("idFilterField", String.valueOf(ultimafecha.ID_FechaModificacion));
        ultimafecha.AgregarParametro("filtro", "");
        this.lsUltimaFecha = ultimafecha.ConsultarProceso();
        
        if( this.lsUltimaFecha != null )
        {
            ultimafecha = (BOFechaActualizacion)lsUltimaFecha.get(0);
        }
        else
        {
            ultimafecha.FechaModificacion = "";
        }
        
        //obtenemos la configuracion de envio
        configEnvio.TipoProceso = "SQL";
        configEnvio.AgregarProceso("SQL.Consultar");
        configEnvio.AgregarParametro("idSortField", String.valueOf(configEnvio.ID_IdConfiguracion));
        configEnvio.AgregarParametro("idFilterField", String.valueOf(configEnvio.ID_IdConfiguracion));
        configEnvio.AgregarParametro("filtro", "");
        this.lsConfigEnvio = configEnvio.ConsultarProceso();
        if( this.lsConfigEnvio != null )
        {
            configEnvio = (BOConfiguracionEnvio)lsConfigEnvio.get(0);
        }
        
        inicializa();
    }
        
    private void inicializa()
    {
        Logg.info("Inicia Timer");
        try
        {
            if(timer == null)
            {
                timer = new Timer();
            }
            else
            {
                StopGPS();
                timer.cancel();
                timer = null;
                new GPS(this.context);
            }
            timer.schedule(this, 0, TickerTime);
        }
        catch(Exception e)
        {
            int x = 0;
        }
    }
    
    public void StopGPS()
    {
        try
        {
            Logg.info("Detiene GPS");
            
            provider.removeUpdates(gpslistener);
            
            provider = null;
            gpsIniciado = false;
        }
        catch(Exception e){}
    }
    
    public static void resetGPS(int tiempo)
    {
        try
        {
            Logg.info("Reseteo del GPS");
            
            if(provider != null)
            {
            	provider.removeUpdates(gpslistener);
                provider = null;
            }
            
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setPowerRequirement(Criteria.POWER_HIGH);
            criteria.setAltitudeRequired(true);
            criteria.setSpeedRequired(true);
            criteria.setCostAllowed(true);
            criteria.setBearingRequired(true);

            criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
            criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);
            criteria.setSpeedAccuracy(Criteria.ACCURACY_MEDIUM);
            criteria.setBearingAccuracy(Criteria.ACCURACY_MEDIUM);
            
            try
            {
        		if(provider == null)
        		{
        			provider = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        		}
            }
            catch(Exception exg){}
            
            gpslistener = null;
            gpslistener = new GPSListener();
            
            isGPSEnabled = provider.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = provider.isProviderEnabled(LocationManager.NETWORK_PROVIDER);            
            
            if (isGPSEnabled) 
            {
            	ServiceData actividad = (ServiceData)context;
            	actividad.runOnUiThread(new Runnable() {
            		  public void run() 
            		  {
            			  try
            			  {
            				  provider.requestLocationUpdates(
            						  LocationManager.GPS_PROVIDER, 50000, 0, gpslistener);
            			  }
            			  catch(Exception ex) { }
            		  }
            		});

            	Logg.info("GPS del dispositivo");
            }
          else if (isNetworkEnabled) 
          {
        	  ServiceData actividad = (ServiceData)context;
          	  actividad.runOnUiThread(new Runnable(){
          		  public void run() 
          		  {
        			  try
        			  {
                      		provider.requestLocationUpdates(
                    			LocationManager.NETWORK_PROVIDER, 50000, 0, gpslistener);
        			  }
        			  catch(Exception ex) { }
          		  }
          		});

              Logg.info("GPS de la red");
          }

          gpsIniciado = true;        	
        }
        catch(Exception e)
        {
            String Error = e.getMessage();
            Logg.error("Error 1: " + Error);
        }
    }

    @Override
    public void run()
    {
        boolean ejecutaHilo = true, ejecutaCambio = false;
        while (ejecutaHilo)
        {
        	
        	if(!Info.ServicioDatosActivos(this.context))
            {
                int contaArranca = 0;
                while (contaArranca < 10)
                {
                    try {
                        Thread.sleep(1000);
                    }
                    catch (Exception e){ }
                    contaArranca++;
                }
            }
            
            if(!HaciendoLlamada())
            {
                //si no hay horario reprograma
                if( (configEnvio.HoraInicio.equals("")) ||  (configEnvio.HoraFin.equals("")) )
                {
                    if( ContadorReprograma > 3 )
                    {
                        ReprogramaEnvio();
                        ContadorReprograma = 0;
                        primerRepro = true;
                    }
                    else
                    {
                        ContadorReprograma++;
                    }
                }
                else//si hay horario mandar si esta en rango
                {
                    //lo primero que hay que hacer es reprogramar al prender, ya despues se revisa lo demas
                    if(!primerRepro)
                    {
                        if( ContadorReprograma > 3 )
                        {
                            ReprogramaEnvio();
                            ContadorReprograma = 0;
                            primerRepro = true;
                            Logg.info("Primera reprogramacion");
                        }
                        else
                        {
                            ContadorReprograma++;
                            try {
                                Thread.sleep(1000);
                            }
                            catch (Exception e){ }
                        }
                    }
                    else//si ya se reprogramo verifica el reango de tiempo
                    {
                        //verificamos esta dentro del rango
                        Calendar cal = Calendar.getInstance();
                        Date fechaActual = cal.getTime();
                        Calendar calInicio = Calendar.getInstance();
                        calInicio.set(Calendar.HOUR_OF_DAY, Integer.parseInt( Utils.splitString( configEnvio.HoraInicio, ':' )[0] ));
                        calInicio.set(Calendar.MINUTE, Integer.parseInt( Utils.splitString( configEnvio.HoraInicio, ':' )[1] ));
                        Date fechaInicio = calInicio.getTime();
                        Calendar calFin = Calendar.getInstance();
                        calFin.set(Calendar.HOUR_OF_DAY, Integer.parseInt( Utils.splitString( configEnvio.HoraFin, ':' )[0] ));
                        calFin.set(Calendar.MINUTE, Integer.parseInt( Utils.splitString( configEnvio.HoraFin, ':' )[1] ));
                        Date fechaFin = calFin.getTime();
                        
                        if((fechaActual.getTime() >=  fechaInicio.getTime()) && 
                            (fechaActual.getTime() <=  fechaFin.getTime()))//si esta en el rango
                        {
                            if(!gpsIniciado) // si no esta iniciado el gps lo inicia
                            {
                                resetGPS(50);
                                
                                lastGPSRead = System.currentTimeMillis();
                                lastReset = lastGPSRead;
                                ultimoGPSValido = lastGPSRead;
                                ultimoTemporal = lastGPSRead;
                                ultimoTempo2 = lastGPSRead;
                        		OpenCellIDRead = lastGPSRead;
                                
                                Logg.info("GPS Iniciado");
                            }
                            
							//Verificamos el tiempo si paso mas de un minuto tomar el OpenCellID y ver por el proximo tiempo 
							if(System.currentTimeMillis() - OpenCellIDRead >= 120000)
							{
								OpenCellIDRead = System.currentTimeMillis();
								Logg.info("Obtener datos de OpenCellID" + Utils.getFechaHoraActual());
								
								ObtenerCelda();
								
								Logg.info("OK Datos de OpenCellID" + Utils.getFechaHoraActual());
							}
							//Verificamos el tiempo si paso mas de un minuto tomar el OpenCellID y ver por el proximo tiempo
                            
                            if((longitude > 0 || longitude < 0) && (latitude > 0 || latitude < 0))
                            {
                                coordenadas = latitude + "," + longitude; //obetenemos las coordenadas
                            }
                            
                            //si se cumplen los min de envio se envia el gps
                            if(System.currentTimeMillis() - lastGPSRead >= SegundosEnvio)
                            {
                                if((longitude > 0 || longitude < 0) && (latitude > 0 || latitude < 0))
                                {
                                    EnviaGPS(coordenadas, tipoAviso);
                                }
                            }
                            
                            if( ( temporalmentefueraServicio ) && ( fueraServicio ) ) // si esta habilitado
                            {
                                //validamos el tiempo a ver si no se cicla por 6 min y lo reseteamos si es asi
                                //si es mas de dos horas se resetea el equipo
                                if( System.currentTimeMillis() - ultimoGPSValido > tiempoMaximoReseteoEquipo )
                                {
                                    ReseteaDispositivo();
                                }
                                else
                                {
                                    //si es mas de 6 min reinicia hasta llegar a las 2 horas
                                    if( System.currentTimeMillis() - ultimoGPSValido > tiempoMaximoReseteoGps )
                                    {
                                        //si es mas de 6 min reinicia cada 6 min
                                        if( System.currentTimeMillis() - ultimoTemporal > tiempoMaximoReseteoGps )
                                        {
                                            resetGPS(40);
                                            ultimoTemporal = System.currentTimeMillis();                                        
                                        }
                                    }
                                }
                            }
                            
                            //si temporalmente fuera de servicio, resetea a los 6 min
                            if( (temporalmentefueraServicio) || (fueraServicio) )
                            {
                                //si es mas de dos horas
                                if( System.currentTimeMillis() - ultimoTemporal > tiempoMaximoReseteoEquipo )
                                {
                                    ReseteaDispositivo();
                                }
                                else
                                {
                                    //si es mas de 6 min reinicia hasta llegar a las 2 horas
                                    if( System.currentTimeMillis() - ultimoTemporal > tiempoMaximoReseteoGps )
                                    {
                                        //si es mas de 6 min reinicia cada 6 min
                                        if( System.currentTimeMillis() - ultimoTempo2 > tiempoMaximoReseteoGps )
                                        {
                                            resetGPS(50);
                                            ultimoTempo2 = System.currentTimeMillis();
                                        }
                                    }
                                }
                            }
                        }
                        else//si no esta en rango debe apagar el gps
                        {
                            if(gpsIniciado)
                            {
                                StopGPS();
                                Logg.info("GPS apagado");
                            }
                        }
                    }
                }
            }
            else//si esta llamando espera un segundo
            {
                try {
                    Thread.sleep(1000);
                }
                catch (Exception e){ }
            }
            ejecutaHilo = false;
        }
    }
    
    private void ReseteaDispositivo()
    {
        if(this.lsFechaReset == null)
        {
            FechaReset.Fecha = Utils.getFechaActual_DD_MM_YYYY();
            FechaReset.TipoProceso = "SQL";
            FechaReset.AgregarProceso("SQL.Agregar");
            FechaReset.EjecutarProceso();
            
            try {
                reset();
            }
            catch (Exception e) {}
            
        }
        else
        {
            FechaReset = (BOReset)lsFechaReset.get(0);
            //si no es igual se resetea y se actualiza
            if( !FechaReset.Fecha.equals( Utils.getFechaActual_DD_MM_YYYY() ) )
            {
                FechaReset.Fecha = Utils.getFechaActual_DD_MM_YYYY();
                FechaReset.TipoProceso = "SQL";
                FechaReset.AgregarProceso("SQL.Actualizar");
                FechaReset.EjecutarProceso();
                
                try {
                    reset();
                }
                catch (Exception e) {}
                
            }
        }
    }
    
	private void ObtenerCelda()
	{
		try
		{
		    TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		    GsmCellLocation cellLocation = (GsmCellLocation)telephonyManager.getCellLocation();
			
		    String networkOperator = telephonyManager.getNetworkOperator();
		    
			repositorio.CellID.mcc = networkOperator.substring(0, 3);
			repositorio.CellID.mnc = networkOperator.substring(3);
			repositorio.CellID.cellid = cellLocation.getCid();
			repositorio.CellID.lac = cellLocation.getLac();
			
			repositorio.CellID.Bateria = String.valueOf(Info.getBateriaNivel(context));
			repositorio.CellID.Wifi = Info.GetIPAdress(this.context);
			repositorio.CellID.FechaHora = Utils.getFechaHoraActual();
			
			repositorio.GuardarCellID();
			fueraServicio = true;
			
			Logg.info("CellID Obtenido, mcc:" + repositorio.CellID.mcc + ", mnc:" + repositorio.CellID.mnc + ", cellid:" + 
					repositorio.CellID.cellid + ", lac:" + repositorio.CellID.lac + ", Bateria:" + repositorio.CellID.Bateria + 
					", Wifi: " + repositorio.CellID.Wifi);
		}
		catch(Exception e)
		{
			Logg.error("Error " + e.getMessage() + " - " + Arrays.toString(e.getStackTrace()));
		}
	}
	
    public static void RevisarProveedor()
    {
    }
	
    private void EnviaGPS(String coordenadas, String aviso)
    {
    	lastGPSRead = System.currentTimeMillis();//el ultimo envio
    	Logg.info("lastGPSRead: " + lastGPSRead);
    	
        response =  null;
        response =  new BOFechaActualizacion(contexto);
        
            try {
                //Enviamos al servidor la posicion gps
                response.TipoProceso = "WS";
                response.AgregarProceso("ActualizarPosicionGPS4");
                response.AgregarParametro("wsNamesPace", "http://tempuri.org/");
                response.AgregarParametro("wsUrl", Repositorio.wsURL);
                response.AgregarParametro("WebMethod", "ActualizarPosicionGPS4");
                
                response.AgregarParametro("Coordenadas", coordenadas);
                response.AgregarParametro("FechaHora", Utils.getFechaHoraActual());
                response.AgregarParametro("IME", Info.getPIN(context));
                
                response.AgregarParametro("Bateria", String.valueOf(Info.getBateriaNivel(this.context)));
                
                response.AgregarParametro("Velocidad", String.valueOf( Utils.round(velocidad * 3.6, 2) + " Km/Hr" ));
                
                response.AgregarParametro("Wifi", Info.GetIPAdress(this.context));
                
                if( ( !temporalmentefueraServicio ) && ( !fueraServicio ) ) // si esta habilitado
                {
                    response.AgregarParametro("TipoAviso", "Normal");
                    //fueraServicio = false;
                    Logg.info("GPS Disponible");
                }
                else if( temporalmentefueraServicio )
                {
                    response.AgregarParametro("TipoAviso", "Temporal");
                    //fueraServicio = false;
                    Logg.info("GPS Temporalmente fuera de servicio");
                }
                else if( fueraServicio )
                {
                    response.AgregarParametro("TipoAviso", "Fuera");
                    //fueraServicio = true;
                    Logg.info("GPS Fuera de servicio");
                }
                
                if( (!HaciendoLlamada()) && ( Info.ServicioDatosActivos(context) ) )
                {
                    lsResponse = response.ConsultarProceso();
                    if (lsResponse != null)
                    {
                        Logg.info("Posicion enviada");
                        if(response.Error.equals(""))//si no hay error al mandar
                        {
                            lastGPSRead = System.currentTimeMillis();//el ultimo envio
                            
                            if( ( temporalmentefueraServicio ) && ( fueraServicio ) ) // si esta habilitado
                            {
                                lastReset = System.currentTimeMillis();//el ultimo envio valido
                            }
                            
                            response = (BOFechaActualizacion) lsResponse.get(0);
                            if( !this.ultimafecha.FechaModificacion.equals(response.FechaModificacion) )
                            {
                                //cambiamos la fecha modifica
                                this.ultimafecha.FechaModificacion = response.FechaModificacion;
                                
                                ultimafecha.ClearProceso();
                                ultimafecha.ClearParametros();
                                ultimafecha.TipoProceso = "SQL";
                                ultimafecha.AgregarProceso("SQL.EliminarTodos");
                                ultimafecha.EjecutarProceso();
                                
                                ultimafecha.ClearProceso();
                                ultimafecha.ClearParametros();
                                ultimafecha.TipoProceso = "SQL";
                                ultimafecha.AgregarProceso("SQL.Agregar");
                                ultimafecha.EjecutarProceso();
                                
                                Logg.info("Fecha no igual, reprogramar");
                                ReprogramaEnvio(); //cambiamos el tiempo de envio y configuracion
                            }
                        }
                    }
                }
                else{
                    //TODO poner algo para q se mande apenas deje de hablar o halla señal
                }
            }
            catch(Exception e) {
                String Error = e.getMessage();
                Logg.error("Error 2: " + Error);
            }
            finally {
                response.ClearParametros();
                response.ClearProceso();
            }

        if(enviarTodo) //enviamos todos los puntos guardados
        {
            
            if( !enviandoPuntos )
            {
            	enviandoPuntos = true;
            	
                //consultamos los puntos gps a ver si hay, si no hay manda el ultimo
                gps.TipoProceso = "SQL";
                gps.AgregarProceso("SQL.Consultar");

                gps.AgregarParametro("idSortField", String.valueOf(gps.ID_FechaHora));
                gps.AgregarParametro("idFilterField", String.valueOf(gps.ID_FechaHora));
                gps.AgregarParametro("filtro", "");
                this.lsGps = gps.ConsultarProceso();
                
                if(this.lsGps != null)
                {
					if(repositorio.IME.equals(""))
					{
	     				 try
	     				 {
		     				 String IME = Info.getPIN(repositorio.context);
		     				 if(!IME.equals(""))
		     				 {
		     					repositorio.IME = IME;
		     				 }
	     				 }
	     				 catch(Exception ex){}
					}
					
					if(!repositorio.IME.equals(""))
					{
						repositorio.EnviarDatosGPS();
					}
                }
                else
                {
                	enviandoPuntos = false;
                }
            }
            
			if(( !enviandoPuntos ) && ( !enviandoPuntosCellId ))
			{
				repositorio.CellID.TipoProceso = "SQL";
				repositorio.CellID.AgregarProceso("SQL.Consultar");

				repositorio.CellID.AgregarParametro("idSortField", String.valueOf(repositorio.CellID.ID_FechaHora));
				repositorio.CellID.AgregarParametro("idFilterField", String.valueOf(repositorio.CellID.ID_FechaHora));
				repositorio.CellID.AgregarParametro("filtro", "");
				repositorio.lsCellID = repositorio.CellID.ConsultarProceso();
				
				repositorio.EnviarDatosGPSCellID();
				
			}
        }
    }
    
    public static void EnviarTodoAhora()
    {
        if( !enviandoPuntos )
        {
        	enviandoPuntos = true;
        	
            //consultamos los puntos gps a ver si hay, si no hay manda el ultimo
            gps.TipoProceso = "SQL";
            gps.AgregarProceso("SQL.Consultar");

            gps.AgregarParametro("idSortField", String.valueOf(gps.ID_FechaHora));
            gps.AgregarParametro("idFilterField", String.valueOf(gps.ID_FechaHora));
            gps.AgregarParametro("filtro", "");
            
            lsGps = gps.ConsultarProceso();
            
            if(lsGps != null)
            {
            	
            	repositorio.EnviarDatosGPS();
            	
//				if (oAsyncEnviarPosiciones == null)
//				{
//					Logg.info("Iniciando proceso se envio");
//                    oAsyncEnviarPosiciones = new AsyncEnviarPosiciones(context);
//                    _asyncEnviarPosiciones = new Thread(oAsyncEnviarPosiciones);
//                    _asyncEnviarPosiciones.start();
//				}
//				else
//				{
//					Logg.info("El proceso de envio GPS ya existe");
//
//					if(!_asyncEnviarPosiciones.isAlive())
//					{
//						Logg.info("El proceso de envio GPS esta detenido, iniciando el proceso");
//
//						_asyncEnviarPosiciones.start();
//					}
//					else
//					{
//						Logg.info("El proceso de envio esta corriendo");
//					}
//				}
				
            }
        }
        
		if(( !enviandoPuntos ) && ( !enviandoPuntosCellId ))
		{
			enviandoPuntosCellId =  true;
			
			repositorio.CellID.TipoProceso = "SQL";
			repositorio.CellID.AgregarProceso("SQL.Consultar");

			repositorio.CellID.AgregarParametro("idSortField", String.valueOf(repositorio.CellID.ID_FechaHora));
			repositorio.CellID.AgregarParametro("idFilterField", String.valueOf(repositorio.CellID.ID_FechaHora));
			repositorio.CellID.AgregarParametro("filtro", "");
			repositorio.lsCellID = repositorio.CellID.ConsultarProceso();
			
			repositorio.EnviarDatosGPSCellID();
			
//			if (oAsyncEnviarPosicionesCellId == null)
//			{
//				//consultamos los puntos gps a ver si hay, si no hay manda el ultimo
//				Logg.info("Iniciando proceso se envio");
//				oAsyncEnviarPosicionesCellId = new AsyncEnviarPosicionesCellId(context, repositorio);
//				_asyncEnviarPosicionesCellId = new Thread(oAsyncEnviarPosicionesCellId);
//				_asyncEnviarPosicionesCellId.start();
//			}
//			else
//			{
//				Logg.info("El proceso de envio GPS ya existe");
//
//				if(!_asyncEnviarPosicionesCellId.isAlive())
//				{
//					Logg.info("El proceso de envio GPS esta detenido, iniciando el proceso");
//
//					_asyncEnviarPosicionesCellId.start();
//				}
//				else
//				{
//					Logg.info("El proceso de envio esta corriendo");
//				}
//			}
			
		}
    }
    
    private void ReprogramaEnvio()
    {
            String FechaReseteoAnterior = configEnvio.FechaReseteo;
            configEnvio = new BOConfiguracionEnvio(contexto);
            
            if( (!HaciendoLlamada()) && ( Info.ServicioDatosActivos(context) ) )
            {
                configEnvio.TipoProceso = "WS";
                configEnvio.AgregarProceso("ConsultarConfiguracionEnvio2");
                configEnvio.AgregarParametro("wsNamesPace", "http://tempuri.org/");
                configEnvio.AgregarParametro("wsUrl", Repositorio.wsURL);
                configEnvio.AgregarParametro("WebMethod", "ConsultarConfiguracionEnvio2");
                configEnvio.AgregarParametro("IME", Info.getPIN(context));
                lsConfigEnvio = configEnvio.ConsultarProceso();
                
                if(!configEnvio.Error.equals("")) //marco error ver si no hay algo guardado en la bd
                {
                    configEnvio.TipoProceso = "SQL";
                    configEnvio.AgregarProceso("SQL.Consultar");
                    configEnvio.AgregarParametro("idSortField", String.valueOf(configEnvio.ID_IdConfiguracion));
                    configEnvio.AgregarParametro("idFilterField", String.valueOf(configEnvio.ID_IdConfiguracion));
                    configEnvio.AgregarParametro("filtro", "");
                    configEnvio.Error = "";
                    lsConfigEnvio = configEnvio.ConsultarProceso();
                    
                    Logg.info("SQL ConsultarConfiguracionEnvio2");
                }
                else
                {
                	Logg.info("WS ConsultarConfiguracionEnvio2");
                }
            }
            else
            {
                configEnvio.TipoProceso = "SQL";
                configEnvio.AgregarProceso("SQL.Consultar");
                configEnvio.AgregarParametro("idSortField", String.valueOf(configEnvio.ID_IdConfiguracion));
                configEnvio.AgregarParametro("idFilterField", String.valueOf(configEnvio.ID_IdConfiguracion));
                configEnvio.AgregarParametro("filtro", "");
                configEnvio.Error = "";
                lsConfigEnvio = configEnvio.ConsultarProceso();
                
                Logg.info("SQL ConsultarConfiguracionEnvio2");
            }
            
            //lsConfigEnvio = configEnvio.ConsultarProceso();
            
            if (lsConfigEnvio != null)
            {
                if(configEnvio.Error.equals(""))
                {
                    configEnvio = (BOConfiguracionEnvio) lsConfigEnvio.get(0);
                    
                    SegundosEnvio = (int)Math.floor( configEnvio.MinutoRetardo * 60 * 1000 );
                    
                    tiempoMaximoReseteoGps = (int)Math.floor( Integer.parseInt(configEnvio.MinutosReseteoGPS) * 60 * 1000 );
                    tiempoMaximoReseteoEquipo = (int)Math.floor( Integer.parseInt(configEnvio.MinutosReseteoDispositivo) * 60 * 1000 );
                    
                    if( configEnvio.EnviarTodo.equals("1") )
                    {
                        enviarTodo = true;
                    }
                    else
                    {
                        enviarTodo = false;
                    }
                    
                    configEnvio.ClearProceso();
                    configEnvio.ClearParametros();
                    configEnvio.TipoProceso = "SQL";
                    configEnvio.AgregarProceso("SQL.EliminarTodos");
                    configEnvio.EjecutarProceso();
                    
                    configEnvio.ClearProceso();
                    configEnvio.ClearParametros();
                    configEnvio.TipoProceso = "SQL";
                    configEnvio.AgregarProceso("SQL.Agregar");
                    configEnvio.EjecutarProceso();
                    
                    //verificamos si hay que reinciar
                    if(!configEnvio.FechaReseteo.equals(""))
                    {
                        if( ( !configEnvio.FechaReseteo.equals(FechaReseteoAnterior) ) && 
                            (!FechaReseteoAnterior.equals("")) )
                        {
                            try {
                                reset();
                            }
                            catch (Exception e) {}
                        }
                    }
                    
                    Logg.info("Hora Inicio: " + configEnvio.HoraInicio + ", Hora Fin: " + configEnvio.HoraFin);
                }
            }
    }
    
    public static void GuardaGPS()
    {
        if((longitude > 0 || longitude < 0) && (latitude > 0 || latitude < 0))
        {
            gps.ClearParametros();
            gps.ClearProceso();
            
            gps.Coordenadas = latitude + "," + longitude;
            
            gps.FechaHora = Utils.getFechaHoraActual();
            gps.Bateria = String.valueOf(Info.getBateriaNivel(context));
            gps.Velocidad = String.valueOf( Utils.round(velocidad * 3.6, 2) + " Km/Hr" );
            
            if(ContadorWifi > 5)
            {
                gps.Wifi = Info.GetIPAdress(context);
                ContadorWifi = 0;
            }
            else
            {
                gps.Wifi = "";
                ContadorWifi++;
            }
            
            if( ( !temporalmentefueraServicio ) && ( !fueraServicio ) ) // si esta habilitado
            {
                gps.TipoAviso = "Normal";
            }
            else if( temporalmentefueraServicio )
            {
                gps.TipoAviso = "Temporal";
            }
            else if( fueraServicio )
            {
                gps.TipoAviso = "Fuera";
            }
            
            gps.TipoProceso = "SQL";
            gps.AgregarProceso("SQL.Agregar");
            gps.EjecutarProceso();
        }
        else
        {
        	Logg.info("Error GPS Invalido");
        }
    }
    
    public  void reset()
    {
    	
    }
    
    private boolean HaciendoLlamada()
    {
        return false;
    }
    
    private void ResetPanico(boolean valor)
    {
        if(valor)
        {
            tipoAviso = "Panico";
            panico    = true;
        }
        else
        {
            tipoAviso = "Normal";
            panico    = false;
        }
    }
		
}

