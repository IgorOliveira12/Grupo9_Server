package Grupo9_RESTServer;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import grupo9_FinancasPessoais.Categoria;
import grupo9_FinancasPessoais.CategoriaService;

/**
 * Controlador REST para gerenciar categorias financeiras.
 */
@Path("/categoria")
public class CategoriaRESTService {

    private CategoriaService cs = new CategoriaService();

    /**
     * Método de saudação em texto simples.
     *
     * @return Uma saudação simples em texto.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello() {
        return "REST Server: Olá Mundo! Eu sou o Controlador de Categorias";
    }
    
    /**
     * Adiciona uma nova categoria.
     *
     * @param categoria A categoria a ser adicionada.
     * @return Resposta HTTP indicando o resultado da operação.
     */
    @POST
	@Path("/addCategoria")
	public Response addCategoria(Categoria categoria) {		
		Categoria categoriaResponse = cs.updateCategoria(categoria);
		
		return Response.status(Response.Status.CREATED)
				.entity(categoriaResponse)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
    
    @DELETE
	@Path("/deleteCategoria/{nomeC}")
	public Response deleteCategoria(@PathParam("nomeC") String nomeC) {
		boolean categoriaRemoved = cs.removeCategoria(nomeC);
		
		return Response.status(Response.Status.OK)
				.entity(categoriaRemoved)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}

    /**
     * Obtém a lista de todas as categorias.
     *
     * @return Resposta HTTP contendo a lista de categorias.
     */
    @GET
    @Path("/getCategorias")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategorias() {
    	List<Categoria> categorias = cs.findAllCategorias();

		return Response.status(Response.Status.OK)
				.entity(categorias)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
    

    /**
     * Obtém uma categoria com base no nome.
     *
     * @param nomeC O nome da categoria a ser obtida.
     * @return Resposta HTTP contendo a categoria encontrada ou uma mensagem de erro.
     */
    @GET
    @Path("/getCategoria/{nomeC}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategoria(@PathParam("nomeC") String nomeC) {
        try {
            Categoria categoriaResponse = cs.findCategoria(nomeC);
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


    /**
     * Visualiza a percentagem de gastos por categoria no último orçamento.
     *
     * @return Resposta HTTP indicando o resultado da operação.
     */
    @GET
    @Path("/visualizarPercentagemGastosPorCategoriaNoOrcamento")
    @Produces(MediaType.TEXT_PLAIN)
    public Response visualizarPercentagemGastosPorCategoriaNoOrcamento() {
        try {
            cs.visualizarPercentagemGastosPorCategoriaNoOrcamento();
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

    /**
     * Altera o gasto máximo de uma categoria.
     *
     * @param nomeC      O nome da categoria a ter o gasto máximo alterado.
     * @param gastoMaximo O novo valor do gasto máximo.
     * @return Resposta HTTP indicando o resultado da operação.
     */
    @PUT
    @Path("/alterarGastoMaximoCategoria/{nomeC}/{gastoMaximo}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response alterarGastoMaximoCategoria(
            @PathParam("nomeC") String nomeC, @PathParam("gastoMaximo") Double gastoMaximo) {
        try {
            cs.alterarGastoMaximoCategoria(nomeC, gastoMaximo);
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
