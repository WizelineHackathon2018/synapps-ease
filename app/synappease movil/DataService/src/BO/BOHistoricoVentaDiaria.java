package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOHistoricoVentaDiaria extends BO implements rmsInterface
{
	public int IdCliente = 0;
	public int IdProducto = 0;
	public int Dia = 0;
	public String Cantidad = "";
	public String Descripcion = "";
	public String Codigo = "";
	public String Unidad = "";

	public final int ID_IdCliente = 1;
	public final int ID_IdProducto = 2;
	public final int ID_Dia = 3;
	public final int ID_Cantidad = 4;
	public final int ID_Descripcion = 5;
	public final int ID_Codigo = 6;
	public final int ID_Unidad = 7;

	public BOHistoricoVentaDiaria(Context context)
	{
		super("BOHistoricoVentaDiaria", context);
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
				Object ooIdProducto = soapPart.getProperty("IdProducto");
				Object ooDia = soapPart.getProperty("Dia");
				Object ooCantidad = soapPart.getProperty("Cantidad");

				BOHistoricoVentaDiaria Precuestionario = new BOHistoricoVentaDiaria(this.context);

				if (ooIdCliente != null)
				{
					Precuestionario.IdCliente = Integer.parseInt(ooIdCliente.toString());
				}
				if (ooIdProducto != null)
				{
					Precuestionario.IdProducto = Integer.parseInt(ooIdProducto.toString());
				}
				if (ooDia != null)
				{
					Precuestionario.Dia = Integer.parseInt(ooDia.toString());
				}
				if (ooCantidad != null)
				{
					Precuestionario.Cantidad = ooCantidad.toString();
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
		BOHistoricoVentaDiaria p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdCliente);
		}
		else if (idField == 2)
		{
			s = String.valueOf(p.IdProducto);
		}
		else if (idField == 3)
		{
			s = String.valueOf(p.Dia);
		}
		else if (idField == 4)
		{
			s = p.Cantidad;
		}
		else if (idField == 5)
		{
			s = p.Descripcion;
		}
		else if (idField == 6)
		{
			s = p.Codigo;
		}
		else if (idField == 7)
		{
			s = p.Unidad;
		}

		return s;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
