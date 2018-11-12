package com.nobbyknox.absa.controllers;

import com.nobbyknox.absa.entities.RoutingRule;
import com.nobbyknox.absa.repositories.RoutingRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class RoutingRuleController {

    @Autowired
    private RoutingRuleRepository repo;

    @RequestMapping(value = "/rules", method = RequestMethod.GET, produces = "application/json")
    public List<RoutingRule> viewAllInvoices() {
        return StreamSupport.stream(repo.findAll().spliterator(), false).collect(Collectors.toList());
    }
}
