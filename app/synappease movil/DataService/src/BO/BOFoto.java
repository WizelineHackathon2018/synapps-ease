package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOFoto extends BO implements rmsInterface
{
	public int IdVendedor = 0;
	public int IdVehiculo = 0;
	public String PIN = "";
	public String Nombre = "";
	public String Fecha = "";
	public String FechaHora = "";

	public String Tipo = "";
	public String GUIDM = "";
	public String Ruta = "";
	public int Enviado = 0;

	public final int ID_IdVendedor = 1;
	public final int ID_IdVehiculo = 2;
	public final int ID_PIN = 3;
	public final int ID_Nombre = 4;
	public final int ID_Fecha = 5;
	public final int ID_FechaHora = 6;

	public final int ID_Tipo = 7;
	public final int ID_GUIDM = 8;
	public final int ID_Ruta = 9;
	public final int ID_Enviado = 10;

	public BOFoto(Context context)
	{
		super("BOFoto2", context);
	}

	public void AsignarValoresBOProceso()
	{
	}

	public List<BO> AsignarValoresProcesoBO(SoapObject wsColeccion)
	{
		return null;
	}

	@Override
	public String getFieldValue(int idField)
	{
		String s = "";
		BOFoto p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdVendedor);
		}
		else if (idField == 2)
		{
			s = String.valueOf(p.IdVehiculo);
		}
		else if (idField == 3)
		{
			s = p.PIN;
		}
		else if (idField == 4)
		{
			s = p.Nombre;
		}
		else if (idField == 5)
		{
			s = p.Fecha;
		}
		else if (idField == 6)
		{
			s = p.FechaHora;
		}
		else if (idField == 7)
		{
			s = p.Tipo;
		}
		else if (idField == 8)
		{
			s = p.GUIDM;
		}
		else if (idField == ID_Ruta)
		{
			s = p.Ruta;
		}
		else if (idField == ID_Enviado)
		{
			s = String.valueOf(p.Enviado);
		}

		return s;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
