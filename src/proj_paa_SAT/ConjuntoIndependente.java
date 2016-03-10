package proj_paa_SAT;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ConjuntoIndependente {
	
	private int nVertice;
	private ArrayList<ArrayList<Integer>> grafo = new ArrayList<>();
	private ArrayList<Integer> vGraus = new ArrayList<>();
	private ArrayList<Integer> vPertinencia = new ArrayList<>();
	
	public static void main(String[] args) {
		ConjuntoIndependente c = new ConjuntoIndependente();
		System.err.println("Solução: "+c.guloso());
	}
	
	
	public ConjuntoIndependente() {
		lerEntrada();
	}
	
	public void lerEntrada(){
		
		try {
			BufferedReader arq = new BufferedReader(new FileReader("conj_indep.txt"));
			int nLinha = 0;
			
			while(arq.ready()){
				if(nLinha == 0){
					nVertice = Integer.parseInt(arq.readLine());
					nLinha++;
				}
				else{
					String[] partes = arq.readLine().split(" ");
					ArrayList<Integer> temp = new ArrayList<>();
					for(int i = 0; i < nVertice; i++){
						temp.add(Integer.parseInt(partes[i]));
					}
					grafo.add(temp);
				}
			}
			arq.close();
			
			printVerificar();
			
		} 
		catch (FileNotFoundException e) { e.printStackTrace();}
		catch (IOException e) { e.printStackTrace();}

	}

	
	public void calculaGrau(){
		for(ArrayList<Integer> vertice : grafo){
			int soma = 0;
			for(int grau : vertice){
				soma += grau;
			}
			System.err.print(soma+" ");
			vGraus.add(soma);
		}
		System.err.println();
	}
	
	
	public ArrayList<Integer> guloso(){
		
		ArrayList<Integer> solucao = new ArrayList<>();
		
		calculaGrau();
		
		for(int i = 0; i < nVertice; i++)
			vPertinencia.add(0);
		
		while(verificaFim()){
			
			//pegar menor grau
			int vMenorGrau = menorGrau();
			
			//add vertice de menor grau
			solucao.add(vMenorGrau);
				
			//pertinecia - vertice
			vPertinencia.set(vMenorGrau, 1);
				
			int i = 0;
			//pertinecia - vertices vizinhos
			for(int lVertice : grafo.get(vMenorGrau)){
				if(lVertice == 1){
					vPertinencia.set(i, 1);
				}
				i++;
			}
		}
		
		return solucao;
		
	}
	
	public int menorGrau(){
		
		int vertice = vGraus.indexOf(Collections.min(vGraus));
		vGraus.set(vertice, Integer.MAX_VALUE);
		
		if(verificaPert(vertice)){
			return vertice;
		}
		return menorGrau();		
	}
	
	public boolean verificaPert(int vMenorGrau){
		if(vPertinencia.get(vMenorGrau) == 1)
			return false;
		return true;
	}
	
	public boolean verificaFim(){
		int soma = 0;
		for(int v : vPertinencia){
			soma += v;
		}
		if(soma == nVertice)
			return false;
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void printVerificar(){

		System.err.println("___________________________");
		
		//Imprimir numero de Variáveis
		System.err.println("Numero de vértices: "+getnVertice());
		
		//Imprimir Clausulas
		System.err.println("\nMatriz: ");
		for(ArrayList<Integer> linha : grafo){
			for(int i = 0; i < getnVertice(); i++)
				System.err.print(linha.get(i)+" ");
			System.err.println();
		}
		
//		//Imprimir Graus
//		for(int linhaVertice : graus)
//			System.err.print(linhaVertice+" ");
		
		System.err.println("+++++++++++++++++++++++++++\n");
	}

	public int getnVertice() {
		return nVertice;
	}
	public void setnVertice(int nVar) {
		this.nVertice = nVar;
	}

}
