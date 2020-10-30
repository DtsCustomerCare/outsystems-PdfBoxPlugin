package com.flplucio.cordova.plugin;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.ByteArrayOutputStream;
import java.util.Set;
import java.util.List;

public class PdfBoxPlugin extends CordovaPlugin {

    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        // add_image
        if (action.equals("add_image")) {

            String pdf_base64 = data.getString(0);
            String png_base64 = data.getString(1);

            try {
                
                PDRectangle rectangle = getPosition();


                try (PDDocument doc = PDDocument.load(Base64.getDecoder().decode(base64File)))
                {
                    PDPage page = doc.getPage(0);

                    PDImageXObject pdImage = PDImageXObject.createFromFile(png_base64, doc);

                    try (PDPageContentStream contentStream = new PDPageContentStream(doc, page, AppendMode.APPEND, true, true))
                    {
                        // contentStream.drawImage(ximage, 20, 20 );
                        // better method inspired by http://stackoverflow.com/a/22318681/535646
                        // reduce this value if the image is too large
                        float scale = 1f;
                        //contentStream.drawImage(pdImage, posX, posY, pdImage.getWidth() * scale, pdImage.getHeight() * scale);
                        contentStream.drawImage(png_base64, rectangle.getLowerLeftX(), rectangle.getUpperRightY(), width * scale, height* scale);
                    }
                    // doc.save(outputFile);
                }


                String pdfout_base64 = Base64.encodeBytes(pdf_out.toByteArray());
                callbackContext.success(pdfout_base64);

            } catch (Exception e) {
                

                callbackContext.error(e.toString());
            }

            return true;
        }
        
        // get_position
        else if (action.equals("get_position")) {

            String base64File = data.getString(0);

            try {

                /* The next part of the code was used in the POC to Get a PDF File and then convert it to Base64, 
                although unecessary i wrote the code like this in order to make sure that it would work 
                on the real use case, wich is with a base64 that comes from the tablet app.*/

                // String filePath = "C:\\Users\\MyUser\\eclipse-workspace\\PDFBoxPOC\\Document_GetPdf.pdf";
                // String base64File = "";
                // File myimage = new File(filePath);
                // FileInputStream imageInFile = new FileInputStream(myimage); 
                // // Reading a file from file system
                // byte fileData[] = new byte[(int) myimage.length()];
                // imageInFile.read(fileData);
                // base64File = Base64.getEncoder().encodeToString(fileData);
                // imageInFile.close();
                
                PDDocument pdfDocument = PDDocument.load((Base64.getDecoder().decode(base64File)));
                PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
                PDAcroForm acroForm = docCatalog.getAcroForm();
                
                PDField f = acroForm.getFields().get(0);
            
                PDRectangle r = f.getWidgets().get(0).getRectangle();

                // return r;

                callbackContext.success("{\"posX\" : \"" + r.getLowerLeftX() + "\", \"posY\" : \"" + r.getUpperRightY() + "\"}");

            } catch (Exception e) {
                callbackContext.error(e.toString());
            }

            return true;
        } else {

            return false;

        }
    }

}