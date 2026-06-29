package com.example.gamecatalog.data.remote

import com.example.gamecatalog.data.remote.dto.RawgGameDetailDto
import com.example.gamecatalog.data.remote.dto.RawgGamesResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RawgApi {

    @GET("games")
    suspend fun getGames(
        @Query("key") apiKey: String,
        @Query("page_size") pageSize: Int = 30,
        @Query("ordering") ordering: String = "-metacritic",
        @Query("genres") genres: String? = null
    ): RawgGamesResponseDto

    @GET("games/{id}")
    suspend fun getGameDetail(
        @Path("id") gameId: Int,
        @Query("key") apiKey: String
    ): RawgGameDetailDto
}