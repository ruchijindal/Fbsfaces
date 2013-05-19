/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.report;

import com.smp.entity.FbsApplicant;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ApplicantDetails {

    @PersistenceContext(unitName = "FlatBookingSystemPU")
    EntityManager entityManager;
    String Applicant_name = "";
    String applicant_id = "";
    String telephone_no = "";
    String mobile = "";
    String email = "";
    String res_add = "";
    String pan_no = "";
    String Applicant_name2 = "";
    String pan_no2 = "";
    String applicant_id2 = "";
    String telephone_no2 = "";
    String mobile2 = "";
    String email2 = "";
    String res_add2 = "";

    public ApplicantDetails findApplicantDetails(String unitCode) {

        List<FbsApplicant> applicantList = entityManager.createNamedQuery("FbsApplicant.findByFlatId").setParameter("flatId",Integer.parseInt( unitCode)).getResultList();//get Applicant and CoApplicant List
        for (int i = applicantList.size()-2; i < applicantList.size(); i++) {
            if ((applicantList.get(i).getApplicantFlag() == 1)) {

                applicant_id = applicantList.get(i).getApplicantId().toString();
                if (applicant_id == null) {
                    applicant_id = " ";
                }
                Applicant_name = applicantList.get(i).getApplicantName();
                if (Applicant_name == null) {
                    Applicant_name = " ";
                }
                pan_no = applicantList.get(i).getPanNo();
                if (pan_no == null) {
                    pan_no = " ";
                }
                telephone_no = applicantList.get(i).getTelephone();
                if (telephone_no == null) {
                    telephone_no = " ";
                }
                mobile = applicantList.get(i).getMobile();
                if (mobile == null) {
                    mobile = " ";
                }
                email = applicantList.get(i).getEmail();
                if (email == null) {
                    email = " ";
                }
                res_add = applicantList.get(i).getResAdd();
                if (res_add == null) {
                    res_add = " ";
                }
                //Paid_amount = applicantList.get(i).getPayableAmt();
            }
            if (applicantList.get(i).getApplicantFlag() == 2) {
                applicant_id2 = applicantList.get(i).getApplicantId().toString();
                if (applicant_id2 == null) {
                    applicant_id2 = " ";
                }
                Applicant_name2 = applicantList.get(i).getApplicantName();
                if (Applicant_name2 == null) {
                    Applicant_name2 = " ";
                }
                pan_no2 = applicantList.get(i).getPanNo();
                if (pan_no2 == null) {
                    pan_no2 = " ";
                }
                telephone_no2 = applicantList.get(i).getTelephone();
                if (telephone_no2 == null) {
                    telephone_no2 = " ";
                }
                mobile2 = applicantList.get(i).getMobile();
                if (mobile2 == null) {
                    mobile2 = " ";
                }
                email2 = applicantList.get(i).getEmail();
                if (email2 == null) {
                    email2 = " ";
                }
                res_add2 = applicantList.get(i).getResAdd();
                if (res_add2 == null) {
                    res_add2 = " ";
                }
            }
        }

        this.Applicant_name = Applicant_name;
        this.Applicant_name2 = Applicant_name2;
        this.applicant_id = applicant_id;
        this.applicant_id2 = applicant_id2;
        this.email = email;
        this.email2 = email2;
        this.mobile = mobile;
        this.mobile2 = mobile2;
        this.pan_no = pan_no;
        this.res_add = res_add;
        this.res_add2 = res_add2;
        this.telephone_no = telephone_no;
        this.telephone_no2 = telephone_no2;
        return this;
    }
}
