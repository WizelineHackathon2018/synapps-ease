package com.System.WebServices;

import java.util.*;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.System.Utils.Logg;

public class wsManager
{
    //Variables de la clase
    public String Error;
    public String wsNamesPace;//Nombre del namespace pe: http://tempuri.org/
    public String wsUrl; //Url del asmx pe: http://intersad.no-ip.org/service/service.asmx
    public String WebMethod; //Nombre del web method pe: DarDatosEncabezado
    private Hashtable listaParametros = new Hashtable();//Lista de parametros
    //Variables de la clase

    //Constructor de la clase
    public wsManager()
    {
    }

    //hace un clear a la lista de prametros
    public void ClearParametros()
    {
        listaParametros.clear();
    }

    //agrega un parametro al web services
    public void AgregarParametro(String NombreParametro, String Valor)
    {
        listaParametros.put(NombreParametro, Valor);
    }

    //Manda a  llamar un WebServices con parametros y demas y regresa verdadero si se ejecuto bien y falso su fallo algo
    public boolean EjecutarWS()
    {
        Error = "";
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(wsNamesPace, WebMethod);

        //Aca se agregan los parametros al Web Services
        Enumeration enumeracion = listaParametros.keys();
        while(enumeracion.hasMoreElements())
        {
            String key = (String)(enumeracion.nextElement());
            String value = (String)listaParametros.get(key);
            soapObject.addProperty(key, value);
        }
        //Aca se agregan los parametros al Web Services

        soapEnvelope.setOutputSoapObject(soapObject);
        soapEnvelope.bodyOut = soapObject;
        soapEnvelope.dotNet = true;
        soapEnvelope.encodingStyle = SoapSerializationEnvelope.XSD;
        HttpTransportSE transport = new HttpTransportSE(wsUrl);
        try
        {
            //Aca se llama al WebService
            transport.debug = true;
            transport.call(wsNamesPace + WebMethod, soapEnvelope);
            this.Error = "";
            Logg.error("WS " + WebMethod);
        }
        catch (Exception e)
        {
            this.Error = WebMethod + " - " + e.getMessage() + " - " + Arrays.toString(e.getStackTrace());

            Logg.error("ErrorWS " + this.Error);
            Logg.error("transport.requestDump " + transport.requestDump);
            Logg.error("transport.responseDump " + transport.responseDump);
            
            soapEnvelope = null;
            transport = null;
            
            return false;
        }
        soapEnvelope = null;
        transport = null;
        return true;
    }
        
    //Aca se ejecuta un Web Services y regresa una coleccion de respuesta
    public SoapObject ConsultarWS()
    {
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(wsNamesPace, WebMethod);         
        //Aca se agregan los parametros al Web Services
        Enumeration enumeracion = listaParametros.keys();
        while(enumeracion.hasMoreElements())
        {
            String key = (String)(enumeracion.nextElement());
            String value = (String)listaParametros.get(key);
            soapObject.addProperty(key, value);
        }
        //Aca se agregan los parametros al Web Services
        soapEnvelope.setOutputSoapObject(soapObject);
        soapEnvelope.bodyOut = soapObject;
        soapEnvelope.dotNet = true;
        soapEnvelope.encodingStyle = SoapSerializationEnvelope.XSD;
        SoapObject responseObject;
        HttpTransportSE transport = new HttpTransportSE(wsUrl);
        
        try
        {
            //Aca se llama al WebService
            transport.debug = true;
            transport.call(wsNamesPace + WebMethod, soapEnvelope);
            responseObject = (SoapObject)soapEnvelope.getResponse();
            int size = responseObject.getPropertyCount();
            Logg.error("WS " + WebMethod);
            this.Error = "";
        }
        catch (Exception e)
        {
        	this.Error = WebMethod + " - " + e.getMessage() + " - " + Arrays.toString(e.getStackTrace());
            Logg.error("ErrorWS " + this.Error);
            Logg.error("transport.requestDump " + transport.requestDump);
            Logg.error("transport.responseDump " + transport.responseDump);

            responseObject = null;
        }
        soapEnvelope = null;
        transport = null;
        return responseObject;
    }
        
    public void Dispose()
    {
        listaParametros = null;
    }
}

