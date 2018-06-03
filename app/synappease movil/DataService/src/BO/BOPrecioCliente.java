package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOPrecioCliente extends BO implements rmsInterface
{
	public int IdCliente = 0;
	public int IdProducto = 0;
	public String Precio = "";

	public final int ID_IdCliente = 1;
	public final int ID_IdProducto = 2;
	public final int ID_Precio = 3;

	public BOPrecioCliente(Context context)
	{
		super("BOPrecioCliente", context);
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
				Object ooIdProducto = soapPart.getProperty("IdProducto");
				Object ooPrecio = soapPart.getProperty("Precio");

				BOPrecioCliente Precuestionario = new BOPrecioCliente(this.context);

				if (ooIdCliente != null)
				{
					Precuestionario.IdCliente = Integer.parseInt(ooIdCliente.toString());
				}
				if (ooIdProducto != null)
				{
					Precuestionario.IdProducto = Integer.parseInt(ooIdProducto.toString());
				}
				if (ooPrecio != null)
				{
					Precuestionario.Precio = ooPrecio.toString();
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
		BOPrecioCliente p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdCliente);
		}
		else if (idField == 2)
		{
			s = String.valueOf(p.IdProducto);
		}
		else if (idField == 3)
		{
			s = String.valueOf(p.Precio);
		}

		return s;	  
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
