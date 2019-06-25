import eduni.simjava.*;
import eduni.simjava.distributions.Sim_uniform_obj;

public class Disk extends Sim_entity {
    private Sim_port in, out;
    private Sim_uniform_obj delay;
    private Sim_stat stat;
    private String contexto;

    public Disk(String name, int min, int max, String contexto) {
        super(name);
        in = new Sim_port("In");
        out = new Sim_port("Out");
        add_port(in);
        add_port(out);
        this.contexto = contexto;
        delay = new Sim_uniform_obj("delay", min, max);
        add_generator(delay);

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
                return EventTypes.FROM_DISK_WEB;
            case "APPLICATION":
                return EventTypes.FROM_DISK_APPLICATION;
            case "DATABASE":
                return EventTypes.FROM_DISK_DATABASE;
        }
        return null;
    }

    @Override
    public void body(){
        while (Sim_system.running()){
            Sim_event e = new Sim_event();
            sim_get_next(e);
            sim_process(delay.sample());
            sim_schedule(out, 0, this.eventType().value);
            sim_trace(1, "Event from " + contexto + " Disk to " + out.get_dest_ename());
        }
    }
}
