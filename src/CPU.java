import eduni.simjava.*;
import eduni.simjava.distributions.Sim_random_obj;
import eduni.simjava.distributions.Sim_uniform_obj;

public class CPU extends Sim_entity {
    private Sim_port in1, in2, in3, in4, out1, out2, out3, out4;
    private double probCache, probDisk, probCpu, probBack;
    private Sim_uniform_obj delay;
    private Sim_random_obj prob;
    private Sim_stat stat;
    private String contexto;

    public CPU(String name, double min, double max, double probCache, double probDisk, double probCPU, double probBack, String contexto) {
        super(name);
        in1 = new Sim_port("In1");
        in2 = new Sim_port("In2");
        in3 = new Sim_port("In3");
        in4 = new Sim_port("In4");
        out1 = new Sim_port("Out1");
        out2 = new Sim_port("Out2");
        out3 = new Sim_port("Out3");
        out4 = new Sim_port("Out4");
        add_port(out1);
        add_port(out2);
        add_port(out3);
        add_port(out4);
        add_port(in1);
        add_port(in2);
        add_port(in3);
        add_port(in4);
        this.contexto = contexto;
        this.probCache = probCache;
        this.probDisk = probDisk;
        this.probCpu = probCPU;
        this.probBack = probBack;

        delay = new Sim_uniform_obj("delay", min, max);
        add_generator(delay);

        prob = new Sim_random_obj("Probability");
        add_generator(prob);

        stat = new Sim_stat();
        stat.add_measure(Sim_stat.ARRIVAL_RATE); //Taxa de chegada
        stat.add_measure(Sim_stat.QUEUE_LENGTH); //Tamanho da fila
        stat.add_measure(Sim_stat.WAITING_TIME); //Tempo de espera
        stat.add_measure(Sim_stat.UTILISATION);  //Utilização
        stat.add_measure(Sim_stat.RESIDENCE_TIME); //Tempo de resposta
        set_stat(stat);
    }

    private EventTypes eventType() {
        switch (this.contexto) {
            case "WEB":
                return EventTypes.FROM_CPU_WEB;
            case "APPLICATION":
                return EventTypes.FROM_CPU_APPLICATION;
        }
        return null;
    }

    @Override
    public void body() {
        while(Sim_system.running()){
            //System.out.println(this.get_name());
            Sim_event e =  new Sim_event();
            sim_get_next(e);
            sim_process(delay.sample());
            double p = prob.sample();

            if (p < probCache) {
                sim_schedule(out3, 0, this.eventType().value);
                sim_trace(1, "Event from " + contexto + " CPU to " + out3.get_dest_ename());
            } else if ( p < probDisk) {
                sim_schedule(out1, 0, this.eventType().value);
                sim_trace(1, "Event from " + contexto + " CPU to " + out1.get_dest_ename());
            } else if (p < probCpu) {
                sim_schedule(out2, 0, this.eventType().value);
                System.out.println(this.get_name());
                sim_trace(1, "Event from " + contexto + " CPU to " + out2.get_dest_ename());
            } else {
                if (contexto.equals("CPU")) {
                    sim_completed(e);
                } else {
                    sim_schedule(out4, 0, this.eventType().value);
                    sim_trace(1, "Event from " + contexto + " CPU to " + out4.get_dest_ename());
                }
            }
        }
    }
}
