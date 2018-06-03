package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOPrecioRango extends BO implements rmsInterface
{
	public int IdProducto = 0;
	public float RangoInicial = 0;
	public float RangoFinal = 0;
	public float PrecioProducto = 0;

	public final int ID_IdProducto = 1;
	public final int ID_RangoInicial = 2;
	public final int ID_RangoFinal = 3;
	public final int ID_PrecioProducto = 4;

	public BOPrecioRango(Context context)
	{
		super("BOPrecioRango", context);
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

				Object ooIdProducto = soapPart.getProperty("IdProducto");
				Object ooRangoInicial = soapPart.getProperty("RangoInicial");
				Object ooRangoFinal = soapPart.getProperty("RangoFinal");
				Object ooPrecioProducto = soapPart.getProperty("PrecioProducto");

				BOPrecioRango Precuestionario = new BOPrecioRango(this.context);

				if (ooIdProducto != null)
				{
					Precuestionario.IdProducto = Integer.parseInt(ooIdProducto.toString());
				}
				if (ooRangoInicial != null)
				{
					Precuestionario.RangoInicial = Float.parseFloat(ooRangoInicial.toString());
				}
				if (ooRangoFinal != null)
				{
					Precuestionario.RangoFinal = Float.parseFloat(ooRangoFinal.toString());
				}
				if (ooPrecioProducto != null)
				{
					Precuestionario.PrecioProducto = Float.parseFloat(ooPrecioProducto.toString());
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
		BOPrecioRango p = this;

		if (idField == this.ID_IdProducto)
		{
			s = String.valueOf(p.IdProducto);
		}
		else if (idField == this.ID_RangoInicial)
		{
			s = String.valueOf(p.RangoInicial);
		}
		else if (idField == this.ID_RangoFinal)
		{
			s = String.valueOf(p.RangoFinal);
		}
		else if (idField == this.ID_PrecioProducto)
		{
			s = String.valueOf(p.PrecioProducto);
		}

		return s;	  
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
