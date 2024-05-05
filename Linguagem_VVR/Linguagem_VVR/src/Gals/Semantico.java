package Gals;

import Gals.SemanticUtils.ReferencePointer;
import Gals.SemanticUtils.ReferenceType;
import Gals.SemanticUtils.ReferenceValueType;

import java.util.ArrayList;
import java.util.Stack;

public class Semantico implements Constants
{
    private Stack<Integer> stackScope = new Stack<>();
    private int currentScope = 0;
    private int currentParamPosition = 0;
    private String currentName = null;
    ArrayList<ReferencePointer> references = new ArrayList<>();
    private ReferenceValueType currentVarType;
    private ReferenceType currentRefType;

    public void executeAction(int action, Token token)	throws SemanticError
    {
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
                System.out.println("Salvando nome "+ currentName + "");
                //Verificando o tipo de refência a ser salvo na lista
                switch (currentRefType){
                    case FUNCAO :{
                        System.out.println("Adiciona a referencia pra lista");
                        references.add(new ReferencePointer(currentName,currentVarType,false,false,currentScope,false,0,false,false,true));
                        System.out.println("Imprime Lista de referência");
                        ReferencePointer.PrintListaDeReferência(references);
                        break;
                    }
                    case PARAMETRO:{
                        System.out.println("Adiciona a referencia pra lista");
                        references.add(new ReferencePointer(currentName,currentVarType,true,false,currentScope,true,currentParamPosition,false,false,false));
                        currentName = null;
                        System.out.println("Imprime Lista de referência");
                        ReferencePointer.PrintListaDeReferência(references);
                        break;
                    }

                    case VAR:{
                        System.out.println("Adiciona a referencia pra lista");
                        references.add(new ReferencePointer(currentName,currentVarType,true,false,currentScope,false,0,false,false,false));
                        currentName = null;
                        System.out.println("Imprime Lista de referência");
                        ReferencePointer.PrintListaDeReferência(references);
                        break;
                    }

                }
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
                // Verificar se stack de scopo está vazia. Se estiver, colocar o escopo 0, global
                if(stackScope.empty())
                    stackScope.push(currentScope);

                currentScope++;
                System.out.println("Incrementando scopo para "+String.valueOf(currentScope));
                stackScope.push(currentScope);
                break;

            case 6:
                currentParamPosition++;
                System.out.println("Incrementando posição do param para " +currentParamPosition);
                break;

            case 7:
                System.out.println("Reiniciando a posição dos params");
                currentParamPosition = 0;

                break;

            case 8:
                System.out.println("Encerrando escopo");
                stackScope.pop();

                currentScope = stackScope.peek();;
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

            case 12:

            default:
                System.out.println("Acao #"+action+", Token: "+token);
                break;
        }
    }
}
