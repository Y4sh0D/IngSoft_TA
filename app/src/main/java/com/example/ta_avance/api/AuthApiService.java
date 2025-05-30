package com.example.ta_avance.api;

import com.example.ta_avance.dto.barbero.BarberoRequest;
import com.example.ta_avance.dto.barbero.BarberoResponse;
import com.example.ta_avance.dto.barbero.BarberoSimpleResponse;
import com.example.ta_avance.dto.horario.GenericResponse;
import com.example.ta_avance.dto.horario.HorarioInstanciaResponse;
import com.example.ta_avance.dto.horario.TurnosDiaRequest;
import com.example.ta_avance.dto.login.LoginRequest;
import com.example.ta_avance.dto.login.LoginResponse;
import com.example.ta_avance.dto.recuperacion.RecuperacionRequest;
import com.example.ta_avance.dto.recuperacion.RecuperacionResponse;
import com.example.ta_avance.dto.refresh.RefreshRequest;
import com.example.ta_avance.dto.servicio.ServicioRequest;
import com.example.ta_avance.dto.servicio.ServicioResponse;
import com.example.ta_avance.dto.servicio.ServicioSimpleResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AuthApiService {
    @POST("api/auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("api/auth/register")
    Call<Void> register(@Body LoginRequest registerRequest);

    @POST("api/servicio/crear")
    Call<Void> crear(@Body ServicioRequest serviciosRequest);

    @POST("api/auth/refreshToken")
    Call<LoginResponse> refresh(@Body RefreshRequest refreshRequest);

    @POST("emailPassword/sendEmail")
    Call<RecuperacionResponse> recuperarContraseña(@Body RecuperacionRequest recuperacionRequest);

    @GET("api/barbero/listar")
    Call<BarberoResponse> listarBarberos();

    @POST("api/barbero/crear")
    Call<BarberoResponse> crearBarbero(@Body BarberoRequest request);

    @DELETE("api/barbero/eliminar/{id}")
    Call<BarberoResponse> eliminarBarbero(@Path("id") int id);

    @PUT("api/barbero/actualizar/{id}")
    Call<BarberoSimpleResponse> actualizarBarbero(@Path("id") int id, @Body BarberoRequest barberoRequest);

    @GET("api/servicio/listar")
    Call<ServicioResponse> listarServicios();

    @POST("api/servicio/crear")
    Call<ServicioResponse> crearServicio(@Body ServicioRequest request);

    @DELETE("api/servicio/eliminar/{id}")
    Call<ServicioResponse> eliminarServicio(@Path("id") int id);

    @PUT("api/servicio/actualizar/{id}")
    Call<ServicioSimpleResponse> actualizarServicio(@Path("id") int id, @Body ServicioRequest request);

    @GET("api/horarioInstancia/actual")
    Call<Map<String, List<HorarioInstanciaResponse>>> obtenerHorarioActual();

    @PUT("api/horarioBarberoBase/actualizarTurnosDia")
    Call<GenericResponse> actualizarTurnosDia(@Body TurnosDiaRequest request);

    @PUT("api/horarioBarberoBase/confirmarHorario")
    Call<GenericResponse> confirmarHorario();


}
