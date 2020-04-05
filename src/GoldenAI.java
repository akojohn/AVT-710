class GoldenAI extends BaseAI {

    GoldenAI(FishList fishList, int windowWidth, int windowHeight) {
        super(fishList, windowWidth, windowHeight);
    }

    @Override
    void move() {
        fishList.moveGolden(windowWidth, windowHeight);
    }
}
