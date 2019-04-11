package com.thayarupan.hibernate.advance.query.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Version;

@NamedNativeQueries({ @NamedNativeQuery(name = "findById", query = "SELECT * FROM item WHERE id=?", resultClass = Item.class) })
@org.hibernate.annotations.Loader(namedQuery="findById")
@org.hibernate.annotations.SQLInsert(sql = "insert into Item " + "(id, name) values (?, ?)")
@org.hibernate.annotations.SQLUpdate(sql = "update Item set " + "name = ?, " + "where ID = ?")
@org.hibernate.annotations.SQLDelete(sql = "delete from USERS where ID = ?")
@SqlResultSetMappings({
		@SqlResultSetMapping(name = "ItemResult", entities = @EntityResult(entityClass = Item.class, 
				fields = {
						@FieldResult(name="id", column="ID"),
						@FieldResult(name="name", column="NAME")
		})) })
@Entity
//Hibernate only supports positional parameters
public class Item {

	@Id
	@GeneratedValue(generator = "ID_GENERATOR")
	protected Long id;

	@Version
	protected long version;

	public long getVersion() {
		return version;
	}

	@Basic(fetch = FetchType.LAZY)
	protected String name;

	@OneToMany(mappedBy = "item")
	private Set<Bid> bids = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", bids=" + null + "]";
	}

	public Set<Bid> getBids() {
		return bids;
	}

	public void setBids(Set<Bid> bids) {
		this.bids = bids;
	}

	public Item(String name) {
		super();
		this.name = name;
	}

	public Item() {
		super();
	}

}
