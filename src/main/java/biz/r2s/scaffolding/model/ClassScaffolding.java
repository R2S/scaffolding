package biz.r2s.scaffolding.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import biz.r2s.core.crud.model.BaseModel;

@Entity
@Table(uniqueConstraints = 	@UniqueConstraint(columnNames = { "clazz" }))
public class ClassScaffolding implements BaseModel<Long>{
	@Id
	@GeneratedValue
	Long id;
	String clazz;
	String json;
	boolean enable;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClazz() {
		return clazz;
	}
	
	public Class getClassScaffolding(){
		try{
			return Class.forName(clazz);
		}catch(Exception e){
			return null;
		}
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}	
}
