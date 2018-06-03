package com.comynt.launa.async;

import java.util.Arrays;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.comynt.launa.GPS;
import com.comynt.launa.Repositorio;

import BO.*;
import android.content.Context;
import com.System.Dispositivo.Info;
import com.System.Utils.Logg;
import com.System.Utils.Utils;

public class AsyncEnviarPosicionesCellId extends Thread
{
	private Repositorio repositorio;
	private BOCellID cellID;
	private BOGps gps;
	Context context;

	String strURLSent = "";

	public AsyncEnviarPosicionesCellId(Context context, Repositorio repositorio)
	{
		this.context = context;
		this.repositorio = repositorio;
		gps = new BOGps(context);
	}

	private void WriteData(OutputStream out, int cid, int lac)
			throws IOException
			{    
		DataOutputStream dataOutputStream = new DataOutputStream(out);
		dataOutputStream.writeShort(21);
		dataOutputStream.writeLong(0);
		dataOutputStream.writeUTF("en");
		dataOutputStream.writeUTF("Android");
		dataOutputStream.writeUTF("1.0");
		dataOutputStream.writeUTF("Web");
		dataOutputStream.writeByte(27);
		dataOutputStream.writeInt(0);
		dataOutputStream.writeInt(0);
		dataOutputStream.writeInt(3);
		dataOutputStream.writeUTF("");

		dataOutputStream.writeInt(cid);
		dataOutputStream.writeInt(lac);   

		dataOutputStream.writeInt(0);
		dataOutputStream.writeInt(0);
		dataOutputStream.writeInt(0);
		dataOutputStream.writeInt(0);
		dataOutputStream.flush();    
			}		

	public void run()
	{
		if( repositorio.lsCellID != null )
		{
			GPS.enviandoPuntos = true;
			GPS.enviandoPuntosCellId = true;
			try
			{
				GPS.enviandoPuntos = true;
				GPS.enviandoPuntosCellId = true;

				for(int x = 0; x < repositorio.lsCellID.size(); x++)
				{
					cellID = (BOCellID)repositorio.lsCellID.get(x);

					if(cellID.Intento == 0) //Es open cell
					{
						strURLSent =
								"http://www.opencellid.org/cell/get?key=de827a7a-d2f0-403d-b657-3be833164c78&mcc=" + 
										cellID.mcc + "&mnc=" + 
										cellID.mnc + "&cellid=" + 
										cellID.cellid +	"&lac=" + 
										cellID.lac + "&format=json";

						//Hacemos el request del punto y si obtenemos informacion guardamos el gps, si no intentamos con google
						try
						{
							HttpClient client = new DefaultHttpClient();
							HttpGet request = new HttpGet(strURLSent);

							HttpResponse response = client.execute(request);

							cellID.result = EntityUtils.toString(response.getEntity());
							Logg.info("result open cell: " + cellID.result);

							if(cellID.result.indexOf("\"error\": \"Cell not found\"") > -1)
							{
								//NO encontro en open cell, usar google
								cellID.Intento = 1;
								cellID.ClearProceso();
								cellID.ClearParametros();
								cellID.TipoProceso = "SQL";
								cellID.AgregarProceso("SQL.Actualizar");
								cellID.EjecutarProceso();
							}
							else
							{
								try
								{
									JSONObject reader = new JSONObject(cellID.result);

									//Guardamos el punto gps para que se envie por el AsynEnviarPosicion
									double latitude, longitude;

									latitude = Double.parseDouble(reader.getString("lat"));
									longitude = Double.parseDouble(reader.getString("lon"));

									if((longitude > 0 || longitude < 0) && (latitude > 0 || latitude < 0))
									{
										gps.ClearParametros();
										gps.ClearProceso();

										gps.Coordenadas = latitude + "," + longitude;
										gps.FechaHora = cellID.FechaHora;
										gps.Bateria = cellID.Bateria;
										gps.Velocidad = "0.0 Km/Hr";
										gps.Wifi = cellID.Wifi;
										gps.TipoAviso = "OpenCellID";

										gps.TipoProceso = "SQL";
										gps.AgregarProceso("SQL.Agregar");
										gps.EjecutarProceso();

										//Eliminamos el punto del cell id
										cellID.ClearProceso();
										cellID.ClearParametros();
										cellID.TipoProceso = "SQL";
										cellID.AgregarProceso("SQL.Eliminar");
										cellID.EjecutarProceso();

										Logg.info("GPS Opencell ID, Coordenadas:" + gps.Coordenadas + ", FechaHora:" + gps.FechaHora + 
												", Bateria:" + gps.Bateria + ", Velocidad:" + gps.Velocidad + 
												", Wifi:" + gps.Wifi + ", TipoAviso: " + gps.TipoAviso);
									}
								}
								catch(Exception ex)
								{
									Logg.error("Error: " + ex.getMessage() + " - " + Arrays.toString(ex.getStackTrace()));
								}
							}
						}
						catch(Exception ex)
						{
							Logg.error("Error: " + ex.getMessage() + " - " + Arrays.toString(ex.getStackTrace()));
						}
					}
					else //Es google, si falla de plano ya lo eliminamos, es un respaldo del respaldo, ultimo chance
					{
						try
						{
							String urlmmap = "http://www.google.com/glm/mmap";

							URL url = new URL(urlmmap);
							URLConnection conn = url.openConnection();
							HttpURLConnection httpConn = (HttpURLConnection) conn;
							httpConn.setRequestMethod("POST");
							httpConn.setDoOutput(true);
							httpConn.setDoInput(true);
							httpConn.connect();

							OutputStream outputStream = httpConn.getOutputStream();
							WriteData(outputStream, cellID.cellid, cellID.lac);

							InputStream inputStream = httpConn.getInputStream();
							DataInputStream dataInputStream = new DataInputStream(inputStream);

							dataInputStream.readShort();
							dataInputStream.readByte();
							int code = dataInputStream.readInt();
							if (code == 0) 
							{
								//									myLatitude = dataInputStream.readInt();
								//									myLongitude = dataInputStream.readInt();

								double latitude, longitude;

								latitude = dataInputStream.readInt();
								longitude = dataInputStream.readInt();

								if((longitude > 0 || longitude < 0) && (latitude > 0 || latitude < 0))
								{
									gps.ClearParametros();
									gps.ClearProceso();

									gps.Coordenadas = latitude + "," + longitude;
									gps.FechaHora = cellID.FechaHora;
									gps.Bateria = cellID.Bateria;
									gps.Velocidad = "0.0 Km/Hr";
									gps.Wifi = cellID.Wifi;
									gps.TipoAviso = "GoogleWebAPI";

									gps.TipoProceso = "SQL";
									gps.AgregarProceso("SQL.Agregar");
									gps.EjecutarProceso();

									//Eliminamos el punto del cell id
									cellID.ClearProceso();
									cellID.ClearParametros();
									cellID.TipoProceso = "SQL";
									cellID.AgregarProceso("SQL.Eliminar");
									cellID.EjecutarProceso();

									Logg.info("GPS Google API, Coordenadas:" + gps.Coordenadas + ", FechaHora:" + gps.FechaHora + 
											", Bateria:" + gps.Bateria + ", Velocidad:" + gps.Velocidad + 
											", Wifi:" + gps.Wifi + ", TipoAviso: " + gps.TipoAviso);
								}
							}
							else
							{
								//No se encuentra ni por google, borrarlo, posiblemente sea interesante subirlo a la parte web
								cellID.ClearProceso();
								cellID.ClearParametros();
								cellID.TipoProceso = "SQL";
								cellID.AgregarProceso("SQL.Eliminar");
								cellID.EjecutarProceso();
							}
						}
						catch(Exception ex)
						{
							Logg.error("Error: " + ex.getMessage() + " - " + Arrays.toString(ex.getStackTrace()));
						}
					}

					try {
						Thread.sleep(1000);
					}
					catch (Exception e){ }
				}
				GPS.enviandoPuntos = false;
				GPS.enviandoPuntosCellId = false;
			}
			catch (Exception e)
			{
				GPS.enviandoPuntos = false;
				GPS.enviandoPuntosCellId = false;
			}
		}
		else
		{
			GPS.enviandoPuntos = false;
			GPS.enviandoPuntosCellId = false;
		}
	}
}
