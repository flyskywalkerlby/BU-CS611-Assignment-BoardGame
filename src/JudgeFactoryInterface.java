/*
* Judge factory to choose judge
* */


public interface JudgeFactoryInterface {
    BaseJudge createJudge(String game_key);
}

class JudgeFactory implements JudgeFactoryInterface {
    @Override
    public BaseJudge createJudge(String game_key) {
        switch (game_key) {
            case "TTT":
                return new JudgeTTT();
            case "OC":
                return new JudgeOC();
            case "SuperTTT":
                return new JudgeSuperTTT();
            case "QRD":
                return new JudgeQRD();
            default:
                throw new IllegalArgumentException("Unsupported");
        }
    }
}
