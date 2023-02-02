package kp.company.test;

import java.util.List;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import kp.company.BusinessLogic;
import kp.company.domain.Department;
import kp.company.domain.Employee;
import kp.company.domain.Title;
import kp.company.util.LoggingConfiguration;

/**
 * Class KpTest.
 * 
 */
/*-
 * Fixed tests order! These tests were incorrectly designed to run in determined order.
 * From version 4.11, JUnit will by default use a deterministic, but not predictable, order.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class KpTest {

	private static final Logger logger = Logger.getLogger(KpTest.class
			.getName());

	// department
	private static final String D_1 = "D=e=p=t=T1";
	private static final String D_2 = "D=e=p=t=T2";
	// first name
	private static final String FN_11 = "F=N=a=m=e=T11";
	private static final String FN_21 = "F=N=a=m=e=T21";
	// last name
	private static final String LN_11 = "L=N=a=m=e=T11";
	private static final String LN_21 = "L=N=a=m=e=T21";
	// title
	private static final String T_1 = "Manager";
	private static final String T_2 = "Analyst";

	/**
	 * Constructor.
	 * 
	 */
	public KpTest() {

		super();
		LoggingConfiguration.setLogging();
		logger.fine("KpTest():");
	}

	/**
	 * Loads titles.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test01_LoadTitles() throws Exception {

		BusinessLogic businessLogic = new BusinessLogic();
		Title title = null;
		List<Title> titles = businessLogic.loadTitles();
		for (Title tit : titles) {
			if (T_1.equals(tit.getName())) {
				title = tit;
				break;
			}
		}
		Assert.assertNotNull("Searching Title", title);
		businessLogic.close();
		logger.info("test01_LoadTitles():");
	}

	/**
	 * Adds department.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test02_AddDepartment() throws Exception {

		BusinessLogic businessLogic = new BusinessLogic();
		Department department = new Department();
		department.setName(D_1);
		businessLogic.storeDepartment(department);
		businessLogic.close();
		logger.info("test02_AddDepartment():");
	}

	/**
	 * Adds employee.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test03_AddEmployee() throws Exception {

		BusinessLogic businessLogic = new BusinessLogic();
		List<Department> departments = businessLogic.loadDepartments();
		Department department = null;
		for (Department dep : departments) {
			if (D_1.equals(dep.getName())) {
				department = dep;
			}
		}
		Assert.assertNotNull("Searching Department", department);

		Title title = null;
		List<Title> titles = businessLogic.loadTitles();
		for (Title tit : titles) {
			if (T_1.equals(tit.getName())) {
				title = tit;
			}

		}
		Assert.assertNotNull("Searching Title", title);

		Employee employee = new Employee();
		employee.setFirstName(FN_11);
		employee.setLastName(LN_11);
		employee.setTitle(title);
		employee = businessLogic.storeEmployee(department, employee);
		businessLogic.close();
		logger.info("test03_AddEmployee():");
	}

	/**
	 * Updates department.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test04_UpdateDepartment() throws Exception {

		BusinessLogic businessLogic = new BusinessLogic();
		List<Department> departments = businessLogic.loadDepartments();
		Department department = null;
		for (Department dep : departments) {
			if (D_1.equals(dep.getName())) {
				department = dep;
				break;
			}
		}
		Assert.assertNotNull("Searching Department", department);
		department.setName(D_2);
		businessLogic.storeDepartment(department);
		businessLogic.close();
		logger.info("test04_UpdateDepartment():");
	}

	/**
	 * Updates employee.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test05_UpdateEmployee() throws Exception {

		BusinessLogic businessLogic = new BusinessLogic();
		List<Department> departments = businessLogic.loadDepartments();
		Department department = null;
		for (Department dep : departments) {
			if (D_2.equals(dep.getName())) {
				department = dep;
				break;
			}
		}
		Assert.assertNotNull("Searching Department", department);

		Title title = null;
		List<Title> titles = businessLogic.loadTitles();
		for (Title tit : titles) {
			if (T_2.equals(tit.getName())) {
				title = tit;
				break;
			}
		}
		Assert.assertNotNull("Searching Title", title);

		List<Employee> employees = businessLogic.loadEmployees(department.getId());
		Assert.assertEquals("Number of employees", 1, employees.size());
		Employee employee = employees.get(0);
		Assert.assertEquals("Employee first name", FN_11, employee.getFirstName());
		Assert.assertEquals("Employee last name", LN_11, employee.getLastName());
		Assert.assertEquals("Employee title", T_1, employee.getTitle().getName());

		employee.setFirstName(FN_21);
		employee.setLastName(LN_21);
		employee.setTitle(title);
		businessLogic.storeEmployee(employee);
		businessLogic.close();
		logger.info("test05_UpdateEmployee():");
	}

	/**
	 * Deletes employee.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test06_DeleteEmployee() throws Exception {

		BusinessLogic businessLogic = new BusinessLogic();
		List<Department> departments = businessLogic.loadDepartments();
		Department department = null;
		for (Department dep : departments) {
			if (D_2.equals(dep.getName())) {
				department = dep;
				break;
			}
		}
		Assert.assertNotNull("Searching Department", department);

		List<Employee> employees = businessLogic.loadEmployees(department.getId());
		Assert.assertEquals("Number of employees", 1, employees.size());
		Employee employee = employees.get(0);
		Assert.assertEquals("Employee first name", FN_21, employee.getFirstName());
		Assert.assertEquals("Employee last name", LN_21, employee.getLastName());
		Assert.assertEquals("Employee title", T_2, employee.getTitle().getName());
		businessLogic.deleteEmployee(employee.getId());

		employees = businessLogic.loadEmployees(department.getId());
		Assert.assertEquals("Number of employees", 0, employees.size());
		businessLogic.close();
		logger.info("test06_DeleteEmployee():");
	}

	/**
	 * Deletes department.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test07_DeleteDepartment() throws Exception {

		BusinessLogic businessLogic = new BusinessLogic();
		List<Department> departments = businessLogic.loadDepartments();
		Department department = null;
		for (Department dep : departments) {
			if (D_2.equals(dep.getName())) {
				department = dep;
				break;
			}
		}
		Assert.assertNotNull("Searching Department", department);
		businessLogic.deleteDepartment(department.getId());

		departments = businessLogic.loadDepartments();
		department = null;
		for (Department dep : departments) {
			if (D_2.equals(dep.getName())) {
				department = dep;
				break;
			}
		}
		Assert.assertNull("Searching deleted Department", department);
		businessLogic.close();
		logger.info("test07_DeleteDepartment():");
	}
}
