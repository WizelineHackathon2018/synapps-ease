package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOInformacionCliente extends BO implements rmsInterface
{
	public int IdCliente = 0;
	public int IdCuestionarioCliente = 0;
	public String Pregunta = "";
	public String Respuesta = "";

	public final int ID_IdCliente = 1;
	public final int ID_IdCuestionarioCliente = 2;
	public final int ID_Pregunta = 3;
	public final int ID_Respuesta = 4;

	public BOInformacionCliente(Context context)
	{
		super("BOInformacionCliente", context);
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

				Object ooIdCliente = soapPart.getProperty("IdCliente");
				Object ooIdCuestionarioCliente = soapPart.getProperty("IdCuestionarioCliente");
				Object ooRespuesta = soapPart.getProperty("Respuesta");

				BOInformacionCliente Precuestionario = new BOInformacionCliente(this.context);

				if (ooIdCliente != null)
				{
					Precuestionario.IdCliente = Integer.parseInt(ooIdCliente.toString());
				}
				if (ooIdCuestionarioCliente != null)
				{
					Precuestionario.IdCuestionarioCliente = Integer.parseInt(ooIdCuestionarioCliente.toString());
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
		BOInformacionCliente p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdCliente);
		}
		else if (idField == 2)
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

		return s;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
