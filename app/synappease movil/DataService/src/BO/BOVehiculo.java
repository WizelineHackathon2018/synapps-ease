package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOVehiculo extends BO implements rmsInterface
{
	public int IdVehiculo = 0;
	public String Descripcion = "";
	public String Numero = "";
	public int NumeroFotos = 0;

	public final int ID_IdVehiculo = 1;
	public final int ID_Descripcion = 2;
	public final int ID_Numero = 3;
	public final int ID_NumeroFotos = 4;

	public BOVehiculo(Context context)
	{
		super("Vehiculo", context);
	}

	public void AsignarValoresBOProceso()
	{
	}

	public List<BO> AsignarValoresProcesoBO(SoapObject wsColeccion)
	{
		if (wsColeccion != null)
		{
			int size = wsColeccion.getPropertyCount();
			List<BO> listaVehiculo = new ArrayList<BO>();

			for (int i = 0; i < size; i++)
			{
				SoapObject soapPart = (SoapObject)wsColeccion.getProperty(i);

				Object ooIdVehiculo = soapPart.getProperty("IdVehiculo");
				Object ooDescripcion = soapPart.getProperty("Descripcion");
				Object ooNumero = soapPart.getProperty("Numero");
				Object ooNumeroFotos = soapPart.getProperty("NumeroFotos");

				BOVehiculo Vehiculo = new BOVehiculo(this.context);

				if (ooIdVehiculo != null)
				{
					Vehiculo.IdVehiculo = Integer.parseInt(ooIdVehiculo.toString());
				}
				if (ooDescripcion != null)
				{
					Vehiculo.Descripcion = ooDescripcion.toString();
				}
				if (ooNumero != null)
				{
					Vehiculo.Numero = ooNumero.toString();
				}
				if (ooNumeroFotos != null)
				{
					Vehiculo.NumeroFotos = Integer.parseInt(ooNumeroFotos.toString());
				}

				listaVehiculo.add(Vehiculo);
			}
			if(listaVehiculo.size() == 0)
			{
				return null;
			}
			else
			{
				return listaVehiculo;
			}
		}

		return null;
	}

	@Override
	public String getFieldValue(int idField)
	{
		String s = "";
		BOVehiculo p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdVehiculo);
		}
		else if (idField == 2)
		{
			s = p.Descripcion;
		}
		else if (idField == 3)
		{
			s = p.Numero;
		}
		else if (idField == 4)
		{
			s = String.valueOf(p.NumeroFotos);
		}

		return s;	  
	}

	@Override
	public String toString()
	{
		return this.Numero + " " + this.Descripcion; 
	}

}
