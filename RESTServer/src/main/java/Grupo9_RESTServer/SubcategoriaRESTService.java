package Grupo9_RESTServer;

import grupo9_FinancasPessoais.Subcategoria;
import grupo9_FinancasPessoais.SubcategoriaService;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/subcategoria")
public class SubcategoriaRESTService {

    private SubcategoriaService subcategoriaService;

    public SubcategoriaRESTService(EntityManager em) {
        this.subcategoriaService = new SubcategoriaService(em);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello() {
        return "REST Server: Olá Mundo! Eu sou o Controlador de Subcategorias";
    }

    @GET
    @Path("/getSubcategorias")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubcategorias() {
        try {
            List<Subcategoria> subcategorias = subcategoriaService.findAllSubcategorias();
            return Response.status(Response.Status.OK)
                    .entity(subcategorias)
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao obter as subcategorias: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @GET
    @Path("/getSubcategoria/{nomeSubc}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubcategoria(@PathParam("nomeSubc") String nomeSubc) {
        try {
            Subcategoria subcategoriaResponse = subcategoriaService.findSubcategoria(nomeSubc);
            if (subcategoriaResponse != null) {
                return Response.status(Response.Status.OK)
                        .entity(subcategoriaResponse)
                        .build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Subcategoria não encontrada.")
                        .type(MediaType.TEXT_PLAIN)
                        .build();
            }
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao obter a subcategoria: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @POST
    @Path("/addSubcategoria")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSubcategoria(Subcategoria subcategoria) {
        try {
            Subcategoria subcategoriaResponse = subcategoriaService.updateSubcategoria(
                    subcategoria.getNomeSubc(), subcategoria.getGastoMaxSubc(),
                    subcategoria.getListaTransacoes());
            return Response.status(Response.Status.CREATED)
                    .entity(subcategoriaResponse)
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao adicionar a subcategoria: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @PUT
    @Path("/updateSubcategoria")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSubcategoria(Subcategoria subcategoria) {
        try {
            Subcategoria subcategoriaResponse = subcategoriaService.updateSubcategoria(
                    subcategoria.getNomeSubc(), subcategoria.getGastoMaxSubc(),
                    subcategoria.getListaTransacoes());
            return Response.status(Response.Status.OK)
                    .entity(subcategoriaResponse)
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar a subcategoria: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @PUT
    @Path("/alterarGastoMaximo/{nomeSubc}/{gastoMaximo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response alterarGastoMaximo(@PathParam("nomeSubc") String nomeSubc,
                                       @PathParam("gastoMaximo") double gastoMaximo) {
        try {
            subcategoriaService.alterarGastoMaximoSubCategoria(nomeSubc, gastoMaximo);
            return Response.status(Response.Status.OK)
                    .entity("Gasto máximo da subcategoria alterado.")
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao alterar o gasto máximo da subcategoria: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @DELETE
    @Path("/removeSubcategoria/{nomeSubc}")
    public Response removeSubcategoria(@PathParam("nomeSubc") String nomeSubc) {
        try {
            subcategoriaService.removeSubcategoria(nomeSubc);
            return Response.status(Response.Status.OK)
                    .entity("Subcategoria removida.")
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao remover a subcategoria: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @GET
    @Path("/calcularPercentagemGastos/{nomeSubc}/{gastosCategoria}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response calcularPercentagemGastos(@PathParam("nomeSubc") String nomeSubc,
                                              @PathParam("gastosCategoria") double gastosCategoria) {
        try {
            Subcategoria subcategoriaResponse = subcategoriaService.findSubcategoria(nomeSubc);
            if (subcategoriaResponse != null) {
                double percentagem = subcategoriaService.calcularPercentagemGastosSubcategoriaComRelacaoCategoria(
                        subcategoriaResponse, gastosCategoria);
                return Response.status(Response.Status.OK)
                        .entity("Percentagem de gastos da subcategoria: " + percentagem + "%")
                        .build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Subcategoria não encontrada.")
                        .type(MediaType.TEXT_PLAIN)
                        .build();
            }
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao calcular a percentagem de gastos: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }
}
