package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOCellID extends BO implements rmsInterface
{
	public String mcc = "";    //Mobile Country Code
	public String mnc = "";    //mobile network code
	public int cellid = 0; //Cell ID
	public int lac = 0;    //Location Area Code
	public String result = ""; //Resultado del request
	public String FechaHora = "";
	public int Intento = 0;

	public String Bateria = "";
	public String TipoAviso = "";
	public String Wifi = "";

	public final int ID_mcc = 1;
	public final int ID_mnc = 2;
	public final int ID_cellid = 3;
	public final int ID_lac = 4;
	public final int ID_result = 5;
	public final int ID_FechaHora = 6;
	public final int ID_Intento = 7;

	public final int ID_Bateria = 8;
	public final int ID_TipoAviso = 9;
	public final int ID_Wifi = 10;

	public BOCellID(Context context)
	{
		super("BOCellID", context);
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
		BOCellID p = this;

		if (idField == ID_mcc)
		{
			s = mcc;
		}
		else if (idField == ID_mnc)
		{
			s = p.mnc;
		}
		else if (idField == ID_cellid)
		{
			s = String.valueOf(p.cellid);
		}
		else if (idField == ID_lac)
		{
			s = String.valueOf(p.lac);
		}
		else if (idField == ID_result)
		{
			s = p.result;
		}
		else if (idField == ID_Intento)
		{
			s = String.valueOf(p.Intento);
		}
		else if (idField == ID_FechaHora)
		{
			s = p.FechaHora;
		}

		else if (idField == ID_Bateria)
		{
			s = p.Bateria;
		}
		else if (idField == ID_TipoAviso)
		{
			s = p.TipoAviso;
		}
		else if (idField == ID_Wifi)
		{
			s = p.Wifi;
		}

		return s;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
