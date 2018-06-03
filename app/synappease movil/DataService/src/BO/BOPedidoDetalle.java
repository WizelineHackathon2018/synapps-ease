package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOPedidoDetalle extends BO implements rmsInterface
{
	public int IdVenta = 0;
	public int IdProducto = 0;
	public int IdVentaDetalle = 0;
	public int PrecioPedido = 0;
	public String Descripcion = "";
	public float Cantidad = 0;
	public float PrecioUnitario = 0;
	public String Codigo = "";
	public String Unidad = "";

	public final int ID_IdVenta = 1;
	public final int ID_IdProducto = 2;
	public final int ID_IdVentaDetalle = 3;
	public final int ID_PrecioPedido = 4;
	public final int ID_Descripcion = 5;
	public final int ID_Cantidad = 6;
	public final int ID_PrecioUnitario = 7;
	public final int ID_Codigo = 8;
	public final int ID_Unidad = 9;

	public BOPedidoDetalle(Context context)
	{
		super("BOPedidoDetalle", context);
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

				Object ooIdVenta = soapPart.getProperty("IdVenta");
				Object ooIdProducto = soapPart.getProperty("IdProducto");
				Object ooIdVentaDetalle = soapPart.getProperty("IdVentaDetalle");
				Object ooPrecioPedido = soapPart.getProperty("PrecioPedido");
				Object ooDescripcion = soapPart.getProperty("Descripcion");
				Object ooCantidad = soapPart.getProperty("Cantidad");
				Object ooPrecioUnitario = soapPart.getProperty("PrecioUnitario");

				Object ooCodigo = soapPart.getProperty("Codigo");
				Object ooUnidad = soapPart.getProperty("Unidad");

				BOPedidoDetalle categoria = new BOPedidoDetalle(this.context);

				if (ooIdVenta != null)
				{
					categoria.IdVenta = Integer.parseInt(ooIdVenta.toString());
				}
				if (ooIdProducto != null)
				{
					categoria.IdProducto = Integer.parseInt(ooIdProducto.toString());
				}
				if (ooIdVentaDetalle != null)
				{
					categoria.IdVentaDetalle = Integer.parseInt(ooIdVentaDetalle.toString());
				}
				if (ooPrecioPedido != null)
				{
					categoria.PrecioPedido = Integer.parseInt(ooPrecioPedido.toString());
				}
				if (ooDescripcion != null)
				{
					categoria.Descripcion = ooDescripcion.toString();
				}
				if (ooCantidad != null)
				{
					categoria.Cantidad = Float.parseFloat(ooCantidad.toString());
				}
				if (ooPrecioUnitario != null)
				{
					categoria.PrecioUnitario = Float.parseFloat(ooPrecioUnitario.toString());
				}

				if (ooCodigo != null)
				{
					categoria.Codigo = ooCodigo.toString();
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
		BOPedidoDetalle p = this;

		if (idField == ID_IdVenta)
		{
			s = String.valueOf(p.IdVenta);
		}
		else if (idField == ID_IdProducto)
		{
			s = String.valueOf(p.IdProducto);
		}
		else if (idField == ID_IdVentaDetalle)
		{
			s = String.valueOf(p.IdVentaDetalle);
		}
		else if (idField == ID_PrecioPedido)
		{
			s = String.valueOf(p.PrecioPedido);
		}
		else if (idField == ID_Descripcion)
		{
			s = p.Descripcion;
		}
		else if (idField == ID_Cantidad)
		{
			s = String.valueOf(p.Cantidad);
		}
		else if (idField == ID_PrecioUnitario)
		{
			s = String.valueOf(p.PrecioUnitario);
		}
		else if (idField == ID_Codigo)
		{
			s = p.Codigo;
		}
		else if (idField == ID_Unidad)
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
