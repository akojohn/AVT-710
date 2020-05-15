public class MoveVertical extends BaseAl {

    @Override
    public synchronized void step(){
        for (JImage obj:Habitat.universeVehicles) {
            if (obj instanceof Motorcycle){
                obj.move();
            }
        }
    }
}
