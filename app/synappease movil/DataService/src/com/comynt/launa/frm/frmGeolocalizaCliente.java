package com.comynt.launa.frm;

import java.io.File;
import java.util.Arrays;

import BO.*;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.System.Base.BaseFragmentActivity;
import com.System.Dispositivo.Info;
import com.System.Images.imageManager;
import com.System.UI.kButton;
import com.System.UI.kIButton;
import com.System.Utils.Logg;
import com.System.Utils.Utils;
import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;
import com.comynt.launa.Consumer;
import com.comynt.launa.Repositorio;
import com.comynt.launa.adapt.ViewPagerAdapter;
import com.comynt.launa.adapt.adapImagenesFoto;
import com.comynt.launa.async.AsyncObtenerGPS;
import com.comynt.launa.async.IAsyncEvent;
import com.comynt.launa.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class frmGeolocalizaCliente extends BaseFragmentActivity implements kIButton, IAsyncEvent
{
	Repositorio repositorio = Repositorio.getInstance();
	adapImagenesFoto adapimg =  null;

	kButton btnAtras = null;
	kButton btnAdelate = null;

	kButton btnEst = null;
	kButton btnGau = null;
	
	TextView lblNombre = null;
	TextView lblLatitud = null;
	TextView lblLongitud = null;
	
	public IAsyncEvent Evento = null;
	public AsyncObtenerGPS oAsyncEnviarDatosPendientes = null;
	public Thread _threadAsyncSincronizar2 = null;
	
	private static final int ACTION_TAKE_PHOTO_B = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frmgeolocalizacliente);

		repositorio.IniciaVariables(this);
		
		showman.AgregarElemento("Espera un momento a levantar el censo GPS");
		showman.AgregarElemento(R.id.lblLatitud);

		showman.AgregarElemento("toma la foto frente al cliente");
		showman.AgregarElemento(R.id.galeriaFotos);

		showman.AgregarElemento("da clic en siguiente, si no se registra el censo GPS en este momento se tomara el censo en la siguiente visita");
		showman.AgregarElemento(R.id.btnAdelante);	    
		
        repositorio.mCurrentPhotoPath = "";

		btnAtras = (kButton) findViewById(R.id.btnAtras);
		btnAdelate = (kButton) findViewById(R.id.btnAdelante);

		btnEst = (kButton) findViewById(R.id.btnEst);
		btnGau = (kButton) findViewById(R.id.btnGau);
	    
	    lblNombre = (TextView) findViewById(R.id.lblNombre);
	    lblNombre.setText(repositorio.Itinerario.Cliente);

	    lblLatitud = (TextView) findViewById(R.id.lblLatitud);
	    lblLongitud = (TextView) findViewById(R.id.lblLongitud);
	    
		adapimg = new adapImagenesFoto(this);
		Gallery gallery = (Gallery)findViewById(R.id.galeriaFotos);
        gallery.setAdapter(adapimg);
        
        gallery.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
			{
				dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);
			}
        	
        });
        
        oAsyncEnviarDatosPendientes = new AsyncObtenerGPS(this);
        oAsyncEnviarDatosPendientes.evento = this;
		_threadAsyncSincronizar2 = new Thread(oAsyncEnviarDatosPendientes);
		_threadAsyncSincronizar2.start();
		
		CargarMenuLateral();
		CargarMenuLateral2();
		
		transicionmanual = true;
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
	
	private void dispatchTakePictureIntent(int actionCode) 
	{
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File f = null;
		
		try
		{
			repositorio.mCurrentPhoto = "Photo" + Utils.getFechaHoraActualBajo() + ".jpg";
			
			f = new File(Environment.getExternalStorageDirectory(), repositorio.mCurrentPhoto);
			
			repositorio.mCurrentPhotoPath = f.getAbsolutePath();
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		}
		catch (Exception e)
		{
			Logg.debug("Error al obtener la imagen: " + Arrays.toString(e.getStackTrace()) );
			f = null;
			repositorio.mCurrentPhotoPath = "";
		}

		startActivityForResult(takePictureIntent, actionCode);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		if (requestCode == ACTION_TAKE_PHOTO_B)
		{
			if(!repositorio.mCurrentPhotoPath.equals(""))
			{
	    		Toast.makeText(this, "Foto: " + repositorio.mCurrentPhotoPath, Toast.LENGTH_LONG).show();
	    		Logg.info("Foto: " + repositorio.mCurrentPhotoPath);
	    		
	    		//Redimencionamos la foto
	    		String archivo = imageManager.redimXPorcent(repositorio.mCurrentPhotoPath, repositorio.mCurrentPhoto, 75, 40);
	    		
	    		Toast.makeText(this, "Nueva Foto: " + archivo, Toast.LENGTH_LONG).show();
	    		Logg.info("Nueva Foto: " + archivo);
	    		
	    		repositorio.mCurrentPhotoPath = archivo;
	    		
	    		imageManager.saveImageBO(repositorio.mCurrentPhoto, repositorio.mCurrentPhotoPath, repositorio.Tipo, repositorio);
	    		
	    		adapimg.elementos.clear();
	    		adapimg.elementos.add(repositorio.mCurrentPhotoPath);
	    		
	    		adapimg.notifyDataSetChanged();
			}
	    }
		else
		{
			oAsyncEnviarDatosPendientes.continuar = false;
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		}
	}

	@Override
	public void commandAction(kButton b, View v)
	{
		if(b == btnAdelate)
		{
			if((lblLatitud.getText().toString().equals("")) && (lblLongitud.getText().toString().equals("")))
			{
				Intent sig = new Intent(frmGeolocalizaCliente.this, frmDatosCliente.class);
				startActivityForResult(sig, 500);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				finish();
				
    			Toast.makeText(this, "No se ha obtenido la posición GPS, queda pendiente", Toast.LENGTH_LONG).show();
			}
			else if((lblLatitud.getText().toString().equals("LATITUD")) && (lblLongitud.getText().toString().equals("LONGITUD")))
			{
				Intent sig = new Intent(frmGeolocalizaCliente.this, frmDatosCliente.class);
				startActivityForResult(sig, 500);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				finish();
				
    			Toast.makeText(this, "No se ha obtenido la posición GPS, queda pendiente", Toast.LENGTH_LONG).show();
			}
			else
			{
				//Si tiene gps tomamos la foto
				if(!repositorio.mCurrentPhotoPath.equals(""))
				{
						//Guardamos los datos de la posicion
						this.repositorio.EnviarPosicion(this.repositorio.Cliente.IdCliente, 
								this.lblLatitud.getText().toString() + "," + lblLongitud.getText().toString());

						this.repositorio.Cliente.PosicionGPS = this.lblLatitud.getText().toString() + "," + lblLongitud.getText().toString();

						this.repositorio.Cliente.ClearProceso();
						this.repositorio.Cliente.ClearParametros();

						this.repositorio.Cliente.TipoProceso = "SQL";
						this.repositorio.Cliente.AgregarProceso("SQL.Actualizar");
						this.repositorio.Cliente.EjecutarProceso();

				    	Toast.makeText(this, "Se ha levantado el censo con exito", Toast.LENGTH_LONG).show();

						oAsyncEnviarDatosPendientes.continuar = false;

						Intent sig = new Intent(frmGeolocalizaCliente.this, frmDatosCliente.class);
						startActivityForResult(sig, 500);
						overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
						finish();
				}
				else
				{
					Toast.makeText(this, "Se debe tomar al menos una foto del cliente", Toast.LENGTH_SHORT).show();
					dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);
				}
			}
		}
		else if(b == btnAtras)
		{
			oAsyncEnviarDatosPendientes.continuar = false;
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
	public void Event(String event, final String args)
	{
		this.runOnUiThread(new Runnable() {
			  public void run() {
				  frmGeolocalizaCliente.this.lblLatitud.setText(Utils.Split(args, ",")[0]);
				  frmGeolocalizaCliente.this.lblLongitud.setText(Utils.Split(args, ",")[1]);
			  }
			});
	}
	
}
