package com.System.UI;

import com.comynt.launa.R;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class kToast extends Toast 
{
	public kToast(Context context) {
		super(context);
	}

	public static void showMessage(Context context, String texto, int Duracion, Activity activity)
	{
        Context contextg =  context.getApplicationContext();
        LayoutInflater inflater = activity.getLayoutInflater();
        
        // Call toast.xml file for toast layout 
        View toastRoot = inflater.inflate(R.layout.itemtoast, null);
        
        TextView txt = (TextView) toastRoot.findViewById(R.id.txtMensaje);
        txt.setText(texto);
          
        Toast toast = new Toast(contextg);
         
        // Set layout to toast 
        toast.setView(toastRoot);
      //toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        
        toast.setDuration(Duracion);
        toast.show();
	}
	
	public static void showMessage(Context context, String texto, int Duracion, Fragment activity)
	{
        Context contextg =  context.getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) activity.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        // Call toast.xml file for toast layout 
        View toastRoot = inflater.inflate(R.layout.itemtoast, null);
        
        TextView txt = (TextView) toastRoot.findViewById(R.id.txtMensaje);
        txt.setText(texto);
          
        Toast toast = new Toast(contextg);
         
        // Set layout to toast 
        toast.setView(toastRoot);
        //toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        
        toast.setDuration(Duracion);
        toast.show();
	}
}
