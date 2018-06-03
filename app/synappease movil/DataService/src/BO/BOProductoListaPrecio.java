package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOProductoListaPrecio extends BO implements rmsInterface
{
	public int IdProductoListaPrecio = 0;
	public int IdListaPrecio = 0;
	public int IdProducto = 0;
	public float Precio = 0.0F;

	public final int ID_IdProductoListaPrecio = 1;
	public final int ID_IdListaPrecio = 2;
	public final int ID_IdProducto = 3;
	public final int ID_Precio = 4;

	public BOProductoListaPrecio(Context context)
	{
		super("ProductoListaPrecio", context);
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

				Object ooIdProductoListaPrecio = soapPart.getProperty("IdProductoListaPrecio");

				Object ooIdProducto = soapPart.getProperty("IdProducto");
				Object ooPrecio = soapPart.getProperty("Precio");

				BOProductoListaPrecio categoria = new BOProductoListaPrecio(this.context);

				if (ooIdProductoListaPrecio != null)
				{
					categoria.IdProductoListaPrecio = Integer.parseInt(ooIdProductoListaPrecio.toString());
				}

				if (ooIdProducto != null)
				{
					categoria.IdProducto = Integer.parseInt(ooIdProducto.toString());
				}
				if (ooPrecio != null)
				{
					categoria.Precio = Float.parseFloat(ooPrecio.toString());
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
		BOProductoListaPrecio p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdProductoListaPrecio);
		}
		else if (idField == 2)
		{
			s = String.valueOf(p.IdListaPrecio);
		}
		else if (idField == 3)
		{
			s = String.valueOf(p.IdProducto);
		}
		else if (idField == 4)
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
