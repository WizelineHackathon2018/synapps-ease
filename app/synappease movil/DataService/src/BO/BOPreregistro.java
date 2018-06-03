package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOPreregistro extends BO implements rmsInterface
{
	public String Nombre = "";
	public String Colonia = "";
	public String Numero = "";
	public String Cruzamientos = "";
	public String Empresa = "";
	public String Contacto = "";
	public String Calle = "";
	public String EMail = "";
	public String Telefono = "";
	public String Celular = "";
	public String PosicionGps = "";
	public int IdCategoria = 0;
	public int IdRuta = 0;

	public final int ID_Nombre = 1;
	public final int ID_Colonia = 2;
	public final int ID_Numero = 3;
	public final int ID_Cruzamientos = 4;
	public final int ID_Empresa = 5;
	public final int ID_Contacto = 6;
	public final int ID_Calle = 7;
	public final int ID_EMail = 8;
	public final int ID_Telefono = 9;
	public final int ID_Celular = 10;
	public final int ID_PosicionGps = 11;
	public final int ID_IdCategoria = 12;
	public final int ID_IdRuta = 13;

	public BOPreregistro(Context context)
	{
		super("Preregistro", context);
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

				BOCliente categoria = new BOCliente(this.context);

				if (ooIdCliente != null)
				{
					categoria.IdCliente = Integer.parseInt(ooIdCliente.toString());
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
		BOPreregistro p = this;

		if (idField == 1)
		{
			s = p.Nombre;
		}
		else if (idField == 2)
		{
			s = p.Colonia;
		}
		else if (idField == 3)
		{
			s = p.Numero;
		}
		else if (idField == 4)
		{
			s = p.Cruzamientos;
		}
		else if (idField == 5)
		{
			s = p.Empresa;
		}
		else if (idField == 6)
		{
			s = p.Contacto;
		}
		else if (idField == 7)
		{
			s = p.Calle;
		}
		else if (idField == 8)
		{
			s = p.EMail;
		}
		else if (idField == 9)
		{
			s = p.Telefono;
		}
		else if (idField == 10)
		{
			s = p.Celular;
		}
		else if (idField == 11)
		{
			s = p.PosicionGps;
		}
		else if (idField == 12)
		{
			s = String.valueOf(p.IdCategoria);
		}
		else if (idField == 13)
		{
			s = String.valueOf(p.IdRuta);
		}

		return s;	  
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
