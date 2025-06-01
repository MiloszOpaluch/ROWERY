package com.example.rowery;

import com.example.rowery.klasy.klient;
import com.example.rowery.klasy.pracownicy;
import com.example.rowery.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

import  com.example.rowery.HibernateUtil;

public class Test {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();



        String hql = "select imie,nazwisko,login,haslo FROM pracownicy";
        List<Object[]> wynik = session.createQuery(hql).getResultList();

        for (Object[] row : wynik) {
            String imie = (String) row[0];
            String nazwisko = (String) row[1];
            String login = (String) row[2];
            String haslo = (String) row[3];
            System.out.println(imie + " " + nazwisko + " "+ login + " " + haslo);
        }
//        session.beginTransaction();
//       klient klient = new klient("Milosz","Opaluch","klient6","klient");
//        session.persist(klient);
//        session.getTransaction().commit();



        session.close();
















    }}


