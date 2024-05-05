package Gals.SemanticUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Stack;

public class ReferencePointer {
    String nome;
    ReferenceValueType tipo;
    boolean iniciada;
    boolean utilizada;
    int escopo;

    boolean isParameter;
    int posicaoParameto;
    boolean isVector;
    boolean isReference;
    boolean isFunction;

    public ReferencePointer(String nome, ReferenceValueType tipo, boolean iniciada, boolean utilizada, int escopo, boolean isParameter, int posicaoParameto, boolean isVector, boolean isReference, boolean isFunction) {
        this.nome = nome;
        this.tipo = tipo;
        this.iniciada = iniciada;
        this.utilizada = utilizada;
        this.escopo = escopo;
        this.isParameter = isParameter;
        this.posicaoParameto = posicaoParameto;
        this.isVector = isVector;
        this.isReference = isReference;
        this.isFunction = isFunction;
    }

    public void setIniciada(boolean iniciada) {
        this.iniciada = iniciada;
    }

    public void setUtilizada(boolean utilizada) {
        this.utilizada = utilizada;
    }

    public String getNome() {
        return nome;
    }

    public ReferenceValueType getTipo() {
        return tipo;
    }

    public boolean isIniciada() {
        return iniciada;
    }

    public boolean isUtilizada() {
        return utilizada;
    }

    public int getEscopo() {
        return escopo;
    }

    public boolean isParameter() {
        return isParameter;
    }

    public int getPosicaoParameto() {
        return posicaoParameto;
    }

    public boolean isVector() {
        return isVector;
    }

    public boolean isReference() {
        return isReference;
    }

    public boolean isFunction() {
        return isFunction;
    }

    @Override
    public String toString() {
        return  "Nome : " +this.nome + "\n" +
                "Tipo: "+ this.tipo.toString()+ "\n" +
                "Iniciada: "+ this.iniciada + "\n" +
                "Utilizada: "+ this.utilizada + "\n" +
                "Escopo: "+ this.escopo+"\n" +
                "É Parâmetro?: "+this.isParameter+"\n" +
                "Posição do parametro: " +this.posicaoParameto+"\n" +
                "É vetor?: "+this.isVector+"\n" +
                "É Referência?: "+this.isReference+"\n" +
                "É Função?: "+this.isFunction;
    }

    public static void PrintListaDeReferencia(ArrayList<ReferencePointer> lista){
        int count = 0;
        for(ReferencePointer referencia : lista){
            System.out.println("Item "+ count + "\n\n");
            System.out.println(referencia.toString());
            count++;
        }
    }

    public static boolean existeVariavelIgual(ArrayList<ReferencePointer> lista, ReferencePointer referenciaAIncluir){

        for(ReferencePointer referencia : lista){
            if(referencia.equals(referenciaAIncluir)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReferencePointer that = (ReferencePointer) o;
        return escopo == that.escopo && isVector == that.isVector && isFunction == that.isFunction && Objects.equals(nome, that.nome) && tipo == that.tipo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, tipo, escopo, isVector, isFunction);
    }
}
