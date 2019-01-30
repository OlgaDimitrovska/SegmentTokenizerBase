package PDFtoText;

import java.io.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class pdftotext {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		  File file = new File("D:\\Stecajni postapki 2018\\SLU@BEN VESNIK NA RM br. 4-STE^AJNI POSTAPKI.pdf");
	      PDDocument document = PDDocument.load(file);

	      //Instantiate PDFTextStripper class
	      PDFTextStripper pdfStripper = new PDFTextStripper();

	      //Retrieving text from PDF document
	      String text = pdfStripper.getText(document);
	      
	      InputStream stream = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
	      
	      System.out.println(text);

	      //Closing the document
	      document.close();
		
		
	}

}
