package Gals;

import Gals.SemanticUtils.ReferenceType;

import java.util.Stack;

public class Semantico implements Constants
{
    private Stack stackScope = new Stack();
    private int currentScope = 0;

    private String currentName = null;

    private ReferenceType currentType;
    public void executeAction(int action, Token token)	throws SemanticError
    {
        switch (action) {
            case 4:
                // Verificar se stack de scopo está vazia. Se estiver, colocar o escopo 0, global
                if(stackScope.empty())
                    stackScope.push(currentScope);

                currentScope++;
                System.out.println("Incrementando scopo para "+String.valueOf(currentScope));
                stackScope.push(currentScope);
                break;


            case 1: {
                System.out.println("Armazenando tipo "+ token.getLexeme() +" da função" );
                switch (token.getLexeme()) {
                    case "int":
                        currentType = ReferenceType.INT;
                        break;
                    case "string":
                        currentType = ReferenceType.STRING;
                        break;
                    case "char":
                        currentType = ReferenceType.CHAR;
                        break;
                    case "double":
                        currentType = ReferenceType.DOUBLE;
                        break;
                    case "bool":
                        currentType = ReferenceType.BOOL;
                        break;
                    default:
                        throw new SemanticError("Função sendo declarada com tipo não esperado");
                }
                System.out.println("Tipo " + currentType.toString() + " armazenado");
                break;
            }
            case 2:
                currentName = token.getLexeme();
                System.out.println("Salvando nome "+ currentName + " da função");
                break;

            default:
                System.out.println("Acao #"+action+", Token: "+token);
                break;
        }
    }
}
