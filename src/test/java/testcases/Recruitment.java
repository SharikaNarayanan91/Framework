package testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BaseTest;
import helper.WebCtrls;
import pages.AdminPage;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PIMPage;
import pages.RecruitmentPage;
import utils.ExcelDataSourceInfo;
import utils.ExcelHelper;
import utils.ReadConfig;

@Listeners(utils.ListenerClass.class)
public class Recruitment extends BaseTest {

	@DataProvider(name = "Recruitment")
	public Object[][] createData(Method method) throws IOException {
		ExcelDataSourceInfo info = method.getAnnotation(ExcelDataSourceInfo.class);
		String testName = info.TestName();
		Object[][] retObjArr = ExcelHelper.getMapArray(this.getClass().getSimpleName(), testName);
		return retObjArr;
	}

	@ExcelDataSourceInfo(TestName = "TC01_Recruitment_AddVacancy")
	@Test(enabled = true, priority = 1, dataProvider = "Recruitment")
	public void TC01_Recruitment_AddVacancy(Map<Object, Object> map) throws IOException {

		ReadConfig readConfig = new ReadConfig();
		LoginPage loginPage = new LoginPage(driver);
		AdminPage adminPage = new AdminPage(driver);
		WebCtrls webCtrls = new WebCtrls();
		DashboardPage dashboardPage = new DashboardPage(driver);
		RecruitmentPage recruitmentPage = new RecruitmentPage(driver);
		PIMPage pimPage = new PIMPage(driver);
		driver.get(readConfig.readPropertyFile("baseURL"));

		// Decrypt the Encrypted password
		String password = webCtrls.decryptString(readConfig.readPropertyFile("Password"));

		// Login with valid credentials
		loginPage.login(readConfig.readPropertyFile("Username"), password);
		loginPage.verifyLogin();

		// Create an employee in PIM
		dashboardPage.clickPIM();
		pimPage.createEmployee((String) map.get("EmployeeFirstName"), (String) map.get("EmployeeMiddleName"),
				(String) map.get("EmployeeLastName"));

		// Select Admin tab
		dashboardPage.clickAdmin();
		
		// Create a user
		String employeeName = (String) map.get("EmployeeFirstName") + " " + (String) map.get("EmployeeMiddleName") + " "
				+ (String) map.get("EmployeeLastName");
		String employeeFirstAndLastName = (String) map.get("EmployeeFirstName") + " "
				+ (String) map.get("EmployeeLastName");
		String username = (String) map.get("EmployeeFirstName") + webCtrls.genrateRandomAlphaString(6);
		String userPassword = webCtrls.decryptString((String) map.get("UserPassword"));
		adminPage.createSystemUser((String) map.get("UserRole"), employeeName, (String) map.get("Status"), username,
				userPassword);

		// Select Recruitment tab
		dashboardPage.clickRecruitment();

		recruitmentPage.clickVacanciesTab();
		map.put("HiringManager", employeeFirstAndLastName);

		recruitmentPage.addVacancy((String) map.get("VacancyName"), (String) map.get("JobTitle"),
				(String) map.get("Description"), (String) map.get("EmployeeFirstName"),
				(String) map.get("NumOfPositions"));

		recruitmentPage.clickVacanciesTab();
		recruitmentPage.searchVacancy((String) map.get("JobTitle"), (String) map.get("VacancyName"),
				(String) map.get("HiringManager"), (String) map.get("VacancyStatus"));

		// Verify the VacancyName of the selected vacancy
		recruitmentPage.verifyVacancyTableRecord("Vacancy", (String) map.get("VacancyName"));

		// Verify the JobTitle of the selected vacancy
		recruitmentPage.verifyVacancyTableRecord("JobTitle", (String) map.get("JobTitle"));

		// Verify the HiringManager of the selected vacancy
		recruitmentPage.verifyVacancyTableRecord("HiringManager", (String) map.get("HiringManager"));

		// Verify the HiringManager of the selected vacancy
		recruitmentPage.verifyVacancyTableRecord("Status", (String) map.get("VacancyStatus"));
	}

	@ExcelDataSourceInfo(TestName = "TC02_Recruitment_AddCandidate")
	@Test(enabled = true, priority = 2, dataProvider = "Recruitment")
	public void TC02_Recruitment_AddCandidate(Map<Object, Object> map) throws IOException {

		ReadConfig readConfig = new ReadConfig();
		LoginPage loginPage = new LoginPage(driver);
		WebCtrls webCtrls = new WebCtrls();
		DashboardPage dashboardPage = new DashboardPage(driver);
		RecruitmentPage recruitmentPage = new RecruitmentPage(driver);
		driver.get(readConfig.readPropertyFile("baseURL"));

		// Decrypt the Encrypted password
		String password = webCtrls.decryptString(readConfig.readPropertyFile("Password"));

		// Login with valid credentials
		loginPage.login(readConfig.readPropertyFile("Username"), password);
		loginPage.verifyLogin();

		// Select Recruitment tab
		dashboardPage.clickRecruitment();

		recruitmentPage.clickCandidatesTab();

		String candidateName=(String) map.get("EmployeeFirstName")+" "+(String) map.get("EmployeeMiddleName")+" "+
				(String) map.get("EmployeeLastName");
		String contactNumber=Integer.toString(webCtrls.genrateRandomNumber(10));
		String email=(String) map.get("EmployeeFirstName")+webCtrls.genrateRandomAlphaString(5)+"@test.com";
		recruitmentPage.addCandidate((String) map.get("EmployeeFirstName"), (String) map.get("EmployeeMiddleName"),
				(String) map.get("EmployeeLastName"), (String) map.get("VacancyName"), email,
				contactNumber,"", (String) map.get("Keywords"), (String) map.get("Notes"));

		recruitmentPage.clickCandidatesTab();
		recruitmentPage.searchCandidateByNameAndVacancy((String) map.get("EmployeeFirstName"), (String) map.get("VacancyName"));

		// Verify the VacancyName of the selected candidate
		recruitmentPage.verifyCandidatesTableRecord("Vacancy", (String) map.get("VacancyName"));

		// Verify the JobTitle of the selected candidate
		recruitmentPage.verifyCandidatesTableRecord("Candidate", candidateName);

		// Verify the Status of the selected candidate
		recruitmentPage.verifyCandidatesTableRecord("Status", (String) map.get("CandidateStatus"));

	}
	@ExcelDataSourceInfo(TestName = "TC03_Recruitment_ShortlistCandidate")
	@Test(enabled = true, priority = 3, dataProvider = "Recruitment")
	public void TC03_Recruitment_ShortlistCandidate(Map<Object, Object> map) throws IOException {

		ReadConfig readConfig = new ReadConfig();
		LoginPage loginPage = new LoginPage(driver);
		WebCtrls webCtrls = new WebCtrls();
		DashboardPage dashboardPage = new DashboardPage(driver);
		RecruitmentPage recruitmentPage = new RecruitmentPage(driver);
		driver.get(readConfig.readPropertyFile("baseURL"));

		// Decrypt the Encrypted password
		String password = webCtrls.decryptString(readConfig.readPropertyFile("Password"));

		// Login with valid credentials
		loginPage.login(readConfig.readPropertyFile("Username"), password);
		loginPage.verifyLogin();

		// Select Recruitment tab
		dashboardPage.clickRecruitment();

		recruitmentPage.clickCandidatesTab();

		String candidateName=(String) map.get("EmployeeFirstName")+" "+(String) map.get("EmployeeMiddleName")+" "+
				(String) map.get("EmployeeLastName");
		recruitmentPage.searchCandidateByNameAndVacancy((String) map.get("EmployeeFirstName"), (String) map.get("VacancyName"));

		// Verify the Status of the selected candidate
		recruitmentPage.verifyCandidatesTableRecord("Status", (String) map.get("CandidateInitialStatus"));
		
		recruitmentPage.viewCandidate(candidateName);
		recruitmentPage.shortlistCandidate();
		recruitmentPage.verifyCandidateStatus((String) map.get("CandidateCurrentStatus"));
		
		//Verify the current status:shortisted
		recruitmentPage.clickCandidatesTab();
		recruitmentPage.searchCandidateByNameAndVacancy((String) map.get("EmployeeFirstName"), (String) map.get("VacancyName"));
		recruitmentPage.verifyCandidatesTableRecord("Status", (String) map.get("CandidateCurrentStatus"));
		 
	}
	@ExcelDataSourceInfo(TestName = "TC04_Recruitment_ScheduleInterviewForCandidate")
	@Test(enabled = true, priority = 4, dataProvider = "Recruitment")
	public void TC04_Recruitment_ScheduleInterviewForCandidate(Map<Object, Object> map) throws IOException {

		ReadConfig readConfig = new ReadConfig();
		LoginPage loginPage = new LoginPage(driver);
		WebCtrls webCtrls = new WebCtrls();
		DashboardPage dashboardPage = new DashboardPage(driver);
		RecruitmentPage recruitmentPage = new RecruitmentPage(driver);
		driver.get(readConfig.readPropertyFile("baseURL"));

		// Decrypt the Encrypted password
		String password = webCtrls.decryptString(readConfig.readPropertyFile("Password"));

		// Login with valid credentials
		loginPage.login(readConfig.readPropertyFile("Username"), password);
		loginPage.verifyLogin();

		// Select Recruitment tab
		dashboardPage.clickRecruitment();

		recruitmentPage.clickCandidatesTab();

		String candidateName=(String) map.get("EmployeeFirstName")+" "+(String) map.get("EmployeeMiddleName")+" "+
				(String) map.get("EmployeeLastName");
		recruitmentPage.searchCandidateByNameAndVacancy((String) map.get("EmployeeFirstName"), (String) map.get("VacancyName"));

		// Verify the Status of the selected candidate
		recruitmentPage.verifyCandidatesTableRecord("Status", (String) map.get("CandidateInitialStatus"));
		
		//Get the Hiring manager of the vacancy
		String hiringManager=recruitmentPage.getRecordCandidatesTable("HiringManager");
		
		//Schedule an interview
		String dateOfInterview=webCtrls.getCurrentDate("yyyy-dd-MM");
		recruitmentPage.viewCandidate(candidateName);
		recruitmentPage.scheduleInterview((String) map.get("InterviewTitle"),hiringManager, dateOfInterview,(String) map.get("TimeOfInterview"),"");
		recruitmentPage.verifyCandidateStatus((String) map.get("CandidateCurrentStatus"));
		
		//Verify the current status:Interview scheduled
		recruitmentPage.clickCandidatesTab();
		recruitmentPage.searchCandidateByNameAndVacancy((String) map.get("EmployeeFirstName"), (String) map.get("VacancyName"));
		recruitmentPage.verifyCandidatesTableRecord("Status", (String) map.get("CandidateCurrentStatus"));
		 
	}
	@ExcelDataSourceInfo(TestName = "TC05_Recruitment_MarkInterviewPassedAndOfferJobToCandidate")
	@Test(enabled = true, priority = 5, dataProvider = "Recruitment")
	public void TC05_Recruitment_MarkInterviewPassedAndOfferJobToCandidate(Map<Object, Object> map) throws IOException {

		ReadConfig readConfig = new ReadConfig();
		LoginPage loginPage = new LoginPage(driver);
		WebCtrls webCtrls = new WebCtrls();
		DashboardPage dashboardPage = new DashboardPage(driver);
		RecruitmentPage recruitmentPage = new RecruitmentPage(driver);
		driver.get(readConfig.readPropertyFile("baseURL"));

		// Decrypt the Encrypted password
		String password = webCtrls.decryptString(readConfig.readPropertyFile("Password"));

		// Login with valid credentials
		loginPage.login(readConfig.readPropertyFile("Username"), password);
		loginPage.verifyLogin();

		// Select Recruitment tab
		dashboardPage.clickRecruitment();

		recruitmentPage.clickCandidatesTab();

		String candidateName=(String) map.get("EmployeeFirstName")+" "+(String) map.get("EmployeeMiddleName")+" "+
				(String) map.get("EmployeeLastName");
		recruitmentPage.searchCandidateByNameAndVacancy((String) map.get("EmployeeFirstName"), (String) map.get("VacancyName"));

		// Verify the Status of the selected candidate
		recruitmentPage.verifyCandidatesTableRecord("Status", (String) map.get("CandidateInitialStatus"));
		
		//Mark the interview as passed
		recruitmentPage.viewCandidate(candidateName);
		recruitmentPage.markInterviewStatus((String) map.get("CandidateInterviewStatus"),"");
		recruitmentPage.verifyCandidateStatus((String) map.get("CandidateStatus2"));
		
		//Verify the status:Interview Passed
		recruitmentPage.clickCandidatesTab();
		recruitmentPage.searchCandidateByNameAndVacancy((String) map.get("EmployeeFirstName"), (String) map.get("VacancyName"));
		recruitmentPage.verifyCandidatesTableRecord("Status", (String) map.get("CandidateStatus2"));
		
		//Offer the job
		recruitmentPage.viewCandidate(candidateName);
		recruitmentPage.offerJob("");
		recruitmentPage.verifyCandidateStatus((String) map.get("CandidateCurrentStatus"));
		
		//Verify the status:Interview Passed
		recruitmentPage.clickCandidatesTab();
		recruitmentPage.searchCandidateByNameAndVacancy((String) map.get("EmployeeFirstName"), (String) map.get("VacancyName"));
		recruitmentPage.verifyCandidatesTableRecord("Status", (String) map.get("CandidateCurrentStatus"));
	}
	
	@ExcelDataSourceInfo(TestName = "TC06_Recruitment_HireCandidate")
	@Test(enabled = true, priority = 6, dataProvider = "Recruitment")
	public void TC06_Recruitment_HireCandidate(Map<Object, Object> map) throws IOException {

		ReadConfig readConfig = new ReadConfig();
		LoginPage loginPage = new LoginPage(driver);
		WebCtrls webCtrls = new WebCtrls();
		DashboardPage dashboardPage = new DashboardPage(driver);
		RecruitmentPage recruitmentPage = new RecruitmentPage(driver);
		driver.get(readConfig.readPropertyFile("baseURL"));

		// Decrypt the Encrypted password
		String password = webCtrls.decryptString(readConfig.readPropertyFile("Password"));

		// Login with valid credentials
		loginPage.login(readConfig.readPropertyFile("Username"), password);
		loginPage.verifyLogin();

		// Select Recruitment tab
		dashboardPage.clickRecruitment();

		recruitmentPage.clickCandidatesTab();

		String candidateName=(String) map.get("EmployeeFirstName")+" "+(String) map.get("EmployeeMiddleName")+" "+
				(String) map.get("EmployeeLastName");
		recruitmentPage.searchCandidateByNameAndVacancy((String) map.get("EmployeeFirstName"), (String) map.get("VacancyName"));

		// Verify the Status of the selected candidate
		recruitmentPage.verifyCandidatesTableRecord("Status", (String) map.get("CandidateInitialStatus"));
		
		//Hire the candidate
		recruitmentPage.viewCandidate(candidateName);
		recruitmentPage.hireCandidate(candidateName);
		recruitmentPage.verifyCandidateStatus((String) map.get("CandidateCurrentStatus"));
		
		//Verify the status:Hired
		recruitmentPage.clickCandidatesTab();
		recruitmentPage.searchCandidateByNameAndVacancy((String) map.get("EmployeeFirstName"), (String) map.get("VacancyName"));
		recruitmentPage.verifyCandidatesTableRecord("Status", (String) map.get("CandidateCurrentStatus"));			 
	}
	@ExcelDataSourceInfo(TestName = "TC07_Recruitment_MarkInterviewFailedAndRejectCandidate")
	@Test(enabled = true, priority = 7, dataProvider = "Recruitment")
	public void TC07_Recruitment_MarkInterviewFailedAndRejectCandidate(Map<Object, Object> map) throws IOException {

		ReadConfig readConfig = new ReadConfig();
		LoginPage loginPage = new LoginPage(driver);
		WebCtrls webCtrls = new WebCtrls();
		DashboardPage dashboardPage = new DashboardPage(driver);
		RecruitmentPage recruitmentPage = new RecruitmentPage(driver);
		driver.get(readConfig.readPropertyFile("baseURL"));

		// Decrypt the Encrypted password
		String password = webCtrls.decryptString(readConfig.readPropertyFile("Password"));

		// Login with valid credentials
		loginPage.login(readConfig.readPropertyFile("Username"), password);
		loginPage.verifyLogin();

		// Select Recruitment tab
		dashboardPage.clickRecruitment();

		String candidateName=(String) map.get("EmployeeFirstName")+" "+(String) map.get("EmployeeMiddleName")+" "+
				(String) map.get("EmployeeLastName");
		String contactNumber=Integer.toString(webCtrls.genrateRandomNumber(10));
		String email=(String) map.get("EmployeeFirstName")+webCtrls.genrateRandomAlphaString(5)+"@test.com";
		recruitmentPage.addCandidate((String) map.get("EmployeeFirstName"), (String) map.get("EmployeeMiddleName"),
				(String) map.get("EmployeeLastName"), (String) map.get("VacancyName"), email,
				contactNumber,"", (String) map.get("Keywords"), (String) map.get("Notes"));
		recruitmentPage.clickCandidatesTab();
		recruitmentPage.searchCandidateByNameAndVacancy((String) map.get("EmployeeFirstName"), (String) map.get("VacancyName"));
		// Get the Hiring manager of the vacancy
		String hiringManager = recruitmentPage.getRecordCandidatesTable("HiringManager");
		
		//Shortlist candidate
		recruitmentPage.viewCandidate(candidateName);
		recruitmentPage.shortlistCandidate();
		//Schedule an interview
		String dateOfInterview=webCtrls.getCurrentDate("yyyy-dd-MM");
		recruitmentPage.scheduleInterview((String) map.get("InterviewTitle"),hiringManager, dateOfInterview,(String) map.get("TimeOfInterview"),"");
	
		recruitmentPage.clickCandidatesTab();
		recruitmentPage.searchCandidateByNameAndVacancy((String) map.get("EmployeeFirstName"), (String) map.get("VacancyName"));
		
		// Verify the Status of the selected candidate:Interview Scheduled
		recruitmentPage.verifyCandidatesTableRecord("Status", (String) map.get("CandidateInitialStatus"));
		
		//Mark the interview as Failed
		recruitmentPage.viewCandidate(candidateName);
		recruitmentPage.markInterviewStatus((String) map.get("CandidateInterviewStatus"),"");
		recruitmentPage.verifyCandidateStatus((String) map.get("CandidateStatus2"));
		
		//Verify the status:Interview Failed
		recruitmentPage.clickCandidatesTab();
		recruitmentPage.searchCandidateByNameAndVacancy((String) map.get("EmployeeFirstName"), (String) map.get("VacancyName"));
		recruitmentPage.verifyCandidatesTableRecord("Status", (String) map.get("CandidateStatus2"));
		
		//Reject the candidate
		recruitmentPage.viewCandidate(candidateName);
		recruitmentPage.rejectCandidate("Rejected");
		recruitmentPage.verifyCandidateStatus((String) map.get("CandidateCurrentStatus"));
		
		//Verify the status:Rejected
		recruitmentPage.clickCandidatesTab();
		recruitmentPage.searchCandidateByNameAndVacancy((String) map.get("EmployeeFirstName"), (String) map.get("VacancyName"));
		recruitmentPage.verifyCandidatesTableRecord("Status", (String) map.get("CandidateCurrentStatus"));
	}
	@ExcelDataSourceInfo(TestName = "TC08_Recruitment_RejectCandidateWithoutShortlist")
	@Test(enabled = true, priority = 8, dataProvider = "Recruitment")
	public void TC08_Recruitment_RejectCandidateWithoutShortlist(Map<Object, Object> map) throws IOException {

		ReadConfig readConfig = new ReadConfig();
		LoginPage loginPage = new LoginPage(driver);
		WebCtrls webCtrls = new WebCtrls();
		DashboardPage dashboardPage = new DashboardPage(driver);
		RecruitmentPage recruitmentPage = new RecruitmentPage(driver);
		driver.get(readConfig.readPropertyFile("baseURL"));

		// Decrypt the Encrypted password
		String password = webCtrls.decryptString(readConfig.readPropertyFile("Password"));

		// Login with valid credentials
		loginPage.login(readConfig.readPropertyFile("Username"), password);
		loginPage.verifyLogin();

		// Select Recruitment tab
		dashboardPage.clickRecruitment();

		String candidateName=(String) map.get("EmployeeFirstName")+" "+(String) map.get("EmployeeMiddleName")+" "+
				(String) map.get("EmployeeLastName");
		String contactNumber=Integer.toString(webCtrls.genrateRandomNumber(10));
		String email=(String) map.get("EmployeeFirstName")+webCtrls.genrateRandomAlphaString(5)+"@test.com";
		recruitmentPage.addCandidate((String) map.get("EmployeeFirstName"), (String) map.get("EmployeeMiddleName"),
				(String) map.get("EmployeeLastName"), (String) map.get("VacancyName"), email,
				contactNumber, "", (String) map.get("Keywords"), (String) map.get("Notes"));
		recruitmentPage.clickCandidatesTab();
		recruitmentPage.searchCandidateByNameAndVacancy((String) map.get("EmployeeFirstName"), (String) map.get("VacancyName"));
		// Verify the Status of the selected candidate
		recruitmentPage.verifyCandidatesTableRecord("Status", (String) map.get("CandidateInitialStatus"));
		
		//Reject candidate
		recruitmentPage.viewCandidate(candidateName);
		recruitmentPage.rejectCandidate((String) map.get("RejectionNote"));
		recruitmentPage.verifyCandidateStatus((String) map.get("CandidateCurrentStatus"));
		
		//Verify the current status:Rejected
		recruitmentPage.clickCandidatesTab();
		recruitmentPage.searchCandidateByNameAndVacancy((String) map.get("EmployeeFirstName"), (String) map.get("VacancyName"));
		recruitmentPage.verifyCandidatesTableRecord("Status", (String) map.get("CandidateCurrentStatus"));
		 
	}
}