package brains;

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

		int[] masCercana = new int[2];

		masCercana[0] = fruits.get(0).getX();
		masCercana[1] = fruits.get(0).getY();

		for (int i = 1; i < fruits.size(); i++) {
			System.out.println();
			System.out.println("frutas: " + fruits);

			if (Math.abs(fruits.get(i).getX() - snake.get(0).getX()) < Math.abs(masCercana[0] - snake.get(0).getX())
					&& Math.abs(fruits.get(i).getY() - snake.get(0).getY()) < Math
							.abs(masCercana[1] - snake.get(0).getY())) {
				masCercana[0] = fruits.get(i).getX();
				masCercana[1] = fruits.get(i).getY();
			}

		}

		System.out.println(snake.get(0).getY());
		System.out.println(masCercana[1]);

		System.out.println(snake.get(0).getX());
		System.out.println(masCercana[0]);

		return moveToFruit(snake, masCercana, previous);

	}

	private Direction moveToFruit(List<Point> snake, int[] masCercana, Direction previous) {
		// comprueba si la vivorita esta en el mismo lugar, si no lo esta ejecuta
		while (snake.get(0).getX() != masCercana[0] || snake.get(0).getY() != masCercana[1]) {
			// fruta a la izquierda
			if (snake.get(0).getX() > masCercana[0]) {

				/*
				 * comprueba que el movimiento anterior no fue el contrario al que se quiere
				 * hacer. Si lo es, se mueve a otro lado
				 */
				if (!previous.equals(Direction.RIGHT)) {
					return Direction.LEFT;
				} else {
					//hay buscar la manera de que decida correctamente a donde
					return Direction.UP;
				}

			} else if (snake.get(0).getX() < masCercana[0]) {
				
				/*
				 * comprueba que el movimiento anterior no fue el contrario al que se quiere
				 * hacer. Si lo es, se mueve a otro lado
				 */
				if (!previous.equals(Direction.LEFT)) {
					return Direction.RIGHT;
				} else {
					//hay buscar la manera de que decida correctamente a donde
					return Direction.UP;
				}
			} else {
				if (snake.get(0).getY() > masCercana[1]) {
					/*
					 * comprueba que el movimiento anterior no fue el contrario al que se quiere
					 * hacer. Si lo es, se mueve a otro lado
					 */
					if (!previous.equals(Direction.UP)) {
						return Direction.DOWN;
					} else {
						//hay buscar la manera de que decida correctamente a donde
						return Direction.RIGHT;
					}
				} else if (snake.get(0).getY() < masCercana[1]) {
					
					/*
					 * comprueba que el movimiento anterior no fue el contrario al que se quiere
					 * hacer. Si lo es, se mueve a otro lado
					 */
					if (!previous.equals(Direction.DOWN)) {
						return Direction.UP;
					} else {
						//hay buscar la manera de que decida correctamente a donde
						return Direction.LEFT;
					}
				}
			}

		}

		return Direction.DOWN;
	}

}
