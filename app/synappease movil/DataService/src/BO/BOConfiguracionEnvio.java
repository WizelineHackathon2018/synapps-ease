package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsInterface;

import android.content.Context;

public class BOConfiguracionEnvio extends BO implements rmsInterface
{
	//Variables publicas
	public int IdConfiguracion = 0;
	public int DiaSemana = 0;
	public int MinutoRetardo = 0;

	public String HoraInicio = "";
	public String HoraFin = "";

	public String MinutosReseteoGPS = "";
	public String MinutosReseteoDispositivo = "";
	public String FechaReseteo = "";
	public String EnviarTodo = "";
	//Variables publicas

	//Enumeraciones
	public final int ID_IdConfiguracion = 1;
	public final int ID_DiaSemana = 2;
	public final int ID_MinutoRetardo = 3;

	public final int ID_HoraInicio = 4;
	public final int ID_HoraFin = 5;

	public final int ID_MinutosReseteoGPS = 6;
	public final int ID_MinutosReseteoDispositivo = 7;
	public final int ID_FechaReseteo = 8;
	public final int ID_EnviarTodo = 9;
	//Enumeraciones

	public BOConfiguracionEnvio(Context context)
	{
		super("BOConfiguracionEnvio", context);
	}

	//Implementaciones de los metodos de WS
	//********************************************************************************************
	public void AsignarValoresBOProceso()
	{
	}

	public List<BO> AsignarValoresProcesoBO(SoapObject wsColeccion)
	{
		if (wsColeccion != null)
		{
			int size = wsColeccion.getPropertyCount();
			List<BO> empList = new ArrayList<BO>();

			for(int i = 0; i < size; i++)
			{
				SoapObject soapPart = (SoapObject)wsColeccion.getProperty(i);

				//Aca obtenemos las propiedades
				Object ooIdConfiguracion = (Object)soapPart.getProperty("IdConfiguracion");
				Object ooDiaSemana = (Object)soapPart.getProperty("DiaSemana");
				Object ooMinutoRetardo = (Object)soapPart.getProperty("MinutoRetardo");

				Object ooHoraInicio = (Object)soapPart.getProperty("HoraInicio");
				Object ooHoraFin = (Object)soapPart.getProperty("HoraFin");

				Object ooMinutosReseteoGPS = (Object)soapPart.getProperty("MinutosReseteoGPS");
				Object ooMinutosReseteoDispositivo = (Object)soapPart.getProperty("MinutosReseteoDispositivo");
				Object ooFechaReseteo = (Object)soapPart.getProperty("FechaReseteo");
				Object ooEnviarTodo = (Object)soapPart.getProperty("EnviarTodo");

				//Aca obtenemos las propiedades

				BOConfiguracionEnvio gpsConfigura = new BOConfiguracionEnvio(this.context);
				if (ooIdConfiguracion != null)
				{
					gpsConfigura.IdConfiguracion = Integer.parseInt(ooIdConfiguracion.toString());
				}
				if (ooDiaSemana != null)
				{
					gpsConfigura.DiaSemana = Integer.parseInt(ooDiaSemana.toString());
				}
				if (ooMinutoRetardo != null)
				{
					gpsConfigura.MinutoRetardo = Integer.parseInt(ooMinutoRetardo.toString());
					//gpsConfigura.MinutoRetardo = 10;//lo ponemos a 10 min de retardo que se debe enviar
				}

				if (ooHoraInicio != null)
				{
					gpsConfigura.HoraInicio = ooHoraInicio.toString();
				}
				if (ooHoraFin != null)
				{
					gpsConfigura.HoraFin = ooHoraFin.toString();
				}

				if (ooMinutosReseteoGPS != null)
				{
					gpsConfigura.MinutosReseteoGPS = ooMinutosReseteoGPS.toString();
				}
				if (ooMinutosReseteoDispositivo != null)
				{
					gpsConfigura.MinutosReseteoDispositivo = ooMinutosReseteoDispositivo.toString();
				}
				if (ooFechaReseteo != null)//validamos q no salga algo invalido
				{
					gpsConfigura.FechaReseteo = getValorCadena(ooFechaReseteo.toString());
				}
				if (ooEnviarTodo != null)//validamos q no salga algo invalido
				{
					gpsConfigura.EnviarTodo = getValorCadena(ooEnviarTodo.toString());
				}

				empList.add(gpsConfigura);
			}

			if(empList.size() == 0)
			{
				return null;
			}
			else
			{
				return empList;	
			}
		}
		else
		{
			return null;
		}
	}

	@Override
	public String getFieldValue(int idField)
	{
		String s = "";
		BOConfiguracionEnvio p = this;

		if (idField == this.ID_IdConfiguracion)
		{
			s = String.valueOf(p.IdConfiguracion);
		}
		else if (idField == this.ID_DiaSemana)
		{
			s = String.valueOf(p.DiaSemana);
		}
		else if (idField == this.ID_MinutoRetardo)
		{
			s = String.valueOf(p.MinutoRetardo);
		}

		else if (idField == this.ID_HoraInicio)
		{
			s = p.HoraInicio;
		}
		else if (idField == this.ID_HoraFin)
		{
			s = p.HoraFin;
		}
		else if (idField == this.ID_MinutosReseteoGPS)
		{
			s = p.MinutosReseteoGPS;
		}
		else if (idField == this.ID_MinutosReseteoDispositivo)
		{
			s = p.MinutosReseteoDispositivo;
		}
		else if (idField == this.ID_FechaReseteo)
		{
			s = p.FechaReseteo;
		}
		else if (idField == this.ID_EnviarTodo)
		{
			s = p.EnviarTodo;
		}

		return s;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
