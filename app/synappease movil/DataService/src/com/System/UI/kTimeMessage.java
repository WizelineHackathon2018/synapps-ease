package com.System.UI;

import java.util.Calendar;

import com.System.Utils.Utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class kTimeMessage extends DialogFragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener 
{
	private String titulo;
	private int dia;
	private int mes;
	private int anio; 
	private int hora; 
	private int minuto; 
	private int MsgType; 
	private int id; 
	private kITimeMessage myListener;
	
	public static final int DLG_FECHA = 0;
	public static final int DLG_HORA = 1;
	public static final int DLG_MSG = 2;

	public kTimeMessage(String titulo, int dia, int mes, int anio, int hora, int minuto, int MsgType, int id, kITimeMessage m)
	{
		super();
		this.titulo = titulo;
		this.dia = dia;
		this.mes = mes;
		this.anio = anio;
		this.hora = hora;
		this.minuto = minuto;
		this.MsgType = MsgType;
		this.id = id;
		this.myListener = m;
	}
	
	public kTimeMessage(String titulo, int dia, int mes, int anio, int MsgType, int id, kITimeMessage m)
	{
		super();
		this.titulo = titulo;
		this.dia = dia;
		this.mes = mes;
		this.anio = anio;
		this.hora = hora;
		this.minuto = minuto;
		this.MsgType = MsgType;
		this.id = id;
		this.myListener = m;
	}
	
	public kTimeMessage(String titulo, int hora, int minuto, int MsgType, int id, kITimeMessage m)
	{
		super();
		this.titulo = titulo;
		this.dia = dia;
		this.mes = mes;
		this.anio = anio;
		this.hora = hora;
		this.minuto = minuto;
		this.MsgType = MsgType;
		this.id = id;
		this.myListener = m;
	}
	
	public kTimeMessage(String titulo, int MsgType, int id, kITimeMessage m)
	{
		super();
		this.titulo = titulo;
		
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		
		this.dia = day;
		this.mes = month;
		this.anio = year;
		this.hora = hour;
		this.minuto = minute;
		
		this.MsgType = MsgType;
		this.id = id;
		this.myListener = m;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		if(MsgType == DLG_FECHA)
		{
			DatePickerDialog dlg = new DatePickerDialog(getActivity(), this, anio, mes, dia);
			dlg.setTitle(this.titulo);
			dlg.setMessage("SELECCIONA LA FECHA");
			return dlg;
		}
		else
		{
			TimePickerDialog dlg = new TimePickerDialog(this.getActivity(), this, hora, minuto, DateFormat.is24HourFormat(this.getActivity()));
			dlg.setTitle(this.titulo);
			dlg.setMessage("SELECCIONA LA HORA");
			return dlg;
		}
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute)
	{
		this.myListener.getAnswer(id, Utils.leftPad(String.valueOf(hourOfDay), "0", 2)  +
				":" + Utils.leftPad(String.valueOf(minute), "0", 2), view);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
	{
		this.myListener.getAnswer(id, Utils.leftPad(String.valueOf(dayOfMonth), "0", 2) + "/" 
				+ Utils.leftPad(String.valueOf(monthOfYear), "0", 2) + "/" 
				+ Utils.leftPad(String.valueOf(year), "0", 2), view);
	}

}
