import eduni.simjava.*;
import eduni.simjava.distributions.Sim_uniform_obj;

public class Cache extends Sim_entity {
    private Sim_port in, out;
    private Sim_uniform_obj delay;
    private Sim_stat stat;

    public Cache(String name, int min, int max){
        super(name);
        in = new Sim_port("In");
        out = new Sim_port("Out");
        add_port(in);
        add_port(out);

        delay = new Sim_uniform_obj("delay", min, max);
        add_generator(delay);

        stat = new Sim_stat();
        stat.add_measure(Sim_stat.UTILISATION);
        stat.add_measure(Sim_stat.SERVICE_TIME);
        stat.add_measure(Sim_stat.WAITING_TIME);
        stat.add_measure(Sim_stat.QUEUE_LENGTH);
        set_stat(stat);
    }

    @Override
    public void body(){
        while (Sim_system.running()){
            Sim_event e =  new Sim_event();
            sim_get_next(e);
            sim_process(delay.sample());
            sim_completed(e);
            sim_schedule(out, 0, 4);
        }
    }
}
