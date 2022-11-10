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
		System.out.println(previous);
		int[] posicionXY = new int[2];
		System.out.println(head);
		posicionXY = buscarCosaMasCercana(head, fruits);
		Point frutaMasCercana = new Point(posicionXY[0], posicionXY[1]);
		return moveToFruit(snake, head, enemies,obstacles, frutaMasCercana, previous);

	}

	private Direction moveToFruit(List<Point> snake, Point head,List<List<Point>>enemies, List<Point> obstacles, Point frutaMasCercana,
			Direction previous) {
		// revisar primer movimiento
		boolean[] obstaculos = buscarObstaculoEnCabeza(head, obstacles);
		boolean[] parteDeSnake = buscarObstaculoEnCabeza(head, snake);
		boolean[] snakeContraria = buscarEnemigoEnCabeza(head, enemies);
		System.out.println("Obstaculos de moveToFruit: " + Arrays.toString(obstaculos));

		if (head.getX() > frutaMasCercana.getX()) {

			if (previous.compatibleWith(Direction.LEFT) && !obstaculos[3] && !parteDeSnake[3] && !snakeContraria[3] && !snakeContraria[7]) {
				return Direction.LEFT;
			} else {
				if (!obstaculos[2] && !parteDeSnake[2] && !snakeContraria[2] && !snakeContraria[6]) {
					return Direction.DOWN;
				} else if (!obstaculos[0] && !parteDeSnake[0] && !snakeContraria[0] && !!snakeContraria[4]) {
					return Direction.UP;
				}else {
					return Direction.RIGHT;
				}
			}

		} else if (head.getX() < frutaMasCercana.getX()) {

			if (previous.compatibleWith(Direction.RIGHT) && !obstaculos[1] && !parteDeSnake[1] && !snakeContraria[1] && !snakeContraria[5]) {
				return Direction.RIGHT;
			} else if (!obstaculos[0] && !parteDeSnake[0] && !snakeContraria[0] && previous.compatibleWith(Direction.UP) && !snakeContraria[4]) {
				return Direction.UP;
			}else if (!obstaculos[2] && !parteDeSnake[2] && !snakeContraria[2] && !snakeContraria[6]) {
				return Direction.DOWN;
			}
		} else {
			if (head.getY() > frutaMasCercana.getY()) {
				if (previous.compatibleWith(Direction.DOWN) && !obstaculos[2] && !parteDeSnake[2] && !snakeContraria[2] && !snakeContraria[6]) {
					return Direction.DOWN;
				} else if (!obstaculos[1] && !parteDeSnake[1] && !snakeContraria[1] && !snakeContraria[5] && previous.compatibleWith(Direction.RIGHT)) {
					return Direction.RIGHT;
				}else if (!obstaculos[3] && !parteDeSnake[3] && !snakeContraria[3] && !snakeContraria[7]) {
					return Direction.DOWN;
				}
			} else if (head.getY() < frutaMasCercana.getY()) {
				if (previous.compatibleWith(Direction.UP) && !obstaculos[0] && !parteDeSnake[0] && !snakeContraria[0] && !snakeContraria[4]) {
					return Direction.UP;
				} else if (!obstaculos[3] && !parteDeSnake[3] && !snakeContraria[3] && !snakeContraria[7]) {
					// hay buscar la manera de que decida correctamente a donde
					return Direction.LEFT;
				} else if (!obstaculos[1] && !parteDeSnake[1] && !snakeContraria[1] && !snakeContraria[5]) {
					return Direction.RIGHT;
				}
			}
		}
		// retorna la direccion esta de forma erronea cuando no entra a los ifs
		return previous;
	}

	private boolean[] buscarObstaculoEnCabeza(Point head, List<Point> obstaculo) {

		boolean obstaculoNorte = false;
		boolean obstaculoSur = false;
		boolean obstaculoEste = false;
		boolean obstaculoOeste = false;

		for (int i = 0; i < obstaculo.size(); i++) {
	
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
	private boolean[] buscarEnemigoEnCabeza(Point head, List<List<Point>> obstaculo) {
		
		boolean obstaculoNorte = false;
		boolean obstaculoSur = false;
		boolean obstaculoEste = false;
		boolean obstaculoOeste = false;
		
		boolean posibleChoqueNorte = false;
		boolean posibleChoqueSur = false;
		boolean posibleChoqueEste = false;
		boolean posibleChoqueOeste = false;

		for (int i = 0; i < obstaculo.size(); i++) {
		
			for(int j = 0; j < obstaculo.get(i).size(); j++) {
				
			if (obstaculo.get(i).get(j).getX() == head.getX() + 1 && obstaculo.get(i).get(j).getY() == head.getY()) {
				obstaculoEste = true;
			}
			if (obstaculo.get(i).get(j).getX() == head.getX() - 1 && obstaculo.get(i).get(j).getY() == head.getY()) {
				obstaculoOeste = true;
			}
			if (obstaculo.get(i).get(j).getX() == head.getX() && obstaculo.get(i).get(j).getY() == head.getY() - 1) {
				obstaculoSur = true;
			}
			if (obstaculo.get(i).get(j).getX() == head.getX() && obstaculo.get(i).get(j).getY() == head.getY() + 1 ) {
				obstaculoNorte = true;
			}
			}
		}
		for (int i = 0; i < obstaculo.size(); i++) {
			
				if (Math.abs(obstaculo.get(i).get(0).getX() - head.getX() + 2) <= 2 && obstaculo.get(i).get(0).getY() == head.getY() ) {
					posibleChoqueEste = true;
				}
				if (Math.abs(obstaculo.get(i).get(0).getX() - head.getX() - 2) <= 2  && obstaculo.get(i).get(0).getY() == head.getY()) {
					posibleChoqueOeste = true;
				}
				if (obstaculo.get(i).get(0).getX() == head.getX() && Math.abs(obstaculo.get(i).get(0).getY() - head.getY() - 1) <= 2 ) {
					posibleChoqueSur = true;
				}
				if (obstaculo.get(i).get(0).getX() == head.getX() && Math.abs(obstaculo.get(i).get(0).getY() - head.getY() + 1) <= 2  ) {
					posibleChoqueNorte = true;
				}

		}
		
		boolean[] obstaculos = new boolean[8];
		obstaculos[0] = obstaculoNorte;
		obstaculos[1] = obstaculoEste;
		obstaculos[2] = obstaculoSur;
		obstaculos[3] = obstaculoOeste;
		obstaculos[4] = posibleChoqueNorte;
		obstaculos[5] = posibleChoqueEste;
		obstaculos[6] = posibleChoqueSur;
		obstaculos[7] = posibleChoqueOeste;
		
		
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
