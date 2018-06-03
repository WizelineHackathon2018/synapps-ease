package com.comynt.launa.frm;

import java.util.ArrayList;
import java.util.List;

import BO.*;
import android.app.Activity;
import android.app.ProgressDialog;
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
import com.System.Bluetooth.btManager;
import com.System.Dispositivo.Info;
import com.System.Sonidos.Sounds;
import com.System.UI.kButton;
import com.System.UI.kEditText;
import com.System.UI.kIButton;
import com.System.UI.kIEditText;
import com.System.UI.kListView;
import com.System.Utils.Logg;
import com.System.Utils.Utils;
import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;
import com.comynt.launa.R;

import com.comynt.launa.Repositorio;
import com.comynt.launa.adapt.ViewPagerAdapter;
import com.comynt.launa.adapt.adapAgenda;
import com.comynt.launa.adapt.adapImagenesFoto;
import com.comynt.launa.adapt.adaptCuestionario;
import com.comynt.launa.async.AsyncImprimir;
import com.comynt.launa.async.IAsyncEvent;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class frmCobro extends BaseFragmentActivity implements kIButton, IAsyncEvent
{
	Repositorio repositorio = Repositorio.getInstance();
	ListView list = null;
	static adaptCuestionario adapter = null;
	
	public IAsyncEvent Evento = null;
	
	kButton btnAtras = null;
	kButton btnAdelate = null;
	
	kButton btnEst = null;
	kButton btnGau = null;
	
	Spinner cbxDocumentos = null;
	
	EditText txtEfectivo = null;
	EditText txtCheque = null;
	EditText txtFolioCheque = null;
	EditText txtFechaCheque = null;
	EditText txtBancoCheque = null;
	EditText txtDeposito = null;
	EditText txtFolioDeposito = null;
	EditText txtFechaDeposito = null;
	EditText txtBancoDeposito = null;
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frmcobro);
		
		repositorio.IniciaVariables(this);
		Evento = this;
		
	    showman.AgregarElemento("Selecciona la factura de la lista, o Abono a factura con referencia");
	    showman.AgregarElemento(R.id.cbxDocumentos);
		
	    showman.AgregarElemento("Captura el monto y método de pago");
	    showman.AgregarElemento(R.id.txtFechaCheque);
	    
	    showman.AgregarElemento("da clic en imprimir");
	    showman.AgregarElemento(R.id.btnAdelante);
	    
	    btnAtras = (kButton) findViewById(R.id.btnAtras);
	    btnAdelate = (kButton) findViewById(R.id.btnAdelante);
	    
		btnEst = (kButton) findViewById(R.id.btnEst);
		btnGau = (kButton) findViewById(R.id.btnGau);
		
		cbxDocumentos = (Spinner)findViewById(R.id.cbxDocumentos);
		
		txtEfectivo = (EditText)findViewById(R.id.txtEfectivo);
		txtCheque = (EditText)findViewById(R.id.txtCheque);
		txtFolioCheque = (EditText)findViewById(R.id.txtFolioCheque);
		txtFechaCheque = (EditText)findViewById(R.id.txtFechaCheque);
		txtBancoCheque = (EditText)findViewById(R.id.txtBancoCheque);
		txtDeposito = (EditText)findViewById(R.id.txtDeposito);
		txtFolioDeposito = (EditText)findViewById(R.id.txtFolioDeposito);
		txtFechaDeposito = (EditText)findViewById(R.id.txtFechaDeposito);
		txtBancoDeposito = (EditText)findViewById(R.id.txtBancoDeposito);
		
		CargarDatos();
		
		CargarMenuLateral();
		CargarMenuLateral2();
        
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
	
	private void CargarDatos() 
	{
		repositorio.ConsultarImpresora();
		
		try
		{
			int _folio = repositorio.Impresora.FolioCobro;
			_folio++;
			repositorio.FolioNota = Utils.leftPad(String.valueOf(_folio), "0", 9);
			repositorio.Impresora.FolioCobro = _folio;
		}
		catch(Exception ex)
		{
			repositorio.FolioNota = "000000000";
			repositorio.Impresora.FolioCobro = 0;
		}
		
		repositorio.FolioNota = 
				repositorio.Impresora.IzquierdaFolioCobro + repositorio.FolioNota + repositorio.Impresora.DerechaFolioCobro;
		
		//Cargamos los datos de las facturas y recalculamos si tienen abonos con el nuevo saldo
		this.repositorio.ObtenerCobroFactura();
		
		if (this.repositorio.lsSaldoClienteNota != null)
		{
			BOSaldoClienteNota SaldoClienteNota = new BOSaldoClienteNota(this);
			
			SaldoClienteNota.NoDocumento = "Abono a factura sin referencia";
			this.repositorio.lsSaldoClienteNota.add(0, SaldoClienteNota);
			
			//Cargamos el combo
			ArrayAdapter userAdapter = new ArrayAdapter(this, R.layout.spinner, repositorio.lsSaldoClienteNota);
			cbxDocumentos.setAdapter(userAdapter);
		}
		else
		{
			//Agregamos el cobro sin referencia
			this.repositorio.lsSaldoClienteNota = new ArrayList<BO>();
			
			BOSaldoClienteNota SaldoClienteNota = new BOSaldoClienteNota(this);
			SaldoClienteNota.NoDocumento = "Abono a factura sin referencia";
			
			this.repositorio.lsSaldoClienteNota.add(SaldoClienteNota);
			
			//Cargamos el combo
			ArrayAdapter userAdapter = new ArrayAdapter(this, R.layout.spinner, this.repositorio.lsSaldoClienteNota);
			cbxDocumentos.setAdapter(userAdapter);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		btManager.setBluetooth(false);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
	
	@Override
	public void commandAction(kButton b, View v) 
	{
		if(b == btnAdelate)
		{
			double totalpago = 0.0D;

			try
			{
				if (!this.txtEfectivo.getText().equals(""))
				{
					totalpago = Double.parseDouble(this.txtEfectivo.getText().toString());
				}
			}
			catch (Exception localException) {}

			try
			{
				if (!this.txtCheque.getText().equals(""))
				{
					totalpago = Double.parseDouble(this.txtCheque.getText().toString());
				}
			}
			catch (Exception localException) {}
			
			try
			{
				if (!this.txtDeposito.getText().equals(""))
				{
					totalpago = Double.parseDouble(this.txtDeposito.getText().toString());
				}
			}
			catch (Exception localException) {}
			
			if (totalpago > 0.0D)
			{
				if (this.cbxDocumentos.getSelectedItemPosition() > -1)
				{
					double montofactura = 0.0D;
					try
					{
						this.repositorio.SaldoClienteNota = (BOSaldoClienteNota) this.repositorio.lsSaldoClienteNota.get(this.cbxDocumentos.getSelectedItemPosition());
						
						montofactura = Double.parseDouble(this.repositorio.SaldoClienteNota.Importe);
						
						int indice = this.cbxDocumentos.getSelectedItemPosition(); 
					}
					catch (Exception localException1){ }
					
					if (montofactura > 0.0D)
					{
						if (totalpago <= montofactura)
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
							
							RealizarCobro();
							finish();
						}
						else
						{
							Toast.makeText(frmCobro.this.getBaseContext(), "El cobro no debe exceder el monto de la factura", Toast.LENGTH_LONG).show();
						}
					}
					else
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
						
						RealizarCobro();
					}
				}
				else
				{
					showHelp(R.id.cbxDocumentos, "Debe seleccionar una factura");
				}
			}
			else
			{
				showHelp(R.id.txtFechaCheque, "Se debe ingresar un monto valido");
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
	
	private void RealizarCobro()
	{
		Logg.info("Cobro numero: " + repositorio.FolioNota);
		
		BONota nota = new BONota(this);
		
		nota.IdEmpresa = this.repositorio.Vendedor.IdEmpresa;
		nota.Discriminante = "Cobro";
		nota.TipoOperacion = "REMICION";
		
		nota.Enviado = 1;
		nota.EnviadoCompleto = 1;
		nota.Cancelar = 1;
		
		nota.IdItinerario = this.repositorio.Itinerario.IdItinerario;
		
		nota.IdCliente = this.repositorio.Itinerario.IdCliente;
		nota.GUIDV = Utils.getGIUD("C", this);
		
		nota.DiscriminantePago = "EFECTIVO";
		
		nota.IdVenta = Utils.Generate();
		
		nota.TipoDocumento = "REMICION";
		
		//nota.Folio = repositorio.FolioNota;
		
		nota.FechaCreacion = Utils.getFechaHoraActual();
		nota.Fecha = Utils.getFechaActual_DD_MM_YYYY();
		
		nota.Observacion = cbxDocumentos.getSelectedItem().toString();
		
		nota.Sincronizado = 0;
		nota.Impreso = 0;
		
		this.repositorio.FechaHoraUltimaOperacion = nota.FechaCreacion;
		
		nota.Credito = 0;
		nota.CreditoMonto = 0;
		
		nota.Descuento = 0;
		nota.DescuentoMonto = 0;
		
		nota.Impuestos = 0;
		
		nota.Saldo = 0;
		nota.IdVendedor = this.repositorio.Vendedor.IdVendedor;
		nota.Vendedor = this.repositorio.Vendedor.Nombre;
		nota.IdCobrador = 1;
		nota.Cobrador = this.repositorio.Vendedor.Nombre;
		nota.IdMoneda = 1;
		nota.Moneda = "pesos";
		nota.Sincronizado = 0;
		
		nota.Abono = 0;
		
		nota.Codigo = this.repositorio.Itinerario.Codigo;
		nota.Cliente = this.repositorio.Itinerario.Cliente;
		nota.RazonSocial = this.repositorio.Itinerario.RazonSocial;
		nota.Direccion = this.repositorio.Itinerario.Direccion;
		
		if (!this.txtEfectivo.getText().toString().equals(""))
		{
			try
			{
				if (this.cbxDocumentos.getSelectedItemPosition() > 0)
				{
					nota.Observacion = cbxDocumentos.getSelectedItem().toString().toUpperCase();
				}
				else
				{
					nota.Observacion = "Abono a factura con referencia";
				}
				
				nota.PagoEfectivo = Double.parseDouble(this.txtEfectivo.getText().toString());
				nota.Subtotal = Float.parseFloat(this.txtEfectivo.getText().toString());
				nota.Total = Double.parseDouble(this.txtEfectivo.getText().toString());
			}
			catch (Exception localException) {}
		}

		if (!this.txtCheque.getText().toString().equals(""))
		{
			try
			{
				if (this.cbxDocumentos.getSelectedItemPosition() > 0)
				{
					nota.Observacion = cbxDocumentos.getSelectedItem().toString().toUpperCase();
				}
				else
				{
					//nota.Observacion = this.txtFolioFactura.getText().toString().toUpperCase();
					nota.Observacion = "Abono a factura con referencia";
				}
		
				nota.PagoCheque = Double.parseDouble(this.txtCheque.getText().toString());
	 
				nota.Subtotal = Float.parseFloat(this.txtCheque.getText().toString());
				nota.Total = Double.parseDouble(this.txtCheque.getText().toString());
	 
				nota.Banco = this.txtBancoCheque.getText().toString();
				nota.FechaCheque = this.txtFechaCheque.getText().toString();
				nota.ChequeNo = this.txtFolioCheque.getText().toString();
			}
			catch (Exception localException1) {}
		}
		
		if (!this.txtDeposito.getText().toString().equals(""))
		{
			try
			{
				if (this.cbxDocumentos.getSelectedItemPosition() > 0)
				{
					nota.Observacion = cbxDocumentos.getSelectedItem().toString().toUpperCase();
				}
				else
				{
					//nota.Observacion = this.txtFolioFactura.getText().toString().toUpperCase();
					nota.Observacion = "Abono a factura con referencia";
				}

				nota.PagoDeposito = Double.parseDouble(this.txtDeposito.getText().toString());
	 
				nota.Subtotal = Float.parseFloat(this.txtDeposito.getText().toString());
				nota.Total = Double.parseDouble(this.txtDeposito.getText().toString());
	 
				nota.Banco = this.txtBancoDeposito.getText().toString();
				nota.FechaCheque = this.txtFechaDeposito.getText().toString();
				nota.ChequeNo = this.txtFolioDeposito.getText().toString(); //ES el numero de folio del deposito
			}
			catch (Exception localException2){}
		}

		nota.FolioDia = repositorio.FolioNota;

		nota.TipoProceso = "SQL";
		nota.AgregarProceso("SQL.Agregar");
		nota.EjecutarProceso();
		
		repositorio.ActualizarImpresora();

		if (this.repositorio.Tipo.equals("CLIENTE"))
		{
			this.repositorio.Cliente = ((BOCliente)this.repositorio.lsCliente.get(this.repositorio.IndiceCliente));
			
			String fechaVisitaDia = Utils.getFechaActual_DD_MM_YYYY();
			this.repositorio.Cliente.Visita = fechaVisitaDia;
			
			this.repositorio.Itinerario.Visita = fechaVisitaDia; //No tenia esto en macsa, le puede hacer falta para refrescar el estado
			
			this.repositorio.Cliente.TipoProceso = "SQL";
			this.repositorio.Cliente.AgregarProceso("SQL.Actualizar");
			this.repositorio.Cliente.EjecutarProceso();
		}
		
		repositorio.oAsyncSincronizar = null;
		repositorio._threadAsyncSincronizar = null;
		
		repositorio.oAsyncSincronizar = new AsyncImprimir(
				ObtenerImprecion(), 
				repositorio.ID_ImprimirAlmacen);
		
		repositorio.oAsyncSincronizar.evento = frmCobro.this.Evento;
		
		repositorio._threadAsyncSincronizar = new Thread(repositorio.oAsyncSincronizar);
		repositorio._threadAsyncSincronizar.start();
	}
	
	@Override
	public void Event(String event, String args)
	{
		this.runOnUiThread(new Runnable()
		{
			  public void run()
			  {
				  Repositorio repositorio = Repositorio.getInstance();
				  
					Toast.makeText(frmCobro.this.getBaseContext(), 
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
	 
	private String ObtenerImprecion()
	{
		String Imprecion = "";
		
		try
		{
			Imprecion = Imprecion + "CENTER\r\n";
			
			Imprecion = Imprecion + "TEXT 5 2 5 20 BACHOCO, S.A. de C.V.\r\n";
			Imprecion = Imprecion + "TEXT 5 2 5 60 BAC800208B25\r\n";
			
			if( repositorio.TITULO1.equals("") )
				Imprecion = Imprecion + "TEXT 7 0 5 110 SUC. LA PAZ, BCS\r\n";
			else
				Imprecion = Imprecion + "TEXT 7 0 5 110 " + repositorio.TITULO1 + "\r\n";

			if( repositorio.DIRECCION1.equals("") )
				Imprecion = Imprecion + "TEXT 7 0 5 135 CALZADA A. OLACHEA #4915\r\n";
			else
				Imprecion = Imprecion + "TEXT 7 0 5 135 " + repositorio.DIRECCION1 + "\r\n";

			if( repositorio.DIRECCION2.equals("") )
				Imprecion = Imprecion + "TEXT 7 0 5 160 COL. PUESTA DEL SOL I C.P. 23090\r\n";
			else
				Imprecion = Imprecion + "TEXT 7 0 5 160 " + repositorio.DIRECCION2 + "\r\n";

			if( repositorio.DIRECCION2.equals("") )
				Imprecion = Imprecion + "TEXT 7 0 5 185 LA PAZ, BCS. MEX TEL: (612)124-01-67\r\n";
			else
				Imprecion = Imprecion + "TEXT 7 0 5 185 " + repositorio.TELEFONO + "\r\n";
			
			Imprecion = Imprecion + "SETMAG 1 2\r\n";
			Imprecion = Imprecion + "TEXT 7 0 5 210 " + this.repositorio.Vendedor.Nombre + " " + repositorio.FolioNota + " \r\n";
			
			Imprecion = Imprecion + "SETMAG 1 1\r\n";
			Imprecion = Imprecion + "LEFT\r\n";
			Imprecion = Imprecion + "TEXT 7 0 5 270 FECHA " + Utils.getFechaActual_DD_MM_YYYY() + "\r\n";
			Imprecion = Imprecion + "RIGHT\r\n";
			Imprecion = Imprecion + "TEXT 7 0 5 270 HORA: " + Utils.getHora_HH_MM_SS() + "  \r\n";
			Imprecion = Imprecion + "CENTER\r\n";
			Imprecion = Imprecion + "SETMAG 1 2\r\n";
			Imprecion = Imprecion + "TEXT 7 0 5 315 PAGO ABONO CUENTA DE CLIENTE\r\n";
			Imprecion = Imprecion + "TEXT 0 3 5 365 ----------------------------------------------------------------------\r\n";
			Imprecion = Imprecion + "LEFT\r\n";
			Imprecion = Imprecion + "TEXT 0 3 5 565 ----------------------------------------------------------------------\r\n";
			Imprecion = Imprecion + "LEFT\r\n";
			Imprecion = Imprecion + "SETMAG 1 1\r\n";
			Imprecion = Imprecion + "TEXT 7 0 5 380 CLIENTE " + this.repositorio.Itinerario.Codigo + "\r\n";
			Imprecion = Imprecion + "TEXT 7 0 5 405 NOMBRE  " + this.repositorio.Itinerario.Cliente + "\r\n";
			Imprecion = Imprecion + "TEXT 7 0 5 430 RFC     " + this.repositorio.Itinerario.RazonSocial + "\r\n";
			Imprecion = Imprecion + "TEXT 7 0 5 455 " + this.repositorio.Itinerario.Direccion + "\r\n";
			Imprecion = Imprecion + "LEFT\r\n";
			Imprecion = Imprecion + "TEXT 7 0 5 500 FACTURA     VIA DE PAGO\r\n";
			Imprecion = Imprecion + "LEFT\r\n";
			Imprecion = Imprecion + "TEXT 7 0 5 540 NUMERO DOC.  BANCO\r\n";
			Imprecion = Imprecion + "RIGHT\r\n";
			Imprecion = Imprecion + "TEXT 7 0 5 500 IMPORTE PAGADO  \r\n";

			double total = 0.0D;

			if ((!this.txtEfectivo.getText().toString().equals("")) && (!this.txtEfectivo.getText().toString().equals("0")))
			{
				total += Double.parseDouble(this.txtEfectivo.getText().toString());
			}

			if ((!this.txtBancoDeposito.getText().toString().equals("")) && (!this.txtBancoDeposito.getText().toString().equals("0")))
			{
				total += Double.parseDouble(this.txtDeposito.getText().toString());
			}

			if ((!this.txtBancoCheque.getText().toString().equals("")) && (!this.txtBancoCheque.getText().toString().equals("0")))
			{
				total += Double.parseDouble(this.txtCheque.getText().toString());
			}

			double total2 = Double.parseDouble(Utils.RedondearValor((float)total));
			String centavo = Utils.Split(Utils.toMoneyFormat((float)total), ".")[1];

			String facturafolio = "";
			int contadorPago = 0;

			if (this.cbxDocumentos.getSelectedItemPosition() > 0)
			{
				this.repositorio.SaldoClienteNota = ((BOSaldoClienteNota)this.repositorio.lsSaldoClienteNota.get(this.cbxDocumentos.getSelectedItemPosition()));
				facturafolio = this.repositorio.SaldoClienteNota.NoDocumento.toUpperCase();
			}
			else
			{
				//facturafolio = this.txtFolioFactura.getText().toString().toUpperCase();
				facturafolio = "Abono a factura con referencia";
			}
			
			if (!this.txtEfectivo.getText().equals(""))
			{
				try
				{
					float valida = Float.parseFloat(this.txtEfectivo.getText().toString());

					Imprecion = Imprecion + "LEFT\r\n";
					Imprecion = Imprecion + "TEXT 7 0 5 " + (580 + contadorPago * 60) + " " + facturafolio + " EFECTIVO\r\n";

					Imprecion = Imprecion + "RIGHT\r\n";
					Imprecion = Imprecion + "TEXT 7 0 5 " + (580 + contadorPago * 60) + " $" + Utils.toMoneyFormat(valida) + "  \r\n";
					contadorPago++;
				}
				catch (Exception localException1) {}
			}

			if (!this.txtCheque.getText().equals(""))
			{
				try
				{
					float valida = Float.parseFloat(this.txtCheque.getText().toString());

					Imprecion = Imprecion + "LEFT\r\n";
					Imprecion = Imprecion + "TEXT 7 0 5 " + (580 + contadorPago * 60) + " " + facturafolio + " CHEQUE\r\n";

					Imprecion = Imprecion + "LEFT\r\n";
					Imprecion = Imprecion + "TEXT 7 0 5 " + (605 + contadorPago * 60) + " " + this.txtFolioCheque.getText().toString() + " " + this.txtBancoCheque.getText().toString() + "\r\n";
					Imprecion = Imprecion + "RIGHT\r\n";
					Imprecion = Imprecion + "TEXT 7 0 5 " + (580 + contadorPago * 60) + " $" + Utils.toMoneyFormat(valida) + "  \r\n";
					contadorPago++;
				}
				catch (Exception localException2) {}
			}

			if (!this.txtDeposito.getText().equals(""))
			{
				try
				{
					float valida = Float.parseFloat(this.txtDeposito.getText().toString());

					Imprecion = Imprecion + "LEFT\r\n";
					Imprecion = Imprecion + "TEXT 7 0 5 " + (580 + contadorPago * 60) + " " + facturafolio + " DEPOSITO\r\n";

					Imprecion = Imprecion + "LEFT\r\n";
					Imprecion = Imprecion + "TEXT 7 0 5 " + (605 + contadorPago * 60) + " " + this.txtFolioDeposito.getText().toString() + " " + this.txtBancoDeposito.getText().toString() + "\r\n";
					Imprecion = Imprecion + "RIGHT\r\n";
					Imprecion = Imprecion + "TEXT 7 0 5 " + (580 + contadorPago * 60) + " $" + Utils.toMoneyFormat(valida) + "  \r\n";
					contadorPago++;
				}
				catch (Exception localException3) {}
			}

			Imprecion = Imprecion + "SETMAG 1 2\r\n";
			Imprecion = Imprecion + "LEFT\r\n";
			Imprecion = Imprecion + "TEXT 7 0 5 " + (570 + contadorPago * 70) + " TOTAL \r\n";
			Imprecion = Imprecion + "RIGHT\r\n";

			Imprecion = Imprecion + "TEXT 7 0 5 " + (570 + contadorPago * 70) + " $" + Utils.leftPad(Utils.toMoneyFormat((float)total), " ", 12) + "  \r\n";
			Imprecion = Imprecion + "SETMAG 1 1\r\n";
			Imprecion = Imprecion + "LEFT\r\n";

			String cantidad2 = Utils.Split(Utils.ConvertNumberToLetter(total2), "CON")[0];

			String monto = centavo + "/100 M.N.";

			if (monto.length() > 40)
			{
				Imprecion = Imprecion + "TEXT 7 0 5 " + (630 + contadorPago * 70) + " " + cantidad2 + "\r\n";

				Imprecion = Imprecion + "LEFT\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + (650 + contadorPago * 70) + " " + centavo + "/100 M.N.\r\n";
			}
			else
			{
				Imprecion = Imprecion + "TEXT 7 0 5 " + (630 + contadorPago * 70) + " " + cantidad2 + " " + centavo + "/100 M.N.\r\n";
			}

			Imprecion = Imprecion + "SETMAG 1 2\r\n";
			Imprecion = Imprecion + "CENTER\r\n";

			Imprecion = Imprecion + "TEXT 7 0 5 " + (810 + contadorPago * 70) + " ACEPTO(AMOS)\r\n";

			Imprecion = Imprecion + "SETMAG 0 0\r\n";
			Imprecion = Imprecion + "FORM\r\n";
			Imprecion = Imprecion + "PRINT\r\n";

			Imprecion = "! 0 0 200 " + (930 + contadorPago * 70) + " 1\r\n" + Imprecion;
		}
		catch (Exception ex)
		{
			Imprecion = "";
		}
	 
		Logg.info(Imprecion);
		
		return Imprecion;
	}
}
