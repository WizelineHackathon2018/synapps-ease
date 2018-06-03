package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOUrl extends BO implements rmsInterface
{
	public String Nombre;

	public final int ID_Nombre = 1;

	public BOUrl(Context context)
	{
		super("BOUrl", context);
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
		BOUrl p = this;

		if (idField == 1)
		{
			s = p.Nombre;
		}

		return s;	  
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
