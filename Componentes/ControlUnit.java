package Componentes;
import Configuracion.ControlSignals;
import Configuracion.ControlSignalPerInstructionControl;
/** La unidad de control se encarga de parsear los opcodes y setear correctamente los flags de las señales
 *  de control en Configuracion.ControlSignals para usar luego en la simulación. 
 *  Debe, partiendo de los 4B del opcode (solo se tratarán instrucciones R y I) identificar el tipo de instrucción,
 *  coger los 6 bits de función y extraer las señales de control correspondientes, luego setearlas en ControlSignals
 */
public class ControlUnit
{
    public final static void setControlSignalsByInst(int instruction) throws Exception{
        // Extraer OpCode de la instrucción (6 MSB) //
        byte opCode  = (byte)Math.abs(((instruction & 0xFF000000)>>24));
        // Con el OpCode ver si es 0 es una operación aritmético-lógica (R) si no es de tipo I (memoria y condicional)
        // Extraer el opfCode correspondiente (6 LSB) si el opcode es 0 (R) //
        if(opCode==0){ 
            opCode = (byte)Math.abs((((instruction & 0x0000000F))));
            opCode &= 0x0F;
            opCode = (byte)Math.abs(opCode);
            // Usar los 4 LSB para identificar la función (problemas java para sacar 
        }
        else{
           // Como los 4LSB del byte CO del opcode son dependientes del registro rs, multiplexar partiendo de los 4MSB //
           opCode = (byte)Math.abs((((instruction & 0xF0000000)>>24)));
           opCode &= 0xF0;
           if(opCode==0x60) opCode = 0x2B; // SW
           if(opCode==-128) opCode = 0x23; // LW
           if(opCode==16) opCode = 0x06; // BEQ
        }
        // Con el opCode setteado obtener el array de señales de control //
        byte[] cSignals = ControlSignalPerInstructionControl.controlSignalsByFunctionCode(opCode);
        // Una vez se tienen las señales de control settear ControlSignals //
        ControlSignals.PCin = cSignals[0]; ControlSignals.Linst = cSignals[1]; ControlSignals.LReg   = cSignals[2];
        ControlSignals.EReg   = cSignals[3]; ControlSignals.OpALU  = cSignals[4]; ControlSignals.LMem   = cSignals[5];
        ControlSignals.EMem   = cSignals[6]; ControlSignals.MxPC   = cSignals[7]; ControlSignals.MxALU  = cSignals[8];
        ControlSignals.MxDst  = cSignals[9]; ControlSignals.MxER   = cSignals[10];      
    }
      
    /**public static void main(String args[]){
        ControlUnit.setControlSignalsByInst(0x00A32808); // Tipo R add $5,$5,$3
        System.out.println(ControlSignals.OpALU);
        //ControlUnit.setControlSignalsByInst(0x8C620004);
    }**/
      
}
