package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;
import com.System.RMS.rmsInterface;
import android.content.Context;

public class BOEvento extends BO implements rmsInterface
{
	public String GUIDE = "";
	public String FechaHora = "";
	public String PIN = "";
	public String Event = "";
	public String GPS = "";
	public String Bateria = "";
	public int IdVendedor = 0;
	public String TipoDispositivo = "";
	public String Version = "";
	
	public final int ID_GUIDE = 1;
	public final int ID_FechaHora = 2;
	public final int ID_PIN = 3;
	public final int ID_Event = 4;
	public final int ID_GPS = 5;
	public final int ID_Bateria = 6;
	public final int ID_IdVendedor = 7;
	public final int ID_TipoDispositivo = 8;
	public final int ID_Version = 9;

	public BOEvento(Context context)
	{
		super("BOEvento", context);
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
		BOEvento p = this;

		if (idField == ID_GUIDE)
		{
			s = p.GUIDE;
		}
		else if (idField == ID_FechaHora)
		{
			s = p.FechaHora;
		}
		else if (idField == ID_PIN)
		{
			s = p.PIN;
		}
		else if (idField == ID_Event)
		{
			s = p.Event;
		}
		else if (idField == ID_GPS)
		{
			s = p.GPS;
		}
		else if (idField == ID_Bateria)
		{
			s = p.Bateria;
		}
		else if (idField == ID_IdVendedor)
		{
			s = String.valueOf(p.IdVendedor);
		}
		else if (idField == ID_TipoDispositivo)
		{
			s = p.TipoDispositivo;
		}
		else if (idField == ID_Version)
		{
			s = p.Version;
		}

		return s;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
