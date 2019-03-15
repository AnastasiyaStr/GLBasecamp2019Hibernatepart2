import DAO.DepartmentDAO;
import DAO.HibernateUtil;
import DAO.WorkerDAO;
import entity.Availability;
import entity.Department;
import entity.Worker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AppMainTest {
    private SessionFactory sessionFactory;

    private Session session = null;
    private HibernateUtil hibernateUtil;
    @Before

    public void before() {

        // setup the session factory

        AnnotationConfiguration configuration = new AnnotationConfiguration();

        configuration.addAnnotatedClass(Department.class)

                .addAnnotatedClass(Worker.class);


        configuration.setProperty("hibernate.dialect",

                "org.hibernate.dialect.H2Dialect");

        configuration.setProperty("hibernate.connection.driver_class",

                "org.h2.Driver");

        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem");

        configuration.setProperty("hibernate.hbm2ddl.auto", "create");


        sessionFactory = configuration.buildSessionFactory();

        session = sessionFactory.openSession();
        hibernateUtil = new HibernateUtil(sessionFactory);
    }
    @Test
    public void createReturnsWorkersWithMatchingSize1() {

        WorkerDAO workerDAO = new WorkerDAO(hibernateUtil);
        workerDAO.createRecord();
        List heroes = workerDAO.displayRecords();
        //SuperHeroRepository heroRepository = new SuperHeroRepository(session);
        assertNull(heroes);
    }

    @Test
    public void createReturnsWorkersWithMatchingSize() {
        WorkerDAO workerDAO = new WorkerDAO(hibernateUtil);
        workerDAO.createRecord();
        List heroes = workerDAO.displayRecords();
        assertNotNull(heroes);
        assertEquals(6, heroes.size());

    }

    @Test
    public void displayRecordsReturnsWorkersWithMatchingSizeFaulty() {
        WorkerDAO workerDAO = new WorkerDAO(hibernateUtil);
        workerDAO.createRecord();
        List workers = workerDAO.displayRecords();
        assertEquals(6,workers.size());
    }
    @Test
    public void displayRecordsReturnsWorkersWithMatchingSize() {

        WorkerDAO workerDAO = new WorkerDAO(hibernateUtil);
        workerDAO.createRecord();
        List workers = workerDAO.displayRecords();
        assertEquals(5,workers.size());
    }

    @Test
    public void updateRecordUpdatesChosenEntryFaulty(){
        WorkerDAO workerDAO = new WorkerDAO(hibernateUtil);
        workerDAO.createRecord();
        workerDAO.updateRecord(1);
        List<Worker> workers = workerDAO.displayRecords();
        assertNull( workers);

    }
    @Test
    public void updateRecordUpdatesChosenEntry(){
        WorkerDAO workerDAO = new WorkerDAO(hibernateUtil);
        workerDAO.createRecord();
        workerDAO.updateRecord(1);
        List<Worker> workers = workerDAO.displayRecords();
        assertEquals("Java Code Geek", workers.get(0).getFullName());

    }

    @Test
    public void whenDeleteIsCalledWorkerIsDeleted(){
        WorkerDAO workerDAO = new WorkerDAO(hibernateUtil);
        workerDAO.createRecord();
        workerDAO.deleteRecord(1);
        List<Worker> workers = workerDAO.displayRecords();
        assertEquals(5,workers.size());
    }



    @Test
    public void whenGetDepartmentByStatusIsCalledEntriesAreReturnedFaulty() {
        DepartmentDAO departmentDAO = new DepartmentDAO(hibernateUtil);
        departmentDAO.createRecord();
        List<Department> department = departmentDAO.getDepartmentsByStatus(true);
        assertEquals(false,department.get(0).getStatus());

    }
    @Test
    public void whenGetDepartmentByStatusAPIIsCalledEntriesAreReturnedFaulty() {
        DepartmentDAO departmentDAO = new DepartmentDAO(hibernateUtil);
        departmentDAO.createRecord();
        List<Department> department = departmentDAO.getDepartmentByStatusAPI(true);
        assertEquals(false,department.get(0).getStatus());

    }
    @Test
    public void whenGetDepartmentByStatusAPIIsCalledEntriesAreReturned() {
        DepartmentDAO departmentDAO = new DepartmentDAO(hibernateUtil);
        departmentDAO.createRecord();
        List<Department> department = departmentDAO.getDepartmentByStatusAPI(false);
        assertEquals(false,department.get(0).getStatus());

    }
    @Test
    public void whenGetDepartmentByStatusIsCalledEntriesAreReturned() {
       DepartmentDAO departmentDAO = new DepartmentDAO(hibernateUtil);
        departmentDAO.createRecord();
        List<Department> department = departmentDAO.getDepartmentsByStatus(false);
        assertEquals(false,department.get(0).getStatus());

    }
    @Test
    public void whenGetWorkerByAvailabilityAndDepartmentIsCalledEntriesAreReturnedFaulty() {
        WorkerDAO workerDAO = new WorkerDAO(hibernateUtil);
        DepartmentDAO departmentDAO = new DepartmentDAO(hibernateUtil);
        departmentDAO.createRecord();
        workerDAO.createRecord();
        List<Worker> worker = workerDAO.getWorkersByAvailability(Availability.PARTTIME,3);
        assertNull(worker);

    }
    @Test
    public void whenGetWorkerByAvailabilityAndDepartmentAPIIsCalledEntriesAreReturnedFaulty() {
        WorkerDAO workerDAO = new WorkerDAO(hibernateUtil);
        DepartmentDAO departmentDAO = new DepartmentDAO(hibernateUtil);
        departmentDAO.createRecord();
        workerDAO.createRecord();
        List<Worker> worker = workerDAO.getWorkersByAvailabilityAPI(Availability.PARTTIME,3);
        assertNull(worker);

    }
    @Test
    public void whenGetWorkerByAvailabilityAndDepartmentAPIIsCalledEntriesAreReturned() {
        WorkerDAO workerDAO = new WorkerDAO(hibernateUtil);
        DepartmentDAO departmentDAO = new DepartmentDAO(hibernateUtil);
        departmentDAO.createRecord();
        workerDAO.createRecord();
        List<Worker> worker = workerDAO.getWorkersByAvailabilityAPI(Availability.PARTTIME,3);
        assertEquals(1,worker.size());

    }
    @Test
    public void whenGetWorkerByAvailabilityAndDepartmentIsCalledEntriesAreReturned() {
       WorkerDAO workerDAO = new WorkerDAO(hibernateUtil);
       DepartmentDAO departmentDAO = new DepartmentDAO(hibernateUtil);
        departmentDAO.createRecord();
        workerDAO.createRecord();
        List<Worker> worker = workerDAO.getWorkersByAvailability(Availability.PARTTIME,3);
        assertEquals(1,worker.size());

    }
    @After

    public void after() {

        session.close();

        sessionFactory.close();

    }
}