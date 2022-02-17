package com.adam.pokedex.models;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="pokemon")
public class Pokemon {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String name;
	@NotBlank
	private String species;
	private Long pokeNumber;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User owner;
	
// Stats to grab from API so as not to make too many excessive calls
// including picture (which will store URL as string)
	private Long hp;
	private Long spd;
	private Long atk;
	private Long def;
	private Long spAtk;
	private Long spDef;
	private ArrayList<String> types;
	private String pictureUrl;
	
	@Column(updatable = false)
	private Date createdAt;
	private Date updatedAt;
	
	@PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
    
    public Pokemon() {}
	public Pokemon(String name, Long pokeNumber, String species, User owner, Long hp, Long spd, Long atk, Long def,
			Long spAtk, Long spDef, ArrayList<String> types, String pictureUrl) {
		this.pokeNumber = pokeNumber;
		this.name = name;
		this.species = species;
		this.owner = owner;
		this.hp = hp;
		this.spd = spd;
		this.atk = atk;
		this.def = def;
		this.spAtk = spAtk;
		this.spDef = spDef;
		this.types = types;
		this.pictureUrl = pictureUrl;
	}	
	public Pokemon(Long pokeNumber, String species, User owner, Long hp, Long spd, Long atk, Long def, Long spAtk, Long spDef,
			ArrayList<String> types, String pictureUrl) {
		this.pokeNumber = pokeNumber;
		this.name = species;
		this.species = species;
		this.owner = owner;
		this.hp = hp;
		this.spd = spd;
		this.atk = atk;
		this.def = def;
		this.spAtk = spAtk;
		this.spDef = spDef;
		this.types = types;
		this.pictureUrl = pictureUrl;
	}
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
	public String getSpecies() {
		return species;
	}
	public void setSpecies(String species) {
		this.species = species;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public Long getHp() {
		return hp;
	}
	public void setHp(Long hp) {
		this.hp = hp;
	}
	public Long getSpd() {
		return spd;
	}
	public void setSpd(Long spd) {
		this.spd = spd;
	}
	public Long getAtk() {
		return atk;
	}
	public void setAtk(Long atk) {
		this.atk = atk;
	}
	public Long getDef() {
		return def;
	}
	public void setDef(Long def) {
		this.def = def;
	}
	public Long getSpAtk() {
		return spAtk;
	}
	public void setSpAtk(Long spAtk) {
		this.spAtk = spAtk;
	}
	public Long getSpDef() {
		return spDef;
	}
	public void setSpDef(Long spDef) {
		this.spDef = spDef;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public ArrayList<String> getTypes() {
		return types;
	}
	public void setTypes(ArrayList<String> types) {
		this.types = types;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public Long getPokeNumber() {
		return pokeNumber;
	}
	public void setPokeNumber(Long pokeNumber) {
		this.pokeNumber = pokeNumber;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
