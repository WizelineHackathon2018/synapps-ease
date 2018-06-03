package com.System.Dispositivo;

import java.math.BigInteger;
import java.net.*;
import java.nio.ByteOrder;
import java.util.Enumeration;

import com.System.Utils.Logg;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;

public class Info 
{
    //Si no esta activa la activa
    public static void ValidaWifi(Context context)
    {
		try
		{
			if(!conexionWifi2(context))
			{
				WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
			    wifiManager.setWifiEnabled(true);
			    
			    Logg.info("La conexion WIFI es OFF, se procede a poner en on");
			}
		}
		catch(Exception ex){}
    }
    
    public static boolean isGprsConnected(Context context) 
    {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
		NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean mobileConnected = mobileInfo.getState() == NetworkInfo.State.CONNECTED;

		return true;
    }
	
    public static boolean ServicioDatosActivos(Context context)
    {
        return conexionDatos(context) || conexionWifi(context);
    }
    
    public static boolean conexionDatos(Context context)
    {
    	ConnectivityManager cm =
    	        (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
    	
    	NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    	
    	boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    	return isConnected;
    }
    
    public static boolean conexionWifi2(Context context)
    {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return wifiInfo != null && wifiInfo.isConnected();
    }
    
    public static boolean conexionWifi(Context context)
    {
        if( GetIPAdress(context).equals("") )
            return false;
        else
            return true;
    }
    
    public static String GetIPAdress(Context context) 
    {
		WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);
        return ip;
    }
    
	/**
	*Format the integer IP address and return it as a String
	*@param i - IP address should be passed in as an Integer
	*/
	public static String intToIp(int i) {
       /* return ((i >> 24 ) & 0xFF ) + "." +
                    ((i >> 16 ) & 0xFF) + "." +
                    ((i >> 8 ) & 0xFF) + "." +
                    ( i & 0xFF) ;*/
        return (i & 0xFF) + "." +
        ((i >> 8 ) & 0xFF) + "." +
        ((i >> 16 ) & 0xFF) + "." +
        ((i >> 24 ) & 0xFF );
    }
    
    public static String getPIN(Context context)
    {
    	//return "356936058630695";

    	TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
    	return telephonyManager.getDeviceId();
    }
    
    public static String GetWifiName(Context context)
    {
        String ssid = "";
        
        try
        {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        	
        	ssid = wifiInfo.getSSID();
        }
        catch(Exception ex)
        {
        	ssid = "";
        }
        
        if(ssid == null)
        {
            return "";
        }
        else
        {
            return ssid;
        }
    }
    
    public static int getBateriaNivel(Context context)
    {
    	IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    	Intent batteryStatus = context.registerReceiver(null, ifilter);
    	
    	int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
    	
    	return level;
    	
    	//int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

    	//float batteryPct = level / (float)scale;
    	
//    	int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
//    	boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
//    	                     status == BatteryManager.BATTERY_STATUS_FULL;
//
//    	// How are we charging?
//    	int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
//    	boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
//    	boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
    }
	
}

