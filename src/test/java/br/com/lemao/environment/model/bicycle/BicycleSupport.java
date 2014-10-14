package br.com.lemao.environment.model.bicycle;

import java.util.List;

import br.com.lemao.environment.hibernate.HibernateUtil;

public final class BicycleSupport {

	public void persist(Bicycle bicycle) {
		HibernateUtil.getCurrentSession().persist(bicycle);
	}

	@SuppressWarnings("unchecked")
	public List<Bicycle> findAll() {
		return HibernateUtil.getCurrentSession().createQuery("From Bicycle").list();
	}

}
