package com.comynt.launa.async;

import com.comynt.launa.*;
import com.System.Dispositivo.Info;
import com.System.Utils.Logg;
import com.System.Utils.Utils;

import BO.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import android.content.Context;

public class AsyncSincronizar extends Thread
{
	private Repositorio repositorio = Repositorio.getInstance();
	public IAsyncEvent evento;

	public boolean Clientes = true;
	public boolean AlmacenMovil = true;
	public boolean Precios = true;
	public boolean CategoriaCliente = true;
	public boolean Rutas = true;
	public boolean MotivosNoVenta = true;
	public boolean ChecklistVehiculo = true;
	public boolean CuestionarioCliente = true;
	public boolean Encuestas = true;
	public boolean Saldos = true;
	public boolean HistoricoVenta = true;

	public void DesmarcarTodo()
	{
		this.Clientes = false;
		this.AlmacenMovil = false;
		this.Precios = false;
		this.CategoriaCliente = false;
		this.Rutas = false;
		this.MotivosNoVenta = false;
		this.ChecklistVehiculo = false;
		this.CuestionarioCliente = false;
		this.Encuestas = false;
		this.Saldos = false;
		this.HistoricoVenta = false;
	}

	Context context;

	public AsyncSincronizar(Context context)
	{
		this.context = context;
	}

	public void run()
	{
		try
		{
			boolean pagina = true;
			int paginaNumber = 0;

			if (Info.ServicioDatosActivos(this.context))
			{
				this.repositorio.EnviarLogSincronizacion("Inicio de sincronización Versión " + repositorio.versionName);
			}
			
			try
			{
				this.repositorio.getClass(); this.repositorio.getClass(); this.repositorio.ConsultarVendedor(1, 3, this.repositorio.User, this.repositorio.Password);
				if (this.repositorio.lsVendedor != null)
				{
					if (this.repositorio.lsVendedor.size() > 0)
					{
						this.repositorio.Vendedor = ((BOVendedor)this.repositorio.lsVendedor.get(0));
					}
				}
			}
			catch (Exception localException1)
			{
			}
			
			
//			if (this.Clientes)
//			{
//				if (Info.ServicioDatosActivos(this.context))
//				{
//					this.repositorio.EliminarCliente();
//					while (pagina)
//					{
//						this.repositorio.getClass(); this.repositorio.getClass(); this.repositorio.ConsultarClientes(1, 3, 
//								String.valueOf(this.repositorio.Vendedor.IdEmpresa), String.valueOf(paginaNumber), "500");
//
//						if (this.repositorio.lsCliente != null)
//						{
//							if (this.repositorio.lsCliente.size() < 500)
//								pagina = false;
//							else
//								paginaNumber++;
//						}
//						else
//							pagina = false;
//					}
//					this.repositorio.lsCliente = null;
//					Logg.info("Descarga de clientes");
//				}
//			}
			
			if (this.Clientes)
			{
				if (Info.ServicioDatosActivos(this.context))
				{
					while (pagina)
					{
						this.repositorio.Cliente.Error = "";
						
						this.repositorio.ConsultarClientes(repositorio.ID_WS, repositorio.ID_GuardaMemoria,  
								String.valueOf(this.repositorio.Vendedor.IdEmpresa), String.valueOf(paginaNumber), "500");
						
						  if((paginaNumber == 0) &&  (this.repositorio.lsCliente != null) && (this.repositorio.Cliente.Error.equals("")))
						  {
							this.repositorio.EliminarCliente();
						  }
						  
						  if(this.repositorio.Cliente.Error.equals(""))
						  {
							repositorio.GuardarCliente();
						  }

						if (this.repositorio.lsCliente != null)
						{
							if (this.repositorio.lsCliente.size() < 500)
								pagina = false;
							else
								paginaNumber++;
						}
						else
							pagina = false;
					}
					this.repositorio.lsCliente = null;
					Logg.info("Descarga de clientes");
				}
			}
			
			

//			if (this.AlmacenMovil)
//			{
//				if (Info.ServicioDatosActivos(this.context))
//				{
//					this.repositorio.EliminarAlmacenMovil();
//
//					pagina = true;
//					paginaNumber = 0;
//					while (pagina)
//					{
//						this.repositorio.getClass(); this.repositorio.getClass(); this.repositorio.ConsultarAlmacenMovil(1, 3, 
//								String.valueOf(this.repositorio.Vendedor.IdVendedor), String.valueOf(paginaNumber), "500");
//						if (this.repositorio.lsAlmacenMovil != null)
//						{
//							if (this.repositorio.lsAlmacenMovil.size() < 500)
//								pagina = false;
//							else
//								paginaNumber++;
//						}
//						else
//							pagina = false;
//					}
//					this.repositorio.lsAlmacenMovil = null;
//					Logg.info("Descarga de almacen");
//				}
//			}
			
			if (this.AlmacenMovil)
			{
				if (Info.ServicioDatosActivos(this.context))
				{
					pagina = true;
					paginaNumber = 0;
					while (pagina)
					{
						this.repositorio.AlmacenMovil.Error = "";
						
						this.repositorio.ConsultarAlmacenMovil(repositorio.ID_WS, repositorio.ID_GuardaMemoria, 
								String.valueOf(this.repositorio.Vendedor.IdVendedor), String.valueOf(paginaNumber), "500");
						
						  if((paginaNumber == 0) &&  (this.repositorio.lsAlmacenMovil != null) && (this.repositorio.AlmacenMovil.Error.equals("")))
						  {
							this.repositorio.EliminarAlmacenMovil();
						  }
						  
						  if(this.repositorio.AlmacenMovil.Error.equals(""))
						  {
							repositorio.GuardarAlmacenMovil();
						  }
						
						if (this.repositorio.lsAlmacenMovil != null)
						{
							if (this.repositorio.lsAlmacenMovil.size() < 500)
								pagina = false;
							else
								paginaNumber++;
						}
						else
							pagina = false;
					}
					this.repositorio.lsAlmacenMovil = null;
					Logg.info("Descarga de almacen");
				}
			}
			
//			if (Info.ServicioDatosActivos(this.context))
//			{
//				this.repositorio.EliminarAlmacenCliente();
//
//				pagina = true;
//				paginaNumber = 0;
//				while (pagina)
//				{
//					this.repositorio.ConsultarAlmacenCliente(1, 3, 
//							String.valueOf(this.repositorio.Vendedor.IdVendedor), String.valueOf(paginaNumber), "500");
//
//					if (this.repositorio.lsAlmacenCliente != null)
//					{
//						if (this.repositorio.lsAlmacenCliente.size() < 500)
//							pagina = false;
//						else
//							paginaNumber++;
//					}
//					else
//						pagina = false;
//				}
//				this.repositorio.lsAlmacenCliente = null;
//				Logg.info("Descarga de almacen cliente");
//			}

			
			if (this.Precios)
			{
				if (Info.ServicioDatosActivos(this.context))
				{
					TraerProductosListas();
				}

			}
			

//			if (Info.ServicioDatosActivos(this.context))
//			{
//				try
//				{
//					this.repositorio.getClass(); this.repositorio.getClass(); this.repositorio.ConsultarPrecioCliente(1, 4);
//					if (this.repositorio.lsPrecioCliente != null)
//					{
//						this.repositorio.EliminarPrecioCliente();
//						this.repositorio.GuardarPrecioCliente();
//						Logg.info("Descarga de precios especiales");
//					}
//				}
//				catch (Exception localException2)
//				{
//				}
//			}
			
			if (Info.ServicioDatosActivos(this.context))
			{
				try
				{
					repositorio.PrecioCliente.Error = "";
					
					this.repositorio.ConsultarPrecioCliente(repositorio.ID_WS, repositorio.ID_GuardaMemoria);
					
					if ((this.repositorio.lsPrecioCliente != null) && (repositorio.PrecioCliente.Error.equals("")))
					{
						this.repositorio.EliminarPrecioCliente();
						this.repositorio.GuardarPrecioCliente();
						Logg.info("Descarga de precios especiales");
					}
				}
				catch (Exception localException2)
				{
				}
			}			
			
			
//			if (this.CategoriaCliente)
//			{
//				if (Info.ServicioDatosActivos(this.context))
//				{
//					this.repositorio.getClass(); this.repositorio.getClass(); this.repositorio.ConsultarCategoria(1, 4);
//					if (this.repositorio.lsCategoria != null)
//					{
//						this.repositorio.EliminarCategoria();
//						this.repositorio.GuardarCategoria();
//						Logg.info("Descarga de categoria");
//					}
//				}
//			}
			
			if (this.CategoriaCliente)
			{
				if (Info.ServicioDatosActivos(this.context))
				{
					repositorio.Categoria.Error = "";
					
					this.repositorio.ConsultarCategoria(repositorio.ID_WS, repositorio.ID_GuardaMemoria);
					
					if ((this.repositorio.lsCategoria != null) && (repositorio.Categoria.Error.equals("")))
					{
						this.repositorio.EliminarCategoria();
						this.repositorio.GuardarCategoria();
						Logg.info("Descarga de categoria");
					}
				}
			}
			
			

//			if (this.Rutas)
//			{
//				if (Info.ServicioDatosActivos(this.context))
//				{
//					this.repositorio.getClass(); this.repositorio.getClass(); this.repositorio.ConsultarRuta(1, 4);
//					if (this.repositorio.lsCategoria != null)
//					{
//						this.repositorio.EliminarRuta();
//						this.repositorio.GuardarRuta();
//						Logg.info("Descarga de ruta");
//					}
//				}
//			}
			
			if (this.Rutas)
			{
				if (Info.ServicioDatosActivos(this.context))
				{
					repositorio.Ruta.Error = "";
					
					this.repositorio.ConsultarRuta(repositorio.ID_WS, repositorio.ID_GuardaMemoria);
					
					if ((this.repositorio.lsRuta != null) && (repositorio.Ruta.Error.equals("")))
					{
						this.repositorio.EliminarRuta();
						this.repositorio.GuardarRuta();
						Logg.info("Descarga de ruta");
					}
				}
			}
			
			

//			if (this.MotivosNoVenta)
//			{
//				if (Info.ServicioDatosActivos(this.context))
//				{
//					this.repositorio.getClass(); this.repositorio.getClass(); this.repositorio.ConsultarPrecuestionario(1, 4);
//					if (this.repositorio.lsCategoria != null)
//					{
//						this.repositorio.EliminarPrecuestionario();
//						this.repositorio.GuardarPrecuestionario();
//						Logg.info("Descarga de precuestionario");
//					}
//				}
//			}
			
			if (this.MotivosNoVenta)
			{
				if (Info.ServicioDatosActivos(this.context))
				{
					repositorio.Precuestionario.Error = "";
					
					this.repositorio.ConsultarPrecuestionario(repositorio.ID_WS, repositorio.ID_GuardaMemoria);
					
					if ((this.repositorio.lsPrecuestionario != null) && (repositorio.Precuestionario.Error.equals("")))
					{
						this.repositorio.EliminarPrecuestionario();
						this.repositorio.GuardarPrecuestionario();
						Logg.info("Descarga de precuestionario");
					}
				}
			}
			
			

//			if (this.ChecklistVehiculo)
//			{
//				if (Info.ServicioDatosActivos(this.context))
//				{
//					this.repositorio.getClass(); this.repositorio.getClass(); this.repositorio.ConsultarVehiculo(1, 4);
//					if (this.repositorio.lsVehiculo != null)
//					{
//						this.repositorio.EliminarVehiculo();
//						this.repositorio.GuardarVehiculo();
//						Logg.info("Descarga de vehiculos");
//					}
//				}
//
//				if (Info.ServicioDatosActivos(this.context))
//				{
//					this.repositorio.getClass(); this.repositorio.getClass(); this.repositorio.ConsultarCheckVehiculo(1, 4);
//					if (this.repositorio.lsCheckVehiculo != null)
//					{
//						this.repositorio.EliminarCheckVehiculo();
//						this.repositorio.GuardarCheckVehiculo();
//						Logg.info("Descarga de vehiculos");
//					}
//				}
//			}
			
			if (this.ChecklistVehiculo)
			{
				if (Info.ServicioDatosActivos(this.context))
				{
					repositorio.Vehiculo.Error = "";
					
					this.repositorio.ConsultarVehiculo(repositorio.ID_WS, repositorio.ID_GuardaMemoria);
					
					if ((this.repositorio.lsVehiculo != null) && (repositorio.Vehiculo.Error.equals("")))
					{
						this.repositorio.EliminarVehiculo();
						this.repositorio.GuardarVehiculo();
						Logg.info("Descarga de vehiculos");
					}
				}

				if (Info.ServicioDatosActivos(this.context))
				{
					repositorio.CheckVehiculo.Error = "";
					
					this.repositorio.ConsultarCheckVehiculo(repositorio.ID_WS, repositorio.ID_GuardaMemoria);
					
					if ((this.repositorio.lsCheckVehiculo != null) && (repositorio.CheckVehiculo.Error.equals("")))
					{
						this.repositorio.EliminarCheckVehiculo();
						this.repositorio.GuardarCheckVehiculo();
						Logg.info("Descarga de vehiculos");
					}
				}
			}
			
			

//			if (this.CuestionarioCliente)
//			{
//				if (Info.ServicioDatosActivos(this.context))
//				{
//					this.repositorio.getClass(); this.repositorio.getClass(); this.repositorio.ConsultarCuestionarioCliente(1, 4);
//					if (this.repositorio.lsCuestionarioCliente != null)
//					{
//						this.repositorio.EliminarCuestionarioCliente();
//						this.repositorio.GuardarCuestionarioCliente();
//						Logg.info("Descarga de ConsultarCuestionarioCliente");
//					}
//				}
//
//				if (Info.ServicioDatosActivos(this.context))
//				{
//					this.repositorio.getClass(); this.repositorio.getClass(); this.repositorio.ConsultarInformacionCliente(1, 4);
//					if (this.repositorio.lsInformacionCliente != null)
//					{
//						if (this.repositorio.lsCuestionarioCliente != null)
//						{
//							for (int y = 0; y < this.repositorio.lsInformacionCliente.size(); y++)
//							{
//								this.repositorio.InformacionCliente = ((BOInformacionCliente)this.repositorio.lsInformacionCliente.get(y));
//								for (int x = 0; x < this.repositorio.lsCuestionarioCliente.size(); x++)
//								{
//									this.repositorio.CuestionarioCliente = ((BOCuestionarioCliente)this.repositorio.lsCuestionarioCliente.get(x));
//									if (this.repositorio.InformacionCliente.IdCuestionarioCliente != this.repositorio.CuestionarioCliente.IdCuestionarioCliente)
//										continue;
//									this.repositorio.InformacionCliente.Pregunta = this.repositorio.CuestionarioCliente.Pregunta;
//									this.repositorio.lsInformacionCliente.set(y, this.repositorio.InformacionCliente);
//								}
//							}
//						}
//						this.repositorio.EliminarInformacionCliente();
//						this.repositorio.GuardarInformacionCliente();
//						Logg.info("Descarga de ConsultarInformacionCliente");
//					}
//				}
//			}
			
			if (this.CuestionarioCliente)
			{
				if (Info.ServicioDatosActivos(this.context))
				{
					repositorio.CuestionarioCliente.Error = "";
					
					this.repositorio.ConsultarCuestionarioCliente(repositorio.ID_WS, repositorio.ID_GuardaMemoria);
					
					if ((this.repositorio.lsCuestionarioCliente != null) && (repositorio.CuestionarioCliente.Error.equals("")))
					{
						this.repositorio.EliminarCuestionarioCliente();
						this.repositorio.GuardarCuestionarioCliente();
						Logg.info("Descarga de ConsultarCuestionarioCliente");
					}
				}

				if (Info.ServicioDatosActivos(this.context))
				{
					repositorio.InformacionCliente.Error = "";
					
					this.repositorio.ConsultarInformacionCliente(repositorio.ID_WS, repositorio.ID_GuardaMemoria);
					
					if ((this.repositorio.lsInformacionCliente != null) && (repositorio.InformacionCliente.Error.equals("")))
					{
						if (this.repositorio.lsCuestionarioCliente != null)
						{
							for (int y = 0; y < this.repositorio.lsInformacionCliente.size(); y++)
							{
								this.repositorio.InformacionCliente = ((BOInformacionCliente)this.repositorio.lsInformacionCliente.get(y));
								for (int x = 0; x < this.repositorio.lsCuestionarioCliente.size(); x++)
								{
									this.repositorio.CuestionarioCliente = ((BOCuestionarioCliente)this.repositorio.lsCuestionarioCliente.get(x));
									if (this.repositorio.InformacionCliente.IdCuestionarioCliente != this.repositorio.CuestionarioCliente.IdCuestionarioCliente)
										continue;
									this.repositorio.InformacionCliente.Pregunta = this.repositorio.CuestionarioCliente.Pregunta;
									this.repositorio.lsInformacionCliente.set(y, this.repositorio.InformacionCliente);
								}
							}
						}
						this.repositorio.EliminarInformacionCliente();
						this.repositorio.GuardarInformacionCliente();
						Logg.info("Descarga de ConsultarInformacionCliente");
					}
				}
			}
			
			
			
//			if (this.Encuestas)
//			{
//				if (Info.ServicioDatosActivos(this.context))
//				{
//					this.repositorio.getClass(); this.repositorio.getClass(); this.repositorio.ConsultarCuestionario(1, 4);
//					if (this.repositorio.lsCuestionario != null)
//					{
//						this.repositorio.EliminarCuestionario();
//						this.repositorio.GuardarCuestionario();
//						Logg.info("Descarga de ConsultarCuestionario");
//					}
//				}
//
//				if (Info.ServicioDatosActivos(this.context))
//				{
//					this.repositorio.getClass(); this.repositorio.getClass(); this.repositorio.ConsultarPreguntaCuestionario(1, 4);
//					if (this.repositorio.lsPreguntaCuestionario != null)
//					{
//						this.repositorio.EliminarPreguntaCuestionario();
//						this.repositorio.GuardarPreguntaCuestionario();
//						Logg.info("Descarga de ConsultarPreguntaCuestionario");
//					}
//				}
//			}
			
			if (this.Encuestas)
			{
				if (Info.ServicioDatosActivos(this.context))
				{
					repositorio.Cuestionario.Error = "";
					
					this.repositorio.ConsultarCuestionario(repositorio.ID_WS, repositorio.ID_GuardaMemoria);
					
					if ((this.repositorio.lsCuestionario != null) && (repositorio.Cuestionario.Error.equals("")))
					{
						this.repositorio.EliminarCuestionario();
						this.repositorio.GuardarCuestionario();
						Logg.info("Descarga de ConsultarCuestionario");
					}
				}

				if (Info.ServicioDatosActivos(this.context))
				{
					repositorio.PreguntaCuestionario.Error = "";
					
					this.repositorio.ConsultarPreguntaCuestionario(repositorio.ID_WS, repositorio.ID_GuardaMemoria);
					
					if ((this.repositorio.lsPreguntaCuestionario != null) && (repositorio.PreguntaCuestionario.Error.equals("")))
					{
						this.repositorio.EliminarPreguntaCuestionario();
						this.repositorio.GuardarPreguntaCuestionario();
						Logg.info("Descarga de ConsultarPreguntaCuestionario");
					}
				}
			}
			
			

//			if (this.Saldos)
//			{
//				if (Info.ServicioDatosActivos(this.context))
//				{
//					this.repositorio.getClass(); this.repositorio.getClass(); this.repositorio.ConsultarSaldoCliente(1, 4);
//					if (this.repositorio.lsSaldoCliente != null)
//					{
//						this.repositorio.EliminarSaldoCliente();
//						this.repositorio.GuardarSaldoCliente();
//						Logg.info("Descarga de ConsultarSaldoCliente");
//					}
//				}
//
//				if (Info.ServicioDatosActivos(this.context))
//				{
//					this.repositorio.getClass(); this.repositorio.getClass(); this.repositorio.ConsultarSaldoClienteNota(1, 4);
//					if (this.repositorio.lsSaldoClienteNota != null)
//					{
//						this.repositorio.EliminarSaldoClienteNota();
//						this.repositorio.GuardarSaldoClienteNota();
//						Logg.info("Descarga de ConsultarSaldoClienteNota");
//					}
//				}
//			}
			
			if (this.Saldos)
			{
				if (Info.ServicioDatosActivos(this.context))
				{
					repositorio.SaldoCliente.Error = "";
					
					this.repositorio.ConsultarSaldoCliente(repositorio.ID_WS, repositorio.ID_GuardaMemoria);
					
					if ((this.repositorio.lsSaldoCliente != null) && (repositorio.SaldoCliente.Error.equals("")))
					{
						this.repositorio.EliminarSaldoCliente();
						this.repositorio.GuardarSaldoCliente();
						Logg.info("Descarga de ConsultarSaldoCliente");
					}
				}

				if (Info.ServicioDatosActivos(this.context))
				{
					repositorio.SaldoClienteNota.Error = "";
					
					this.repositorio.ConsultarSaldoClienteNota(repositorio.ID_WS, repositorio.ID_GuardaMemoria);
					
					if ((this.repositorio.lsSaldoClienteNota != null) && (repositorio.SaldoClienteNota.Error.equals("")))
					{
						this.repositorio.EliminarSaldoClienteNota();
						this.repositorio.GuardarSaldoClienteNota();
						Logg.info("Descarga de ConsultarSaldoClienteNota");
					}
				}
			}
			

			
//			if (this.HistoricoVenta)
//			{
//				if (Info.ServicioDatosActivos(this.context))
//				{
//					this.repositorio.getClass(); this.repositorio.getClass(); this.repositorio.ConsultarHistoricoVentaDiaria(1, 4);
//					if (this.repositorio.lsHistoricoVentaDiaria != null)
//					{
//						this.repositorio.getClass(); this.repositorio.getClass(); this.repositorio.ConsultarProducto(2, 4, 
//								String.valueOf(this.repositorio.Vendedor.IdVendedor), "0", "0");
//
//						if (this.repositorio.lsProducto != null)
//						{
//							for (int x = 0; x < this.repositorio.lsHistoricoVentaDiaria.size(); x++)
//							{
//								this.repositorio.HistoricoVentaDiaria = ((BOHistoricoVentaDiaria)this.repositorio.lsHistoricoVentaDiaria.get(x));
//								for (int y = 0; y < this.repositorio.lsProducto.size(); y++)
//								{
//									this.repositorio.Producto = ((BOProducto)this.repositorio.lsProducto.get(y));
//									if (this.repositorio.HistoricoVentaDiaria.IdProducto != this.repositorio.Producto.IdProducto)
//										continue;
//									this.repositorio.HistoricoVentaDiaria.Descripcion = this.repositorio.Producto.Descripcion;
//									this.repositorio.HistoricoVentaDiaria.Codigo = this.repositorio.Producto.Codigo;
//									this.repositorio.HistoricoVentaDiaria.Unidad = this.repositorio.Producto.Unidad;
//									try
//									{
//										this.repositorio.lsHistoricoVentaDiaria.set(x, this.repositorio.HistoricoVentaDiaria);
//									}
//									catch (Exception localException3)
//									{
//									}
//								}
//							}
//						}
//						this.repositorio.EliminarHistoricoVentaDiaria();
//						this.repositorio.GuardarHistoricoVentaDiaria();
//						Logg.info("Descarga de ConsultarHistoricoVentaDiaria");
//					}
//				}
//			}

			
			this.repositorio.lsProducto = null;

			this.repositorio.lsProductoPedido = null;

			try
			{
				this.repositorio.DepurarNotas();
			}
			catch (Exception ex)
			{}

			

//			try
//			{
//				if (Info.ServicioDatosActivos(this.context))
//				{
//					this.repositorio.getClass(); this.repositorio.getClass(); this.repositorio.ConsultarConfiguraImpresion(1, 4);
//					if (this.repositorio.lsConfiguraImpresion != null)
//					{
//						this.repositorio.EliminarConfiguraImpresion();
//						this.repositorio.GuardarConfiguraImpresion();
//						Logg.info("Descarga de ConfiguraImpresion");
//					}
//					repositorio.EstableceParametrosImpresion();
//				}
//			}
//			catch(Exception ex){}
			
			try
			{
				if (Info.ServicioDatosActivos(this.context))
				{
					repositorio.ConfiguraImpresion.Error = "";
					
					this.repositorio.ConsultarConfiguraImpresion(repositorio.ID_WS, repositorio.ID_GuardaMemoria);
					
					if ((this.repositorio.lsConfiguraImpresion != null) && (repositorio.ConfiguraImpresion.Error.equals("")))
					{
						this.repositorio.EliminarConfiguraImpresion();
						this.repositorio.GuardarConfiguraImpresion();
						Logg.info("Descarga de ConfiguraImpresion");
					}
					repositorio.EstableceParametrosImpresion();
				}
			}
			catch(Exception ex){}			


			
//			try
//			{
//				if (Info.ServicioDatosActivos(this.context))
//				{
//					this.repositorio.EliminarPrecioRango();
//
//					pagina = true;
//					paginaNumber = 0;
//					while (pagina)
//					{
//						this.repositorio.getClass(); this.repositorio.getClass();
//
//						this.repositorio.ConsultarPrecioRango(repositorio.ID_WS, repositorio.ID_GuardaSQL, 
//								String.valueOf(paginaNumber), "500");
//
//						if (this.repositorio.lsPrecioRango != null)
//						{
//							if (this.repositorio.lsPrecioRango.size() < 500)
//								pagina = false;
//							else
//								paginaNumber++;
//						}
//						else
//							pagina = false;
//					}
//					this.repositorio.lsPrecioRango = null;
//					Logg.info("Descarga de PrecioRango");
//				}
//			}
//			catch(Exception ex){}
			
			try
			{
				if (Info.ServicioDatosActivos(this.context))
				{
					pagina = true;
					paginaNumber = 0;
					while (pagina)
					{
						this.repositorio.PrecioRango.Error = "";

						this.repositorio.ConsultarPrecioRango(repositorio.ID_WS, repositorio.ID_GuardaMemoria, 
								String.valueOf(paginaNumber), "500");

						  if((paginaNumber == 0) &&  (this.repositorio.lsPrecioRango != null) && (this.repositorio.PrecioRango.Error.equals("")))
						  {
							this.repositorio.EliminarPrecioRango();
						  }
						  
						  if(this.repositorio.PrecioRango.Error.equals(""))
						  {
							repositorio.GuardarPrecioRango();
						  }
						
						if (this.repositorio.lsPrecioRango != null)
						{
							if (this.repositorio.lsPrecioRango.size() < 500)
								pagina = false;
							else
								paginaNumber++;
						}
						else
							pagina = false;
					}
					this.repositorio.lsPrecioRango = null;
					Logg.info("Descarga de PrecioRango");
				}
			}
			catch(Exception ex){}
			


//			try
//			{
//				if (Info.ServicioDatosActivos(this.context))
//				{
//					this.repositorio.getClass(); this.repositorio.getClass(); this.repositorio.ConsultarPedidoNota(repositorio.ID_WS, repositorio.ID_GuardaMemoria);
//					if (this.repositorio.lsPedidoNota != null)
//					{
//						this.repositorio.EliminarPedidoNota();
//						this.repositorio.GuardarPedidoNota();
//						Logg.info("Descarga de Pedido Nota");
//					}
//				}
//			}
//			catch(Exception ex){}

//			try
//			{
//				if (Info.ServicioDatosActivos(this.context))
//				{
//					this.repositorio.getClass(); this.repositorio.getClass(); this.repositorio.ConsultarPedidoNotaDetalle(repositorio.ID_WS, repositorio.ID_GuardaMemoria);
//					if (this.repositorio.lsPedidoNotaDetalle != null)
//					{
//						this.repositorio.EliminarPedidoNotaDetalle();
//						this.repositorio.GuardarPedidoNotaDetalle();
//						Logg.info("Descarga de Pedido Nota Detalle");
//					}
//				}
//			}
//			catch(Exception ex){}


			this.repositorio.FechaCliente = Utils.getFechaActual_DD_MM_YYYY();

			repositorio.estaSincronizando = false;
			repositorio.estaSincronizandoAutomatico = false;

			if (Info.ServicioDatosActivos(this.context))
			{
				this.repositorio.EnviarLogSincronizacion("Fin de sincronización");
			}

		}
		catch (Exception ex)
		{
			repositorio.estaSincronizando = false;
			repositorio.estaSincronizandoAutomatico = false;
			
			String Error = ex.getMessage();
			Logg.info("AsyncError: " + Error);
		}
		
		repositorio.estaSincronizando = false;
		repositorio.estaSincronizandoAutomatico = false;

		if (this.evento != null)
			this.evento.Event("End", "");
	}

//	private void TraerProductosListas()
//	{
//		boolean pagina = true;
//		int paginaNumber = 0;
//
//		if (Info.ServicioDatosActivos(this.context))
//		{
//			if (this.repositorio.lsProducto == null)
//			{
//				this.repositorio.getClass(); this.repositorio.getClass(); this.repositorio.ConsultarProducto(2, 4, 
//						String.valueOf(this.repositorio.Vendedor.IdVendedor), "0", "0");
//				if (this.repositorio.lsProducto == null)
//				{
//					this.repositorio.EliminarProducto();
//
//					pagina = true;
//					paginaNumber = 0;
//					while (pagina)
//					{
//						this.repositorio.getClass(); this.repositorio.getClass(); this.repositorio.ConsultarProducto(1, 3, 
//								String.valueOf(this.repositorio.Vendedor.IdEmpresa), String.valueOf(paginaNumber), "500");
//						if (this.repositorio.lsProducto != null)
//						{
//							if (this.repositorio.lsProducto.size() < 500)
//								pagina = false;
//							else
//								paginaNumber++;
//						}
//						else
//							pagina = false;
//					}
//					this.repositorio.lsProducto = null;
//					Logg.info("Descarga de productos");
//				}
//			}
//
//		}
//
//		if (Info.ServicioDatosActivos(this.context))
//		{
//			this.repositorio.getClass(); this.repositorio.getClass(); this.repositorio.ConsultarListaPrecio(1, 4, 
//					String.valueOf(this.repositorio.Vendedor.IdEmpresa));
//
//			if (this.repositorio.lsListaPrecio != null)
//			{
//				this.repositorio.EliminarListaPrecio();
//				this.repositorio.GuardarListaPrecio();
//				Logg.info("Descarga de lista de precio");
//
//				this.repositorio.EliminarProductoListaPrecio();
//
//				for (int x = 0; x < this.repositorio.lsListaPrecio.size(); x++)
//				{
//					this.repositorio.ListaPrecio = ((BOListaPrecio)this.repositorio.lsListaPrecio.get(x));
//
//					pagina = true;
//					paginaNumber = 0;
//					while (pagina)
//					{
//						this.repositorio.getClass(); this.repositorio.getClass(); this.repositorio.ConsultarProductoListaPrecio(1, 3, 
//								String.valueOf(this.repositorio.ListaPrecio.IdListaPrecio), String.valueOf(paginaNumber), "500");
//
//						if (this.repositorio.lsProductoListaPrecio != null)
//						{
//							if (this.repositorio.lsProductoListaPrecio.size() < 500)
//								pagina = false;
//							else
//								paginaNumber++;
//						}
//						else
//							pagina = false;
//					}
//					Logg.info("Descarga de productos por lista de precios");
//				}
//			}
//
//		}
//
//		this.repositorio.lsListaPrecio = null;
//		this.repositorio.lsCliente = null;
//	}
	
	private void TraerProductosListas()
	{
		boolean pagina = true;
		int paginaNumber = 0;

		if (Info.ServicioDatosActivos(this.context))
		{
				this.repositorio.ConsultarProducto(2, 4, 
						String.valueOf(this.repositorio.Vendedor.IdVendedor), "0", "0");
				
				if (this.repositorio.lsProducto == null)
				{
					pagina = true;
					paginaNumber = 0;
					while (pagina)
					{
						repositorio.Producto.Error = "";
						
						this.repositorio.ConsultarProducto(repositorio.ID_WS, repositorio.ID_GuardaMemoria,  
								String.valueOf(this.repositorio.Vendedor.IdEmpresa), String.valueOf(paginaNumber), "500");
						
						  if((paginaNumber == 0) &&  (this.repositorio.lsProducto != null) && (this.repositorio.Producto.Error.equals("")))
						  {
							this.repositorio.EliminarProducto();
						  }

						  if(this.repositorio.Producto.Error.equals(""))
						  {
							repositorio.GuardarProducto();
						  }
						
						if (this.repositorio.lsProducto != null)
						{
							if (this.repositorio.lsProducto.size() < 500)
								pagina = false;
							else
								paginaNumber++;
						}
						else
							pagina = false;
					}
					this.repositorio.lsProducto = null;
					Logg.info("Descarga de productos");
				}
		}

		if (Info.ServicioDatosActivos(this.context))
		{
			this.repositorio.ConsultarListaPrecio(1, 4, 
					String.valueOf(this.repositorio.Vendedor.IdEmpresa));

			if ((this.repositorio.lsListaPrecio != null) && (repositorio.ListaPrecio.Error.equals("")))
			{
				this.repositorio.EliminarListaPrecio();
				this.repositorio.GuardarListaPrecio();
				Logg.info("Descarga de lista de precio");

				for (int x = 0; x < this.repositorio.lsListaPrecio.size(); x++)
				{
					this.repositorio.ListaPrecio = ((BOListaPrecio)this.repositorio.lsListaPrecio.get(x));

					pagina = true;
					paginaNumber = 0;
					while (pagina)
					{
						this.repositorio.ProductoListaPrecio.Error = "";
						
						this.repositorio.ConsultarProductoListaPrecio(repositorio.ID_WS, repositorio.ID_GuardaMemoria,  
								String.valueOf(this.repositorio.ListaPrecio.IdListaPrecio), String.valueOf(paginaNumber), "500");

						  if((paginaNumber == 0) &&  (this.repositorio.lsProductoListaPrecio != null) && (this.repositorio.ProductoListaPrecio.Error.equals("")))
						  {
							this.repositorio.EliminarProductoListaPrecio();
						  }

						  if(this.repositorio.ProductoListaPrecio.Error.equals(""))
						  {
							repositorio.GuardarProductoListaPrecio();
						  }
						
						if (this.repositorio.lsProductoListaPrecio != null)
						{
							if (this.repositorio.lsProductoListaPrecio.size() < 500)
								pagina = false;
							else
								paginaNumber++;
						}
						else
							pagina = false;
					}
					Logg.info("Descarga de productos por lista de precios");
				}
			}

		}

		this.repositorio.lsListaPrecio = null;
		this.repositorio.lsCliente = null;
	}
}


