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
	@Path("/deleteMeta/{nomeMeta}")
	public Response deleteMeta(@PathParam("nomeMeta") String nomeMeta) {
		boolean metaRemoved = ms.removeMeta(nomeMeta);
		
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
    public Response getMetas() {
    	List<Meta> metas = ms.findAllMetasDoUltimoOrcamento();

		return Response.status(Response.Status.OK)
				.entity(metas)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
    @GET
    @Path("/getAllMetas")
    public Response getAllMetas() {
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
    @Path("/alterarPrazoMeta/{nomeMeta}/{novaData}")
    public Response alterarPrazoMeta(
            @PathParam("nomeMeta") String nomeMeta, @PathParam("novaData") String novaData) {
        try {
            ms.alterarPrazoMeta(nomeMeta, novaData);
            return Response.status(Response.Status.OK)
                    .entity("Prazo da Meta " + nomeMeta + " alterado!")
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
    public Response verificarMetasCumpridas() {
        try {
            String resultado = ms.obterMetasCumpridas();
            return Response.status(Response.Status.OK)
                    .entity(resultado)
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
    public Response listarMetasNaoCumpridas() {
        try {
            String resultadoJson = ms.obterMetasNaoCumpridas();
            return Response.status(Response.Status.OK)
                    .entity(resultadoJson)
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar metas não cumpridas: " + e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }
}
