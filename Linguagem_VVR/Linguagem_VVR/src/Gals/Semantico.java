package Gals;

import Gals.SemanticUtils.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class Semantico implements Constants
{
    private Stack<Integer> stackScope = new Stack<>();
    private Stack<Integer> stackType = new Stack<>();
    private ReferenceValueType currentRefAtribType = null;
    private int lastScope = 0;
    private int currentParamPosition = 0;
    private String currentName = null;
    ArrayList<ReferencePointer> references = new ArrayList<>();
    ArrayList<TemporaryReference> tempIdentifiers = new ArrayList<>();
    ArrayList<ReferencePointer> currentReferences = new ArrayList<>();
    TemporaryReference currentReference = null;
    private ReferenceValueType currentVarType;
    private ReferenceType currentRefType;

    public ArrayList<ReferencePointer> getReferences(){
        return references;
    }
    public void executeAction(int action, Token token)	throws SemanticError
    {
        // Verificar se stack de scopo está vazia. Se estiver, colocar o escopo 0, global
        if(stackScope.empty())
            stackScope.push(0);

        switch (action) {


            case 1: {
                System.out.println("Armazenando tipo "+ token.getLexeme() +" da função" );
                switch (token.getLexeme()) {
                    case "int":
                        currentVarType = ReferenceValueType.INT;
                        break;
                    case "string":
                        currentVarType = ReferenceValueType.STRING;
                        break;
                    case "char":
                        currentVarType = ReferenceValueType.CHAR;
                        break;
                    case "double":
                        currentVarType = ReferenceValueType.DOUBLE;
                        break;
                    case "bool":
                        currentVarType = ReferenceValueType.BOOL;
                        break;
                    default:
                        throw new SemanticError("Função sendo declarada com tipo não esperado");
                }
                System.out.println("Tipo " + currentVarType.toString() + " armazenado");
                currentRefType = ReferenceType.FUNCAO;
                System.out.println("Referência a ser salva: " + currentRefType.toString());
                break;
            }
            case 2:
                currentName = token.getLexeme();
                currentReference = new TemporaryReference(currentName,false);
                tempIdentifiers.add(currentReference);
                System.out.println("Salvando nome "+ currentName + "");
                //Verificando o tipo de refência a ser salvo na lista
                break;
            case 3: {
                System.out.println("Armazenando tipo " + token.getLexeme() + " do parâmetro");
                switch (token.getLexeme()) {
                    case "int":
                        currentVarType = ReferenceValueType.INT;
                        break;
                    case "string":
                        currentVarType = ReferenceValueType.STRING;
                        break;
                    case "char":
                        currentVarType = ReferenceValueType.CHAR;
                        break;
                    case "double":
                        currentVarType = ReferenceValueType.DOUBLE;
                        break;
                    case "bool":
                        currentVarType = ReferenceValueType.BOOL;
                        break;
                    default:
                        throw new SemanticError("Parametro sendo declarada com tipo não esperado");
                }
                System.out.println("Tipo " + currentVarType.toString() + " armazenado");
                currentRefType = ReferenceType.PARAMETRO;
                System.out.println("Referência a ser salva: " + currentRefType.toString());
                break;
            }
            case 4:

                lastScope++;
                System.out.println("Incrementando scopo para "+String.valueOf(lastScope));
                stackScope.push(lastScope);
                break;

            case 5, 7, 12:
                System.out.println("Limpando valores armazenados até então");
                currentName = null;
                currentReferences = new ArrayList<>();
                currentRefType = null;
                currentVarType = null;
                currentReference = null;
                currentParamPosition = 0;
                tempIdentifiers = new ArrayList<>();
                break;

            case 6:
                currentParamPosition++;
                System.out.println("Incrementando posição do param para " +currentParamPosition);
                break;

            case 8:
                System.out.println("Encerrando escopo");
                stackScope.pop();

                ReferencePointer.PrintListaDeReferencia(references);
                break;

            case 9:
            {
                System.out.println("Armazenando tipo " + token.getLexeme() + " da variavél");
                switch (token.getLexeme()) {
                    case "int":
                        currentVarType = ReferenceValueType.INT;
                        break;
                    case "string":
                        currentVarType = ReferenceValueType.STRING;
                        break;
                    case "char":
                        currentVarType = ReferenceValueType.CHAR;
                        break;
                    case "double":
                        currentVarType = ReferenceValueType.DOUBLE;
                        break;
                    case "bool":
                        currentVarType = ReferenceValueType.BOOL;
                        break;
                    default:
                        throw new SemanticError("Parametro sendo declarada com tipo não esperado");
                }
                System.out.println("Tipo " + currentVarType.toString() + " armazenado");
                currentRefType = ReferenceType.VAR;
                System.out.println("Referência a ser salva: " + currentRefType.toString());
                break;
            }
            case 10:{
                System.out.println("Procurando referência na tabela");
                Stack<Integer> stackTemp = (Stack<Integer>) stackScope.clone();
                //System.out.println("Iterando escopo " + stackScope.peek());
                ReferencePointer referenciaEncontrada = null;
                while (!stackTemp.empty() && referenciaEncontrada == null){
                    int scope = stackTemp.peek();
                    System.out.println("Iterando escopo " + lastScope);
                    referenciaEncontrada = ReferencePointer.procurarReferencia(currentReference,scope,false,references);
                    stackTemp.pop();
                }
                if(referenciaEncontrada == null){
                    throw new SemanticError("Variavél " + currentReference.getNome() + " não encontrada");
                }
                referenciaEncontrada.setIniciada(true);
                System.out.println("Inserindo tipo da referência na stack");
                currentRefAtribType = referenciaEncontrada.getTipo();
                break;
            }
            case 11:{
                System.out.println("Referência é vetor");
                currentReference.setVector(true);
                break;
            }

            case 13: {

                System.out.println("Adiciona a função pra lista");
                ReferencePointer reference = new ReferencePointer(currentReference.getNome(), currentVarType, true, false, stackScope.peek(), false, 0, currentReference.isVector(), false, true);
                references.add(reference);
                System.out.println("Imprime Lista de referência");
                //ReferencePointer.PrintListaDeReferencia(references);
                break;
            }
            case 14: {
                System.out.println("Adiciona o param pra lista");
                references.add(new ReferencePointer(currentReference.getNome(),currentVarType,true,false,stackScope.peek() + 1,true,currentParamPosition,currentReference.isVector(),false,false));
                currentName = null;
                System.out.println("Imprime Lista de referência");
                //ReferencePointer.PrintListaDeReferência(references);
                break;
            }

            case 15: {
                System.out.println("Procurando referência na tabela");
                Stack<Integer> stackTemp = (Stack<Integer>) stackScope.clone();
                //System.out.println("Iterando escopo " + stackScope.peek());
                ReferencePointer referenciaEncontrada = null;
                while (!stackTemp.empty() && referenciaEncontrada == null){
                    int scope = stackTemp.peek();
                    System.out.println("Iterando escopo " + lastScope);
                    referenciaEncontrada = ReferencePointer.procurarReferencia(currentReference,scope,false,references);
                    stackTemp.pop();
                }
                if(referenciaEncontrada == null){
                    throw new SemanticError("Variavél " + currentReference.getNome() + " não encontrada");
                }
                referenciaEncontrada.setUtilizada(true);
                //ReferencePointer.PrintListaDeReferencia(references);
                break;
            }

            case 16:{

                System.out.println("Utilizar todas as referencias em memória");
                for(ReferencePointer reference : currentReferences){
                    System.out.println("Utilizando a referência "+ reference.getNome());
                    reference.setIniciada(true);

                }
                break;
            }

            case 17: {
                System.out.println("Adicionando todos os identificadores na tabela de referência");
                for(TemporaryReference identifier : tempIdentifiers) {
                    ReferencePointer reference = new ReferencePointer(identifier.getNome(), currentVarType, false, false, stackScope.peek(), false, 0, identifier.isVector(), false, false);

                    boolean existeVar = ReferencePointer.existeVariavelIgual(references, reference);

                    if (existeVar) {
                        throw new SemanticError("Tentativa de adicionar a var " + identifier.getNome() + " em um escopo em que já está declarada");
                    }
                    references.add(reference);
                    currentReferences.add(reference);
                }

                break;
            }
            case 18: {
                System.out.println("Verificando se retorno da expressão bate com o referência atruibuída");

                int resultadoExp = stackType.pop();

                int resultadoAtrib = SemanticTable.atribType(currentRefAtribType.getVarCode(),resultadoExp);

                if(resultadoAtrib == ReturnType.ERR.getCode()){
                    throw new SemanticError ("Atribuição da váriavel "+ currentReference.getNome());
                }
                System.out.println("Atribuição da var "+currentReference.getNome() +" válida");

                break;
            }
            case 31:{
                stackType.push(ReferenceValueType.INT.getVarCode());
                break;
            }
            case 35:{
                stackType.push(ReferenceValueType.CHAR.getVarCode());
                break;
            }


            default:
                System.out.println("Acao #"+action+", Token: "+token);
                break;
        }
    }
}
