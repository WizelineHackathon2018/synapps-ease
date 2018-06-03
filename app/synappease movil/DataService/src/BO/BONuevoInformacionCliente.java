package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BONuevoInformacionCliente extends BO implements rmsInterface
{
	public int IdCliente = 0;
	public int IdCuestionarioCliente = 0;
	public String Respuesta = "";
	public int Enviar = 0;
	public String GUIDV = "";

	public final int ID_IdCliente = 1;
	public final int ID_IdCuestionarioCliente = 2;
	public final int ID_Respuesta = 3;
	public final int ID_Enviar = 4;
	public final int ID_GUIDV = 5;

	public BONuevoInformacionCliente(Context context)
	{
		super("BONuevoInformacionCliente", context);
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
		BONuevoInformacionCliente p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdCliente);
		}
		else if (idField == 2)
		{
			s = String.valueOf(p.IdCuestionarioCliente);
		}
		else if (idField == 3)
		{
			s = p.Respuesta;
		}
		else if (idField == 4)
		{
			s = String.valueOf(p.Enviar);
		}
		else if (idField == 5)
		{
			s = p.GUIDV;
		}

		return s;	  
	}

	@Override
	public String toString() 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
