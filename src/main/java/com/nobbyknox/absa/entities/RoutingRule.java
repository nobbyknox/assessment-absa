package com.nobbyknox.absa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class RoutingRule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @NotNull
    public String name;

    @NotNull
    public String routeData;

    @Override
    public String toString() {
        return "RoutingRule{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", routeData='" + routeData + '\'' +
            '}';
    }
}
