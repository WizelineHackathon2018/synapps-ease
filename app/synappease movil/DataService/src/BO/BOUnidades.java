package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOUnidades extends BO implements rmsInterface
{
	public int IdUnidad = 0;
	public String Nombre = "";

	public final int ID_IdCategoria = 1;
	public final int ID_IdRestoran = 2;
	public final int ID_Nombre = 3;

	public BOUnidades(Context context)
	{
		super("Unidades", context);
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

				Object ooIdUnidad = soapPart.getProperty("IdUnidad");
				Object ooNombre = soapPart.getProperty("Nombre");

				BOUnidades categoria = new BOUnidades(this.context);

				if (ooIdUnidad != null)
				{
					categoria.IdUnidad = Integer.parseInt(ooIdUnidad.toString());
				}
				if (ooNombre != null)
				{
					categoria.Nombre = ooNombre.toString();
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
		BOUnidades p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdUnidad);
		}
		else if (idField == 3)
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
