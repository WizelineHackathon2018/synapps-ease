package com.System.Printer;

import com.zebra.android.comm.BluetoothPrinterConnection;
import com.zebra.android.comm.TcpPrinterConnection;
import com.zebra.android.comm.ZebraPrinterConnection;
import com.zebra.android.comm.ZebraPrinterConnectionException;

import com.zebra.android.printer.PrinterLanguage;
import com.zebra.android.printer.ZebraPrinter;
import com.zebra.android.printer.ZebraPrinterFactory;
import com.zebra.android.printer.ZebraPrinterLanguageUnknownException;

/*    */ public class ZebraBlueTooth
/*    */ {
/*    */   private static ZebraPrinterConnection zebraPrinterConnection;
/*    */ 
/*    */   public static ZebraPrinter connect(String IDPrinter)
/*    */   {
/* 17 */     pairBT(IDPrinter);

/* 18 */     ZebraPrinter printer = null;

/*    */     PrinterLanguage pl;
/*    */ 
/* 20 */     if (zebraPrinterConnection.isConnected()) {
/*    */       try
/*    */       {
/* 23 */         printer = ZebraPrinterFactory.getInstance(zebraPrinterConnection);
/* 24 */         pl = printer.getPrinterControlLanguage();
/*    */       }
/*    */       catch (ZebraPrinterConnectionException e)
/*    */       {
/* 26 */         printer = null;
/* 27 */         disconnect();
/*    */       } catch (ZebraPrinterLanguageUnknownException e) {
/* 29 */         printer = null;
/* 30 */         disconnect();
/*    */       }
/*    */     }
/*    */ 
/* 34 */     return printer;
/*    */   }
/*    */ 
/*    */   public static ZebraPrinterConnection getConnection() {
/* 38 */     return zebraPrinterConnection;
/*    */   }
/*    */ 
/*    */   public static void disconnect()
/*    */   {
/*    */     try {
/* 44 */       if (zebraPrinterConnection != null)
/* 45 */         zebraPrinterConnection.close();
/*    */     }
/*    */     catch (ZebraPrinterConnectionException localZebraPrinterConnectionException) {
/*    */     }
/* 49 */     zebraPrinterConnection = null;
/*    */   }
/*    */ 
/*    */   private static void pairBT(String IDPrinter)
/*    */   {
	
/*    */     try {
/* 55 */       if ((zebraPrinterConnection != null) && (zebraPrinterConnection.isConnected())) {
/* 56 */         return;
/*    */       }
/* 58 */       zebraPrinterConnection = new BluetoothPrinterConnection(IDPrinter);
/* 59 */       zebraPrinterConnection.open();
/*    */     } catch (ZebraPrinterConnectionException e) {
/* 61 */       disconnect();
/*    */     }
/*    */   }
/*    */ }
