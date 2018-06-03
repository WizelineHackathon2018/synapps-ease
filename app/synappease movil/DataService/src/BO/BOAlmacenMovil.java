package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOAlmacenMovil extends BO implements rmsInterface
{
	public int IdAlmacenMovil = 0;
	public String Producto = "";
	public int IdProducto = 0;
	public String Unidad = "";

	public float CantidadInicial = 0.0F;
	public float CantidadFinal = 0.0F;
	public float Devoluciones = 0.0F;
	public float Reposiciones = 0.0F;
	public float Existencia = 0.0F;
	public float Venta = 0.0F;

	public String Codigo = "";
	public float PrecioPublico = 0.0F;
	public String MargenDescuento = "";

	public String DescripcionProveedor = "";
	public int Particion = 0;

	public String FechaVigencia = "";

	public final int ID_IdAlmacenMovil = 1;
	public final int ID_Producto = 2;
	public final int ID_CantidadInicial = 3;
	public final int ID_IdProducto = 4;
	public final int ID_Unidad = 5;

	public final int ID_CantidadFinal = 6;
	public final int ID_Devoluciones = 7;
	public final int ID_Reposiciones = 8;

	public final int ID_Venta = 9;
	public final int ID_Codigo = 10;
	public final int ID_PrecioPublico = 11;
	public final int ID_MargenDescuento = 12;
	public final int ID_Existencia = 13;

	public final int ID_DescripcionProveedor = 14;
	public final int ID_Particion = 15;

	public final int ID_FechaVigencia = 17;

	public BOAlmacenMovil(Context context)
	{
		super("BOAlmacenMovil", context);
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

				Object ooIdAlmacenMovil = soapPart.getProperty("IdAlmacenMovil");
				Object ooProducto = soapPart.getProperty("Producto");
				Object ooCantidadInicial = soapPart.getProperty("CantidadInicial");
				Object ooIdProducto = soapPart.getProperty("IdProducto");
				Object ooUnidad = soapPart.getProperty("Unidad");
				Object ooCodigo = soapPart.getProperty("Codigo");
				Object ooPrecioPublico = soapPart.getProperty("PrecioPublico");
				Object ooMargenDescuento = soapPart.getProperty("MargenDescuento");
				Object ooExistencia = soapPart.getProperty("Existencia");

				Object ooDescripcionProveedor = soapPart.getProperty("DescripcionProveedor");
				Object ooParticion = soapPart.getProperty("Particion");

				BOAlmacenMovil categoria = new BOAlmacenMovil(this.context);

				if (ooIdAlmacenMovil != null)
				{
					categoria.IdAlmacenMovil = Integer.parseInt(ooIdAlmacenMovil.toString());
				}
				if (ooProducto != null)
				{
					categoria.Producto = ooProducto.toString();
				}
				if (ooCantidadInicial != null)
				{
					categoria.CantidadInicial = Float.parseFloat(ooCantidadInicial.toString().replace(',', '.'));
					categoria.Existencia = categoria.CantidadInicial;
				}
				if (ooIdProducto != null)
				{
					categoria.IdProducto = Integer.parseInt(ooIdProducto.toString());
				}
				if (ooUnidad != null)
				{
					categoria.Unidad = ooUnidad.toString();
				}
				if (ooCodigo != null)
				{
					categoria.Codigo = ooCodigo.toString();
				}
				if (ooPrecioPublico != null)
				{
					categoria.PrecioPublico = Float.parseFloat(ooPrecioPublico.toString().replace(',', '.'));
				}
				if (ooMargenDescuento != null)
				{
					categoria.MargenDescuento = ooMargenDescuento.toString();
				}
				if (ooExistencia != null)
				{
					categoria.Existencia = categoria.CantidadInicial;
				}

				if (ooDescripcionProveedor != null)
				{
					categoria.DescripcionProveedor = ooDescripcionProveedor.toString();
				}
				if (ooParticion != null)
				{
					categoria.Particion = Integer.parseInt(ooParticion.toString());
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
		BOAlmacenMovil p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdAlmacenMovil);
		}
		else if (idField == 2)
		{
			s = p.Producto;
		}
		else if (idField == 3)
		{
			s = String.valueOf(p.CantidadInicial);
		}
		else if (idField == 4)
		{
			s = String.valueOf(p.IdProducto);
		}
		else if (idField == 5)
		{
			s = p.Unidad;
		}
		else if (idField == 6)
		{
			s = String.valueOf(p.CantidadFinal);
		}
		else if (idField == 7)
		{
			s = String.valueOf(p.Devoluciones);
		}
		else if (idField == 8)
		{
			s = String.valueOf(p.Reposiciones);
		}
		else if (idField == 9)
		{
			s = String.valueOf(p.Venta);
		}
		else if (idField == 10)
		{
			s = p.Codigo;
		}
		else if (idField == 11)
		{
			s = String.valueOf(p.PrecioPublico);
		}
		else if (idField == 12)
		{
			s = p.MargenDescuento;
		}
		else if (idField == 13)
		{
			s = String.valueOf(p.Existencia);
		}
		else if (idField == 14)
		{
			s = p.DescripcionProveedor;
		}
		else if (idField == 15)
		{
			s = String.valueOf(p.Particion);
		}
		else if (idField == 17)
		{
			s = p.FechaVigencia;
		}

		return s;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
