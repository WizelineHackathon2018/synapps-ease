package com.comynt.launa.async;

import BO.*;
import com.System.Dispositivo.Info;
import com.comynt.launa.*;

import android.content.Context;
import java.util.Calendar;
import java.util.Date;

public class AsyncLogin extends Thread
{
	private Repositorio repositorio = Repositorio.getInstance();
	public IAsyncEvent evento;
	
	public String User = "";
	public String Password = "";

	Context context;

	public AsyncLogin(Context context)
	{
		this.context = context;
	}

	public void run()
	{
		this.repositorio.User = this.User;
		this.repositorio.Password = this.Password;

		try
		{
			if(Info.ServicioDatosActivos(this.context))
			{
				this.repositorio.ConsultarVendedor(repositorio.ID_WS, repositorio.ID_GuardaMemoria, this.User, this.Password);

				if(repositorio.lsVendedor != null)
				{
					repositorio.Vendedor = (BOVendedor)repositorio.lsVendedor.get(0);
					repositorio.Vendedor.Usuario = this.User;
					repositorio.Vendedor.Password = this.Password;
					this.repositorio.EliminarVendedor();
					this.repositorio.GuardarVendedor();
					
					this.repositorio.ActualizarDatosImpresora();
				}
			}
			else
			{
				this.repositorio.ConsultarVendedor(repositorio.ID_SQL, repositorio.ID_GuardaMemoria,  
				this.User, this.Password);
			}
		}
		catch (Exception localException1) 
		{
			this.repositorio.ConsultarVendedor(repositorio.ID_SQL, repositorio.ID_GuardaMemoria,  
			this.User, this.Password);
		}

		try {
			this.repositorio.ConsultarVendedor(repositorio.ID_SQL, repositorio.ID_GuardaMemoria, this.User, this.Password);
		}
		catch (Exception localException2) 
		{
			int x = 0;
			x++;
		}

		if (this.evento != null)
			this.evento.Event("End", "");
	}
}

