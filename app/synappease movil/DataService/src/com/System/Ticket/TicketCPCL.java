package com.System.Ticket;

import BO.BOAlmacenMovil;
import BO.BONota;
import BO.BONotaDetalle;

import com.System.Utils.Logg;
import com.System.Utils.Utils;
import com.comynt.launa.Repositorio;

public class TicketCPCL 
{
	Repositorio repositorio = Repositorio.getInstance();

	public TicketCPCL()
	{
	}

	//obtiene el ticket de la venta ya sea al contado o credito
	public String ObtenerTicketVenta( double total, String Fecha, String Hora )
	{
		int alturaproducto = repositorio.productos.size() * 60;
		String Imprecion = "";

		if (repositorio.Cliente.Discriminante.equals("CREDITO"))
		{
			try
			{
				Imprecion = Imprecion + "! 0 0 200 " + (1650 + alturaproducto) + " 1\r\n";

				Imprecion = Imprecion + "CENTER\r\n";

				//	/* 1010 */         Imprecion = Imprecion + "TEXT 5 2 5 20 BACHOCO, S.A. de C.V.\r\n";

				Imprecion = Imprecion + "TEXT 5 2 5 20 BACHOCO, S.A. de C.V.\r\n";
				Imprecion = Imprecion + "TEXT 5 2 5 60 BAC800208B25\r\n";

				Imprecion = Imprecion + "SETMAG 1 1\r\n";

				if( repositorio.TITULO1.equals("") )
					Imprecion = Imprecion + "TEXT 7 0 5 110 SUC. LA PAZ, BCS\r\n";
				else
					Imprecion = Imprecion + "TEXT 7 0 5 110 " + repositorio.TITULO1 + "\r\n";

				if( repositorio.DIRECCION1.equals("") )
					Imprecion = Imprecion + "TEXT 7 0 5 135 CALZADA A. OLACHEA #4915\r\n";
				else
					Imprecion = Imprecion + "TEXT 7 0 5 135 " + repositorio.DIRECCION1 + "\r\n";

				if( repositorio.DIRECCION2.equals("") )
					Imprecion = Imprecion + "TEXT 7 0 5 160 COL. PUESTA DEL SOL I C.P. 23090\r\n";
				else
					Imprecion = Imprecion + "TEXT 7 0 5 160 " + repositorio.DIRECCION2 + "\r\n";

				if( repositorio.DIRECCION2.equals("") )
					Imprecion = Imprecion + "TEXT 7 0 5 185 LA PAZ, BCS. MEX TEL: (612)124-01-67\r\n";
				else
					Imprecion = Imprecion + "TEXT 7 0 5 185 " + repositorio.TELEFONO + "\r\n";

				Imprecion = Imprecion + "SETMAG 1 2\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 210 RUTA " + repositorio.Vendedor.Nombre + " " + repositorio.FolioNota + "\r\n";
				Imprecion = Imprecion + "SETMAG 1 1\r\n";
				Imprecion = Imprecion + "LEFT\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 270 FECHA " + Fecha + "\r\n";
				Imprecion = Imprecion + "RIGHT\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 270 HORA: " + Hora + "  \r\n";
				Imprecion = Imprecion + "CENTER\r\n";
				Imprecion = Imprecion + "SETMAG 1 2\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 315 VENTA CREDITO\r\n";
				Imprecion = Imprecion + "TEXT 0 3 5 365 ---------------------------\r\n";
				Imprecion = Imprecion + "LEFT\r\n";
				Imprecion = Imprecion + "TEXT 0 3 5 565 ----------------------------------------------------------------------\r\n";
				Imprecion = Imprecion + "LEFT\r\n";
				Imprecion = Imprecion + "SETMAG 1 1\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 380 CLIENTE " + repositorio.Itinerario.Codigo + "\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 405 NOMBRE  " + repositorio.Itinerario.Cliente + "\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 430 RFC     " + repositorio.Itinerario.RazonSocial + "\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 455 " + repositorio.Itinerario.Direccion + "\r\n";
				Imprecion = Imprecion + "LEFT\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 500 SKU     DESCRIPCIÓN\r\n";
				Imprecion = Imprecion + "LEFT\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 540 P. UNIT.\r\n";
				Imprecion = Imprecion + "RIGHT\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 540 CANTIDAD     UMB       TOTAL  \r\n";

				boolean existeEmbalaje = false;
				int sumaProducto = 0;

				for (int x = 0; x < repositorio.productos.size(); x++)
				{
					BONotaDetalle producto = (BONotaDetalle)repositorio.productos.elementAt(x);

					if (!producto.Envase)
					{
						Imprecion = Imprecion + "LEFT\r\n";
						Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(580 + sumaProducto * 70) + " " + producto.CodigoBarra + " " + producto.Nombre + "\r\n";
						Imprecion = Imprecion + "LEFT\r\n";
						Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(605 + sumaProducto * 70) + " $" + Utils.leftPad(Utils.toMoneyFormat(producto.Precio), " ", 6) + "\r\n";
						Imprecion = Imprecion + "RIGHT\r\n";
						Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(605 + sumaProducto * 70) + " " + Utils.leftPad(Utils.toMoneyFormat(producto.Cantidad), " ", 8) + " " + Utils.leftPad(producto.UnidadMedida, " ", 8) + " $" + Utils.leftPad(Utils.toMoneyFormat(producto.Precio * producto.Cantidad), " ", 10) + "  \r\n";
						sumaProducto++;
					}
					else {
						existeEmbalaje = true;
					}
				}
				alturaproducto = sumaProducto * 50;

				double total2 = Double.parseDouble(Utils.RedondearValor((float)total));
				String centavo = Utils.Split(Utils.toMoneyFormat((float)total), ".")[1];

				Imprecion = Imprecion + "SETMAG 1 2\r\n";
				Imprecion = Imprecion + "LEFT\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(645 + alturaproducto) + " TOTAL \r\n";
				Imprecion = Imprecion + "RIGHT\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(645 + alturaproducto) + " $" + Utils.leftPad(Utils.toMoneyFormat((float)total), " ", 12) + "  \r\n";

				Imprecion = Imprecion + "SETMAG 1 1\r\n";
				Imprecion = Imprecion + "LEFT\r\n";

				String cantidad2 = Utils.Split(Utils.ConvertNumberToLetter(total2), "CON")[0];

				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(700 + alturaproducto) + " " + cantidad2 + "\r\n";

				Imprecion = Imprecion + "LEFT\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(740 + alturaproducto) + " " + centavo + "/100 M.N.\r\n";

				Imprecion = Imprecion + "SETMAG 1 1\r\n";
				Imprecion = Imprecion + "CENTER\r\n";


				if( repositorio.ENCUESTA.equals("") )
				{
					Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(780 + alturaproducto) + " ***SI NO LE ENTREGAN TICKET***\r\n";
					Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(805 + alturaproducto) + " ***SU COMPRA ES GRATIS***\r\n";
				}
				else
					Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(780 + alturaproducto) + " " + repositorio.ENCUESTA + "\r\n";


				int sumaEmbalaje = 0;

				if (existeEmbalaje)
				{
					Imprecion = Imprecion + "SETMAG 1 2\r\n";
					Imprecion = Imprecion + "CENTER\r\n";
					Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(820 + alturaproducto) + " EMBALAJE\r\n";

					Imprecion = Imprecion + "TEXT 0 3 5 " + String.valueOf(860 + alturaproducto) + " ----------------------------------------------------------------------\r\n";

					Imprecion = Imprecion + "SETMAG 1 1\r\n";

					Imprecion = Imprecion + "LEFT\r\n";
					Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(880 + alturaproducto) + " SKU     DESCRIPCIÓN\r\n";
					Imprecion = Imprecion + "LEFT\r\n";
					Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(920 + alturaproducto) + " INICIAL\r\n";
					Imprecion = Imprecion + "RIGHT\r\n";

					Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(920 + alturaproducto) + " ENTREGA   DEVOLUCIÓN   FINAL \r\n";

					for (int x = 0; x < repositorio.productos.size(); x++)
					{
						BONotaDetalle producto = (BONotaDetalle)repositorio.productos.elementAt(x);

						if (!producto.Envase)
							continue;
						Imprecion = Imprecion + "LEFT\r\n";
						Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(940 + alturaproducto + sumaEmbalaje * 70) + " " + producto.CodigoBarra + " " + producto.Nombre + "\r\n";
						Imprecion = Imprecion + "LEFT\r\n";
						Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(965 + alturaproducto + sumaEmbalaje * 70) + " 0\r\n";
						Imprecion = Imprecion + "RIGHT\r\n";
						Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(965 + alturaproducto + sumaEmbalaje * 70) + " " + Utils.leftPad(Utils.toMoneyFormat(producto.Cantidad), " ", 8) + " " + Utils.leftPad(producto.UnidadMedida, " ", 8) + " " + Utils.leftPad(Utils.toMoneyFormat(producto.Cantidad), " ", 8) + "  \r\n";
						sumaEmbalaje++;
					}

					sumaEmbalaje = 150 + sumaEmbalaje * 50;
				}

				Imprecion = Imprecion + "SETMAG 1 2\r\n";
				Imprecion = Imprecion + "CENTER\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(835 + alturaproducto + sumaEmbalaje) + " PAGARE\r\n";

				Imprecion = Imprecion + "SETMAG 1 1\r\n";
				Imprecion = Imprecion + "LEFT\r\n";


				if( repositorio.PAGARE1.equals("") )
					Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(900 + alturaproducto + sumaEmbalaje) + " EN CALZADA A. OLACHEA #4915, PUESTA DEL SOL I\r\n";
				else
					Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(900 + alturaproducto + sumaEmbalaje) + " " + repositorio.PAGARE1 + "\r\n";

				if( repositorio.PAGARE2.equals("") )
					Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(925 + alturaproducto + sumaEmbalaje) + " C.P. 23090, LA PAZ BCS A " + Utils.getFechaActual_DD_MM_YYYY() + "\r\n";
				else
					Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(925 + alturaproducto + sumaEmbalaje) + " " + repositorio.PAGARE2 + " " + Utils.getFechaActual_DD_MM_YYYY() + "\r\n";

				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(950 + alturaproducto + sumaEmbalaje) + " (" + Utils.ConvertIntegerToLetter(Utils.getDiaActual()) + "DE " + Utils.getMesNombre() + " DEL " + Utils.ConvertIntegerToLetter(Utils.getAnioActual()) + ")\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(975 + alturaproducto + sumaEmbalaje) + " DEBO(EMOS) Y PAGARE(MOS)INCONDICIONALMENTE \r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1000 + alturaproducto + sumaEmbalaje) + " POR ESTE PAGARÉ AL ORDEN DE BACHOCO, S.A. \r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1025 + alturaproducto + sumaEmbalaje) + " DOS DE JUNIO DEL DOS MIL DOCE) LA CANTIDAD \r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1050 + alturaproducto + sumaEmbalaje) + " DE: $" + Utils.toMoneyFormat((float)total) + " (" + cantidad2 + "\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1075 + alturaproducto + sumaEmbalaje) + " " + centavo + "/100 M.N. ) VALOR RECIBIDO A \r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1100 + alturaproducto + sumaEmbalaje) + " MI(NUESTRA) SATISFACCIÓN. EN CASO DE NO \r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1125 + alturaproducto + sumaEmbalaje) + " PAGARSE A LA FECHA DE VENCIMIENTO ME OBLIGO \r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1150 + alturaproducto + sumaEmbalaje) + " A PAGAR UN INTERÉS MORATORIO EQUIVALENTE AL 3% \r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1175 + alturaproducto + sumaEmbalaje) + " MENSUAL A PARTIR DE SU VENCIMIENTO. SERÁ \r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1200 + alturaproducto + sumaEmbalaje) + " EXIGIBLES ESTE Y TODOS AQUELLOS QUE EXCEDAN \r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1225 + alturaproducto + sumaEmbalaje) + " LA FECHA DE VENCIMIENTO.\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1250 + alturaproducto + sumaEmbalaje) + " " + this.repositorio.Itinerario.Codigo + " " + this.repositorio.Itinerario.Cliente + "\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1275 + alturaproducto + sumaEmbalaje) + " RFC " + this.repositorio.Itinerario.RazonSocial + "\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1300 + alturaproducto + sumaEmbalaje) + " " + this.repositorio.Itinerario.Direccion + "\r\n";						

				//	/* 1142 */         Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(950 + alturaproducto + sumaEmbalaje) + " (" + Utils.ConvertIntegerToLetter(Utils.getDiaActual()) + 
				//						"DE " + Utils.getMesNombre() + 
				//						" DEL " + Utils.ConvertIntegerToLetter(Utils.getAnioActual()) + ")\r\n";
				//	/* 1143 */         Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(975 + alturaproducto + sumaEmbalaje) + " DEBO(EMOS) Y PAGARE(MOS)INCONDICIONALMENTE \r\n";
				//	/* 1144 */         Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1000 + alturaproducto + sumaEmbalaje) + " POR ESTE PAGARÉ AL ORDEN DE DESARROLLO DE \r\n";
				//	/* 1145 */         Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1025 + alturaproducto + sumaEmbalaje) + " ALIMENTOS DE MEXICO, S.A. DE C.V. LA CANTIDAD\r\n";
				//	/* 1146 */         Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1050 + alturaproducto + sumaEmbalaje) + " DE: $" + Utils.toMoneyFormat((float)total) + " (" + cantidad2 + "\r\n";
				//	/* 1147 */         Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1075 + alturaproducto + sumaEmbalaje) + " " + centavo + "/100 M.N. ) VALOR RECIBIDO A \r\n";
				//	/* 1148 */         Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1100 + alturaproducto + sumaEmbalaje) + " MI(NUESTRA) SATISFACCIÓN. EN CASO DE NO \r\n";
				//	/* 1149 */         Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1125 + alturaproducto + sumaEmbalaje) + " PAGARSE A LA FECHA DE VENCIMIENTO ME OBLIGO \r\n";
				//	/* 1150 */         Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1150 + alturaproducto + sumaEmbalaje) + " A PAGAR UN INTERÉS MORATORIO EQUIVALENTE AL 3% \r\n";
				//	/* 1151 */         Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1175 + alturaproducto + sumaEmbalaje) + " MENSUAL A PARTIR DE SU VENCIMIENTO. SERÁ \r\n";
				//	/* 1152 */         Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1200 + alturaproducto + sumaEmbalaje) + " EXIGIBLES ESTE Y TODOS AQUELLOS QUE EXCEDAN \r\n";
				//	/* 1153 */         Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1225 + alturaproducto + sumaEmbalaje) + " LA FECHA DE VENCIMIENTO.\r\n";
				//	
				//	/* 1155 */         Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1250 + alturaproducto + sumaEmbalaje) + " " + repositorio.Itinerario.Codigo + " " + repositorio.Itinerario.Cliente + "\r\n";
				//	/* 1156 */         Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1275 + alturaproducto + sumaEmbalaje) + " RFC " + repositorio.Itinerario.RazonSocial + "\r\n";
				//	/* 1157 */         Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1300 + alturaproducto + sumaEmbalaje) + " " + repositorio.Itinerario.Direccion + "\r\n";


				Imprecion = Imprecion + "SETMAG 1 2\r\n";
				Imprecion = Imprecion + "CENTER\r\n";

				Imprecion = Imprecion + "LINE 0 " + String.valueOf(1450 + alturaproducto + sumaEmbalaje) + " 400 " + String.valueOf(1450 + alturaproducto + sumaEmbalaje) + " 4\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1470 + alturaproducto + sumaEmbalaje) + " ACEPTO(AMOS)\r\n";
				Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(1510 + alturaproducto + sumaEmbalaje) + " NOMBRE Y FIRMA\r\n");

				Imprecion = Imprecion + "SETMAG 0 0\r\n";
				Imprecion = Imprecion + "FORM\r\n";
				Imprecion = Imprecion + "PRINT\r\n";
			}
			catch (Exception ex)
			{
				Imprecion = "";
			}
		}
		else
		{
			try
			{
				Imprecion = Imprecion + "! 0 0 200 " + (870 + alturaproducto) + " 1\r\n";

				Imprecion = Imprecion + "CENTER\r\n";

				//	/* 1181 */         Imprecion = Imprecion + "TEXT 5 2 5 20 LA UNA\r\n";

				Imprecion = Imprecion + "TEXT 5 2 5 20 BACHOCO, S.A. de C.V.\r\n";
				Imprecion = Imprecion + "TEXT 5 2 5 60 BAC800208B25\r\n";

				Imprecion = Imprecion + "SETMAG 1 1\r\n";

				if( repositorio.TITULO1.equals("") )
					Imprecion = Imprecion + "TEXT 7 0 5 110 SUC. LA PAZ, BCS\r\n";
				else
					Imprecion = Imprecion + "TEXT 7 0 5 110 " + repositorio.TITULO1 + "\r\n";

				if( repositorio.DIRECCION1.equals("") )
					Imprecion = Imprecion + "TEXT 7 0 5 135 CALZADA A. OLACHEA #4915\r\n";
				else
					Imprecion = Imprecion + "TEXT 7 0 5 135 " + repositorio.DIRECCION1 + "\r\n";

				if( repositorio.DIRECCION2.equals("") )
					Imprecion = Imprecion + "TEXT 7 0 5 160 COL. PUESTA DEL SOL I C.P. 23090\r\n";
				else
					Imprecion = Imprecion + "TEXT 7 0 5 160 " + repositorio.DIRECCION2 + "\r\n";

				if( repositorio.DIRECCION2.equals("") )
					Imprecion = Imprecion + "TEXT 7 0 5 185 LA PAZ, BCS. MEX TEL: (612)124-01-67\r\n";
				else
					Imprecion = Imprecion + "TEXT 7 0 5 185 " + repositorio.TELEFONO + "\r\n";


				Imprecion = Imprecion + "SETMAG 1 2\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 210 RUTA " + repositorio.Vendedor.Nombre + " " + repositorio.FolioNota + "\r\n";
				Imprecion = Imprecion + "SETMAG 1 1\r\n";
				Imprecion = Imprecion + "LEFT\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 270 FECHA " + Utils.getFechaActual_DD_MM_YYYY() + "\r\n";
				Imprecion = Imprecion + "RIGHT\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 270 HORA: " + Utils.getHora_HH_MM_SS() + "  \r\n";
				Imprecion = Imprecion + "CENTER\r\n";
				Imprecion = Imprecion + "SETMAG 1 2\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 315 VENTA CONTADO\r\n";
				Imprecion = Imprecion + "TEXT 0 3 5 365 ---------------------------\r\n";
				Imprecion = Imprecion + "LEFT\r\n";
				Imprecion = Imprecion + "TEXT 0 3 5 565 ----------------------------------------------------------------------\r\n";
				Imprecion = Imprecion + "LEFT\r\n";
				Imprecion = Imprecion + "SETMAG 1 1\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 380 CLIENTE " + repositorio.Itinerario.Codigo + "\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 405 NOMBRE  " + repositorio.Itinerario.Cliente + "\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 430 RFC     " + repositorio.Itinerario.RazonSocial + "\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 455 " + repositorio.Itinerario.Direccion + "\r\n";
				Imprecion = Imprecion + "LEFT\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 500 SKU     DESCRIPCIÓN\r\n";
				Imprecion = Imprecion + "LEFT\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 540 P. UNIT.\r\n";
				Imprecion = Imprecion + "RIGHT\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 540 CANTIDAD     UMB       TOTAL  \r\n";

				for (int x = 0; x < repositorio.productos.size(); x++)
				{
					BONotaDetalle producto = (BONotaDetalle)repositorio.productos.elementAt(x);

					Imprecion = Imprecion + "LEFT\r\n";
					Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(580 + x * 70) + " " + producto.CodigoBarra + " " + producto.Nombre + "\r\n";
					Imprecion = Imprecion + "LEFT\r\n";
					Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(605 + x * 70) + " $" + Utils.leftPad(Utils.toMoneyFormat(producto.Precio), " ", 6) + "\r\n";
					Imprecion = Imprecion + "RIGHT\r\n";
					Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(605 + x * 70) + " " + Utils.leftPad(Utils.toMoneyFormat(producto.Cantidad), " ", 8) + " " + Utils.leftPad(producto.UnidadMedida, " ", 8) + " $" + Utils.leftPad(Utils.toMoneyFormat(producto.Precio * producto.Cantidad), " ", 10) + "  \r\n";
				}

				String valorredondeo = Utils.RedondearValor((float)total);

				double total2 = Double.parseDouble(Utils.RedondearValor((float)total));

				String centavo = Utils.Split(Utils.toMoneyFormat((float)total), ".")[1];

				Imprecion = Imprecion + "SETMAG 1 2\r\n";
				Imprecion = Imprecion + "LEFT\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(645 + alturaproducto) + " TOTAL \r\n";
				Imprecion = Imprecion + "RIGHT\r\n";

				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(645 + alturaproducto) + " $" + Utils.leftPad(Utils.toMoneyFormat((float)total), " ", 12) + "  \r\n";
				Imprecion = Imprecion + "SETMAG 1 1\r\n";
				Imprecion = Imprecion + "LEFT\r\n";

				String cantidad2 = Utils.Split(Utils.ConvertNumberToLetter(total2), "CON")[0];

				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(700 + alturaproducto) + " " + cantidad2 + "\r\n";

				Imprecion = Imprecion + "LEFT\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(740 + alturaproducto) + " " + centavo + "/100 M.N.\r\n";

				Imprecion = Imprecion + "SETMAG 1 1\r\n";
				Imprecion = Imprecion + "CENTER\r\n";

				if( repositorio.ENCUESTA.equals("") )
				{
					Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(780 + alturaproducto) + " ***SI NO LE ENTREGAN TICKET***\r\n";
					Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(805 + alturaproducto) + " ***SU COMPRA ES GRATIS***\r\n";
				}
				else
					Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(780 + alturaproducto) + " " + repositorio.ENCUESTA + "\r\n";

				Imprecion = Imprecion + "SETMAG 0 0 \r\n";

				Imprecion = Imprecion + "FORM\r\n";
				Imprecion = Imprecion + "PRINT\r\n";
			}
			catch (Exception ex)
			{
				Imprecion = "";
			}
		}

		/* 1269 */     return Imprecion;
	}

	//obtiene el ticket de la reimprecion de la venta ya sea al contado o credito
	public String ObtenerTicketReimprecion()
	{
		if(this.repositorio.Venta.Discriminante.equals("Venta"))
		{
			int alturaproducto = this.repositorio.lsVentaDetalle.size() * 60;
			String Imprecion = "";

			/* 324 */     if (this.repositorio.Venta.DiscriminantePago.equals("CREDITO"))
			{
				/* 327 */       Imprecion = (Imprecion + "! 0 0 200 " + (1650 + alturaproducto) + " 1\r\n");

				/* 329 */       Imprecion += "CENTER\r\n";

				//			/* 1181 */         Imprecion = Imprecion + "TEXT 5 2 5 20 LA UNA\r\n";

				/* 1010 */         Imprecion = Imprecion + "TEXT 5 2 5 20 BACHOCO, S.A. de C.V.\r\n";
				/* 1011 */         Imprecion = Imprecion + "TEXT 5 2 5 60 BAC800208B25\r\n";

				/* 332 */       Imprecion += "SETMAG 1 1\r\n";

				if( repositorio.TITULO1.equals("") )
					Imprecion = Imprecion + "TEXT 7 0 5 110 SUC. LA PAZ, BCS\r\n";
				else
					Imprecion = Imprecion + "TEXT 7 0 5 110 " + repositorio.TITULO1 + "\r\n";

				if( repositorio.DIRECCION1.equals("") )
					Imprecion = Imprecion + "TEXT 7 0 5 135 CALZADA A. OLACHEA #4915\r\n";
				else
					Imprecion = Imprecion + "TEXT 7 0 5 135 " + repositorio.DIRECCION1 + "\r\n";

				if( repositorio.DIRECCION2.equals("") )
					Imprecion = Imprecion + "TEXT 7 0 5 160 COL. PUESTA DEL SOL I C.P. 23090\r\n";
				else
					Imprecion = Imprecion + "TEXT 7 0 5 160 " + repositorio.DIRECCION2 + "\r\n";

				if( repositorio.DIRECCION2.equals("") )
					Imprecion = Imprecion + "TEXT 7 0 5 185 LA PAZ, BCS. MEX TEL: (612)124-01-67\r\n";
				else
					Imprecion = Imprecion + "TEXT 7 0 5 185 " + repositorio.TELEFONO + "\r\n";


				/* 337 */       Imprecion += "SETMAG 1 2\r\n";
				/* 338 */       Imprecion = (Imprecion + "TEXT 7 0 5 210 RUTA " + this.repositorio.Vendedor.Nombre + " " + repositorio.Venta.FolioDia + "\r\n");
				/* 339 */       Imprecion += "SETMAG 1 1\r\n";
				/* 340 */       Imprecion += "LEFT\r\n";
				/* 341 */       Imprecion = (Imprecion + "TEXT 7 0 5 270 FECHA " + Utils.Split(this.repositorio.Venta.FechaCreacion, " ")[0] + "\r\n");
				/* 342 */       Imprecion += "RIGHT\r\n";
				/* 343 */       Imprecion = (Imprecion + "TEXT 7 0 5 270 HORA: " + Utils.Split(this.repositorio.Venta.FechaCreacion, " ")[1] + "  \r\n");
				/* 344 */       Imprecion += "CENTER\r\n";
				/* 345 */       Imprecion += "SETMAG 1 2\r\n";
				/* 346 */       Imprecion += "TEXT 7 0 5 315 VENTA CREDITO - REIMPRESION\r\n";
				/* 347 */       Imprecion += "TEXT 0 3 5 365 ---------------------------\r\n";
				/* 348 */       Imprecion += "LEFT\r\n";
				/* 349 */       Imprecion += "TEXT 0 3 5 565 ----------------------------------------------------------------------\r\n";
				/* 350 */       Imprecion += "LEFT\r\n";
				/* 351 */       Imprecion += "SETMAG 1 1\r\n";
				/* 352 */       Imprecion = (Imprecion + "TEXT 7 0 5 380 CLIENTE " + this.repositorio.Venta.Codigo + "\r\n");
				/* 353 */       Imprecion = (Imprecion + "TEXT 7 0 5 405 NOMBRE  " + this.repositorio.Venta.Cliente + "\r\n");
				/* 354 */       Imprecion = (Imprecion + "TEXT 7 0 5 430 RFC     " + this.repositorio.Venta.RazonSocial + "\r\n");
				/* 355 */       Imprecion = (Imprecion + "TEXT 7 0 5 455 " + this.repositorio.Venta.Direccion + "\r\n");
				/* 356 */       Imprecion += "LEFT\r\n";
				/* 357 */       Imprecion += "TEXT 7 0 5 500 SKU     DESCRIPCIÓN\r\n";
				/* 358 */       Imprecion += "LEFT\r\n";
				/* 359 */       Imprecion += "TEXT 7 0 5 540 P. UNIT.\r\n";
				/* 360 */       Imprecion += "RIGHT\r\n";
				/* 361 */       Imprecion += "TEXT 7 0 5 540 CANTIDAD     UMB       TOTAL  \r\n";

				/* 363 */       float total = 0.0F;
				/* 364 */       boolean existeEmbalaje = false;
				/* 365 */       int sumaProducto = 0;

				/* 367 */       for (int x = 0; x < this.repositorio.lsVentaDetalle.size(); x++)
				{
					/* 369 */         BONotaDetalle producto = (BONotaDetalle)this.repositorio.lsVentaDetalle.get(x);

					/* 371 */         if (!producto.Envase)
					{
						/* 373 */           total += producto.Precio * producto.Cantidad;

						/* 375 */           Imprecion += "LEFT\r\n";
						/* 376 */           Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(580 + sumaProducto * 70) + " " + producto.CodigoBarra + " " + producto.Nombre + "\r\n");
						/* 377 */           Imprecion += "LEFT\r\n";
						/* 378 */           Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(605 + sumaProducto * 70) + " $" + Utils.leftPad(Utils.toMoneyFormat(producto.Precio), " ", 6) + "\r\n");
						/* 379 */           Imprecion += "RIGHT\r\n";
						/* 380 */           Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(605 + sumaProducto * 70) + " " + Utils.leftPad(Utils.toMoneyFormat(producto.Cantidad), " ", 8) + " " + Utils.leftPad(producto.UnidadMedida, " ", 8) + " $" + Utils.leftPad(Utils.toMoneyFormat(producto.Precio * producto.Cantidad), " ", 10) + "  \r\n");
						/* 381 */           sumaProducto++;
					}
					else {
						/* 384 */           existeEmbalaje = true;
					}
				}
				/* 387 */       alturaproducto = sumaProducto * 50;

				/* 389 */       double total2 = Double.parseDouble(Utils.RedondearValor(total));
				/* 390 */       String centavo = Utils.Split(Utils.toMoneyFormat(total), ".")[1];

				/* 392 */       Imprecion += "SETMAG 1 2\r\n";
				/* 393 */       Imprecion += "LEFT\r\n";
				/* 394 */       Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(645 + alturaproducto) + " TOTAL \r\n");
				/* 395 */       Imprecion += "RIGHT\r\n";

				/* 397 */       Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(645 + alturaproducto) + " $" + Utils.leftPad(Utils.toMoneyFormat(total), " ", 12) + "  \r\n");
				/* 398 */       Imprecion += "SETMAG 1 1\r\n";
				/* 399 */       Imprecion += "LEFT\r\n";

				/* 401 */       String cantidad2 = Utils.Split(Utils.ConvertNumberToLetter(total2), "CON")[0];

				/* 404 */       Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(700 + alturaproducto) + " " + cantidad2 + "\r\n");

				/* 406 */       Imprecion += "LEFT\r\n";
				/* 407 */       Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(740 + alturaproducto) + " " + centavo + "/100 M.N.\r\n");

				/* 409 */       Imprecion += "SETMAG 1 1\r\n";
				/* 410 */       Imprecion += "CENTER\r\n";


				if( repositorio.ENCUESTA.equals("") )
				{
					Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(780 + alturaproducto) + " ***SI NO LE ENTREGAN TICKET***\r\n";
					Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(805 + alturaproducto) + " ***SU COMPRA ES GRATIS***\r\n";

					//Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(780 + alturaproducto) + " \"GRACIAS POR CONTESTAR LA ENCUESTA\"\r\n");
				}
				else
					Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(780 + alturaproducto) + " " + repositorio.ENCUESTA + "\r\n");


				/* 413 */       int sumaEmbalaje = 0;

				/* 416 */       if (existeEmbalaje)
				{
					/* 418 */         Imprecion += "SETMAG 1 2\r\n";
					/* 419 */         Imprecion += "CENTER\r\n";
					/* 420 */         Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(820 + alturaproducto) + " EMBALAJE\r\n");

					/* 422 */         Imprecion = (Imprecion + "TEXT 0 3 5 " + String.valueOf(860 + alturaproducto) + " ----------------------------------------------------------------------\r\n");

					/* 424 */         Imprecion += "SETMAG 1 1\r\n";

					/* 426 */         Imprecion += "LEFT\r\n";
					/* 427 */         Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(880 + alturaproducto) + " SKU     DESCRIPCIÓN\r\n");
					/* 428 */         Imprecion += "LEFT\r\n";
					/* 429 */         Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(920 + alturaproducto) + " INICIAL\r\n");
					/* 430 */         Imprecion += "RIGHT\r\n";

					/* 433 */         Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(920 + alturaproducto) + " ENTREGA   DEVOLUCIÓN   FINAL \r\n");

					/* 435 */         for (int x = 0; x < this.repositorio.lsVentaDetalle.size(); x++)
					{
						/* 437 */           BONotaDetalle producto = (BONotaDetalle)this.repositorio.lsVentaDetalle.get(x);

						/* 439 */           if (!producto.Envase)
							continue;
						/* 441 */           Imprecion += "LEFT\r\n";
						/* 442 */           Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(940 + alturaproducto + sumaEmbalaje * 70) + " " + producto.CodigoBarra + " " + producto.Nombre + "\r\n");
						/* 443 */           Imprecion += "LEFT\r\n";
						/* 444 */           Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(965 + alturaproducto + sumaEmbalaje * 70) + " 0\r\n");
						/* 445 */           Imprecion += "RIGHT\r\n";
						/* 446 */           Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(965 + alturaproducto + sumaEmbalaje * 70) + " " + Utils.leftPad(Utils.toMoneyFormat(producto.Cantidad), " ", 8) + " " + Utils.leftPad(producto.UnidadMedida, " ", 8) + " " + Utils.leftPad(Utils.toMoneyFormat(producto.Cantidad), " ", 8) + "  \r\n");
						/* 447 */           sumaEmbalaje++;
					}

					/* 451 */         sumaEmbalaje = 150 + sumaEmbalaje * 50;
				}

				/* 454 */       Imprecion += "SETMAG 1 2\r\n";
				/* 455 */       Imprecion += "CENTER\r\n";
				/* 1135 */         Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(835 + alturaproducto + sumaEmbalaje) + " PAGARE\r\n";

				/* 458 */       Imprecion += "SETMAG 1 1\r\n";
				/* 459 */       Imprecion += "LEFT\r\n";


				if( repositorio.PAGARE1.equals("") )
					Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(900 + alturaproducto + sumaEmbalaje) + " EN CALZADA A. OLACHEA #4915, PUESTA DEL SOL I\r\n";
				else
					Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(900 + alturaproducto + sumaEmbalaje) + " " + repositorio.PAGARE1 + "\r\n";

				if( repositorio.PAGARE2.equals("") )
					Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(925 + alturaproducto + sumaEmbalaje) + " C.P. 23090, LA PAZ BCS A " + Utils.getFechaActual_DD_MM_YYYY() + "\r\n";
				else
					Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(925 + alturaproducto + sumaEmbalaje) + " " + repositorio.PAGARE2 + " " + Utils.getFechaActual_DD_MM_YYYY() + "\r\n";


				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(950 + alturaproducto + sumaEmbalaje) + " (" + Utils.ConvertIntegerToLetter(Utils.getDiaActual()) + "DE " + Utils.getMesNombre() + " DEL " + Utils.ConvertIntegerToLetter(Utils.getAnioActual()) + ")\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(975 + alturaproducto + sumaEmbalaje) + " DEBO(EMOS) Y PAGARE(MOS)INCONDICIONALMENTE \r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1000 + alturaproducto + sumaEmbalaje) + " POR ESTE PAGARÉ AL ORDEN DE BACHOCO, S.A. \r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1025 + alturaproducto + sumaEmbalaje) + " DOS DE JUNIO DEL DOS MIL DOCE) LA CANTIDAD \r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1050 + alturaproducto + sumaEmbalaje) + " DE: $" + Utils.toMoneyFormat((float)total) + " (" + cantidad2 + "\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1075 + alturaproducto + sumaEmbalaje) + " " + centavo + "/100 M.N. ) VALOR RECIBIDO A \r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1100 + alturaproducto + sumaEmbalaje) + " MI(NUESTRA) SATISFACCIÓN. EN CASO DE NO \r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1125 + alturaproducto + sumaEmbalaje) + " PAGARSE A LA FECHA DE VENCIMIENTO ME OBLIGO \r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1150 + alturaproducto + sumaEmbalaje) + " A PAGAR UN INTERÉS MORATORIO EQUIVALENTE AL 3% \r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1175 + alturaproducto + sumaEmbalaje) + " MENSUAL A PARTIR DE SU VENCIMIENTO. SERÁ \r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1200 + alturaproducto + sumaEmbalaje) + " EXIGIBLES ESTE Y TODOS AQUELLOS QUE EXCEDAN \r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1225 + alturaproducto + sumaEmbalaje) + " LA FECHA DE VENCIMIENTO.\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1250 + alturaproducto + sumaEmbalaje) + " " + this.repositorio.Itinerario.Codigo + " " + this.repositorio.Itinerario.Cliente + "\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1275 + alturaproducto + sumaEmbalaje) + " RFC " + this.repositorio.Itinerario.RazonSocial + "\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1300 + alturaproducto + sumaEmbalaje) + " " + this.repositorio.Itinerario.Direccion + "\r\n";		

				//			/* 463 */       Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(950 + alturaproducto + sumaEmbalaje) + " (" + Utils.ConvertIntegerToLetter(Utils.getDiaActual()) + "DE " + Utils.getMesNombre() + " DEL " + Utils.ConvertIntegerToLetter(Utils.getAnioActual()) + ")\r\n");
				//			/* 464 */       Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(975 + alturaproducto + sumaEmbalaje) + " DEBO(EMOS) Y PAGARE(MOS)INCONDICIONALMENTE \r\n");
				//			/* 1144 */         Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1000 + alturaproducto + sumaEmbalaje) + " POR ESTE PAGARÉ AL ORDEN DE DESARROLLO DE \r\n";
				//			/* 1145 */         Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(1025 + alturaproducto + sumaEmbalaje) + " DESARROLLO DE ALIMENTOS DE MEXICO, S.A. DE C.V. LA CANTIDAD\r\n";
				//			/* 467 */       Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(1050 + alturaproducto + sumaEmbalaje) + " DE: $" + Utils.toMoneyFormat(total) + " (" + cantidad2 + "\r\n");
				//			/* 468 */       Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(1075 + alturaproducto + sumaEmbalaje) + " " + centavo + "/100 M.N. ) VALOR RECIBIDO A \r\n");
				//			/* 469 */       Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(1100 + alturaproducto + sumaEmbalaje) + " MI(NUESTRA) SATISFACCIÓN. EN CASO DE NO \r\n");
				//			/* 470 */       Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(1125 + alturaproducto + sumaEmbalaje) + " PAGARSE A LA FECHA DE VENCIMIENTO ME OBLIGO \r\n");
				//			/* 471 */       Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(1150 + alturaproducto + sumaEmbalaje) + " A PAGAR UN INTERÉS MORATORIO EQUIVALENTE AL 3% \r\n");
				//			/* 472 */       Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(1175 + alturaproducto + sumaEmbalaje) + " MENSUAL A PARTIR DE SU VENCIMIENTO. SERÁ \r\n");
				//			/* 473 */       Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(1200 + alturaproducto + sumaEmbalaje) + " EXIGIBLES ESTE Y TODOS AQUELLOS QUE EXCEDAN \r\n");
				//			/* 474 */       Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(1225 + alturaproducto + sumaEmbalaje) + " LA FECHA DE VENCIMIENTO.\r\n");
				//			/* 475 */       Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(1250 + alturaproducto + sumaEmbalaje) + " " + this.repositorio.Venta.Codigo + " " + this.repositorio.Venta.Cliente + "\r\n");
				//			/* 476 */       Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(1275 + alturaproducto + sumaEmbalaje) + " RFC " + this.repositorio.Venta.RazonSocial + "\r\n");
				//			/* 477 */       Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(1300 + alturaproducto + sumaEmbalaje) + " " + this.repositorio.Venta.Direccion + "\r\n");


				/* 479 */       Imprecion += "SETMAG 1 2\r\n";
				/* 480 */       Imprecion += "CENTER\r\n";

				/* 482 */       Imprecion = (Imprecion + "LINE 0 " + String.valueOf(1450 + alturaproducto + sumaEmbalaje) + " 400 " + String.valueOf(1450 + alturaproducto + sumaEmbalaje) + " 4\r\n");
				/* 483 */       Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(1470 + alturaproducto + sumaEmbalaje) + " ACEPTO(AMOS)\r\n");
				/* 483 */       Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(1510 + alturaproducto + sumaEmbalaje) + " NOMBRE Y FIRMA\r\n");

				/* 485 */       Imprecion += "SETMAG 0 0\r\n";
				/* 486 */       Imprecion += "FORM\r\n";
				/* 487 */       Imprecion += "PRINT\r\n";

				/* 491 */       return Imprecion;
			}

			/* 513 */     Imprecion = (Imprecion + "! 0 0 200 " + (870 + alturaproducto) + " 1\r\n");

			/* 515 */     Imprecion += "CENTER\r\n";

			/* 1010 */         Imprecion = Imprecion + "TEXT 5 2 5 20 BACHOCO, S.A. de C.V.\r\n";
			/* 1011 */         Imprecion = Imprecion + "TEXT 5 2 5 60 BAC800208B25\r\n";

			/* 518 */     Imprecion += "SETMAG 1 1\r\n";

			if( repositorio.TITULO1.equals("") )
				Imprecion = Imprecion + "TEXT 7 0 5 110 SUC. LA PAZ, BCS\r\n";
			else
				Imprecion = Imprecion + "TEXT 7 0 5 110 " + repositorio.TITULO1 + "\r\n";

			if( repositorio.DIRECCION1.equals("") )
				Imprecion = Imprecion + "TEXT 7 0 5 135 CALZADA A. OLACHEA #4915\r\n";
			else
				Imprecion = Imprecion + "TEXT 7 0 5 135 " + repositorio.DIRECCION1 + "\r\n";

			if( repositorio.DIRECCION2.equals("") )
				Imprecion = Imprecion + "TEXT 7 0 5 160 COL. PUESTA DEL SOL I C.P. 23090\r\n";
			else
				Imprecion = Imprecion + "TEXT 7 0 5 160 " + repositorio.DIRECCION2 + "\r\n";

			if( repositorio.DIRECCION2.equals("") )
				Imprecion = Imprecion + "TEXT 7 0 5 185 LA PAZ, BCS. MEX TEL: (612)124-01-67\r\n";
			else
				Imprecion = Imprecion + "TEXT 7 0 5 185 " + repositorio.TELEFONO + "\r\n";

			/* 523 */     Imprecion += "SETMAG 1 2\r\n";
			/* 524 */     Imprecion = (Imprecion + "TEXT 7 0 5 210 RUTA " + this.repositorio.Vendedor.Nombre + " " + repositorio.Venta.FolioDia + "\r\n");
			/* 525 */     Imprecion += "SETMAG 1 1\r\n";
			/* 526 */     Imprecion += "LEFT\r\n";
			/* 527 */     Imprecion = (Imprecion + "TEXT 7 0 5 270 FECHA " + Utils.Split(this.repositorio.Venta.FechaCreacion, " ")[0] + "\r\n");
			/* 528 */     Imprecion += "RIGHT\r\n";
			/* 529 */     Imprecion = (Imprecion + "TEXT 7 0 5 270 HORA: " + Utils.Split(this.repositorio.Venta.FechaCreacion, " ")[1] + "  \r\n");
			/* 530 */     Imprecion += "CENTER\r\n";
			/* 531 */     Imprecion += "SETMAG 1 2\r\n";
			/* 532 */     Imprecion += "TEXT 7 0 5 315 VENTA CONTADO - REIMPRESION\r\n";
			/* 533 */     Imprecion += "TEXT 0 3 5 365 ---------------------------\r\n";
			/* 534 */     Imprecion += "LEFT\r\n";
			/* 535 */     Imprecion += "TEXT 0 3 5 565 ----------------------------------------------------------------------\r\n";
			/* 536 */     Imprecion += "LEFT\r\n";
			/* 537 */     Imprecion += "SETMAG 1 1\r\n";
			/* 538 */     Imprecion = (Imprecion + "TEXT 7 0 5 380 CLIENTE " + this.repositorio.Venta.Codigo + "\r\n");
			/* 539 */     Imprecion = (Imprecion + "TEXT 7 0 5 405 NOMBRE  " + this.repositorio.Venta.Cliente + "\r\n");
			/* 540 */     Imprecion = (Imprecion + "TEXT 7 0 5 430 RFC     " + this.repositorio.Venta.RazonSocial + "\r\n");
			/* 541 */     Imprecion = (Imprecion + "TEXT 7 0 5 455 " + this.repositorio.Venta.Direccion + "\r\n");
			/* 542 */     Imprecion += "LEFT\r\n";
			/* 543 */     Imprecion += "TEXT 7 0 5 500 SKU     DESCRIPCIÓN\r\n";
			/* 544 */     Imprecion += "LEFT\r\n";
			/* 545 */     Imprecion += "TEXT 7 0 5 540 P. UNIT.\r\n";
			/* 546 */     Imprecion += "RIGHT\r\n";
			/* 547 */     Imprecion += "TEXT 7 0 5 540 CANTIDAD     UMB       TOTAL  \r\n";

			/* 549 */     float total = 0.0F;

			/* 551 */     for (int x = 0; x < this.repositorio.lsVentaDetalle.size(); x++)
			{
				/* 553 */       BONotaDetalle producto = (BONotaDetalle)this.repositorio.lsVentaDetalle.get(x);

				/* 555 */       total += producto.Precio * producto.Cantidad;

				/* 557 */       Imprecion += "LEFT\r\n";
				/* 558 */       Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(580 + x * 70) + " " + producto.CodigoBarra + " " + producto.Nombre + "\r\n");
				/* 559 */       Imprecion += "LEFT\r\n";
				/* 560 */       Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(605 + x * 70) + " $" + Utils.leftPad(Utils.toMoneyFormat(producto.Precio), " ", 6) + "\r\n");
				/* 561 */       Imprecion += "RIGHT\r\n";
				/* 562 */       Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(605 + x * 70) + " " + Utils.leftPad(Utils.toMoneyFormat(producto.Cantidad), " ", 8) + " " + Utils.leftPad(producto.UnidadMedida, " ", 8) + " $" + Utils.leftPad(Utils.toMoneyFormat(producto.Precio * producto.Cantidad), " ", 10) + "  \r\n");
			}

			/* 565 */     double total2 = Double.parseDouble(Utils.RedondearValor(total));
			/* 566 */     String centavo = Utils.Split(Utils.toMoneyFormat(total), ".")[1];

			/* 568 */     Imprecion += "SETMAG 1 2\r\n";
			/* 569 */     Imprecion += "LEFT\r\n";
			/* 570 */     Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(645 + alturaproducto) + " TOTAL \r\n");
			/* 571 */     Imprecion += "RIGHT\r\n";

			/* 573 */     Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(645 + alturaproducto) + " $" + Utils.leftPad(Utils.toMoneyFormat(total), " ", 12) + "  \r\n");
			/* 574 */     Imprecion += "SETMAG 1 1\r\n";
			/* 575 */     Imprecion += "LEFT\r\n";

			/* 577 */     String cantidad2 = Utils.Split(Utils.ConvertNumberToLetter(total2), "CON")[0];

			/* 580 */     Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(700 + alturaproducto) + " " + cantidad2 + "\r\n");

			/* 582 */     Imprecion += "LEFT\r\n";
			/* 583 */     Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(740 + alturaproducto) + " " + centavo + "/100 M.N.\r\n");

			/* 587 */     Imprecion += "SETMAG 1 1\r\n";
			/* 588 */     Imprecion += "CENTER\r\n";

			if( repositorio.ENCUESTA.equals("") )
			{
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(780 + alturaproducto) + " ***SI NO LE ENTREGAN TICKET***\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(805 + alturaproducto) + " ***SU COMPRA ES GRATIS***\r\n";

				//Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(780 + alturaproducto) + " \"GRACIAS POR CONTESTAR LA ENCUESTA\"\r\n");
			}
			else
				Imprecion = (Imprecion + "TEXT 7 0 5 " + String.valueOf(780 + alturaproducto) + " " + repositorio.ENCUESTA + "\r\n");

			/* 591 */     Imprecion += "SETMAG 0 0 \r\n";

			/* 593 */     Imprecion += "FORM\r\n";
			/* 594 */     Imprecion += "PRINT\r\n";

			/* 614 */     return Imprecion;
		}
		else
		{
			String Imprecion = "";
			try
			{
				Imprecion = Imprecion + "CENTER\r\n";

				/* 1010 */         Imprecion = Imprecion + "TEXT 5 2 5 20 BACHOCO, S.A. de C.V.\r\n";
				/* 1011 */         Imprecion = Imprecion + "TEXT 5 2 5 60 BAC800208B25\r\n";

				Imprecion = Imprecion + "SETMAG 1 1\r\n";

				if( repositorio.TITULO1.equals("") )
					Imprecion = Imprecion + "TEXT 7 0 5 110 SUC. LA PAZ, BCS\r\n";
				else
					Imprecion = Imprecion + "TEXT 7 0 5 110 " + repositorio.TITULO1 + "\r\n";

				if( repositorio.DIRECCION1.equals("") )
					Imprecion = Imprecion + "TEXT 7 0 5 135 CALZADA A. OLACHEA #4915\r\n";
				else
					Imprecion = Imprecion + "TEXT 7 0 5 135 " + repositorio.DIRECCION1 + "\r\n";

				if( repositorio.DIRECCION2.equals("") )
					Imprecion = Imprecion + "TEXT 7 0 5 160 COL. PUESTA DEL SOL I C.P. 23090\r\n";
				else
					Imprecion = Imprecion + "TEXT 7 0 5 160 " + repositorio.DIRECCION2 + "\r\n";

				if( repositorio.DIRECCION2.equals("") )
					Imprecion = Imprecion + "TEXT 7 0 5 185 LA PAZ, BCS. MEX TEL: (612)124-01-67\r\n";
				else
					Imprecion = Imprecion + "TEXT 7 0 5 185 " + repositorio.TELEFONO + "\r\n";

				Imprecion = Imprecion + "SETMAG 1 2\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 210 " + this.repositorio.Vendedor.Nombre + " " + repositorio.Venta.FolioDia + " \r\n";

				Imprecion = Imprecion + "SETMAG 1 1\r\n";
				Imprecion = Imprecion + "LEFT\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 270 FECHA " + Utils.Split(this.repositorio.Venta.FechaCreacion, " ")[0] + "\r\n";
				Imprecion = Imprecion + "RIGHT\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 270 HORA: " + Utils.Split(this.repositorio.Venta.FechaCreacion, " ")[1] + "  \r\n";
				Imprecion = Imprecion + "CENTER\r\n";
				Imprecion = Imprecion + "SETMAG 1 2\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 315 PAGO ABONO CUENTA DE CLIENTE-REIMPRESION\r\n";
				Imprecion = Imprecion + "TEXT 0 3 5 365 ----------------------------------------------------------------------\r\n";
				Imprecion = Imprecion + "LEFT\r\n";
				Imprecion = Imprecion + "TEXT 0 3 5 565 ----------------------------------------------------------------------\r\n";
				Imprecion = Imprecion + "LEFT\r\n";
				Imprecion = Imprecion + "SETMAG 1 1\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 380 CLIENTE " + this.repositorio.Venta.Codigo + "\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 405 NOMBRE  " + this.repositorio.Venta.Cliente + "\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 430 RFC     " + this.repositorio.Venta.RazonSocial + "\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 455 " + this.repositorio.Venta.Direccion + "\r\n";
				Imprecion = Imprecion + "LEFT\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 500 FACTURA     VIA DE PAGO\r\n";
				Imprecion = Imprecion + "LEFT\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 540 NUMERO DOC.  BANCO\r\n";
				Imprecion = Imprecion + "RIGHT\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 500 IMPORTE PAGADO  \r\n";

				double total = 0.0D;

				if (this.repositorio.Venta.PagoEfectivo > 0)
				{
					total += this.repositorio.Venta.PagoEfectivo;
				}

				if (this.repositorio.Venta.PagoDeposito > 0)
				{
					total += this.repositorio.Venta.PagoDeposito;
				}

				if (this.repositorio.Venta.PagoCheque > 0)
				{
					total += this.repositorio.Venta.PagoCheque;
				}

				double total2 = Double.parseDouble(Utils.RedondearValor((float)total));
				String centavo = Utils.Split(Utils.toMoneyFormat((float)total), ".")[1];

				String facturafolio = "";
				int contadorPago = 0;

				facturafolio = this.repositorio.Venta.Observacion;

				if (this.repositorio.Venta.PagoEfectivo > 0.0D)
				{
					try
					{
						Imprecion = Imprecion + "LEFT\r\n";
						Imprecion = Imprecion + "TEXT 7 0 5 " + (580 + contadorPago * 60) + " " + facturafolio + " EFECTIVO\r\n";

						Imprecion = Imprecion + "RIGHT\r\n";
						Imprecion = Imprecion + "TEXT 7 0 5 " + (580 + contadorPago * 60) + " $" + Utils.toMoneyFormat((float) this.repositorio.Venta.PagoEfectivo) + "  \r\n";
						contadorPago++;
					}
					catch (Exception localException1) {}
				}

				if (this.repositorio.Venta.PagoCheque > 0.0D)
				{
					try
					{
						Imprecion = Imprecion + "LEFT\r\n";
						Imprecion = Imprecion + "TEXT 7 0 5 " + (580 + contadorPago * 60) + " " + facturafolio + " CHEQUE\r\n";

						Imprecion = Imprecion + "LEFT\r\n";
						Imprecion = Imprecion + "TEXT 7 0 5 " + (605 + contadorPago * 60) + " " + this.repositorio.Venta.Folio + " " + this.repositorio.Venta.Banco + "\r\n";
						Imprecion = Imprecion + "RIGHT\r\n";
						Imprecion = Imprecion + "TEXT 7 0 5 " + (580 + contadorPago * 60) + " $" + Utils.toMoneyFormat((float) this.repositorio.Venta.PagoCheque) + "  \r\n";
						contadorPago++;
					}
					catch (Exception localException2) {}
				}

				if (this.repositorio.Venta.PagoDeposito > 0.0D)
				{
					try
					{
						Imprecion = Imprecion + "LEFT\r\n";
						Imprecion = Imprecion + "TEXT 7 0 5 " + (580 + contadorPago * 60) + " " + facturafolio + " DEPOSITO\r\n";

						Imprecion = Imprecion + "LEFT\r\n";
						Imprecion = Imprecion + "TEXT 7 0 5 " + (605 + contadorPago * 60) + " " + this.repositorio.Venta.Folio + " " + this.repositorio.Venta.Banco + "\r\n";
						Imprecion = Imprecion + "RIGHT\r\n";
						Imprecion = Imprecion + "TEXT 7 0 5 " + (580 + contadorPago * 60) + " $" + Utils.toMoneyFormat((float) this.repositorio.Venta.PagoDeposito) + "  \r\n";
						contadorPago++;
					}
					catch (Exception localException3) {}
				}

				Imprecion = Imprecion + "SETMAG 1 2\r\n";
				Imprecion = Imprecion + "LEFT\r\n";
				Imprecion = Imprecion + "TEXT 7 0 5 " + (570 + contadorPago * 70) + " TOTAL \r\n";
				Imprecion = Imprecion + "RIGHT\r\n";

				Imprecion = Imprecion + "TEXT 7 0 5 " + (570 + contadorPago * 70) + " $" + Utils.leftPad(Utils.toMoneyFormat((float)total), " ", 12) + "  \r\n";
				Imprecion = Imprecion + "SETMAG 1 1\r\n";
				Imprecion = Imprecion + "LEFT\r\n";

				String cantidad2 = Utils.Split(Utils.ConvertNumberToLetter(total2), "CON")[0];

				String monto = centavo + "/100 M.N.";

				if (monto.length() > 40)
				{
					Imprecion = Imprecion + "TEXT 7 0 5 " + (630 + contadorPago * 70) + " " + cantidad2 + "\r\n";

					Imprecion = Imprecion + "LEFT\r\n";
					Imprecion = Imprecion + "TEXT 7 0 5 " + (650 + contadorPago * 70) + " " + centavo + "/100 M.N.\r\n";
				}
				else
				{
					Imprecion = Imprecion + "TEXT 7 0 5 " + (630 + contadorPago * 70) + " " + cantidad2 + " " + centavo + "/100 M.N.\r\n";
				}

				Imprecion = Imprecion + "SETMAG 1 2\r\n";
				Imprecion = Imprecion + "CENTER\r\n";

				Imprecion = Imprecion + "TEXT 7 0 5 " + (810 + contadorPago * 70) + " ACEPTO(AMOS)\r\n";

				Imprecion = Imprecion + "SETMAG 0 0\r\n";
				Imprecion = Imprecion + "FORM\r\n";
				Imprecion = Imprecion + "PRINT\r\n";

				Imprecion = "! 0 0 200 " + (930 + contadorPago * 70) + " 1\r\n" + Imprecion;
			}
			catch (Exception ex)
			{
				Imprecion = "";
			}

			Logg.info(Imprecion);

			return Imprecion;			
		}
	}

	//Obtiene el ticket del almacen
	public String ObtenerTicketAlmacen()
	{
		/*  859 */     int alturaproducto = repositorio.lsAlmacenMovil.size() * 60;
		/*  860 */     String Imprecion = "";

		try
		{
			//ENCABEZADO DEL TICKET

			/* 1178 */         Imprecion = Imprecion + "! 0 0 200 " + (870 + alturaproducto) + " 1\r\n";

			/* 1180 */         Imprecion = Imprecion + "CENTER\r\n";

			//		/* 1181 */         Imprecion = Imprecion + "TEXT 5 2 5 20 LA UNA\r\n";

			/* 1010 */         Imprecion = Imprecion + "TEXT 5 2 5 20 BACHOCO, S.A. de C.V.\r\n";
			/* 1011 */         Imprecion = Imprecion + "TEXT 5 2 5 60 BAC800208B25\r\n";

			/* 1183 */         Imprecion = Imprecion + "SETMAG 1 1\r\n";


			if( repositorio.TITULO1.equals("") )
				Imprecion = Imprecion + "TEXT 7 0 5 110 SUC. LA PAZ, BCS\r\n";
			else
				Imprecion = Imprecion + "TEXT 7 0 5 110 " + repositorio.TITULO1 + "\r\n";

			if( repositorio.DIRECCION1.equals("") )
				Imprecion = Imprecion + "TEXT 7 0 5 135 CALZADA A. OLACHEA #4915\r\n";
			else
				Imprecion = Imprecion + "TEXT 7 0 5 135 " + repositorio.DIRECCION1 + "\r\n";

			if( repositorio.DIRECCION2.equals("") )
				Imprecion = Imprecion + "TEXT 7 0 5 160 COL. PUESTA DEL SOL I C.P. 23090\r\n";
			else
				Imprecion = Imprecion + "TEXT 7 0 5 160 " + repositorio.DIRECCION2 + "\r\n";

			if( repositorio.DIRECCION2.equals("") )
				Imprecion = Imprecion + "TEXT 7 0 5 185 LA PAZ, BCS. MEX TEL: (612)124-01-67\r\n";
			else
				Imprecion = Imprecion + "TEXT 7 0 5 185 " + repositorio.TELEFONO + "\r\n";

			/* 1188 */         Imprecion = Imprecion + "SETMAG 1 2\r\n";
			/* 1189 */         Imprecion = Imprecion + "TEXT 7 0 5 210 RUTA " + repositorio.Vendedor.Nombre + " \r\n";
			/* 1190 */         Imprecion = Imprecion + "SETMAG 1 1\r\n";
			/* 1191 */         Imprecion = Imprecion + "LEFT\r\n";
			/* 1192 */         Imprecion = Imprecion + "TEXT 7 0 5 270 FECHA " + Utils.getFechaActual_DD_MM_YYYY() + "\r\n";
			/* 1193 */         Imprecion = Imprecion + "RIGHT\r\n";
			/* 1194 */         Imprecion = Imprecion + "TEXT 7 0 5 270 HORA: " + Utils.getHora_HH_MM_SS() + "  \r\n";
			/* 1195 */         Imprecion = Imprecion + "CENTER\r\n";

			/* 1196 */         Imprecion = Imprecion + "SETMAG 1 2\r\n";
			/* 1197 */         Imprecion = Imprecion + "TEXT 7 0 5 315 IMPRESION ALMACEN\r\n";
			/* 1198 */         Imprecion = Imprecion + "TEXT 0 3 5 365 ---------------------------\r\n";
			/* 1200 */         Imprecion = Imprecion + "TEXT 0 3 5 450 ----------------------------------------------------------------------\r\n";
			/* 1201 */         Imprecion = Imprecion + "LEFT\r\n";
			/* 1202 */         Imprecion = Imprecion + "SETMAG 1 1\r\n";

			/* 1207 */         Imprecion = Imprecion + "LEFT\r\n";

			/* 1208 */         Imprecion = Imprecion + "TEXT 7 0 5 385 CODIGO     DESCRIPCION\r\n";
			/* 1209 */         Imprecion = Imprecion + "LEFT\r\n";
			/* 1212 */         Imprecion = Imprecion + "TEXT 7 0 5 415 CANTIDAD(I)  VENTA(V)  EXISTENCIA(E)  UNIDAD      \r\n";

			/* 1214 */         for (int x = 0; x < repositorio.lsAlmacenMovil.size(); x++)
			{
				/* 1216 */           BOAlmacenMovil producto = (BOAlmacenMovil)repositorio.lsAlmacenMovil.get(x);

				/* 1218 */           Imprecion = Imprecion + "LEFT\r\n";
				/* 1219 */           Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(480 + x * 70) + " " + 
						producto.Codigo + " " + producto.Producto + "\r\n";

				/* 1220 */           Imprecion = Imprecion + "LEFT\r\n";
				/* 1223 */           Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(505 + x * 70) + " " + 
						Utils.rigthPad("Ini:" + Utils.toMoneyFormat(producto.CantidadInicial), " ", 8) + " " +
						Utils.rigthPad("Vent:" + Utils.toMoneyFormat(producto.Venta), " ", 8) + " " +
						Utils.rigthPad("Exis:" + Utils.toMoneyFormat(producto.CantidadInicial - producto.Venta), " ", 8) + " " +
						producto.Unidad + "     \r\n";
			}

			/* 1240 */         Imprecion = Imprecion + "CENTER\r\n";

			/* 1196 */         Imprecion = Imprecion + "SETMAG 1 2\r\n";
			/* 1200 */         Imprecion = Imprecion + "TEXT 0 3 5 " + String.valueOf(680 + alturaproducto) + " ----------------------------------------------------------------------\r\n";

			/* 1239 */         Imprecion = Imprecion + "SETMAG 1 1\r\n";
			/* 1245 */         Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(690 + alturaproducto) + " NOMBRE Y FIRMA VENDEDOR\r\n";

			/* 1196 */         Imprecion = Imprecion + "SETMAG 1 2\r\n";
			/* 1200 */         Imprecion = Imprecion + "TEXT 0 3 5 " + String.valueOf(810 + alturaproducto) + " ----------------------------------------------------------------------\r\n";

			/* 1239 */         Imprecion = Imprecion + "SETMAG 1 1\r\n";
			/* 1248 */         Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(820 + alturaproducto) + " NOMBRE Y FIRMA ALMACEN\r\n";

			/* 1252 */         Imprecion = Imprecion + "SETMAG 1 1\r\n";
			/* 1253 */         Imprecion = Imprecion + "CENTER\r\n";

			/* 1256 */         Imprecion = Imprecion + "SETMAG 0 0 \r\n";

			/* 1258 */         Imprecion = Imprecion + "FORM\r\n";
			/* 1259 */         Imprecion = Imprecion + "PRINT\r\n";
		}
		catch (Exception ex)
		{
			/* 1263 */         Imprecion = "";
		}

		return Imprecion;
	}

	//obtiene el ticket de la liquidacion
	public String ObtenerTicketLiquidacion()
	{
		/*  859 */     int alturaproducto = repositorio.lsVenta.size() * 70;
		/*  860 */     String Imprecion = "";
		double Total = 0;
		double TotalCredito = 0;

		try
		{
			/* 1178 */         Imprecion = Imprecion + "! 0 0 200 " + (810 + alturaproducto) + " 1\r\n";

			/* 1180 */         Imprecion = Imprecion + "CENTER\r\n";

			//		/* 1181 */         Imprecion = Imprecion + "TEXT 5 2 5 20 LA UNA\r\n";

			/* 1010 */         Imprecion = Imprecion + "TEXT 5 2 5 20 BACHOCO, S.A. de C.V.\r\n";
			/* 1011 */         Imprecion = Imprecion + "TEXT 5 2 5 60 BAC800208B25\r\n";

			/* 1183 */         Imprecion = Imprecion + "SETMAG 1 1\r\n";

			if( repositorio.TITULO1.equals("") )
				Imprecion = Imprecion + "TEXT 7 0 5 110 SUC. LA PAZ, BCS\r\n";
			else
				Imprecion = Imprecion + "TEXT 7 0 5 110 " + repositorio.TITULO1 + "\r\n";

			if( repositorio.DIRECCION1.equals("") )
				Imprecion = Imprecion + "TEXT 7 0 5 135 CALZADA A. OLACHEA #4915\r\n";
			else
				Imprecion = Imprecion + "TEXT 7 0 5 135 " + repositorio.DIRECCION1 + "\r\n";

			if( repositorio.DIRECCION2.equals("") )
				Imprecion = Imprecion + "TEXT 7 0 5 160 COL. PUESTA DEL SOL I C.P. 23090\r\n";
			else
				Imprecion = Imprecion + "TEXT 7 0 5 160 " + repositorio.DIRECCION2 + "\r\n";

			if( repositorio.DIRECCION2.equals("") )
				Imprecion = Imprecion + "TEXT 7 0 5 185 LA PAZ, BCS. MEX TEL: (612)124-01-67\r\n";
			else
				Imprecion = Imprecion + "TEXT 7 0 5 185 " + repositorio.TELEFONO + "\r\n";

			/* 1188 */         Imprecion = Imprecion + "SETMAG 1 2\r\n";
			/* 1189 */         Imprecion = Imprecion + "TEXT 7 0 5 210 RUTA " + repositorio.Vendedor.Nombre + " \r\n";
			/* 1190 */         Imprecion = Imprecion + "SETMAG 1 1\r\n";
			/* 1191 */         Imprecion = Imprecion + "LEFT\r\n";
			/* 1192 */         Imprecion = Imprecion + "TEXT 7 0 5 270 FECHA " + Utils.getFechaActual_DD_MM_YYYY() + "\r\n";
			/* 1193 */         Imprecion = Imprecion + "RIGHT\r\n";
			/* 1194 */         Imprecion = Imprecion + "TEXT 7 0 5 270 HORA: " + Utils.getHora_HH_MM_SS() + "  \r\n";
			/* 1195 */         Imprecion = Imprecion + "CENTER\r\n";

			/* 1196 */         Imprecion = Imprecion + "SETMAG 1 2\r\n";
			/* 1197 */         Imprecion = Imprecion + "TEXT 7 0 5 315 IMPRESION LIQUIDACION\r\n";
			/* 1198 */         Imprecion = Imprecion + "TEXT 0 3 5 365 ---------------------------\r\n";
			/* 1200 */         Imprecion = Imprecion + "TEXT 0 3 5 450 ----------------------------------------------------------------------\r\n";
			/* 1200 */         Imprecion = Imprecion + "TEXT 0 3 5 " + String.valueOf(490 + alturaproducto) + " ----------------------------------------------------------------------\r\n";
			/* 1201 */         Imprecion = Imprecion + "LEFT\r\n";
			/* 1202 */         Imprecion = Imprecion + "SETMAG 1 1\r\n";

			/* 1207 */         Imprecion = Imprecion + "LEFT\r\n";
			/* 1208 */         Imprecion = Imprecion + "TEXT 7 0 5 385 FECHA-HORA\r\n";

			/* 1207 */         Imprecion = Imprecion + "CENTER\r\n";
			/* 1208 */         Imprecion = Imprecion + "TEXT 7 0 5 385 TIPO\r\n";

			/* 1207 */         Imprecion = Imprecion + "RIGHT\r\n";
			/* 1208 */         Imprecion = Imprecion + "TEXT 7 0 5 385 REQUIERE    \r\n";

			/* 1209 */         Imprecion = Imprecion + "LEFT\r\n";
			/* 1212 */         Imprecion = Imprecion + "TEXT 7 0 5 415 MONTO   CLIENTE\r\n";

			/* 1214 */         for (int x = 0; x < repositorio.lsVenta.size(); x++)
			{
				/* 1216 */           BONota producto = (BONota)repositorio.lsVenta.get(x);

				if(producto.Credito == 0)
				{
					Total = Total + producto.Total;
				}
				else
				{
					TotalCredito = TotalCredito + producto.Total;
				}

				/* 1218 */           Imprecion = Imprecion + "LEFT\r\n";
				/* 1219 */           Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(480 + x * 70) + " " + 
						Utils.Split(producto.FechaCreacion, " ")[1] + "\r\n";

				/* 1218 */           Imprecion = Imprecion + "CENTER\r\n";
				/* 1219 */           Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(480 + x * 70) + " " + 
						producto.DiscriminantePago + "\r\n";

				if(producto.TipoOperacion.equals("REMICION"))
				{
					/* 1218 */           Imprecion = Imprecion + "RIGHT\r\n";
					/* 1219 */           Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(480 + x * 70) + " REMISION     \r\n";
				}
				else
				{
					/* 1218 */           Imprecion = Imprecion + "RIGHT\r\n";
					/* 1219 */           Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(480 + x * 70) + " " + 
							producto.TipoOperacion + "     \r\n";
				}

				/* 1220 */           Imprecion = Imprecion + "LEFT\r\n";
				/* 1223 */           Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(505 + x * 70) + " " + 
						"$" + Utils.rigthPad(Utils.toMoneyFormat((float) producto.Total), " ", 8) + " " + producto.Cliente + "\r\n";
			}

			/* 1071 */         Imprecion = Imprecion + "SETMAG 1 2\r\n";

			/* 1072 */         Imprecion = Imprecion + "LEFT\r\n";
			/* 1073 */         Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(500 + alturaproducto) + " TOTAL EFECTIVO \r\n";
			/* 1074 */         Imprecion = Imprecion + "RIGHT\r\n";
			/* 1076 */         Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(500 + alturaproducto) + " $" + 
					Utils.leftPad(Utils.toMoneyFormat((float)Total), " ", 12) + "  \r\n";


			/* 1072 */         Imprecion = Imprecion + "LEFT\r\n";
			/* 1073 */         Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(540 + alturaproducto) + " TOTAL CREDITO \r\n";
			/* 1074 */         Imprecion = Imprecion + "RIGHT\r\n";
			/* 1076 */         Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(540 + alturaproducto) + " $" + 
					Utils.leftPad(Utils.toMoneyFormat((float)TotalCredito), " ", 12) + "  \r\n";



			/* 1240 */         Imprecion = Imprecion + "CENTER\r\n";

			/* 1196 */         Imprecion = Imprecion + "SETMAG 1 2\r\n";
			/* 1200 */         Imprecion = Imprecion + "TEXT 0 3 5 " + String.valueOf(640 + alturaproducto) + " ----------------------------------------------------------------------\r\n";

			/* 1239 */         Imprecion = Imprecion + "SETMAG 1 1\r\n";
			/* 1245 */         Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(650 + alturaproducto) + " NOMBRE Y FIRMA VENDEDOR\r\n";

			/* 1196 */         Imprecion = Imprecion + "SETMAG 1 2\r\n";
			/* 1200 */         Imprecion = Imprecion + "TEXT 0 3 5 " + String.valueOf(770 + alturaproducto) + " ----------------------------------------------------------------------\r\n";

			/* 1239 */         Imprecion = Imprecion + "SETMAG 1 1\r\n";
			/* 1248 */         Imprecion = Imprecion + "TEXT 7 0 5 " + String.valueOf(780 + alturaproducto) + " NOMBRE Y FIRMA LIQUIDACION\r\n";

			/* 1252 */         Imprecion = Imprecion + "SETMAG 1 1\r\n";
			/* 1253 */         Imprecion = Imprecion + "CENTER\r\n";

			/* 1256 */         Imprecion = Imprecion + "SETMAG 0 0 \r\n";

			/* 1258 */         Imprecion = Imprecion + "FORM\r\n";
			/* 1259 */         Imprecion = Imprecion + "PRINT\r\n";
		}
		catch (Exception ex)
		{
			/* 1263 */         Imprecion = "";
		}

		return Imprecion;
	}

}
