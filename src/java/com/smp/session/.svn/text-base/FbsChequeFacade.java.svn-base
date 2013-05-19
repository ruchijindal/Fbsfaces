/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.session;

import com.smp.entity.FbsCheque;
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
public class FbsChequeFacade {
    @PersistenceContext(unitName = "FlatBookingSystemPU")
    private EntityManager em;

    public void create(FbsCheque fbsCheque) {
        em.persist(fbsCheque);
    }

    public void edit(FbsCheque fbsCheque) {
        em.merge(fbsCheque);
    }

    public void remove(FbsCheque fbsCheque) {
        em.remove(em.merge(fbsCheque));
    }

    public FbsCheque find(Object id) {
        return em.find(FbsCheque.class, id);
    }

    public List<FbsCheque> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(FbsCheque.class));
        return em.createQuery(cq).getResultList();
    }

    public List<FbsCheque> findRange(int[] range) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(FbsCheque.class));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<FbsCheque> rt = cq.from(FbsCheque.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
