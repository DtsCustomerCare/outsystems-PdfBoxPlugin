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
		        addImageToPDF(rectangle.getLowerLeftX(), rectangle.getUpperRightY(), rectangle.getWidth(), rectangle.getHeight());

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

                // String filePath = "C:\\Users\\Aubay\\eclipse-workspace\\PDFBoxPOC\\Document_GetPdf.pdf";
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

                callbackContext.success("{\"posX\" : \"" + r.getLowerLeftX() + "\", \"posY\" : \"" + r.getLowerLeftY() + "\"}");

            } catch (Exception e) {
                callbackContext.error(e.toString());
            }

            return true;
        } else {

            return false;

        }
    }

}