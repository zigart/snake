package brains;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import edu.unlam.snake.brain.Brain;
import edu.unlam.snake.engine.Direction;
import edu.unlam.snake.engine.Point;

public class MyBrain extends Brain {

	private static final int SUROESTE = 7;
	private static final int NOROESTE = 6;
	private static final int SURESTE = 5;
	private static final int NORESTE = 4;
	private static final int OESTE = 3;
	private static final int SUR = 2;
	// Pueden agregarse todos los atributos necesarios
	Point[] posibleAvanceEnemigo;
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
		List<List<Point>> colisionesPosiblesObstaculos = new LinkedList();
		List<List<Point>> colisionesPosiblesSnake = new LinkedList();
		List<List<List<Point>>> conjuntoDeElementosColisionables = new LinkedList();
		colisionesPosiblesObstaculos.add(obstacles);
		colisionesPosiblesSnake.add(snake);
		conjuntoDeElementosColisionables.add(enemies);
		conjuntoDeElementosColisionables.add(colisionesPosiblesObstaculos);
		conjuntoDeElementosColisionables.add(colisionesPosiblesSnake);

		// completar con la lógica necesaria para mover la serpiente,
		// intentando comer la mayor cantidad de frutas y sobrevivir
		// el mayor tiempo posible.
		Point frutaMasCercana = buscarCosaMasCercana(head, fruits);
		return moveToFruit(head, enemies, conjuntoDeElementosColisionables, frutaMasCercana, previous);

	}

	private Direction moveToFruit(Point head, List<List<Point>> enemies, List<List<List<Point>>> elementosColisionables,
			Point frutaMasCercana, Direction previous) {

		Direction[] direccionesRecomendadas = direccionesRecomendadas(head, frutaMasCercana);

		// comprobar que no se encierra sola toma la cabeza como algo, no deberia.
		// Arreglar

		if (!comprobarSiHayAlgoEnCiertaDireccion(head, direccionesRecomendadas[0], elementosColisionables)
				&& previous.compatibleWith(direccionesRecomendadas[0])
				&& !posiblesMovimientosEnemigos(head, enemies, direccionesRecomendadas[0])
				&& !comprobarQueNoSeEncierra(head, direccionesRecomendadas[0], elementosColisionables)) {
			return direccionesRecomendadas[0];
		} else if (!comprobarSiHayAlgoEnCiertaDireccion(head, direccionesRecomendadas[1], elementosColisionables)
				&& previous.compatibleWith(direccionesRecomendadas[1])
				&& !posiblesMovimientosEnemigos(head, enemies, direccionesRecomendadas[1])
				&& !comprobarQueNoSeEncierra(head, direccionesRecomendadas[1], elementosColisionables)) {
			return direccionesRecomendadas[1];
		} else if (!comprobarSiHayAlgoEnCiertaDireccion(head, direccionesRecomendadas[2], elementosColisionables)
				&& previous.compatibleWith(direccionesRecomendadas[2])
				&& !posiblesMovimientosEnemigos(head, enemies, direccionesRecomendadas[2])
				&& !comprobarQueNoSeEncierra(head, direccionesRecomendadas[2], elementosColisionables)) {
			return direccionesRecomendadas[2];
		} else {
			return direccionesRecomendadas[3];
		}

	}

	private Direction[] direccionesRecomendadas(Point head, Point frutaMasCercana) {

		Direction[] direccionesRecomendadas = new Direction[4];

		int distanciaX = head.getX() - frutaMasCercana.getX();
		int distanciaY = head.getY() - frutaMasCercana.getY();

		if (Math.abs(distanciaX) > Math.abs(distanciaY)) {

			if (distanciaX > 0) {
				direccionesRecomendadas[0] = Direction.LEFT;
			} else {
				direccionesRecomendadas[0] = Direction.RIGHT;
			}

			if (distanciaY > 0) {
				direccionesRecomendadas[1] = Direction.DOWN;
			} else {
				direccionesRecomendadas[1] = Direction.UP;
			}

			if (distanciaY > 0) {
				direccionesRecomendadas[2] = Direction.UP;
			} else {
				direccionesRecomendadas[2] = Direction.DOWN;
			}

			if (distanciaX > 0) {
				direccionesRecomendadas[3] = Direction.RIGHT;
			} else {
				direccionesRecomendadas[3] = Direction.LEFT;
			}
		} else {

			if (distanciaY > 0) {
				direccionesRecomendadas[0] = Direction.DOWN;
			} else {
				direccionesRecomendadas[0] = Direction.UP;
			}

			if (distanciaX > 0) {
				direccionesRecomendadas[1] = Direction.LEFT;
			} else {
				direccionesRecomendadas[1] = Direction.RIGHT;
			}

			if (distanciaX > 0) {
				direccionesRecomendadas[2] = Direction.RIGHT;
			} else {
				direccionesRecomendadas[2] = Direction.LEFT;
			}

			if (distanciaY > 0) {
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

	private boolean posiblesMovimientosEnemigos(Point head, List<List<Point>> enemigos, Direction direccion) {
		posibleAvanceEnemigo = new Point[8];
		for (int i = 0; i < enemigos.size(); i++) {
			posibleAvanceEnemigo[NORTE] = new Point(enemigos.get(i).get(0).getX(), enemigos.get(i).get(0).getY() + 2);
			posibleAvanceEnemigo[ESTE] = new Point(enemigos.get(i).get(0).getX() + 2, enemigos.get(i).get(0).getY());
			posibleAvanceEnemigo[SUR] = new Point(enemigos.get(i).get(0).getX(), enemigos.get(i).get(0).getY() - 2);
			posibleAvanceEnemigo[OESTE] = new Point(enemigos.get(i).get(0).getX() - 2, enemigos.get(i).get(0).getY());
			posibleAvanceEnemigo[NORESTE] = new Point(enemigos.get(i).get(0).getX() + 1,
					enemigos.get(i).get(0).getY() + 1);
			posibleAvanceEnemigo[SURESTE] = new Point(enemigos.get(i).get(0).getX() + 1,
					enemigos.get(i).get(0).getY() - 1);
			posibleAvanceEnemigo[NOROESTE] = new Point(enemigos.get(i).get(0).getX() - 1,
					enemigos.get(i).get(0).getY() + 1);
			posibleAvanceEnemigo[SUROESTE] = new Point(enemigos.get(i).get(0).getX() - 1,
					enemigos.get(i).get(0).getY() - 1);
			if (comprobarSiPuedenLlegarAColisionarLasCabezasDeSnakes(head, posibleAvanceEnemigo, direccion)) {
				return true;
			}
		}
		return false;
	}

	private boolean comprobarSiPuedenLlegarAColisionarLasCabezasDeSnakes(Point head, Point[] posiblesPosiciones,
			Direction direccion) {
		int posicionHeadX = head.getX();
		int posicionHeadY = head.getY();
		int posicionEnemigaY = posiblesPosiciones[NORTE].getY() - 2;
		int posicionEnemigaX = posiblesPosiciones[ESTE].getY() - 2;

		boolean posiblesMovimientosEnemigosEnX;
		boolean posiblesMovimientosEnemigosEnY;
		boolean posiblesMovimientosHaciaEsquinasSuperioresEnemigos;
		boolean posiblesMovimientosHaciaEsquinasInferioresEnemigos;

		if (direccion == Direction.UP) {
			posiblesMovimientosEnemigosEnX = (posicionHeadX == posiblesPosiciones[ESTE].getX()
					&& posicionHeadY + 1 == posicionEnemigaY)
					|| (posicionHeadX == posiblesPosiciones[OESTE].getX() && posicionHeadY + 1 == posicionEnemigaY);
			posiblesMovimientosEnemigosEnY = (posicionHeadX == posicionEnemigaX
					&& posicionHeadY + 1 == posiblesPosiciones[NORTE].getY())
					|| (posicionHeadX == posicionEnemigaX && posicionHeadY + 1 == posiblesPosiciones[SUR].getY());
			posiblesMovimientosHaciaEsquinasSuperioresEnemigos = (posicionHeadX == posiblesPosiciones[NORESTE].getX()
					&& posicionHeadY + 1 == posiblesPosiciones[NORESTE].getY())
					|| (posicionHeadX == posiblesPosiciones[NOROESTE].getX()
							&& posicionHeadY + 1 == posiblesPosiciones[NOROESTE].getY());
			posiblesMovimientosHaciaEsquinasInferioresEnemigos = (posicionHeadX == posiblesPosiciones[SURESTE].getX()
					&& posicionHeadY + 1 == posiblesPosiciones[SURESTE].getY())
					|| (posicionHeadX == posiblesPosiciones[SUROESTE].getX()
							&& posicionHeadY + 1 == posiblesPosiciones[SUROESTE].getY());

		} else if (direccion == Direction.DOWN) {
			posiblesMovimientosEnemigosEnX = (posicionHeadX == posiblesPosiciones[ESTE].getX()
					&& posicionHeadY - 1 == posicionEnemigaY)
					|| (posicionHeadX == posiblesPosiciones[OESTE].getX() && posicionHeadY - 1 == posicionEnemigaY);
			posiblesMovimientosEnemigosEnY = (posicionHeadX == posicionEnemigaX
					&& posicionHeadY - 1 == posiblesPosiciones[NORTE].getY())
					|| (posicionHeadX == posicionEnemigaX && posicionHeadY - 1 == posiblesPosiciones[SUR].getY());
			posiblesMovimientosHaciaEsquinasSuperioresEnemigos = (posicionHeadX == posiblesPosiciones[NORESTE].getX()
					&& posicionHeadY - 1 == posiblesPosiciones[NORESTE].getY())
					|| (posicionHeadX == posiblesPosiciones[NOROESTE].getX()
							&& posicionHeadY - 1 == posiblesPosiciones[NOROESTE].getY());
			posiblesMovimientosHaciaEsquinasInferioresEnemigos = (posicionHeadX == posiblesPosiciones[SURESTE].getX()
					&& posicionHeadY - 1 == posiblesPosiciones[SURESTE].getY())
					|| (posicionHeadX == posiblesPosiciones[SUROESTE].getX()
							&& posicionHeadY - 1 == posiblesPosiciones[SUROESTE].getY());

		} else if (direccion == Direction.LEFT) {
			posiblesMovimientosEnemigosEnX = (posicionHeadX - 1 == posiblesPosiciones[ESTE].getX()
					&& posicionHeadY == posicionEnemigaY)
					|| (posicionHeadX - 1 == posiblesPosiciones[OESTE].getX() && posicionHeadY == posicionEnemigaY);
			posiblesMovimientosEnemigosEnY = (posicionHeadX - 1 == posicionEnemigaX
					&& posicionHeadY == posiblesPosiciones[NORTE].getY())
					|| (posicionHeadX - 1 == posicionEnemigaX && posicionHeadY == posiblesPosiciones[SUR].getY());
			posiblesMovimientosHaciaEsquinasSuperioresEnemigos = (posicionHeadX - 1 == posiblesPosiciones[NORESTE]
					.getX() && posicionHeadY == posiblesPosiciones[NORESTE].getY())
					|| (posicionHeadX - 1 == posiblesPosiciones[NOROESTE].getX()
							&& posicionHeadY == posiblesPosiciones[NOROESTE].getY());
			posiblesMovimientosHaciaEsquinasInferioresEnemigos = (posicionHeadX - 1 == posiblesPosiciones[SURESTE]
					.getX() && posicionHeadY == posiblesPosiciones[SURESTE].getY())
					|| (posicionHeadX - 1 == posiblesPosiciones[SUROESTE].getX()
							&& posicionHeadY == posiblesPosiciones[SUROESTE].getY());

		} else {
			posiblesMovimientosEnemigosEnX = (posicionHeadX + 1 == posiblesPosiciones[ESTE].getX()
					&& posicionHeadY == posicionEnemigaY)
					|| (posicionHeadX + 1 == posiblesPosiciones[OESTE].getX() && posicionHeadY == posicionEnemigaY);
			posiblesMovimientosEnemigosEnY = (posicionHeadX + 1 == posicionEnemigaX
					&& posicionHeadY == posiblesPosiciones[NORTE].getY())
					|| (posicionHeadX + 1 == posicionEnemigaX && posicionHeadY == posiblesPosiciones[SUR].getY());
			posiblesMovimientosHaciaEsquinasSuperioresEnemigos = (posicionHeadX + 1 == posiblesPosiciones[NORESTE]
					.getX() && posicionHeadY == posiblesPosiciones[NORESTE].getY())
					|| (posicionHeadX + 1 == posiblesPosiciones[NOROESTE].getX()
							&& posicionHeadY == posiblesPosiciones[NOROESTE].getY());
			posiblesMovimientosHaciaEsquinasInferioresEnemigos = (posicionHeadX + 1 == posiblesPosiciones[SURESTE]
					.getX() && posicionHeadY == posiblesPosiciones[SURESTE].getY())
					|| (posicionHeadX + 1 == posiblesPosiciones[SUROESTE].getX()
							&& posicionHeadY == posiblesPosiciones[SUROESTE].getY());

		}

		if (posiblesMovimientosEnemigosEnX || posiblesMovimientosEnemigosEnY
				|| posiblesMovimientosHaciaEsquinasSuperioresEnemigos
				|| posiblesMovimientosHaciaEsquinasInferioresEnemigos) {
			return true;
		}

		return false;
	}

	private boolean comprobarQueNoSeEncierra(Point head, Direction direccion, List<List<List<Point>>> cosa) {
		Point headCopia;
		if (direccion == Direction.UP) {
			headCopia = new Point(head.getX(), head.getY() + 1);
			return comprobarSiLasCuatroDireccionesEstanOcupadas(headCopia, cosa);
		} else if (direccion == Direction.DOWN) {
			headCopia = new Point(head.getX(), head.getY() - 1);
			return comprobarSiLasCuatroDireccionesEstanOcupadas(headCopia, cosa);
		} else if (direccion == Direction.LEFT) {
			headCopia = new Point(head.getX() - 1, head.getY());
			return comprobarSiLasCuatroDireccionesEstanOcupadas(headCopia, cosa);
		} else {
			headCopia = new Point(head.getX() + 1, head.getY());
			return comprobarSiLasCuatroDireccionesEstanOcupadas(headCopia, cosa);
		}
	}
	
	private boolean comprobarSiLasCuatroDireccionesEstanOcupadas(Point head,List<List<List<Point>>> cosa ) {
		boolean arriba =comprobarSiHayAlgoEnCiertaDireccion(head, Direction.UP, cosa);
		boolean abajo = comprobarSiHayAlgoEnCiertaDireccion(head, Direction.DOWN, cosa);
		boolean derecha = comprobarSiHayAlgoEnCiertaDireccion(head, Direction.RIGHT, cosa);
		boolean izquierda = comprobarSiHayAlgoEnCiertaDireccion(head, Direction.LEFT, cosa);
		
		if(arriba && abajo && derecha && izquierda) {
			return true;
		}
		return false;
	}

}
