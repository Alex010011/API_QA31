package contactokhttp;

import com.google.gson.Gson;
import dto.AuthRegRequestDto;
import dto.AuthRegResponseDto;
import dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTestOkHttp {

    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");

    @Test
    public void loginTest() throws IOException {

        AuthRegRequestDto requestDto = AuthRegRequestDto.builder()
                .email("noa@gmail.com")
                .password("Nnoa12345$").build();

        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDto),JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        Assert.assertTrue(response.isSuccessful());

        AuthRegResponseDto responseDto =
                gson.fromJson(response.body().string(), AuthRegResponseDto.class);
        String token = responseDto.getToken();
        System.out.println(token);

        Assert.assertEquals(response.code(), 200);

    }

    @Test
    public void loginWrongPasswordFormat() throws IOException {
        AuthRegRequestDto requestDto = AuthRegRequestDto.builder()
                .email("noa@gmail.com")
                .password("Nnoa").build();

        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDto),JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        boolean successful = response.isSuccessful();
        Assert.assertFalse(successful);

        System.out.println(response.code());

        Assert.assertEquals(response.code(), 400);

 //       System.out.println(response.body().string());

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);

        System.out.println(errorDto.getMessage());
        System.out.println(errorDto.getDetails());

        Assert.assertEquals(errorDto.getMessage(),"Password length need be 8 or more symbols");
    }

    @Test
    public void loginWrongEmailFormat() throws IOException {
        AuthRegRequestDto requestDto = AuthRegRequestDto.builder()
                .email("noa@gmail.c")
                .password("Nnoa12345!").build();

        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDto),JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        boolean successful = response.isSuccessful();
        Assert.assertFalse(successful);

        System.out.println(response.code());

        Assert.assertEquals(response.code(), 400);

        //       System.out.println(response.body().string());

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);

        System.out.println(errorDto.getMessage());
        System.out.println(errorDto.getDetails());

        Assert.assertEquals(errorDto.getMessage(),"Wrong email format! Example: name@mail.com");
    }
    @Test
    public void loginWrongEmailOrPassword() throws IOException {

        AuthRegRequestDto requestDto = AuthRegRequestDto.builder()
                .email("noa@gmail.com")
                .password("Nnoa5432$").build();

        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDto),JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        boolean successful = response.isSuccessful();
        Assert.assertFalse(successful);

        System.out.println(response.code());

        Assert.assertEquals(response.code(), 401);

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);

        System.out.println(errorDto.getMessage());
        System.out.println(errorDto.getDetails());

        Assert.assertEquals(errorDto.getMessage(),"Wrong email or password!");

    }
}
