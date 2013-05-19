package com.smp.validate;


import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.validation.ConstraintViolation;
import javax.faces.validator.Validator;
import javax.validation.metadata.BeanDescriptor;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class EmailValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext,
    UIComponent uIComponent, Object value) throws ValidatorException {

    
    String enteredEmail = (String)value;

    Pattern p = Pattern.compile(".+@.+\\.[a-z]+");

   
    Matcher m = p.matcher(enteredEmail);

   
    boolean matchFound = m.matches();

    if (!matchFound) {
        FacesMessage message = new FacesMessage();
        message.setDetail("Email not valid - The email must be in the format yourname@yourdomain.com");
        message.setSummary("Email not valid - The email must be in the format yourname@yourdomain.com");
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(message);
    }
}



}
