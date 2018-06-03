package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOCuestionario extends BO implements rmsInterface
{
	public int IdCuestionario;
	public String Nombre;

	public final int ID_IdCuestionario = 1;
	public final int ID_Nombre = 2;

	public BOCuestionario(Context context)
	{
		super("BOCuestionario", context);
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

				Object ooIdCuestionario = soapPart.getProperty("IdCuestionario");
				Object ooNombre = soapPart.getProperty("Nombre");

				BOCuestionario Precuestionario = new BOCuestionario(this.context);

				if (ooIdCuestionario != null)
				{
					Precuestionario.IdCuestionario = Integer.parseInt(ooIdCuestionario.toString());
				}
				if (ooNombre != null)
				{
					Precuestionario.Nombre = ooNombre.toString();
				}

				listaUrlManager.add(Precuestionario);
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
		BOCuestionario p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdCuestionario);
		}
		else if (idField == 2)
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
