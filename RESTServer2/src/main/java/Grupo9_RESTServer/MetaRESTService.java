package Grupo9_RESTServer;

import grupo9_FinancasPessoais.Categoria;
import grupo9_FinancasPessoais.Meta;
import grupo9_FinancasPessoais.MetaService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Controlador REST para gerenciar metas financeiras.
 */
@Path("/meta")
public class MetaRESTService {

    private MetaService ms = new MetaService();

    /**
     * Método de saudação em texto simples.
     *
     * @return Uma saudação simples em texto.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello() {
        return "REST Server: Olá Mundo! Eu sou o Controlador de Metas";
    }


    @POST
	@Path("/addMeta")
	public Response addCategoria(Meta meta) {		
    	Meta metaResponse = ms.updateMeta(meta.getNome(), meta.getDescricao(), meta.getValor(), meta.getData());
		
		return Response.status(Response.Status.CREATED)
				.entity(metaResponse)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
    
    @DELETE
	@Path("/deleteMeta/{nome}")
	public Response deleteMeta(@PathParam("nome") String nome) {
		boolean metaRemoved = ms.removeMeta(nome);
		
		return Response.status(Response.Status.OK)
				.entity(metaRemoved)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}

    /**
     * Obtém a lista de todas as categorias.
     *
     * @return Resposta HTTP contendo a lista de categorias.
     */
    @GET
    @Path("/getMetas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMetas() {
    	List<Meta> metas = ms.findAllMetas();

		return Response.status(Response.Status.OK)
				.entity(metas)
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
    @Path("/getMeta/{nome}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMeta(@PathParam("nome") String nome) {
        try {
        	Meta metaResponse = ms.findMeta(nome);
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


    /**
     * Altera o valor de uma meta.
     *
     * @param nome      O nome da meta a ter o valor alterado.
     * @param novoValor O novo valor da meta.
     * @return Resposta HTTP indicando o resultado da operação.
     */
    @PUT
    @Path("/alterarValorMeta/{nome}/{novoValor}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response alterarValorMeta(
            @PathParam("nome") String nome, @PathParam("novoValor") Double novoValor) {
        try {
            ms.alterarValorMeta(nome, novoValor);
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

    /**
     * Altera o prazo de uma meta.
     *
     * @param nome      O nome da meta a ter o prazo alterado.
     * @param novaData  A nova data de prazo da meta.
     * @return Resposta HTTP indicando o resultado da operação.
     */
    @PUT
    @Path("/alterarPrazoMeta/{nome}/{novaData}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response alterarPrazoMeta(
            @PathParam("nome") String nome, @PathParam("novaData") String novaData) {
        try {
            ms.alterarPrazoMeta(nome, novaData);
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

    /**
     * Verifica as metas cumpridas.
     *
     * @return Resposta HTTP indicando o resultado da operação.
     */
    @GET
    @Path("/verificarMetasCumpridas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response verificarMetasCumpridas() {
        try {
            ms.verificarMetasCumpridas();
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

    /**
     * Lista as metas não cumpridas.
     *
     * @return Resposta HTTP indicando o resultado da operação.
     */
    @GET
    @Path("/listarMetasNaoCumpridas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarMetasNaoCumpridas() {
        try {
            ms.listarMetasNaoCumpridas();
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
