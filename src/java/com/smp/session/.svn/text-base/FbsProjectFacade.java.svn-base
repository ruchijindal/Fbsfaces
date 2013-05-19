/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.session;

import com.smp.entity.FbsProject;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author smp
 */
@Stateless
public class FbsProjectFacade extends AbstractFacade<FbsProject> {
    @PersistenceContext(unitName = "FlatBookingSystemPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public FbsProjectFacade() {
        super(FbsProject.class);
    }

}
