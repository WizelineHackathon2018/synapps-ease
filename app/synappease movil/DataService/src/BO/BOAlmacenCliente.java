package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;
import com.System.Utils.Logg;

import android.content.Context;

public class BOAlmacenCliente extends BO implements rmsInterface
{
	public int IdAlmacenCliente = 0;
	public int IdProducto = 0;
	public int IdCliente = 0;

	public float Venta = 0;
	public float Existencia = 0;
	public float PrecioPublico = 0;
	public float CantidadInicial = 0;

	public int Particion = 0;
	public String Producto = "";
	public String Unidad = "";
	public String Codigo = "";
	public String MargenDescuento = "";
	public String DescripcionProveedor = "";

	public final int ID_IdAlmacenCliente = 1;
	public final int ID_IdProducto = 2;
	public final int ID_IdCliente = 3;

	public final int ID_Existencia = 4;
	public final int ID_Particion = 5;
	public final int ID_Producto = 6;
	public final int ID_CantidadInicial = 7;

	public final int ID_Unidad = 8;
	public final int ID_Codigo = 9;
	public final int ID_PrecioPublico = 10;
	public final int ID_MargenDescuento = 11;
	public final int ID_DescripcionProveedor = 12;

	public final int ID_Venta = 13;

	public BOAlmacenCliente(Context context)
	{
		super("BOAlmacenCliente", context);
	}

	@Override
	public String getFieldValue(int idField)
	{
		String s = "";
		BOAlmacenCliente p = this;

		if (idField == this.ID_IdAlmacenCliente)
		{
			s = String.valueOf(p.IdAlmacenCliente);
		}
		else if (idField == this.ID_IdProducto)
		{
			s = String.valueOf(p.IdProducto);
		}
		else if (idField == this.ID_IdCliente)
		{
			s = String.valueOf(p.IdCliente);
		}
		else if (idField == this.ID_Existencia)
		{
			s = String.valueOf(p.Existencia);
		}
		else if (idField == this.ID_Particion)
		{
			s = String.valueOf(p.Particion);
		}
		else if (idField == this.ID_Producto)
		{
			s = p.Producto;
		}
		else if (idField == this.ID_CantidadInicial)
		{
			s = String.valueOf(p.CantidadInicial);
		}
		else if (idField == this.ID_Unidad)
		{
			s = p.Unidad;
		}
		else if (idField == this.ID_Codigo)
		{
			s = p.Codigo;
		}
		else if (idField == this.ID_PrecioPublico)
		{
			s = String.valueOf(p.PrecioPublico);
		}
		else if (idField == this.ID_MargenDescuento)
		{
			s = p.MargenDescuento;
		}
		else if (idField == this.ID_DescripcionProveedor)
		{
			s = p.DescripcionProveedor;
		}
		else if (idField == this.ID_Venta)
		{
			s = String.valueOf(p.Venta);
		}

		return s;
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

				Object ooIdAlmacenCliente = soapPart.getProperty("IdAlmacenCliente");
				Object ooIdProducto = soapPart.getProperty("IdProducto");
				Object ooIdCliente = soapPart.getProperty("IdCliente");
				Object ooExistencia = soapPart.getProperty("Existencia");
				Object ooParticion = soapPart.getProperty("Particion");

				Object ooProducto = soapPart.getProperty("Producto");
				Object ooCantidadInicial = soapPart.getProperty("CantidadInicial");
				Object ooUnidad = soapPart.getProperty("Unidad");
				Object ooCodigo = soapPart.getProperty("Codigo");
				Object ooPrecioPublico = soapPart.getProperty("PrecioPublico");
				Object ooMargenDescuento = soapPart.getProperty("MargenDescuento");
				Object ooDescripcionProveedor = soapPart.getProperty("DescripcionProveedor");

				BOAlmacenCliente Precuestionario = new BOAlmacenCliente(this.context);

				if (ooIdAlmacenCliente != null)
				{
					Precuestionario.IdAlmacenCliente = Integer.parseInt(ooIdAlmacenCliente.toString());
				}
				if (ooIdProducto != null)
				{
					Precuestionario.IdProducto = Integer.parseInt(ooIdProducto.toString());
				}
				if (ooIdCliente != null)
				{
					Precuestionario.IdCliente = Integer.parseInt(ooIdCliente.toString());
				}
				if (ooParticion != null)
				{
					Precuestionario.Particion = Integer.parseInt(ooParticion.toString());
				}

				if (ooProducto != null)
				{
					Precuestionario.Producto = ooProducto.toString();
				}
				if (ooCantidadInicial != null)
				{
					Precuestionario.CantidadInicial = Float.parseFloat(ooCantidadInicial.toString().replace(',', '.'));
					Precuestionario.Existencia = Precuestionario.CantidadInicial;
				}
				if (ooUnidad != null)
				{
					Precuestionario.Unidad = ooUnidad.toString();
				}
				if (ooCodigo != null)
				{
					Precuestionario.Codigo = ooCodigo.toString();
				}
				if (ooPrecioPublico != null)
				{
					Precuestionario.PrecioPublico = Float.parseFloat(ooPrecioPublico.toString().replace(',', '.'));
				}
				if (ooMargenDescuento != null)
				{
					Precuestionario.MargenDescuento = ooMargenDescuento.toString();
				}
				if (ooDescripcionProveedor != null)
				{
					Precuestionario.DescripcionProveedor = ooDescripcionProveedor.toString();
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
	public String toString()
	{
		return this.Codigo + "-" + this.Producto;
	}

}

