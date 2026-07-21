package com.ifglife.repository;

import com.ifglife.entity.PolicyProposalEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PolicyProposalRepository implements PanacheRepository<PolicyProposalEntity> {

    public Optional<PolicyProposalEntity> findByProposalId(String proposalId) {
        return find("proposalId", proposalId).firstResultOptional();
    }

    public boolean existsByProposalId(String proposalId) {
        return count("proposalId", proposalId) > 0;
    }

    public List<PolicyProposalEntity> listAllByCreatedAtDesc() {
        return list("order by createdAt desc");
    }
}