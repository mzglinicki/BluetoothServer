package codeCreator;

/**
 * Created by mzglinicki.96 on 14.07.2016.
 */
public class Constants {

    public final static String PROC_MAIN = "PROC main() \n";
    public final static String GO_TO_FIRST_POINT_COMMENT = "     !Idź do punktu pierwszego p10 \n     MoveL p10, v1000, z0, tool0; \n";
    public final static String MOVE_L_OFFS_P20_START_COMMAND = "     MoveL Offs (p20,";
    public final static String MOVE_L_OFFS_P20_END_COMMAND = ", 0), v1000, z0, tool0;";
    public final static String END_DRAWING = "     !Zakończ\n      MoveL p10, v1000, z0, tool0;\nENDPROC";
    public final static String END_OF_SHAPE_COMMENT = "!koniec kształtu";
    public final static String GET_FIRST_TOOL = "Black";
    public final static String GET_SECOND_TOOL = "Green";
    public final static String GET_THIRD_TOOL = "Blue";
    public final static String GET_FOURTH_TOOL = "Red";
    public final static String GET_FIFTH_TOOL = "Orange";
    public final static String GET_SIXTH_TOOL = "Yellow";
}