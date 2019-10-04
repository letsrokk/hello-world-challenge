package com.libertex.qa.challenge.service;

import com.libertex.qa.challenge.model.Session;
import com.libertex.qa.challenge.request.LogoutRequest;
import com.libertex.qa.challenge.response.ResultCode;
import com.libertex.qa.challenge.response.StandartResponse;
import com.libertex.qa.challenge.utils.SessionManager;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/challenge/logout")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LogoutResource {

    @Inject
    SessionManager sessionManager;

    @Operation(summary = "Logout with Client username", description = "Logout and remove Client Session")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Success"),
            @APIResponse(responseCode = "500", description = "Error Occurred. See details in response body")
    })
    @POST
    @Transactional
    public Response logout(LogoutRequest logoutRequest) {
        String username = logoutRequest.getUsername();
        Response response;
        try {
            Session session = sessionManager.getSessionByUsername(username);
            if (session != null) {
                sessionManager.deleteSession(session);
            }
            response = Response.ok(
                    StandartResponse.builder()
                            .resultCode(ResultCode.Ok)
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

}