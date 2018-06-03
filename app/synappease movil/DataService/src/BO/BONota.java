package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BONota extends BO implements rmsInterface
{
	public int IdVenta = 0;
	public String Status = "";
	public String TipoDocumento = "";
	public String Folio = "";
	public String FechaCreacion = "";
	public Date FechaVencimiento = new Date();
	public Date FechaEntrega = new Date();
	public String Comentarios = "";
	public int Sincronizado = 0;
	public int Impreso = 0;
	public String Referencia = "";
	public String DetReferencia = "";
	public int Revisado = 0;

	public int Descuento = 0;
	public float DescuentoMonto = 0.0F;
	public float TipoCambio = 0.0F;
	public float Subtotal = 0.0F;

	public double Impuestos = 0.0D;
	public double Total = 0.0D;
	public double Saldo = 0.0D;

	public double PagoEfectivo = 0.0D;
	public double PagoCheque = 0.0D;

	public double CreditoMonto = 0.0D;
	public double Abono = 0.0D;

	public int Credito = 0;

	public int IdCliente = 0;
	public int IdVendedor = 0;
	public int IdMoneda = 0;
	public int IdZona = 0;
	public int IdListaPrecio = 0;

	public int IdCobrador = 0;
	public String Cliente = "";
	public String Vendedor = "";
	public String Moneda = "";
	public String Zona = "";
	public String ListaPrecio = "";
	public String Cobrador = "";

	public int Enviado = 0;
	public int EnviadoCompleto = 0;
	public int IdItinerario = 0;

	public String Observacion = "";
	public String Discriminante = "";

	public String Fecha = "";

	public String FolioDia = "";
	public String GUIDV = "";

	public double PagoDeposito = 0.0D;
	public double PagoTransferencia = 0.0D;

	public String NombreImpreso = "";
	public String FechaCheque = "";
	public String Banco = "";
	public String ChequeNo = "";

	public double SaldoAnterior = 0.0D;
	public double NuevoSaldo = 0.0D;
	public String DiscriminantePago = "";

	public String TipoOperacion = "";

	public int Cancelar = 0;

	public String Codigo = "";
	public String RazonSocial = "";
	public String Direccion = "";

	public int IdEmpresa = 0;

	public int IdVentaPedido = 0;

	public final int ID_IdVenta = 1;
	public final int ID_Status = 2;
	public final int ID_TipoDocumento = 3;
	public final int ID_Folio = 4;
	public final int ID_FechaCreacion = 5;
	public final int ID_FechaVencimiento = 6;
	public final int ID_FechaEntrega = 7;
	public final int ID_Comentarios = 8;
	public final int ID_Sincronizado = 9;
	public final int ID_Impreso = 10;
	public final int ID_Referencia = 11;
	public final int ID_DetReferencia = 12;
	public final int ID_Revisado = 13;
	public final int ID_Credito = 14;
	public final int ID_CreditoMonto = 15;
	public final int ID_Descuento = 16;
	public final int ID_DescuentoMonto = 17;
	public final int ID_TipoCambio = 18;
	public final int ID_Subtotal = 19;
	public final int ID_Impuestos = 20;
	public final int ID_Total = 21;
	public final int ID_Saldo = 22;
	public final int ID_IdCliente = 23;
	public final int ID_IdVendedor = 24;
	public final int ID_IdMoneda = 25;
	public final int ID_IdZona = 26;
	public final int ID_IdListaPrecio = 27;
	public final int ID_IdCobrador = 28;
	public final int ID_Cliente = 29;
	public final int ID_Vendedor = 30;
	public final int ID_Moneda = 31;
	public final int ID_Zona = 32;
	public final int ID_ListaPrecio = 33;
	public final int ID_Cobrador = 34;

	public final int ID_Enviado = 35;
	public final int ID_EnviadoCompleto = 36;
	public final int ID_IdItinerario = 37;

	public final int ID_PagoEfectivo = 38;
	public final int ID_PagoCheque = 39;

	public final int ID_Observacion = 40;
	public final int ID_Discriminante = 41;

	public final int ID_Fecha = 42;

	public final int ID_FolioDia = 43;
	public final int ID_GUIDV = 44;

	public final int ID_PagoDeposito = 45;
	public final int ID_PagoTransferencia = 46;

	public final int ID_NombreImpreso = 47;
	public final int ID_FechaCheque = 48;
	public final int ID_Banco = 49;
	public final int ID_ChequeNo = 50;

	public final int ID_SaldoAnterior = 51;
	public final int ID_NuevoSaldo = 52;
	public final int ID_DiscriminantePago = 53;

	public final int ID_Abono = 54;

	public final int ID_TipoOperacion = 55;

	public final int ID_Cancelar = 56;

	public final int ID_Codigo = 57;
	public final int ID_RazonSocial = 58;
	public final int ID_Direccion = 59;

	public final int ID_IdEmpresa = 60;

	public final int ID_IdVentaPedido = 61;

	public BONota(Context context)
	{
		super("BONota", context);
	}

	public void AsignarValoresBOProceso()
	{
	}

	public List<BO> AsignarValoresProcesoBO(SoapObject wsColeccion)
	{
		if (wsColeccion != null)
		{
			int size = wsColeccion.getPropertyCount();
			List<BO> listaVenta = new ArrayList<BO>();

			for (int i = 0; i < size; i++)
			{
				SoapObject soapPart = (SoapObject)wsColeccion.getProperty(i);

				Object ooIdVenta = soapPart.getProperty("IdVenta");
				Object ooStatus = soapPart.getProperty("Status");
				Object ooTipoDocumento = soapPart.getProperty("TipoDocumento");
				Object ooFolio = soapPart.getProperty("Folio");
				Object ooFechaCreacion = soapPart.getProperty("FechaCreacion");
				Object ooFechaVencimiento = soapPart.getProperty("FechaVencimiento");
				Object ooFechaEntrega = soapPart.getProperty("FechaEntrega");
				Object ooComentarios = soapPart.getProperty("Comentarios");
				Object ooSincronizado = soapPart.getProperty("Sincronizado");
				Object ooImpreso = soapPart.getProperty("Impreso");
				Object ooReferencia = soapPart.getProperty("Referencia");
				Object ooDetReferencia = soapPart.getProperty("DetReferencia");
				Object ooRevisado = soapPart.getProperty("Revisado");
				Object ooCredito = soapPart.getProperty("Credito");
				Object ooCreditoMonto = soapPart.getProperty("CreditoMonto");
				Object ooDescuento = soapPart.getProperty("Descuento");
				Object ooDescuentoMonto = soapPart.getProperty("DescuentoMonto");
				Object ooTipoCambio = soapPart.getProperty("TipoCambio");
				Object ooSubtotal = soapPart.getProperty("Subtotal");
				Object ooImpuestos = soapPart.getProperty("Impuestos");
				Object ooTotal = soapPart.getProperty("Total");
				Object ooSaldo = soapPart.getProperty("Saldo");
				Object ooIdCliente = soapPart.getProperty("IdCliente");
				Object ooIdVendedor = soapPart.getProperty("IdVendedor");
				Object ooIdMoneda = soapPart.getProperty("IdMoneda");
				Object ooIdZona = soapPart.getProperty("IdZona");
				Object ooIdListaPrecio = soapPart.getProperty("IdListaPrecio");

				Object ooIdCobrador = soapPart.getProperty("IdCobrador");
				Object ooCliente = soapPart.getProperty("Cliente");
				Object ooVendedor = soapPart.getProperty("Vendedor");
				Object ooMoneda = soapPart.getProperty("Moneda");
				Object ooZona = soapPart.getProperty("Zona");
				Object ooListaPrecio = soapPart.getProperty("ListaPrecio");
				Object ooCobrador = soapPart.getProperty("Cobrador");

				BONota venta = new BONota(this.context);

				if (ooIdVenta != null)
				{
					venta.IdVenta = Integer.parseInt(ooIdVenta.toString());
				}
				if (ooStatus != null)
				{
					venta.Status = ooStatus.toString();
				}
				if (ooTipoDocumento != null)
				{
					venta.TipoDocumento = ooTipoDocumento.toString();
				}
				if (ooFolio != null)
				{
					venta.Folio = ooFolio.toString();
				}
				if (ooFechaCreacion != null)
				{
					venta.FechaCreacion = ooFechaCreacion.toString();
				}
				if (ooFechaVencimiento != null)
				{
					venta.FechaVencimiento.setTime(Long.parseLong(ooFechaVencimiento.toString()));
				}
				if (ooFechaEntrega != null)
				{
					venta.FechaEntrega.setTime(Long.parseLong(ooFechaEntrega.toString()));
				}
				if (ooComentarios != null)
				{
					venta.Comentarios = ooComentarios.toString();
				}
				if (ooSincronizado != null)
				{
					venta.Sincronizado = Integer.parseInt(ooSincronizado.toString());
				}
				if (ooImpreso != null)
				{
					venta.Impreso = Integer.parseInt(ooImpreso.toString());
				}
				if (ooReferencia != null)
				{
					venta.Referencia = ooReferencia.toString();
				}
				if (ooDetReferencia != null)
				{
					venta.DetReferencia = ooDetReferencia.toString();
				}
				if (ooRevisado != null)
				{
					venta.Revisado = Integer.parseInt(ooRevisado.toString());
				}
				if (ooCredito != null)
				{
					venta.Credito = Integer.parseInt(ooCredito.toString());
				}
				if (ooCreditoMonto != null)
				{
					venta.CreditoMonto = Double.parseDouble(ooCreditoMonto.toString());
				}
				if (ooDescuento != null)
				{
					venta.Descuento = Integer.parseInt(ooDescuento.toString());
				}
				if (ooDescuentoMonto != null)
				{
					venta.DescuentoMonto = Float.parseFloat(ooDescuentoMonto.toString());
				}
				if (ooTipoCambio != null)
				{
					venta.TipoCambio = Float.parseFloat(ooTipoCambio.toString());
				}
				if (ooSubtotal != null)
				{
					venta.Subtotal = Float.parseFloat(ooSubtotal.toString());
				}
				if (ooImpuestos != null)
				{
					venta.Impuestos = Float.parseFloat(ooImpuestos.toString());
				}
				if (ooTotal != null)
				{
					venta.Total = Float.parseFloat(ooTotal.toString());
				}
				if (ooSaldo != null)
				{
					venta.Saldo = Float.parseFloat(ooSaldo.toString());
				}
				if (ooIdCliente != null)
				{
					venta.IdCliente = Integer.parseInt(ooIdCliente.toString());
				}
				if (ooIdVendedor != null)
				{
					venta.IdVendedor = Integer.parseInt(ooIdVendedor.toString());
				}
				if (ooIdMoneda != null)
				{
					venta.IdMoneda = Integer.parseInt(ooIdMoneda.toString());
				}
				if (ooIdZona != null)
				{
					venta.IdZona = Integer.parseInt(ooIdZona.toString());
				}
				if (ooIdListaPrecio != null)
				{
					venta.IdListaPrecio = Integer.parseInt(ooIdListaPrecio.toString());
				}
				if (ooIdCobrador != null)
				{
					venta.IdCobrador = Integer.parseInt(ooIdCobrador.toString());
				}
				if (ooCliente != null)
				{
					venta.Cliente = ooCliente.toString();
				}
				if (ooVendedor != null)
				{
					venta.Vendedor = ooVendedor.toString();
				}
				if (ooMoneda != null)
				{
					venta.Moneda = ooMoneda.toString();
				}
				if (ooZona != null)
				{
					venta.Zona = ooZona.toString();
				}
				if (ooListaPrecio != null)
				{
					venta.ListaPrecio = ooListaPrecio.toString();
				}
				if (ooCobrador != null)
				{
					venta.Cobrador = ooCobrador.toString();
				}

				listaVenta.add(venta);
			}
			if(listaVenta.size() == 0)
			{
				return null;
			}
			else
			{
				return listaVenta;
			}
		}

		return null;
	}

	@Override
	public String getFieldValue(int idField)
	{
		String s = "";
		BONota p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdVenta);
		}
		else if (idField == 2)
		{
			s = p.Status;
		}
		else if (idField == 3)
		{
			s = p.TipoDocumento;
		}
		else if (idField == 4)
		{
			s = String.valueOf(p.Folio);
		}
		else if (idField == 5)
		{
			s = p.FechaCreacion;
		}
		else if (idField != 6)
		{
			if (idField != 7)
			{
				if (idField == 8)
				{
					s = p.Comentarios;
				}
				else if (idField == 9)
				{
					s = String.valueOf(p.Sincronizado);
				}
				else if (idField == 10)
				{
					s = String.valueOf(p.Impreso);
				}
				else if (idField == 11)
				{
					s = p.Referencia;
				}
				else if (idField == 12)
				{
					s = p.DetReferencia;
				}
				else if (idField == 13)
				{
					s = String.valueOf(p.Revisado);
				}
				else if (idField == 14)
				{
					s = String.valueOf(p.Credito);
				}
				else if (idField == 15)
				{
					s = String.valueOf(p.CreditoMonto);
				}
				else if (idField == 16)
				{
					s = String.valueOf(p.Descuento);
				}
				else if (idField == 17)
				{
					s = String.valueOf(p.DescuentoMonto);
				}
				else if (idField == 18)
				{
					s = String.valueOf(p.TipoCambio);
				}
				else if (idField == 19)
				{
					s = String.valueOf(p.Subtotal);
				}
				else if (idField == 20)
				{
					s = String.valueOf(p.Impuestos);
				}
				else if (idField == 21)
				{
					s = String.valueOf(p.Total);
				}
				else if (idField == 22)
				{
					s = String.valueOf(p.Saldo);
				}
				else if (idField == 23)
				{
					s = String.valueOf(p.IdCliente);
				}
				else if (idField == 24)
				{
					s = String.valueOf(p.IdVendedor);
				}
				else if (idField == 25)
				{
					s = String.valueOf(p.IdMoneda);
				}
				else if (idField == 26)
				{
					s = String.valueOf(p.IdZona);
				}
				else if (idField == 27)
				{
					s = String.valueOf(p.IdListaPrecio);
				}
				else if (idField == 28)
				{
					s = String.valueOf(p.IdCobrador);
				}
				else if (idField == 29)
				{
					s = p.Cliente;
				}
				else if (idField == 30)
				{
					s = p.Vendedor;
				}
				else if (idField == 31)
				{
					s = p.Moneda;
				}
				else if (idField == 32)
				{
					s = p.Zona;
				}
				else if (idField == 33)
				{
					s = p.ListaPrecio;
				}
				else if (idField == 34)
				{
					s = p.Cobrador;
				}
				else if (idField == 35)
				{
					s = String.valueOf(p.Enviado);
				}
				else if (idField == 36)
				{
					s = String.valueOf(p.EnviadoCompleto);
				}
				else if (idField == 37)
				{
					s = String.valueOf(p.IdItinerario);
				}
				else if (idField == 38)
				{
					s = String.valueOf(p.PagoEfectivo);
				}
				else if (idField == 39)
				{
					s = String.valueOf(p.PagoCheque);
				}
				else if (idField == 40)
				{
					s = p.Observacion;
				}
				else if (idField == 41)
				{
					s = p.Discriminante;
				}
				else if (idField == 42)
				{
					s = p.Fecha;
				}
				else if (idField == 43)
				{
					s = p.FolioDia;
				}
				else if (idField == 44)
				{
					s = p.GUIDV;
				}
				else if (idField == 45)
				{
					s = String.valueOf(p.PagoDeposito);
				}
				else if (idField == 46)
				{
					s = String.valueOf(p.PagoTransferencia);
				}
				else if (idField == 47)
				{
					s = p.NombreImpreso;
				}
				else if (idField == 48)
				{
					s = p.FechaCheque;
				}
				else if (idField == 49)
				{
					s = p.Banco;
				}
				else if (idField == 50)
				{
					s = p.ChequeNo;
				}
				else if (idField == 51)
				{
					s = String.valueOf(p.SaldoAnterior);
				}
				else if (idField == 52)
				{
					s = String.valueOf(p.NuevoSaldo);
				}
				else if (idField == 53)
				{
					s = p.DiscriminantePago;
				}
				else if (idField == 54)
				{
					s = String.valueOf(p.Abono);
				}
				else if (idField == 55)
				{
					s = p.TipoOperacion;
				}
				else if (idField == 56)
				{
					s = String.valueOf(p.Cancelar);
				}
				else if (idField == 57)
				{
					s = p.Codigo;
				}
				else if (idField == 58)
				{
					s = p.RazonSocial;
				}
				else if (idField == 59)
				{
					s = p.Direccion;
				}
				else if (idField == 60)
				{
					s = String.valueOf(p.IdEmpresa);
				}
				else if (idField == this.ID_IdVentaPedido)
				{
					s = String.valueOf(p.IdVentaPedido);
				}

			}
		}

		return s;	  
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
