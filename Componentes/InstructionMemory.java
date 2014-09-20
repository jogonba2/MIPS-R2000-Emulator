package Componentes;
import Configuracion.General;
import Configuracion.ControlSignals;
// Se intentará evitar pasar por los wrappers de java y usar clases que impliquen su uso //
// Un array de bytes con los opcodes del código a ejecutar, se posicionará un índice de la siguiente forma:
// El array de opcodes tendrá de longitud: dirección final - dirección inicial
// for(int i=OpCodes.instBaseAddr;i>=0;i--){ opCode = v[OpCodes.instBaseAddr-i]; }

// La unidad de control sacará a su salida la instrucción indicada por el PC si Linst está a 1 //
public class InstructionMemory
{
    private int[] memInstructionAddrs;
    private final int staticLength = General.instLastAddr - General.instBaseAddr;
    
    public InstructionMemory(){
        // Lanzar excepción si se supera el limite de staticLength (añadir este límite también) //
        this.memInstructionAddrs = new int[this.staticLength];
    }
    
    public void addInstruction(int inst){ this.memInstructionAddrs[(General.PC - General.instBaseAddr)] = inst;}
    
    public int getInstruction() throws Exception{ 
        if(ControlSignals.Linst==1) return this.memInstructionAddrs[(General.PC - General.instBaseAddr)];
        return -1;
    }
    
    public void resetMemory(){ for(int i=0,act=this.memInstructionAddrs[i];i<staticLength && act!=0;this.memInstructionAddrs[i++]=0,act=this.memInstructionAddrs[i]); }
    
    public void loadProgram(int[] program){ 
        int initialPC = General.PC;
        for(int e : program){ this.addInstruction(e); General.PC += 4;}
        General.PC = initialPC;
    }
    
    /**public static void main(String args[]){
    /*  try{
        InstructionMemory im = new InstructionMemory();
        ControlSignals.Linst = 1;
        im.addInstruction(0x13371337);
        General.PC += 4;
        im.addInstruction(0x13371338);
        General.PC += 4;
        General.PC = General.instBaseAddr;
        System.out.println(im.getInstruction());
        General.PC += 4;
        System.out.println(im.getInstruction());
    }catch(Exception e){}
    }**/
   
        
}