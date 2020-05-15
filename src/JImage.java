
public abstract class JImage implements IBehaviour {
    long bornTime;
    long lifeTime;
    long identificationNumber;

    public boolean isDeterminate(long currentUniverseTime) {
        if (currentUniverseTime >= bornTime + lifeTime)
            return true;
        else return false;
    }
}
