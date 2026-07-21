package com.ifglife.controller;

import com.ifglife.dto.PolicyProposalSubmittedEvent;
import com.ifglife.dto.api.ProposalRequest;
import com.ifglife.dto.api.ProposalResponse;
import com.ifglife.kafka.messeging.ProposalSubmissionProducer;
import com.ifglife.service.ProposalCrudService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/proposals")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProposalController {

    @Inject
    ProposalSubmissionProducer proposalSubmissionProducer;

    @Inject
    ProposalCrudService proposalCrudService;

    @POST
    @Path("/submit")
    public Response submit(PolicyProposalSubmittedEvent event) {
        proposalSubmissionProducer.publish(event);
        return Response.accepted().build();
    }

    @POST
    public Response create(ProposalRequest request) {
        ProposalResponse created = proposalCrudService.create(request);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @GET
    public List<ProposalResponse> getAll() {
        return proposalCrudService.getAll();
    }

    @GET
    @Path("/{proposalId}")
    public ProposalResponse getById(@PathParam("proposalId") String proposalId) {
        return proposalCrudService.getById(proposalId);
    }

    @PUT
    @Path("/{proposalId}")
    public ProposalResponse update(@PathParam("proposalId") String proposalId, ProposalRequest request) {
        return proposalCrudService.update(proposalId, request);
    }

    @DELETE
    @Path("/{proposalId}")
    public Response delete(@PathParam("proposalId") String proposalId) {
        proposalCrudService.delete(proposalId);
        return Response.noContent().build();
    }
}