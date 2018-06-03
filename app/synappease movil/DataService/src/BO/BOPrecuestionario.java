package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOPrecuestionario extends BO implements rmsInterface
{
	public String Pregunta = "";
	public int PermiteVender = 0;

	public final int ID_Pregunta = 1;
	public final int ID_PermiteVender = 2;

	public BOPrecuestionario(Context context)
	{
		super("Precuestionario", context);
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

				Object ooPregunta = soapPart.getProperty("PreguntaInicio");
				Object ooPermiteVender = soapPart.getProperty("PermiteCompra");

				BOPrecuestionario Precuestionario = new BOPrecuestionario(this.context);

				if (ooPregunta != null)
				{
					Precuestionario.Pregunta = ooPregunta.toString();
				}
				if (ooPermiteVender != null)
				{
					Precuestionario.PermiteVender = Integer.parseInt(ooPermiteVender.toString());
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
		BOPrecuestionario p = this;

		if (idField == 2)
		{
			s = String.valueOf(p.PermiteVender);
		}
		else if (idField == 1)
		{
			s = p.Pregunta;
		}

		return s;	  
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
