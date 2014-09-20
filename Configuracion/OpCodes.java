package Configuracion;

// Será útil si se quiere dar soporte a traducción de órdenes (string) -> (op code) //
public class OpCodes
{
    // CPU Opcodes //
    public final static byte opfAdd = 0x08;
    public final static byte opfSub = 0x02;
    public final static byte opfAnd = 0x04;
    public final static byte opfOr  = 0x05;
    public final static byte opfLw  = 0x23;
    public final static byte opfSw  = 0x2B;
    public final static byte opfBeq = 0x06;
    
    // ALU Opcodes //
    public final static byte opaAnd = 0x00;
    public final static byte opaOr  = 0x01;
    public final static byte opaAdd = 0x02;
    public final static byte opaSub = 0x06;
    
}
