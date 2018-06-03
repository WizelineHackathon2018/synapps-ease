package com.comynt.launa;

import com.comynt.launa.async.*;
import com.comynt.launa.frm.*;
import com.comynt.launa.R;

import com.System.Base.BaseFragmentActivity;
import com.System.Dispositivo.Info;
import com.System.Sonidos.Sounds;
import com.System.UI.kToast;
import com.System.Utils.Logg;
import com.System.Utils.Utils;

import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import BO.*;

public class Consumer extends BaseFragmentActivity implements IAsyncEvent
{
	private Repositorio repositorio = Repositorio.getInstance();

	public EditText txtUser = null;
	public EditText txtPassword = null;

	public Button btnInicio = null;
	public Button btnVoz = null;
	public Button btnAtras = null;

	public IAsyncEvent Evento = null;

	private boolean gps_enabled = false;
	private LocationManager lm;
	
	private static final int REQUEST_CODE = 0;
	private DevicePolicyManager mDPM;
	private ComponentName mAdminName;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frmlogin2);
		
		repositorio.IniciaVariables(this);
		Evento = this;
		
		showman.AgregarElemento("Ingresa tu usuario");
		showman.AgregarElemento(R.id.txtUsuario);
		
		showman.AgregarElemento("Ingresa tu contraseña");
		showman.AgregarElemento(R.id.txtPassword);
		
		showman.AgregarElemento("Da clic en siguiente");
		showman.AgregarElemento(R.id.btnAdelante);
		
		try
		{
			repositorio.versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		}
		catch(Exception ex){}
		
		this.setTitle("Smart Plus++ " + repositorio.versionName);
		
		try
		{
			String IME = Info.getPIN(repositorio.context);
			if(!IME.equals(""))
			{
				repositorio.IME = IME;
			}
		}catch(Exception ex){}

		Intent in = new Intent(Consumer.this, ServiceData.class);

		if(!ServiceData.isRunning())
		{
			Logg.info("SERVICIO DATA SERVICE INICIADO");

			Consumer.this.startService(in);
		}
		
		Info.ValidaWifi(this);

		this.repositorio.EnviarDatos();

		//Consultamos la configuracion que se tenga sobre la usabilidad
		repositorio.ConsultarConfiguracionUsabilidad();

		if(repositorio.lsConfiguracionUsabilidad != null)
		{
			if(repositorio.lsConfiguracionUsabilidad.size() > 0)
			{
				repositorio.ConfiguracionUsabilidad = (BOConfiguracionUsabilidad)repositorio.lsConfiguracionUsabilidad.get(0);

				if(repositorio.ConfiguracionUsabilidad.Menu == 0)
				{
					RadioButton rbDerecha = (RadioButton) findViewById(R.id.rbDerecha);
					rbDerecha.setChecked(true);
				}
				else
				{
					RadioButton rbIzquierda = (RadioButton) findViewById(R.id.rbIzquierda);
					rbIzquierda.setChecked(true);
				}

				if(repositorio.ConfiguracionUsabilidad.Voz == 0)
				{
					CheckBox rbDerecha = (CheckBox) findViewById(R.id.ckVoz);
					rbDerecha.setChecked(false);
				}
				else
				{
					CheckBox rbDerecha = (CheckBox) findViewById(R.id.ckVoz);
					rbDerecha.setChecked(true);
				}
			}
		}

		txtUser = (EditText) findViewById(R.id.txtUsuario);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
		
		btnInicio = (Button) findViewById(R.id.btnAdelante);
		btnVoz = (Button) findViewById(R.id.btnVoz);
		btnAtras = (Button) findViewById(R.id.btnAtras);

		btnAtras.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				//Home
				txtUser.setText("");
				txtPassword.setText("");

				try
				{
					Intent homeIntent = new Intent(Intent.ACTION_MAIN);
					homeIntent.addCategory(Intent.CATEGORY_HOME);
					homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(homeIntent);
				}
				catch(Exception ex){}
			}
		});

		btnVoz.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				showman.showHelp();
			}
		});

		btnInicio.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				btnInicio.playSoundEffect(0);

				if (!getGPSenable())
				{
					Sounds.PlayFailed(repositorio.context, false);

					checkGps();
					
					repositorio.GuardarEvento("GPS Desactivado", Consumer.this.getBaseContext());

					Toast.makeText(Consumer.this.getBaseContext(), "Evento registrado, GPS Desactivado, Hora: " + repositorio.Evento.FechaHora, Toast.LENGTH_SHORT).show();
				}
				else
				{
					mDPM = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
					mAdminName = new ComponentName(v.getContext(), DeviceAdmin.class);

					//if (!mDPM.isAdminActive(mAdminName))
					if(false)
					{
						Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
						intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mAdminName);
						intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Click on Activate button to secure your application.");
						startActivityForResult(intent, REQUEST_CODE);
					}
					else
					{
						if ((txtUser.getText().toString().trim().equals("")) || (txtPassword.getText().toString().trim().equals("")))
						{
							showHelp(R.id.txtPassword, "Usuario/Password requerido");
						}
						else
						{
							if(!repositorio.estaautenticando)
							{
								dialog = new ProgressDialog(v.getContext());
								dialog.setMessage("Por favor espere autenticando...");
					            dialog.setCancelable(true);
					            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					            dialog.show();
								
								Sounds.PlaySuccess(repositorio.context, false);
								
								if (!Info.ServicioDatosActivos(v.getContext()))
								{
									Toast.makeText(v.getContext(), "No se detecta red Telefónica o WIFI activa, por favor revise la cobertura, autentificando localmente", 
											Toast.LENGTH_SHORT).show();
								}
								
								Toast.makeText(Consumer.this.getBaseContext(), "Autentificando el usuario, por favor espere", Toast.LENGTH_LONG).show();
								
								AsyncLogin oAsyncSincronizar = new AsyncLogin(Consumer.this.getBaseContext());
								
								oAsyncSincronizar.evento = Consumer.this.Evento;
								
								oAsyncSincronizar.User = txtUser.getText().toString();
								oAsyncSincronizar.Password = txtPassword.getText().toString();
								
								Thread _threadAsyncSincronizar = new Thread(oAsyncSincronizar);
								_threadAsyncSincronizar.start();
							}
							else
							{
								Sounds.PlayFailed(repositorio.context, false);
								
								Toast.makeText(v.getContext(), "Ya se esta ejecutando el proceso de verificación, por favor espere", 
									Toast.LENGTH_SHORT).show();
								
							}
						}
					}
				}
			}
		});

		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		if(repositorio.estaautenticando)
		{
			if(dialog == null)
			{
				dialog = new ProgressDialog(this);
				dialog.setMessage("Por favor espere autenticando...");
	            dialog.setCancelable(true);
	            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	            
	            dialog.setIndeterminateDrawable(getWallpaper());
	            dialog.setProgressDrawable(getWallpaper());
	            
	            dialog.show();
			}
		}
		
		//kToast.showMessage(Consumer.this.getBaseContext(), "Hoa jajaja " + Utils.getFechaHoraActual(), Toast.LENGTH_SHORT, this);
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

	public boolean getGPSenable()
	{
		gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		return gps_enabled;
	}

	public void checkGps() 
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("GPS Desactivado");  // GPS not found
		builder.setMessage("Debe activarlo para continuar, precione si, y activelo en la parte superior, Ubicación -> SI, si tiene dudas comuniquese con soporte");
		builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialogInterface, int i) 
			{
				startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
			}
		});
		builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialogInterface, int i) 
			{
				if (!getGPSenable())
				{
					Toast.makeText(Consumer.this.getBaseContext(), "Debe habilitar el GPS para continuar", Toast.LENGTH_LONG).show();            		
				}
			}
		});

		builder.create().show();
		return;
	}
	
	@Override
	public void Event(String event, String args)
	{
		Repositorio repositorio = Repositorio.getInstance();
		
		if (event.equals("End"))
		{
			repositorio.estaautenticando = false;
			
			if (repositorio.lsVendedor != null)
			{
				if (repositorio.lsVendedor.size() > 0)
				{
					repositorio.Vendedor = (BOVendedor)repositorio.lsVendedor.get(0);
					
					if((repositorio.Vendedor.Usuario.equals(txtUser.getText().toString().trim())) 
							&& (repositorio.Vendedor.Password.equals(txtPassword.getText().toString().trim())))
					{
						IniciarLogin();
					}
					else
					{
						this.runOnUiThread(new Runnable() 
						{
							public void run() 
							{
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
								
								Sounds.PlayFailed(Consumer.this.getBaseContext(), false);
								
								showHelp(R.id.txtPassword, "El usuario o password es incorrecto");
							}
						});
					}
				}
				else
				{
					this.runOnUiThread(new Runnable() 
					{
						public void run() 
						{
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
							
							Sounds.PlayFailed(Consumer.this.getBaseContext(), false);
							
							showHelp(R.id.txtPassword, "El usuario o password es incorrecto");
						}
					});
				}
			}
			else if(!this.repositorio.Vendedor.Error.equals(""))
			{
				this.runOnUiThread(new Runnable() {
					public void run() 
					{
						Repositorio repositorio = Repositorio.getInstance();
						
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
						
						Sounds.PlayFailed(Consumer.this.getBaseContext(), false);
						
						showHelp(R.id.txtPassword, "Error de comunicación con el servidor, por favor revise la red celular o WIFI, Error: ");						  
						
						Toast.makeText(Consumer.this.getBaseContext(), repositorio.Vendedor.Error, Toast.LENGTH_SHORT).show();
					}
				});
			}
			else
			{
				this.runOnUiThread(new Runnable()
				{
					public void run() 
					{
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
						
						Sounds.PlayFailed(Consumer.this.getBaseContext(), false);
						
						showHelp(R.id.txtPassword, "El usuario o password es incorrecto");
					}
				});
			}
		}
	}
	
	private void IniciarLogin()
	{
		this.repositorio.Vendedor = ((BOVendedor)this.repositorio.lsVendedor.get(0));
		
		if (this.repositorio.Vendedor.IdVendedor > 0)
		{
			this.runOnUiThread(new Runnable() {
				public void run() {
					
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
					
					//Guardamos los valores para la configuracion
					Sounds.PlaySuccess(Consumer.this.getBaseContext(), false);
					
					//Guardamos los valores para la configuracion
					RadioButton rbDerecha = (RadioButton) findViewById(R.id.rbDerecha);
					CheckBox ckVoz = (CheckBox) findViewById(R.id.ckVoz);
					
					if(rbDerecha.isChecked())
					{
						repositorio.ConfiguracionUsabilidad.Menu = 0;
					}
					else
					{
						repositorio.ConfiguracionUsabilidad.Menu = 1;
					}
					
					if(ckVoz.isChecked())
					{
						repositorio.ConfiguracionUsabilidad.Voz = 1;
					}
					else
					{
						repositorio.ConfiguracionUsabilidad.Voz = 0;
					}
					
					repositorio.EliminarConfiguracionUsabilidad();
					repositorio.GuardarConfiguracionUsabilidad();
					
					Toast.makeText(Consumer.this.getBaseContext(), "Bienvenido " + repositorio.Vendedor.Nombre, Toast.LENGTH_LONG).show();
					
					//kToast.showMessage(Consumer.this.getBaseContext(), "Bienvenido " + repositorio.Vendedor.Nombre, Toast.LENGTH_SHORT, this);
					
					Intent sig = new Intent(Consumer.this, frmMenu.class);
					startActivityForResult(sig, 500);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				}
			});
		}
		else
		{
			this.runOnUiThread(new Runnable() {
				public void run() {
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
					
					Sounds.PlayFailed(Consumer.this.getBaseContext(), false);

					showHelp(R.id.txtPassword, "El usuario o password es incorrecto");
				}
			});
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}	

}
