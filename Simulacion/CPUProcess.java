package Simulacion;
import Configuracion.*;
import Componentes.*;

public class CPUProcess
{
    // Algunas clases son estáticas y no necesitan instanciación pero se quiere expresar que la CPU tiene los
    // siguientes elementos (relación tiene un)
    private CyclicMux2to1       cyclicMx;
    private ALU                 alu;
    private DataMemory          dataMemory;
    private InstructionMemory   instMemory;
    private Registers           registers;
    private ControlUnit         controlUnit;
    private SignExtender        signExtender;
    private RI                  instParser;
    
    public CPUProcess(){
        this.dataMemory        = new DataMemory();
        this.instMemory        = new InstructionMemory();
        this.registers         = new Registers();
        this.controlUnit       = new ControlUnit(); 
    }
    
    public final void __run__(){     
        /** Test Work **/
        // Setear los registros (no hay instrucción de asignación en registro con esta ruta de datos, se deben precargar)
        this.registers.setValue(13,5); // Valor,Registro //
        this.registers.setValue(13,3);
        this.registers.setValue(0x00010000,12); // Registro 12 addr en memoria para guardar el resultado //
        this.registers.setValue(0x0000FFFC,30); // Registro 13 addr en memoria para probar avance de punteros en lectura de memoria //
        this.registers.setValue(31,15);         // Guardar el resultado final conocido para comparar con el correcto (comparación del código de ejemplo) //
        //[0x00400000] add $5,$5,$3  ( 13 + 13 = 26 ) en $5 -> 0x00A32808
        //[0x00400004] sub $4,$5,$3  ( 26 - 13 = 13 ) en $4 -> 0x00A32022
        //[0x00400008] or $6,$4,$5   ( 13 | 26 = 31 ) en $6 -> 0x00853025
        //[0x0040000C] sw $6,0($12)  ( Guardar en memoria $6 ) -> 0xAD860000
        //[0x00400010] lw $7,0($12)  ( Cargar el valor guardado en $7 ) -> 0x8D870000
        //[0x00400014] lw $16,4($30) ( Cargar el valor guardado con desplazamiento desde posición inferior en un registro alto ) -> 0x8FD00004
        //[0x00400018] beq $16,$15,28 ( Si $15 == $16 saltar a 0x00400000 (PC + desp) (como siempre lo será, bucle infinito ) -> 0x120F0006
        // Program: {0x00A32808,0x00A32022,0x00853025,0xAD860000,0x8D870000,0x8FD00004,0x120F0006}
        // El desplazamiento (desp) se expresa en nº de lineas (de instrucciones)
        //and &6,&6,$zero ( poner a 0 $6 )     -> 0x00C03024 ($zero puede ser cualquier registro puesto a 0, no es como en mips reg[0] reservado, aquí el programador es libre)
        
        int program[] = {0x00A32808,0x00A32022,0x00853025,0xAD860000,0x8D870000,0x8FD00004,0x120F0006}; 
        // Cargar programa en memoria de instrucciones //
        this.instMemory.loadProgram(program);
        // Variables //
        int actInst = 0x00;
        int[] instParsed = new int[11];
        int rs,rt,rd = 0x00;
        int drs,drt  = 0x00; // Datos contenidos en registros rs y rt
        int k = 0x00;
        byte antEReg = 0x00;
        int data = 0x00;
        int[] registerOutput = new int[2];
        int aluOutput = 0x00;
        int memOut    = 0x00;
        int NPC       = 0x00;
        // Señales de control constantes //
        ControlSignals.Linst = 1;
        // Bucle principal //
        while(true){     
            /** Etapa LI (Lectura de instrucción **/
            // Leer la instrucción de la dirección de memoria PC // 
            try{
                actInst = this.instMemory.getInstruction();
            }catch(Exception e){ break; }
            // Avanzar PC dependiendo si viene de un condicional o si avanza normalmente (+4) //
           
            /** Etapa DI (Decodificación de la instrucción **/
            // Satear las señales de control de la correspondiente instrucción en ControlSignals//
            try{
                this.controlUnit.setControlSignalsByInst(actInst);
            }catch(Exception e){ break; }
            // Decodificar la instrucción //
            instParsed = this.instParser.getOutput(actInst);
            // Obtener rd del MxDst //
            rd = this.cyclicMx.getOutput(instParsed[4],instParsed[5],ControlSignals.MxDst);
            // Leer Rs y Rt del banco de registros (en operaciones load rt no hace falta leer pero se hace por acortar //
            // Para esto poner el bit LReg a 1 y el EReg a 0, guardar anterior valor de EReg por si luego hace falta escribir //
            rs = instParsed[1];
            rt = instParsed[2];
            // [instruccion para la unidad de control,rs,rt,inm,tipoI,tipoR] //
            antEReg = ControlSignals.EReg; ControlSignals.EReg = 0; 
            System.out.println("[Decode]");
            System.out.println("\tRT: " + rt + " RS: " + rs + " RD: " + rd);
            
            /** Añadir más excepciones sobre otros componentes que pueden ser mal usados **/
            try{
                registerOutput = this.registers.getOutput(rd,rs,rt,data);
            }catch(Exception e){ break; }
            // Se obtiene el valor extendido del extensor de signo //
            k = this.signExtender.signExtend((short)instParsed[3]);
            System.out.println("\tRS Value: " + registerOutput[0]);
            System.out.println("\tRt Value: " + registerOutput[1]);
            
            /** Etapa EX (Ejecución de la instrucción) **/
            // Multiplexar el valor procedente del extensor de signo y de rt con la señal MxALU y utilizar el valor como entrada 1 de la ALU //
            aluOutput = this.alu.getOutput(registerOutput[0],this.cyclicMx.getOutput(registerOutput[1],k,ControlSignals.MxALU));
            System.out.println("[Execution] Result: " + aluOutput);
            
            /** Etapa M (Interacción con memoria) **/
            /** Data = register[rt] **/
            /** Addr = resultado de la ALU **/
            memOut = this.dataMemory.getOutput(registerOutput[1],aluOutput);
            // Con el valor de MemOut y ALUOut se multiplexa y se cogen los datos a guardar en el registro //
            data = this.cyclicMx.getOutput(aluOutput,memOut,ControlSignals.MxER);
            
            /** Etapa ER (Escritura en registro) **/
            ControlSignals.EReg = antEReg; // Restaurar el registro de escritura
            // Si el flag EReg està activado, poner LReg a 0 y guardar en registro //
            if(ControlSignals.EReg==1){
                ControlSignals.LReg = 0;
                try{
                    this.registers.getOutput(rd,rs,rt,data);
                }catch(Exception e){ e.printStackTrace(); }
                ControlSignals.LReg = 1;
            }          
            // Para avanzar el pc, sumar PC+4 + el desplazamiento extendido desplazado 2 veces a izquierda (*(2^2)), se multiplexa ese valor con el (PC+4) avance normal //
            // En teoría lo de arriba funciona, pero paso de tocar más, se deja el salto hacia atrás y ya //
            NPC = General.PC - (k<<2);
            General.PC = this.cyclicMx.getOutput(General.PC+4,NPC,General.flagZ);
            
            System.out.println("[New PC] 0x00" + Integer.toHexString(General.PC));
        }
        
    }
    
    public static void main(String args[]){
        CPUProcess cpu = new CPUProcess();
        cpu.__run__();
    }
}
