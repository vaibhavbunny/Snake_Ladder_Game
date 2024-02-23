import java.io.*;
import java.util.*;

public class SnakesLadder extends AbstractSnakeLadders {
	
	int N, M;
	int snakes[];
	int ladders[]; 
	ArrayList<Integer> startladders = new ArrayList<>() ; 
	cellNode ZeroNodes[];
	cellNode EndNodes[];
	public SnakesLadder(String name)throws Exception{
		File file = new File(name);
		BufferedReader br = new BufferedReader(new FileReader(file));
		N = Integer.parseInt(br.readLine());
        
        M = Integer.parseInt(br.readLine());

		int newLadders[];
		int newSnakes[];

	    snakes = new int[N];
		ladders = new int[N];
		newLadders = new int[N];
		newSnakes = new int[N]; 
		ZeroNodes = new cellNode[N+1]; 
		EndNodes = new cellNode[N+1]; 
		ArrayList<cellNode> ZeroDistance = new ArrayList<>() ; 

	    for (int i = 0; i < N; i++){
			snakes[i] = -1;
			ladders[i] = -1;
			newLadders[i] = -1;
			newSnakes[i] = -1; 
			ZeroNodes[i] = null;
			EndNodes[i] = null;
		}
		ZeroNodes[N] = null; 
		EndNodes[N] = null; 

		for(int i=0;i<M;i++){
            String e = br.readLine();
            StringTokenizer st = new StringTokenizer(e);
            int source = Integer.parseInt(st.nextToken());
            int destination = Integer.parseInt(st.nextToken());


			if(source<destination){
				ladders[source] = destination;
				startladders.add(source);
				newSnakes[destination] = source;
			}
			else{
				snakes[source] = destination;
				 
				newLadders[destination] = source; 
			}
        }

		int[] visitedcells = new int[N+1] ; 
		cellNode Init = new cellNode() ; 
		Init.cellnumber = 0 ; 
		Init.distance = 0 ; 
		ZeroNodes[0] = Init ;
		for(int i = 0 ; i < visitedcells.length ; i++){
			visitedcells[i] = 0 ; 
		} 
		visitedcells[0] = 1 ; 
		ZeroDistance.add(Init) ; 
		while(!ZeroDistance.isEmpty()){
			cellNode topNode = ZeroDistance.remove(0) ; 
			int cellNumber = topNode.cellnumber ; 
			int cellDistance = topNode.distance ; 
			for(int i = cellNumber + 1 ; i <= cellNumber + 6 && i <= N ; i++){
				if(visitedcells[i] == 0){
					int t = i ; 
					cellNode startCell = new cellNode();
					startCell.distance = cellDistance + 1 ; 
					startCell.cellnumber = i ; 
					if(ZeroNodes[i] == null){
						ZeroNodes[i] = startCell ; 
					}
					
					visitedcells[startCell.cellnumber] = 1 ; 
					if(i != N){
						ZeroDistance.add(startCell) ;
					}
					if(i != N){
						while(ladders[t] != -1 || snakes[t] != -1){
						
							if(ladders[t] != -1){
								cellNode newcell = new cellNode() ; 
								newcell.distance = cellDistance + 1 ; 
								newcell.cellnumber = ladders[t] ; 
								ZeroDistance.add(newcell) ; 
								if(ZeroNodes[newcell.cellnumber] == null){
									ZeroNodes[newcell.cellnumber] = newcell ; 
								}
								visitedcells[newcell.cellnumber] = 1 ; 
								t = ladders[t] ; 
							}
							else if(snakes[t] != -1){
								cellNode newcell = new cellNode();
								newcell.distance = cellDistance + 1 ; 
								newcell.cellnumber = snakes[t] ;
								ZeroDistance.add(newcell) ; 
								if(ZeroNodes[newcell.cellnumber] == null){
									ZeroNodes[newcell.cellnumber] = newcell ; 
								}
								visitedcells[newcell.cellnumber] = 1 ; 
								t = snakes[t] ; 
							}
							
						}
					}
					
				}
			}
		}

		int[] revVisited = new int[N+1] ; 
		ArrayList<cellNode> revNodes = new ArrayList<>() ; 
		cellNode revInit = new cellNode() ; 
		revInit.cellnumber = N ; 
		revInit.distance = 0 ; 
		EndNodes[N] = Init ;
		for(int i = 0 ; i < revVisited.length ; i++){
			revVisited[i] = 0 ; 
		} 
		revVisited[N] = 1 ; 
		revNodes.add(revInit) ;
		while(!revNodes.isEmpty()){
			cellNode topNode = revNodes.remove(0) ; 
			int cellNumber = topNode.cellnumber ; 
			int cellDistance = topNode.distance ; 
			for(int i = cellNumber-1 ; i >= cellNumber-6 && i >= 0 ; i--){
				if(revVisited[i] == 0){
					int t = i ; 
					cellNode startCell = new cellNode();
					startCell.distance = cellDistance + 1 ; 
					startCell.cellnumber = i ; 
					if(EndNodes[i] == null){
						EndNodes[i] = startCell ; 
					}
					
					revVisited[startCell.cellnumber] = 1 ; 
					if(i != 0){
						revNodes.add(startCell) ;
					}
					if(i != N){
						while(newLadders[t] != -1 || newSnakes[t] != -1){

							if(newLadders[t] != -1){
								cellNode newcell = new cellNode() ; 
								newcell.distance = cellDistance + 1 ; 
								newcell.cellnumber = newLadders[t] ; 
								revNodes.add(newcell) ; 
								if(EndNodes[newcell.cellnumber] == null){
									EndNodes[newcell.cellnumber] = newcell ; 
								}
								revVisited[newcell.cellnumber] = 1 ; 
								t = newLadders[t] ; 
							}
							else if(newSnakes[i] != -1){
								cellNode newcell = new cellNode();
								newcell.distance = cellDistance + 1 ; 
								newcell.cellnumber = newSnakes[t] ;
								revNodes.add(newcell) ; 
								if(EndNodes[newcell.cellnumber] == null){
									EndNodes[newcell.cellnumber] = newcell ; 
								}
								visitedcells[newcell.cellnumber] = 1 ; 
								t = newSnakes[t] ; 
							}

						}
					}
				}
			} 
		}
	}
    
	public class cellNode{
		int cellnumber ; 
		int distance ; 
	}


	public int OptimalMoves()
	{
		/* Complete this function and return the minimum number of moves required to win the game. */
		// return ZeroNodes[N].distance ; 
		int[] visitednodes = new int[N+1] ;
		ArrayList<cellNode> visitedcells = new ArrayList<>() ;  
		cellNode initializer = new cellNode() ; 
		initializer.cellnumber = 0 ; 
		initializer.distance = 0 ;
		for(int i = 0 ; i < visitednodes.length ; i++){
			visitednodes[i] = 0 ; 
		}
		visitednodes[0] = 1 ; 
		visitedcells.add(initializer) ; 
		int requiredans = -1 ; 
		while(!visitedcells.isEmpty()){
			cellNode topNode = visitedcells.remove(0) ; 
			int CellNumber = topNode.cellnumber ; 

			if(CellNumber == N){
				requiredans = topNode.distance ; 
				visitedcells.add(topNode) ; 
				break ;  
			}
			else{
				for(int i = CellNumber+1 ; i <= CellNumber + 6 && i <= N ; i++){
					if(visitednodes[i] == 0){
						cellNode newCell = new cellNode() ; 
						newCell.distance = topNode.distance + 1 ;
						visitednodes[i] = 1 ; 
						if(i < N){
							if(ladders[i] != -1){
								int x = i ; 
								while(ladders[x] != -1 || snakes[x] != -1){
									if(ladders[x] != -1){
										newCell.cellnumber = ladders[x] ; 
										x = newCell.cellnumber ; 
									}
									else if(snakes[x] != -1){
										newCell.cellnumber = snakes[x] ; 
										x = newCell.cellnumber ; 
									}
								}
							}
							else if(snakes[i] != -1){
								int x = i ; 
								while(ladders[x] != -1 || snakes[x] != -1){
									if(ladders[x] != -1){
										newCell.cellnumber = ladders[x] ; 
										x = newCell.cellnumber ; 
									}
									else if(snakes[x] != -1){
										newCell.cellnumber = snakes[x] ; 
										x = newCell.cellnumber ; 
									}
								}
							}
							else{
								newCell.cellnumber = i ; 
							}
						}
						else{
							newCell.cellnumber = i ; 
						}
						visitedcells.add(newCell) ; 
					}
				}
			}
		}
		return requiredans ; 
	}

	public int Query(int x, int y)
	{
		/* Complete this function and 
			return +1 if adding a snake/ladder from x to y improves the optimal solution, 
			else return -1. */
			if(ZeroNodes[x].distance + EndNodes[y].distance < ZeroNodes[N].distance){
				return +1 ; 
			}
			return -1;
	}

	public int[] FindBestNewSnake()
	{
		int result[] = {-1,-1};
		/* Complete this function and 
			return (x, y) i.e the position of snake if adding it increases the optimal solution by largest value,
			if no such snake exists, return (-1, -1) */
		int optiMoves = OptimalMoves() ; 
		int n = startladders.size() ; 
		for(int i = 0 ; i < n ; i++){
			int x1 = startladders.get(i) ; 
			int y1 = ladders[x1] ; 
			for(int j = 0 ; j < n ; j++){
				if(i != j){
					int x2 = startladders.get(j) ; 
					int y2 = ladders[x2] ; 
					if(y1 > x2)
					if(optiMoves > ZeroNodes[x1].distance + EndNodes[y2].distance){
						result[0] = y1 ;
						result[1] = x2 ;  
						optiMoves = ZeroNodes[x1].distance + EndNodes[y2].distance ;
					}
				}
			}
		}
		return result;
	}

}
