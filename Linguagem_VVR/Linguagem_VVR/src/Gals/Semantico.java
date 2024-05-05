package Gals;

import Gals.SemanticUtils.ReferencePointer;
import Gals.SemanticUtils.ReferenceType;
import Gals.SemanticUtils.ReferenceValueType;

import java.util.ArrayList;
import java.util.Stack;

public class Semantico implements Constants
{
    private Stack<Integer> stackScope = new Stack<>();

    private int lastScope = 0;
    private int currentParamPosition = 0;
    private String currentName = null;
    ArrayList<ReferencePointer> references = new ArrayList<>();
    ArrayList<String> tempIdentifiers = new ArrayList<>();

    ArrayList<ReferencePointer> currentReferences = new ArrayList<>();
    private ReferenceValueType currentVarType;
    private ReferenceType currentRefType;
    boolean isVector = false;
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
                tempIdentifiers.add(currentName);
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
                isVector = false;
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
            case 11:{
                isVector = true;
                break;
            }

            case 13: {

                System.out.println("Adiciona a função pra lista");
                ReferencePointer reference = new ReferencePointer(currentName, currentVarType, false, false, stackScope.peek(), false, 0, isVector, false, true);
                references.add(reference);
                System.out.println("Imprime Lista de referência");
                isVector = false;
                //ReferencePointer.PrintListaDeReferencia(references);
                break;
            }
            case 14: {
                System.out.println("Adiciona o param pra lista");
                references.add(new ReferencePointer(currentName,currentVarType,true,false,stackScope.peek(),true,currentParamPosition,isVector,false,false));
                currentName = null;
                System.out.println("Imprime Lista de referência");
                isVector = false;
                //ReferencePointer.PrintListaDeReferência(references);
                break;
            }

            case 15: {
                System.out.println("Adiciona a(s) var(s) pra lista");

                ReferencePointer reference = new ReferencePointer(currentName, currentVarType, false, false, stackScope.peek(), false, 0, isVector, false, false);

                boolean existeVar = ReferencePointer.existeVariavelIgual(references,reference);

                if(existeVar){
                    throw new SemanticError("Tentativa de adicionar a var "+currentName+" em um escopo em que já está declarada");
                }
                references.add(reference);
                currentReferences.add(reference);
                isVector = false;

                System.out.println("Imprime Lista de referência");
                //ReferencePointer.PrintListaDeReferencia(references);
                break;
            }

            default:
                System.out.println("Acao #"+action+", Token: "+token);
                break;
        }
    }
}
