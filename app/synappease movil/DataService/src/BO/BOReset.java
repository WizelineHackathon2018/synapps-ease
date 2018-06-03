package BO;

import java.io.*;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOReset extends BO implements rmsInterface
{
	//Variables publicas
	public String Fecha;
	//Variables publicas

	//Enumeraciones
	public final int ID_Fecha = 1;
	//Enumeraciones

	public BOReset(Context context)
	{
		super("BOReset", context);
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
		BOReset p = this;

		if (idField == this.ID_Fecha)
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
