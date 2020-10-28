package com.flplucio.cordova.plugin;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Set;
import java.util.List;

public class PdfBoxPlugin extends CordovaPlugin {

    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        // add_image
        if (action.equals("add_image")) {

            try {
                
                //Here goes the code that will merge the client sign image with the VV PDF

                callbackContext.success(pdfout_base64);

            } catch (Exception e) {
                

                callbackContext.error(e.toString());
            }

            return true;
        }
        
        // get_position
        else if (action.equals("get_position")) {

            try {

                //Here goes the code that will get the image position

                callbackContext.success("{\"posX\" : \"" + posX + "\", \"posY\" : \"" + posY + "\"}");

            } catch (Exception e) {
                callbackContext.error(e.toString());
            }

            return true;
        } else {

            return false;

        }
    }

}