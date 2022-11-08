package brains;

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
		buscarCosaMasCercana(fruits, snake.get(0).getX(), snake.get(0).getY());
		// completar con la lógica necesaria para mover la serpiente,
		// intentando comer la mayor cantidad de frutas y sobrevivir
		// el mayor tiempo posible.

		int posicionX = fruits.get(0).getX();
		int posicionY = fruits.get(0).getY();
		int posicionXSnake = snake.get(0).getX();
		int posicionYSnake = snake.get(0).getY();

		System.out.println(posicionX + " " + posicionY);
		System.out.println("snake: " + posicionXSnake + " " + posicionYSnake);

		
		for (int i = 1; i < fruits.size(); i++) {
			System.out.println("distancia de fruta 1: " + (Math.abs((posicionX - posicionXSnake)) + Math.abs(( posicionY - posicionYSnake))));
			System.out.println("distancia de fruta 2: " + (Math.abs((fruits.get(i).getX() - posicionXSnake)) + Math.abs(fruits.get(i).getY() - posicionYSnake)));
			
			
			if (Math.abs((posicionX - posicionXSnake)) + Math.abs(( posicionY - posicionYSnake)) > Math
					.abs((fruits.get(i).getX() - posicionXSnake)) + Math.abs(fruits.get(i).getY() - posicionYSnake)) {
				System.out.println("se reemplaza el valor por: " + fruits.get(i));
				posicionX = fruits.get(i).getX();
				posicionY = fruits.get(i).getY();
			}
		}

		return moveToFruit(snake, posicionX, posicionY, previous);

	}

	private Direction moveToFruit(List<Point> snake, int masCercanaX, int masCercanaY, Direction previous) {
		// comprueba si la vivorita esta en el mismo lugar, si no lo esta ejecuta
		while (snake.get(0).getX() != masCercanaX || snake.get(0).getY() != masCercanaY) {
			// fruta a la izquierda
			if (snake.get(0).getX() > masCercanaX) {

				/*
				 * comprueba que el movimiento anterior no fue el contrario al que se quiere
				 * hacer. Si lo es, se mueve a otro lado
				 */
				if (!previous.equals(Direction.RIGHT)) {
					return Direction.LEFT;
				} else {
					// hay buscar la manera de que decida correctamente a donde
					//deberia haber una comprobacion que busque que donde mueve no hay nada
					return Direction.UP;
				}

			} else if (snake.get(0).getX() < masCercanaX) {

				/*
				 * comprueba que el movimiento anterior no fue el contrario al que se quiere
				 * hacer. Si lo es, se mueve a otro lado
				 */
				if (!previous.equals(Direction.LEFT)) {
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
					if (!previous.equals(Direction.UP)) {
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
					if (!previous.equals(Direction.DOWN)) {
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

	/*
	 * x = la posicion de la cabeza del snake y = la posicion de la cabeza del snake
	 * cosa = las posiciones en el mapa donde hay algo
	 */
//
//	public Posicion buscarCosaMasCercana(List<Point> cosa, int x, int y) {
//
//		// validarCasillero(x,y, cosa);
//
//		Posicion posicionDelObjeto = new Posicion();
//		// Se crea un flag que pasa a ser verdadero cuando encuentra algo
//		boolean cosaEncontrada = false;
//
//		// El contador comienza en la distancia mas chica que puede analizar
//		int contador = 1;
//
//		while (!cosaEncontrada || contador < 38) {
//
//			int i = contador;
//			while (i > 0) {
//
//				int j = 0;
//				do {
//
//					if (cosa.get(0).getX() + 1 == cosa && cosas[x - 1 + i][y - 1 + j] != Cosas.NADA) {
//
//						posicionDelObjeto.x = x + i;
//						posicionDelObjeto.y = y + j;
//						objetoEncontrado = true;
//
//					} else if (cosas[x - 1 - i][y - 1 - j] == cosa && cosas[x - 1 - i][y - 1 - j] != Cosas.NADA) {
//
//						posicionDelObjeto.x = x - i;
//						posicionDelObjeto.y = y - j;
//						objetoEncontrado = true;
//
//					} else if (cosas[x - 1 - i][y - 1 + j] == cosa && cosas[x - 1 - i][y - 1 + j] != Cosas.NADA) {
//
//						posicionDelObjeto.x = x - i;
//						posicionDelObjeto.y = y + j;
//						objetoEncontrado = true;
//
//					} else if (cosas[x - 1 + i][y - 1 - j] == cosa && cosas[x - 1 + i][y - 1 - j] != Cosas.NADA) {
//
//						posicionDelObjeto.x = x + i;
//						posicionDelObjeto.y = y - j;
//						objetoEncontrado = true;
//
//					}
//					i--;
//					j++;
//				} while (j <= i + contador);
//			}
//			contador++;
//		}
//
//		if (!objetoEncontrado) {
//			throw new Error("No se encontro ningun objeto de ese tipo en el mapa o la accion es invalida");
//		}
//		return posicionDelObjeto;
//	}

	class Posicion {
		int x, y;
	}


	public void buscarCosaMasCercana(List<Point> cosa, int x, int y) {

	}
}
