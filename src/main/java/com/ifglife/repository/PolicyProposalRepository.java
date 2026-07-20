package com.ifglife.repository;

import com.ifglife.entity.PolicyProposalEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PolicyProposalRepository implements PanacheRepository<PolicyProposalEntity> {
}