package contactokhttp;

import com.google.gson.Gson;
import dto.ContactDto;
import dto.ErrorDto;
import dto.GetAllContactsDto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetAllContactsTestOkHttp {

    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBnbWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo";

    @Test
    public void GetAllContactsSucceess() throws IOException {

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue( response.isSuccessful());
        Assert.assertEquals( response.code(), 200);

        GetAllContactsDto contacts = gson.fromJson(response.body().string(),GetAllContactsDto.class);
        List<ContactDto> list = contacts.getContacts();
        for (ContactDto contact:list) {
            System.out.println(contact.getId());
            System.out.println(contact.getEmail());
            System.out.println("****************");

        }

    }

    @Test
    public void getAllContactsWrongTokenTest() throws IOException {

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .addHeader("Authorization", "fgjeprglef")
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 401);

        ErrorDto errorDto = gson.fromJson(response.body().string(),ErrorDto.class);
        Assert.assertEquals(errorDto.getMessage(),"Wrong token format!");
        System.out.println(errorDto.getCode());
        System.out.println(errorDto.getDetails());

    }
}
