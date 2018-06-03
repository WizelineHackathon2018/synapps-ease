package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOError extends BO implements rmsInterface
{
	public String Detalle = "";
	public String Os = "";
	public int Sdk = 0;
	public String Dev = "";
	public String Imei = "";
	public String Imsi = "";
	public String Mac = "";
	public String Mail = "";
	public String Simser = "";
	
	public String Tipo = "";
	public String FechaHora = "";
	
	public final int ID_Detalle = 1;
	public final int ID_Os = 2;
	public final int ID_Sdk = 3;
	public final int ID_Dev = 4;
	public final int ID_Imei = 5;
	public final int ID_Imsi = 6;
	public final int ID_Mac = 7;
	public final int ID_Mail = 8;
	public final int ID_Simser = 9;
	
	public final int ID_Tipo = 10;
	public final int ID_FechaHora = 11;

	public BOError(Context context)
	{
		super("BOError2", context);
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
		BOError p = this;
		
		if (idField == 1)
		{
			s = p.Detalle;
		}
		else if (idField == ID_Os)
		{
			s = p.Os;
		}
		else if (idField == ID_Sdk)
		{
			s = String.valueOf(p.Sdk);
		}
		else if (idField == ID_Dev)
		{
			s = p.Dev;
		}
		else if (idField == ID_Imei)
		{
			s = p.Imei;
		}
		else if (idField == ID_Imsi)
		{
			s = p.Imsi;
		}
		else if (idField == ID_Mac)
		{
			s = p.Mac;
		}
		else if (idField == ID_Mail)
		{
			s = p.Mail;
		}
		else if (idField == ID_Simser)
		{
			s = p.Simser;
		}
		
		else if (idField == ID_Tipo)
		{
			s = p.Tipo;
		}
		else if (idField == ID_FechaHora)
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
