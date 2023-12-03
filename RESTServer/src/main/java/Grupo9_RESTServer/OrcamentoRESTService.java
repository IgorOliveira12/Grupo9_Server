package Grupo9_RESTServer;

import grupo9_FinancasPessoais.Orcamento;
import grupo9_FinancasPessoais.OrcamentoService;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/orcamento")
public class OrcamentoRESTService {

    private OrcamentoService orcamentoService;

    public OrcamentoRESTService(EntityManager em) {
        this.orcamentoService = new OrcamentoService(em);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello() {
        return "REST Server: Olá Mundo! Eu sou o Controlador de Orçamentos";
    }

    @GET
    @Path("/getOrcamentos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrcamentos() {
        try {
            List<Orcamento> orcamentos = orcamentoService.findAllOrcamentos();
            return Response.status(Response.Status.OK)
                    .entity(orcamentos)
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao obter os orçamentos: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @GET
    @Path("/getOrcamento/{valorAnual}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrcamento(@PathParam("valorAnual") double valorAnual) {
        try {
            Orcamento orcamentoResponse = orcamentoService.findOrcamento(valorAnual);
            if (orcamentoResponse != null) {
                return Response.status(Response.Status.OK)
                        .entity(orcamentoResponse)
                        .build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Orçamento não encontrado.")
                        .type(MediaType.TEXT_PLAIN)
                        .build();
            }
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao obter o orçamento: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @POST
    @Path("/addOrcamento")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addOrcamento(Orcamento orcamento) {
        try {
            Orcamento orcamentoResponse = orcamentoService.updateOrcamento(
                    orcamento.getValorAnual(), orcamento.getDataCriacao(),
                    orcamento.getListaCategorias(), orcamento.getListaMetas());
            return Response.status(Response.Status.CREATED)
                    .entity(orcamentoResponse)
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao adicionar o orçamento: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @PUT
    @Path("/updateOrcamento")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOrcamento(Orcamento orcamento) {
        try {
            Orcamento orcamentoResponse = orcamentoService.updateOrcamento(
                    orcamento.getValorAnual(), orcamento.getDataCriacao(),
                    orcamento.getListaCategorias(), orcamento.getListaMetas());
            return Response.status(Response.Status.OK)
                    .entity(orcamentoResponse)
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar o orçamento: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @PUT
    @Path("/adicionarOuReduzirValorOrcamento/{valorAlteracao}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response adicionarOuReduzirValorOrcamento(@PathParam("valorAlteracao") double valorAlteracao) {
        try {
            Orcamento orcamentoResponse = orcamentoService.adicionarOuReduzirValorOrcamento(valorAlteracao);
            return Response.status(Response.Status.OK)
                    .entity(orcamentoResponse)
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao adicionar ou reduzir o valor do orçamento: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @GET
    @Path("/obterUltimoOrcamento")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterUltimoOrcamento() {
        try {
            Orcamento orcamentoResponse = orcamentoService.obterUltimoOrcamento();
            if (orcamentoResponse != null) {
                return Response.status(Response.Status.OK)
                        .entity(orcamentoResponse)
                        .build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Nenhum orçamento encontrado.")
                        .type(MediaType.TEXT_PLAIN)
                        .build();
            }
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao obter o último orçamento: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @DELETE
    @Path("/removeOrcamento/{valorAnual}")
    public Response removeOrcamento(@PathParam("valorAnual") double valorAnual) {
        try {
            orcamentoService.removeOrcamento(valorAnual);
            return Response.status(Response.Status.OK)
                    .entity("Orcamento removido.")
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao remover o orçamento: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @GET
    @Path("/imprimirHistoricoOrcamentos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response imprimirHistoricoOrcamentos() {
        try {
            orcamentoService.imprimirHistoricoOrcamentos();
            return Response.status(Response.Status.OK)
                    .entity("Histórico de orçamentos impresso.")
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao imprimir o histórico de orçamentos: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @GET
    @Path("/calcularGastoRealizado/{valorAnual}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response calcularGastoRealizado(@PathParam("valorAnual") double valorAnual) {
        try {
            Orcamento orcamentoResponse = orcamentoService.findOrcamento(valorAnual);
            if (orcamentoResponse != null) {
                double gastoRealizado = orcamentoService.calcularGastoRealizado(orcamentoResponse);
                return Response.status(Response.Status.OK)
                        .entity("Gasto Realizado: " + gastoRealizado + "€")
                        .build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Orçamento não encontrado.")
                        .type(MediaType.TEXT_PLAIN)
                        .build();
            }
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao calcular o gasto realizado: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @GET
    @Path("/mostrarStatusOrcamento")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostrarStatusOrcamento() {
        try {
            orcamentoService.mostrarStatusOrcamento();
            return Response.status(Response.Status.OK)
                    .entity("Status do orçamento mostrado.")
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao mostrar o status do orçamento: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }
}
