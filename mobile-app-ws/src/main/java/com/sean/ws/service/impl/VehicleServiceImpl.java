package com.sean.ws.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sean.ws.io.entity.AddressEntity;
import com.sean.ws.io.entity.UserEntity;
import com.sean.ws.io.entity.VehicleEntity;
import com.sean.ws.service.VehicleService;
import com.sean.ws.shared.HibernateUtil;
import com.sean.ws.shared.dto.VehicleDto;

@Service
@Transactional(rollbackFor = Exception.class)
public class VehicleServiceImpl implements VehicleService {

	@Autowired
	ModelMapper modelMapper;

	@Value("${spring.profiles.active:Unknown}")
	private String activeProfile;

	@Override
	public UserEntity createVehicle(VehicleDto vehicle) {

		// VehicleEntity vehicleEntity = modelMapper.map(vehicle, VehicleEntity.class);

		// hibernate cfg
		/*
		 * Configuration con = new Configuration().configure()
		 * .addAnnotatedClass(UserEntity.class) .addAnnotatedClass(AddressEntity.class);
		 * 
		 * StandardServiceRegistryBuilder serviceRegistryBuilder = new
		 * StandardServiceRegistryBuilder();
		 * serviceRegistryBuilder.applySettings(con.getProperties());
		 * 
		 * ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
		 * 
		 * SessionFactory sf = con.buildSessionFactory(serviceRegistry); Session session
		 * = sf.openSession();
		 * 
		 * Transaction tx = session.beginTransaction(); //VehicleDto returnValue =
		 * (VehicleDto) session.save(vehicleEntity); UserEntity userEntity =
		 * session.get(UserEntity.class, 2L); tx.commit();
		 * 
		 * return userEntity;
		 */
		/*
		 * EntityManagerFactory entityManagerFactory =
		 * Persistence.createEntityManagerFactory("persistence"); EntityManager
		 * entityManager = entityManagerFactory.createEntityManager();
		 * 
		 * entityManager.getTransaction().begin(); entityManager.persist(vehicleEntity);
		 * entityManager.getTransaction().commit();
		 * 
		 * return null;
		 */
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			String hql = "select user from UserEntity user where user.id= :id";
			// Transaction tx = session.beginTransaction();
			Object obj = session.createQuery(hql).setParameter("id", 2l).uniqueResult();
			// tx.commit();
			UserEntity userEntity = modelMapper.map(obj, UserEntity.class);
			return userEntity;

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public VehicleDto storeVehicle(VehicleEntity vehicle) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			Transaction tx = session.beginTransaction();
			session.save(vehicle);
			tx.commit();
			VehicleDto vehicleDto = modelMapper.map(vehicle, VehicleDto.class);
			return vehicleDto;

		} catch (HibernateException e) {
			throw new HibernateException(e);
		}
		// return null;
	}

	@Override
	public List<Object> listObject() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			//String hql = "select user from UserEntity user";
			String sql = "select u.first_name,u.last_name,a.city,a.country from users u left join addresses a on u.id=a.user_id";
			//, addresses a where u.id=a.user_id
			// Transaction tx = session.beginTransaction();
			//Stream<Object> objs = session.createNativeQuery(sql).stream();
			List<Object> objs = session.createNativeQuery(sql).list();
			//Object[] obj = (Object[]) objs.get(0);
			//System.out.println(obj[0]);
			
			for(Object obj : objs){
				Object[] objAry = (Object[]) obj;
				Stream<Object> stream = Stream.of(objAry);
				stream.forEach(System.out::print);
			 }
			
			
			List<Object> cobjs = new ArrayList<>();
			cobjs.add("aa");
			cobjs.add("bb");
			
			//String aa = ReflectionToStringBuilder.toString(listString);
			//System.out.println(cobjs.toString());
			//System.out.println(objs.getClass().getName());
			/*
			Iterator<Object> i = objs.iterator();
			while (i.hasNext()) {
		        System.out.println(i.next());
		    }
		    */
			//System.out.println(Arrays.toString(objs));
			
			return objs;

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String listToString(List<?> list) {
	    String result = "+";
	    for (int i = 0; i < list.size(); i++) {
	        result += " " + list.get(i);
	    }
	    return result;
	}

}
