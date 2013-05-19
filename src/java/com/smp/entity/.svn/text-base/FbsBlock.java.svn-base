/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smp.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author smp7
 */
@Entity
@Table(name = "fbs_block")
@NamedQueries({
    @NamedQuery(name = "FbsBlock.findAll", query = "SELECT f FROM FbsBlock f"),
    @NamedQuery(name = "FbsBlock.findByBlockId", query = "SELECT f FROM FbsBlock f WHERE f.blockId = :blockId"),
    @NamedQuery(name = "FbsBlock.findByBlockName", query = "SELECT f FROM FbsBlock f WHERE f.blockName = :blockName"),
    @NamedQuery(name = "FbsBlock.findByFkProjId", query = "SELECT f FROM FbsBlock f WHERE f.fkProjId = :fkProjId"),
    @NamedQuery(name = "FbsBlock.findByImagePath", query = "SELECT f FROM FbsBlock f WHERE f.imagePath = :imagePath"),
    @NamedQuery(name = "FbsBlock.findByStatus", query = "SELECT f FROM FbsBlock f WHERE f.status = :status"),
    @NamedQuery(name = "FbsBlock.findByProjId&Status", query = "SELECT f FROM FbsBlock f WHERE f.status = :status and f.fkProjId = :fkProjId")})
public class FbsBlock implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BLOCK_ID")
    private Integer blockId;
    @Basic(optional = false)
    @Column(name = "BLOCK_NAME")
    private String blockName;
    @Basic(optional = false)
    @Column(name = "FK_PROJ_ID")
    private long fkProjId;
    @Column(name = "IMAGE_PATH")
    private String imagePath;
    @Lob
    @Column(name = "XML_FILE")
    private String xmlFile;
    @Column(name = "STATUS")
    private String status;

    public FbsBlock() {
    }

    public FbsBlock(Integer blockId) {
        this.blockId = blockId;
    }

    public FbsBlock(Integer blockId, String blockName, long fkProjId) {
        this.blockId = blockId;
        this.blockName = blockName;
        this.fkProjId = fkProjId;
    }

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public long getFkProjId() {
        return fkProjId;
    }

    public void setFkProjId(long fkProjId) {
        this.fkProjId = fkProjId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getXmlFile() {
        return xmlFile;
    }

    public void setXmlFile(String xmlFile) {
        this.xmlFile = xmlFile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (blockId != null ? blockId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FbsBlock)) {
            return false;
        }
        FbsBlock other = (FbsBlock) object;
        if ((this.blockId == null && other.blockId != null) || (this.blockId != null && !this.blockId.equals(other.blockId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smp.entity.FbsBlock[blockId=" + blockId + "]";
    }

}
