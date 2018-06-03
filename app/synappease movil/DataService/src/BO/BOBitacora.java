package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOBitacora extends BO implements rmsInterface
{
	public int IdBitacoraPregunta = 0;
	public String Nombre = "";
	public int Tipo = 0;
	public String Respuestas = "";
	public int Requerido = 0;

	public final int ID_IdBitacoraPregunta = 1;
	public final int ID_Nombre = 2;
	public final int ID_Tipo = 3;
	public final int ID_Respuestas = 4;
	public final int ID_Requerido = 5;

	public BOBitacora(Context context)
	{
		super("Bitacora", context);
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

				Object ooIdBitacoraPregunta = soapPart.getProperty("IdBitacoraPregunta");
				Object ooNombre = soapPart.getProperty("Nombre");
				Object ooTipo = soapPart.getProperty("Tipo");
				Object ooRespuestas = soapPart.getProperty("Respuestas");
				Object ooRequerido = soapPart.getProperty("Requerido");

				BOBitacora categoria = new BOBitacora(this.context);

				if (ooIdBitacoraPregunta != null)
				{
					categoria.IdBitacoraPregunta = Integer.parseInt(ooIdBitacoraPregunta.toString());
				}
				if (ooNombre != null)
				{
					categoria.Nombre = ooNombre.toString();
				}
				if (ooTipo != null)
				{
					categoria.Tipo = Integer.parseInt(ooTipo.toString());
				}
				if (ooRespuestas != null)
				{
					categoria.Respuestas = ooRespuestas.toString();
				}
				if (ooRequerido != null)
				{
					categoria.Requerido = Integer.parseInt(ooRequerido.toString());
				}

				listaUrlManager.add(categoria);
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
		BOBitacora p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdBitacoraPregunta);
		}
		else if (idField == 2)
		{
			s = p.Nombre;
		}
		else if (idField == 3)
		{
			s = String.valueOf(p.Tipo);
		}
		else if (idField == 4)
		{
			s = p.Respuestas;
		}
		else if (idField == 5)
		{
			s = String.valueOf(p.Requerido);
		}

		return s;  
	}

	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
