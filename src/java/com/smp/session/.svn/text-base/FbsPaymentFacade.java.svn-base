/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.session;

import com.smp.entity.FbsPayment;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author smp
 */
@Stateless
public class FbsPaymentFacade {
    @PersistenceContext(unitName = "FlatBookingSystemPU")
    private EntityManager em;

    public void create(FbsPayment fbsPayment) {
        em.persist(fbsPayment);
    }

    public void edit(FbsPayment fbsPayment) {
        em.merge(fbsPayment);
    }

    public void remove(FbsPayment fbsPayment) {
        em.remove(em.merge(fbsPayment));
    }

    public FbsPayment find(Object id) {
        return em.find(FbsPayment.class, id);
    }

    public List<FbsPayment> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(FbsPayment.class));
        return em.createQuery(cq).getResultList();
    }

    public List<FbsPayment> findRange(int[] range) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(FbsPayment.class));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<FbsPayment> rt = cq.from(FbsPayment.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
