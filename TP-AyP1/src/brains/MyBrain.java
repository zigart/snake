package brains;

import java.util.Arrays;
import java.util.List;

import edu.unlam.snake.brain.Brain;
import edu.unlam.snake.engine.Direction;
import edu.unlam.snake.engine.Point;

public class MyBrain extends Brain {

	private static final int ABAJO = 2;
	private static final int ARRIBA = 0;
	private static final int IZQUIERDA = 3;
	private static final int DERECHA = 1;

	boolean[] obstaculos;
	boolean[] parteDeSnake;
	boolean[] snakeContraria;
	// Pueden agregarse todos los atributos necesarios
	boolean flag = true;
	// 🤔 = parcialmente
	// 1. Buscar fruta mas cercana con el recorrido mas rapido. 🤔
	// 2. Comprobar a su alrededor que no haya ningun objeto. ✔
	// 3. Comprobar que en su proximo movimiento no hay cuerpo enemigo o que no van
	// a colisionar 🤔
	// las cabezas
	// 4. Comprobar que no choque con su propio cuerpo
	// 5. Comprobar que en modo battle royale tratar de mantenerse lejos de los
	// bordes.
	// 6. Comprobar que no se encierra sola, verificando que a 2 casilleros del head
	// no queda
	// entre su cuerpo y algo.
	// buscar otra logica para el 6 como por ejemplo girar cuando tiene cierto largo
	// 7. Realizar el movimiento validando las comprobaciones anteriores.

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
		Point frutaMasCercana = buscarCosaMasCercana(head, fruits);
		return moveToFruit(snake, head, enemies, obstacles, frutaMasCercana, previous);

	}

	private Direction moveToFruit(List<Point> snake, Point head, List<List<Point>> enemies, List<Point> obstacles,
			Point frutaMasCercana, Direction previous) {
		// sacar las declaraciones afuera
		obstaculos = buscarObstaculoEnMiCabeza(head, obstacles);
		parteDeSnake = buscarObstaculoEnMiCabeza(head, snake);
		snakeContraria = buscarEnemigoCercaDeMiCabeza(head, enemies);

		if (head.getX() > frutaMasCercana.getX()) {
			if (!estaOcupado(IZQUIERDA)) {
				return Direction.LEFT;
			} else if (!estaOcupado(ABAJO)) {
				return Direction.DOWN;
			} else if (!estaOcupado(ARRIBA)) {
				return Direction.UP;
			} else {
				return Direction.RIGHT;
			}

		} else if (head.getX() < frutaMasCercana.getX())

		{
			if (!estaOcupado(DERECHA)) {
				return Direction.RIGHT;
			} else if (!estaOcupado(ARRIBA)) {
				return Direction.UP;
			} else if (!estaOcupado(ABAJO)) {
				return Direction.DOWN;
			} else {
				return Direction.LEFT;
			}
		} else {
			if (head.getY() > frutaMasCercana.getY()) {
				if (!estaOcupado(ABAJO)) {
					return Direction.DOWN;
				} else if (!estaOcupado(DERECHA)) {
					return Direction.RIGHT;
				} else if (!estaOcupado(IZQUIERDA)) {
					return Direction.LEFT;
				} else {
					return Direction.UP;
				}
			} else if (head.getY() < frutaMasCercana.getY()) {
				if (!estaOcupado(ARRIBA)) {
					return Direction.UP;
				} else if (!estaOcupado(IZQUIERDA)) {
					// hay buscar la manera de que decida correctamente a donde
					return Direction.LEFT;
				} else if (!estaOcupado(DERECHA)) {
					return Direction.RIGHT;
				} else {
					return Direction.DOWN;
				}
			}
		}
		return previous;
	}

	private boolean[] buscarObstaculoEnMiCabeza(Point head, List<Point> obstaculo) {

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
		obstaculos[ARRIBA] = obstaculoNorte;
		obstaculos[DERECHA] = obstaculoEste;
		obstaculos[ABAJO] = obstaculoSur;
		obstaculos[IZQUIERDA] = obstaculoOeste;
		return obstaculos;
	}

	private boolean[] buscarEnemigoCercaDeMiCabeza(Point head, List<List<Point>> obstaculo) {

		boolean obstaculoNorte = false;
		boolean obstaculoSur = false; 
		boolean obstaculoEste = false;
		boolean obstaculoOeste = false;

		for (int i = 0; i < obstaculo.size(); i++) {

			for (int j = 0; j < obstaculo.get(i).size(); j++) {

				if (obstaculo.get(i).get(j).getX() == head.getX() + 1
						&& obstaculo.get(i).get(j).getY() == head.getY()) {
					obstaculoEste = true;
				}
				if (obstaculo.get(i).get(j).getX() == head.getX() - 1
						&& obstaculo.get(i).get(j).getY() == head.getY()) {
					obstaculoOeste = true;
				}
				if (obstaculo.get(i).get(j).getX() == head.getX()
						&& obstaculo.get(i).get(j).getY() == head.getY() - 1) {
					obstaculoSur = true;
				}
				if (obstaculo.get(i).get(j).getX() == head.getX()
						&& obstaculo.get(i).get(j).getY() == head.getY() + 1) {
					obstaculoNorte = true;
				}
			}
		}

		boolean[] obstaculos = new boolean[4];
		obstaculos[ARRIBA] = obstaculoNorte;
		obstaculos[DERECHA] = obstaculoEste;
		obstaculos[ABAJO] = obstaculoSur;
		obstaculos[IZQUIERDA] = obstaculoOeste;
		return obstaculos;
	}

	public Point buscarCosaMasCercana(Point origen, List<Point> cosa) {

		Point position = cosa.get(0).clone();
		// hacer clase privada con esto
		int distancia = Math.abs((position.getX() - origen.getX())) + Math.abs((position.getY() - origen.getY()));

		for (int i = 1; i < cosa.size(); i++) {

			int nuevaDistancia = Math.abs((cosa.get(i).getX() - origen.getX()))
					+ Math.abs((cosa.get(i).getY() - origen.getY()));

			if (distancia > nuevaDistancia) {
				distancia = nuevaDistancia;
				position = cosa.get(i).clone();
			}
		}

		return position;
	}

	private boolean estaOcupado(int direccion) {
		return obstaculos[direccion] || parteDeSnake[direccion] || snakeContraria[direccion];
	}

}
