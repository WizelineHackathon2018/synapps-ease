package com.comynt.launa.async;

import java.util.List;

import BO.*;
import android.content.Context;
import com.System.Dispositivo.Info;
import com.System.Utils.Logg;
import com.System.Utils.Utils;
import com.comynt.launa.Repositorio;

public class AsyncEnviarDatosPendientes extends Thread
{
	private Repositorio repositorio;
	Context context;
	
	public  int contador = 0;
	public int contadorLogger = 0;
	
	private int contadorCancela = 0;
	private int indicelogger = 0;

	public AsyncEnviarDatosPendientes(Context context, Repositorio repositorio)
	{
		this.context = context;
		this.repositorio = repositorio;
	}

	public void run()
	{
		while (true)
		{
			if (!this.repositorio.estaVendiendo)
			{
				if (this.contador > 120)
				{
					if (Info.ServicioDatosActivos(this.context))
					{
						if (!this.repositorio.estaVendiendo)
						{
							this.repositorio.EnviarPrecuestionarioCliente();
						}

						if (!this.repositorio.estaVendiendo)
						{
							this.repositorio.EnviarNuevoCliente();
						}

						if (!this.repositorio.estaVendiendo)
						{
							this.repositorio.EnviarPosicionPendiente();
						}

						if (!this.repositorio.estaVendiendo)
						{
							this.repositorio.EnviarChecklistVehiculo();
						}

						if (!this.repositorio.estaVendiendo)
						{
							this.repositorio.EnviarDatosCliente();
						}

						if(Info.conexionWifi2(this.context))
						{
							BOFoto Foto = new BOFoto(this.context);
							Foto.TipoProceso = "SQL";
							Foto.AgregarProceso("SQL.Consultar");
							Foto.AgregarParametro("idSortField", String.valueOf(Foto.ID_FechaHora));
							Foto.AgregarParametro("idFilterField", String.valueOf(Foto.ID_Enviado));
							Foto.AgregarParametro("filtro", "0");
							List<BO> lsFoto = Foto.ConsultarProceso();

							if(lsFoto != null)
							{
								for(int x = 0; x < lsFoto.size(); x++)
								{
									if(Info.ServicioDatosActivos(this.context))
									{
										Foto = (BOFoto)lsFoto.get(x);
										repositorio.EnviarImagenes(Foto);
									}
								}
							}
						}

						this.contador = 0;
						Logg.info("Enviar datos");
					}
					else
					{
						this.contador = 60;
					}
				}
				else
				{
					this.contador += 1;
				}
			}
			else
			{
				this.contador = 0;
			}

			if (this.contadorLogger > 120)
			{
				this.contadorLogger = 0;
				this.indicelogger += 1;
				
				if (Info.ServicioDatosActivos(this.context))
				{
					this.repositorio.CommitCancela();
					this.repositorio.EnviarVenta();
				}
			}
			else
			{
				this.contadorLogger += 1;
			}
			
			try
			{
				Thread.sleep(1000L);
			}
			catch (Exception localException){}
		}
	}
}

