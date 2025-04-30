{\rtf1\ansi\ansicpg1252\cocoartf2822
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\paperw11900\paperh16840\margl1440\margr1440\vieww11520\viewh8400\viewkind0
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\f0\fs24 \cf0 package com.example.service;\
\
import com.example.model.Accessories;\
import com.example.model.AccessoriesCategory;\
import com.example.model.Supplier;\
import javax.persistence.EntityManager;\
import javax.persistence.PersistenceContext;\
import javax.persistence.TypedQuery;\
import java.util.List;\
\
public class AccessoriesService \{\
\
    @PersistenceContext\
    private EntityManager em;\
\
    public List<Accessories> findAll() \{\
        TypedQuery<Accessories> query = em.createQuery("SELECT a FROM Accessories a", Accessories.class);\
        return query.getResultList();\
    \}\
\
    public List<AccessoriesCategory> findAllCategories() \{\
        TypedQuery<AccessoriesCategory> query = em.createQuery("SELECT c FROM AccessoriesCategory c", AccessoriesCategory.class);\
        return query.getResultList();\
    \}\
\
    public List<Supplier> findAllSuppliers() \{\
        TypedQuery<Supplier> query = em.createQuery("SELECT s FROM Supplier s", Supplier.class);\
        return query.getResultList();\
    \}\
\
    public List<Accessories> search(String code, String category, String supplier) \{\
        StringBuilder jpql = new StringBuilder("SELECT a FROM Accessories a WHERE 1=1");\
        if (code != null && !code.isEmpty()) \{\
            jpql.append(" AND a.accessoriesTpKey LIKE :code");\
        \}\
        if (category != null && !category.isEmpty()) \{\
            jpql.append(" AND a.category.accessoriesCategoryTpName LIKE :category");\
        \}\
        if (supplier != null && !supplier.isEmpty()) \{\
            jpql.append(" AND a.supplier.supplierCode LIKE :supplier");\
        \}\
\
        TypedQuery<Accessories> query = em.createQuery(jpql.toString(), Accessories.class);\
        if (code != null && !code.isEmpty()) \{\
            query.setParameter("code", "%" + code + "%");\
        \}\
        if (category != null && !category.isEmpty()) \{\
            query.setParameter("category", "%" + category + "%");\
        \}\
        if (supplier != null && !supplier.isEmpty()) \{\
            query.setParameter("supplier", "%" + supplier + "%");\
        \}\
\
        return query.getResultList();\
    \}\
\}\
}