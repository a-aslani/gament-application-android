package gamentorg.gament.services.network

import gamentorg.gament.services.network.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("v1/games")
    fun fetchAllGames(@Query("page") page: Int): Call<GamesResponse>

    @FormUrlEncoded
    @POST("v1/phone")
    fun sendPhoneNumber(@Field("phone") phone: String): Call<LoginPhoneResponse>

    @FormUrlEncoded
    @POST("v1/code/{phone}")
    fun checkVerifyCode(@Field("code") verifyCode: String, @Path("phone") phoneKey: String): Call<LoginVerifyCodeResponse>

    @GET("v1/users/info")
    fun fetchUserInfo(@Header("Authorization") authorization: String): Call<UserInfoResponse>

    @FormUrlEncoded
    @POST("v1/users/username")
    fun checkUsername(@Field("username") username: String): Call<CheckUsernameResponse>

    @Multipart
    @POST("v1/users")
    fun createUser(
        @Header("Register") registerToken: String,
        @Part image: MultipartBody.Part?,
        @Part("username") username: RequestBody,
        @Part("name") name: RequestBody,
        @Part("family") family: RequestBody) : Call<RegisterResponse>

    @GET("v1/tournaments/{game}")
    fun fetchAllTournaments(@Path("game") gameKey: String, @Query("page") page: Int): Call<TournamentsResponse>
}