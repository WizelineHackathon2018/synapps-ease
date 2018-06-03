package com.comynt.launa.adapt;

import BO.*;

import com.comynt.launa.Repositorio;
import com.comynt.launa.frm.frmActividades;
import com.comynt.launa.R;

import com.System.UI.kButton;
import com.System.UI.kEditText;
import com.System.Utils.Logg;
import com.System.Utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class adapProducto extends BaseAdapter implements Filterable
{
	public List<BO> dataoriginal = new ArrayList<BO>();
    public List<BO> data = new ArrayList<BO>();
    
    public boolean Recarga = false;
    
    //public Hashtable datosListaHash = new Hashtable();
    
    public ArrayList<HashMap<String, String>> datosListaHash = new ArrayList<HashMap<String, String>>();
    
    private Activity activity;
    private LayoutInflater inflater = null;
    private View vi;
    private Filter myFilter;
    private boolean cambio = true;

    public adapProducto(Activity a, List<BO> d)
    {
        activity = a;
        
//        for(int x = 0; x < d.size(); x++)
//        {
//        	data.add(d.get(x));
//        	dataoriginal.add(d.get(x));
//        }
        
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
 
    public int getCount() {
        return data.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
    
    public void reload()
    {
    	data.clear();
        for(int x = 0; x < dataoriginal.size(); x++)
        {
        	data.add(dataoriginal.get(x));
        }
        notifyDataSetInvalidated();
    }
    
    public void addBO(BO item)
    {
    	dataoriginal.add(item);
    	data.clear();
        for(int x = 0; x < dataoriginal.size(); x++)
        {
        	data.add(dataoriginal.get(x));
        }
        notifyDataSetInvalidated();
    }
    
    public void editBO(BO item, int index)
    {
    	dataoriginal.set(index, item);
    	data.clear();
        for(int x = 0; x < dataoriginal.size(); x++)
        {
        	data.add(dataoriginal.get(x));
        }
        notifyDataSetInvalidated();
    }
 
    public View getView(int position, View convertView, ViewGroup parent)
    {
//    	BOProducto Producto = null;
//    	Producto = (BOProducto)data.get(position);
    	
    	BOAlmacenMovil AlmacenMovil = null;
    	AlmacenMovil = (BOAlmacenMovil)data.get(position);
    	
    	//String fechaDia = Utils.getFechaActual_DD_MM_YYYY();
    	
        if(cambio)
        {
            vi = convertView;

//            if(convertView == null)
//            {

            	vi = inflater.inflate(R.layout.itemlsproductos, null);

            	kEditText txtCantidad =(kEditText)vi.findViewById(R.id.txtItemCantidad);
                
                txtCantidad.Val = String.valueOf(AlmacenMovil.IdAlmacenMovil);
                
        		Logg.info("GENERAR ITEM IDPRODUCTO " + AlmacenMovil.IdProducto);

                //Si ya existe, le ponemos la cantidad anterior
        		for(int x = 0; x < datosListaHash.size(); x++)
                {
        			HashMap<String,String> item = new HashMap<String,String>();
        			item = datosListaHash.get(x);
        			
        			if( item.containsKey(txtCantidad.Val) )
        			{
                    	try
                    	{
                            txtCantidad.setText( item.get(txtCantidad.Val) );
                            Recarga = true;
                            break;
                    	}
                    	catch(Exception ex){}
        			}
                }
                
            	TextView txtItemDescripcionProducto =(TextView)vi.findViewById(R.id.txtItemDescripcionProducto);
            	txtItemDescripcionProducto.setText(AlmacenMovil.Codigo.toUpperCase() + 
            			" | $" + Utils.toMoneyFormat(AlmacenMovil.PrecioPublico) + 
            			" | " + AlmacenMovil.Producto.toUpperCase() );

            	kButton btnAgregarProducto = (kButton) vi.findViewById(R.id.btnAgregarProducto);
        		btnAgregarProducto.Name = String.valueOf(position);
        		btnAgregarProducto.Val = "+";

            	kButton btnEliminarProducto = (kButton) vi.findViewById(R.id.btnEliminarProducto);
            	btnEliminarProducto.Name = String.valueOf(position);
            	btnEliminarProducto.Val = "-";

//            }

        }

        return vi;
    }
    
    @Override
    public Filter getFilter()
    {
        if (myFilter == null)
            myFilter = new filtAgenda();
        
        return myFilter;
    }

    private class filtAgenda extends Filter
    {
    	List<BO> lsNewList = new ArrayList<BO>();
    	
    	@Override
        protected FilterResults performFiltering(CharSequence constraint) 
    	{
    		FilterResults results = new FilterResults();

    		Logg.info("Productos : " + dataoriginal.size());
    		
    		if(dataoriginal != null)
    		{
    		    if (constraint == null || constraint.length() == 0) 
    		    {
    		    	Logg.info("Productos : " + dataoriginal.size());

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
    		    		Logg.info("Productos : " + dataoriginal.size());
    		    		
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
    		    		BOAlmacenMovil Producto = null;
        		        
        		        Logg.info("Productos : " + dataoriginal.size());
        		        
        		        for(int x = 0; x < dataoriginal.size(); x++)
        		        {
        		        	Producto = (BOAlmacenMovil)dataoriginal.get(x);
        		        	
        		        	if ((Producto.Codigo.toUpperCase().indexOf(constraint.toString().toUpperCase()) > -1) ||
        		        			(Producto.Codigo.toUpperCase().indexOf(constraint.toString().toUpperCase()) > -1))
        		        	{
        		        		Logg.info("Agrega producto" + Producto.Codigo + "-" + Producto.Producto);
        		        		
        			        	lsNewList.add(Producto);
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
        protected void publishResults(CharSequence constraint,FilterResults results)
        {
            if (results.count == 0)
            {
            	cambio = false;
                notifyDataSetChanged();
            	Logg.info("data producto notifyDataSetInvalidated()");
            }
            else
            {
            	cambio = true;
            	data.clear();
            	
            	lsNewList = (List<BO>)results.values;
            	
                for(int x = 0; x < lsNewList.size(); x++)
                {
                	data.add(lsNewList.get(x));
                }
            	
                notifyDataSetChanged();
                Logg.info("data producto items: " + data.size());
            }
        }
    }
    
}
