package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOPrecuestionarioCliente extends BO implements rmsInterface
{
	public String Pregunta = "";
	public int IdVendedor = 0;
	public int IdCliente = 0;

	public final int ID_Pregunta = 1;
	public final int ID_IdVendedor = 2;
	public final int ID_IdCliente = 3;

	public BOPrecuestionarioCliente(Context context)
	{
		super("PrecuestionarioCliente", context);
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

				BOCliente categoria = new BOCliente(this.context);

				if (ooIdCliente != null)
				{
					categoria.IdCliente = Integer.parseInt(ooIdCliente.toString());
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
		BOPrecuestionarioCliente p = this;

		if (idField == 2)
		{
			s = String.valueOf(p.IdVendedor);
		}
		else if (idField == 1)
		{
			s = p.Pregunta;
		}
		else if (idField == 3)
		{
			s = String.valueOf(p.IdCliente);
		}

		return s;	  
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
