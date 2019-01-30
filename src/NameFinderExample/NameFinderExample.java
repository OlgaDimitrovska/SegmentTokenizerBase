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
			 
			 System.out.println("----------------Барање ентитети што припаѓаат на категорија : ----------------");
 
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
		    
	     
		    


	     //String[] sentence = new String[]{"Основниот" , "суд" , "во" , "Гостивар" , "објавува" , "дека" , "со" , "решение" , "на" , "овој" , "суд" , "Ст." , "бр." , "106/2002" , "од" , "25.10.2002" , "година" , "отворена" , "е" ,"стечајна", "постапка" ,"над", "должникот" ,"Друштвото", "за", "трговија", "угостителство", "сообраќај" ,"и" ,"услуги" ,"НИЈАЗ" ,"Абдулфета", "ДООЕЛ", "увоз","-","извоз" ,"од" ,"Гостивар", "но" ,"истата", "не" , "се" ,"спроведува" ,"поради", "немање" ,"на", "имот", "па" , "отворената"	,"стечајна" ,"постапка", "над", "должникот", "се" ,"заклучува",".", "Од" ,"Основниот" ,"суд" ,"во", "Гостивар","." }; 
	     //String[] sentence2 = new String[] {"Основниот" ,"суд", "Скопје" ,"I", "-", "Скопје", "објавува", "дека" ,"со" ,"ре- шение", "на" ,"овој" ,"суд" ,"I.", "Ст." ,"бр.", "427/02", "од" ,"29.01.2003" ,"година", ",", "е", "отворена", "стечајна" ,"постапка", "над" ,"должникот", "Друштво" ,"за" ,"производство" ,"и", "промет" ,"на" ,"хемиски" ,"производи", "за" ,"градежништво" ,"и", "заштита" ,"на" ,"материјали" ,"и" ,"објекти" ,"АНТИКОР-МАКЕДОНИЈА" ,"АД", "-" ,"Скопје" ,"," ,"со", "седиште", "на" ,"ул." ,"20", "бр. 4-Б", " ," ,"со", "жиро" ,"сметка" ,"бр.", "40120-601-13362" ,"при" ,"Агенција" ,"за" ,"работа" ,"со" ,"блокирани", "сметки", "на", "учесниците", "во", "платниот" ,"промет" ,"на" ,"Република", "Маке- донија","-","Скопје",".", "За", "стечаен", "управник", "се" ,"определува" ,"Зоран" ,"Стојанкиќ", "од" ,"Скопје",",", "со" ,"адреса", "на", "ул.", "Владимир Комаров", "бр." ,"1/1-13",".", "Се" ,"закажува", "рочиште", "на", "собир", "на", "доверители" ,"за", "испитување" ,"и", "утврдување", "на" ,"пријавените", "побарувања", "(испитно" ,"рочиште)", "и", "рочиште" ,"на", "собрание", "на", "довери- телите", "на", "кое", "врз" ,"основа" ,"на" ,"извештајот", "на", "стечајниот", "управник", "доверителите", "ќе" ,"одлучуваат", "за", "понатамошни- от" ,"тек" ,"на", "постапката", "(извештајно", "рочиште)" ,"на", "12.03.2003", "година",",", "во", "12,30", "часот" ,"во" ,"соба", "72", "во", "зградата" ,"на" ,"Апелациониот" ,"суд", "во" ,"Скопје", "." ,"Да", "се" ,"објави", "оглас" ,"на" ,"огласна", "табла", "во" ,"судот", "и" ,"во", "Службен" ,"весник", "на", "РМ" ,".", "Од", "Основниот", "суд", "Скопје", "I", "-", "Скопје", "."};  
	
	     
	     //String[] sentence4= new String[] {"Основниот","суд","Скопје","I","-","Скопје",",","објавува","дека","со","решение","на","овој","суд","I.","Ст.","бр.","261/02","од","30.09.2002","го-","дина,","е","поведена","претходна","постапка","над","должникот","АЛМАКО","Дивна","и","други","ДОО","-","Скопје,","со","седиште","на","ул.","“8","Ударна","бригада“","бр.","20","А,","со","жиро","сметка","бр.","40100-601-90212.",
	    	//	 "За","стечаен","судија","се","определува","Дејан","Костовски","судија","на","овој","суд","и","член","на","стечајниот","совет.",
	    		// "Се","закажува","рочиште","поради","изјаснување","по","пред-","логот","за","отворање","на","стечајна","постапка","на","ден","30.10.2002","година","во","12,30","часот","во","соба","бр.","70","во","згра-","дата","на","Апелациониот","суд","во","Скопје.",
	    		 //"За","привремен","стечаен","управник","се","определува","Симон","Михајлоски","од","Скопје,","ул.","“Белишка“","бр.","18.",
	    		 //"Се","задолжува","привремениот","стечаен","управник","да","го","заштити","и","одржува","имотот","на","должникот,","да","продолжи","со","водење","на","претпријатието","на","должникот,","се","до","доне-","сување","на","Одлуката","за","отворање","на","стечајна","постапка","и","да","испита","дали","од","имотот","на","должникот","може","да","се","на-","мират","трошоците","на","постапката.",
	    		 //"Се","повикуваат","должниците","на","должникот","своите","обврски","и","должниковите","солидарни","содолжници","и","га-","ранти","без","одлагање","да","ги","исполнуваат","своите","обврски","кон","должникот.",
	    		 //"Се","задолжува","должникот","да","му","овозможи","на","при-","времениот","стечаен","управник","да","влезе","во","неговите","де-","ловни","простории","за","да","може","да","ги","спроведе","потребните","дејствија,","како","и","да","му","допушти","увид","во","трговските","книги","и","неговата","деловна","документација.",
	    		 //"Решението","да","се","објави","во","“Службен","весник","на","РМ“","и","на","огласната","табла","во","судот.",
	    		 //"Од","Основниот","суд","Скопје","I-","Скопје"};
	     
	     //String[] sentence5= new String[] {"Основниот","суд","во","Гевгелија,","објавува","дека","со","реше-","ние","Ст.","бр.","47/2002","од","30.09.2002","година","е","отворена","стечајна","постапка","над","должникот","Трговско","друштво","за","промет","и","услуги","“МИМА-ПЕ","Милица","Чалкова“","ДОО-","ЕЛ","Гевгелија,","со","жиро","сметка","број","41610-601-29364","која","се","води","кај","Агенцијата","за","работа","со","блокирани","сме-","тки,","регистрирано","во","Основниот","суд","Скопје","I","-","Скопје,","но","истата","не","се","спроведува","заради","немање","на","имот","од","кој","би","се","намириле","трошоците","на","стечајната","постапка.",
	    	//	 "Отворената","стечајна","постапка","над","должникот","МИ-","МА-ПЕ","Милица","Чалкова","од","Гевгелија,","веднаш","се","заклучува",
	    		// "Против","ова","решение","дозволена","е","жалба","во","рок","од","8","дена","од","објавувањето","преку","овој","суд","до","Апелациониот","суд","во","Скопје.",
	    		 //"Од","Основниот","суд","во","Гевгелија."};
	     
	     
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
