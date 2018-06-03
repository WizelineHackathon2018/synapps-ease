package com.comynt.launa.adapt;

import java.util.ArrayList;
import java.util.HashMap;

import com.comynt.launa.frm.*;
import com.comynt.launa.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class adapMenu extends BaseAdapter
{
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;
    private View vi;

    public adapMenu(Activity a, ArrayList<HashMap<String, String>> d)
    {
        activity = a;
        data=d;
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
 
    public View getView(int position, View convertView, ViewGroup parent)
    {
        vi = convertView;
        ViewHolder holder;

        if(vi == null)
        {
        	vi = inflater.inflate(R.layout.itemlsmenu, null);

        	holder = new ViewHolder();
        	holder.lblOpcionPrincipal = (TextView)vi.findViewById(R.id.lblOpcionPrincipal);
        	holder.thumb_image = (ImageView)vi.findViewById(R.id.img_itm_principal);

        	vi.setTag(holder);
        }
        else
        {
        	holder = (ViewHolder)vi.getTag();
        }

        HashMap<String,String> song = new HashMap<String,String>();
        song = data.get(position);

        holder.lblOpcionPrincipal.setText(song.get(frmMenu.KEY_OPCION));

        String valor = song.get(frmMenu.KEY_OPCION);

        if(valor.equals("1) Sincronización de inicio"))
        {
        	holder.thumb_image.setImageResource(R.drawable.iconlarge9);
        }
        else if(valor.equals("2) CheckList vehiculo"))
        {
        	holder.thumb_image.setImageResource(R.drawable.iconlarge0);
        }
        else if(valor.equals("3) Realizar Venta con almacen"))
        {
        	holder.thumb_image.setImageResource(R.drawable.iconlarge1);
        }
        else if(valor.equals("4) Reimprimir"))
        {
        	holder.thumb_image.setImageResource(R.drawable.iconlarge2);
        }
        else if(valor.equals("5) Ver e Imprimir inventario"))
        {
        	holder.thumb_image.setImageResource(R.drawable.iconlarge3);
        }
        else if(valor.equals("6) Ver e Imprimir liquidación"))
        {
        	holder.thumb_image.setImageResource(R.drawable.iconlarge4);
        }
        else if(valor.equals("7) Sincronización de fin"))
        {
        	holder.thumb_image.setImageResource(R.drawable.iconlarge9);
        }

        return vi;
    }
    
    static class ViewHolder
    {
    	TextView lblOpcionPrincipal;
    	ImageView thumb_image;
    }
    
}
