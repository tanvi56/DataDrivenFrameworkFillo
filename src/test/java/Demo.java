import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.baseClass.BaseClass;
import com.filereader.ReadExcelFile;

public class Demo extends BaseClass{
	
	@BeforeClass
	public void beforeclass() throws Throwable {
		invokeBrowser("chrome");
		openUrl("webURL");
	}
	
	@Test
	public void demo1() throws Throwable
	{
		logger = report.createTest("Booking.com Home");
		map = ReadExcelFile.readData("Home", "HappyPath");
		Thread.sleep(1000);
		getElement("place_Xpath").sendKeys(map.get("place"));	
	}
}
