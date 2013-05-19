/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.session;

import com.smp.entity.FbsBrokerCat;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author smp
 */
@Stateless
public class FbsBrokerCatFacade extends AbstractFacade<FbsBrokerCat> {
    @PersistenceContext(unitName = "FlatBookingSystemPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public FbsBrokerCatFacade() {
        super(FbsBrokerCat.class);
    }

}
