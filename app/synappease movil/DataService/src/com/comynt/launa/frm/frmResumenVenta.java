package com.comynt.launa.frm;

import BO.*;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.System.Sonidos.Sounds;
import com.System.Ticket.TicketCPCL;
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
import com.comynt.launa.adapt.adaptResumenVenta;
import com.comynt.launa.async.AsyncImprimir;
import com.comynt.launa.async.IAsyncEvent;
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

public class frmResumenVenta extends BaseFragmentActivity implements kIButton, IAsyncEvent
{
	public Repositorio repositorio = Repositorio.getInstance();
	adapImagenesFoto adapimg =  null;
	private BONota nota = null;
	
	public IAsyncEvent Evento = null;
	
	private TicketCPCL tiquet = null;
	
	kButton btnAtras = null;
	kButton btnAdelate = null;

	kButton btnEst = null;
    kButton btnGau = null;
	
	TextView lblArticulosNota = null;
	TextView lblTotalNota = null;
	TextView lblMinimoPagar = null;
	
	ListView list = null;
	static adaptResumenVenta adapter = null;
	
	private float _descuento = 0.0F;
	private float subtotal = 0.0F;
	private float cantidad = 0.0F;
	private double impuestos = 0.0D;
	public double total = 0.0D;
	private double AbonoNota = 0.0D;
	private double CreditoNota = 0.0D;
	
	private String TipoOperacion = "";
	private String NumeroOperacion = "";
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frmresumenventa);

		repositorio.IniciaVariables(this);

		Evento = this;
		
	    showman.AgregarElemento("Verifica las cantidades, así como los precios y el total a pagar con el cliente");
	    showman.AgregarElemento(R.id.lvResumenVenta);

	    showman.AgregarElemento("da clic en terminar");
	    showman.AgregarElemento(R.id.btnAdelante);

		btnAtras = (kButton) findViewById(R.id.btnAtras);
		btnAdelate = (kButton) findViewById(R.id.btnAdelante);

		btnEst = (kButton) findViewById(R.id.btnEst);
		btnGau = (kButton) findViewById(R.id.btnGau);

		lblArticulosNota = (TextView) findViewById(R.id.lblArticulosNota);
		lblTotalNota = (TextView) findViewById(R.id.lblTotalNota);
		lblMinimoPagar = (TextView) findViewById(R.id.lblMinimoPagar);

		this.TipoOperacion = "Venta";
		list = (ListView)findViewById(R.id.lvResumenVenta);

		adapter = new adaptResumenVenta(this);
		list.setAdapter(adapter);

		list.setClickable(false);

		CargarMenuLateral();
		CargarMenuLateral2();

		CargarDatos();

		btManager.setBluetooth(true);
		
		if(repositorio.esventa)
		{
			if(dialog == null)
			{
				dialog = new ProgressDialog(this);
				dialog.setMessage("Por favor espere Imprimiendo...");
	            dialog.setCancelable(false);
	            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	            dialog.show();		    			
			}
		}
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
	public void onBackPressed() {
//		btManager.setBluetooth(false);
		super.onBackPressed();
	}

	@Override
	public void commandAction(kButton b, View v)
	{
		if(b == btnAdelate)
		{
			if(repositorio.esventa)
			{
				return;
			}
			else
			{
				repositorio.esventa = true;
			}
			
			dialog = new ProgressDialog(v.getContext());
			dialog.setMessage("Por favor espere Imprimiendo...");
            dialog.setCancelable(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();

			Toast.makeText(this, "Imprimiendo...", Toast.LENGTH_LONG).show();
			
			btManager.setBluetooth(true);
			
			Sounds.PlaySuccess(this, false);
			
			GenerarNota();
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

	private void CargarDatos()
	{
		repositorio.ConsultarImpresora();
		if (this.repositorio.Cliente.Discriminante.equals("CREDITO"))
		{
			try
			{
				int _folio = repositorio.Impresora.FolioVentaCredito;
				_folio++;
				repositorio.FolioNota = Utils.leftPad(String.valueOf(_folio), "0", 9);
				repositorio.Impresora.FolioVentaCredito = _folio;
			}
			catch(Exception ex)
			{
				repositorio.FolioNota = "000000000";
				repositorio.Impresora.FolioVentaCredito = 0;
			}

			repositorio.FolioNota = 
					repositorio.Impresora.IzquierdaFolioVentaCredito + repositorio.FolioNota + repositorio.Impresora.DerechaFolioVentaCredito;
		}
		else
		{
			try
			{
				int _folio = repositorio.Impresora.FolioVentaContado;
				_folio++;
				repositorio.FolioNota = Utils.leftPad(String.valueOf(_folio), "0", 9);
				repositorio.Impresora.FolioVentaContado = _folio;
			}
			catch(Exception ex)
			{
				repositorio.FolioNota = "000000000";
				repositorio.Impresora.FolioVentaContado = 0;
			}

			repositorio.FolioNota = 
					repositorio.Impresora.IzquierdaFolioVentaContado + repositorio.FolioNota + repositorio.Impresora.DerechaFolioVentaContado;
		}
		
		this.subtotal = 0.0F;
		this.cantidad = 0.0F;
		this.impuestos = 0.0D;
		this.total = 0.0D;

		for (int x = 0; x < repositorio.productos.size(); x++)
		{
			BONotaDetalle producto = (BONotaDetalle)repositorio.productos.elementAt(x);

			this.subtotal += producto.Precio * producto.Cantidad;
			this.cantidad += producto.Cantidad;
			this.impuestos += producto.Precio * producto.Cantidad * 0.16D;
		}

		this.impuestos = 0.0D;
		this.total = this.subtotal;

		this.lblArticulosNota.setText("Articulos de la nota: " + repositorio.productos.size());
		this.lblTotalNota.setText("Total nota: $" + Utils.toMoneyFormat(this.subtotal));
		this.lblMinimoPagar.setText("Minimo a pagar: $" + Utils.toMoneyFormat(this.subtotal));
	}


	private void GenerarNota() throws NumberFormatException
	{
		if (repositorio.productos.size() == 0)
		{
			showHelp(R.id.lvResumenVenta, "No hay productos en la nota");
		}
		else
		{
			if (this.repositorio.Cliente.Discriminante.equals("CREDITO"))
			{
				Logg.info("Venta credito numero: " + repositorio.FolioNota);
			}
			else
			{
				Logg.info("Venta contado numero: " + repositorio.FolioNota);
			}
			
			this.nota = new BONota(this);
			
			this.nota.IdEmpresa = this.repositorio.Vendedor.IdEmpresa;
			
			this.nota.Discriminante = "Venta";
			
			RadioButton rbRemision = (RadioButton) findViewById(R.id.rbRemision);	
			
			if (rbRemision.isChecked())
				this.nota.TipoOperacion = "REMICION";
			else {
				this.nota.TipoOperacion = "FACTURA";
			}
			
			this.nota.Enviado = 1;
			this.nota.EnviadoCompleto = 1;
			this.nota.Cancelar = 1;
			
			this.nota.IdItinerario = this.repositorio.Itinerario.IdItinerario;
			
			this.nota.IdCliente = this.repositorio.Itinerario.IdCliente;
			this.nota.GUIDV = Utils.getGIUD("V", this);

			if (this.repositorio.Cliente.Discriminante.equals("CREDITO"))
			{
				this.nota.DiscriminantePago = "CREDITO";
			}
			else
			{
				this.nota.DiscriminantePago = "EFECTIVO";
			}
			
			this.nota.IdVenta = Utils.Generate();
			
			this.nota.TipoDocumento = this.TipoOperacion;
			
			this.nota.Folio = repositorio.FolioNota;
			
			this.nota.FechaCreacion = Utils.getFechaHoraActual();
			this.nota.Fecha = Utils.getFechaActual_DD_MM_YYYY();
			this.nota.Observacion = "";
			this.nota.Sincronizado = 0;
			this.nota.Impreso = 0;
			
			this.repositorio.FechaHoraUltimaOperacion = this.nota.FechaCreacion;
			
			if (this.CreditoNota > 0.0D)
			{
				this.nota.Credito = 1;
				this.nota.CreditoMonto = this.CreditoNota;
			}
			else
			{
				this.nota.Credito = 0;
				this.nota.CreditoMonto = 0.0D;
			}
			
			this.nota.Descuento = (this._descuento == 0.0F ? 0 : 1);
			this.nota.DescuentoMonto = this._descuento;
			this.nota.Subtotal = this.subtotal;
			this.nota.Impuestos = this.impuestos;
			this.nota.Total = this.total;
			this.nota.Saldo = this.total;
			this.nota.IdVendedor = this.repositorio.Vendedor.IdVendedor;
			this.nota.Vendedor = "vendedor";
			this.nota.IdCobrador = 1;
			this.nota.Cobrador = "vendedor";
			this.nota.IdMoneda = 1;
			this.nota.Moneda = "moneda de la nota";
			this.nota.Sincronizado = 0;

			this.nota.Abono = this.AbonoNota;

			this.nota.Cliente = this.repositorio.Itinerario.Cliente;

			if (this.repositorio.Cliente.Discriminante.equals("CREDITO"))
			{
				this.nota.PagoEfectivo = this.total;

				nota.Credito = 1;
				nota.CreditoMonto = this.total;  //monto del credito en la nota
			}
			else
			{
				this.nota.PagoEfectivo = this.total;

				nota.Credito = 0;
				nota.CreditoMonto = 0;
			}

			this.nota.PagoDeposito = 0;
			this.nota.PagoTransferencia = 0;
			this.nota.PagoCheque = 0;

			this.nota.NombreImpreso = "";
			this.nota.FechaCheque = "";
			this.nota.Banco = "";
			this.nota.ChequeNo = "";

			this.nota.FolioDia = repositorio.FolioNota;
			
			this.nota.Codigo = this.repositorio.Itinerario.Codigo;
			this.nota.Direccion = this.repositorio.Itinerario.Direccion;
			this.nota.RazonSocial = this.repositorio.Itinerario.RazonSocial;

			this.nota.TipoProceso = "SQL";
			this.nota.AgregarProceso("SQL.Agregar");
			this.nota.EjecutarProceso();

			this.repositorio.Itinerario.SaldoPendiente = 
					(this.repositorio.Itinerario.SaldoPendiente + (float)this.total - 
							(float)this.nota.PagoEfectivo);

			this.repositorio.Itinerario.TipoProceso = "SQL";
			this.repositorio.Itinerario.AgregarProceso("SQL.Actualizar");
			this.repositorio.Itinerario.EjecutarProceso();

			this.repositorio.Cliente = ((BOCliente)this.repositorio.lsCliente.get(this.repositorio.IndiceCliente));

			this.repositorio.Cliente.SaldoPendiente = this.repositorio.Itinerario.SaldoPendiente;

			String fechaVisitaDia = Utils.getFechaActual_DD_MM_YYYY();
			this.repositorio.Cliente.Visita = fechaVisitaDia;
			
			this.repositorio.Itinerario.Visita = fechaVisitaDia; //No tenia esto en macsa, le puede hacer falta para refrescar el estado

			this.repositorio.Cliente.TipoProceso = "SQL";
			this.repositorio.Cliente.AgregarProceso("SQL.Actualizar");
			this.repositorio.Cliente.EjecutarProceso();

			for (int x = 0; x < repositorio.productos.size(); x++)
			{
				BONotaDetalle producto = (BONotaDetalle)repositorio.productos.elementAt(x);
				producto.IdVenta = this.nota.IdVenta;
				producto.Enviado = 0;
				producto.FolioDia = this.nota.FolioDia;
				producto.GUIDV = this.nota.GUIDV;
				producto.GUIDVD = Utils.getGIUD("VD", this);

				producto.Discriminante = "Venta";

				producto.TipoProceso = "SQL";
				producto.AgregarProceso("SQL.Agregar");
				producto.EjecutarProceso();
			}

			for (int x = 0; x < this.repositorio.embalaje.size(); x++)
			{
				BODevolucionEmbalaje DevolucionEmbalaje = (BODevolucionEmbalaje)this.repositorio.embalaje.elementAt(x);
				DevolucionEmbalaje.GUIDV = this.nota.GUIDV;
				DevolucionEmbalaje.Enviado = 0;

				DevolucionEmbalaje.TipoProceso = "SQL";
				DevolucionEmbalaje.AgregarProceso("SQL.Agregar");
				DevolucionEmbalaje.EjecutarProceso();
			}

			BOConfiguracion Configuracion = new BOConfiguracion(this);
			Configuracion.TipoProceso = "SQL";
			Configuracion.AgregarProceso("SQL.Consultar");
			Configuracion.getClass(); Configuracion.AgregarParametro("idSortField", String.valueOf(3));
			Configuracion.getClass(); Configuracion.AgregarParametro("idFilterField", String.valueOf(3));
			Configuracion.AgregarParametro("filtro", "");

			List<BO> arrayBO = Configuracion.ConsultarProceso();

			if (arrayBO != null)
			{
				Configuracion = (BOConfiguracion)arrayBO.get(0);

				if (this.TipoOperacion.equals("Venta"))
				{
					Configuracion.NoVenta = Integer.parseInt(this.NumeroOperacion);
				}
				else if (this.TipoOperacion.equals("Pedido"))
				{
					Configuracion.NoPedido = Integer.parseInt(this.NumeroOperacion);
				}
				else
				{
					Configuracion.NoCotizacion = Integer.parseInt(this.NumeroOperacion);
				}

				Configuracion.TipoProceso = "SQL";
				Configuracion.AgregarProceso("SQL.Actualizar");
				Configuracion.EjecutarProceso();
			}
			else
			{
				if (this.TipoOperacion.equals("Venta"))
				{
					Configuracion.NoVenta = 0;
				}
				else if (this.TipoOperacion.equals("Pedido"))
				{
					Configuracion.NoPedido = 0;
				}
				else
				{
					Configuracion.NoCotizacion = 0;
				}
				this.repositorio.Itinerario.TipoProceso = "SQL";
				this.repositorio.Itinerario.AgregarProceso("SQL.Actualizar");
				this.repositorio.Itinerario.EjecutarProceso();
			}

			if (this.repositorio.lsAlmacenMovil != null)
			{
				int w1 = 0;
				int z1 = 0;

				for (w1 = 0; w1 < repositorio.productos.size(); w1++)
				{
					BONotaDetalle producto = (BONotaDetalle)repositorio.productos.elementAt(w1);

					for (z1 = 0; z1 < this.repositorio.lsAlmacenMovil.size(); z1++)
					{
						this.repositorio.AlmacenMovil = ((BOAlmacenMovil)this.repositorio.lsAlmacenMovil.get(z1));
						if (this.repositorio.AlmacenMovil.IdProducto != producto.IdProducto)
							continue;
						this.repositorio.AlmacenMovil.Venta += producto.Cantidad;
						this.repositorio.AlmacenMovil.Existencia -= producto.Cantidad;
						this.repositorio.AlmacenMovil.TipoProceso = "SQL";
						this.repositorio.AlmacenMovil.AgregarProceso("SQL.Actualizar");
						this.repositorio.AlmacenMovil.EjecutarProceso();
					}
				}
			}

			tiquet = new TicketCPCL();

			if(this.repositorio.Cliente.Discriminante.equals("CREDITO"))
			{
				repositorio.oAsyncSincronizar = null;
				repositorio._threadAsyncSincronizar = null;
				
				repositorio.oAsyncSincronizar = new AsyncImprimir(tiquet.ObtenerTicketVenta(total, 
						Utils.Split(this.nota.FechaCreacion, " ")[0], Utils.Split(this.nota.FechaCreacion, " ")[1] ), 
						repositorio.ID_ImprimirVentaCredito);
				
				repositorio.oAsyncSincronizar.evento = frmResumenVenta.this.Evento;
				
				repositorio._threadAsyncSincronizar = new Thread(repositorio.oAsyncSincronizar);
				repositorio._threadAsyncSincronizar.start();
			}
			else
			{
				repositorio.oAsyncSincronizar = null;
				repositorio._threadAsyncSincronizar = null;
				
				repositorio.oAsyncSincronizar = new AsyncImprimir(tiquet.ObtenerTicketVenta(total,
						Utils.Split(this.nota.FechaCreacion, " ")[0], Utils.Split(this.nota.FechaCreacion, " ")[1] ), 
						repositorio.ID_ImprimirVentaContado);
				
				repositorio.oAsyncSincronizar.evento = frmResumenVenta.this.Evento;
				
				repositorio._threadAsyncSincronizar = new Thread(repositorio.oAsyncSincronizar);
				repositorio._threadAsyncSincronizar.start();
			}
		}
	}
	
	@Override
	public void Event(String event, String args)
	{
		this.runOnUiThread(new Runnable()
		{
			  public void run()
			  {
				  Repositorio repositorio = Repositorio.getInstance();
				  
					Toast.makeText(frmResumenVenta.this.getBaseContext(), 
						"Se ha generado la nota con exito", Toast.LENGTH_LONG).show();

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
					
					try
					{
						Sounds.PlaySuccess(repositorio.context, false);
					}catch(Exception ex){}

					finish();
					btManager.setBluetooth(false);
			  }
		});
	}

}
