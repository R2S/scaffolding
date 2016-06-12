package biz.r2s.scaffolding.controller;

import java.util.Map;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import biz.r2s.scaffolding.interceptor.DomainResource;
import biz.r2s.scaffolding.interceptor.DomainScaffoldStore;
import biz.r2s.scaffolding.security.PermissionFacade;
import biz.r2s.scaffolding.service.ejb.MetaServiceEjbImpl;
@Path("/scaffolding/{resourceName}/meta")
public class MetaController {
	@EJB
	MetaServiceEjbImpl metaService;
	PermissionFacade permissionFacade;

	@PathParam("resourceName")
	String resourceName;

	//@PathParam("fatherId")
	Object fatherId;

	//@PathParam("propertyHasMany")
	String propertyHasMany;

	public MetaController() {
		permissionFacade = new PermissionFacade();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("list")
	public Response list() {
		Class domain = getDomain(resourceName);

		if (domain != null) {
			DomainResource domainResource = getDomainResource(domain, propertyHasMany);
			if (permissionFacade.isPermissionDomainResource(domainResource)) {
				Map meta = metaService.getMetaList(domain, propertyHasMany, fatherId);
				if (meta != null) {
					return Response.ok(meta).build();
				} else {
					return Response.serverError().entity("Error ao gerar metadado").build();
				}
			} else {
				return Response.serverError().entity("Usuário não tem acesso ao meta").build();
			}
		} else {
			return Response.serverError().entity("Domain não encontrato").build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("create")
	public Response create() {
		Class domain = getDomain(resourceName);

		if (domain != null) {
			DomainResource domainResource = getDomainResource(domain, propertyHasMany);
			if (permissionFacade.isPermissionDomainResource(domainResource)) {
				Map meta = metaService.getMetaCreate(domain, propertyHasMany, fatherId);
				if (meta != null) {
					return Response.ok(meta).build();
				} else {
					return Response.serverError().entity("Error ao gerar metadado").build();
				}
			} else {
				return Response.serverError().entity("Usuário não tem acesso ao meta").build();
			}
		} else {
			return Response.serverError().entity("Domain não encontrato").build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("edit")
	public Response edit() {
		Class domain = getDomain(resourceName);

		if (domain != null) {
			DomainResource domainResource = getDomainResource(domain, propertyHasMany);
			if (permissionFacade.isPermissionDomainResource(domainResource)) {
				Map meta = metaService.getMetaEdit(domain, propertyHasMany, fatherId);
				if (meta != null) {
					return Response.ok(meta).build();
				} else {
					return Response.serverError().entity("Error ao gerar metadado").build();
				}
			} else {
				return Response.serverError().entity("Usuário não tem acesso ao meta").build();
			}
		} else {
			return Response.serverError().entity("Domain não encontrato").build();
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("show")
	public Response show() {
		Class domain = getDomain(resourceName);

		if (domain != null) {
			DomainResource domainResource = getDomainResource(domain, propertyHasMany);
			if (permissionFacade.isPermissionDomainResource(domainResource)) {
				Map meta = metaService.getMetaShow(domain, propertyHasMany, fatherId);
				if (meta != null) {
					return Response.ok(meta).build();
				} else {
					return Response.serverError().entity("Error ao gerar metadado").build();
				}
			} else {
				return Response.serverError().entity("Usuário não tem acesso ao meta").build();
			}
		} else {
			return Response.serverError().entity("Domain não encontrato").build();
		}
	}

	private Class getDomain(String resourceName) {
		Class resource = null;
		if (resourceName != null) {
			try {
				resource = Class.forName(resourceName);
			} catch (Exception e) {
				resource = null;
			}
		}
		return resource;
	}

	private DomainResource getDomainResource(Class domain, String propertyHasMany) {

		DomainResource domainResource = DomainScaffoldStore.getDomainResourse(domain, propertyHasMany);
		if (domainResource == null) {
			domainResource = new DomainResource();
			domainResource.setDomainClass(domain);
			domainResource.setPropertyName(propertyHasMany);
		}
		return domainResource;
	}

}