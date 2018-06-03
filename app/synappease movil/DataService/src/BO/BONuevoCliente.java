package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BONuevoCliente extends BO implements rmsInterface
{
	public String Nombre = "";
	public String Calle = "";
	public String Colonia = "";
	public String Numero = "";
	public String Cruzamientos = "";
	public String Empresa = "";
	public String Contacto = "";
	public String EMail = "";
	public String Telefono = "";
	public String Celular = "";
	public String GPSDispositivo = "";
	public String GUIDV = "";

	public int IdCliente = 0;
	public int IdEmpresa = 0;
	public int IdCategoriaCliente = 0;
	public int IdRuta = 0;

	public final int ID_Nombre = 1;
	public final int ID_Calle = 2;
	public final int ID_Colonia = 3;
	public final int ID_Numero = 4;
	public final int ID_Cruzamientos = 5;
	public final int ID_Empresa = 6;
	public final int ID_Contacto = 7;
	public final int ID_EMail = 8;
	public final int ID_Telefono = 9;
	public final int ID_Celular = 10;
	public final int ID_GPSDispositivo = 11;
	public final int ID_GUIDV = 12;

	public final int ID_IdCliente = 13;
	public final int ID_IdEmpresa = 14;
	public final int ID_IdCategoriaCliente = 15;
	public final int ID_IdRuta = 16;

	public BONuevoCliente(Context context)
	{
		super("BONuevoCliente", context);
	}

	public void AsignarValoresBOProceso()
	{
	}

	public List<BO> AsignarValoresProcesoBO(SoapObject wsColeccion)
	{
		return null;
	}

	@Override
	public String getFieldValue(int idField)
	{
		String s = "";
		BONuevoCliente p = this;

		if (idField == 1)
		{
			s = p.Nombre;
		}
		else if (idField == 2)
		{
			s = p.Calle;
		}
		else if (idField == 3)
		{
			s = p.Colonia;
		}
		else if (idField == 4)
		{
			s = p.Numero;
		}
		else if (idField == 5)
		{
			s = p.Cruzamientos;
		}
		else if (idField == 6)
		{
			s = p.Empresa;
		}
		else if (idField == 7)
		{
			s = p.Contacto;
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
			s = p.GPSDispositivo;
		}
		else if (idField == 12)
		{
			s = p.GUIDV;
		}
		else if (idField == 13)
		{
			s = String.valueOf(p.IdCliente);
		}
		else if (idField == 14)
		{
			s = String.valueOf(p.IdEmpresa);
		}
		else if (idField == 15)
		{
			s = String.valueOf(p.IdCategoriaCliente);
		}
		else if (idField == 16)
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
