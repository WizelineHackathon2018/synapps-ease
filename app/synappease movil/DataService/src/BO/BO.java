package BO;

import org.ksoap2.serialization.SoapObject;

import com.System.RMS.rmsComparator;
import com.System.RMS.rmsFilter;
import com.System.RMS.rmsInterface;
import com.System.SqLite.SQLiteManager;
import com.System.Utils.Logg;
import com.System.Utils.Utils;
import com.System.WebServices.*;

import java.lang.reflect.Field;
import java.util.*;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase; 

public abstract class BO implements Cloneable
{
    //Variables para el manejo de Procesos
    public String TipoProceso = "";//WS - Para Web Services,  RMS - Para Record Store,  SMS - Mensaje sms,  BT - Blue tooth, ML - Mail
    public String NombreProceso = "";
    public String Error = "";
    
    private Hashtable listaParametros = new Hashtable();//Lista de parametros
    //Variables para el manejo de Procesos
    
    //Variables para el manejo de RMS
    private String TipoBO = "";
    
//    protected rmsManager rms = new rmsManager();//Administrador del RMS
    private Class Definicion;
    
    protected wsManager wsClient = new wsManager();//Administrador del Web Services
    protected long idRecord;
    //Variables para el manejo de RMS
    
    static SQLiteManager sqlmanager = null;
    static SQLiteDatabase db = null;
    private String pathBD = "";
    
    public Context context;
    
	String NombreTabla = "";
	Field[] fields = null;
	String Campos = "";
    
    public BO(String tipoBO, Context context)
    {
    	this.context = context;
    	sqlmanager = new SQLiteManager(context);
        TipoBO = tipoBO;
        Definicion = this.getClass();
    	NombreTabla = TipoBO;
    	fields = Definicion.getDeclaredFields();
        
        if(db == null)
        {
        	db = sqlmanager.getWritableDatabase();
        	
        	if(!db.isOpen())
        	{
        		db = sqlmanager.getWritableDatabase();        		
        	}
        }
        
        if(db != null)
        {
        	 db.execSQL(CheckBD());
        }
    }
    
    public String getTipo()
    {
        return TipoBO;
    }
    
    public String getValorCadena(String Valor)
    {
        if( (!Valor.equalsIgnoreCase("anyType{}")) && (!Valor.equalsIgnoreCase("")) )
        {
            return Valor;
        }
        else
        {
            return "";
        }
    }

    //Metodos de operacion de WS - Implementa Proceso
//***************************************************************************************************************************************************
    //Reseta los parametros
    public void ClearParametros()
    {
        listaParametros.clear();
    }

    //Reseta los procesos
    public void ClearProceso()
    {
        NombreProceso = "";
    }

    //Agrega un proceso
    public void AgregarProceso(String NombreProceso)
    {
        this.NombreProceso = NombreProceso;
    }

    //Agrega un parametro de tipo cadena al proceso
    public void AgregarParametro(String NombreParametro, String Valor)
    {
        listaParametros.put(NombreParametro, Valor);
    }

    public abstract void AsignarValoresBOProceso();//Se implementa que valores se deben asignar del BO al Web Services

    public abstract List<BO> AsignarValoresProcesoBO(SoapObject wsColeccion);//Se implementa que valores se deben asignar del Web Services al BO

    public abstract String toString();

    public void EjecutarProceso()
    {
        //WS - Para Web Services,  RMS - Para Record Store,  SMS - Mensaje sms,  BT - Blue tooth, ML - Mail
        if(TipoProceso.equalsIgnoreCase("WS"))
        {
            this.AsignarValoresBOProceso();
            Enumeration e = listaParametros.keys();
            while(e.hasMoreElements())
            {
                String key = (String)(e.nextElement());
                String value = (String)listaParametros.get(key);
                if(key.equalsIgnoreCase("wsNamesPace"))
                {
                    wsClient.wsNamesPace = value;
                }
                else if(key.equalsIgnoreCase("wsUrl"))
                {
                    wsClient.wsUrl = value;
                }
                else if(key.equalsIgnoreCase("WebMethod"))
                {
                    wsClient.WebMethod = value;
                }
                else
                {
                    wsClient.AgregarParametro(key, value);
                }
            }
            wsClient.EjecutarWS();
            this.Error = wsClient.Error;
            wsClient.ClearParametros();//se liberan los recuros de los parametros
        }
        else if(TipoProceso.equalsIgnoreCase("SQL"))//Si es RMS
        {
            //Consultar el RMS
            if(NombreProceso.equalsIgnoreCase("SQL.Agregar"))//Usar siempre estos nombres
            {
                try
                {
                    this.InsertarRMS();
                } 
                catch (Exception ex){
                	
                	Logg.error("Error SQL.Agregar: " + Arrays.toString(ex.getStackTrace()));
                	
                    //ex.printStackTrace();
                    
                }
            }//Actualizar el RMS
            else if(NombreProceso.equalsIgnoreCase("SQL.Actualizar"))//Usar siempre estos nombres
            {
                try {
                    this.updateRMS();
                } catch (Exception ex) {
                	
                	Logg.error("Error SQL.Actualizar: " + Arrays.toString(ex.getStackTrace()));
                	
                    //ex.printStackTrace();
                    
                }
            }//Elimina el RMS
            else if(NombreProceso.equalsIgnoreCase("SQL.Eliminar"))//Usar siempre estos nombres
            {
                try {
                    this.deleteRMS();
                } catch (Exception ex) {
                	
                	Logg.error("Error SQL.Eliminar: " + Arrays.toString(ex.getStackTrace()));
                	
                    //ex.printStackTrace();
                    
                }
            }//Elimina todos los RMS
            else if(NombreProceso.equalsIgnoreCase("SQL.EliminarTodos"))//Usar siempre estos nombres
            {
                try {
                    this.deleteAllRMS();
                } catch (Exception ex) {
                	
                	Logg.error("Error SQL.EliminarTodos: " + Arrays.toString(ex.getStackTrace()));
                	
                    //ex.printStackTrace();
                    
                }
            }
            else if(NombreProceso.equalsIgnoreCase("SQL.Dispose"))//Usar siempre estos nombres
            {
                try {
                    this.DisposeRMS();
                } catch (Exception ex) {
                	
                	Logg.error("Error SQL.Dispose: " + Arrays.toString(ex.getStackTrace()));
                	
                    //ex.printStackTrace();
                }
            }
        }
    }

    public List<BO> ConsultarProceso()
    {
        //WS - Para Web Services,  RMS - Para Record Store,  SMS - Mensaje sms,  BT - Blue tooth, ML - Mail
        if(TipoProceso.equalsIgnoreCase("WS"))
        {
            //this.AsignarValoresBOProceso();
            Enumeration e = listaParametros.keys();
            while(e.hasMoreElements())
            {
                String key = (String)(e.nextElement());
                String value = (String)listaParametros.get(key);
                if(key.equalsIgnoreCase("wsNamesPace"))
                {
                    wsClient.wsNamesPace = value;
                }
                else if(key.equalsIgnoreCase("wsUrl"))
                {
                    wsClient.wsUrl = value;
                }
                else if(key.equalsIgnoreCase("WebMethod"))
                {
                    wsClient.WebMethod = value;
                }
                else
                {
                    wsClient.AgregarParametro(key, value);
                }
            }
            //Ejecuta el Web services y regresa una coleccion de BO con los valores obtenidos del web services
            SoapObject wsColeccion = wsClient.ConsultarWS();
            this.Error = wsClient.Error;
            return this.AsignarValoresProcesoBO(wsColeccion);
        }
        else if(TipoProceso.equalsIgnoreCase("SQL"))//Si es RMS
        {
            if(NombreProceso.equalsIgnoreCase("SQL.Consultar"))//Usar siempre estos nombres
            {
            	int idSortField = 0;
            	
            	int idFilterField = 0;
            	String filtro = "";
            	
            	int idFilterField2 = 0;
            	String filtro2 = "";
            	
            	Enumeration e = this.listaParametros.keys();
            	while (e.hasMoreElements())
            	{
            		String key = (String)e.nextElement();
            		if (key.equalsIgnoreCase("idSortField"))
            		{
            			int value = Integer.parseInt((String)this.listaParametros.get(key));
            			idSortField = value;
            		}
            		else if (key.equalsIgnoreCase("idFilterField"))
            		{
            			int value = Integer.parseInt((String)this.listaParametros.get(key));
            			idFilterField = value;
            		}
            		else if (key.equalsIgnoreCase("idFilterField2"))
            		{
            			int value = Integer.parseInt((String)this.listaParametros.get(key));
            			idFilterField2 = value;
            		}
            		else if (key.equalsIgnoreCase("filtro"))
            		{
            			String value = (String)this.listaParametros.get(key);
            			filtro = value;
            		} 
            		else 
            		{
            			if (!key.equalsIgnoreCase("filtro2"))
            				continue;
            			String value = (String)this.listaParametros.get(key);
            			filtro2 = value;
            		}
            	}
                
            	return ConsultarRMS(idSortField, idFilterField, idFilterField2, filtro, filtro2);
            	
            }
        }
        
        return null;
    }    
    //***************************************************************************************************************************************************
    //Metodos de operacion de WS -Implementa Proceso

    private void InsertarRMS()
    {
        if(db != null)
        {
        	if(!db.isOpen())
        	{
        		db = sqlmanager.getWritableDatabase();        		
        	}
        	
        	 db.execSQL(this.getInsertBO());
        	 this.idRecord = getLastInsertId();
        }
    }
    
    private void updateRMS()
    {
        if(db != null)
        {
        	if(!db.isOpen())
        	{
        		db = sqlmanager.getWritableDatabase();        		
        	}
       	 	
        	 db.execSQL(this.getUpdateBO());
        }
    }
    
    private void deleteRMS()
    {
        if(db != null)
        {
        	if(!db.isOpen())
        	{
        		db = sqlmanager.getWritableDatabase();        		
        	}
       	 	
        	 db.execSQL(this.getDeleteBO());
        }
    }
    
    private void deleteAllRMS()
    {
        if(db != null)
        {
        	if(!db.isOpen())
        	{
        		db = sqlmanager.getWritableDatabase();        		
        	}
       	 	
        	 db.execSQL(this.getDropBO());
        	 db.execSQL(CheckBD());
        }
    }
    
    private void DisposeRMS()
    {
        if(db != null)
        {
        	if(!db.isOpen())
        	{
        		db = sqlmanager.getWritableDatabase();        		
        	}
       	 	
        	 db.execSQL(this.getDisposeSQLBO());
        }
    } 
    
    private String CheckBD()
    {
    	Campos = "";
    	if(fields.length > 0)
    	{
        	Campos = "create table if not exists " + NombreTabla + " ( COl_ID integer primary key autoincrement ";
        	for(int x = 0; x < fields.length; x++)
        	{
        		Field field = fields[x];
        		String nombre = field.getName();
        		if(nombre.indexOf("ID_") == -1)
        		{
        			String Tipo = field.getType().getName();
        			
        			//Logg.info("Tipo dato: " + Tipo);
        			
        			if(Tipo.equals("java.lang.String"))
        			{
        				Campos = Campos + ", " + nombre + " text";
        			}
        			else if(Tipo.equals("int"))
        			{
        				Campos = Campos + ", " + nombre + " integer"; //java.util.Date
        			}
        			else if(Tipo.equals("float") || Tipo.equals("double") || Tipo.equals("long") || Tipo.equals("java.util.Date"))
        			{
        				Campos = Campos + ", " + nombre + " REAL";
        			}
        			else
        			{
        				Campos = Campos + ", " + nombre + " text";
        			}
        		}
        	}
        	Campos = Campos + " )";
    	}
    	return Campos;
    }
    
    private String getInsertBO()
    {
    	String Campos1 = "";
    	String Campos2 = "";
    	Campos = "";
    	boolean primero = true;
    	
    	if(fields.length > 0)
    	{
        	Campos1 = " insert into " + NombreTabla + " ( ";
        	Campos2 = " values ( ";
        	for(int x = 0; x < fields.length; x++)
        	{
        		Field field = fields[x];
        		String nombre = field.getName();
        		
        		if(nombre.indexOf("ID_") == -1)
        		{
        			String Tipo = field.getType().getName();
        			if(Tipo.equals("java.lang.String"))
        			{
        				if(primero)
        				{
        					Campos1 = Campos1 + " " + nombre;
        					
        					field.setAccessible(true);
        					Object value = "";
        					try {
        						value = field.get(this);
        						
        						if(value.toString().equals("anyType{}"))
        						{
        							value = "";
        						}
							}
        					catch (IllegalArgumentException e) 
        					{
								//e.printStackTrace();
	    			    		Logg.error("Error al insertar: " + Arrays.toString(e.getStackTrace()));
							}
        					catch (IllegalAccessException e)
							{
								//e.printStackTrace();
	    			    		Logg.error("Error al insertar: " + Arrays.toString(e.getStackTrace()));
							}
        					Campos2 = Campos2 + " '" + value.toString() + "'";
        					
        					primero = false;
        				}
        				else
        				{
        					Campos1 = Campos1 + ", " + nombre;
        					
        					field.setAccessible(true);
        					Object value = "";
        					try {
        						value = field.get(this);
        						
        						if(value.toString().equals("anyType{}"))
        						{
        							value = "";
        						}
							}
        					catch (IllegalArgumentException e) 
        					{
								//e.printStackTrace();
        			    		Logg.error("Error al insertar: " + Arrays.toString(e.getStackTrace()));
							}
        					catch (IllegalAccessException e)
							{
								//e.printStackTrace();
        			    		Logg.error("Error al insertar: " + Arrays.toString(e.getStackTrace()));
							}
        					Campos2 = Campos2 + ", '" + value.toString() + "'";
        				}
        			}
        			else if(Tipo.equals("int") || Tipo.equals("float") || Tipo.equals("double") || Tipo.equals("long"))  //long
        			{
        				if(primero)
        				{
        					Campos1 = Campos1 + " " + nombre;
        					
        					field.setAccessible(true);
        					Object value = "";
        					try {
        						value = field.get(this);
							}
        					catch (IllegalArgumentException e) 
        					{
								//e.printStackTrace();
	    			    		Logg.error("Error al insertar: " + Arrays.toString(e.getStackTrace()));
							}
        					catch (IllegalAccessException e)
							{
								//e.printStackTrace();
	    			    		Logg.error("Error al insertar: " + Arrays.toString(e.getStackTrace()));
							}
        					Campos2 = Campos2 + " " + value.toString();
        					
        					primero = false;
        				}
        				else
        				{
        					Campos1 = Campos1 + ", " + nombre;
        					
        					field.setAccessible(true);
        					Object value = "";
        					try {
        						value = field.get(this);
							}
        					catch (IllegalArgumentException e) 
        					{
								//e.printStackTrace();
        			    		Logg.error("Error al insertar: " + Arrays.toString(e.getStackTrace()));
							}
        					catch (IllegalAccessException e)
							{
								//e.printStackTrace();
        			    		Logg.error("Error al insertar: " + Arrays.toString(e.getStackTrace()));
							}
        					Campos2 = Campos2 + ", " + value.toString();
        				}
        			}
        			
        			else if(Tipo.equals("java.util.Date"))  //java.util.Date
        			{
        				if(primero)
        				{
        					Campos1 = Campos1 + " " + nombre;
        					
        					field.setAccessible(true);
        					Object value = "";
        					try {
        						value = field.get(this);
        						Date valdate = (Date) value;
        						value = valdate.getTime();
							}
        					catch (IllegalArgumentException e) 
        					{
								//e.printStackTrace();
	    			    		Logg.error("Error al insertar: " + Arrays.toString(e.getStackTrace()));
							}
        					catch (IllegalAccessException e)
							{
								//e.printStackTrace();
	    			    		Logg.error("Error al insertar: " + Arrays.toString(e.getStackTrace()));
							}
        					Campos2 = Campos2 + " " + value.toString();
        					
        					primero = false;
        				}
        				else
        				{
        					Campos1 = Campos1 + ", " + nombre;
        					
        					field.setAccessible(true);
        					Object value = "";
        					try {
        						value = field.get(this);
        						Date valdate = (Date) value;
        						value = valdate.getTime();
							}
        					catch (IllegalArgumentException e) 
        					{
								//e.printStackTrace();
        			    		Logg.error("Error al insertar: " + Arrays.toString(e.getStackTrace()));
							}
        					catch (IllegalAccessException e)
							{
								//e.printStackTrace();
        			    		Logg.error("Error al insertar: " + Arrays.toString(e.getStackTrace()));
							}
        					Campos2 = Campos2 + ", " + value.toString();
        				}
        			}
        			
        			else
        			{
        				if(primero)
        				{
        					Campos1 = Campos1 + " " + nombre;
        					
        					field.setAccessible(true);
        					Object value = "";
        					try {
        						value = field.get(this);
        						
        						if(value.toString().equals("anyType{}"))
        						{
        							value = "";
        						}
							}
        					catch (IllegalArgumentException e) 
        					{
								//e.printStackTrace();
	    			    		Logg.error("Error al insertar: " + Arrays.toString(e.getStackTrace()));
							}
        					catch (IllegalAccessException e)
							{
								//e.printStackTrace();
	    			    		Logg.error("Error al insertar: " + Arrays.toString(e.getStackTrace()));
							}
        					Campos2 = Campos2 + " '" + value.toString() + "'";
        					
        					primero = false;
        				}
        				else
        				{
        					Campos1 = Campos1 + ", " + nombre;
        					
        					field.setAccessible(true);
        					Object value = "";
        					try {
        						value = field.get(this);
        						
        						if(value.toString().equals("anyType{}"))
        						{
        							value = "";
        						}
							}
        					catch (IllegalArgumentException e) 
        					{
								//e.printStackTrace();
	    			    		Logg.error("Error al insertar: " + Arrays.toString(e.getStackTrace()));
							}
        					catch (IllegalAccessException e)
							{
								//e.printStackTrace();
	    			    		Logg.error("Error al insertar: " + Arrays.toString(e.getStackTrace()));
							}
        					Campos2 = Campos2 + ", '" + value.toString() + "'";
        				}
        			}
        		}
        	}
        	Campos1 = Campos1 + " ) ";
        	Campos2 = Campos2 + " ) ";
    	}
    	Campos = Campos1 + Campos2;
    	
    	Logg.info("Insert: " + Campos);
    	return Campos;
    }
    
    private String getUpdateBO()
    {
    	Campos = "";  //update BOGps set  'Bateria', 'Coordenadas', 'FechaHora', 'Wifi', 'Velocidad', 'TipoAviso' where COl_ID = 3 
    	
    	if(fields.length > 0)
    	{
        	Campos = "update " + NombreTabla + " set ";
        	for(int x = 0; x < fields.length; x++)
        	{
        		Field field = fields[x];
        		String nombre = field.getName();
        		if(nombre.indexOf("ID_") == -1)
        		{
        			String Tipo = field.getType().getName();
        			if(Tipo.equals("java.lang.String"))
        			{
    					field.setAccessible(true);
    					Object value = "";
    					try {
    						value = field.get(this);
						}
    					catch (IllegalArgumentException e) 
    					{
							//e.printStackTrace();
    			    		Logg.error("Error al actualizar: " + Arrays.toString(e.getStackTrace()));
						}
    					catch (IllegalAccessException e)
						{
							//e.printStackTrace();
    			    		Logg.error("Error al actualizar: " + Arrays.toString(e.getStackTrace()));
						}
        				
        				Campos = Campos + " " + nombre + " = '" + value.toString() + "',";
        			}
        			else if(Tipo.equals("int") || Tipo.equals("float") || Tipo.equals("double") || Tipo.equals("long")) //long
        			{
    					field.setAccessible(true);
    					Object value = "";
    					try {
    						value = field.get(this);
						}
    					catch (IllegalArgumentException e) 
    					{
							//e.printStackTrace();
    			    		Logg.error("Error al actualizar: " + Arrays.toString(e.getStackTrace()));
						}
    					catch (IllegalAccessException e)
						{
							//e.printStackTrace();
    			    		Logg.error("Error al actualizar: " + Arrays.toString(e.getStackTrace()));
						}
        				
        				Campos = Campos + " " + nombre + " = " + value.toString() + ",";
        			}

        			else if(Tipo.equals("java.util.Date"))  //java.util.Date
        			{
    					field.setAccessible(true);
    					Object value = "";
    					try {
    						value = field.get(this);
    						Date valdate = (Date) value;
    						value = valdate.getTime();
						}
    					catch (IllegalArgumentException e) 
    					{
							//e.printStackTrace();
    			    		Logg.error("Error al actualizar: " + Arrays.toString(e.getStackTrace()));
						}
    					catch (IllegalAccessException e)
						{
							//e.printStackTrace();
    			    		Logg.error("Error al actualizar: " + Arrays.toString(e.getStackTrace()));
						}
        				
        				Campos = Campos + " " + nombre + " = " + value.toString() + ",";
        			}

        			else
        			{
    					field.setAccessible(true);
    					Object value = "";
    					try {
    						value = field.get(this);
						}
    					catch (IllegalArgumentException e) 
    					{
							//e.printStackTrace();
    			    		Logg.error("Error al actualizar: " + Arrays.toString(e.getStackTrace()));
						}
    					catch (IllegalAccessException e)
						{
							//e.printStackTrace();
    			    		Logg.error("Error al actualizar: " + Arrays.toString(e.getStackTrace()));
						}
        				
        				Campos = Campos + " " + nombre + " = '" + value.toString() + "',";
        			}
        		}
        	}
        	Campos = Campos.substring(0, Campos.length() - 1) + " where COl_ID = " + this.idRecord;
    	}
    	
    	Logg.info("Update: " + Campos);
    	return Campos;
    }
    
    private String getDeleteBO()
    {
    	return "delete from " + NombreTabla + " where COl_ID = " + this.idRecord;
    }
    
    private String getDropBO()
    {
    	return "drop table " + NombreTabla;
    }
    
    private String getDisposeSQLBO()
    {
    	return "drop table " + NombreTabla;
    }
    
    private List<BO> ConsultarRMS(int idSortField, final int idFilterField, final int idFilterField2, final String filtro, final String filtro2)
    {
    	Campos = "";
    	List<BO> empList = null;
    	
    	if(fields.length > 0)
    	{
        	Campos = "select COl_ID";
        	for(int x = 0; x < fields.length; x++)
        	{
        		Field field = fields[x];
        		String nombre = field.getName();
        		if(nombre.indexOf("ID_") == -1)
        		{
    				Campos = Campos + ", " + nombre;
        		}
        	}
        	Campos = Campos + " from " + NombreTabla;
        	
        	Logg.info("Select: " + Campos);
        	
        	//crea el where 
        	String[] args = null;
        	
        	//hace la consulta y el set de los campos
        	Cursor c = null;
        	
            if(db != null)
            {
            	if(!db.isOpen())
            	{
            		db = sqlmanager.getWritableDatabase();
            	}

            	 c = db.rawQuery(Campos, args);
            }
        	
        	if (c.moveToFirst())
        	{
       	     	//Recorremos el cursor hasta que no haya más registros
        		empList = new ArrayList<BO>();

        	     do
        	     {
        	    	 BO item = null;
					item = (BO) this.clone();
					item.idRecord = 0;

    	        	for(int x = 0; x < fields.length; x++)
    	        	{
    	        		Field field = fields[x];
    	        		String nombre = field.getName();
    	        		if(nombre.indexOf("ID_") == -1)
    	        		{
    	        			String Tipo = field.getType().getName();

    	        			//Logg.info("Tipo: " + Tipo);

    	        			if(Tipo.equals("java.lang.String"))
    	        			{
    	        				String valor= c.getString(c.getColumnIndex(nombre));
            					field.setAccessible(true);

            					try {
            						field.set(item, valor);
    							}
            					catch (IllegalArgumentException e)
            					{
    								//e.printStackTrace();
            			    		Logg.error("Error al consultar: " + Arrays.toString(e.getStackTrace()));
    							}
            					catch (IllegalAccessException e)
    							{
    								//e.printStackTrace();
            			    		Logg.error("Error al consultar: " + Arrays.toString(e.getStackTrace()));
    							}
    	        			}
    	        			else if(Tipo.equals("int"))
    	        			{
    	        				int valor= c.getInt(c.getColumnIndex(nombre));
            					field.setAccessible(true);

            					try {
            						field.set(item, valor);
    							}
            					catch (IllegalArgumentException e)
            					{
    								//e.printStackTrace();
            			    		Logg.error("Error al consultar: " + Arrays.toString(e.getStackTrace()));
    							}
            					catch (IllegalAccessException e)
    							{
    								//e.printStackTrace();
            			    		Logg.error("Error al consultar: " + Arrays.toString(e.getStackTrace()));
    							}
    	        			}
    	        			else if(Tipo.equals("float"))
    	        			{
    	        				float valor= c.getFloat(c.getColumnIndex(nombre));
            					field.setAccessible(true);

            					try {
            						field.set(item, valor);
    							}
            					catch (IllegalArgumentException e)
            					{
    								//e.printStackTrace();
            			    		Logg.error("Error al consultar: " + Arrays.toString(e.getStackTrace()));
    							}
            					catch (IllegalAccessException e)
    							{
    								//e.printStackTrace();
            			    		Logg.error("Error al consultar: " + Arrays.toString(e.getStackTrace()));
    							}
    	        			}
    	        			else if(Tipo.equals("double"))
    	        			{
    	        				double valor= c.getDouble(c.getColumnIndex(nombre));
            					field.setAccessible(true);

            					try {
            						field.set(item, valor);
    							}
            					catch (IllegalArgumentException e)
            					{
            			    		Logg.error("Error al consultar: " + Arrays.toString(e.getStackTrace()));
    								//e.printStackTrace();
    							}
            					catch (IllegalAccessException e)
    							{
            			    		Logg.error("Error al consultar: " + Arrays.toString(e.getStackTrace()));
    								//e.printStackTrace();
    							}
    	        			}
    	        			else if(Tipo.equals("long"))
    	        			{
    	        				long valor= c.getLong(c.getColumnIndex(nombre));
            					field.setAccessible(true);

            					try {
            						field.set(item, valor);
    							}
            					catch (IllegalArgumentException e)
            					{
            			    		Logg.error("Error al consultar: " + Arrays.toString(e.getStackTrace()));
    								//e.printStackTrace();
    							}
            					catch (IllegalAccessException e)
    							{
            			    		Logg.error("Error al consultar: " + Arrays.toString(e.getStackTrace()));
    								//e.printStackTrace();
    							}
    	        			}

    	        			else if(Tipo.equals("java.util.Date"))
    	        			{
    	        				long valor= c.getLong(c.getColumnIndex(nombre));
            					field.setAccessible(true);

            					Date fecha = new Date();
            					fecha.setTime(valor);

            					try {
            						field.set(item, fecha);
    							}
            					catch (IllegalArgumentException e)
            					{
            			    		Logg.error("Error al consultar: " + Arrays.toString(e.getStackTrace()));
    								//e.printStackTrace();
    							}
            					catch (IllegalAccessException e)
    							{
            			    		Logg.error("Error al consultar: " + Arrays.toString(e.getStackTrace()));
    								//e.printStackTrace();
    							}
    	        			}

    	        			else
    	        			{
    	        				String valor= c.getString(c.getColumnIndex(nombre));
            					field.setAccessible(true);

            					try {
            						field.set(item, valor);
    							}
            					catch (IllegalArgumentException e) 
            					{
            			    		Logg.error("Error al consultar: " + Arrays.toString(e.getStackTrace()));
    								//e.printStackTrace();
    							}
            					catch (IllegalAccessException e)
    							{
            			    		Logg.error("Error al consultar: " + Arrays.toString(e.getStackTrace()));
    								//e.printStackTrace();
    							}
    	        			}
    	        			
    	        		}
    	        	}

    	        	item.idRecord = c.getLong(c.getColumnIndex("COl_ID"));

    	        	empList.add(item);
        	     } 
        	     while(c.moveToNext());
        	}
    	}

    	if(empList != null)
    	{
        	if(empList.size() == 0)
        	{
        		return null;
        	}
        	else
        	{
        		//Si tiene elementos aplicamos los filtros y el ordenamiento
        		if((!filtro.equals("")) || (!filtro2.equals("")))
        		{
            	    return removeBO(new rmsFilter<BO>() 
                    {
                    	int idFilter = idFilterField; 
                    	int idFilter2 = idFilterField2;
                    	String filt = filtro;
                    	String filt2 = filtro2;

                    	public boolean shouldRemove(BO BOObject)
                    	{
                    		rmsInterface rmsObject = (rmsInterface) BOObject;
                    	        	
                    	    if (filt2.equals(""))
                    	    {
                    	    	String s = rmsObject.getFieldValue(idFilter);

                    	        Logg.info("Filtro1: " + filt + " Val: " + s);

                    	        if(s.indexOf(filt) > -1)
                    	        {
                    	        	return false;
                    	        }
                    	    }
                    	    else
                    	    {
                    	    	String s = rmsObject.getFieldValue(idFilter);
                    	        String s2 = rmsObject.getFieldValue(idFilter2);

                    	        Logg.info("Filtro1: " + filt + " Val: " + s);
                    	        Logg.info("Filtro2: " + filt2 + " Val2: " + s2);

                    	        if((s.indexOf(filt) > -1) && (s2.indexOf(filt2) > -1))
                    	        {
                    	        	return false;
                    	        }
                    	    }
                    	    return true;
                    	}
                    }, empList, idFilterField, idFilterField2, filtro, filtro2);
        		}

        		//validamos si tiene el campo de ordenamiento
        		if(idSortField > -1)
        		{
        			Collections.sort(empList, new rmsComparator(idSortField));
        		}
        		
            	if(empList.size() == 0)
            	{
            		return null;
            	}

        		return empList;
        	}
    	}
    	else
    	{
    		return null;
    	}
    }

    //Metodos para elminar los registros que no esten dentro del criterio
    public List<BO> removeBO(rmsFilter<BO> filter, List<BO> list, int idField, int idField2, String criterio, String criterio2)
    {
        Iterator<BO> boIterator = list.iterator();
        while (boIterator.hasNext()) 
        {
        	BO c = boIterator.next();
            if (filter.shouldRemove(c)) 
            {
                boIterator.remove();
            }
        }
        
    	if(list.size() == 0)
    	{
    		return null;
    	}
        
        return list;
    }

    public long getLastInsertId()
    {
        long index = 0;
        Cursor cursor = null;
    	
    	try
    	{
        	if(!db.isOpen())
        	{
        		db = sqlmanager.getWritableDatabase();        		
        	}
    		
            cursor = db.query(
                    "sqlite_sequence",
                    new String[]{"seq"},
                    "name = ?",
                    new String[]{NombreTabla},
                    null,
                    null,
                    null,
                    null
            );
            if (cursor.moveToFirst()) {
                index = cursor.getLong(cursor.getColumnIndex("seq"));
            }
    	}
    	catch(Exception ex)
    	{
    		Logg.error("Error en la secuencia: " + NombreTabla + ", " + Arrays.toString(ex.getStackTrace()));
    		index = Utils.Generate();
    	}
    	finally
    	{
    		if(cursor != null)
    		{
    			if(!cursor.isClosed())
    			{
    				cursor.close(); 				
    			}
    		}
    	}
        
        return index;
    }
    
    //Hace la liberacion de recursos
    public void Dispose()
    {
        wsClient.Dispose();
        listaParametros = null;         
        wsClient = null;
    }
    
    public Object clone()
    {
        Object obj = null;
        try{
        	
        	//obj = this.getClass().newInstance();
        	
            obj = super.clone();
        	
        }catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return obj;
    }    
    
}

