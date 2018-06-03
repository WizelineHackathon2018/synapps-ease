package com.comynt.launa.frm;

import BO.BONota;
import BO.BONotaDetalle;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.System.Base.BaseFragmentActivity;
import com.System.Bluetooth.btManager;
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
import com.comynt.launa.adapt.adaptVerDatos;
import com.comynt.launa.async.AsyncImprimir;
import com.comynt.launa.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class frmVerLiquidacion extends BaseFragmentActivity implements kIButton, kIEditText
{
	Repositorio repositorio = Repositorio.getInstance();
	ListView list = null;

	kButton btnAtras = null;
	kButton btnAdelate = null;

    kButton btnEst = null;
    kButton btnGau = null;
    
    TextView lblTotalVentaContado = null;
    TextView lblTotalVentaCredito = null;
    TextView lblTotalCobro = null;
    TextView lblTotalGeneral = null;
	
	static adaptVerDatos adapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frmliquidacion);

		repositorio.IniciaVariables(this);
		
	    showman.AgregarElemento("Para imprimir la liquidación da clic en imprimir");
	    showman.AgregarElemento(R.id.btnAdelante);
		
		btManager.setBluetooth(true);

	    btnAtras = (kButton) findViewById(R.id.btnAtras);
	    btnAdelate = (kButton) findViewById(R.id.btnAdelante);

		btnEst = (kButton) findViewById(R.id.btnEst);
		btnGau = (kButton) findViewById(R.id.btnGau);
		
		this.repositorio.VerificaCreditoSaldoTodo();
		
		lblTotalVentaContado = (TextView) findViewById(R.id.lblTotalVentaContado);
		lblTotalVentaCredito = (TextView) findViewById(R.id.lblTotalVentaCredito);
		lblTotalCobro = (TextView) findViewById(R.id.lblTotalCobro);
		lblTotalGeneral = (TextView) findViewById(R.id.lblTotalGeneral);

		repositorio.ConsultarOperacionesDiarias("Venta");
		
		lblTotalVentaContado.setText( "Total Venta Contado: $" + Utils.toMoneyFormat((float)this.repositorio.VentaContado));
		lblTotalVentaCredito.setText( "Total Venta Credito: $" + Utils.toMoneyFormat((float)this.repositorio.VentaCredito));
		lblTotalCobro.setText(        "Total Cobro: $" + Utils.toMoneyFormat((float)this.repositorio.Cobro));
		lblTotalGeneral.setText(      "Total: $" + Utils.toMoneyFormat((float)this.repositorio.VentaContado + (float)this.repositorio.VentaCredito + (float)this.repositorio.Cobro));

		if(repositorio.lsVenta2 != null)
		{
			//Agregamos los items de la venta
			int contador = repositorio.lsVenta2.size();
			int indice = 0;
			
			for(int x = 0; x < contador; x++)
			{
	        	BONota Nota = null;
	        	Nota = (BONota)repositorio.lsVenta2.get(indice);

	            if(Nota.Discriminante.equals("Venta"))
	            {
	            	//Obtenemos el detalle y lo insertamos en las notas
	            	this.repositorio.ConsultarDetalleVentasDiaria(String.valueOf(Nota.GUIDV));
	            	
	            	for(int y = 0; y < repositorio.lsVentaDetalle.size(); y++)
	            	{
		            	this.repositorio.VentaDetalle = (BONotaDetalle)repositorio.lsVentaDetalle.get(y);
		            	repositorio.lsVenta2.add(indice + 1, this.repositorio.VentaDetalle);
		            	indice++;
	            	}
	            }
	            
	            indice++;
			}
			
			list = (ListView)findViewById(R.id.lsDatosCliente);

	        adapter = new adaptVerDatos(this, repositorio.lsVenta2, 1);
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
    		if(repositorio.lsVenta != null)
    		{
				btManager.setBluetooth(true);
				
				try
				{
					Thread.sleep(1000);
				}
				catch(Exception ex){}
    			
	    		Toast.makeText(v.getContext(), "Generando Ticket", Toast.LENGTH_LONG).show();

    			TicketCPCL ticket = new TicketCPCL();

    			AsyncImprimir oAsyncSincronizar = new AsyncImprimir(ticket.ObtenerTicketLiquidacion(), repositorio.ID_ImprimirLiquidacion);
    			Thread _threadAsyncSincronizar = new Thread(oAsyncSincronizar);
    			_threadAsyncSincronizar.start();
    		}
    		else
    		{
	    		Toast.makeText(v.getContext(), "No hay operaciones en el dia", Toast.LENGTH_LONG).show();
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
    		frmVerLiquidacion.this.adapter.getFilter().filter(cs);
    	}
    	else
    	{
    		if(cs.length() > 0)
    		{
    			frmVerLiquidacion.this.adapter.getFilter().filter(cs);
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
