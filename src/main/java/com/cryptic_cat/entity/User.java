package com.cryptic_cat.entity;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "username")
	private String userName;

	@Column(name = "password")
	private String password;
	
	@Column(name = "email")
	private String email;	
	
	@Column(name = "first_name")
	private String firstName;	
	
	@Column(name = "last_name")
	private String lastName;	

	@Column(name = "enabled")
	private boolean enabled;
	
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;
    

	public User(String userName, String password, String email, String firstName, String lastName, boolean enabled) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.enabled = enabled;
	}
    
    
    public User(String userName, String password, String email, String firstName, String lastName, boolean enabled, Collection<Role> roles) {
	    this.userName = userName;
	    this.password = password;
	    this.enabled = enabled;
	    this.email = email;
	    this.roles = roles;
    }
    
    public void addRole(Role role) {
    	if(roles == null) {
    		roles = new ArrayList<Role>();
    	}
    	roles.add(role);
    }





}
