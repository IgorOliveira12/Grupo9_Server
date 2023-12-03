package Grupo9_RESTServer;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import grupo9_FinancasPessoais.Categoria;
import grupo9_FinancasPessoais.CategoriaService;

@Path("/categoria")
public class CategoriaRESTService {

    private CategoriaService categoriaService;

    public CategoriaRESTService(EntityManager em) {
        this.categoriaService = new CategoriaService(em);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello() {
        return "REST Server: Olá Mundo! Eu sou o Controlador de Categorias";
    }

    @GET
    @Path("/getCategorias")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategorias() {
        try {
            List<Categoria> categorias = categoriaService.findAllCategorias();
            return Response.status(Response.Status.OK)
                    .entity(categorias)
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao obter as categorias: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @GET
    @Path("/getCategoria/{nomeC}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategoria(@PathParam("nomeC") String nomeC) {
        try {
            Categoria categoriaResponse = categoriaService.findCategoria(nomeC);
            if (categoriaResponse != null) {
                return Response.status(Response.Status.OK)
                        .entity(categoriaResponse)
                        .build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Categoria não encontrada.")
                        .type(MediaType.TEXT_PLAIN)
                        .build();
            }
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao obter a categoria: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @POST
    @Path("/addCategoria")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCategoria(Categoria categoria) {
        try {
            Categoria categoriaResponse = categoriaService.updateCategoria(
                    categoria.getNomeC(), categoria.getGastoMaximo());
            return Response.status(Response.Status.CREATED)
                    .entity(categoriaResponse)
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao adicionar a categoria: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao adicionar a categoria: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @PUT
    @Path("/updateCategoria")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCategoria(Categoria categoria) {
        try {
            Categoria categoriaResponse = categoriaService.updateCategoria(
                    categoria.getNomeC(), categoria.getGastoMaximo(),
                    categoria.getListaTransacoes(), categoria.getListaSubcategoria());
            return Response.status(Response.Status.OK)
                    .entity(categoriaResponse)
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao atualizar a categoria: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar a categoria: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    public Response visualizarPercentagemGastosPorCategoriaNoOrcamento() {
        try {
            categoriaService.visualizarPercentagemGastosPorCategoriaNoOrcamento();
            return Response.status(Response.Status.OK)
                    .entity("Percentagem de gastos por categoria no último orçamento visualizada.")
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao visualizar a percentagem de gastos por categoria: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }
    
    @PUT
    @Path("/alterarGastoMaximoCategoria/{nomeC}/{gastoMaximo}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response alterarGastoMaximoCategoria(
            @PathParam("nomeC") String nomeC, @PathParam("gastoMaximo") Double gastoMaximo) {
        try {
            categoriaService.alterarGastoMaximoCategoria(nomeC, gastoMaximo);
            return Response.status(Response.Status.OK)
                    .entity("Valor máximo da Categoria " + nomeC + " alterado para: " + gastoMaximo)
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao alterar o valor máximo da categoria: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao alterar o valor máximo da categoria: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }
}
