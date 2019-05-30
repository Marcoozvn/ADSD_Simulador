import eduni.simjava.*;
import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_port;
import eduni.simjava.distributions.Sim_uniform_obj;

public class Source extends Sim_entity {
    private Sim_port out;
    private int numChegadas;
    private Sim_uniform_obj delay;
    private Sim_stat stat;

    public Source (String name, int numChegadas, int min, int max) {
        super(name);
        out = new Sim_port("Out");
        add_port(out);

        delay = new Sim_uniform_obj("delay", min, max);
        add_generator(delay);

        this.numChegadas = numChegadas;

        stat = new Sim_stat();
        stat.add_measure(Sim_stat.UTILISATION);
        stat.add_measure(Sim_stat.SERVICE_TIME);
        stat.add_measure(Sim_stat.WAITING_TIME);
        stat.add_measure(Sim_stat.QUEUE_LENGTH);
        set_stat(stat);
    }

    @Override
    public void body(){
        for(int i = 0; i <= this.numChegadas; i++) {
            sim_schedule(out, 0.0, 0);

            sim_pause(delay.sample());
        }
    }
}
