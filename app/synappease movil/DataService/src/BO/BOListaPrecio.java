package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOListaPrecio extends BO implements rmsInterface
{
	public int IdListaPrecio = 0;
	public String Nombre = "";
	public int RequiereAutorizacion = 0;

	public final int ID_IdListaPrecio = 1;
	public final int ID_Nombre = 2;
	public final int ID_RequiereAutorizacion = 3;
	public BO[] lsProductoListaPrecio;

	public BOListaPrecio(Context context)
	{
		super("ListaPrecio", context);
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

				Object ooIdListaPrecio = soapPart.getProperty("IdListaPrecio");
				Object ooNombre = soapPart.getProperty("Nombre");
				Object ooRequiereAutorizacion = soapPart.getProperty("RequiereAutorizacion");

				BOListaPrecio categoria = new BOListaPrecio(this.context);

				if (ooIdListaPrecio != null)
				{
					categoria.IdListaPrecio = Integer.parseInt(ooIdListaPrecio.toString());
				}
				if (ooNombre != null)
				{
					categoria.Nombre = ooNombre.toString().replace('-', ' ');
				}
				if (ooRequiereAutorizacion != null)
				{
					categoria.RequiereAutorizacion = Integer.parseInt(ooRequiereAutorizacion.toString());
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
		BOListaPrecio p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdListaPrecio);
		}
		else if (idField == 2)
		{
			s = p.Nombre;
		}
		else if (idField == 3)
		{
			s = String.valueOf(p.RequiereAutorizacion);
		}

		return s;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
