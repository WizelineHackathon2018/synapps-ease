package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOConfiguraImpresion extends BO implements rmsInterface
{
	public int Tipo = 0;
	public String Texto = "";

	public final int ID_Tipo = 1;
	public final int ID_Texto = 2;

	public BOConfiguraImpresion(Context context)
	{
		super("BOConfiguraImpresion", context);
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

				Object ooTipo = soapPart.getProperty("Orden");
				Object ooTexto = soapPart.getProperty("Texto");

				BOConfiguraImpresion Precuestionario = new BOConfiguraImpresion(this.context);

				if (ooTipo != null)
				{
					Precuestionario.Tipo = Integer.parseInt(ooTipo.toString());
				}
				if (ooTexto != null)
				{
					Precuestionario.Texto = ooTexto.toString();
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
		BOConfiguraImpresion p = this;

		if (idField == this.ID_Tipo)
		{
			s = String.valueOf(p.Tipo);
		}
		else if (idField == this.ID_Texto)
		{
			s = p.Texto;
		}

		return s;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
