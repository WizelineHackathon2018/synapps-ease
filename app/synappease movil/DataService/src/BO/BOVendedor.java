package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOVendedor extends BO implements rmsInterface
{
	public int IdVendedor = 0;
	public int IdEmpresa = 0;
	public String Nombre = "";
	public String IDImpresora = "";

	public int MinutoCancelacion = 0;
	public int RangoClienteGPS = 0;

	public String Usuario = "";
	public String Password = "";

	public String IzquierdaFolioCobro = "";
	public int FolioCobro = 0;
	public String DerechaFolioCobro = "";

	public String IzquierdaFolioVentaContado = "";
	public int FolioVentaContado = 0;
	public String DerechaFolioVentaContado = "";

	public String IzquierdaFolioVentaCredito = "";
	public int FolioVentaCredito = 0;
	public String DerechaFolioVentaCredito = "";

	public int CopiasFolioCobro = 0;
	public int CopiasFolioVentaContado = 0;
	public int CopiasFolioVentaCredito = 0;

	public String FechaFolioCobro = "";
	public String FechaFolioVentaContado = "";
	public String FechaFolioVentaCredito = "";

	public final int ID_IdVendedor = 1;
	public final int ID_IdEmpresa = 2;
	public final int ID_Nombre = 3;

	public final int ID_IDImpresora = 4;
	public final int ID_MinutoCancelacion = 5;
	public final int ID_RangoClienteGPS = 6;

	public final int ID_IzquierdaFolioCobro = 7;
	public final int ID_FolioCobro = 8;
	public final int ID_DerechaFolioCobro = 9;

	public final int ID_IzquierdaFolioVentaContado = 10;
	public final int ID_FolioVentaContado = 11;
	public final int ID_DerechaFolioVentaContado = 12;

	public final int ID_IzquierdaFolioVentaCredito = 13;
	public final int ID_FolioVentaCredito = 14;
	public final int ID_DerechaFolioVentaCredito = 15;

	public final int ID_CopiasFolioCobro = 16;
	public final int ID_CopiasFolioVentaContado = 17;
	public final int ID_CopiasFolioVentaCredito = 18;

	public final int ID_FechaFolioCobro = 19;
	public final int ID_FechaFolioVentaContado = 20;
	public final int ID_FechaFolioVentaCredito = 21;

	public BOVendedor(Context context)
	{
		super("BOVendedor2", context);
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

				Object ooIdVendedor = soapPart.getProperty("IdVendedor");
				Object ooIdEmpresa = soapPart.getProperty("IdEmpresa");
				Object ooNombre = soapPart.getProperty("Nombre");

				Object ooIDImpresora = soapPart.getProperty("IDImpresora");
				Object ooMinutoCancelacion = soapPart.getProperty("MinutoCancelacion");
				Object ooRangoClienteGPS = soapPart.getProperty("RangoClienteGPS");

				Object ooIzquierdaFolioCobro = soapPart.getProperty("IzquierdaFolioCobro");
				Object ooFolioCobro = soapPart.getProperty("FolioCobro");
				Object ooDerechaFolioCobro = soapPart.getProperty("DerechaFolioCobro");

				Object ooIzquierdaFolioVentaContado = soapPart.getProperty("IzquierdaFolioVentaContado");
				Object ooFolioVentaContado = soapPart.getProperty("FolioVentaContado");
				Object ooDerechaFolioVentaContado = soapPart.getProperty("DerechaFolioVentaContado");

				Object ooIzquierdaFolioVentaCredito = soapPart.getProperty("IzquierdaFolioVentaCredito");
				Object ooFolioVentaCredito = soapPart.getProperty("FolioVentaCredito");
				Object ooDerechaFolioVentaCredito = soapPart.getProperty("DerechaFolioVentaCredito");

				Object ooCopiasFolioCobro = soapPart.getProperty("CopiasFolioCobro");
				Object ooCopiasFolioVentaContado = soapPart.getProperty("CopiasFolioVentaContado");
				Object ooCopiasFolioVentaCredito = soapPart.getProperty("CopiasFolioVentaCredito");

				Object ooFechaFolioCobro = soapPart.getProperty("FechaFolioCobro");
				Object ooFechaFolioVentaContado = soapPart.getProperty("FechaFolioVentaContado");
				Object ooFechaFolioVentaCredito = soapPart.getProperty("FechaFolioVentaCredito");

				BOVendedor categoria = new BOVendedor(this.context);

				if (ooIdVendedor != null)
				{
					categoria.IdVendedor = Integer.parseInt(ooIdVendedor.toString());
				}
				if (ooIdEmpresa != null)
				{
					categoria.IdEmpresa = Integer.parseInt(ooIdEmpresa.toString());
				}
				if (ooNombre != null)
				{
					categoria.Nombre = ooNombre.toString();
				}
				if (ooIDImpresora != null)
				{
					categoria.IDImpresora = this.getValorCadena(ooIDImpresora.toString());
				}
				if (ooMinutoCancelacion != null)
				{
					categoria.MinutoCancelacion = Integer.parseInt(ooMinutoCancelacion.toString());
				}
				if (ooRangoClienteGPS != null)
				{
					categoria.RangoClienteGPS = Integer.parseInt(ooRangoClienteGPS.toString());
				}
				if (ooIzquierdaFolioCobro != null)
				{
					categoria.IzquierdaFolioCobro = this.getValorCadena(ooIzquierdaFolioCobro.toString());
				}
				if (ooFolioCobro != null)
				{
					categoria.FolioCobro = Integer.parseInt(ooFolioCobro.toString());
				}
				if (ooDerechaFolioCobro != null)
				{
					categoria.DerechaFolioCobro = this.getValorCadena(ooDerechaFolioCobro.toString());
				}

				if (ooIzquierdaFolioVentaContado != null)
				{
					categoria.IzquierdaFolioVentaContado = this.getValorCadena(ooIzquierdaFolioVentaContado.toString());
				}
				if (ooFolioVentaContado != null)
				{
					categoria.FolioVentaContado = Integer.parseInt(ooFolioVentaContado.toString());
				}
				if (ooDerechaFolioVentaContado != null)
				{
					categoria.DerechaFolioVentaContado = this.getValorCadena(ooDerechaFolioVentaContado.toString());
				}
				if (ooIzquierdaFolioVentaCredito != null)
				{
					categoria.IzquierdaFolioVentaCredito = this.getValorCadena(ooIzquierdaFolioVentaCredito.toString());
				}
				if (ooFolioVentaCredito != null)
				{
					categoria.FolioVentaCredito = Integer.parseInt(ooFolioVentaCredito.toString());
				}
				if (ooDerechaFolioVentaCredito != null)
				{
					categoria.DerechaFolioVentaCredito = this.getValorCadena(ooDerechaFolioVentaCredito.toString());
				}
				if (ooCopiasFolioCobro != null)
				{
					categoria.CopiasFolioCobro = Integer.parseInt(ooCopiasFolioCobro.toString());
				}
				if (ooCopiasFolioVentaContado != null)
				{
					categoria.CopiasFolioVentaContado = Integer.parseInt(ooCopiasFolioVentaContado.toString());
				}
				if (ooCopiasFolioVentaCredito != null)
				{
					categoria.CopiasFolioVentaCredito = Integer.parseInt(ooCopiasFolioVentaCredito.toString());
				}
				if (ooFechaFolioCobro != null)
				{
					categoria.FechaFolioCobro = ooFechaFolioCobro.toString();
				}
				if (ooFechaFolioVentaContado != null)
				{
					categoria.FechaFolioVentaContado = ooFechaFolioVentaContado.toString();
				}
				if (ooFechaFolioVentaCredito != null)
				{
					categoria.FechaFolioVentaCredito = ooFechaFolioVentaCredito.toString();
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
		BOVendedor p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdVendedor);
		}
		else if (idField == 2)
		{
			s = String.valueOf(p.IdEmpresa);
		}
		else if (idField == 3)
		{
			s = p.Nombre;
		}
		else if (idField == 4)
		{
			s = p.IDImpresora;
		}
		else if (idField == 5)
		{
			s = String.valueOf(p.MinutoCancelacion);
		}
		else if (idField == 6)
		{
			s = String.valueOf(p.RangoClienteGPS);
		}

		else if (idField == ID_IzquierdaFolioCobro)
		{
			s = p.IzquierdaFolioCobro;
		}
		else if (idField == ID_FolioCobro)
		{
			s = String.valueOf(p.FolioCobro);
		}
		else if (idField == ID_DerechaFolioCobro)
		{
			s = p.DerechaFolioCobro;
		}

		else if (idField == ID_IzquierdaFolioVentaContado)
		{
			s = p.IzquierdaFolioVentaContado;
		}
		else if (idField == ID_FolioVentaContado)
		{
			s = String.valueOf(p.FolioVentaContado);
		}
		else if (idField == ID_DerechaFolioVentaContado)
		{
			s = p.DerechaFolioVentaContado;
		}

		else if (idField == ID_IzquierdaFolioVentaCredito)
		{
			s = p.IzquierdaFolioVentaCredito;
		}
		else if (idField == ID_FolioVentaCredito)
		{
			s = String.valueOf(p.FolioVentaCredito);
		}
		else if (idField == ID_DerechaFolioVentaCredito)
		{
			s = p.DerechaFolioVentaCredito;
		}

		else if (idField == ID_CopiasFolioCobro)
		{
			s = String.valueOf(p.CopiasFolioCobro);
		}
		else if (idField == ID_CopiasFolioVentaContado)
		{
			s = String.valueOf(p.CopiasFolioVentaContado);
		}
		else if (idField == ID_CopiasFolioVentaCredito)
		{
			s = String.valueOf(p.CopiasFolioVentaCredito);
		}

		else if (idField == ID_FechaFolioCobro)
		{
			s = p.FechaFolioCobro;
		}
		else if (idField == ID_FechaFolioVentaContado)
		{
			s = p.FechaFolioVentaContado;
		}
		else if (idField == ID_FechaFolioVentaCredito)
		{
			s = p.FechaFolioVentaCredito;
		}

		return s;	  
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
