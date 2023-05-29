package org.solar.auth.entity;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Authorities extends BEntity{
    String role;
}
