package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOCheckVehiculo extends BO implements rmsInterface
{
	public int IdCheckVehiculo = 0;
	public int Tipo = 0;
	public String Texto = "";
	public String Respuesta = "";

	public final int ID_IdCheckVehiculo = 1;
	public final int ID_Tipo = 2;
	public final int ID_Texto = 3;
	public final int ID_Respuesta = 4;

	public BOCheckVehiculo(Context context)
	{
		super("CheckVehiculo", context);
	}

	public void AsignarValoresBOProceso()
	{
	}

	public List<BO> AsignarValoresProcesoBO(SoapObject wsColeccion)
	{
		if (wsColeccion != null)
		{
			int size = wsColeccion.getPropertyCount();
			List<BO> listaUrlManager = new ArrayList<BO>();

			for (int i = 0; i < size; i++)
			{
				SoapObject soapPart = (SoapObject)wsColeccion.getProperty(i);

				Object ooIdCheckVehiculo = soapPart.getProperty("IdCheckVehiculo");
				Object ooTipo = soapPart.getProperty("Tipo");
				Object ooTexto = soapPart.getProperty("Texto");
				Object ooRespuesta = soapPart.getProperty("Respuesta");

				BOCheckVehiculo CheckVehiculo = new BOCheckVehiculo(this.context);

				if (ooIdCheckVehiculo != null)
				{
					CheckVehiculo.IdCheckVehiculo = Integer.parseInt(ooIdCheckVehiculo.toString());
				}
				if (ooTipo != null)
				{
					CheckVehiculo.Tipo = Integer.parseInt(ooTipo.toString());
				}
				if (ooTexto != null)
				{
					CheckVehiculo.Texto = ooTexto.toString();
				}
				if (ooRespuesta != null)
				{
					CheckVehiculo.Respuesta = getValorCadena(ooRespuesta.toString());
				}

				listaUrlManager.add(CheckVehiculo);
			}
			if(listaUrlManager.size() == 0)
			{
				return null;
			}
			else
			{
				return listaUrlManager;
			}
		}

		return null;
	}

	@Override
	public String getFieldValue(int idField)
	{
		String s = "";
		BOCheckVehiculo p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdCheckVehiculo);
		}
		else if (idField == 2)
		{
			s = String.valueOf(p.Tipo);
		}
		else if (idField == 3)
		{
			s = p.Texto;
		}
		else if (idField == 4)
		{
			s = p.Respuesta;
		}

		return s;  
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}

