/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.session;

import com.smp.entity.FbsParkingAllot;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author smp7
 */
@Stateless
public class FbsParkingAllotFacade extends AbstractFacade<FbsParkingAllot> {
    @PersistenceContext(unitName = "FlatBookingSystemPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public FbsParkingAllotFacade() {
        super(FbsParkingAllot.class);
    }

}
