import eduni.simjava.*;
import eduni.simjava.distributions.Sim_random_obj;
import eduni.simjava.distributions.Sim_uniform_obj;

public class CpuDatabaseServer extends Sim_entity {
    private Sim_port in1, in2, out1, out2;
    private Sim_uniform_obj delay;
    private Sim_stat stat;
    private Sim_random_obj prob;

    public CpuDatabaseServer(String name, int min, int max) {
        super(name);
        in1 = new Sim_port("In1");
        in2 = new Sim_port("In2");
        out1 = new Sim_port("Out1");
        out2 = new Sim_port("Out2");
        add_port(in1);
        add_port(in2);
        add_port(out1);
        add_port(out2);

        delay = new Sim_uniform_obj("delay", min, max);
        add_generator(delay);

        prob = new Sim_random_obj("Probability");
        add_generator(prob);

        stat = new Sim_stat();
        stat.add_measure(Sim_stat.UTILISATION);
        stat.add_measure(Sim_stat.SERVICE_TIME);
        stat.add_measure(Sim_stat.WAITING_TIME);
        stat.add_measure(Sim_stat.QUEUE_LENGTH);
        set_stat(stat);
    }

    @Override
    public void body(){
        while(Sim_system.running()){
            Sim_event e =  new Sim_event();
            sim_get_next(e);
            sim_process(delay.sample());
            double p = this.prob.sample();
            if (p < 0.6) {
                sim_schedule(out2, 0, 1);
                sim_trace(1, "Event from CPUDatabase to " + out2.get_dest_ename());
            } else {
                sim_schedule(out1, 0, 1);
                sim_trace(1, "Event from CPUDatabase to " + out1.get_dest_ename());
            }
        }
    }

}
