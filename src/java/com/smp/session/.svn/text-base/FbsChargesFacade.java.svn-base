/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.session;

import com.smp.entity.FbsCharges;
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
public class FbsChargesFacade {
    @PersistenceContext(unitName ="FlatBookingSystemPU")
    private EntityManager em;

    public void create(FbsCharges fbsCharges) {
        em.persist(fbsCharges);
    }

    public void edit(FbsCharges fbsCharges) {
        em.merge(fbsCharges);
    }

    public void remove(FbsCharges fbsCharges) {
        em.remove(em.merge(fbsCharges));
    }

    public FbsCharges find(Object id) {
        return em.find(FbsCharges.class, id);
    }

    public List<FbsCharges> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(FbsCharges.class));
        return em.createQuery(cq).getResultList();
    }

    public List<FbsCharges> findRange(int[] range) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(FbsCharges.class));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<FbsCharges> rt = cq.from(FbsCharges.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
