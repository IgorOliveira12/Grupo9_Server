package Grupo9_RESTServer;

import grupo9_FinancasPessoais.Meta;
import grupo9_FinancasPessoais.MetaService;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/meta")
public class MetaRESTService {

    private MetaService metaService;

    public MetaRESTService(EntityManager em) {
        this.metaService = new MetaService(em);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello() {
        return "REST Server: Olá Mundo! Eu sou o Controlador de Metas";
    }

    @GET
    @Path("/getMetas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMetas() {
        try {
            List<Meta> metas = metaService.findAllMetas();
            return Response.status(Response.Status.OK)
                    .entity(metas)
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao obter as metas: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @GET
    @Path("/getMeta/{nome}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMeta(@PathParam("nome") String nome) {
        try {
            Meta metaResponse = metaService.findMeta(nome);
            if (metaResponse != null) {
                return Response.status(Response.Status.OK)
                        .entity(metaResponse)
                        .build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Meta não encontrada.")
                        .type(MediaType.TEXT_PLAIN)
                        .build();
            }
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao obter a meta: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @POST
    @Path("/addMeta")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMeta(Meta meta) {
        try {
            Meta metaResponse = metaService.updateMeta(
                    meta.getDescricao(), meta.getValor(), meta.getData(), meta.getNome());
            return Response.status(Response.Status.CREATED)
                    .entity(metaResponse)
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao adicionar a meta: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @PUT
    @Path("/updateMeta")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMeta(Meta meta) {
        try {
            Meta metaResponse = metaService.updateMeta(
                    meta.getDescricao(), meta.getValor(), meta.getData(), meta.getNome());
            return Response.status(Response.Status.OK)
                    .entity(metaResponse)
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar a meta: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @PUT
    @Path("/alterarValorMeta/{nome}/{novoValor}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response alterarValorMeta(
            @PathParam("nome") String nome, @PathParam("novoValor") Double novoValor) {
        try {
            metaService.alterarValorMeta(nome, novoValor);
            return Response.status(Response.Status.OK)
                    .entity("Valor da Meta " + nome + " alterado para: " + novoValor)
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao alterar o valor da meta: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @PUT
    @Path("/alterarPrazoMeta/{nome}/{novaData}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response alterarPrazoMeta(
            @PathParam("nome") String nome, @PathParam("novaData") String novaData) {
        try {
            metaService.alterarPrazoMeta(nome, novaData);
            return Response.status(Response.Status.OK)
                    .entity("Prazo da Meta " + nome + " alterado para: " + novaData)
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao alterar o prazo da meta: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @GET
    @Path("/verificarMetasCumpridas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response verificarMetasCumpridas() {
        try {
            metaService.verificarMetasCumpridas();
            return Response.status(Response.Status.OK)
                    .entity("Metas cumpridas verificadas.")
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao verificar metas cumpridas: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @GET
    @Path("/listarMetasNaoCumpridas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarMetasNaoCumpridas() {
        try {
            metaService.listarMetasNaoCumpridas();
            return Response.status(Response.Status.OK)
                    .entity("Metas não cumpridas listadas.")
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar metas não cumpridas: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }
}
