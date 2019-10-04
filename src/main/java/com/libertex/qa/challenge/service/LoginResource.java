package com.libertex.qa.challenge.service;

import com.libertex.qa.challenge.model.ChallengeHeaders;
import com.libertex.qa.challenge.model.Client;
import com.libertex.qa.challenge.model.Session;
import com.libertex.qa.challenge.request.LoginRequest;
import com.libertex.qa.challenge.response.ResultCode;
import com.libertex.qa.challenge.response.StandartResponse;
import com.libertex.qa.challenge.utils.SessionManager;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/challenge/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {

    @Inject
    EntityManager entityManager;

    @Inject
    ClientResource clientResource;

    @Inject
    SessionManager sessionManager;

    @Operation(
            summary = "Login with Client username and receive Session ID",
            description = "Create Client session. Session ID is returned as header 'X-Session-Id'"
    )
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Success"),
            @APIResponse(responseCode = "500", description = "Error Occurred. See details in response body")
    })
    @POST
    @Transactional
    public Response login(LoginRequest loginRequest) {
        String login = loginRequest.getUsername();
        Response response;
        try {
            List<Client> clients = clientResource.getClients(login);

            if (clients.isEmpty()) {
                response = Response.serverError().entity(
                        StandartResponse.builder()
                                .resultCode(ResultCode.IncorrectParameter)
                                .errorMessage("Username or Password are not correct")
                                .build()
                ).build();
            } else {
                Session session = sessionManager.getSessionByUsername(login);
                if (session != null) {
                    sessionManager.deleteSession(session);
                }

                session = Session.builder()
                        .username(login)
                        .sessionId(UUID.randomUUID().toString())
                        .build();

                entityManager.persist(session);
                entityManager.flush();

                response = Response.ok(StandartResponse.builder()
                        .resultCode(ResultCode.Ok)
                        .build())
                        .header(ChallengeHeaders.X_SESSION_ID, session.getSessionId())
                        .build();
            }
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

}