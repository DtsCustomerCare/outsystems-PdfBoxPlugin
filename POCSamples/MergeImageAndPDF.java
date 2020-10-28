import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class MergeImageAndPDF {
	
    public void createPDFFromImage( String inputFile, String imagePath, String outputFile , float posX, float posY, float width, float height) throws IOException {
        try (PDDocument doc = PDDocument.load(new File(inputFile)))
        {
            PDPage page = doc.getPage(0);

            PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, doc);

            try (PDPageContentStream contentStream = new PDPageContentStream(doc, page, AppendMode.APPEND, true, true))
            {
                // contentStream.drawImage(ximage, 20, 20 );
                // better method inspired by http://stackoverflow.com/a/22318681/535646
                // reduce this value if the image is too large
                float scale = 1f;
                //contentStream.drawImage(pdImage, posX, posY, pdImage.getWidth() * scale, pdImage.getHeight() * scale);
                contentStream.drawImage(pdImage, posX, posY, width * scale, height* scale);
            }
            doc.save(outputFile);
        }
    }
}
