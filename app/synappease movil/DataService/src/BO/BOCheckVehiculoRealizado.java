package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOCheckVehiculoRealizado extends BO implements rmsInterface
{
	public int IdCheckVehiculo = 0;
	public int IdVehiculo = 0;
	public int IdVendedor = 0;
	public String PIN = "";
	public String Fecha = "";
	public String Valor = "";

	public final int ID_IdCheckVehiculo = 1;
	public final int ID_IdVehiculo = 2;
	public final int ID_IdVendedor = 3;
	public final int ID_PIN = 4;
	public final int ID_Fecha = 5;
	public final int ID_Valor = 6;

	public BOCheckVehiculoRealizado(Context context)
	{
		super("BOCheckVehiculoRealizado", context);
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
		BOCheckVehiculoRealizado p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdCheckVehiculo);
		}
		else if (idField == 2)
		{
			s = String.valueOf(p.IdVehiculo);
		}
		else if (idField == 3)
		{
			s = String.valueOf(p.IdVendedor);
		}
		else if (idField == 4)
		{
			s = p.PIN;
		}
		else if (idField == 5)
		{
			s = p.Fecha;
		}
		else if (idField == 6)
		{
			s = p.Valor;
		}

		return s;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}

