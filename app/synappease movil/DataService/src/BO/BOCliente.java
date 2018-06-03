package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;
import com.System.Utils.Utils;

import android.content.Context;

public class BOCliente extends BO implements rmsInterface
{
	public int IdCliente = 0;
	public String Nombre = "";
	public String Direccion = "";
	public String Frecuencia = "";
	public String Visita = "";

	public String PosicionGPS = "";
	public int Promocion = 0;
	public int PromocionInicio = 0;
	public int PromocionFin = 0;

	public float SaldoPendiente = 0.0F;
	public int RequiereAutorizacion = 0;
	public String Discriminante = "";
	public String Codigo = "";

	public int MesEncuesta = 0;

	public int DiasMaximoEntrega = 0;
	public int PrecioPedido = 0;

	public final int ID_IdCliente = 1;
	public final int ID_Nombre = 2;
	public final int ID_Direccion = 3;
	public final int ID_Frecuencia = 4;
	public final int ID_Visita = 5;

	public final int ID_PosicionGPS = 6;
	public final int ID_Promocion = 7;
	public final int ID_PromocionInicio = 8;
	public final int ID_PromocionFin = 9;

	public final int ID_SaldoPendiente = 10;
	public final int ID_RequiereAutorizacion = 11;
	public final int ID_Discriminante = 12;
	public final int ID_Codigo = 13;

	public final int ID_MesEncuesta = 14;

	public final int ID_DiasMaximoEntrega = 15;
	public final int ID_PrecioPedido = 16;

	public BOCliente(Context context)
	{
		super("BOCliente2", context);
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
				BOCliente categoria = new BOCliente(this.context);
				try
				{
					SoapObject soapPart = (SoapObject)wsColeccion.getProperty(i);

					Object ooIdCliente = soapPart.getProperty("IdCliente");
					Object ooNombre = soapPart.getProperty("Nombre");
					Object ooDireccion = soapPart.getProperty("Direccion");
					Object ooFrecuencia = soapPart.getProperty("Frecuencia");
					Object ooPosicionGPS = soapPart.getProperty("PosicionGPS");
					Object ooSaldoPendiente = soapPart.getProperty("SaldoPendiente");
					Object ooRequiereAutorizacion = soapPart.getProperty("RequiereAutorizacion");
					Object ooDiscriminante = soapPart.getProperty("Discriminante");
					Object ooCodigo = soapPart.getProperty("Codigo");

					Object ooMesEncuesta = soapPart.getProperty("MesEncuesta");

					Object ooDiasMaximoEntrega = soapPart.getProperty("DiasMaximoEntrega");
					Object ooPrecioPedido = soapPart.getProperty("PrecioPedido");

					if (ooIdCliente != null)
					{
						categoria.IdCliente = Integer.parseInt(ooIdCliente.toString());
					}
					if (ooNombre != null)
					{
						String nombre = getValorCadena(ooNombre.toString()).toUpperCase();
						try
						{
							System.out.println(nombre);

							String[] palabras = Utils.Split(nombre, " ");

							if (palabras.length > 1)
							{
								int residuo = palabras.length % 2;
								if (residuo == 0)
								{
									int mitad = 0;
									mitad = palabras.length / 2;

									String palabra1 = "";
									String palabra2 = "";

									for (int x = 0; x < mitad; x++)
									{
										palabra1 = palabra1 + palabras[x] + " ";
										palabra2 = palabra2 + palabras[(mitad + x)] + " ";
									}

									if (palabra1.equalsIgnoreCase(palabra2))
									{
										categoria.Nombre = palabra1;
									}
									else
									{
										categoria.Nombre = nombre;
									}

								}
								else
								{
									categoria.Nombre = nombre;
								}
							}
							else
							{
								categoria.Nombre = nombre;
							}
						}
						catch (Exception ex)
						{
							categoria.Nombre = nombre;
						}
					}
					if (ooDireccion != null)
					{
						categoria.Direccion = getValorCadena(ooDireccion.toString());
					}
					if (ooFrecuencia != null)
					{
						categoria.Frecuencia = getValorCadena(ooFrecuencia.toString());
					}
					if (ooPosicionGPS != null)
					{
						categoria.PosicionGPS = getValorCadena(ooPosicionGPS.toString());
					}
					if (ooSaldoPendiente != null)
					{
						categoria.SaldoPendiente = Float.parseFloat(ooSaldoPendiente.toString());
					}
					if (ooRequiereAutorizacion != null)
					{
						categoria.RequiereAutorizacion = Integer.parseInt(ooRequiereAutorizacion.toString());
					}
					if (ooDiscriminante != null)
					{
						categoria.Discriminante = getValorCadena(ooDiscriminante.toString());
					}
					if (ooCodigo != null)
					{
						categoria.Codigo = getValorCadena(ooCodigo.toString());
					}
					if (ooMesEncuesta != null)
					{
						categoria.MesEncuesta = Integer.parseInt(ooMesEncuesta.toString());
					}

					if (ooDiasMaximoEntrega != null)
					{
						categoria.DiasMaximoEntrega = Integer.parseInt(ooDiasMaximoEntrega.toString());
					}
					if (ooPrecioPedido != null)
					{
						categoria.PrecioPedido = Integer.parseInt(ooPrecioPedido.toString());
					}

					categoria.Visita = "-";
				}
				catch (Exception ex)
				{
					//ooIdCliente = 0;
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
		BOCliente p = this;

		if (idField == 1)
		{
			s = String.valueOf(p.IdCliente);
		}
		else if (idField == 2)
		{
			s = p.Nombre;
		}
		else if (idField == 3)
		{
			s = p.Direccion;
		}
		else if (idField == 4)
		{
			s = p.Frecuencia;
		}
		else if (idField == 5)
		{
			s = p.Visita;
		}
		else if (idField == 6)
		{
			s = p.PosicionGPS;
		}
		else if (idField == 10)
		{
			s = String.valueOf(p.SaldoPendiente);
		}
		else if (idField == 11)
		{
			s = String.valueOf(p.RequiereAutorizacion);
		}
		else if (idField == 12)
		{
			s = p.Discriminante;
		}
		else if (idField == 12)
		{
			s = p.Codigo;
		}
		else if (idField == 13)
		{
			try
			{
				s = String.valueOf(p.MesEncuesta);
			}
			catch (Exception localException1) {
			}
		}

		else if (idField == ID_DiasMaximoEntrega)
		{
			s = String.valueOf(p.DiasMaximoEntrega);
		}
		else if (idField == ID_PrecioPedido)
		{
			s = String.valueOf(p.PrecioPedido);
		}

		return s;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
