import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.io.image.ImageData; 
import com.itextpdf.io.image.ImageDataFactory; 

import com.itextpdf.kernel.pdf.PdfDocument; 

import com.itextpdf.text.Image;

public class PDF_Generator_v2 {
	/**
	 * TO DO: Add a table of contents class 
	 */
	
	
	public static void main(String[] args)
	{
		new PDF_Generator_v2().generateTestPage();
		System.out.println("Finished v2...");
	}
	
	public PDF_Generator_v2()
	{
		
	}
	
	/**
	 * Returns whether or not an image export was successful
	 * @param destinationPath
	 * @return
	 */
	public boolean saveRecipeAsPDF(GlazeRecipe recipe, String destinationPath)
	{
		return false;
		/**
		try{
			Document document = new Document();
			File tempFile = new File(destinationPath);
			tempFile.createNewFile();
		    FileOutputStream fop = new FileOutputStream(tempFile);
		    PdfWriter writer = PdfWriter.getInstance(document, fop);
		    document.open(); 
			new GlazePage(document, writer, recipe);
		    document.close();
		    return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		*/
	}
	
	public void generateTestPage()
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
		GlazeRecipe baldwinGreen = new GlazeRecipe("Glaze Recipes/Baldwin Green/Baldwin Green.txt");
		GlazeRecipe baldwinBlue = new GlazeRecipe("Glaze Recipes/Baldwin Blue/Baldwin Blue.txt");
		GlazeRecipe pinnelSeafoam = new GlazeRecipe("Glaze Recipes/Pinnell Seafoam/Pinnell Seafoam.txt");
		GlazeRecipe clausenOpalBlue = new GlazeRecipe("Glaze Recipes/Clausen Opal Blue/Clausen Opal Blue.txt");
		GlazeRecipe yellowBrownOpal = new GlazeRecipe("Glaze Recipes/Yellow Brown Opal/Yellow Brown Opal.txt");
		
		Document document = new Document();
	    //Create new File
	    File file = new File("/Users/ScottCampbell/Desktop/Test PDF/testPDF.pdf");
	    file.createNewFile();
	    FileOutputStream fop = new FileOutputStream(file);
	    PdfWriter writer = PdfWriter.getInstance(document, fop);
	    document.open(); 
		
	    new GlazeTitlePage(document, writer);
	    document.newPage();
	    
		new GlazePage(document, writer, baldwinGreen, baldwinBlue);
		new GlazePageFooter(writer, "Mid-Range Glazes", "Gonzaga University Glaze Catalog", "Page 1");
		document.newPage();
		
		new GlazePage(document, writer, pinnelSeafoam, clausenOpalBlue);
		new GlazePageFooter(writer, "Mid-Range Glazes", "Gonzaga University Glaze Catalog", "Page 2");
		document.newPage();
		
		new GlazePage(document, writer, yellowBrownOpal);
		new GlazePageFooter(writer, "Mid-Range Glazes", "Gonzaga University Glaze Catalog", "Page 3");
		
		document.close();
	}
	
	/**
	 * This class creates a title page for the glaze catalog
	 */
	private class GlazeTitlePage
	{
		private final int START_Y_POS = 650;						// The starting position of the description
		private final int BUFFER_X = 80;							// Buffer from the left and right side of the page
		private final int PAGE_WIDTH = 595;							// The width of the Page in pixels
		private final int TITLE_TO_LINE	= 25;						// The distance from the title to the line under it
		private final int TITLE_TO_DESC = 70;						// The distance from the title to the description
		private final int PHOTO_BUFFER = 50;						// Buffer of the photo from the sides
		private final int PHOTO_START_Y = 300;						// Starting y position of the photo
		
		private Font titleFont = new Font(Font.FontFamily.HELVETICA, 31, Font.NORMAL);
		private Font descFont = new Font(Font.FontFamily.HELVETICA, 22, Font.NORMAL);

		public GlazeTitlePage(Document document, PdfWriter writer) throws Exception
		{
			PdfContentByte cb = writer.getDirectContent();
			ColumnText ct1 = new ColumnText(cb);	// Title
			ColumnText ct2 = new ColumnText(cb);	// Description
			
			Phrase title = new Phrase();	title.setFont(titleFont);
			title.add(new Chunk("Gonzaga University"));
			LineSeparator ls1 = new LineSeparator();
			ls1.drawLine(cb, BUFFER_X, PAGE_WIDTH - BUFFER_X, START_Y_POS - TITLE_TO_LINE);
			
			Phrase desc = new Phrase();		desc.setFont(descFont);
			desc.add(new Chunk("Visual Database of Ceramic Glazes"));
			
			Image image1 = Image.getInstance("titleImage.png");
		    image1.scaleAbsolute(PAGE_WIDTH - 2*PHOTO_BUFFER, (float)(.25*(PAGE_WIDTH - 2*PHOTO_BUFFER)));
		    image1.setAbsolutePosition(PHOTO_BUFFER, PHOTO_START_Y);
		    document.add(image1);
			
			ct1.setSimpleColumn(title, BUFFER_X, START_Y_POS, PAGE_WIDTH - BUFFER_X, 0, 15, Element.ALIGN_CENTER);
			ct2.setSimpleColumn(desc, BUFFER_X, START_Y_POS - TITLE_TO_DESC, PAGE_WIDTH - BUFFER_X, 0, 15, Element.ALIGN_CENTER);
			ct1.go();
			ct2.go();
		}
	}
	
	/**
	 * This class creates a table of contents and adds it to the PDF
	 */
	private class TableOfContents
	{
		private GlazeRecipe[] lowFireRecipes;
		private GlazeRecipe[] midFireRecipes;
		private GlazeRecipe[] highFireRecipes;
		
		/**
		 * @param glazeRootFile - the file that contains all of the glaze recipes
		 */
		public TableOfContents(Document document, PdfWriter writer, File glazeRootFile)
		{
			//Seperate the recipes into low, mid and high firing range
			//		create pages out of each 
		}
	}
	
	/**
	 * This class will create and properly format a header for a glaze page
	 */
	private class GlazePageFooter
	{
		
		private final int START_Y_POS = 45;							// The starting position of the description
		private final int BUFFER_X = 80;							// Buffer from the left and right side of the page
		private final int PAGE_WIDTH = 595;							// The width of the Page in pixels

		
		private Font headerFont = new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL);
		
		public GlazePageFooter(PdfWriter writer, String left, String center, String right) throws Exception
		{
			PdfContentByte cb = writer.getDirectContent();
			ColumnText ct1 = new ColumnText(cb);	// Left
			ColumnText ct2 = new ColumnText(cb);	// Right
			ColumnText ct3 = new ColumnText(cb);	// Center
			
			Phrase leftContents = new Phrase();		leftContents.setFont(headerFont);
			Phrase centerContents = new Phrase();	centerContents.setFont(headerFont);
			Phrase rightContents = new Phrase();	rightContents.setFont(headerFont);
			
			leftContents.add(new Chunk(left));
			centerContents.add(new Chunk(center));
			rightContents.add(new Chunk(right));
				
			ct1.setSimpleColumn(leftContents, BUFFER_X, START_Y_POS, PAGE_WIDTH - BUFFER_X, 0, 15, Element.ALIGN_LEFT);			ct1.go();
			ct2.setSimpleColumn(centerContents, BUFFER_X, START_Y_POS, PAGE_WIDTH - BUFFER_X, 0, 15, Element.ALIGN_CENTER);		ct2.go();
			ct3.setSimpleColumn(rightContents, BUFFER_X, START_Y_POS, PAGE_WIDTH - BUFFER_X, 0, 15, Element.ALIGN_RIGHT);		ct3.go();
		}
	}
	
	/**
	 * This class will create and properly format a header for a glaze page
	 */
	private class GlazePageHeader
	{
		
		private final int START_Y_POS = 818;						// The starting position of the description
		private final int BUFFER_X = 80;							// Buffer from the left and right side of the page
		private final int PAGE_WIDTH = 595;							// The width of the Page in pixels

		
		private Font headerFont = new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL);
		
		public GlazePageHeader(PdfWriter writer, String left, String center, String right) throws Exception
		{
			PdfContentByte cb = writer.getDirectContent();
			ColumnText ct1 = new ColumnText(cb);	// Left
			ColumnText ct2 = new ColumnText(cb);	// Right
			ColumnText ct3 = new ColumnText(cb);	// Center
			
			Phrase leftContents = new Phrase();		leftContents.setFont(headerFont);
			Phrase centerContents = new Phrase();	centerContents.setFont(headerFont);
			Phrase rightContents = new Phrase();	rightContents.setFont(headerFont);
			
			leftContents.add(new Chunk(left));
			centerContents.add(new Chunk(center));
			rightContents.add(new Chunk(right));
				
			ct1.setSimpleColumn(leftContents, BUFFER_X, START_Y_POS, PAGE_WIDTH - BUFFER_X, 0, 15, Element.ALIGN_LEFT);			ct1.go();
			ct2.setSimpleColumn(centerContents, BUFFER_X, START_Y_POS, PAGE_WIDTH - BUFFER_X, 0, 15, Element.ALIGN_CENTER);		ct2.go();
			ct3.setSimpleColumn(rightContents, BUFFER_X, START_Y_POS, PAGE_WIDTH - BUFFER_X, 0, 15, Element.ALIGN_RIGHT);		ct3.go();
		}
	}
	
	/**
	 * This class will create and properly format one or two glaze recipe's onto a single sheet
	 */
	private class GlazePage 
	{
		private PdfWriter writer;
		private Document document;
		
		private final int PAGE_WIDTH = 595;							// The width of the Page in pixels
		private final int TOP_RECIPE_OFFSET = -15; 					// Offset of the top recipe 
		private final int BOT_RECIPE_OFFSET = -385; 				// Offset of bot recipe, -370 corresponds to 1/2 page
		
		private final int GLAZE_PHOTO_WIDTH = 80; 					// Width of the photo in pixels
		private final int GLAZE_PHOTO_HEIGHT = 100; 				// Height of the photo in pixels
		
		private final int TITLE_START_Y = 780;						// Starting position of the Glaze Title
		private final int TITLE_LINE_SPACING = 13;					// Distance between bottom of photo and line below description
		private final int PHOTO_BUFFER_X = 80;						// Distance from the left side to first photo
		private final int PHOTO_SPACING = 20;						// Distance between the photos
		private final int PHOTO_START_Y = 655;						// Starting position of the first row of photos
		private final int PHOTO_2_START_Y = 630;					// Starting position of the second row of photos
		private final int PHOTO_DESC_LINE_SPACING = 13;				// Distance between bottom of photo and line below description
		private final int PHOTO_DESC_SPACING = 5;					// Distance between bottom of photo and description
		private final int PHOTO_TO_FORMULA = 70;					// Distance between the photos and the formula
		private final int COMP_TO_AMT = 160;						// Distance between the ingr name and its amount
		private final int FORMULA_TITLE_START = 725; 				// Starting position of the formula title
		private final int GLAZE_INFO_START = 765;					// Starting position of the Glaze Info 
		private final int FORMULA_TITLE_TO_COMP = 25;				// Distance from the Formula title to the components
		private final int FORMULA_TITLE_TO_LINE = 20;				// Distance from the Formula title to the line below it
		private final int COMMENTS_BUFFER = PHOTO_BUFFER_X + 20;	// Distance from the left side to comments block
		private final int COMMENTS_START = 513;						// Starting Position of the Comments block
		private final int COMMENTS_TITLE_TO_LINE = 20;				// Distance from Comments title to the line under it
		private final int COMMENTS_TITLE_TO_TEXT = 23;				// Distance from the Comments title to the comments
		
		private Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.NORMAL);
		private Font formulaFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
		private Font formulaTitleFont = new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL);
		private Font photoDescFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
		private Font commentsTitleFont = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
		private Font commentsFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
		
		public GlazePage(Document document, PdfWriter writer, GlazeRecipe topRecipe, GlazeRecipe botRecipe) throws Exception
		{
			this.writer = writer;
			this.document = document;
			
			addTitleAndImages(topRecipe, TOP_RECIPE_OFFSET);
			addGlazeRecipe(topRecipe, TOP_RECIPE_OFFSET);
			addComments(topRecipe, TOP_RECIPE_OFFSET);
			
			addTitleAndImages(botRecipe, BOT_RECIPE_OFFSET);
			addGlazeRecipe(botRecipe, BOT_RECIPE_OFFSET);
			addComments(botRecipe, BOT_RECIPE_OFFSET);
		}
		public GlazePage(Document document, PdfWriter writer, GlazeRecipe topRecipe) throws Exception
		{
			this.writer = writer;
			this.document = document;
			
			addTitleAndImages(topRecipe, TOP_RECIPE_OFFSET);
			addGlazeRecipe(topRecipe, TOP_RECIPE_OFFSET);
			addComments(topRecipe, TOP_RECIPE_OFFSET);
		}
		
		public void addComments(GlazeRecipe recipe, int offset) throws Exception
		{
			PdfContentByte cb = writer.getDirectContent();
			ColumnText ct1 = new ColumnText(cb);
			ColumnText ct2 = new ColumnText(cb);
			
			Phrase commentsTitle = new Phrase();	commentsTitle.setFont(commentsTitleFont);
			Phrase comments = new Phrase();			comments.setFont(commentsFont);
			
			Chunk commentsChunk = new Chunk(recipe.getComments());
			commentsChunk.setLineHeight(9); // This should be the same as commentsFont's size + 1!
			
			commentsTitle.add(new Chunk("COMMENTS"));
			comments.add(commentsChunk);
						
			ct1.setSimpleColumn(commentsTitle, COMMENTS_BUFFER, COMMENTS_START + offset, PAGE_WIDTH - COMMENTS_BUFFER, 0, 15, Element.ALIGN_CENTER);
			ct1.go();
			LineSeparator ls = new LineSeparator();
			ls.drawLine(cb, COMMENTS_BUFFER, PAGE_WIDTH - COMMENTS_BUFFER, COMMENTS_START + offset - COMMENTS_TITLE_TO_LINE);
			ct2.setSimpleColumn(comments, COMMENTS_BUFFER, COMMENTS_START + offset - COMMENTS_TITLE_TO_TEXT, PAGE_WIDTH - COMMENTS_BUFFER, 0, 15, Element.ALIGN_LEFT);
			ct2.go();
		}
		
		public void addGlazeRecipe(GlazeRecipe recipe, int offset) throws Exception
		{
			GlazeComponent[] sortedComps = sortComponents(recipe);
			
			PdfContentByte cb = writer.getDirectContent();
			ColumnText ct1 = new ColumnText(cb);
			ColumnText ct2 = new ColumnText(cb);
			ColumnText ct3 = new ColumnText(cb);
			ColumnText ct4 = new ColumnText(cb);
		
			Phrase glazeInfo = new Phrase();
			glazeInfo.setFont(formulaFont);
			glazeInfo.add(new Chunk("Cone Range: " + parseConeRange(recipe).trim() + "\nAtmosphere: " + parseAtmoshpere(recipe).trim()));
			
			Phrase formulaTitle = new Phrase();
			formulaTitle.setFont(formulaTitleFont);
			formulaTitle.add(new Chunk("FORMULA\n"));
			LineSeparator ls = new LineSeparator();
			ls.drawLine(cb, PHOTO_BUFFER_X + 2*GLAZE_PHOTO_WIDTH + PHOTO_TO_FORMULA, PAGE_WIDTH - PHOTO_BUFFER_X, FORMULA_TITLE_START + offset - FORMULA_TITLE_TO_LINE);
			
			Phrase comps = new Phrase();
			comps.setFont(formulaFont);
			comps.add(new Chunk(parseIngredientNames(sortedComps) + "\n\nTotal"));

			Phrase amts = new Phrase();
			amts.setFont(formulaFont);
			amts.add(new Chunk(parseIngredientAmts(sortedComps) + "\n\n" + round(total(sortedComps),2)));

			ct1.setSimpleColumn(comps, PHOTO_BUFFER_X + 2*GLAZE_PHOTO_WIDTH + PHOTO_TO_FORMULA, FORMULA_TITLE_START + offset - FORMULA_TITLE_TO_COMP, PAGE_WIDTH, 0, 15, Element.ALIGN_LEFT);
			ct2.setSimpleColumn(amts,  PHOTO_BUFFER_X + 2*GLAZE_PHOTO_WIDTH + PHOTO_TO_FORMULA + COMP_TO_AMT, FORMULA_TITLE_START + offset - FORMULA_TITLE_TO_COMP, PAGE_WIDTH - PHOTO_BUFFER_X, 0, 15, Element.ALIGN_RIGHT);
			ct3.setSimpleColumn(formulaTitle, PHOTO_BUFFER_X + 2*GLAZE_PHOTO_WIDTH + PHOTO_TO_FORMULA, FORMULA_TITLE_START + offset, PAGE_WIDTH - PHOTO_BUFFER_X, 0, 15, Element.ALIGN_CENTER);
			ct4.setSimpleColumn(glazeInfo, PHOTO_BUFFER_X + 2*GLAZE_PHOTO_WIDTH + PHOTO_TO_FORMULA, GLAZE_INFO_START + offset, PAGE_WIDTH, 0, 15, Element.ALIGN_LEFT);
			ct1.go();
			ct2.go();
			ct3.go();
			ct4.go();
		}
		private double round(double value, int places) {
		    if (places < 0) throw new IllegalArgumentException();

		    long factor = (long) Math.pow(10, places);
		    value = value * factor;
		    long tmp = Math.round(value);
		    return (double) tmp / factor;
		}
		private double total(GlazeComponent[] comps)
		{
			double sum = 0;
			for(int k = 0; k < comps.length; k++) {sum += comps[k].getAmount();}
			return sum;
		}
		private GlazeComponent[] sortComponents(GlazeRecipe recipe)
		{
			GlazeComponent[] ingr = recipe.getComponents();
			GlazeComponent[] adds = recipe.getAdds();
			GlazeComponent[] all;
			
			if(ingr == null && adds != null) {
				all = new GlazeComponent[adds.length];
				for(int k = 0; k < adds.length; k++)
				{
					for(int g = 0; g < all.length; g++)
					{
						if(all[g] == null){
							all[g] = adds[k];
							break;
						} else if(adds[k].getAmount() > all[g].getAmount()) {
							for(int f = all.length - 1; f > g; f--)
							{
								all[f] = all[f - 1];
							}
							all[g] = adds[k];
							break;
						}
					}
				}
			} else if (adds == null && ingr != null) {
				all = new GlazeComponent[ingr.length];
				for(int k = 0; k < ingr.length; k++)
				{
					for(int g = 0; g < all.length; g++)
					{
						if(all[g] == null){
							all[g] = ingr[k];
							break;
						} else if(ingr[k].getAmount() > all[g].getAmount()) {
							//insert the item here
							for(int f = all.length - 1; f > g; f--)
							{
								all[f] = all[f - 1];
							}
							all[g] = ingr[k];
							break;
						}
					}
				}
			} else {
				all = new GlazeComponent[ingr.length + adds.length];
				for(int k = 0; k < ingr.length; k++)
				{
					for(int g = 0; g < all.length; g++)
					{
						if(all[g] == null){
							all[g] = ingr[k];
							break;
						} else if(ingr[k].getAmount() > all[g].getAmount()) {
							//insert the item here
							for(int f = all.length - 1; f > g; f--)
							{
								all[f] = all[f - 1];
							}
							all[g] = ingr[k];
							break;
						}
					}
				}
				for(int k = 0; k < adds.length; k++)
				{
					for(int g = 0; g < all.length; g++)
					{
						if(all[g] == null){
							all[g] = adds[k];
							break;
						} else if(adds[k].getAmount() > all[g].getAmount()) {
							//insert the item here
							for(int f = all.length - 1; f > g; f--)
							{
								all[f] = all[f - 1];
							}
							all[g] = adds[k];
							break;
						}
					}
				}
			}
			return all;
		}
		private String parseIngredientNames(GlazeComponent[] comps)
		{
			if(comps == null)
			{
				return "No Ingredients Listed";
			}
			String names = "";
			for(int k=0; k< comps.length; k++)
			{
				names += comps[k].getName().trim() + "\n";
			}
			return names.trim();
		}
		private String parseIngredientAmts(GlazeComponent[] comps)
		{
			if(comps == null)
			{
				return "0.0";
			}
			String amts = "";
			for(int k=0; k< comps.length; k++)
			{
				amts += round(comps[k].getAmount(),2) + "\n";
			}
			return amts.trim();
		}
		private String parseConeRange(GlazeRecipe recipe) 
		{ 
			String lower = recipe.getLowerCone().trim();
			String upper = recipe.getUpperCone().trim();
			
			if(!lower.equals(upper)) { return lower + " - " + upper; } // different cones
			else {return lower;}
		}
		private String parseAtmoshpere(GlazeRecipe recipe)
		{
			String atms = "";
			String[] firings = recipe.getFiringAttribute();
			
			if(firings.length > 2) {
				for(int k = 0; k < firings.length - 1; k++) { 
					atms += firings[k].trim() + ", "; 
				}
				atms += "& " + firings[firings.length - 1].trim();
			} else {
				if(firings.length == 1) {
					if(firings[0].trim().contains("Ox.")) {
						atms = "Oxidation" ;
					}
				} else {
					if(firings[0].contains("Ox.")) {
						atms = "Oxidation" ;
					} else if(firings[0].contains("Red.")) {
						atms = "Reduction";
					} else {
						atms = firings[0].trim();
					}
					
					if(firings[1].contains("Ox.")) {
						atms += " & Oxidation" ;
					} else if(firings[1].contains("Red.")) {
						atms += " & Reduction";
					} else {
						atms += " & " + firings[1].trim();
					}
				}
				
			}
			return atms;
		}
		
		public void addTitleAndImages(GlazeRecipe recipe, int offset) throws Exception
		{
			PdfContentByte cb = writer.getDirectContent();
			ColumnText ct1 = new ColumnText(cb); // Photo 1 Desc
			ColumnText ct2 = new ColumnText(cb); // Photo 2 Desc
			ColumnText ct3 = new ColumnText(cb); // Photo 3 Desc
			ColumnText ct4 = new ColumnText(cb); // Photo 4 Desc
			ColumnText ct5 = new ColumnText(cb); // Glaze Title
			
			Phrase glazeTitle = new Phrase();		glazeTitle.setFont(titleFont);
			Phrase photo1Desc = new Phrase(); 		photo1Desc.setFont(photoDescFont);
			Phrase photo2Desc = new Phrase();		photo2Desc.setFont(photoDescFont);
			Phrase photo3Desc = new Phrase();		photo3Desc.setFont(photoDescFont);
			Phrase photo4Desc = new Phrase();		photo4Desc.setFont(photoDescFont);
			
			glazeTitle.add(new Chunk(recipe.getName().toUpperCase()));
			LineSeparator ls5 = new LineSeparator();
			ls5.drawLine(cb, PHOTO_BUFFER_X, PAGE_WIDTH - PHOTO_BUFFER_X, TITLE_START_Y + offset - TITLE_LINE_SPACING);
		    ct5.setSimpleColumn(glazeTitle, PHOTO_BUFFER_X, TITLE_START_Y + offset + TITLE_LINE_SPACING, PAGE_WIDTH - PHOTO_BUFFER_X, 0, 15, Element.ALIGN_CENTER);
		    ct5.go();
			
			GlazePhoto[] photos = recipe.getPhotos();
			if(photos.length >= 4) // Use the top 4
			{
				photo1Desc.add(new Chunk(photos[0].getDesc()));
				photo2Desc.add(new Chunk(photos[1].getDesc()));
				photo3Desc.add(new Chunk(photos[2].getDesc()));
				photo4Desc.add(new Chunk(photos[3].getDesc()));
				
				Image image1 = Image.getInstance(cb, photos[0].getPhoto(), 1);
			    image1.scaleAbsolute(GLAZE_PHOTO_WIDTH, GLAZE_PHOTO_HEIGHT);
			    image1.setAbsolutePosition(PHOTO_BUFFER_X, PHOTO_START_Y + offset);
			    LineSeparator ls1 = new LineSeparator();
				ls1.drawLine(cb, PHOTO_BUFFER_X, PHOTO_BUFFER_X + GLAZE_PHOTO_WIDTH, PHOTO_START_Y + offset - PHOTO_DESC_LINE_SPACING);
			    ct1.setSimpleColumn(photo1Desc, PHOTO_BUFFER_X, PHOTO_START_Y + offset + PHOTO_DESC_SPACING, PHOTO_BUFFER_X + GLAZE_PHOTO_WIDTH, 0, 15, Element.ALIGN_CENTER);
			    ct1.go();
			    
			    Image image2 = Image.getInstance(cb, photos[1].getPhoto(), 1);
			    image2.scaleAbsolute(GLAZE_PHOTO_WIDTH, GLAZE_PHOTO_HEIGHT);
			    image2.setAbsolutePosition(PHOTO_BUFFER_X + GLAZE_PHOTO_WIDTH + PHOTO_SPACING, PHOTO_START_Y + offset);
			    LineSeparator ls2 = new LineSeparator();
				ls2.drawLine(cb, PHOTO_BUFFER_X + PHOTO_SPACING + GLAZE_PHOTO_WIDTH, PHOTO_BUFFER_X + PHOTO_SPACING + 2*GLAZE_PHOTO_WIDTH, PHOTO_START_Y + offset - PHOTO_DESC_LINE_SPACING);
			    ct2.setSimpleColumn(photo2Desc, PHOTO_BUFFER_X + PHOTO_SPACING + GLAZE_PHOTO_WIDTH, PHOTO_START_Y + offset + PHOTO_DESC_SPACING, PHOTO_BUFFER_X + PHOTO_SPACING + 2*GLAZE_PHOTO_WIDTH, 0, 15, Element.ALIGN_CENTER);
			    ct2.go();
			    
			    Image image3 = Image.getInstance(cb, photos[2].getPhoto(), 1);
			    image3.scaleAbsolute(GLAZE_PHOTO_WIDTH, GLAZE_PHOTO_HEIGHT);
			    image3.setAbsolutePosition(PHOTO_BUFFER_X, PHOTO_2_START_Y - GLAZE_PHOTO_HEIGHT + offset);
			    LineSeparator ls3 = new LineSeparator();
				ls3.drawLine(cb, PHOTO_BUFFER_X, PHOTO_BUFFER_X + GLAZE_PHOTO_WIDTH, PHOTO_2_START_Y + offset - PHOTO_DESC_LINE_SPACING - GLAZE_PHOTO_HEIGHT);
			    ct3.setSimpleColumn(photo3Desc, PHOTO_BUFFER_X, PHOTO_2_START_Y + offset + PHOTO_DESC_SPACING - GLAZE_PHOTO_HEIGHT, PHOTO_BUFFER_X + GLAZE_PHOTO_WIDTH, 0, 15, Element.ALIGN_CENTER);
			    ct3.go();
			    
			    Image image4 = Image.getInstance(cb, photos[3].getPhoto(), 1);
			    image4.scaleAbsolute(GLAZE_PHOTO_WIDTH, GLAZE_PHOTO_HEIGHT);
			    image4.setAbsolutePosition(PHOTO_BUFFER_X + GLAZE_PHOTO_WIDTH + PHOTO_SPACING,  PHOTO_2_START_Y + offset - GLAZE_PHOTO_HEIGHT);
			    LineSeparator ls4 = new LineSeparator();
				ls4.drawLine(cb, PHOTO_BUFFER_X + PHOTO_SPACING + GLAZE_PHOTO_WIDTH, PHOTO_BUFFER_X + PHOTO_SPACING + 2*GLAZE_PHOTO_WIDTH, PHOTO_2_START_Y + offset - PHOTO_DESC_LINE_SPACING - GLAZE_PHOTO_HEIGHT);
			    ct4.setSimpleColumn(photo4Desc, PHOTO_BUFFER_X + PHOTO_SPACING + GLAZE_PHOTO_WIDTH, PHOTO_2_START_Y + offset + PHOTO_DESC_SPACING - GLAZE_PHOTO_HEIGHT, PHOTO_BUFFER_X + PHOTO_SPACING + 2*GLAZE_PHOTO_WIDTH, 0, 15, Element.ALIGN_CENTER);
			    ct4.go();
			    
			    document.add(image1);
			    document.add(image2);
			    document.add(image3);
			    document.add(image4);
			} else if (photos.length == 3) { // 3 Images
				photo1Desc.add(new Chunk(photos[0].getDesc()));
				photo2Desc.add(new Chunk(photos[1].getDesc()));
				photo3Desc.add(new Chunk(photos[2].getDesc()));
				
				Image image1 = Image.getInstance(cb, photos[0].getPhoto(), 1);
			    image1.scaleAbsolute(GLAZE_PHOTO_WIDTH, GLAZE_PHOTO_HEIGHT);
			    image1.setAbsolutePosition(PHOTO_BUFFER_X, PHOTO_START_Y + offset);
			    LineSeparator ls1 = new LineSeparator();
				ls1.drawLine(cb, PHOTO_BUFFER_X, PHOTO_BUFFER_X + GLAZE_PHOTO_WIDTH, PHOTO_START_Y + offset - PHOTO_DESC_LINE_SPACING);
			    ct1.setSimpleColumn(photo1Desc, PHOTO_BUFFER_X, PHOTO_START_Y + offset + PHOTO_DESC_SPACING, PHOTO_BUFFER_X + GLAZE_PHOTO_WIDTH, 0, 15, Element.ALIGN_CENTER);
			    ct1.go();
			    
			    Image image2 = Image.getInstance(cb, photos[1].getPhoto(), 1);
			    image2.scaleAbsolute(GLAZE_PHOTO_WIDTH, GLAZE_PHOTO_HEIGHT);
			    image2.setAbsolutePosition(PHOTO_BUFFER_X + GLAZE_PHOTO_WIDTH + PHOTO_SPACING, PHOTO_START_Y + offset);
			    LineSeparator ls2 = new LineSeparator();
				ls2.drawLine(cb, PHOTO_BUFFER_X + PHOTO_SPACING + GLAZE_PHOTO_WIDTH, PHOTO_BUFFER_X + PHOTO_SPACING + 2*GLAZE_PHOTO_WIDTH, PHOTO_START_Y + offset - PHOTO_DESC_LINE_SPACING);
			    ct2.setSimpleColumn(photo2Desc, PHOTO_BUFFER_X + PHOTO_SPACING + GLAZE_PHOTO_WIDTH, PHOTO_START_Y + offset + PHOTO_DESC_SPACING, PHOTO_BUFFER_X + PHOTO_SPACING + 2*GLAZE_PHOTO_WIDTH, 0, 15, Element.ALIGN_CENTER);
			    ct2.go();
			    
			    Image image3 = Image.getInstance(cb, photos[2].getPhoto(), 1);
			    image3.scaleAbsolute(GLAZE_PHOTO_WIDTH, GLAZE_PHOTO_HEIGHT);
			    image3.setAbsolutePosition(PHOTO_BUFFER_X + (GLAZE_PHOTO_WIDTH + PHOTO_SPACING)/2, PHOTO_2_START_Y - GLAZE_PHOTO_HEIGHT + offset);
			    LineSeparator ls3 = new LineSeparator();
				ls3.drawLine(cb, PHOTO_BUFFER_X + (GLAZE_PHOTO_WIDTH + PHOTO_SPACING)/2, PHOTO_BUFFER_X + GLAZE_PHOTO_WIDTH + (GLAZE_PHOTO_WIDTH + PHOTO_SPACING)/2, PHOTO_2_START_Y + offset - PHOTO_DESC_LINE_SPACING - GLAZE_PHOTO_HEIGHT);
			    ct3.setSimpleColumn(photo3Desc, PHOTO_BUFFER_X + (GLAZE_PHOTO_WIDTH + PHOTO_SPACING)/2, PHOTO_2_START_Y + offset + PHOTO_DESC_SPACING - GLAZE_PHOTO_HEIGHT, PHOTO_BUFFER_X + GLAZE_PHOTO_WIDTH + (GLAZE_PHOTO_WIDTH + PHOTO_SPACING)/2, 0, 15, Element.ALIGN_CENTER);
			    ct3.go();
			    
			    document.add(image1);
			    document.add(image2);
			    document.add(image3);
			} else if (photos.length == 2) { // 2 Images
				photo1Desc.add(new Chunk(photos[0].getDesc()));
				photo2Desc.add(new Chunk(photos[1].getDesc()));
				
				Image image1 = Image.getInstance(cb, photos[0].getPhoto(), 1);
			    image1.scaleAbsolute(GLAZE_PHOTO_WIDTH, GLAZE_PHOTO_HEIGHT);
			    image1.setAbsolutePosition(PHOTO_BUFFER_X, PHOTO_START_Y + offset);
			    LineSeparator ls1 = new LineSeparator();
				ls1.drawLine(cb, PHOTO_BUFFER_X, PHOTO_BUFFER_X + GLAZE_PHOTO_WIDTH, PHOTO_START_Y + offset - PHOTO_DESC_LINE_SPACING);
			    ct1.setSimpleColumn(photo1Desc, PHOTO_BUFFER_X, PHOTO_START_Y + offset + PHOTO_DESC_SPACING, PHOTO_BUFFER_X + GLAZE_PHOTO_WIDTH, 0, 15, Element.ALIGN_CENTER);
			    ct1.go();
			    
			    Image image2 = Image.getInstance(cb, photos[1].getPhoto(), 1);
			    image2.scaleAbsolute(GLAZE_PHOTO_WIDTH, GLAZE_PHOTO_HEIGHT);
			    image2.setAbsolutePosition(PHOTO_BUFFER_X + GLAZE_PHOTO_WIDTH + PHOTO_SPACING, PHOTO_START_Y + offset);
			    LineSeparator ls2 = new LineSeparator();
				ls2.drawLine(cb, PHOTO_BUFFER_X + PHOTO_SPACING + GLAZE_PHOTO_WIDTH, PHOTO_BUFFER_X + PHOTO_SPACING + 2*GLAZE_PHOTO_WIDTH, PHOTO_START_Y + offset - PHOTO_DESC_LINE_SPACING);
			    ct2.setSimpleColumn(photo2Desc, PHOTO_BUFFER_X + PHOTO_SPACING + GLAZE_PHOTO_WIDTH, PHOTO_START_Y + offset + PHOTO_DESC_SPACING, PHOTO_BUFFER_X + PHOTO_SPACING + 2*GLAZE_PHOTO_WIDTH, 0, 15, Element.ALIGN_CENTER);
			    ct2.go();
			    
			    document.add(image1);
			    document.add(image2);
			} else if (photos.length == 1) { // 1 Image
				photo1Desc.add(new Chunk(photos[0].getDesc()));

				Image image1 = Image.getInstance(cb, photos[0].getPhoto(), 1);
			    image1.scaleAbsolute(GLAZE_PHOTO_WIDTH, GLAZE_PHOTO_HEIGHT);
			    image1.setAbsolutePosition(PHOTO_BUFFER_X + (GLAZE_PHOTO_WIDTH + PHOTO_SPACING)/2, PHOTO_START_Y + offset);
			    LineSeparator ls1 = new LineSeparator();
				ls1.drawLine(cb, PHOTO_BUFFER_X + (GLAZE_PHOTO_WIDTH + PHOTO_SPACING)/2, PHOTO_BUFFER_X + GLAZE_PHOTO_WIDTH + (GLAZE_PHOTO_WIDTH + PHOTO_SPACING)/2, PHOTO_START_Y + offset - PHOTO_DESC_LINE_SPACING);
			    ct1.setSimpleColumn(photo1Desc, PHOTO_BUFFER_X + (GLAZE_PHOTO_WIDTH + PHOTO_SPACING)/2, PHOTO_START_Y + offset + PHOTO_DESC_SPACING, PHOTO_BUFFER_X + GLAZE_PHOTO_WIDTH + (GLAZE_PHOTO_WIDTH + PHOTO_SPACING)/2, 0, 15, Element.ALIGN_CENTER);
			    ct1.go();
			    
			    document.add(image1);
			} else { // No Images
				
			}
		}
	}
	
}
