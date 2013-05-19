/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.session;

import com.smp.entity.FbsLogin;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author smp
 */
//@ManagedBean(name="fbsLoginFacade")
//@ApplicationScoped
@Stateless
public class FbsLoginFacade {
    @PersistenceContext(unitName = "FlatBookingSystemPU")
    private EntityManager em;

    public void create(FbsLogin fbsLogin) {
        em.persist(fbsLogin);
    }

    public void edit(FbsLogin fbsLogin) {
        em.merge(fbsLogin);
    }

    public void remove(FbsLogin fbsLogin) {
        em.remove(em.merge(fbsLogin));
    }

    public FbsLogin find(Object id) {
        return em.find(FbsLogin.class, id);
    }

    public List<FbsLogin> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(FbsLogin.class));
        return em.createQuery(cq).getResultList();
    }

    public List<FbsLogin> findRange(int[] range) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(FbsLogin.class));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<FbsLogin> rt = cq.from(FbsLogin.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
