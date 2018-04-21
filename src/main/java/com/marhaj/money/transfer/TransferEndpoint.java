package com.marhaj.money.transfer;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.marhaj.money.transfer.dto.Transfer;

@Path("/transfer")
@Produces(MediaType.APPLICATION_JSON)
public class TransferEndpoint {
	private TransferFacade transferFacade;

	public TransferEndpoint() {
		this(new TransferFacade());
	}

	public TransferEndpoint(TransferFacade transferFacade) {
		super();
		this.transferFacade = transferFacade;
	}

	@POST
	@Path("/save")
	public Response transfer(Transfer transfer) {
		transferFacade.save(transfer);
		return Response.status(Response.Status.OK).build();
	}

	@GET
	@Path("/all")
	public List<Transfer> transfers() {
		return transferFacade.transfers();
	}
}
