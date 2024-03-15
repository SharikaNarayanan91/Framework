package testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import utils.ListenerClass;
import utils.ReadConfig;

@Listeners(utils.ListenerClass.class)
public class Recruitment extends BaseTest {
	private static Logger logger = LogManager.getLogger(Login.class);

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

		// Select Recruitment tab
		dashboardPage.clickRecruitment();

		recruitmentPage.clickCandidatesTab();

		String candidateName=(String) map.get("EmployeeFirstName")+" "+(String) map.get("EmployeeMiddleName")+" "+
				(String) map.get("EmployeeLastName");
		String contactNumber=Integer.toString(webCtrls.genrateRandomNumber(10));
		String email=(String) map.get("EmployeeFirstName")+webCtrls.genrateRandomAlphaString(5)+"@test.com";
		recruitmentPage.addCandidate((String) map.get("EmployeeFirstName"), (String) map.get("EmployeeMiddleName"),
				(String) map.get("EmployeeLastName"), (String) map.get("VacancyName"), email,
				contactNumber, (String) map.get("Keywords"), (String) map.get("Notes"));

		recruitmentPage.clickCandidatesTab();
		recruitmentPage.searchCandidateByNameAndVacancy((String) map.get("EmployeeFirstName"), (String) map.get("VacancyName"));

		// Verify the VacancyName of the selected candidate
		recruitmentPage.verifyCandidatesTableRecord("Vacancy", (String) map.get("VacancyName"));

		// Verify the JobTitle of the selected candidate
		recruitmentPage.verifyCandidatesTableRecord("Candidate", candidateName);

		// Verify the Status of the selected candidate
		recruitmentPage.verifyCandidatesTableRecord("Status", (String) map.get("CandidateStatus"));

		/*
		 * // Delete the candidate recruitmentPage.deleteCandidate(candidateName);
		 * 
		 * // Delete the vacancy recruitmentPage.clickVacanciesTab();
		 * recruitmentPage.searchVacancyByVacancyName((String) map.get("VacancyName"));
		 * recruitmentPage.deleteVacancy((String) map.get("VacancyName"));
		 */
	}
	@ExcelDataSourceInfo(TestName = "TC03_Recruitment_ShortlistCandidate")
	@Test(enabled = true, priority = 3, dataProvider = "Recruitment")
	public void TC03_Recruitment_ShortlistCandidate(Map<Object, Object> map) throws IOException {

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
		

		// Delete the candidate
		recruitmentPage.deleteCandidate(candidateName);

		// Delete the vacancy 
		recruitmentPage.clickVacanciesTab();
		recruitmentPage.searchVacancyByVacancyName((String) map.get("VacancyName"));
		recruitmentPage.deleteVacancy((String) map.get("VacancyName"));
		 
	}
}
