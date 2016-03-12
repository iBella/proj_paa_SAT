package proj_paa_SAT;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Clique {

	private int nVertice;
	private ArrayList<ArrayList<Integer>> grafo = new ArrayList<>();
	private ConjuntoIndependente c;

	public Clique(){}
	
	//Construtor para teste
	public Clique(String s){
		lerEntrada(s);
	}
	
	public void lerEntrada(String s){
		
		try {
			BufferedReader arq = new BufferedReader(new FileReader(s));
			int nLinha = 0;
			int j = 0;
			
			while(arq.ready()){
				
				if(nLinha == 0){
					nVertice = Integer.parseInt(arq.readLine());
					nLinha++;
				}
				else{
					String[] partes = arq.readLine().split(" ");
					ArrayList<Integer> temp = new ArrayList<>();
					for(int i = 0; i < nVertice; i++){
						if(Integer.parseInt(partes[i]) == 0 && i != j){
							temp.add(1);
							//System.err.print(1+" ");
						}
						else{
							temp.add(0);
							//System.err.print(0+" ");
						}
					}
					grafo.add(temp);
					//System.err.println();
					j++;
				}
			}
			arq.close();
			
		} 
		catch (FileNotFoundException e) { e.printStackTrace();}
		catch (IOException e) { e.printStackTrace();}

	}

	public void algoritmoClique(){
		c = new ConjuntoIndependente(grafo, nVertice);
		getC().algoritmoConjuntoIndep();
	}
	
	public ConjuntoIndependente getC() {
		return c;
	}
	
	public void teste() {
		
		System.err.println("_____________________________");
		System.err.println("CLIQUE");
		
		Clique c01 = new Clique("clique01.txt");
		Clique c02 = new Clique("clique02.txt");
		Clique c03 = new Clique("clique03.txt");
		
		long tempo1 = System.nanoTime();
		c01.algoritmoClique();
		tempo1 = System.nanoTime() - tempo1; 
		System.err.println("Teste 1:\nSolução: "+c01.getC().criarSolucao()+"\nTempo: "+tempo1);
		
		long tempo2 = System.nanoTime();
		c02.algoritmoClique();
		tempo2 = System.nanoTime() - tempo2; 
		System.err.println("Teste 2:\nSolução: "+c02.getC().criarSolucao()+"\nTempo: "+tempo2);
		
		long tempo3 = System.nanoTime();
		c03.algoritmoClique();
		tempo3 = System.nanoTime() - tempo3; 
		System.err.println("Teste 3:\nSolução: "+c03.getC().criarSolucao()+"\nTempo: "+tempo3);

	}
}
