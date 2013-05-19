/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.validate;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;

/**
 *
 * @author Admin
 */
import javax.faces.validator.*;

public class MobileNumberValidator implements Validator{

    public MobileNumberValidator() {
    }

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String strValue = (String)value;
        if (strValue.length() != 10){
             FacesMessage msg = new FacesMessage();
            msg.setDetail("Mobile number must be of 10 digits");
            msg.setSummary("Mobile number must be of 10 digits");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);

        }
    }
}