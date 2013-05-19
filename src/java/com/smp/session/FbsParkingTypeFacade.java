/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.session;

import com.smp.entity.FbsParkingType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author smp
 */
@Stateless
public class FbsParkingTypeFacade extends AbstractFacade<FbsParkingType> {
    @PersistenceContext(unitName = "FlatBookingSystemPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public FbsParkingTypeFacade() {
        super(FbsParkingType.class);
    }

}
