package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOSaldoCliente extends BO implements rmsInterface
{
	public int IdCliente;
	public String Nombre;
	public String Vencido;
	public String PorVencer;
	public String LimiteCredito;
	public String Credito1Dias;
	public String Credito1Monto;
	public String Credito2Dias;
	public String Credito2Monto;
	public String Credito3Dias;
	public String Credito3Monto;
	public String Credito4Dias;
	public String Credito4Monto;
	public String Credito5Dias;
	public String Credito5Monto;

	public final int ID_IdCliente = 1;
	public final int ID_Nombre = 2;
	public final int ID_Vencido = 3;
	public final int ID_PorVencer = 4;
	public final int ID_LimiteCredito = 5;
	public final int ID_Credito1Dias = 6;
	public final int ID_Credito1Monto = 7;
	public final int ID_Credito2Dias = 8;
	public final int ID_Credito2Monto = 9;
	public final int ID_Credito3Dias = 10;
	public final int ID_Credito3Monto = 11;
	public final int ID_Credito4Dias = 12;
	public final int ID_Credito4Monto = 13;
	public final int ID_Credito5Dias = 14;
	public final int ID_Credito5Monto = 15;

	public BOSaldoCliente(Context context)
	{
		super("BOSaldoCliente", context);
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
				Object ooNombre = soapPart.getProperty("Nombre");
				Object ooVencido = soapPart.getProperty("Vencido");
				Object ooPorVencer = soapPart.getProperty("PorVencer");
				Object ooLimiteCredito = soapPart.getProperty("LimiteCredito");
				Object ooCredito1Dias = soapPart.getProperty("Credito1Dias");
				Object ooCredito1Monto = soapPart.getProperty("Credito1Monto");
				Object ooCredito2Dias = soapPart.getProperty("Credito2Dias");
				Object ooCredito2Monto = soapPart.getProperty("Credito2Monto");
				Object ooCredito3Dias = soapPart.getProperty("Credito3Dias");
				Object ooCredito3Monto = soapPart.getProperty("Credito3Monto");
				Object ooCredito4Dias = soapPart.getProperty("Credito4Dias");
				Object ooCredito4Monto = soapPart.getProperty("Credito4Monto");
				Object ooCredito5Dias = soapPart.getProperty("Credito5Dias");
				Object ooCredito5Monto = soapPart.getProperty("Credito5Monto");

				BOSaldoCliente Precuestionario = new BOSaldoCliente(this.context);

				if (ooIdCliente != null)
				{
					Precuestionario.IdCliente = Integer.parseInt(ooIdCliente.toString());
				}
				if (ooNombre != null)
				{
					Precuestionario.Nombre = ooNombre.toString();
				}
				if (ooVencido != null)
				{
					Precuestionario.Vencido = ooVencido.toString();
				}
				if (ooPorVencer != null)
				{
					Precuestionario.PorVencer = ooPorVencer.toString();
				}
				if (ooLimiteCredito != null)
				{
					Precuestionario.LimiteCredito = ooLimiteCredito.toString();
				}
				if (ooCredito1Dias != null)
				{
					Precuestionario.Credito1Dias = getValorCadena(ooCredito1Dias.toString());
				}
				if (ooCredito1Monto != null)
				{
					Precuestionario.Credito1Monto = ooCredito1Monto.toString();
				}
				if (ooCredito2Dias != null)
				{
					Precuestionario.Credito2Dias = getValorCadena(ooCredito2Dias.toString());
				}
				if (ooCredito2Monto != null)
				{
					Precuestionario.Credito2Monto = ooCredito2Monto.toString();
				}
				if (ooCredito3Dias != null)
				{
					Precuestionario.Credito3Dias = getValorCadena(ooCredito3Dias.toString());
				}
				if (ooCredito3Monto != null)
				{
					Precuestionario.Credito3Monto = ooCredito3Monto.toString();
				}
				if (ooCredito4Dias != null)
				{
					Precuestionario.Credito4Dias = getValorCadena(ooCredito4Dias.toString());
				}
				if (ooCredito4Monto != null)
				{
					Precuestionario.Credito4Monto = ooCredito4Monto.toString();
				}
				if (ooCredito5Dias != null)
				{
					Precuestionario.Credito5Dias = getValorCadena(ooCredito5Dias.toString());
				}
				if (ooCredito5Monto != null)
				{
					Precuestionario.Credito5Monto = ooCredito5Monto.toString();
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
		BOSaldoCliente p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdCliente);
		}
		else if (idField == 2)
		{
			s = p.Nombre;
		}
		else if (idField == 3)
		{
			s = p.Vencido;
		}
		else if (idField == 4)
		{
			s = p.PorVencer;
		}
		else if (idField == 5)
		{
			s = p.LimiteCredito;
		}
		else if (idField == 6)
		{
			s = p.Credito1Dias;
		}
		else if (idField == 7)
		{
			s = p.Credito1Monto;
		}
		else if (idField == 8)
		{
			s = p.Credito2Dias;
		}
		else if (idField == 9)
		{
			s = p.Credito2Monto;
		}
		else if (idField == 10)
		{
			s = p.Credito3Dias;
		}
		else if (idField == 11)
		{
			s = p.Credito3Monto;
		}
		else if (idField == 12)
		{
			s = p.Credito4Dias;
		}
		else if (idField == 13)
		{
			s = p.Credito4Monto;
		}
		else if (idField == 14)
		{
			s = p.Credito5Dias;
		}
		else if (idField == 15)
		{
			s = p.Credito5Monto;
		}

		return s;	  
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
