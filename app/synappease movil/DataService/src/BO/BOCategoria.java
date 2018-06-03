package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOCategoria extends BO implements rmsInterface
{
	public int IdCategoria;
	public String Nombre;
	public int ValorDefecto;

	public final int ID_IdCategoria = 1;
	public final int ID_Nombre = 2;
	public final int ID_ValorDefecto = 3;

	public BOCategoria(Context context)
	{
		super("BOCategoria", context);
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

				Object ooIdCategoria = soapPart.getProperty("IdCategoria");
				Object ooNombre = soapPart.getProperty("Nombre");
				Object ooValorDefecto = soapPart.getProperty("ValorDefecto");

				BOCategoria Precuestionario = new BOCategoria(this.context);

				if (ooIdCategoria != null)
				{
					Precuestionario.IdCategoria = Integer.parseInt(ooIdCategoria.toString());
				}
				if (ooNombre != null)
				{
					Precuestionario.Nombre = ooNombre.toString();
				}
				if (ooValorDefecto != null)
				{
					Precuestionario.ValorDefecto = Integer.parseInt(ooValorDefecto.toString());
				}
				listaUrlManager.add(Precuestionario);
				if(listaUrlManager.size() == 0)
				{
					return null;
				}
				else
				{
					return listaUrlManager;
				}
			}
			return listaUrlManager;
		}

		return null;
	}

	@Override
	public String getFieldValue(int idField)
	{
		String s = "";
		BOCategoria p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdCategoria);
		}
		else if (idField == 2)
		{
			s = p.Nombre;
		}
		else if (idField == 3)
		{
			s = String.valueOf(p.ValorDefecto);
		}

		return s;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
