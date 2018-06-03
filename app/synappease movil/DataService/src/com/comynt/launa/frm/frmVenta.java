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
import android.view.MenuInflater;
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
import java.util.Vector;

import com.System.Base.BaseFragmentActivity;
import com.System.Bluetooth.btManager;
import com.System.Dispositivo.Info;
import com.System.UI.kButton;
import com.System.UI.kEditText;
import com.System.UI.kIButton;
import com.System.UI.kIEditText;
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
import com.comynt.launa.adapt.adapProducto;
import com.comynt.launa.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;

public class frmVenta extends BaseFragmentActivity implements kIButton, kIListView, kIEditText
{
	Repositorio repositorio = Repositorio.getInstance();

	int fila = -1;
	int selectedfila = -1;

	kEditText txtBuscar = null;

	kButton btnAtras = null;
	kButton btnAdelate = null;

    kButton btnEst = null;
    kButton btnGau = null;
	
	private float _subtotal = 0.0F;

	kListView list = null;
	static adapProducto adapter = null;

	private View vi = null;
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frmventa);

		repositorio.IniciaVariables(this);
		
		//habla("Captura las cantidades del producto a vender, dando clic donde están los ceros o usa los botones de más y menos para agregar o disminuir, recuerda no se puede vender un producto con precio cero, y a continuación da clic en siguiente");
	    showman.AgregarElemento("Captura las cantidades del producto a vender, recuerda no se puede vender un producto con precio cero");
	    showman.AgregarElemento(R.id.lsveractividades);

	    showman.AgregarElemento("da clic en siguiente");
	    showman.AgregarElemento(R.id.btnAdelante);

		btnAtras = (kButton) findViewById(R.id.btnAtras);
		btnAdelate = (kButton) findViewById(R.id.btnAdelante);

		btnEst = (kButton) findViewById(R.id.btnEst);
		btnGau = (kButton) findViewById(R.id.btnGau);
		
		this.repositorio.VerificaCreditoSaldo();
        
    	this.repositorio.ConsultarAlmacenMovil(2, 4, String.valueOf(this.repositorio.Vendedor.IdEmpresa), "0", "0");
        
        //Establecemos los precios publico
    	repositorio.ConsultarProductoListaPrecio(repositorio.ID_SQL, repositorio.ID_GuardaMemoria, "0", "0", "0");
        
        //Establecemos los precios especiales
    	repositorio.ConsultarPrecioCliente(repositorio.ID_SQL, repositorio.ID_GuardaMemoria);
        
        if ((this.repositorio.lsProductoListaPrecio != null) && (repositorio.lsAlmacenMovil != null))
        {
        	for(int x = 0; x < this.repositorio.lsProductoListaPrecio.size(); x++)
        	{
        		this.repositorio.ProductoListaPrecio = (BOProductoListaPrecio)this.repositorio.lsProductoListaPrecio.get(x);
        		
        		for(int y = 0; y < repositorio.lsAlmacenMovil.size(); y++)
        		{
        			repositorio.AlmacenMovil = (BOAlmacenMovil)repositorio.lsAlmacenMovil.get(y);
        			if(repositorio.AlmacenMovil.IdProducto == repositorio.ProductoListaPrecio.IdProducto)
        			{
        				repositorio.AlmacenMovil.PrecioPublico = repositorio.ProductoListaPrecio.Precio;
        				repositorio.lsAlmacenMovil.set(y, repositorio.AlmacenMovil);
        			}
        		}
        	}
        }
        
        if ((this.repositorio.lsPrecioCliente != null) && (repositorio.lsAlmacenMovil != null))
        {
        	for(int x = 0; x < this.repositorio.lsPrecioCliente.size(); x++)
        	{
        		this.repositorio.PrecioCliente = (BOPrecioCliente)this.repositorio.lsPrecioCliente.get(x);
        		
        		for(int y = 0; y < repositorio.lsAlmacenMovil.size(); y++)
        		{
        			repositorio.AlmacenMovil = (BOAlmacenMovil)repositorio.lsAlmacenMovil.get(y);
        			
        			if((repositorio.AlmacenMovil.IdProducto == repositorio.PrecioCliente.IdProducto) &&
        					(repositorio.Itinerario.IdCliente == repositorio.PrecioCliente.IdCliente))
        			{
        				repositorio.AlmacenMovil.PrecioPublico = Float.parseFloat(repositorio.PrecioCliente.Precio);
        				repositorio.lsAlmacenMovil.set(y, repositorio.AlmacenMovil);
        			}
        		}
        	}
        }

			list = (kListView)findViewById(R.id.lsveractividades);

	        adapter = new adapProducto(this, repositorio.lsAlmacenMovil);
	        list.setAdapter(adapter);

			list.setClickable(false);
			
			txtBuscar = (kEditText) findViewById(R.id.txtBuscar);
		
		btManager.setBluetooth(true);
		
		CargarMenuLateral();
		CargarMenuLateral2();

		btManager.setBluetooth(true);

		transicionmanual = true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.mnuproductoventa, menu);
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
	            
	        case R.id.mnu_calculadora:
	        	
	        	try
	        	{
	        		ArrayList<HashMap<String,Object>> items =new ArrayList<HashMap<String,Object>>();
	        		PackageManager pm = getPackageManager();
	        		List<PackageInfo> packs = pm.getInstalledPackages(0);  
	        		for (PackageInfo pi : packs) {
	        		if( pi.packageName.toString().toLowerCase().contains("calcul")){
	        		    HashMap<String, Object> map = new HashMap<String, Object>();
	        		    map.put("appName", pi.applicationInfo.loadLabel(pm));
	        		    map.put("packageName", pi.packageName);
	        		    items.add(map);
	        		 }
	        		}	        		
	        		
	        		if(items.size()>=1)
	        		{
	        			String packageName = (String) items.get(0).get("packageName");
	        			Intent i = pm.getLaunchIntentForPackage(packageName);
	        			if (i != null)
	        			  startActivity(i);
	        			} 
	        		else{
	        			      // Application not found
	        		}
	        		
	        	}
	        	catch(Exception ex){}
	        	
	        	return true;

	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}	
	
	@Override
    public void onBackPressed() {
		btManager.setBluetooth(false);
        super.onBackPressed();
    }	
	
	public View getViewByPosition(int pos, ListView listView)
	{
		final int firstListItemPosition = listView.getFirstVisiblePosition();
		final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

		if (pos < firstListItemPosition || pos > lastListItemPosition ) 
		{
		    return listView.getAdapter().getView(pos, null, listView);
		} 
		else 
		{
		    final int childIndex = pos - firstListItemPosition;
		    return listView.getChildAt(childIndex);
		}
	}	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		if(repositorio.esventa)
		{
			finish();
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		}
		else
		{
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		}
		btManager.setBluetooth(false);
	}
	
	@Override
	public void commandAction(kButton b, View v)
	{
		if(b.Name.equals(""))
		{
			if(b == btnAdelate)
			{
				repositorio.productos.clear();

				if(this.adapter.datosListaHash.size() > 0)
				{
					//Validamos si tenemos productos y que tienen cantidad si lo tienen pasar a la nota
					for(int x = 0; x < repositorio.lsAlmacenMovil.size(); x++)
					{
						repositorio.AlmacenMovil = (BOAlmacenMovil)repositorio.lsAlmacenMovil.get(x);
						float Cantidad = 0;

						for(int y = 0; y < this.adapter.datosListaHash.size(); y++)
						{
		        			HashMap<String,String> item = new HashMap<String,String>();
		        			item = this.adapter.datosListaHash.get(y);

		        			if( item.containsKey( String.valueOf(repositorio.AlmacenMovil.IdAlmacenMovil) ) )
		        			{
		    		        	try
		    		        	{
		    		        		Cantidad = Float.parseFloat( item.get( String.valueOf(repositorio.AlmacenMovil.IdAlmacenMovil) ) );
		    		        	}
		    		        	catch(Exception ex)
		    		        	{
		    		        		Cantidad = 0;
		    		        	}

		    		        	if(Cantidad > 0)
		    		        	{
		    						BONotaDetalle notadetalle = new BONotaDetalle(this);
		    						notadetalle.Nombre = this.repositorio.AlmacenMovil.Producto;
		    						notadetalle.IdProducto = this.repositorio.AlmacenMovil.IdProducto;
		    						notadetalle.CodigoBarra = this.repositorio.AlmacenMovil.Codigo;
		    						notadetalle.Precio = this.repositorio.AlmacenMovil.PrecioPublico;
		    						notadetalle.Existencias = this.repositorio.AlmacenMovil.Existencia;
		    						notadetalle.Discriminante = "Venta";
		    						notadetalle.UnidadMedida = this.repositorio.AlmacenMovil.Unidad;
		    						notadetalle.Envase =false;
		    						notadetalle.Cantidad = Cantidad;

		    						repositorio.productos.add(notadetalle);
		    		        	}

		        				break;
		        			}
						}
					}

					Intent sig = new Intent(frmVenta.this, frmResumenVenta.class);
					startActivityForResult(sig, 500);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				}
				else
				{
					Toast.makeText(this, "Debe agregar al menos un producto", Toast.LENGTH_LONG).show();
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
		else //es un boton de la lista
		{
			vi = getViewByPosition(Integer.parseInt(b.Name), list);
			if(b.Val.equals("+")) //aumentamos en 1
			{
				float Cantidad = 0;
	        	EditText txtItemCantidad =(EditText)vi.findViewById(R.id.txtItemCantidad);
	        	
	        	try
	        	{
	        		Cantidad = Float.parseFloat(txtItemCantidad.getText().toString());
	        	}
	        	catch(Exception ex)
	        	{
	        		Cantidad = 0;
	        	}
	        	Cantidad++;
	        	txtItemCantidad.setText(String.valueOf(Cantidad));
			}
			else  //disminuimos en 1
			{
				float Cantidad = 0;
	        	EditText txtItemCantidad =(EditText)vi.findViewById(R.id.txtItemCantidad);
	        	
	        	try
	        	{
	        		Cantidad = Float.parseFloat(txtItemCantidad.getText().toString());
	        		if(Cantidad > 0)
	        		{
		        		Cantidad--;
	        		}
	        	}
	        	catch(Exception ex)
	        	{
	        		Cantidad = 0;
	        	}
	        	txtItemCantidad.setText(String.valueOf(Cantidad));
			}
		}
	}

	@Override
	public void onTextChangedEditText(kEditText e, CharSequence cs, int arg1, int arg2, int arg3)
	{
		if(e == txtBuscar)
		{
	    	if(cs.toString().equals(""))
	    	{
				frmVenta.this.adapter.getFilter().filter(cs);
	    	}
	    	else
	    	{
	    		if(cs.length() > 0)
	    		{
	    			frmVenta.this.adapter.getFilter().filter(cs);
	    		}
	    	}
		}
		else
		{
			if(!this.adapter.Recarga)
			{
	    		for(int x = 0; x < this.adapter.datosListaHash.size(); x++)
	    		{
	    			HashMap<String,String> item = new HashMap<String,String>();
	    			item = this.adapter.datosListaHash.get(x);
	    			
	    			if( item.containsKey( e.Val ) )
	    			{
	    				if( (!cs.toString().equals("0")) || (!cs.toString().equals("0.0")) || (!cs.toString().equals("")) )
	    				{
	    					HashMap<String,String> item1 = new HashMap<String,String>();
	    					item1.put( e.Val, cs.toString() );

	    					this.adapter.datosListaHash.set(x, item1);
	    				}
	    				else //Elimina el anterior
	    				{
	    					this.adapter.datosListaHash.remove(x);
	    				}

	    				break;
	    			}
	    		}

				if( (!cs.toString().equals("0")) || (!cs.toString().equals("0.0")) || (!cs.toString().equals("")) )
				{
					HashMap<String,String> item1 = new HashMap<String,String>();
					item1.put( e.Val, cs.toString() );

					this.adapter.datosListaHash.add(item1);
				}
			}
			else
			{
				this.adapter.Recarga = false;
			}

		}
	}

	@Override
	public void afterTextChangedEditText(kEditText e, Editable s)
	{
		int c1 = 0;
		c1++;
	}

	@Override
	public void beforeTextChangedEditText(kEditText e, CharSequence s, int start, int count, int after)
	{
		int c1 = 0;
		c1++;
	}

	@Override
	public void listItemAction(kListView l, AdapterView<?> parent, View view, int position, long id)
	{
		selectedfila = position;
	}

	@Override
	public void doubleclick_listItemAction(kListView l, AdapterView<?> parent, View view, int position, long id)
	{
		// TODO Auto-generated method stub
	}

}
