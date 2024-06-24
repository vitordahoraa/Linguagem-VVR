package Gals;

import Gals.SemanticUtils.*;

import java.sql.Ref;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Stack;

public class Semantico implements Constants
{
    private StringBuilder STRB_Assembly_DATA = new StringBuilder(".data\n temp1 : 0 \n temp2 : 0 \n temp3 : 0 \n");
    private StringBuilder STRB_Assembly_INSTRUCTION = new StringBuilder(".text\n");
    private Stack<Integer> stackScope = new Stack<>();
    private Stack<Integer> stackType = new Stack<>();
    private Stack<Integer> expressionPosition = new Stack<>();

    private Stack<Integer> stackOperator = new Stack<>();
    private Stack<Object> stackValue = new Stack<>();
    private Stack<String> stackLabel = new Stack<>();
    private ReferenceValueType currentRefAtribType = null;
    private ReferencePointer currentRefAtrib = null;
    private TemporaryReference vectorReference = null;
    private TemporaryReference TempVectorReference = null;
    private boolean isVectorReference = false;
    private int lastScope = 0;
    private int currentParamPosition = 0;
    boolean isFirstInEXP = true;
    boolean isAtribToVector = false;

    private int vectorSizeOrPosition = 0;
    private String currentName = null;
    private ArrayList<ReferencePointer> references = new ArrayList<>();
    private ArrayList<TemporaryReference> tempIdentifiers = new ArrayList<>();
    private ArrayList<ReferencePointer> currentReferences = new ArrayList<>();
    private TemporaryReference currentReference = null;
    private ReferenceValueType currentVarType;
    private ReferenceType currentRefType;
    private boolean isDeclaration;
    private boolean isResultEXP;

    private String lastBooleanOperator;
    private String AssemblyVariableINC;
    private int assemblyInstructionLength;
    private String incrementAssemblyInstructions;
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
                System.out.println("Salvando nome "+ currentName);

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
                currentName = null;
                currentReferences = new ArrayList<>();
                currentRefType = null;
                currentVarType = null;
                currentReference = null;
                currentParamPosition = 0;
                tempIdentifiers = new ArrayList<>();
                stackType = new Stack<>();
                stackOperator =  new Stack<>();
                currentRefAtrib =  null;
                isFirstInEXP = true;
                isVectorReference = false;
                vectorReference = null;
                expressionPosition.clear();
                isResultEXP = true;
                isAtribToVector = false;
                TempVectorReference = null;
                AssemblyVariableINC = null;
                assemblyInstructionLength = 0;

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
                stackType = new Stack<>();
                stackOperator =  new Stack<>();
                stackValue = new Stack<>();
                isDeclaration = false;
                currentRefAtrib =  null;
                isFirstInEXP = true;
                isVectorReference = false;
                vectorReference = null;
                expressionPosition.clear();
                isResultEXP = true;
                isAtribToVector = false;
                TempVectorReference = null;
                lastBooleanOperator = null;
                AssemblyVariableINC = null;
                assemblyInstructionLength = 0;
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
                currentRefAtribType = currentVarType;
                isDeclaration = true;
                System.out.println("É declaração");

                System.out.println("Referência a ser salva: " + currentRefType.toString());


                break;
            }
            case 10:{
                System.out.println("Procurando referência na tabela para atribuir");
                Stack<Integer> stackTemp = (Stack<Integer>) stackScope.clone();
                //System.out.println("Iterando escopo " + stackScope.peek());
                TemporaryReference searchReference = (isVectorReference) ? vectorReference : currentReference;
                ReferencePointer referenciaEncontrada = null;
                while (!stackTemp.empty() && referenciaEncontrada == null){
                    int scope = stackTemp.peek();
                    System.out.println("Iterando escopo " + lastScope);
                    referenciaEncontrada = ReferencePointer.procurarReferencia(searchReference,scope,false,references);
                    stackTemp.pop();
                }
                if(referenciaEncontrada == null){
                    throw new SemanticError("Variavél " + searchReference.getNome() + " não encontrada");
                }
                referenciaEncontrada.setIniciada(true);
                System.out.println("Inserindo tipo da referência na stack");
                currentRefAtribType = referenciaEncontrada.getTipo();
                currentRefAtrib = referenciaEncontrada;
                if(vectorReference != null){
                    STRB_Assembly_INSTRUCTION.append("STO temp1\n");
                    isAtribToVector = true;
                }
                break;
            }
            case 11:{
                System.out.println("Referência é vetor");
                vectorReference.setVector(true);
                vectorReference .setVectorSize(vectorSizeOrPosition);
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
                references.add(new ReferencePointer(currentReference.getNome(),currentVarType,true,false,lastScope + 1,true,currentParamPosition,currentReference.isVector(),false,false));
                currentName = null;
                System.out.println("Imprime Lista de referência");
                //ReferencePointer.PrintListaDeReferência(references);
                break;
            }

            case 15: {
                System.out.println("Procurando referência na tabela na chamada de parâmetro");

                Stack<Integer> stackTemp = (Stack<Integer>) this.stackScope.clone();
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
                stackType.push(referenciaEncontrada.getTipo().getVarCode());

                break;
            }

            case 16:{

                System.out.println("Utilizar todas as referencias em memória");
                for(ReferencePointer reference : currentReferences){
                    System.out.println("Utilizando a referência "+ reference.getNome());
                    reference.setIniciada(true);

                }
                isResultEXP = true;
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
                    if (reference.isVector())
                        reference.setVectorSize(identifier.getVectorSize());
                    references.add(reference);
                    currentReferences.add(reference);
                }

                break;
            }
            case 18: {
                System.out.println("Verificando se retorno da expressão bate com o referência atruibuída");

                int resultadoExpType = stackType.pop();

                int resultadoAtribType = SemanticTable.atribType(currentRefAtribType.getVarCode(),resultadoExpType);
                //  System.out.println("Resultado EXP : " + resultadoExp + "\n CurrentRefAtribType: "+ currentRefAtribType.getVarCode() + "\n Resultado: " + resultadoAtrib);

                if(resultadoAtribType == ReturnType.ERR.getCode()){
                    throw new SemanticError ("Atribuição com a referência "+ currentReference.getNome() + " incorreta");
                }
                System.out.println("Atribuição da var "+currentRefAtrib.getNome() +" válida, inserindo valor");
                //Verificar se a atribuição é vetor
                if(!currentRefAtrib.isVector() ) {
                    STRB_Assembly_INSTRUCTION.append("STO " + currentRefAtrib.getNome() + "_" + currentRefAtrib.getEscopo()+"\n");
                    try {
                        currentRefAtrib.setLastValue((Integer) stackValue.peek());
                    } catch (java.util.EmptyStackException ex) {
                        System.out.println("\n\nERRO AO OLHAR STACK DE VALORES, PROVAVELMENTE POR QUE HÁ VETORES NA EXPRESSÃO. TABELA DE REFERÊNCIAS SE DESATUALIZARÁ!");
                    }
                } else{
                    STRB_Assembly_INSTRUCTION.append("STO temp2\n");
                    STRB_Assembly_INSTRUCTION.append("LD temp1\n");
                    STRB_Assembly_INSTRUCTION.append("STO $indr\n");
                    STRB_Assembly_INSTRUCTION.append("LD temp2\n");
                    STRB_Assembly_INSTRUCTION.append("STOV "+currentRefAtrib.getNome()+"_"+currentRefAtrib.getEscopo()+"\n");


                }
                break;
            }
            case 19:{
                System.out.println("Validando expressão booleana");
                int resultEXP = stackType.pop();
                if(resultEXP != ReferenceValueType.BOOL.getVarCode()){
                    throw new SemanticError("Expressão booleana experada");
                }
                break;
            }

            case 20:{
                System.out.println("Atribuindo indexização para o vetor");
                int resultEXP = stackType.pop();
                if(resultEXP != ReferenceValueType.INT.getVarCode()){
                    throw new SemanticError("Expressão inteira experada para tamanho do vetor");
                }
                vectorSizeOrPosition = (Integer) stackValue.pop();

                break;
            }

            case 21: {

                System.out.println("Procurando referência na tabela para valor de expressão");
                Stack<Integer> stackTemp = (Stack<Integer>) this.stackScope.clone();
                TemporaryReference searchReference = currentReference;

                ReferencePointer referenciaEncontrada = null;
                while (!stackTemp.empty() && referenciaEncontrada == null){
                    int scope = stackTemp.peek();
                    System.out.println("Iterando escopo " + lastScope);
                    referenciaEncontrada = ReferencePointer.procurarReferencia(searchReference,scope,false,references);
                    stackTemp.pop();
                }
                if(referenciaEncontrada == null){
                    throw new SemanticError("Variavél " + searchReference.getNome() + " não encontrada");
                }
                referenciaEncontrada.setUtilizada(true);
                stackType.push(referenciaEncontrada.getTipo().getVarCode());
                String AssemblyVariable = referenciaEncontrada.getNome() + "_" + referenciaEncontrada.getEscopo();

                AssemblyVariableINC =  AssemblyVariable;

                //Gerar assembly, mas somente se não for uma expressão de declaração de vetor
                if(!referenciaEncontrada.isVector()) {
                    // COMENTADO para validar se verificar é necessária --
                    if (!(isVectorReference && isDeclaration))
                        //Verifica a posição na expressão
                        if (expressionPosition.size() == 0) {
                            STRB_Assembly_INSTRUCTION.append("LD " + AssemblyVariable + "\n");
                            expressionPosition.push(1);
                        } else {
                            if (expressionPosition.peek() == 0)
                                STRB_Assembly_INSTRUCTION.append("LD " + AssemblyVariable + "\n");
                            else if (stackOperator.peek() == OperatorType.SUM.getCode())
                                STRB_Assembly_INSTRUCTION.append("ADD " + AssemblyVariable + "\n");
                            else if (stackOperator.peek() == OperatorType.SUB.getCode())
                                STRB_Assembly_INSTRUCTION.append("SUB " + AssemblyVariable + "\n");
                            else if (stackOperator.peek() == OperatorType.REL.getCode()){
                                STRB_Assembly_INSTRUCTION.append("STO temp1\n");
                                STRB_Assembly_INSTRUCTION.append("LD "+ AssemblyVariable + "\n");
                                STRB_Assembly_INSTRUCTION.append("STO temp2\n");
                                STRB_Assembly_INSTRUCTION.append("LD temp1\n");
                                STRB_Assembly_INSTRUCTION.append("SUB temp2\n");

                            }
                            else if (stackOperator.peek() == OperatorType.INC.getCode()){
                                STRB_Assembly_INSTRUCTION.append("STO " + AssemblyVariable + "\n");
                            }

                            int currentPosition = expressionPosition.pop();
                            expressionPosition.push(currentPosition+1);

                        }
                    System.out.println("Encontrou último valor de "+AssemblyVariable +", igual a "+String.valueOf(referenciaEncontrada.getLastValue()));
                    stackValue.push(referenciaEncontrada.getLastValue());
                }else{
                    //Se for primeiro operando
                    if(expressionPosition.size()==0){
                        STRB_Assembly_INSTRUCTION.append("STO $indr\n");
                        STRB_Assembly_INSTRUCTION.append("LDV " + AssemblyVariable+"\n");
                        expressionPosition.push(1);
                    }
                    else if(expressionPosition.peek() == 0){
                        STRB_Assembly_INSTRUCTION.append("STO $indr\n");
                        STRB_Assembly_INSTRUCTION.append("LDV " + AssemblyVariable+"\n");
                    } else {
                        if (isAtribToVector) {
                            STRB_Assembly_INSTRUCTION.append("STO $indr\n");
                            STRB_Assembly_INSTRUCTION.append("LDV " + AssemblyVariable + "\n");
                            STRB_Assembly_INSTRUCTION.append("STO temp3\n");
                            STRB_Assembly_INSTRUCTION.append("LD temp2\n");

                            if (stackOperator.peek() == OperatorType.SUM.getCode())
                                STRB_Assembly_INSTRUCTION.append("ADD temp3\n");
                            else if (stackOperator.peek() == OperatorType.SUB.getCode())
                                STRB_Assembly_INSTRUCTION.append("SUB temp2\n");

                        } else {
                            STRB_Assembly_INSTRUCTION.append("STO $indr\n");
                            STRB_Assembly_INSTRUCTION.append("LDV " + AssemblyVariable + "\n");
                            STRB_Assembly_INSTRUCTION.append("STO temp2\n");
                            STRB_Assembly_INSTRUCTION.append("LD temp1\n");

                            if (stackOperator.peek() == OperatorType.SUM.getCode())
                                STRB_Assembly_INSTRUCTION.append("ADD temp2\n");
                            else if (stackOperator.peek() == OperatorType.SUB.getCode())
                                STRB_Assembly_INSTRUCTION.append("SUB temp2\n");

                        }
                    }
                }
                isFirstInEXP = false;

                break;
            }

            case 22: {
                System.out.println("Verificando se retorno da expressão bate com o referência atruibuída");

                int resultadoExpType = stackType.pop();

                int resultadoAtribType = SemanticTable.atribType(currentRefAtribType.getVarCode(),resultadoExpType);
                //  System.out.println("Resultado EXP : " + resultadoExp + "\n CurrentRefAtribType: "+ currentRefAtribType.getVarCode() + "\n Resultado: " + resultadoAtrib);

                if(resultadoAtribType == ReturnType.ERR.getCode()){
                    throw new SemanticError ("Atribuição com a referência "+ currentReference.getNome() + " incorreta");
                }
                for(ReferencePointer reference : currentReferences){
                    System.out.println("Declaração da var "+reference.getNome() +" válida, inserindo valor");
                    STRB_Assembly_INSTRUCTION.append("STO "+reference.getNome()+ "_"+ reference.getEscopo() + "\n");
                    //Se for vetor na declaração, não colocar no lastValue
                    try {
                        reference.setLastValue((Integer) stackValue.peek());
                    } catch (EmptyStackException ex){
                        System.out.println("ERRO AO SALVAR ULTIMO VALOR NAS DECLARAÇÕES, PROVAVELMENTE HAJA UM VETOR NA ATRIBUIÇÃO");
                    }
                }
                break;
            }
            case 23:{
                stackOperator.push(OperatorType.LOG.getCode());
                break;
            }
            case 24:{
                stackOperator.push(OperatorType.BIT.getCode());
                break;
            }
            case 25:{
                stackOperator.push(OperatorType.REL.getCode());
                lastBooleanOperator = token.getLexeme();
                break;
            }
            case 26:{
                stackOperator.push(OperatorType.BIW.getCode());
                break;
            }
            case 27:{
                stackOperator.push(OperatorType.SUM.getCode());
                break;
            }
            case 28:{
                stackOperator.push(OperatorType.MUL.getCode());
                break;
            }
            case 29:{
                stackOperator.push(OperatorType.NEG.getCode());
                break;
            }
            case 30:{
                stackOperator.push(OperatorType.INC.getCode());
                STRB_Assembly_INSTRUCTION.append("ADDI 1\n");
                STRB_Assembly_INSTRUCTION.append("STO "+AssemblyVariableINC+"\n");
                break;
            }
            case 31:{
                stackType.push(ReferenceValueType.INT.getVarCode());
                stackValue.push(Integer.parseInt(token.getLexeme()));

                //Gerar assembly
                //Caso for uma expressão dentro de uma declaração de vetor, não gerar assembly

                if(!(isVectorReference && isDeclaration)) {
                    //Se for a primeira posição, dar load
                    if (expressionPosition.size() == 0) {
                        STRB_Assembly_INSTRUCTION.append("LDI " + token.getLexeme() + "\n");
                        expressionPosition.push(1);
                        }
                    //Se não, fazer as operações
                    else {
                        if(expressionPosition.peek()==0){
                            STRB_Assembly_INSTRUCTION.append("LDI " + token.getLexeme() + "\n");
                        }else if (stackOperator.peek() == OperatorType.SUM.getCode()) {
                            STRB_Assembly_INSTRUCTION.append("ADDI " + token.getLexeme() + "\n");
                        }
                        else if (stackOperator.peek() == OperatorType.SUB.getCode()) {
                            STRB_Assembly_INSTRUCTION.append("SUBI " + token.getLexeme() + "\n");
                        } else if(stackOperator.peek() == OperatorType.REL.getCode()){
                            STRB_Assembly_INSTRUCTION.append("STO temp1\n");
                            STRB_Assembly_INSTRUCTION.append("LDI "+ token.getLexeme() + "\n");
                            STRB_Assembly_INSTRUCTION.append("STO temp2\n");
                            STRB_Assembly_INSTRUCTION.append("LD temp1\n");
                            STRB_Assembly_INSTRUCTION.append("SUB temp2\n");
                        }
                        int currentEXPPos = expressionPosition.pop();
                        expressionPosition.push(currentEXPPos + 1);
                    }
                }

                break;
            }
            case 32:{
                stackType.push(ReferenceValueType.DOUBLE.getVarCode());
                stackValue.push(Double.parseDouble(token.getLexeme()));
                break;
            }
            case 33:{
                stackType.push(ReferenceValueType.BOOL.getVarCode());
                stackValue.push(Boolean.parseBoolean(token.getLexeme()));
                break;
            }
            case 34:{
                stackType.push(ReferenceValueType.STRING.getVarCode());
                stackValue.push(token.getLexeme());
                break;
            }
            case 35:{
                stackType.push(ReferenceValueType.CHAR.getVarCode());
                stackValue.push(token.getLexeme());
                break;
            }
            case 36:{
                //Lista que agrupa as declarações múltiplas, para depois atribuir os valores
                //Caso seja uma referência de vetor, ignorar as referências utilizadas dentro do colchetes
                if(isVectorReference) {
                    tempIdentifiers.add(vectorReference);
                    isVectorReference = false;
                }
                else
                    tempIdentifiers.add(currentReference);
                break;
            }
            case 37:{
                System.out.println("Salvando valores previamente calculados\n");
                vectorReference = currentReference;
                isVectorReference = true;
                if(expressionPosition.size()!=0){
                    if(expressionPosition.peek() != 0)
                        if(isResultEXP) {
                            if(isAtribToVector)
                                STRB_Assembly_INSTRUCTION.append("STO temp2\n");
                            else
                                STRB_Assembly_INSTRUCTION.append("STO temp1\n");
                        }

                }
                expressionPosition.push(0);
                break;
            }

            case 38:{
                stackOperator.push(OperatorType.SUB.getCode());
                break;
            }

            case 39:{
                stackOperator.push(OperatorType.DIV.getCode());
                break;
            }
            case 40:{
                stackOperator.push(OperatorType.MOD.getCode());
                break;
            }
            case 41,42,43,44,45,46,47,49: {
                System.out.println("Validando valores nas stacks");
                int tipo2 = stackType.pop();
                int tipo1 = stackType.pop();
                int op = stackOperator.pop();

                int resultadoEXP = SemanticTable.resultType(tipo1, tipo2, op);
                if (resultadoEXP == ReturnType.ERR.getCode()) {
                    throw new SemanticError("Retorno da expresão inválida");
                }
                stackType.push(resultadoEXP);
                break;

            }

            case 48:{
                System.out.println("Validando valores inteiros nas stacks");
                int tipo2 = stackType.pop();
                int tipo1 = stackType.pop();
                int op = stackOperator.pop();

                int resultadoEXP = SemanticTable.resultType(tipo1,tipo2,op);
                if(resultadoEXP == ReturnType.ERR.getCode()){
                    throw new SemanticError("Retorno da expresão inválida");
                }
                stackType.push(resultadoEXP);
                //Calculando resultado
                try {
                    int value2 = (Integer) stackValue.pop();
                    int value1 = (Integer) stackValue.pop();
                    if (op == OperatorType.SUM.getCode()) {
                        stackValue.push(value2 + value1);
                    } else if (op == OperatorType.SUB.getCode()) {
                        stackValue.push(value2 - value1);
                    }
                } catch (EmptyStackException ex){
                    System.out.println("\nERRO CALCULAR VALORES DA EXPRESSÃO, TABELA DE REFERÊNCIA SE DESATUALIZARÁ");
                }
                break;

            }

            case 50:{

                System.out.println("Validando valores nas stacks");
                int tipo1 = stackType.pop();
                int op = stackOperator.pop();

                int resultadoEXP = SemanticTable.resultType(tipo1,tipo1,op);
                if(resultadoEXP == ReturnType.ERR.getCode()){
                    throw new SemanticError("Retorno da expresão inválida");
                }
                System.out.println(resultadoEXP);
                stackType.push(resultadoEXP);
                break;

            }

            case 51:{

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
                System.out.println("Validando valores nas stacks");
                referenciaEncontrada.setUtilizada(true);
                int tipo1 = referenciaEncontrada.getTipo().getVarCode();
                int op = stackOperator.pop();

                int resultadoEXP = SemanticTable.resultType(tipo1,tipo1,op);
                if(resultadoEXP == ReturnType.ERR.getCode()){
                    throw new SemanticError("Retorno da expresão inválida");
                }
                System.out.println(resultadoEXP);
                stackType.push(resultadoEXP);
                break;

            }

            case 52:{
                isDeclaration = true;
                break;
            }

            case 53:{
                System.out.println("Procurando referência na tabela");
                Stack<Integer> stackTemp = (Stack<Integer>) stackScope.clone();
                //System.out.println("Iterando escopo " + stackScope.peek());
                ReferencePointer referenciaEncontrada = null;
                while (!stackTemp.empty() && referenciaEncontrada == null){
                    int scope = stackTemp.peek();
                    System.out.println("Iterando escopo " + lastScope);
                    referenciaEncontrada = ReferencePointer.procurarReferencia(currentReference,scope,true,references);
                    stackTemp.pop();
                }
                if(referenciaEncontrada == null){
                    throw new SemanticError("Variavél " + currentReference.getNome() + " não encontrada");
                }
                referenciaEncontrada.setUtilizada(true);
                stackType.push(referenciaEncontrada.getTipo().getVarCode());
                //ReferencePointer.PrintListaDeReferencia(references);
                break;
            }
            case 55:{
                STRB_Assembly_INSTRUCTION.append("STO temp1\n");
                expressionPosition.push(0);
                break;
            }
            case 56:{
                expressionPosition.pop();
                if(stackOperator.size() != 0) {
                    if(isAtribToVector){
                        STRB_Assembly_INSTRUCTION.append("STO temp3\n");
                        STRB_Assembly_INSTRUCTION.append("LD temp2\n");
                        int operation = stackOperator.peek();

                        OperatorType opEnum = OperatorType.values()[operation];

                        switch(opEnum) {
                            case SUM:{
                                STRB_Assembly_INSTRUCTION.append("ADD temp3\n");
                                break;
                            }
                            case SUB:{
                                STRB_Assembly_INSTRUCTION.append("SUB temp3\n");
                                break;
                            }
                        }

                    }else {
                        STRB_Assembly_INSTRUCTION.append("STO temp2\n");
                        STRB_Assembly_INSTRUCTION.append("LD temp1\n");
                        int operation = stackOperator.peek();

                        OperatorType opEnum = OperatorType.values()[operation];

                        switch(opEnum) {
                            case SUM:{
                                STRB_Assembly_INSTRUCTION.append("ADD temp2\n");
                                break;
                            }
                            case SUB:{
                                STRB_Assembly_INSTRUCTION.append("SUB temp2\n");
                                break;
                            }
                        }
                    }
                }
                break;
            }

            case 57:{
                TempVectorReference = currentReference;
                break;

            }

            case 58:{
                expressionPosition.pop();
                currentReference = TempVectorReference;
                break;
                /*if(expressionPosition.size()==0){
                    STRB_Assembly_INSTRUCTION.append("STO $indr\n");

                }*/
            }

            case 59:{
                if(!currentReference.isVector()) {
                    STRB_Assembly_INSTRUCTION.append("LD $in_port\n");
                    STRB_Assembly_INSTRUCTION.append("STO " + currentReference.getNome() + "\n");
                } else{
                    STRB_Assembly_INSTRUCTION.append("STO $indr\n");
                    STRB_Assembly_INSTRUCTION.append("LD $in_port\n");
                    STRB_Assembly_INSTRUCTION.append("STOV " + currentReference.getNome() + "\n");
                }

                break;
            }

            case 60:{
                if(!currentReference.isVector()) {
                    STRB_Assembly_INSTRUCTION.append("LD " + currentReference.getNome() + "\n");
                    STRB_Assembly_INSTRUCTION.append("STO $out_port\n");
                } else{
                    STRB_Assembly_INSTRUCTION.append("STO $indr\n");
                    STRB_Assembly_INSTRUCTION.append("LDV " + currentReference.getNome() + "\n");
                    STRB_Assembly_INSTRUCTION.append("STO $out_port\n");
                }

                break;
            }

            case 61:{
                String label = "L_IF_" + String.valueOf(lastScope);

                System.out.println("Gerando rótulo "+label);

                stackLabel.push(label);

                this.writeConditionalBranchAssembly(lastBooleanOperator,label);
                break;
            }

            case 62:{
                String label = stackLabel.pop();
                STRB_Assembly_INSTRUCTION.append(label+":\n");
                System.out.println("Escrevendo rótulo "+label);

                break;
            }

            case 63:{
                String labelIF = stackLabel.pop();
                String labelFIM = "L_ELSE_" + String.valueOf(lastScope);
                System.out.println("Gerando rótulo "+labelFIM);
                STRB_Assembly_INSTRUCTION.append("JMP " + labelFIM+"\n");
                System.out.println("Gerando jump do rótulo "+labelFIM);
                stackLabel.push(labelFIM);
                STRB_Assembly_INSTRUCTION.append(labelIF+":\n");
                break;
            }

            case 64:{

                String label = "L_WHILE_START_EXP_" + String.valueOf(lastScope);
                System.out.println("Gerando rótulo "+label);
                stackLabel.push(label);
                STRB_Assembly_INSTRUCTION.append(label+":\n");
                break;
            }

            case 65:{

                String label = "L_WHILE_END_SCOPE_" + String.valueOf(lastScope);
                System.out.println("Gerando rótulo "+label);
                stackLabel.push(label);
                writeConditionalBranchAssembly(lastBooleanOperator,label);
                break;
            }

            case 66:{

                String labelScope = stackLabel.pop();
                String labelEXP = stackLabel.pop();

                System.out.println("Gerando Jump do rótulo "+labelEXP);
                STRB_Assembly_INSTRUCTION.append("JMP "+ labelEXP +"\n");
                STRB_Assembly_INSTRUCTION.append(labelScope+":\n");

                break;
            }


            case 67:{

                String label = "L_DO_WHILE_START_EXP_" + String.valueOf(lastScope);
                System.out.println("Gerando rótulo "+label);
                stackLabel.push(label);
                STRB_Assembly_INSTRUCTION.append(label+":\n");
                break;
            }

            case 68:{
                String label = stackLabel.pop();

                writeConditionalBranchAssemblyBackwards(lastBooleanOperator,label);
                break;
            }
            case 69:{
                expressionPosition = new Stack<>();

                //Necessidade de diminuir o escopo não testada
                String label = "L_FOR_START_EXP_" + String.valueOf(lastScope);
                System.out.println("Gerando rótulo "+label);
                stackLabel.push(label);
                STRB_Assembly_INSTRUCTION.append(label+":\n");

                break;
            }
            case 70:{
                expressionPosition = new Stack<>();

                String label = "L_FOR_END_INSTRUCTION_" + String.valueOf(lastScope);
                System.out.println("Gerando rótulo "+label);
                stackLabel.push(label);
                writeConditionalBranchAssembly(lastBooleanOperator,label);

                assemblyInstructionLength = STRB_Assembly_INSTRUCTION.length();
                break;
            }

            case 71:{
                expressionPosition = new Stack<>();
                lastScope--;
                stackScope.pop();

                incrementAssemblyInstructions = STRB_Assembly_INSTRUCTION.substring(assemblyInstructionLength, STRB_Assembly_INSTRUCTION.length());
                STRB_Assembly_INSTRUCTION.delete(assemblyInstructionLength, STRB_Assembly_INSTRUCTION.length());

                break;
            }

            case 72:{
                String labelEND = stackLabel.pop();
                String labelEXP = stackLabel.pop();

                STRB_Assembly_INSTRUCTION.append(incrementAssemblyInstructions);
                incrementAssemblyInstructions = null;

                STRB_Assembly_INSTRUCTION.append("JMP "+labelEXP+"\n");
                STRB_Assembly_INSTRUCTION.append(labelEND+":\n");

                break;
            }

            case 73:{
                System.out.println("Incrementando escopo para FOR");

                lastScope++;
                stackScope.push(lastScope);

                break;
            }
            default:
                System.out.println("Acao #"+action+", Token: "+token);
                break;
        }
    }

    // Função que gera o assembly na arquitetura BIP
    public String generateAssembly() throws SemanticError {
        //Gerando .data

        //Gerando variaveis com valor inicial 0
        for(ReferencePointer RefPointer : references){
            // Se não for vetor nem função
            if(!RefPointer.isFunction() && !RefPointer.isVector())
                STRB_Assembly_DATA.append(" " + RefPointer.getNome() + "_" + RefPointer.getEscopo()+" : 0\n");
            //Se for vetor
            if(RefPointer.isVector()){
                String vectorSize = "";
                for(int i = 0; i < RefPointer.getVectorSize(); i++){
                    vectorSize = vectorSize + "0,";
                }
                try {
                    vectorSize = vectorSize.substring(0, vectorSize.length() - 1);
                } catch(StringIndexOutOfBoundsException ex){
                    throw new SemanticError("Impossível iniciar vetor pois tamanho está indefinido");
                }
                STRB_Assembly_DATA.append(" " + RefPointer.getNome()  + "_" + RefPointer.getEscopo() + " : " + vectorSize+"\n");
            }
        }

        // DEVIDO A UM BUG DO BIPIDE, É NECESSÁRIO NÃO TER RÓTULOS VAZIOS, LOGO INSERIREI UM HLT NO FINAL DO ASSEMBLY
        STRB_Assembly_INSTRUCTION.append("HLT");
        //Gerando vetores
        return STRB_Assembly_DATA.toString() + STRB_Assembly_INSTRUCTION.toString();
    }

    private void writeConditionalBranchAssembly(String lastBooleanOperator, String label) throws SemanticError{
        System.out.println("Gerando breach do rótulo "+label);
        switch (lastBooleanOperator){
            case "<":{
                STRB_Assembly_INSTRUCTION.append("BGE "+label+"\n");
                break;
            }
            case ">":{
                STRB_Assembly_INSTRUCTION.append("BLE "+label+"\n");
                break;
            }
            case "==":{
                STRB_Assembly_INSTRUCTION.append("BNE "+label+"\n");
                break;
            }
            case "!=":{
                STRB_Assembly_INSTRUCTION.append("BEQ "+label+"\n");
                break;
            }
            case "<=":{
                STRB_Assembly_INSTRUCTION.append("BGT "+label+"\n");
                break;
            }
            case ">=":{
                STRB_Assembly_INSTRUCTION.append("BLT "+label+"\n");
                break;
            }
            default:{
                throw new SemanticError("OPERADOR RELACIONAL NÃO IDENTIFICADO");
            }
        }
    }

    private void writeConditionalBranchAssemblyBackwards(String lastBooleanOperator, String label) throws SemanticError{
        switch (lastBooleanOperator){
            case "<":{
                STRB_Assembly_INSTRUCTION.append("BLT "+label+"\n");
                break;
            }
            case ">":{
                STRB_Assembly_INSTRUCTION.append("BGT "+label+"\n");
                break;
            }
            case "==":{
                STRB_Assembly_INSTRUCTION.append("BEQ "+label+"\n");
                break;
            }
            case "!=":{
                STRB_Assembly_INSTRUCTION.append("BNQ "+label+"\n");
                break;
            }
            case "<=":{
                STRB_Assembly_INSTRUCTION.append("BLE "+label+"\n");
                break;
            }
            case ">=":{
                STRB_Assembly_INSTRUCTION.append("BGE "+label+"\n");
                break;
            }
            default:{
                throw new SemanticError("OPERADOR RELACIONAL NÃO IDENTIFICADO");
            }
        }
    }
}
