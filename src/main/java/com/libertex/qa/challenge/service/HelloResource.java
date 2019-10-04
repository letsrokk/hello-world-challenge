package com.libertex.qa.challenge.service;

import com.libertex.qa.challenge.model.ChallengeHeaders;
import com.libertex.qa.challenge.model.Client;
import com.libertex.qa.challenge.model.Session;
import com.libertex.qa.challenge.response.HelloResponse;
import com.libertex.qa.challenge.response.ResultCode;
import com.libertex.qa.challenge.response.StandartResponse;
import com.libertex.qa.challenge.utils.SessionManager;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/challenge/hello")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HelloResource {

    @Inject
    ClientResource clientResource;

    @Inject
    SessionManager sessionManager;

    @Operation(
            summary = "Receive Hello message from Challenge App",
            description = "Returns Hello message for client. Valid 'X-Session-Id' header is required"
    )
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Success"),
            @APIResponse(responseCode = "401", description = "User Unauthorized"),
            @APIResponse(responseCode = "500", description = "Error Occurred. See details in response body")
    })
    @GET
    public Response getHello(@HeaderParam(ChallengeHeaders.X_SESSION_ID) String xSessionId) {
        Session session = sessionManager.getSessionBySessionId(xSessionId);

        Response response;
        if(session == null) {
            response = Response.status(Response.Status.UNAUTHORIZED).entity(
                    StandartResponse.builder()
                            .resultCode(ResultCode.Unauthorized)
                            .build()
            ).build();
        } else {
            Client client = clientResource.getClients(session.getUsername()).get(0);
            response = Response.ok(
                    HelloResponse.builder()
                            .resultCode(ResultCode.Ok)
                            .message(String.format("Hello, %s!", client.getFullName()))
                            .build()
            ).build();
        }

        return response;
    }
}