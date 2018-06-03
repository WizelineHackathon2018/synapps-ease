package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOFechaActualizacion extends BO implements rmsInterface
{
	//Variables publicas
	public String FechaModificacion = "";
	//Variables publicas

	//Enumeraciones
	public final int ID_FechaModificacion = 1;
	//Enumeraciones

	public BOFechaActualizacion(Context context)
	{
		super("BOFechaActualizacion", context);
	}

	//Implementaciones de los metodos de WS
	//***************************************************************************************************************************************************
	public void AsignarValoresBOProceso()
	{
	}

	public List<BO> AsignarValoresProcesoBO(SoapObject wsColeccion)
	{
		if (wsColeccion != null)
		{
			int size = wsColeccion.getPropertyCount();
			List<BO> empList = new ArrayList<BO>();

			for(int i = 0; i < size; i++)
			{
				SoapObject soapPart = (SoapObject)wsColeccion.getProperty(i);

				//Aca obtenemos las propiedades
				Object ooFechaModificacion = (Object)soapPart.getProperty("FechaModificacion");                
				BOFechaActualizacion gpsConfigura = new BOFechaActualizacion(this.context);

				if (ooFechaModificacion != null)
				{
					gpsConfigura.FechaModificacion = ooFechaModificacion.toString();
				}

				empList.add(gpsConfigura);
			}

			if(empList.size() == 0)
			{
				return null;
			}
			else
			{
				return empList;	
			}
		}
		else
		{
			return null;
		}
	}
	//***************************************************************************************************************************************************
	//Implementaciones de los metodos de WS

	@Override
	public String getFieldValue(int idField)
	{
		String s = "";
		BOFechaActualizacion p = this;

		if (idField == this.ID_FechaModificacion)
		{
			s = p.FechaModificacion;
		}

		return s;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
