package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOProducto extends BO implements rmsInterface
{
	public int IdProducto = 0;
	public int IdUnidad = 0;
	public float PrecioPublico = 0.0F;
	public String Descripcion = "";
	public String Codigo = "";
	public String DescripcionProveedor = "";
	public String MargenDescuento = "";
	public int Existencia = 0;
	public int Particion = 0;
	public String Unidad = "";

	public final int ID_IdProducto = 1;
	public final int ID_IdUnidad = 2;
	public final int ID_PrecioPublico = 3;
	public final int ID_Descripcion = 4;
	public final int ID_Codigo = 5;
	public final int ID_DescripcionProveedor = 6;
	public final int ID_MargenDescuento = 7;
	public final int ID_Existencia = 8;
	public final int ID_Particion = 9;
	public final int ID_Unidad = 10;

	public BOProducto(Context context)
	{
		super("BOProducto", context);
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

				Object ooIdProducto = soapPart.getProperty("IdProducto");
				Object ooIdUnidad = soapPart.getProperty("IdUnidad");
				Object ooPrecioPublico = soapPart.getProperty("PrecioPublico");
				Object ooDescripcion = soapPart.getProperty("Descripcion");
				Object ooCodigo = soapPart.getProperty("Codigo");
				Object ooDescripcionProveedor = soapPart.getProperty("DescripcionProveedor");
				Object ooMargenDescuento = soapPart.getProperty("MargenDescuento");
				Object ooExistencia = soapPart.getProperty("Existencia");
				Object ooParticion = soapPart.getProperty("Particion");
				Object ooUnidad = soapPart.getProperty("Unidad");

				BOProducto categoria = new BOProducto(this.context);

				if (ooIdProducto != null)
				{
					categoria.IdProducto = Integer.parseInt(ooIdProducto.toString());
				}
				if (ooIdUnidad != null)
				{
					categoria.IdUnidad = Integer.parseInt(ooIdUnidad.toString());
				}
				if (ooPrecioPublico != null)
				{
					categoria.PrecioPublico = Float.parseFloat(ooPrecioPublico.toString());
				}

				if (ooDescripcion != null)
				{
					categoria.Descripcion = ooDescripcion.toString();
				}

				if (ooDescripcionProveedor != null)
				{
					categoria.DescripcionProveedor = ooDescripcionProveedor.toString();
				}

				if (ooCodigo != null)
				{
					categoria.Codigo = ooCodigo.toString();
				}

				if (ooMargenDescuento != null)
				{
					categoria.MargenDescuento = ooMargenDescuento.toString();
				}
				if (ooExistencia != null)
				{
					categoria.Existencia = Integer.parseInt(ooExistencia.toString());
				}
				if (ooParticion != null)
				{
					categoria.Particion = Integer.parseInt(ooParticion.toString());
				}
				if (ooUnidad != null)
				{
					categoria.Unidad = ooUnidad.toString();
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
		BOProducto p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdProducto);
		}
		else if (idField == 2)
		{
			s = String.valueOf(p.IdUnidad);
		}
		else if (idField == 3)
		{
			s = String.valueOf(p.PrecioPublico);
		}
		else if (idField == 4)
		{
			s = p.Descripcion;
		}
		else if (idField == 5)
		{
			s = p.Codigo;
		}
		else if (idField == 6)
		{
			s = p.DescripcionProveedor;
		}
		else if (idField == 7)
		{
			s = p.MargenDescuento;
		}
		else if (idField == 8)
		{
			s = String.valueOf(p.Existencia);
		}
		else if (idField == 9)
		{
			s = String.valueOf(p.Particion);
		}
		else if (idField == 10)
		{
			s = p.Unidad;
		}

		return s;	  
	}

	@Override
	public String toString() {
		return this.Codigo + " " + this.Descripcion;
	}

}
