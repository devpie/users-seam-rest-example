package de.thm.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.seam.rest.validation.ValidateRequest;

import de.thm.model.User;
import de.thm.model.Users;

/**
 * Using the Stateless annotion in order to not need to propagate some
 * Transactions manually.
 * 
 * @author jan
 */
@Path("/user")
@Stateless
public class UserService {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	@GET
	@Path("/{id:[0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("id") long id) {
		return em.find(User.class, id);
	}

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers() {
		return em.createQuery("select u from User u", User.class)
				.getResultList();
	}

	@POST
	@Path("/create")
	@ValidateRequest
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createUsers(Users users) {
		for (User u : users.getUsers())
			em.persist(u);
		return "OK";
	}
}
