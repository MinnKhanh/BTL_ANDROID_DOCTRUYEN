package com.example.appreadbook.API;

import com.example.appreadbook.Model.Categorydata;
import com.example.appreadbook.Model.Chap;
import com.example.appreadbook.Model.Product;
import com.example.appreadbook.Model.TaiKhoan;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson= new GsonBuilder().setDateFormat("yyy-MM-dd HH:mm:ss").setLenient().create();


    ApiService apiService=new Retrofit.Builder().baseUrl("http://192.168.43.160/")
            .addConverterFactory(GsonConverterFactory.create(gson)).build().create(ApiService.class);
    @GET("Android/display.php")
    Call<List<Categorydata>> getStudents();
    @GET("Android/display.php")
    Call<List<Categorydata>> getcate();
    @GET("Android/truyen.php")
    Call<List<Product>> gettruyen();
    @GET("Android/Loaitruyen.php")
    Call<List<Product>> getloaitruyen(@Query("idtype") String idtype);
    @GET("Android/removetruyen.php")
    Call<String> removetruyen(@Query("idtruyen") int idtruyen);
    @GET("Android/detail.php")
    Call<List<Chap>> getchap(@Query("idtruyen") int idtruyen);
    @GET("Android/post.php")
    Call<Product> inserttruyen(@Query("name") String name,@Query("tacgia") String tacgia, @Query("img") String img,
                               @Query("maTL") String maTL,
                              @Query("description") String description);
    @FormUrlEncoded
    @POST("Android/addchap.php")
    Call<Chap> addchap(@Field("namechap") int namechap, @Field("content") String content,
                       @Field("Matruyen") int Matruyen);

    @GET("Android/favorite.php")
    Call<List<Product>> getfavorite(@Query("taikhoan") String taikhoan,@Query("matkhau") String matkhau);


    @GET("Android/deletefavorite.php")
    Call<String> deletefavorite(@Query("taikhoan") String taikhoan, @Query("matkhau") String matkhau,@Query("idtruyen") int idtruyen);
    @GET("Android/addfavorite.php")
    Call<Integer> addfavorite(@Query("taikhoan") String taikhoan, @Query("matkhau") String matkhau,@Query("idtruyen") int idtruyen);


    @GET("Android/Dangnhap.php")
    Call<TaiKhoan> dangnhap(@Query("taikhoan") String taikhoan, @Query("matkhau") String matkhau);
    @GET("Android/Dangky.php")
    Call<TaiKhoan> dangky(@Query("taikhoan") String taikhoan, @Query("matkhau") String matkhau, @Query("pers") int pers);
    @FormUrlEncoded
    @POST("Android/update.php")
    Call<Product> update(@Field("id") int id,@Field("name") String name,@Field("tacgia") String tacgia,@Field("NgayXuat") String NgayXuat, @Field("img") String img,
                               @Field("maTL") String maTL,
                               @Field("description") String description);


    @GET("Android/removechap.php")
    Call<String> removechap(@Query("namechap") int namechap,@Query("idtruyen") int idtruyen);

    @FormUrlEncoded
    @POST("Android/updatechap.php")
    Call<String> updatechap(@Field("namechap") int namechap,@Field("newnamechap") int newnamechap, @Field("content") String content,
                       @Field("Matruyen") int Matruyen);
    @GET("Android/changeaccount.php")
    Call<TaiKhoan> changeaccount(@Query("taikhoan") String taikhoan, @Query("matkhau") String matkhau,
                                 @Query("pers") int pers,@Query("newtaikhoan") String newtaikhoan, @Query("newmatkhau") String newmatkhau);
    @GET("Android/getIntro.php")
    Call<List<Product>> getIntro();

    @FormUrlEncoded
    @POST("Android/post.php")
    Call<Product>sendPost(@Field("name") String name,@Field("tacgia") String tacgia, @Field("img") String img,
                         @Field("maTL") String maTL,
                         @Field("description") String description);

    @FormUrlEncoded
    @POST("Android/addcatery.php")
    Call<String> addcate(@Field("name") String name, @Field("img") String img,
                       @Field("description") String description);
    @FormUrlEncoded
    @POST("Android/updatecate.php")
    Call<String> updatecate(@Field("name") String name,
                               @Field("description") String description,@Field("newname") String newname, @Field("newimg") String newimg,
                                  @Field("newdescription") String newdescription);
    @GET("Android/removecate.php")
    Call<String> removecate(@Query("id") String id);
    @FormUrlEncoded
    @POST("Android/Notification.php")
    Call<String> sendNotification(@Field("name") String name,
                            @Field("addr") String addr,
                                  @Field("date") String date, @Field("gioitinh") String gioitinh);
    @GET("Android/dangnhap2.php")
    Call<TaiKhoan> dangnhap2(@Query("taikhoan") String taikhoan, @Query("matkhau") String matkhau);
}
