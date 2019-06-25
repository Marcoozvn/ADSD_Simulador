public enum EventTypes {
    FROM_SOURCE(0),
    FROM_CPU_WEB(1),
    FROM_CPU_APPLICATION(2),
    FROM_CPU_DATABASE(3),
    FROM_CACHE_WEB(4),
    FROM_CACHE_APPLICATION(5),
    FROM_DISK_WEB(6),
    FROM_DISK_APPLICATION(7),
    FROM_DISK_DATABASE(8);

    public final int value;

    private EventTypes(int value) {
        this.value = value;
    }
}
