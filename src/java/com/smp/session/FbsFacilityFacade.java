/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.session;

import com.smp.entity.FbsFacility;
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
public class FbsFacilityFacade {
    @PersistenceContext(unitName = "FlatBookingSystemPU")
    private EntityManager em;

    public void create(FbsFacility fbsFacility) {
        em.persist(fbsFacility);
    }

    public void edit(FbsFacility fbsFacility) {
        em.merge(fbsFacility);
    }

    public void remove(FbsFacility fbsFacility) {
        em.remove(em.merge(fbsFacility));
    }

    public FbsFacility find(Object id) {
        return em.find(FbsFacility.class, id);
    }

    public List<FbsFacility> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(FbsFacility.class));
        return em.createQuery(cq).getResultList();
    }

    public List<FbsFacility> findRange(int[] range) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(FbsFacility.class));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<FbsFacility> rt = cq.from(FbsFacility.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
