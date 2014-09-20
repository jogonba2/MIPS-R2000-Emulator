package Componentes;
import Configuracion.ControlSignals;
public class Registers
{
   // 32 registros de 4B 
   private int[] output; // se devuelven las 2 salidas en un array, la 1º salida es rs, la 2º es rt
   private int[] registers;
   
   public Registers(){
       this.registers = new int[32]; // 32 registros //
       this.output    = new int[2];
   }
   
   public int[] getOutput(int rd,int rs,int rt,int data) throws Exception{
       // Si la señal de control LReg está a 1, la 1º salida será el valor del registro v[rs] y la 2º salida será el valor del registro v[rt] //
       if(ControlSignals.LReg==1){ if(rs<32 && rt<32) {this.output[0] = registers[rs]; this.output[1] = registers[rt]; } else throw new Exception("Invalid Register"); }
       // Si no, si la señar EReg está a 1, las salidas serán 0 y se guardará en el registro rd el valor data //
       else if(ControlSignals.EReg==1){ if(rd<32){this.registers[rd] = data; } else throw new Exception("Invalid Register");}
       return this.output;
   }
   
   // DEBUGGING //
   public void setValue(int d,int i){ this.registers[i] = d;    }
   public int  getValue(int i)      { return this.registers[i]; }
   
   /**public static void main(String args[]){
       try{
           Registers regs = new Registers();
           ControlSignals.EReg = 1;
           for(int e : regs.getOutput(14,0,0,1337)) System.out.println(e);
           ControlSignals.EReg = 0;
           ControlSignals.LReg = 1;
           for(int e : regs.getOutput(0,14,15,9999)) System.out.println(e);
       }catch(Exception e){ e.printStackTrace(); }
    }**/
}
