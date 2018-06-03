package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOClienteObtenido extends BO implements rmsInterface
{
	public int IdCliente = 0;
	public String GPS = "";

	public final int ID_IdCliente = 1;
	public final int ID_GPS = 2;

	public BOClienteObtenido(Context context)
	{
		super("BOClienteObtenido", context);
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
		BOClienteObtenido p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdCliente);
		}
		if (idField == 2)
		{
			s = p.GPS;
		}

		return s;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
