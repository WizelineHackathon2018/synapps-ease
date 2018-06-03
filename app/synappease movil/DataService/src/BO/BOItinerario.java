package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOItinerario extends BO implements rmsInterface
{
	public int IdItinerario = 0;
	public String Cliente = "";
	public String RazonSocial = "";
	public String Direccion = "";
	public String FechaProgramada = "";
	public int IdCliente = 0;
	public String Visita = "";

	public String PosicionGPS = "";
	public float SaldoPendiente = 0.0F;

	public double TotalAbonoDia = 0.0D;

	public String Codigo = "";
	public int RequiereAutorizacion = 0;

	public final int ID_IdItinerario = 1;
	public final int ID_Cliente = 2;
	public final int ID_RazonSocial = 3;
	public final int ID_Direccion = 4;
	public final int ID_FechaProgramada = 5;
	public final int ID_IdCliente = 6;

	public final int ID_PosicionGPS = 7;
	public final int ID_SaldoPendiente = 8;

	public final int ID_Codigo = 9;
	public final int ID_RequiereAutorizacion = 10;

	public BOItinerario(Context context)
	{
		super("BOItinerario2", context);
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

				Object ooIdItinerario = soapPart.getProperty("IdItinerario");
				Object ooCliente = soapPart.getProperty("Cliente");
				Object ooRazonSocial = soapPart.getProperty("RazonSocial");
				Object ooDireccion = soapPart.getProperty("Direccion");
				Object ooFechaProgramada = soapPart.getProperty("FechaProgramada");
				Object ooPosicionGPS = soapPart.getProperty("PosicionGPS");
				Object ooSaldoPendiente = soapPart.getProperty("SaldoPendiente");

				BOItinerario categoria = new BOItinerario(this.context);

				if (ooIdItinerario != null)
				{
					categoria.IdItinerario = Integer.parseInt(ooIdItinerario.toString());
				}
				if (ooCliente != null)
				{
					categoria.Cliente = ooCliente.toString();
				}
				if (ooRazonSocial != null)
				{
					categoria.RazonSocial = ooRazonSocial.toString();
				}
				if (ooDireccion != null)
				{
					categoria.Direccion = ooDireccion.toString();
				}
				if (ooFechaProgramada != null)
				{
					categoria.FechaProgramada = ooFechaProgramada.toString();
				}
				if (ooPosicionGPS != null)
				{
					categoria.PosicionGPS = ooPosicionGPS.toString();
				}
				if (ooSaldoPendiente != null)
				{
					categoria.SaldoPendiente = Float.parseFloat(ooSaldoPendiente.toString());
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
		BOItinerario p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdItinerario);
		}
		else if (idField == 2)
		{
			s = p.Cliente;
		}
		else if (idField == 3)
		{
			s = p.RazonSocial;
		}
		else if (idField == 4)
		{
			s = p.Direccion;
		}
		else if (idField == 5)
		{
			s = p.FechaProgramada;
		}
		else if (idField == 6)
		{
			s = String.valueOf(p.IdCliente);
		}
		else if (idField == 7)
		{
			s = p.PosicionGPS;
		}
		else if (idField == 8)
		{
			s = String.valueOf(p.SaldoPendiente);
		}
		else if (idField == 9)
		{
			s = p.Codigo;
		}
		else if (idField == ID_RequiereAutorizacion)
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
