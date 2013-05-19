/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.session;

import com.smp.entity.FbsBookingTemp;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author smp11
 */
@Stateless
public class FbsBookingTempFacade extends AbstractFacade<FbsBookingTemp> {
    @PersistenceContext(unitName = "FlatBookingSystemPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public FbsBookingTempFacade() {
        super(FbsBookingTemp.class);
    }

}
