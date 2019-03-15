
package entity.dao;

import java.io.Serializable;
import java.util.List;
import entity.Worker;

public interface WorkerDAO
    extends Serializable
{


    List<Worker> findAll();

    Worker findByPK(final int id);

}
