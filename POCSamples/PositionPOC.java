import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

public class PositionPOC {
	
	public PDRectangle getFieldArea() throws IOException {
		
		String filePath = "C:\\Users\\Aubay\\eclipse-workspace\\PDFBoxPOC\\Document_GetPdf.pdf";
		String base64File = "";
		File myimage = new File(filePath);
		FileInputStream imageInFile = new FileInputStream(myimage); 
        // Reading a file from file system
        byte fileData[] = new byte[(int) myimage.length()];
        imageInFile.read(fileData);
        base64File = Base64.getEncoder().encodeToString(fileData);
        imageInFile.close();
		
	    PDDocument pdfDocument = PDDocument.load((Base64.getDecoder().decode(base64File)));
	    PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
	    PDAcroForm acroForm = docCatalog.getAcroForm();
	    
	    PDField f = acroForm.getFields().get(0);
	   
        PDRectangle r = f.getWidgets().get(0).getRectangle();
 
	    return r;
	}

}
