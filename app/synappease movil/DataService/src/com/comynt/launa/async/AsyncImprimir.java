package com.comynt.launa.async;

import android.os.Looper;

import com.System.Bluetooth.btManager;
import com.System.Printer.ZebraBlueTooth;
import com.System.Utils.Logg;
import com.comynt.launa.Repositorio;
import com.zebra.android.comm.BluetoothPrinterConnection;
import com.zebra.android.comm.TcpPrinterConnection;
import com.zebra.android.comm.ZebraPrinterConnection;
import com.zebra.android.comm.ZebraPrinterConnectionException;

import com.zebra.android.printer.PrinterLanguage;
import com.zebra.android.printer.ZebraPrinter;
import com.zebra.android.printer.ZebraPrinterFactory;
import com.zebra.android.printer.ZebraPrinterLanguageUnknownException;

public class AsyncImprimir extends Thread
{
	private Repositorio repositorio = Repositorio.getInstance();
	private ZebraPrinter printer;
	private String Imprecion = "";
	private int Tipo = -1;

	public IAsyncEvent evento;

	public AsyncImprimir(String Imprecion, int Tipo)
	{
		this.Imprecion = Imprecion;
		this.Tipo = Tipo;
	}

	public void run()
	{
		try {
			Thread.sleep(2000);
		}
		catch (Exception e){ }

		try
		{
			Looper.prepare();

			doImprecion();

			if (this.evento != null)
				this.evento.Event("End", "");

			Looper.loop();
			Looper.myLooper().quit();
		}
		catch(Exception ex)
		{
			if (this.evento != null)
				this.evento.Event("End", "");

			Looper.loop();
			Looper.myLooper().quit();
		}
	}

	private void doImprecion() 
	{
		if (!this.Imprecion.equals(""))
		{
			if (Tipo == repositorio.ID_ImprimirVentaCredito) 
			{
				try
				{
					Logg.info("Conectando a la impresora " + this.repositorio.Vendedor.IDImpresora);
					this.printer = ZebraBlueTooth.connect(this.repositorio.Vendedor.IDImpresora);

					Logg.info("Conectado a la impresora " + this.repositorio.Vendedor.IDImpresora);

					Logg.info("Enviando impreción");
					byte[] configLabel = getConfigLabel();

					//ZebraBlueTooth.getConnection().write(configLabel);
					ZebraBlueTooth.getConnection().write(configLabel);  //Se comento este para que solo se imprimia uno para prueba

					Logg.info("Impreción enviada");
				}
				catch (Exception ex)
				{
					Logg.info("Error al imprimir " + ex.getMessage());
				}
				finally
				{
					ZebraBlueTooth.disconnect();
					btManager.setBluetooth(false);
				}
			}

			else if(Tipo == repositorio.ID_ImprimirAlmacen) 
			{
				try
				{
					Logg.info("Conectando a la impresora " + this.repositorio.Vendedor.IDImpresora);
					this.printer = ZebraBlueTooth.connect(this.repositorio.Vendedor.IDImpresora);
					Logg.info("Conectado a la impresora " + this.repositorio.Vendedor.IDImpresora);

					Logg.info("Enviando impreción");
					byte[] configLabel = getConfigLabel();

					ZebraBlueTooth.getConnection().write(configLabel);

					Logg.info("Impreción enviada");
				}
				catch (Exception ex)
				{
					Logg.info("Error al imprimir " + ex.getMessage());
				}
				finally
				{
					ZebraBlueTooth.disconnect();
					btManager.setBluetooth(false);
				}
			}

			else if(Tipo == repositorio.ID_ImprimirLiquidacion)
			{
				try
				{
					Logg.info("Conectando a la impresora " + this.repositorio.Vendedor.IDImpresora);
					this.printer = ZebraBlueTooth.connect(this.repositorio.Vendedor.IDImpresora);
					Logg.info("Conectado a la impresora " + this.repositorio.Vendedor.IDImpresora);

					Logg.info("Enviando impreción");
					byte[] configLabel = getConfigLabel();

					ZebraBlueTooth.getConnection().write(configLabel);

					Logg.info("Impreción enviada");
				}
				catch (Exception ex)
				{
					Logg.info("Error al imprimir " + ex.getMessage());
				}
				finally
				{
					ZebraBlueTooth.disconnect();
					btManager.setBluetooth(false);
				}
			}

			else if(Tipo == repositorio.ID_ReImprimirVenta)
			{
				try
				{
					Logg.info("Conectando a la impresora " + this.repositorio.Vendedor.IDImpresora);
					this.printer = ZebraBlueTooth.connect(this.repositorio.Vendedor.IDImpresora);
					Logg.info("Conectado a la impresora " + this.repositorio.Vendedor.IDImpresora);

					Logg.info("Enviando impreción");
					byte[] configLabel = getConfigLabel();

					ZebraBlueTooth.getConnection().write(configLabel);

					Logg.info("Impreción enviada");
				}
				catch (Exception ex)
				{
					Logg.info("Error al imprimir " + ex.getMessage());
				}
				finally
				{
					ZebraBlueTooth.disconnect();
					btManager.setBluetooth(false);
				}
			}

			else //Es venta contado
			{
				try
				{
					Logg.info("Conectando a la impresora " + this.repositorio.Vendedor.IDImpresora);

					this.printer = ZebraBlueTooth.connect(this.repositorio.Vendedor.IDImpresora);

					Logg.info("Conectado a la impresora " + this.repositorio.Vendedor.IDImpresora);

					Logg.info("Enviando impreción");
					byte[] configLabel = getConfigLabel();

					//ZebraBlueTooth.getConnection().write(configLabel);
					ZebraBlueTooth.getConnection().write(configLabel);

					Logg.info("Impreción enviada");
				}
				catch (Exception ex)
				{
					Logg.info("Error al imprimir " + ex.getMessage());
				}
				finally
				{
					ZebraBlueTooth.disconnect();
					btManager.setBluetooth(false);
				}
			}

		}
	}

	private byte[] getConfigLabel()
	{
		PrinterLanguage printerLanguage = this.printer.getPrinterControlLanguage();

		byte[] configLabel = (byte[])null;

		//     if (printerLanguage == PrinterLanguage.ZPL)
		//     {
		//       configLabel = "^XA^FO17,16^GB379,371,8^FS^FT65,255^A0N,135,134^FDTEST^FS^XZ".getBytes();
		//     }
		//     else if (printerLanguage == PrinterLanguage.CPCL)
		//     {
		//       configLabel = this.Imprecion.getBytes();
		//     }

		Logg.info("PrinterLanguage.CPCL");
		configLabel = this.Imprecion.getBytes();

		return configLabel;
	}
}
