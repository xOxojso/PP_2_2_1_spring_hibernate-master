package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.query.QueryParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUserByCarModelAndSeries(String model, int series) {
//      Query<Car> query = sessionFactory
//              .getCurrentSession()
//              .createQuery("FROM Car car LEFT OUTER JOIN FETCH car.user WHERE car.model=:model AND car.series=:series", Car.class);
//      return query.uniqueResult().getUser();
        try (Session session = sessionFactory.openSession()) {
            Car car = session.createQuery("FROM Car WHERE user.car.model=:model AND user.car.series=:series", Car.class)
                    .setParameter("model", model).setParameter("series", series).uniqueResult();
            return car.getUser();
        }
    }

}
