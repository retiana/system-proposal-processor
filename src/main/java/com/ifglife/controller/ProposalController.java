package com.ifglife.controller;

import com.ifglife.dto.PolicyProposalSubmittedEvent;
import com.ifglife.kafka.messeging.ProposalSubmissionProducer;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/proposals")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProposalController {

    @Inject
    ProposalSubmissionProducer proposalSubmissionProducer;

    @POST
    public Response submit(PolicyProposalSubmittedEvent event) {
        proposalSubmissionProducer.publish(event);
        return Response.accepted().build();
    }
}