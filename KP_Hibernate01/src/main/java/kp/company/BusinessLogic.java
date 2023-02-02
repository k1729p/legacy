package kp.company;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import kp.company.domain.Department;
import kp.company.domain.Employee;
import kp.company.domain.Title;
import kp.company.exc.KpException;
import kp.company.util.HibernateUtil;

/**
 * Class BusinessLogic.
 */
@SuppressWarnings("deprecation")
public class BusinessLogic {

	private static final Logger logger = Logger.getLogger(BusinessLogic.class.getName());

	/**
	 * Retrieves all <code>Department</code>s from the data store.
	 * 
	 * @return the list of departments
	 * @throws KpException the exception
	 */
	@SuppressWarnings({ "unchecked" })
	public List<Department> loadDepartments() throws KpException {

		List<Department> departments = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.openSession();
			transaction = session.beginTransaction();
			Query<?> query = session.createQuery("from Department");
			departments = (List<Department>) query.list();
			transaction.commit();
		} catch (HibernateException ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.log(Level.SEVERE, "loadDepartments(): Exception[" + ex.getMessage() + "]", ex);
			throw new KpException();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("loadDepartments():");
		}
		return departments;
	}

	/**
	 * Retrieves all <code>Employee</code>s from the data store.
	 * 
	 * @param department the department
	 * @return the list of employees
	 * @throws KpException the exception
	 */
	@SuppressWarnings({ "unchecked" })
	public List<Employee> loadEmployees(Department department) throws KpException {

		List<Employee> employees = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.openSession();
			transaction = session.beginTransaction();
			String hql = "from Employee as employee where employee.department.id=:departmentId";
			Query<?> query = session.createQuery(hql).setLong("departmentId", department.getId());
			employees = (List<Employee>) query.list();
			// "touch" for for the name of a title
			for (Employee employee : employees) {
				employee.getTitle().getName();
			}
			transaction.commit();
		} catch (HibernateException ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.log(Level.SEVERE, "loadEmployees(): Exception[" + ex.getMessage() + "]", ex);
			throw new KpException();
		} finally {
			if (session != null) {
				session.close();
			}
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
	@SuppressWarnings({ "unchecked" })
	public List<Title> loadTitles() throws KpException {

		List<Title> titles = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.openSession();
			transaction = session.beginTransaction();
			Query<?> query = session.createQuery("from Title");
			titles = (List<Title>) query.list();
			transaction.commit();
		} catch (HibernateException ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.log(Level.SEVERE, "loadTitles(): Exception[" + ex.getMessage() + "]", ex);
			throw new KpException();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("loadTitles():");
		}
		return titles;
	}

	/**
	 * Retrieves summary list from the data store.
	 * 
	 * @return the summary list
	 * @throws KpException the exception
	 */
	@SuppressWarnings({ "unchecked" })
	public Map<String, List<String>> loadSummary() throws KpException {

		TreeMap<String, List<String>> summaryMap = new TreeMap<String, List<String>>();
		List<Object[]> resultList = null;
		Session session = null;
		Transaction transaction = null;
		String hql = "select distinct " + "d.name, concat(count(*), ' ', t.name, "
				+ "(case when count(*) = 1 then '' else 's' end)) " + "from Employee as employee "
				+ "left join employee.department as d " + "left join employee.title as t "
				+ "group by d.name, d.id, t.name " + "order by d.name, d.id, t.name";
		try {
			session = HibernateUtil.openSession();
			transaction = session.beginTransaction();
			Query<?> query = session.createQuery(hql);
			resultList = (List<Object[]>) query.list();
			transaction.commit();
		} catch (HibernateException ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.log(Level.SEVERE, "loadSummary(): Exception[" + ex.getMessage() + "]", ex);
			throw new KpException();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		for (Object[] resultItem : resultList) {
			String key = (String) resultItem[0];
			String value = (String) resultItem[1];
			List<String> empList = summaryMap.get(key);
			if (empList == null) {
				empList = new ArrayList<String>();
				summaryMap.put(key, empList);
			}
			empList.add(value);
		}
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("loadSummary():");
		}
		return summaryMap;
	}

	/**
	 * Saves or updates a <code>Department</code> to the data store. Makes
	 * persistent instance.
	 * 
	 * @param department department
	 * @throws KpException the exception
	 */
	public void saveOrUpdateDepartment(Department department) throws KpException {

		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(department);
			transaction.commit();
		} catch (HibernateException ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.log(Level.SEVERE, "saveOrUpdateDepartment(): Exception[" + ex.getMessage() + "]", ex);
			throw new KpException();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("saveOrUpdateDepartment(): id[" + department.getId() + "]");
		}
	}

	/**
	 * Saves an <code>Employee</code> to the data store. Makes persistent instance.
	 * 
	 * @param department department
	 * @param employee   employee
	 * @throws KpException the exception
	 */
	public void saveEmployee(Department department, Employee employee) throws KpException {

		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.openSession();
			transaction = session.beginTransaction();
			session.refresh(department);
			department.addEmployee(employee);
			session.save(employee);
			transaction.commit();
		} catch (HibernateException ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.log(Level.SEVERE, "saveEmployee(): Exception[" + ex.getMessage() + "]", ex);
			throw new KpException();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("saveEmployee(): id[" + employee.getId() + "]");
		}
	}

	/**
	 * Updates an <code>Employee</code> to the data store. Makes persistent
	 * instance.
	 * 
	 * @param employee employee
	 * @throws KpException the exception
	 */
	public void updateEmployee(Employee employee) throws KpException {

		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.openSession();
			transaction = session.beginTransaction();
			session.update(employee);
			transaction.commit();
		} catch (HibernateException ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.log(Level.SEVERE, "updateEmployee(): Exception[" + ex.getMessage() + "]", ex);
			throw new KpException();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("updateEmployee(): id[" + employee.getId() + "]");
		}
	}

	/**
	 * Deletes a <code>Department</code> from the data store. Makes transient
	 * instance from persistent instance.
	 * 
	 * @param department department
	 * @throws KpException the exception
	 */
	public void deleteDepartment(Department department) throws KpException {

		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.openSession();
			transaction = session.beginTransaction();
			session.delete(department);
			transaction.commit();
		} catch (HibernateException ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.log(Level.SEVERE, "deleteDepartment(): Exception[" + ex.getMessage() + "]", ex);
			throw new KpException();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("deleteDepartment():");
		}
	}

	/**
	 * Deletes an <code>Employee</code> from the data store. Makes transient
	 * instance from persistent instance.
	 * 
	 * @param department department
	 * @param employeeId employee id
	 * @throws KpException the exception
	 */
	public void deleteEmployee(Department department, Long employeeId) throws KpException {

		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.openSession();
			transaction = session.beginTransaction();
			session.refresh(department);
			Employee employee = department.removeEmployee(employeeId);
			session.update(department);
			session.delete(employee);
			transaction.commit();
		} catch (HibernateException ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.log(Level.SEVERE, "deleteEmployee(): Exception[" + ex.getMessage() + "]", ex);
			throw new KpException();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("deleteEmployee():");
		}
	}

	/**
	 * Joins two <code>Department</code>s. Moves <code>Employee</code>s from second
	 * <code>Department</code> to first <code>Department</code>.
	 * 
	 * <P>
	 * Simulates workflow for joining departments process.
	 * <P>
	 * Workflow outcome depends on the number of departments before join:
	 * <UL>
	 * <LI>success for more than 2 departments - join departments and commit this
	 * transaction
	 * <LI>failure for exactly 2 departments - join departments and rollback this
	 * transaction
	 * <LI>failure for less than 2 departments - no join
	 * </UL>
	 * 
	 * @return result flag
	 * @throws KpException the exception
	 */
	@SuppressWarnings({ "unchecked" })
	public boolean joinDepartments() throws KpException {

		List<Department> departments = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.openSession();
			transaction = session.beginTransaction();

			Query<?> query = session.createQuery("from Department");
			departments = (List<Department>) query.list();
			int numberOfDepartments = departments.size();
			if (numberOfDepartments < 2) {
				// only one single department in the company
				logger.info("joinDepartments(): ONE DEPARTMENT ONLY");
				return false;
			}
			Iterator<Department> iterator = departments.iterator();
			Department targetDepartment = iterator.next();
			Department sourceDepartment = iterator.next();

			List<Employee> employees = new ArrayList<Employee>(sourceDepartment.getEmployees());
			for (Employee employee : employees) {
				sourceDepartment.removeEmployee(employee);
				targetDepartment.addEmployee(employee);
			}
			session.delete(sourceDepartment);

			if (numberOfDepartments == 2) {
				// trigger rollback
				transaction.rollback();
				logger.info("joinDepartments(): ROLLBACK");
				return false;
			} else {
				transaction.commit();
				logger.info("joinDepartments(): WORKFLOW OK");
				return true;
			}
		} catch (HibernateException ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.log(Level.SEVERE, "joinDepartments(): Exception[" + ex.getMessage() + "]", ex);
			throw new KpException();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
