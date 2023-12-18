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

    private TransacaoService ts = new TransacaoService();

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
    public Response getTransacoes() {
        try {
            List<Transacao> transacoes = ts.findAllTransacoes();
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
    public Response getTransacao(@PathParam("descricao") String descricao) {
        try {
            Transacao transacaoResponse = ts.findTransacao(descricao);
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
	public Response addCategoria(Transacao transacao) {		
		Transacao transacaoResponse = ts.updateTransacao(transacao.getData(), transacao.getValor(), transacao.getDescricao());
		
		return Response.status(Response.Status.CREATED)
				.entity(transacaoResponse)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}

    /**
     * Atualiza uma transação existente.
     *
     * @param transacao A transação atualizada.
     * @return Resposta HTTP indicando o resultado da operação.
     */

    /**
     * Remove uma transação com base na descrição.
     *
     * @param descricao A descrição da transação a ser removida.
     * @return Resposta HTTP indicando o resultado da operação.
     */
    @DELETE
	@Path("/deleteTransacao/{data}")
	public Response deleteTransacao(@PathParam("data") String data) {
		boolean transacaoRemoved = ts.removeTransacao(ts.findTransacao(data));
		System.out.println("a");
		
		return Response.status(Response.Status.OK)
				.entity(transacaoRemoved)
				.type(MediaType.APPLICATION_JSON)
				.build();
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
            Transacao transacao = ts.findTransacao(descricao);
            Categoria categoria = new Categoria();
            categoria.setNomeC(novaCategoria);
            ts.alterarCategoriaTransacao(transacao, categoria);
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
            Transacao transacao = ts.findTransacao(descricao);
            Subcategoria subcategoria = new Subcategoria();
            subcategoria.setNomeSubc(novaSubcategoria);
            ts.alterarSubcategoriaTransacao(transacao, subcategoria);
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

}
