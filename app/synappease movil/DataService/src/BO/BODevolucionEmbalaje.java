package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BODevolucionEmbalaje extends BO implements rmsInterface
{
	public int IdCliente = 0;
	public int IdProducto = 0;
	public String Cantidad = "";
	public String GUIDV = "";
	public int Enviado = 0;

	public final int ID_IdCliente = 1;
	public final int ID_IdProducto = 2;
	public final int ID_Cantidad = 3;
	public final int ID_GUIDV = 4;
	public final int ID_Enviado = 5;

	public BODevolucionEmbalaje(Context context)
	{
		super("BODevolucionEmbalaje", context);
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
		BODevolucionEmbalaje p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdCliente);
		}
		else if (idField == 2)
		{
			s = String.valueOf(p.IdProducto);
		}
		else if (idField == 3)
		{
			s = p.Cantidad;
		}
		else if (idField == 4)
		{
			s = p.GUIDV;
		}
		else if (idField == 5)
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
