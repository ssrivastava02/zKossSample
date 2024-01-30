package myzkProto1.model;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import myzkProto1.util.JpaUtil;

public class UserDao {

	private SessionFactory sessionFactory;

	public UserDao() {
		this.sessionFactory = JpaUtil.getSessionFactory();
	}

	public void createUser(User user) {
		try (Session session = sessionFactory.openSession())
		{
			session.beginTransaction();
			session.persist(user);
			session.getTransaction().commit();
		}
	}

	public void deleteUser(long userId) {
		try (Session session = sessionFactory.openSession())
		{
			session.beginTransaction();
			User user = session.get(User.class, userId);
			if (user != null) {
				session.remove(user);
			}
			session.getTransaction().commit();
		}
	}

	public void updateUser(User updatedUser) {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			User existingUser = session.get(User.class, updatedUser.getId());
			if (existingUser != null) {
				// Update user properties with the new values
				existingUser.setFirstName(updatedUser.getFirstName());
				existingUser.setLastName(updatedUser.getLastName());
				existingUser.setEmail(updatedUser.getEmail());
				existingUser.setPhone(updatedUser.getPhone());
				existingUser.setDOB(updatedUser.getDOB());
				existingUser.setAddress(updatedUser.getAddress());

				// Save the updated user entity
				session.merge(existingUser);
			}
			session.getTransaction().commit();
		}
	}
}