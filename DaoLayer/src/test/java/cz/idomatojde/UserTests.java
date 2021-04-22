package cz.idomatojde;

import cz.idomatojde.dao.UserDao;
import cz.idomatojde.dao.utils.UserContactInfo;
import cz.idomatojde.entity.User;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.Test;

import javax.inject.Inject;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/** User specific DAO tests
 * @author Michal Hazdra
 */
@ContextConfiguration("classpath:applicationConfig.xml")
@DataJpaTest
@EnableAutoConfiguration
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserTests extends AbstractTestNGSpringContextTests {

    @Inject
    public UserDao dao;

    public static User getUser(String username) {
        User user = new User();
        user.setUsername(username);
        user.setPassHash("UGFzc3dvcmQ=");
        user.setPassSalt("U2FsdA==");
        user.setName("Name");
        user.setSurname("Surname");
        user.setPhoneNumber("+420123456789");
        user.setEmail("pepega@mail.com");
        user.setCredits(123);
        user.setWantsAdvertisement(false);
        user.setAdmin(false);

        return user;
    }

    @Test
    public void userCreation() {
        //Setup
        User user = getUser("pepega");

        //Act
        dao.create(user);

        //Validate
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(dao.getById(1L).getUsername()).isEqualTo("pepega");
        assertThat(dao.getById(1L).getPassHash()).isEqualTo("UGFzc3dvcmQ=");
        assertThat(dao.getById(1L).getPassSalt()).isEqualTo("U2FsdA==");
        assertThat(dao.getById(1L).getName()).isEqualTo("Name");
        assertThat(dao.getById(1L).getSurname()).isEqualTo("Surname");
        assertThat(dao.getById(1L).getPhoneNumber()).isEqualTo("+420123456789");
        assertThat(dao.getById(1L).getEmail()).isEqualTo("pepega@mail.com");
        assertThat(dao.getById(1L).getCredits()).isEqualTo(123);
        assertThat(dao.getById(1L).wantsAdvertisement()).isEqualTo(false);
        assertThat(dao.getById(1L).isAdmin()).isEqualTo(false);
    }

    @Test
    public void userCredits() {
        //Setup
        User user = getUser("nootlikethis");

        //Act
        dao.create(user);

        //Validate
        assertThat(dao.getById(1L).getCredits()).isEqualTo(123);

        //Act
        dao.addCredits(1,user.getId());

        //Validate
        assertThat(user.getCredits()).isEqualTo(124);
    }

    @Test
    public void userPhoneNumber() {
        //Setup
        User user = getUser("knockknock");
        final String phone = "+420987654321";

        //Act
        dao.create(user);

        //Validate
        assertThat(dao.getById(1L).getPhoneNumber()).isEqualTo("+420123456789");

        //Act
        dao.addPhone(phone, user.getId());

        //Validate
        assertThat(user.getPhoneNumber()).isEqualTo(phone);
        assertThat(dao.getById(user.getId()).getPhoneNumber()).isEqualTo(phone);
    }

    @Test
    public void userContactInfo() {
        //Setup
        User user = getUser("thisUsedToBeAPublicFieldClass");

        //Act
        dao.create(user);

        UserContactInfo contact = dao.getContactInfo(user.getId());

        //Validate
        assertThat(contact.getName()).isEqualTo("Name");
        assertThat(contact.getSurname()).isEqualTo("Surname");
        assertThat(contact.getEmail()).isEqualTo("pepega@mail.com");
        assertThat(contact.getPhone()).isEqualTo("+420123456789");
    }

    @Test
    public void userUpdate() {
        //Setup
        final String name = "pleaseRestartYourComputerToApplyUpdates";
        User user = getUser(name);

        //Act
        dao.create(user);

        user.setAdmin(true);
        user.setWantsAdvertisement(true);
        user.setCredits(Integer.MAX_VALUE);

        dao.update(user);

        User updated = dao.getById(1L);

        //Validate
        assertThat(updated.getUsername()).isEqualTo(name);
        assertThat(updated.isAdmin()).isEqualTo(true);
        assertThat(updated.wantsAdvertisement()).isEqualTo(true);
        assertThat(updated.getCredits()).isEqualTo(Integer.MAX_VALUE);
    }
}