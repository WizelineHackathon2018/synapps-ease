package BO;

import java.io.*;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOGps extends BO implements rmsInterface
{
	//Variables publicas
	public String Coordenadas = "";
	public String FechaHora = "";
	public String Bateria = "";
	public String Velocidad = "";
	public String TipoAviso = "";
	public String Wifi = "";
	//Variables publicas

	//Enumeraciones
	public final int ID_Coordenadas = 1;
	public final int ID_FechaHora = 2;
	public final int ID_Bateria = 3;
	public final int ID_Velocidad = 4;
	public final int ID_TipoAviso = 5;
	public final int ID_Wifi = 6;
	//Enumeraciones

	public BOGps(Context context)
	{
		super("BOGps", context);
	}

	//Implementaciones de los metodos de WS
	//***************************************************************************************************************************************************
	public void AsignarValoresBOProceso()
	{
	}

	public List<BO> AsignarValoresProcesoBO(SoapObject wsColeccion)
	{
		return null;
	}
	//***************************************************************************************************************************************************
	//Implementaciones de los metodos de WS

	@Override
	public String getFieldValue(int idField)
	{
		String s = "";
		BOGps p = this;

		if (idField == this.ID_Coordenadas)
		{
			s = p.Coordenadas;
		}
		else if (idField == this.ID_FechaHora)
		{
			s = p.FechaHora;
		}
		else if (idField == this.ID_Bateria)
		{
			s = p.Bateria;
		}
		else if (idField == this.ID_Velocidad)
		{
			s = p.Velocidad;
		}
		else if (idField == this.ID_TipoAviso)
		{
			s = p.TipoAviso;
		}
		else if (idField == this.ID_Wifi)
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
