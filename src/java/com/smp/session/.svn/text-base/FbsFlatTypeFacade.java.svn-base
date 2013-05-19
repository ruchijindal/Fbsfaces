/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.session;

import com.smp.entity.FbsFlatType;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author smp
 */
//@ManagedBean(name="fbsFlatTypeFacade")
 //@ApplicationScoped
@Stateless
public class FbsFlatTypeFacade {
    @PersistenceContext(unitName = "FlatBookingSystemPU")
    private EntityManager em;

    public void create(FbsFlatType fbsFlatType) {
        em.persist(fbsFlatType);
    }

    public void edit(FbsFlatType fbsFlatType) {
        System.out.println("hello primefaces");
        em.merge(fbsFlatType);
    }
     

    public void remove(FbsFlatType fbsFlatType) {
        em.remove(em.merge(fbsFlatType));
    }

    public FbsFlatType find(Object id) {
        return em.find(FbsFlatType.class, id);
    }

    public List<FbsFlatType> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(FbsFlatType.class));
        return em.createQuery(cq).getResultList();
    }

    public List<FbsFlatType> findRange(int[] range) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(FbsFlatType.class));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<FbsFlatType> rt = cq.from(FbsFlatType.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
