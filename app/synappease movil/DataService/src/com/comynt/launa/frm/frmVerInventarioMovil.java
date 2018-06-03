package com.comynt.launa.frm;

import java.util.List;

import BO.BO;
import BO.BOCheckVehiculo;
import BO.BOCheckVehiculoRealizado;
import BO.BOVehiculo;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.System.Base.BaseFragmentActivity;
import com.System.Bluetooth.btManager;
import com.System.Dispositivo.Info;
import com.System.Ticket.TicketCPCL;
import com.System.UI.kButton;
import com.System.UI.kEditText;
import com.System.UI.kIButton;
import com.System.UI.kIEditText;
import com.System.Utils.Logg;
import com.System.Utils.Utils;
import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;
import com.comynt.launa.Repositorio;
import com.comynt.launa.adapt.ViewPagerAdapter;
import com.comynt.launa.adapt.adapImagenesFoto;
import com.comynt.launa.adapt.adaptCuestionario;
import com.comynt.launa.adapt.adaptVerDatos;
import com.comynt.launa.async.AsyncImprimir;
import com.comynt.launa.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class frmVerInventarioMovil extends BaseFragmentActivity implements kIButton, kIEditText  
{
	Repositorio repositorio = Repositorio.getInstance();
	ListView list = null;

	kButton btnAtras = null;
	kButton btnAdelate = null;

    kButton btnEst = null;
    kButton btnGau = null;
	
	static adaptVerDatos adapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frmdatosventainventario);

		repositorio.IniciaVariables(this);
		
	    showman.AgregarElemento("Para imprimir el inventario da clic en imprimir");
	    showman.AgregarElemento(R.id.btnAdelante);
		
		btManager.setBluetooth(true);

	    btnAtras = (kButton) findViewById(R.id.btnAtras);
	    btnAdelate = (kButton) findViewById(R.id.btnAdelante);

		btnEst = (kButton) findViewById(R.id.btnEst);
		btnGau = (kButton) findViewById(R.id.btnGau);

		repositorio.ConsultarAlmacenMovil(repositorio.ID_SQL, repositorio.ID_GuardaMemoria, "", "", "");
		
		if(repositorio.lsAlmacenMovil != null)
		{
			list = (ListView)findViewById(R.id.lsDatosCliente);

	        adapter = new adaptVerDatos(this, repositorio.lsAlmacenMovil, 0);
	        list.setAdapter(adapter);

			list.setClickable(false);
		}
        
		CargarMenuLateral();
		CargarMenuLateral2();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.mnugeneral, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
	    // Handle presses on the action bar items
	    switch (item.getItemId()) 
	    {
	        case R.id.mnu_vermenu:
	        	showman.showHelp();
	            return true;

	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}	

	@Override
	public void commandAction(kButton b, View v) 
	{
		if(b == btnAdelate)
		{
			//lO IMPRIME
    		if(repositorio.lsAlmacenMovil != null)
    		{
	    		Toast.makeText(v.getContext(), "Generando Ticket", Toast.LENGTH_LONG).show();
	    		
				btManager.setBluetooth(true);
				
				try
				{
					Thread.sleep(1000);
				}
				catch(Exception ex){}

    			TicketCPCL ticket = new TicketCPCL();

    			AsyncImprimir oAsyncSincronizar = new AsyncImprimir(ticket.ObtenerTicketAlmacen(), repositorio.ID_ImprimirAlmacen);
    			Thread _threadAsyncSincronizar = new Thread(oAsyncSincronizar);
    			_threadAsyncSincronizar.start();
    		}
    		else
    		{
	    		Toast.makeText(v.getContext(), "No hay inventario cargado en el almacen", Toast.LENGTH_LONG).show();
    		}
		}
		else if(b == btnAtras)
		{
			finish();
			btManager.setBluetooth(false);
		}
		else if(b == btnEst)
		{
			menu.showMenu();
		}
		else if(b == btnGau)
		{
			menu2.showMenu();
		}
	}
	
	@Override
    public void onBackPressed() {
		btManager.setBluetooth(false);
        super.onBackPressed();
    }
	
	@Override
	public void onTextChangedEditText(kEditText e, CharSequence cs, int arg1, int arg2, int arg3)
	{
    	if(cs.toString().equals(""))
    	{
    		frmVerInventarioMovil.this.adapter.getFilter().filter(cs);
    	}
    	else
    	{
    		if(cs.length() > 0)
    		{
    			frmVerInventarioMovil.this.adapter.getFilter().filter(cs);
    		}
    	}
	}

	@Override
	public void afterTextChangedEditText(kEditText e, Editable s)
	{
	}

	@Override
	public void beforeTextChangedEditText(kEditText e, CharSequence s, int start, int count, int after)
	{
	}

}
