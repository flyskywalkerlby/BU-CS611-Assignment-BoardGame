# CS611-Assignment 3

## Quoridor
Name: Boyang Liu  
Email: liubyfly@bu.edu  
Student ID: U54290758

Name: Chunaho Bi  
Email: chunhbi@bu.edu   
Student ID: U58132128

## Files
```
|--Main
|--Engine
|--Board
|--Tile
|--TileObject
   |--TilePiece
   |--TileTTT
   |--TilePieceWithWall
|--Piece
|--Wall
|--Color
|--GameFactory (interface)
|--BaseGame
   |--TTT
   |--OC
   |--SuperTTT
   |--Quoridor
|--JudgeFactory (interface)
|--BaseJudge
   |--JudgeTTT
   |--JudgeOC
   |--JudgeSuperTTT
   |--JudgeQuoridor
|--Team
|--Player
|--Performance
```

## Quoridor Implement Bullet points
### Color Representation
- Player can choose different colors as their representation
- Walls on the board are displayed in red, which is easier to distinguish.
- Available moves are shown in different color, which is easier for player to choose their action.
- Tile index are shown in color other than base board.

### Heuristic check while wall placing
- Heuristic path finding techniques is used in the game to determine possible success, which took reference to A* algorithm and DFS. This prevents player from blocking the opponent permanently, which is according to the game rules.
- Similar to this, available move positions are calculated with jump considered.(jump over the opponent)

### User Interaction
- Player can easily input their decisions by simply typing an index for player action, or four indexes for placing a wall.
- Easy quit by typing "quit" or "exit", easy game restart after a finished one.

### Extendability
- Created base classes like TileObject and Wall, which may be extended in future games without modification of existent structures.
- Created interfaces like GameFactories and JudgeFactories, which is encapsulated tightly for future use.

### Scalability
- The game can be easily scaled to other sizes.
- The team can contain multiple players.
- Color can be chosen by players.

## Adjustments compared to the previous assignment
- Add Quoridor.java
  - Move or Place a wall
  - Some case analysis functions
- Add JudgeQRD (very simple, actually may not have to)
- Add TilePieceWithWall extends TileObject
- Add Wall to record the relationship between wall and tiles
- Better UI, made most use of color

## How to compile and run
1. Navigate to the directory "liubyfly@csa2.bu.edu:~/cs611/hw3/src"  
   or unzip "cs611-hw3-Quoridor-Boyang Liu-ChunhaoBi"
2. Run the following instructions:  
   mkdir out  
   javac *.java -d out  
   java -cp ./out Main

## Input/Output Example
Since color is the core in our work.
We use screenshots to display our input/output examples.

- some invalid operations  
![case](images/invalid_oprerations.png)  

- jump  
![jump](images/jump.png)  

- result   
![result](images/result.png)  

