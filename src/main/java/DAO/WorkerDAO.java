package DAO;


import entity.Availability;
import entity.Department;
import entity.Worker;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

public class WorkerDAO {

    private static HibernateUtil HibernateUtil;

    public WorkerDAO(HibernateUtil hibernateUtil) {
        WorkerDAO.HibernateUtil = hibernateUtil;
    }

    static Session sessionObj;

    // Method 1: This Method Used To Create A New Worker Record In The Database Table
    public static void createRecord() {

        Worker workerObj = null;

        try {
            // Getting Session Object From SessionFactory
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            // Getting Transaction Object From Session Object
            sessionObj.beginTransaction();


            // Creating Transaction Entities
            for (int j = 1; j <= 5; j++) {

                workerObj = new Worker();
                workerObj.setId(j);
                workerObj.setFullName("Joey" + j);
                workerObj.setAge(j + 20);
                workerObj.setDepartment((Department) sessionObj.get(Department.class, 2));
                workerObj.setAvailability(Availability.FULLTIME);
                sessionObj.save(workerObj);
            }
            workerObj = new Worker();
            workerObj.setId(6);
            workerObj.setFullName("Joey" + 6);
            workerObj.setAge(6 + 20);
            workerObj.setDepartment((Department) sessionObj.get(Department.class, 3));
            workerObj.setAvailability(Availability.PARTTIME);
            sessionObj.save(workerObj);
            // Committing The Transactions To The Database
            sessionObj.getTransaction().commit();
            System.out.println("\nSuccessfully Created Records In The Database!\n");
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
        List workersList = new ArrayList();
        try {
            // Getting Session Object From SessionFactory
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            // Getting Transaction Object From Session Object
            sessionObj.beginTransaction();

            workersList = sessionObj.createQuery("FROM Worker").list();
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
        return workersList;
    }

    // Method 3: This Method Is Used To Update A Record In The Database Table
    public static void updateRecord(int worker_id) {
        try {
            // Getting Session Object From SessionFactory
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            // Getting Transaction Object From Session Object
            sessionObj.beginTransaction();

            // Creating Transaction Entity
            Worker worObj = (Worker) sessionObj.get(Worker.class, worker_id);

            worObj.setFullName("Java Code Geek");
            worObj.setAge(200);
            worObj.setAvailability(Availability.PARTTIME);


            // Committing The Transactions To The Database
            sessionObj.getTransaction().commit();
            System.out.println("\nWorker With Id?= " + worker_id + " Is Successfully Updated In The Database!\n");
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

    // Method 4(a): This Method Is Used To Delete A Particular Record From The Database Table
    public static void deleteRecord(Integer worker_id) {
        try {
            // Getting Session Object From SessionFactory
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            // Getting Transaction Object From Session Object
            sessionObj.beginTransaction();

            Worker workObj = findRecordById(worker_id);
            sessionObj.delete(workObj);

            // Committing The Transactions To The Database
            sessionObj.getTransaction().commit();
            System.out.println("\nWorker With Id?= " + worker_id + " Is Successfully Deleted From The Database!\n");
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

    // Method 4(b): This Method To Find Particular Record In The Database Table
    public static Worker findRecordById(Integer find_worker_id) {
        Worker findWorkerObj = null;
        try {
            // Getting Session Object From SessionFactory
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            // Getting Transaction Object From Session Object
            sessionObj.beginTransaction();

            findWorkerObj = (Worker) sessionObj.load(Worker.class, find_worker_id);
        } catch (Exception sqlException) {
            if (null != sessionObj.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
                sessionObj.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        }
        return findWorkerObj;
    }

    // Method 5: This Method Is Used To Delete All Records From The Database Table
    public static void deleteAllRecords() {
        try {
            // Getting Session Object From SessionFactory
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            // Getting Transaction Object From Session Object
            sessionObj.beginTransaction();

            Query queryObj = sessionObj.createQuery("DELETE FROM Worker");
            queryObj.executeUpdate();

            // Committing The Transactions To The Database
            sessionObj.getTransaction().commit();
            System.out.println("\nSuccessfully Deleted All Records From The Database Table!\n");
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


    /*public static List<Worker> getWorkersByDepartments(int department_id) {
        sessionObj = HibernateUtil.getSessionFactory().openSession();
        // Getting Transaction Object From Session Object
        sessionObj.beginTransaction();

        String hql = "FROM Worker where department_id = :paramName";
        Query query = sessionObj.createQuery(hql);

        query.setParameter("paramName", department_id);
        List<Worker> workers = query.list();
        return workers;
    }*/
    public static List<Worker> getWorkersByAvailability(Availability availability,int dep_id) {
        sessionObj = HibernateUtil.getSessionFactory().openSession();
        // Getting Transaction Object From Session Object
        sessionObj.beginTransaction();

        String hql = "FROM Worker where availability = :paramName AND  department_id = :paramName1 ";
        Query query = sessionObj.createQuery(hql);

        query.setParameter("paramName", availability);
        query.setParameter("paramName1", dep_id);
        List<Worker> workers = query.list();
        return workers;
    }
    public static List getWorkersByAvailabilityAPI(Availability availability,int dep_id) {
        sessionObj = HibernateUtil.getSessionFactory().openSession();
        // Getting Transaction Object From Session Object
        sessionObj.beginTransaction();
        return sessionObj.createCriteria(Worker.class).add(Restrictions.eq("availability", availability))
                .createCriteria("department").add(Restrictions.eq("id", dep_id))
                .list();


    }


  /*  public static void getWorkersByDepartmentsAPI(int department_id) {
        sessionObj = HibernateUtil.getSessionFactory().openSession();
        // Getting Transaction Object From Session Object
        sessionObj.beginTransaction();
      sessionObj.createCriteria(Worker.class).createCriteria("department").add(Restrictions.eq("id", 3))
               .list()
               .forEach(System.out::println);


    }*/
}