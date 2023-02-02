package kp.company;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import kp.company.domain.Department;
import kp.company.domain.Employee;
import kp.company.domain.Title;
import kp.company.exc.KpException;

/**
 * Class BusinessLogic.
 */
public class BusinessLogic {

	private static final Logger logger = Logger.getLogger(BusinessLogic.class
			.getName());

	private EntityManagerFactory entityManagerFactory = null;
	private EntityManager entityManager = null;
	private String PERSISTENCE_UNIT_NAME = "kp_hibernate02";

	/**
	 * Constructor
	 */
	public BusinessLogic() {

		entityManagerFactory = Persistence
				.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		entityManager = entityManagerFactory.createEntityManager();
	}

	/**
	 * Closes resources
	 */
	public void close() {

		if (entityManager != null) {
			entityManager.close();
			entityManager = null;
		}
		if (entityManagerFactory != null) {
			entityManagerFactory.close();
			entityManagerFactory = null;
		}
	}

	/**
	 * Retrieves all <code>Department</code>s from the data store.
	 * 
	 * @return the list of departments
	 * @throws KpException the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Department> loadDepartments() throws KpException {

		List<Department> departments = null;
		EntityTransaction transaction = null;
		try {
			transaction = entityManager.getTransaction();
			transaction.begin();
			String ql = "select department from Department department "
					+ "order by department.name asc";
			Query query = entityManager.createQuery(ql);
			departments = (List<Department>) query.getResultList();
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			logger.log(Level.SEVERE, "loadDepartments(): Exception["
					+ ex.getMessage() + "]", ex);
			throw new KpException();
		}
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("loadDepartments():");
		}
		return departments;
	}

	/**
	 * Retrieves all <code>Employee</code>s from the data store.
	 * 
	 * @param departmentId
	 *            departmentId
	 * @return the list of employees
	 * @throws KpException the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Employee> loadEmployees(Long departmentId) throws KpException {

		List<Employee> employees = null;
		EntityTransaction transaction = null;
		try {
			transaction = entityManager.getTransaction();
			transaction.begin();
			String ql = "select employee from Employee employee "
					+ "where employee.department.id=:id "
					+ "order by employee.lastName asc";
			Query query = entityManager.createQuery(ql);
			query.setParameter("id", departmentId);
			employees = (List<Employee>) query.getResultList();
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			logger.log(Level.SEVERE, "loadEmployees(): Exception["
					+ ex.getMessage() + "]", ex);
			throw new KpException();
		}
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("loadEmployees():");
		}
		return employees;
	}

	/**
	 * Retrieves all <code>Title</code>s from the data store.
	 * 
	 * @return the list of titles
	 * @throws KpException the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Title> loadTitles() throws KpException {

		List<Title> titles = null;
		EntityTransaction transaction = null;
		try {
			transaction = entityManager.getTransaction();
			transaction.begin();
			String ql = "select title from Title title "
					+ "order by title.name asc";
			Query query = entityManager.createQuery(ql);
			titles = (List<Title>) query.getResultList();
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			logger.log(Level.SEVERE, "loadTitles(): Exception["
					+ ex.getMessage() + "]", ex);
			throw new KpException();
		}
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("loadTitles():");
		}
		return titles;
	}

	/**
	 * Stores a <code>Department</code> to the data store.
	 * 
	 * @param department
	 *            department
	 * @return merged department
	 * @throws KpException the exception
	 */
	public Department storeDepartment(Department department) throws KpException {

		Department mergedDepartment = null;
		EntityTransaction transaction = null;
		try {
			transaction = entityManager.getTransaction();
			transaction.begin();
			mergedDepartment = entityManager.merge(department);
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			logger.log(Level.SEVERE, "storeDepartment(): Exception["
					+ ex.getMessage() + "]", ex);
			throw new KpException();
		}
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("storeDepartment(): id[" + mergedDepartment.getId()
					+ "]");
		}
		return mergedDepartment;
	}

	/**
	 * Stores an <code>Employee</code> to the data store.
	 * 
	 * @param employee
	 *            employee
	 * @return merged employee
	 * @throws KpException the exception
	 */
	public Employee storeEmployee(Employee employee) throws KpException {

		return storeEmployee(null, employee);
	}

	/**
	 * Stores an <code>Employee</code> to the data store.
	 * 
	 * @param department
	 *            department
	 * @param employee
	 *            employee
	 * @return merged employee
	 * @throws KpException the exception
	 */
	public Employee storeEmployee(Department department, Employee employee)
			throws KpException {

		Employee mergedEmployee = null;
		EntityTransaction transaction = null;
		try {
			transaction = entityManager.getTransaction();
			transaction.begin();
			if (department != null) {
				employee.setDepartment(department);
			}
			mergedEmployee = entityManager.merge(employee);
			if (department != null) {
				department.addEmployee(mergedEmployee);
				entityManager.merge(department);
			}
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			logger.log(Level.SEVERE, "storeEmployee(): Exception["
					+ ex.getMessage() + "]", ex);
			throw new KpException();
		}
		if (logger.isLoggable(Level.CONFIG)) {
			logger
					.config("storeEmployee(): id[" + mergedEmployee.getId()
							+ "]");
		}
		return mergedEmployee;
	}

	/**
	 * Deletes a <code>Department</code> from the data store.
	 * 
	 * @param id
	 *            id
	 * @throws KpException the exception
	 */
	public void deleteDepartment(Long id) throws KpException {

		EntityTransaction transaction = null;
		try {
			transaction = entityManager.getTransaction();
			transaction.begin();
			Department department = entityManager.find(Department.class, id);
			entityManager.remove(department);
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			logger.log(Level.SEVERE, "deleteDepartment(): Exception["
					+ ex.getMessage() + "]", ex);
			throw new KpException();
		}
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("deleteDepartment():");
		}
	}

	/**
	 * Deletes an <code>Employee</code> from the data store.
	 * 
	 * @param id
	 *            id
	 * @throws KpException the exception
	 */
	public void deleteEmployee(Long id) throws KpException {

		EntityTransaction transaction = null;
		try {
			transaction = entityManager.getTransaction();
			transaction.begin();
			Employee employee = entityManager.find(Employee.class, id);
			Department department = employee.getDepartment();
			department.removeEmployee(employee);
			entityManager.merge(department);
			entityManager.remove(employee);
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			logger.log(Level.SEVERE, "deleteEmployee(): Exception["
					+ ex.getMessage() + "]", ex);
			throw new KpException();
		}
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("deleteEmployee():");
		}
	}

}
