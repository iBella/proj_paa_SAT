package proj_paa_SAT;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//Usado = 1
//Nao usado = -1
//Disponivel = 0

public class conjIndep {

	private int nVertice;
	private ArrayList<ArrayList<Integer>> grafo = new ArrayList<>();
	private ArrayList<Integer> vGraus = new ArrayList<>();
	private int qtVerticeSolucao;
	private ArrayList<Integer> solucao = new ArrayList<>();
	private ArrayList<Integer> inicial = new ArrayList<>();
	
	public static void main(String[] args) {
		new conjIndep();
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
			vGraus.add(soma);
		}
		System.err.println();
	}
	public void solucaoIncial(){
		for(int i = 0; i < nVertice; i++){
			solucao.add(-1);
			inicial.add(0);
		}
	}
	
	public conjIndep() {
		lerEntrada();
		calculaGrau();
		qtVerticeSolucao = 0;
		solucaoIncial();
		
		branch_and_bound(inicial, 0);
		
		System.err.println(criarSolucao());
	}
	
	public void branch_and_bound(ArrayList<Integer> v, int i){
		if(eCompleto(i)){
			if(qtVerticesUsados(v) > qtVerticeSolucao){
				for(int s : v)
					solucao.add(s);
				qtVerticeSolucao = qtVerticesUsados(v);
			}
		}
		else{
			v.set(i, -1);
			if(eConsistente(v, i) && ePromissor(v, i)){
				branch_and_bound(v, i+1);
			}
			v.set(i, 1);
			if(eConsistente(v, i) && ePromissor(v, i)){
				branch_and_bound(v, i+1);
			}
			v.set(i, 0);
			
		}
	}
	
	public boolean eCompleto(int i){
		if(i < solucao.size())
			return false;
		return true;
	}
	
	public int qtVerticesUsados(ArrayList<Integer> v){
		int cont = 0;
		for(int i = 0; i < v.size(); i++){
			if(v.get(i) == 1){
				cont++;
			}
		}
		return cont;
	}
	
	public boolean eConsistente(ArrayList<Integer> v, int i){
		if(v.get(i) == 1){
			int j = 0;
			while(j < i){
				if(v.get(j) == 1 && (grafo.get(j).get(i) == 1 || grafo.get(i).get(j) == 1)){
					return false;
				}
				j++;
			}
		}
		return true;
	}
	
	public boolean ePromissor(ArrayList<Integer> v, int j){
		int i = 0;
		int consitentes = 0;
		
		while(i < nVertice){
			if(v.get(i) == -1 || v.get(i) == 0){
				if(eConsistente(v, i)){
					consitentes++;
				}
			}
			i++;
		}
			
		if((consitentes + qtVerticesUsados(v)) >= qtVerticeSolucao){
			return true;
		}
		return false;
	}
	
	public ArrayList<Integer> criarSolucao(){
		ArrayList<Integer> solFinal = new ArrayList<>();
		
		int i = 0;
		for(int s : solucao){
			if(s == 1)
				solFinal.add(i);
			i++;
		}
		
		return solFinal;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
