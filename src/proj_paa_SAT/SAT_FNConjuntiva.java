package proj_paa_SAT;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SAT_FNConjuntiva {

	private ArrayList<int[]> clausulas = new ArrayList<>();
	private int nVar=0;
	private int nClausulas=0;
	private int variaveis[];
	
	private boolean checkTrue[];
	
	private int numTrue;
	
	public SAT_FNConjuntiva() {
	}
	
	/**
	 * 	Method to read input from a file/console and store it in 2*2 matrix
	 */ 
	public void lerEntrada(int entrada){
		
		try {
			BufferedReader arq = new BufferedReader(new FileReader("bolleanSAT_"+entrada+".txt"));
			int nLinha = 0;
			
			while(arq.ready()){
				if(nLinha == 0){
					nVar = Integer.parseInt(arq.readLine());
					nLinha++;
				}
				else{
					String[] partes = arq.readLine().split(" ");
					int[] temp = new int[nVar];
					for(int i = 0; i < nVar; i++){
						temp[i] = Integer.parseInt(partes[i]);
					}
					clausulas.add(temp);
					nClausulas++;
				}
			}
			arq.close();
			
			checkTrue = new boolean[nClausulas];
			variaveis = new int[nVar];
			inicializa();
			
			printVerificar();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 	Method to initialize values array to -1. This array will contain boolean solution for given 3-CNF
	 */ 
	public void inicializa(){
		for(int i=0; i< nVar; i++)
			variaveis[i] = -1;
		for(int i=0; i< nClausulas; i++)
			checkTrue[i] = false;
	}
	
	/**
	 * 	Method to set boolean list values to true for all those next clauses which contains
	 *  the current variables. So next time when clause contains this variable, no need to check that 
	 *  clause as the value of that clause would be anyways 1. This reduced large number of comparisons  
	 */
	public void setClauseTrue(int pos, int valor){
		
		for(int i = 0; i < nClausulas; i++){
			int[] temp = clausulas.get(i);
			
			if(checkTrue[i])
				continue;
			
			if(temp[pos] == valor){
				checkTrue[i] = true;
				System.err.println("CheckClausulaTrue["+i+"] = true");
				numTrue++;
			}
		}
	}
	
	/**
	 * 	Method will iterate through all the clauses to check for the solution. If the clause is already
	 *  true by some previous variable, no need to check it again.
	 */ 
	public void booleanSatisfiability(){
		
		int combinaVar = 0;
		int combinaClausula = 0;

		while( combinaClausula != nClausulas){
			System.err.println("=> Verificando combinação Cláusula: "+ combinaClausula);
			combinaVar = 0;
			
			while( combinaVar != nVar){
				System.err.println("==> Verificando combinação Variável: "+ combinaVar);
				numTrue = 0;
				inicializa();
				
				//percorrer as cláusulas
				for(int i = 0; i < nClausulas; i++){
					//verifica as combinações das cláusulas
					if(i == 0)
						i = combinaClausula;
					
					if(checkTrue[i])
						continue;
					
					int[] temp = clausulas.get(i);
					
					//percorrer os literais
					for(int j = 0; j < nVar; j++){
						//verifica as combinações das variáveis
						if(i == combinaClausula && j == combinaVar)
							j = combinaVar;
						
						int var = temp[j];
						System.err.println("var = "+var);
						
						if(variaveis[j] != -1)
							continue;
						
						if(var == 0){
							variaveis[j] = 0;
							System.err.println("Variavel["+j+"] = false");
						}
						else{
							variaveis[j] = 1;
							System.err.println("Variavel["+j+"] = true");
						}
						
						setClauseTrue(j, var);
						break;			//só é necessário um true por cláusula										
					}
					
					if(numTrue == nClausulas){
						System.err.println("0 = false / 1 = true");
						for(int z = 0; z < nVar; z++){
							if(variaveis[z] == -1)
								variaveis[z] = 1;
							System.out.println(variaveis[z]);
						}
						System.exit(1);
					}
				}
				combinaVar++;
			}
			combinaClausula++;
		}
		
		combinaVar = 0;
		combinaClausula = 0;

		while( combinaClausula != nClausulas){
			System.err.println("=> Verificando combinação Cláusula: "+ combinaClausula);
			combinaVar = 0;
			
			while( combinaVar != nVar){
				System.err.println("==> Verificando combinação Variável: "+ combinaVar);
				numTrue = 0;
				inicializa();
				
				//percorrer as cláusulas
				for(int i = 0; i < nClausulas; i++){
					//verifica as combinações das cláusulas
					if(i == 0)
						i = combinaClausula;
					
					if(checkTrue[i])
						continue;
					
					int[] temp = clausulas.get(i);
					
					//percorrer os literais
					for(int j = 0; j < nVar; j++){
						//verifica as combinações das variáveis
						if(i == combinaClausula && j == combinaVar)
							j = combinaVar;
						
						int var = temp[j];
						System.err.println("var = "+var);
						
						if(variaveis[j] != -1)
							continue;
						
						if(var == 0){
							variaveis[j] = 0;
							System.err.println("Variavel["+j+"] = true");
						}
						else{
							variaveis[j] = 1;
							System.err.println("Variavel["+j+"] = false");
						}
						
						setClauseTrue(j, var);
						break;			//só é necessário um true por cláusula										
					}
					
					if(numTrue == nClausulas){
						System.err.println("0 = true / 1 = false");
						for(int z = 0; z < nVar; z++){
							if(variaveis[z] == -1)
								variaveis[z] = 0;
							System.out.println(variaveis[z]);
						}
						System.exit(1);
					}
				}
				combinaVar++;
			}
			combinaClausula++;
		}
		
		System.out.println("Não Satisfaz");
	}
	
	public void printVerificar(){

		System.err.println("______________________________________");
		
		//Imprimir numero de Variáveis
		System.err.println("Numero de variáveis: "+nVar);
		//Imprimir numero de Clausulas
		System.err.println("Numero de cláusulas: "+nClausulas);
		
		//Imprimir Clausulas
		System.err.println("\nCláusulas: ");
		for(int[] clausula : clausulas){
			for(int i = 0; i < nVar; i++)
				System.err.print(clausula[i]+" ");
			System.err.println();
		}
			
		//Imprimir Valores
		System.err.println("\nValores: ");
		for(int i = 0; i < nVar; i++)
			System.err.print(variaveis[i]+" ");
		System.err.println();
		
		//Imprimir CheckTrue
		System.err.println("\nCheckTrue: ");
		for(int i = 0; i < nClausulas; i++)
			System.err.print(checkTrue[i]+" ");
		System.err.println();
		
		System.err.println("++++++++++++++++++++++++++++++++++++++\n");
	}
	
	/**
	 * 	Driver code for the program. 
	 */ 
	public static void main(String args[]){			
		SAT_FNConjuntiva d = new SAT_FNConjuntiva();
		d.lerEntrada(1);
		d.booleanSatisfiability();
	}

}
