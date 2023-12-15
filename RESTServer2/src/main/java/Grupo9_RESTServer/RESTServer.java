package Grupo9_RESTServer;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import grupo9_FinancasPessoais.Categoria;
import grupo9_FinancasPessoais.CategoriaService;
import grupo9_FinancasPessoais.Meta;
import grupo9_FinancasPessoais.MetaService;
import grupo9_FinancasPessoais.Orcamento;
import grupo9_FinancasPessoais.OrcamentoService;
import grupo9_FinancasPessoais.Subcategoria;
import grupo9_FinancasPessoais.SubcategoriaService;
import grupo9_FinancasPessoais.Transacao;
import grupo9_FinancasPessoais.TransacaoService;

@Path("/")
public class RESTServer {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		return "REST Server : Olaaa!";
	}
}