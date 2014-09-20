package Configuracion;

public class General
{
    public static int PC                   = 0x00400000;
    public static int rs                   = 0x00000000;
    public static int rd                   = 0x00000000;
    public static int rt                   = 0x00000000;
    public static int actInstruction       = 0x00000000;
    public static byte flagZ               = 0x00;
    public static byte flagP               = 0x00;
    // Los tamaños son reducidos para evitar limitaciones de tamaño
    public final static int dataBaseAddr   = 0x00010000;
    public final static int dataLastAddr   = 0x0001FFFF;
    public final static int instBaseAddr   = 0x00400000;
    public final static int instLastAddr   = 0x004FFFFF;
    // De la 0x80000000 hasta 0xFFFFFFFF reservado para el operativo (2GB)
    //public final static int kernelBaseAddr = 0x80000000;
    //public final static int kernelLastAddr = 0xFFFFFFFF;
}
