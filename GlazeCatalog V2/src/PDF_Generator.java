import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.itextpdf.io.image.ImageData; 
import com.itextpdf.io.image.ImageDataFactory; 

import com.itextpdf.kernel.pdf.PdfDocument; 

import com.itextpdf.text.Image;

public class PDF_Generator {

	
	public static void main(String[] args)
	{
		new PDF_Generator();
		System.out.println("Finished ...");
	}
	
	public PDF_Generator()
	{
		try{
			createGlazePage();
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("ERROR !!!!");
		}
	}
	
	public void createGlazePage() throws Exception
	{
		Document document = new Document();
	    //Create new File
	    File file = new File("/Users/ScottCampbell/Desktop/testPDF.pdf");
	    file.createNewFile();
	    FileOutputStream fop = new FileOutputStream(file);
	    PdfWriter.getInstance(document, fop);
	    document.open(); 
	    
	    Font fontHeader = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	    Font fontBody = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL);
	    
	    
	    Image image1 = Image.getInstance("/Users/ScottCampbell/Desktop/addy.jpg");
	    image1.scaleAbsolute(450, 250);
	    //Add to document
	    document.add(image1);

	    
	    document.close();	    
	}
	
}
