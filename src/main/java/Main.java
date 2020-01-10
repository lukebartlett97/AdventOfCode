import mazes.DrawMazes;
import mazes.Maze;
import mazes.MazeGenerator;
import mazes.templates.CircleTemplate;
import mazes.templates.GridTemplate;
import mazes.templates.IslandTemplate;
import mazes.templates.ManualPieceTemplate;
import mazes.templates.MazeTemplate;
import mazes.templates.RingsTemplate;
import mazes.templates.WallTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main
{
  public static void main(String[] args) throws IOException, InterruptedException
  {
    //FuelSum solution = new FuelSum();
    //CrossedWires solution = new CrossedWires();
    //ForgottenPassword solution = new ForgottenPassword();
    //Constellations solution = new Constellations()
    //IntCodeCont solution = new IntCodeCont();
    //IntCodeCont solution = new IntCodeCont();
    //UniversalOrbit solution = new UniversalOrbit();
    //AmplificationCircuit solution = new AmplificationCircuit();
    //SpaceImage solution = new SpaceImage();
    //SensorBoost solution = new SensorBoost();
    //MonitoringStation solution = new MonitoringStation();
    //LeftSolver solution = new LeftSolver();
    //Maze maze = gen.createMaze(10,10, 0, 2, 9, 5, true, 0);
    //Maze maze = gen.createMaze(20,20, 0, 2, 19, 15, true, 0);
    //Maze maze = gen.createMaze(50,50, 0, 2, 49, 35, true, 0);
//    MazeGenerator gen = new MazeGenerator();
//    WallTemplate wallTemplate = new WallTemplate();
//    RingsTemplate ringsTemplate = new RingsTemplate(28, 30, 57, 56);
////    IslandTemplate islandTemplate = new IslandTemplate(10, 10, 20, 20);
////    IslandTemplate islandTemplate2 = new IslandTemplate(40, 10, 50, 20);
////    IslandTemplate islandTemplate3 = new IslandTemplate(10, 40, 20, 50);
////    IslandTemplate islandTemplate4 = new IslandTemplate(40, 40, 50, 50);
////    IslandTemplate openSpace = new IslandTemplate(20, 20, 40, 40, Maze.OPEN);
//    GridTemplate gridTemplate = new GridTemplate(1, 1, 25, 25);
//    CircleTemplate circleTemplate = new CircleTemplate(42, 14, 8);
//    CircleTemplate circleTemplate2 = new CircleTemplate(14, 42, 8);
////    ManualPieceTemplate markerTemplate = new ManualPieceTemplate(29, 29, Maze.MARKER);
//    List<MazeTemplate> templates = new ArrayList<>();
//    templates.add(wallTemplate);
//    templates.add(ringsTemplate);
//    templates.add(gridTemplate);
////    templates.add(markerTemplate);
//    templates.add(circleTemplate);
//    templates.add(circleTemplate2);
//    Maze maze = gen.createMaze(59,59, 0, 29, 58, 29, templates, 0);
//    maze.printMaze();
//    maze.repaint();

    //FlowSolver solution = new FlowSolver();
    //solution.solveFull(maze.clone(true));
    //solution.printSolution(true);
    DrawMazes drawMazes = new DrawMazes();
    drawMazes.printSolution(false);


    //flowSolver.printSolution(true);
  }
}
