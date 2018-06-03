package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOCuestionarioCliente extends BO implements rmsInterface
{
	public int IdCuestionarioCliente = 0;
	public String Pregunta = "";
	public String Respuesta = "";
	public int Tipo = 0;

	public String RespuestaAnterior = "";

	public final int ID_IdCuestionarioCliente = 1;
	public final int ID_Pregunta = 3;
	public final int ID_Respuesta = 4;
	public final int ID_Tipo = 5;

	public BOCuestionarioCliente(Context context)
	{
		super("BOCuestionarioCliente", context);
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

				Object ooIdCuestionarioCliente = soapPart.getProperty("IdCuestionarioCliente");
				Object ooPregunta = soapPart.getProperty("Pregunta");
				Object ooRespuesta = soapPart.getProperty("Respuesta");
				Object ooTipo = soapPart.getProperty("Tipo");

				BOCuestionarioCliente Precuestionario = new BOCuestionarioCliente(this.context);

				if (ooIdCuestionarioCliente != null)
				{
					Precuestionario.IdCuestionarioCliente = Integer.parseInt(ooIdCuestionarioCliente.toString());
				}
				if (ooPregunta != null)
				{
					Precuestionario.Pregunta = ooPregunta.toString();
				}
				if (ooRespuesta != null)
				{
					Precuestionario.Respuesta = getValorCadena(ooRespuesta.toString());
				}
				if (ooTipo != null)
				{
					Precuestionario.Tipo = Integer.parseInt(ooTipo.toString());
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
		BOCuestionarioCliente p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdCuestionarioCliente);
		}
		else if (idField == 3)
		{
			s = p.Pregunta;
		}
		else if (idField == 4)
		{
			s = p.Respuesta;
		}
		else if (idField == 5)
		{
			s = String.valueOf(p.Tipo);
		}

		return s;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
