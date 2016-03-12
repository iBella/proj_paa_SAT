package proj_paa_SAT;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SAT {

	private int nVariaveis;
	private ArrayList<ArrayList<Integer>> matriz = new ArrayList<>();
	private ConjuntoIndependente c;
	private int nClausulas = 0;
	private ArrayList<Integer> listaDeVertices;
	private ArrayList<ArrayList<Integer>> grafo = new ArrayList<>();
	
	public SAT(){}
	
	//Construtor para teste
	public SAT(String s) {
		lerEntrada(s);
		grafo();
	}
	
	public void lerEntrada(String s){
		
		try {
			BufferedReader arq = new BufferedReader(new FileReader(s));
			int nLinha = 0;
			
			while(arq.ready()){
				
				if(nLinha == 0){
					nVariaveis = Integer.parseInt(arq.readLine());
					nLinha++;
				}
				else{
					String[] partes = arq.readLine().split(" ");
					ArrayList<Integer> temp = new ArrayList<>();
					for(int i = 0; i < nVariaveis; i++){
						temp.add(Integer.parseInt(partes[i]));
					}
					matriz.add(temp);
					nClausulas++;
				}
			}
			arq.close();
		} 
		catch (FileNotFoundException e) { e.printStackTrace();}
		catch (IOException e) { e.printStackTrace();}

	}

	public void grafo(){
		//inicializa o grafo com 0
		for(int i = 0; i < nClausulas*nVariaveis; i++){
			ArrayList<Integer> temp = new ArrayList<>();
			for(int j = 0; j < nClausulas*nVariaveis; j++){
				temp.add(0);
			}
			grafo.add(temp);
		}
		
		
		int i = 0;
		int clausulasAtuais = 0;
		listaDeVertices = new ArrayList<>();
		
		for(int j = 0; j < nClausulas; j++){
			
			for(int vertice: matriz.get(j)){
				
				listaDeVertices.add(vertice);
				
				if(vertice == 2){
					i++;
					continue;
				}
					
				for(int k = 0; k < clausulasAtuais; k++){
					if(listaDeVertices.get(k) == 2)
						continue;
					if(k%nVariaveis == i%nVariaveis && 
							(listaDeVertices.get(i) + listaDeVertices.get(k)) == 1){
						continue;
					}
					grafo.get(k).set(i, 1);
					grafo.get(i).set(k, 1);
				}
				i++;
			}
			clausulasAtuais += nVariaveis;
		}
		
		
		for(i = 0; i < nClausulas*nVariaveis; i++){
			for(int j = 0; j < nClausulas*nVariaveis; j++){
				if(grafo.get(i).get(j) == 0 && i != j)
					grafo.get(i).set(j, 1);
				else
					grafo.get(i).set(j, 0);
			}
		}
	}

	public void algoritmoSAT(){
		c = new ConjuntoIndependente(grafo, nClausulas*nVariaveis);
		getC().algoritmoConjuntoIndep();
	}
	
	public ConjuntoIndependente getC() {
		return c;
	}
	
	public ArrayList<Boolean> criarSolucaoSAT(){
		ArrayList<Boolean> solucao = new ArrayList<>();
		ArrayList<Integer> solucaoCI = getC().criarSolucao();
		
		if(solucaoCI.size() < nClausulas)
			return solucao;
		
		for(int i = 0; i < nVariaveis; i++){
			solucao.add(false);
		}
		for(int s: solucaoCI){
			if(listaDeVertices.get(s) == 1)
				solucao.set(s%nVariaveis, true);
			else
				solucao.set(s%nVariaveis, false);
		}
		return solucao;
	}
	
	public void teste() {
		
		System.err.println("_____________________________");
		System.err.println("SAT");
		
		SAT c01 = new SAT("sat01.txt");
		SAT c02 = new SAT("sat02.txt");
		SAT c03 = new SAT("sat03.txt");
		
		long tempo1 = System.nanoTime();
		c01.algoritmoSAT();
		tempo1 = System.nanoTime() - tempo1; 
		System.err.println("Teste 1:\nSolução: "+c01.criarSolucaoSAT()+"\nTempo: "+tempo1);
		
		long tempo2 = System.nanoTime();
		c02.algoritmoSAT();
		tempo2 = System.nanoTime() - tempo2; 
		System.err.println("Teste 2:\nSolução: "+c02.criarSolucaoSAT()+"\nTempo: "+tempo2);
		
		long tempo3 = System.nanoTime();
		c03.algoritmoSAT();
		tempo3 = System.nanoTime() - tempo3; 
		System.err.println("Teste 3:\nSolução: "+c03.criarSolucaoSAT()+"\nTempo: "+tempo3);

	}
}
