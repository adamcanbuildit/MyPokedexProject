package com.adam.pokedex.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String displayName;
	
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String password;
	@Transient
	private String passwordConfirmation;
	
	@OneToMany(mappedBy="owner",fetch=FetchType.LAZY)
	private List<Pokemon> pokemonRoster;
	
	@ManyToMany
	@JoinTable(name="pokedex_likes",
			joinColumns = @JoinColumn(name="likerId"),
			inverseJoinColumns = @JoinColumn(name="likesId"))
	private List<User> likers;
	
	@ManyToMany
	@JoinTable(name="pokedex_likes",
			joinColumns = @JoinColumn(name="likesId"),
			inverseJoinColumns = @JoinColumn(name="likerId"))
	private List<User> likes;
	
	
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
    
    public User() {}
	public User(String email, String password, String passwordConfirmation) {
		this.email = email;
		this.password = password;
		this.passwordConfirmation = passwordConfirmation;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}
	public List<Pokemon> getPokemonRoster() {
		return pokemonRoster;
	}
	public void setPokemonRoster(List<Pokemon> pokemonRoster) {
		this.pokemonRoster = pokemonRoster;
	}
	public List<User> getLikers() {
		return likers;
	}
	public void setLikers(List<User> likers) {
		this.likers = likers;
	}
	public Date getCreatedAt() {
		return createdAt;
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
