package com.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "role")
public class Role extends AbstractEntity{
        @Column
      private String nom;
}
