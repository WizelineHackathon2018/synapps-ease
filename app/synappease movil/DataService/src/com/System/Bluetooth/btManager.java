package com.System.Bluetooth;

import com.System.Utils.Logg;

import android.bluetooth.BluetoothAdapter;

public class btManager 
{
	public static boolean setBluetooth(boolean enable) 
	{
		try
		{
		    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		    boolean isEnabled = bluetoothAdapter.isEnabled();
		    if (enable && !isEnabled)
		    {
		        return bluetoothAdapter.enable();
		    }
		    else if(!enable && isEnabled)
		    {
		        return bluetoothAdapter.disable();
		    }
		}
		catch(Exception ex)
		{
			Logg.error("Error al iniciar el bluetooth");
		}
	    return true;
	}
}
