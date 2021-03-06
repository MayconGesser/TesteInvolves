package br.com.involves.selecao.testes.testesaplicacao;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import br.com.involves.selecao.objetos.ParserCSV;
import br.com.involves.selecao.objetos.enums.Comando;

public class TesteFiltragem {
	
	final String caminho = "cidades.csv";
	ParserCSV parser = null;
	final int numeroLinhasTotal = 5564;
	HashMap<String[],Integer> resultadosCertos = new HashMap<>();
	String[] filtragem_1 = {"uf","RO"};
	String[] filtragem_2 = {"uf","AC"};
	String[] filtragem_3 = {"uf","AM"};
	String[] filtragem_4 = {"ibge_id","1300300"};
	ArrayList<String[]> argsFiltragens = new ArrayList<>();
	
	@Test
	public void devePassarQuandoFiltrado() {
		try{
			parser = new ParserCSV(caminho);
		}catch(FileNotFoundException e){
			fail("Arquivo não encontrado");
		}
		resultadosCertos.put(filtragem_1, 51);
		resultadosCertos.put(filtragem_2, 22);
		resultadosCertos.put(filtragem_3, 62);
		
		argsFiltragens.add(filtragem_1);
		argsFiltragens.add(filtragem_2);
		argsFiltragens.add(filtragem_3);
		argsFiltragens.add(filtragem_4);
		
		for(int i = 0; i<argsFiltragens.size()-1; i++){
			int resultadoEsperado = resultadosCertos.get(argsFiltragens.get(i));
			//-1 novamente desconta o cabecalho previamente incluido
			int resultadoFuncaoFiltrar = mockFuncaoFiltrar(argsFiltragens.get(i))-1;
			assertEquals(resultadoEsperado,resultadoFuncaoFiltrar);
		}
		
		//testa se a funcao nao apresenta comportamento inesperado 
		//quando o valor a ser filtrado eh unico
		int resultadoFuncaoFiltrarPorId = (int)mockFuncaoFiltrar(argsFiltragens.get(3))-1;
		assertEquals(1,resultadoFuncaoFiltrarPorId);
	}
	
	public int mockFuncaoFiltrar(String[] args){
		int retorno = parser.executarConsulta(
				Comando.FILTER, args
			).size();
		return retorno;
	}
}
