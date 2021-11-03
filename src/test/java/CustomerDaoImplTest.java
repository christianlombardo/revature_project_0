import com.company.Customer;
import com.company.CustomerDaoImpl;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class CustomerDaoImplTest {

    @Test
    public void addUserTest() throws SQLException {
        // Arrange
        CustomerDaoImpl userDao = new CustomerDaoImpl();
        Customer customer = new Customer();
        customer.setName("test3");
        customer.setUsername("test3");
        customer.setPassword("1");

        boolean actualResult = false;

        // Act
        try {
            actualResult = userDao.addUser(customer);
        }
        catch (SQLException e) {
            System.out.println(e.getStackTrace());
        }

        // Assert
        assertEquals(actualResult, true);

    }

}
