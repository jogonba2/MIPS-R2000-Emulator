package Componentes;
import Configuracion.OpCodes;
import Configuracion.ControlSignals;
import Configuracion.General;
public class ALU
{
    
    // Get alu execution output
    public final static int getOutput(int ent0,int ent1){ 
        int res = 0x00;
        
        if(ControlSignals.OpALU==OpCodes.opaAnd) res = ent0 & ent1;
        if(ControlSignals.OpALU==OpCodes.opaOr)  res = ent0 | ent1;
        if(ControlSignals.OpALU==OpCodes.opaAdd) res = ent0 + ent1;        
        if(ControlSignals.OpALU==OpCodes.opaSub) res = ent0 - ent1;
        if(res==0) General.flagZ = 1;
        else General.flagZ = 0;
        if(res%2==0) General.flagP = 1;
        else General.flagP = 0;
        return res;
    }
        
    
    /**public static void main(String args[]){
        ControlSignals.OpALU = 0x02;
        System.out.println(ALU.getOutput(2,2));
    }**/
}
