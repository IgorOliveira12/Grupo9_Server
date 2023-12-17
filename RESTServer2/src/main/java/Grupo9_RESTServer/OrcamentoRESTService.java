package Grupo9_RESTServer;

import grupo9_FinancasPessoais.Categoria;
import grupo9_FinancasPessoais.Orcamento;
import grupo9_FinancasPessoais.OrcamentoService;
import grupo9_FinancasPessoais.OrcamentoService.OrcamentoException;
import grupo9_FinancasPessoais.OrcamentoService.OrcamentoNotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Controlador REST para gerenciar orçamentos financeiros.
 */
@Path("/orcamento")
public class OrcamentoRESTService {

    private OrcamentoService os = new OrcamentoService();

    /**
     * Método de saudação em texto simples.
     *
     * @return Uma saudação simples em texto.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello() {
        return "REST Server: Olá Mundo! Eu sou o Controlador de Orçamentos";
    }

    @POST
	@Path("/addOrcamento")
	public Response addCategoria(Orcamento orcamento) {		
		Orcamento orcamentoResponse = os.updateOrcamento(orcamento.getDataCriacao(), orcamento.getValorAnual());
		
		return Response.status(Response.Status.CREATED)
				.entity(orcamentoResponse)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
    
    /**
    @DELETE
	@Path("/deleteCategoria/{nomeC}")
	public Response deleteCategoria(@PathParam("nomeC") String nomeC) {
		boolean categoriaRemoved = os.removeOrcamento(nomeC);
		
		return Response.status(Response.Status.OK)
				.entity(categoriaRemoved)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
	*/
    
    
    /**
     * Obtém a lista de todos os orçamentos.
     *
     * @return Resposta HTTP contendo a lista de orçamentos.
     */
    @GET
    @Path("/getOrcamentos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrcamentos() {
        try {
            List<Orcamento> orcamentos = os.findAllOrcamentos();
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

    /**
     * Obtém um orçamento com base no valor anual.
     *
     * @param valorAnual O valor anual do orçamento a ser obtido.
     * @return Resposta HTTP contendo o orçamento encontrado ou uma mensagem de erro.
     */
    @GET
    @Path("/getOrcamento/{dataCriacao}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrcamento(@PathParam("valorAnual") String dataCriacao) {
        try {
            Orcamento orcamentoResponse = os.findOrcamento(dataCriacao);
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

    /**
     * Adiciona ou reduz o valor de um orçamento.
     *
     * @param valorAlteracao O valor a ser adicionado ou reduzido no orçamento.
     * @return Resposta HTTP indicando o resultado da operação.
     */
    @PUT
    @Path("/adicionarOuReduzirValorOrcamento/{valorAlteracao}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response adicionarOuReduzirValorOrcamento(@PathParam("valorAlteracao") double valorAlteracao) {
        try {
            Orcamento orcamentoResponse = os.adicionarOuReduzirValorOrcamento(valorAlteracao);
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

    /**
     * Obtém o último orçamento.
     *
     * @return Resposta HTTP contendo o último orçamento ou uma mensagem de erro.
     */
    @GET
    @Path("/obterUltimoOrcamento")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterUltimoOrcamento() {
        try {
            Orcamento orcamentoResponse = os.obterUltimoOrcamento();
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

    /**
     * Imprime o histórico de orçamentos.
     *
     * @return Resposta HTTP indicando o resultado da operação.
     */
    @GET
    @Path("/imprimirHistoricoOrcamentos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response imprimirHistoricoOrcamentos() {
        try {
            os.imprimirHistoricoOrcamentos();
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

    /**
     * Calcula o gasto realizado com base no valor anual do orçamento.
     *
     * @param valorAnual O valor anual do orçamento.
     * @return Resposta HTTP contendo o gasto realizado ou uma mensagem de erro.
     */
    @GET
    @Path("/calcularGastoRealizado/{dataCriacao}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response calcularGastoRealizado(@PathParam("valorAnual") String dataCriacao) {
        try {
            Orcamento orcamentoResponse = os.findOrcamento(dataCriacao);
            if (orcamentoResponse != null) {
                double gastoRealizado = os.calcularGastoRealizado(orcamentoResponse);
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

    /**
     * Mostra o status do orçamento.
     *
     * @return Resposta HTTP indicando o resultado da operação.
     */
    @GET
    @Path("/mostrarStatusOrcamento")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostrarStatusOrcamento() {
        try {
            os.mostrarStatusOrcamento();
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