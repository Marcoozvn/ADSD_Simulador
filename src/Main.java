import eduni.simjava.Sim_system;

public class Main {

    public static void main(String[] args) {
        Sim_system.initialise();
        Source source = new Source("Source", 10000, 1, 12);

        CPU cpuWebServer = new CPU("CpuWebServer", 1, 20, 0.05, 0.24, 0.4, 0.3);
        CPU cpuApplicationServer = new CPU("CpuApplicationServer", 1, 12, 0.05, 0.35, 0.2, 0.4);
        CpuDatabaseServer cpuDatabaseServer = new CpuDatabaseServer("CpuDatabaseServer", 1, 20);
        Out out = new Out("Out");

        Disk diskWebServer = new Disk("DiskWebServer", 20, 40);
        Disk diskApplicationServer = new Disk("DiskApplicationService", 20, 32);
        Disk diskDatabaseServer = new Disk("DiskDatabaseServer", 20, 35);

        Cache cacheWebServer = new Cache("CacheWebServer", 1, 12);
        Cache cacheApplicationServer = new Cache("CacheApplicationServer", 1, 12);

        //Ligacao com source
        Sim_system.link_ports("Source", "Out", "CpuWebServer", "In1");

        //Ligacao com o disco
        Sim_system.link_ports("CpuWebServer", "Out1", "DiskWebServer", "In");
        Sim_system.link_ports("CpuWebServer", "In2", "DiskWebServer", "Out");

        Sim_system.link_ports("CpuWebServer", "Out4", "Out", "In");

        //Ligacao com a cache
        Sim_system.link_ports("CpuWebServer", "Out3", "CacheWebServer", "In");
        Sim_system.link_ports("CpuWebServer", "In3", "CacheWebServer", "Out");

        //ligacao web -> api
        Sim_system.link_ports("CpuWebServer", "Out2", "CpuApplicationServer", "In1");

        //ligacao com o disco
        Sim_system.link_ports("CpuApplicationServer", "Out1", "DiskApplicationService", "In");
        Sim_system.link_ports("CpuApplicationServer", "In2", "DiskApplicationService", "Out");

        //ligacao com a cache
        Sim_system.link_ports("CpuApplicationServer", "Out3", "CacheApplicationServer", "In");
        Sim_system.link_ports("CpuApplicationServer", "In3", "CacheApplicationServer", "Out");

        //ligacao api -> web
        Sim_system.link_ports("CpuApplicationServer", "Out4", "CpuWebServer", "In4");

        //ligacao api -> disco
        Sim_system.link_ports("CpuApplicationServer", "Out2", "CpuDatabaseServer", "In1");

        //ligacao com o disco
        Sim_system.link_ports("CpuDatabaseServer", "Out1", "DiskDatabaseServer", "In");
        Sim_system.link_ports("CpuDatabaseServer", "In2", "DiskDatabaseServer", "Out");

        //ligacao disco -> api
        Sim_system.link_ports("CpuDatabaseServer", "Out2", "CpuApplicationServer", "In4");

        Sim_system.run();
    }
}
