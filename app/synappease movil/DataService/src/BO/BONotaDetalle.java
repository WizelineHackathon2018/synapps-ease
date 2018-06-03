package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BONotaDetalle extends BO implements rmsInterface
{
	public int IdProducto = 0;
	public String Nombre = "";
	public int IdCategoria = 0;
	public int IdUnidadMedida = 0;
	public int IdMoneda = 0;
	public float Cantidad = 0.0F;
	public float Precio = 0.0F;
	public int IdLista = 0;
	public String CodigoBarra = "";
	public int Impuesto = 0;
	public int Subtotal = 0;
	public String Categoria = "";
	public String UnidadMedida = "";
	public String Moneda = "";
	public String Lista = "";
	public int IdVenta = 0;

	public int Enviado = 0;
	public String Discriminante = "";

	public float Existencias = 0.0F;

	public String NotaDevolucion = "";
	public String NotaReposicion = "";
	public String NotaPromocion = "";

	public boolean Envase = false;

	public String FolioDia = "";
	public String GUIDV = "";
	public String GUIDVD = "";

	public int IdVentaDetallePedido = 0;
	public float Devolucion = 0;

	public float CantidadInicial = 0;

	public final int ID_IdProducto = 1;
	public final int ID_Nombre = 2;
	public final int ID_IdCategoria = 3;
	public final int ID_IdUnidadMedida = 4;
	public final int ID_IdMoneda = 5;
	public final int ID_Cantidad = 6;
	public final int ID_Precio = 7;
	public final int ID_IdLista = 8;
	public final int ID_CodigoBarra = 9;
	public final int ID_Impuesto = 10;
	public final int ID_Subtotal = 11;
	public final int ID_Categoria = 12;
	public final int ID_UnidadMedida = 13;
	public final int ID_Moneda = 14;
	public final int ID_Lista = 15;
	public final int ID_IdVenta = 16;

	public final int ID_Enviado = 17;
	public final int ID_Discriminante = 18;

	public final int ID_NotaDevolucion = 19;
	public final int ID_NotaReposicion = 20;
	public final int ID_NotaPromocion = 21;

	public final int ID_FolioDia = 22;
	public final int ID_GUIDV = 23;
	public final int ID_GUIDVD = 24;

	public final int ID_IdVentaDetallePedido = 25;
	public final int ID_Devolucion = 26;

	public BONotaDetalle(Context context)
	{
		super("BONotaDetalle", context);
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
		BONotaDetalle p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdProducto);
		}
		else if (idField == 2)
		{
			s = p.Nombre;
		}
		else if (idField == 3)
		{
			s = String.valueOf(p.IdCategoria);
		}
		else if (idField == 4)
		{
			s = String.valueOf(p.IdUnidadMedida);
		}
		else if (idField == 5)
		{
			s = String.valueOf(p.IdMoneda);
		}
		else if (idField == 6)
		{
			s = String.valueOf(p.Cantidad);
		}
		else if (idField == 7)
		{
			s = String.valueOf(p.Precio);
		}
		else if (idField == 8)
		{
			s = String.valueOf(p.IdLista);
		}
		else if (idField == 9)
		{
			s = p.CodigoBarra;
		}
		else if (idField == 10)
		{
			s = String.valueOf(p.Impuesto);
		}
		else if (idField == 11)
		{
			s = String.valueOf(p.Subtotal);
		}
		else if (idField == 12)
		{
			s = p.Categoria;
		}
		else if (idField == 13)
		{
			s = p.UnidadMedida;
		}
		else if (idField == 14)
		{
			s = p.Moneda;
		}
		else if (idField == 15)
		{
			s = p.Lista;
		}
		else if (idField == 16)
		{
			s = String.valueOf(p.IdVenta);
		}
		else if (idField == 17)
		{
			s = String.valueOf(p.Enviado);
		}
		else if (idField == 18)
		{
			s = p.Discriminante;
		}
		else if (idField == 19)
		{
			s = p.NotaDevolucion;
		}
		else if (idField == 20)
		{
			s = p.NotaReposicion;
		}
		else if (idField == 21)
		{
			s = p.NotaPromocion;
		}
		else if (idField == 22)
		{
			s = p.FolioDia;
		}
		else if (idField == 23)
		{
			s = p.GUIDV;
		}
		else if (idField == 24)
		{
			s = p.GUIDVD;
		}
		else if (idField == ID_IdVentaDetallePedido)
		{
			s = String.valueOf(p.IdVentaDetallePedido);
		}
		else if (idField == ID_Devolucion)
		{
			s = String.valueOf(p.Devolucion);
		}

		return s;	  
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
