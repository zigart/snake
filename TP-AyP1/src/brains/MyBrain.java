package brains;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.unlam.snake.brain.Brain;
import edu.unlam.snake.engine.Direction;
import edu.unlam.snake.engine.Point;

public class MyBrain extends Brain {

	// Pueden agregarse todos los atributos necesarios

	public MyBrain() {
		super("EXE");
	}

	/**
	 * Retorna el próximo movimiento que debe hacer la serpiente.
	 * 
	 * @param head,     la posición de la cabeza
	 * @param previous, la dirección en la que venía moviéndose
	 */
	public Direction getDirection(Point head, Direction previous) {
		List<Point> fruits = info.getFruits();
		List<Point> snake = info.getSnake();
		List<List<Point>> enemies = info.getEnemies();
		List<Point> obstacles = info.getObstacles();
		// completar con la lógica necesaria para mover la serpiente,
		// intentando comer la mayor cantidad de frutas y sobrevivir
		// el mayor tiempo posible.

		int[] posicionXY = new int[2];
		System.out.println(head);
		posicionXY = buscarCosaMasCercana(head, fruits);
		Point frutaMasCercana = new Point(posicionXY[0],posicionXY[1]);
		int[] obstaculoDefrutaXYMasCercano = new int[6];
		obstaculoDefrutaXYMasCercano = buscarCosaMasCercana(frutaMasCercana, obstacles);
		
		System.out.println("objeto mas cercano fruta: " + Arrays.toString(obstaculoDefrutaXYMasCercano));
		return moveToFruit(snake, head, obstacles, posicionXY[0], posicionXY[1], previous);

	}

	private Direction moveToFruit(List<Point> snake, Point head, List<Point> obstacles, int masCercanaX,
			int masCercanaY, Direction previous) {
		// comprueba si la viborita esta en el mismo lugar, si no lo esta ejecuta

		while (snake.get(0).getX() != masCercanaX || snake.get(0).getY() != masCercanaY) {

			int[] posicionXY = new int[2];
			posicionXY = buscarCosaMasCercana(head, obstacles);

			boolean comprobarQueNoChocaIzquierda = snake.get(0).getX() - 1 != posicionXY[0]
					|| snake.get(0).getY() != posicionXY[1];
			boolean comprobarQueNoChocaAbajo = snake.get(0).getX() != posicionXY[0]
					|| snake.get(0).getY() - 1 != posicionXY[1];
			boolean comprobarQueNoChocaArriba = snake.get(0).getX() != posicionXY[0]
					|| snake.get(0).getY() + 1 != posicionXY[1];
			boolean comprobarQueNoChocaDerecha = snake.get(0).getX() + 1 != posicionXY[0]
					|| snake.get(0).getY() != posicionXY[1];

			// fruta a la izquierda
			if (snake.get(0).getX() > masCercanaX) {
				/*
				 * comprueba que el movimiento anterior no fue el contrario al que se quiere
				 * hacer. Si lo es, se mueve a otro lado
				 */

				if (previous.compatibleWith(Direction.LEFT) && comprobarQueNoChocaIzquierda
						&& noMorderseLaCola(snake, head, Direction.LEFT)) {
					return Direction.LEFT;
				} else {
					// hay buscar la manera de que decida correctamente a donde
					// deberia haber una comprobacion que busque que donde mueve no hay nada

					if (comprobarQueNoChocaAbajo && noMorderseLaCola(snake, head, Direction.DOWN)) {
						return Direction.DOWN;
					} else if (comprobarQueNoChocaArriba && noMorderseLaCola(snake, head, Direction.UP)) {
						return Direction.UP;

					}
				}

			} else if (snake.get(0).getX() < masCercanaX) {

				/*
				 * comprueba que el movimiento anterior no fue el contrario al que se quiere
				 * hacer. Si lo es, se mueve a otro lado
				 */
				if (previous.compatibleWith(Direction.RIGHT) && comprobarQueNoChocaDerecha
						&& noMorderseLaCola(snake, head, Direction.RIGHT)) {
					return Direction.RIGHT;
				} else if (comprobarQueNoChocaArriba && previous.compatibleWith(Direction.UP)
						&& noMorderseLaCola(snake, head, Direction.UP)) {
					// hay buscar la manera de que decida correctamente a donde
					return Direction.UP;
				} else {
					if (noMorderseLaCola(snake, head, Direction.DOWN)) {
						return Direction.DOWN;
					}
				}
			} else {
				if (snake.get(0).getY() > masCercanaY) {
					/*
					 * comprueba que el movimiento anterior no fue el contrario al que se quiere
					 * hacer. Si lo es, se mueve a otro lado
					 */

					if (previous.compatibleWith(Direction.DOWN) && comprobarQueNoChocaAbajo
							&& noMorderseLaCola(snake, head, Direction.DOWN)) {
						return Direction.DOWN;
					} else if (comprobarQueNoChocaDerecha && previous.compatibleWith(Direction.RIGHT)
							&& noMorderseLaCola(snake, head, Direction.RIGHT)) {
						// hay buscar la manera de que decida correctamente a donde
						return Direction.RIGHT;
					} else {
						if (noMorderseLaCola(snake, head, Direction.LEFT)) {
							return Direction.LEFT;
						}
					}
				} else if (snake.get(0).getY() < masCercanaY) {

					/*
					 * comprueba que el movimiento anterior no fue el contrario al que se quiere
					 * hacer. Si lo es, se mueve a otro lado
					 */
					if (previous.compatibleWith(Direction.UP) && comprobarQueNoChocaArriba
							&& noMorderseLaCola(snake, head, Direction.UP)) {
						System.out.println("SOY EL QUE VA PARA ARRIBA");
						return Direction.UP;
					} else if (comprobarQueNoChocaIzquierda && previous.compatibleWith(Direction.LEFT)
							&& comprobarQueNoChocaArriba && noMorderseLaCola(snake, head, Direction.LEFT)) {
						// hay buscar la manera de que decida correctamente a donde
						return Direction.LEFT;
					} else {
						if (noMorderseLaCola(snake, head, Direction.RIGHT)) {
							return Direction.RIGHT;
						}
					}
				}
			}

		}

		return Direction.DOWN;
	}

	public int[] buscarCosaMasCercana(Point snake, List<Point> cosa) {
		int[] positions = new int[6];
		positions[0] = cosa.get(0).getX();
		positions[1] = cosa.get(0).getY();
		positions[2] = cosa.get(0).getX();
		positions[3] = cosa.get(0).getY();
		positions[4] = cosa.get(0).getX();
		positions[5] = cosa.get(0).getY();
		int valorABorrar = 0;
		int posicionXSnake = snake.getX();
		int posicionYSnake = snake.getY();
		for (int i = 1; i < cosa.size(); i++) {
			if (Math.abs((positions[0] - posicionXSnake)) + Math.abs((positions[1] - posicionYSnake)) > Math
					.abs((cosa.get(i).getX() - posicionXSnake)) + Math.abs(cosa.get(i).getY() - posicionYSnake)) {
				positions[0] = cosa.get(i).getX();
				positions[1] = cosa.get(i).getY();
				valorABorrar = i;
			} 
		}
		
		if(cosa.size() > 1) {
			
		
		cosa.remove(valorABorrar);
		
		for (int i = 1; i < cosa.size(); i++) {
			if (Math.abs((positions[2] - posicionXSnake)) + Math.abs((positions[3] - posicionYSnake)) > Math
					.abs((cosa.get(i).getX() - posicionXSnake)) + Math.abs(cosa.get(i).getY() - posicionYSnake)) {
				positions[2] = cosa.get(i).getX();
				positions[3] = cosa.get(i).getY();
				valorABorrar = i;
			} 
		}
		}
		
		if(cosa.size() > 1) {
			
		
		cosa.remove(valorABorrar);
		
		for (int i = 1; i < cosa.size(); i++) {
			if (Math.abs((positions[4] - posicionXSnake)) + Math.abs((positions[5] - posicionYSnake)) > Math
					.abs((cosa.get(i).getX() - posicionXSnake)) + Math.abs(cosa.get(i).getY() - posicionYSnake)) {
				positions[4] = cosa.get(i).getX();
				positions[5] = cosa.get(i).getY();
				valorABorrar = i;
			} 
		}
		}
		
		System.out.println("dentro del metodo: "+  Arrays.toString(positions));
		return positions;
	}

	public boolean noMorderseLaCola(List<Point> snake, Point head, Direction direction) {
		boolean noEncontroParteDeLaSnake = true;

		for (int i = 0; i < snake.size(); i++) {
			System.out.println(snake.get(i).getY());
			if (head.getX() != snake.get(i).getX() && head.getY() != snake.get(i).getY()) {

				if (direction == Direction.RIGHT && head.getX() + 1 == snake.get(i).getX()
						&& head.getY() == snake.get(i).getY()) {
					System.out.println("entre a la derecha");
					noEncontroParteDeLaSnake = false;
				} else if (direction == Direction.LEFT && head.getX() - 1 == snake.get(i).getX()
						&& head.getY() == snake.get(i).getY()) {
					System.out.println("entre a la Izquierda");
					noEncontroParteDeLaSnake = false;

				} else if (direction == Direction.UP && head.getX() == snake.get(i).getX()
						&& head.getY() + 1 == snake.get(i).getY()) {
					System.out.println("entre arriba");
					noEncontroParteDeLaSnake = false;

				} else if (direction == Direction.DOWN && head.getX() == snake.get(i).getX()
						&& head.getY() - 1 == snake.get(i).getY()) {
					System.out.println("entre abajo");
					noEncontroParteDeLaSnake = false;
				}
			}
		}
		return noEncontroParteDeLaSnake;

	}

}
