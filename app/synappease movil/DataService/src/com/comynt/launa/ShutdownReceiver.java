package com.comynt.launa;

import com.System.Dispositivo.Info;
import com.System.Utils.Logg;
import com.System.Utils.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ShutdownReceiver extends BroadcastReceiver 
{
	Repositorio repositorio = Repositorio.getInstance();

    @Override
    public void onReceive(Context context, Intent intent)
    {
        //Agregamos el evento de apagado
		repositorio.GuardarEvento("Dispositivo Apagado", context);
    	
    	Toast.makeText(context, "Registro de evento: Dispositivo Apagado, HoraEvento: " + repositorio.Evento.FechaHora, Toast.LENGTH_LONG).show();
    	
    	Logg.info("Registro de evento: Dispositivo Apagado, HoraEvento: " + repositorio.Evento.FechaHora);
    }

}
