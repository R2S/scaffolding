package biz.r2s.scaffolding.controller;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import biz.r2s.scaffolding.service.ejb.MenuServiceEjbImpl;
@Path("/scaffolding/meta")
public class MenuController {
	@EJB
	MenuServiceEjbImpl menuService;
	
	public MenuController() {
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("menu")
	public Response menu() {
		List menus = menuService.listMenu();
		Map retorno = new java.util.HashMap();
		retorno.put("data", menus != null ? menus : new java.util.ArrayList());
		return Response.ok(retorno).build();
	}
}