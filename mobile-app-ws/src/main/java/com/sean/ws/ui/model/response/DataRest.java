package com.sean.ws.ui.model.response;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;

import com.sean.ws.shared.PaginationLinks;

public class DataRest extends ResourceSupport{
	private Resources<UserRest> users;
	private PaginationLinks paginationLinks;

	public DataRest(Resources<UserRest> users, Object[] paginationLinks) {
		super();
		this.users = users;
		this.paginationLinks = new PaginationLinks(paginationLinks);
	}

	public Resources<UserRest> getUsers() {
		return users;
	}

	public void setUsers(Resources<UserRest> users) {
		this.users = users;
	}

	public PaginationLinks getPaginationLinks() {
		return paginationLinks;
	}

	public void setPaginationLinks(PaginationLinks paginationLinks) {
		this.paginationLinks = paginationLinks;
	}

}
