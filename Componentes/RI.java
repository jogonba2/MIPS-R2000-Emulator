package Componentes;

/** El RI se encarga de a partir de la instrucción separar rs,rt los 16 bits de inmediato, es la primera parte del proceso,
 *  posteriormente se ejecutará la unidad de control para asignar las señales de control y luego seguir el proceso
 */
public class RI
{
    // A partir de la instrucción, se devolverá un array con 6 valores [instruccion para la unidad de control,rs,rt,inm,tipoI,tipoR]
    // Los valores son de diferente tamaño <= sizeof(int) por tanto se unifican todos en un array de int para evitar tener que depender de genericidad y otras soluciones //
    public final static int[] getOutput(int inst){
        int[] output = new int[6];
        output[0] = inst;                                      // Instruction //
        output[1] =  (byte)((inst & 0x03E00000) >> 21);        // Rs //
        output[2] =  (byte)((inst & 0x001F0000) >> 16);        // Rt //
        output[3] = (short)((inst & 0x0000FFFF));              // inm //
        output[4] =  (byte)((inst & 0x001F0000) >> 16);        // tipoI -> Rt //
        output[5] =  (byte)((inst & 0x0000F800) >> 11);        // tipoR -> Rd // 
        return output;
    }
    
    
    /** public static void main(String args[]){
        // Instruction: add $5,$5,$3 //
        for(int e : RI.getOutput(0x00A32808)) System.out.println(e);
    }**/
}
