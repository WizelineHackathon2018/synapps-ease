package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOPedido extends BO implements rmsInterface
{
	public int IdVenta = 0;
	public int IdCliente = 0;
	public String Nombre = "";
	public String Observaciones = "";
	public int Realizada = 0;

	public final int ID_IdVenta = 1;
	public final int ID_IdCliente = 2;
	public final int ID_Nombre = 3;
	public final int ID_Observaciones = 4;
	public final int ID_Realizada = 5;

	public BOPedido(Context context)
	{
		super("BOPedido", context);
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

				Object ooIdVenta = soapPart.getProperty("IdVenta");
				Object ooIdCliente = soapPart.getProperty("IdCliente");
				Object ooNombre = soapPart.getProperty("Nombre");
				Object ooObservaciones = soapPart.getProperty("Observaciones");

				BOPedido categoria = new BOPedido(this.context);

				if (ooIdVenta != null)
				{
					categoria.IdVenta = Integer.parseInt(ooIdVenta.toString());
				}
				if (ooIdCliente != null)
				{
					categoria.IdCliente = Integer.parseInt(ooIdCliente.toString());
				}
				if (ooNombre != null)
				{
					categoria.Nombre = ooNombre.toString();
				}
				if (ooObservaciones != null)
				{
					categoria.Observaciones = ooObservaciones.toString();
				}
				categoria.Realizada = 0;

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
		BOPedido p = this;

		if (idField == ID_IdVenta)
		{
			s = String.valueOf(p.IdVenta);
		}
		else if (idField == ID_IdCliente)
		{
			s = String.valueOf(p.IdCliente);
		}
		else if (idField == ID_Nombre)
		{
			s = p.Nombre;
		}
		else if (idField == ID_Observaciones)
		{
			s = p.Observaciones;
		}
		else if (idField == ID_Realizada)
		{
			s = String.valueOf(p.Realizada);
		}

		return s;	  
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
