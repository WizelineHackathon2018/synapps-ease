package com.comynt.launa.frm;

import BO.*;
import android.net.Uri;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface.*;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.System.Base.BaseFragmentActivity;
import com.System.Bluetooth.btManager;
import com.System.Dispositivo.Info;
import com.System.Sonidos.Sounds;
import com.System.UI.kEditText;
import com.System.UI.kIEditText;
import com.System.UI.kButton;
import com.System.UI.kIButton;
import com.System.UI.kIListView;
import com.System.UI.kIMessage;
import com.System.UI.kListView;
import com.System.UI.kMessage;
import com.System.Utils.Logg;
import com.System.Utils.Utils;
import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;
import com.comynt.launa.Consumer;
import com.comynt.launa.Repositorio;
import com.comynt.launa.adapt.ViewPagerAdapter;
import com.comynt.launa.adapt.adapAgenda;
import com.comynt.launa.adapt.adapImagenesFoto;
import com.comynt.launa.adapt.adaptInventario;
import com.comynt.launa.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;

public class frmActividades extends BaseFragmentActivity implements kIButton, kIListView, kIEditText
{
	Repositorio repositorio = Repositorio.getInstance();
	
	long tiempoanterior = 0;
	int positionanterior = -2;
	Date fechaactual;
	
	int fila = -1;
	int selectedfila = -1;
	
	kEditText txtBuscar = null;
	
	kButton btnAdelate = null;
	kButton btnAtras = null;
	kButton btnEst = null;
	kButton btnGau = null;
	
	kListView list = null;
	static adapAgenda adapter = null;
	
	static boolean agregar = false;
	static boolean horainicial = false;
	
	ListView lslmInventarioVenta = null;
	static adaptInventario lmadapterInventario = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frmagenda);
		
		repositorio.IniciaVariables(this);
		
		showman.AgregarElemento("Da clic sobre un cliente");
		showman.AgregarElemento(R.id.lsveractividades);
		
		showman.AgregarElemento("Da clic en siguiente");
		showman.AgregarElemento(R.id.btnAdelante);
		
		btnAtras = (kButton) findViewById(R.id.btnAtras);
		btnAdelate = (kButton) findViewById(R.id.btnAdelante);
		
		btnEst = (kButton) findViewById(R.id.btnEst);
		btnGau = (kButton) findViewById(R.id.btnGau);
	    
        if (this.repositorio.lsCliente == null)
        {
        	this.repositorio.ConsultarClientes(2, 4, String.valueOf(this.repositorio.Vendedor.IdEmpresa), "0", "0");
        }

		this.repositorio.lsItinerario = new ArrayList<BO>();
		
		if(repositorio.lsCliente != null)
		{
			for (int x = 0; x < this.repositorio.lsCliente.size(); x++)
			{
				String fechaDia = Utils.getFechaActual_DD_MM_YYYY();
				this.repositorio.Cliente = ((BOCliente)this.repositorio.lsCliente.get(x));
				
				if(this.repositorio.Cliente.RequiereAutorizacion == 1)
				{
					this.repositorio.Cliente.Discriminante = "CREDITO";
				}
				else
				{
					this.repositorio.Cliente.Discriminante = "CONTADO";
				}
				
				this.repositorio.lsCliente.set(x, this.repositorio.Cliente);

				BOItinerario itienarioItem = new BOItinerario(this);
				itienarioItem.IdItinerario = 0;
				itienarioItem.IdCliente = this.repositorio.Cliente.IdCliente;
				itienarioItem.Cliente = this.repositorio.Cliente.Nombre;
				itienarioItem.Direccion = this.repositorio.Cliente.Direccion;
				itienarioItem.Visita = this.repositorio.Cliente.Visita;
				itienarioItem.PosicionGPS = this.repositorio.Cliente.PosicionGPS;
				itienarioItem.Codigo = this.repositorio.Cliente.Codigo;
				itienarioItem.SaldoPendiente = this.repositorio.Cliente.SaldoPendiente;
				itienarioItem.RequiereAutorizacion = this.repositorio.Cliente.RequiereAutorizacion;

				this.repositorio.lsItinerario.add(itienarioItem);
			}
		}
		
		list = (kListView)findViewById(R.id.lsveractividades);

        adapter = new adapAgenda(this, repositorio.lsItinerario);
        list.setAdapter(adapter);

		list.setClickable(false);
		
		txtBuscar = (kEditText) findViewById(R.id.txtBuscar);
		
		this.repositorio.ConsultarAlmacenMovil2();
		this.repositorio.ConsultarOperacionesDiarias("Venta");
		
		CargarMenuLateral();
		CargarMenuLateral2();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.mnuventa, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
	    // Handle presses on the action bar items
	    switch (item.getItemId()) 
	    {
        case R.id.mnu_navegar:
        	NavegarGPSCliente(frmActividades.this.getBaseContext());
            return true;
            
        case R.id.mnu_vermenu:
			showman.showHelp();
            return true;

	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		this.adapter.editBO(repositorio.Itinerario, selectedfila);
		
		list.setClickable(false);
		selectedfila = -1;
		this.setTitle("AV - ITINERARIO");
		repositorio.estaVendiendo = false;
		
		btManager.setBluetooth(false);
	}
	
	@Override
	public void commandAction(kButton b, View v)
	{
		if(b == btnAdelate)
		{
			if(repositorio.lsItinerario != null)
			{
				if(selectedfila > -1)
				{
					repositorio.Itinerario = (BOItinerario)adapter.data.get(selectedfila);
					
					BOCliente itienarioItem = new BOCliente(this);
					itienarioItem.IdCliente = this.repositorio.Itinerario.IdCliente;
					itienarioItem.Nombre = this.repositorio.Itinerario.Cliente;
					itienarioItem.Direccion = this.repositorio.Itinerario.Direccion;
					itienarioItem.Visita = this.repositorio.Itinerario.Visita;
					itienarioItem.PosicionGPS = this.repositorio.Itinerario.PosicionGPS;
					itienarioItem.Codigo = this.repositorio.Itinerario.Codigo;
					itienarioItem.SaldoPendiente = this.repositorio.Itinerario.SaldoPendiente;
					itienarioItem.Discriminante = "EFECTIVO";
					itienarioItem.RequiereAutorizacion = this.repositorio.Itinerario.RequiereAutorizacion;
					
					repositorio.Cliente = itienarioItem;
					
					repositorio.Cliente.Discriminante = "EFECTIVO";
					
					this.repositorio.IndiceCliente = selectedfila;
					repositorio.Tipo= "CLIENTE";

					if(repositorio.Itinerario.PosicionGPS.equals(""))  //Si no tiene gps pide el gps
					{
						Intent sig = new Intent(frmActividades.this, frmGeolocalizaCliente.class);
						startActivityForResult(sig, 500);
						overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
					}
					else  //si tiene, muestra la informacion general del cliente
					{
						Intent sig = new Intent(frmActividades.this, frmDatosCliente.class);
						startActivityForResult(sig, 500);
						overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
					}
				}
				else
				{
					showHelp(R.id.lsveractividades, "Debe seleccionar un cliente para continuar");
				}
			}
			else
			{
				showHelp(R.id.lsveractividades, "Debe seleccionar un cliente para continuar");
			}
		}
		else if(b == btnAtras)
		{
			finish();
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
	public void listItemAction(kListView l, AdapterView<?> parent, View view, int position, long id) 
	{
    	list.playSoundEffect(0);
		selectedfila = position;
		
		repositorio.Itinerario = (BOItinerario)adapter.data.get(selectedfila);
		
		Toast.makeText(view.getContext(), repositorio.Itinerario.Cliente, Toast.LENGTH_SHORT).show();
		
		Sounds.PlaySuccess(this, false);
		
		this.setTitle("AV - " + repositorio.Itinerario.Cliente);
	}

	@Override
	public void doubleclick_listItemAction(kListView l, AdapterView<?> parent, View view, int position, long id) 
	{
		repositorio.Itinerario = (BOItinerario)repositorio.lsItinerario.get(position);
		NavegarGPSCliente(view.getContext());
	}

	@Override
	public void onTextChangedEditText(kEditText e, CharSequence cs, int arg1, int arg2, int arg3)
	{
    	if(cs.toString().equals(""))
    	{
			frmActividades.this.adapter.getFilter().filter(cs);
    	}
    	else
    	{
    		if(cs.length() > 0)
    		{
				frmActividades.this.adapter.getFilter().filter(cs);
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
	
	//intenta habrir wazeo bien podria ser google maps si tit gps
	private void NavegarGPSCliente(Context context) 
	{
		if(repositorio.lsItinerario != null)
		{
			if(selectedfila > -1)
			{
	    		repositorio.Itinerario = (BOItinerario)repositorio.lsItinerario.get(selectedfila);
				
	    		if(repositorio.Itinerario.PosicionGPS.equals(""))
	    		{
	        		Toast.makeText(context, "No tiene posicion GPS", Toast.LENGTH_LONG).show();
	    		}
	    		else
	    		{
	        		try
	        		{
	        		   String url = "waze://?ll=" + repositorio.Itinerario.PosicionGPS + "&z=10";

	        		   Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( url ) );
	        		   startActivity( intent );

	        		   Logg.info("waze://?ll=" + repositorio.Itinerario.PosicionGPS + "&z=10");
	        		}
	        		catch ( ActivityNotFoundException ex  )
	        		{
	        			Logg.info("Error al cargar el waze");

	    	    		try
	    	    		{
	    	    			String url = "geo:0,0?q=" + repositorio.Itinerario.PosicionGPS + "(" + repositorio.Itinerario.Cliente + ")";
	    	    			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse( url ) );
	    	    			startActivity(intent);

	    	    			Logg.info("geo:0,0?q=" + repositorio.Itinerario.PosicionGPS + "(" + repositorio.Itinerario.Cliente + ")");
	    	    		}
	    	    		catch ( ActivityNotFoundException ex2  )
	    	    		{
	    	    			Logg.info("Error al cargar google maps");
	    	    		}
	        		}
	    		}
			}
			else
			{
				showHelp(R.id.lsveractividades, "Debe seleccionar un cliente para continuar");
			}
		}
		else
		{
			showHelp(R.id.lsveractividades, "Debe seleccionar un cliente para continuar");
		}
	}
	
}

