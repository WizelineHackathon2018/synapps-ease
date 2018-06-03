package com.comynt.launa.adapt;

import BO.*;

import com.comynt.launa.Repositorio;
import com.comynt.launa.frm.frmActividades;
import com.comynt.launa.R;

import com.System.Utils.Logg;
import com.System.Utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//Solo ver un dato arriba y uno abajo, aplica para inventario y liquidacion, el de liquidacion se usa igual para reimprimir  
public class adaptVerDatos extends BaseAdapter implements Filterable
{
	public List<BO> dataoriginal = new ArrayList<BO>();
    public List<BO> data = new ArrayList<BO>();
    
    private Activity activity;
    private LayoutInflater inflater = null;
    private View vi;
    private int Tipo = 0;
    private Filter myFilter;
    private boolean cambio = true;

    public adaptVerDatos(Activity a, List<BO> d, int Tipo)
    {
        activity = a;
        this.Tipo = Tipo;
        
        if(d != null)
        {
            for(int x = 0; x < d.size(); x++)
            {
            	data.add(d.get(x));
            	dataoriginal.add(d.get(x));
            }
        }

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    public adaptVerDatos(Fragment a, List<BO> d, int Tipo)
    {
        //activity = a;
        this.Tipo = Tipo;
        
        if(d != null)
        {
            for(int x = 0; x < d.size(); x++)
            {
            	data.add(d.get(x));
            	dataoriginal.add(d.get(x));
            }
        }
        
        inflater = (LayoutInflater)a.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }    

    public int getCount() {
        return data.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
    	if(Tipo == 0) //Si es 0, entonces es inventario
    	{
        	BOAlmacenMovil AlmacenMovil = null;
        	AlmacenMovil = (BOAlmacenMovil)data.get(position);
        	
        	Logg.info("GENERAR ITEM BOAlmacenMovil" + position);
        	
            vi = inflater.inflate(R.layout.itemlsinventario2, null);
            
            Button txtSKU =(Button)vi.findViewById(R.id.txtbtnSKU);
            Button txtINICIAL =(Button)vi.findViewById(R.id.txtbtnINICIAL);
            Button txtVENTA =(Button)vi.findViewById(R.id.txtbtnVENTA);
            Button txtEXISTENCIA =(Button)vi.findViewById(R.id.txtbtnEXISTENCIA);
            Button txtUNIDADES =(Button)vi.findViewById(R.id.txtbtnUNIDADES);
            
            TextView txtinvDescripcion =(TextView)vi.findViewById(R.id.txtinvDescripcion);
            
            txtSKU.setText(Utils.leftPad(AlmacenMovil.Codigo, "0", 6));
            
            txtINICIAL.setText(Utils.toKilosFormat(AlmacenMovil.CantidadInicial));
            txtVENTA.setText(Utils.toKilosFormat(AlmacenMovil.Venta));
            txtEXISTENCIA.setText( Utils.toKilosFormat( (AlmacenMovil.CantidadInicial - AlmacenMovil.Venta) ));
            
            Logg.info("Producto:" + AlmacenMovil.Codigo + " inicio: " + Utils.toKilosFormat(AlmacenMovil.CantidadInicial) + 
            		", venta:" + Utils.toKilosFormat(AlmacenMovil.Venta) + 
            		", fin:" + Utils.toKilosFormat( (AlmacenMovil.CantidadInicial - AlmacenMovil.Venta)));
            
            txtUNIDADES.setText(Utils.rigthPad(AlmacenMovil.Unidad, " ", 3));
            
            txtinvDescripcion.setText(AlmacenMovil.Producto);
            
            return vi;
    	}
    	if(Tipo == 2) //Si es 2, entonces es precio publico
    	{
        	BOProducto AlmacenMovil = null;
        	AlmacenMovil = (BOProducto)data.get(position);
        	
        	Logg.info("GENERAR ITEM BOProducto " + position);
        	
            vi = inflater.inflate(R.layout.itemlsverdato, null);
            
            TextView lblfechaagenda =(TextView)vi.findViewById(R.id.lblArribaDato);
            lblfechaagenda.setText(Utils.leftPad(AlmacenMovil.Codigo, "0", 6) + " | Precio publico: " + AlmacenMovil.PrecioPublico);
            
            TextView txtItemHora =(TextView)vi.findViewById(R.id.lblAbajoDato);
            txtItemHora.setText(AlmacenMovil.Descripcion);
            
            return vi;
    	}
    	else //Si es 1 entonces es ver las notas
    	{
        	BONota Nota = null;
        	Nota = (BONota)data.get(position);
        	
        	Logg.info("GENERAR ITEM BONota" + position);

            if(Nota.Discriminante.equals("Venta"))
            {
                vi = inflater.inflate(R.layout.itemlsverdatonota, null);
                
                TextView lblfechaagenda =(TextView)vi.findViewById(R.id.lblArribaDato);
                lblfechaagenda.setText(Nota.Codigo + " | " + Nota.Cliente);
            	
                TextView txtItemHora =(TextView)vi.findViewById(R.id.lblAbajoDato);
                txtItemHora.setText(Nota.Discriminante + " | " + Utils.Split(Nota.FechaCreacion, " ")[1] + " | " + "$" + Utils.rigthPad(Utils.toMoneyFormat((float) Nota.Total), " ", 8) + 
                		" | " + Nota.DiscriminantePago + " | " + Nota.FolioDia);
            }
            else
            {
                vi = inflater.inflate(R.layout.itemlsverdatonota, null);
                
                TextView lblfechaagenda =(TextView)vi.findViewById(R.id.lblArribaDato);
                lblfechaagenda.setText(Nota.Codigo + " | " + Nota.Cliente);
            	
            	if(Nota.PagoEfectivo > 0)
            	{
                    TextView txtItemHora =(TextView)vi.findViewById(R.id.lblAbajoDato);
                    txtItemHora.setText(Nota.Discriminante + " | " + Utils.Split(Nota.FechaCreacion, " ")[1] + " | " + "$" + Utils.rigthPad(Utils.toMoneyFormat((float) Nota.Total), " ", 8) + 
                    		" | EFECTIVO | " + Nota.FolioDia);
            	}
            	else if (Nota.PagoCheque > 0)
            	{
                    TextView txtItemHora =(TextView)vi.findViewById(R.id.lblAbajoDato);
                    txtItemHora.setText(Nota.Discriminante + " | " + Utils.Split(Nota.FechaCreacion, " ")[1] + " | " + "$" + Utils.rigthPad(Utils.toMoneyFormat((float) Nota.Total), " ", 8) + 
                    		" | CHEQUE | " + Nota.FolioDia);
            	}
            	else
            	{
                    TextView txtItemHora =(TextView)vi.findViewById(R.id.lblAbajoDato);
                    txtItemHora.setText(Nota.Discriminante + " | " + Utils.Split(Nota.FechaCreacion, " ")[1] + " | " + "$" + Utils.rigthPad(Utils.toMoneyFormat((float) Nota.Total), " ", 8) + 
                    		" | DEPOSITO | " + Nota.FolioDia);
            	}
            }

            return vi;
    	}
    }
    
    
    //implmentacion del filtro por tipo, 0 entonces es inventario, 1 es por notas de venta, y 2 por precios publico
    @Override
    public Filter getFilter()
    {
        if (myFilter == null)
            myFilter = new filtDatos();
        
        return myFilter;
    }

    private class filtDatos extends Filter
    {
    	List<BO> lsNewList = new ArrayList<BO>();
    	
    	@Override
        protected FilterResults performFiltering(CharSequence constraint) 
    	{
    		FilterResults results = new FilterResults();

    		Logg.info("Actividades : " + dataoriginal.size());
    		
    		if(dataoriginal != null)
    		{
    		    if (constraint == null || constraint.length() == 0) 
    		    {
    		    	Logg.info("Actividades : " + dataoriginal.size());

    		    	lsNewList.clear();
    		        for(int x = 0; x < dataoriginal.size(); x++)
    		        {
    		        	lsNewList.add(dataoriginal.get(x));
    		        }
    		        results.values = lsNewList;
    		        results.count = lsNewList.size();
    		    }
    		    else
    		    {
    		    	if(constraint.toString().equals(""))
    		    	{
    		    		Logg.info("Actividades : " + dataoriginal.size());
    		    		
        		    	lsNewList.clear();
        		        for(int x = 0; x < dataoriginal.size(); x++)
        		        {
        		        	lsNewList.add(dataoriginal.get(x));
        		        }
        		        
        		        results.values = lsNewList;
        		        results.count = lsNewList.size();
    		    	}
    		    	else
    		    	{
    		    		lsNewList.clear();
    		    		
    		    		//if tipo == 0 entonces es inventario, 1 es por notas de venta, y 2 por precios publico
    		    		//0 - BOAlmacenMovil
    		    		//1 - BONota
    		    		//2 - BOProducto
    		    		
    		    		if(Tipo == 0)
    		    		{
    		    			BOAlmacenMovil actividad = null;
            		        
            		        Logg.info("Producto : " + dataoriginal.size());
            		        
            		        for(int x = 0; x < dataoriginal.size(); x++)
            		        {
            		        	actividad = (BOAlmacenMovil)dataoriginal.get(x);
            		        	
            		        	if ((actividad.Producto.toUpperCase().indexOf(constraint.toString().toUpperCase()) > -1) ||
            		        			(actividad.Codigo.toUpperCase().indexOf(constraint.toString().toUpperCase()) > -1))
            		        	{
            		        		Logg.info("Agrega producto " + actividad.Producto + "-" + actividad.Codigo);
            		        		
            			        	lsNewList.add(actividad);
            		        	}
            		        }
    		    		}
    		    		else if(Tipo == 1)
    		    		{
    		    			BONota actividad = null;
            		        
            		        Logg.info("Nota : " + dataoriginal.size());
            		        
            		        for(int x = 0; x < dataoriginal.size(); x++)
            		        {
            		        	actividad = (BONota)dataoriginal.get(x);
            		        	
            		        	if ( (actividad.Cliente.toUpperCase().indexOf(constraint.toString().toUpperCase()) > -1) ||
            		        			(actividad.Codigo.toUpperCase().indexOf(constraint.toString().toUpperCase()) > -1) ||
            		        			(actividad.TipoOperacion.toUpperCase().indexOf(constraint.toString().toUpperCase()) > -1) ||
            		        			(actividad.DiscriminantePago.toUpperCase().indexOf(constraint.toString().toUpperCase()) > -1) ||
            		        			(actividad.Folio.toUpperCase().indexOf(constraint.toString().toUpperCase()) > -1) )
            		        	{
            		        		Logg.info("Agrega nota " + actividad.Cliente + "-" + actividad.Codigo);
            		        		
            			        	lsNewList.add(actividad);
            		        	}
            		        }
    		    		}
    		    		else
    		    		{
    		    			BOProducto actividad = null;
            		        
            		        Logg.info("Precio producto : " + dataoriginal.size());
            		        
            		        for(int x = 0; x < dataoriginal.size(); x++)
            		        {
            		        	actividad = (BOProducto)dataoriginal.get(x);
            		        	
            		        	if ((actividad.Descripcion.toUpperCase().indexOf(constraint.toString().toUpperCase()) > -1) ||
            		        			(actividad.Codigo.toUpperCase().indexOf(constraint.toString().toUpperCase()) > -1))
            		        	{
            		        		Logg.info("Agrega precio producto " + actividad.Descripcion + "-" + actividad.Codigo);
            		        		
            			        	lsNewList.add(actividad);
            		        	}
            		        }
    		    		}
    		    		
        		        results.values = lsNewList;
        		        results.count = lsNewList.size();
    		    	}
    		    }
    		}
    		else
    		{
    			Logg.info("Lista vacia");
    			
    			lsNewList.clear();
    	        
    	        results.values = lsNewList;
    	        results.count = lsNewList.size();
    		}
    	    
    	    return results;

        }
     
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
        	int contador = results.count;
        	
            if (results.count == 0)
            {
            	cambio = false;
                notifyDataSetChanged();
            	Logg.info("data agenda notifyDataSetInvalidated()");
            }
            else
            {
            	cambio = true;
            	data.clear();
            	
            	lsNewList = (List<BO>)results.values;
            	
                for(int x = 0; x < lsNewList.size(); x++)
                {
                	try
                	{
                    	data.add(lsNewList.get(x));
                	}
                	catch(Exception ex)
                	{
                		Logg.info("Error al filtrar: " + ex.getMessage());
                	}
                }
            	
                notifyDataSetChanged();
                Logg.info("data agenda items: " + data.size());
            }
        }
    }
    
}
