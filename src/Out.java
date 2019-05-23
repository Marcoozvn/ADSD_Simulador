import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_system;

public class Out extends Sim_entity {
    private Sim_port in;

    public Out(String name) {
        super(name);
        in = new Sim_port("In");
        add_port(in);
    }

    @Override
    public void body() {
        while(Sim_system.running()){
            Sim_event e =  new Sim_event();
            sim_get_next(e);
            sim_completed(e);
        }
    }
}
