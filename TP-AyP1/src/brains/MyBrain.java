package brains;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
		System.out.println(fruits);
		// completar con la lógica necesaria para mover la serpiente,
		// intentando comer la mayor cantidad de frutas y sobrevivir
		// el mayor tiempo posible.

		int[] posicionXY = new int[2];
		
		posicionXY = buscarCosaMasCercana(snake,fruits);
		
		return moveToFruit(snake, obstacles, posicionXY[0], posicionXY[1], previous);

	}

	private Direction moveToFruit(List<Point> snake, List<Point> obstacles, int masCercanaX, int masCercanaY,
			Direction previous) {
		// comprueba si la vivorita esta en el mismo lugar, si no lo esta ejecuta
		while (snake.get(0).getX() != masCercanaX || snake.get(0).getY() != masCercanaY) {
			int[] posicionXY = new int[2];
		
			posicionXY = buscarCosaMasCercana(snake,obstacles);
			
			System.out.println(posicionXY[0] + " valores cy " + posicionXY[1]);
			boolean comprobarQueNoChocaIzquierda = snake.get(0).getX() - 1 != posicionXY[0]
					|| snake.get(0).getY() != posicionXY[1];
			boolean comprobarQueNoChocaAbajo = snake.get(0).getX() != posicionXY[0] || snake.get(0).getY() - 1 != posicionXY[1];
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

				if (!previous.equals(Direction.RIGHT) && comprobarQueNoChocaIzquierda) {

					return Direction.LEFT;
				} else {
					// hay buscar la manera de que decida correctamente a donde
					// deberia haber una comprobacion que busque que donde mueve no hay nada

					if (comprobarQueNoChocaArriba) {
						return Direction.UP;
					} else {
						return Direction.DOWN;
					}
				}

			} else if (snake.get(0).getX() < masCercanaX) {

				/*
				 * comprueba que el movimiento anterior no fue el contrario al que se quiere
				 * hacer. Si lo es, se mueve a otro lado
				 */
				if (!previous.equals(Direction.LEFT) && comprobarQueNoChocaDerecha) {
					return Direction.RIGHT;
				} else {
					// hay buscar la manera de que decida correctamente a donde
					return Direction.UP;
				}
			} else {
				if (snake.get(0).getY() > masCercanaY) {
					/*
					 * comprueba que el movimiento anterior no fue el contrario al que se quiere
					 * hacer. Si lo es, se mueve a otro lado
					 */

					if (!previous.equals(Direction.UP) && comprobarQueNoChocaAbajo) {

						return Direction.DOWN;
					} else {
						// hay buscar la manera de que decida correctamente a donde

						return Direction.RIGHT;
					}
				} else if (snake.get(0).getY() < masCercanaY) {

					/*
					 * comprueba que el movimiento anterior no fue el contrario al que se quiere
					 * hacer. Si lo es, se mueve a otro lado
					 */
					if (!previous.equals(Direction.DOWN) && comprobarQueNoChocaArriba) {

						return Direction.UP;
					} else {
						// hay buscar la manera de que decida correctamente a donde

						return Direction.LEFT;
					}
				}
			}

		}

		return Direction.DOWN;
	}


	public int[] buscarCosaMasCercana(List<Point>snake,List<Point> cosa) {
		int[] positions = new int[2];
		positions[0] = cosa.get(0).getX();
		positions[1] = cosa.get(0).getY();
		
		
		int posicionXSnake = snake.get(0).getX();
		int posicionYSnake = snake.get(0).getY();
		for (int i = 1; i < cosa.size(); i++) {

			if (Math.abs((positions[0] - posicionXSnake))
					+ Math.abs((positions[1] - posicionYSnake)) > Math.abs((cosa.get(i).getX() - posicionXSnake))
							+ Math.abs(cosa.get(i).getY() - posicionYSnake)) {
				positions[0] = cosa.get(i).getX();
				positions[1] = cosa.get(i).getY();
				System.out.println(cosa.get(i));
			}
		}
		System.out.println(positions[0] + " posiciones " + positions[1]);
		return positions;
	}
}
