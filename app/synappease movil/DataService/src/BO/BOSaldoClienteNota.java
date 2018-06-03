package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOSaldoClienteNota extends BO implements rmsInterface
{
	public int IdCliente;
	public String NoDocumento;
	public String Fecha;
	public String Importe;
	public String SaldoFactura = "0";
	public String CobroFactura = "0";

	public final int ID_IdCliente = 1;
	public final int ID_NoDocumento = 2;
	public final int ID_Fecha = 3;
	public final int ID_Importe = 4;
	public final int ID_SaldoFactura = 5;
	public final int ID_CobroFactura = 6;

	public BOSaldoClienteNota(Context context)
	{
		super("BOSaldoClienteNota", context);
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
				Object ooNoDocumento = soapPart.getProperty("NoDocumento");
				Object ooFecha = soapPart.getProperty("Fecha");
				Object ooImporte = soapPart.getProperty("Importe");

				BOSaldoClienteNota Precuestionario = new BOSaldoClienteNota(this.context);

				if (ooIdCliente != null)
				{
					Precuestionario.IdCliente = Integer.parseInt(ooIdCliente.toString());
				}
				if (ooNoDocumento != null)
				{
					Precuestionario.NoDocumento = ooNoDocumento.toString();
				}
				if (ooFecha != null)
				{
					Precuestionario.Fecha = ooFecha.toString();
				}
				if (ooImporte != null)
				{
					Precuestionario.Importe = ooImporte.toString();
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
		BOSaldoClienteNota p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdCliente);
		}
		else if (idField == 2)
		{
			s = p.NoDocumento;
		}
		else if (idField == 3)
		{
			s = p.Fecha;
		}
		else if (idField == 4)
		{
			s = p.Importe;
		}
		else if (idField == 5)
		{
			s = String.valueOf(p.SaldoFactura);
		}
		else if (idField == 5)
		{
			s = p.SaldoFactura;
		}
		else if (idField == 5)
		{
			s = p.CobroFactura;
		}

		return s;	  
	}

	@Override
	public String toString()
	{
		if(this.NoDocumento.equals("Abono a factura sin referencia"))
		{
			return "Abono a factura sin referencia";
		}
		else
		{
			return this.NoDocumento + " $" + this.Importe + " Abono: " + this.CobroFactura + " Saldo:" + this.SaldoFactura + " FV:" + Fecha;
		}
	}

}
