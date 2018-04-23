package com.marhaj.money.account;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.marhaj.money.account.dto.Account;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountEndpoint {
	private AccountFacade accountFacade;

	public AccountEndpoint() {
		this(new AccountFacade());
	}

	public AccountEndpoint(AccountFacade accountFacade) {
		this.accountFacade = accountFacade;
	}

	@GET
	@Path("/{userName}")
	public Account account(@PathParam("userName") String accountName) {
		return accountFacade.account(accountName);
	}

	@GET
	@Path("/all")
	public List<Account> accounts() {
		return accountFacade.accounts();
	}

	@POST
	@Path("/save")
	public Account save(Account account) {
		return accountFacade.save(account);
	}

	@DELETE
	@Path("/{userName}")
	public Response delete(@PathParam("userName") String userName) {
		try {
			accountFacade.delete(userName);
		} catch (Exception e) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.status(Response.Status.OK).build();
	}
}
