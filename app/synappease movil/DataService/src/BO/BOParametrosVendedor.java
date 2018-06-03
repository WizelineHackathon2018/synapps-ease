package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOParametrosVendedor extends BO implements rmsInterface
{
	public String Parametro = "";
	public String Valor = "";

	public final int ID_Parametro = 1;
	public final int ID_Valor = 2;

	public BOParametrosVendedor(Context context)
	{
		super("ParametrosVendedor", context);
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

				Object ooParametro = soapPart.getProperty("Parametro");
				Object ooValor = soapPart.getProperty("Valor");

				BOParametrosVendedor categoria = new BOParametrosVendedor(this.context);

				if (ooParametro != null)
				{
					categoria.Parametro = ooParametro.toString();
				}
				if (ooValor != null)
				{
					categoria.Valor = ooValor.toString();
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
		BOParametrosVendedor p = this;

		if (idField == 1)
		{
			s = p.Parametro;
		}
		else if (idField == 2)
		{
			s = p.Valor;
		}

		return s;	  
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
