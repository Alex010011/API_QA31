package contactokhttp;

import com.google.gson.Gson;
import dto.AuthRegRequestDto;
import dto.AuthRegResponseDto;
import dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class RegistrationTest {

    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");

    @Test
    public void registrationSuccess() throws IOException {
        int index = (int) (System.currentTimeMillis()/1000)%3600;

        AuthRegRequestDto requestDto = AuthRegRequestDto.builder()
                .email("testqa"+index+"@gmail.com")
                .password("Nnoa12345$").build();

        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDto), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/registration")
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
    public void registrationUserExists() throws IOException {
        AuthRegRequestDto requestDto = AuthRegRequestDto.builder()
                .email("noa@gmail.com")
                .password("Nnoa54321$").build();

        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDto),JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/registration")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        boolean successful = response.isSuccessful();
        Assert.assertFalse(successful);

        System.out.println(response.code());

        Assert.assertEquals(response.code(), 409);

        //       System.out.println(response.body().string());

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);

        System.out.println(errorDto.getMessage());
        System.out.println(errorDto.getDetails());

        Assert.assertEquals(errorDto.getMessage(),"User already exist!");
    }

    @Test
    public void registrationWrongEmailFormat() throws IOException {
        AuthRegRequestDto requestDto = AuthRegRequestDto.builder()
                .email("noa@gmail.m")
                .password("Nnoa54321$").build();

        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDto),JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/registration")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        boolean successful = response.isSuccessful();
        Assert.assertFalse(successful);

        System.out.println(response.code());

        Assert.assertEquals(response.code(), 400);

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);

        System.out.println(errorDto.getMessage());
        System.out.println(errorDto.getDetails());

        Assert.assertEquals(errorDto.getMessage(),"Wrong email format! Example: name@mail.com");
    }

    @Test
    public void registrationWrongPassFormat() throws IOException {
        AuthRegRequestDto requestDto = AuthRegRequestDto.builder()
                .email("noa@gmail.com")
                .password("Nnoa54321").build();

        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDto),JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/registration")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        boolean successful = response.isSuccessful();
        Assert.assertFalse(successful);

        System.out.println(response.code());

        Assert.assertEquals(response.code(), 400);

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);

        System.out.println(errorDto.getMessage());
        System.out.println(errorDto.getDetails());

        Assert.assertEquals(errorDto.getMessage(),"Password must contain at least one special symbol from ['$','~','-','_']!");
    }
}
