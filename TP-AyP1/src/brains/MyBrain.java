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
	private boolean seMovioEnX = true;
	private boolean entrarAl2 = true;
	private int contador = 0;

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
		System.out.println(previous);
		int[] posicionXY = new int[2];
		System.out.println(head);
		posicionXY = buscarCosaMasCercana(head, fruits);
		Point frutaMasCercana = new Point(posicionXY[0], posicionXY[1]);
		return moveToFruit(snake, head, obstacles, frutaMasCercana, previous);

	}

	private Direction moveToFruit(List<Point> snake, Point head, List<Point> obstacles, Point frutaMasCercana,
			Direction previous) {
		// revisar primer movimiento
		boolean[] obstaculos = buscarObstaculoEnCabeza(head, obstacles);
		System.out.println("Obstaculos de moveToFruit: " + Arrays.toString(obstaculos));
		
		if (head.getX() > frutaMasCercana.getX()) {
			/*
			 * comprueba que el movimiento anterior no fue el contrario al que se quiere
			 * hacer. Si lo es, se mueve a otro lado
			 */

			if (previous.compatibleWith(Direction.LEFT) && !obstaculos[3]) {
				return Direction.LEFT;
			} else {
				// hay buscar la manera de que decida correctamente a donde
				// deberia haber una comprobacion que busque que donde mueve no hay nada

				if (!obstaculos[2]) {
					return Direction.DOWN;
				} else if (!obstaculos[0]) {
					return Direction.UP;

				}
			}

		} else if (snake.get(0).getX() < frutaMasCercana.getX()) {

			/*
			 * comprueba que el movimiento anterior no fue el contrario al que se quiere
			 * hacer. Si lo es, se mueve a otro lado
			 */
			if (previous.compatibleWith(Direction.RIGHT) && !obstaculos[1]
					) {
				return Direction.RIGHT;
			} else if (!obstaculos[0] && previous.compatibleWith(Direction.UP)) {
				// hay buscar la manera de que decida correctamente a donde
				return Direction.UP;
			} else {
				
					return Direction.DOWN;
				}
			} else {
			if (head.getY() > frutaMasCercana.getY()) {
				/*
				 * comprueba que el movimiento anterior no fue el contrario al que se quiere
				 * hacer. Si lo es, se mueve a otro lado
				 */

				if (previous.compatibleWith(Direction.DOWN) && !obstaculos[2]) {
					return Direction.DOWN;
				} else if (!obstaculos[1] && previous.compatibleWith(Direction.RIGHT)) {
					// hay buscar la manera de que decida correctamente a donde
					return Direction.RIGHT;
				} else {
						return Direction.LEFT;
				}
			} else if (head.getY() < frutaMasCercana.getY()) {

				/*
				 * comprueba que el movimiento anterior no fue el contrario al que se quiere
				 * hacer. Si lo es, se mueve a otro lado
				 */
				if (previous.compatibleWith(Direction.UP) && !obstaculos[0]) {
					System.out.println("SOY EL QUE VA PARA ARRIBA");
					return Direction.UP;
				} else if (!obstaculos[3] && previous.compatibleWith(Direction.LEFT)) {
					// hay buscar la manera de que decida correctamente a donde
					return Direction.LEFT;
				}else if(!obstaculos[1]&& previous.compatibleWith(Direction.RIGHT)) {
					return Direction.RIGHT;
				}
			}
		}
	return Direction.DOWN;

	}

	private boolean[] buscarObstaculoEnCabeza(Point head, List<Point> obstaculo) {

		System.out.println(head);
		boolean obstaculoNorte = false;
		boolean obstaculoSur = false;
		boolean obstaculoEste = false;
		boolean obstaculoOeste = false;

		for (int i = 0; i < obstaculo.size(); i++) {
			System.out.println("NORTE: " + (head.getX()) + " " + obstaculo.get(i).getX() + "y: " + (head.getY() + 1)
					+ " " + (obstaculo.get(i).getY()));
			System.out.println("SUR: " + (head.getX()) + " " + obstaculo.get(i).getX() + "y: " + (head.getY() - 1) + " "
					+ (obstaculo.get(i).getY()));
			System.out.println("ESTE: " + (head.getX() + 1) + " " + obstaculo.get(i).getX() + " y: " + (head.getY())
					+ " " + (obstaculo.get(i).getY()));
			System.out.println("OESTE: " + (head.getX() - 1) + " " + obstaculo.get(i).getX() + " y: " + (head.getY())
					+ " " + (obstaculo.get(i).getY()));

			if (obstaculo.get(i).getX() == head.getX() + 1 && obstaculo.get(i).getY() == head.getY()) {
				obstaculoEste = true;
			}
			if (obstaculo.get(i).getX() == head.getX() - 1 && obstaculo.get(i).getY() == head.getY()) {
				obstaculoOeste = true;
			}
			if (obstaculo.get(i).getX() == head.getX() && obstaculo.get(i).getY() == head.getY() - 1) {
				obstaculoSur = true;
			}
			if (obstaculo.get(i).getX() == head.getX() && obstaculo.get(i).getY() == head.getY() + 1) {
				obstaculoNorte = true;
			}
		}

		boolean[] obstaculos = new boolean[4];
		obstaculos[0] = obstaculoNorte;
		obstaculos[1] = obstaculoEste;
		obstaculos[2] = obstaculoSur;
		obstaculos[3] = obstaculoOeste;

		return obstaculos;
	}

	public int[] buscarCosaMasCercana(Point snake, List<Point> cosa) {
		int[] positions = new int[2];
		positions[0] = cosa.get(0).getX();
		positions[1] = cosa.get(0).getY();

		int posicionXSnake = snake.getX();
		int posicionYSnake = snake.getY();
		for (int i = 1; i < cosa.size(); i++) {
			if (Math.abs((positions[0] - posicionXSnake)) + Math.abs((positions[1] - posicionYSnake)) > Math
					.abs((cosa.get(i).getX() - posicionXSnake)) + Math.abs(cosa.get(i).getY() - posicionYSnake)) {
				positions[0] = cosa.get(i).getX();
				positions[1] = cosa.get(i).getY();
			}
		}

		System.out.println("dentro del metodo: " + Arrays.toString(positions));
		return positions;
	}

}
