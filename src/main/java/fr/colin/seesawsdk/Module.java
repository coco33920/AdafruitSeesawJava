package fr.colin.seesawsdk;

public abstract class Module {

    private int register;
    private Seesaw seesaw;
    public Module(int register, Seesaw seesaw) {
        this.register = register;
        this.seesaw = seesaw;
    }

    public int getRegister() {
        return register;
    }

    public Seesaw getSeesaw() {
        return seesaw;
    }

    public void write(int function, byte data){
        //TODO general low level write function
    }

    public void read(int function, byte data){
        //TODO general low level read function
    }


}
