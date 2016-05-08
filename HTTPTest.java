//importing libraries
import org.junit.Test;
import static org.junit.Assert.*;

//tTest class
public class HTTPTest {

    @Test
    public void singleTest() {

        //making object
        JavaWebBrowserHyperlinkHandler gui = new JavaWebBrowserHyperlinkHandler();

        //input
        String address = "http://google.com";

        //result
        boolean result = true;

        //testing
        assertEquals(result, gui.HTTPGETRequest(address));

    }
}