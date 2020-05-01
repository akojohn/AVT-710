package Client2;

class GuppyAI extends BaseAI {

    GuppyAI(FishList fishList, int windowWidth, int windowHeight) {
        super(fishList, windowWidth, windowHeight);
    }

    @Override
    void move() {
        fishList.moveGuppy(windowWidth, windowHeight);
    }
}
