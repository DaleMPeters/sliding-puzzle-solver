sliding-puzzle-solver
=====================

A Java program to read in a file representing the current state of the sliding puzzle, solve it using one of A* Search (including Manhattan and Hamming distance heuristics), Breadth First Search or Depth First Search and outputs it to the command line with the route it took to get there and the approximate time it took to solve the puzzle.

The input file must be of the following format:

	x,x,x
	x,x,x
	x,x,x
	
where each x is a unique integer between 0 and 8.