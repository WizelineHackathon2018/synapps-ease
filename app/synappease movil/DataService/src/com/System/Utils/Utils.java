package com.System.Utils;

import java.util.*;

import android.content.Context;

import com.System.Dispositivo.Info;

public class Utils 
{
	private static final String[] UNIDADES = { "", "UN ", "DOS ", "TRES ", 
		"CUATRO ", "CINCO ", "SEIS ", "SIETE ", "OCHO ", "NUEVE ", "DIEZ ", 
		"ONCE ", "DOCE ", "TRECE ", "CATORCE ", "QUINCE ", "DIECISEIS ", 
		"DIECISIETE ", "DIECIOCHO ", "DIECINUEVE ", "VEINTE " };

	private static final String[] DECENAS = { "VENTI", "TREINTA ", "CUARENTA ", 
		"CINCUENTA ", "SESENTA ", "SETENTA ", "OCHENTA ", "NOVENTA ", 
	"CIEN " };

	private static final String[] CENTENAS = { "CIENTO ", "DOSCIENTOS ", 
		"TRESCIENTOS ", "CUATROCIENTOS ", "QUINIENTOS ", "SEISCIENTOS ", 
		"SETECIENTOS ", "OCHOCIENTOS ", "NOVECIENTOS " };	

	public static String getMesNombre()
	{
		Calendar c = Calendar.getInstance();
		int messist = c.get(2);
		messist++;

		if (messist == 1)
			return "ENERO";
		if (messist == 2)
			return "FEBRERO";
		if (messist == 3)
			return "MARZO";
		if (messist == 4)
			return "ABRIL";
		if (messist == 5)
			return "MAYO";
		if (messist == 6)
			return "JUNIO";
		if (messist == 7)
			return "JULIO";
		if (messist == 8)
			return "AGOSTO";
		if (messist == 9)
			return "SEPTIEMBRE";
		if (messist == 10)
			return "OCTUBRE";
		if (messist == 11) {
			return "NOVIEMBRE";
		}
		return "DICIEMBRE";
	}

	public static int getDiaSemanaInglesa()
	{
		/*   98 */     Calendar cal = Calendar.getInstance();
		/*   99 */     int diaSemana = cal.get(7);
		if (diaSemana == 2)
			return 1;
		if (diaSemana == 3)
			return 2;
		if (diaSemana == 4)
			return 3;
		if (diaSemana == 5)
			return 4;
		if (diaSemana == 6)
			return 5;
		if (diaSemana == 7) {
			return 6;
		}
		return 7;
	}

	public static String ConvertNumberToLetter(double number)
			throws NumberFormatException
			{
		String converted = new String();

		double doubleNumber = number;
		if (doubleNumber > 999999999.0D) {
			throw new NumberFormatException(
					"El numero es mayor de 999'999.999, no es posible convertirlo");
		}

		String[] splitNumber = Split(String.valueOf(doubleNumber).replace('.', '#'), "#");

		int millon = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0], 8)) + 
				String.valueOf(getDigitAt(splitNumber[0], 7)) + 
				String.valueOf(getDigitAt(splitNumber[0], 6)));
		if (millon == 1)
			converted = "UN MILLON ";
		if (millon > 1) {
			converted = convertNumber(String.valueOf(millon)) + "MILLONES ";
		}

		int miles = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0], 5)) + 
				String.valueOf(getDigitAt(splitNumber[0], 4)) + 
				String.valueOf(getDigitAt(splitNumber[0], 3)));
		if (miles == 1)
			converted = converted + "MIL ";
		if (miles > 1) {
			converted = converted + convertNumber(String.valueOf(miles)) + "MIL ";
		}

		int cientos = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0], 2)) + 
				String.valueOf(getDigitAt(splitNumber[0], 1)) + 
				String.valueOf(getDigitAt(splitNumber[0], 0)));
		if (cientos == 1) {
			converted = converted + "UN";
		}
		if (millon + miles + cientos == 0)
			converted = converted + "CERO";
		if (cientos > 1) {
			converted = converted + convertNumber(String.valueOf(cientos));
		}
		converted = converted + "PESOS";

		int centavos = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[1], 2)) + 
				String.valueOf(getDigitAt(splitNumber[1], 1)) + 
				String.valueOf(getDigitAt(splitNumber[1], 0)));
		if (centavos == 1)
			converted = converted + " CON UN CENTAVO";
		if (centavos > 1) {
			converted = converted + " CON " + convertNumber(String.valueOf(centavos)) + 
					"CENTAVOS";
		}
		return converted;
			}

	public static String ConvertIntegerToLetter(double number)
			throws NumberFormatException
			{
		String converted = new String();

		double doubleNumber = number;
		if (doubleNumber > 999999999.0D) {
			throw new NumberFormatException(
					"El numero es mayor de 999'999.999, no es posible convertirlo");
		}

		String[] splitNumber = Split(String.valueOf(doubleNumber).replace('.', '#'), "#");

		int millon = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0], 8)) + 
				String.valueOf(getDigitAt(splitNumber[0], 7)) + 
				String.valueOf(getDigitAt(splitNumber[0], 6)));
		if (millon == 1)
			converted = "UN MILLON ";
		if (millon > 1) {
			converted = convertNumber(String.valueOf(millon)) + "MILLONES ";
		}

		int miles = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0], 5)) + 
				String.valueOf(getDigitAt(splitNumber[0], 4)) + 
				String.valueOf(getDigitAt(splitNumber[0], 3)));
		if (miles == 1)
			converted = converted + "MIL ";
		if (miles > 1) {
			converted = converted + convertNumber(String.valueOf(miles)) + "MIL ";
		}

		int cientos = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0], 2)) + 
				String.valueOf(getDigitAt(splitNumber[0], 1)) + 
				String.valueOf(getDigitAt(splitNumber[0], 0)));
		if (cientos == 1) {
			converted = converted + "UN";
		}
		if (millon + miles + cientos == 0)
			converted = converted + "CERO";
		if (cientos > 1) {
			converted = converted + convertNumber(String.valueOf(cientos));
		}

		return converted;
			}

	private static String convertNumber(String number)
	{
		if (number.length() > 3) {
			throw new NumberFormatException(
					"La longitud maxima debe ser 3 digitos");
		}
		String output = new String();
		if (getDigitAt(number, 2) != 0) {
			output = CENTENAS[(getDigitAt(number, 2) - 1)];
		}
		int k = Integer.parseInt(String.valueOf(getDigitAt(number, 1)) + 
				String.valueOf(getDigitAt(number, 0)));

		if (k <= 20) {
			output = output + UNIDADES[k];
		}
		else if ((k > 30) && (getDigitAt(number, 0) != 0))
			output = output + DECENAS[(getDigitAt(number, 1) - 2)] + "Y " + 
					UNIDADES[getDigitAt(number, 0)];
		else {
			output = output + DECENAS[(getDigitAt(number, 1) - 2)] + 
					UNIDADES[getDigitAt(number, 0)];
		}

		if ((getDigitAt(number, 2) == 1) && (k == 0)) {
			output = "CIEN";
		}
		return output;
	}

	private static int getDigitAt(String origin, int position) {
		if ((origin.length() > position) && (position >= 0))
			return origin.charAt(origin.length() - position - 1) - '0';
		return 0;
	}

	// -> ###.5 - ###.9
	public static String RedondearValor(float valor)
	{
		if (valor < 0.0F)
		{
			if (valor * -1.0F < Float.parseFloat("0.009999"))
			{
				valor *= -1.0F;
			}
		}
		
		if (valor < 0.01D)
		{
			valor = 0.0F;
		}
		
		String sValor = String.valueOf(valor).trim();
		int iDecimalPosition = sValor.indexOf(".", 0);
		String sEntero = sValor.substring(0, iDecimalPosition);
		String sDecimal = sValor.substring(iDecimalPosition + 1, sValor.length());
		if (sDecimal.length() > 2)
		{
			String sDigit = sDecimal.substring(2, 3);
			if (Integer.parseInt(sDigit) >= 5)
			{
				valor += Float.parseFloat("0.01");
				sValor = String.valueOf(valor).trim();
				iDecimalPosition = sValor.indexOf(".", 0);
				
				try
				{
					sDecimal = String.valueOf(valor).trim().substring(iDecimalPosition + 1, iDecimalPosition + 3);
				}
				catch (Exception ex)
				{
					sDecimal = String.valueOf(valor).trim().substring(iDecimalPosition + 1, iDecimalPosition + 2);
				}

				sEntero = sValor.substring(0, iDecimalPosition);
			}
			else
			{
				sDecimal = sDecimal.substring(0, 2);
			}
		}
		else if (Integer.parseInt(sDecimal) == 0) {
			sDecimal = "00";
		} else if (sDecimal.length() == 1) {
			sDecimal = sDecimal + "0";
		}
		int iResiduo = sEntero.length() % 3;
		int iNumTrios = sEntero.length() / 3;
		char[] array = sEntero.toCharArray();
		String s = "";

		for (int i = 0; i < array.length; i++)
		{
			s = s + String.valueOf(array[i]);
			if ((iResiduo > 0) && (i < iResiduo) && (iNumTrios > 0))
			{
				if (i == iResiduo - 1)
				{
					s = s;
				}
			}

			if (((i - iResiduo + 1) % 3 != 0) || (i >= array.length - 1) || (i - iResiduo + 1 <= 0))
				continue;
			s = s;
		}

		return s + "." + sDecimal;
	}
	

	public static String getFechaHoraActual(long fechamili)
	{
		Calendar c = Calendar.getInstance();
		Date date = new Date(fechamili);
		c.setTime(date);

		String fecha = 
				leftPad(String.valueOf(c.get(Calendar.DAY_OF_MONTH)), "0", 2) + "/" + 
						leftPad(String.valueOf(c.get(Calendar.MONTH) + 1), "0", 2) + "/" + 
						leftPad(String.valueOf(c.get(Calendar.YEAR)), "0", 2) + " " + 
						leftPad(String.valueOf(c.get(Calendar.HOUR_OF_DAY)), "0", 2) + ":" + 
						leftPad(String.valueOf(c.get(Calendar.MINUTE)), "0", 2) + ":" + 
						leftPad(String.valueOf(c.get(Calendar.SECOND)), "0", 2);

		return fecha;
	}

	public static int getDia(long fecha)
	{
		Calendar cal = Calendar.getInstance();
		Date date= new Date(fecha);
		cal.setTime(date);
		int diaMes = cal.get(Calendar.DAY_OF_MONTH);
		return diaMes;
	}

	public static int getMes(long fecha)
	{
		Calendar cal = Calendar.getInstance();
		Date date= new Date(fecha);
		cal.setTime(date);
		int mes    = cal.get(Calendar.MONTH);
		mes=mes+1;
		return mes;
	}

	public static int getAnio(long fecha)
	{
		Calendar cal = Calendar.getInstance();
		Date date = new Date(fecha);
		cal.setTime(date);
		int anno = cal.get(Calendar.YEAR);
		return anno;
	}

	public static int getDiaActual()
	{
		Calendar c = Calendar.getInstance();
		int diasist = c.get(Calendar.DAY_OF_MONTH);
		return diasist;
	}

	public static int getMesActual()
	{
		Calendar c = Calendar.getInstance();
		int messist = c.get(Calendar.MONTH);
		messist=messist+1;
		return messist;
	}

	public static int getAnioActual()
	{
		Calendar c = Calendar.getInstance();
		int annosist = c.get(Calendar.YEAR);
		return annosist;
	}

	public static int getHoraActual()
	{
		Calendar c = Calendar.getInstance();
		int horasist = c.get(Calendar.HOUR_OF_DAY);
		return horasist;
	}

	public static int getMinutoActual()
	{
		Calendar c = Calendar.getInstance();
		int minutosist = c.get(Calendar.MINUTE);
		return minutosist;
	}

	public static int getSegundoActual()
	{
		Calendar c = Calendar.getInstance();
		int segundosist = c.get(Calendar.SECOND);
		return segundosist;
	}

	public static int getMiliSegundoActual()
	{
		Calendar c = Calendar.getInstance();
		int milisegundosist = c.get(Calendar.MILLISECOND);
		c = null;
		return milisegundosist;
	}

	public static String getHora_HH_MM_SS()
	{
		Calendar c = Calendar.getInstance();
		String hora = 
				leftPad(String.valueOf(c.get(Calendar.HOUR_OF_DAY)), "0", 2) + ":" + 
						leftPad(String.valueOf(c.get(Calendar.MINUTE)), "0", 2) + ":" + 
						leftPad(String.valueOf(c.get(Calendar.SECOND)), "0", 2);
		return hora;
	}

	public static String getFechaActual_DD_MM_YYYY()
	{
		Calendar c = Calendar.getInstance();

		String fecha = 
				leftPad(String.valueOf(c.get(Calendar.DAY_OF_MONTH)), "0", 2) + "/" + 
						leftPad(String.valueOf(c.get(Calendar.MONTH) + 1), "0", 2) + "/" + 
						leftPad(String.valueOf(c.get(Calendar.YEAR)), "0", 2);
		return fecha;
	}

	//Formato 02/02/2014
	public static String getFechaActual_DD_MM_YYYY_Server()
	{
		Calendar c = Calendar.getInstance();

		String fecha = 
				leftPad(String.valueOf(c.get(Calendar.DAY_OF_MONTH)), "0", 2) + "/" + 
						leftPad(String.valueOf(c.get(Calendar.MONTH) + 1), "0", 2) + "/" + 
						leftPad(String.valueOf(c.get(Calendar.YEAR)), "0", 2);

		return fecha;
	}

	//Obtiene la fecha en forma de cadena
	public static String getFechaHoraActual()
	{
		Calendar c = Calendar.getInstance();

		String fecha = 
				leftPad(String.valueOf(c.get(Calendar.DAY_OF_MONTH)), "0", 2) + "/" + 
						leftPad(String.valueOf(c.get(Calendar.MONTH) + 1), "0", 2) + "/" + 
						leftPad(String.valueOf(c.get(Calendar.YEAR)), "0", 2) + " " + 
						leftPad(String.valueOf(c.get(Calendar.HOUR_OF_DAY)), "0", 2) + ":" + 
						leftPad(String.valueOf(c.get(Calendar.MINUTE)), "0", 2) + ":" + 
						leftPad(String.valueOf(c.get(Calendar.SECOND)), "0", 2);
		return fecha;
	}

	public static String getFechaHoraActualBajo()
	{
		Calendar c = Calendar.getInstance();

		String fecha = 
				leftPad(String.valueOf(c.get(Calendar.DAY_OF_MONTH)), "0", 2) + "_" + 
						leftPad(String.valueOf(c.get(Calendar.MONTH) + 1), "0", 2) + "_" + 
						leftPad(String.valueOf(c.get(Calendar.YEAR)), "0", 2) + "_" + 
						leftPad(String.valueOf(c.get(Calendar.HOUR_OF_DAY)), "0", 2) + "_" + 
						leftPad(String.valueOf(c.get(Calendar.MINUTE)), "0", 2) + "_" + 
						leftPad(String.valueOf(c.get(Calendar.SECOND)), "0", 2);
		return fecha;
	}

	//Obtiene la fecha actual en un objeto Date
	public static Date getFechaActual()
	{
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}

	/**
	 * hace el redondeo de una cantidad acortando los decimales encontrados en esta
	 * @param value cantidad a la cual se le van a recortar los decimales encontrados en esta
	 * @param numdecimals numero de decimales que debe contener el numero despues de aplicar el recorte de decimales
	 * @return la cantidad final que contiene los decimales especificados
	 */
	public static float Round(float value,int numdecimals)
	{
		String val = String.valueOf(value);
		int indice = val.indexOf(".");
		if(indice != -1)
		{
			String frac = val.substring(indice+1,val.length());
			val = val.substring(0,indice+1);
			if( frac.length() > numdecimals )
			{
				frac = frac.substring(0,numdecimals);
			}
			val += frac;
		}
		return Float.parseFloat(val);
	}

	public static double round(double d, int decimal) 
	{
		double powerOfTen = 1;

		while (decimal-- > 0)
		{
			powerOfTen *= 10.0;
		}

		double d1 = d * powerOfTen;
		int d1asint = (int)d1; // Clip the decimal portion away and cache the cast, this is a costly transformation.
		double d2 = d1 - d1asint; // Get the remainder of the double.

		// Is the remainder > 0.5? if so, round up, otherwise round down (lump in .5 with > case for simplicity).
		return ( d2 >= 0.5 ? (d1asint + 1)/powerOfTen : (d1asint)/powerOfTen);
	}

	/**
	 * encuentra si las cantidades que se van a comparar estan en un rango determinado al buscar los valores
	 * @param saldo la cantidad a la que se le va a comparar si cae dentro del rango
	 * @param pago indica si la cantidad mayor de
	 * @param rango el valor que indica que la cantidad cae en un rango aceptable en donde indica que esta dentro de los valores normales
	 * @return indica si se cumplio que el valor solicitado cae dentro de lo especificado
	 */
	public static boolean isInRange(float saldo, float pago, float rango)
	{
		float rangoSiguiente,rangoAnterior;
		rangoSiguiente = pago + rango;
		rangoAnterior  = pago - rango;
		if( rangoSiguiente >= saldo && saldo >= rangoAnterior )
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * obtiene el numero de decimales de un numero especificado
	 * @param value es el valor al cual se le extraen los numeros especificados
	 * @return el numero de decimales que contiene el numero especificado
	 */
	public static int getNumberDecimals(float value)
	{
		String val = String.valueOf(value);
		int indice = val.indexOf(".");
		if(indice != -1)
		{
			String frac = val.substring(indice+1,val.length());
			return frac.length();
		}
		return 0;
	}

	//REGRESA UN VALOR FLOAT EN FORMATO #,###,##0.00
	public static String toMoneyFormat(float valor)
	{
		String s, sValor, sDigit;
		String sEntero; //PARTE ENTERA
		String sDecimal; //PARTE DECIMAL
		int iDecimalPosition; //INDICE DEL PUNTO DECIMAL
		int iNumDigits, iNumTrios, iResiduo;
		//SI EL VALOR ES NEGATIVO
		if (valor < 0)
		{
			//SI EL VALOR TIENDE A CERO LO HACEMOS POSITIVO
			if ((valor * -1) < Float.parseFloat("0.009999"))
			{
				valor = valor * (-1);
			}
		}
		if (valor < 0.01)
		{
			valor = 0;
		}
		sValor = String.valueOf(valor).trim();
		iDecimalPosition = sValor.indexOf(".", 0);
		sEntero = sValor.substring(0, (iDecimalPosition ));
		sDecimal = sValor.substring(iDecimalPosition + 1, sValor.length());
		
		if (sDecimal.length() > 2)
		{
			sDigit = sDecimal.substring(2, 3);
			if (Integer.parseInt(sDigit) >= 5){
				valor = valor + Float.parseFloat("0.01");
				sValor = String.valueOf(valor).trim();
				iDecimalPosition = sValor.indexOf(".", 0);
				try
				{
					sDecimal = String.valueOf(valor).trim().substring(iDecimalPosition + 1, iDecimalPosition + 3);
				}
				catch(Exception ex)
				{
					sDecimal = String.valueOf(valor).trim().substring(iDecimalPosition + 1, iDecimalPosition + 2);
					//System.err.println( getClass().getName() + " : " + ex.toString() );
				}
				sEntero = sValor.substring(0, (iDecimalPosition ));
			}
			else
			{
				sDecimal = sDecimal.substring(0, 2);
			}
		}
		else if (Integer.parseInt(sDecimal) == 0)
			sDecimal = "00";
		else if (sDecimal.length() == 1)
			sDecimal = sDecimal + "0";
		
		//MANEJO PARTE ENTERA
		iResiduo = sEntero.length() % 3;
		iNumTrios = sEntero.length() / 3;
		char[] array = sEntero.toCharArray();
		s = "";

		for (int i = 0; i < array.length; i++)
		{
			s = s + String.valueOf(array[i]);
			if ((iResiduo > 0) && (i < iResiduo) && (iNumTrios > 0))
			{
				if (i == (iResiduo - 1))
				{
					s = s + ",";
				}
			}
			if (((( i - iResiduo + 1) % 3) == 0) && (i < (array.length - 1)) && (( i - iResiduo + 1) > 0))
			{
				s = s + ",";
			}
		}
		return (s + "." + sDecimal);
	}
	
	//REGRESA UN VALOR FLOAT EN FORMATO #,###,##0.000 con tres decimales
	public static String toKilosFormat(float valor)
	{
		String s, sValor, sDigit;
		String sEntero; //PARTE ENTERA
		String sDecimal; //PARTE DECIMAL
		int iDecimalPosition; //INDICE DEL PUNTO DECIMAL
		int iNumDigits, iNumTrios, iResiduo;
		//SI EL VALOR ES NEGATIVO
		if (valor < 0)
		{
			//SI EL VALOR TIENDE A CERO LO HACEMOS POSITIVO
			if ((valor * -1) < Float.parseFloat("0.0009999"))
			{
				valor = valor * (-1);
			}
		}
		
		if (valor < 0.001)
		{
			valor = 0;
		}
		
		sValor = String.valueOf(valor).trim();
		iDecimalPosition = sValor.indexOf(".", 0);
		sEntero = sValor.substring(0, (iDecimalPosition ));
		sDecimal = sValor.substring(iDecimalPosition + 1, sValor.length());
		
		if (sDecimal.length() > 3)
		{
			sDigit = sDecimal.substring(3, 4);
			
			if (Integer.parseInt(sDigit) >= 5)
			{
				valor = valor + Float.parseFloat("0.001");
				sValor = String.valueOf(valor).trim();
				iDecimalPosition = sValor.indexOf(".", 0);
				
				try
				{
					sDecimal = String.valueOf(valor).trim().substring(iDecimalPosition + 1, iDecimalPosition + 4);
				}
				catch(Exception ex)
				{
					sDecimal = String.valueOf(valor).trim().substring(iDecimalPosition + 1, iDecimalPosition + 3);
				}
				
				sEntero = sValor.substring(0, (iDecimalPosition ));
			}
			else
			{
				sDecimal = sDecimal.substring(0, 3);
			}
		}
		else if (Integer.parseInt(sDecimal) == 0)
			sDecimal = "000";
		else if (sDecimal.length() == 1)
			sDecimal = sDecimal + "00";
		else if (sDecimal.length() == 2)
			sDecimal = sDecimal + "0";
		
		//MANEJO PARTE ENTERA
		iResiduo = sEntero.length() % 3;
		iNumTrios = sEntero.length() / 3;
		char[] array = sEntero.toCharArray();
		s = "";

		for (int i = 0; i < array.length; i++)
		{
			s = s + String.valueOf(array[i]);
			if ((iResiduo > 0) && (i < iResiduo) && (iNumTrios > 0))
			{
				if (i == (iResiduo - 1))
				{
					s = s + ",";
				}
			}
			if (((( i - iResiduo + 1) % 3) == 0) && (i < (array.length - 1)) && (( i - iResiduo + 1) > 0))
			{
				s = s + ",";
			}
		}
		return (s + "." + sDecimal);
	}
	
	//ESTE PROCESO RECIBE UN ENTERO Y LO DEVUELVE EN UN FORMATO DE FOLIO
	//NUMERO DE DIGITOS VARIABLE
	public static String toFolioFormat(int valor, int NumDigits)
	{
		String s1;
		int iLong;
		s1 = String.valueOf(valor).trim();
		iLong = s1.length();
		if (iLong < NumDigits)
		{
			for (int i = iLong; i < NumDigits; i++)
			{
				s1 = "0" + s1;
			}
		}
		return s1;
	}

	//ESTE PROCESO RECIBE UNA CADENA Y LA RELLENA CON LA LETRA HASTA ALCANZAR
	//EL NUMERO DE DIGITOS
	public static String leftPad(String cadena, String letra, int NumDigits)
	{
		String s1;
		int iLong;
		s1 = String.valueOf(cadena).trim();
		iLong = s1.length();
		if (iLong < NumDigits)
		{
			for (int i = iLong; i < NumDigits; i++)
			{
				s1 = letra + s1;
			}
		}
		return s1;
	}

	//ESTE PROCESO RECIBE UNA CADENA Y LA RELLENA CON LA LETRA HASTA ALCANZAR
	//EL NUMERO DE DIGITOS
	public static String rigthPad(String cadena, String letra, int NumDigits)
	{
		String s1;
		int iLong;
		s1 = String.valueOf(cadena).trim();
		iLong = s1.length();
		if (iLong < NumDigits)
		{
			for (int i = iLong; i < NumDigits; i++)
			{
				s1 = s1 + letra;
			}
		}
		return s1;
	}

	//ESTE PROCESO LE DA FORMATO A LAS FECHAS
	//d/m/aaaa ejemplo 5/1/2006
	public static String FormatDate_d_m_aaaa(Date fecha)
	{
		String s;
		int day;
		int month;
		int year;

		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);

		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH) + 1;
		year = cal.get(Calendar.YEAR);

		s = String.valueOf(day).trim() + "/" + String.valueOf(month).trim() + "/" + String.valueOf(year).trim();

		return s;
	}

	//ESTE PROCESO LE DA FORMATO A LAS FECHAS
	//dd/mmm/aaaa ejemplo 5/ENE/2006
	public static String FormatDate_dd_mmm_aaaa(Date fecha)
	{
		String s, mes;
		int day;
		int month;
		int year;

		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);

		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH) + 1;
		year = cal.get(Calendar.YEAR);
		mes = formatMes(month);
		s = String.valueOf(day).trim() + "/" + mes + "/" + String.valueOf(year).trim();

		return s;
	}

	//ESTE PROCESO LE DA FORMATO A LAS FECHAS
	//aaaa-mm-dd ejemplo 2006-1-31
	//USO PARA LA BD DE MY SQL
	public static String FormatDate_aaaa_mm_dd(Date fecha)
	{
		String s;
		int day;
		int month;
		int year;

		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);

		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH) + 1;
		year = cal.get(Calendar.YEAR);
		s = String.valueOf(year).trim() + "-" + month + "-" + String.valueOf(day).trim();

		return s;
	}

	//ESTE PROCESO LE DA FORMATO A LAS FECHAS
	//AAAAMMDD ejemplo 20060131
	//USO PARA LA BD DE MY SQL
	public static String FormatDateAAAAMMDD(Date fecha)
	{
		String s, mes;
		String dia;
		int day;
		int month;
		int year;

		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);

		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH) + 1;
		year = cal.get(Calendar.YEAR);
		if (month < 10){
			mes = "0" + month;
		} else {
			mes = String.valueOf(month).trim();
		}

		if (day < 10){
			dia = "0" + day;
		} else {
			dia = String.valueOf(day).trim();
		}
		s = String.valueOf(year).trim() + mes + dia;

		return s;
	}

	//ESTE PROCESO LE DA FORMATO A LAS FECHAS
	//dd/mmm/aaaa ejemplo 5/ENE/2006
	//DATA UNA CADENA DE FECHA EN EL FORMATO d/m/aaaa EJEMLPO 5/1/2006
	public static String FormatDate_dd_mmm_aaaa(String fecha)
	{
		String s, mes;
		String day;
		int month;
		String year;
		int posSeparador1, posSeparador2;

		posSeparador1 = fecha.indexOf("/", 0);
		posSeparador2 = fecha.indexOf("/", posSeparador1 + 1);

		day = fecha.substring(0, posSeparador1);
		year = fecha.substring(posSeparador2 + 1);
		mes = fecha.substring(posSeparador1 + 1, posSeparador2);
		month = Integer.parseInt(mes);
		mes = formatMes(month);
		s = day + "/" + mes + "/" + year;
		return s;
	}

	//REGRESA LA ABREVIACION DEL MES
	private static String formatMes(int mes)
	{
		String s = "";
		if (mes == 1){
			s = "ENE";
		} else if (mes == 2) {
			s = "FEB";
		} else if (mes == 3) {
			s = "MAR";
		} else if (mes == 4) {
			s = "ABR";
		} else if (mes == 5) {
			s = "MAY";
		} else if (mes == 6) {
			s = "JUN";
		} else if (mes == 7) {
			s = "JUL";
		} else if (mes == 8) {
			s = "AGO";
		} else if (mes == 9) {
			s = "SEP";
		} else if (mes == 10) {
			s = "OCT";
		} else if (mes == 11) {
			s = "NOV";
		} else if (mes == 12) {
			s = "DIC";
		}
		return s;
	}

	//ESTE PROCESO CONVIERTE UNA CADENA EN FORMATO MONEDA A FLOAT
	public static String MoneyToFloat (String valor)
	{
		String s1 = "";
		String s = "";
		int numChar = valor.length();
		for(int i = 0; i < numChar; i++)
		{
			s1 = valor.substring(i, (i+1));
			if (!s1.equals(","))
			{
				s = s + s1;
			}
		}
		return s;
	}

	//CONVIERTE UNA CADENA EN UNA FECHA
	public static Date stringToDate(String fecha)
	{
		String s, mes;
		String dia;
		int month, day, year;
		String ano;
		Date fechaRes = new Date();
		int posSeparador1, posSeparador2;

		posSeparador1 = fecha.indexOf("/", 0);
		posSeparador2 = fecha.indexOf("/", posSeparador1 + 1);

		dia = fecha.substring(0, posSeparador1);
		ano = fecha.substring(posSeparador2 + 1);
		mes = fecha.substring(posSeparador1 + 1, posSeparador2);
		month = Integer.parseInt(mes) - 1;
		year =  Integer.parseInt(ano);
		day =  Integer.parseInt(dia);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.YEAR, year);
		fechaRes = cal.getTime();
		return fechaRes;
	}

	//ESTABLECE UNA MASCARA PARA NUMERO DE TARJETAS
	//CONVIERTE A X TODOS LOS DIGITOS DE UNA TARJETA EXCEPTO
	//LOS ULTIMOS 4 EJEMPLO
	//555544443333 REGRESA XXXXXXXXX3333
	public static String xMask(String cadena)
	{
		int num = 0;
		String s = "";
		String result = "";
		num = cadena.length();
		s = cadena.substring((num-4), (num));
		result = leftPad(s, "X", num);
		return result;
	}

	public static Date string2ToDate(String fecha)
	{
		String s, mes;
		String dia;
		int month, day, year;
		String ano;
		Date fechaRes = new Date();
		int posSeparador1, posSeparador2;

		posSeparador1 = fecha.indexOf("-", 0);
		posSeparador2 = fecha.indexOf("-", posSeparador1 + 1);

		dia = fecha.substring(0, posSeparador1);
		ano = fecha.substring(posSeparador2 + 1);
		mes = fecha.substring(posSeparador1 + 1, posSeparador2);
		month = Integer.parseInt(mes) - 1;
		year =  Integer.parseInt(ano);
		day =  Integer.parseInt(dia);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.YEAR, year);
		fechaRes = cal.getTime();
		return fechaRes;
	}

	public static String[] splitString(String text, char divider)
	{
		char[] array = text.toCharArray();
		int size = array.length;
		Vector vector = new Vector();
		String cadena = "";
		for(int i=0;i<size;i++)
		{
			if(array[i] == divider )
			{
				cadena = cadena.trim();
				if(!cadena.trim().equals(""))
					vector.addElement(cadena);
				cadena = "";
			}
			else
			{
				if(array[i]!= ' ')
					cadena += array[i];
			}
		}
		if(cadena.trim().length()>0)
		{
			vector.addElement(cadena);
		}
		int length = vector.size();
		String[] cadenas = new String[length];
		for(int i=0;i<length;i++)
		{
			cadenas[i] = (String)vector.elementAt(i);
		}
		return cadenas;
	}

	//ESTE PROCESO LE DA FORMATO A LAS FECHAS
	//dd/mmm/aaaa ejemplo 5/ENE/2006
	//DADA UNA CADENA DE FECHA EN EL FORMATO aaaa-m-d EJEMLPO 2006-2-12
	public static String FormatDate2_dd_mmm_aaaa(String fecha)
	{
		String s, mes;
		String day;
		int month;
		String year;
		int posSeparador1, posSeparador2;

		posSeparador1 = fecha.indexOf("-", 0);
		posSeparador2 = fecha.indexOf("-", posSeparador1 + 1);

		year = fecha.substring(0,posSeparador1);
		day = fecha.substring(posSeparador2 +1);
		mes = fecha.substring(posSeparador1 + 1,posSeparador2);

		month = Integer.parseInt(mes);
		mes = formatMes(month);
		s = day + "/" + mes + "/" + year;
		return s;
	}

	public static int Generate()
	{
		Random random = new Random();
		int randomNumber = Math.abs( random.nextInt() % 99999 );
		randomNumber = randomNumber + Utils.getDiaActual() + Utils.getMesActual() + Utils.getHoraActual() + Utils.getMinutoActual() + Utils.getMiliSegundoActual();
		return randomNumber;
	}

	public static String[] Split(String original, String separator)
	{
		Vector nodes = new Vector();
		//String separator = ":";
		int index = original.indexOf(separator);
		while(index >= 0)
		{
			nodes.addElement( original.substring(0, index) );
			original = original.substring(index+separator.length());
			index = original.indexOf(separator);
		}
		// Get the last node
		nodes.addElement( original );
		// Create splitted string array
		String[] result = new String[ nodes.size() ];
		if( nodes.size()>0 )
		{
			for(int loop=0; loop<nodes.size(); loop++)
			{
				result[loop] = (String)nodes.elementAt(loop);
			}
		}
		return result;
	}

	public static String getGIUD(String Tipo, Context context)
	{
		Calendar c = Calendar.getInstance();
		return Info.getPIN(context) + "-" + String.valueOf(c.get(5)) + "/" + String.valueOf(c.get(2) + 1) + 
				"/" + String.valueOf(c.get(1)) + " " + String.valueOf(c.get(11)) + 
				":" + String.valueOf(c.get(12)) + ":" + String.valueOf(c.get(13)) + 
				":" + String.valueOf(c.get(14)) + "-" + Info.getBateriaNivel(context) + 
				Info.getPIN(context) + "-" + String.valueOf(getRandom()) + 
				"-" + Tipo;
	}

	public static int getRandom()
	{
		Random random = new Random();
		int randomNumber = Math.abs(random.nextInt() % 99999);
		return randomNumber;
	}
	
	public static int getRandom(int rango)
	{
		Random random = new Random();
		int randomNumber = Math.abs(random.nextInt() % rango);
		return randomNumber;
	}

	public static String getFolioFecha()
	{
		Calendar c = Calendar.getInstance();
		String hora = String.valueOf(c.get(5)) + "/" + String.valueOf(c.get(2) + 1) + 
				"/" + String.valueOf(c.get(1)) + " " + String.valueOf(c.get(11)) + 
				":" + String.valueOf(c.get(12)) + ":" + String.valueOf(c.get(13));
		return hora;
	}

}
