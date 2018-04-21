package com.marhaj.money.user;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.marhaj.money.user.dto.User;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserEndpoint {
	private UserFacade userFacade;

	public UserEndpoint() {
		this(new UserFacade());
	}
	
	public UserEndpoint(UserFacade userFacade) {
		this.userFacade = userFacade;
	}

	@GET
	@Path("/{userName}")
	public User user(@PathParam("userName") String userName) {
		return userFacade.user(userName);
	}

	@GET
	@Path("/all")
	public List<User> users() {
		return userFacade.users();
	}

	@POST
	@Path("/save")
	public User save(User user) {
		return userFacade.save(user);
	}

	@DELETE
	@Path("/{userName}")
	public Response delete(@PathParam("userName") String userName) {
		try {
			userFacade.delete(userName);
		} catch (Exception e) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.status(Response.Status.OK).build();
	}
}
