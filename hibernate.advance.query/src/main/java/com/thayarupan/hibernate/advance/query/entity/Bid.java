package com.thayarupan.hibernate.advance.query.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Bid {
	
	  	@Id
	    @GeneratedValue(generator = "ID_GENERATOR")
	    protected Long id;

	    @ManyToOne(fetch = FetchType.LAZY) 
	    protected Item item;

	    @NotNull
	    protected BigDecimal amount;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Item getItem() {
			return item;
		}

		public void setItem(Item item) {
			this.item = item;
		}

		public BigDecimal getAmount() {
			return amount;
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}

		@Override
		public String toString() {
			return "Bid [id=" + id + ", item=" + item.getId() + ", amount=" + amount + "]";
		}

		public Bid( @NotNull BigDecimal amount, Item item) {
			super();
			this.item = item;
			this.amount = amount;
		}  
		
		public Bid() {
		}  
	    
}
