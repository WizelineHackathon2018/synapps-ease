package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOPreguntaCuestionario extends BO implements rmsInterface
{
	public int IdPreguntaCuestionario;
	public String Pregunta;
	public int Tipo;
	public String Respuesta;

	public final int ID_IdPreguntaCuestionario = 1;
	public final int ID_Pregunta = 2;
	public final int ID_Tipo = 3;
	public final int ID_Respuesta = 4;

	public BOPreguntaCuestionario(Context context)
	{
		super("BOPreguntaCuestionario", context);
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

				Object ooIdPreguntaCuestionario = soapPart.getProperty("IdPreguntaCuestionario");
				Object ooPregunta = soapPart.getProperty("Pregunta");
				Object ooTipo = soapPart.getProperty("Tipo");
				Object ooRespuesta = soapPart.getProperty("Respuesta");

				BOPreguntaCuestionario Precuestionario = new BOPreguntaCuestionario(this.context);

				if (ooIdPreguntaCuestionario != null)
				{
					Precuestionario.IdPreguntaCuestionario = Integer.parseInt(ooIdPreguntaCuestionario.toString());
				}
				if (ooPregunta != null)
				{
					Precuestionario.Pregunta = ooPregunta.toString();
				}
				if (ooTipo != null)
				{
					Precuestionario.Tipo = Integer.parseInt(ooTipo.toString());
				}
				if (ooRespuesta != null)
				{
					Precuestionario.Respuesta = ooRespuesta.toString();
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
		BOPreguntaCuestionario p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdPreguntaCuestionario);
		}
		else if (idField == 2)
		{
			s = p.Pregunta;
		}
		else if (idField == 3)
		{
			s = String.valueOf(p.Tipo);
		}
		else if (idField == 4)
		{
			s = p.Respuesta;
		}

		return s;	  
	}

	@Override
	public String toString() {
		return this.Pregunta;
	}

}
