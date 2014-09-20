package Configuracion;
import java.util.HashMap;
import java.util.Map;
import Configuracion.OpCodes;
public class ControlSignalPerInstructionControl
{
   // Array[0][j] = add (function opcode(byte) 00100000)
   // Array[0][j] = sub (function opcode(byte) 00100010)
   // Array[0][j] = and (function opcode(byte) 00100100)
   // Array[0][j] = or  (function opcode(byte) 00100101)
   // Array[0][j] = lw  (function opcode(byte) 00100011)
   // Array[0][j] = sw  (function opcode(byte) 00101011)
   // Array[0][j] = beq (function opcode(byte) 00000100)
   // Array[x][0] = PCin
   // Array[x][1] = LInst
   // Array[x][2] = LReg
   // Array[x][3] = EReg
   // Array[x][4] = opALU
   // Array[x][5] = LMem
   // Array[x][6] = EMem
   // Array[x][7] = MxPC
   // Array[x][8] = MxALU
   // Array[x][9] = MxDst
   // Array[x][10] = MxER

   private static final byte[][] controlTable  = {{0x01,0x01,0x01,0x01,0x02,0x00,0x00,0x00,0x00,0x01,0x00},
                                                  {0x01,0x01,0x01,0x01,0x06,0x00,0x00,0x00,0x00,0x01,0x00},
                                                  {0x01,0x01,0x01,0x01,0x00,0x00,0x00,0x00,0x00,0x01,0x00},
                                                  {0x01,0x01,0x01,0x01,0x01,0x00,0x00,0x00,0x00,0x01,0x00},
                                                  {0x01,0x01,0x01,0x01,0x02,0x01,0x00,0x00,0x01,0x00,0x01},
                                                  {0x01,0x01,0x01,0x00,0x02,0x00,0x01,0x00,0x01,0x00,0x00},
                                                  {0x01,0x01,0x01,0x00,0x06,0x00,0x00,0x00,0x00,0x00,0x00}};
                                                  
    
   public static final byte[] controlSignalsByFunctionCode(byte opFunction) throws Exception{
       System.out.println("\n\n---------------- -----------------");
       if(opFunction==OpCodes.opfAdd){System.out.println("\n[Instruction] ADD"); return ControlSignalPerInstructionControl.controlTable[0]; }     // add
       else if(opFunction==OpCodes.opfSub){ System.out.println("\n[Instruction] SUB"); return ControlSignalPerInstructionControl.controlTable[1]; }// sub
       else if(opFunction==OpCodes.opfAnd){ System.out.println("\n[Instruction] AND"); return ControlSignalPerInstructionControl.controlTable[2]; }// and
       else if(opFunction==OpCodes.opfOr){ System.out.println("\n[Instruction] OR"); return ControlSignalPerInstructionControl.controlTable[3]; }// or
       else if(opFunction==OpCodes.opfLw){ System.out.println("\n[Instruction] LW"); return ControlSignalPerInstructionControl.controlTable[4]; }// lw
       else if(opFunction==OpCodes.opfSw){ System.out.println("\n[Instruction] SW"); return ControlSignalPerInstructionControl.controlTable[5]; }// sw
       else if(opFunction==OpCodes.opfBeq){ System.out.println("\n[Instruction] BEQ"); return ControlSignalPerInstructionControl.controlTable[6];} // beq
       return null;
    }
    
    /**public static void main(String args[]){
       System.out.println(ControlSignalPerInstructionControl.controlSignalsByFunctionCode((byte)0x20)[4]);
    }**/
}
