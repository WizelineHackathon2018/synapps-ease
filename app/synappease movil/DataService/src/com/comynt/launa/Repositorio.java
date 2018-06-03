package com.comynt.launa;

import BO.*;

import com.System.Utils.Logg;
import com.System.Utils.Utils;
import com.System.Dispositivo.Info;
import com.System.Dispositivo.api.DeviceInfo;
import com.comynt.launa.async.AsyncEnviarDatosPendientes;
import com.comynt.launa.async.AsyncEnviarPosiciones;
import com.comynt.launa.async.AsyncEnviarPosicionesCellId;
import com.comynt.launa.async.AsyncImprimir;
import com.comynt.launa.frm.IOUtil;

import android.content.Context;
import java.util.*;
import java.io.*;

import org.kobjects.base64.Base64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Repositorio
{
	private static final Repositorio INSTANCE = new Repositorio();
	
	public static final Repositorio getInstance()
	{
		return INSTANCE;
	}
	
	public static String WSNamespace = "http://tempuri.org/";
	
	
	//Desarrollo
	//public static String wsURL = "http://192.168.0.2/VM_LaUna/WSVentas.asmx";
	
	//QA
	public static String wsURL = "http://zinterprod.no-ip.org:8090/VM_LaUna_Test/WSVentas.asmx";
	
	//Producccion
	//public static String wsURL = "http://zinterprod.no-ip.org:8090/VM_LaUna/WSVentas.asmx";
	
	//ERROR LOCAL
	public static String wsURLError = "http://zinterprod.no-ip.org:8090/VM_DAMSA_TEST/WSVentas.asmx";
	
	
	public String mCurrentPhotoPath = "";
	public String mCurrentPhoto = "";
	
	public String versionName = "";
	public String IME = "";	
	
	public boolean estaSincronizando = false;
	public boolean estaSincronizandoAutomatico = false;
	
	public boolean esventa = false;
	public boolean estaautenticando = false;
	
	public void IniciaVariables(Context contextv)
	{
		context = contextv;
		
		if(context != null)
		{
			if(ConfiguracionUsabilidad == null)
			{
				ConfiguracionUsabilidad = new BOConfiguracionUsabilidad(this.context);
			}
			else
			{
				ConfiguracionUsabilidad.context = this.context;
			}
			
			if(Vendedor == null)
			{
				Vendedor = new BOVendedor(this.context);
			}
			else
			{
				Vendedor.context = this.context;
			}
			
			if(Configuracion == null)
			{
				Configuracion = new BOConfiguracion(this.context);
			}
			else
			{
				Configuracion.context = this.context;
			}
			
			if(NuevoCliente == null)
			{
				NuevoCliente = new BONuevoCliente(this.context);
			}
			else
			{
				NuevoCliente.context = this.context;
			}
			
			if(ClienteObtenido == null)
			{
				ClienteObtenido = new BOClienteObtenido(this.context);
			}
			else
			{
				ClienteObtenido.context = this.context;
			}
			
			if(AlmacenMovil == null)
			{
				AlmacenMovil = new BOAlmacenMovil(this.context);
			}
			else
			{
				AlmacenMovil.context = this.context;
			}
			
			if(Producto == null)
			{
				Producto = new BOProducto(this.context);
			}
			else
			{
				Producto.context = this.context;
			}
			
			if(ProductoListaPrecio == null)
			{
				ProductoListaPrecio = new BOProductoListaPrecio(this.context);
			}
			else
			{
				ProductoListaPrecio.context = this.context;
			}
			
			if(ListaPrecio == null)
			{
				ListaPrecio = new BOListaPrecio(this.context);
			}
			else
			{
				ListaPrecio.context = this.context;
			}
			
			if(Itinerario == null)
			{
				Itinerario = new BOItinerario(this.context);
			}
			else
			{
				Itinerario.context = this.context;
			}
			
			if(ParametrosVendedor == null)
			{
				ParametrosVendedor = new BOParametrosVendedor(this.context);
			}
			else
			{
				ParametrosVendedor.context = this.context;
			}
			
			if(Cliente == null)
			{
				Cliente = new BOCliente(this.context);
			}
			else
			{
				Cliente.context = this.context;
			}
			
			if(Venta == null)
			{
				Venta = new BONota(this.context);
			}
			else
			{
				Venta.context = this.context;
			}
			
			if(VentaDetalle == null)
			{
				VentaDetalle = new BONotaDetalle(this.context);
			}
			else
			{
				VentaDetalle.context = this.context;
			}
			
			if(Preregistro == null)
			{
				Preregistro = new BOPreregistro(this.context);
			}
			else
			{
				Preregistro.context = this.context;
			}
			
			if(Precuestionario == null)
			{
				Precuestionario = new BOPrecuestionario(this.context);
			}
			else
			{
				Precuestionario.context = this.context;
			}
			
			if(PrecuestionarioCliente == null)
			{
				PrecuestionarioCliente = new BOPrecuestionarioCliente(this.context);
			}
			else
			{
				PrecuestionarioCliente.context = this.context;
			}
			
			if(Categoria == null)
			{
				Categoria = new BOCategoria(this.context);
			}
			else
			{
				Categoria.context = this.context;
			}
			
			if(Ruta == null)
			{
				Ruta = new BORuta(this.context);
			}
			else
			{
				Ruta.context = this.context;
			}
			
			if(Licencia == null)
			{
				Licencia = new BOLicencia(this.context);
			}
			else
			{
				Licencia.context = this.context;
			}
			
			if(Dispositivo == null)
			{
				Dispositivo = new BODispositivo(this.context);
			}
			else
			{
				Dispositivo.context = this.context;
			}
			
			if(Bitacora == null)
			{
				Bitacora = new BOBitacora(this.context);
			}
			else
			{
				Bitacora.context = this.context;
			}
			
			if(CheckVehiculo == null)
			{
				CheckVehiculo = new BOCheckVehiculo(this.context);
			}
			else
			{
				CheckVehiculo.context = this.context;
			}
			
			if(Vehiculo == null)
			{
				Vehiculo = new BOVehiculo(this.context);
			}
			else
			{
				Vehiculo.context = this.context;
			}
			
			if(EstadoPrecios == null)
			{
				EstadoPrecios = new BOEstadoPrecios(this.context);
			}
			else
			{
				EstadoPrecios.context = this.context;
			}
			
			if(CheckVehiculoRealizado == null)
			{
				CheckVehiculoRealizado = new BOCheckVehiculoRealizado(this.context);
			}
			else
			{
				CheckVehiculoRealizado.context = this.context;
			}
			
			if(Cuestionario == null)
			{
				Cuestionario = new BOCuestionario(this.context);
			}
			else
			{
				Cuestionario.context = this.context;
			}
			
			if(CuestionarioCliente == null)
			{
				CuestionarioCliente = new BOCuestionarioCliente(this.context);
			}
			else
			{
				CuestionarioCliente.context = this.context;
			}
			
			if(PreguntaCuestionario == null)
			{
				PreguntaCuestionario = new BOPreguntaCuestionario(this.context);
			}
			else
			{
				PreguntaCuestionario.context = this.context;
			}
			
			if(InformacionCliente == null)
			{
				InformacionCliente = new BOInformacionCliente(this.context);
			}
			else
			{
				InformacionCliente.context = this.context;
			}
			
			if(SaldoCliente == null)
			{
				SaldoCliente = new BOSaldoCliente(this.context);
			}
			else
			{
				SaldoCliente.context = this.context;
			}
			
			if(SaldoClienteNota == null)
			{
				SaldoClienteNota = new BOSaldoClienteNota(this.context);
			}
			else
			{
				SaldoClienteNota.context = this.context;
			}
			
			if(HistoricoVentaDiaria == null)
			{
				HistoricoVentaDiaria = new BOHistoricoVentaDiaria(this.context);
			}
			else
			{
				HistoricoVentaDiaria.context = this.context;
			}
			
			if(Url == null)
			{
				Url = new BOUrl(this.context);
			}
			else
			{
				Url.context = this.context;
			}
			
			if(DevolucionEmbalaje == null)
			{
				DevolucionEmbalaje = new BODevolucionEmbalaje(this.context);
			}
			else
			{
				DevolucionEmbalaje.context = this.context;
			}
			
			if(PrecioCliente == null)
			{
				PrecioCliente = new BOPrecioCliente(this.context);
			}
			else
			{
				PrecioCliente.context = this.context;
			}
			
			if(PedidoNota == null)
			{
				PedidoNota = new BOPedido(this.context);
			}
			else
			{
				PedidoNota.context = this.context;
			}
			
			if(PedidoNotaDetalle == null)
			{
				PedidoNotaDetalle = new BOPedidoDetalle(this.context);
			}
			else
			{
				PedidoNotaDetalle.context = this.context;
			}
			
			if(ConfiguraImpresion == null)
			{
				ConfiguraImpresion = new BOConfiguraImpresion(this.context);
			}
			else
			{
				ConfiguraImpresion.context = this.context;
			}
			
			if(AlmacenCliente == null)
			{
				AlmacenCliente = new BOAlmacenCliente(this.context);
			}
			else
			{
				AlmacenCliente.context = this.context;
			}
			
			if(PrecioRango == null)
			{
				PrecioRango = new BOPrecioRango(this.context);
			}
			else
			{
				PrecioRango.context = this.context;
			}
			
			if(CellID == null)
			{
				CellID = new BOCellID(this.context);
			}
			else
			{
				CellID.context = this.context;
			}
			
			if(Impresora == null)
			{
				Impresora = new BOImpresora(this.context);
			}
			else
			{
				Impresora.context = this.context;
			}
			
			if(Error == null)
			{
				Error = new BOError(this.context);
			}
			else
			{
				Error.context = this.context;
			}
			
			if(Evento == null)
			{
				Evento = new BOEvento(this.context);
			}
			else
			{
				Evento.context = this.context;
			}
		}
	}
	
	// ************ Inicia Constantes ************
	
	public final int ID_DERECHA = 1;
	public final int ID_IZQUIERDA = 2;
	
	public String User = "";
	public String Password = "";
	
	public final int ID_WS = 1;
	public final int ID_SQL = 2;
	
	public final int ID_GuardaSQL = 3;
	public final int ID_GuardaMemoria = 4;
	
	public final int ID_ImprimirVentaContado = 0;
	public final int ID_ImprimirVentaCredito = 1;
	public final int ID_ImprimirAlmacen = 2;
	public final int ID_ImprimirLiquidacion = 3;
	
	public final int ID_ReImprimirVenta = 4;
	
	// ************ Fin Constantes ************
	
	// ************ Inicia Variables de instancia ************
	
	public int ActividadesDelDia = 0;
	
	public boolean isLoad = true;
	public boolean cobro = false;
	public boolean VerificaGPS = false;
	public boolean buscaGeoreferencia = false;
	public boolean isListenerFoto = false;
	
	public double longitude = 0.0D;
	public double latitude = 0.0D;
	public double altitud = 0.0D;
	
	public String FolioNota = "0";
	
	public String TITULO1 = "";
	public String DIRECCION1 = "";
	public String DIRECCION2 = "";
	public String TELEFONO = "";
	public String ENCUESTA = "";
	public String PAGARE1 = "";
	public String PAGARE2 = "";
	public String Tipo = "";
	
	public int Alto = 0;
	public int Ancho = 0;
	
	public String FechaCliente = "";
	
	public String HoraInicio = "";
	public String HoraFin = "";
	public double minVista = 0.0D;
	
	public String FechaActualDia2 = "";
	public String FechaActualDia = "";
	
	public boolean estaVendiendo = false;
	public boolean consignacion = false;
	public boolean entrada = false;
	
	public int IndiceCliente = -1;
	
	public boolean Conf_Cliente = false;
	public boolean Conf_Itinerario = false;
	public boolean Conf_AlmancenMovil = false;
	public boolean Conf_ListaPrecio = false;
	public boolean Conf_Credito = false;
	public boolean Conf_Cheque = false;
	
	public String FechaHoraUltimaOperacion = "";
	
	public float _CantidadInicial = 0.0F;
	public float _CantidadExistencia = 0.0F;
	public float _CantidadVenta = 0.0F;
	public float _CantidadDevuelta = 0.0F;
	public float _CantidadRepuesta = 0.0F;
	
	public float _CantidadTotal = 0.0F;
	
	public boolean cotizacion = false;
	public boolean TieneCredito = false;
	
	public double LimiteCredito = 0.0D;
	public double CreditoVencido = 0.0D;
	public double CreditoPorVencer = 0.0D;
	public double VentaContado = 0.0D;
	public double VentaCredito = 0.0D;
	public double Cobro = 0.0D;
	public double Pedido = 0.0D;
	
	// ************ Fin Variables de instancia ************
	
	
	//********************************************* Inicia Objetos de instancia *************************************
	
	public Context context = null;
	
	public Calendar objHoraInicio;
	public Calendar objHoraFin;
	
	public Vector productos = new Vector();
	public Vector embalaje = new Vector();
	public Vector fotosPath = new Vector();
	
	//	public ControlManager UImanager = new ControlManager();
	//	public frmGeoreferencia Georeferencia;
	//	public frmItinerario frmitinerario;
	
	private AsyncEnviarDatosPendientes oAsyncEnviarDatosPendientes;
	private Thread _threadoAsyncEnviarDatosPendientes;
	
	public AsyncImprimir oAsyncSincronizar;
	public Thread _threadAsyncSincronizar;
	
	public Timer myTimer = new Timer();
	public GPS gps = null;
	
	//	public CamaraFileListener listener = new CamaraFileListener(this);
	//	public ImageSender sender = new ImageSender(this);
	//	public MailSender mailSender = new MailSender(this);
	
	//********************************************* Fin Objetos de instancia *************************************
	
	//********************************************* Inicia BO Seccion *************************************
	
	public BOCellID CellID = null;
	public BOConfiguracionUsabilidad ConfiguracionUsabilidad = null;
	public BOVendedor Vendedor = null;
	public BOConfiguracion Configuracion = null;
	public BONuevoCliente NuevoCliente = null;
	public BOClienteObtenido ClienteObtenido = null;
	public BOAlmacenMovil AlmacenMovil = null;
	public BOProducto Producto = null;
	public BOProductoListaPrecio ProductoListaPrecio = null;
	public BOListaPrecio ListaPrecio = null;
	public BOItinerario Itinerario = null;
	public BOParametrosVendedor ParametrosVendedor = null;
	public BOCliente Cliente = null;
	public BONota Venta = null;
	public BONotaDetalle VentaDetalle = null;
	public BOPreregistro Preregistro = null;
	public BOPrecuestionario Precuestionario = null;
	public BOPrecuestionarioCliente PrecuestionarioCliente = null;
	public BOCategoria Categoria = null;
	public BORuta Ruta = null;
	public BOLicencia Licencia = null;
	public BODispositivo Dispositivo = null;
	public BOBitacora Bitacora = null;
	public BOCheckVehiculo CheckVehiculo = null;
	public BOVehiculo Vehiculo = null;
	
	public BOEstadoPrecios EstadoPrecios = null;
	public BOCheckVehiculoRealizado CheckVehiculoRealizado = null;
	public BOCuestionario Cuestionario = null;
	public BOCuestionarioCliente CuestionarioCliente = null;
	public BOPreguntaCuestionario PreguntaCuestionario = null;
	public BOInformacionCliente InformacionCliente = null;
	public BOSaldoCliente SaldoCliente = null;
	public BOSaldoClienteNota SaldoClienteNota = null;
	public BOHistoricoVentaDiaria HistoricoVentaDiaria = null;
	public BOUrl Url = null;
	public BODevolucionEmbalaje DevolucionEmbalaje = null;
	public BOPrecioCliente PrecioCliente = null;
	public BOPedido PedidoNota = null;
	public BOPedidoDetalle PedidoNotaDetalle = null;
	public BOConfiguraImpresion ConfiguraImpresion = null;
	public BOAlmacenCliente AlmacenCliente = null;
	public BOPrecioRango PrecioRango = null;
	public BOImpresora Impresora = null;
	
	public List<BO> lsImpresora = null;
	public List<BO> lsCellID = null;
	public List<BO> lsClienteObtenido = null;
	public List<BO> lsConfiguracionUsabilidad = null;
	public List<BO> lsVendedor = null;
	public List<BO> lsAlmacenMovil = null;
	public List<BO> lsAlmacenMovil2 = null;
	public List<BO> lsProducto = null;
	public List<BO> lsProductoPedido = null;
	public List<BO> lsProductoListaPrecio = null;
	public List<BO> lsListaPrecio = null;
	public List<BO> lsItinerario = null;
	public List<BO> lsParametrosVendedor = null;
	public List<BO> lsCliente = null;
	public List<BO> lsVenta = null;
	public List<BO> lsVenta2 = null;
	public List<BO> lsVentaDetalle = null;
	public List<BO> lsPreregistro = null;
	public List<BO> lsPrecuestionario = null;
	public List<BO> lsPrecuestionarioCliente = null;
	public List<BO> lsCategoria = null;
	public List<BO> lsRuta = null;
	public List<BO> lsLicencia = null;
	public List<BO> lsDispositivo = null;
	public List<BO> lsBitacora = null;
	public List<BO> lsCheckVehiculo = null;
	public List<BO> lsVehiculo = null;
	public List<BO> lsFolio = null;
	public List<BO> lsEstadoPrecios = null;
	public List<BO> lsCheckVehiculoRealizado = null;
	public List<BO> lsCuestionario = null;
	public List<BO> lsCuestionarioCliente = null;
	public List<BO> lsPreguntaCuestionario = null;
	public List<BO> lsInformacionCliente = null;
	public List<BO> lsSaldoCliente = null;
	public List<BO> lsSaldoClienteNota = null;
	public List<BO> lsHistoricoVentaDiaria = null;
	public List<BO> lsUrl = null;
	public List<BO> lsDevolucionEmbalaje = null;
	public List<BO> lsPrecioCliente = null;
	public List<BO> lsPedidoNota = null;
	public List<BO> lsPedidoNotaDetalle = null;
	public List<BO> lsConfiguraImpresion = null;
	public List<BO> lsAlmacenCliente = null;
	public List<BO> lsPrecioRango = null;
	
	public BOError Error = null;
	public List<BO> lsError = null;

	public BOEvento Evento = null;
	public List<BO> lsEvento = null;	
	
	//********************************************* Fin BO Seccion *************************************
	
	//********************************************* Inicia Metodos Seccion *************************************
	
	private void GuardarListaBO(List<BO> ls, BO bo)
	{
		bo.ClearProceso();
		bo.ClearParametros();
		if (ls != null)
		{
			for (int x = 0; x < ls.size(); x++)
			{
				bo = ls.get(x);
				if(bo != null)
				{
					bo.TipoProceso = "SQL";
					bo.AgregarProceso("SQL.Agregar");
					bo.EjecutarProceso();
				}
			}
		}
	}
	
	private void EliminarBO(BO bo)
	{
		bo.ClearProceso();
		bo.ClearParametros();
		bo.TipoProceso = "SQL";
		bo.AgregarProceso("SQL.EliminarTodos");
		bo.EjecutarProceso();
	}
	
	public void GuardarError()
	{
		this.Error.ClearProceso();
		this.Error.ClearParametros();
		
		this.Error.TipoProceso = "SQL";
		this.Error.AgregarProceso("SQL.Agregar");
		this.Error.EjecutarProceso();
	}
	
	public void EnviarError()
	{
		try
		{
			BOError Error = new BOError(context);

			Error.ClearProceso();
			Error.ClearParametros();
			Error.TipoProceso = "SQL";
			Error.AgregarProceso("SQL.Consultar");
			Error.AgregarParametro("idSortField", String.valueOf(Error.ID_FechaHora));
			Error.AgregarParametro("idFilterField", String.valueOf(Error.ID_FechaHora));
			Error.AgregarParametro("filtro", "");
			
			List<BO> lsError = Error.ConsultarProceso();

			if (lsError != null)
			{
				for (int w = 0; w < lsError.size(); w++)
				{
					Error = (BOError)lsError.get(w);

					Error.TipoProceso = "WS";
					Error.AgregarProceso("AgregarError");

					Error.AgregarParametro("wsNamesPace", WSNamespace);
					Error.AgregarParametro("wsUrl", wsURLError);

					Error.AgregarParametro("WebMethod", "AgregarError");

					Error.AgregarParametro("Detalle", Error.Detalle);
					Error.AgregarParametro("Os", Error.Os);
					Error.AgregarParametro("Sdk", String.valueOf(Error.Sdk));
					Error.AgregarParametro("Dev", Error.Dev);
					Error.AgregarParametro("Imei", Error.Imei);
					Error.AgregarParametro("Imsi", Error.Imsi);
					Error.AgregarParametro("Mac", Error.Mac);
					Error.AgregarParametro("Mail", Error.Mail);
					Error.AgregarParametro("Simser", Error.Simser);
					Error.AgregarParametro("Tipo", Error.Tipo);
					Error.AgregarParametro("FechaHora", Error.FechaHora);

					Error.EjecutarProceso();

						if(Error.Error.equals(""))
						{
							Error.ClearProceso();
							Error.ClearParametros();
							Error.TipoProceso = "SQL";
							Error.AgregarProceso("SQL.Eliminar");
							Error.EjecutarProceso();
						}
				}
			}
		}
		catch (Exception localException2)
		{
		}
	}
	
	public void CapturarError(Exception e, String Origen)
	{
		try
		{
			DeviceInfo dispositivo = new DeviceInfo(context);
			
			try
			{
				Error.Os = dispositivo.getOsVersion();
			}catch(Exception ex){}
			
			try
			{
				Error.Sdk = dispositivo.getSdkVersion();
			}catch(Exception ex){}
			
			try
			{
				Error.Dev = dispositivo.getDevice();
			}catch(Exception ex){}
			
			try
			{
				Error.Imei = dispositivo.getDeviceId();
			}catch(Exception ex){}
			
			try
			{
				Error.Imsi = dispositivo.getIMSINumber();
			}catch(Exception ex){}
			
			try
			{
				Error.Mac = dispositivo.getMACAddress();
			}catch(Exception ex){}
			
			try
			{
				Error.Simser = dispositivo.getSimSerialNumber();
			}catch(Exception ex){}
			
			Error.FechaHora = Utils.getFechaHoraActual();

			try
			{
				Error.Detalle = Arrays.toString(e.getStackTrace());
				Error.Tipo = "Movil.getStackTrace:" + Origen;
				GuardarError();
			}
			catch(Exception ex){}
			
			try
			{
				Error.Detalle = e.toString();
				Error.Tipo = "Movil.toString:" + Origen;
				GuardarError();
			}
			catch(Exception ex){}
			
			try
			{
				Error.Detalle = e.getMessage();
				Error.Tipo = "Movil.getMessage:" + Origen;
				GuardarError();
			}
			catch(Exception ex){}
		}
		catch(Exception ex2){}
		
		String Error = e.getMessage();
		Logg.error("Error: " + Error);
	}
	
	public void GuardarEvento(String EventoTexto, Context context)
	{
		try
		{
	    	Evento.GUIDE = Utils.getGIUD("E", context);
	    	Evento.FechaHora = Utils.getFechaHoraActual();
	    	Evento.PIN = Info.getPIN(context);
	    	Evento.Event = EventoTexto;
	    	Evento.GPS = GPSPoint.PosicionGPS;
	    	Evento.Bateria = String.valueOf(Info.getBateriaNivel(context));
	    	Evento.IdVendedor = Vendedor.IdVendedor;
	    	Evento.TipoDispositivo = "Android";
			
			Evento.Version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
			
			Evento.ClearProceso();
			Evento.ClearParametros();
			Evento.TipoProceso = "SQL";
			Evento.AgregarProceso("SQL.Agregar");
			Evento.EjecutarProceso();
		}
		catch(Exception ex){}
	}
	
	public void GuardarEvento(String EventoTexto)
	{
		try
		{
	    	Evento.GUIDE = Utils.getGIUD("E", context);
	    	Evento.FechaHora = Utils.getFechaHoraActual();
	    	Evento.PIN = Info.getPIN(context);
	    	Evento.Event = EventoTexto;
	    	Evento.GPS = GPSPoint.PosicionGPS;
	    	Evento.Bateria = String.valueOf(Info.getBateriaNivel(context));
	    	Evento.IdVendedor = Vendedor.IdVendedor;
	    	Evento.TipoDispositivo = "Android";
			
			Evento.Version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
			
			Evento.ClearProceso();
			Evento.ClearParametros();
			Evento.TipoProceso = "SQL";
			Evento.AgregarProceso("SQL.Agregar");
			Evento.EjecutarProceso();
		}
		catch(Exception ex){}
	}
	
	public void EnviarEvento()
	{
		try
		{
			BOEvento Evento = new BOEvento(context);

			Evento.ClearProceso();
			Evento.ClearParametros();
			Evento.TipoProceso = "SQL";
			Evento.AgregarProceso("SQL.Consultar");
			Evento.AgregarParametro("idSortField", String.valueOf(Evento.ID_FechaHora));
			Evento.AgregarParametro("idFilterField", String.valueOf(Evento.ID_FechaHora));
			Evento.AgregarParametro("filtro", "");
			List<BO> lsEvento = Evento.ConsultarProceso();

			if (lsEvento != null)
			{
				for (int w = 0; w < lsEvento.size(); w++)
				{
					Evento = (BOEvento)lsEvento.get(w);

					Evento.TipoProceso = "WS";
					Evento.AgregarProceso("AgregarEvento");

					Evento.AgregarParametro("wsNamesPace", WSNamespace);
					Evento.AgregarParametro("wsUrl", wsURLError);

					Evento.AgregarParametro("WebMethod", "AgregarEvento");

					Evento.AgregarParametro("GUIDE", Evento.GUIDE);
					Evento.AgregarParametro("FechaHora", Evento.FechaHora);
					Evento.AgregarParametro("PIN", Evento.PIN);
					Evento.AgregarParametro("Event", Evento.Event);
					Evento.AgregarParametro("GPS", Evento.GPS);
					Evento.AgregarParametro("Bateria", Evento.Bateria);
					Evento.AgregarParametro("IdVendedor", String.valueOf(Evento.IdVendedor));
					Evento.AgregarParametro("TipoDispositivo", Evento.TipoDispositivo);
					Evento.AgregarParametro("Version", Evento.Version);
					
					Evento.EjecutarProceso();

						if(Evento.Error.equals(""))
						{
							Evento.ClearProceso();
							Evento.ClearParametros();
							Evento.TipoProceso = "SQL";
							Evento.AgregarProceso("SQL.Eliminar");
							Evento.EjecutarProceso();
						}
				}
			}
		}
		catch (Exception localException2)
		{
		}
	}
	
	public void ConsultarAlmacenMovil2()
	{
		this.AlmacenMovil.ClearProceso();
		this.AlmacenMovil.ClearParametros();

		this.AlmacenMovil.TipoProceso = "SQL";
		this.AlmacenMovil.AgregarProceso("SQL.Consultar");

		this.AlmacenMovil.AgregarParametro("idSortField", String.valueOf(1));
		this.AlmacenMovil.AgregarParametro("idFilterField", String.valueOf(15));
		this.AlmacenMovil.AgregarParametro("filtro", "");

		this.lsAlmacenMovil2 = this.AlmacenMovil.ConsultarProceso();
	}
	
	
	public void EliminarImpresora()
	{
		this.Impresora.ClearProceso();
		this.Impresora.ClearParametros();
		this.Impresora.TipoProceso = "SQL";
		this.Impresora.AgregarProceso("SQL.EliminarTodos");
		this.Impresora.EjecutarProceso();
	}

	public void GuardarImpresora()
	{
		if (this.lsImpresora != null)
		{
			for (int x = 0; x < this.lsImpresora.size(); x++)
			{
				this.Impresora = ((BOImpresora)this.lsImpresora.get(x));
				this.Impresora.TipoProceso = "SQL";
				this.Impresora.AgregarProceso("SQL.Agregar");
				this.Impresora.EjecutarProceso();
			}
		}
	}

	public void ConsultarImpresora()
	{
		this.Impresora.ClearProceso();
		this.Impresora.ClearParametros();

		this.Impresora.TipoProceso = "SQL";
		this.Impresora.AgregarProceso("SQL.Consultar");
		this.Impresora.getClass(); this.Impresora.AgregarParametro("idSortField", String.valueOf(1));
		this.Impresora.getClass(); this.Impresora.AgregarParametro("idFilterField", String.valueOf(1));
		this.Impresora.AgregarParametro("filtro", "");
		this.lsImpresora = this.Impresora.ConsultarProceso();
	}

	public void ActualizarImpresora()
	{
		this.Impresora.ClearProceso();
		this.Impresora.ClearParametros();
		this.Impresora.TipoProceso = "SQL";
		this.Impresora.AgregarProceso("SQL.Actualizar");
		this.Impresora.EjecutarProceso();
	}

	public void ActualizarDatosImpresora()
	{
		if(this.Vendedor.IdVendedor > 0)
		{
			this.Impresora.ClearProceso();
			this.Impresora.ClearParametros();
			this.Impresora.TipoProceso = "SQL";
			this.Impresora.AgregarProceso("SQL.Consultar");
			this.Impresora.AgregarParametro("idSortField", String.valueOf(1));
			this.Impresora.AgregarParametro("idFilterField", String.valueOf(1));
			this.Impresora.AgregarParametro("filtro", "");
			
			this.lsImpresora = this.Impresora.ConsultarProceso();

			if(this.lsImpresora == null)
			{
				//Agrega
				Logg.info("Agrega datos de la impresora");
				
				this.Impresora.IDPrinter = this.Vendedor.IDImpresora;
				Logg.info("this.Vendedor.IDImpresora " + this.Vendedor.IDImpresora);
				
				this.Impresora.IzquierdaFolioCobro = this.Vendedor.IzquierdaFolioCobro;
				Logg.info("this.Vendedor.IzquierdaFolioCobro " + this.Vendedor.IzquierdaFolioCobro);
				
				this.Impresora.FolioCobro = this.Vendedor.FolioCobro;
				Logg.info("this.Vendedor.FolioCobro " + this.Vendedor.FolioCobro);
				
				this.Impresora.DerechaFolioCobro = this.Vendedor.DerechaFolioCobro;
				Logg.info("this.Vendedor.DerechaFolioCobro " + this.Vendedor.DerechaFolioCobro);
				
				this.Impresora.CopiasFolioCobro = this.Vendedor.CopiasFolioCobro;
				Logg.info("this.Vendedor.CopiasFolioCobro " + this.Vendedor.CopiasFolioCobro);
				
				this.Impresora.FechaFolioCobro = this.Vendedor.FechaFolioCobro;
				Logg.info("this.Vendedor.FechaFolioCobro " + this.Vendedor.FechaFolioCobro);
				
				this.Impresora.IzquierdaFolioVentaContado = this.Vendedor.IzquierdaFolioVentaContado;
				Logg.info("this.Vendedor.IzquierdaFolioVentaContado " + this.Vendedor.IzquierdaFolioVentaContado);
				
				this.Impresora.FolioVentaContado = this.Vendedor.FolioVentaContado;
				Logg.info("this.Vendedor.FolioVentaContado " + this.Vendedor.FolioVentaContado);
				
				this.Impresora.DerechaFolioVentaContado = this.Vendedor.DerechaFolioVentaContado;
				Logg.info("this.Vendedor.DerechaFolioVentaContado " + this.Vendedor.DerechaFolioVentaContado);
				
				this.Impresora.CopiasFolioVentaContado = this.Vendedor.CopiasFolioVentaContado;
				Logg.info("this.Vendedor.CopiasFolioVentaContado " + this.Vendedor.CopiasFolioVentaContado);
				
				this.Impresora.FechaFolioVentaContado = this.Vendedor.FechaFolioVentaContado;
				Logg.info("this.Vendedor.FechaFolioVentaContado " + this.Vendedor.FechaFolioVentaContado);
				
				this.Impresora.IzquierdaFolioVentaCredito = this.Vendedor.IzquierdaFolioVentaCredito;
				Logg.info("this.Vendedor.IzquierdaFolioVentaCredito " + this.Vendedor.IzquierdaFolioVentaCredito);
				
				this.Impresora.FolioVentaCredito = this.Vendedor.FolioVentaCredito;
				Logg.info("this.Vendedor.FolioVentaCredito " + this.Vendedor.FolioVentaCredito);
				
				this.Impresora.DerechaFolioVentaCredito = this.Vendedor.DerechaFolioVentaCredito;
				Logg.info("this.Vendedor.DerechaFolioVentaCredito " + this.Vendedor.DerechaFolioVentaCredito);
				
				this.Impresora.CopiasFolioVentaCredito = this.Vendedor.CopiasFolioVentaCredito;
				Logg.info("this.Vendedor.CopiasFolioVentaCredito " + this.Vendedor.CopiasFolioVentaCredito);
				
				this.Impresora.FechaFolioVentaCredito = this.Vendedor.FechaFolioVentaCredito;
				Logg.info("this.Vendedor.FechaFolioVentaCredito " + this.Vendedor.FechaFolioVentaCredito);
				
				this.Impresora.ClearProceso();
				this.Impresora.ClearParametros();
				
				this.Impresora.TipoProceso = "SQL";
				this.Impresora.AgregarProceso("SQL.Agregar");
				this.Impresora.EjecutarProceso();
			}
			else
			{
				if(this.lsImpresora.size() > 0)
				{
					//Actualizar
					this.Impresora = (BOImpresora)this.lsImpresora.get(0);
					
					if(!this.Impresora.FechaFolioCobro.equals(this.Vendedor.FechaFolioCobro))
					{
						Logg.info("Actualiza datos de la impresora");
						
						this.Impresora.IzquierdaFolioCobro = this.Vendedor.IzquierdaFolioCobro;
						Logg.info("this.Vendedor.IzquierdaFolioCobro " + this.Vendedor.IzquierdaFolioCobro);
						
						this.Impresora.FolioCobro = this.Vendedor.FolioCobro;
						Logg.info("this.Vendedor.FolioCobro " + this.Vendedor.FolioCobro);
						
						this.Impresora.DerechaFolioCobro = this.Vendedor.DerechaFolioCobro;
						Logg.info("this.Vendedor.DerechaFolioCobro " + this.Vendedor.DerechaFolioCobro);
						
						this.Impresora.CopiasFolioCobro = this.Vendedor.CopiasFolioCobro;
						Logg.info("this.Vendedor.CopiasFolioCobro " + this.Vendedor.CopiasFolioCobro);
						
						this.Impresora.FechaFolioCobro = this.Vendedor.FechaFolioCobro;
						Logg.info("this.Vendedor.FechaFolioCobro " + this.Vendedor.FechaFolioCobro);
						
						this.Impresora.IDPrinter = this.Vendedor.IDImpresora;
						Logg.info("this.Vendedor.FechaFolioCobro " + this.Vendedor.FechaFolioCobro);
					}
					
					if(!this.Impresora.FechaFolioVentaContado.equals(this.Vendedor.FechaFolioVentaContado))
					{
						Logg.info("Actualiza datos de la impresora");
						
						this.Impresora.IzquierdaFolioVentaContado = this.Vendedor.IzquierdaFolioVentaContado;
						Logg.info("this.Vendedor.IzquierdaFolioVentaContado " + this.Vendedor.IzquierdaFolioVentaContado);
						
						this.Impresora.FolioVentaContado = this.Vendedor.FolioVentaContado;
						Logg.info("this.Vendedor.FolioVentaContado " + this.Vendedor.FolioVentaContado);
						
						this.Impresora.DerechaFolioVentaContado = this.Vendedor.DerechaFolioVentaContado;
						Logg.info("this.Vendedor.DerechaFolioVentaContado " + this.Vendedor.DerechaFolioVentaContado);
						
						this.Impresora.CopiasFolioVentaContado = this.Vendedor.CopiasFolioVentaContado;
						Logg.info("this.Vendedor.CopiasFolioVentaContado " + this.Vendedor.CopiasFolioVentaContado);
						
						this.Impresora.FechaFolioVentaContado = this.Vendedor.FechaFolioVentaContado;
						Logg.info("this.Vendedor.FechaFolioVentaContado " + this.Vendedor.FechaFolioVentaContado);
						
						this.Impresora.IDPrinter = this.Vendedor.IDImpresora;
						Logg.info("this.Vendedor.IDImpresora " + this.Vendedor.IDImpresora);
					}
					
					if(!this.Impresora.FechaFolioVentaCredito.equals(this.Vendedor.FechaFolioVentaCredito))
					{
						Logg.info("Actualiza datos de la impresora");
						
						this.Impresora.IzquierdaFolioVentaCredito = this.Vendedor.IzquierdaFolioVentaCredito;
						Logg.info("this.Vendedor.IzquierdaFolioVentaCredito " + this.Vendedor.IzquierdaFolioVentaCredito);
						
						this.Impresora.FolioVentaCredito = this.Vendedor.FolioVentaCredito;
						Logg.info("this.Vendedor.FolioVentaCredito " + this.Vendedor.FolioVentaCredito);
						
						this.Impresora.DerechaFolioVentaCredito = this.Vendedor.DerechaFolioVentaCredito;
						Logg.info("this.Vendedor.DerechaFolioVentaCredito " + this.Vendedor.DerechaFolioVentaCredito);
						
						this.Impresora.CopiasFolioVentaCredito = this.Vendedor.CopiasFolioVentaCredito;
						Logg.info("this.Vendedor.CopiasFolioVentaCredito " + this.Vendedor.CopiasFolioVentaCredito);
						
						this.Impresora.FechaFolioVentaCredito = this.Vendedor.FechaFolioVentaCredito;
						Logg.info("this.Vendedor.FechaFolioVentaCredito " + this.Vendedor.FechaFolioVentaCredito);
						
						this.Impresora.IDPrinter = this.Vendedor.IDImpresora;
						Logg.info("this.Vendedor.IDImpresora " + this.Vendedor.IDImpresora);
					}
					
					Logg.info("OK Carga de datos de la impresora");
					
					this.Impresora.ClearProceso();
					this.Impresora.ClearParametros();
					
					this.Impresora.TipoProceso = "SQL";
					this.Impresora.AgregarProceso("SQL.Actualizar");
					this.Impresora.EjecutarProceso();
				}
				else
				{
					//Agrega
					this.Impresora.IDPrinter = this.Vendedor.IDImpresora;

					this.Impresora.IzquierdaFolioCobro = this.Vendedor.IzquierdaFolioCobro;
					this.Impresora.FolioCobro = this.Vendedor.FolioCobro;
					this.Impresora.DerechaFolioCobro = this.Vendedor.DerechaFolioCobro;
					this.Impresora.CopiasFolioCobro = this.Vendedor.CopiasFolioCobro;
					this.Impresora.FechaFolioCobro = this.Vendedor.FechaFolioCobro;

					this.Impresora.IzquierdaFolioVentaContado = this.Vendedor.IzquierdaFolioVentaContado;
					this.Impresora.FolioVentaContado = this.Vendedor.FolioVentaContado;
					this.Impresora.DerechaFolioVentaContado = this.Vendedor.DerechaFolioVentaContado;
					this.Impresora.CopiasFolioVentaContado = this.Vendedor.CopiasFolioVentaContado;
					this.Impresora.FechaFolioVentaContado = this.Vendedor.FechaFolioVentaContado;

					this.Impresora.IzquierdaFolioVentaCredito = this.Vendedor.IzquierdaFolioVentaCredito;
					this.Impresora.FolioVentaCredito = this.Vendedor.FolioVentaCredito;
					this.Impresora.DerechaFolioVentaCredito = this.Vendedor.DerechaFolioVentaCredito;
					this.Impresora.CopiasFolioVentaCredito = this.Vendedor.CopiasFolioVentaCredito;
					this.Impresora.FechaFolioVentaCredito = this.Vendedor.FechaFolioVentaCredito;

					this.Impresora.ClearProceso();
					this.Impresora.ClearParametros();

					this.Impresora.TipoProceso = "SQL";
					this.Impresora.AgregarProceso("SQL.Agregar");
					this.Impresora.EjecutarProceso();
				}
			}
		}
	}

	public void EliminarConfiguracionUsabilidad()
	{
		EliminarBO(this.ConfiguracionUsabilidad);
	}

	public void GuardarCellID()
	{
		this.CellID.ClearProceso();
		this.CellID.ClearParametros();
		this.CellID.TipoProceso = "SQL";
		this.CellID.AgregarProceso("SQL.Agregar");
		this.CellID.EjecutarProceso();
	}

	public void ConsultarConfiguracionUsabilidad()
	{
		this.ConfiguracionUsabilidad.TipoProceso = "SQL";
		this.ConfiguracionUsabilidad.AgregarProceso("SQL.Consultar");

		this.ConfiguracionUsabilidad.getClass(); this.ConfiguracionUsabilidad.AgregarParametro("idSortField", String.valueOf(3));
		this.ConfiguracionUsabilidad.getClass(); this.ConfiguracionUsabilidad.AgregarParametro("idFilterField", String.valueOf(3));
		this.ConfiguracionUsabilidad.AgregarParametro("filtro", "");

		this.lsConfiguracionUsabilidad = this.ConfiguracionUsabilidad.ConsultarProceso();
	}

	public void GuardarConfiguracionUsabilidad()
	{
		ConfiguracionUsabilidad.ClearProceso();
		ConfiguracionUsabilidad.ClearParametros();
		ConfiguracionUsabilidad.TipoProceso = "SQL";
		ConfiguracionUsabilidad.AgregarProceso("SQL.Agregar");
		ConfiguracionUsabilidad.EjecutarProceso();
	}





	public void EliminarPrecioRango()
	{
		this.PrecioRango.ClearProceso();
		this.PrecioRango.ClearParametros();
		this.PrecioRango.TipoProceso = "SQL";
		this.PrecioRango.AgregarProceso("SQL.EliminarTodos");
		this.PrecioRango.EjecutarProceso();
	}

	public void GuardarPrecioRango()
	{
		if (this.lsPrecioRango != null)
		{
			for (int x = 0; x < this.lsPrecioRango.size(); x++)
			{
				this.PrecioRango = ((BOPrecioRango)this.lsPrecioRango.get(x));
				this.PrecioRango.TipoProceso = "SQL";
				this.PrecioRango.AgregarProceso("SQL.Agregar");
				this.PrecioRango.EjecutarProceso();
			}
		}
	}

	public void ConsultarPrecioRango(int TipoProceso, int Persistir, String PageNumber, String PageSize)
	{
		this.PrecioRango.ClearProceso();
		this.PrecioRango.ClearParametros();

		if (TipoProceso == 1)
		{
			this.PrecioRango.TipoProceso = "WS";
			this.PrecioRango.AgregarProceso("ConsultarPrecioRango");
			this.PrecioRango.AgregarParametro("wsNamesPace", WSNamespace);
			this.PrecioRango.AgregarParametro("wsUrl", wsURL);
			this.PrecioRango.AgregarParametro("WebMethod", "ConsultarPrecioRango");

			this.PrecioRango.AgregarParametro("IdEmpresa", String.valueOf(this.Vendedor.IdEmpresa));
			this.PrecioRango.AgregarParametro("PageNumber", PageNumber);
			this.PrecioRango.AgregarParametro("PageSize", PageSize);

			this.PrecioRango.AgregarParametro("wsUrl", wsURL);
			this.lsPrecioRango = this.PrecioRango.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsPrecioRango != null)
				{
					for (int x = 0; x < this.lsPrecioRango.size(); x++)
					{
						this.PrecioRango = ((BOPrecioRango)this.lsPrecioRango.get(x));
						this.PrecioRango.TipoProceso = "SQL";
						this.PrecioRango.AgregarProceso("SQL.Agregar");
						this.PrecioRango.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.PrecioRango.TipoProceso = "SQL";
			this.PrecioRango.AgregarProceso("SQL.Consultar");

			this.PrecioRango.AgregarParametro("idSortField", String.valueOf(PrecioRango.ID_IdProducto));
			this.PrecioRango.AgregarParametro("idFilterField", String.valueOf(PrecioRango.ID_IdProducto));
			this.PrecioRango.AgregarParametro("filtro", "");

			this.lsPrecioRango = this.PrecioRango.ConsultarProceso();
		}
	}


	public void EliminarAlmacenCliente()
	{
		this.AlmacenCliente.ClearProceso();
		this.AlmacenCliente.ClearParametros();
		this.AlmacenCliente.TipoProceso = "SQL";
		this.AlmacenCliente.AgregarProceso("SQL.EliminarTodos");
		this.AlmacenCliente.EjecutarProceso();
	}

	public void GuardarAlmacenCliente()
	{
		if (this.lsAlmacenCliente != null)
		{
			for (int x = 0; x < this.lsAlmacenCliente.size(); x++)
			{
				this.AlmacenCliente = ((BOAlmacenCliente)this.lsAlmacenCliente.get(x));
				this.AlmacenCliente.TipoProceso = "SQL";
				this.AlmacenCliente.AgregarProceso("SQL.Agregar");
				this.AlmacenCliente.EjecutarProceso();
			}
		}
	}

	public void ConsultarAlmacenCliente(int TipoProceso, int Persistir, String IdVendedor, String PageNumber, 
			String PageSize )
	{
		this.AlmacenCliente.ClearProceso();
		this.AlmacenCliente.ClearParametros();

		if (TipoProceso == 1)
		{
			this.AlmacenCliente.TipoProceso = "WS";
			this.AlmacenCliente.AgregarProceso("ConsultarAlmacenCliente");
			this.AlmacenCliente.AgregarParametro("wsNamesPace", WSNamespace);
			this.AlmacenCliente.AgregarParametro("wsUrl", wsURL);
			this.AlmacenCliente.AgregarParametro("WebMethod", "ConsultarAlmacenCliente");

			this.AlmacenCliente.AgregarParametro("IdVendedor", IdVendedor);
			this.AlmacenCliente.AgregarParametro("PageNumber", PageNumber);
			this.AlmacenCliente.AgregarParametro("PageSize", PageSize);

			this.AlmacenCliente.AgregarParametro("wsUrl", wsURL);
			this.lsAlmacenCliente = this.AlmacenCliente.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsAlmacenCliente != null)
				{
					for (int x = 0; x < this.lsAlmacenCliente.size(); x++)
					{
						this.AlmacenCliente = ((BOAlmacenCliente)this.lsAlmacenCliente.get(x));
						this.AlmacenCliente.TipoProceso = "SQL";
						this.AlmacenCliente.AgregarProceso("SQL.Agregar");
						this.AlmacenCliente.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.AlmacenCliente.TipoProceso = "SQL";
			this.AlmacenCliente.AgregarProceso("SQL.Consultar");

			this.AlmacenCliente.AgregarParametro("idSortField", String.valueOf(AlmacenCliente.ID_Producto));
			this.AlmacenCliente.AgregarParametro("idFilterField", String.valueOf(AlmacenCliente.ID_IdCliente));
			this.AlmacenCliente.AgregarParametro("filtro", String.valueOf(this.Itinerario.IdCliente));

			this.lsAlmacenCliente = this.AlmacenCliente.ConsultarProceso();
		}
	}


	public void EliminarPedidoNota()
	{
		this.PedidoNota.ClearProceso();
		this.PedidoNota.ClearParametros();
		this.PedidoNota.TipoProceso = "SQL";
		this.PedidoNota.AgregarProceso("SQL.EliminarTodos");
		this.PedidoNota.EjecutarProceso();
	}

	public void GuardarPedidoNota()
	{
		if (this.lsPedidoNota != null)
		{
			for (int x = 0; x < this.lsPedidoNota.size(); x++)
			{
				this.PedidoNota = ((BOPedido)this.lsPedidoNota.get(x));
				this.PedidoNota.TipoProceso = "SQL";
				this.PedidoNota.AgregarProceso("SQL.Agregar");
				this.PedidoNota.EjecutarProceso();
			}
		}
	}

	public void ConsultarPedidoNota(int TipoProceso, int Persistir)
	{
		this.PedidoNota.ClearProceso();
		this.PedidoNota.ClearParametros();

		if (TipoProceso == 1)
		{
			this.PedidoNota.TipoProceso = "WS";
			this.PedidoNota.AgregarProceso("ConsultarPedido");
			this.PedidoNota.AgregarParametro("wsNamesPace", WSNamespace);
			this.PedidoNota.AgregarParametro("wsUrl", wsURL);
			this.PedidoNota.AgregarParametro("WebMethod", "ConsultarPedido");

			this.PedidoNota.AgregarParametro("IdVendedor", String.valueOf(this.Vendedor.IdVendedor));

			this.PedidoNota.AgregarParametro("wsUrl", wsURL);
			this.lsPedidoNota = this.PedidoNota.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsPedidoNota != null)
				{
					for (int x = 0; x < this.lsPedidoNota.size(); x++)
					{
						this.PedidoNota = ((BOPedido)this.lsPedidoNota.get(x));
						this.PedidoNota.TipoProceso = "SQL";
						this.PedidoNota.AgregarProceso("SQL.Agregar");
						this.PedidoNota.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.PedidoNota.TipoProceso = "SQL";
			this.PedidoNota.AgregarProceso("SQL.Consultar");

			this.PedidoNota.getClass(); this.PedidoNota.AgregarParametro("idSortField", String.valueOf(PedidoNota.ID_Nombre));
			this.PedidoNota.getClass(); this.PedidoNota.AgregarParametro("idFilterField", String.valueOf(PedidoNota.ID_Realizada));
			this.PedidoNota.AgregarParametro("filtro", "0");

			this.lsPedidoNota = this.PedidoNota.ConsultarProceso();
		}
	}

	// ***********************************************************************************************************

	public void EliminarPedidoNotaDetalle()
	{
		this.PedidoNotaDetalle.ClearProceso();
		this.PedidoNotaDetalle.ClearParametros();
		this.PedidoNotaDetalle.TipoProceso = "SQL";
		this.PedidoNotaDetalle.AgregarProceso("SQL.EliminarTodos");
		this.PedidoNotaDetalle.EjecutarProceso();
	}

	public void GuardarPedidoNotaDetalle()
	{
		if (this.lsPedidoNotaDetalle != null)
		{
			for (int x = 0; x < this.lsPedidoNotaDetalle.size(); x++)
			{
				this.PedidoNotaDetalle = ((BOPedidoDetalle)this.lsPedidoNotaDetalle.get(x));
				this.PedidoNotaDetalle.TipoProceso = "SQL";
				this.PedidoNotaDetalle.AgregarProceso("SQL.Agregar");
				this.PedidoNotaDetalle.EjecutarProceso();
			}
		}
	}

	public void ConsultarPedidoNotaDetalle(int TipoProceso, int Persistir)
	{
		this.PedidoNotaDetalle.ClearProceso();
		this.PedidoNotaDetalle.ClearParametros();

		if (TipoProceso == 1)
		{
			this.PedidoNotaDetalle.TipoProceso = "WS";
			this.PedidoNotaDetalle.AgregarProceso("ConsultarPedidoDetalle");
			this.PedidoNotaDetalle.AgregarParametro("wsNamesPace", WSNamespace);
			this.PedidoNotaDetalle.AgregarParametro("wsUrl", wsURL);
			this.PedidoNotaDetalle.AgregarParametro("WebMethod", "ConsultarPedidoDetalle");

			this.PedidoNotaDetalle.AgregarParametro("IdVendedor", String.valueOf(this.Vendedor.IdVendedor));

			this.PedidoNotaDetalle.AgregarParametro("wsUrl", wsURL);
			this.lsPedidoNotaDetalle = this.PedidoNotaDetalle.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsPedidoNotaDetalle != null)
				{
					for (int x = 0; x < this.lsPedidoNotaDetalle.size(); x++)
					{
						this.PedidoNotaDetalle = ((BOPedidoDetalle)this.lsPedidoNotaDetalle.get(x));
						this.PedidoNotaDetalle.TipoProceso = "SQL";
						this.PedidoNotaDetalle.AgregarProceso("SQL.Agregar");
						this.PedidoNotaDetalle.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.PedidoNotaDetalle.TipoProceso = "SQL";
			this.PedidoNotaDetalle.AgregarProceso("SQL.Consultar");
			this.PedidoNotaDetalle.getClass(); this.PedidoNotaDetalle.AgregarParametro("idSortField", String.valueOf(PedidoNotaDetalle.ID_IdVenta));
			this.PedidoNotaDetalle.getClass(); this.PedidoNotaDetalle.AgregarParametro("idFilterField", String.valueOf(PedidoNotaDetalle.ID_IdVenta));
			this.PedidoNotaDetalle.AgregarParametro("filtro", "");
			this.lsPedidoNotaDetalle = this.PedidoNotaDetalle.ConsultarProceso();
		}
	}

	// ***********************************************************************************************************

	public void EliminarConfiguraImpresion()
	{
		this.ConfiguraImpresion.ClearProceso();
		this.ConfiguraImpresion.ClearParametros();
		this.ConfiguraImpresion.TipoProceso = "SQL";
		this.ConfiguraImpresion.AgregarProceso("SQL.EliminarTodos");
		this.ConfiguraImpresion.EjecutarProceso();
	}

	public void GuardarConfiguraImpresion()
	{
		if (this.lsConfiguraImpresion != null)
		{
			for (int x = 0; x < this.lsConfiguraImpresion.size(); x++)
			{
				this.ConfiguraImpresion = ((BOConfiguraImpresion)this.lsConfiguraImpresion.get(x));
				this.ConfiguraImpresion.TipoProceso = "SQL";
				this.ConfiguraImpresion.AgregarProceso("SQL.Agregar");
				this.ConfiguraImpresion.EjecutarProceso();
			}
		}
	}

	public void ConsultarConfiguraImpresion(int TipoProceso, int Persistir)
	{
		this.ConfiguraImpresion.ClearProceso();
		this.ConfiguraImpresion.ClearParametros();

		if (TipoProceso == 1)
		{
			this.ConfiguraImpresion.TipoProceso = "WS";
			this.ConfiguraImpresion.AgregarProceso("ConsultarConfiguraImprecion");
			this.ConfiguraImpresion.AgregarParametro("wsNamesPace", WSNamespace);
			this.ConfiguraImpresion.AgregarParametro("wsUrl", wsURL);
			this.ConfiguraImpresion.AgregarParametro("WebMethod", "ConsultarConfiguraImprecion");

			this.ConfiguraImpresion.AgregarParametro("IdEmpresa", String.valueOf(this.Vendedor.IdEmpresa));

			this.ConfiguraImpresion.AgregarParametro("wsUrl", wsURL);
			this.lsConfiguraImpresion = this.ConfiguraImpresion.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsConfiguraImpresion != null)
				{
					for (int x = 0; x < this.lsConfiguraImpresion.size(); x++)
					{
						this.ConfiguraImpresion = ((BOConfiguraImpresion)this.lsConfiguraImpresion.get(x));
						this.ConfiguraImpresion.TipoProceso = "SQL";
						this.ConfiguraImpresion.AgregarProceso("SQL.Agregar");
						this.ConfiguraImpresion.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.ConfiguraImpresion.TipoProceso = "SQL";
			this.ConfiguraImpresion.AgregarProceso("SQL.Consultar");
			this.ConfiguraImpresion.getClass(); this.ConfiguraImpresion.AgregarParametro("idSortField", String.valueOf(1));
			this.ConfiguraImpresion.getClass(); this.ConfiguraImpresion.AgregarParametro("idFilterField", String.valueOf(1));
			this.ConfiguraImpresion.AgregarParametro("filtro", "");
			this.lsConfiguraImpresion = this.ConfiguraImpresion.ConsultarProceso();
		}
	}

	// ************************************************************************************

	public void EstableceParametrosImpresion()
	{
		this.ConfiguraImpresion.TipoProceso = "SQL";
		this.ConfiguraImpresion.AgregarProceso("SQL.Consultar");
		this.ConfiguraImpresion.getClass(); this.ConfiguraImpresion.AgregarParametro("idSortField", String.valueOf(1));
		this.ConfiguraImpresion.getClass(); this.ConfiguraImpresion.AgregarParametro("idFilterField", String.valueOf(1));
		this.ConfiguraImpresion.AgregarParametro("filtro", "");
		this.lsConfiguraImpresion = this.ConfiguraImpresion.ConsultarProceso();
		if( lsConfiguraImpresion != null )
		{
			for(int x = 0; x < lsConfiguraImpresion.size(); x++)
			{
				ConfiguraImpresion = (BOConfiguraImpresion)lsConfiguraImpresion.get(x);

				if(ConfiguraImpresion.Tipo == 1)
				{
					TITULO1 = ConfiguraImpresion.Texto;
				}
				else if(ConfiguraImpresion.Tipo == 2)
				{
					DIRECCION1 = ConfiguraImpresion.Texto;
				}
				else if(ConfiguraImpresion.Tipo == 3)
				{
					DIRECCION2 = ConfiguraImpresion.Texto;
				}
				else if(ConfiguraImpresion.Tipo == 4)
				{
					TELEFONO = ConfiguraImpresion.Texto;
				}
				else if(ConfiguraImpresion.Tipo == 5)
				{
					ENCUESTA = ConfiguraImpresion.Texto;
				}
				else if(ConfiguraImpresion.Tipo == 6)
				{
					PAGARE1 = ConfiguraImpresion.Texto;
				}
				else if(ConfiguraImpresion.Tipo == 7)
				{
					PAGARE2 = ConfiguraImpresion.Texto;
				}
			}
		}
	}


	public void EliminarPrecioCliente()
	{
		this.PrecioCliente.ClearProceso();
		this.PrecioCliente.ClearParametros();
		this.PrecioCliente.TipoProceso = "SQL";
		this.PrecioCliente.AgregarProceso("SQL.EliminarTodos");
		this.PrecioCliente.EjecutarProceso();
	}

	public void GuardarPrecioCliente()
	{
		if (this.lsPrecioCliente != null)
		{
			for (int x = 0; x < this.lsPrecioCliente.size(); x++)
			{
				this.PrecioCliente = ((BOPrecioCliente)this.lsPrecioCliente.get(x));

				Logg.info("c: " + this.PrecioCliente.IdCliente + " prod: " + this.PrecioCliente.IdProducto + " pc: " + this.PrecioCliente.Precio);

				this.PrecioCliente.TipoProceso = "SQL";
				this.PrecioCliente.AgregarProceso("SQL.Agregar");
				this.PrecioCliente.EjecutarProceso();
			}
		}
	}

	public void ConsultarPrecioCliente(int TipoProceso, int Persistir)
	{
		this.PrecioCliente.ClearProceso();
		this.PrecioCliente.ClearParametros();

		if (TipoProceso == 1)
		{
			this.PrecioCliente.TipoProceso = "WS";
			this.PrecioCliente.AgregarProceso("ConsultarProductoPrecioCliente");
			this.PrecioCliente.AgregarParametro("wsNamesPace", WSNamespace);
			this.PrecioCliente.AgregarParametro("wsUrl", wsURL);
			this.PrecioCliente.AgregarParametro("WebMethod", "ConsultarProductoPrecioCliente");

			this.PrecioCliente.AgregarParametro("IdVendedor", String.valueOf(this.Vendedor.IdVendedor));

			this.PrecioCliente.AgregarParametro("wsUrl", wsURL);
			this.lsPrecioCliente = this.PrecioCliente.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsPrecioCliente != null)
				{
					for (int x = 0; x < this.lsPrecioCliente.size(); x++)
					{
						this.PrecioCliente = ((BOPrecioCliente)this.lsPrecioCliente.get(x));
						this.PrecioCliente.TipoProceso = "SQL";
						this.PrecioCliente.AgregarProceso("SQL.Agregar");
						this.PrecioCliente.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.PrecioCliente.TipoProceso = "SQL";
			this.PrecioCliente.AgregarProceso("SQL.Consultar");
			this.PrecioCliente.getClass(); this.PrecioCliente.AgregarParametro("idSortField", String.valueOf(1));
			this.PrecioCliente.getClass(); this.PrecioCliente.AgregarParametro("idFilterField", String.valueOf(1));
			this.PrecioCliente.AgregarParametro("filtro", "");
			this.lsPrecioCliente = this.PrecioCliente.ConsultarProceso();
		}
	}

	public void AgregarUrl()
	{
		this.Url.ClearProceso();
		this.Url.ClearParametros();
		this.Url.TipoProceso = "SQL";
		this.Url.AgregarProceso("SQL.Agregar");
		this.Url.EjecutarProceso();
	}

	public void ActualizarUrl()
	{
		this.Url.ClearProceso();
		this.Url.ClearParametros();
		this.Url.TipoProceso = "SQL";
		this.Url.AgregarProceso("SQL.Actualizar");
		this.Url.EjecutarProceso();
	}

	public void EliminarUrl()
	{
		this.Url.ClearProceso();
		this.Url.ClearParametros();
		this.Url.TipoProceso = "SQL";
		this.Url.AgregarProceso("SQL.EliminarTodos");
		this.Url.EjecutarProceso();
	}

	public void ConsultarUrl()
	{
		this.Url.TipoProceso = "SQL";
		this.Url.AgregarProceso("SQL.Consultar");
		this.Url.getClass(); this.Url.AgregarParametro("idSortField", String.valueOf(1));
		this.Url.getClass(); this.Url.AgregarParametro("idFilterField", String.valueOf(1));
		this.Url.AgregarParametro("filtro", "");
		this.lsUrl = this.Url.ConsultarProceso();
	}

	public void VerificaCreditoSaldoTodo()
	{
		this.VentaContado = 0.0D;
		this.VentaCredito = 0.0D;
		this.Cobro = 0.0D;
		this.Pedido = 0.0D;
		this.TieneCredito = false;

		ConsultarSaldoCliente(2, 4);
		this.SaldoCliente.ClearProceso();
		this.SaldoCliente.ClearParametros();
		this.SaldoCliente.TipoProceso = "SQL";
		this.SaldoCliente.AgregarProceso("SQL.Consultar");
		this.SaldoCliente.getClass(); this.SaldoCliente.AgregarParametro("idSortField", String.valueOf(1));
		this.SaldoCliente.getClass(); this.SaldoCliente.AgregarParametro("idFilterField", String.valueOf(1));
		this.SaldoCliente.AgregarParametro("filtro", "");
		this.lsSaldoCliente = this.SaldoCliente.ConsultarProceso();

		if (this.lsSaldoCliente != null)
		{
			this.TieneCredito = true;

			this.SaldoCliente = ((BOSaldoCliente)this.lsSaldoCliente.get(0));
			this.LimiteCredito = Double.parseDouble(this.SaldoCliente.LimiteCredito);
			this.CreditoVencido = Double.parseDouble(this.SaldoCliente.Vencido);
			this.CreditoPorVencer = Double.parseDouble(this.SaldoCliente.PorVencer);
		}

		this.Venta.ClearProceso();
		this.Venta.ClearParametros();
		this.Venta.TipoProceso = "SQL";
		this.Venta.AgregarProceso("SQL.Consultar");
		this.Venta.getClass(); this.Venta.AgregarParametro("idSortField", String.valueOf(30));
		this.Venta.getClass(); this.Venta.AgregarParametro("idFilterField", String.valueOf(42));
		this.Venta.AgregarParametro("filtro", Utils.getFechaActual_DD_MM_YYYY());
		this.lsVenta = this.Venta.ConsultarProceso();

		if (this.lsVenta != null)
		{
			for (int x = 0; x < this.lsVenta.size(); x++)
			{
				this.Venta = ((BONota)this.lsVenta.get(x));

				if ((this.Venta.Fecha.equals(Utils.getFechaActual_DD_MM_YYYY())) && 
						(this.Venta.Discriminante.equals("Venta")) && 
						(this.Venta.DiscriminantePago.equals("EFECTIVO")))
				{
					this.VentaContado += this.Venta.Total;
				}
				else if ((this.Venta.Fecha.equals(Utils.getFechaActual_DD_MM_YYYY())) && 
						(this.Venta.Discriminante.equals("Venta")) && 
						(this.Venta.DiscriminantePago.equals("CREDITO")))
				{
					this.VentaCredito += this.Venta.Total;
				}
				else if ((this.Venta.Fecha.equals(Utils.getFechaActual_DD_MM_YYYY())) && 
						(this.Venta.Discriminante.equals("Cobro")))
				{
					this.Cobro += this.Venta.Total;
				} else {
					if ((!this.Venta.Fecha.equals(Utils.getFechaActual_DD_MM_YYYY())) || 
							(!this.Venta.Discriminante.equals("Pedido")))
						continue;
					this.Pedido += this.Venta.Total;
				}
			}
		}
	}

	public void VerificaCreditoSaldo()
	{
		this.VentaContado = 0.0D;
		this.VentaCredito = 0.0D;
		this.Cobro = 0.0D;
		this.Pedido = 0.0D;
		this.TieneCredito = false;

		ConsultarSaldoCliente(2, 4);

		this.SaldoCliente.ClearProceso();
		this.SaldoCliente.ClearParametros();
		this.SaldoCliente.TipoProceso = "SQL";
		this.SaldoCliente.AgregarProceso("SQL.Consultar");
		this.SaldoCliente.getClass(); this.SaldoCliente.AgregarParametro("idSortField", String.valueOf(1));
		this.SaldoCliente.getClass(); this.SaldoCliente.AgregarParametro("idFilterField", String.valueOf(1));
		this.SaldoCliente.AgregarParametro("filtro", String.valueOf(this.Itinerario.IdCliente));
		this.lsSaldoCliente = this.SaldoCliente.ConsultarProceso();

		if (this.lsSaldoCliente != null)
		{
			this.TieneCredito = true;

			this.SaldoCliente = ((BOSaldoCliente)this.lsSaldoCliente.get(0));
			this.LimiteCredito = Double.parseDouble(this.SaldoCliente.LimiteCredito);
			this.CreditoVencido = Double.parseDouble(this.SaldoCliente.Vencido);
			this.CreditoPorVencer = Double.parseDouble(this.SaldoCliente.PorVencer);
		}
		else
		{
			this.SaldoCliente.LimiteCredito = "0";
			this.SaldoCliente.Vencido = "0";
			this.SaldoCliente.PorVencer = "0";
		}

		this.Venta.ClearProceso();
		this.Venta.ClearParametros();
		this.Venta.TipoProceso = "SQL";
		this.Venta.AgregarProceso("SQL.Consultar");
		this.Venta.getClass(); this.Venta.AgregarParametro("idSortField", String.valueOf(30));
		this.Venta.getClass(); this.Venta.AgregarParametro("idFilterField", String.valueOf(23));
		this.Venta.AgregarParametro("filtro", String.valueOf(this.Itinerario.IdCliente));
		this.lsVenta = this.Venta.ConsultarProceso();

		if (this.lsVenta != null)
		{
			for (int x = 0; x < this.lsVenta.size(); x++)
			{
				this.Venta = ((BONota)this.lsVenta.get(x));

				if ((this.Venta.Fecha.equals(Utils.getFechaActual_DD_MM_YYYY())) && 
						(this.Venta.Discriminante.equals("Venta")) && 
						(this.Venta.DiscriminantePago.equals("EFECTIVO")))
				{
					this.VentaContado += this.Venta.Total;
				}
				else if ((this.Venta.Fecha.equals(Utils.getFechaActual_DD_MM_YYYY())) && 
						(this.Venta.Discriminante.equals("Venta")) && 
						(this.Venta.DiscriminantePago.equals("CREDITO")))
				{
					this.VentaCredito += this.Venta.Total;
				}
				else if ((this.Venta.Fecha.equals(Utils.getFechaActual_DD_MM_YYYY())) && 
						(this.Venta.Discriminante.equals("Cobro")))
				{
					this.Cobro += this.Venta.Total;
				} else {
					if ((!this.Venta.Fecha.equals(Utils.getFechaActual_DD_MM_YYYY())) || 
							(!this.Venta.Discriminante.equals("Pedido")))
						continue;
					this.Pedido += this.Venta.Total;
				}
			}
		}
	}

	private String getRepositorioEstado()
	{
		String listas = "";

		return listas;
	}

	public void ObtenerHoraInicio()
	{
		this.HoraInicio = Utils.getHora_HH_MM_SS();
		this.objHoraInicio = Calendar.getInstance();
	}

	public void ObtenerHoraFin()
	{
		this.HoraFin = Utils.getHora_HH_MM_SS();
		this.objHoraFin = Calendar.getInstance();
	}

	public void ObtenerDiferenciaHora()
	{
	}

	public void EnviarDatos()
	{
		if (this.oAsyncEnviarDatosPendientes == null)
		{
			Logg.info("Iniciando proceso se envio");
			this.oAsyncEnviarDatosPendientes = new AsyncEnviarDatosPendientes(context, this);
			this._threadoAsyncEnviarDatosPendientes = new Thread(this.oAsyncEnviarDatosPendientes);
			this._threadoAsyncEnviarDatosPendientes.start();
		}
		else
		{
			Logg.info("El proceso de envio ya existe");

			if(!_threadoAsyncEnviarDatosPendientes.isAlive())
			{
				Logg.info("El proceso de envio esta detenido, iniciando el proceso");

				this._threadoAsyncEnviarDatosPendientes.start();
			}
			else
			{
				Logg.info("El proceso de envio esta corriendo");
			}
		}
	}

	private static AsyncEnviarPosiciones oAsyncEnviarPosiciones;
	private static Thread _asyncEnviarPosiciones;

	private static AsyncEnviarPosicionesCellId oAsyncEnviarPosicionesCellId;
	private static Thread _asyncEnviarPosicionesCellId;

	public void EnviarDatosGPSCellID()
	{
		try
		{
			if (this.oAsyncEnviarPosicionesCellId == null)
			{
				//consultamos los puntos gps a ver si hay, si no hay manda el ultimo
				Logg.info("Iniciando proceso se envio");
				oAsyncEnviarPosicionesCellId = new AsyncEnviarPosicionesCellId(context, this);
				_asyncEnviarPosicionesCellId = new Thread(oAsyncEnviarPosicionesCellId);
				_asyncEnviarPosicionesCellId.start();
			}
			else
			{
				Logg.info("El proceso de envio GPS ID ya existe");

				if(!_asyncEnviarPosicionesCellId.isAlive())
				{
					Logg.info("El proceso de envio GPS ID esta detenido, iniciando el proceso");

					this._asyncEnviarPosicionesCellId.start();
				}
				else
				{
					Logg.info("El proceso de envio esta corriendo");
				}
			}
		}
		catch(Exception ex)
		{
			Logg.info("Error al enviar el GPS ID");

			try
			{
				oAsyncEnviarPosicionesCellId = new AsyncEnviarPosicionesCellId(context, this);
				_asyncEnviarPosicionesCellId = new Thread(oAsyncEnviarPosicionesCellId);
				_asyncEnviarPosicionesCellId.start();
			}
			catch(Exception ex2)
			{
				Logg.info("Error al enviar el GPS ID 2");
			}
		}
	}

	public void EnviarDatosGPS()
	{
		try
		{
			if (this.oAsyncEnviarPosiciones == null)
			{
				Logg.info("Iniciando proceso se envio");
				oAsyncEnviarPosiciones = new AsyncEnviarPosiciones(context);
				_asyncEnviarPosiciones = new Thread(oAsyncEnviarPosiciones);
				_asyncEnviarPosiciones.start();
			}
			else
			{
				Logg.info("El proceso de envio GPS ya existe");

				if(!_asyncEnviarPosiciones.isAlive())
				{
					Logg.info("El proceso de envio GPS esta detenido, iniciando el proceso");

					this._asyncEnviarPosiciones.start();
				}
				else
				{
					Logg.info("El proceso de envio esta corriendo");
				}
			}
		}
		catch(Exception ex)
		{
			Logg.info("Error al enviar el GPS");

			try
			{
				oAsyncEnviarPosiciones = new AsyncEnviarPosiciones(context);
				_asyncEnviarPosiciones = new Thread(oAsyncEnviarPosiciones);
				_asyncEnviarPosiciones.start();
			}
			catch(Exception ex2)
			{
				Logg.info("Error al enviar el GPS 2");
			}
		}
	}

	public void EnviarDatosAhora()
	{
		if (this.oAsyncEnviarDatosPendientes == null)
		{
			Logg.info("Iniciando proceso se envio");
			this.oAsyncEnviarDatosPendientes = new AsyncEnviarDatosPendientes(context, this);
			this._threadoAsyncEnviarDatosPendientes = new Thread(this.oAsyncEnviarDatosPendientes);
			this._threadoAsyncEnviarDatosPendientes.start();

			oAsyncEnviarDatosPendientes.contador = 121;
			oAsyncEnviarDatosPendientes.contadorLogger = 121;
		}
		else
		{
			Logg.info("El proceso de envio ya existe");

			if(!_threadoAsyncEnviarDatosPendientes.isAlive())
			{
				Logg.info("El proceso de envio esta detenido, iniciando el proceso");

				oAsyncEnviarDatosPendientes.contador = 121;
				oAsyncEnviarDatosPendientes.contadorLogger = 121;

				this._threadoAsyncEnviarDatosPendientes.start();
			}
			else
			{
				Logg.info("El proceso de envio esta corriendo");

				oAsyncEnviarDatosPendientes.contador = 121;
				oAsyncEnviarDatosPendientes.contadorLogger = 121;
			}
		}
	}

	public void EliminarLicencia()
	{
		this.Licencia.ClearProceso();
		this.Licencia.ClearParametros();
		this.Licencia.TipoProceso = "SQL";
		this.Licencia.AgregarProceso("SQL.EliminarTodos");
		this.Licencia.EjecutarProceso();
	}

	public void ConsultarLog()
	{
		this.Dispositivo.ClearProceso();
		this.Dispositivo.ClearParametros();
		this.Dispositivo.TipoProceso = "SQL";
		this.Dispositivo.AgregarProceso("SQL.Consultar");
		this.Dispositivo.getClass(); this.Dispositivo.AgregarParametro("idSortField", String.valueOf(1));
		this.Dispositivo.getClass(); this.Dispositivo.AgregarParametro("idFilterField", String.valueOf(1));
		this.Dispositivo.AgregarParametro("filtro", "");
		this.lsDispositivo = this.Dispositivo.ConsultarProceso();
	}

	public void ConsultarEstadoPreciosSQL()
	{
		this.EstadoPrecios.ClearProceso();
		this.EstadoPrecios.ClearParametros();
		this.EstadoPrecios.TipoProceso = "SQL";
		this.EstadoPrecios.AgregarProceso("SQL.Consultar");
		this.EstadoPrecios.getClass(); this.EstadoPrecios.AgregarParametro("idSortField", String.valueOf(2));
		this.EstadoPrecios.getClass(); this.EstadoPrecios.AgregarParametro("idFilterField", String.valueOf(2));
		this.EstadoPrecios.AgregarParametro("filtro", "");
		this.lsEstadoPrecios = this.EstadoPrecios.ConsultarProceso();
	}

	public void AgregarEstadoPrecios()
	{
		this.EstadoPrecios.ClearProceso();
		this.EstadoPrecios.ClearParametros();

		this.EstadoPrecios.TipoProceso = "SQL";
		this.EstadoPrecios.AgregarProceso("SQL.EliminarTodos");
		this.EstadoPrecios.EjecutarProceso();

		this.EstadoPrecios.ClearProceso();
		this.EstadoPrecios.ClearParametros();

		this.EstadoPrecios.TipoProceso = "SQL";
		this.EstadoPrecios.AgregarProceso("SQL.Agregar");
		this.EstadoPrecios.EjecutarProceso();
	}

	//	public void AgregarFolio()
	//	{
	//		this.Folio.ClearProceso();
	//		this.Folio.ClearParametros();
	//		this.Folio.TipoProceso = "SQL";
	//		this.Folio.AgregarProceso("SQL.Agregar");
	//		this.Folio.EjecutarProceso();
	//	}
	//
	//	public void ActualizarFolio()
	//	{
	//		this.Folio.ClearProceso();
	//		this.Folio.ClearParametros();
	//		this.Folio.TipoProceso = "SQL";
	//		this.Folio.AgregarProceso("SQL.Actualizar");
	//		this.Folio.EjecutarProceso();
	//	}
	//
	//	public void EliminarFolio()
	//	{
	//		this.Folio.ClearProceso();
	//		this.Folio.ClearParametros();
	//		this.Folio.TipoProceso = "SQL";
	//		this.Folio.AgregarProceso("SQL.EliminarTodos");
	//		this.Folio.EjecutarProceso();
	//	}
	//
	//	public void ConsultarFolio()
	//	{
	//		this.Folio.TipoProceso = "SQL";
	//		this.Folio.AgregarProceso("SQL.Consultar");
	//		this.Folio.getClass(); this.Folio.AgregarParametro("idSortField", String.valueOf(1));
	//		this.Folio.getClass(); this.Folio.AgregarParametro("idFilterField", String.valueOf(1));
	//		this.Folio.AgregarParametro("filtro", "");
	//		this.lsFolio = this.Folio.ConsultarProceso();
	//	}

	public void ConsultarSaldoClienteByID()
	{
		this.SaldoCliente.ClearProceso();
		this.SaldoCliente.ClearParametros();

		this.SaldoCliente.TipoProceso = "WS";
		this.SaldoCliente.AgregarProceso("ConsultarSaldoClienteByID");
		this.SaldoCliente.AgregarParametro("wsNamesPace", WSNamespace);
		this.SaldoCliente.AgregarParametro("wsUrl", wsURL);
		this.SaldoCliente.AgregarParametro("WebMethod", "ConsultarSaldoClienteByID");

		this.SaldoCliente.AgregarParametro("IdCliente", String.valueOf(this.Itinerario.IdCliente));

		this.SaldoCliente.AgregarParametro("wsUrl", wsURL);

		this.lsSaldoCliente = this.SaldoCliente.ConsultarProceso();
	}

	public void ConsultarSaldoClienteNotaByID()
	{
		this.SaldoClienteNota.ClearProceso();
		this.SaldoClienteNota.ClearParametros();

		this.SaldoClienteNota.TipoProceso = "WS";
		this.SaldoClienteNota.AgregarProceso("ConsultarSaldoClienteNotaByID");
		this.SaldoClienteNota.AgregarParametro("wsNamesPace", WSNamespace);
		this.SaldoClienteNota.AgregarParametro("wsUrl", wsURL);
		this.SaldoClienteNota.AgregarParametro("WebMethod", "ConsultarSaldoClienteNotaByID");

		this.SaldoClienteNota.AgregarParametro("IdCliente", String.valueOf(this.Itinerario.IdCliente));

		this.SaldoClienteNota.AgregarParametro("wsUrl", wsURL);

		this.lsSaldoClienteNota = this.SaldoClienteNota.ConsultarProceso();
	}

	public void EliminarHistoricoVentaDiaria()
	{
		this.HistoricoVentaDiaria.ClearProceso();
		this.HistoricoVentaDiaria.ClearParametros();
		this.HistoricoVentaDiaria.TipoProceso = "SQL";
		this.HistoricoVentaDiaria.AgregarProceso("SQL.EliminarTodos");
		this.HistoricoVentaDiaria.EjecutarProceso();
	}

	public void GuardarHistoricoVentaDiaria()
	{
		if (this.lsHistoricoVentaDiaria != null)
		{
			for (int x = 0; x < this.lsHistoricoVentaDiaria.size(); x++)
			{
				this.HistoricoVentaDiaria = ((BOHistoricoVentaDiaria)this.lsHistoricoVentaDiaria.get(x));
				this.HistoricoVentaDiaria.TipoProceso = "SQL";
				this.HistoricoVentaDiaria.AgregarProceso("SQL.Agregar");
				this.HistoricoVentaDiaria.EjecutarProceso();
			}
		}
	}

	public void ConsultarHistoricoVentaDiaria(int TipoProceso, int Persistir)
	{
		this.HistoricoVentaDiaria.ClearProceso();
		this.HistoricoVentaDiaria.ClearParametros();

		if (TipoProceso == 1)
		{
			this.HistoricoVentaDiaria.TipoProceso = "WS";
			this.HistoricoVentaDiaria.AgregarProceso("ConsultarHistoricoVentaDiaria");
			this.HistoricoVentaDiaria.AgregarParametro("wsNamesPace", WSNamespace);
			this.HistoricoVentaDiaria.AgregarParametro("wsUrl", wsURL);
			this.HistoricoVentaDiaria.AgregarParametro("WebMethod", "ConsultarHistoricoVentaDiaria");

			this.HistoricoVentaDiaria.AgregarParametro("IdVendedor", String.valueOf(this.Vendedor.IdVendedor));

			this.HistoricoVentaDiaria.AgregarParametro("wsUrl", wsURL);
			this.lsHistoricoVentaDiaria = this.HistoricoVentaDiaria.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsHistoricoVentaDiaria != null)
				{
					for (int x = 0; x < this.lsHistoricoVentaDiaria.size(); x++)
					{
						this.HistoricoVentaDiaria = ((BOHistoricoVentaDiaria)this.lsHistoricoVentaDiaria.get(x));
						this.HistoricoVentaDiaria.TipoProceso = "SQL";
						this.HistoricoVentaDiaria.AgregarProceso("SQL.Agregar");
						this.HistoricoVentaDiaria.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.HistoricoVentaDiaria.TipoProceso = "SQL";
			this.HistoricoVentaDiaria.AgregarProceso("SQL.Consultar");
			this.HistoricoVentaDiaria.getClass(); this.HistoricoVentaDiaria.AgregarParametro("idSortField", String.valueOf(4));


			this.HistoricoVentaDiaria.getClass(); this.HistoricoVentaDiaria.AgregarParametro("idFilterField", String.valueOf(1));
			this.HistoricoVentaDiaria.AgregarParametro("filtro", String.valueOf(this.Cliente.IdCliente));

			HistoricoVentaDiaria.getClass(); HistoricoVentaDiaria.AgregarParametro("idFilterField2", String.valueOf(HistoricoVentaDiaria.ID_Dia));
			HistoricoVentaDiaria.AgregarParametro("filtro2", String.valueOf(Utils.getDiaSemanaInglesa()));


			this.lsHistoricoVentaDiaria = this.HistoricoVentaDiaria.ConsultarProceso();
		}
	}

	public void EliminarInformacionCliente()
	{
		this.InformacionCliente.ClearProceso();
		this.InformacionCliente.ClearParametros();
		this.InformacionCliente.TipoProceso = "SQL";
		this.InformacionCliente.AgregarProceso("SQL.EliminarTodos");
		this.InformacionCliente.EjecutarProceso();
	}

	public void GuardarInformacionCliente()
	{
		if (this.lsInformacionCliente != null)
		{
			for (int x = 0; x < this.lsInformacionCliente.size(); x++)
			{
				this.InformacionCliente = ((BOInformacionCliente)this.lsInformacionCliente.get(x));
				this.InformacionCliente.TipoProceso = "SQL";
				this.InformacionCliente.AgregarProceso("SQL.Agregar");
				this.InformacionCliente.EjecutarProceso();
			}
		}
	}

	public void ConsultarInformacionCliente(int TipoProceso, int Persistir)
	{
		this.InformacionCliente.ClearProceso();

		this.InformacionCliente.ClearParametros();

		if (TipoProceso == 1)
		{
			this.InformacionCliente.TipoProceso = "WS";
			this.InformacionCliente.AgregarProceso("ConsultarInformacionCliente");
			this.InformacionCliente.AgregarParametro("wsNamesPace", WSNamespace);
			this.InformacionCliente.AgregarParametro("wsUrl", wsURL);
			this.InformacionCliente.AgregarParametro("WebMethod", "ConsultarInformacionCliente");

			this.InformacionCliente.AgregarParametro("IdVendedor", String.valueOf(this.Vendedor.IdVendedor));

			this.InformacionCliente.AgregarParametro("wsUrl", wsURL);
			this.lsInformacionCliente = this.InformacionCliente.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsInformacionCliente != null)
				{
					for (int x = 0; x < this.lsInformacionCliente.size(); x++)
					{
						this.InformacionCliente = ((BOInformacionCliente)this.lsInformacionCliente.get(x));
						this.InformacionCliente.TipoProceso = "SQL";
						this.InformacionCliente.AgregarProceso("SQL.Agregar");
						this.InformacionCliente.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.InformacionCliente.TipoProceso = "SQL";
			this.InformacionCliente.AgregarProceso("SQL.Consultar");
			this.InformacionCliente.getClass(); this.InformacionCliente.AgregarParametro("idSortField", String.valueOf(2));
			this.InformacionCliente.getClass(); this.InformacionCliente.AgregarParametro("idFilterField", String.valueOf(1));
			this.InformacionCliente.AgregarParametro("filtro", String.valueOf(this.Cliente.IdCliente));
			this.lsInformacionCliente = this.InformacionCliente.ConsultarProceso();
		}
	}

	public void EliminarDevolucionEmbalaje()
	{
		this.DevolucionEmbalaje.ClearProceso();
		this.DevolucionEmbalaje.ClearParametros();

		this.DevolucionEmbalaje.TipoProceso = "SQL";
		this.DevolucionEmbalaje.AgregarProceso("SQL.EliminarTodos");
		this.DevolucionEmbalaje.EjecutarProceso();
	}

	public void AgregarDevolucionEmbalaje()
	{
		this.DevolucionEmbalaje.ClearProceso();
		this.DevolucionEmbalaje.ClearParametros();

		this.DevolucionEmbalaje.TipoProceso = "SQL";
		this.DevolucionEmbalaje.AgregarProceso("SQL.Agregar");
		this.DevolucionEmbalaje.EjecutarProceso();
	}

	public void ConsultarDevolucionEmbalaje(int TipoProceso, int Persistir)
	{
		this.DevolucionEmbalaje.ClearProceso();
		this.DevolucionEmbalaje.ClearParametros();

		if (TipoProceso != 1)
		{
			if (TipoProceso == 2)
			{
				this.DevolucionEmbalaje.TipoProceso = "SQL";
				this.DevolucionEmbalaje.AgregarProceso("SQL.Consultar");
				this.DevolucionEmbalaje.getClass(); this.DevolucionEmbalaje.AgregarParametro("idSortField", String.valueOf(1));
				this.DevolucionEmbalaje.getClass(); this.DevolucionEmbalaje.AgregarParametro("idFilterField", String.valueOf(1));
				this.DevolucionEmbalaje.AgregarParametro("filtro", "");
				this.lsDevolucionEmbalaje = this.DevolucionEmbalaje.ConsultarProceso();
			}
		}
	}

	public void EliminarSaldoCliente()
	{
		this.SaldoCliente.ClearProceso();
		this.SaldoCliente.ClearParametros();
		this.SaldoCliente.TipoProceso = "SQL";
		this.SaldoCliente.AgregarProceso("SQL.EliminarTodos");
		this.SaldoCliente.EjecutarProceso();
	}

	public void GuardarSaldoCliente()
	{
		if (this.lsSaldoCliente != null)
		{
			for (int x = 0; x < this.lsSaldoCliente.size(); x++)
			{
				this.SaldoCliente = ((BOSaldoCliente)this.lsSaldoCliente.get(x));
				this.SaldoCliente.TipoProceso = "SQL";
				this.SaldoCliente.AgregarProceso("SQL.Agregar");
				this.SaldoCliente.EjecutarProceso();
			}
		}
	}

	public void ConsultarSaldoCliente(int TipoProceso, int Persistir)
	{
		this.SaldoCliente.ClearProceso();
		this.SaldoCliente.ClearParametros();

		if (TipoProceso == 1)
		{
			this.SaldoCliente.TipoProceso = "WS";
			this.SaldoCliente.AgregarProceso("ConsultarSaldoCliente");
			this.SaldoCliente.AgregarParametro("wsNamesPace", WSNamespace);
			this.SaldoCliente.AgregarParametro("wsUrl", wsURL);
			this.SaldoCliente.AgregarParametro("WebMethod", "ConsultarSaldoCliente");

			this.SaldoCliente.AgregarParametro("IdEmpresa", String.valueOf(this.Vendedor.IdEmpresa));

			this.SaldoCliente.AgregarParametro("wsUrl", wsURL);
			this.lsSaldoCliente = this.SaldoCliente.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsSaldoCliente != null)
				{
					for (int x = 0; x < this.lsSaldoCliente.size(); x++)
					{
						this.SaldoCliente = ((BOSaldoCliente)this.lsSaldoCliente.get(x));
						this.SaldoCliente.TipoProceso = "SQL";
						this.SaldoCliente.AgregarProceso("SQL.Agregar");
						this.SaldoCliente.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.SaldoCliente.TipoProceso = "SQL";
			this.SaldoCliente.AgregarProceso("SQL.Consultar");
			this.SaldoCliente.getClass(); this.SaldoCliente.AgregarParametro("idSortField", String.valueOf(1));
			this.SaldoCliente.getClass(); this.SaldoCliente.AgregarParametro("idFilterField", String.valueOf(1));
			this.SaldoCliente.AgregarParametro("filtro", String.valueOf(this.Itinerario.IdCliente));
			this.lsSaldoCliente = this.SaldoCliente.ConsultarProceso();
		}
	}

	public void EliminarSaldoClienteNota()
	{
		this.SaldoClienteNota.ClearProceso();
		this.SaldoClienteNota.ClearParametros();
		this.SaldoClienteNota.TipoProceso = "SQL";
		this.SaldoClienteNota.AgregarProceso("SQL.EliminarTodos");
		this.SaldoClienteNota.EjecutarProceso();
	}

	public void GuardarSaldoClienteNota()
	{
		if (this.lsSaldoClienteNota != null)
		{
			for (int x = 0; x < this.lsSaldoClienteNota.size(); x++)
			{
				this.SaldoClienteNota = ((BOSaldoClienteNota)this.lsSaldoClienteNota.get(x));
				this.SaldoClienteNota.TipoProceso = "SQL";
				this.SaldoClienteNota.AgregarProceso("SQL.Agregar");
				this.SaldoClienteNota.EjecutarProceso();
			}
		}
	}

	public void ConsultarSaldoClienteNota(int TipoProceso, int Persistir)
	{
		this.SaldoClienteNota.ClearProceso();
		this.SaldoClienteNota.ClearParametros();

		if (TipoProceso == 1)
		{
			this.SaldoClienteNota.TipoProceso = "WS";
			this.SaldoClienteNota.AgregarProceso("ConsultarSaldoClienteNota");
			this.SaldoClienteNota.AgregarParametro("wsNamesPace", WSNamespace);
			this.SaldoClienteNota.AgregarParametro("wsUrl", wsURL);
			this.SaldoClienteNota.AgregarParametro("WebMethod", "ConsultarSaldoClienteNota");

			this.SaldoClienteNota.AgregarParametro("IdEmpresa", String.valueOf(this.Vendedor.IdEmpresa));

			this.SaldoClienteNota.AgregarParametro("wsUrl", wsURL);
			this.lsSaldoClienteNota = this.SaldoClienteNota.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsSaldoClienteNota != null)
				{
					for (int x = 0; x < this.lsSaldoClienteNota.size(); x++)
					{
						this.SaldoClienteNota = ((BOSaldoClienteNota)this.lsSaldoClienteNota.get(x));
						this.SaldoClienteNota.TipoProceso = "SQL";
						this.SaldoClienteNota.AgregarProceso("SQL.Agregar");
						this.SaldoClienteNota.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.SaldoClienteNota.TipoProceso = "SQL";
			this.SaldoClienteNota.AgregarProceso("SQL.Consultar");
			this.SaldoClienteNota.getClass(); this.SaldoClienteNota.AgregarParametro("idSortField", String.valueOf(1));
			this.SaldoClienteNota.getClass(); this.SaldoClienteNota.AgregarParametro("idFilterField", String.valueOf(1));
			this.SaldoClienteNota.AgregarParametro("filtro", String.valueOf(this.Itinerario.IdCliente));
			this.lsSaldoClienteNota = this.SaldoClienteNota.ConsultarProceso();
		}
	}

	public void EliminarPreguntaCuestionario()
	{
		this.PreguntaCuestionario.ClearProceso();
		this.PreguntaCuestionario.ClearParametros();
		this.PreguntaCuestionario.TipoProceso = "SQL";
		this.PreguntaCuestionario.AgregarProceso("SQL.EliminarTodos");
		this.PreguntaCuestionario.EjecutarProceso();
	}

	public void GuardarPreguntaCuestionario()
	{
		if (this.lsPreguntaCuestionario != null)
		{
			for (int x = 0; x < this.lsPreguntaCuestionario.size(); x++)
			{
				this.PreguntaCuestionario = ((BOPreguntaCuestionario)this.lsPreguntaCuestionario.get(x));
				this.PreguntaCuestionario.TipoProceso = "SQL";
				this.PreguntaCuestionario.AgregarProceso("SQL.Agregar");
				this.PreguntaCuestionario.EjecutarProceso();
			}
		}
	}

	public void ConsultarPreguntaCuestionario(int TipoProceso, int Persistir)
	{
		this.PreguntaCuestionario.ClearProceso();
		this.PreguntaCuestionario.ClearParametros();

		if (TipoProceso == 1)
		{
			this.PreguntaCuestionario.TipoProceso = "WS";
			this.PreguntaCuestionario.AgregarProceso("ConsultarPreguntaCuestionario");
			this.PreguntaCuestionario.AgregarParametro("wsNamesPace", WSNamespace);
			this.PreguntaCuestionario.AgregarParametro("wsUrl", wsURL);
			this.PreguntaCuestionario.AgregarParametro("WebMethod", "ConsultarPreguntaCuestionario");

			this.PreguntaCuestionario.AgregarParametro("wsUrl", wsURL);
			this.lsPreguntaCuestionario = this.PreguntaCuestionario.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsPreguntaCuestionario != null)
				{
					for (int x = 0; x < this.lsPreguntaCuestionario.size(); x++)
					{
						this.PreguntaCuestionario = ((BOPreguntaCuestionario)this.lsPreguntaCuestionario.get(x));
						this.PreguntaCuestionario.TipoProceso = "SQL";
						this.PreguntaCuestionario.AgregarProceso("SQL.Agregar");
						this.PreguntaCuestionario.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.PreguntaCuestionario.TipoProceso = "SQL";
			this.PreguntaCuestionario.AgregarProceso("SQL.Consultar");
			this.PreguntaCuestionario.getClass(); this.PreguntaCuestionario.AgregarParametro("idSortField", String.valueOf(1));
			this.PreguntaCuestionario.getClass(); this.PreguntaCuestionario.AgregarParametro("idFilterField", String.valueOf(1));
			this.PreguntaCuestionario.AgregarParametro("filtro", "");
			this.lsPreguntaCuestionario = this.PreguntaCuestionario.ConsultarProceso();
		}
	}

	public void EliminarCuestionario()
	{
		this.Cuestionario.ClearProceso();
		this.Cuestionario.ClearParametros();
		this.Cuestionario.TipoProceso = "SQL";
		this.Cuestionario.AgregarProceso("SQL.EliminarTodos");
		this.Cuestionario.EjecutarProceso();
	}

	public void GuardarCuestionario()
	{
		if (this.lsCuestionario != null)
		{
			for (int x = 0; x < this.lsCuestionario.size(); x++)
			{
				this.Cuestionario = ((BOCuestionario)this.lsCuestionario.get(x));
				this.Cuestionario.TipoProceso = "SQL";
				this.Cuestionario.AgregarProceso("SQL.Agregar");
				this.Cuestionario.EjecutarProceso();
			}
		}
	}

	public void ConsultarCuestionario(int TipoProceso, int Persistir)
	{
		this.Cuestionario.ClearProceso();
		this.Cuestionario.ClearParametros();

		if (TipoProceso == 1)
		{
			this.Cuestionario.TipoProceso = "WS";
			this.Cuestionario.AgregarProceso("ConsultarCuestionario");
			this.Cuestionario.AgregarParametro("wsNamesPace", WSNamespace);
			this.Cuestionario.AgregarParametro("wsUrl", wsURL);
			this.Cuestionario.AgregarParametro("WebMethod", "ConsultarCuestionario");

			this.Cuestionario.AgregarParametro("wsUrl", wsURL);
			this.lsCuestionario = this.Cuestionario.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsCuestionario != null)
				{
					for (int x = 0; x < this.lsCuestionario.size(); x++)
					{
						this.Cuestionario = ((BOCuestionario)this.lsCuestionario.get(x));
						this.Cuestionario.TipoProceso = "SQL";
						this.Cuestionario.AgregarProceso("SQL.Agregar");
						this.Cuestionario.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.Cuestionario.TipoProceso = "SQL";
			this.Cuestionario.AgregarProceso("SQL.Consultar");
			this.Cuestionario.getClass(); this.Cuestionario.AgregarParametro("idSortField", String.valueOf(1));
			this.Cuestionario.getClass(); this.Cuestionario.AgregarParametro("idFilterField", String.valueOf(1));
			this.Cuestionario.AgregarParametro("filtro", "");
			this.lsCuestionario = this.Cuestionario.ConsultarProceso();
		}
	}

	public void EliminarCuestionarioCliente()
	{
		this.CuestionarioCliente.ClearProceso();
		this.CuestionarioCliente.ClearParametros();
		this.CuestionarioCliente.TipoProceso = "SQL";
		this.CuestionarioCliente.AgregarProceso("SQL.EliminarTodos");
		this.CuestionarioCliente.EjecutarProceso();
	}

	public void GuardarCuestionarioCliente()
	{
		if (this.lsCuestionarioCliente != null)
		{
			for (int x = 0; x < this.lsCuestionarioCliente.size(); x++)
			{
				this.CuestionarioCliente = ((BOCuestionarioCliente)this.lsCuestionarioCliente.get(x));
				this.CuestionarioCliente.TipoProceso = "SQL";
				this.CuestionarioCliente.AgregarProceso("SQL.Agregar");
				this.CuestionarioCliente.EjecutarProceso();
			}
		}
	}

	public void ConsultarCuestionarioCliente(int TipoProceso, int Persistir)
	{
		this.CuestionarioCliente.ClearProceso();
		this.CuestionarioCliente.ClearParametros();

		if (TipoProceso == 1)
		{
			this.CuestionarioCliente.TipoProceso = "WS";
			this.CuestionarioCliente.AgregarProceso("ConsultarCuestionarioCliente");
			this.CuestionarioCliente.AgregarParametro("wsNamesPace", WSNamespace);
			this.CuestionarioCliente.AgregarParametro("wsUrl", wsURL);
			this.CuestionarioCliente.AgregarParametro("WebMethod", "ConsultarCuestionarioCliente");

			this.CuestionarioCliente.AgregarParametro("wsUrl", wsURL);
			this.lsCuestionarioCliente = this.CuestionarioCliente.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsCuestionarioCliente != null)
				{
					for (int x = 0; x < this.lsCuestionarioCliente.size(); x++)
					{
						this.CuestionarioCliente = ((BOCuestionarioCliente)this.lsCuestionarioCliente.get(x));
						this.CuestionarioCliente.TipoProceso = "SQL";
						this.CuestionarioCliente.AgregarProceso("SQL.Agregar");
						this.CuestionarioCliente.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.CuestionarioCliente.TipoProceso = "SQL";
			this.CuestionarioCliente.AgregarProceso("SQL.Consultar");
			this.CuestionarioCliente.getClass(); this.CuestionarioCliente.AgregarParametro("idSortField", String.valueOf(1));
			this.CuestionarioCliente.getClass(); this.CuestionarioCliente.AgregarParametro("idFilterField", String.valueOf(1));
			this.CuestionarioCliente.AgregarParametro("filtro", "");
			this.lsCuestionarioCliente = this.CuestionarioCliente.ConsultarProceso();
		}
	}

	public void EliminarVehiculo()
	{
		this.Vehiculo.ClearProceso();
		this.Vehiculo.ClearParametros();
		this.Vehiculo.TipoProceso = "SQL";
		this.Vehiculo.AgregarProceso("SQL.EliminarTodos");
		this.Vehiculo.EjecutarProceso();
	}

	public void GuardarVehiculo()
	{
		if (this.lsVehiculo != null)
		{
			for (int x = 0; x < this.lsVehiculo.size(); x++)
			{
				this.Vehiculo = ((BOVehiculo)this.lsVehiculo.get(x));
				this.Vehiculo.TipoProceso = "SQL";
				this.Vehiculo.AgregarProceso("SQL.Agregar");
				this.Vehiculo.EjecutarProceso();
			}
		}
	}

	public void ConsultarVehiculo(int TipoProceso, int Persistir)
	{
		this.Vehiculo.ClearProceso();
		this.Vehiculo.ClearParametros();

		if (TipoProceso == 1)
		{
			this.Vehiculo.TipoProceso = "WS";
			this.Vehiculo.AgregarProceso("ConsultarVehiculo");
			this.Vehiculo.AgregarParametro("wsNamesPace", WSNamespace);
			this.Vehiculo.AgregarParametro("wsUrl", wsURL);
			this.Vehiculo.AgregarParametro("WebMethod", "ConsultarVehiculo");

			this.Vehiculo.AgregarParametro("wsUrl", wsURL);
			this.lsVehiculo = this.Vehiculo.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsVehiculo != null)
				{
					for (int x = 0; x < this.lsVehiculo.size(); x++)
					{
						this.Vehiculo = ((BOVehiculo)this.lsVehiculo.get(x));
						this.Vehiculo.TipoProceso = "SQL";
						this.Vehiculo.AgregarProceso("SQL.Agregar");
						this.Vehiculo.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.Vehiculo.TipoProceso = "SQL";
			this.Vehiculo.AgregarProceso("SQL.Consultar");
			this.Vehiculo.getClass(); this.Vehiculo.AgregarParametro("idSortField", String.valueOf(1));
			this.Vehiculo.getClass(); this.Vehiculo.AgregarParametro("idFilterField", String.valueOf(1));
			this.Vehiculo.AgregarParametro("filtro", "");
			this.lsVehiculo = this.Vehiculo.ConsultarProceso();
		}
	}

	public void EliminarCheckVehiculo()
	{
		this.CheckVehiculo.ClearProceso();
		this.CheckVehiculo.ClearParametros();
		this.CheckVehiculo.TipoProceso = "SQL";
		this.CheckVehiculo.AgregarProceso("SQL.EliminarTodos");
		this.CheckVehiculo.EjecutarProceso();
	}

	public void GuardarCheckVehiculo()
	{
		if (this.lsCheckVehiculo != null)
		{
			for (int x = 0; x < this.lsCheckVehiculo.size(); x++)
			{
				this.CheckVehiculo = ((BOCheckVehiculo)this.lsCheckVehiculo.get(x));
				this.CheckVehiculo.TipoProceso = "SQL";
				this.CheckVehiculo.AgregarProceso("SQL.Agregar");
				this.CheckVehiculo.EjecutarProceso();
			}
		}
	}

	public void ConsultarCheckVehiculo(int TipoProceso, int Persistir)
	{
		this.CheckVehiculo.ClearProceso();
		this.CheckVehiculo.ClearParametros();

		if (TipoProceso == 1)
		{
			this.CheckVehiculo.TipoProceso = "WS";
			this.CheckVehiculo.AgregarProceso("ConsultarCheckVehiculo");
			this.CheckVehiculo.AgregarParametro("wsNamesPace", WSNamespace);
			this.CheckVehiculo.AgregarParametro("wsUrl", wsURL);
			this.CheckVehiculo.AgregarParametro("WebMethod", "ConsultarCheckVehiculo");

			this.CheckVehiculo.AgregarParametro("wsUrl", wsURL);
			this.lsCheckVehiculo = this.CheckVehiculo.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsCheckVehiculo != null)
				{
					for (int x = 0; x < this.lsCheckVehiculo.size(); x++)
					{
						this.CheckVehiculo = ((BOCheckVehiculo)this.lsCheckVehiculo.get(x));
						this.CheckVehiculo.TipoProceso = "SQL";
						this.CheckVehiculo.AgregarProceso("SQL.Agregar");
						this.CheckVehiculo.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.CheckVehiculo.TipoProceso = "SQL";
			this.CheckVehiculo.AgregarProceso("SQL.Consultar");
			this.CheckVehiculo.getClass(); this.CheckVehiculo.AgregarParametro("idSortField", String.valueOf(1));
			this.CheckVehiculo.getClass(); this.CheckVehiculo.AgregarParametro("idFilterField", String.valueOf(1));
			this.CheckVehiculo.AgregarParametro("filtro", "");
			this.lsCheckVehiculo = this.CheckVehiculo.ConsultarProceso();
		}
	}

	public void EliminarBitacora()
	{
		this.Bitacora.ClearProceso();
		this.Bitacora.ClearParametros();
		this.Bitacora.TipoProceso = "SQL";
		this.Bitacora.AgregarProceso("SQL.EliminarTodos");
		this.Bitacora.EjecutarProceso();
	}

	public void ConsultarBitacora(int TipoProceso, int Persistir)
	{
		this.Bitacora.ClearProceso();
		this.Bitacora.ClearParametros();
		if (TipoProceso == 1)
		{
			this.Bitacora.TipoProceso = "WS";
			this.Bitacora.AgregarProceso("ConsultarBitacora");
			this.Bitacora.AgregarParametro("wsNamesPace", WSNamespace);
			this.Bitacora.AgregarParametro("wsUrl", wsURL);
			this.Bitacora.AgregarParametro("WebMethod", "ConsultarBitacora");

			this.Bitacora.AgregarParametro("IdEmpresa", String.valueOf(this.Vendedor.IdEmpresa));

			this.Bitacora.AgregarParametro("wsUrl", wsURL);
			this.lsBitacora = this.Bitacora.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsBitacora != null)
				{
					for (int x = 0; x < this.lsBitacora.size(); x++)
					{
						this.Bitacora = ((BOBitacora)this.lsBitacora.get(x));
						this.Bitacora.TipoProceso = "SQL";
						this.Bitacora.AgregarProceso("SQL.Agregar");
						this.Bitacora.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.Bitacora.TipoProceso = "SQL";
			this.Bitacora.AgregarProceso("SQL.Consultar");
			this.Bitacora.getClass(); this.Bitacora.AgregarParametro("idSortField", String.valueOf(1));
			this.Bitacora.getClass(); this.Bitacora.AgregarParametro("idFilterField", String.valueOf(1));
			this.Bitacora.AgregarParametro("filtro", "");
			this.lsBitacora = this.Bitacora.ConsultarProceso();
		}
	}

	public void EliminarCategoria()
	{
		this.Categoria.ClearProceso();
		this.Categoria.ClearParametros();
		this.Categoria.TipoProceso = "SQL";
		this.Categoria.AgregarProceso("SQL.EliminarTodos");
		this.Categoria.EjecutarProceso();
	}

	public void ConsultarCategoria(int TipoProceso, int Persistir)
	{
		this.Categoria.ClearProceso();
		this.Categoria.ClearParametros();
		if (TipoProceso == 1)
		{
			this.Categoria.TipoProceso = "WS";
			this.Categoria.AgregarProceso("ConsultarCategorias");
			this.Categoria.AgregarParametro("wsNamesPace", WSNamespace);
			this.Categoria.AgregarParametro("wsUrl", wsURL);
			this.Categoria.AgregarParametro("WebMethod", "ConsultarCategorias");
			this.Categoria.AgregarParametro("IdEmpresa", String.valueOf(this.Vendedor.IdEmpresa));
			this.Categoria.AgregarParametro("wsUrl", wsURL);
			this.lsCategoria = this.Categoria.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsCategoria != null)
				{
					for (int x = 0; x < this.lsCategoria.size(); x++)
					{
						this.Categoria = ((BOCategoria)this.lsCategoria.get(x));
						this.Categoria.TipoProceso = "SQL";
						this.Categoria.AgregarProceso("SQL.Agregar");
						this.Categoria.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.Categoria.TipoProceso = "SQL";
			this.Categoria.AgregarProceso("SQL.Consultar");
			this.Categoria.getClass(); this.Categoria.AgregarParametro("idSortField", String.valueOf(1));
			this.Categoria.getClass(); this.Categoria.AgregarParametro("idFilterField", String.valueOf(1));
			this.Categoria.AgregarParametro("filtro", "");
			this.lsCategoria = this.Categoria.ConsultarProceso();
		}
	}

	public void GuardarCategoria()
	{
		if (this.lsCategoria != null)
		{
			for (int x = 0; x < this.lsCategoria.size(); x++)
			{
				this.Categoria = ((BOCategoria)this.lsCategoria.get(x));
				this.Categoria.TipoProceso = "SQL";
				this.Categoria.AgregarProceso("SQL.Agregar");
				this.Categoria.EjecutarProceso();
			}
		}
	}

	public void EliminarRuta()
	{
		this.Ruta.ClearProceso();
		this.Ruta.ClearParametros();
		this.Ruta.TipoProceso = "SQL";
		this.Ruta.AgregarProceso("SQL.EliminarTodos");
		this.Ruta.EjecutarProceso();
	}

	public void ConsultarRuta(int TipoProceso, int Persistir)
	{
		this.Ruta.ClearProceso();
		this.Ruta.ClearParametros();
		if (TipoProceso == 1)
		{
			this.Ruta.TipoProceso = "WS";
			this.Ruta.AgregarProceso("ConsultarRutas");
			this.Ruta.AgregarParametro("wsNamesPace", WSNamespace);
			this.Ruta.AgregarParametro("wsUrl", wsURL);
			this.Ruta.AgregarParametro("WebMethod", "ConsultarRutas");
			this.Ruta.AgregarParametro("IdEmpresa", String.valueOf(this.Vendedor.IdEmpresa));
			this.Ruta.AgregarParametro("wsUrl", wsURL);
			this.lsRuta = this.Ruta.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsRuta != null)
				{
					for (int x = 0; x < this.lsRuta.size(); x++)
					{
						this.Ruta = ((BORuta)this.lsRuta.get(x));
						this.Ruta.TipoProceso = "SQL";
						this.Ruta.AgregarProceso("SQL.Agregar");
						this.Ruta.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.Ruta.TipoProceso = "SQL";
			this.Ruta.AgregarProceso("SQL.Consultar");
			this.Ruta.getClass(); this.Ruta.AgregarParametro("idSortField", String.valueOf(1));
			this.Ruta.getClass(); this.Ruta.AgregarParametro("idFilterField", String.valueOf(1));
			this.Ruta.AgregarParametro("filtro", "");
			this.lsRuta = this.Ruta.ConsultarProceso();
		}
	}

	public void GuardarRuta()
	{
		if (this.lsRuta != null)
		{
			for (int x = 0; x < this.lsRuta.size(); x++)
			{
				this.Ruta = ((BORuta)this.lsRuta.get(x));
				this.Ruta.TipoProceso = "SQL";
				this.Ruta.AgregarProceso("SQL.Agregar");
				this.Ruta.EjecutarProceso();
			}
		}
	}

	public void EliminarCliente()
	{
		this.Cliente.ClearProceso();
		this.Cliente.ClearParametros();
		this.Cliente.TipoProceso = "SQL";
		this.Cliente.AgregarProceso("SQL.EliminarTodos");
		this.Cliente.EjecutarProceso();
	}

	public void GuardarCliente()
	{
		this.Cliente.ClearProceso();
		this.Cliente.ClearParametros();

		if (this.lsCliente != null)
		{
			for (int x = 0; x < this.lsCliente.size(); x++)
			{
				this.Cliente = ((BOCliente)this.lsCliente.get(x));
				this.Cliente.TipoProceso = "SQL";
				this.Cliente.AgregarProceso("SQL.Agregar");
				this.Cliente.EjecutarProceso();
			}
		}
	}

	public void ConsultarClientes(int TipoProceso, int Persistir, String IdEmpresa, String PageNumber, String PageSize)
	{
		this.Cliente.ClearProceso();
		this.Cliente.ClearParametros();
		if (TipoProceso == 1)
		{
			this.Cliente.TipoProceso = "WS";

			this.Cliente.AgregarProceso("ConsultarCliente");
			this.Cliente.AgregarParametro("wsNamesPace", WSNamespace);
			this.Cliente.AgregarParametro("wsUrl", wsURL);
			this.Cliente.AgregarParametro("WebMethod", "ConsultarCliente");

			this.Cliente.AgregarParametro("IdVendedor", String.valueOf(this.Vendedor.IdVendedor));
			this.Cliente.AgregarParametro("PageNumber", PageNumber);
			this.Cliente.AgregarParametro("PageSize", PageSize);

			this.lsCliente = this.Cliente.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsCliente != null)
				{
					for (int x = 0; x < this.lsCliente.size(); x++)
					{
						this.Cliente = ((BOCliente)this.lsCliente.get(x));

						Logg.info("c: " + this.Cliente.Nombre + " id: " + this.Cliente.IdCliente);

						this.Cliente.TipoProceso = "SQL";
						this.Cliente.AgregarProceso("SQL.Agregar");
						this.Cliente.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.Cliente.TipoProceso = "SQL";
			this.Cliente.AgregarProceso("SQL.Consultar");
			this.Cliente.getClass(); this.Cliente.AgregarParametro("idSortField", String.valueOf(2));
			this.Cliente.getClass(); this.Cliente.AgregarParametro("idFilterField", String.valueOf(4));
			this.Cliente.AgregarParametro("filtro", String.valueOf(Utils.getDiaSemanaInglesa()));
			this.lsCliente = this.Cliente.ConsultarProceso();
		}
	}

	public void ConsultarCliente(int TipoProceso, int Persistir, String Nombre)
	{
		if (TipoProceso == 1)
		{
			this.Cliente.ClearProceso();
			this.Cliente.ClearParametros();
			this.Cliente.TipoProceso = "WS";

			this.Cliente.AgregarProceso("ConsultarCliente");
			this.Cliente.AgregarParametro("wsNamesPace", WSNamespace);
			this.Cliente.AgregarParametro("wsUrl", wsURL);
			this.Cliente.AgregarParametro("WebMethod", "ConsultarCliente");

			this.lsCliente = this.Cliente.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsCliente != null)
				{
					for (int x = 0; x < this.lsCliente.size(); x++)
					{
						this.Cliente = ((BOCliente)this.lsCliente.get(x));
						this.Cliente.TipoProceso = "SQL";
						this.Cliente.AgregarProceso("SQL.Agregar");
						this.Cliente.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			if (this.Conf_Itinerario)
			{
				this.Itinerario.ClearProceso();
				this.Itinerario.ClearParametros();
				this.Itinerario.TipoProceso = "SQL";
				this.Itinerario.AgregarProceso("SQL.Consultar");
				this.Itinerario.getClass(); this.Itinerario.AgregarParametro("idSortField", String.valueOf(2));
				this.Itinerario.getClass(); this.Itinerario.AgregarParametro("idFilterField", String.valueOf(2));
				this.Itinerario.AgregarParametro("filtro", Nombre);
				this.lsItinerario = this.Itinerario.ConsultarProceso();
			}
			else
			{
				this.Cliente.ClearProceso();
				this.Cliente.ClearParametros();
				this.Cliente.TipoProceso = "SQL";
				this.Cliente.AgregarProceso("SQL.Consultar");
				this.Cliente.getClass(); this.Cliente.AgregarParametro("idSortField", String.valueOf(2));
				this.Cliente.getClass(); this.Cliente.AgregarParametro("idFilterField", String.valueOf(2));
				this.Cliente.AgregarParametro("filtro", Nombre);
				this.lsCliente = this.Cliente.ConsultarProceso();
			}
		}
	}

	public void EliminarVendedor()
	{
		this.Vendedor.ClearProceso();
		this.Vendedor.ClearParametros();
		this.Vendedor.TipoProceso = "SQL";
		this.Vendedor.AgregarProceso("SQL.EliminarTodos");
		this.Vendedor.EjecutarProceso();
	}



	public void GuardarVendedor()
	{
		GuardarListaBO(this.lsVendedor, this.Vendedor);
	}



	public void ConsultarVendedor(int TipoProceso, int Persistir, String UsuarioVendedor, String PasswordVendedor)
	{
		this.Vendedor.ClearProceso();
		this.Vendedor.ClearParametros();
		if (TipoProceso == 1)
		{
			this.Vendedor.TipoProceso = "WS";
			this.Vendedor.AgregarProceso("ConsultarVendedor2");
			this.Vendedor.AgregarParametro("wsNamesPace", WSNamespace);
			this.Vendedor.AgregarParametro("wsUrl", wsURL);
			this.Vendedor.AgregarParametro("WebMethod", "ConsultarVendedor2");

			this.Vendedor.AgregarParametro("UsuarioVendedor", UsuarioVendedor);
			this.Vendedor.AgregarParametro("PasswordVendedor", PasswordVendedor);
			this.Vendedor.AgregarParametro("PIN", Info.getPIN(context));
			this.Vendedor.AgregarParametro("IP", Info.GetIPAdress(context));
			this.Vendedor.AgregarParametro("RED", Info.GetWifiName(context));

			this.lsVendedor = this.Vendedor.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsVendedor != null)
				{
					for (int x = 0; x < this.lsVendedor.size(); x++)
					{
						this.Vendedor = ((BOVendedor)this.lsVendedor.get(x));
						this.Vendedor.TipoProceso = "SQL";
						this.Vendedor.AgregarProceso("SQL.Agregar");
						this.Vendedor.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.Vendedor.TipoProceso = "SQL";
			this.Vendedor.AgregarProceso("SQL.Consultar");
			this.Vendedor.getClass(); this.Vendedor.AgregarParametro("idSortField", String.valueOf(3));
			this.Vendedor.getClass(); this.Vendedor.AgregarParametro("idFilterField", String.valueOf(3));
			this.Vendedor.AgregarParametro("filtro", "");
			this.lsVendedor = this.Vendedor.ConsultarProceso();
		}
	}

	public void GuardarAlmacenMovil()
	{
		this.AlmacenMovil.ClearProceso();
		this.AlmacenMovil.ClearParametros();

		if (this.lsAlmacenMovil != null)
		{
			for (int x = 0; x < this.lsAlmacenMovil.size(); x++)
			{
				this.AlmacenMovil = ((BOAlmacenMovil)this.lsAlmacenMovil.get(x));
				this.AlmacenMovil.TipoProceso = "SQL";
				this.AlmacenMovil.AgregarProceso("SQL.Agregar");
				this.AlmacenMovil.EjecutarProceso();
			}
		}
	}

	public void EliminarAlmacenMovil()
	{
		this.AlmacenMovil.ClearProceso();
		this.AlmacenMovil.ClearParametros();
		this.AlmacenMovil.TipoProceso = "SQL";
		this.AlmacenMovil.AgregarProceso("SQL.EliminarTodos");
		this.AlmacenMovil.EjecutarProceso();
	}

	public void ConsultarAlmacenMovil(int TipoProceso, int Persistir, String IdVendedor, String PageNumber, String PageSize)
	{
		this.AlmacenMovil.ClearProceso();
		this.AlmacenMovil.ClearParametros();
		if (TipoProceso == 1)
		{
			this.AlmacenMovil.TipoProceso = "WS";
			this.AlmacenMovil.AgregarProceso("ConsultarAlmacenMovil");
			this.AlmacenMovil.AgregarParametro("wsNamesPace", WSNamespace);
			this.AlmacenMovil.AgregarParametro("wsUrl", wsURL);
			this.AlmacenMovil.AgregarParametro("WebMethod", "ConsultarAlmacenMovil");

			this.AlmacenMovil.AgregarParametro("IdVendedor", IdVendedor);
			this.AlmacenMovil.AgregarParametro("PageNumber", PageNumber);
			this.AlmacenMovil.AgregarParametro("PageSize", PageSize);

			this.AlmacenMovil.AgregarParametro("wsUrl", wsURL);
			this.lsAlmacenMovil = this.AlmacenMovil.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsAlmacenMovil != null)
				{
					for (int x = 0; x < this.lsAlmacenMovil.size(); x++)
					{
						this.AlmacenMovil = ((BOAlmacenMovil)this.lsAlmacenMovil.get(x));

						Logg.info(this.AlmacenMovil.Codigo + " prod: " + this.AlmacenMovil.Producto + " id: " + this.AlmacenMovil.IdProducto);

						this.AlmacenMovil.TipoProceso = "SQL";
						this.AlmacenMovil.AgregarProceso("SQL.Agregar");
						this.AlmacenMovil.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.AlmacenMovil.TipoProceso = "SQL";
			this.AlmacenMovil.AgregarProceso("SQL.Consultar");
			this.AlmacenMovil.getClass(); this.AlmacenMovil.AgregarParametro("idSortField", String.valueOf(1));

			this.AlmacenMovil.getClass(); this.AlmacenMovil.AgregarParametro("idFilterField", String.valueOf(15));

			this.AlmacenMovil.AgregarParametro("filtro", "");

			this.lsAlmacenMovil = this.AlmacenMovil.ConsultarProceso();
		}
	}

	public void ConsultarAlmacenMovil2(int TipoProceso, int Persistir, String IdVendedor, String PageNumber, String PageSize, boolean vendiendo)
	{
		this.AlmacenMovil.ClearProceso();
		this.AlmacenMovil.ClearParametros();

		this.AlmacenMovil.TipoProceso = "SQL";
		this.AlmacenMovil.AgregarProceso("SQL.Consultar");
		this.AlmacenMovil.AgregarParametro("idSortField", String.valueOf(this.AlmacenMovil.ID_Producto));
		this.AlmacenMovil.AgregarParametro("idFilterField", String.valueOf(AlmacenMovil.ID_Producto));
		this.AlmacenMovil.AgregarParametro("filtro", "");

		this.lsAlmacenMovil2 = this.AlmacenMovil.ConsultarProceso();
	}

	public void ConsultarAlmacenMovilNoEmbalaje()
	{
		this.AlmacenMovil.TipoProceso = "SQL";
		this.AlmacenMovil.AgregarProceso("SQL.Consultar");

		this.AlmacenMovil.getClass(); this.AlmacenMovil.AgregarParametro("idSortField", String.valueOf(1));
		this.AlmacenMovil.getClass(); this.AlmacenMovil.AgregarParametro("idFilterField", String.valueOf(15));
		this.AlmacenMovil.AgregarParametro("filtro", "1");

		this.lsAlmacenMovil = this.AlmacenMovil.ConsultarProceso();
	}

	public void EliminarProducto()
	{
		this.Producto.ClearProceso();
		this.Producto.ClearParametros();
		this.Producto.TipoProceso = "SQL";
		this.Producto.AgregarProceso("SQL.EliminarTodos");
		this.Producto.EjecutarProceso();
	}

	public void GuardarProducto()
	{
		if (this.lsProducto != null)
		{
			for (int x = 0; x < this.lsProducto.size(); x++)
			{
				this.Producto = ((BOProducto)this.lsProducto.get(x));
				this.Producto.TipoProceso = "SQL";
				this.Producto.AgregarProceso("SQL.Agregar");
				this.Producto.EjecutarProceso();
			}
		}
	}

	public void ConsultarProductoPedido(int TipoProceso, int Persistir, String IdEmpresa, String PageNumber, String PageSize)
	{
		this.Producto.ClearProceso();
		this.Producto.ClearParametros();
		if (TipoProceso == 1)
		{
			this.Producto.TipoProceso = "WS";
			this.Producto.AgregarProceso("ConsultarProducto");
			this.Producto.AgregarParametro("wsNamesPace", WSNamespace);
			this.Producto.AgregarParametro("wsUrl", wsURL);

			this.Producto.AgregarParametro("WebMethod", "ConsultarProducto");
			this.Producto.AgregarParametro("IdEmpresa", IdEmpresa);
			this.Producto.AgregarParametro("PageNumber", PageNumber);
			this.Producto.AgregarParametro("PageSize", PageSize);

			this.Producto.AgregarParametro("wsUrl", wsURL);
			this.lsProductoPedido = this.Producto.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsProductoPedido != null)
				{
					for (int x = 0; x < this.lsProductoPedido.size(); x++)
					{
						this.Producto = ((BOProducto)this.lsProductoPedido.get(x));
						this.Producto.TipoProceso = "SQL";
						this.Producto.AgregarProceso("SQL.Agregar");
						this.Producto.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.Producto.TipoProceso = "SQL";
			this.Producto.AgregarProceso("SQL.Consultar");
			this.Producto.getClass(); this.Producto.AgregarParametro("idSortField", String.valueOf(1));
			this.Producto.getClass(); this.Producto.AgregarParametro("idFilterField", String.valueOf(9));
			this.Producto.AgregarParametro("filtro", "1");
			this.lsProductoPedido = this.Producto.ConsultarProceso();
		}
	}

	public void ConsultarProducto(int TipoProceso, int Persistir, String IdEmpresa, String PageNumber, String PageSize)
	{
		this.Producto.ClearProceso();
		this.Producto.ClearParametros();
		if (TipoProceso == 1)
		{
			this.Producto.TipoProceso = "WS";
			this.Producto.AgregarProceso("ConsultarProducto");
			this.Producto.AgregarParametro("wsNamesPace", WSNamespace);
			this.Producto.AgregarParametro("wsUrl", wsURL);

			this.Producto.AgregarParametro("WebMethod", "ConsultarProducto");
			this.Producto.AgregarParametro("IdEmpresa", IdEmpresa);
			this.Producto.AgregarParametro("PageNumber", PageNumber);
			this.Producto.AgregarParametro("PageSize", PageSize);

			this.Producto.AgregarParametro("wsUrl", wsURL);
			this.lsProducto = this.Producto.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsProducto != null)
				{
					for (int x = 0; x < this.lsProducto.size(); x++)
					{
						this.Producto = ((BOProducto)this.lsProducto.get(x));
						this.Producto.TipoProceso = "SQL";
						this.Producto.AgregarProceso("SQL.Agregar");
						this.Producto.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.Producto.TipoProceso = "SQL";
			this.Producto.AgregarProceso("SQL.Consultar");
			this.Producto.getClass(); this.Producto.AgregarParametro("idSortField", String.valueOf(1));
			this.Producto.getClass(); this.Producto.AgregarParametro("idFilterField", String.valueOf(1));
			this.Producto.AgregarParametro("filtro", "");
			this.lsProducto = this.Producto.ConsultarProceso();
		}
	}

	public void EliminarProductoListaPrecio()
	{
		this.ProductoListaPrecio.ClearProceso();
		this.ProductoListaPrecio.ClearParametros();
		this.ProductoListaPrecio.TipoProceso = "SQL";
		this.ProductoListaPrecio.AgregarProceso("SQL.EliminarTodos");
		this.ProductoListaPrecio.EjecutarProceso();
	}

	public void GuardarProductoListaPrecio()
	{
		if (this.lsProductoListaPrecio != null)
		{
			for (int x = 0; x < this.lsProductoListaPrecio.size(); x++)
			{
				this.ProductoListaPrecio = ((BOProductoListaPrecio)this.lsProductoListaPrecio.get(x));
				this.ProductoListaPrecio.TipoProceso = "SQL";
				this.ProductoListaPrecio.AgregarProceso("SQL.Agregar");
				this.ProductoListaPrecio.EjecutarProceso();
			}
		}
	}

	public void ConsultarProductoListaPrecio(int TipoProceso, int Persistir, String IdListaPrecio, String PageNumber, String PageSize)
	{
		this.ProductoListaPrecio.ClearProceso();
		this.ProductoListaPrecio.ClearParametros();
		if (TipoProceso == 1)
		{
			this.ProductoListaPrecio.TipoProceso = "WS";
			this.ProductoListaPrecio.AgregarProceso("ConsultarListaPrecioAsignadaVendedor2");
			this.ProductoListaPrecio.AgregarParametro("wsNamesPace", WSNamespace);
			this.ProductoListaPrecio.AgregarParametro("wsUrl", wsURL);
			this.ProductoListaPrecio.AgregarParametro("WebMethod", "ConsultarListaPrecioAsignadaVendedor2");

			this.ProductoListaPrecio.AgregarParametro("IdListaPrecio", IdListaPrecio);
			this.ProductoListaPrecio.AgregarParametro("PageNumber", PageNumber);
			this.ProductoListaPrecio.AgregarParametro("PageSize", PageSize);
			this.ProductoListaPrecio.AgregarParametro("IdVendedor", String.valueOf(this.Vendedor.IdVendedor));

			this.ProductoListaPrecio.AgregarParametro("wsUrl", wsURL);
			this.lsProductoListaPrecio = this.ProductoListaPrecio.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsProductoListaPrecio != null)
				{
					for (int x = 0; x < this.lsProductoListaPrecio.size(); x++)
					{
						this.ProductoListaPrecio = ((BOProductoListaPrecio)this.lsProductoListaPrecio.get(x));
						this.ProductoListaPrecio.IdListaPrecio = Integer.parseInt(IdListaPrecio);
						this.ProductoListaPrecio.TipoProceso = "SQL";
						this.ProductoListaPrecio.AgregarProceso("SQL.Agregar");
						this.ProductoListaPrecio.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.ProductoListaPrecio.TipoProceso = "SQL";
			this.ProductoListaPrecio.AgregarProceso("SQL.Consultar");
			this.ProductoListaPrecio.getClass(); this.ProductoListaPrecio.AgregarParametro("idSortField", String.valueOf(3));
			this.ProductoListaPrecio.getClass(); this.ProductoListaPrecio.AgregarParametro("idFilterField", String.valueOf(3));
			this.ProductoListaPrecio.AgregarParametro("filtro", "");
			this.lsProductoListaPrecio = this.ProductoListaPrecio.ConsultarProceso();
		}
	}

	public void GuardarProductoListaPrecio(int IdListaPrecio)
	{
		if (this.lsProductoListaPrecio != null)
		{
			for (int x = 0; x < this.lsProductoListaPrecio.size(); x++)
			{
				this.ProductoListaPrecio = ((BOProductoListaPrecio)this.lsProductoListaPrecio.get(x));
				this.ProductoListaPrecio.IdListaPrecio = IdListaPrecio;
				this.ProductoListaPrecio.TipoProceso = "SQL";
				this.ProductoListaPrecio.AgregarProceso("SQL.Agregar");
				this.ProductoListaPrecio.EjecutarProceso();
			}
		}
	}

	public void EliminarListaPrecio()
	{
		this.ListaPrecio.ClearProceso();
		this.ListaPrecio.ClearParametros();
		this.ListaPrecio.TipoProceso = "SQL";
		this.ListaPrecio.AgregarProceso("SQL.EliminarTodos");
		this.ListaPrecio.EjecutarProceso();
	}

	public void ConsultarListaPrecio(int TipoProceso, int Persistir, String IdEmpresa)
	{
		this.ListaPrecio.ClearProceso();
		this.ListaPrecio.ClearParametros();
		if (TipoProceso == 1)
		{
			this.ListaPrecio.TipoProceso = "WS";
			this.ListaPrecio.AgregarProceso("ConsultarListaPrecio");
			this.ListaPrecio.AgregarParametro("wsNamesPace", WSNamespace);
			this.ListaPrecio.AgregarParametro("wsUrl", wsURL);
			this.ListaPrecio.AgregarParametro("WebMethod", "ConsultarListaPrecio");
			this.ListaPrecio.AgregarParametro("IdEmpresa", IdEmpresa);
			this.ListaPrecio.AgregarParametro("wsUrl", wsURL);
			this.lsListaPrecio = this.ListaPrecio.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsListaPrecio != null)
				{
					for (int x = 0; x < this.lsListaPrecio.size(); x++)
					{
						this.ListaPrecio = ((BOListaPrecio)this.lsListaPrecio.get(x));
						this.ListaPrecio.TipoProceso = "SQL";
						this.ListaPrecio.AgregarProceso("SQL.Agregar");
						this.ListaPrecio.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.ListaPrecio.TipoProceso = "SQL";
			this.ListaPrecio.AgregarProceso("SQL.Consultar");
			this.ListaPrecio.getClass(); this.ListaPrecio.AgregarParametro("idSortField", String.valueOf(1));
			this.ListaPrecio.getClass(); this.ListaPrecio.AgregarParametro("idFilterField", String.valueOf(1));
			this.ListaPrecio.AgregarParametro("filtro", "");
			this.lsListaPrecio = this.ListaPrecio.ConsultarProceso();
		}
	}

	public void GuardarListaPrecio()
	{
		if (this.lsListaPrecio != null)
		{
			for (int x = 0; x < this.lsListaPrecio.size(); x++)
			{
				this.ListaPrecio = ((BOListaPrecio)this.lsListaPrecio.get(x));
				this.ListaPrecio.TipoProceso = "SQL";
				this.ListaPrecio.AgregarProceso("SQL.Agregar");
				this.ListaPrecio.EjecutarProceso();
			}
		}
	}

	public void EliminarItinerario()
	{
		this.Itinerario.ClearProceso();
		this.Itinerario.ClearParametros();
		this.Itinerario.TipoProceso = "SQL";
		this.Itinerario.AgregarProceso("SQL.EliminarTodos");
		this.Itinerario.EjecutarProceso();
	}

	public void ConsultarItinerario(int TipoProceso, int Persistir, String IdVendedor)
	{
		this.Itinerario.ClearProceso();
		this.Itinerario.ClearParametros();
		if (TipoProceso == 1)
		{
			this.Itinerario.TipoProceso = "WS";
			this.Itinerario.AgregarProceso("ConsultarItinerario");
			this.Itinerario.AgregarParametro("wsNamesPace", WSNamespace);
			this.Itinerario.AgregarParametro("wsUrl", wsURL);
			this.Itinerario.AgregarParametro("WebMethod", "ConsultarItinerario");
			this.Itinerario.AgregarParametro("IdVendedor", IdVendedor);
			this.lsItinerario = this.Itinerario.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsItinerario != null)
				{
					for (int x = 0; x < this.lsItinerario.size(); x++)
					{
						this.Itinerario = ((BOItinerario)this.lsItinerario.get(x));
						this.Itinerario.TipoProceso = "SQL";
						this.Itinerario.AgregarProceso("SQL.Agregar");
						this.Itinerario.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.Itinerario.TipoProceso = "SQL";
			this.Itinerario.AgregarProceso("SQL.Consultar");
			this.Itinerario.getClass(); this.Itinerario.AgregarParametro("idSortField", String.valueOf(2));
			this.Itinerario.getClass(); this.Itinerario.AgregarParametro("idFilterField", String.valueOf(2));
			this.Itinerario.AgregarParametro("filtro", "");
			this.lsItinerario = this.Itinerario.ConsultarProceso();
		}
	}

	public void EliminarParametrosVendedor()
	{
		this.ParametrosVendedor.ClearProceso();
		this.ParametrosVendedor.ClearParametros();
		this.ParametrosVendedor.TipoProceso = "SQL";
		this.ParametrosVendedor.AgregarProceso("SQL.EliminarTodos");
		this.ParametrosVendedor.EjecutarProceso();
	}

	public void ConsultarParametrosVendedor(int TipoProceso, int Persistir, String IdVendedor)
	{
		this.ParametrosVendedor.ClearProceso();
		this.ParametrosVendedor.ClearParametros();
		if (TipoProceso == 1)
		{
			this.ParametrosVendedor.TipoProceso = "WS";
			this.ParametrosVendedor.AgregarProceso("ConsultarParametros");
			this.ParametrosVendedor.AgregarParametro("wsNamesPace", WSNamespace);
			this.ParametrosVendedor.AgregarParametro("wsUrl", wsURL);
			this.ParametrosVendedor.AgregarParametro("WebMethod", "ConsultarParametros");
			this.ParametrosVendedor.AgregarParametro("IdVendedor", IdVendedor);
			this.lsParametrosVendedor = this.ParametrosVendedor.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsParametrosVendedor != null)
				{
					for (int x = 0; x < this.lsParametrosVendedor.size(); x++)
					{
						this.ParametrosVendedor = ((BOParametrosVendedor)this.lsParametrosVendedor.get(x));
						this.ParametrosVendedor.TipoProceso = "SQL";
						this.ParametrosVendedor.AgregarProceso("SQL.Agregar");
						this.ParametrosVendedor.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.ParametrosVendedor.TipoProceso = "SQL";
			this.ParametrosVendedor.AgregarProceso("SQL.Consultar");
			this.ParametrosVendedor.getClass(); this.ParametrosVendedor.AgregarParametro("idSortField", String.valueOf(1));
			this.ParametrosVendedor.getClass(); this.ParametrosVendedor.AgregarParametro("idFilterField", String.valueOf(1));
			this.ParametrosVendedor.AgregarParametro("filtro", "");
			this.lsParametrosVendedor = this.ParametrosVendedor.ConsultarProceso();
		}
	}

	public void EliminarPrecuestionario()
	{
		this.Precuestionario.ClearProceso();
		this.Precuestionario.ClearParametros();
		this.Precuestionario.TipoProceso = "SQL";
		this.Precuestionario.AgregarProceso("SQL.EliminarTodos");
		this.Precuestionario.EjecutarProceso();
	}

	public void ConsultarPrecuestionario(int TipoProceso, int Persistir)
	{
		this.Precuestionario.ClearProceso();
		this.Precuestionario.ClearParametros();
		if (TipoProceso == 1)
		{
			this.Precuestionario.TipoProceso = "WS";
			this.Precuestionario.AgregarProceso("ConsultarPreCuestionario");
			this.Precuestionario.AgregarParametro("wsNamesPace", WSNamespace);
			this.Precuestionario.AgregarParametro("wsUrl", wsURL);
			this.Precuestionario.AgregarParametro("WebMethod", "ConsultarPreCuestionario");
			this.Precuestionario.AgregarParametro("IdEmpresa", String.valueOf(this.Vendedor.IdEmpresa));
			this.lsPrecuestionario = this.Precuestionario.ConsultarProceso();
			if (Persistir == 3)
			{
				if (this.lsPrecuestionario != null)
				{
					for (int x = 0; x < this.lsPrecuestionario.size(); x++)
					{
						this.Precuestionario = ((BOPrecuestionario)this.lsPrecuestionario.get(x));
						this.Precuestionario.TipoProceso = "SQL";
						this.Precuestionario.AgregarProceso("SQL.Agregar");
						this.Precuestionario.EjecutarProceso();
					}
				}
			}
		}
		else if (TipoProceso == 2)
		{
			this.Precuestionario.TipoProceso = "SQL";
			this.Precuestionario.AgregarProceso("SQL.Consultar");
			this.Precuestionario.getClass(); this.Precuestionario.AgregarParametro("idSortField", String.valueOf(1));
			this.Precuestionario.getClass(); this.Precuestionario.AgregarParametro("idFilterField", String.valueOf(2));
			this.Precuestionario.AgregarParametro("filtro", "0");
			this.lsPrecuestionario = this.Precuestionario.ConsultarProceso();
		}
	}

	public void GuardarPrecuestionario()
	{
		if (this.lsPrecuestionario != null)
		{
			for (int x = 0; x < this.lsPrecuestionario.size(); x++)
			{
				this.Precuestionario = ((BOPrecuestionario)this.lsPrecuestionario.get(x));
				this.Precuestionario.TipoProceso = "SQL";
				this.Precuestionario.AgregarProceso("SQL.Agregar");
				this.Precuestionario.EjecutarProceso();
			}
		}
	}

	public void DepurarNotas()
	{
		BONota VentaLocal = new BONota(context);
		BONotaDetalle VentaDetalleLocal = new BONotaDetalle(context);

		VentaLocal.ClearProceso();
		VentaLocal.ClearParametros();

		VentaLocal.TipoProceso = "SQL";
		VentaLocal.AgregarProceso("SQL.Consultar");
		VentaLocal.getClass(); VentaLocal.AgregarParametro("idSortField", String.valueOf(30));
		VentaLocal.getClass(); VentaLocal.AgregarParametro("idFilterField", String.valueOf(36));
		VentaLocal.AgregarParametro("filtro", "1");

		List<BO> lsVentaLocal = VentaLocal.ConsultarProceso();

		if (lsVentaLocal != null)
		{
			for (int x = 0; x < lsVentaLocal.size(); x++)
			{
				VentaLocal = (BONota)lsVentaLocal.get(x);

				String fechadia = Utils.getFechaActual_DD_MM_YYYY();
				if (VentaLocal.Fecha.equals(fechadia)) {
					continue;
				}
				VentaDetalleLocal.ClearProceso();
				VentaDetalleLocal.ClearParametros();

				VentaDetalleLocal.TipoProceso = "SQL";
				VentaDetalleLocal.AgregarProceso("SQL.Consultar");
				VentaDetalleLocal.getClass(); VentaDetalleLocal.AgregarParametro("idSortField", String.valueOf(16));
				VentaDetalleLocal.getClass(); VentaDetalleLocal.AgregarParametro("idFilterField", String.valueOf(23));
				VentaDetalleLocal.AgregarParametro("filtro", VentaLocal.GUIDV);

				List<BO> lsVentaDetalleLocal = VentaDetalleLocal.ConsultarProceso();

				if (lsVentaDetalleLocal != null)
				{
					for (int x2 = 0; x2 < lsVentaDetalleLocal.size(); x2++)
					{
						VentaDetalleLocal = (BONotaDetalle)lsVentaDetalleLocal.get(x2);

						VentaDetalleLocal.ClearProceso();
						VentaDetalleLocal.ClearParametros();

						VentaDetalleLocal.TipoProceso = "SQL";
						VentaDetalleLocal.AgregarProceso("SQL.Eliminar");
						VentaDetalleLocal.EjecutarProceso();
					}

				}

				VentaLocal.ClearProceso();
				VentaLocal.ClearParametros();

				VentaLocal.TipoProceso = "SQL";
				VentaLocal.AgregarProceso("SQL.Eliminar");
				VentaLocal.EjecutarProceso();
			}
		}
	}

	public void DepurarLog()
	{
		ConsultarLog();
		if (this.lsDispositivo != null)
		{
			for (int x = 0; x < this.lsDispositivo.size(); x++)
			{
				this.Dispositivo = ((BODispositivo)this.lsDispositivo.get(x));

				if (this.Dispositivo.Fecha.equals(Utils.getFechaActual_DD_MM_YYYY()))
					continue;
				this.Dispositivo.TipoProceso = "SQL";
				this.Dispositivo.AgregarProceso("SQL.Eliminar");
				this.Dispositivo.EjecutarProceso();
			}
		}
	}

	public void ObtenerParametros()
	{
		if (this.lsParametrosVendedor != null)
		{
			for (int y = 0; y < this.lsParametrosVendedor.size(); y++)
			{
				this.ParametrosVendedor = ((BOParametrosVendedor)this.lsParametrosVendedor.get(y));
				if (this.ParametrosVendedor.Parametro.equalsIgnoreCase("cliente"))
				{
					if (this.ParametrosVendedor.Valor.equalsIgnoreCase("1"))
					{
						this.Conf_Cliente = true;
					}
					else
					{
						this.Conf_Cliente = false;
					}
				}
				else if (this.ParametrosVendedor.Parametro.equalsIgnoreCase("itinerario"))
				{
					if (this.ParametrosVendedor.Valor.equalsIgnoreCase("1"))
					{
						this.Conf_Itinerario = true;
					}
					else
					{
						this.Conf_Itinerario = false;
					}
				}
				else if (this.ParametrosVendedor.Parametro.equalsIgnoreCase("almancenmovil"))
				{
					if (this.ParametrosVendedor.Valor.equalsIgnoreCase("1"))
					{
						this.Conf_AlmancenMovil = true;
					}
					else
					{
						this.Conf_AlmancenMovil = false;
					}
				}
				else if (this.ParametrosVendedor.Parametro.equalsIgnoreCase("listaprecio"))
				{
					if (this.ParametrosVendedor.Valor.equalsIgnoreCase("1"))
					{
						this.Conf_ListaPrecio = true;
					}
					else
					{
						this.Conf_ListaPrecio = false;
					}
				}

				if (this.ParametrosVendedor.Parametro.equalsIgnoreCase("cheque"))
				{
					if (this.ParametrosVendedor.Valor.equalsIgnoreCase("1"))
					{
						this.Conf_Cheque = true;
					}
					else
					{
						this.Conf_Cheque = false;
					}
				}

				if (!this.ParametrosVendedor.Parametro.equalsIgnoreCase("credito"))
					continue;
				if (this.ParametrosVendedor.Valor.equalsIgnoreCase("1"))
				{
					this.Conf_Credito = true;
				}
				else
				{
					this.Conf_Credito = false;
				}
			}
		}
	}

	public void ConsultarDetalleVentasDiaria(String GUIDV)
	{
		this.VentaDetalle.ClearProceso();
		this.VentaDetalle.ClearParametros();
		this.VentaDetalle.TipoProceso = "SQL";
		this.VentaDetalle.AgregarProceso("SQL.Consultar");
		this.VentaDetalle.getClass(); this.VentaDetalle.AgregarParametro("idSortField", String.valueOf(2));
		this.VentaDetalle.getClass(); this.VentaDetalle.AgregarParametro("idFilterField", String.valueOf(23));
		this.VentaDetalle.AgregarParametro("filtro", GUIDV);
		this.lsVentaDetalle = this.VentaDetalle.ConsultarProceso();
	}

	public void ConsultarOperacionesDiarias(String Discriminante)
	{
		this.Venta.ClearProceso();
		this.Venta.ClearParametros();
		this.Venta.TipoProceso = "SQL";
		this.Venta.AgregarProceso("SQL.Consultar");
		this.Venta.getClass(); this.Venta.AgregarParametro("idSortField", String.valueOf(5));
		this.Venta.getClass(); this.Venta.AgregarParametro("idFilterField", String.valueOf(42));

		this.Venta.AgregarParametro("filtro", Utils.getFechaActual_DD_MM_YYYY());
		//	     this.Venta.AgregarParametro("filtro", "");

		this.lsVenta = this.Venta.ConsultarProceso();
	}

	public void ConsultarOperacionesDiarias2(String Discriminante)
	{
		this.Venta.ClearProceso();
		this.Venta.ClearParametros();
		this.Venta.TipoProceso = "SQL";
		this.Venta.AgregarProceso("SQL.Consultar");
		this.Venta.getClass(); this.Venta.AgregarParametro("idSortField", String.valueOf(5));
		this.Venta.getClass(); this.Venta.AgregarParametro("idFilterField", String.valueOf(42));

		this.Venta.AgregarParametro("filtro", Utils.getFechaActual_DD_MM_YYYY());

		this.lsVenta2 = this.Venta.ConsultarProceso();
	}	

	public void ConsultarOperacionesCancelacion()
	{
		this.Venta.ClearProceso();
		this.Venta.ClearParametros();
		this.Venta.TipoProceso = "SQL";
		this.Venta.AgregarProceso("SQL.Consultar");
		this.Venta.getClass(); this.Venta.AgregarParametro("idSortField", String.valueOf(5));
		this.Venta.getClass(); this.Venta.AgregarParametro("idFilterField", String.valueOf(56));
		this.Venta.AgregarParametro("filtro", "1");
		this.lsVenta = this.Venta.ConsultarProceso();
	}

	public void EnviarPrecuestionarioCliente()
	{
		this.PrecuestionarioCliente.ClearProceso();
		this.PrecuestionarioCliente.ClearParametros();

		this.PrecuestionarioCliente.TipoProceso = "SQL";
		this.PrecuestionarioCliente.AgregarProceso("SQL.Consultar");
		this.PrecuestionarioCliente.getClass(); this.PrecuestionarioCliente.AgregarParametro("idSortField", String.valueOf(3));
		this.PrecuestionarioCliente.getClass(); this.PrecuestionarioCliente.AgregarParametro("idFilterField", String.valueOf(3));
		this.PrecuestionarioCliente.AgregarParametro("filtro", "");

		this.lsPrecuestionarioCliente = this.PrecuestionarioCliente.ConsultarProceso();

		if (this.lsPrecuestionarioCliente != null)
		{
			for (int x = 0; x < this.lsPrecuestionarioCliente.size(); x++)
			{
				if (!Info.ServicioDatosActivos(context))
					continue;
				this.PrecuestionarioCliente = ((BOPrecuestionarioCliente)this.lsPrecuestionarioCliente.get(x));

				this.PrecuestionarioCliente.ClearProceso();
				this.PrecuestionarioCliente.ClearParametros();

				this.PrecuestionarioCliente.TipoProceso = "WS";
				this.PrecuestionarioCliente.AgregarProceso("AgregarCliente");
				this.PrecuestionarioCliente.AgregarParametro("wsNamesPace", WSNamespace);
				this.PrecuestionarioCliente.AgregarParametro("wsUrl", wsURL);

				this.PrecuestionarioCliente.AgregarParametro("WebMethod", "ActualizarEstadoCliente");

				this.PrecuestionarioCliente.AgregarParametro("Estado", this.PrecuestionarioCliente.Pregunta);
				this.PrecuestionarioCliente.AgregarParametro("IdCliente", String.valueOf(this.PrecuestionarioCliente.IdCliente));
				this.PrecuestionarioCliente.AgregarParametro("IdVendedor", String.valueOf(this.PrecuestionarioCliente.IdVendedor));

				this.PrecuestionarioCliente.EjecutarProceso();

				if (!this.PrecuestionarioCliente.Error.equals(""))
					continue;

				this.PrecuestionarioCliente.ClearProceso();
				this.PrecuestionarioCliente.ClearParametros();

				this.PrecuestionarioCliente.TipoProceso = "SQL";
				this.PrecuestionarioCliente.AgregarProceso("SQL.Eliminar");
				this.PrecuestionarioCliente.EjecutarProceso();
			}

		}

		//	     Utils.setTrace("Precuestionario enviado");
	}

	public void EnviarPreregistro()
	{
		this.PrecuestionarioCliente.ClearProceso();
		this.PrecuestionarioCliente.ClearParametros();

		this.PrecuestionarioCliente.TipoProceso = "SQL";
		this.PrecuestionarioCliente.AgregarProceso("SQL.Consultar");
		this.PrecuestionarioCliente.getClass(); this.PrecuestionarioCliente.AgregarParametro("idSortField", String.valueOf(3));
		this.PrecuestionarioCliente.getClass(); this.PrecuestionarioCliente.AgregarParametro("idFilterField", String.valueOf(3));
		this.PrecuestionarioCliente.AgregarParametro("filtro", "");

		this.lsPrecuestionarioCliente = this.PrecuestionarioCliente.ConsultarProceso();

		if (this.lsPrecuestionarioCliente != null)
		{
			for (int x = 0; x < this.lsPrecuestionarioCliente.size(); x++)
			{
				this.PrecuestionarioCliente = ((BOPrecuestionarioCliente)this.lsPrecuestionarioCliente.get(x));

				this.PrecuestionarioCliente.ClearProceso();
				this.PrecuestionarioCliente.ClearParametros();

				this.PrecuestionarioCliente.TipoProceso = "WS";
				this.PrecuestionarioCliente.AgregarProceso("AgregarCliente");
				this.PrecuestionarioCliente.AgregarParametro("wsNamesPace", WSNamespace);
				this.PrecuestionarioCliente.AgregarParametro("wsUrl", wsURL);

				this.PrecuestionarioCliente.AgregarParametro("WebMethod", "ActualizarEstadoCliente");

				this.PrecuestionarioCliente.AgregarParametro("Estado", this.PrecuestionarioCliente.Pregunta);
				this.PrecuestionarioCliente.AgregarParametro("IdCliente", String.valueOf(this.PrecuestionarioCliente.IdCliente));
				this.PrecuestionarioCliente.AgregarParametro("IdVendedor", String.valueOf(this.PrecuestionarioCliente.IdVendedor));

				this.PrecuestionarioCliente.EjecutarProceso();

				if (!this.PrecuestionarioCliente.Error.equals(""))
					continue;
				this.PrecuestionarioCliente.ClearProceso();
				this.PrecuestionarioCliente.ClearParametros();

				this.PrecuestionarioCliente.TipoProceso = "SQL";
				this.PrecuestionarioCliente.AgregarProceso("SQL.Eliminar");
			}
		}
	}

	public void EnviarDatosCliente()
	{
		try
		{
			BONuevoInformacionCliente NuevoInformacionCliente = new BONuevoInformacionCliente(context);

			NuevoInformacionCliente.ClearProceso();
			NuevoInformacionCliente.ClearParametros();
			NuevoInformacionCliente.TipoProceso = "SQL";
			NuevoInformacionCliente.AgregarProceso("SQL.Consultar");
			NuevoInformacionCliente.AgregarParametro("idSortField", String.valueOf(NuevoInformacionCliente.IdCliente));
			NuevoInformacionCliente.getClass(); NuevoInformacionCliente.AgregarParametro("idFilterField", String.valueOf(5));
			NuevoInformacionCliente.AgregarParametro("filtro", "");
			List<BO> lsNuevoInformacionCliente = NuevoInformacionCliente.ConsultarProceso();

			if (lsNuevoInformacionCliente != null)
			{
				for (int w = 0; w < lsNuevoInformacionCliente.size(); w++)
				{
					NuevoInformacionCliente = (BONuevoInformacionCliente)lsNuevoInformacionCliente.get(w);
					if(NuevoInformacionCliente.IdCliente > 0)
					{

						NuevoInformacionCliente.TipoProceso = "WS";
						NuevoInformacionCliente.AgregarProceso("AgregarInformacionCliente");

						NuevoInformacionCliente.AgregarParametro("wsNamesPace", WSNamespace);
						NuevoInformacionCliente.AgregarParametro("wsUrl", wsURL);

						NuevoInformacionCliente.AgregarParametro("WebMethod", "AgregarInformacionCliente");

						NuevoInformacionCliente.AgregarParametro("IdCuestionarioCliente", String.valueOf(NuevoInformacionCliente.IdCuestionarioCliente));
						NuevoInformacionCliente.AgregarParametro("IdCliente", String.valueOf(NuevoInformacionCliente.IdCliente));
						NuevoInformacionCliente.AgregarParametro("Respuesta", NuevoInformacionCliente.Respuesta);

						NuevoInformacionCliente.EjecutarProceso();

						NuevoInformacionCliente.ClearProceso();
						NuevoInformacionCliente.ClearParametros();
						NuevoInformacionCliente.TipoProceso = "SQL";
						NuevoInformacionCliente.AgregarProceso("SQL.Eliminar");
						NuevoInformacionCliente.EjecutarProceso();
					}
				}
			}
		}
		catch (Exception localException2)
		{
		}
	}

	public void EnviarNuevoCliente()
	{
		BONuevoCliente NuevoClienteEnvio = new BONuevoCliente(context);

		NuevoClienteEnvio.TipoProceso = "SQL";
		NuevoClienteEnvio.AgregarProceso("SQL.Consultar");
		NuevoClienteEnvio.getClass(); NuevoClienteEnvio.AgregarParametro("idSortField", String.valueOf(6));
		NuevoClienteEnvio.getClass(); NuevoClienteEnvio.AgregarParametro("idFilterField", String.valueOf(6));
		NuevoClienteEnvio.AgregarParametro("filtro", "");

		List<BO> lsNuevoClienteEnvio = NuevoClienteEnvio.ConsultarProceso();

		if (lsNuevoClienteEnvio != null)
		{
			for (int x = 0; x < lsNuevoClienteEnvio.size(); x++)
			{
				if (!Info.ServicioDatosActivos(context))
					continue;

				NuevoClienteEnvio = (BONuevoCliente)lsNuevoClienteEnvio.get(x);

				if (NuevoClienteEnvio.IdCliente == 0)
				{
					SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(110);
					SoapObject soapObject = new SoapObject(WSNamespace, "AgregarCliente");

					soapObject.addProperty("IdEmpresa", String.valueOf(NuevoClienteEnvio.IdEmpresa));
					soapObject.addProperty("IdCategoriaCliente", String.valueOf(NuevoClienteEnvio.IdCategoriaCliente));
					soapObject.addProperty("IdRuta", String.valueOf(NuevoClienteEnvio.IdRuta));

					soapObject.addProperty("EmpresaCliente", NuevoClienteEnvio.Empresa);
					soapObject.addProperty("Nombre", NuevoClienteEnvio.Nombre);
					soapObject.addProperty("Calle", NuevoClienteEnvio.Calle);
					soapObject.addProperty("Numero", NuevoClienteEnvio.Numero);

					soapObject.addProperty("Colonia", NuevoClienteEnvio.Colonia);
					soapObject.addProperty("Cruzamientos", NuevoClienteEnvio.Cruzamientos);
					soapObject.addProperty("EMail", NuevoClienteEnvio.EMail);
					soapObject.addProperty("Telefono", NuevoClienteEnvio.Telefono);
					soapObject.addProperty("Celular", NuevoClienteEnvio.Celular);

					soapObject.addProperty("Contacto", NuevoClienteEnvio.Contacto);
					soapObject.addProperty("PosicionGps", NuevoClienteEnvio.GPSDispositivo);
					soapObject.addProperty("GUIDM", NuevoClienteEnvio.GUIDV);

					soapEnvelope.setOutputSoapObject(soapObject);
					soapEnvelope.bodyOut = soapObject;
					soapEnvelope.dotNet = true;
					soapEnvelope.encodingStyle = "http://www.w3.org/2001/XMLSchema";

					HttpTransportSE transport = new HttpTransportSE(wsURL);
					try
					{
						try
						{
							Logg.info("AgregarCliente, IdEmpresa " + String.valueOf(NuevoClienteEnvio.IdEmpresa) + " IdCategoriaCliente" + 
									String.valueOf(NuevoClienteEnvio.IdCategoriaCliente) + " IdRuta" + String.valueOf(NuevoClienteEnvio.IdRuta) + 
									" Empresa" + NuevoClienteEnvio.Empresa + " Nombre" + NuevoClienteEnvio.Nombre + 
									" Calle" + NuevoClienteEnvio.Calle + " Numero" + NuevoClienteEnvio.Numero + 
									" Colonia" + NuevoClienteEnvio.Colonia + "Cruzamientos" + NuevoClienteEnvio.Cruzamientos + 
									" EMail" + NuevoClienteEnvio.EMail + " Telefono" + NuevoClienteEnvio.Telefono + 
									" Celular" + NuevoClienteEnvio.Celular + " Contacto" + NuevoClienteEnvio.Contacto + 
									" PosicionGps" + NuevoClienteEnvio.GPSDispositivo + " GUIDM" + NuevoClienteEnvio.GUIDV);

							soapObject.addProperty("IdEmpresa", String.valueOf(NuevoClienteEnvio.IdEmpresa));
							soapObject.addProperty("IdCategoriaCliente", String.valueOf(NuevoClienteEnvio.IdCategoriaCliente));
							soapObject.addProperty("IdRuta", String.valueOf(NuevoClienteEnvio.IdRuta));
							soapObject.addProperty("EmpresaCliente", NuevoClienteEnvio.Empresa.replace('\n', ' '));
							soapObject.addProperty("Nombre", NuevoClienteEnvio.Nombre.replace('\n', ' '));
							soapObject.addProperty("Calle", NuevoClienteEnvio.Calle.replace('\n', ' '));
							soapObject.addProperty("Numero", NuevoClienteEnvio.Numero.replace('\n', ' '));
							soapObject.addProperty("Colonia", NuevoClienteEnvio.Colonia.replace('\n', ' '));
							soapObject.addProperty("Cruzamientos", NuevoClienteEnvio.Cruzamientos.replace('\n', ' '));
							soapObject.addProperty("EMail", NuevoClienteEnvio.EMail.replace('\n', ' '));
							soapObject.addProperty("Telefono", NuevoClienteEnvio.Telefono.replace('\n', ' '));
							soapObject.addProperty("Celular", NuevoClienteEnvio.Celular.replace('\n', ' '));
							soapObject.addProperty("Contacto", NuevoClienteEnvio.Contacto.replace('\n', ' '));
							soapObject.addProperty("PosicionGps", NuevoClienteEnvio.GPSDispositivo.replace('\n', ' '));
							soapObject.addProperty("GUIDM", NuevoClienteEnvio.GUIDV.replace('\n', ' '));
						}
						catch (Exception localException1)
						{
						}

						transport.call(WSNamespace + "AgregarCliente", soapEnvelope);

						SoapPrimitive respuesta = (SoapPrimitive)soapEnvelope.getResponse();

						NuevoClienteEnvio.IdCliente = Integer.parseInt(respuesta.toString());

						NuevoClienteEnvio.ClearProceso();
						NuevoClienteEnvio.ClearParametros();

						NuevoClienteEnvio.TipoProceso = "SQL";
						NuevoClienteEnvio.AgregarProceso("SQL.Actualizar");
						NuevoClienteEnvio.EjecutarProceso();

						Logg.info("Cliente enviado " + NuevoClienteEnvio.Nombre);

						try
						{
							BONuevoInformacionCliente NuevoInformacionCliente = new BONuevoInformacionCliente(context);

							NuevoInformacionCliente.ClearProceso();
							NuevoInformacionCliente.ClearParametros();
							NuevoInformacionCliente.TipoProceso = "SQL";
							NuevoInformacionCliente.AgregarProceso("SQL.Consultar");
							NuevoInformacionCliente.AgregarParametro("idSortField", String.valueOf(NuevoInformacionCliente.IdCliente));
							NuevoInformacionCliente.getClass(); NuevoInformacionCliente.AgregarParametro("idFilterField", String.valueOf(5));
							NuevoInformacionCliente.AgregarParametro("filtro", NuevoClienteEnvio.GUIDV);
							List<BO> lsNuevoInformacionCliente = NuevoInformacionCliente.ConsultarProceso();

							if (lsNuevoInformacionCliente != null)
							{
								for (int w = 0; w < lsNuevoInformacionCliente.size(); w++)
								{
									NuevoInformacionCliente = (BONuevoInformacionCliente)lsNuevoInformacionCliente.get(w);

									NuevoInformacionCliente.TipoProceso = "WS";
									NuevoInformacionCliente.AgregarProceso("AgregarInformacionCliente");

									NuevoInformacionCliente.AgregarParametro("wsNamesPace", WSNamespace);
									NuevoInformacionCliente.AgregarParametro("wsUrl", wsURL);

									NuevoInformacionCliente.AgregarParametro("WebMethod", "AgregarInformacionCliente");

									NuevoInformacionCliente.AgregarParametro("IdCuestionarioCliente", String.valueOf(NuevoInformacionCliente.IdCuestionarioCliente));
									NuevoInformacionCliente.AgregarParametro("IdCliente", String.valueOf(NuevoClienteEnvio.IdCliente));
									NuevoInformacionCliente.AgregarParametro("Respuesta", NuevoInformacionCliente.Respuesta);

									NuevoInformacionCliente.EjecutarProceso();

									NuevoInformacionCliente.ClearProceso();
									NuevoInformacionCliente.ClearParametros();
									NuevoInformacionCliente.TipoProceso = "SQL";
									NuevoInformacionCliente.AgregarProceso("SQL.Eliminar");
									NuevoInformacionCliente.EjecutarProceso();
								}
							}
						}
						catch (Exception localException2)
						{
						}
					}
					catch (Exception e) {
						String Error = e.getMessage();
						Logg.info("Error al enviar el cliente: " + e.toString() + " Message: " + Error);
					}
				}

				if (NuevoClienteEnvio.IdCliente <= 0)
				{
					continue;
				}
				BONota VentaLocal = new BONota(context);

				VentaLocal.ClearProceso();
				VentaLocal.ClearParametros();

				VentaLocal.TipoProceso = "SQL";
				VentaLocal.AgregarProceso("SQL.Consultar");
				VentaLocal.getClass(); VentaLocal.AgregarParametro("idSortField", String.valueOf(30));
				VentaLocal.getClass(); VentaLocal.AgregarParametro("idFilterField", String.valueOf(36));
				VentaLocal.AgregarParametro("filtro", "0");
				List<BO> lsVentaLocal = VentaLocal.ConsultarProceso();

				if (lsVentaLocal != null)
				{
					for (int x4 = 0; x4 < lsVentaLocal.size(); x4++)
					{
						VentaLocal = (BONota)lsVentaLocal.get(x4);
						if (!VentaLocal.GUIDV.equals(NuevoClienteEnvio.GUIDV))
							continue;
						VentaLocal.IdCliente = NuevoClienteEnvio.IdCliente;

						VentaLocal.ClearProceso();
						VentaLocal.ClearParametros();

						VentaLocal.TipoProceso = "SQL";
						VentaLocal.AgregarProceso("SQL.Actualizar");
						VentaLocal.EjecutarProceso();
					}

				}

				try
				{
					BOFoto Foto = new BOFoto(context);
					Foto.TipoProceso = "SQL";
					Foto.AgregarProceso("SQL.Consultar");
					Foto.getClass(); Foto.AgregarParametro("idSortField", String.valueOf(6));
					Foto.getClass(); Foto.AgregarParametro("idFilterField", String.valueOf(6));
					Foto.AgregarParametro("filtro", "");
					List<BO> lsFoto = Foto.ConsultarProceso();

					if (lsFoto != null)
					{
						for (int x5 = 0; x5 < lsFoto.size(); x5++)
						{
							Foto = (BOFoto)lsFoto.get(x5);

							if (!Foto.GUIDM.equals(NuevoClienteEnvio.GUIDV))
								continue;
							Foto.IdVehiculo = NuevoClienteEnvio.IdCliente;

							Foto.ClearProceso();
							Foto.ClearParametros();

							Foto.TipoProceso = "SQL";
							Foto.AgregarProceso("SQL.Actualizar");
							Foto.EjecutarProceso();
						}
					}
				}
				catch (Exception localException3)
				{
				}

				NuevoClienteEnvio.ClearProceso();
				NuevoClienteEnvio.ClearParametros();

				NuevoClienteEnvio.TipoProceso = "SQL";
				NuevoClienteEnvio.AgregarProceso("SQL.Eliminar");
				NuevoClienteEnvio.EjecutarProceso();
			}
		}
	}

	public void EnviarPosicion(int IdCliente, String coordenada)
	{
		this.ClienteObtenido.IdCliente = IdCliente;
		this.ClienteObtenido.GPS = coordenada;

		this.ClienteObtenido.ClearProceso();
		this.ClienteObtenido.ClearParametros();
		this.ClienteObtenido.TipoProceso = "SQL";
		this.ClienteObtenido.AgregarProceso("SQL.Agregar");
		this.ClienteObtenido.EjecutarProceso();

		EnviarPosicionPendiente();
	}

	public void ConsultarClienteObtenido()
	{
		this.ClienteObtenido.ClearProceso();
		this.ClienteObtenido.ClearParametros();

		this.ClienteObtenido.TipoProceso = "SQL";
		this.ClienteObtenido.AgregarProceso("SQL.Consultar");
		this.ClienteObtenido.getClass(); this.ClienteObtenido.AgregarParametro("idSortField", String.valueOf(1));
		this.ClienteObtenido.getClass(); this.ClienteObtenido.AgregarParametro("idFilterField", String.valueOf(1));
		this.ClienteObtenido.AgregarParametro("filtro", "");
		this.lsClienteObtenido = this.ClienteObtenido.ConsultarProceso();
	}

	public void EnviarPosicionPendiente()
	{
		ConsultarClienteObtenido();
		if (this.lsClienteObtenido != null)
		{
			for (int x = 0; x < this.lsClienteObtenido.size(); x++)
			{
				if (!Info.ServicioDatosActivos(context))
					continue;
				this.ClienteObtenido = ((BOClienteObtenido)this.lsClienteObtenido.get(x));

				this.ClienteObtenido.TipoProceso = "WS";
				this.ClienteObtenido.AgregarProceso("ActualizarGPSCliente");

				this.ClienteObtenido.AgregarParametro("wsNamesPace", WSNamespace);
				this.ClienteObtenido.AgregarParametro("wsUrl", wsURL);

				this.ClienteObtenido.AgregarParametro("WebMethod", "ActualizarGPSCliente");

				this.ClienteObtenido.AgregarParametro("IdCliente", String.valueOf(this.ClienteObtenido.IdCliente));
				this.ClienteObtenido.AgregarParametro("gps", this.ClienteObtenido.GPS);

				this.ClienteObtenido.EjecutarProceso();

				if (!this.ClienteObtenido.Error.equals(""))
					continue;
				this.ClienteObtenido.ClearProceso();
				this.ClienteObtenido.ClearParametros();
				this.ClienteObtenido.TipoProceso = "SQL";
				this.ClienteObtenido.AgregarProceso("SQL.Eliminar");
				this.ClienteObtenido.EjecutarProceso();
			}
		}
	}

	public void EnviarVenta()
	{
		BODevolucionEmbalaje Embalaje = new BODevolucionEmbalaje(context);
		List<BO> lsEmbalaje = (List<BO>)null;

		BONota VentaLocal = new BONota(context);
		BONotaDetalle VentaDetalleLocal = new BONotaDetalle(context);

		VentaLocal.ClearProceso();
		VentaLocal.ClearParametros();

		VentaLocal.TipoProceso = "SQL";
		VentaLocal.AgregarProceso("SQL.Consultar");
		VentaLocal.getClass(); VentaLocal.AgregarParametro("idSortField", String.valueOf(30));
		VentaLocal.getClass(); VentaLocal.AgregarParametro("idFilterField", String.valueOf(36));
		VentaLocal.AgregarParametro("filtro", "0");
		List<BO> lsVentaLocal = VentaLocal.ConsultarProceso();

		if (lsVentaLocal != null)
		{
			Logg.info("Ventas pendientes: " + lsVentaLocal.size());

			for (int x = 0; x < lsVentaLocal.size(); x++)
			{
				VentaLocal = (BONota)lsVentaLocal.get(x);

				if (VentaLocal.IdCliente <= 0)
					continue;
				if (!Info.ServicioDatosActivos(context))
					continue;
				if (VentaLocal.Enviado == 0)
				{
					SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(110);

					SoapObject soapObject;
					if(VentaLocal.Discriminante.equals("Entrega"))
					{
						soapObject = new SoapObject(WSNamespace, "AgregarEntrega");
					}
					else
					{
						soapObject = new SoapObject(WSNamespace, "AgregarNota2");
					}

					soapObject.addProperty("idItinerio", String.valueOf(VentaLocal.IdItinerario));
					soapObject.addProperty("idcliente", String.valueOf(VentaLocal.IdCliente));
					soapObject.addProperty("idvendedor", String.valueOf(VentaLocal.IdVendedor));
					soapObject.addProperty("idlistaprecio", String.valueOf(VentaLocal.IdListaPrecio));
					soapObject.addProperty("fecha", VentaLocal.FechaCreacion);
					soapObject.addProperty("totalventa", String.valueOf(VentaLocal.Total));

					soapObject.addProperty("PagoEfectivo", String.valueOf(VentaLocal.PagoEfectivo));
					soapObject.addProperty("PagoCheque", String.valueOf(VentaLocal.PagoCheque));
					soapObject.addProperty("PagoDeposito", String.valueOf(VentaLocal.PagoDeposito));
					soapObject.addProperty("PagoTransferencia", String.valueOf(VentaLocal.PagoTransferencia));

					soapObject.addProperty("NombreImpreso", VentaLocal.NombreImpreso);
					soapObject.addProperty("FechaCheque", VentaLocal.FechaCheque);
					soapObject.addProperty("Banco", VentaLocal.Banco);
					soapObject.addProperty("ChequeNo", VentaLocal.ChequeNo);

					soapObject.addProperty("DiscriminantePago", VentaLocal.DiscriminantePago);

					soapObject.addProperty("SaldoAnterior", String.valueOf(VentaLocal.SaldoAnterior));
					soapObject.addProperty("PagoCredito", String.valueOf(VentaLocal.Abono));
					soapObject.addProperty("NuevoSaldo", String.valueOf(VentaLocal.NuevoSaldo));

					soapObject.addProperty("credito", String.valueOf(VentaLocal.CreditoMonto));

					soapObject.addProperty("observacion", VentaLocal.Observacion);
					soapObject.addProperty("discriminante", VentaLocal.Discriminante);

					if (VentaLocal.IdEmpresa == 0)
						soapObject.addProperty("idempresa", String.valueOf(this.Vendedor.IdEmpresa));
					else {
						soapObject.addProperty("idempresa", String.valueOf(VentaLocal.IdEmpresa));
					}

					soapObject.addProperty("folio", VentaLocal.FolioDia);
					soapObject.addProperty("GUIDV", VentaLocal.GUIDV);

					soapObject.addProperty("TipoOperacion", VentaLocal.TipoOperacion);

					if(VentaLocal.Discriminante.equals("Entrega"))
					{
						soapObject.addProperty("IdVentaPedido", String.valueOf(VentaLocal.IdVentaPedido));						
					}

					soapEnvelope.setOutputSoapObject(soapObject);
					soapEnvelope.bodyOut = soapObject;
					soapEnvelope.dotNet = true;
					soapEnvelope.encodingStyle = "http://www.w3.org/2001/XMLSchema";

					HttpTransportSE transport = new HttpTransportSE(wsURL);
					try
					{
						if(VentaLocal.Discriminante.equals("Entrega"))
						{
							transport.call(WSNamespace + "AgregarEntrega", soapEnvelope);
						}
						else
						{
							transport.call(WSNamespace + "AgregarNota2", soapEnvelope);
						}

						if(soapEnvelope.getResponse() != null)
						{
							SoapPrimitive respuesta = (SoapPrimitive)soapEnvelope.getResponse();

							VentaLocal.Enviado = Integer.parseInt(respuesta.getValue().toString());

							VentaLocal.ClearProceso();
							VentaLocal.ClearParametros();

							VentaLocal.TipoProceso = "SQL";
							VentaLocal.AgregarProceso("SQL.Actualizar");
							VentaLocal.EjecutarProceso();

							Logg.info("Venta enviada" + VentaLocal.Cliente);
						}
						else
						{
							VentaLocal.Enviado = 0;
							Logg.info("Respuesta NULL: " + "idItinerio:" + String.valueOf(VentaLocal.IdItinerario) + "; idcliente:" + String.valueOf(VentaLocal.IdCliente) + 
									"; IdVendedor:" + String.valueOf(VentaLocal.IdVendedor) + "; IdListaPrecio:" + String.valueOf(VentaLocal.IdListaPrecio) + 
									"; FechaCreacion:" + VentaLocal.FechaCreacion + "; Total:" + String.valueOf(VentaLocal.Total) +
									"; PagoEfectivo:" + String.valueOf(VentaLocal.PagoEfectivo) + "; PagoCheque:" + String.valueOf(VentaLocal.PagoCheque) +
									"; PagoDeposito:" + String.valueOf(VentaLocal.PagoDeposito) + "; PagoTransferencia:" + String.valueOf(VentaLocal.PagoTransferencia) + 
									"; NombreImpreso:" + VentaLocal.NombreImpreso + "; FechaCheque:" + VentaLocal.FechaCheque + 
									"; Banco:" + VentaLocal.Banco + "; ChequeNo:" + VentaLocal.ChequeNo +
									"; DiscriminantePago:" + VentaLocal.DiscriminantePago + "; SaldoAnterior:" + String.valueOf(VentaLocal.SaldoAnterior) +
									"; Abono:" + String.valueOf(VentaLocal.Abono) + "; NuevoSaldo:" + String.valueOf(VentaLocal.NuevoSaldo) +
									"; CreditoMonto:" + String.valueOf(VentaLocal.CreditoMonto) + "; Observacion:" + VentaLocal.Observacion + 
									"; Discriminante:" + VentaLocal.Discriminante + "; IdEmpresa:" + String.valueOf(this.Vendedor.IdEmpresa) +
									"; FolioDia:" + VentaLocal.FolioDia + "; GUIDV:" + VentaLocal.GUIDV +
									"; TipoOperacion:" + VentaLocal.TipoOperacion);
						}

					}
					catch (Exception e)
					{
						String Error = e.getMessage();
						Logg.info("Error al enviar la venta: " + Error);
					}
				}

				if (VentaLocal.Enviado <= 0) {
					continue;
				}

				boolean hayError = false;
				VentaDetalleLocal.ClearProceso();
				VentaDetalleLocal.ClearParametros();

				VentaDetalleLocal.TipoProceso = "SQL";
				VentaDetalleLocal.AgregarProceso("SQL.Consultar");
				VentaDetalleLocal.getClass(); VentaDetalleLocal.AgregarParametro("idSortField", String.valueOf(16));

				VentaDetalleLocal.getClass(); VentaDetalleLocal.AgregarParametro("idFilterField", String.valueOf(17));
				VentaDetalleLocal.AgregarParametro("filtro", "0");

				VentaDetalleLocal.getClass(); VentaDetalleLocal.AgregarParametro("idFilterField2", String.valueOf(23));
				VentaDetalleLocal.AgregarParametro("filtro2", String.valueOf(VentaLocal.GUIDV));

				List<BO> lsVentaDetalleLocal = VentaDetalleLocal.ConsultarProceso();

				if (lsVentaDetalleLocal != null)
				{
					for (int x2 = 0; x2 < lsVentaDetalleLocal.size(); x2++)
					{
						VentaDetalleLocal = (BONotaDetalle)lsVentaDetalleLocal.get(x2);

						if (!VentaLocal.GUIDV.equalsIgnoreCase(VentaDetalleLocal.GUIDV))
							continue;

						if(VentaDetalleLocal.Enviado == 1)
							continue;

						if (VentaDetalleLocal.Discriminante.equalsIgnoreCase("Venta"))
						{
							VentaDetalleLocal.ClearProceso();
							VentaDetalleLocal.ClearParametros();
							VentaDetalleLocal.TipoProceso = "WS";

							VentaDetalleLocal.AgregarProceso("AgregarNotaDetalle2");

							VentaDetalleLocal.AgregarParametro("wsNamesPace", WSNamespace);
							VentaDetalleLocal.AgregarParametro("wsUrl", wsURL);

							VentaDetalleLocal.AgregarParametro("WebMethod", "AgregarNotaDetalle2");

							VentaDetalleLocal.AgregarParametro("idnota", String.valueOf(VentaLocal.Enviado));

							VentaDetalleLocal.AgregarParametro("idproducto", String.valueOf(VentaDetalleLocal.IdProducto));
							VentaDetalleLocal.AgregarParametro("cantidad", String.valueOf(VentaDetalleLocal.Cantidad));
							VentaDetalleLocal.AgregarParametro("precio", String.valueOf(VentaDetalleLocal.Precio));
							VentaDetalleLocal.AgregarParametro("discriminante", VentaDetalleLocal.Discriminante);

							VentaDetalleLocal.AgregarParametro("IdVendedor", String.valueOf(VentaLocal.IdVendedor));
							VentaDetalleLocal.AgregarParametro("GUIDV", VentaLocal.GUIDV);

							VentaDetalleLocal.AgregarParametro("GUIDVD", VentaDetalleLocal.GUIDVD);
							VentaDetalleLocal.AgregarParametro("IdLista", String.valueOf(VentaDetalleLocal.IdLista));

							VentaDetalleLocal.EjecutarProceso();

							Logg.info("VentaDetelle enviado " + VentaDetalleLocal.GUIDVD);
						}
						else if (VentaDetalleLocal.Discriminante.equalsIgnoreCase("Devolucion"))
						{
							try
							{
								VentaDetalleLocal.ClearProceso();
								VentaDetalleLocal.ClearParametros();
								VentaDetalleLocal.TipoProceso = "WS";
								VentaDetalleLocal.AgregarProceso("InsertarDevolucion2");
								VentaDetalleLocal.AgregarParametro("wsNamesPace", WSNamespace);
								VentaDetalleLocal.AgregarParametro("wsUrl", wsURL);
								VentaDetalleLocal.AgregarParametro("WebMethod", "InsertarDevolucion2");

								VentaDetalleLocal.AgregarParametro("idnota", String.valueOf(VentaLocal.Enviado));

								VentaDetalleLocal.AgregarParametro("idproducto", String.valueOf(VentaDetalleLocal.IdProducto));
								VentaDetalleLocal.AgregarParametro("cantidad", String.valueOf(VentaDetalleLocal.Cantidad));
								VentaDetalleLocal.AgregarParametro("precio", String.valueOf(VentaDetalleLocal.Precio));
								VentaDetalleLocal.AgregarParametro("nonota", VentaDetalleLocal.NotaDevolucion);

								VentaDetalleLocal.AgregarParametro("IdVendedor", String.valueOf(VentaLocal.IdVendedor));
								VentaDetalleLocal.AgregarParametro("GUIDV", VentaLocal.GUIDV);

								VentaDetalleLocal.AgregarParametro("GUIDVD", VentaDetalleLocal.GUIDVD);
								VentaDetalleLocal.AgregarParametro("IdLista", String.valueOf(VentaDetalleLocal.IdLista));

								VentaDetalleLocal.EjecutarProceso();

								Logg.info("VentaDetelle enviado " + VentaDetalleLocal.GUIDVD);
							} catch (Exception localException1) {
							}
						}
						else if (VentaDetalleLocal.Discriminante.equalsIgnoreCase("Reposicion"))
						{
							try
							{
								VentaDetalleLocal.ClearProceso();
								VentaDetalleLocal.ClearParametros();
								VentaDetalleLocal.TipoProceso = "WS";
								VentaDetalleLocal.AgregarProceso("InsertarReposicion2");
								VentaDetalleLocal.AgregarParametro("wsNamesPace", WSNamespace);
								VentaDetalleLocal.AgregarParametro("wsUrl", wsURL);
								VentaDetalleLocal.AgregarParametro("WebMethod", "InsertarReposicion2");

								VentaDetalleLocal.AgregarParametro("idnota", String.valueOf(VentaLocal.Enviado));

								VentaDetalleLocal.AgregarParametro("idproducto", String.valueOf(VentaDetalleLocal.IdProducto));
								VentaDetalleLocal.AgregarParametro("cantidad", String.valueOf(VentaDetalleLocal.Cantidad));
								VentaDetalleLocal.AgregarParametro("precio", String.valueOf(VentaDetalleLocal.Precio));
								VentaDetalleLocal.AgregarParametro("nonota", VentaDetalleLocal.NotaReposicion);

								VentaDetalleLocal.AgregarParametro("IdVendedor", String.valueOf(VentaLocal.IdVendedor));
								VentaDetalleLocal.AgregarParametro("GUIDV", VentaLocal.GUIDV);

								VentaDetalleLocal.AgregarParametro("GUIDVD", VentaDetalleLocal.GUIDVD);
								VentaDetalleLocal.AgregarParametro("IdLista", String.valueOf(VentaDetalleLocal.IdLista));

								VentaDetalleLocal.EjecutarProceso();

								Logg.info("VentaDetelle enviado " + VentaDetalleLocal.GUIDVD);
							}
							catch (Exception localException2)
							{
							}
						}
						else if (VentaDetalleLocal.Discriminante.equalsIgnoreCase("Entrega"))
						{
							try
							{
								VentaDetalleLocal.ClearProceso();
								VentaDetalleLocal.ClearParametros();
								VentaDetalleLocal.TipoProceso = "WS";
								VentaDetalleLocal.AgregarProceso("AgregarEntregaDetalle");
								VentaDetalleLocal.AgregarParametro("wsNamesPace", WSNamespace);
								VentaDetalleLocal.AgregarParametro("wsUrl", wsURL);
								VentaDetalleLocal.AgregarParametro("WebMethod", "AgregarEntregaDetalle");

								VentaDetalleLocal.AgregarParametro("idnota", String.valueOf(VentaLocal.Enviado));

								VentaDetalleLocal.AgregarParametro("idproducto", String.valueOf(VentaDetalleLocal.IdProducto));
								VentaDetalleLocal.AgregarParametro("cantidad", String.valueOf(VentaDetalleLocal.Cantidad));
								VentaDetalleLocal.AgregarParametro("precio", String.valueOf(VentaDetalleLocal.Precio));
								VentaDetalleLocal.AgregarParametro("discriminante", VentaDetalleLocal.Discriminante);

								VentaDetalleLocal.AgregarParametro("IdVendedor", String.valueOf(VentaLocal.IdVendedor));
								VentaDetalleLocal.AgregarParametro("GUIDV", VentaLocal.GUIDV);

								VentaDetalleLocal.AgregarParametro("GUIDVD", VentaDetalleLocal.GUIDVD);
								VentaDetalleLocal.AgregarParametro("IdLista", String.valueOf(VentaDetalleLocal.IdLista));

								VentaDetalleLocal.AgregarParametro("IdVentaDetallePedido", String.valueOf(VentaDetalleLocal.IdVentaDetallePedido));
								VentaDetalleLocal.AgregarParametro("Devolucion", String.valueOf(VentaDetalleLocal.Devolucion));

								VentaDetalleLocal.EjecutarProceso();
							}
							catch (Exception localException2)
							{
							}
						}
						else {
							try {
								VentaDetalleLocal.ClearProceso();
								VentaDetalleLocal.ClearParametros();
								VentaDetalleLocal.TipoProceso = "WS";
								VentaDetalleLocal.AgregarProceso("AgregarNotaDetalle2");
								VentaDetalleLocal.AgregarParametro("wsNamesPace", WSNamespace);
								VentaDetalleLocal.AgregarParametro("wsUrl", wsURL);
								VentaDetalleLocal.AgregarParametro("WebMethod", "AgregarNotaDetalle2");

								VentaDetalleLocal.AgregarParametro("idnota", String.valueOf(VentaLocal.Enviado));

								VentaDetalleLocal.AgregarParametro("idproducto", String.valueOf(VentaDetalleLocal.IdProducto));
								VentaDetalleLocal.AgregarParametro("cantidad", String.valueOf(VentaDetalleLocal.Cantidad));
								VentaDetalleLocal.AgregarParametro("precio", String.valueOf(VentaDetalleLocal.Precio));
								VentaDetalleLocal.AgregarParametro("discriminante", VentaDetalleLocal.Discriminante);

								VentaDetalleLocal.AgregarParametro("IdVendedor", String.valueOf(VentaLocal.IdVendedor));
								VentaDetalleLocal.AgregarParametro("GUIDV", VentaLocal.GUIDV);

								VentaDetalleLocal.AgregarParametro("GUIDVD", VentaDetalleLocal.GUIDVD);
								VentaDetalleLocal.AgregarParametro("IdLista", String.valueOf(VentaDetalleLocal.IdLista));

								VentaDetalleLocal.EjecutarProceso();

								Logg.info("VentaDetelle enviado " + VentaDetalleLocal.GUIDVD);
							}
							catch (Exception localException3) {
							}
						}
						if (VentaDetalleLocal.Error.equals(""))
						{
							VentaDetalleLocal.Enviado = 1;
							VentaDetalleLocal.TipoProceso = "SQL";
							VentaDetalleLocal.AgregarProceso("SQL.Actualizar");
							VentaDetalleLocal.EjecutarProceso();
						}
						else
						{
							hayError = true;
						}
					}
				}

				if (!hayError)
				{
					VentaLocal.EnviadoCompleto = 1;
					VentaLocal.TipoProceso = "SQL";
					VentaLocal.AgregarProceso("SQL.Actualizar");
					VentaLocal.EjecutarProceso();
				}


				//	         Embalaje.ClearProceso();
				//	         Embalaje.ClearParametros();
				//	 
				//	         Embalaje.TipoProceso = "SQL";
				//	         Embalaje.AgregarProceso("SQL.Consultar");
				//	         Embalaje.getClass(); Embalaje.AgregarParametro("idSortField", String.valueOf(1));
				//	 
				//	         Embalaje.getClass(); Embalaje.AgregarParametro("idFilterField", String.valueOf(4));
				//	         Embalaje.AgregarParametro("filtro", String.valueOf(VentaLocal.GUIDV));
				//	 
				//	         Embalaje.getClass(); Embalaje.AgregarParametro("idFilterField2", String.valueOf(5));
				//	         Embalaje.AgregarParametro("filtro2", "0");
				//	 
				//	         lsEmbalaje = Embalaje.ConsultarProceso();
				//	 
				//	         if (lsEmbalaje == null) {
				//	           continue;
				//	         }
				//
				//	         for (int x2 = 0; x2 < lsEmbalaje.size(); x2++)
				//	         {
				//	           Embalaje = (BODevolucionEmbalaje)lsEmbalaje.get(x2);
				//	 
				//	           Embalaje.ClearProceso();
				//	           Embalaje.ClearParametros();
				//	           Embalaje.TipoProceso = "WS";
				//	           Embalaje.AgregarProceso("RegresarEmbalaje");
				//	           Embalaje.AgregarParametro("wsNamesPace", WSNamespace);
				//	           Embalaje.AgregarParametro("wsUrl", wsURL);
				//	           Embalaje.AgregarParametro("WebMethod", "RegresarEmbalaje");
				//	 
				//	           Embalaje.AgregarParametro("IdCliente", String.valueOf(Embalaje.IdCliente));
				//	           Embalaje.AgregarParametro("IdProducto", String.valueOf(Embalaje.IdProducto));
				//	           Embalaje.AgregarParametro("Cantidad", String.valueOf(Embalaje.Cantidad));
				//	 
				//						if(Info.ServicioDatosActivos(context))
				//						{
				//							Embalaje.EjecutarProceso();
				//							Embalaje.Enviado = 1;
				//							Embalaje.TipoProceso = "SQL";
				//							Embalaje.AgregarProceso("SQL.Actualizar");
				//							Embalaje.EjecutarProceso();
				//						}
				//	         }


			}
		}
	}

	public void EnviarLogSincronizacion(String estado)
	{
		this.Configuracion.ClearProceso();
		this.Configuracion.ClearParametros();

		this.Configuracion.TipoProceso = "WS";
		this.Configuracion.AgregarProceso("AgregarLogSincronizacion");
		this.Configuracion.AgregarParametro("wsNamesPace", WSNamespace);
		this.Configuracion.AgregarParametro("wsUrl", wsURL);

		this.Configuracion.AgregarParametro("WebMethod", "AgregarLogSincronizacion");

		this.Configuracion.AgregarParametro("IdVendedor", String.valueOf(this.Vendedor.IdVendedor));
		this.Configuracion.AgregarParametro("PIN", Info.getPIN(context));
		this.Configuracion.AgregarParametro("IP", Info.GetIPAdress(context));
		this.Configuracion.AgregarParametro("RED", Info.GetWifiName(context));

		this.Configuracion.AgregarParametro("Estado", estado);
		this.Configuracion.AgregarParametro("DetalleDescarga", getRepositorioEstado());

		this.Configuracion.EjecutarProceso();
	}

	public String ConsultarEstadoPrecios()
	{
		SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(110);
		SoapObject soapObject = new SoapObject(WSNamespace, "ConsultarEstadoPrecios");

		soapEnvelope.setOutputSoapObject(soapObject);
		soapEnvelope.bodyOut = soapObject;
		soapEnvelope.dotNet = true;
		soapEnvelope.encodingStyle = "http://www.w3.org/2001/XMLSchema";

		HttpTransportSE transport = new HttpTransportSE(wsURL);
		try
		{
			transport.call(WSNamespace + "ConsultarEstadoPrecios", soapEnvelope);
			SoapPrimitive respuesta = (SoapPrimitive)soapEnvelope.getResponse();

			return respuesta.toString();
		}
		catch (Exception e) {
		}
		return "";
	}

	public void EnviarErrores()
	{
	}

	public void EnviarChecklistVehiculo()
	{
		this.CheckVehiculoRealizado.ClearProceso();
		this.CheckVehiculoRealizado.ClearProceso();

		this.CheckVehiculoRealizado.TipoProceso = "SQL";
		this.CheckVehiculoRealizado.AgregarProceso("SQL.Consultar");
		this.CheckVehiculoRealizado.getClass(); this.CheckVehiculoRealizado.AgregarParametro("idSortField", String.valueOf(5));
		this.CheckVehiculoRealizado.getClass(); this.CheckVehiculoRealizado.AgregarParametro("idFilterField", String.valueOf(5));
		this.CheckVehiculoRealizado.AgregarParametro("filtro", "");
		this.lsCheckVehiculoRealizado = this.CheckVehiculoRealizado.ConsultarProceso();

		if (this.lsCheckVehiculoRealizado != null)
		{
			for (int w = 0; w < this.lsCheckVehiculoRealizado.size(); w++)
			{
				this.CheckVehiculoRealizado = ((BOCheckVehiculoRealizado)this.lsCheckVehiculoRealizado.get(w));

				this.CheckVehiculoRealizado.ClearProceso();
				this.CheckVehiculoRealizado.ClearParametros();

				this.CheckVehiculoRealizado.TipoProceso = "WS";
				this.CheckVehiculoRealizado.AgregarProceso("AgregarCheckVehiculoRealizado");
				this.CheckVehiculoRealizado.AgregarParametro("wsNamesPace", WSNamespace);
				this.CheckVehiculoRealizado.AgregarParametro("wsUrl", wsURL);

				this.CheckVehiculoRealizado.AgregarParametro("WebMethod", "AgregarCheckVehiculoRealizado");

				this.CheckVehiculoRealizado.AgregarParametro("IdCheckVehiculo", String.valueOf(this.CheckVehiculoRealizado.IdCheckVehiculo));
				this.CheckVehiculoRealizado.AgregarParametro("IdVehiculo", String.valueOf(this.CheckVehiculoRealizado.IdVehiculo));
				this.CheckVehiculoRealizado.AgregarParametro("IdVendedor", String.valueOf(this.CheckVehiculoRealizado.IdVendedor));
				this.CheckVehiculoRealizado.AgregarParametro("PIN", this.CheckVehiculoRealizado.PIN);
				this.CheckVehiculoRealizado.AgregarParametro("Fecha", this.CheckVehiculoRealizado.Fecha);
				this.CheckVehiculoRealizado.AgregarParametro("Valor", this.CheckVehiculoRealizado.Valor);

				this.CheckVehiculoRealizado.EjecutarProceso();
			}
		}
	}

	public void EnviarImagenes(BOFoto Foto)
	{
		if (Foto.IdVehiculo > 0)
		{
			Logg.info("Enviando fotos");

			Foto.ClearProceso();
			Foto.ClearParametros();

			Foto.TipoProceso = "WS";
			Foto.AgregarProceso("AgregarImagen");

			Foto.AgregarParametro("wsNamesPace", WSNamespace);
			Foto.AgregarParametro("wsUrl", wsURL);

			Foto.AgregarParametro("WebMethod", "AgregarImagen");

			String RutaArchivo = Foto.Ruta;

			Logg.info("RutaArchivo: " + RutaArchivo);

			try
			{
				File file = new File(RutaArchivo);
				//f = new File(Environment.getExternalStorageDirectory(), repositorio.mCurrentPhoto);

				if (file.exists())
				{
					byte[] buf = IOUtil.readFile(file);

					if(buf != null)
					{
						Foto.AgregarParametro("base64String", Base64.encode(buf));

						Foto.AgregarParametro("IdVendedor", String.valueOf(Foto.IdVendedor));
						Foto.AgregarParametro("IdVehiculo", String.valueOf(Foto.IdVehiculo));
						Foto.AgregarParametro("PIN", Foto.PIN);
						Foto.AgregarParametro("Nombre", RutaArchivo);
						Foto.AgregarParametro("Fecha", Foto.Fecha);
						Foto.AgregarParametro("FechaHora", Foto.FechaHora);
						Foto.AgregarParametro("Tipo", Foto.Tipo);

						Foto.EjecutarProceso();

						if (Foto.Error.equals(""))
						{
							Foto.Enviado = 1;
							Foto.ClearProceso();
							Foto.ClearParametros();
							Foto.TipoProceso = "SQL";
							Foto.AgregarProceso("SQL.Actualizar");
							Foto.EjecutarProceso();
						}

						Logg.info("Foto enviada" + Foto.Nombre);

						//Si no se elimina guardar un valor para indicar que se debe eliminar en el siguiente ciclo
						file.delete();
						Logg.info("Foto eliminada");
					}

				}
			}
			catch (Exception e)
			{
				Logg.error("Error al enviar la foto: " + Arrays.toString(e.getStackTrace()));
			}
			Foto.ClearProceso();
			Foto.ClearParametros();
		}
	}

	//	   public void IniciarCamara()
	//	   {
	//	     if (!this.isListenerFoto)
	//	     {
	//	       addListenerFileWriter();
	//	     }
	//	 
	//	     try
	//	     {
	//	       CameraArguments vidargs = new CameraArguments();
	//	       Invoke.invokeApplication(6, vidargs);
	//	     }
	//	     catch (Exception localException)
	//	     {
	//	     }
	//	   }

	//	   public void addListenerFileWriter()
	//	   {
	//	     if (!this.isListenerFoto)
	//	     {
	//	       try
	//	       {
	//	         this.fotosPath = new Vector();
	//	         UiApplication.getUiApplication().addFileSystemJournalListener(this.listener);
	//	         this.isListenerFoto = true;
	//	       }
	//	       catch (Exception localException) {
	//	       }
	//	     }
	//	   }
	//	 
	//	   public void removeListenerFileWriter() {
	//	     if (this.isListenerFoto)
	//	     {
	//	       try
	//	       {
	//	         this.fotosPath.removeAllElements();
	//	         UiApplication.getUiApplication().removeFileSystemJournalListener(this.listener);
	//	         this.isListenerFoto = false;
	//	       }
	//	       catch (Exception localException)
	//	       {
	//	       }
	//	     }
	//	   }

	//	   public void GuardarImagenes() {
	//	     this.sender.GuardarImagenes();
	//	   }
	//	 
	//	   public void EnviarMail(String toMail, String subjectMail, String bodyMail, String FileName)
	//	   {
	//	     this.mailSender.sendEmail(toMail, subjectMail, bodyMail, FileName);
	//	   }

	public void CommitCancela()
	{
		ConsultarOperacionesCancelacion();

		if (this.lsVenta != null)
		{
			for (int x = 0; x < this.lsVenta.size(); x++)
			{
				this.Venta = ((BONota)this.lsVenta.get(x));
				if (this.Venta.Cancelar != 1)
					continue;
				this.Venta.Cancelar = 0;
				this.Venta.Enviado = 0;
				this.Venta.EnviadoCompleto = 0;

				this.Venta.ClearProceso();
				this.Venta.ClearParametros();

				this.Venta.TipoProceso = "SQL";
				this.Venta.AgregarProceso("SQL.Actualizar");
				this.Venta.EjecutarProceso();
			}
		}
	}

	public void cancelarNotas()
	{
		ConsultarOperacionesCancelacion();

		if (this.lsVenta != null)
		{
			for (int x = 0; x < this.lsVenta.size(); x++)
			{
				this.Venta = ((BONota)this.lsVenta.get(x));
				if (this.Venta.Cancelar != 1)
					continue;
				int z1 = 0;

				this.VentaDetalle.ClearProceso();
				this.VentaDetalle.ClearParametros();

				this.VentaDetalle.TipoProceso = "SQL";
				this.VentaDetalle.AgregarProceso("SQL.Consultar");
				this.VentaDetalle.getClass(); this.VentaDetalle.AgregarParametro("idSortField", String.valueOf(16));

				this.VentaDetalle.getClass(); this.VentaDetalle.AgregarParametro("idFilterField", String.valueOf(17));
				this.VentaDetalle.AgregarParametro("filtro", "0");

				this.VentaDetalle.getClass(); this.VentaDetalle.AgregarParametro("idFilterField2", String.valueOf(23));
				this.VentaDetalle.AgregarParametro("filtro2", String.valueOf(this.Venta.GUIDV));

				this.lsVentaDetalle = this.VentaDetalle.ConsultarProceso();

				ConsultarAlmacenMovil(2, 4, String.valueOf(this.Vendedor.IdVendedor), "0", "0");

				if ((this.lsVentaDetalle != null) && (this.lsAlmacenMovil != null))
				{
					for (int x2 = 0; x2 < this.lsVentaDetalle.size(); x2++)
					{
						this.VentaDetalle = ((BONotaDetalle)this.lsVentaDetalle.get(x2));

						for (z1 = 0; z1 < this.lsAlmacenMovil.size(); z1++)
						{
							this.AlmacenMovil = ((BOAlmacenMovil)this.lsAlmacenMovil.get(z1));
							if (this.AlmacenMovil.IdProducto != this.VentaDetalle.IdProducto)
								continue;
							this.AlmacenMovil.Venta -= this.VentaDetalle.Cantidad;
							this.AlmacenMovil.Existencia += this.VentaDetalle.Cantidad;
							this.AlmacenMovil.TipoProceso = "SQL";
							this.AlmacenMovil.AgregarProceso("SQL.Actualizar");
							this.AlmacenMovil.EjecutarProceso();

							this.VentaDetalle.ClearProceso();
							this.VentaDetalle.ClearParametros();

							this.VentaDetalle.TipoProceso = "SQL";
							this.VentaDetalle.AgregarProceso("SQL.Eliminar");
							this.VentaDetalle.EjecutarProceso();
						}
					}
				}

				this.Venta.ClearProceso();
				this.Venta.ClearParametros();

				this.Venta.TipoProceso = "SQL";
				this.Venta.AgregarProceso("SQL.Eliminar");
				this.Venta.EjecutarProceso();
			}
		}
	}

	public void ObtenerCobroFactura()
	{
		try
		{
			if (this.lsSaldoClienteNota == null)
			{
				ConsultarSaldoClienteNota(2, 4);
			}

			if (this.lsSaldoClienteNota != null)
			{
				this.Venta.ClearProceso();
				this.Venta.ClearParametros();
				this.Venta.TipoProceso = "SQL";
				this.Venta.AgregarProceso("SQL.Consultar");
				this.Venta.getClass(); this.Venta.AgregarParametro("idSortField", String.valueOf(30));
				this.Venta.getClass(); this.Venta.AgregarParametro("idFilterField", String.valueOf(23));
				this.Venta.AgregarParametro("filtro", "");
				this.lsVenta = this.Venta.ConsultarProceso();

				if (this.lsVenta != null)
				{
					for (int z = 0; z < this.lsSaldoClienteNota.size(); z++)
					{
						this.SaldoClienteNota = ((BOSaldoClienteNota)this.lsSaldoClienteNota.get(z));

						double cobro = 0.0D;
						float montofactura = 0.0F;
						try
						{
							montofactura = Float.parseFloat(this.SaldoClienteNota.SaldoFactura);
						}
						catch (Exception localException) {
						}
						for (int x = 0; x < this.lsVenta.size(); x++)
						{
							this.Venta = ((BONota)this.lsVenta.get(x));

							if ((!this.Venta.Fecha.equals(Utils.getFechaActual_DD_MM_YYYY())) || 
									(!this.Venta.Discriminante.equals("Cobro"))) 
							{
								continue;
							}
							if (this.Venta.ChequeNo.indexOf(this.SaldoClienteNota.NoDocumento) <= -1)
								continue;
							cobro = cobro + this.Venta.PagoEfectivo + this.Venta.PagoCheque + this.Venta.PagoDeposito;
						}

						this.SaldoClienteNota.CobroFactura = String.valueOf((float)cobro);
						this.SaldoClienteNota.SaldoFactura = String.valueOf(Float.parseFloat(this.SaldoClienteNota.Importe) - (float)cobro);

						this.SaldoClienteNota.ClearProceso();
						this.SaldoClienteNota.ClearParametros();

						this.SaldoClienteNota.TipoProceso = "SQL";
						this.SaldoClienteNota.AgregarProceso("SQL.Actualizar");
						this.SaldoClienteNota.EjecutarProceso();
					}
				}
			}
		}
		catch (Exception localException1)
		{
		}
	}

	//Simula objetos de la sincronizacion
	public void SimulaSincronizacion()
	{
		//agregamos los vehiculos
		BOVehiculo Vehiculo = new BOVehiculo(this.context);

		Vehiculo.TipoProceso = "SQL";
		Vehiculo.AgregarProceso("SQL.EliminarTodos");
		Vehiculo.EjecutarProceso();

		Vehiculo.IdVehiculo = 1;
		Vehiculo.Descripcion = "Unidad 1";
		Vehiculo.Numero = "X0224";
		Vehiculo.NumeroFotos = 0;

		Vehiculo.TipoProceso = "SQL";
		Vehiculo.AgregarProceso("SQL.Agregar");
		Vehiculo.EjecutarProceso();

		Vehiculo.IdVehiculo = 2;
		Vehiculo.Descripcion = "Unidad 2";
		Vehiculo.Numero = "X0234";
		Vehiculo.NumeroFotos = 0;

		Vehiculo.TipoProceso = "SQL";
		Vehiculo.AgregarProceso("SQL.Agregar");
		Vehiculo.EjecutarProceso();

		Vehiculo.IdVehiculo = 3;
		Vehiculo.Descripcion = "Unidad 3";
		Vehiculo.Numero = "X0X0453224";
		Vehiculo.NumeroFotos = 0;

		Vehiculo.TipoProceso = "SQL";
		Vehiculo.AgregarProceso("SQL.Agregar");
		Vehiculo.EjecutarProceso();

		//agregamos los clientes para la venta
		BOCliente Cliente = new BOCliente(this.context);

		Cliente.TipoProceso = "SQL";
		Cliente.AgregarProceso("SQL.EliminarTodos");
		Cliente.EjecutarProceso();

		Cliente.IdCliente = 110;
		Cliente.Nombre = "Molecular Quality, S.A. De C.V.";
		Cliente.Direccion = "MD123234";
		Cliente.Frecuencia = "1234567";
		Cliente.Visita = "";
		Cliente.PosicionGPS = "19.686475,-99.194757";
		Cliente.Promocion = 0;
		Cliente.PromocionInicio = 0;
		Cliente.PromocionFin = 0;
		Cliente.SaldoPendiente = 0.0F;
		Cliente.RequiereAutorizacion = 0;
		Cliente.Discriminante = "";
		Cliente.Codigo = "COM5555";
		Cliente.MesEncuesta = 0;
		Cliente.DiasMaximoEntrega = 0;
		Cliente.PrecioPedido = 0;

		Cliente.TipoProceso = "SQL";
		Cliente.AgregarProceso("SQL.Agregar");
		Cliente.EjecutarProceso();

		Cliente.IdCliente = 119;
		Cliente.Nombre = "Clinicas Dentales Sonria, S. A. De C. V. (Suc.Tona";
		Cliente.Direccion = "MDDF000M3X";
		Cliente.Frecuencia = "123456";
		Cliente.Visita = "";
		Cliente.PosicionGPS = "19.421312,-99.163437";
		Cliente.Promocion = 0;
		Cliente.PromocionInicio = 0;
		Cliente.PromocionFin = 0;
		Cliente.SaldoPendiente = 0.0F;
		Cliente.RequiereAutorizacion = 0;
		Cliente.Discriminante = "";
		Cliente.Codigo = "COM3020554";
		Cliente.MesEncuesta = 0;
		Cliente.DiasMaximoEntrega = 0;
		Cliente.PrecioPedido = 0;

		Cliente.TipoProceso = "SQL";
		Cliente.AgregarProceso("SQL.Agregar");
		Cliente.EjecutarProceso();

		Cliente.IdCliente = 120;
		Cliente.Nombre = "Colegio San Ignacio De Loyola Vizcainas, I.A.P.";
		Cliente.Direccion = "MXDFGDFG ";
		Cliente.Frecuencia = "123456";
		Cliente.Visita = "";
		Cliente.PosicionGPS = "19.42895,-99.140448";
		Cliente.Promocion = 0;
		Cliente.PromocionInicio = 0;
		Cliente.PromocionFin = 0;
		Cliente.SaldoPendiente = 0.0F;
		Cliente.RequiereAutorizacion = 0;
		Cliente.Discriminante = "";
		Cliente.Codigo = "237193";
		Cliente.MesEncuesta = 0;
		Cliente.DiasMaximoEntrega = 0;
		Cliente.PrecioPedido = 0;

		Cliente.TipoProceso = "SQL";
		Cliente.AgregarProceso("SQL.Agregar");
		Cliente.EjecutarProceso();

		Cliente.IdCliente = 121;
		Cliente.Nombre = "Clinica Chulavista Servicios De Salud, S.A. De C.V";
		Cliente.Direccion = "MDFHJHDDFH";
		Cliente.Frecuencia = "123456";
		Cliente.Visita = "";
		Cliente.PosicionGPS = "19.48791,-99.119754";
		Cliente.Promocion = 0;
		Cliente.PromocionInicio = 0;
		Cliente.PromocionFin = 0;
		Cliente.SaldoPendiente = 0.0F;
		Cliente.RequiereAutorizacion = 0;
		Cliente.Discriminante = "";
		Cliente.Codigo = "238218";
		Cliente.MesEncuesta = 0;
		Cliente.DiasMaximoEntrega = 0;
		Cliente.PrecioPedido = 0;

		Cliente.TipoProceso = "SQL";
		Cliente.AgregarProceso("SQL.Agregar");
		Cliente.EjecutarProceso();

		Cliente.IdCliente = 122;
		Cliente.Nombre = "Liceo Franco Mexicano, A.C. Sucursal Coyoacan S";
		Cliente.Direccion = "DBDFGDFGDFG";
		Cliente.Frecuencia = "123456";
		Cliente.Visita = "";
		Cliente.PosicionGPS = "19.343673,-99.180963";
		Cliente.Promocion = 0;
		Cliente.PromocionInicio = 0;
		Cliente.PromocionFin = 0;
		Cliente.SaldoPendiente = 0.0F;
		Cliente.RequiereAutorizacion = 0;
		Cliente.Discriminante = "";
		Cliente.Codigo = "238220";
		Cliente.MesEncuesta = 0;
		Cliente.DiasMaximoEntrega = 0;
		Cliente.PrecioPedido = 0;

		Cliente.TipoProceso = "SQL";
		Cliente.AgregarProceso("SQL.Agregar");
		Cliente.EjecutarProceso();

		Cliente.IdCliente = 123;
		Cliente.Nombre = "EMILIA GERALDO";
		Cliente.Direccion = "";
		Cliente.Frecuencia = "123456";
		Cliente.Visita = "";
		Cliente.PosicionGPS = "";
		Cliente.Promocion = 0;
		Cliente.PromocionInicio = 0;
		Cliente.PromocionFin = 0;
		Cliente.SaldoPendiente = 0.0F;
		Cliente.RequiereAutorizacion = 0;
		Cliente.Discriminante = "";
		Cliente.Codigo = "238215";
		Cliente.MesEncuesta = 0;
		Cliente.DiasMaximoEntrega = 0;
		Cliente.PrecioPedido = 0;

		Cliente.TipoProceso = "SQL";
		Cliente.AgregarProceso("SQL.Agregar");
		Cliente.EjecutarProceso();

		//Agregamos los productos
		BOProducto Producto = new BOProducto(this.context);

		Producto.TipoProceso = "SQL";
		Producto.AgregarProceso("SQL.EliminarTodos");
		Producto.EjecutarProceso();

		Producto.IdProducto = 17605;
		Producto.IdUnidad = 1;
		Producto.PrecioPublico = 12.5F;
		Producto.Descripcion = "Cultivos Y Cepas";
		Producto.Codigo = "2400";
		Producto.DescripcionProveedor = "";
		Producto.MargenDescuento = "";
		Producto.Existencia = 0;
		Producto.Particion = 0;
		Producto.Unidad = "PZA";

		Producto.TipoProceso = "SQL";
		Producto.AgregarProceso("SQL.Agregar");
		Producto.EjecutarProceso();

		Producto.IdProducto = 17606;
		Producto.IdUnidad = 1;
		Producto.PrecioPublico = 8.5F;
		Producto.Descripcion = "Punzocortante";
		Producto.Codigo = "2401";
		Producto.DescripcionProveedor = "";
		Producto.MargenDescuento = "";
		Producto.Existencia = 0;
		Producto.Particion = 0;
		Producto.Unidad = "PZA";

		Producto.TipoProceso = "SQL";
		Producto.AgregarProceso("SQL.Agregar");
		Producto.EjecutarProceso();

		Producto.IdProducto = 17607;
		Producto.IdUnidad = 1;
		Producto.PrecioPublico = 6.5F;
		Producto.Descripcion = "Patológicos";
		Producto.Codigo = "2402";
		Producto.DescripcionProveedor = "";
		Producto.MargenDescuento = "";
		Producto.Existencia = 0;
		Producto.Particion = 0;
		Producto.Unidad = "PZA";

		Producto.TipoProceso = "SQL";
		Producto.AgregarProceso("SQL.Agregar");
		Producto.EjecutarProceso();

		Producto.IdProducto = 17608;
		Producto.IdUnidad = 1;
		Producto.PrecioPublico = 6.5F;
		Producto.Descripcion = "No Anatómico";
		Producto.Codigo = "2501";
		Producto.DescripcionProveedor = "";
		Producto.MargenDescuento = "";
		Producto.Existencia = 0;
		Producto.Particion = 0;
		Producto.Unidad = "PZA";

		Producto.TipoProceso = "SQL";
		Producto.AgregarProceso("SQL.Agregar");
		Producto.EjecutarProceso();

		Producto.IdProducto = 17609;
		Producto.IdUnidad = 1;
		Producto.PrecioPublico = 6.5F;
		Producto.Descripcion = "Sangre";
		Producto.Codigo = "2551";
		Producto.DescripcionProveedor = "";
		Producto.MargenDescuento = "";
		Producto.Existencia = 0;
		Producto.Particion = 0;
		Producto.Unidad = "PZA";

		Producto.TipoProceso = "SQL";
		Producto.AgregarProceso("SQL.Agregar");
		Producto.EjecutarProceso();

		//Agregamos los precios, por cliente
		BOPrecioCliente PrecioCliente = new BOPrecioCliente(this.context);

		PrecioCliente.TipoProceso = "SQL";
		PrecioCliente.AgregarProceso("SQL.EliminarTodos");
		PrecioCliente.EjecutarProceso();

		PrecioCliente.IdCliente = 110;
		PrecioCliente.IdProducto = 17605;
		PrecioCliente.Precio = "3";

		PrecioCliente.TipoProceso = "SQL";
		PrecioCliente.AgregarProceso("SQL.Agregar");
		PrecioCliente.EjecutarProceso();

		PrecioCliente.IdCliente = 110;
		PrecioCliente.IdProducto = 17606;
		PrecioCliente.Precio = "3";

		PrecioCliente.TipoProceso = "SQL";
		PrecioCliente.AgregarProceso("SQL.Agregar");
		PrecioCliente.EjecutarProceso();

		PrecioCliente.IdCliente = 110;
		PrecioCliente.IdProducto = 17607;
		PrecioCliente.Precio = "3";

		PrecioCliente.TipoProceso = "SQL";
		PrecioCliente.AgregarProceso("SQL.Agregar");
		PrecioCliente.EjecutarProceso();

		PrecioCliente.IdCliente = 119;
		PrecioCliente.IdProducto = 17607;
		PrecioCliente.Precio = "3";

		PrecioCliente.TipoProceso = "SQL";
		PrecioCliente.AgregarProceso("SQL.Agregar");
		PrecioCliente.EjecutarProceso();

		PrecioCliente.IdCliente = 120;
		PrecioCliente.IdProducto = 17607;
		PrecioCliente.Precio = "3";

		PrecioCliente.TipoProceso = "SQL";
		PrecioCliente.AgregarProceso("SQL.Agregar");
		PrecioCliente.EjecutarProceso();

		//Agregamos la informacion del cliente en base al cuestionario
		BOCuestionarioCliente CuestionarioCliente = new BOCuestionarioCliente(this.context);

		CuestionarioCliente.TipoProceso = "SQL";
		CuestionarioCliente.AgregarProceso("SQL.EliminarTodos");
		CuestionarioCliente.EjecutarProceso();

		CuestionarioCliente.IdCuestionarioCliente = 1;
		CuestionarioCliente.Pregunta = "Equipo que le gusta";
		CuestionarioCliente.Respuesta = "";
		CuestionarioCliente.Tipo = 2;

		CuestionarioCliente.TipoProceso = "SQL";
		CuestionarioCliente.AgregarProceso("SQL.Agregar");
		CuestionarioCliente.EjecutarProceso();

		CuestionarioCliente.IdCuestionarioCliente = 2;
		CuestionarioCliente.Pregunta = "Marca que mas compra";
		CuestionarioCliente.Respuesta = "";
		CuestionarioCliente.Tipo = 2;

		CuestionarioCliente.TipoProceso = "SQL";
		CuestionarioCliente.AgregarProceso("SQL.Agregar");
		CuestionarioCliente.EjecutarProceso();

		CuestionarioCliente.IdCuestionarioCliente = 2;
		CuestionarioCliente.Pregunta = "Contento";
		CuestionarioCliente.Respuesta = "SI/NO";
		CuestionarioCliente.Tipo = 1;

		CuestionarioCliente.TipoProceso = "SQL";
		CuestionarioCliente.AgregarProceso("SQL.Agregar");
		CuestionarioCliente.EjecutarProceso();

		CuestionarioCliente.IdCuestionarioCliente = 1;
		CuestionarioCliente.Pregunta = "Test1";
		CuestionarioCliente.Respuesta = "";
		CuestionarioCliente.Tipo = 2;

		CuestionarioCliente.TipoProceso = "SQL";
		CuestionarioCliente.AgregarProceso("SQL.Agregar");
		CuestionarioCliente.EjecutarProceso();

		CuestionarioCliente.IdCuestionarioCliente = 1;
		CuestionarioCliente.Pregunta = "Test2";
		CuestionarioCliente.Respuesta = "";
		CuestionarioCliente.Tipo = 2;

		CuestionarioCliente.TipoProceso = "SQL";
		CuestionarioCliente.AgregarProceso("SQL.Agregar");
		CuestionarioCliente.EjecutarProceso();

		CuestionarioCliente.IdCuestionarioCliente = 1;
		CuestionarioCliente.Pregunta = "Test3";
		CuestionarioCliente.Respuesta = "";
		CuestionarioCliente.Tipo = 2;

		CuestionarioCliente.TipoProceso = "SQL";
		CuestionarioCliente.AgregarProceso("SQL.Agregar");
		CuestionarioCliente.EjecutarProceso();

		CuestionarioCliente.IdCuestionarioCliente = 1;
		CuestionarioCliente.Pregunta = "Test4";
		CuestionarioCliente.Respuesta = "";
		CuestionarioCliente.Tipo = 2;

		CuestionarioCliente.TipoProceso = "SQL";
		CuestionarioCliente.AgregarProceso("SQL.Agregar");
		CuestionarioCliente.EjecutarProceso();

		CuestionarioCliente.IdCuestionarioCliente = 2;
		CuestionarioCliente.Pregunta = "Contento";
		CuestionarioCliente.Respuesta = "SI/NO/SU/SO/SY/SY";
		CuestionarioCliente.Tipo = 1;

		CuestionarioCliente.TipoProceso = "SQL";
		CuestionarioCliente.AgregarProceso("SQL.Agregar");
		CuestionarioCliente.EjecutarProceso();


		//Agregamos el checklist
		BOCheckVehiculo CheckVehiculo = new BOCheckVehiculo(this.context);

		CheckVehiculo.TipoProceso = "SQL";
		CheckVehiculo.AgregarProceso("SQL.EliminarTodos");
		CheckVehiculo.EjecutarProceso();

		CheckVehiculo.IdCheckVehiculo = 1;
		CheckVehiculo.Texto = "GASOLINA";
		CheckVehiculo.Respuesta = "";
		CheckVehiculo.Tipo = 2;

		CheckVehiculo.TipoProceso = "SQL";
		CheckVehiculo.AgregarProceso("SQL.Agregar");
		CheckVehiculo.EjecutarProceso();

		CheckVehiculo.IdCheckVehiculo = 1;
		CheckVehiculo.Texto = "KILOMETRAJE";
		CheckVehiculo.Respuesta = "";
		CheckVehiculo.Tipo = 2;

		CheckVehiculo.TipoProceso = "SQL";
		CheckVehiculo.AgregarProceso("SQL.Agregar");
		CheckVehiculo.EjecutarProceso();

		CheckVehiculo.IdCheckVehiculo = 1;
		CheckVehiculo.Texto = "ESTADO GENERAL";
		CheckVehiculo.Respuesta = "";
		CheckVehiculo.Tipo = 2;

		CheckVehiculo.TipoProceso = "SQL";
		CheckVehiculo.AgregarProceso("SQL.Agregar");
		CheckVehiculo.EjecutarProceso();

		CheckVehiculo.IdCheckVehiculo = 1;
		CheckVehiculo.Texto = "FECHA DEL ULTIMO SERVICIO";
		CheckVehiculo.Respuesta = "";
		CheckVehiculo.Tipo = 2;

		CheckVehiculo.TipoProceso = "SQL";
		CheckVehiculo.AgregarProceso("SQL.Agregar");
		CheckVehiculo.EjecutarProceso();


		//Agregamos la estadistica del cliente
		BOHistoricoVentaDiaria HistoricoVentaDiaria = new BOHistoricoVentaDiaria(this.context);

		HistoricoVentaDiaria.TipoProceso = "SQL";
		HistoricoVentaDiaria.AgregarProceso("SQL.EliminarTodos");
		HistoricoVentaDiaria.EjecutarProceso();

		HistoricoVentaDiaria.IdCliente = 0;
		HistoricoVentaDiaria.IdProducto = 0;
		HistoricoVentaDiaria.Dia = 0;
		HistoricoVentaDiaria.Cantidad = "5";
		HistoricoVentaDiaria.Descripcion = "Cultivos Y Cepas";
		HistoricoVentaDiaria.Codigo = "2501";
		HistoricoVentaDiaria.Unidad = "PZA";

		HistoricoVentaDiaria.TipoProceso = "SQL";
		HistoricoVentaDiaria.AgregarProceso("SQL.Agregar");
		HistoricoVentaDiaria.EjecutarProceso();

		HistoricoVentaDiaria.IdCliente = 0;
		HistoricoVentaDiaria.IdProducto = 0;
		HistoricoVentaDiaria.Dia = 0;
		HistoricoVentaDiaria.Cantidad = "12";
		HistoricoVentaDiaria.Descripcion = "Punzocortante";
		HistoricoVentaDiaria.Codigo = "2501";
		HistoricoVentaDiaria.Unidad = "PZA";

		HistoricoVentaDiaria.TipoProceso = "SQL";
		HistoricoVentaDiaria.AgregarProceso("SQL.Agregar");
		HistoricoVentaDiaria.EjecutarProceso();


		//Agregamos el almacen
		BOAlmacenMovil AlmacenMovil = new BOAlmacenMovil(this.context);

		AlmacenMovil.TipoProceso = "SQL";
		AlmacenMovil.AgregarProceso("SQL.EliminarTodos");
		AlmacenMovil.EjecutarProceso();

		AlmacenMovil.IdProducto = 1;
		AlmacenMovil.CantidadInicial = 1;
		AlmacenMovil.CantidadFinal = 1;
		AlmacenMovil.Devoluciones = 1;
		AlmacenMovil.Venta = 1;
		AlmacenMovil.Codigo = "2005";

		AlmacenMovil.TipoProceso = "SQL";
		AlmacenMovil.AgregarProceso("SQL.Agregar");
		AlmacenMovil.EjecutarProceso();

		AlmacenMovil.IdProducto = 2;
		AlmacenMovil.CantidadInicial = 2;
		AlmacenMovil.CantidadFinal = 2;
		AlmacenMovil.Devoluciones = 2;
		AlmacenMovil.Venta = 2;
		AlmacenMovil.Codigo = "2006";

		AlmacenMovil.TipoProceso = "SQL";
		AlmacenMovil.AgregarProceso("SQL.Agregar");
		AlmacenMovil.EjecutarProceso();

		AlmacenMovil.IdProducto = 3;
		AlmacenMovil.CantidadInicial = 3;
		AlmacenMovil.CantidadFinal = 3;
		AlmacenMovil.Devoluciones = 3;
		AlmacenMovil.Venta = 3;
		AlmacenMovil.Codigo = "2007";

		AlmacenMovil.TipoProceso = "SQL";
		AlmacenMovil.AgregarProceso("SQL.Agregar");
		AlmacenMovil.EjecutarProceso();

		AlmacenMovil.IdProducto = 4;
		AlmacenMovil.CantidadInicial = 4;
		AlmacenMovil.CantidadFinal = 4;
		AlmacenMovil.Devoluciones = 4;
		AlmacenMovil.Venta = 4;
		AlmacenMovil.Codigo = "2008";

		AlmacenMovil.TipoProceso = "SQL";
		AlmacenMovil.AgregarProceso("SQL.Agregar");
		AlmacenMovil.EjecutarProceso();

	}

}
