package com.comynt.launa.frm;

import BO.*;

import com.System.Base.BaseFragmentActivity;
import com.System.Bluetooth.btManager;
import com.System.Dispositivo.Info;
import com.System.Sonidos.Sounds;
import com.System.Ticket.TicketCPCL;
import com.System.UI.kButton;
import com.System.UI.kIButton;
import com.System.UI.kIImageButton;
import com.System.UI.kImageButton;
import com.System.UI.kListView;
import com.System.Utils.Logg;
import com.System.Utils.Utils;
import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;
import com.comynt.launa.*;
import com.comynt.launa.adapt.*;
import com.comynt.launa.async.AsyncImprimir;
import com.comynt.launa.async.AsyncLogin;
import com.comynt.launa.async.AsyncSincronizacionFinal;
import com.comynt.launa.async.AsyncSincronizar;
import com.comynt.launa.async.IAsyncEvent;
import com.comynt.launa.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;

import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

public class frmMenu extends BaseFragmentActivity implements IAsyncEvent, kIButton
{
	Repositorio repositorio = Repositorio.getInstance();
	public IAsyncEvent Evento = null;
	
    Button btnGau = null;
    Button btnEst = null;
    Button btnVoz = null;
	
	ArrayList<HashMap<String, String>> datosLista = new ArrayList<HashMap<String, String>>();
	ListView list = null;
	adapMenu adapter = null;
	
	ListView lslmInventarioVenta = null;
	static adaptInventario lmadapterInventario = null;
	
    public static final String KEY_OPCION = "OPCION";
    private ProgressDialog dialog;
    
	private boolean gps_enabled = false;
	private LocationManager lm;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.frmmenu);
		
		repositorio.IniciaVariables(this);
		Evento = this;
		
	    showman.AgregarElemento("Da click sobre una opción para comenzar");
	    showman.AgregarElemento(R.id.lvOpciones);
		
		btManager.setBluetooth(false);
		
		repositorio.EnviarDatos();
		
		btnVoz = (Button) findViewById(R.id.btnVoz);
		btnGau = (Button) findViewById(R.id.btnGau);
		btnEst = (Button) findViewById(R.id.btnEst);
		
		HashMap<String,String> item1 = new HashMap<String,String>();
		item1.put(KEY_OPCION, "1) Sincronización de inicio");
		datosLista.add(item1);
		
		HashMap<String,String> item2 = new HashMap<String,String>();
		item2.put(KEY_OPCION, "2) CheckList vehiculo");
		datosLista.add(item2);
		
		HashMap<String,String> item3 = new HashMap<String,String>();
		item3.put(KEY_OPCION, "3) Realizar Venta con almacen");
		datosLista.add(item3);
		
		HashMap<String,String> item4 = new HashMap<String,String>();
		item4.put(KEY_OPCION, "4) Reimprimir");
		datosLista.add(item4);
		
		HashMap<String,String> item5 = new HashMap<String,String>();
		item5.put(KEY_OPCION, "5) Ver e Imprimir inventario");
		datosLista.add(item5);
		
		HashMap<String,String> item6 = new HashMap<String,String>();
		item6.put(KEY_OPCION, "6) Ver e Imprimir liquidación");
		datosLista.add(item6);
		
		HashMap<String,String> item7 = new HashMap<String,String>();
		item7.put(KEY_OPCION, "7) Sincronización de fin");
		datosLista.add(item7);
		
		list = (ListView)findViewById(R.id.lvOpciones);
		
        adapter = new adapMenu(this, datosLista);
        list.setAdapter(adapter);
        
		list.setClickable(false);
		
		list.setOnItemClickListener(new OnItemClickListener()
		{
		    @Override
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		    {
		    	list.playSoundEffect(0);
		    	
		    	if(position == 0) //sincronizar - OK
		    	{
		    		Info.ValidaWifi(view.getContext());
		    		
					if (!getGPSenable())
					{
						repositorio.GuardarEvento("GPS Desactivado", frmMenu.this.getBaseContext());

						Toast.makeText(frmMenu.this.getBaseContext(), "Evento registrado, GPS Desactivado, Hora: " + repositorio.Evento.FechaHora, Toast.LENGTH_SHORT).show();
					}
		    		
		    		if (Info.ServicioDatosActivos(frmMenu.this.getBaseContext()))
		    		{
			    		if(!repositorio.estaSincronizando)
			    		{
			    			dialog = new ProgressDialog(view.getContext());
			    			dialog.setMessage("Por favor espere sincronizando!!");
			                dialog.setCancelable(false);
			                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			                dialog.show();		    			
			    			
				    		Toast.makeText(view.getContext(), "Sincronizando.. ", Toast.LENGTH_LONG).show();

				    		AsyncSincronizar oAsyncSincronizar = new AsyncSincronizar(frmMenu.this.getBaseContext());
							oAsyncSincronizar.evento = frmMenu.this.Evento;
							Thread _threadAsyncSincronizar = new Thread(oAsyncSincronizar);
							_threadAsyncSincronizar.start();
			    		}
			    		else
			    		{
			    			if(dialog == null)
			    			{
				    			dialog = new ProgressDialog(view.getContext());
				    			dialog.setMessage("Por favor espere sincronizando!!");
				                dialog.setCancelable(false);
				                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				                dialog.show();		    			
			    			}
			    			
				    		Toast.makeText(view.getContext(), "Sincronizando.. ", Toast.LENGTH_LONG).show();
			    		}
		    		}
		    		else
		    		{
		    			Toast.makeText(view.getContext(), "Por favor revise estar conectado a la red celualar o wifi para efectuar la operación", Toast.LENGTH_LONG).show();
		    		}
		    		
		    	}
		    	else if(position == 1) //checklist - OK
		    	{
		    		if(repositorio.estaSincronizando)
		    		{
		    			if(dialog == null)
		    			{
			    			dialog = new ProgressDialog(view.getContext());
			    			dialog.setMessage("Por favor espere sincronizando!!");
			                dialog.setCancelable(false);
			                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			                dialog.show();		    			
		    			}
		    			
			    		Toast.makeText(view.getContext(), "Sincronizando.. ", Toast.LENGTH_LONG).show();
		    		}
		    		else
		    		{
		    			if(!Info.isGprsConnected(view.getContext()))
		    			{
		    				repositorio.GuardarEvento("Datos Desactivados", view.getContext());
		    				Toast.makeText(view.getContext(), "Los datos están inactivos, se recomienda que los active, Evento registrado, Datos Desactivados, Hora: " + 
		    						repositorio.Evento.FechaHora, Toast.LENGTH_SHORT).show();
		    			}
		    			
						if (!getGPSenable())
						{
							repositorio.GuardarEvento("GPS Desactivado", frmMenu.this.getBaseContext());

							Toast.makeText(frmMenu.this.getBaseContext(), "Evento registrado, GPS Desactivado, Hora: " + repositorio.Evento.FechaHora, Toast.LENGTH_SHORT).show();
						}
		    			
			    		repositorio.estaVendiendo = false;
						Intent sig = new Intent(frmMenu.this, frmCheckList.class);
						startActivityForResult(sig, 500);
						overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
		    		}
		    		
		    	}
		    	else if(position == 2) //agenda - OK
		    	{
		    		if(repositorio.estaSincronizando)
		    		{
		    			if(dialog == null)
		    			{
			    			dialog = new ProgressDialog(view.getContext());
			    			dialog.setMessage("Por favor espere sincronizando!!");
			                dialog.setCancelable(false);
			                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			                dialog.show();		    			
		    			}
		    			
			    		Toast.makeText(view.getContext(), "Sincronizando.. ", Toast.LENGTH_LONG).show();
		    		}
		    		else
		    		{
		    			if(!Info.isGprsConnected(view.getContext()))
		    			{
		    				repositorio.GuardarEvento("Datos Desactivados", view.getContext());
		    				Toast.makeText(view.getContext(), "Los datos están inactivos, se recomienda que los active, Evento registrado, Datos Desactivados, Hora: " + 
		    						repositorio.Evento.FechaHora, Toast.LENGTH_SHORT).show();
		    			}
		    			
						if (!getGPSenable())
						{
							repositorio.GuardarEvento("GPS Desactivado", frmMenu.this.getBaseContext());

							Toast.makeText(frmMenu.this.getBaseContext(), "Evento registrado, GPS Desactivado, Hora: " + repositorio.Evento.FechaHora, Toast.LENGTH_SHORT).show();
						}
		    			
						Intent sig = new Intent(frmMenu.this, frmActividades.class);
						startActivityForResult(sig, 500);
						overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
		    		}
		    		
		    	}
		    	else if(position == 3) //reimprimir las ventas
		    	{
		    		if(repositorio.estaSincronizando)
		    		{
		    			if(dialog == null)
		    			{
			    			dialog = new ProgressDialog(view.getContext());
			    			dialog.setMessage("Por favor espere sincronizando!!");
			                dialog.setCancelable(false);
			                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			                dialog.show();		    			
		    			}
		    			
			    		Toast.makeText(view.getContext(), "Sincronizando.. ", Toast.LENGTH_LONG).show();
		    		}
		    		else
		    		{
		    			if(!Info.isGprsConnected(view.getContext()))
		    			{
		    				repositorio.GuardarEvento("Datos Desactivados", view.getContext());
		    				Toast.makeText(view.getContext(), "Los datos están inactivos, se recomienda que los active, Evento registrado, Datos Desactivados, Hora: " + 
		    						repositorio.Evento.FechaHora, Toast.LENGTH_SHORT).show();
		    			}
		    			
						if (!getGPSenable())
						{
							repositorio.GuardarEvento("GPS Desactivado", frmMenu.this.getBaseContext());

							Toast.makeText(frmMenu.this.getBaseContext(), "Evento registrado, GPS Desactivado, Hora: " + repositorio.Evento.FechaHora, Toast.LENGTH_SHORT).show();
						}
		    			
			    		repositorio.estaVendiendo = false;
						Intent sig = new Intent(frmMenu.this, frmReimprimir.class);
						startActivityForResult(sig, 500);
						overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
		    		}
		    		
		    	}
		    	else if(position == 4) //imprimir almacen
		    	{
		    		if(repositorio.estaSincronizando)
		    		{
		    			if(dialog == null)
		    			{
			    			dialog = new ProgressDialog(view.getContext());
			    			dialog.setMessage("Por favor espere sincronizando!!");
			                dialog.setCancelable(false);
			                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			                dialog.show();		    			
		    			}
		    			
			    		Toast.makeText(view.getContext(), "Sincronizando.. ", Toast.LENGTH_LONG).show();
		    		}
		    		else
		    		{
		    			if(!Info.isGprsConnected(view.getContext()))
		    			{
		    				repositorio.GuardarEvento("Datos Desactivados", view.getContext());
		    				Toast.makeText(view.getContext(), "Los datos están inactivos, se recomienda que los active, Evento registrado, Datos Desactivados, Hora: " + 
		    						repositorio.Evento.FechaHora, Toast.LENGTH_SHORT).show();
		    			}
		    			
						if (!getGPSenable())
						{
							repositorio.GuardarEvento("GPS Desactivado", frmMenu.this.getBaseContext());

							Toast.makeText(frmMenu.this.getBaseContext(), "Evento registrado, GPS Desactivado, Hora: " + repositorio.Evento.FechaHora, Toast.LENGTH_SHORT).show();
						}
		    			
			    		repositorio.estaVendiendo = false;
						Intent sig = new Intent(frmMenu.this, frmVerInventarioMovil.class);
						startActivityForResult(sig, 500);
						overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
		    		}
		    		
		    	}
		    	else if(position == 5) //IMPRIMIR LIQUIDACION
		    	{
		    		if(repositorio.estaSincronizando)
		    		{
		    			if(dialog == null)
		    			{
			    			dialog = new ProgressDialog(view.getContext());
			    			dialog.setMessage("Por favor espere sincronizando!!");
			                dialog.setCancelable(false);
			                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			                dialog.show();		    			
		    			}
		    			
			    		Toast.makeText(view.getContext(), "Sincronizando.. ", Toast.LENGTH_LONG).show();
		    		}
		    		else
		    		{
		    			if(!Info.isGprsConnected(view.getContext()))
		    			{
		    				repositorio.GuardarEvento("Datos Desactivados", view.getContext());
		    				Toast.makeText(view.getContext(), "Los datos están inactivos, se recomienda que los active, Evento registrado, Datos Desactivados, Hora: " + 
		    						repositorio.Evento.FechaHora, Toast.LENGTH_SHORT).show();
		    			}
		    			
						if (!getGPSenable())
						{
							repositorio.GuardarEvento("GPS Desactivado", frmMenu.this.getBaseContext());

							Toast.makeText(frmMenu.this.getBaseContext(), "Evento registrado, GPS Desactivado, Hora: " + repositorio.Evento.FechaHora, Toast.LENGTH_SHORT).show();
						}
		    			
			    		repositorio.estaVendiendo = false;
						Intent sig = new Intent(frmMenu.this, frmVerLiquidacion.class);
						startActivityForResult(sig, 500);
						overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
		    		}
		    		
		    	}
		    	else if(position == 6) //sincronizacion de fin
		    	{
		    		Info.ValidaWifi(view.getContext());
		    		
		    		if (Info.ServicioDatosActivos(frmMenu.this.getBaseContext()))
		    		{
			    		if(!repositorio.estaSincronizando)
			    		{
			    			dialog = new ProgressDialog(view.getContext());
			                dialog.setMessage("Por favor espere sincronizando!!");
			                dialog.setCancelable(false);
			                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			                dialog.show();		    			
			    			
				    		Toast.makeText(view.getContext(), "Sincronizando.. ", Toast.LENGTH_LONG).show();

				    		repositorio.estaSincronizandoAutomatico = true;
				    		AsyncSincronizacionFinal oAsyncSincronizar = new AsyncSincronizacionFinal(frmMenu.this.getBaseContext());
							oAsyncSincronizar.evento = frmMenu.this.Evento;
							Thread _threadAsyncSincronizar = new Thread(oAsyncSincronizar);
							_threadAsyncSincronizar.start();
			    		}
			    		else
			    		{
			    			if(dialog == null)
			    			{
				    			dialog = new ProgressDialog(view.getContext());
				    			dialog.setMessage("Por favor espere sincronizando!!");
				                dialog.setCancelable(false);
				                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				                dialog.show();		    			
			    			}
			    			
				    		Toast.makeText(view.getContext(), "Sincronizando.. ", Toast.LENGTH_LONG).show();
			    		}
		    		}
		    		else
		    		{
		    			Toast.makeText(view.getContext(), "Por favor revise estar conectado a la red celualar o wifi para efectuar la operación", Toast.LENGTH_LONG).show();
		    		}
		    	}
		    }
		});
		
		if((repositorio.estaSincronizando) || (repositorio.estaSincronizandoAutomatico))
		{
			if(dialog == null)
			{
    			dialog = new ProgressDialog(this);
    			dialog.setMessage("Por favor espere sincronizando!!");
                dialog.setCancelable(false);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();
			}
			
    		Toast.makeText(this, "Sincronizando.. ", Toast.LENGTH_LONG).show();
		}
		
		this.repositorio.ConsultarAlmacenMovil2();
		this.repositorio.ConsultarOperacionesDiarias("Venta");
		
		CargarMenuLateral();
		CargarMenuLateral2();
	}
    
	public boolean getGPSenable()
	{
		try
		{
			gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
			return gps_enabled;
		}
		catch(Exception ex)
		{
			repositorio.CapturarError(ex, "frmMenu");
			return true;
		}
	}    
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
		if(b == btnVoz)
		{
			finish();
		}
		if(b == btnGau)
		{
			menu2.showMenu();
		}
		else if(b == btnEst)
		{
			menu.showMenu();
		}
	}
	
	@Override
	public void Event(String event, String args)
	{
		this.runOnUiThread(new Runnable() {
			  public void run() {
				  Repositorio repositorio = Repositorio.getInstance();
				  
					repositorio.estaSincronizando = false;
					repositorio.estaSincronizandoAutomatico = false;

					Toast.makeText(frmMenu.this.getBaseContext(), 
						"Se ha sincronizado la información con exito", Toast.LENGTH_LONG).show();

					try
					{
						if(dialog != null)
						{
							dialog.dismiss();
						}
					}
					catch(Exception ex){}
					
					try
					{
						dialog.dismiss();
					}
					catch(Exception ex){}
					
					Sounds.PlaySuccess(repositorio.context, false);
			  }
		});
	}

}
