package com.System.Images;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;

import com.System.Dispositivo.Info;
import com.System.Images.ScalingUtilities.ScalingLogic;
import com.System.Utils.Logg;
import com.System.Utils.Utils;
import com.comynt.launa.Repositorio;

import BO.BOFoto;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class imageManager 
{
	public static void saveImageBO(String nombrefile, String rutafile, Repositorio repositorio) 
	{
		BOFoto Foto = new BOFoto(repositorio.context);
		Foto.IdVendedor = repositorio.Vendedor.IdVendedor;
		
		if (repositorio.Tipo.equals("NUEVOCLIENTE"))
		{
			Foto.IdVehiculo = 0;
			Foto.Tipo = "CLIENTE";
			Foto.GUIDM = repositorio.NuevoCliente.GUIDV;
			Logg.info("repositorio.Tipo ( NUEVOCLIENTE )");
		}
		else if (repositorio.Tipo.equals("CLIENTE"))
		{
			Foto.IdVehiculo = repositorio.Itinerario.IdCliente;
			Foto.Tipo = "CLIENTE";
			Logg.info("repositorio.Tipo ( CLIENTE )");
		}
		if (repositorio.Tipo.equals("NOCOMPRO"))
		{
			Foto.IdVehiculo = 0;
			Foto.Tipo = "CLIENTE";
			Foto.GUIDM = repositorio.NuevoCliente.GUIDV;
			Logg.info("repositorio.Tipo ( NOCOMPRO )");
		}
		else
		{
			Foto.IdVehiculo = repositorio.Vehiculo.IdVehiculo;
			Foto.Tipo = "VEHICULO";
			Logg.info("repositorio.Tipo ( VEHICULO )");
		}
 
		Foto.PIN = Info.getPIN(repositorio.context);
		Foto.Nombre = nombrefile;
		Foto.Ruta = rutafile;
		Foto.Fecha = Utils.getFechaActual_DD_MM_YYYY();
		Foto.FechaHora = Utils.getFechaHoraActual();
 
		Foto.TipoProceso = "SQL";
		Foto.AgregarProceso("SQL.Agregar");
		Foto.EjecutarProceso();
	}
	
	public static void saveImageBO(String nombrefile, String rutafile, String typePhoto, Repositorio repositorio) 
	{
		BOFoto Foto = new BOFoto(repositorio.context);
		Foto.IdVendedor = repositorio.Vendedor.IdVendedor;
		Foto.IdVehiculo = repositorio.Itinerario.IdCliente;
		
		if (typePhoto.equals("NUEVOCLIENTE"))
		{
			Foto.IdVehiculo = 0;
			Foto.Tipo = "CLIENTE";
			Foto.GUIDM = repositorio.NuevoCliente.GUIDV;
			Logg.info("repositorio.Tipo ( NUEVOCLIENTE )");
		}
		if (typePhoto.equals("NUEVOCLIENTE"))
		{
			Foto.IdVehiculo = 0;
			Foto.Tipo = "CLIENTE";
			Foto.GUIDM = repositorio.NuevoCliente.GUIDV;
			Logg.info("repositorio.Tipo ( NUEVOCLIENTE )");
		}
		if (typePhoto.equals("NOCOMPRO"))
		{
			Foto.IdVehiculo = 0;
			Foto.Tipo = "CLIENTE";
			Foto.GUIDM = repositorio.NuevoCliente.GUIDV;
			Logg.info("repositorio.Tipo ( NOCOMPRO )");
		}
		else if (typePhoto.equals("CLIENTE"))
		{
			Foto.IdVehiculo = repositorio.Itinerario.IdCliente;
			Foto.Tipo = "CLIENTE";
			Logg.info("repositorio.Tipo ( CLIENTE )");
		}
		else
		{
			Foto.IdVehiculo = repositorio.Vehiculo.IdVehiculo;
			Foto.Tipo = "VEHICULO";
			Logg.info("repositorio.Tipo ( VEHICULO )");
		}
 
		Foto.PIN = Info.getPIN(repositorio.context);
		Foto.Nombre = nombrefile;
		Foto.Ruta = rutafile;
		Foto.Fecha = Utils.getFechaActual_DD_MM_YYYY();
		Foto.FechaHora = Utils.getFechaHoraActual();
 
		Foto.TipoProceso = "SQL";
		Foto.AgregarProceso("SQL.Agregar");
		Foto.EjecutarProceso();
	}
	
	public static String redim50Porcent(String path, String filename) 
	{
        String strMyImagePath = null;
        Bitmap scaledBitmap = null;
        
        int DESIREDWIDTH = 0;
        int DESIREDHEIGHT = 0;
        
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        //Returns null, sizes are in the options variable
        BitmapFactory.decodeFile(path, options);
        int width = options.outWidth;
        int height = options.outHeight;
        
        DESIREDWIDTH = Math.round(width/2);
        DESIREDHEIGHT = Math.round(height/2);
        
        Logg.info("width: " + width + " height: " + height + " DESIREDWIDTH: " + DESIREDWIDTH + " DESIREDHEIGHT: " + DESIREDHEIGHT);

        try 
        {
        	Logg.info("Part 1: Decode image");
            Bitmap unscaledBitmap = ScalingUtilities.decodeFile(path, width, height, ScalingLogic.FIT);

            Logg.info("unscaledBitmap.getWidth(): " + unscaledBitmap.getWidth() + " DESIREDWIDTH: " + DESIREDWIDTH + 
            		" && unscaledBitmap.getHeight(): " + unscaledBitmap.getHeight() + " DESIREDHEIGHT: " + DESIREDHEIGHT);
            
            if (!(unscaledBitmap.getWidth() <= DESIREDWIDTH && unscaledBitmap.getHeight() <= DESIREDHEIGHT)) 
            {
            	Logg.info("Part 2: Scale image");
                scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, DESIREDWIDTH, DESIREDHEIGHT, ScalingLogic.FIT);
                unscaledBitmap.recycle();
                System.gc();
            }
            else 
            {
            	Logg.info("unscaledBitmap.recycle();");
                unscaledBitmap.recycle();
                System.gc();
                return path;
            }

//            // Store to tmp file
//            String extr = Environment.getExternalStorageDirectory().toString();
//            
//            File mFolder = new File(extr + "/TMMFOLDER");
//            if (!mFolder.exists()) 
//            {
//                mFolder.mkdir();
//            }
//
//            String s = "tmp.png";
//
//            File f = new File(mFolder.getAbsolutePath(), s);
            
            File f = new File(Environment.getExternalStorageDirectory(), filename);

            strMyImagePath = f.getAbsolutePath();
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(f);
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos);
                fos.flush();
                fos.close();
            } 
            catch (FileNotFoundException e) 
            {
            	Logg.debug("No se encuentra el archivo: " + Arrays.toString(e.getStackTrace()) );
            } 
            catch (Exception e) 
            {
            	Logg.debug("Error al generar el archivo: " + Arrays.toString(e.getStackTrace()) );
            }

            scaledBitmap.recycle();
            System.gc();
        } 
        catch (Throwable e) 
        {
        	Logg.debug("No se encuentra el archivo: " + Arrays.toString(e.getStackTrace()) );
        }

        if (strMyImagePath == null) 
        {
        	Logg.debug("strMyImagePath == null ");
            return path;
        }
        
        Logg.info("Path: " + strMyImagePath);
        
        return strMyImagePath;
    }
	
	public static String redimXPorcent(String path, String filename, float Porcent, int quality) 
	{
        String strMyImagePath = null;
        Bitmap scaledBitmap = null;
        
        int DESIREDWIDTH = 0;
        int DESIREDHEIGHT = 0;
        
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        //Returns null, sizes are in the options variable
        BitmapFactory.decodeFile(path, options);
        int width = options.outWidth;
        int height = options.outHeight;
        
        DESIREDWIDTH = Math.round( width * (Porcent/100) );
        DESIREDHEIGHT = Math.round( height * (Porcent/100) );

        Logg.info("width: " + width + " height: " + height + " DESIREDWIDTH: " + DESIREDWIDTH + " DESIREDHEIGHT: " + DESIREDHEIGHT);

        try
        {
        	Logg.info("Part 1: Decode image");
            Bitmap unscaledBitmap = ScalingUtilities.decodeFile(path, width, height, ScalingLogic.FIT);

            Logg.info("unscaledBitmap.getWidth(): " + unscaledBitmap.getWidth() + " DESIREDWIDTH: " + DESIREDWIDTH + 
            		" && unscaledBitmap.getHeight(): " + unscaledBitmap.getHeight() + " DESIREDHEIGHT: " + DESIREDHEIGHT);
            
            if (!(unscaledBitmap.getWidth() <= DESIREDWIDTH && unscaledBitmap.getHeight() <= DESIREDHEIGHT)) 
            {
            	Logg.info("Part 2: Scale image");
                scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, DESIREDWIDTH, DESIREDHEIGHT, ScalingLogic.FIT);
                unscaledBitmap.recycle();
                //System.gc();
            }
            else 
            {
            	Logg.info("unscaledBitmap.recycle();");
                unscaledBitmap.recycle();
                //System.gc();
                return path;
            }

//            // Store to tmp file
//            String extr = Environment.getExternalStorageDirectory().toString();
//            
//            File mFolder = new File(extr + "/TMMFOLDER");
//            if (!mFolder.exists()) 
//            {
//                mFolder.mkdir();
//            }
//
//            String s = "tmp.jpg";

            //File f = new File(mFolder.getAbsolutePath(), filename);
            
            File f = new File(Environment.getExternalStorageDirectory(), filename);

            strMyImagePath = f.getAbsolutePath();
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(f);
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos);
                fos.flush();
                fos.close();
            } 
            catch (FileNotFoundException e) 
            {
            	Logg.debug("No se encuentra el archivo: " + Arrays.toString(e.getStackTrace()) );
            } 
            catch (Exception e) 
            {
            	Logg.debug("Error al generar el archivo: " + Arrays.toString(e.getStackTrace()) );
            }

            scaledBitmap.recycle();
            //System.gc();
        } 
        catch (Throwable e) 
        {
        	Logg.debug("No se encuentra el archivo: " + Arrays.toString(e.getStackTrace()) );
        }

        if (strMyImagePath == null) 
        {
        	Logg.debug("strMyImagePath == null ");
            return path;
        }
        
        Logg.info("Path: " + strMyImagePath);
        
        return strMyImagePath;
    }
	
}
