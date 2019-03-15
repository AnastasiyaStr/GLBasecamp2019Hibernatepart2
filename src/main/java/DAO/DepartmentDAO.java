package DAO;



import org.hibernate.Session;
import java.util.*;

import entity.Department;
import org.hibernate.*;
public class DepartmentDAO {
    static Session sessionObj;
    private static HibernateUtil HibernateUtil;
    public DepartmentDAO(HibernateUtil hibernateUtil){
        DepartmentDAO.HibernateUtil = hibernateUtil;
    }
    // Method 1: This Method Used To Create A New Worker Record In The Database Table
    public static void createRecord() {

        Department departmentObj = null;

        try {
            // Getting Session Object From SessionFactory
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            // Getting Transaction Object From Session Object
            sessionObj.beginTransaction();
            for (int j = 1; j <= 4; j++) {
                departmentObj = new Department();
                departmentObj.setId(j);
                departmentObj.setName("Programming Department" + j);
                departmentObj.setStatus(false);
                sessionObj.save(departmentObj);
            }

            sessionObj.getTransaction().commit();
            System.out.println("\nSuccessfully Created  Records In The Database!\n");
        } catch (Exception sqlException) {
            if (null != sessionObj.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
                sessionObj.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if (sessionObj != null) {
                sessionObj.close();
            }
        }
    }

    // Method 2: This Method Is Used To Display The Records From The Database Table
    @SuppressWarnings("unchecked")
    public static List displayRecords() {
        List departmentList = new ArrayList();
        try {
            // Getting Session Object From SessionFactory
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            // Getting Transaction Object From Session Object
            sessionObj.beginTransaction();

            departmentList = sessionObj.createQuery("FROM Department").list();
        } catch(Exception sqlException) {
            if(null != sessionObj.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
                sessionObj.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if(sessionObj != null) {
                sessionObj.close();
            }
        }
        return departmentList;
    }
    // Method 3: This Method Is Used To Update A Record In The Database Table
    public static void updateRecord(int department_id) {
        try {
            // Getting Session Object From SessionFactory
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            // Getting Transaction Object From Session Object
            sessionObj.beginTransaction();

            // Creating Transaction Entity
            Department depObj = (Department) sessionObj.get(Department.class, department_id);
            depObj.setName("GlobalLogic");
            depObj.setStatus(false);


            // Committing The Transactions To The Database
            sessionObj.getTransaction().commit();
            System.out.println("\nDepartment With Id?= " + department_id + " Is Successfully Updated In The Database!\n");
        } catch(Exception sqlException) {
            if(null != sessionObj.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
                sessionObj.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if(sessionObj != null) {
                sessionObj.close();
            }
        }
    }

    // Method 4(a): This Method Is Used To Delete A Particular Record From The Database Table
    public static void deleteRecord(Integer department_id) {
        try {
            // Getting Session Object From SessionFactory
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            // Getting Transaction Object From Session Object
            sessionObj.beginTransaction();

            Department depObj = findRecordById(department_id);
            sessionObj.delete(depObj);

            // Committing The Transactions To The Database
            sessionObj.getTransaction().commit();
            System.out.println("\nDepartment With Id?= " + department_id + " Is Successfully Deleted From The Database!\n");
        } catch(Exception sqlException) {
            if(null != sessionObj.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
                sessionObj.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if(sessionObj != null) {
                sessionObj.close();
            }
        }
    }

    // Method 4(b): This Method To Find Particular Record In The Database Table
    public static Department findRecordById(Integer find_department_id) {
        Department findDepartmentObj = null;
        try {
            // Getting Session Object From SessionFactory
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            // Getting Transaction Object From Session Object
            sessionObj.beginTransaction();

            findDepartmentObj = (Department) sessionObj.load(Department.class, find_department_id);
        } catch(Exception sqlException) {
            if(null != sessionObj.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
                sessionObj.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        }
        return findDepartmentObj;
    }
    // Method 5: This Method Is Used To Delete All Records From The Database Table
    public static void deleteAllRecords() {
        try {
            // Getting Session Object From SessionFactory
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            // Getting Transaction Object From Session Object
            sessionObj.beginTransaction();

            Query queryObj = sessionObj.createQuery("DELETE FROM Department");
            queryObj.executeUpdate();

            // Committing The Transactions To The Database
            sessionObj.getTransaction().commit();
            // logger.info("\nSuccessfully Deleted All Records From The Database Table!\n");
        } catch(Exception sqlException) {
            if(null != sessionObj.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
                sessionObj.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if(sessionObj != null) {
                sessionObj.close();
            }
        }
    }
}
