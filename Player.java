

public class Player {

    private final Pawn _pawn1;
    private final Pawn _pawn2;
    private Pawn _pawn3;
    private Pawn _pawn4;

    public Player(Pawn pawn1, Pawn pawn2, Pawn pawn3, Pawn pawn4)
    {
        _pawn1 = pawn1;
        _pawn2 = pawn2;
        _pawn3 = pawn3;
        _pawn4 = pawn4;
    }
    public Pawn getPawn1()
    {
        return _pawn1;
    }
    public Pawn getPawn2()
    {
        return _pawn2;
    }
    public Pawn getPawn3()
    {
        return _pawn3;
    }
    public Pawn getPawn4()
    {
        return _pawn4;
    }


}
