package com.comynt.launa.frm;

import BO.*;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.System.Base.BaseFragmentActivity;
import com.System.Dispositivo.Info;
import com.System.Images.imageManager;
import com.System.UI.kButton;
import com.System.UI.kEditText;
import com.System.UI.kIButton;
import com.System.UI.kIEditText;
import com.System.UI.kIMessage;
import com.System.Utils.Logg;
import com.System.Utils.Utils;

import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;
import com.comynt.launa.Consumer;
import com.comynt.launa.Repositorio;
import com.comynt.launa.adapt.ViewPagerAdapter;
import com.comynt.launa.adapt.adapImagenesFoto;
import com.comynt.launa.adapt.adaptIncidencia;
import com.comynt.launa.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import android.text.Editable;
import android.text.InputFilter;

public class frmMotivoNoCompra extends BaseFragmentActivity implements kIButton, kIEditText
{
	Repositorio repositorio = Repositorio.getInstance();

	kButton btnAtras = null;
	kButton btnAdelate = null;

	kButton btnEst = null;
    kButton btnGau = null;

	ListView lsdmIncidenciaStericycle = null;
	static adaptIncidencia lmadaptIncidenciaStericycle = null;

	private static final int ACTION_TAKE_PHOTO_B = 1;
	private String mCurrentPhotoPath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frmmotivonocompra);

		repositorio.IniciaVariables(this);

	    showman.AgregarElemento("Selecciona un motivo de no compra");
	    showman.AgregarElemento(R.id.lsdmIncidenciaStericycle);

	    showman.AgregarElemento("da clic en terminar");
	    showman.AgregarElemento(R.id.btnAdelante);
	    
		btnAtras = (kButton) findViewById(R.id.btnAtras);
		btnAdelate = (kButton) findViewById(R.id.btnAdelante);
		
		btnEst = (kButton) findViewById(R.id.btnEst);
		btnGau = (kButton) findViewById(R.id.btnGau);

		repositorio.ConsultarPrecuestionario(repositorio.ID_SQL, repositorio.ID_GuardaMemoria);

		if(repositorio.lsPrecuestionario != null)
		{
			String valores = "";

			for(int x = 0; x < repositorio.lsPrecuestionario.size(); x++)
			{
				repositorio.Precuestionario = (BOPrecuestionario)repositorio.lsPrecuestionario.get(x);

				if(x== 0)
				{
					valores = repositorio.Precuestionario.Pregunta;					
				}
				else
				{
					valores = valores + "/" + repositorio.Precuestionario.Pregunta;
				}

			}

			BOCuestionarioCliente CuestionarioCliente = new BOCuestionarioCliente(this);
			CuestionarioCliente.Pregunta = this.repositorio.Itinerario.Cliente;
			CuestionarioCliente.Respuesta = valores; 

			List<BO> lsCuestionarioCliente = new ArrayList<BO>();
			lsCuestionarioCliente.add(CuestionarioCliente);

			lsdmIncidenciaStericycle = (ListView)findViewById(R.id.lsdmIncidenciaStericycle);

			lmadaptIncidenciaStericycle = new adaptIncidencia(this, lsCuestionarioCliente);
			lsdmIncidenciaStericycle.setAdapter(lmadaptIncidenciaStericycle);

			lsdmIncidenciaStericycle.setClickable(false);
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
	public void commandAction(kButton b, View v) {
		if(b == btnAdelate)
		{
			this.repositorio.ObtenerHoraFin();
			this.repositorio.ObtenerDiferenciaHora();

			if((this.repositorio.lsPrecuestionario != null) && (this.repositorio.lsPrecuestionario.size() > 0))
			{
					if(lmadaptIncidenciaStericycle.UImanager.getSelecteIndex(0) > -1)
					{
						this.repositorio.Precuestionario = 
								(BOPrecuestionario)this.repositorio.lsPrecuestionario.get(lmadaptIncidenciaStericycle.UImanager.getSelecteIndex(0));

						this.repositorio.PrecuestionarioCliente.ClearProceso();
						this.repositorio.PrecuestionarioCliente.ClearParametros();

						this.repositorio.PrecuestionarioCliente.IdCliente = this.repositorio.Itinerario.IdCliente;
						this.repositorio.PrecuestionarioCliente.IdVendedor = this.repositorio.Vendedor.IdVendedor;
						this.repositorio.PrecuestionarioCliente.Pregunta = this.repositorio.Precuestionario.Pregunta;

//						this.repositorio.PrecuestionarioCliente.Observaciones = this.txtObservaciones.getText().toString();
//						this.repositorio.PrecuestionarioCliente.IdPreguntaInicio = this.repositorio.Precuestionario.IdPreguntaInicio;
//
//						this.repositorio.PrecuestionarioCliente.HoraInicio = this.repositorio.HoraInicio;
//						this.repositorio.PrecuestionarioCliente.HoraFin = this.repositorio.HoraFin;
//
//						this.repositorio.PrecuestionarioCliente.FechaHoraInicio = this.repositorio.FechaHoraInicio;
//						this.repositorio.PrecuestionarioCliente.FechaHoraFin = this.repositorio.FechaHoraFin;

						this.repositorio.PrecuestionarioCliente.TipoProceso = "SQL";
						this.repositorio.PrecuestionarioCliente.AgregarProceso("SQL.Agregar");
						this.repositorio.PrecuestionarioCliente.EjecutarProceso();

						String fechaVisitaDia = Utils.getFechaActual_DD_MM_YYYY();

						this.repositorio.Cliente.Visita = fechaVisitaDia;
						
						this.repositorio.Itinerario.Visita = fechaVisitaDia; //No tenia esto en macsa, le puede hacer falta para refrescar el estado
						
						this.repositorio.Cliente.TipoProceso = "SQL";
						this.repositorio.Cliente.AgregarProceso("SQL.Actualizar");
						this.repositorio.Cliente.EjecutarProceso();
						
						this.repositorio.Itinerario.Visita = fechaVisitaDia;
						this.repositorio.Itinerario.TipoProceso = "SQL";
						this.repositorio.Itinerario.AgregarProceso("SQL.Actualizar");
						this.repositorio.Itinerario.EjecutarProceso();
						
						Toast.makeText(frmMotivoNoCompra.this.getBaseContext(), "Se ha guardado la información con exito", Toast.LENGTH_LONG).show();
						
						finish();
					}
					else
					{
						showHelp(R.id.lsdmIncidenciaStericycle, "Debe seleccionar un motivo");
					}
			}
			else
			{
				Toast.makeText(frmMotivoNoCompra.this.getBaseContext(), "Los campos son requeridos", Toast.LENGTH_LONG).show();
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
