import DAO.HibernateUtil;
import DAO.WorkerDAO;
import entity.Department;
import entity.Worker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
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
        List heroes = session.createQuery("FROM Worker").list();
        //SuperHeroRepository heroRepository = new SuperHeroRepository(session);
        assertNull(heroes);
    }

    @Test
    public void createReturnsWorkersWithMatchingSize() {
        WorkerDAO workerDAO = new WorkerDAO(hibernateUtil);
        workerDAO.createRecord();
        List heroes = session.createQuery("FROM Worker").list();
        assertNotNull(heroes);
        assertEquals(6, heroes.size());

    }

    @Test
    public void displayRecordsReturnsWorkersWithMatchingSizeFaulty() {
        WorkerDAO workerDAO = new WorkerDAO(hibernateUtil);
        List workers = workerDAO.displayRecords();
        assertEquals(6,workers.size());
    }
    @Test
    public void displayRecordsReturnsWorkersWithMatchingSize() {

        WorkerDAO workerDAO = new WorkerDAO(hibernateUtil);
        workerDAO.createRecord();
        List workers = workerDAO.displayRecords();
        assertEquals(6,workers.size());
    }

    @Test
    public void updateRecordUpdatesChosenEntry(){
        WorkerDAO workerDAO = new WorkerDAO(hibernateUtil);
        workerDAO.createRecord();
        workerDAO.updateRecord(1);
        List<Worker> workers = session.createQuery("FROM Worker").list();
        assertEquals("Java Code Geek", workers.get(0).getFullName());

    }

    @Test
    public void whenDeleteIsCalledWorkerIsDeleted(){
        WorkerDAO workerDAO = new WorkerDAO(hibernateUtil);
        workerDAO.createRecord();
        workerDAO.deleteRecord(1);
        List<Worker> workers = session.createQuery("FROM Worker").list();
        assertEquals(5,workers.size());

    }
}