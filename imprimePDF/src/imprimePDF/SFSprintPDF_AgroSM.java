package imprimePDF;



import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BarcodePDF417;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;



public class SFSprintPDF_AgroSM {
	
	
	//private static PdfWriter writer;


	
	public static final String FONT = ".\\resources\\fonts\\Consolas.ttf";
	
	public static final String FONT_AN = ".\\resources\\fonts\\arial-narrow.ttf";
	public static final String FONT_BOLD = ".\\resources\\fonts\\FrankfurtGothic-Bold.ttf";
	
	// private static String FILE = "c:/temp/FirstPdf.pdf";
	
	public static void imp_factura(String _file_xml, factura_cabecera Cabecera, factura_detalle[] Detalle, int _lineas_de_la_factura, String _file_pdf, String _file_jpg) throws DocumentException, IOException {
		//String reportePDF = ".\\data\\20525719953\\05_pdfs\\xxx.pdf"; 
		
		
		String reportePDF = _file_pdf;
		 // 
		String formato_factura = _file_jpg;
		// .gif and .jpg are ok too!
		
	
        
		 	Document document = new Document();
	        // step 2
	       
	        
	       // Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(reportePDF));
          //  PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(reportePDF));
            
            PdfWriter writer =
    	            PdfWriter.getInstance(document, new FileOutputStream(reportePDF));
            
	        // step 3
	        document.open();
	        
	        BaseFont bf = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	        BaseFont bf_bold = BaseFont.createFont(FONT_BOLD,  BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	        
	        
	        
	        Font console = new Font(bf, 9);
	        
	
			Image img = Image.getInstance(formato_factura);
			img.scalePercent(23);
			img.setAbsolutePosition(0, 70); // horizontal , vertical
			document.add(img);
	       
	        // step 4
	     
	
	        
	         
	        // ruc  emisor
	        PdfContentByte canvas = writer.getDirectContent(); //  getDirectContentUnder();
	        writer.setCompressionLevel(0);
	        
	        
	        canvas.saveState();                               // q
	        canvas.beginText();                               // BT
	        canvas.moveText(460, 762);                         // 36 788 Td
	        //canvas.setFontAndSize(BaseFont.createFont(), 11); // /F1 12 Tf
	        canvas.setFontAndSize(bf, 12);
	        //canvas.showText(Cabecera.get_ruc_emisor());	        // (Hello World)Tj
	        canvas.showText("20534068582");	        // (Hello World)Tj
	        canvas.endText();                                 // ET
	        canvas.restoreState();                            // Q
	 
	        // serie
	        canvas.saveState();                               // q
	        canvas.beginText();                               // BT
	        canvas.moveText(472, 720);                         // 36 788 Td
	        canvas.setFontAndSize(bf, 11); // /F1 12 Tf
	        canvas.showText(Cabecera.get_serie());	        // (Hello World)Tj
	        canvas.endText();                                 // ET
	        canvas.restoreState();                            // Q
	 
	        // NOMBRE DEL DOCUMENTO
	        canvas.saveState();                               // q
	        canvas.beginText();                               // BT
	        canvas.moveText(400, 745);                         // 36 788 Td
	        canvas.setFontAndSize(bf, 14); // /F1 12 Tf
	        canvas.showText(Cabecera.get_tipo_doc_descripcion());	        // (Hello World)Tj
	        canvas.endText();                                 // ET
	        canvas.restoreState();                            // Q
	  
	        				
	        
	        // folio
	        canvas.saveState();                               // q
	        canvas.beginText();                               // BT
	        canvas.moveText(472, 703);                         // 36 788 Td
	        canvas.setFontAndSize(bf, 11); // /F1 12 Tf
	        canvas.showText(Cabecera.get_folio());	        // (Hello World)Tj
	        canvas.endText();                                 // ET
	        canvas.restoreState();                            // Q
	        
	        
	        
	        
	       
	        
	        // RAZON SOCIAL  del receptor
	        canvas.saveState();                               // q
	        canvas.beginText();                               // BT
	        canvas.moveText(60, 645);                         // 36 788 Td
	        canvas.setFontAndSize(bf, 10); // /F1 12 Tf
	        canvas.showText("          "+Formato.padRight(Cabecera.get_razon_social_receptor(),50));	        // (Hello World)Tj
	        canvas.endText();                                 // ET
	        canvas.restoreState();                            // Q
	      
	        String _dir01="";
	        String _dir02="";
	        
	        
	        if (Cabecera.get_direccion_receptor().length()>81) {
	        	int _tam=Cabecera.get_direccion_receptor().length();
	        	_dir01=Cabecera.get_direccion_receptor().substring(0, 81);
	        	_dir02=Cabecera.get_direccion_receptor().substring(81, _tam);
	        } else {
	        	_dir01=Cabecera.get_direccion_receptor();
	        	_dir02="";
	        	
	        }
	        
	        
	        
	        // RAZON SOCIAL  del receptor
	        canvas.saveState();                               // q
	        canvas.beginText();                               // BT
	        canvas.moveText(60, 625);                         // 36 788 Td
	        canvas.setFontAndSize(bf, 10); // /F1 12 Tf
	        canvas.showText("      "+_dir01);	        // (Hello World)Tj
	        canvas.endText();                                 // ET
	        canvas.restoreState();                            // Q
	      

	        canvas.saveState();                               // q
	        canvas.beginText();                               // BT
	        canvas.moveText(60, 607);                         // 36 788 Td
	        canvas.setFontAndSize(bf, 10); // /F1 12 Tf
	        canvas.showText("      "+_dir02.trim());	        // (Hello World)Tj
	        canvas.endText();                                 // ET
	        canvas.restoreState();                            // Q

	        
	        
	        // ruc del receptor
	        canvas.saveState();                               // q
	        canvas.beginText();                               // BT
	        canvas.moveText(60, 588);                         // 36 788 Td
	        canvas.setFontAndSize(bf_bold, 11); // /F1 12 Tf
	        if (Cabecera.get_ruc_receptor().length()>8) {
	        	canvas.showText(" RUC:  "+Cabecera.get_ruc_receptor());	        // (Hello World)Tj
	        } else {
	        	canvas.showText(" DNI:  "+Cabecera.get_ruc_receptor());
	        }
	        canvas.endText();                                 // ET
	        canvas.restoreState();                            // Q
	  
	        
	        
	        
	        // RAZON SOCIAL  del receptor
	        canvas.saveState();                               // q
	        canvas.beginText();                               // BT
	        canvas.moveText(90, 565);                         // 36 788 Td
	        canvas.setFontAndSize(bf, 10); // /F1 12 Tf
	        canvas.showText("        "+Formato.padRight(Cabecera.get_guia(),50));	        // (Hello World)Tj
	        canvas.endText();                                 // ET
	        canvas.restoreState();     
	        
	        // fecha de vencimiento
	        canvas.saveState();                               // q
	        canvas.beginText();                               // BT
	        canvas.moveText(345, 565);                         // 36 788 Td
	        canvas.setFontAndSize(bf, 10); // /F1 12 Tf
	        canvas.showText(Cabecera.get_fecha_vencimiento());	        // (Hello World)Tj
	        canvas.endText();                                 // ET
	        canvas.restoreState();     
	        
	        //dias vencimiento
	        canvas.saveState();                               // q
	        canvas.beginText();                               // BT
	        canvas.moveText(500, 565);                         // 36 788 Td
	        canvas.setFontAndSize(bf, 10); // /F1 12 Tf
	        canvas.showText(Cabecera.get_dias());	        // (Hello World)Tj
	        canvas.endText();                                 // ET
	        canvas.restoreState();     
	        
	        
	        
	        // fecha de emision del docto
	        canvas.saveState();                               // q
	        canvas.beginText();                               // BT
	        canvas.moveText(310, 588);                         // 36 788 Td
	        canvas.setFontAndSize(bf, 10); // /F1 12 Tf
	        canvas.showText(Cabecera.get_fecha());	        // (Hello World)Tj
	        canvas.endText();                                 // ET
	        canvas.restoreState();                            // Q


	        //moneda
	        canvas.saveState();                               // q
	        canvas.beginText();                               // BT
	        canvas.moveText(390, 588);                         // 36 788 Td
	        canvas.setFontAndSize(bf, 10); // /F1 12 Tf
	        
	        if (Cabecera.get_moneda().equals("PEN")) {
		        canvas.showText("             "+"Soles");	        // (Hello World)Tj
	        	
	        } else {
		        canvas.showText("             "+"Dolares");	        // (Hello World)Tj

	        }
	        //canvas.showText("Moneda:           "+Cabecera.get_moneda());	        // (Hello World)Tj
	//        canvas.showText("Moneda:           "+"Soles");	        // (Hello World)Tj
	        canvas.endText();                                 // ET
	        canvas.restoreState();                            // Q
        
   
	        
	        // cantidad en letra
	        canvas.saveState();                               // q
	        canvas.beginText();                               // BT
	        canvas.moveText(70, 307);                         // 36 788 Td
	        canvas.setFontAndSize(bf, 9); // /F1 12 Tf
	        if (Cabecera.get_moneda().equals("PEN")) {
	        	canvas.showText(Cabecera.get_total_letra()+" SOLES.");	        // (Hello World)Tj
		        	
	        } else {
	        	canvas.showText(Cabecera.get_total_letra()+" DOLARES.");	        // (Hello World)Tj
		        
	        }
	        
	    //    canvas.showText(Cabecera.get_total_letra()+" SOLES.");	        // (Hello World)Tj
	        canvas.endText();                                 // ET
	        canvas.restoreState();                            // Q
	       
	        
	     // TOTAL subtotal
	        canvas.saveState();                               // q
	        canvas.beginText();                               // BT
	        canvas.moveText(390, 280);                         // 36 788 Td
	        canvas.setFontAndSize(bf, 10); // /F1 12 Tf
	//        if (Cabecera.get_tipo_doc().substring(1).equals("1")) {
	        	
	        	 if (Cabecera.get_moneda().equals("PEN")) {
	        			canvas.showText("  Sub Total S/:    "+Formato.dinero(Cabecera.get_subtotal()));
	    					 
	        	 } else {
	        			canvas.showText("  Sub Total  $:    "+Formato.dinero(Cabecera.get_subtotal()));
	    				
	        	 }
	        	//	canvas.showText("  Sub Total S/:    "+Formato.dinero(Cabecera.get_subtotal()));
	//			}
	        // canvas.showText("  Sub Total S/:    "+Formato.dinero(Cabecera.get_subtotal()));
	        canvas.endText();                                 // ET
	        canvas.restoreState();                            // Q
	       
	        // TOTAL subtotal
	        canvas.saveState();                               // q
	        canvas.beginText();                               // BT
	        canvas.moveText(390, 260);                         // 36 788 Td
	        canvas.setFontAndSize(bf, 10); // /F1 12 Tf
//	        if (Cabecera.get_tipo_doc().substring(1).equals("1")) {
	        	canvas.showText("  IGV (18%):       "+Formato.dinero(Cabecera.get_total_igv()));
//	        }
	        canvas.endText();                                 // ET
	        canvas.restoreState();                            // Q  
	        
	        // TOTAL DE LA FACTURAS
	        canvas.saveState();                               // q
	        canvas.beginText();                               // BT
	        canvas.moveText(390, 240);                         // 36 788 Td
	        canvas.setFontAndSize(bf, 10); // /F1 12 Tf
	     // canvas.showText("Total:      "+Cabecera.get_total());	
	        if (Cabecera.get_moneda().equals("PEN")) {
	            canvas.showText("  TOTAL S/:        "+Formato.dinero(Cabecera.get_total()));
	       	 
	        } else {
	            canvas.showText("  TOTAL  $:        "+Formato.dinero(Cabecera.get_total()));
	       	 	
	        }
	      //  canvas.showText("  TOTAL S/:        "+Formato.dinero(Cabecera.get_total()));
	        canvas.endText();                                 // ET
	        canvas.restoreState();                            // Q
	       
	        
	        
	        
		     // resumen hash
	        canvas.saveState();                               // q
	        canvas.beginText();                               // BT
	        canvas.moveText(30, 190);                         // 36 788 Td
	        canvas.setFontAndSize(bf, 8); // /F1 12 Tf
	        canvas.showText(Cabecera.get_codigo_hash());	        // (Hello World)Tj
	        canvas.endText();                                 // ET
	        canvas.restoreState();                            // Q
	        
	  	 
	        
	        
			
			String _contenido_qr = Cabecera.get_ruc_emisor()+"|"+Cabecera.get_tipo_documento()+"|"+   
					Cabecera.get_serie()+"-"+Cabecera.get_folio()+"|"+
					Cabecera.get_total_igv()+"|"+Cabecera.get_total()+"|"+Cabecera.get_fecha_qr()+"|"+
					Cabecera.get_tipo_doc_adquiriente()+"|"+Cabecera.get_ruc_receptor()+"|";

			BarcodeQRCode barcodeQRCode = new BarcodeQRCode(_contenido_qr, 92, 92, null);
			Image codeQrImage = barcodeQRCode.getImage();
			codeQrImage.setAbsolutePosition(38, 202);
			document.add(codeQrImage);
 
	        
	  	     Paragraph _linea00 = new Paragraph();
	  	     Chunk _espacio = new Chunk(" ");
	  	     _linea00.add(_espacio);
	  	     
	  	     
	  	     for (int z = 1; z<=18; z++) {
	  	    	 document.add(_linea00);
	  	     }
	  	     
	  	     
	  	     Paragraph _linea01 = new Paragraph();
	  	     
	  	     for (int i=0; i<_lineas_de_la_factura; i++) {
	  	    	
	  	    	 
	  	   	if (!isNullOrEmpty(Detalle[i].get_descripcion())) {
				
	  	     
	  	    	 Chunk _producto = new Chunk(Formato.padRight(Detalle[i].get_codigo(),12));
	  	    	 Chunk _descripcion = new Chunk(Formato.padRight(Formato.cadena35(Detalle[i].get_descripcion()),43));
	  	    	 Chunk _unidad_de_medida = new Chunk(Formato.cadena5(Detalle[i].get_unidad()));
	  	    	 Chunk _cantidad = new Chunk(Formato.dinero(Detalle[i].get_cantidad()));
	  	    	 Chunk _signo_soles = new Chunk("s/");
	  	    	 Chunk _signo_dolares = new Chunk(" $");
	  	    	 
	  	    	 Chunk _precio = new Chunk(Formato.dinero6(Detalle[i].get_precio_unitario()));
	  	    	 Chunk _importe = new Chunk(Formato.dinero(Detalle[i].get_subtotal()));
	  	    	 Chunk _importe_sin_igv = new Chunk(Formato.dinero(Detalle[i].get_subtotal_sin_igv()));
	  	     
	  	    	 _espacio.setFont(console);
	  	    	 //  _lineas_de_la_factura
	  	     
	  	    	 _linea01.add(_espacio);
	  	     
	  	     
	  	    	 _producto.setFont(console);
	  	    	 _descripcion.setFont(console);
	  	    	 _unidad_de_medida.setFont(console);
	  	    	 _cantidad.setFont(console);
	  	    	 _precio.setFont(console);
	  	    	 _importe.setFont(console);
	  	    	 _importe_sin_igv.setFont(console);
	  	    	_signo_dolares.setFont(console);
	  	     
	  	     
	  	     
	  	     
	  	     
	  	    	 _linea01.add(_espacio);
	  	    	 _linea01.add(_espacio);
	  	    //	 _linea01.add(_espacio);
	  	    //	 _linea01.add(_espacio);
	  	    //	 _linea01.add(_espacio);
	  	    //	 _linea01.add(_espacio);
	  	    	  	_linea01.add(_unidad_de_medida);
	  	    	 _linea01.add(_espacio);
	  	    	 _linea01.add(_espacio);
	  	    	 _linea01.add(_espacio);
	  	    	 _linea01.add(_cantidad);
	  	    	 _linea01.add(_espacio);
	  	    	 _linea01.add(_espacio);
	  	     
	  	     
	  	    
	  	    
	  	     	     
	  	    	 _linea01.add(_descripcion);
	  	    	 _linea01.add(_espacio);
	  	    	 _linea01.add(_espacio);
	  	    	 _linea01.add(_espacio);
	  	    	 _linea01.add(_espacio);
	  	    	 _linea01.add(_espacio);
	  	    	 _linea01.add(_espacio);
	  	    	 _linea01.add(_espacio);
	  	   // 	 _linea01.add(_espacio);
	  	    	 _linea01.add(_espacio);
	  	    	 _linea01.add(_espacio);
	  	    	 _linea01.add(_espacio);
	  	    	 if (Cabecera.get_moneda().equals("PEN")) {
	  	    		 _linea01.add(_signo_soles);
	 	  	    		 
	  	    	 } else {
	  	    		 _linea01.add(_signo_dolares);
	 	  	    	 
	  	    	 }
	  	    //	 _linea01.add(_signo_soles);
	  	    	 _linea01.add(_precio);
	  	    	 _linea01.add(_espacio);
	  	    	 _linea01.add(_espacio);
	  	    	 _linea01.add(_espacio);
	  	    	 _linea01.add(_espacio);
	  	    	 _linea01.add(_espacio);
	  	    //	 _linea01.add(_espacio);
	  	    	 _linea01.add(_importe_sin_igv);
	  	     
	  	    
	  	    
	  	     
	  	     
	  	    	 document.add(_linea01);
	  	    	 _linea01.removeAll(_linea01);
	  	     
	  	     	}
	  	     }
	  	     	
	  	     
	  	       
	  	       
	  	       
	    
	        
	        // step 5
	        document.close();		
		
		
		
	}

	
	
	public static boolean isNullOrEmpty(String a) {
		return a == null || a.isEmpty();
		} 

	  

}
