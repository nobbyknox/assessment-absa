package com.nobbyknox.absa.repositories;

import com.nobbyknox.absa.entities.RoutingRule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutingRuleRepository extends CrudRepository<RoutingRule, Long> {
}
