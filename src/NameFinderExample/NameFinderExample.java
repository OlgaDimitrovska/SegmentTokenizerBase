package NameFinderExample;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.checkerframework.checker.nullness.compatqual.KeyForType;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.Span;
import org.elasticsearch.common.util.set.Sets;
import org.elasticsearch.common.xcontent.ObjectParser.ValueType;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.*;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.RegionTextRenderFilter;
import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import PDFtoText.pdftotext;

import org.apache.poi.xssf.*;
import org.apache.poi.xssf.usermodel.*;
import org.mozilla.universalchardet.UniversalDetector;

public class NameFinderExample {
	

	
	public static void main(String[] args) {
		
		
		 try {
			 

			 List<String> dokument = getSlucai(PDFtoTEXT());
			 
			 System.out.println("----------------������ �������� ��� ��������� �� ��������� : ----------------");
 
			 for(String slucaj : dokument)
			 {
				find(slucaj);
				System.out.printf("%n"); 
				 
			 }
			 

			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		 
	

	}
	
	
	 	
	
	
	public static String PDFtoTEXT() throws IOException{
		
		  
	          
		  File file = new File("D:\\Stecajni postapki 2018\\SLU@BEN VESNIK NA RM br. 4-STE^AJNI POSTAPKI.pdf");
		 /* File txt= new File("D:\\Stecajni postapki 2018\\outFile.txt");
		  
		  PdfReader reader = new PdfReader("D:\\Stecajni postapki 2018\\SLU@BEN VESNIK NA RM br. 1-STE^AJNI POSTAPKI.pdf");
		  PrintWriter out = new PrintWriter(new FileOutputStream(txt));
		  Rectangle rect = new Rectangle(70, 80, 420, 500);
		  RenderFilter filter = new RegionTextRenderFilter(rect);
		  TextExtractionStrategy strategy;
		  for (int i = 1; i <= reader.getNumberOfPages(); i++) {
		       strategy = new FilteredTextRenderListener(
		       new LocationTextExtractionStrategy(), filter);
		       out.println(PdfTextExtractor.getTextFromPage(reader, i, strategy));
		  }
		  out.flush();
		  out.close();
		  
		  */
		  
	      PDDocument document = PDDocument.load(file);
 
	      PDFTextStripper pdfStripper = new PDFTextStripper();

	      String text = pdfStripper.getText(document);
	      
	      //FileInputStream fis = new FileInputStream(file);
	      
	      //System.out.println(text);

	      document.close();
	      
	      
	       return text;
	      
	     
	     

	}
	

	public static List<String> getSlucai(String str) {
	    List<String> tokens = new ArrayList<>();
	    StringTokenizer tokenizer = new StringTokenizer(str,"__________");
	    while (tokenizer.hasMoreElements()) {
	        tokens.add(tokenizer.nextToken());
	    }
	    return tokens;
	    
	
	}
	

	
	public static LinkedHashMap<String, String> find(String content) throws IOException {
	    
	
	    	InputStream is= new  FileInputStream("D:\\JavaNlpMk\\mk ner modeli statusi stecai\\predstecajni-ner-model.bin");
			 
			TokenNameFinderModel model = new TokenNameFinderModel(is);
		    is.close();

		    //Map<String, TokenNameFinderModel> nameFinderModels = new HashMap<>(); 
		     
		    NameFinderME nameFinder = new NameFinderME(model);
	        //TokenNameFinderModel finderModel= nameFinderModels.get(field);

	        String[] tokens = SimpleTokenizer.INSTANCE.tokenize(content);
	        
	        Span spans[] = nameFinder.find(tokens);
	        
	        //System.out.println(spans.length);
	        
	        String spans2[]= new String[spans.length-1];
   
	        for (int i=0; i<spans.length-1; ++i) {
	            spans2[i] =spans[i].toString();
	        }

	        String[] names = Span.spansToStrings(spans, tokens);
	        
	        LinkedHashMap<String, String> settt = new LinkedHashMap<String, String>();
	      
	        
	        for(int j=0;j<spans2.length-1;j++) {
	        	
	        	settt.put(spans2[j], names[j]);
	        	
	        }
	        
	        

	        for (Entry<String, String> entry : settt.entrySet()) {
	            System.out.println(entry.getKey().toString()+" : "+entry.getValue().toString());
	        }
	        
	        return settt;
	   
	}

	
	// public Set<String> NajdiSud() throws IOException{
		 
		 
		// 
		 
		 
	 
	     // feed the model to name finder class
	  //   NameFinderME nameFinder = new NameFinderME(model);
	     
	    // ArrayList<String> sent =getTokens(PDFtoTEXT());
	     
	     //Instantiating SimpleTokenizer class 
	     //for(String sentence : sent)
		   // {
		    	//StringTokenizer st= new StringTokenizer(sentence," ");
		    	
		    //	to=SimpleTokenizer.INSTANCE.tokenize(sentence);
		    	// spans = new NameFinderME(model).find(to);
		        // names = Span.spansToStrings(spans, to);
		        //h= new HashSet<String>(Arrays.asList(names));
		    
		   // }
	     
	    // return h;
		    
	     
		    


	     //String[] sentence = new String[]{"���������" , "���" , "��" , "��������" , "�������" , "����" , "��" , "�������" , "��" , "���" , "���" , "��." , "��." , "106/2002" , "��" , "25.10.2002" , "������" , "��������" , "�" ,"�������", "��������" ,"���", "���������" ,"���������", "��", "�������", "�������������", "��������" ,"�" ,"������" ,"�ȣ��" ,"���������", "�����", "����","-","�����" ,"��" ,"��������", "��" ,"������", "��" , "��" ,"����������" ,"������", "������" ,"��", "����", "��" , "����������"	,"�������" ,"��������", "���", "���������", "��" ,"���������",".", "��" ,"���������" ,"���" ,"��", "��������","." }; 
	     //String[] sentence2 = new String[] {"���������" ,"���", "�����" ,"I", "-", "�����", "�������", "����" ,"��" ,"��- �����", "��" ,"���" ,"���" ,"I.", "��." ,"��.", "427/02", "��" ,"29.01.2003" ,"������", ",", "�", "��������", "�������" ,"��������", "���" ,"���������", "�������" ,"��" ,"������������" ,"�", "������" ,"��" ,"�������" ,"���������", "��" ,"������������" ,"�", "�������" ,"��" ,"���������" ,"�" ,"������" ,"�������-�������ȣ�" ,"��", "-" ,"�����" ,"," ,"��", "�������", "��" ,"��." ,"20", "��. 4-�", " ," ,"��", "����" ,"������" ,"��.", "40120-601-13362" ,"���" ,"�������" ,"��" ,"������" ,"��" ,"���������", "������", "��", "����������", "��", "��������" ,"������" ,"��" ,"���������", "����- �����","-","�����",".", "��", "�������", "��������", "��" ,"����������" ,"�����" ,"�������", "��" ,"�����",",", "��" ,"������", "��", "��.", "�������� �������", "��." ,"1/1-13",".", "��" ,"��������", "�������", "��", "�����", "��", "����������" ,"��", "����������" ,"�", "����������", "��" ,"����������", "����������", "(�������" ,"�������)", "�", "�������" ,"��", "��������", "��", "������- ������", "��", "���", "���" ,"������" ,"��" ,"���������", "��", "���������", "��������", "������������", "��" ,"����������", "��", "�����������- ��" ,"���" ,"��", "����������", "(���������", "�������)" ,"��", "12.03.2003", "������",",", "��", "12,30", "�����" ,"��" ,"����", "72", "��", "��������" ,"��" ,"������������" ,"���", "��" ,"�����", "." ,"��", "��" ,"�����", "�����" ,"��" ,"�������", "�����", "��" ,"�����", "�" ,"��", "�������" ,"������", "��", "��" ,".", "��", "���������", "���", "�����", "I", "-", "�����", "."};  
	
	     
	     //String[] sentence4= new String[] {"���������","���","�����","I","-","�����",",","�������","����","��","�������","��","���","���","I.","��.","��.","261/02","��","30.09.2002","��-","����,","�","��������","���������","��������","���","���������","������","�����","�","�����","���","-","�����,","��","�������","��","��.","�8","������","��������","��.","20","�,","��","����","������","��.","40100-601-90212.",
	    	//	 "��","�������","�����","��","����������","����","���������","�����","��","���","���","�","����","��","���������","�����.",
	    		// "��","��������","�������","������","����������","��","����-","�����","��","��������","��","�������","��������","��","���","30.10.2002","������","��","12,30","�����","��","����","��.","70","��","����-","����","��","������������","���","��","�����.",
	    		 //"��","���������","�������","��������","��","����������","�����","���������","��","�����,","��.","���������","��.","18.",
	    		 //"��","���������","������������","�������","��������","��","��","�������","�","�������","������","��","���������,","��","��������","��","�����","��","�������������","��","���������,","��","��","����-","������","��","��������","��","��������","��","�������","��������","�","��","������","����","��","������","��","���������","����","��","��","��-","�����","���������","��","����������.",
	    		 //"��","����������","����������","��","���������","������","�������","�","������������","���������","����������","�","��-","�����","���","��������","��","��","�����������","������","�������","���","���������.",
	    		 //"��","���������","���������","��","��","��������","��","���-","���������","�������","��������","��","�����","��","��������","��-","�����","���������","��","��","����","��","��","��������","����������","�������,","����","�","��","��","�������","����","��","����������","�����","�","��������","�������","������������.",
	    		 //"���������","��","��","�����","��","��������","������","��","�̓","�","��","���������","�����","��","�����.",
	    		 //"��","���������","���","�����","I-","�����"};
	     
	     //String[] sentence5= new String[] {"���������","���","��","��������,","�������","����","��","����-","���","��.","��.","47/2002","��","30.09.2002","������","�","��������","�������","��������","���","���������","��������","�������","��","������","�","������","�����-��","������","��������","���-","��","��������,","��","����","������","���","41610-601-29364","���","��","����","��","���������","��","������","��","���������","���-","���,","������������","��","���������","���","�����","I","-","�����,","��","������","��","��","����������","������","������","��","����","��","��","��","��","��������","���������","��","���������","��������.",
	    	//	 "����������","�������","��������","���","���������","��-","��-��","������","�������","��","��������,","������","��","���������",
	    		// "������","���","�������","���������","�","�����","��","���","��","8","����","��","�����������","�����","���","���","��","������������","���","��","�����.",
	    		 //"��","���������","���","��","��������."};
	     
	     
	    // String sentence6="";
	     
	     
	    
	     
	     
//	     Span nameSpans[] = nameFinder.find(sentence5);
//	     
//	     for(Span s: nameSpans){
//	            System.out.print(s.toString());
//	            System.out.print("  :  ");
//	            // s.getStart() : contains the start index of possible name in the input string array
//	            // s.getEnd() : contains the end index of the possible name in the input string array
//	            for(int index=s.getStart();index<s.getEnd();index++){
//	                System.out.print(sentence5[index]+" ");
//	            }
//	            System.out.println();
//	        }
//	     
//	     
	     
		 
	 //}

}
