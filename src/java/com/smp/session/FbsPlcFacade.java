/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.session;

import com.smp.entity.FbsPlc;
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
public class FbsPlcFacade {
    @PersistenceContext(unitName = "FlatBookingSystemPU")
    private EntityManager em;

    public void create(FbsPlc fbsPlc) {
        em.persist(fbsPlc);
    }

    public void edit(FbsPlc fbsPlc) {
        em.merge(fbsPlc);
    }

    public void remove(FbsPlc fbsPlc) {
        em.remove(em.merge(fbsPlc));
    }

    public FbsPlc find(Object id) {
        return em.find(FbsPlc.class, id);
    }

    public List<FbsPlc> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(FbsPlc.class));
        return em.createQuery(cq).getResultList();
    }

    public List<FbsPlc> findRange(int[] range) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(FbsPlc.class));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<FbsPlc> rt = cq.from(FbsPlc.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
