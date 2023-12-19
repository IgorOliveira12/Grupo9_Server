package Grupo9_RESTServer;

import grupo9_FinancasPessoais.*;

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
    public Response getSubcategorias() {
    	List<Subcategoria> subcategorias = ss.findAllSubcategorias();

		return Response.status(Response.Status.OK)
				.entity(subcategorias)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}

    /**
     * Obtém uma subcategoria com base no nome.
     *
     * @param nomeSubc O nome da subcategoria a ser obtida.
     * @return Resposta HTTP contendo a subcategoria encontrada ou uma mensagem de erro.
     */
    @GET
    @Path("/getSubcategoria/{nomeSubc}")
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
    public Response addSubcategoria(Subcategoria subcategoria) {
        Subcategoria subcategoriaResponse = ss.updateSubcategoria(subcategoria.getNomeSubc(), subcategoria.getGastoMaxSubc());
        
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
    @Path("/alterarGastoMaximo/{nomeSubcategoria}/{gastoMaximo}")
    public Response alterarGastoMaximoCategoria(
            @PathParam("nomeSubcategoria") String nomeSubcategoria, @PathParam("gastoMaximo") Double gastoMaximo) {
        try {
            ss.alterarGastoMaximoSubCategoria(nomeSubcategoria, gastoMaximo);
            return Response.status(Response.Status.OK)
                    .entity("Valor máximo da Categoria " + nomeSubcategoria + " alterado para: " + gastoMaximo)
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
    
    @PUT
    @Path("/atribuirCategoriaNaSubcategoria/{nomeC}/{nomeSubc}")
    public Response atribuirCategoriaNaSubcategoria(@PathParam("nomeC") String nomeC,
                                              @PathParam("nomeSubc") String nomeSubc) {
        try {
        	CategoriaService cs = new CategoriaService();
        	Categoria categoriaResponse = cs.findCategoria(nomeC);
            Subcategoria subcategoriaResponse = ss.findSubcategoria(nomeSubc);
            if (categoriaResponse != null && subcategoriaResponse != null) {
                ss.atribuirCategoriaNaSubcategoria(categoriaResponse, subcategoriaResponse);
                return Response.status(Response.Status.OK)
                        .entity("Categoria atribuída com sucesso à subcategoria.")
                        .build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Categoria ou subcategoria não encontrada.")
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
    
    public static String replaceS(String input) {
        return input.replaceAll("_", " ");
    }
    
}