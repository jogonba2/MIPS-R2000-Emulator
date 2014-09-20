package Componentes;
import Configuracion.General;
import Configuracion.ControlSignals;
// Se intentará evitar pasar por los wrappers de java y usar clases que impliquen su uso //
// Un array de bytes con los opcodes del código a ejecutar, se posicionará un índice de la siguiente forma:
// El array de opcodes tendrá de longitud: dirección final - dirección inicial
// for(int i=OpCodes.instBaseAddr;i>=0;i--){ opCode = v[OpCodes.instBaseAddr-i]; }

// La memoria de datos 
public class DataMemory
{
    private int[] memDataAddrs;
    public int pActData;
    private final int staticLength = General.dataLastAddr - General.dataBaseAddr;
    
    public DataMemory(){
        this.memDataAddrs = new int[this.staticLength];
        this.pActData     = 0;
    }
    
    public int getOutput(int data,int addr){
        // Si la señal de control LMem está a 1, coger el valor de la dirección addr y retornarla //
        if(ControlSignals.LMem==1){ System.out.println("Readed from memory : " + this.getWord(addr)); return this.getWord(addr); }
        // Si no, si EMem está a 1, guardar los datos en la dirección siguiente
        else if(ControlSignals.EMem==1){ this.addWord(data); System.out.println("Writted in memory : " + memDataAddrs[this.pActData-4]); }
        return 0x00;
    }
    
    public void addWord(int data){ this.memDataAddrs[this.pActData] = data; this.pActData += 4; }
 
    public void resetMemory(){ 
        for(int i=0,act=this.memDataAddrs[i];i<this.staticLength && act!=0;this.memDataAddrs[i++]=0,act=this.memDataAddrs[i]);
        this.pActData    = 0;
    }
    
    public int getWord(int addr){ return this.memDataAddrs[addr-General.dataBaseAddr]; }
    public int getHalf(int addr){ return (short)(this.getWord(addr) & 0x00001111); }
    public int getByte(int addr){ return (byte)(this.getWord(addr) & 0x00000011); }
    
    /**public static void main(String args[]){ 
        DataMemory im = new DataMemory();
        // Control signal de escritura a 1 //
        ControlSignals.LMem = 0;
        ControlSignals.EMem = 1;
        System.out.println(im.getOutput(1337,0x00010000));
        System.out.println(im.getOutput(1338,0x00010004));
        // Control signal de lectura a 1 //
        ControlSignals.LMem = 1;
        ControlSignals.EMem = 0;
        System.out.println(im.getOutput(999,0x00010000));
        System.out.println(im.getOutput(999,0x00010004));
    }**/
    
        
}