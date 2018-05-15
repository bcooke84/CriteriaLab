package db;

import models.Child;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;


public class DBChild {

    private static Transaction transaction;
    private static Session session;

    public static void save(Child child) {

        session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.save(child);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public static List<Child> getAll(){
        session = HibernateUtil.getSessionFactory().openSession();
        List<Child> children = null;
        try { Criteria cr = session.createCriteria(Child.class);
            children = cr.list();
        }catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        } return children;
    }

    public static Child findByName(String name){
        session = HibernateUtil.getSessionFactory().openSession();
        Child result = null;
        try { Criteria cr = session.createCriteria(Child.class);
            cr.add(Restrictions.ilike("name", name));
            result = (Child)cr.uniqueResult();
        }catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        } return result;
    }

    public static List<Child> sortChildren(){
        session = HibernateUtil.getSessionFactory().openSession();
        List<Child> sortedChildren = null;
        try {Criteria cr = session.createCriteria(Child.class);
            cr.addOrder(Order.asc("age"));
            sortedChildren = cr.list();
        } catch(HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        } return sortedChildren;
    }

    public static List<Child> rangeList(String range) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Child> rangeList = null;
        try { Criteria cr = session.createCriteria(Child.class);
            cr.add(Restrictions.eq("range", range));
            rangeList = cr.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return rangeList;
    }

    public static void update(Child child){
        session = HibernateUtil.getSessionFactory().openSession();
        try{ transaction = session.beginTransaction();
        session.update(child);
        transaction.commit();
        } catch(HibernateException e){
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


}

