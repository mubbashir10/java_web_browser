import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;

import java.io.*;
import java.net.URL;

import org.fit.cssbox.swingbox.util.DefaultHyperlinkHandler;


public class JavaWebBrowserHyperlinkHandler extends DefaultHyperlinkHandler
{
    public static Gui browser;
    
    public JavaWebBrowserHyperlinkHandler(Gui browser){

        //assigning to current object
        this.browser = browser;
        
    }

    public JavaWebBrowserHyperlinkHandler(){


    }

    @Override
    protected void loadPage(JEditorPane pane, HyperlinkEvent evt){

        //creating new object with alternative contructor
        Gui b = new Gui(evt.getURL().toString());
    }

    //making HTTP request
    public boolean HTTPGETRequest(String address){

        boolean responseStatus = true;

        try{
            //making request
            URL url = new URL(address);
            responseStatus = true;
        }
        catch (Exception e) {
            System.out.println("The requested resource wasn't found!");
            responseStatus = false;
        }

        return responseStatus;
    }

}
