package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BODispositivo extends BO implements rmsInterface
{
	public int Indice = 0;
	public String Hora = "";
	public String Datos = "";
	public String NivelBateria = "";
	public String NiveSenial = "";
	public String Fecha = "";

	public final int ID_Indice = 1;
	public final int ID_Hora = 2;
	public final int ID_Datos = 3;
	public final int ID_NivelBateria = 4;
	public final int ID_NiveSenial = 5;
	public final int ID_Fecha = 6;

	public BODispositivo(Context context)
	{
		super("Dispositivo", context);
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
		BODispositivo p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.Indice);
		}
		else if (idField == 2)
		{
			s = p.Hora;
		}
		else if (idField == 3)
		{
			s = p.Datos;
		}
		else if (idField == 4)
		{
			s = p.NivelBateria;
		}
		else if (idField == 5)
		{
			s = p.NiveSenial;
		}
		else if (idField == 6)
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
