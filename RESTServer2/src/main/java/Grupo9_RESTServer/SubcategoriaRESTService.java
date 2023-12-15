package Grupo9_RESTServer;

import grupo9_FinancasPessoais.Categoria;
import grupo9_FinancasPessoais.Subcategoria;
import grupo9_FinancasPessoais.SubcategoriaService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Controlador REST para gerenciar subcategorias de gastos.
 */
@Path("/subcategoria")
public class SubcategoriaRESTService {

    private SubcategoriaService ss = new SubcategoriaService();

    /**
     * Método de saudação em texto simples.
     *
     * @return Uma saudação simples em texto.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello() {
        return "REST Server: Olá Mundo! Eu sou o Controlador de Subcategorias";
    }

    /**
     * Obtém a lista de todas as subcategorias.
     *
     * @return Resposta HTTP contendo a lista de subcategorias.
     */
    @GET
    @Path("/getSubcategorias")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubcategorias() {
        try {
            List<Subcategoria> subcategorias = ss.findAllSubcategorias();
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

    /**
     * Obtém uma subcategoria com base no nome.
     *
     * @param nomeSubc O nome da subcategoria a ser obtida.
     * @return Resposta HTTP contendo a subcategoria encontrada ou uma mensagem de erro.
     */
    @GET
    @Path("/getSubcategoria/{nomeSubc}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubcategoria(@PathParam("nomeSubc") String nomeSubc) {
        try {
            Subcategoria subcategoriaResponse = ss.findSubcategoria(nomeSubc);
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

    /**
     * Adiciona uma nova subcategoria.
     *
     * @param subcategoria A subcategoria a ser adicionada.
     * @return Resposta HTTP indicando o resultado da operação.
     */
    @POST
    @Path("/addSubcategoria")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addSubcategoria(Subcategoria subcategoria, @FormParam("nomeC") String nomeC) {
        Subcategoria subcategoriaResponse = ss.updateSubcategoria(subcategoria, nomeC);
        
        return Response.status(Response.Status.CREATED)
                .entity(subcategoriaResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }


    /**
     * Altera o gasto máximo de uma subcategoria.
     *
     * @param nomeSubc    O nome da subcategoria.
     * @param gastoMaximo O novo gasto máximo.
     * @return Resposta HTTP indicando o resultado da operação.
     */
    @PUT
    @Path("/alterarGastoMaximo/{nomeSubc}/{gastoMaximo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response alterarGastoMaximo(@PathParam("nomeSubc") String nomeSubc,
                                       @PathParam("gastoMaximo") double gastoMaximo) {
        try {
            ss.alterarGastoMaximoSubCategoria(nomeSubc, gastoMaximo);
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

    /**
     * Remove uma subcategoria com base no nome.
     *
     * @param nomeSubc O nome da subcategoria a ser removida.
     * @return Resposta HTTP indicando o resultado da operação.
     */
    @DELETE
	@Path("/deleteSubcategoria/{nomeSubc}")
	public Response deleteSubcategoria(@PathParam("nomeSubc") String nomeSubc) {
		boolean subcategoriaRemoved = ss.removeSubcategoria(nomeSubc);
		
		return Response.status(Response.Status.OK)
				.entity(subcategoriaRemoved)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}

    /**
     * Calcula a percentagem de gastos de uma subcategoria em relação aos gastos totais da categoria.
     *
     * @param nomeSubc          O nome da subcategoria.
     * @param gastosCategoria   O total de gastos da categoria.
     * @return Resposta HTTP contendo a percentagem calculada ou uma mensagem de erro.
     */
    @GET
    @Path("/calcularPercentagemGastos/{nomeSubc}/{gastosCategoria}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response calcularPercentagemGastos(@PathParam("nomeSubc") String nomeSubc,
                                              @PathParam("gastosCategoria") double gastosCategoria) {
        try {
            Subcategoria subcategoriaResponse = ss.findSubcategoria(nomeSubc);
            if (subcategoriaResponse != null) {
                double percentagem = ss.calcularPercentagemGastosSubcategoriaComRelacaoCategoria(
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