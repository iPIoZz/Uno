import java.util.UUID;

public class User {
    private String name;
    private UUID uuid;
    private boolean busy;

    public User(String name)
    {
        this.name = name;
        this.uuid = UUID.randomUUID();
        this.busy = false;
        Main.addUser(this);
    }

    public User(User user)
    {
        this.name = name;
        this.uuid = UUID.randomUUID();
        this.busy = false;
        Main.addUser(this);
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }
}
