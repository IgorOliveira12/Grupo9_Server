package Grupo9_RESTServer;

import grupo9_FinancasPessoais.Categoria;
import grupo9_FinancasPessoais.Subcategoria;
import grupo9_FinancasPessoais.Transacao;
import grupo9_FinancasPessoais.TransacaoService;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Controlador REST para gerenciar transações financeiras.
 */
@Path("/transacao")
public class TransacaoRESTService {

    private TransacaoService transacaoService;

    /**
     * Método de saudação em texto simples.
     *
     * @return Uma saudação simples em texto.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello() {
        return "REST Server: Olá Mundo! Eu sou o Controlador de Transações";
    }

    /**
     * Obtém a lista de todas as transações.
     *
     * @return Resposta HTTP contendo a lista de transações.
     */
    @GET
    @Path("/getTransacoes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransacoes() {
        try {
            List<Transacao> transacoes = transacaoService.findAllTransacoes();
            return Response.status(Response.Status.OK)
                    .entity(transacoes)
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao obter as transações: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    /**
     * Obtém uma transação com base na descrição.
     *
     * @param descricao A descrição da transação a ser obtida.
     * @return Resposta HTTP contendo a transação encontrada ou uma mensagem de erro.
     */
    @GET
    @Path("/getTransacao/{descricao}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransacao(@PathParam("descricao") String descricao) {
        try {
            Transacao transacaoResponse = transacaoService.findTransacao(descricao);
            if (transacaoResponse != null) {
                return Response.status(Response.Status.OK)
                        .entity(transacaoResponse)
                        .build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Transação não encontrada.")
                        .type(MediaType.TEXT_PLAIN)
                        .build();
            }
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao obter a transação: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    /**
     * Adiciona uma nova transação.
     *
     * @param transacao A transação a ser adicionada.
     * @return Resposta HTTP indicando o resultado da operação.
     */
    @POST
    @Path("/addTransacao")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTransacao(Transacao transacao) {
        try {
            Transacao transacaoResponse = transacaoService.updateTransacao(
                    transacao.getData(), transacao.getValor(), transacao.getDescricao());
            return Response.status(Response.Status.CREATED)
                    .entity(transacaoResponse)
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao adicionar a transação: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    /**
     * Atualiza uma transação existente.
     *
     * @param transacao A transação atualizada.
     * @return Resposta HTTP indicando o resultado da operação.
     */
    @PUT
    @Path("/updateTransacao")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTransacao(Transacao transacao) {
        try {
            Transacao transacaoResponse = transacaoService.updateTransacao(
                    transacao.getData(), transacao.getValor(), transacao.getDescricao());
            return Response.status(Response.Status.OK)
                    .entity(transacaoResponse)
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar a transação: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    /**
     * Remove uma transação com base na descrição.
     *
     * @param descricao A descrição da transação a ser removida.
     * @return Resposta HTTP indicando o resultado da operação.
     */
    @DELETE
    @Path("/removeTransacao/{descricao}")
    public Response removeTransacao(@PathParam("descricao") String descricao) {
        try {
            Transacao transacao = transacaoService.findTransacao(descricao);
            transacaoService.removeTransacao(transacao);
            return Response.status(Response.Status.OK)
                    .entity("Transação removida.")
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao remover a transação: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    /**
     * Altera a categoria de uma transação existente.
     *
     * @param descricao      A descrição da transação a ser alterada.
     * @param novaCategoria  A nova categoria a ser atribuída à transação.
     * @return Resposta HTTP indicando o resultado da operação.
     */
    @PUT
    @Path("/alterarCategoria/{descricao}/{novaCategoria}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response alterarCategoria(@PathParam("descricao") String descricao,
                                     @PathParam("novaCategoria") String novaCategoria) {
        try {
            Transacao transacao = transacaoService.findTransacao(descricao);
            Categoria categoria = new Categoria();
            categoria.setNomeC(novaCategoria);
            transacaoService.alterarCategoriaTransacao(transacao, categoria);
            return Response.status(Response.Status.OK)
                    .entity("Categoria da transação alterada.")
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao alterar a categoria da transação: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    /**
     * Altera a subcategoria de uma transação existente.
     *
     * @param descricao        A descrição da transação a ser alterada.
     * @param novaSubcategoria A nova subcategoria a ser atribuída à transação.
     * @return Resposta HTTP indicando o resultado da operação.
     */
    @PUT
    @Path("/alterarSubcategoria/{descricao}/{novaSubcategoria}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response alterarSubcategoria(@PathParam("descricao") String descricao,
                                        @PathParam("novaSubcategoria") String novaSubcategoria) {
        try {
            Transacao transacao = transacaoService.findTransacao(descricao);
            Subcategoria subcategoria = new Subcategoria();
            subcategoria.setNomeSubc(novaSubcategoria);
            transacaoService.alterarSubcategoriaTransacao(transacao, subcategoria);
            return Response.status(Response.Status.OK)
                    .entity("Subcategoria da transação alterada.")
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao alterar a subcategoria da transação: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    /**
     * Altera a data de uma transação existente.
     *
     * @param descricao A descrição da transação a ser alterada.
     * @param novaData  A nova data a ser atribuída à transação.
     * @return Resposta HTTP indicando o resultado da operação.
     */
    @PUT
    @Path("/alterarData/{descricao}/{novaData}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response alterarData(@PathParam("descricao") String descricao,
                                @PathParam("novaData") String novaData) {
        try {
            Transacao transacao = transacaoService.findTransacao(descricao);
            transacaoService.alterarDataTransacao(transacao, novaData);
            return Response.status(Response.Status.OK)
                    .entity("Data da transação alterada.")
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao alterar a data da transação: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }
}
