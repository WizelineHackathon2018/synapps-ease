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
import android.widget.TextView;
import android.widget.Toast;

import com.System.Base.BaseFragmentActivity;
import com.System.Bluetooth.btManager;
import com.System.Dispositivo.Info;
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
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

//Cargamos el estado del cliente y como 
public class frmInformacionCliente extends BaseFragmentActivity implements kIButton
{
	Repositorio repositorio = Repositorio.getInstance();
	ListView list = null;

	static adaptCuestionario adapter = null;

	kButton btnAtras = null;

	kButton btnEst = null;
	kButton btnGau = null;
	
	kButton btnNoCompro = null;
	kButton btnCobro = null;
	kButton btnVentaContado = null;
	kButton btnVentaCredito = null;

	TextView txtCliente = null;
	TextView txtDireccion = null;
	TextView txtVentaContado = null;
	TextView txtVentaCredito = null;
	TextView txtCobro = null;
	
	TextView txtLimiteCredito = null;
	
	TextView txtSaldoVencido = null;
	TextView txtSaldoporVencer = null;
	TextView txtCreditoDisponible = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frminformacioncliente);

		this.repositorio.IniciaVariables(this);
		
	    showman.AgregarElemento("Selecciona la operación a realizar con el cliente, venta de contado, crédito, cobro o si no compra el motivo de no compra, la venta a crédito está sujeta a las políticas de crédito");
	    
	    showman.AgregarElemento(R.id.btnVentaContado);
	    showman.AgregarElemento(R.id.btnVentaCredito);
	    showman.AgregarElemento(R.id.btnCobro);
	    showman.AgregarElemento(R.id.btnNoCompro);
		
		this.repositorio.VentaContado = 0;
		this.repositorio.VentaCredito = 0;
		this.repositorio.Cobro = 0;
		this.repositorio.LimiteCredito = 0;
		repositorio.CreditoPorVencer = 0;
		repositorio.CreditoVencido = 0;
		
		this.repositorio.VerificaCreditoSaldo();
		this.repositorio.ConsultarSaldoClienteNota(2, 4);
		this.repositorio.ObtenerHoraInicio();

		txtCliente = (TextView) findViewById(R.id.txtCliente);
		txtDireccion = (TextView) findViewById(R.id.txtDireccion);
		txtVentaContado = (TextView) findViewById(R.id.txtVentaContado);
		txtVentaCredito = (TextView) findViewById(R.id.txtVentaCredito);
		txtCobro = (TextView) findViewById(R.id.txtCobro);
		txtLimiteCredito = (TextView) findViewById(R.id.txtLimiteCredito);
		txtSaldoVencido = (TextView) findViewById(R.id.txtSaldoVencido);
		txtSaldoporVencer = (TextView) findViewById(R.id.txtSaldoporVencer);
		
		txtCreditoDisponible = (TextView) findViewById(R.id.txtCreditoDisponible);
		
		btnAtras = (kButton) findViewById(R.id.btnAtras);
		
		btnEst = (kButton) findViewById(R.id.btnEst);
		btnGau = (kButton) findViewById(R.id.btnGau);
		
		btnNoCompro = (kButton) findViewById(R.id.btnNoCompro);
		btnCobro = (kButton) findViewById(R.id.btnCobro);
		btnVentaContado = (kButton) findViewById(R.id.btnVentaContado);
		btnVentaCredito = (kButton) findViewById(R.id.btnVentaCredito);
		
		txtCliente.setText("Cliente:" + this.repositorio.Itinerario.Cliente);
		txtDireccion.setText("Dirección:" + this.repositorio.Itinerario.Direccion);
		
		txtVentaContado.setText("Venta Contado:" + Utils.toMoneyFormat((float)this.repositorio.VentaContado));
		txtVentaCredito.setText("Venta Credito:" + Utils.toMoneyFormat((float)this.repositorio.VentaCredito));
		txtCobro.setText("Cobro:" + Utils.toMoneyFormat((float)this.repositorio.Cobro));
		
		if(repositorio.Cliente.RequiereAutorizacion == 1)
		{
			txtLimiteCredito.setText("Tiene Credito: " + this.repositorio.SaldoCliente.LimiteCredito);
		}
		else
		{
			txtLimiteCredito.setText("Tiene Credito: NO");
		}
		
		txtSaldoVencido.setText("Saldo Vencido:" + this.repositorio.SaldoCliente.Vencido);
		txtSaldoporVencer.setText("Saldo por Vencer:" + this.repositorio.SaldoCliente.PorVencer);
		
		double nuevolimite = 0;
		nuevolimite = this.repositorio.LimiteCredito - repositorio.CreditoPorVencer - repositorio.CreditoVencido - repositorio.VentaCredito + repositorio.Cobro;
		if(nuevolimite > this.repositorio.LimiteCredito)
		{
			nuevolimite = this.repositorio.LimiteCredito;
		}
		
		txtCreditoDisponible.setText("Credito Disponible:" + Utils.toMoneyFormat((float) nuevolimite));
		
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		this.repositorio.VerificaCreditoSaldo();

		txtVentaContado.setText("Venta Contado:" + Utils.toMoneyFormat((float)this.repositorio.VentaContado));
		txtVentaCredito.setText("Venta Credito:" + Utils.toMoneyFormat((float)this.repositorio.VentaCredito));
		txtCobro.setText("Cobro:" + Utils.toMoneyFormat((float)this.repositorio.Cobro));

		txtSaldoVencido.setText("Saldo Vencido:" + this.repositorio.SaldoCliente.Vencido);
		txtSaldoporVencer.setText("Saldo por Vencer:" + this.repositorio.SaldoCliente.PorVencer);
		
		
		double nuevolimite = 0;
		nuevolimite = this.repositorio.LimiteCredito - repositorio.CreditoPorVencer - repositorio.CreditoVencido - repositorio.VentaCredito + repositorio.Cobro;
		if(nuevolimite > this.repositorio.LimiteCredito)
		{
			nuevolimite = this.repositorio.LimiteCredito;
		}
		
		txtCreditoDisponible.setText("Credito Disponible:" + Utils.toMoneyFormat((float) nuevolimite));
		
		
		btManager.setBluetooth(false);

		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	@Override
	public void commandAction(kButton b, View v) 
	{
		if(b == btnNoCompro)
		{
			Toast.makeText(v.getContext(), "Especifique el motivo de no compra u operación", Toast.LENGTH_LONG).show();

			Intent sig = new Intent(frmInformacionCliente.this, frmMotivoNoCompra.class);
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
		else if(b == btnCobro)
		{
			if(repositorio.CreditoPorVencer + repositorio.CreditoVencido == 0)
			{
				Toast.makeText(v.getContext(), "No se tiene informacion de saldos, solo podra hacer abonos con referencia", Toast.LENGTH_LONG).show();

				Intent sig = new Intent(frmInformacionCliente.this, frmCobro.class);
				startActivityForResult(sig, 500);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				finish();
			}
			else
			{
				Intent sig = new Intent(frmInformacionCliente.this, frmCobro.class);
				startActivityForResult(sig, 500);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				finish();
			}
		}
		else if(b == btnVentaContado)
		{
			repositorio.Cliente.Discriminante = "EFECTIVO";

			Intent sig = new Intent(frmInformacionCliente.this, frmVenta.class);
			startActivityForResult(sig, 500);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			finish();
		}
		else if(b == btnVentaCredito)
		{
			repositorio.Cliente.Discriminante = "CREDITO";
			
			double nuevolimite = 0;
			nuevolimite = this.repositorio.LimiteCredito - repositorio.CreditoPorVencer - repositorio.CreditoVencido - repositorio.VentaCredito + repositorio.Cobro;
			if(nuevolimite > this.repositorio.LimiteCredito)
			{
				nuevolimite = this.repositorio.LimiteCredito;
			}
			
			if(repositorio.Cliente.RequiereAutorizacion == 1)
			{
				if(nuevolimite > 0)
				{
					Toast.makeText(v.getContext(), "El limite del cliente es de: " + Utils.toMoneyFormat((float) nuevolimite), Toast.LENGTH_LONG).show();
				
					Intent sig = new Intent(frmInformacionCliente.this, frmVenta.class);
					startActivityForResult(sig, 500);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
					finish();
				}
				else
				{
					showHelp(R.id.txtCreditoDisponible, "No tiene suficiente credito para efectuar la venta a credito");
				}
			}
			else
			{
				Toast.makeText(v.getContext(), "No tiene habilitado el credito para efectuar la venta", Toast.LENGTH_LONG).show();
			}

		}
	}
}
