package com.extentReports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
public static ExtentReports report;
	
	
	public static ExtentReports getReportInstance() {
		if(report ==null) {
			String reportName = DateUtils.getTimeStamp();
			report = new ExtentReports();
			ExtentSparkReporter spark = new ExtentSparkReporter("target/"+reportName+".html");
			spark.config().setTheme(Theme.DARK);
			spark.config().setDocumentTitle("Automation Report");
			spark.config().setReportName("Booking.com");
			report.attachReporter(spark);
		}
		
	
		return report;
	}
}
