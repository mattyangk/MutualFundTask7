package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import databeans.EmployeeBean;

public class EmployeeDAO extends GenericDAO<EmployeeBean>{
	public EmployeeDAO(ConnectionPool connectionPool, String tableName)
			throws DAOException {
		super(EmployeeBean.class, tableName, connectionPool);
	}

	public EmployeeBean getEmployeeByUsername(String username)
			throws RollbackException {
		EmployeeBean[] employee = match(MatchArg.equals("username", username));
		if (employee.length != 1) {
			System.out.println("not correct number of employees");
			return null;
		}
		return employee[0];
	}

}
