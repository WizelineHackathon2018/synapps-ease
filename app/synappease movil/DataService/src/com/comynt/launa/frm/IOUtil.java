package com.comynt.launa.frm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import com.System.Utils.Logg;

public class IOUtil 
{
    public static byte[] readFile(File file) throws IOException 
    {
        return getFileBytes(file);
    }
    
    public static byte[] getFileBytes(File file) throws IOException 
    {
        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        try 
        {
            byte[] buffer = new byte[4096];
            ous = new ByteArrayOutputStream();
            ios = new FileInputStream(file);
            int read = 0;
            while ((read = ios.read(buffer)) != -1)
                ous.write(buffer, 0, read);
        } 
        finally 
        {
            try 
            {
                if (ous != null)
                    ous.close();
            } 
            catch (IOException e) 
            {
            	Logg.error("Error el cerrar ous.close(): " + Arrays.toString(e.getStackTrace()));
            }
            try 
            {
                if (ios != null)
                    ios.close();
            } 
            catch (IOException e) 
            {
            	Logg.error("Error el cerrar ios.close(): " + Arrays.toString(e.getStackTrace()));
            }
        }
        return ous.toByteArray();
    }    

//    public static byte[] readFile(File file) throws IOException 
//    {
//    	int read = 0;
//    	int offset = 0;
//    	int chunk_size = 1024;
//    	int total_size = 0;
//
//    	ArrayList<byte[]> chunks = new ArrayList<byte[]>();
//    	chunks.add(new byte[chunk_size]);
//    	
//    	//first I read data from file chunk by chunk
//    	while ( (read = filein.read(chunks.get(chunks.size()-1), offset, buffer_size)) != -1) {
//    	    total_size+=read;
//    	    if (read == buffer_size) {
//    	         chunks.add(new byte[buffer_size]);
//    	    }
//    	}
//    	int index = 0;
//
//    	// then I create big buffer        
//    	byte[] rawdata = new byte[total_size];
//
//    	// then I copy data from every chunk in this buffer
//    	for (byte [] chunk: chunks) {
//    	    for (byte bt : chunk) {
//    	         index += 0;
//    	         rawdata[index] = bt;
//    	         if (index >= total_size) break;
//    	    }
//    	    if (index>= total_size) break;
//    	}
//
//    	// and clear chunks array
//    	chunks.clear();
//    }
}