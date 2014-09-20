package Componentes;


public class SignExtender
{
    public static final int signExtend(short valueToExtend){
        int res = 0x00000000;
        // Si el MSB es un 1, se extiende el 1 en los 16 bits restantes del int //
        if((valueToExtend&0x8000)==0x8000){ res = 0xFFFF0000 | valueToExtend; }
        else res |= valueToExtend;
        return res;
    }
    
    /**public static void main(String args[]){
        System.out.println((short)0xFFF1);
        System.out.println(SignExtender.signExtend((short)0x3FF1));
    }**/
}
