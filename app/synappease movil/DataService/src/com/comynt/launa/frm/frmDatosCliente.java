package com.comynt.launa.frm;

import BO.*;
import android.app.Activity;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.System.Base.BaseFragmentActivity;
import com.System.Dispositivo.Info;
import com.System.UI.kButton;
import com.System.UI.kEditText;
import com.System.UI.kIButton;
import com.System.UI.kIEditText;
import com.System.UI.kListView;
import com.System.Utils.Logg;
import com.System.Utils.Utils;
import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;
import com.comynt.launa.Repositorio;
import com.comynt.launa.adapt.ViewPagerAdapter;
import com.comynt.launa.adapt.adapAgenda;
import com.comynt.launa.adapt.adapImagenesFoto;
import com.comynt.launa.adapt.adaptCuestionario;
import com.comynt.launa.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class frmDatosCliente extends BaseFragmentActivity implements kIButton, kIEditText
{
	Repositorio repositorio = Repositorio.getInstance();
	ListView list = null;
	static adaptCuestionario adapter = null;

	kButton btnAtras = null;
	kButton btnAdelate = null;

	kButton btnEst = null;
	kButton btnGau = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frmdatoscliente);

		repositorio.IniciaVariables(this);
		
	    showman.AgregarElemento("Revisa los datos del cliente o bien captura los datos si están incompletos");
	    showman.AgregarElemento(R.id.lsDatosCliente);

	    showman.AgregarElemento("da clic en siguiente");
	    showman.AgregarElemento(R.id.btnAdelante);

		//Consultamos las preguntas del cuestionario
    	this.repositorio.ConsultarCuestionarioCliente(2, 4);
    	
    	//Consultamos las respuestas del cliente
    	this.repositorio.ConsultarInformacionCliente(2, 4);

//		if(repositorio.lsCuestionarioCliente != null)
//		{
			list = (ListView)findViewById(R.id.lsDatosCliente);

	        adapter = new adaptCuestionario(this, repositorio.lsCuestionarioCliente, 0, repositorio.lsInformacionCliente);
	        list.setAdapter(adapter);

			list.setClickable(false);
//		}

		    btnAtras = (kButton) findViewById(R.id.btnAtras);
		    btnAdelate = (kButton) findViewById(R.id.btnAdelante);

			btnEst = (kButton) findViewById(R.id.btnEst);
			btnGau = (kButton) findViewById(R.id.btnGau);

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
			for (int x = 0; x < adapter.UImanager.ControlsCount(); x++)
			{
				if (adapter.UImanager.getSelectedText(x).equals(""))
					continue;
				
				this.repositorio.CuestionarioCliente = (BOCuestionarioCliente) this.repositorio.lsCuestionarioCliente.get(x);
				
				//Agregamos la nueva informacion a la bd
				BONuevoInformacionCliente NuevoInformacionCliente = new BONuevoInformacionCliente(this);
				
				NuevoInformacionCliente.IdCliente = this.repositorio.Itinerario.IdCliente;
				NuevoInformacionCliente.Enviar = 1;
				NuevoInformacionCliente.IdCuestionarioCliente = this.repositorio.CuestionarioCliente.IdCuestionarioCliente;
				NuevoInformacionCliente.Respuesta = adapter.UImanager.getSelectedText(x);
				NuevoInformacionCliente.GUIDV = "";
				
				NuevoInformacionCliente.TipoProceso = "SQL";
				NuevoInformacionCliente.AgregarProceso("SQL.Agregar");
				NuevoInformacionCliente.EjecutarProceso();
				
				Logg.info("Se agrego nuevo dato: " + adapter.UImanager.getSelectedText(x));
				
				//si no existe local lo agregamos
				if( !existeRespuestaLocal(this.repositorio.CuestionarioCliente) )
				{
					BOInformacionCliente InformacionCliente = new BOInformacionCliente(this);
					
					InformacionCliente.IdCliente = this.repositorio.Itinerario.IdCliente;
					InformacionCliente.IdCuestionarioCliente = this.repositorio.CuestionarioCliente.IdCuestionarioCliente;
					InformacionCliente.Respuesta = adapter.UImanager.getSelectedText(x);
					
					InformacionCliente.TipoProceso = "SQL";
					InformacionCliente.AgregarProceso("SQL.Agregar");
					InformacionCliente.EjecutarProceso();
					
					Logg.info("Se agrego nuevo dato local: " + adapter.UImanager.getSelectedText(x));
				}
			}
			
			Intent sig = new Intent(frmDatosCliente.this, frmInformacionCliente.class);
			startActivityForResult(sig, 500);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			finish();
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
	
	private boolean existeRespuestaLocal(BOCuestionarioCliente CuestionarioCliente)
	{
    	if (repositorio.lsInformacionCliente != null)
    	{
    		for (int x = 0; x < repositorio.lsInformacionCliente.size(); x++)
    		{
    			BOInformacionCliente InformacionCliente = (BOInformacionCliente) repositorio.lsInformacionCliente.get(x);
    			
    			if (CuestionarioCliente.IdCuestionarioCliente == InformacionCliente.IdCuestionarioCliente)
    			{
    				return true;
    			}
    		}
    	}
    	
    	return false;
	}

	@Override
	public void onTextChangedEditText(kEditText e, CharSequence cs, int arg1,
			int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterTextChangedEditText(kEditText e, Editable s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChangedEditText(kEditText e, CharSequence s,
			int start, int count, int after) {
		// TODO Auto-generated method stub
		
	}
	
}
