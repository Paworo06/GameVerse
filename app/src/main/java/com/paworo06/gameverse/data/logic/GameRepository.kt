package com.paworo06.gameverse.data.logic

import com.paworo06.gameverse.R
import com.paworo06.gameverse.data.model.Game

object GameRepository {
    private val listGames = listOf(
        Game(
            id = 1,
            name = "Minecraft",
            desc = "Juego de construcción y aventura en mundo abierto",
            price = 9.99,
            imageRes = R.drawable.game_minecraft
        ),
        Game(
            id = 2,
            name = "GTA V",
            desc = "Juego de acción en mundo abierto",
            price = 12.99,
            imageRes = R.drawable.game_gta_v
        ),
        Game(
            id = 3,
            name = "Hollow Knight",
            desc = "Metroidvania en 2D con profunda exploracion y narrativa ambiental",
            price = 5.99,
            imageRes = R.drawable.game_hollow_knight
        ),
        Game(
            id = 4,
            name = "Rainbow Six Siege",
            desc = "Shooter táctico en primera persona 5v5",
            price = 59.99,
            imageRes = R.drawable.game_rainbow_six_siege
        ),
        Game(
            id = 5,
            name = "The Last Of Us",
            desc = "RPG de aventura y supervivencia",
            price = 59.99,
            imageRes = R.drawable.game_the_last_of_us
        )
    )
    /**
     * Metodo que obtiene y devuelve un listado de todos los juegos
     * @return List<Game> con todos los juegos existentes
     */
    fun getAllGames(): List<Game> {
        return listGames
    }

    /**
     * Metodo que obtiene un juego específico buscado por un ID único
     * @param id int del identificador a buscar
     * @return objeto Game específico
     */
    fun getGameById(id: Int): Game? {
        for (game in getAllGames()) {
            if (game.id == id) {
                return game
            }
        }
        return null
    }

    /**
     * Metodo que obtiene un juego específico buscado por el nombre
     * @param name String del nombre del juego a buscar
     * @return objeto Game específico
     */
    fun searchGameByName(name: String): Game? {
        for (game in getAllGames()) {
            if (game.name.equals(name, ignoreCase = true)) {
                return game
            }
        }
        return null
    }
}