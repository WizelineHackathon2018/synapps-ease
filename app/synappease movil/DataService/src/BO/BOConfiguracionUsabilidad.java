package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOConfiguracionUsabilidad extends BO implements rmsInterface
{
	public int Menu = 0;
	public int Voz = 0;

	public final int ID_Menu = 1;
	public final int ID_Voz = 2;

	public BOConfiguracionUsabilidad(Context context)
	{
		super("BOConfiguracionUsabilidad", context);		  
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
		BOConfiguracionUsabilidad p = this;

		if (idField == ID_Menu)
		{
			s = String.valueOf(p.Menu);
		}
		if (idField == ID_Voz)
		{
			s = String.valueOf(p.Voz);
		}

		return s;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
}
