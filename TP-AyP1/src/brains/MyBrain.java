package brains;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import edu.unlam.snake.brain.Brain;
import edu.unlam.snake.engine.Direction;
import edu.unlam.snake.engine.Point;

public class MyBrain extends Brain {

	// magic numbers
	private static final int SUROESTE = 7;
	private static final int NOROESTE = 6;
	private static final int SURESTE = 5;
	private static final int NORESTE = 4;
	private static final int OESTE = 3;
	private static final int SUR = 2;
	private static final int ESTE = 1;
	private static final int NORTE = 0;

	

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
		// nuevas listas
		List<List<Point>> colisionesPosiblesObstaculos = new LinkedList();
		List<List<Point>> colisionesPosiblesSnake = new LinkedList();
		List<List<List<Point>>> conjuntoDeElementosColisionables = new LinkedList();
		List<Point> snakeClone = new LinkedList<>(snake);
		// formateo de listas
		snakeClone.remove(0);
		colisionesPosiblesObstaculos.add(obstacles);
		colisionesPosiblesSnake.add(snakeClone);
		conjuntoDeElementosColisionables.add(enemies);
		conjuntoDeElementosColisionables.add(colisionesPosiblesObstaculos);
		conjuntoDeElementosColisionables.add(colisionesPosiblesSnake);

		// busca la fruta mas cercana
		Point frutaMasCercana = buscarCosaMasCercana(head, fruits);

		return direccionarALaFruta(head, enemies, conjuntoDeElementosColisionables, frutaMasCercana, previous);

	}

	private Direction direccionarALaFruta(Point head, List<List<Point>> enemies,
			List<List<List<Point>>> elementosColisionables, Point frutaMasCercana, Direction previous) {

		// guarda las mejores direcciones a las que se puede dirigir
		Direction[] direccionesRecomendadas = direccionesRecomendadas(head, frutaMasCercana);

		// comprueba si los movimientos son validos y los realiza de ser posible
		if (comprobarMovimientoValido(head, direccionesRecomendadas[0], enemies, elementosColisionables, previous)) {
			return direccionesRecomendadas[0];
		} else if (comprobarMovimientoValido(head, direccionesRecomendadas[1], enemies, elementosColisionables,
				previous)) {
			return direccionesRecomendadas[1];
		} else if (comprobarMovimientoValido(head, direccionesRecomendadas[2], enemies, elementosColisionables,
				previous)) {
			return direccionesRecomendadas[2];
		} else {
			return direccionesRecomendadas[3];
		}

	}

	private boolean comprobarMovimientoValido(Point head, Direction direccion, List<List<Point>> enemies,
			List<List<List<Point>>> elementosColisionables, Direction previous) {
		return !comprobarSiHayAlgoEnCiertaDireccion(head, direccion, elementosColisionables)
				&& previous.compatibleWith(direccion) && !posiblesMovimientosEnemigos(head, enemies, direccion)
				&& !comprobarQueNoSeEncierraEnSuProximoMovimiento(head, previous, direccion, elementosColisionables);
	}

	private Direction[] direccionesRecomendadas(Point head, Point frutaMasCercana) {

		Direction[] direccionesRecomendadas = new Direction[4];
		//busca las distancias
		int distanciaEntreX = head.getX() - frutaMasCercana.getX();
		int distanciaEntreY = head.getY() - frutaMasCercana.getY();
		//en base a las distancias mira si esta mas lejos de x o de y, alternando su movimiento
		if (Math.abs(distanciaEntreX) > Math.abs(distanciaEntreY)) {
			//se va alternando el movimiento entre x e y para las posiciones recomendadas
			if (distanciaEntreX > 0) {
				direccionesRecomendadas[0] = Direction.LEFT;
			} else {
				direccionesRecomendadas[0] = Direction.RIGHT;
			}

			if (distanciaEntreY > 0) {
				direccionesRecomendadas[1] = Direction.DOWN;
			} else {
				direccionesRecomendadas[1] = Direction.UP;
			}

			if (distanciaEntreY > 0) {
				direccionesRecomendadas[2] = Direction.UP;
			} else {
				direccionesRecomendadas[2] = Direction.DOWN;
			}

			if (distanciaEntreX > 0) {
				direccionesRecomendadas[3] = Direction.RIGHT;
			} else {
				direccionesRecomendadas[3] = Direction.LEFT;
			}
		} else {

			if (distanciaEntreY > 0) {
				direccionesRecomendadas[0] = Direction.DOWN;
			} else {
				direccionesRecomendadas[0] = Direction.UP;
			}

			if (distanciaEntreX > 0) {
				direccionesRecomendadas[1] = Direction.LEFT;
			} else {
				direccionesRecomendadas[1] = Direction.RIGHT;
			}

			if (distanciaEntreX > 0) {
				direccionesRecomendadas[2] = Direction.RIGHT;
			} else {
				direccionesRecomendadas[2] = Direction.LEFT;
			}

			if (distanciaEntreY > 0) {
				direccionesRecomendadas[3] = Direction.UP;
			} else {
				direccionesRecomendadas[3] = Direction.DOWN;
			}
		}
		return direccionesRecomendadas;

	}

	public Point buscarCosaMasCercana(Point origen, List<Point> cosa) {

		Point position = cosa.get(0).clone();
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

	private boolean comprobarSiHayAlgoEnCiertaDireccion(Point posicion, Direction direccion,
			List<List<List<Point>>> cosa) {
		boolean objetoALaDerecha;
		boolean objetoALaIzquierda;
		boolean objetoArriba;
		boolean objetoAbajo;
		for (int i = 0; i < cosa.size(); i++) {
			for (int j = 0; j < cosa.get(i).size(); j++) {
				for (int k = 0; k < cosa.get(i).get(j).size(); k++) {
					//itera toda la lista y comprueba alrededor del punto si hay alguna colision posible con algo
					objetoALaDerecha = direccion == Direction.RIGHT
							&& posicion.getX() + 1 == cosa.get(i).get(j).get(k).getX()
							&& posicion.getY() == cosa.get(i).get(j).get(k).getY();
					objetoALaIzquierda = direccion == Direction.LEFT
							&& posicion.getX() - 1 == cosa.get(i).get(j).get(k).getX()
							&& posicion.getY() == cosa.get(i).get(j).get(k).getY();
					objetoArriba = direccion == Direction.UP && posicion.getY() + 1 == cosa.get(i).get(j).get(k).getY()
							&& posicion.getX() == cosa.get(i).get(j).get(k).getX();
					objetoAbajo = direccion == Direction.DOWN && posicion.getY() - 1 == cosa.get(i).get(j).get(k).getY()
							&& posicion.getX() == cosa.get(i).get(j).get(k).getX();
					if (objetoALaDerecha || objetoALaIzquierda || objetoArriba || objetoAbajo) {
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * Post:devuelve verdadero si la cabeza enemiga puede llegar a matar a la
	 * nuestra en el proximo turno
	 * 
	 * @param head
	 * @param enemigos
	 * @param direccion
	 * @return
	 */
	private boolean posiblesMovimientosEnemigos(Point head, List<List<Point>> enemigos, Direction direccion) {
		//intenta predecir los posibles movimientos enemigos para evitar que nuestra snake haga ese mismo y choque
		Point[] posibleAvanceEnemigo = new Point[8];
		boolean puedeChocar = false;
		for (int i = 0; i < enemigos.size() && !puedeChocar; i++) {
			// crea un cuadrado de posiciones al rededor de la cabeza de la snake enemiga
			posibleAvanceEnemigo[NORTE] = new Point(enemigos.get(i).get(0).getX(), enemigos.get(i).get(0).getY() + 1);
			posibleAvanceEnemigo[ESTE] = new Point(enemigos.get(i).get(0).getX() + 1, enemigos.get(i).get(0).getY());
			posibleAvanceEnemigo[SUR] = new Point(enemigos.get(i).get(0).getX(), enemigos.get(i).get(0).getY() - 1);
			posibleAvanceEnemigo[OESTE] = new Point(enemigos.get(i).get(0).getX() - 1, enemigos.get(i).get(0).getY());
			posibleAvanceEnemigo[NORESTE] = new Point(enemigos.get(i).get(0).getX() + 1,
					enemigos.get(i).get(0).getY() + 1);
			posibleAvanceEnemigo[SURESTE] = new Point(enemigos.get(i).get(0).getX() + 1,
					enemigos.get(i).get(0).getY() - 1);
			posibleAvanceEnemigo[NOROESTE] = new Point(enemigos.get(i).get(0).getX() - 1,
					enemigos.get(i).get(0).getY() + 1);
			posibleAvanceEnemigo[SUROESTE] = new Point(enemigos.get(i).get(0).getX() - 1,
					enemigos.get(i).get(0).getY() - 1);
			if (comprobarSiPuedenLlegarAColisionarLasCabezasDeSnakes(head, posibleAvanceEnemigo, direccion)) {
				puedeChocar = true;
			}
		}
		return puedeChocar;
	}

	private boolean comprobarSiPuedenLlegarAColisionarLasCabezasDeSnakes(Point head, Point[] posiblesPosiciones,
			Direction direccion) {
		
		int posicionHeadX = head.getX();
		int posicionHeadY = head.getY();

		boolean puedeChocar = false;

		//en base a nuestro movimiento verifica si va a coincidir con el posible movimiento enemigo
		if (direccion == Direction.LEFT) {

			for (int i = 0; i < posiblesPosiciones.length && !puedeChocar; i++) {

				if (posicionHeadX - 1 == posiblesPosiciones[i].getX()
						&& posicionHeadY == posiblesPosiciones[i].getY()) {
					puedeChocar = true;
				}

			}
		} else if (direccion == Direction.RIGHT) {

			for (int i = 0; i < posiblesPosiciones.length && !puedeChocar; i++) {

				if (posicionHeadX + 1 == posiblesPosiciones[i].getX()
						&& posicionHeadY == posiblesPosiciones[i].getY()) {
					puedeChocar = true;
				}

			}

		} else if (direccion == Direction.UP) {
			for (int i = 0; i < posiblesPosiciones.length && !puedeChocar; i++) {

				if (posicionHeadX == posiblesPosiciones[i].getX()
						&& posicionHeadY + 1 == posiblesPosiciones[i].getY()) {
					puedeChocar = true;
				}

			}
		} else {
			for (int i = 0; i < posiblesPosiciones.length && !puedeChocar; i++) {

				if (posicionHeadX == posiblesPosiciones[i].getX()
						&& posicionHeadY - 1 == posiblesPosiciones[i].getY()) {
					puedeChocar = true;
				}

			}
		}

		return puedeChocar;
	}

	private boolean comprobarQueNoSeEncierraEnSuProximoMovimiento(Point head, Direction previous, Direction direccion,
			List<List<List<Point>>> cosa) {
		Point headCopia;
		//comprueba que si realiza el movimiento no va a quedar entre 3 objetos. De no ser posible otra cosa morira
		if (direccion == Direction.UP) {
			headCopia = new Point(head.getX(), head.getY() + 1);
			return comprobarSiLasCuatroDireccionesEstanOcupadas(headCopia, direccion, cosa);
		} else if (direccion == Direction.DOWN) {
			headCopia = new Point(head.getX(), head.getY() - 1);
			return comprobarSiLasCuatroDireccionesEstanOcupadas(headCopia, direccion, cosa);
		} else if (direccion == Direction.LEFT) {
			headCopia = new Point(head.getX() - 1, head.getY());
			return comprobarSiLasCuatroDireccionesEstanOcupadas(headCopia, direccion, cosa);
		} else {
			headCopia = new Point(head.getX() + 1, head.getY());
			return comprobarSiLasCuatroDireccionesEstanOcupadas(headCopia, direccion, cosa);
		}
	}

	private boolean comprobarSiLasCuatroDireccionesEstanOcupadas(Point head, Direction direction,
			List<List<List<Point>>> cosa) {
		//toma los valores y la direccion, retornando un booleano con las posibles posiciones donde puede haber algo
		boolean arriba = comprobarSiHayAlgoEnCiertaDireccion(head, Direction.UP, cosa);
		boolean abajo = comprobarSiHayAlgoEnCiertaDireccion(head, Direction.DOWN, cosa);
		boolean derecha = comprobarSiHayAlgoEnCiertaDireccion(head, Direction.RIGHT, cosa);
		boolean izquierda = comprobarSiHayAlgoEnCiertaDireccion(head, Direction.LEFT, cosa);

		if (direction == Direction.UP) {
			return arriba && derecha && izquierda;
		} else if (direction == Direction.DOWN) {
			return abajo && derecha && izquierda;
		} else if (direction == Direction.LEFT) {
			return izquierda && arriba && abajo;
		} else {
			return derecha && arriba && abajo;
		}

	}

}
