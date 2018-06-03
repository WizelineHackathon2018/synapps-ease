package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOImpresora extends BO implements rmsInterface
{
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
	
	public String IDPrinter = "";
	public int IdVendedor = 0;
	
	public final int ID_IzquierdaFolioCobro = 1;
	public final int ID_FolioCobro = 2;
	public final int ID_DerechaFolioCobro = 3;
	
	public final int ID_IzquierdaFolioVentaContado = 4;
	public final int ID_FolioVentaContado = 5;
	public final int ID_DerechaFolioVentaContado = 6;
	
	public final int ID_IzquierdaFolioVentaCredito = 7;
	public final int ID_FolioVentaCredito = 8;
	public final int ID_DerechaFolioVentaCredito = 9;
	
	public final int ID_CopiasFolioCobro = 10;
	public final int ID_CopiasFolioVentaContado = 11;
	public final int ID_CopiasFolioVentaCredito = 12;
	
	public final int ID_FechaFolioCobro = 13;
	public final int ID_FechaFolioVentaContado = 14;
	public final int ID_FechaFolioVentaCredito = 15;
	
	public final int ID_IDPrinter = 16;
	public final int ID_IdVendedor = 17;

	public BOImpresora(Context context)
	{
		super("BOImpresora", context);
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
		BOImpresora p = this;

		if (idField == ID_IzquierdaFolioCobro)
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
		else if (idField == ID_IDPrinter)
		{
			s = p.IDPrinter;
		}
		else if (idField == ID_IdVendedor)
		{
			s = String.valueOf(p.IdVendedor);
		}

		return s;	  
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
