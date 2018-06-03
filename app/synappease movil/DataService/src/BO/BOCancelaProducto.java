package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOCancelaProducto extends BO implements rmsInterface
{
	public int IdVendedor = 0;
	public int IdProducto = 0;
	public int IdLista = 0;
	public String PIN = "";
	public String GUIDV = "";
	public String PrecioUnitario = "";
	public String Cantidad = "";
	public String FolioNota = "";
	public String Fecha = "";
	public String FechaHora = "";

	public final int ID_IdVendedor = 1;
	public final int ID_IdProducto = 2;
	public final int ID_IdLista = 3;
	public final int ID_PIN = 4;
	public final int ID_GUIDV = 5;
	public final int ID_PrecioUnitario = 6;
	public final int ID_Cantidad = 7;
	public final int ID_FolioNota = 8;
	public final int ID_Fecha = 9;
	public final int ID_FechaHora = 10;

	public BOCancelaProducto(Context context)
	{
		super("BOCancelaProducto", context);
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
		BOCancelaProducto p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdVendedor);
		}
		else if (idField == 2)
		{
			s = String.valueOf(p.IdProducto);
		}
		else if (idField == 3)
		{
			s = String.valueOf(p.IdLista);
		}
		else if (idField == 4)
		{
			s = p.PIN;
		}
		else if (idField == 5)
		{
			s = p.GUIDV;
		}
		else if (idField == 6)
		{
			s = p.PrecioUnitario;
		}
		else if (idField == 7)
		{
			s = p.Cantidad;
		}
		else if (idField == 8)
		{
			s = p.FolioNota;
		}
		else if (idField == 9)
		{
			s = p.Fecha;
		}
		else if (idField == 10)
		{
			s = p.FechaHora;
		}

		return s;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}

