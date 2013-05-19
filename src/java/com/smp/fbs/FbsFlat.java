/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.fbs;

import java.util.Date;

/**
 *
 * @author smp
 */

public class FbsFlat {

   private Long flatId;
   private String flatType;
   private String flatTypeSpecification;
    private String flatNo;
   private int blockId;
   private String blockName;
   private int projId;
   private Long fkFloorId;
   private String floorNo;
   private String applicantName;
   private Long floorId;
   private String status;
   private Date bookDate;
  private String bookDate1;
  private Long transfer;

    public void setTransfer(Long transfer) {
        this.transfer = transfer;
    }

    public Long getTransfer() {
        return transfer;
    }
  
   public void setBlockId(int blockId)
    {
        this.blockId=blockId;
    }
   public void setProjId(int projId)
    {
        this.projId=projId;
    }

   public void setFlatId(Long flatId)
    {
        this.flatId=flatId;
    }
    public void setfkFloorId(Long floorId)
    {
        this.fkFloorId=floorId;
    }
   public void setFlatType(String flatType)
    {
        this.flatType=flatType;
    }
    public void setFlatNo(String flatNo)
    {
        this.flatNo=flatNo;
    }
    public void setFloorNo(String floorNo)
    {
        this.floorNo=floorNo;
    }
     public void setApplicantName(String applicantName)
    {
        this.applicantName=applicantName;
    }
      public void setStatus(String status)
    {
        this.status=status;
    }
       public void setBlockName(String blockName)
    {
        this.blockName=blockName;
    }
       public void setFlatTypeSpecification(String flatTypeSpecification)
    {
        this.flatTypeSpecification=flatTypeSpecification;
    }
        public void setFloorId(Long floorId)
    {
        this.floorId=floorId;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public void setBookDate1(String bookDate1) {
        this.bookDate1 = bookDate1;
    }


   
    public int getBlockId()
 {
      return blockId;
 }
    public int getProjId()
 {
      return projId;
 }
 public String getFlatNo()
 {
      return flatNo;
 }
 public String getFloorNo()
 {
      return floorNo;
 }
 public Long getFlatId()
 {
     return flatId;
 }
 public Long getfkFloorId()
 {
     return fkFloorId;
 }
 public String getFlatType()
 {
     return flatType;
 }
  public String getApplicantName( )
    {
         return applicantName;
    }
   public String getStatus()
    {
        return status;
    }
    public String getBlockName( )
    {
        return blockName;
    }
     public String getFlatTypeSpecification( )
    {
       return flatTypeSpecification;
    }
      public Long getFloorId()
    {
        return floorId;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public String getBookDate1() {
        return bookDate1;
    }

    

}
