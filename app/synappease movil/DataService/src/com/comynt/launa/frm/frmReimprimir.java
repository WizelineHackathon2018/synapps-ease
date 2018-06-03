package com.comynt.launa.frm;

import BO.BOItinerario;
import BO.BONota;
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
import android.widget.ListView;
import android.widget.Toast;

import com.System.Base.BaseFragmentActivity;
import com.System.Bluetooth.btManager;
import com.System.Sonidos.Sounds;
import com.System.Ticket.TicketCPCL;
import com.System.UI.kButton;
import com.System.UI.kEditText;
import com.System.UI.kIButton;
import com.System.UI.kIEditText;
import com.System.UI.kIListView;
import com.System.UI.kListView;
import com.System.Utils.Logg;
import com.System.Utils.Utils;
import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;
import com.comynt.launa.Repositorio;
import com.comynt.launa.adapt.ViewPagerAdapter;
import com.comynt.launa.adapt.adaptVerDatos;
import com.comynt.launa.async.AsyncImprimir;
import com.comynt.launa.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class frmReimprimir extends BaseFragmentActivity implements kIButton, kIListView, kIEditText
{
	Repositorio repositorio = Repositorio.getInstance();
	ListView list = null;

	kButton btnAtras = null;
	kButton btnAdelate = null;

	kButton btnEst = null;
    kButton btnGau = null;
	
	static adaptVerDatos adapter = null;
	
	int selectedfila = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frmreimprimir);

		repositorio.IniciaVariables(this);
		
	    showman.AgregarElemento("Selecciona la operación");
	    showman.AgregarElemento(R.id.lsveractividades);

	    showman.AgregarElemento("da clic en imprimir");
	    showman.AgregarElemento(R.id.btnAdelante);
		
		btManager.setBluetooth(true);

	    btnAtras = (kButton) findViewById(R.id.btnAtras);
	    btnAdelate = (kButton) findViewById(R.id.btnAdelante);

		btnEst = (kButton) findViewById(R.id.btnEst);
		btnGau = (kButton) findViewById(R.id.btnGau);

		repositorio.ConsultarOperacionesDiarias("Venta");

		if(repositorio.lsVenta2 != null)
		{
			list = (ListView)findViewById(R.id.lsveractividades);

	        adapter = new adaptVerDatos(this, repositorio.lsVenta2, 1);
	        list.setAdapter(adapter);
	        //list.setSelector(R.drawable.gradient_bg_hover);

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
			if(selectedfila > -1)
			{
				//lO IMPRIME SI SELECCIONAR UN ITEM
	    		if(repositorio.lsVenta2 != null)
	    		{
					btManager.setBluetooth(true);
					
					try
					{
						Thread.sleep(1000);
					}
					catch(Exception ex){}
	    			
	    			//this.repositorio.Venta = ((BONota)this.repositorio.lsVenta.get(selectedfila));
					//repositorio.Itinerario = (BOItinerario)adapter.data.get(selectedfila);
	    			
	    			this.repositorio.Venta = ((BONota)adapter.data.get(selectedfila));
	    			
	    			if(this.repositorio.Venta.Discriminante.equals("Venta"))
	    			{
		    			this.repositorio.ConsultarDetalleVentasDiaria(this.repositorio.Venta.GUIDV);

		    			repositorio.EstableceParametrosImpresion();

		        		if(repositorio.lsVentaDetalle != null)
		        		{
		    	    		Toast.makeText(v.getContext(), "Generando Ticket", Toast.LENGTH_LONG).show();

		        			TicketCPCL ticket = new TicketCPCL();

		        			AsyncImprimir oAsyncSincronizar = new AsyncImprimir(ticket.ObtenerTicketReimprecion(), repositorio.ID_ReImprimirVenta);
		        			Thread _threadAsyncSincronizar = new Thread(oAsyncSincronizar);
		        			_threadAsyncSincronizar.start();
		        		}
		        		else
		        		{
		    	    		Toast.makeText(v.getContext(), "No hay detalle de la nota", Toast.LENGTH_LONG).show();
		        		}
	    			}
	    			else
	    			{
		    			repositorio.EstableceParametrosImpresion();

	    	    		Toast.makeText(v.getContext(), "Generando Ticket", Toast.LENGTH_LONG).show();

	        			TicketCPCL ticket = new TicketCPCL();

	        			AsyncImprimir oAsyncSincronizar = new AsyncImprimir(ticket.ObtenerTicketReimprecion(), repositorio.ID_ReImprimirVenta);
	        			Thread _threadAsyncSincronizar = new Thread(oAsyncSincronizar);
	        			_threadAsyncSincronizar.start();
	    			}
	    		}
	    		else
	    		{
		    		//Toast.makeText(v.getContext(), "No hay notas en el dia", Toast.LENGTH_LONG).show();
	    			
	    			showHelp(R.id.lsveractividades, "No hay notas en el dia");
	    		}
			}
			else
			{
				//Toast.makeText(v.getContext(), "Debe seleccionar una nota para reimprimir", Toast.LENGTH_LONG).show();
				
				showHelp(R.id.lsveractividades, "Debe seleccionar una nota para reimprimir");
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
	public void listItemAction(kListView l, AdapterView<?> parent, View view, int position, long id) 
	{
    	list.playSoundEffect(0);
		selectedfila = position;
		
		if(selectedfila > -1)
		{
    		if(repositorio.lsVenta2 != null)
    		{
    			this.repositorio.Venta = ((BONota)this.repositorio.lsVenta2.get(selectedfila));

    			Toast.makeText(view.getContext(), Utils.Split(this.repositorio.Venta.FechaCreacion, " ")[1] + " - " + this.repositorio.Venta.Cliente, Toast.LENGTH_SHORT).show();    			
    		}
		}
		
		Sounds.PlaySuccess(this, false);
	}

	@Override
	public void doubleclick_listItemAction(kListView l, AdapterView<?> parent, View view, int position, long id) 
	{
	}
	
	@Override
	public void onTextChangedEditText(kEditText e, CharSequence cs, int arg1, int arg2, int arg3)
	{
    	if(cs.toString().equals(""))
    	{
    		frmReimprimir.this.adapter.getFilter().filter(cs);
    	}
    	else
    	{
    		if(cs.length() > 0)
    		{
    			frmReimprimir.this.adapter.getFilter().filter(cs);
    		}
    	}
    	selectedfila = -1;
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
