package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOEstadoPrecios extends BO implements rmsInterface
{
	public int Folio = 0;
	public String Fecha = "";

	public final int ID_Folio = 1;
	public final int ID_Fecha = 2;

	public BOEstadoPrecios(Context context)
	{
		super("BOEstadoPrecios", context);
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
		BOEstadoPrecios p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.Folio);
		}
		else if (idField == 2)
		{
			s = p.Fecha;
		}

		return s;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
