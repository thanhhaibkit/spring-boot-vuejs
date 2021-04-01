package com.thanhhai.demo.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "accounts")
@Proxy(lazy = false)
public class Account extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @NaturalId
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "sub_domain", nullable = false)
    private String subDomain;

    @Column(name = "schema")
    private String schema;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL)
    private List<User> users;
}
