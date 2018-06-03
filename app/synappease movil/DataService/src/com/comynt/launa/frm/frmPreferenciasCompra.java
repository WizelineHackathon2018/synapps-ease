package com.comynt.launa.frm;

import BO.*;
import android.net.Uri;
import android.os.Bundle;
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

import com.System.Dispositivo.Info;
import com.System.UI.kButton;
import com.System.UI.kIButton;
import com.System.UI.kIMessage;
import com.System.UI.kMessage;
import com.System.Utils.Logg;
import com.System.Utils.Utils;
import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;
import com.comynt.launa.Consumer;
import com.comynt.launa.Repositorio;
import com.comynt.launa.adapt.ViewPagerAdapter;
import com.comynt.launa.adapt.adapAgenda;
import com.comynt.launa.adapt.adapImagenesFoto;
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

public class frmPreferenciasCompra extends FragmentActivity implements kIButton
{
	Repositorio repositorio = Repositorio.getInstance();

	long tiempoanterior = 0;
	int positionanterior = -2;
	Date fechaactual;

	int fila = -1;
	int selectedfila = -1;

	EditText txtBuscar = null;

	kButton btnAtras = null;
	kButton btnAdelate = null;
	kButton btnVoz = null;

	ListView list = null;
	static adapAgenda adapter = null;

	static boolean agregar = false;
	static boolean horainicial = false;

    private static TextToSpeech mTts;
    private OnInitListener tts_escucha;
    
    SlidingMenu menu;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frmpreferenciascompra);

	    btnAtras = (kButton) findViewById(R.id.btnAtras);
	    btnAdelate = (kButton) findViewById(R.id.btnAdelante);
	    btnVoz = (kButton) findViewById(R.id.btnVoz);

		tts_escucha = new OnInitListener()
		{
		    public void onInit(int status)
		    {
		        if (status == TextToSpeech.SUCCESS)
		        {
		        }
		        else
		        {
		        	//mens("Sin iniciar TextToSpeech.");
		        }
		    }
		};
        mTts = new TextToSpeech(this, tts_escucha);

        if (this.repositorio.lsCliente == null)
        {
        	this.repositorio.ConsultarClientes(2, 4, String.valueOf(this.repositorio.Vendedor.IdEmpresa), "0", "0");
        }

		if(repositorio.lsCliente != null)
		{
			this.repositorio.lsItinerario = new ArrayList<BO>();

			for (int x = 0; x < this.repositorio.lsCliente.size(); x++)
			{
				String fechaDia = Utils.getFechaActual_DD_MM_YYYY();
				this.repositorio.Cliente = ((BOCliente)this.repositorio.lsCliente.get(x));

				BOItinerario itienarioItem = new BOItinerario(this);
				itienarioItem.IdItinerario = 0;
				itienarioItem.IdCliente = this.repositorio.Cliente.IdCliente;
				itienarioItem.Cliente = this.repositorio.Cliente.Nombre;
				itienarioItem.Direccion = this.repositorio.Cliente.Direccion;
				itienarioItem.Visita = this.repositorio.Cliente.Visita;
				itienarioItem.PosicionGPS = this.repositorio.Cliente.PosicionGPS;
				itienarioItem.Codigo = this.repositorio.Cliente.Codigo;
				itienarioItem.SaldoPendiente = this.repositorio.Cliente.SaldoPendiente;

				this.repositorio.lsItinerario.add(itienarioItem);
			}
			
			list = (ListView)findViewById(R.id.lsveractividades);

	        adapter = new adapAgenda(this, repositorio.lsItinerario);
	        list.setAdapter(adapter);

			list.setClickable(false);

			Calendar c = Calendar.getInstance();
			fechaactual = c.getTime();
			tiempoanterior = fechaactual.getTime();
			
			list.setOnItemClickListener(new OnItemClickListener() 
			{
			    @Override
			    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			    {
			    	list.playSoundEffect(0);
			    	
			    	long fechanueva = 0;
					Calendar c = Calendar.getInstance();
					fechanueva = c.getTime().getTime();
					
					selectedfila = position;
					
			    	if((fechanueva - tiempoanterior < 1500) && (positionanterior == position)) //DOBLE CLICK VER LA INFO DEL CLIENTE
			    	{
			    		agregar = false;
			    		fila = position;
			    		Logg.info("Posicion seleccionada: " + fila);
			    	}

			    	tiempoanterior = fechanueva;
			    	positionanterior = position;

			    }
			});

			txtBuscar = (EditText) findViewById(R.id.txtBuscar);
			txtBuscar.addTextChangedListener(new TextWatcher() 
			{
	            @Override
	            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) 
	            {

	            	if(cs.toString().equals(""))
	            	{
	            		frmPreferenciasCompra.this.adapter.getFilter().filter(cs);
	            	}
	            	else
	            	{
	            		if(cs.length() > 2)
	            		{
	            			frmPreferenciasCompra.this.adapter.getFilter().filter(cs);
	            		}
	            	}

	            }

	            @Override
	            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) 
	            {
	                // TODO Auto-generated method stub
	            }

	            @Override
	            public void afterTextChanged(Editable arg0) 
	            {
	                // TODO Auto-generated method stub                         
	            }
	        });			
		}

        CargarMenuLateral();
	}

	private void CargarMenuLateral()
	{
		menu = new SlidingMenu(this);
		
		if(repositorio.ConfiguracionUsabilidad.Menu == 0)
		{
			menu.setMode(SlidingMenu.RIGHT);
		}
		else
		{
			menu.setMode(SlidingMenu.LEFT);
		}
		
    	this.repositorio.ConsultarAlmacenMovil2(2, 4, String.valueOf(this.repositorio.Vendedor.IdEmpresa), "0", "0", false);
    	this.repositorio.ConsultarOperacionesDiarias2("Venta");
		
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		
		menu.setMenu(R.layout.tablateralmenu);
		
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
		
		PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		tabs.setViewPager(pager);
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
	        	menu.showMenu();
	            return true;

	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public static void habla(String cad)
	{
		Logg.info("Sirigonia: " + cad);

		mTts.speak(cad, TextToSpeech.QUEUE_FLUSH, null);
    	while(mTts.isSpeaking());
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	@Override
	public void commandAction(kButton b, View v)
	{
		if(b == btnAdelate)
		{
		}
		else if(b == btnAtras)
		{
			finish();
		}
		else if(b == btnVoz)
		{
			habla("¿En que te puedo ayudar?");
			//startVoiceRecognitionActivity();
		}
	}

}

