package com.innovative.bean;

import java.sql.Timestamp;
import java.util.List;

/**
 * 设备
 */
public class Equipment {

    private String id;
    private String contact;//联系人
    private Timestamp createdAt;//创建时间
    private String createdBy;//创建人
    private boolean deleted;//是否删除
    private Timestamp deletedAt;//删除时间
    private String deletedBy;//删除人
    private String introduction;//介绍
    private boolean isActive;//是否显示
    private String manufacturer;//制造商
    private String name;//标题
    private String picture;//图片
    
    private String purchasedAt;//购买时间
    private int rank;
    private int rowVersion;
    private String[] sectors;//领域
    private String sharing;//内部共享说明
    private String state;//状态
    private String[] tags;//标签
    private String unit;//单位
    private Timestamp updatedAt;//更新时间
    private String updatedBy;//更新人
    private String file;//更新人
    
    List<FileBean> filelist ;
    private int fileSize;//
    public Equipment() {
    }


	public Equipment(String id, String contact, Timestamp createdAt, String createdBy, boolean deleted,
			Timestamp deletedAt, String deletedBy, String introduction, boolean isActive, String manufacturer,
			String name, String picture, String purchasedAt, int rank, int rowVersion, String[] sectors, String sharing,
			String state, String[] tags, String unit, Timestamp updatedAt, String updatedBy, String file,
			List<FileBean> filelist) {
		super();
		this.id = id;
		this.contact = contact;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.deleted = deleted;
		this.deletedAt = deletedAt;
		this.deletedBy = deletedBy;
		this.introduction = introduction;
		this.isActive = isActive;
		this.manufacturer = manufacturer;
		this.name = name;
		this.picture = picture;
		this.purchasedAt = purchasedAt;
		this.rank = rank;
		this.rowVersion = rowVersion;
		this.sectors = sectors;
		this.sharing = sharing;
		this.state = state;
		this.tags = tags;
		this.unit = unit;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.file = file;
		this.filelist = filelist;
	}


	public int getFileSize() {
		return filelist==null|| filelist.size()<=0 ? 0 : filelist.size();
	}


	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}


	public List<FileBean> getFilelist() {
		return filelist;
	}




	public void setFilelist(List<FileBean> filelist) {
		this.filelist = filelist;
	}




	public String getFile() {
		return file;
	}



	public void setFile(String file) {
		this.file = file;
	}



	public String[] getSectors() {
		return sectors;
	}



	public void setSectors(String[] sectors) {
		this.sectors = sectors;
	}



	public String[] getTags() {
		return tags;
	}



	public void setTags(String[] tags) {
		this.tags = tags;
	}



	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


    public String getPurchasedAt() {
		return purchasedAt;
	}


	public void setPurchasedAt(String purchasedAt) {
		this.purchasedAt = purchasedAt;
	}



	public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(int rowVersion) {
        this.rowVersion = rowVersion;
    }

   
    public String getSharing() {
        return sharing;
    }

    public void setSharing(String sharing) {
        this.sharing = sharing;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

  

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
