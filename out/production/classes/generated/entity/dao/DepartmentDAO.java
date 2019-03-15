
package entity.dao;

import java.io.Serializable;
import java.util.List;
import entity.Department;

public interface DepartmentDAO
    extends Serializable
{


    List<Department> findAll();

    Department findByPK(final Integer id);

}
