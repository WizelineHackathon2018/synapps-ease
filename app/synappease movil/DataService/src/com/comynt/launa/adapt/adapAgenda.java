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

public class adapAgenda extends BaseAdapter implements Filterable
{
	public List<BO> dataoriginal = new ArrayList<BO>();
    public List<BO> data = new ArrayList<BO>();
    
    private Activity activity;
    private LayoutInflater inflater = null;
    private View vi;
    private Filter myFilter;
    private boolean cambio = true;

    public adapAgenda(Activity a, List<BO> d)
    {
        activity = a;
        
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
    	data.set(index, item);
    	notifyDataSetInvalidated();
    }
    
    public View getView(int position, View convertView, ViewGroup parent)
    {
    	BOItinerario itinerario = null;
    	itinerario = (BOItinerario)data.get(position);
    	String fechaDia = Utils.getFechaActual_DD_MM_YYYY();

        if(cambio)
        {

    		Logg.info("GENERAR ITEM ACTIVIDAD" + position);

        	vi = inflater.inflate(R.layout.itemlsactividades, null);

        	TextView lblfechaagenda =(TextView)vi.findViewById(R.id.txtItemCliente);
        	lblfechaagenda.setText(itinerario.Codigo + " | " + itinerario.Cliente);

//        	if(itinerario.RequiereAutorizacion == 1)
//        	{
//            	TextView txtItemHora =(TextView)vi.findViewById(R.id.txtItemDireccion);
//            	txtItemHora.setText("Credito=SI | " + itinerario.Direccion);
//        	}
//        	else
//        	{
//            	TextView txtItemHora =(TextView)vi.findViewById(R.id.txtItemDireccion);
//            	txtItemHora.setText("Credito=NO | " + itinerario.Direccion);
//        	}

    		Button btnEdit = (Button) vi.findViewById(R.id.btnEditar);
    		btnEdit.setClickable(false);

        	if (itinerario.Visita.equals(fechaDia))
        	{
        		btnEdit.setBackgroundResource(R.drawable.actividad_entiempo);
        	}
        	else
        	{
        		btnEdit.setBackgroundResource(R.drawable.actividad_atrazada);
        	}

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
    		    		BOItinerario actividad = null;
        		        
        		        Logg.info("Actividades : " + dataoriginal.size());
        		        
        		        for(int x = 0; x < dataoriginal.size(); x++)
        		        {
        		        	actividad = (BOItinerario)dataoriginal.get(x);
        		        	
        		        	if ((actividad.Cliente.toUpperCase().indexOf(constraint.toString().toUpperCase()) > -1) ||
        		        			(actividad.Codigo.toUpperCase().indexOf(constraint.toString().toUpperCase()) > -1))
        		        	{
        		        		Logg.info("Agrega actividad" + actividad.Cliente + "-" + actividad.Direccion);
        		        		
        			        	lsNewList.add(actividad);
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
