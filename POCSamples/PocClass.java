import java.awt.Color;
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PocClass {

	public static void main(String[] args) throws IOException {
				PDRectangle rectangle = getPosition();
		addImageToPDF(rectangle.getLowerLeftX(), rectangle.getUpperRightY(), rectangle.getWidth(), rectangle.getHeight());
	}

	private static PDRectangle getPosition() throws IOException {
		PositionPOC positionPoc = new PositionPOC();
		PDRectangle rectangle = positionPoc.getFieldArea();
		return rectangle;
		//System.out.println(" :LowerLeftX[" + rectangle.getLowerLeftX() + "] LowerLeftY[" + rectangle.getLowerLeftY() + "] UpperRightX[" + rectangle.getUpperRightX() + "] UpperRightY[" + rectangle.getUpperRightY() + "]\n\n");
		/*String formTemplate = "C:\\Users\\Aubay\\eclipse-workspace\\PDFBoxPOC\\Document_GetPdf.pdf";
		PDDocument pdfDocument = PDDocument.load(new File(formTemplate));
		PDPage page = pdfDocument.getPage(0);
		PDPageContentStream pageContentStream = new PDPageContentStream(pdfDocument, page, AppendMode.APPEND, true, true);
		printRect(pageContentStream, rectangle);
		pdfDocument.save("C:\\Users\\Aubay\\eclipse-workspace\\PDFBoxPOC\\Document_GetPdf_WithLines.pdf");
		
		System.out.println("RectangleWasDraw");*/
	}
	
	@SuppressWarnings("unused")
	private static void printRect(final PDPageContentStream contentStream, final PDRectangle rect) throws IOException {
		  contentStream.setStrokingColor(Color.YELLOW);
		  contentStream.drawLine(rect.getLowerLeftX(), rect.getLowerLeftY(), rect.getLowerLeftX(), rect.getUpperRightY()); // left
		  contentStream.drawLine(rect.getLowerLeftX(), rect.getUpperRightY(), rect.getUpperRightX(), rect.getUpperRightY()); // top
		  contentStream.drawLine(rect.getUpperRightX(), rect.getLowerLeftY(), rect.getUpperRightX(), rect.getUpperRightY()); // right
		  contentStream.drawLine(rect.getLowerLeftX(), rect.getLowerLeftY(), rect.getUpperRightX(), rect.getLowerLeftY()); // bottom
		  contentStream.setStrokingColor(Color.BLACK);
		  contentStream.close();
		}

	@SuppressWarnings("unused")
	private static void createNewPDF() throws IOException {
		String filename = "MyPDF.pdf";
		String message = "Hello World!";
		
		try (PDDocument doc = new PDDocument())
		{
		    PDPage page = new PDPage();
		    doc.addPage(page);
		    
		    PDFont font = PDType1Font.HELVETICA;

		    try (PDPageContentStream contents = new PDPageContentStream(doc, page))
		    {
		        contents.beginText();
		        contents.setFont(font, 12);
		        contents.newLineAtOffset(100, 700);
		        contents.showText(message);
		        contents.endText();
		    }
		
		    doc.save(filename);
		    System.out.println("File " + filename + " created at: " + System.getProperty("user.dir"));
		};
	}
    
    @SuppressWarnings("unused")
	private static void addImageToPDF(float posX, float posY, float width, float height) throws IOException
    {
    	MergeImageAndPDF mergeclass = new MergeImageAndPDF();
    	mergeclass.createPDFFromImage( "C:\\Users\\Aubay\\eclipse-workspace\\PDFBoxPOC\\Document_GetPdf.pdf", "C:\\Users\\Aubay\\Pictures\\MyImages\\rubik.jpg", "C:\\Users\\Aubay\\eclipse-workspace\\PDFBoxPOC\\Document_GetPdf_WithImage.pdf", posX, posY, width, height);
    	System.out.println("File created at: " + System.getProperty("user.dir"));
    }

}
