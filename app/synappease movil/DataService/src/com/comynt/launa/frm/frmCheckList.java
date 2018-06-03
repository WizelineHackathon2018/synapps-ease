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
import com.comynt.launa.adapt.adaptCuestionario;
import com.comynt.launa.adapt.adaptInventario;
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

public class frmCheckList extends BaseFragmentActivity implements kIButton, kIEditText
{
	Repositorio repositorio = Repositorio.getInstance();
	ListView list = null;
	static adaptCuestionario adapter = null;
	static adapImagenesFoto adapimg =  null;

	private String mCurrentPhotoPath;

	kButton btnAtras = null;
	kButton btnAdelate = null;

	kButton btnEst = null;
	kButton btnGau = null;

	ListView lslmInventarioVenta = null;
	static adaptInventario lmadapterInventario = null;
	
	private static final int ACTION_TAKE_PHOTO_B = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frmchecklist);

		repositorio.IniciaVariables(this);
		
	    showman.AgregarElemento("Selecciona el vehículo");
	    showman.AgregarElemento(R.id.cbxAutos);

	    showman.AgregarElemento("registra el estado del mismo");
	    showman.AgregarElemento(R.id.lsdmChecklist);
	    
	    showman.AgregarElemento("da clic en terminar");
	    showman.AgregarElemento(R.id.btnAdelante);

	    btnAtras = (kButton) findViewById(R.id.btnAtras);
	    btnAdelate = (kButton) findViewById(R.id.btnAdelante);
	    
		btnEst = (kButton) findViewById(R.id.btnEst);
		btnGau = (kButton) findViewById(R.id.btnGau);
	    
        repositorio.mCurrentPhotoPath = "";

		if(repositorio.lsVehiculo == null)
		{
			repositorio.ConsultarVehiculo(repositorio.ID_SQL, repositorio.ID_GuardaMemoria);
		}
		
		if(repositorio.lsVehiculo != null)
		{
			ArrayAdapter userAdapter = new ArrayAdapter(this, R.layout.spinner, repositorio.lsVehiculo);
			Spinner spinner = (Spinner) findViewById(R.id.cbxAutos);
			spinner.setAdapter(userAdapter);
		}
		
		if(repositorio.lsCheckVehiculo == null)
		{
			repositorio.ConsultarCheckVehiculo(repositorio.ID_SQL, repositorio.ID_GuardaMemoria);
		}
		
		if(repositorio.lsCheckVehiculo != null)
		{
			list = (ListView)findViewById(R.id.lsdmChecklist);

	        adapter = new adaptCuestionario(this, repositorio.lsCheckVehiculo, 1);
	        list.setAdapter(adapter);

			list.setClickable(false);
		}
		
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
	    		
	    		imageManager.saveImageBO(repositorio.mCurrentPhoto, repositorio.mCurrentPhotoPath, "VEHICULO", repositorio);
	    		
	    		adapimg.elementos.clear();
	    		adapimg.elementos.add(repositorio.mCurrentPhotoPath);
	    		
	    		adapimg.notifyDataSetChanged();
			}
	    }
		else
		{
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		}
	}

	@Override
	public void commandAction(kButton b, View v) 
	{
		if(b == btnAdelate)
		{
			Spinner spinner = (Spinner) findViewById(R.id.cbxAutos);

			if((spinner.getSelectedItemPosition() > -1) && (this.repositorio.lsCheckVehiculo != null) && 
					(this.repositorio.lsCheckVehiculo.size() > 0))
			{
				BOVehiculo vehiculo = (BOVehiculo)repositorio.lsVehiculo.get(spinner.getSelectedItemPosition());

				BOCheckVehiculoRealizado CheckVehiculoRealizado = new BOCheckVehiculoRealizado(v.getContext());

				CheckVehiculoRealizado.TipoProceso = "SQL";
				CheckVehiculoRealizado.AgregarProceso("SQL.Consultar");
				CheckVehiculoRealizado.getClass(); CheckVehiculoRealizado.AgregarParametro("idSortField", String.valueOf(5));
				CheckVehiculoRealizado.getClass(); CheckVehiculoRealizado.AgregarParametro("idFilterField", String.valueOf(5));
				CheckVehiculoRealizado.AgregarParametro("filtro", Utils.getFechaActual_DD_MM_YYYY());
				List<BO> lsCheckVehiculoRealizado = CheckVehiculoRealizado.ConsultarProceso();

				CheckVehiculoRealizado.ClearProceso();
				CheckVehiculoRealizado.ClearParametros();

				CheckVehiculoRealizado.TipoProceso = "SQL";
				CheckVehiculoRealizado.AgregarProceso("SQL.EliminarTodos");
				CheckVehiculoRealizado.EjecutarProceso();

				for (int x = 0; x < this.repositorio.lsCheckVehiculo.size(); x++)
				{
					this.repositorio.CheckVehiculo = ((BOCheckVehiculo)this.repositorio.lsCheckVehiculo.get(x));

					CheckVehiculoRealizado.ClearProceso();
					CheckVehiculoRealizado.ClearParametros();

					CheckVehiculoRealizado.IdCheckVehiculo = this.repositorio.CheckVehiculo.IdCheckVehiculo;
					CheckVehiculoRealizado.IdVehiculo = this.repositorio.Vehiculo.IdVehiculo;
					CheckVehiculoRealizado.IdVendedor = this.repositorio.Vendedor.IdVendedor;
					CheckVehiculoRealizado.PIN = Info.getPIN(this);
					CheckVehiculoRealizado.Valor = adapter.UImanager.getSelectedText(x);
					CheckVehiculoRealizado.Fecha = Utils.getFechaActual_DD_MM_YYYY();

					CheckVehiculoRealizado.TipoProceso = "SQL";
					CheckVehiculoRealizado.AgregarProceso("SQL.Agregar");
					CheckVehiculoRealizado.EjecutarProceso();
				}

				Toast.makeText(frmCheckList.this.getBaseContext(), "Se ha guardado la información con exito", Toast.LENGTH_LONG).show();

				//guardamos la info de la foto
				finish();
			}
			else
			{
				showHelp(R.id.lsdmChecklist, "Los campos son requeridos");
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
