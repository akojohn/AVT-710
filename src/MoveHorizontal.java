public class MoveHorizontal extends BaseAl {

    @Override
    public synchronized void step() {
        for (JImage obj : Habitat.universeVehicles) {
            if (obj instanceof Automobile) {
                obj.move();
            }
        }
    }
}
