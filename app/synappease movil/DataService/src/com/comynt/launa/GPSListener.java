package com.comynt.launa;

import android.location.LocationProvider;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.System.Utils.Utils;
import com.System.Utils.Logg;

public class GPSListener implements LocationListener
{
    private double longitude = 0;
    private double latitude = 0;
    private boolean fallo = false;
    public GPS gpsmain;
    
    public GPSListener()
    {
    }
    
	@Override
	public void onLocationChanged(Location location) 
	{
        if(location != null) // && location.isValid())
        {
            try {
                GPS.longitude   = location.getLongitude();
                GPS.latitude    = location.getLatitude();
                GPS.velocidad   = location.getSpeed();
                
                GPS.ultimoGPSValido = System.currentTimeMillis();//el ultimo gps valido
                GPS.ultimoTemporal = System.currentTimeMillis();//el ultimo temporal
                GPS.OpenCellIDRead = System.currentTimeMillis();//el ultimo OpenCellID
                
                if(GPS.enviarTodo)
                {
                    GPS.GuardaGPS();
                }
                
                GPSPoint.PosicionGPS = String.valueOf(GPS.latitude) + "," + String.valueOf(GPS.longitude);
                GPSPoint.Tiempo = System.currentTimeMillis();
                
                Logg.info( "GPS:" + String.valueOf(GPS.longitude) + 
                    "," + String.valueOf(GPS.latitude) + " Vel: " + String.valueOf(GPS.velocidad)  + " Fech:" + Utils.getFechaHoraActual());
            }
            catch(Exception e) {
                String Error = e.getMessage();
                Logg.error( Error + " " + Utils.getFechaHoraActual() );
            }
        }
        else {
            Logg.error( "posicion invalida" + " " + Utils.getFechaHoraActual());
        }
        
        GPS.RevisarProveedor(); //revisa el estado del proveedor para ver si esta disponible o no
        
//        if(provider.getState() == LocationProvider.AVAILABLE)
//        {
//            GPS.GpsEstado = LocationProvider.AVAILABLE;
//            GPS.fueraServicio = false;
//            GPS.temporalmentefueraServicio = false;
//        }
	}

	@Override
	public void onProviderDisabled(String provider) 
	{
		Logg.error("Proveedor deshabilitado");
		
        GPS.GpsEstado = LocationProvider.TEMPORARILY_UNAVAILABLE;
        GPS.temporalmentefueraServicio = true;
        GPS.fueraServicio = false;
        GPS.ultimoTemporal = System.currentTimeMillis();//el ultimo temporal
        GPS.ultimoTempo2 = System.currentTimeMillis();//el ultimo temporal
	}

	@Override
	public void onProviderEnabled(String provider) 
	{
        GPS.GpsEstado = LocationProvider.AVAILABLE;
        GPS.temporalmentefueraServicio = false;
        GPS.fueraServicio = false;
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) 
	{
        if(status == LocationProvider.AVAILABLE)
        {
            GPS.GpsEstado = LocationProvider.AVAILABLE;
            GPS.temporalmentefueraServicio = false;
            GPS.fueraServicio = false;
        }
        else if(status == LocationProvider.TEMPORARILY_UNAVAILABLE)
        {
            GPS.GpsEstado = LocationProvider.TEMPORARILY_UNAVAILABLE;
            GPS.temporalmentefueraServicio = true;
            GPS.fueraServicio = false;
            GPS.ultimoTemporal = System.currentTimeMillis();//el ultimo temporal
            GPS.ultimoTempo2 = System.currentTimeMillis();//el ultimo temporal
        }
        else if(status == LocationProvider.OUT_OF_SERVICE)
        {
            GPS.GpsEstado = LocationProvider.OUT_OF_SERVICE;
            GPS.temporalmentefueraServicio = false;
            GPS.fueraServicio = true;
        }
	}
    
}
