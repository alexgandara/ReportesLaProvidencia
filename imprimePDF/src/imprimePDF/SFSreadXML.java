package imprimePDF;

import java.io.File;














import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BarcodeQRCode;

//import wsHomologador.detalle;



public class SFSreadXML {
	
		
	public static factura_cabecera Cabecera = new factura_cabecera();
	public static factura_detalle[] Detalle = new factura_detalle[100];
	public static int _lineas_de_la_factura=0;
	
	public static void main(String args[]) {
		
	//	String _file_xml="R:\\conector\\data\\20525378358\\03_xmls_con_firma\\20525378358-01-F001-0000001.xml";
		
		System.out.println("..20534068582........");
		
		String _file= args[0];
		String _correo_destino = "";
		String _correo_destino_cc = "";
		if (!isNullOrEmpty(args[1])) {
			_correo_destino = args[1];
			_correo_destino_cc = args[2];
		} else {
			_correo_destino= "nada";
			_correo_destino_cc= "nada";
		}
		
	//	String _file= "20525378358-01-F001-0000001";
		String _file_xml = ".\\data\\20534068582\\03_xmls_con_firma_pruebas\\"+_file+".xml";
		String _file_pdf = ".\\data\\20534068582\\05_pdfs_pruebas\\"+_file+".pdf";
		
		String _file_pdf_fn =".\\data\\20534068582\\05_pdfs\\Factura_Negociable-"+_file+".pdf";
		
		
		String _file_jpg="T:\\conecta.global\\data\\20534068582\\10_formatos\\CartaCompleta_Pruebas.jpg";
		
	
		
		
		File fXmlFile = new File(_file_xml);
		try {
			
			
			
			
			String raya="----------------------------------------------------------------";
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			
			doc.getDocumentElement().normalize();
			
	//		NodeList nList = doc.getElementsByTagName("Invoice");
			

			System.out.println("DATOS DEL DOCUMENTO");
			String _temp = _file;
			int _num = _temp.length();

			Cabecera.set_serie(_temp.substring(15,19));
			Cabecera.set_folio(_temp.substring(20,_num));

		
			
			System.out.println("Serie _ _ _ _ _ _ _ _ _ _ _ _ _: " + Cabecera.get_serie());
			System.out.println("Folio _ _ _ _ _ _ _ _ _ _ _ _ _: " + Cabecera.get_folio());
		
			System.out.println(raya);

			Cabecera.set_descripcion_documento(doc.getDocumentElement().getNodeName());	
			System.out.println("Documento _ _ _ _ _ _ _ _ _ _ _: " + Cabecera.get_descripcion_documento());


			NodeList nList_linea = doc.getElementsByTagName("cbc:InvoicedQuantity");
			NodeList nList_InvoicedQuantity = doc.getElementsByTagName("cbc:InvoicedQuantity");

			Cabecera.set_tipo_doc_descripcion("FACTURA");
			if (Cabecera.get_descripcion_documento().equals("Invoice")) {
				Cabecera.set_tipo_doc_descripcion("FACTURA ELECTRONICA");
				Cabecera.set_tipo_documento("01");
				Cabecera.set_Ruc_Dni("    ");

				// cbc:ID	cantidad


			} 

			if (Cabecera.get_descripcion_documento().equals("CreditNote")) {
				Cabecera.set_tipo_doc_descripcion("  NOTA DE CREDITO  ");
				Cabecera.set_tipo_documento("07");
				Cabecera.set_Ruc_Dni("    ");

				// SI ES NOTA DE CREDITO   cac:DiscrepancyResponse
				// cbc:ReferenceID

				NodeList nList_ReferenceID = doc.getElementsByTagName("cbc:ReferenceID");
				Node nNode_ReferenceID = nList_ReferenceID.item(0);
				Cabecera.set_doc_relacionado(nNode_ReferenceID.getTextContent());
				System.out.println("Documento Relacionado _ _ _ _ _: " + Cabecera.get_doc_relacionado());


				// cbc:Description
				NodeList nList_Description = doc.getElementsByTagName("cbc:Description");
				Node nNode_Description = nList_Description.item(0);
				Cabecera.set_motivo_de_anulacion(nNode_Description.getTextContent());
				System.out.println("Motivo de Anulacion_ _ _ _ _ _ _: " + Cabecera.get_motivo_de_anulacion());


				nList_linea = doc.getElementsByTagName("cbc:CreditedQuantity");
				nList_InvoicedQuantity = doc.getElementsByTagName("cbc:CreditedQuantity");



			} 


			if (Cabecera.get_descripcion_documento().equals("DebitNote")) {
				Cabecera.set_tipo_doc_descripcion("  NOTA DE DEBITO   ");
				Cabecera.set_tipo_documento("08");
				Cabecera.set_Ruc_Dni("    ");

				NodeList nList_ReferenceID = doc.getElementsByTagName("cbc:ReferenceID");
				Node nNode_ReferenceID = nList_ReferenceID.item(0);
				Cabecera.set_doc_relacionado(nNode_ReferenceID.getTextContent());
				System.out.println("Documento Relacionado _ _ _ _ _: " + Cabecera.get_doc_relacionado());


				// cbc:Description
				NodeList nList_Description = doc.getElementsByTagName("cbc:Description");
				Node nNode_Description = nList_Description.item(0);
				Cabecera.set_motivo_de_anulacion(nNode_Description.getTextContent());
				System.out.println("Motivo de Anulacion_ _ _ _ _ _ : " + Cabecera.get_motivo_de_anulacion());

				
				// cbc:ID	cantidad
				nList_linea = doc.getElementsByTagName("cbc:DebitedQuantity");
				nList_InvoicedQuantity = doc.getElementsByTagName("cbc:DebitedQuantity");	



			} 



			
			
		
			
			
			// cbc:IssueDate
			NodeList nList_IssueDate = doc.getElementsByTagName("cbc:IssueDate");
			Node nNode_IssueDate = nList_IssueDate.item(0);

			//Cabecera.set_fecha(nNode_IssueDate.getTextContent());

			String _fecha = nNode_IssueDate.getTextContent();

			String _Dia = "";
			String _Mes = "";
			String _Ano = "";
			_Dia = _fecha.substring(8, 10);  //2016.09.17  2016-11-30
			_Mes = _fecha.substring(5, 7);  //2016.09.17  0123456789
			_Ano = _fecha.substring(0, 4);             // 1234567890
			Cabecera.set_fecha( _Dia+"-"+_Mes+"-"+_Ano);
			System.out.println("Fecha del Docto _ _ _ _ _ _ _ _: " + Cabecera.get_fecha());	


			// cbc:DueDate

			NodeList nList_DueDate = doc.getElementsByTagName("cbc:DueDate");


			try {
				Node nNode_DueDate = nList_DueDate.item(0);
				String _fecha_vencimiento = nNode_DueDate.getTextContent();

				_Dia = "";
				_Mes = "";
				_Ano = "";
				_Dia = _fecha_vencimiento.substring(8, 10);  //2016.09.17  2016-11-30
				_Mes = _fecha_vencimiento.substring(5, 7);  //2016.09.17  0123456789
				_Ano = _fecha_vencimiento.substring(0, 4);             // 1234567890
				Cabecera.set_fecha_vencimiento( _Ano+"-"+_Mes+"-"+_Dia);
				System.out.println("Fecha del Venciminento _ _ _ _: " + Cabecera.get_fecha_vencimiento());	

			} catch (Exception e) {
				Cabecera.set_fecha_vencimiento( "    -  -  ");

			}

			
			
			
			// cbc:InvoiceTypeCode
			NodeList nList_InvoiceTypeCode = doc.getElementsByTagName("cbc:InvoiceTypeCode");
			try {
				Node nNode_InvoiceTypeCode = nList_InvoiceTypeCode.item(0);
				Cabecera.set_tipo_doc(nNode_InvoiceTypeCode.getTextContent());
				System.out.println("Tipo del Documento: _ _ _ _ _ _: " + Cabecera.get_tipo_doc());

				if (Cabecera.get_tipo_doc().substring(1).equals("3")) {
					Cabecera.set_tipo_doc_descripcion("BOLETA ELECTRONICA");
					Cabecera.set_tipo_documento("03");
					Cabecera.set_Ruc_Dni("    ");
				}

			} catch (Exception e) {

			}

			
			if (Cabecera.get_descripcion_documento().equals("CreditNote")) {
				Cabecera.set_tipo_doc("07");
				System.out.println("Tipo del Documento: _ _ _ _ _ _: " + Cabecera.get_tipo_doc());
			} 

			if (Cabecera.get_descripcion_documento().equals("DebitNote")) {
				Cabecera.set_tipo_doc("08");
				System.out.println("Tipo del Documento: _ _ _ _ _ _: " + Cabecera.get_tipo_doc());
			} 

			
			// cbc:InvoiceTypeCode
	//		NodeList nList_InvoiceTypeCode2 = doc.getElementsByTagName("cbc:InvoiceTypeCode");
			try {
				Node nNode_InvoiceTypeCode = nList_InvoiceTypeCode.item(0);
				Cabecera.set_tipo_doc(nNode_InvoiceTypeCode.getTextContent());
				System.out.println("Tipo del Documento: _ _ _ _ _ _: " + Cabecera.get_tipo_doc());

				if (Cabecera.get_tipo_doc().substring(1).equals("3")) {
					Cabecera.set_tipo_doc_descripcion("BOLETA ELECTRONICA");
					Cabecera.set_Ruc_Dni("    ");
				}

			} catch (Exception e) {

			}

			
			
			// cbc:ProfileID
			NodeList nList_ProfileID = doc.getElementsByTagName("cbc:ProfileID");

			try {	
				Node nNode_ProfileID = nList_ProfileID.item(0);
				Cabecera.set_profile(nNode_ProfileID.getTextContent());
			} catch (Exception e) {
				Cabecera.set_profile("0101");

			}


			String _profile=Cabecera.get_profile();

			Cabecera.set_tipo_operacion("-");


		

			Cabecera.set_tipo_operacion("-");


			if (_profile.equals("0101")) {
				Cabecera.set_tipo_operacion("Venta Interna");
			}



			//				if (_profile.equals("02")) {
			//					Cabecera.set_tipo_operacion("Expotación");
			//				}


			//				if (_id.equals("03")) {
			//					Cabecera.set_tipo_operacion("No Domicilado");
			//				}

			if (_profile.equals("0102")) {
				Cabecera.set_tipo_operacion("Anticipo");
			}

			//				if (_id.equals("05")) {
			//				Cabecera.set_tipo_operacion("Vta Itinerante");
			//				}

			//				if (_id.equals("06")) {
			//					Cabecera.set_tipo_operacion("Factura Guia");
			//				}



			
			NodeList nodeList_AccountingSupplierParty = doc.getElementsByTagName("cac:AccountingSupplierParty").item(0).getChildNodes();
			Node nNode_AccountingSupplierParty = nodeList_AccountingSupplierParty.item(0);
			String _RUC_EMISOR=nNode_AccountingSupplierParty.getTextContent().substring(0, 11);

			
			
			// cac:AdditionalDocumentReference
			NodeList nList_AdditionalDocumentReference = doc.getElementsByTagName("cac:AdditionalDocumentReference").item(0).getChildNodes();
			Node nNode_AdditionalDocumentReference = nList_AdditionalDocumentReference.item(1);
			Cabecera.set_guia(nNode_AdditionalDocumentReference.getTextContent());
			System.out.println("Guia_ _ _ _ _ _ _ _ _ _ _ _ _ _: " + Cabecera.get_guia());
			
			
			
			
			// cbc:DocumentCurrencyCode
			NodeList nList_DocumentCurrencyCode = doc.getElementsByTagName("cbc:DocumentCurrencyCode");
			Node nNode_DocumentCurrencyCode = nList_DocumentCurrencyCode.item(0);
			Cabecera.set_moneda(nNode_DocumentCurrencyCode.getTextContent());
			System.out.println("Tipo de Moneda_ _ _ _ _ _ _ _ _: " + Cabecera.get_moneda());

			
			

			// datos adicioneales que se neesitan par ael QR

			// tipo doc aquiriente
			NodeList nList_tipo_doc_ad = doc.getElementsByTagName("cbc:ID");
			Node nNode_tipo_doc = nList_tipo_doc_ad.item(4);
			String _ident = nNode_tipo_doc.getTextContent();

			// schemeID

			NamedNodeMap attr_tipo_doc_ad = nNode_tipo_doc.getAttributes();
			String _tipo_doc_ad="";
			if (null != attr_tipo_doc_ad) {
				Node p = attr_tipo_doc_ad.getNamedItem("schemeID");
				_tipo_doc_ad=p.getNodeValue();
			}



			if (_tipo_doc_ad.equals("1")) {
				Cabecera.set_tipo_doc_adquiriente("1");
			} 

			if (_tipo_doc_ad.equals("6")) {
				Cabecera.set_tipo_doc_adquiriente("6");
			} 


			Cabecera.set_tipo_doc_adquiriente(nNode_tipo_doc.getTextContent());
			Cabecera.set_fecha_qr(_fecha);
			Cabecera.set_tipo_documento(_file.substring(12,14));



			
			System.out.println(raya);

			
			
			
			NodeList nodeList_AccountingSupplierParty1 = doc.getElementsByTagName("cac:AccountingSupplierParty").item(0).getChildNodes();
			Node nNode_AccountingSupplierParty1 = nodeList_AccountingSupplierParty1.item(0);
			String _RUC_EMISOR1=nNode_AccountingSupplierParty1.getTextContent().substring(0, 11);

			Cabecera.set_ruc_emisor(_RUC_EMISOR1);
			System.out.println("RUC del Emisor_ _ _ _ _ _ _ _ _: " + Cabecera.get_ruc_emisor());
			
			
			// cac:PartyName
			NodeList nList_PartyName = doc.getElementsByTagName("cac:PartyName");
			Node nNode_PartyName = nList_PartyName.item(0);
			Cabecera.set_razon_social_emisor(nNode_PartyName.getTextContent());
			System.out.println("Razon Social del Emisor_ _ _ _ : " + Cabecera.get_razon_social_emisor());
			
			
			
			System.out.println(raya);
			
			// cbc:CustomerAssignedAccountID "RUC DEL RECEPTOR"
			NodeList nodeList_AccountingCustomerParty = doc.getElementsByTagName("cac:AccountingCustomerParty").item(0).getChildNodes();
			Node nNode_AccountingCustomerParty = nodeList_AccountingCustomerParty.item(0);
			String _RUC_RECEPTOR=nNode_AccountingCustomerParty.getTextContent().substring(0, 11);
			Cabecera.set_ruc_receptor(_RUC_RECEPTOR);
			System.out.println("RUC del Receptor_ _ _ _ _ _ _ _: " + Cabecera.get_ruc_receptor());
			
			
			// cac:PartyName
			NodeList nList_PartyName_r = doc.getElementsByTagName("cbc:RegistrationName");
			Node nNode_PartyName_r = nList_PartyName_r.item(1);
			Cabecera.set_razon_social_receptor(nNode_PartyName_r.getTextContent());
			System.out.println("Razon Social del Receptor_ _ _ : " + Cabecera.get_razon_social_receptor());
			
			
			
			
			
			// cbc:direccion
			
			// cbc:Value  direccion
			NodeList nList_Value = doc.getElementsByTagName("cbc:Line");
			Node nNode_Value = nList_Value.item(1);
			Cabecera.set_direccion_receptor(nNode_Value.getTextContent());
			System.out.println("Direccion del Receptor_ _ _ _ _: " + Cabecera.get_direccion_receptor());
			
			
			
			System.out.println(raya);



			NodeList nList_pre = doc.getElementsByTagName("cac:PrepaidPayment");
			String _id_pre = "";
			double _prepaidAmount = 0;
			String _doc_id = "";



			for (int temp = 0; temp < nList_pre.getLength(); temp++) {

				Node nNode_pre = nList_pre.item(temp);


				Element eElement_pre = (Element) nNode_pre;

				_id_pre=eElement_pre.getElementsByTagName("cbc:ID").item(0).getTextContent();
				_prepaidAmount=Double.parseDouble(eElement_pre.getElementsByTagName("cbc:PaidAmount").item(0).getTextContent());
				_doc_id=eElement_pre.getElementsByTagName("cbc:InstructionID").item(0).getTextContent();
				//		System.out.println("ID:"+_id+" "+"Payable:"+_PayableAmount);





			}








			//cbc:TaxableAmount   MONTO GRABADO
			NodeList nList_TaxableAmount = doc.getElementsByTagName("cbc:TaxableAmount");
			Node nNode_TaxableAmount_gra = nList_TaxableAmount.item(0);
			Cabecera.set_total_gravado(Double.parseDouble(nNode_TaxableAmount_gra.getTextContent()));
			System.out.println("Importe Grabado_ _ _ _ _ _ _ _: " + Cabecera.get_total_gravado());

			if (Cabecera.get_descripcion_documento().equals("Invoice")) {
			
			Node nNode_TaxableAmount_exo = nList_TaxableAmount.item(1);
			Cabecera.set_total_exonerado(Double.parseDouble(nNode_TaxableAmount_exo.getTextContent()));
			System.out.println("Importe Exonerado_ _ _ _ _ _ _: " + Cabecera.get_total_gravado());

			Node nNode_TaxableAmount_ina = nList_TaxableAmount.item(2);
			Cabecera.set_total_inafecto(Double.parseDouble(nNode_TaxableAmount_ina.getTextContent()));
			System.out.println("Importe Inafecto _ _ _ _ _ _ _: " + Cabecera.get_total_gravado());


			Node nNode_TaxableAmount_gratis = nList_TaxableAmount.item(3);
			//cbc:Name se verifica que exista transaccion gratuita
			NodeList nList_Names = doc.getElementsByTagName("cbc:Name");
			for (int _nodos=0; _nodos<=nList_Names.getLength()-1;_nodos++) {
				Node nNode_Names = nList_Names.item(_nodos);	
				if (nNode_Names.getTextContent().equals("GRA")) {
					Cabecera.set_total_gratuitas(Double.parseDouble(nNode_TaxableAmount_gratis.getTextContent()));
					System.out.println("Importe Gratuito _ _ _ _ _ _ _: " + Cabecera.get_total_gratuitas());


				}
			}

			}
			
		

			//cbc:TaxAmount



			// cbc:TaxAmount
			NodeList nList_TaxAmount = doc.getElementsByTagName("cbc:TaxAmount");
			Node nNode_TaxAmount = nList_TaxAmount.item(0);
			Cabecera.set_total_igv(Double.parseDouble(nNode_TaxAmount.getTextContent()));
			System.out.println("Importe IGV_ _ _ _ _ _ _ _ _ _: " + Cabecera.get_total_igv());


			// cbc:TaxableAmount
			NodeList nList_TaxableAmount_Det = doc.getElementsByTagName("cbc:TaxableAmount");



			// cbc:PayableAmount
			NodeList nList_PayableAmount = doc.getElementsByTagName("cbc:PayableAmount");
			Node nNode_PayableAmount = nList_PayableAmount.item(0);
	//		Cabecera.set_total(Double.parseDouble(nNode_PayableAmount.getTextContent()));
	//		System.out.println("TOTAL _ _ _ _ _ _ _ _ _ _ _ _ : " + Cabecera.get_total());

		


			Cabecera.set_total_descuentos(0.00);
			//			System.out.println("Importe Descuentos _ _ _ _ _ _: " + Cabecera.get_total_descuentos());


			NodeList nList_desc_cab = doc.getElementsByTagName("cbc:AllowanceTotalAmount");
			Node nNode_desc_cab = nList_desc_cab.item(0);
			Cabecera.set_descuentos_cabecera(Double.parseDouble(nNode_desc_cab.getTextContent()));


			System.out.println("Descuento Cabecera_  _ _ _ _ : " + Cabecera.get_descuentos_cabecera());


			if (Cabecera.get_descripcion_documento().equals("Invoice")) {

				// cac:LegalMonetaryTotal
				NodeList nList_total = doc.getElementsByTagName("cac:LegalMonetaryTotal");
				double _PayableAmount_total = 0;
				Node nNode_total = nList_total.item(0);
				Element eElement_total = (Element) nNode_total;
				_PayableAmount_total=Double.parseDouble(eElement_total.getElementsByTagName("cbc:PayableAmount").item(0).getTextContent());
				Cabecera.set_total(_PayableAmount_total);
				System.out.println("Importe Total_ _  _ _ _ _ _ _ : " + Cabecera.get_total());
				Cabecera.set_subtotal(Cabecera.get_total()-Cabecera.get_total_igv());
				Cabecera.set_total_letra(denomina.main(Cabecera.get_total()-Cabecera.get_total_descuentos()));
				System.out.println("Importe con Letra _ _ _ _ _ _ : " + Cabecera.get_total_letra());

			}




			if (Cabecera.get_descripcion_documento().equals("CreditNote")) {

				// cac:LegalMonetaryTotal
				NodeList nList_total = doc.getElementsByTagName("cac:LegalMonetaryTotal");
				double _PayableAmount_total = 0;
				Node nNode_total = nList_total.item(0);
				Element eElement_total = (Element) nNode_total;
				_PayableAmount_total=Double.parseDouble(eElement_total.getElementsByTagName("cbc:PayableAmount").item(0).getTextContent());
				Cabecera.set_total(_PayableAmount_total);
				System.out.println("Importe Total_ _  _ _ _ _ _ _ : " + Cabecera.get_total());
				Cabecera.set_total_letra(denomina.main(Cabecera.get_total()-Cabecera.get_total_descuentos()));
				System.out.println("Importe con Letra _ _ _ _ _ _ : " + Cabecera.get_total_letra());

			}



			if (Cabecera.get_descripcion_documento().equals("DebitNote")) {
				// cac:RequestedMonetaryTotal

				NodeList nList_total = doc.getElementsByTagName("cac:RequestedMonetaryTotal");
				double _PayableAmount_total = 0;
				Node nNode_total = nList_total.item(0);
				Element eElement_total = (Element) nNode_total;
				_PayableAmount_total=Double.parseDouble(eElement_total.getElementsByTagName("cbc:PayableAmount").item(0).getTextContent());
				Cabecera.set_total(_PayableAmount_total);
				System.out.println("Importe Total_ _  _ _ _ _ _ _ : " + Cabecera.get_total());
				Cabecera.set_total_letra(denomina.main(Cabecera.get_total()-Cabecera.get_total_descuentos()));
				System.out.println("Importe con Letra _ _ _ _ _ _ : " + Cabecera.get_total_letra());



			}









			// DigestValue
			NodeList nList_DigestValue = doc.getElementsByTagName("DigestValue");
			Node nNode_DigestValue = nList_DigestValue.item(0);
			Cabecera.set_codigo_hash(nNode_DigestValue.getTextContent());
			System.out.println("Codigo Hash_ _ _ _ _ _ _ _ _ _: " + Cabecera.get_codigo_hash());
			

			 
			 
				
			 
			 
		//	Cabecera.set_total_igv(0);
		//	Cabecera.set_subtotal(0);
		//	Cabecera.set_total(0);
			
			System.out.println(raya);
			System.out.println("Detalle del Documento_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
			
			// cbc:ID	cantidad
///			NodeList nList_linea = doc.getElementsByTagName("cbc:InvoicedQuantity");
			
			System.out.println("numero de lineas _: " + nList_linea.getLength());	
			int _total_linea=nList_linea.getLength();
			
			for (int _linea = 0; _linea < nList_linea.getLength(); _linea++) {
				Detalle[_linea] = new factura_detalle();
				Node nNode_linea = nList_linea.item(_linea);
				Detalle[_linea].set_cantidad(Double.parseDouble(nNode_linea.getTextContent()));
			
				}
			
			
			

			// cbc:ID	cbc:LineExtensionAmount
			NodeList nList_LineExtensionAmount = doc.getElementsByTagName("cbc:LineExtensionAmount");
			for (int _linea = 0; _linea < _total_linea; _linea++) {
				Node nNode_LineExtensionAmount = nList_LineExtensionAmount.item(_linea);
				Detalle[_linea].set_subtotal((Double.parseDouble(nNode_LineExtensionAmount.getTextContent())));
				}

			
			// cbc:PriceAmount  cac:Price
			NodeList nList_PriceAmount = doc.getElementsByTagName("cac:Price");
			for (int _linea = 0; _linea < _total_linea; _linea++) {
				Node nNode_PriceAmount = nList_PriceAmount.item(_linea);
				Detalle[_linea].set_precio_unitario((Double.parseDouble(nNode_PriceAmount.getTextContent())));
				Detalle[_linea].set_subtotal_sin_igv(Detalle[_linea].get_precio_unitario()*Detalle[_linea].get_cantidad());
				}
	
			
			// cbc:TaxableAmount
//			NodeList nList_TaxableAmount = doc.getElementsByTagName("cbc:TaxableAmount");
//			for (int _linea = 0; _linea < _total_linea; _linea++) {
//				Node nNode_TaxableAmount = nList_TaxableAmount.item(_linea);
//				Detalle[_linea].set_igv((Double.parseDouble(nNode_TaxableAmount.getTextContent())));
//				}
			
			// cbc:TaxableAmount
			NodeList nList_TaxableAmount2 = doc.getElementsByTagName("cbc:TaxableAmount");
			for (int _linea = 0; _linea < _total_linea; _linea++) {
				Node nNode_TaxableAmount = nList_TaxableAmount2.item(_linea);
				try {
					Detalle[_linea].set_igv((Double.parseDouble(nNode_TaxableAmount.getTextContent())));
				
					} catch (Exception e) {
						Detalle[_linea].set_igv(0);
					}
				}
				
			
			
	
			// cbc:Description
			NodeList nList_Description = doc.getElementsByTagName("cbc:Description");
			for (int _linea = 0; _linea < _total_linea; _linea++) {
				Node nNode_Description = nList_Description.item(_linea);
				Detalle[_linea].set_descripcion(nNode_Description.getTextContent());
				}
	
			// cac:SellersItemIdentification
			NodeList nList_SellersItemIdentification = doc.getElementsByTagName("cac:SellersItemIdentification");
			for (int _linea = 0; _linea < _total_linea; _linea++) {
		//		Node nNode_SellersItemIdentification = nList_SellersItemIdentification.item(_linea);
		//		Node nNode_codigo = nNode_SellersItemIdentification.getFirstChild();
				
		//		System.out.println("The node's value that you are looking now is : " +  nList_SellersItemIdentification.item(_linea).getTextContent());
        //        System.out.println(" Get the Name of the Next Sibling " + nList_SellersItemIdentification.item(_linea).getNextSibling().getNodeName());
         //       System.out.println(" Get the Value of the Next Sibling " + nList_SellersItemIdentification.item(_linea).getNextSibling().getNodeValue());
				
				//Node nNode_codigo = nNode_SellersItemIdentification.getNextSibling()
				String _temp2 = nList_SellersItemIdentification.item(_linea).getTextContent();
				
			//	_temp2.substring(10,11); //_temp.length();
				String _temp3=_temp2.replace(" ","");
				Detalle[_linea].set_codigo(_temp3.substring(1,_temp3.length()-1));
				}
			
			//   para sacar la unidad de medida cbc:InvoicedQuantity
///			NodeList nList_InvoicedQuantity = doc.getElementsByTagName("cbc:InvoicedQuantity");
			for (int _linea = 0; _linea < _total_linea; _linea++) {
				
				
				Node nNode_InvoicedQuantity = nList_InvoicedQuantity.item(_linea);
				if (nNode_InvoicedQuantity.hasAttributes()) {
				    NamedNodeMap attributes = nNode_InvoicedQuantity.getAttributes();
				    Node nameAttribute = attributes.getNamedItem("unitCode");
				    if (nameAttribute != null) {
				         System.out.println("Name attribute: " +nameAttribute.getTextContent());
				        Detalle[_linea].set_unidad(nameAttribute.getTextContent());
				    }
				}
				
				
				}
			
			
			int _linea_imp2=0;
			for (int _linea_imp=0;_linea_imp<_total_linea;_linea_imp++) {
				_linea_imp2=_linea_imp+1;
				System.out.println("");
				System.out.println("Linea_ _ _ _ _ _ _ _ _ _ _: " + _linea_imp2);
				System.out.println("Codigo_ _ _ _ _ _ _ _ _ _ : " + Detalle[_linea_imp].get_codigo());
				System.out.println("Unidad de Medida_ _ _ _ _ : " + Detalle[_linea_imp].get_unidad());
				System.out.println("Descripcion _ _ _ _ _ _ _ : " + Detalle[_linea_imp].get_descripcion());
				System.out.println("Cantidad_ _ _ _ _ _ _ _ _ : " + Detalle[_linea_imp].get_cantidad());
				System.out.println("Precio Unitario _ _ _ _ _ : " + Detalle[_linea_imp].get_precio_unitario());
				System.out.println("IGV _ _ _ _ _ _ _ _ _ _ _ : " + Detalle[_linea_imp].get_igv());
				System.out.println("Monto con IGV _ _ _ _ _ _ : " + Detalle[_linea_imp].get_subtotal());
				System.out.println("Monto sin IGV _ _ _ _ _ _ : " + Detalle[_linea_imp].get_subtotal_sin_igv());
				_lineas_de_la_factura=_linea_imp2;
				
				
				
			}
			
		
			
			//                      String _file_xml, Cabecera, Detalle, int _lineas_de_la_factura, String _file_pdf, String _file_jpg) throws DocumentException, IOException {
			SFSprintPDF_AgroSM.imp_factura(_file_xml, Cabecera, Detalle, _lineas_de_la_factura,_file_pdf, _file_jpg);	
			if (Cabecera.get_tipo_doc().substring(1).equals("1")) {
///				SFSprintPDF_AgroSMFacturaNegociable.imp_factura(_file_xml, Cabecera, Detalle, _lineas_de_la_factura,_file_pdf_fn);
			}
			
			System.out.println("Reporte PDF Generado:"+_file_pdf);
			if (_correo_destino=="nada") {
				System.out.println("Correo Vacio, no se envio correo...");
			} else {
				System.out.println("Envinando Correo a "+_correo_destino);
				SFSemail.main(_correo_destino,_file_xml,_file_pdf,_file,_correo_destino_cc);
				System.out.println("Correo Enviado.");
			}
			
			
			
			
			//factura.imp_factura(_file_xml, Cabecera, Detalle);
				
			
			//SFSprintPDF.imp_factura(_file_xml, Cabecera, Detalle, _lineas_de_la_factura,_file_pdf);
		} catch (Exception e) {
	  		e.printStackTrace();
    	}

	}
	
	public static boolean isNullOrEmpty(String a) {
		return a == null || a.isEmpty();
		} 
			
	
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	
	
	
	

}
