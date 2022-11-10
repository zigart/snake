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
		Point frutaMasCercana = new Point(posicionXY[0], posicionXY[1]);
		int[] obstaculoDefrutaXYMasCercano = new int[6];
		obstaculoDefrutaXYMasCercano = buscarCosaMasCercana(frutaMasCercana, obstacles);

		return moveToFruit(snake, head, obstacles, frutaMasCercana, previous);

	}

	private Direction moveToFruit(List<Point> snake, Point head, List<Point> obstacles, Point frutaMasCercana,
			Direction previous) {

		System.out.println("fruta mas cercana " + frutaMasCercana);

		if (seMovioEnX) {

			if (frutaMasCercana.getY() > head.getY()) {
				seMovioEnX = !seMovioEnX;
				return Direction.UP;
			}
			if (frutaMasCercana.getY() < head.getY()) {
				seMovioEnX = !seMovioEnX;
				return Direction.DOWN;
			}

		} else {

			if (frutaMasCercana.getX() > head.getX()) {
				seMovioEnX = !seMovioEnX;
				return Direction.RIGHT;
			}

			if (frutaMasCercana.getX() < head.getX()) {
				seMovioEnX = !seMovioEnX;
				return Direction.LEFT;
			}
		}

		return previous;

	}

	private boolean[] buscarObstaculoEnCabeza(Point head, List<Point> obstaculo) {
		int[] obstaculosCercanos = new int[6];
		obstaculosCercanos = buscarCosaMasCercana(head, obstaculo);
		boolean obstaculoNorte;
		boolean obstaculoSur;
		boolean obstaculoEste;
		boolean obstaculoOeste;

		for (int i = 0; i < obstaculosCercanos.length; i++) {

			if (i % 2 == 0) {
				if (head.getX() + 1 == obstaculosCercanos[i]) {
					obstaculoEste = true;
				}
				
				if (head.getX() - 1 == obstaculosCercanos[i]) {
					obstaculoOeste = true;
				}
			}else {
				if (head.getY() + 1 == obstaculosCercanos[i]) {
					obstaculoNorte = true;
				}
				
				if (head.getY() - 1 == obstaculosCercanos[i]) {
					obstaculoSur = true;
				}
			}
			
			
		}

		return true;
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

		if (cosa.size() > 1) {

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

		if (cosa.size() > 1) {

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

		System.out.println("dentro del metodo: " + Arrays.toString(positions));
		return positions;
	}

}
