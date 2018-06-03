package com.comynt.launa.adapt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.System.UI.kButton;
import com.System.Utils.Logg;
import com.System.Utils.Utils;
import com.comynt.launa.adapt.adapMenu.ViewHolder;
import com.comynt.launa.frm.frmMenu;
import com.comynt.launa.R;

import BO.*;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

public class adaptInventario extends BaseAdapter implements Filterable
{
	public List<BO> dataoriginal = new ArrayList<BO>();
    public List<BO> data = new ArrayList<BO>();
    
    private Fragment activity;
    private LayoutInflater inflater = null;
    private View vi;
    private Filter myFilter;
    private boolean cambio = true;

    public adaptInventario(Fragment a, List<BO> d)
    {
        activity = a;
        
        for(int x = 0; x < d.size(); x++)
        {
        	data.add(d.get(x));
        	dataoriginal.add(d.get(x));
        }
        
        inflater = (LayoutInflater)activity.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    	BOAlmacenMovil AlmacenMovil = null;
    	AlmacenMovil = (BOAlmacenMovil)data.get(position);

        vi = convertView;
        ViewHolder holder;
        
        if(vi == null)
        {
        	vi = inflater.inflate(R.layout.itemlsinventario, null);

        	holder = new ViewHolder();
        	
//        	holder.txtinvDescripcionView = (TextView)vi.findViewById(R.id.txtinvDescripcion);
//        	holder.txtinvInicialView = (TextView)vi.findViewById(R.id.txtinvInventarioInicial);
//        	holder.txtinvVentaView = (TextView)vi.findViewById(R.id.txtinvVentaInventario);
//        	holder.txtinvExistenciaView = (TextView)vi.findViewById(R.id.txtinvFinalInventario);

        	vi.setTag(holder);
        }
        else
        {
        	holder = (ViewHolder)vi.getTag();
        }

//        holder.txtinvDescripcionView.setText(String.valueOf(AlmacenMovil.Producto));
//        holder.txtinvInicialView.setText("I: " + String.valueOf(AlmacenMovil.CantidadInicial));
//        holder.txtinvVentaView.setText("V: " + String.valueOf(AlmacenMovil.Venta));
//        holder.txtinvExistenciaView.setText("F: " + String.valueOf( AlmacenMovil.CantidadInicial - AlmacenMovil.Venta ));
        
    	return vi;
    }
    
    static class ViewHolder
    {
    	TextView txtinvDescripcionView;
    	TextView txtinvInicialView;
    	TextView txtinvVentaView;
    	TextView txtinvExistenciaView;
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
    		    		BOAlmacenMovil AlmacenMovil = null;
        		        
        		        Logg.info("Actividades : " + dataoriginal.size());
        		        
        		        for(int x = 0; x < dataoriginal.size(); x++)
        		        {
        		        	AlmacenMovil = (BOAlmacenMovil)dataoriginal.get(x);
        		        	
        		        	if ((AlmacenMovil.Codigo.toUpperCase().indexOf(constraint.toString().toUpperCase()) > -1)) //||
        		        			//(AlmacenMovil.IdProducto.toUpperCase().indexOf(constraint.toString().toUpperCase()) > -1))
        		        	{
        		        		Logg.info("Agrega actividad" + AlmacenMovil.Codigo + "-" + AlmacenMovil.CantidadInicial);
        		        		
        			        	lsNewList.add(AlmacenMovil);
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
            	notifyDataSetInvalidated();
            	Logg.info("data agenda notifyDataSetInvalidated()");
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
                Logg.info("data agenda items: " + data.size());
            }
        }
    }
    
}
