package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOConfiguracion extends BO implements rmsInterface
{
	public int NoVenta = 0;
	public int NoPedido = 0;
	public int NoCotizacion = 0;

	public final int ID_NoVenta = 1;
	public final int ID_NoPedido = 2;
	public final int ID_NoCotizacion = 3;

	public BOConfiguracion(Context context)
	{
		super("Configuracion", context);
	}

	public void AsignarValoresBOProceso()
	{
	}

	public List<BO> AsignarValoresProcesoBO(SoapObject wsColeccion)
	{
		if (wsColeccion != null)
		{
			int size = wsColeccion.getPropertyCount();
			List<BO> listaConfiguracion = new ArrayList<BO>();

			for (int i = 0; i < size; i++)
			{
				SoapObject soapPart = (SoapObject)wsColeccion.getProperty(i);
				Object ooNoVenta = soapPart.getProperty("NoVenta");
				Object ooNoPedido = soapPart.getProperty("NoPedido");
				Object ooNoCotizacion = soapPart.getProperty("NoCotizacion");
				BOConfiguracion Configuracion = new BOConfiguracion(this.context);

				if (ooNoVenta != null)
				{
					Configuracion.NoVenta = Integer.parseInt(ooNoVenta.toString());
				}
				if (ooNoPedido != null)
				{
					Configuracion.NoPedido = Integer.parseInt(ooNoPedido.toString());
				}
				if (ooNoCotizacion != null)
				{
					Configuracion.NoCotizacion = Integer.parseInt(ooNoCotizacion.toString());
				}
				listaConfiguracion.add(Configuracion);
			}
			if(listaConfiguracion.size() == 0)
			{
				return null;
			}
			else
			{
				return listaConfiguracion;
			}
		}

		return null;
	}

	@Override
	public String getFieldValue(int idField)
	{
		String s = "";
		BOConfiguracion p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.NoVenta);
		}
		if (idField == 2)
		{
			s = String.valueOf(p.NoPedido);
		}
		if (idField == 3)
		{
			s = String.valueOf(p.NoCotizacion);
		}

		return s;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
