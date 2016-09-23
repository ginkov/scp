package com.jiayun.scp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 选择已有的套装作为新套装的模板.
 * @author xinyin
 *
 */
@Entity
@Table(name="prod_combo_template")
public class ProdComboTemplate {
	
	@Id
	@Column(name="id")
	private Integer id;
	
	@OneToOne
	@JoinColumn(name="combo_id")
	private ProdSelling ps;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public ProdSelling getPs() {
		return ps;
	}
	public void setPs(ProdSelling ps) {
		this.ps = ps;
	}
}
