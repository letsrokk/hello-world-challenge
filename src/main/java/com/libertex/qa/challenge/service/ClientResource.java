package com.libertex.qa.challenge.service;

import com.libertex.qa.challenge.model.Client;
import com.libertex.qa.challenge.response.GetClientsResponse;
import com.libertex.qa.challenge.response.ResultCode;
import com.libertex.qa.challenge.response.StandartResponse;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/challenge/clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientResource {

    @Inject
    EntityManager entityManager;

    @Operation(
            summary = "Create a new Client",
            description = "Create new client by providing Username and Full Name. Client is stored in im-memory DB"
    )
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Success"),
            @APIResponse(responseCode = "500", description = "Error Occurred. See details in response body")
    })
    @POST
    @Transactional
    public Response create(Client clientInfo) {
        Response response;
        try {
            entityManager.persist(clientInfo);
            entityManager.flush();
            response = Response.ok(
                    StandartResponse.builder()
                            .resultCode(ResultCode.Ok)
                            .build()
            ).build();
        } catch (ConstraintViolationException e) {
            response = Response.serverError()
                    .entity(
                            StandartResponse.builder()
                                    .resultCode(ResultCode.UserAlreadyExists)
                                    .errorMessage(String.format("User with login '%s' already exists", clientInfo.getUsername()))
                                    .build()
                    ).build();
        } catch (PropertyValueException e) {
            response = Response.serverError()
                    .entity(
                            StandartResponse.builder()
                                    .resultCode(ResultCode.ConstraintViolation)
                                    .errorMessage(e.getMessage())
                                    .build()
                    ).build();
        } catch (Exception e) {
            response = Response.serverError()
                    .entity(
                            StandartResponse.builder()
                                    .resultCode(ResultCode.UnexpectedError)
                                    .errorMessage(e.getMessage())
                                    .build()
                    ).build();
        }
        return response;
    }

    @Operation(summary = "Get a list of usernames from DB")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Success"),
            @APIResponse(responseCode = "500", description = "Error Occurred. See details in response body")
    })
    @GET
    public Response getClients() {
        Response response;
        try {
            List<String> logins = getClients(null)
                    .stream()
                    .map(Client::getUsername)
                    .collect(Collectors.toList());;

            response = Response.ok(
                    GetClientsResponse.builder()
                            .resultCode(ResultCode.Ok)
                            .clients(logins)
                            .build()
            ).build();
        } catch (Exception e) {
            response = Response.serverError().entity(
                    StandartResponse.builder()
                            .resultCode(ResultCode.UnexpectedError)
                            .errorMessage(e.getMessage())
                            .build()
            ).build();
        }
        return response;
    }

    List<Client> getClients(String username) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> rootEntry = criteriaQuery.from(Client.class);
        CriteriaQuery<Client> selectQuery = criteriaQuery.select(rootEntry);

        if (username != null) {
            selectQuery = selectQuery.where(criteriaBuilder.equal(rootEntry.get("username"), username));
        }
        TypedQuery<Client> query = entityManager.createQuery(selectQuery);

        return query.getResultList();
    }

}