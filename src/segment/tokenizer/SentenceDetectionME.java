package segment.tokenizer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceModel;

public class SentenceDetectionME {
	
	
	public static void main(String args[]) throws Exception{
	
		String sent="Hi. How are you? Welcome to Tutorialspoint. " 
		         + "We provide free tutorials on various technologies";
		
//		String sentence ="Основниот суд Скопје I - Скопје, објавува дека со решението I Ст. бр. 185/2000 од 05.12.2000 година, \n"
//			+ "над правното лице Претпријатие за трговија и услуги “СТАМЕКС“ ДОО увоз-извоз-Скопје, е отворена стечајна постапка. \n"
//			+ "Отворената стечајна постапка над должникот Прет- пријатие за трговија и услуги “СТАМЕКС“ ДОО увоз- извоз-Скопје, \n"
//			+ " бул. “Партизански одреди“ бр. 155/I-58 и жиро сметка 40100-601-302551, се заклучува. \n"
//			+ "Да се објави огласот во “Службен весник на РМ“ и на огласната табла во судот. \n"
//			+ "По правосилноста на решението должникот да се брише од судскиот регистар кој го води овој суд. \n" 
//			+ "Од Основниот суд Скопје I - Скопје.";
			
	
	
	FileInputStream modelIn = new FileInputStream("C:\\Users\\olgad\\Downloads\\en-sent.bin");

	try {
		  SentenceModel model = new SentenceModel(modelIn);
		}
		catch (IOException e) {
		  e.printStackTrace();
		}
		finally {
		  if (modelIn != null) {
		    try {
		      modelIn.close();
		    }
		    catch (IOException e) {
		    }
		  }
		}
	
	
}
}
