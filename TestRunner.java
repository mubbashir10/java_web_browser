//importing libraries
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

//test runner class
public class TestRunner {
	
	//main methd	
	public static void main(String[] args) {

		//http test
		Result resultA = JUnitCore.runClasses(HTTPTest.class);
		for (Failure failureA : resultA.getFailures())
			System.out.println(failureA.toString());
		System.out.println("HTTP Request Test: "+resultA.wasSuccessful());


	}	
}  	