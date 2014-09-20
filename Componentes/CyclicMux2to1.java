package Componentes;


public class CyclicMux2to1
{
    // Get mux execution output
    // Como se permite el reuso de un objeto multiplexor y cada uno tiene una señal de control diferente, hay que
    // pasarle al método su correspondiente señal.
    public static final int getOutput(int ent0,int ent1,byte cSignal){
        int output = cSignal==0 ? ent0 : ent1;
        return output;
    }
    
    /**public static void main(String args[]){
        System.out.println(CyclicMux2to1.getOutput(2,3,(byte)0));
    }**/
}