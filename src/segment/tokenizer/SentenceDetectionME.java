package segment.tokenizer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceModel;

public class SentenceDetectionME {
	
	
	public static void main(String args[]) throws Exception{
	
		String sent="Hi. How are you? Welcome to Tutorialspoint. " 
		         + "We provide free tutorials on various technologies";
		
//		String sentence ="��������� ��� ����� I - �����, ������� ���� �� ��������� I ��. ��. 185/2000 �� 05.12.2000 ������, \n"
//			+ "��� �������� ���� ����������� �� ������� � ������ �������ѓ ��� ����-�����-�����, � �������� ������� ��������. \n"
//			+ "���������� ������� �������� ��� ��������� ����- ������� �� ������� � ������ �������ѓ ��� ����- �����-�����, \n"
//			+ " ���. ������������ ������ ��. 155/I-58 � ���� ������ 40100-601-302551, �� ���������. \n"
//			+ "�� �� ����� ������� �� �������� ������ �� �̓ � �� ��������� ����� �� �����. \n"
//			+ "�� ������������� �� ��������� ��������� �� �� ����� �� �������� �������� �� �� ���� ��� ���. \n" 
//			+ "�� ��������� ��� ����� I - �����.";
			
	
	
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
