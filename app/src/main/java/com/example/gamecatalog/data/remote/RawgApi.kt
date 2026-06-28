package com.example.gamecatalog.data.remote

import com.example.gamecatalog.data.remote.dto.RawgGamesResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RawgApi {

    @GET("games")
    suspend fun getGames(
        @Query("key") apiKey: String,
        @Query("page_size") pageSize: Int = 20,
        @Query("ordering") ordering: String = "-metacritic"
    ): RawgGamesResponseDto
}